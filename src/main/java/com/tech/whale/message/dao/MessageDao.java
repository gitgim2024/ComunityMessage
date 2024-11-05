package com.tech.whale.message.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.tech.whale.message.dto.MessageDto;
import com.tech.whale.message.dto.MessageImageDto;
import com.tech.whale.message.dto.MessageRoomDto;
import com.tech.whale.message.dto.MessageRoomUserDto;
import com.tech.whale.message.dto.MessageUserInfoDto;

@Mapper
public interface MessageDao {
	public void createRoomID(MessageRoomDto dto);
	public Integer roomExist(ArrayList<String> Ids,int Count);
	public void addRoomUser(int roomID,ArrayList<String> Ids);
	public void addMessage(MessageDto msgDto);
	public void addMsgImg(MessageImageDto imgDto);
	public void updateMsgImg(MessageImageDto imgDto);
	public void updateImgMsgID(int message_id, String[] imagesArray);
	public ArrayList<MessageImageDto> getImages(int message_id);
	public MessageDto getMessageById(int message_id);
	
	public ArrayList<MessageDto> getMessagesByRoom(int message_room_id);
	public ArrayList<Integer> getMyMsgRoom(String user_id);
	public ArrayList<String> getRoomUsers(int message_room_id);
	public MessageDto getRoomLastMsg(int message_room_id);
	public int unreadMsgCnt(int message_room_id,String user_id);
	
	public void deleteRoom(int message_room_id);
	
	//검색관련
	//메시지를 기준으로 메시지를 입력한 유저나 메시지내용이 포함된 항목을 조회
	public ArrayList<MessageDto> getMsgFilter(ArrayList<Integer> roomList, String searchInput);
	public ArrayList<String> getFollowFilter(String user_id);
	public ArrayList<MessageUserInfoDto> getFollowUsers(List<String> followList);
	public ArrayList<MessageUserInfoDto> getUsersById(String searchInput);
	
	
}
