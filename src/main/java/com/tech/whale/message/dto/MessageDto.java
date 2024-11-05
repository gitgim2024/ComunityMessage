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
public class MessageDto {
	private int	message_id;
	private Timestamp message_create_date;
	private String message_context;
	private int message_room_id;
	private String user_id;
}
