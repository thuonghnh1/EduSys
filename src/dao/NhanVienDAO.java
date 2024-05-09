package dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import entity.NhanVien;
import utils.JdbcHelper;

public class NhanVienDAO extends EduSysDAO<NhanVien, String> {

	final String INSERT_SQL = "INSERT INTO NhanVien(MaNhanVien, MatKhau, HoTen, VaiTro) VALUES(?, ?, ?, ?)";
	final String UPDATE_SQL = "UPDATE NhanVien SET MatKhau = ?, HoTen = ?, VaiTro = ? WHERE MaNhanVien = ?";
	final String DELETE_SQL = "DELETE FROM NhanVien WHERE MaNhanVien = ?";
	final String SELECT_ALL_SQL = "SELECT * FROM NhanVien";
	final String SELECT_BY_ID_SQL = "SELECT * FROM NhanVien WHERE MaNhanVien = ?";

	@Override
	public void insert(NhanVien entity) {
		JdbcHelper.update(INSERT_SQL, entity.getMaNV(), entity.getPass(), entity.getHoTen(), entity.isVaiTro());
	}

	@Override
	public void update(NhanVien entity) {
		JdbcHelper.update(UPDATE_SQL, entity.getPass(), entity.getHoTen(), entity.isVaiTro(), entity.getMaNV());
	}

	@Override
	public void delete(String id) {
		JdbcHelper.update(DELETE_SQL, id);
	}

	@Override
	public List<NhanVien> selectAll() {
		return selectBySql(SELECT_ALL_SQL);
	}

	@Override
	public NhanVien selectById(String id) {
		List<NhanVien> list= selectBySql(SELECT_BY_ID_SQL, id);
		if (list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

	@Override
	public List<NhanVien> selectBySql(String sql, Object... args) {
		List<NhanVien> list = new ArrayList<>();
		try {
			ResultSet rs = JdbcHelper.query(sql, args);
			while (rs.next()) {
				NhanVien entity = new NhanVien();
				entity.setMaNV(rs.getString("MaNhanVien"));
				entity.setPass(rs.getString("MatKhau"));
				entity.setHoTen(rs.getString("HoTen"));
				entity.setVaiTro(rs.getBoolean("VaiTro"));
				list.add(entity);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return list;
	}

}
