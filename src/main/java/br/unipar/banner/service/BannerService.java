package br.unipar.banner.service;

import br.unipar.banner.model.Banner;
import br.unipar.banner.repositories.BannerRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BannerService {

    @Autowired
    private BannerRepository bannerRepository;

    public Banner createBanner(Banner banner) {
        if (banner.getLojaId() == null || banner.getLojaId().isEmpty()) {
            throw new ConstraintViolationException("lojaId não pode ser nulo ou vazio", null);
        }
        if (banner.getTitle() == null || banner.getTitle().isEmpty()) {
            throw new ConstraintViolationException("title não pode ser nulo ou vazio", null);
        }
        if (banner.getImageUrl() == null || banner.getImageUrl().isEmpty()) {
            throw new ConstraintViolationException("imageUrl não pode ser nulo ou vazio", null);
        }
        if (banner.getExternLink() == null || banner.getExternLink().isEmpty()) {
            throw new ConstraintViolationException("ExternLink não pode ser nulo ou vazio", null);
        }
        if (banner.getCredit() < 0) {
            throw new IllegalArgumentException("O valor de crédito não pode ser negativo.");
        }

        return bannerRepository.save(banner);
    }

    public List<Banner> getActiveBanners() {
        List<Banner> banners = bannerRepository.findByIsActiveTrue();
        if (banners.isEmpty()) {
            throw new EntityNotFoundException("Nenhum banner ativo encontrado.");
        }
        return banners;
    }

    public List<Banner> getBannersByLojaId(String lojaId) {
        List<Banner> banners = bannerRepository.findByLojaId(lojaId);
        if (banners.isEmpty()) {
            throw new EntityNotFoundException("Nenhum banner encontrado para a loja com o id: " + lojaId);
        }
        return banners;
    }

    public Banner desativarBanner(UUID id) {
        Banner banner = bannerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Banner não encontrado com o id: " + id));

        if (!banner.isActive()) {
            throw new IllegalStateException("O banner já está desativado.");
        }

        banner.setIsActive(false);
        return bannerRepository.save(banner);
    }

    public Page<Banner> listBanners(String lojaId, Pageable pageable) {
        Page<Banner> banners = bannerRepository.findByLojaId(lojaId, pageable);
        if (banners.isEmpty()) {
            throw new EntityNotFoundException("Nenhum banner encontrado para a loja com o id: " + lojaId);
        }
        return banners;
    }

    public Banner findById(UUID id) {
        return bannerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Banner não encontrado com o id: " + id));
    }

}
