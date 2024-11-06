package com.tech.whale.message.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tech.whale.message.dao.MessageDao;
import com.tech.whale.message.dto.MessageRoomDto;
import com.tech.whale.message.service.MsgHomeService;
import com.tech.whale.message.service.MsgRoomService;
import com.tech.whale.message.service.MsgSerchService;
import com.tech.whale.message.service.MsgServiceInter;

@Controller
@RequestMapping("message")
public class MessageController {
	MsgServiceInter msgService;
	@Autowired
	private MessageDao iDao;

	@RequestMapping("/home")
	public String settingHome(HttpServletRequest request, Model model) {
		System.out.println(">>[JAVA] MessageController : settingHome");
		model.addAttribute("request", request);
		msgService = new MsgHomeService(iDao);
		msgService.execute(model);
		return "message/messageHome";
	}

	@RequestMapping("/messageSearch")
	public String messageSearch(HttpServletRequest request, Model model) {
		System.out.println(">>[JAVA] MessageController : messageSearch");
		// Gson 객체 생성
	    Gson gson = new Gson();
	    // JSON 문자열을 List<MessageRoomDto>로 변환
	    List<MessageRoomDto> msgRoomList = 
	    		gson.fromJson(request.getParameter("list"),
	    				new TypeToken<List<MessageRoomDto>>() {}.getType());
		model.addAttribute("list", msgRoomList);
		return "message/messageSearch";
	}

	@RequestMapping("/search")
	public String search(HttpServletRequest request, Model model) {
		System.out.println(">>[JAVA] MessageController : search");
		model.addAttribute("request", request);
		MsgSerchService msgService = new MsgSerchService(iDao);
		msgService.execute(model);

		return msgService.getReturnURL();
	}

	@RequestMapping("/messageRoom")
	public String messageRoom(HttpServletRequest request, Model model) {
//		String tabId = request.getParameter("tabId");
		System.out.println(">>[JAVA] MessageController : messageRoom");
		model.addAttribute("request", request);
		msgService = new MsgRoomService(iDao);
		msgService.execute(model);
		return "message/messageRoom";
	}

}
