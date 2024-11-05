package com.tech.whale.message.dto;

import java.sql.Timestamp;
import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class MessageRoomDto {
	private int message_room_id;
	
	//db와 상관없이 편의를 위해 추가함.
	private ArrayList<String> userList;//유저목록
	//마지막 메시지
	private String lastmsg_date;
	private String lastmsg_context;
	//안읽은 메시지 수
	private int unread_msg;
}
