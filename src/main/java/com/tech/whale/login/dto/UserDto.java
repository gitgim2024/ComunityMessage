package com.tech.whale.login.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDto {
    private String user_id;
    private String user_password;
    private String user_email;
    private String user_nickname;  // 닉네임 필드 추가
    private String user_spotify_id;
}
