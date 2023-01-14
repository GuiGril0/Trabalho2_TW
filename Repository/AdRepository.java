package com.roytuts.spring.boot.security.form.based.jdbc.userdetailsservice.auth.Repository;

import com.roytuts.spring.boot.security.form.based.jdbc.userdetailsservice.auth.Entity.Ad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdRepository extends JpaRepository<Ad, Long> {
    List<Ad> findTop3ByOrderByDateDesc();

    List<Ad> findByType(String type);

    List<Ad> findByTypeAndLocal(String type, String local);

    List<Ad> findByTypeAndAdvertiser(String type, String advertiser);

    List<Ad> findByTypeAndLocalAndAdvertiser(String type, String local, String advertiser);
}
