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
import domain.Event;
import domain.User;

/**
 * Servlet implementation class ListUserServlet
 */
@WebServlet("/ListUserServlet")
public class ListUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Integer page=0;
		if(null ==request.getParameter("page")){
			page=1;
		}else {
			page=Integer.parseInt(request.getParameter("page"));
		}
		Event event=new Event();
		event.setPage(page);
		page=5*(page-1)+1;

		try {
			UserDao userDao = DaoFactory.createUserDao();
			List<User> userlength = userDao.findAll();
			List<User> userList = userDao.findAllPage(page);
			int length=(int)(userlength.size()/5);

			if (userlength.size()%5!=0) {
				length=length+1;
			}
			event.setLength(length);

			request.setAttribute("event", event);
			request.setAttribute("userList", userList);
			request.getRequestDispatcher("view/UserList.jsp").forward(request, response);
		} catch (Exception e) {
			throw new ServletException(e);
		}

	}

}
