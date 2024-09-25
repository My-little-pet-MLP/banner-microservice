package br.unipar.banner.controllers;

import br.unipar.banner.dto.BannerDTO;
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

    @PostMapping
    public ResponseEntity<Banner> createBanner(@RequestBody BannerDTO bannerDTO) {
        Banner banner = bannerService.createBanner(bannerDTO);
        return new ResponseEntity<>(banner, HttpStatus.CREATED);
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

            banner.setIsActive(false);
            Banner updatedBanner = bannerService.update(banner);

            return ResponseEntity.ok(updatedBanner);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/getHelloWorld")
    public ResponseEntity<String> getHelloWorld() {
        return ResponseEntity.ok("Hello, World!");
    }

}
