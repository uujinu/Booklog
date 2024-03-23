package com.booklog.booklog.infra.email;

import com.booklog.booklog.common.code.ErrorCode;
import com.booklog.booklog.common.util.RedisUtil;
import com.booklog.booklog.exception.EmailException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender emailSender;
    private final RedisUtil redisUtil;
    public static String emailVerificationCode;
    public static String randomPassword;

    @Value("${spring.mail.username}")
    private String from;

    // 코드 인증 이메일 본문
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

    // 임시 비밀번호 발급 이메일 본문
    private MimeMessage createPWMessage(String to) throws Exception {
        randomPassword = getRandomPassword();
        MimeMessage message = emailSender.createMimeMessage();

        message.addRecipients(Message.RecipientType.TO, to);
        message.setSubject("[Booklog] 임시 비밀번호를 확인해주세요.");

        String content = "";
        content += "<div style='margin:100px;'>";
        content += "<h1>안녕하세요, Booklog입니다.</h1>";
        content += "<br>";
        content += "<p>아래 임시 비밀번호로 로그인 후 비밀번호를 변경해주세요.<p>";
        content += "<br>";
        content += "<br>";
        content += "<div style='font-size:130%;'>";
        content += "CODE : <strong>";
        content += randomPassword + "</strong><br/> ";
        content += "</div>";
        message.setText(content, "utf-8", "html");
        message.setFrom(new InternetAddress(from, "Booklog"));

        return message;
    }

    public String sendCodeToEmail(String to) {
        try {
            MimeMessage message = createMessage(to);
            redisUtil.setDataExpire(to, emailVerificationCode, 180);
            emailSender.send(message);
            return emailVerificationCode;
        } catch (Exception e) {
            e.printStackTrace();
            throw new EmailException(ErrorCode.EMAIL_SEND_ERROR);
        }
    }

    public String sendRandomPasswordToEmail(String to) {
        try {
            MimeMessage message = createPWMessage(to);
            emailSender.send(message);
            return randomPassword;
        } catch (Exception e) {
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

    // 임시 비밀번호 생성
    public static String createPassword(int size) {
        char[] charSet = new char[] {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
                'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
                'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                '!', '@', '#', '$', '%', '^', '&'
        };

        StringBuffer sb = new StringBuffer();
        SecureRandom sr = new SecureRandom();
        sr.setSeed(new Date().getTime());

        int idx = 0;
        int len = charSet.length;
        for (int i = 0; i < size; i++) {
            idx = sr.nextInt(len);
            sb.append(charSet[idx]);
        }
        return sb.toString();
    }

    // 임시 비밀번호 조건 체크
    public static String getRandomPassword() {
        boolean idx = false;
        String pw = "";

        while(!idx) {
            pw = createPassword(10);
            idx = pw.matches("(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}");
        }
        return pw;
    }
}
