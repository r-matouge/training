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

@WebServlet("/DeleteEventServlet")
public class DeleteEventServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.getRequestDispatcher("view/DeleteEventDone.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Integer id = Integer.parseInt(request.getParameter("id"));

		Event event = new Event();
		event.setId(id);

		try {
			EventDao eventDao = DaoFactory.createEventDao();
			eventDao.delete(event);

			request.getRequestDispatcher("view/DeleteEventDone.jsp").forward(request, response);
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
}