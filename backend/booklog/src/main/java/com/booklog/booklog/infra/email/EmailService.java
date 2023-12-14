package com.booklog.booklog.infra.email;

import com.booklog.booklog.common.code.ErrorCode;
import com.booklog.booklog.common.util.RedisUtil;
import com.booklog.booklog.exception.EmailException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender emailSender;
    private final RedisUtil redisUtil;
    public static String emailVerificationCode;// = createCode();;

    @Value("${spring.mail.username}")
    private String from;

    // 회원가입 이메일 인증
    private MimeMessage createMessage(String to) throws Exception {
        emailVerificationCode = createCode();
        MimeMessage message = emailSender.createMimeMessage();

        message.addRecipients(Message.RecipientType.TO, to);
        message.setSubject("[Booklog] 인증번호를 확인해주세요.");

        String content = "";
        content += "<div style='margin:100px;'>";
        content += "<h1>안녕하세요, Booklog입니다.</h1>";
        content += "<br>";
        content += "<p>아래 코드를 인증란에 입력해주세요<p>";
        content += "<br>";
        content += "<br>";
        content += "<div style='font-size:130%;'>";
        content += "CODE : <strong>";
        content += emailVerificationCode + "</strong><br/> ";
        content += "</div>";
        message.setText(content, "utf-8", "html");
        message.setFrom(new InternetAddress(from, "Booklog"));

        return message;
    }

    public void sendCodeToEmail(String to) throws Exception {
        MimeMessage message = createMessage(to);

        try {
            redisUtil.setDataExpire(to, emailVerificationCode, 180);
            emailSender.send(message);
        } catch (MailException e) {
            e.printStackTrace();
            throw new EmailException(ErrorCode.EMAIL_SEND_ERROR);
        }
    }

    // 코드 확인
    public Boolean verifyEmailCode(EmailCodeDto dto) {
        String storedCode = redisUtil.getData(dto.getEmail());

        if (storedCode == null) {  // 인증 코드 만료
            return false;
        }
        return storedCode.equals(dto.getCode());
    }

    // 인증 코드 생성
    public static String createCode() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 6; i++) {
            key.append((rnd.nextInt(10)));
        }
        return key.toString();
    }
}
