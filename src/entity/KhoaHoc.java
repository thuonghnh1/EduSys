package entity;

import java.util.Date;

public class KhoaHoc {
	private int maKhoaHoc, thoiLuong;
	private String maChuyenDe, ghiChu, maNhanVien;
	private double hocPhi;
	private Date ngayKhaiGiang, ngayTao;

	@Override
	public String toString() {
		return this.maChuyenDe + " (" + this.ngayKhaiGiang + ")";
	}

	public KhoaHoc() {
		super();
	}

	public KhoaHoc(int maKhoaHoc, String maChuyenDe, double hocPhi, int thoiLuong, Date ngayKhaiGiang, String ghiChu,
			String maNhanVien, Date ngayTao) {
		super();
		this.maKhoaHoc = maKhoaHoc;
		this.thoiLuong = thoiLuong;
		this.maChuyenDe = maChuyenDe;
		this.ghiChu = ghiChu;
		this.maNhanVien = maNhanVien;
		this.hocPhi = hocPhi;
		this.ngayKhaiGiang = ngayKhaiGiang;
		this.ngayTao = ngayTao;
	}

	public int getMaKhoaHoc() {
		return maKhoaHoc;
	}

	public void setMaKhoaHoc(int maKhoaHoc) {
		this.maKhoaHoc = maKhoaHoc;
	}

	public int getThoiLuong() {
		return thoiLuong;
	}

	public void setThoiLuong(int thoiLuong) {
		this.thoiLuong = thoiLuong;
	}

	public String getMaChuyenDe() {
		return maChuyenDe;
	}

	public void setMaChuyenDe(String maChuyenDe) {
		this.maChuyenDe = maChuyenDe;
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

	public double getHocPhi() {
		return hocPhi;
	}

	public void setHocPhi(double hocPhi) {
		this.hocPhi = hocPhi;
	}

	public Date getNgayKhaiGiang() {
		return ngayKhaiGiang;
	}

	public void setNgayKhaiGiang(Date ngayKhaiGiang) {
		this.ngayKhaiGiang = ngayKhaiGiang;
	}

	public Date getNgayTao() {
		return ngayTao;
	}

	public void setNgayTao(Date ngayTao) {
		this.ngayTao = ngayTao;
	}

}
