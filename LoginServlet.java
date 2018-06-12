package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DaoFactory;
import dao.EventDao;
import domain.Event;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("view/login.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// パラメータの取得
		String loginId = request.getParameter("loginId");
		String loginPass = request.getParameter("loginPass");

		try {
			EventDao EventDao = DaoFactory.createEventDao();
			Event event = EventDao.findByLoginIdAndLoginPass(loginId, loginPass);

			if (event != null) {
				request.getSession().setAttribute("loginName", event.getName());
				request.getSession().setAttribute("Id", event.getId());
				request.getSession().setAttribute("type_id", event.getType_id());
				response.sendRedirect("ListEventNowServlet");
			} else {
				request.setAttribute("error", true);
				request.getRequestDispatcher("view/login.jsp").forward(request, response);
			}
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
}