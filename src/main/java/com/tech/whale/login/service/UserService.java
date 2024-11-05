package com.tech.whale.login.service;

import com.tech.whale.login.dto.UserDto;
import com.tech.whale.login.dao.UserDao;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private JavaMailSender mailSender; // JavaMailSender 의존성 주입

    // 사용자 인증 메서드
    public boolean authenticate(String username, String password) {
        String storedPassword = userDao.getPasswordByUsername(username);
        return passwordEncoder.matches(password, storedPassword);
    }
    
    public void checkAccessIdLogin(String username, HttpSession session) {
    	Integer accessId = userDao.checkAccessId(username);
    	session.setAttribute("access_id", accessId.toString());
    }

    // 사용자 등록 메서드 (User 객체 기반)
    public boolean registerUser(String username, String password, String email, String nickname, String spotifyId) {
        try {
            // 비밀번호 암호화
            String encodedPassword = passwordEncoder.encode(password);

            // UserDto 객체 생성
            UserDto userDto = new UserDto(username, encodedPassword, email, nickname, spotifyId);

            // DB에 유저 정보 저장
            userDao.insertUserInfo(userDto);

            // 환경 설정 기본값 세팅
            userDao.insertUserNotification(username);
            userDao.insertPageAccessSetting(username);
            userDao.insertStartPageSetting(username);
            userDao.insertBlock(username);
            userDao.insertUserSetting(username);
            userDao.insertFollow(username);
            Integer followId = userDao.selectFollowId(username);
            userDao.insertProfile(username, followId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // 기존 사용자 등록 메서드 (개별 필드 기반)
    public void registerUser(String username, String password, String email) {
        String encodedPassword = passwordEncoder.encode(password);
        userDao.saveUser(password, encodedPassword, email);
    }

    // 이메일 중복 확인
    public boolean isEmailRegistered(String email) {
        Integer count = userDao.existsByEmail(email);
        return count != null && count > 0;
    }

    // 비밀번호 재설정 이메일 전송
    public void sendResetPasswordEmail(String email) {
        String token = generateRandomToken();
        String user_id = userDao.getUserIdByEmail(email);  // 이메일로 유저 아이디 가져오기
        String resetLink = "http://localhost:9002/whale/reset-password?token=" + token;
        userDao.saveResetToken(user_id,token);
        // 이메일 전송 로직
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Whale 계정 비밀번호 재설정");
        message.setText("안녕하세요, " + user_id + "님,\n\n"
                + "비밀번호를 재설정하려면 아래 링크를 클릭하세요:\n" + resetLink
                + "\n\n만약 이 요청을 본인이 한 것이 아니라면, 이 메일을 무시해 주세요."
                + "\n\n감사합니다.\nWhale 팀");
        message.setFrom("Music Whale Support <" + System.getenv("EMAIL_USER") + ">");  // 보내는 사람 설정

        try {
            mailSender.send(message);  // 이메일 전송
            System.out.println("비밀번호 재설정 이메일이 성공적으로 전송되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("이메일 전송에 실패했습니다.");
        }
    }

    // 비밀번호 재설정 토큰 검증
    public boolean verifyResetToken(String token) {
    	Integer count = userDao.isValidToken(token);
        return count != null && count > 0;
    }

    // 비밀번호 업데이트
    public void updatePassword(String token, String newPassword) {
        String hashedPassword = passwordEncoder.encode(newPassword);
        userDao.updatePasswordByToken(hashedPassword, token);
    }

    // 랜덤 상태 값 생성
    public String generateRandomState() {
        return "randomStateString";  // 상태 값 생성 로직
    }

    // 랜덤 토큰 생성
    private String generateRandomToken() {
        return UUID.randomUUID().toString();  // 토큰 생성 로직
    }
}
