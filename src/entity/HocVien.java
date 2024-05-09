package entity;

public class HocVien {
	private int maHocVien, maKhoaHoc;
	private String maNguoiHoc;
	private double diemTB = -1;

	public HocVien() {
		super();
	}

	public HocVien(int maHocVien, int maKhoaHoc, String maNguoiHoc, double diemTB) {
		super();
		this.maHocVien = maHocVien;
		this.maKhoaHoc = maKhoaHoc;
		this.maNguoiHoc = maNguoiHoc;
		this.diemTB = diemTB;
	}

	public int getMaHocVien() {
		return maHocVien;
	}

	public void setMaHocVien(int maHocVien) {
		this.maHocVien = maHocVien;
	}

	public int getMaKhoaHoc() {
		return maKhoaHoc;
	}

	public void setMaKhoaHoc(int maKhoaHoc) {
		this.maKhoaHoc = maKhoaHoc;
	}

	public String getMaNguoiHoc() {
		return maNguoiHoc;
	}

	public void setMaNguoiHoc(String maNguoiHoc) {
		this.maNguoiHoc = maNguoiHoc;
	}

	public double getDiemTB() {
		return diemTB;
	}

	public void setDiemTB(double diemTB) {
		this.diemTB = diemTB;
	}

}
