package com.example.demo.repository;

import com.example.demo.entity.PhonePrefix;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PhonePrefixRepository extends JpaRepository<PhonePrefix,Long> {
    boolean existsByPhonePrefix(String phonePrefix);
    Optional<PhonePrefix> findByPhonePrefix(String phonePrefix);
}
