package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.mindrot.jbcrypt.BCrypt;

import domain.Event;

public class EventDao {
	private DataSource ds;

	public EventDao(DataSource ds) {
		this.ds = ds;
	}

	public List<Event> EventList(Integer ID) throws Exception {
		List<Event> eventList = new ArrayList<>();
		try (Connection con = ds.getConnection()) {
			String sql = " SELECT events.id,events.title,events.start,events.place, "
					+ " groups.name as group_name "
					+ " from events join groups "
					+ " on events.group_id=groups.id ";
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				eventList.add(mapToEvent(rs,ID));
			}
		}
		return eventList;
	};

	public List<Event> PageList(int start,Integer ID) throws Exception {
		List<Event> eventList = new ArrayList<>();
		try (Connection con = ds.getConnection()) {
			String sql = " SELECT events.id,events.title,events.start,events.place, "
					+ " groups.name as group_name "
					+ " from events join groups "
					+ " on events.group_id=groups.id order by events.start desc ";
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			int i = 1;
			while (rs.next()) {
				if (i >= start && i <= start + 4) {
					eventList.add(mapToEvent(rs,ID));
				}
				i++;
			}
		}
		return eventList;
	};

	private Event mapToEvent(ResultSet rs,Integer ID) throws SQLException, ParseException {
		Event event = new Event();
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		event.setId((Integer) rs.getObject("id"));
		event.setTitle(rs.getString("title"));
		event.setStart(sdFormat.parse(rs.getString("start")));
		event.setPlace(rs.getString("place"));
		event.setGroup_name(rs.getString("group_name"));
		event.setAttend(Attend((Integer) rs.getObject("id"),ID));
		return event;
	}

	private boolean Attend(Integer event,Integer user) throws SQLException {

		try (Connection con = ds.getConnection()) {
			String sql = " select *from attends "
					+ " where user_id=? and event_id=? ";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setObject(1, user, Types.INTEGER);
			stmt.setObject(2, event, Types.INTEGER);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				return true;
			}else {
				return false;
			}
		}

	}

	public List<Event> EventListNow(Integer ID) throws Exception {
		List<Event> eventList = new ArrayList<>();
		try (Connection con = ds.getConnection()) {
			String sql = " SELECT events.id,events.title,events.start,events.place, "
					+ " groups.name as group_name "
					+ " from events join groups "
					+ " on events.group_id=groups.id "
					+ " where date(events.start)=date(now())";
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				eventList.add(mapToEvent(rs,ID));
			}
		}
		return eventList;
	};

	public List<Event> EventListNowPage(int start,Integer ID) throws Exception {
		List<Event> eventList = new ArrayList<>();
		try (Connection con = ds.getConnection()) {
			String sql = " SELECT events.id,events.title,events.start,events.place, "
					+ " groups.name as group_name "
					+ " from events join groups "
					+ " on events.group_id=groups.id "
					+ " where date(events.start)=date(now())";
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			int i = 1;
			while (rs.next()) {
				if (i >= start && i <= start + 4) {
					eventList.add(mapToEvent(rs,ID));
				}
				i++;
			}
		}
		return eventList;
	};

	public List<Event> EventMore(Integer id,Integer user) throws Exception {
		List<Event> eventList = new ArrayList<>();
		try (Connection con = ds.getConnection()) {
			String sql = " SELECT events.id,events.title,events.start,"
					+ " events.end,events.place,events.registered_by, "
					+ " groups.name as group_name,events.detail,"
					+ " users.name as name "
					+ " from events join groups "
					+ " on events.group_id=groups.id"
					+ " join users on events.registered_by=users.id"
					+ " where events.id=?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setObject(1, id, Types.INTEGER);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				eventList.add(mapToEvent2(rs,user));
			}
		}
		return eventList;
	};

	private Event mapToEvent2(ResultSet rs,Integer user) throws SQLException, ParseException {
		Event event = new Event();
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		event.setId((Integer) rs.getObject("id"));
		event.setTitle(rs.getString("title"));
		event.setStart(sdFormat.parse(rs.getString("start")));
		event.setEnd(sdFormat.parse(rs.getString("end")));
		event.setPlace(rs.getString("place"));
		event.setGroup_name(rs.getString("group_name"));
		event.setDetail(rs.getString("detail"));
		event.setName(rs.getString("name"));
		event.setAttend(Attend((Integer) rs.getObject("id"),user));
		event.setEnd_not(End_not(rs));
		event.setRegistered_by((Integer)rs.getObject("registered_by"));
		return event;
	}

	private boolean End_not(ResultSet rs) throws ParseException, SQLException {
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		if((sdFormat.parse(rs.getString("end"))).equals(sdFormat.parse("0001-01-01 00:00:00"))){
			return true;
		}else {
			return false;
		}
	}
	public void insert(Event event) throws Exception {
		try (Connection con = ds.getConnection()) {
			String sql = " INSERT INTO events "
					+ " VALUES(null,?,?,?,?,?,?,?,NOW()) ";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, event.getTitle());
			stmt.setObject(2, event.getStart(), Types.TIMESTAMP);
			stmt.setObject(3, event.getEnd(), Types.TIMESTAMP);
			stmt.setString(4, event.getPlace());
			stmt.setObject(5, event.getGroup_id(), Types.INTEGER);
			stmt.setString(6, event.getDetail());
			stmt.setObject(7, event.getRegistered_by(), Types.INTEGER);
			stmt.executeUpdate();
		}

	};

	public void update(Event event) throws Exception {
		try (Connection con = ds.getConnection()) {
			String sql = " update events set title=?,start=?, "
					+ " end=?,place=?,group_id=?,detail=?,registered_by=?  where id=? ";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, event.getTitle());
			stmt.setObject(2, event.getStart(), Types.TIMESTAMP);
			stmt.setObject(3, event.getEnd(), Types.TIMESTAMP);
			stmt.setString(4, event.getPlace());
			stmt.setObject(5, event.getGroup_id(), Types.INTEGER);
			stmt.setString(6, event.getDetail());
			stmt.setObject(7, event.getRegistered_by(), Types.INTEGER);
			stmt.setObject(8, event.getId(), Types.INTEGER);
			stmt.executeUpdate();
		}
	};

	public void delete(Event event) throws Exception {
		try (Connection con = ds.getConnection()) {
			String sql = " delete from events "
					+ " where id=? ";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setObject(1, event.getId(), Types.INTEGER);
			stmt.executeUpdate();
			String sql2 = " delete from attends "
					+ " where event_id=? ";
			PreparedStatement stmt2 = con.prepareStatement(sql2);
			stmt2.setObject(1, event.getId(), Types.INTEGER);
			stmt2.executeUpdate();
		}
	};

	public Event findByLoginIdAndLoginPass(String loginId, String loginPass) throws Exception {

		Event event = null;
		try (Connection con = ds.getConnection()) {
			String sql = " SELECT * "
					+ " FROM users WHERE login_id=? ";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, loginId);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				if (BCrypt.checkpw(loginPass, rs.getString("login_pass"))) {
					event = mapToEvent3(rs);
				}
			}
		}
		return event;
	}

	private Event mapToEvent3(ResultSet rs) throws SQLException {
		Event event = new Event();
		event.setId((Integer) rs.getObject("id"));
		event.setName(rs.getString("name"));
		event.setType_id((Integer)rs.getObject("type_id"));
		return event;
	}

	public List<Event> AttendUser(Integer id) throws Exception {
		List<Event> eventList = new ArrayList<>();
		try (Connection con = ds.getConnection()) {
			String sql = "select users.name as name from attends "
					+ " join users on attends.user_id=users.id "
					+ " where event_id=? ";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setObject(1, id, Types.INTEGER);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Event event = new Event();
				event.setName(rs.getString("name"));
				eventList.add(event);
			}
		}
		return eventList;
	};

	public List<Event> MakeUser(Integer id) throws Exception {
		List<Event> eventList = new ArrayList<>();
		try (Connection con = ds.getConnection()) {
			String sql = "select users.name from events "
					+ " join users on users.id=events.registered_by "
					+ " where events.id=? ";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setObject(1, id, Types.INTEGER);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Event event = new Event();
				event.setName(rs.getString("name"));
				eventList.add(event);
			}
		}
		return eventList;
	};

	public void insertAttend(Integer id,Integer userid) throws Exception {
		try (Connection con = ds.getConnection()) {
			String sql = " insert into attends values (null,?,?) ";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setObject(1, userid, Types.INTEGER);
			stmt.setObject(2, id, Types.INTEGER);
			stmt.executeUpdate();
		}

	};

	public void deleteAttend(Integer id,Integer userid) throws Exception {
		try (Connection con = ds.getConnection()) {
			String sql = " delete from attends "
					+ "where event_id=? and user_id=? ";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setObject(1, id, Types.INTEGER);
			stmt.setObject(2, userid, Types.INTEGER);
			stmt.executeUpdate();

		}

	};
}
