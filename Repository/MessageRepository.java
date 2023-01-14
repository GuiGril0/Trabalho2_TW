package com.roytuts.spring.boot.security.form.based.jdbc.userdetailsservice.auth.Repository;

import com.roytuts.spring.boot.security.form.based.jdbc.userdetailsservice.auth.Entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByAid(long aid);


}
