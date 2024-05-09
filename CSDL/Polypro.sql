CREATE DATABASE Polypro;
USE Polypro;

CREATE TABLE NhanVien (
	MaNhanVien VARCHAR(5) PRIMARY KEY NOT NULL,
	MatKhau VARCHAR(50),
	HoTen NVARCHAR(50),
	VaiTro BIT NOT null
	);

CREATE TABLE NguoiHoc (
	MaNguoiHoc VARCHAR(5) PRIMARY KEY NOT NULL,
	Hoten NVARCHAR(50),
	NgaySinh DATE,
	GioiTinh NVARCHAR(3),
	SDT VARCHAR(10),
	Email VARCHAR(50),
	GhiChu NVARCHAR(255),
	MaNhanVien VARCHAR(5),
	NgayDK DATE,
	FOREIGN KEY (MaNhanVien) REFERENCES NhanVien(MaNhanVien)
	);

CREATE TABLE ChuyenDe (
	MaChuyenDe VARCHAR(5) NOT NULL PRIMARY KEY,
	TenChuyenDe NVARCHAR(50),
	HocPhi FLOAT,
	ThoiLuong INT,
	HinhLogo NVARCHAR(100),
	Motachuyende NVARCHAR(MAX)
	);

CREATE TABLE KhoaHoc (
	MaKhoaHoc INT IDENTITY(1,1) PRIMARY KEY NOT NULL,
	MaChuyenDe VARCHAR(5) NOT NULL,
	HocPhi FLOAT,
	ThoiLuong INT,
	NgayKhaiGiang DATE,
	GhiChu NVARCHAR(255),
	MaNhanVien VARCHAR(5),
	NgayTao DATE,
	FOREIGN KEY (MaChuyenDe) REFERENCES ChuyenDe(MaChuyenDe),
	FOREIGN KEY (MaNhanVien) REFERENCES NhanVien(MaNhanVien)
	);

	CREATE TABLE HocVien (
	MaHocVien INT IDENTITY(1,1) PRIMARY KEY NOT NULL,
	MaKhoaHoc INT NOT NULL,
	MaNguoiHoc VARCHAR(5) NOT NULL,
	DiemTB FLOAT,
	FOREIGN KEY (MaKhoaHoc) REFERENCES KhoaHoc(MaKhoaHoc),
    FOREIGN KEY (MaNguoiHoc) REFERENCES NguoiHoc(MaNguoiHoc)
	);

	-- Không cho phép xóa nhân viên khi đã nhập khóa học:

	-- Không cho phép xóa nhân viên khi đã nhập người học:

-- Thủ tục lưu trữ SP
-- Điểm
GO
	CREATE PROC sp_BangDiem (@MaKhoaHoc INT)
	AS BEGIN 
		SELECT 
			nh.MaNguoiHoc,
			nh.HoTen,
			hv.DiemTB
			FROM HocVien hv
				join NguoiHoc nh ON nh.MaNguoiHoc = hv.MaNguoiHoc
			WHERE hv.MaKhoaHoc = @MaKhoaHoc
			ORDER BY hv.DiemTB DESC
	END

-- Điểm chuyên đề
GO
	CREATE PROC sp_DiemChuyenDe
	AS BEGIN
	SELECT 
		TenChuyenDe ChuyenDe,
		COUNT(MaHocVien) SoHV,
		MIN(DiemTB) ThapNhat,
		MAX(DiemTB) CaoNhat,
		AVG(DiemTB) TrungBinh
	FROM KhoaHoc kh
		JOIN HocVien hv ON kh.MaKhoaHoc=hv.MaKhoaHoc
		JOIN ChuyenDe cd ON cd.MaChuyenDe=kh.MaChuyenDe
		GROUP BY TenChuyenDe
	END

	EXEC sp_DiemChuyenDe

-- Tổng người học
GO
	CREATE PROC sp_LuongNguoiHoc
	AS BEGIN
	SELECT
		YEAR (NgayDK) Nam,
		COUNT(*) SoLuong,
		MIN (NgayDK) DauTien,
		MAX(NgayDK) CuoiCung
	FROM NguoiHoc
	GROUP BY YEAR(NgayDK)
	END

-- Doanh thu
GO
	CREATE PROC sp_DoanhThu(@Year INT)
AS BEGIN
	SELECT 
		TenChuyenDe ChuyenDe,
		COUNT (DISTINCT kh.MaKhoaHoc) SoKhoaHoc,
		COUNT (hv.MaHocVien) SoHocVien,
		SUM(kh.HocPhi) DoanhThu,
		MIN(kh.HocPhi) ThapNhat,
		MAX(kh.HocPhi) CaoNhat,
		AVG(kh.HocPhi) TB
	FROM KhoaHoc kh
		JOIN HocVien hv ON kh.MaKhoaHoc =hv.MaKhoaHoc
		JOIN ChuyenDe cd ON cd.MaChuyenDe=kh.MaChuyenDe
	WHERE YEAR (NgayKhaiGiang) = @Year
	GROUP BY TenChuyenDe
	END
	
	INSERT INTO NhanVien(MaNhanVien, MatKhau, HoTen, VaiTro) VALUES('HNHT1','123', N'Huỳnh Ngọc Hoài Thương','true');
	UPDATE NhanVien SET MatKhau = '111', HoTen = N'Lý Trạch Ngôn', VaiTro = 'true' WHERE MaNhanVien = 'LTN2'
	
	INSERT INTO ChuyenDe (MaChuyenDe, TenChuyenDe, HocPhi, ThoiLuong, HinhLogo, Motachuyende)
	VALUES ('JA001', N'Lập trình Java', 1000000, 30, 'logo_poly.png', N'Java từ Zero thành Hero');

	INSERT INTO KhoaHoc (MaKhoaHoc, MaChuyenDe, HocPhi, ThoiLuong, NgayKhaiGiang, GhiChu, MaNhanVien, NgayTao)
	VALUES (1, 'JA001', 1200000, 30, '2023-08-15', N'Ghi chú khoá học tháng 8', 'HNHT1', '2023-04-01');
	
	INSERT INTO NguoiHoc (MaNguoiHoc, Hoten, NgaySinh, GioiTinh, SDT, Email, GhiChu, MaNhanVien, NgayDK)
	VALUES ('NH001', N'Bạch Khởi', '2004-07-29', 'Nam', '0987654321', 'bq@gmail.com', N'Học viên xuất sắc', 'HNHT1', '2023-08-01');

	INSERT INTO HocVien (MaKhoaHoc, MaNguoiHoc, DiemTB)
	VALUES (1, 'NH001', 9.9);