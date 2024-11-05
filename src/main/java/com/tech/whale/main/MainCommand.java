package com.tech.whale.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface MainCommand {
	public void execute(HttpServletRequest req, HttpSession session);
}
