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

@Service
public class MsgRoomService implements MsgServiceInter {
	private MessageDao messageDao;

	public MsgRoomService(MessageDao messageDao) {
		this.messageDao = messageDao;
	}

	@Override
	public void execute(Model model) {
		System.out.println(">>>MsgRoomService");

		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
	    HttpSession session = request.getSession();// HttpSession 객체	    
	    String userId = (String) session.getAttribute("user_id");
	    String ids="";
		
		// 이미 생성된 방이 있는지 확인
		String strRoomId = request.getParameter("roomId");
		Integer roomID=0;
		boolean existRoom=true;//방이 이미 존재하는지 여부
		if(strRoomId !=null) {
			System.out.println("그냥 메시지방 참가");
			roomID=Integer.parseInt(strRoomId);
			System.out.println("userList확인");
			String[] userList = request.getParameterValues("userList");
			ids = String.join(", ", userList);
		}else {
		    //선택된 아이디 값에 유저 Id도 추가
		    String[] selectedIds = request.getParameterValues("selectedIds");
	        ArrayList<String> idList = new ArrayList<>(Arrays.asList(selectedIds));
	        idList.add(userId);
		    
			ids = String.join(", ", idList);
			
			try {
				roomID=messageDao.roomExist(idList, idList.size());
				if (roomID == null) {
					existRoom=false;
					System.out.println("방이 존재하지 않습니다. > 신규 방 생성");
					MessageRoomDto msgRoomDto=new MessageRoomDto();
			        messageDao.createRoomID(msgRoomDto);
			        roomID=msgRoomDto.getMessage_room_id();
			        //참가자 추가
			        messageDao.addRoomUser(roomID,idList);
			        
				} else {
					//기존 대화내역 불러오기 추가되어야함
					System.out.println("결과 방 ID: " + roomID);
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("SQL 실행 중 에러 발생: " + e.getMessage());
			}
		}
		ArrayList<MessageDto> roomMsgs=new ArrayList<>();
		if(existRoom) {//이미 방이 있다면, 기존 메시지를 뿌려줘야함.
			roomMsgs = messageDao.getMessagesByRoom(roomID);
			System.out.println("기존메시지들 전달완료");
		}
		System.out.println("생성된 방번호 : " + roomID);
		System.out.println(">>>>>온거 확인 : " + ids);
		//참가자들 유저정보 및 url도 전달해주기. map으로 user_ID 키값으로 user닉네임과 프사url정도만 객체로 담아주면될듯
		
		model.addAttribute("roomMsgs",roomMsgs);
		model.addAttribute("roomID",roomID);
		model.addAttribute("ids", ids);

	}
}
