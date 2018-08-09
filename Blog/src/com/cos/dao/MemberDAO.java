package com.cos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.cos.dto.MemberVO;
import com.cos.util.DBManager;

public class MemberDAO {
	private PreparedStatement pstmt;
	private ResultSet rs;

	//insert
	public int insert(MemberVO member) {
		String SQL = " INSERT INTO member (id,password,username,roadFullAddr,engAddr,email,salt,emailcheck) VALUES(?,?,?,?,?,?,?,false)";
		Connection conn = DBManager.getConnection();
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, member.getId());
			pstmt.setString(2, member.getPassword());
			pstmt.setString(3, member.getUsername());
			pstmt.setString(4, member.getRoadFullAddr());
			pstmt.setString(5, member.getEngAddr());
			pstmt.setString(6, member.getEmail());
			pstmt.setString(7, member.getSalt());
			pstmt.executeUpdate();
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("fail");
		}finally {
			DBManager.close(conn, pstmt);
		}
		return -1;
	}
	
	//============================================================이메일 인증
	public int select_emailcheck(String id) {
		String SQL = "SELECT emailcheck FROM member WHERE id=?";
		Connection conn = DBManager.getConnection();
		
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				boolean emailcheck = rs.getBoolean("emailcheck");
				if(emailcheck == true) {
					return 1;
				}else {
					return 2;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(conn, pstmt, rs);
		}
		return -1;
	}
	public String select_email(String id) {
		String SQL = "SELECT email FROM member WHERE id=?";
		Connection conn = DBManager.getConnection();
		
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				String email = rs.getString("email");
				return email;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(conn, pstmt, rs);
		}
		return null;
	}
	public String select_salt(String id) {
		String SQL = "SELECT salt FROM member WHERE id=?";
		Connection conn = DBManager.getConnection();
		
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				String salt = rs.getString("salt");
				return salt;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(conn, pstmt, rs);
		}
		return null;
	}
	public int update_emailcheck(String id) {
		String SQL ="UPDATE member SET emailcheck = true WHERE id = ?";
		Connection conn = DBManager.getConnection();
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, id);
			pstmt.executeUpdate();
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(conn, pstmt);
		}
		return -1;
	}
	
	// 로그인
	public int login(MemberVO member) {
		System.out.println("====== login page Start ============");
		String SQL = "SELECT * FROM member WHERE id=? and password =?";
		Connection conn = DBManager.getConnection();
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, member.getId());
			pstmt.setString(2, member.getPassword());
			rs = pstmt.executeQuery();
			if(rs.next()) {
				System.out.println("DAO id ::"+rs.getString("id"));
				System.out.println("DAO pw ::"+rs.getString("password"));
				return 1;
			}else {
				return 0;
			}
		} catch (Exception e) {
			System.out.println("DB error");
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		
		return -1;
	}
}
