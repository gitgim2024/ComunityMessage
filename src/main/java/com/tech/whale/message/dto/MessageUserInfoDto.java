package com.tech.whale.message.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class MessageUserInfoDto {
	private String user_id;
	private String user_nickname;
	private String user_image_url;
}
