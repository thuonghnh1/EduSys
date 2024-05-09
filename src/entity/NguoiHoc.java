package entity;

import java.util.Date;

public class NguoiHoc {
	private String maNguoiHoc, hoTen, GioiTinh, sdt, email, ghiChu, maNhanVien;
	private Date ngaySinh, ngayDK;

	@Override
	public String toString() {
		return this.hoTen;
	}

	public NguoiHoc() {
		super();
	}

	public NguoiHoc(String maNguoiHoc, String hoTen, Date ngaySinh, String gioiTinh, String sdt, String email,
			String ghiChu, String maNhanVien, Date ngayDK) {
		super();
		this.maNguoiHoc = maNguoiHoc;
		this.hoTen = hoTen;
		GioiTinh = gioiTinh;
		this.sdt = sdt;
		this.email = email;
		this.ghiChu = ghiChu;
		this.maNhanVien = maNhanVien;
		this.ngaySinh = ngaySinh;
		this.ngayDK = ngayDK;
	}

	public String getMaNguoiHoc() {
		return maNguoiHoc;
	}

	public void setMaNguoiHoc(String maNguoiHoc) {
		this.maNguoiHoc = maNguoiHoc;
	}

	public String getHoTen() {
		return hoTen;
	}

	public void setHoTen(String hoTen) {
		this.hoTen = hoTen;
	}

	public String getGioiTinh() {
		return GioiTinh;
	}

	public void setGioiTinh(String gioiTinh) {
		GioiTinh = gioiTinh;
	}

	public String getSdt() {
		return sdt;
	}

	public void setSdt(String sdt) {
		this.sdt = sdt;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGhiChu() {
		return ghiChu;
	}

	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
	}

	public String getMaNhanVien() {
		return maNhanVien;
	}

	public void setMaNhanVien(String maNhanVien) {
		this.maNhanVien = maNhanVien;
	}

	public Date getNgaySinh() {
		return ngaySinh;
	}

	public void setNgaySinh(Date ngaySinh) {
		this.ngaySinh = ngaySinh;
	}

	public Date getNgayDK() {
		return ngayDK;
	}

	public void setNgayDK(Date ngayDK) {
		this.ngayDK = ngayDK;
	}

}
