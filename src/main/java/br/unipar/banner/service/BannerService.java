package br.unipar.banner.service;

import br.unipar.banner.dto.BannerDTO;
import br.unipar.banner.model.Banner;
import br.unipar.banner.repositories.BannerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class BannerService {

    @Autowired
    private BannerRepository bannerRepository;

    public Banner createBanner(BannerDTO bannerDTO) {
        Banner banner = new Banner();
        banner.setId(UUID.randomUUID());
        banner.setTitle(bannerDTO.getTitle());
        banner.setImageUrl(bannerDTO.getImageUrl());
        banner.setIsActive(bannerDTO.isActive());
        banner.setPaid(bannerDTO.isPaid());
        banner.setExternLink(bannerDTO.getExternLink());
        banner.setCredit(bannerDTO.getCredit());
        banner.setCreatedAt(new Date());
        banner.setDeadLine(bannerDTO.getDeadLine());
        banner.setNumberOfClicks(bannerDTO.getNumberOfClicks());
        banner.setLojaId(bannerDTO.getLojaId());

        return bannerRepository.save(banner);
    }

    public List<Banner> sortBanner() {
        return bannerRepository.findByIsActiveTrue();
    }

    public List<Banner> getBannersByLojaId(String lojaId) {
        return bannerRepository.findByLojaId(lojaId);
    }

    public Banner findById(UUID bannerId) {
        return bannerRepository.findById(bannerId).orElse(null);
    }

    public Banner update(Banner banner) {
        return bannerRepository.save(banner);
    }

}
