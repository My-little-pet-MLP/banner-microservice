package br.unipar.banner.controllers;

import br.unipar.banner.exceptions.BannerNotFoundException;
import br.unipar.banner.images.ImageStorageProperties;
import br.unipar.banner.model.Banner;
import br.unipar.banner.service.BannerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import br.unipar.banner.exceptions.ImageNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/banners")
public class BannerController {

    @Autowired
    private BannerService bannerService;

    //lugar para armazenar o caminho do arquivo
    private final Path imageStorageLocation;

    //inicializando o local de armazenamento
    public BannerController(ImageStorageProperties imageStorageProperties) {
        //local é relativo, então precisa transformar em absoluto
        this.imageStorageLocation = Paths.get(imageStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();
    }

    @PostMapping("/store/{lojaId}")
    public ResponseEntity<Banner> insert(@RequestParam("banner") String bannerData,
                                         @RequestParam("file") MultipartFile file,
                                         @PathVariable String lojaId) {
        try {
            System.out.println("Recebendo requisição para inserir banner.");

            // Parse the banner data from JSON
            ObjectMapper objectMapper = new ObjectMapper();
            Banner banner = objectMapper.readValue(bannerData, Banner.class);
            banner.setLojaId(lojaId);

            // Handle file upload and set imageUrl
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            Path targetLocation = imageStorageLocation.resolve(fileName);
            try {
                file.transferTo(targetLocation);
            } catch (IOException e) {
                throw new ImageNotFoundException("Erro ao baixar o arquivo: " + file.getOriginalFilename(), e);
            }

            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/banners/download/")
                    .path(fileName)
                    .toUriString();
            banner.setImageUrl(fileDownloadUri);

            Banner savedBanner = bannerService.insert(banner, file);
            System.out.println("URL de download da imagem: " + fileDownloadUri);
            System.out.println("Banner salvo com sucesso no banco de dados.");

            return ResponseEntity.ok(savedBanner);
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        } catch(Exception  e) {
            e.printStackTrace(); // Log de qualquer outra exceção não prevista
            return ResponseEntity.status(500).build(); // Retornar erro de servidor
        }
    }

    @GetMapping("/sort")
    public ResponseEntity<List<Banner>> sortBanner() {
        List<Banner> banners = bannerService.sortBanner();
        return ResponseEntity.ok(banners);
    }

    @GetMapping("/loja/{lojaId}")
    public List<Banner> getBannersByLojaId(@PathVariable String lojaId) {
        return bannerService.getBannersByLojaId(lojaId);
    }

    @PutMapping("/desativar/{bannerId}")
    public ResponseEntity<Banner> desativarBanner(@PathVariable UUID bannerId) {
        try {
            Banner banner = bannerService.findById(bannerId);

            if (banner == null) {
                return ResponseEntity.notFound().build();
            }

            banner.setIsActive(false); // Desativa o banner
            Banner updatedBanner = bannerService.update(banner); // Use um método apropriado para atualizar o banner

            return ResponseEntity.ok(updatedBanner);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
