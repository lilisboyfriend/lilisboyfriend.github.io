package servlet;

import java.io.IOException;
import java.sql.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbDaoCon.DbDao;
@WebServlet(name="login",urlPatterns={"/login"})
public class login extends HttpServlet {

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String errMsg = "";
		RequestDispatcher rd;
		String username = request.getParameter("username");
		String pass = request.getParameter("pass");
		try {
			DbDao dd = new DbDao("com.mysql.jdbc.Driver","jdbc:mysql://localhost:3306/platform","root","123456");
			ResultSet rs = dd.query("select pwd from logintest where name = ?", username);
			if(rs.next()) {
				if(rs.getString("pwd").equals(pass)) {
					HttpSession session = request.getSession(true);
					session.setAttribute("name", username);
					rd = request.getRequestDispatcher("/shop.jsp");
				}
			}
			else {
				errMsg += "�����û������벻���ϣ�����������";
			}
		} catch (Exception e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		if (errMsg != null && errMsg.equals("")) {
			rd = request.getRequestDispatcher("/index.jsp");
			request.setAttribute("err", errMsg);
		}
	}
	

}
