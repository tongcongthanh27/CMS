package com.example.demo.service;

import com.example.demo.dto.request.auth.ForgotPasswordRequest;
import com.example.demo.dto.request.auth.ResetPasswordRequest;
import com.example.demo.entity.PasswordResetToken;
import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.repository.PasswordResetTokenRepository;
import com.example.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;
@Slf4j
@Service
@RequiredArgsConstructor // thay the cho autowired
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PasswordResetService {
    PasswordResetService passwordResetService;
    PasswordResetTokenRepository passwordResetTokenRepository;
    private static final long EXPIRY_MINUTES = 15;
    UserRepository userRepository;
    private final EmailService emailService;

    public void forgotPassword(ForgotPasswordRequest request){
        userRepository.findByEmail(request.getEmail()).ifPresent(user -> {
            String token = createToken(user.getEmail());
            emailService.sendPasswordResetEmail(user.getEmail(),
                    token,EXPIRY_MINUTES);
        });
    }

//    public void resetPassword(ResetPasswordRequest request){
//        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
//            throw new AppException(ErrorCode.PASSWORD_CONFIRM_NOT_MATCH);
//        }
//        String email = passwordResetTokenRepository.findByToken(request.getToken()).o
//
//        }
//        var user = userRepository.findByEmail()
//    }














    @Transactional
    public String createToken(String email){
        passwordResetTokenRepository.deleteByEmail(email);

        String token = UUID.randomUUID().toString();
        LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(EXPIRY_MINUTES);

        PasswordResetToken resetToken = PasswordResetToken.builder()
                .token(token)
                .email(email)
                .expiryDate(expiryDate)
                .build();
        passwordResetTokenRepository.save(resetToken);
        log.info("Password reset token created for email: {}", email);
        return token;

    }

}
