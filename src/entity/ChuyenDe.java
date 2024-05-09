package entity;

public class ChuyenDe {
	private String maCD, tenCD, hinhLogo, moTa;
	private double hocPhi;
	private int thoiLuong;
	
	public ChuyenDe() {
		super();
	}
	
	public ChuyenDe(String maCD, String tenCD, double hocPhi, int thoiLuong, String hinhLogo, String moTa) {
		super();
		this.maCD = maCD;
		this.tenCD = tenCD;
		this.hinhLogo = hinhLogo;
		this.moTa = moTa;
		this.hocPhi = hocPhi;
		this.thoiLuong = thoiLuong;
	}

	@Override
	public String toString() {
		return this.tenCD;
	}

	public String getMaCD() {
		return maCD;
	}

	public void setMaCD(String maCD) {
		this.maCD = maCD;
	}

	public String getTenCD() {
		return tenCD;
	}

	public void setTenCD(String tenCD) {
		this.tenCD = tenCD;
	}

	public String getHinhLogo() {
		return hinhLogo;
	}

	public void setHinhLogo(String hinhLogo) {
		this.hinhLogo = hinhLogo;
	}

	public String getMoTa() {
		return moTa;
	}

	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}

	public double getHocPhi() {
		return hocPhi;
	}

	public void setHocPhi(double hocPhi) {
		this.hocPhi = hocPhi;
	}

	public int getThoiLuong() {
		return thoiLuong;
	}

	public void setThoiLuong(int thoiLuong) {
		this.thoiLuong = thoiLuong;
	}
	
}
