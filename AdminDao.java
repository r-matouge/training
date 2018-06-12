package dao;

import java.util.List;

import javax.sql.DataSource;

import domain.Admin;

public class AdminDao {
	private DataSource ds;

	public AdminDao(DataSource ds) {
		this.ds = ds;
	}
	public List<Admin> findAll() throws Exception{
		return null;
	};

	public Admin findById(Integer id) throws Exception{
		return null;
	};

	public void insert(Admin admin) throws Exception{

	};

	public void update(Admin admin) throws Exception{

	};

	public void delete(Admin admin) throws Exception{

	};

	public Admin findByLoginIdAndLoginPass(String loginId, String loginPass) throws Exception{
		return null;
	};

}
