package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DaoFactory;
import dao.UserDao;
import domain.User;

@WebServlet("/AddUserServlet")
public class AddUserServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("view/AddUser.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

//		パラメーターの取得
		String name = request.getParameter("name");
		System.out.println(name);
		String login_id = request.getParameter("login_id");

		System.out.println(login_id);
		String login_pass = request.getParameter("login_pass");
		Integer group_id = Integer.parseInt(request.getParameter("group_id"));

		//データの追加
		User user = new User();
		user.setName(name);
		user.setLoginId(login_id);
		user.setLoginPass(login_pass);
		user.setGroup_id(group_id);

		try {
			UserDao userDao = DaoFactory.createUserDao();
			userDao.insert(user);

			request.getRequestDispatcher("view/AddUserDone.jsp").forward(request, response);
		} catch(SQLException e) {
			request.setAttribute("Caution", "同じIDは追加できません");
			request.getRequestDispatcher("view/AddUser.jsp").forward(request, response);

		}catch (Exception e) {
			throw new ServletException(e);
		}
	}

}
