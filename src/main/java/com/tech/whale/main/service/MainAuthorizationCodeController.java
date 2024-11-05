package com.tech.whale.main.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.tech.whale.main.MainCommand;
import com.tech.whale.main.models.MainAuthorizationCode;

@Service
public class MainAuthorizationCodeController implements MainCommand {
	private final MainAuthorizationCode mainAuthorizationCode;
	
	public MainAuthorizationCodeController(MainAuthorizationCode mainAuthorizationCode) {
		this.mainAuthorizationCode = mainAuthorizationCode;
	}
	
	@Override
	public void execute(HttpServletRequest req, HttpSession session) {
		mainAuthorizationCode.authorizationCode_Async(req.getParameter("code"),session);
	}
}
