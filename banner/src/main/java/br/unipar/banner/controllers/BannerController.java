package br.unipar.banner.controllers;

import br.unipar.banner.dto.BannerDTO;
import br.unipar.banner.model.Banner;
import br.unipar.banner.service.BannerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/banners")
public class BannerController {

    @Autowired
    private BannerService bannerService;

    @Operation(description = "Criar um banner")
    @PostMapping
    public ResponseEntity<Banner> createBanner(@RequestBody BannerDTO bannerDTO) {
        Banner banner = bannerService.createBanner(bannerDTO);
        return new ResponseEntity<>(banner, HttpStatus.CREATED);
    }

    @Operation(description = "Seleciona os banners para exibir")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "retorna os banners"),
            @ApiResponse(responseCode = "400", description = "Não existe o banner selecionado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/sort")
    public ResponseEntity<List<Banner>> sortBanner() {
        List<Banner> banners = bannerService.sortBanner();
        return ResponseEntity.ok(banners);
    }

    @Operation(description = "Seleciona os banners por id da loja")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "retorna os banners da loja especificada"),
            @ApiResponse(responseCode = "400", description = "Não existe loja com o id informado"),
            @ApiResponse(responseCode = "404", description = "Banner não encontrado com o ID da loja especificado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/loja/{lojaId}")
    public List<Banner> getBannersByLojaId(@PathVariable String lojaId) {
        return bannerService.getBannersByLojaId(lojaId);
    }

    @Operation(description = "Desativa um banner pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Banner desativado com sucesso"),
            @ApiResponse(responseCode = "400", description = "ID do banner inválido"),
            @ApiResponse(responseCode = "404", description = "Banner não encontrado com o ID especificado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
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
