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
public class MessageImageDto {
	private int	img_id;
	private String img_org_name;
	private String img_chg_name;
	private String img_savepath;
	private String img_url;
	private String message_id;
}
