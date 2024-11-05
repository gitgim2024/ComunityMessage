package com.tech.whale.main.models;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class FollowNotiDto {
	private int follow_noti_id;
	private int follow_noti_check;
	private Date follow_noti_date;
	private int follow_noti;
	private String user_id;
	private String target_user_id;
}
