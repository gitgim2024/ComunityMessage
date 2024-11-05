package com.tech.whale.message.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.tech.whale.message.dao.MessageDao;
import com.tech.whale.message.dto.MessageDto;
import com.tech.whale.message.dto.MessageRoomDto;
import com.tech.whale.message.util.MsgListToTable;
import com.tech.whale.message.util.MsgStringUtil;

@Service
public class MsgHomeService implements MsgServiceInter {
	private MessageDao messageDao;

	public MsgHomeService(MessageDao messageDao) {
		this.messageDao = messageDao;
	}

	@Override
	public void execute(Model model) {
		System.out.println(">>>MsgRoomService");

		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");

	    HttpSession session = request.getSession();// HttpSession 객체	    
	    //현재 세션 유저 id
	    String userId = (String) session.getAttribute("user_id");
	    
	    
		//속해있는 방 목록 불러오기.
	    ArrayList<Integer> roomList = messageDao.getMyMsgRoom(userId);
	    
	    System.out.println("방목록 : "+roomList);
	    MsgListToTable msgList=new MsgListToTable();
	    ArrayList<MessageRoomDto> list=msgList.createMsgTable(roomList, messageDao, userId);
	    model.addAttribute("list",list);

	}
}
