package controller;

import java.io.IOException;
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

@WebServlet("/EventMoreServlet")
public class EventMoreServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public EventMoreServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getParameter("attend") == null) {
			// パラメータの取得
			Integer id = Integer.parseInt(request.getParameter("id"));

			// データの追加
			//		Event eventMore = new Event();
			//		eventMore.setId(id);

			try {
				HttpSession session =request.getSession();
				Integer ID = (Integer) session.getAttribute("Id");
				EventDao eventDao = DaoFactory.createEventDao();
				List<Event> eventList = eventDao.EventMore(id,ID);
				List<Event> userList = eventDao.AttendUser(id);
				List<Event> make = eventDao.MakeUser(id);
				int sumMember=0;
				for(Event e:userList) {
					sumMember++;
				}

				request.setAttribute("eventList", eventList);
				request.setAttribute("userList", userList);
				request.setAttribute("sumMember",sumMember );
				request.setAttribute("make", make);
				request.getRequestDispatcher("view/EventMore.jsp").forward(request, response);
			} catch (Exception e) {
				throw new ServletException(e);
			}
		} else if (Integer.parseInt(request.getParameter("attend")) == 0) {

			// パラメータの取得
			Integer id = Integer.parseInt(request.getParameter("id"));
			Integer userid = Integer.parseInt(request.getParameter("userid"));
			// データの追加
			//		Event eventMore = new Event();
			//		eventMore.setId(id);

			try {
				HttpSession session =request.getSession();
				Integer ID = (Integer) session.getAttribute("Id");
				EventDao eventDao = DaoFactory.createEventDao();
				eventDao.deleteAttend(id,ID);
				List<Event> eventList = eventDao.EventMore(id,ID);
				List<Event> userList = eventDao.AttendUser(id);
				List<Event> make = eventDao.MakeUser(id);
				int sumMember=0;
				for(Event e:userList) {
					sumMember++;
				}

				request.setAttribute("eventList", eventList);
				request.setAttribute("userList", userList);
				request.setAttribute("sumMember",sumMember );
				request.setAttribute("make", make);
				request.getRequestDispatcher("view/EventMore.jsp").forward(request, response);
			} catch (Exception e) {
				throw new ServletException(e);
			}

		} else if (Integer.parseInt(request.getParameter("attend")) == 1) {

			// パラメータの取得
			Integer id = Integer.parseInt(request.getParameter("id"));
			//Integer userid = Integer.parseInt(request.getParameter("userid"));
			// データの追加
			//		Event eventMore = new Event();
			//		eventMore.setId(id);

			try {
				EventDao eventDao = DaoFactory.createEventDao();
				HttpSession session =request.getSession();
				Integer ID = (Integer) session.getAttribute("Id");
				String name = (String) session.getAttribute("loginName");
				boolean attend=true;
				for(Event e :eventDao.AttendUser(id)){
					if(e.getName().equals(name)) {
						attend=false;
					}
				}
				if(attend) {
					eventDao.insertAttend(id,ID);
				}
				List<Event> eventList = eventDao.EventMore(id,ID);
				List<Event> userList = eventDao.AttendUser(id);
				List<Event> make = eventDao.MakeUser(id);
				int sumMember=0;
				for(Event e:userList) {
					sumMember++;
				}

				request.setAttribute("eventList", eventList);
				request.setAttribute("userList", userList);
				request.setAttribute("sumMember",sumMember );
				request.setAttribute("make", make);
				request.getRequestDispatcher("view/EventMore.jsp").forward(request, response);
			} catch (Exception e) {
				throw new ServletException(e);
			}

		}
	}

}
