package br.unipar.banner.controllers;

import br.unipar.banner.dto.BannerDTO;
import br.unipar.banner.model.Banner;
import br.unipar.banner.service.BannerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/banners")
public class BannerController {

    @Autowired
    private BannerService bannerService;

    @Operation(summary = "Cria um novo banner para uma loja específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Banner criado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Banner.class))),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro no servidor",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<Banner> createBanner(@RequestParam String lojaId, @RequestBody Banner banner) {
        banner.setCreatedAt(new Date());
        banner.setDeadLine(calculateDeadLine(banner.getCredit(), banner.getCreatedAt()));
        banner.setLojaId(lojaId);

        Banner savedBanner = bannerService.createBanner(banner);
        return ResponseEntity.ok(savedBanner);
    }

    // Método para calcular a deadLine com base no crédito (R$5 = 1 dia)
    private Date calculateDeadLine(double credit, Date createdAt) {
        int daysToAdd = (int) (credit / 5);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(createdAt);
        calendar.add(Calendar.DAY_OF_YEAR, daysToAdd);
        return calendar.getTime();
    }

    // Método para listar todos os banners ativos
    @Operation(summary = "Lista todos os banners ativos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Banners ativos listados com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Banner.class))),
            @ApiResponse(responseCode = "500", description = "Erro no servidor",
                    content = @Content)
    })
    @GetMapping("/active")
    public ResponseEntity<List<Banner>> listActiveBanners() {
        List<Banner> activeBanners = bannerService.getActiveBanners();
        return ResponseEntity.ok(activeBanners);
    }

    @Operation(description = "Seleciona os banners por id da loja")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Banners listados com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro no servidor",
                    content = @Content)
    })
    @GetMapping("/lista-banners/{lojaId}")
    public List<Banner> getBannersByLojaId(@PathVariable String lojaId) {
        return bannerService.getBannersByLojaId(lojaId);
    }

    @Operation(summary = "Desativa um banner alterando isActive para false")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Banner desativado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Banner.class))),
            @ApiResponse(responseCode = "404", description = "Banner não encontrado",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Erro no servidor",
                    content = @Content)
    })
    @PutMapping("/desativar/{bannerId}")
    public ResponseEntity<Banner> desativarBanner(@PathVariable UUID bannerId) {
        Banner updatedBanner = bannerService.desativarBanner(bannerId);
        return ResponseEntity.ok(updatedBanner);
    }


    @Operation(summary = "Lista os banners de uma loja com paginação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Banners listados com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro no servidor",
                    content = @Content)
    })
    @GetMapping("/lista-banners/{lojaId}/{page}")
    public ResponseEntity<Page<Banner>> listAllPagina(
            @RequestParam String lojaId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<Banner> banners = bannerService.listBanners(lojaId, PageRequest.of(page, size));
        return ResponseEntity.ok(banners);
    }

    @Operation(summary = "Busca um banner por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Banner encontrado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Banner.class))),
            @ApiResponse(responseCode = "404", description = "Banner não encontrado",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Erro no servidor",
                    content = @Content)
    })
    @GetMapping("/{bannerId}")
    public ResponseEntity<Banner> findById(@PathVariable UUID bannerId) {
        Banner banner = bannerService.findById(bannerId);
        return ResponseEntity.ok(banner);
    }

}
