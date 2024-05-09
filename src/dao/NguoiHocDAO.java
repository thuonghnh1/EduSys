package dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import entity.NguoiHoc;
import utils.JdbcHelper;

public class NguoiHocDAO extends EduSysDAO<NguoiHoc, String> {
	final String INSERT_SQL = "INSERT INTO NguoiHoc (MaNguoiHoc, Hoten, NgaySinh, GioiTinh, SDT, Email, GhiChu, MaNhanVien, NgayDK) VALUES(?,?,?,?,?,?,?,?,?)";
	final String UPDATE_SQL = "UPDATE NguoiHoc SET Hoten = ?, NgaySinh = ?, GioiTinh = ?, SDT = ?, Email = ?, GhiChu = ?, MaNhanVien = ?, NgayDK = ?  WHERE MaNguoiHoc = ?";
	final String DELETE_SQL = "DELETE FROM NguoiHoc WHERE MaNguoiHoc = ?";
	final String SELECT_ALL_SQL = "SELECT * FROM NguoiHoc";
	final String SELECT_BY_ID_SQL = "SELECT * FROM NguoiHoc WHERE MaNguoiHoc = ?";

	final String SELECT_NOT_IN_COURSE_SQL = "SELECT * FROM NguoiHoc WHERE MaNguoiHoc NOT IN (Select MaNguoiHoc FROM HocVien WHERE MaKhoaHoc = ?)";
	final String SELECT_BY_KEYWORD = "SELECT * FROM NguoiHoc WHERE HoTen LIKE ? AND MaNguoiHoc NOT IN (SELECT  MaNguoiHoc FROM HocVien WHERE MaKhoaHoc = ?)";
	final String SELECT_BY_NAME = "SELECT * FROM NguoiHoc WHERE HoTen LIKE ?";

	@Override
	public void insert(NguoiHoc entity) {
		JdbcHelper.update(INSERT_SQL, entity.getMaNguoiHoc(), entity.getHoTen(), entity.getNgaySinh(),
				entity.getGioiTinh(), entity.getSdt(), entity.getEmail(), entity.getGhiChu(), entity.getMaNhanVien(),
				entity.getNgayDK());
	}

	@Override
	public void update(NguoiHoc entity) {
		JdbcHelper.update(UPDATE_SQL, entity.getHoTen(), entity.getNgaySinh(), entity.getGioiTinh(), entity.getSdt(),
				entity.getEmail(), entity.getGhiChu(), entity.getMaNhanVien(), entity.getNgayDK(),
				entity.getMaNguoiHoc());
	}

	@Override
	public void delete(String id) {
		JdbcHelper.update(DELETE_SQL, id);
	}

	@Override
	public List<NguoiHoc> selectAll() {
		return selectBySql(SELECT_ALL_SQL);
	}

	@Override
	public NguoiHoc selectById(String id) {
		List<NguoiHoc> list = selectBySql(SELECT_BY_ID_SQL, id);
		if (list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

	@Override
	public List<NguoiHoc> selectBySql(String sql, Object... args) {
		List<NguoiHoc> list = new ArrayList<>();
		try {
			ResultSet rs = JdbcHelper.query(sql, args);
			while (rs.next()) {
				NguoiHoc entity = new NguoiHoc();
				entity.setMaNguoiHoc(rs.getString("MaNguoiHoc"));
				entity.setHoTen(rs.getString("Hoten"));
				entity.setNgaySinh(rs.getDate("NgaySinh"));
				entity.setGioiTinh(rs.getString("GioiTinh"));
				entity.setSdt(rs.getString("SDT"));
				entity.setEmail(rs.getString("Email"));
				entity.setGhiChu(rs.getString("GhiChu"));
				entity.setMaNhanVien(rs.getString("MaNhanVien"));
				entity.setNgayDK(rs.getDate("NgayDK"));
				list.add(entity);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return list;
	}

	public List<NguoiHoc> selectNotInCourse(int maKH) {
		return selectBySql(SELECT_NOT_IN_COURSE_SQL, maKH);
	}

	public List<NguoiHoc> selectByKeyword(int maKH, String keyword) {
		return selectBySql(SELECT_BY_KEYWORD, "%" + keyword + "%", maKH);
	}

	public List<NguoiHoc> selectByName(String keyword) {
		return selectBySql(SELECT_BY_NAME, "%" + keyword + "%");
	}
}
