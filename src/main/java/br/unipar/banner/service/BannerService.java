package br.unipar.banner.service;

import br.unipar.banner.dto.BannerDTO;
import br.unipar.banner.model.Banner;
import br.unipar.banner.repositories.BannerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Calendar;
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

        Date createdAt = new Date();
        banner.setCreatedAt(createdAt);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(createdAt);
        calendar.add(Calendar.DAY_OF_MONTH, bannerDTO.getCredit()); // Adicionar o crédito em dias
        Date deadLine = calendar.getTime();
        banner.setDeadLine(deadLine);
        
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

    //listAll com especificar idLoja, número da página e quantidade de produtos
    public Page<Banner> listAllPagina(String lojaId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return bannerRepository.findAllByLojaId(lojaId, pageable);
    }
}
