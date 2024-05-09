package dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import entity.ChuyenDe;
import utils.JdbcHelper;

public class ChuyenDeDAO extends EduSysDAO<ChuyenDe, String> {
	final String INSERT_SQL = "INSERT INTO ChuyenDe (MaChuyenDe, TenChuyenDe, HocPhi, ThoiLuong, HinhLogo, Motachuyende) VALUES(?,?,?,?,?,?)";
	final String UPDATE_SQL = "UPDATE ChuyenDe SET HocPhi = ?, TenChuyenDe = ?, ThoiLuong = ?, HinhLogo = ?, Motachuyende = ? WHERE MaChuyenDe = ?";
	final String DELETE_SQL = "DELETE FROM ChuyenDe WHERE MaChuyenDe = ?";
	final String SELECT_ALL_SQL = "SELECT * FROM ChuyenDe";
	final String SELECT_BY_ID_SQL = "SELECT * FROM ChuyenDe WHERE MaChuyenDe = ?";

	@Override
	public void insert(ChuyenDe entity) {
		JdbcHelper.update(INSERT_SQL, entity.getMaCD(), entity.getTenCD(), entity.getHocPhi(), entity.getThoiLuong(),
				entity.getHinhLogo(), entity.getMoTa());
	}

	@Override
	public void update(ChuyenDe entity) {
		JdbcHelper.update(UPDATE_SQL, entity.getHocPhi(), entity.getTenCD(), entity.getThoiLuong(),
				entity.getHinhLogo(), entity.getMoTa(), entity.getMaCD());
	}

	@Override
	public void delete(String id) {
		JdbcHelper.update(DELETE_SQL, id);
	}

	@Override
	public List<ChuyenDe> selectAll() {
		return selectBySql(SELECT_ALL_SQL);
	}

	@Override
	public ChuyenDe selectById(String id) {
		List<ChuyenDe> list = selectBySql(SELECT_BY_ID_SQL, id);
		if (list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

	@Override
	public List<ChuyenDe> selectBySql(String sql, Object... args) {
		List<ChuyenDe> list = new ArrayList<>();
		try {
			ResultSet rs = JdbcHelper.query(sql, args);
			while (rs.next()) {
				ChuyenDe entity = new ChuyenDe();
				entity.setMaCD(rs.getString("MaChuyenDe"));
				entity.setTenCD(rs.getString("TenChuyenDe"));
				entity.setHocPhi(rs.getDouble("HocPhi"));
				entity.setThoiLuong(rs.getInt("ThoiLuong"));
				entity.setHinhLogo(rs.getString("HinhLogo"));
				entity.setMoTa(rs.getString("Motachuyende"));
				list.add(entity);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return list;
	}

}
