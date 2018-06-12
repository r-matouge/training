package controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.DaoFactory;
import dao.EventDao;
import domain.Event;

@WebServlet("/UpdateEventServlet")
public class UpdateEventServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// パラメータの取得
		Integer id = Integer.parseInt(request.getParameter("event_id"));

		// データの追加
		//				Event eventMore = new Event();
		//				eventMore.setId(id);

		try {
			EventDao eventDao = DaoFactory.createEventDao();
			HttpSession session =request.getSession();
			Integer ID = (Integer) session.getAttribute("Id");
			List<Event> eventList = eventDao.EventMore(id,ID);

			request.setAttribute("eventList", eventList);
			request.getRequestDispatcher("view/UpdateEvent.jsp").forward(request, response);
		} catch (Exception e) {
			throw new ServletException(e);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Integer id = Integer.parseInt(request.getParameter("event_id"));
		String title = request.getParameter("title");

		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date start = null;
		Date end = null;
		try {
			start = sdFormat.parse(request.getParameter("start")+":00");
			if(""!=request.getParameter("end")) {
				end = sdFormat.parse(request.getParameter("end")+":00");
			}else {
				end = sdFormat.parse("0001-01-01 00:00:00");
			}
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		String place = request.getParameter("place");
		Integer group_id = Integer.parseInt(request.getParameter("group_id"));
		String detail = request.getParameter("detail");
//		Integer registered_by = Integer.parseInt(request.getParameter("registered_by"));

		Event event = new Event();
		event.setId(id);
		event.setTitle(title);
		event.setStart(start);
		event.setEnd(end);
		event.setPlace(place);
		event.setGroup_id(group_id);
		event.setDetail(detail);
		event.setRegistered_by(1);

		try {
			EventDao eventDao = DaoFactory.createEventDao();
			eventDao.update(event);

			request.setAttribute("eventId", id);
			request.getRequestDispatcher("view/UpdateEventDone.jsp").forward(request, response);
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
}