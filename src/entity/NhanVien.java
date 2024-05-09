package entity;

public class NhanVien {
	private String maNV, hoTen, pass;
	private boolean vaiTro;
	
	public NhanVien() {
		super();
	}

	public NhanVien(String maNV, String hoTen, String pass, boolean vaiTro) {
		super();
		this.maNV = maNV;
		this.hoTen = hoTen;
		this.pass = pass;
		this.vaiTro = vaiTro;
	}

	public String getMaNV() {
		return maNV;
	}

	public void setMaNV(String maNV) {
		this.maNV = maNV;
	}

	public String getHoTen() {
		return hoTen;
	}

	public void setHoTen(String hoTen) {
		this.hoTen = hoTen;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public boolean isVaiTro() {
		return vaiTro;
	}

	public void setVaiTro(boolean vaiTro) {
		this.vaiTro = vaiTro;
	}
	
	@Override
	public String toString() {
		return this.hoTen;
	}

}
