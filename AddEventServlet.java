package controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.DaoFactory;
import dao.EventDao;
import domain.Event;

@WebServlet("/AddEventServlet")
public class AddEventServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("view/AddEvent.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//パラメータの取得
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
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		}

		System.out.print(start);
		System.out.print(end);

		String place = request.getParameter("place");
		Integer group_id = Integer.parseInt(request.getParameter("group_id"));
		String detail = request.getParameter("detail");
		HttpSession session =request.getSession();
		Integer ID = (Integer) session.getAttribute("Id");


		//データの追加
		Event event = new Event();
		event.setTitle(title);
		event.setStart(start);
		event.setEnd(end);
		event.setPlace(place);
		event.setGroup_id(group_id);
		event.setDetail(detail);
		event.setRegistered_by(ID);

		try {
			EventDao eventDao = DaoFactory.createEventDao();
			eventDao.insert(event);
			request.getRequestDispatcher("view/AddEventDone.jsp").forward(request, response);
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
}