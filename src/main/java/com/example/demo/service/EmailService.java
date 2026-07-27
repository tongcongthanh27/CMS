package com.example.demo.service;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;

    @Value("${app.frontend-url}")
    private String frontendUrl;

    @Value("${spring.mail.username}")
    private String fromEmail;
    public void sendPasswordResetEmail(String toEmail, String token, long expiryMinutes){
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,"UTF-8");

            mimeMessageHelper.setFrom(fromEmail);
            mimeMessageHelper.setTo(toEmail);
            mimeMessageHelper.setSubject("[CMS VNPT] Đặt lại mật khẩu");

            String resetLink = frontendUrl + "/reset-password?token" + token;
            String htmlContent = buildEmailContent(resetLink,expiryMinutes);
            mimeMessageHelper.setText(htmlContent,true);

            javaMailSender.send(mimeMessage);
            log.info("Password reset email sent to: {}", toEmail);
        } catch (Exception e) {
            log.error("Failed to send password reset email to: {}", toEmail, e);
        }

    }
    private String buildEmailContent(String resetLink, long expiryTime){
        return """
            <div style="font-family: Arial, sans-serif; max-width: 600px; margin: 20px auto; border: 1px solid #dce2e6; border-radius: 8px; overflow: hidden; box-shadow: 0 4px 6px rgba(0,0,0,0.05);">
                <!-- Header -->
                <div style="background-color: #005BAA; padding: 25px 20px; text-align: center;">
                    <h2 style="color: #ffffff; margin: 0; font-size: 24px; letter-spacing: 1px;">VNPT CMS</h2>
                    <p style="color: #e0f0ff; margin: 5px 0 0 0; font-size: 14px;">Hệ thống Quản trị Nội dung</p>
                </div>
                
                <!-- Body -->
                <div style="padding: 30px 25px;">
                    <h3 style="color: #333333; margin-top: 0; border-bottom: 2px solid #f0f0f0; padding-bottom: 10px;">
                        Yêu cầu đặt lại mật khẩu
                    </h3>
                    <p style="color: #4a4a4a; line-height: 1.6; font-size: 15px;">Xin chào,</p>
                    <p style="color: #4a4a4a; line-height: 1.6; font-size: 15px;">
                        Hệ thống ghi nhận một yêu cầu đặt lại mật khẩu cho tài khoản quản trị của bạn trên <strong>VNPT CMS</strong>. 
                        Vui lòng nhấp vào nút bên dưới để tiến hành thiết lập mật khẩu mới:
                    </p>
                    
                    <!-- Call to Action Button -->
                    <div style="text-align: center; margin: 35px 0;">
                        <a href="%s"
                           style="display: inline-block; padding: 14px 32px;
                                  background-color: #005BAA; color: #ffffff;
                                  text-decoration: none; border-radius: 6px;
                                  font-weight: bold; font-size: 16px;
                                  box-shadow: 0 2px 4px rgba(0, 91, 170, 0.3);">
                            Đặt lại mật khẩu
                        </a>
                    </div>
                    
                    <!-- Warning & Expiry -->
                    <div style="background-color: #fff9e6; border-left: 4px solid #f0ad4e; padding: 15px; margin-top: 20px;">
                        <p style="color: #666666; font-size: 14px; margin: 0 0 8px 0;">
                            <strong style="color: #d9534f;">Lưu ý:</strong> Liên kết này chỉ có hiệu lực trong vòng <strong>%d phút</strong>.
                        </p>
                        <p style="color: #666666; font-size: 14px; margin: 0;">
                            Nếu bạn không thực hiện yêu cầu này, vui lòng bỏ qua email và tuyệt đối không chia sẻ liên kết này cho bất kỳ ai.
                        </p>
                    </div>
                </div>
                
                <!-- Footer -->
                <div style="background-color: #f8f9fa; padding: 20px; text-align: center; border-top: 1px solid #dce2e6;">
                    <p style="color: #888888; font-size: 12px; margin: 0; line-height: 1.5;">
                        <strong>© 2026 Tập đoàn Bưu chính Viễn thông Việt Nam (VNPT).</strong><br>
                        Đây là email gửi tự động từ hệ thống, vui lòng không phản hồi (No-reply).<br>
                        Nếu cần hỗ trợ, vui lòng liên hệ Bộ phận IT Helpdesk.
                    </p>
                </div>
            </div>
            """.formatted(resetLink, expiryTime);
    }

}
