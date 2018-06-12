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

@WebServlet("/ListEventNowServlet")
public class ListEventNowServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

    public ListEventNowServlet() {
        super();
    }

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
			HttpSession session =request.getSession();
			Integer ID = (Integer) session.getAttribute("Id");
			EventDao eventDao = DaoFactory.createEventDao();
			List<Event> eventlength = eventDao.EventListNow(ID);
			List<Event> eventList = eventDao.EventListNowPage(page,ID);
			int length=(int)(eventlength.size()/5);

			if (eventlength.size()%5!=0) {
				length=length+1;
			}
			event.setLength(length);

			request.setAttribute("event", event);
			request.setAttribute("eventList", eventList);
			request.getRequestDispatcher("view/EventToday.jsp").forward(request, response);
		}
		catch (Exception e) {
			throw new ServletException(e);
		}
	}
}
