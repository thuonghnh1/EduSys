package dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import utils.JdbcHelper;

public class ThongKeDAO {
    public List<Object[]> getListToArray(String sql, String[] cols, Object... args) {
        try {
            List<Object[]> list = new ArrayList<>();
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                Object[] vals = new Object[cols.length];
                for (int i = 0; i < cols.length; i++) {
                    vals[i] = rs.getObject(cols[i]);
                }
                list.add(vals);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public List<Object[]> getBangDiem(Integer MaKhoaHoc) {
    	String sql = "{CALL sp_BangDiem(?)}";
    	String[] cols = {"MaNguoiHoc","HoTen","DiemTB"};
    	return getListToArray(sql, cols, MaKhoaHoc);
    }

    public List<Object[]> getLuongNguoiHoc() {
    	String sql = "{CALL sp_LuongNguoiHoc}";
    	String[] cols = {"Nam","SoLuong","DauTien","CuoiCung"};
    	return getListToArray(sql, cols);
    }
    
    public List<Object[]> getDiemChuyenDe() {
    	String sql = "{CALL sp_DiemChuyenDe}";
    	String[] cols = {"ChuyenDe","SoHV","ThapNhat","CaoNhat","TrungBinh"};
    	return getListToArray(sql, cols);
    }
    
    public List<Object[]> getDoanhThu(Integer nam) {
    	String sql = "{CALL sp_DoanhThu(?)}";
    	String[] cols = {"ChuyenDe","SoKhoaHoc","SoHocVien","DoanhThu","ThapNhat","CaoNhat","TB"};
    	return getListToArray(sql, cols, nam);
    }
    
}
