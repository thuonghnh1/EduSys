package dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import entity.HocVien;
import utils.JdbcHelper;

public class HocVienDAO extends EduSysDAO<HocVien, Integer> {

	final String INSERT_SQL = "INSERT INTO HocVien (MaKhoaHoc, MaNguoiHoc, DiemTB) VALUES(?,?,?)";
	final String UPDATE_SQL = "UPDATE HocVien SET DiemTB = ?, MaKhoaHoc = ?, MaNguoiHoc = ? WHERE MaHocVien = ?";
	final String DELETE_SQL = "DELETE FROM HocVien WHERE MaNguoiHoc = ?";
	final String SELECT_ALL_SQL = "SELECT * FROM HocVien";
	final String SELECT_BY_ID_SQL = "SELECT * FROM HocVien WHERE MaHocVien = ?";
	final String SELECT_BY_KHOAHOC_SQL = "SELECT * FROM HocVien WHERE MaKhoaHoc = ?";

	@Override
	public void insert(HocVien entity) {
		JdbcHelper.update(INSERT_SQL, entity.getMaKhoaHoc(), entity.getMaNguoiHoc(), entity.getDiemTB());
	}

	@Override
	public void update(HocVien entity) {
		JdbcHelper.update(UPDATE_SQL, entity.getDiemTB(), entity.getMaKhoaHoc(), entity.getMaNguoiHoc(),
				entity.getMaHocVien());
	}

	@Override
	public void delete(Integer id) {
		JdbcHelper.update(DELETE_SQL, id);
	}

	@Override
	public List<HocVien> selectAll() {
		return selectBySql(SELECT_ALL_SQL);
	}

	@Override
	public HocVien selectById(Integer id) {
		List<HocVien> list = selectBySql(SELECT_BY_ID_SQL, id);
		if (list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

	@Override
	public List<HocVien> selectBySql(String sql, Object... args) {
		List<HocVien> list = new ArrayList<>();
		try {
			ResultSet rs = JdbcHelper.query(sql, args);
			while (rs.next()) {
				HocVien entity = new HocVien();
				entity.setMaHocVien(rs.getInt("MaHocVien"));
				entity.setMaKhoaHoc(rs.getInt("MaKhoaHoc"));
				entity.setMaNguoiHoc(rs.getString("MaNguoiHoc"));
				entity.setDiemTB(rs.getDouble("DiemTB"));
				list.add(entity);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return list;
	}

	public List<HocVien> SELECT_BY_KHOAHOC_SQL(int maKH) {
		return selectBySql(SELECT_BY_KHOAHOC_SQL, maKH);
	}
}
