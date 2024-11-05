package com.tech.whale.main.models;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MainDao {
	public UserInfoDto selectUserInfo(String userId);
	public Integer selectLikeNotiPostId(String postId);
	public Integer selectLikeNotiFeedId(String feedId);
	public Integer selectCommentsNotiPostId(String postId);
	public Integer selectCommentsNotiFeedId(String feedId);
	public String selectPostUserId(String postId);
	public String selectFeedUserId(String feedId);
	public Integer selectFollowNotiId(String userId, String targetId);
	public List<LikeNotiDto> getLikeNoti(String userId);
	public List<ComNotiDto> getCommentsNoti(String userId);
	public List<FollowNotiDto> getFollowNoti(String userId);
	public void insertPostLikeNoti(String postId, String userId);
	public void insertFeedLikeNoti(String feedId, String userId);
	public void insertPostCommentsNoti(String postId, String userId, String commentText);
	public void insertFeedCommentsNoti(String feedId, String userId, String commentText);
	public void insertFollowNoti(int followNotiType, String userId, String targetId);
	public void updateLikeNoti(String like_noti_id);
	public void updateCommentsNoti(String comments_noti_id);
	public void updateFollowNoti(String followNotiId);
	public void deleteLikeNoti(String like_noti_id);
	public void deleteCommentsNoti(String comments_noti_id);
	public void deleteFollowNoti(String userId, String targetId);
	public void deleteFollowNotiSetting(String userId);
}
