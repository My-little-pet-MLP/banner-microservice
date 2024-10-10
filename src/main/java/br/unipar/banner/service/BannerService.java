package br.unipar.banner.service;

import br.unipar.banner.model.Banner;
import br.unipar.banner.repositories.BannerRepository;
import jakarta.persistence.EntityNotFoundException;
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
        return bannerRepository.save(banner);
    }

    public List<Banner> getActiveBanners() {
        return bannerRepository.findByIsActiveTrue();
    }

    public List<Banner> getBannersByLojaId(String lojaId) {
        return bannerRepository.findByLojaId(lojaId);
    }

    public Banner desativarBanner(UUID id) {
        Banner banner = bannerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Banner não encontrado com o id: " + id));

        banner.setIsActive(false);
        return bannerRepository.save(banner);
    }

    public Page<Banner> listBanners(String lojaId, Pageable pageable) {
        return bannerRepository.findByLojaId(lojaId, pageable);
    }
}
