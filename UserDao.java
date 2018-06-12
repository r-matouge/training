package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.mindrot.jbcrypt.BCrypt;

import domain.User;
public class UserDao {
	private DataSource ds;

	public UserDao(DataSource ds) {
		this.ds = ds;
	}
	public List<User> findAll() throws Exception{
		List<User> userList = new ArrayList<>();
		try (Connection con = ds.getConnection()) {
			String sql = " SELECT users.id,users.name,users.login_id, "
					+ " groups.name as group_name "
					+ " from users join groups "
					+ " on users.group_id=groups.id ";
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				userList.add(mapToUser(rs));
			}
		}
		return userList;
	};
	public List<User> findAllPage(int start) throws Exception{
		List<User> userList = new ArrayList<>();
		try (Connection con = ds.getConnection()) {
			String sql = " SELECT users.id,users.login_id,users.name, "
					+ " groups.name as group_name "
					+ " from users join groups "
					+ " on users.group_id=groups.id ";
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			int i = 1;
			while (rs.next()) {
				if (i >= start && i <= start + 4) {
					userList.add(mapToUser(rs));
				}
				i++;
			}
		}
		return userList;
	};



	private User mapToUser(ResultSet rs) throws SQLException {
		User user = new User();
		user.setId((Integer) rs.getObject("id"));
		user.setLoginId(rs.getString("login_id"));
		user.setName(rs.getString("name"));
		user.setGroup_name(rs.getString("group_name"));
		return user;
	}

	public List<User> UserMore(Integer id) throws Exception{
		List<User> userList = new ArrayList<>();
		try (Connection con = ds.getConnection()) {
			String sql = " SELECT users.id,users.login_id,users.name, "
					+ " groups.name as group_name "
					+ " from users join groups "
					+ " on users.group_id=groups.id where users.id=?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setObject(1,id,Types.INTEGER);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				userList.add(mapToUser(rs));
			}
		}
		return userList;
	};

	public void insert(User user) throws Exception{
		try (Connection con = ds.getConnection()) {
			String sql = " INSERT INTO users "
					+ " VALUES(null,?,?,?,2,?,NOW()) ";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, user.getLoginId());
			stmt.setString(2, BCrypt.hashpw(user.getLoginPass(),BCrypt.gensalt()));
			stmt.setString(3, user.getName());
			stmt.setObject(4, user.getGroup_id(), Types.INTEGER);
			stmt.executeUpdate();
		}
	};

	public void update(User user) throws Exception{
		try (Connection con = ds.getConnection()) {
			String sql = " update users set name=?,login_pass=?, "
					+" login_id=?, group_id=? where id=? ";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, user.getName());
			stmt.setString(2, BCrypt.hashpw(user.getLoginPass(),BCrypt.gensalt()));
			stmt.setString(3, user.getLoginId());
			//stmt.setObject(4, 2, Types.INTEGER);
			stmt.setObject(4, user.getGroup_id(), Types.INTEGER);
			stmt.setObject(5, user.getId(), Types.INTEGER);
			stmt.executeUpdate();
		}
	};

	public void update_notpass(User user) throws Exception{
		try (Connection con = ds.getConnection()) {
			String sql = " update users set name=?, "
					+" login_id=?, group_id=? where id=? ";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, user.getName());
			//stmt.setString(2, BCrypt.hashpw(user.getLoginPass(),BCrypt.gensalt()));
			stmt.setString(2, user.getLoginId());
			//stmt.setObject(4, 2, Types.INTEGER);
			stmt.setObject(3, user.getGroup_id(), Types.INTEGER);
			stmt.setObject(4, user.getId(), Types.INTEGER);
			stmt.executeUpdate();
		}
	};

	public void delete(User user) throws Exception{
		try (Connection con = ds.getConnection()) {
			String sql = " delete from users "
					+ " where id=? ";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setObject(1,user.getId(),Types.INTEGER);
			stmt.executeUpdate();
			String sql2 = " delete from attends "
					+ " where user_id=? ";
			PreparedStatement stmt2 = con.prepareStatement(sql2);
			stmt2.setObject(1, user.getId(), Types.INTEGER);
			stmt2.executeUpdate();
		}
	};



}
