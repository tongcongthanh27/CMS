package com.example.demo.repository;

import com.example.demo.entity.PasswordResetToken;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken,Integer> {
    // Tìm token để xác thực khi user click vào link
    Optional<PasswordResetToken> findByToken(String token);
    @Query("SELECT p.email FROM PasswordResetToken p WHERE p.token = :token")
    Optional<String> findEmailByToken(@Param("token") String token);
    // Xóa token cũ của user (tương đương với việc xóa reverse mapping trong Redis)
    void deleteByEmail(String email);
}
