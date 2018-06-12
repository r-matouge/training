package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DaoFactory;
import dao.UserDao;
import domain.User;

@WebServlet("/UpdateUserServlet")
public class UpdateUserServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Integer id = Integer.parseInt(request.getParameter("user_id"));

		try {
			UserDao userDao = DaoFactory.createUserDao();
			List<User> userList = userDao.UserMore(id);

			request.setAttribute("userList", userList);
			request.getRequestDispatcher("view/UpdateUser.jsp").forward(request, response);
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//パラメーターの取得
		String name = request.getParameter("name");
		Integer id = Integer.parseInt(request.getParameter("user_id"));
		String login_id = request.getParameter("login_id");
		String login_pass=null;
		Integer group_id = Integer.parseInt(request.getParameter("group_id"));

		User user = new User();

		user.setName(name);
		user.setId(id);
		user.setLoginId(login_id);
		user.setGroup_id(group_id);

		if(""==request.getParameter("login_pass")) {
			try {
				UserDao userDao = DaoFactory.createUserDao();
				userDao.update_notpass(user);

				request.setAttribute("userId", id);
				request.getRequestDispatcher("view/UpdateUserDone.jsp").forward(request, response);
			} catch(SQLException e) {
				UserDao userDao = DaoFactory.createUserDao();
				List<User> userList = null;
				try {
					userList = userDao.UserMore(id);
				} catch (Exception e1) {
					// TODO 自動生成された catch ブロック
					e1.printStackTrace();
				}

				request.setAttribute("userList", userList);
				request.setAttribute("Caution", "同じIDは追加できません");
				request.getRequestDispatcher("view/UpdateUser.jsp").forward(request, response);
			}catch (Exception e) {
				throw new ServletException(e);
			}
		}else {
			login_pass = request.getParameter("login_pass");
			user.setLoginPass(login_pass);
			try {
				UserDao userDao = DaoFactory.createUserDao();
				userDao.update(user);

				request.setAttribute("userId", id);
				request.getRequestDispatcher("view/UpdateUserDone.jsp").forward(request, response);
			} catch(SQLException e) {
				UserDao userDao = DaoFactory.createUserDao();
				List<User> userList = null;
				try {
					userList = userDao.UserMore(id);
				} catch (Exception e1) {
					// TODO 自動生成された catch ブロック
					e1.printStackTrace();
				}

				request.setAttribute("userList", userList);
				request.setAttribute("Caution", "同じIDは追加できません");
				request.getRequestDispatcher("view/UpdateUser.jsp").forward(request, response);
			}catch (Exception e) {
				throw new ServletException(e);
			}
		}

	}

}