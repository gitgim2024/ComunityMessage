package com.tech.whale.message.controllers;

import java.lang.reflect.Array;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("message")
public class MessageController {
	@RequestMapping("/home")
	public String settingHome(HttpServletRequest request, HttpSession session, Model model) {
		System.out.println(">>[JAVA] MessageController : settingHome");
		String[] test = new String[] { "A", "B", "C", "D", "E", "F", "G" };
		model.addAttribute("list", test);
		return "message/messageHome";
	}

	@RequestMapping("/messageSearch")
	public String messageSearch(HttpServletRequest request, HttpSession session, Model model) {
		System.out.println(">>[JAVA] MessageController : messageSearch");
		String[] test = new String[] { "1", "2" };
		model.addAttribute("list", test);
		return "message/messageSearch";
	}

	@RequestMapping("/search")
	public String search(HttpServletRequest request, HttpSession session, Model model) {
		String tabId = request.getParameter("tabId");
		System.out.println(">>[JAVA] MessageController : search : " + tabId);

		if (tabId.equals("msg")) {
			String[] test = new String[] { "가", "나" };
			model.addAttribute("list", test);
			return "message/messageTable";
		} else {
			String[] test = new String[] { "user1", "user2", "user3", "user4", "user5", "user6" };
			model.addAttribute("list", test);
			return "message/userTable";
		}
	}

	@RequestMapping("/messageRoom")
	public String messageRoom(HttpServletRequest request, HttpSession session, Model model) {

		String tabId = request.getParameter("tabId");
		System.out.println(">>[JAVA] MessageController : messageRoom : ");

		String[] selectedIds = request.getParameterValues("selectedIds");

		String ids = String.join(", ", selectedIds);

		System.out.println(">>>>>온거 확인 : " + ids);

		model.addAttribute("ids", ids);
		return "message/messageRoom";
	}

}
