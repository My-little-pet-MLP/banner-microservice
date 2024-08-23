package br.unipar.banner.service;

import br.unipar.banner.exceptions.ImageNotFoundException;
import br.unipar.banner.images.ImageStorageProperties;
import br.unipar.banner.model.Banner;
import br.unipar.banner.repositories.BannerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class BannerService {

    private final BannerRepository bannerRepository;
    private final Path imageStorageLocation;

    @Autowired
    public BannerService(BannerRepository bannerRepository, ImageStorageProperties imageStorageProperties) {
        this.bannerRepository = bannerRepository;
        this.imageStorageLocation = Paths.get(imageStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();
    }

    @Transactional
    public Banner insert(Banner banner, MultipartFile file) throws IOException {
        System.out.println("Inserindo banner no banco de dados.");

        // Handle file upload and set imageUrl
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Path targetLocation = imageStorageLocation.resolve(fileName);

        try {
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new ImageNotFoundException("Error uploading image: " + fileName, e);
        }

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/banners/download/")
                .path(fileName)
                .toUriString();
        banner.setImageUrl(fileDownloadUri);

        banner.setCreatedAt(new Date());

        System.out.println("Banner salvo: " + banner);
        return bannerRepository.save(banner);
    }

    public List<Banner> sortBanner() {
        Pageable pageable = PageRequest.of(0, 3);
        List<Banner> banners = bannerRepository.findAll(pageable).getContent();
        return banners;
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
