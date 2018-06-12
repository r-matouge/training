package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DaoFactory;
import dao.UserDao;
import domain.User;

@WebServlet("/UserMoreServlet")
public class UserMoreServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

    	// パラメータの取得
		Integer id = Integer.parseInt(request.getParameter("id"));

		// データの追加
//		Event eventMore = new Event();
//		eventMore.setId(id);

		try {
			UserDao userDao = DaoFactory.createUserDao();
			List<User> userList = userDao.UserMore(id);

			request.setAttribute("userList", userList);
			request.getRequestDispatcher("view/UserMore.jsp").forward(request, response);
		}
		catch (Exception e) {
			throw new ServletException(e);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Integer id = Integer.parseInt(request.getParameter("id"));
		try {

			UserDao userDao = DaoFactory.createUserDao();
			List<User> userList = userDao.UserMore(id);

			request.setAttribute("userList", userList);
			request.getRequestDispatcher("view/UserMore.jsp").forward(request, response);
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
}