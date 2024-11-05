package com.tech.whale.main.service;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.tech.whale.main.models.ComNotiDto;
import com.tech.whale.main.models.FollowNotiDto;
import com.tech.whale.main.models.LikeNotiDto;
import com.tech.whale.main.models.MainDao;
import com.tech.whale.main.models.UserInfoDto;

@Service
public class MainService {
	private MainDao mainDao;
	
	public MainService(MainDao mainDao) {
		this.mainDao = mainDao;
	}
	
	public String[] userInfoMain(HttpSession session) {
		UserInfoDto userInfo = mainDao.selectUserInfo((String) session.getAttribute("user_id"));
		return new String[] {userInfo.getUser_nickname(),userInfo.getUser_image_url()};
	}
	
	// [ 좋아요 알림 서비스 ]
	public List<LikeNotiDto> getLikeNotiMainService(HttpSession session) {
		List<LikeNotiDto> likeNotis = mainDao.getLikeNoti((String) session.getAttribute("user_id"));
		return likeNotis;
	}
	
	// [ 댓글 알림 서비스 ]
	public List<ComNotiDto> getCommentsNotiMainService(HttpSession session) {
		List<ComNotiDto> commentsNotis = mainDao.getCommentsNoti((String) session.getAttribute("user_id"));
		return commentsNotis;
	}
	// [ 팔로우 알림 서비스 ]
	public List<FollowNotiDto> getFollowNotiMainService(HttpSession session) {
		List<FollowNotiDto> followNotis = mainDao.getFollowNoti((String) session.getAttribute("user_id"));
		return followNotis;
	}
	
	// [ 좋아요 알림 읽음 처리 ]
	public void updateLikeNotiMainService(String likeNotiId) {
		mainDao.updateLikeNoti(likeNotiId);
	}
	
	// [ 댓글 알림 읽음 처리 ]
	public void updateCommentsNotiMainService(String commentsNotiId) {
		mainDao.updateCommentsNoti(commentsNotiId);
	}
	
	// [ 팔로우 알림 읽음 처리 ]
	public void updateFollowNotiMainService(String followNotiId) {
		mainDao.updateFollowNoti(followNotiId);
	}
	
	// [ 좋아요 알림 삭제 처리 ]
	public void deleteLikeNotiMainService(String likeNotiId) {
		mainDao.deleteLikeNoti(likeNotiId);
	}
	
	// [ 댓글 알림 삭제 처리 ]
	public void deleteCommentsNotiMainService(String commentsNotiId) {
		mainDao.deleteCommentsNoti(commentsNotiId);
	}
	
	// [ 팔로우 알림 삭제 처리 ]
	public void deleteFollowNotiMainService(String userId, String targetId) {
		mainDao.deleteFollowNoti(userId, targetId);
	}
}
