package com.cos.controller.member;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cos.action.Action;
import com.cos.dao.MemberDAO;
import com.cos.dto.MemberVO;
import com.cos.util.SHA256;
import com.cos.util.Script;

public class MemberLoginAction implements Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = "main.jsp";

		MemberVO member = new MemberVO();
		MemberDAO dao = new MemberDAO();

		String id = null;
		String password = null;
		String salt = null;

		if (request.getParameter("id") != null) {
			id = request.getParameter("id");
			salt = dao.select_salt(id);
			System.out.println("loginAction salt ::  "+salt);
			if(salt == null) Script.moving(response, "아이디가 존재하지 않습니다.");
		}
		if (request.getParameter("password") != null) {
			password = request.getParameter("password");
			password = SHA256.getEncrypt(password, salt);
			System.out.println("loginAction password :: "+ password);
		}

		member.setId(id);
		member.setPassword(password);

		int result = dao.login(member);
		if (result == 1) {
			HttpSession session = request.getSession();
			session.setAttribute("id", member.getId());
			Script.moving(response, "환영합니다. " + member.getId()+" 님", url);

		} else if (result == -1) {
			Script.moving(response, "데이터베이스 에러");
		} else if (result == 0) {
			Script.moving(response, "아이디 또는 비밀번호가 일치하지 않습니다.");
		}
	}
}
