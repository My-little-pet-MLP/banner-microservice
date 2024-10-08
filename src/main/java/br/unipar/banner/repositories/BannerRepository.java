package br.unipar.banner.repositories;

import br.unipar.banner.model.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BannerRepository extends JpaRepository<Banner, UUID> {

    List<Banner> findByLojaId(String lojaId);
    List<Banner> findByIsActiveTrue();
    Page<Banner> findByLojaId(String lojaId, Pageable pageable);
}
