package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcHelper {
	private static String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private static String dburl = "jdbc:sqlserver://localhost:1433;databaseName=Polypro;encrypt=true;trustServerCertificate=true;";
	private static String user = "sa";
	private static String pass = "123";

	static {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	/* 
	 * Xây dựng PreparedStatement
	 * 
	 * @return pstmt tạo được
	 * 
	 * @throws SQLException lỗi sai cú pháp
	 */
	public static PreparedStatement getStmt(String sql, Object... args) throws SQLException {
		Connection connection = DriverManager.getConnection(dburl, user, pass);
		PreparedStatement pstmt = null;
		if (sql.trim().startsWith("{")) {
			pstmt = connection.prepareCall(sql);
		} else {
			pstmt = connection.prepareStatement(sql);
		}
		for (int i = 0; i < args.length; i++) {
			pstmt.setObject(i + 1, args[i]);
		}
		return pstmt;
	}

	/* 
	 * Câu lệnh thao tác (INSERT, UPDATE, DELETE)
	 * 
	 * @sql là câu lệnh SQL có chứa tham số
	 * 
	 * @args là ds các giá trị được cung cấp cho các tham số trong câu lệnh SQL
	 */
	public static int update(String sql, Object... args) {
		try {
			PreparedStatement stmt = getStmt(sql, args);
			try {
				return stmt.executeUpdate();
			} finally {
				stmt.getConnection().close();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/* 
	 * Truy vấn dữ liệu
	 * 
	 * @sql là câu lệnh SQL có chứa tham số
	 * 
	 * @args là ds các giá trị được cung cấp cho các tham số trong câu lệnh SQL
	 */
	public static ResultSet query(String sql, Object... args) {
		try {
			PreparedStatement stmt = getStmt(sql, args);
			return stmt.executeQuery();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
// trả về đối tượng chung chung
	public static Object value(String sql, Object... args) {
		try {
			ResultSet rs = query(sql, args);
			if (rs.next()) {
				return rs.getObject(0);
			}
			rs.getStatement().getConnection().close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return null;
	}
	
}
