package com.tech.whale.message.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.tech.whale.message.dao.MessageDao;
import com.tech.whale.message.dto.MessageDto;
import com.tech.whale.message.dto.MessageRoomDto;
import com.tech.whale.message.dto.MessageUserInfoDto;
import com.tech.whale.message.util.MsgListToTable;
import com.tech.whale.message.util.MsgStringUtil;

@Service
public class MsgSerchService implements MsgServiceInter {
	private MessageDao messageDao;
	private String returnURL;

	public String getReturnURL() {
		return returnURL;
	}

	public MsgSerchService(MessageDao messageDao) {
		this.messageDao = messageDao;
	}

	@Override
	public void execute(Model model) {
		System.out.println(">>>MsgSerchService");

		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");

	    HttpSession session = request.getSession();// HttpSession 객체	    
	    //현재 세션 유저 id
	    String userId = (String) session.getAttribute("user_id");
	    
	  //속해있는 방 목록 불러오기.
	    ArrayList<Integer> roomList = messageDao.getMyMsgRoom(userId);
	    
	    String tabId = request.getParameter("tabId");
	    String searchInput = request.getParameter("searchInput");
	    if(searchInput==null) {
	    	searchInput="";
	    }
	    String searchWord=searchInput;//filteredList조회할 때 searchInput그대로 쓰면 안되어서 다시 변수 생성
	    System.out.println("방목록"+roomList);
	    System.out.println(">>>>>>>>>>검색어 : "+searchWord);
	    System.out.println("탭 : "+tabId);
	    
	    
	    if (tabId.equals("msg")) {
	    	if(roomList.size()!=0) {
		    	System.out.println("msg진입");
		    	
			    ArrayList<MessageRoomDto> msgRoomList = new ArrayList<>();
			    for (Integer room : roomList) {
			    	//여기만 기본조회랑 다름
			    	ArrayList<MessageDto> messages = messageDao.getMsgFilter(roomList,searchWord);
				    if(messages.size()==0) {
				    	System.out.println(">>>>>>>아무것도 입력안된 방삭제");
				    	messageDao.deleteRoom(room);//메시지가 없는방은 삭제
				    	continue;
				    }//데이터가 있다면 아무동작도 하지 않음.
			    	MessageRoomDto roomDto = new MessageRoomDto();
			    	//방에 속해 있는 유저목록
			    	ArrayList<String> userList = messageDao.getRoomUsers(room);
			    	roomDto.setUserList(userList);
			 
			    	//마지막 메시지
			    	MessageDto msgDto=messageDao.getRoomLastMsg(room);
			    	String lastMsgContext = msgDto.getMessage_context();
			    	if(lastMsgContext==null) {//이미지만 보낸 메시지는 null값이 될 수 있음
			    		lastMsgContext="사진을 보냈습니다.";
			    	}
			    	System.out.println("마지막 메시지 : "+lastMsgContext);
			    	roomDto.setLastmsg_context(lastMsgContext);
			    	MsgStringUtil stringutil=new MsgStringUtil();
			    	String lastmsgTime= stringutil.formatMessageTime(msgDto.getMessage_create_date());
			    	roomDto.setLastmsg_date(lastmsgTime);
			    	//안 읽은 메시지 목록
			    	int unreadCnt = messageDao.unreadMsgCnt(room,userId);
			    	roomDto.setUnread_msg(unreadCnt);
			    	System.out.println("안 읽은 메시지 목록 가져오기 성공");
			    	msgRoomList.add(roomDto);
				}
		    	
		    	System.out.println("msg1조회성공");
		    	model.addAttribute("list", msgRoomList);
	    	}else {
	    		MsgListToTable msgListTotable=new MsgListToTable();
	    		ArrayList<MessageRoomDto> list=msgListTotable.createMsgTable(roomList, messageDao, userId);
	    		model.addAttribute("list", list);
	    	}
	    	
			returnURL="message/messageTable";
		} else if(tabId.equals("friend")){
			System.out.println("friend진입");
			ArrayList<String> followList = messageDao.getFollowFilter(userId);
			System.out.println("friend1 조회성공");
			//검색값을 포함하는 목록만 남김
			ArrayList<MessageUserInfoDto> userList = new ArrayList<>();
			if(searchWord!="") {
				List<String> filteredList = followList.stream()
					    .filter(id -> id.contains(searchWord))
					    .collect(Collectors.toList());
				System.out.println("friend 필터 성공");
				userList=messageDao.getFollowUsers(filteredList);
			}else {
				userList=messageDao.getFollowUsers(followList);
			}
			System.out.println("friend2 조회성공");
			model.addAttribute("list", userList);
			returnURL="message/userTable";
		} else{
			System.out.println("user진입");
			ArrayList<MessageUserInfoDto> userList;
			if(searchWord!="") {
				System.out.println("조회할거 : "+searchWord);
				userList=messageDao.getUsersById(searchWord);
				System.out.println("user1 조회성공");
			}else {
				System.out.println("비어있어서 빈 list 송출");
				userList=new ArrayList<>();
			}
			model.addAttribute("list", userList);
			returnURL="message/userTable";
			
		}

	}
}
