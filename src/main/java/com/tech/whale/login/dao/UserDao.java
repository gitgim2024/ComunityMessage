package com.tech.whale.login.dao;

import com.tech.whale.login.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
    public void insertUserInfo(UserDto user);
    public void insertUserNotification(String user_id);
    public void insertPageAccessSetting(String user_id);
    public void insertStartPageSetting(String user_id);
    public void insertBlock(String user_id);
    public void insertUserSetting(String user_id);
    public void insertFollow(String user_id);
    public Integer selectFollowId(String user_id);
    public void insertProfile(String user_id, Integer followId);
    public Integer checkAccessId(String user_id);
    public String getPasswordByUsername(String user_id);
    public void saveUser(String user_id, String password, String email);
    public Integer existsByEmail(String email);
    public void saveResetToken(String user_id, String token);
    public Integer isValidToken(String token);
    public void updatePasswordByToken(String hashedPassword, String token);
    public String getUserIdByEmail(String email);
}
