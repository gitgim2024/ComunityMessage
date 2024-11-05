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
public class ComNotiDto {
	private int comments_noti_id;
	private int comments_noti_check;
	private Date comments_noti_date;
	private String target_user_id;
	private int post_id;
	private int community_id;
	private int feed_id;
	private String post_comments_text;
	private String feed_comments_text;
}
