package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.KhoaHoc;
import utils.JdbcHelper;

public class KhoaHocDAO extends EduSysDAO<KhoaHoc, Integer> {
	final String INSERT_SQL = "INSERT INTO KhoaHoc (MaChuyenDe, HocPhi, ThoiLuong, NgayKhaiGiang, GhiChu, MaNhanVien, NgayTao) VALUES(?,?,?,?,?,?,?)";
	final String UPDATE_SQL = "UPDATE KhoaHoc SET MaChuyenDe = ?, HocPhi = ?, ThoiLuong = ?, NgayKhaiGiang = ?, GhiChu = ?, MaNhanVien = ?, NgayTao = ?  WHERE MaKhoaHoc = ?";
	final String DELETE_SQL = "DELETE FROM KhoaHoc WHERE MaKhoaHoc = ?";
	final String SELECT_ALL_SQL = "SELECT * FROM KhoaHoc";
	final String SELECT_BY_ID_SQL = "SELECT * FROM KhoaHoc WHERE MaKhoaHoc = ?";
	final String SELECT_BY_MA_CD_SQL = "SELECT * FROM KhoaHoc WHERE MaChuyenDe = ?";
	final String SELECT_YEAR = "SELECT DISTINCT YEAR(NgayKhaiGiang) AS Nam FROM KhoaHoc ORDER BY Nam DESC";

	@Override
	public void insert(KhoaHoc entity) {
		JdbcHelper.update(INSERT_SQL, entity.getMaChuyenDe(), entity.getHocPhi(), entity.getThoiLuong(),
				entity.getNgayKhaiGiang(), entity.getGhiChu(), entity.getMaNhanVien(), entity.getNgayTao());
	}

	@Override
	public void update(KhoaHoc entity) {
		JdbcHelper.update(UPDATE_SQL, entity.getMaChuyenDe(), entity.getHocPhi(), entity.getThoiLuong(), entity.getNgayKhaiGiang(),
				entity.getGhiChu(), entity.getMaNhanVien(), entity.getNgayTao(), entity.getMaKhoaHoc());
	}

	@Override
	public void delete(Integer id) {
		JdbcHelper.update(DELETE_SQL, id);
	}

	@Override
	public List<KhoaHoc> selectAll() {
		return selectBySql(SELECT_ALL_SQL);
	}

	@Override
	public KhoaHoc selectById(Integer id) {
		List<KhoaHoc> list = selectBySql(SELECT_BY_ID_SQL, id);
		if (list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

	@Override
	public List<KhoaHoc> selectBySql(String sql, Object... args) {
		List<KhoaHoc> list = new ArrayList<>();
		try {
			ResultSet rs = JdbcHelper.query(sql, args);
			while (rs.next()) {
				KhoaHoc entity = new KhoaHoc();
				entity.setMaKhoaHoc(rs.getInt("MaKhoaHoc"));
				entity.setHocPhi(rs.getDouble("HocPhi"));
				entity.setThoiLuong(rs.getInt("ThoiLuong"));
				entity.setNgayKhaiGiang(rs.getDate("NgayKhaiGiang"));
				entity.setGhiChu(rs.getString("GhiChu"));
				entity.setMaNhanVien(rs.getString("MaNhanVien"));
				entity.setNgayTao(rs.getDate("NgayTao"));
				entity.setMaChuyenDe(rs.getString("MaChuyenDe"));
				list.add(entity);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return list;
	}

	public List<KhoaHoc> selectKhoaHocByChuyenDe(String maCD) {
		return selectBySql(SELECT_BY_MA_CD_SQL, maCD);
	}

	public List<Integer> selectYear() {
		List<Integer> years = new ArrayList<>();
		try {
			ResultSet rs = JdbcHelper.query(SELECT_YEAR);
			while (rs.next()) {
				years.add(rs.getInt(1));
			}
			rs.getStatement().getConnection().close();
			return years;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
}
