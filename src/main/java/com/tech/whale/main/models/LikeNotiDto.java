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
public class LikeNotiDto {
	private int like_noti_id;
	private int like_noti_check;
	private Date like_noti_date;
	private String like_noti_type;
	private String target_user_id;
	private int post_id;
	private int community_id;
	private int feed_id;
}
