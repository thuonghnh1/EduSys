package ui;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JToolBar;
import javax.swing.border.EtchedBorder;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.Font;

import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;
import java.awt.Color;
import java.awt.Desktop;

import javax.swing.border.LineBorder;

import utils.Auth;
import utils.MsgBox;
import utils.XImage;

import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.SwingConstants;

public class EduSysJFrame extends JFrame {

	private JPanel contentPane;
	private JLabel lblDongHo;
	private boolean helloDialogClosed = false;
	private boolean loginCompleted = false;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EduSysJFrame frame = new EduSysJFrame();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
					frame.OpenWel();
					frame.init();
					frame.startDongHo();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void init() {
		setSize(1000, 600);
		setIconImage(XImage.getAppIcon());
		setTitle("HỆ THỐNG QUẢN LÝ ĐÀO TẠO EDUSYS");
	}

	void OpenWel() {
		HelloJDialog hello = new HelloJDialog();
		hello.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		hello.setVisible(true);
		hello.setLocationRelativeTo(this);
		hello.pubInit();
		hello.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				helloDialogClosed = true;
				OpenLogin();
			}
		});
	}

	void OpenLogin() {
		LoginJDialog login = new LoginJDialog();
		login.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		login.setVisible(true);
		login.setLocationRelativeTo(this);
		login.pubInit();
		login.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				loginCompleted = true;
				init();
			}
		});
	}

	void startDongHo() {
		new javax.swing.Timer(1000, new ActionListener() {
			SimpleDateFormat Format = new SimpleDateFormat("hh:mm:ss a");

			@Override
			public void actionPerformed(ActionEvent e) {
				lblDongHo.setText(Format.format(new Date()));
			}
		}).start();
	}

	void openDoiMatKhau() {
		if (Auth.isLogin()) {
			new DoiPassJDialog(this, true).setVisible(true);

		} else {
			MsgBox.alert(this, "Vui lòng đăng nhập!!");
		}
	}

	void openDangXuat() {
		Auth.clear();
		OpenLogin();
	}

	void openKetThuc() {
		if (MsgBox.confirm(this, "Bạn muốn kết thúc làm việc??")) {
			System.exit(0);
		}
	}

	void openChuyenDe() {
		if (Auth.isLogin()) {
			ChuyenDeJDialog chuyenDe = new ChuyenDeJDialog();
			chuyenDe.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			chuyenDe.setVisible(true);
			chuyenDe.setLocationRelativeTo(this);
			chuyenDe.pubInit();
		} else {
			MsgBox.alert(this, "Vui lòng đăng nhập!!");
		}
	}

	void openKhoaHoc() {
		if (Auth.isLogin()) {
			KhoaHocJDialog khoaHoc = new KhoaHocJDialog();
			khoaHoc.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			khoaHoc.setVisible(true);
			khoaHoc.setLocationRelativeTo(this);
			khoaHoc.pubInit();
		} else {
			MsgBox.alert(this, "Vui lòng đăng nhập!!");
		}
	}

	void openNguoiHoc() {
		if (Auth.isLogin()) {
			NguoiHocJDialog nguoiHoc = new NguoiHocJDialog();
			nguoiHoc.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			nguoiHoc.setVisible(true);
			nguoiHoc.setLocationRelativeTo(this);
			nguoiHoc.pubInit();
		} else {
			MsgBox.alert(this, "Vui lòng đăng nhập!!");
		}
	}

	void openHocVien() {
		if (Auth.isLogin()) {
			HocVienJDialog hocVienDialog = new HocVienJDialog();
			hocVienDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			hocVienDialog.setVisible(true);
			hocVienDialog.setLocationRelativeTo(this);
			hocVienDialog.pubInit();
		} else {
			MsgBox.alert(this, "Vui lòng đăng nhập!!");
		}
	}

	void openNhanVien() {
		if (Auth.isLogin()) {
			NhanVienJDialog nv = new NhanVienJDialog();
			nv.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			nv.setVisible(true);
			nv.setLocationRelativeTo(this);
			nv.pubInit();
		} else {
			MsgBox.alert(this, "Vui lòng đăng nhập!!");
		}
	}

	void openHuongDan() {
		try {
			Desktop.getDesktop().browse(new File("help/index.html").toURI());
		} catch (IOException ex) {
			MsgBox.alert(this, "Không tìm thấy file hướng dẫn!");
		}
	}

	void openGioiThieu() {
		if (Auth.isLogin()) {
			new GioiThieuJDialog(this, true).setVisible(true);
		} else {
			MsgBox.alert(this, "Vui lòng đăng nhập!!");
		}
	}

	void openThongKe(int index) {
		if (Auth.isLogin()) {
			if (index == 3 && !Auth.isManager()) {
				MsgBox.alert(this, "Bạn không có quyền để xem doanh thu");
			} else {
				ThongKeJDialog thongKe = new ThongKeJDialog();
				thongKe.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				thongKe.selectTab(index);
				thongKe.setVisible(true);
				thongKe.setLocationRelativeTo(this);
				thongKe.pubInit();
			}
		} else {
			MsgBox.alert(this, "Vui lòng đăng nhập!!");
		}
	}

	public EduSysJFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1019, 605);

		JMenuBar mnuBar = new JMenuBar();
		setJMenuBar(mnuBar);

		JMenu mnuHeThong = new JMenu("Hệ Thống");
		mnuHeThong.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		mnuBar.add(mnuHeThong);

		JMenuItem mniDoiMatKhau = new JMenuItem("Đổi Mật Khẩu");
		mniDoiMatKhau.setMnemonic(KeyEvent.VK_0);// alt + 0
		mniDoiMatKhau.setIcon(new ImageIcon("C:\\Users\\thuon\\eclipse-workspace\\EduSys\\src\\icon\\Refresh.png"));
		mniDoiMatKhau.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openDoiMatKhau();
			}
		});
		mniDoiMatKhau.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		mnuHeThong.add(mniDoiMatKhau);

		JSeparator sep1_1 = new JSeparator();
		mnuHeThong.add(sep1_1);

		JMenuItem mniDangXuat = new JMenuItem("Đăng Xuất");
		mniDangXuat.setMnemonic(KeyEvent.VK_1);
		mniDangXuat.setIcon(new ImageIcon("C:\\Users\\thuon\\eclipse-workspace\\EduSys\\src\\icon\\Log out.png"));
		mniDangXuat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openDangXuat();
			}
		});
		mniDangXuat.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		mnuHeThong.add(mniDangXuat);

		JSeparator sep1_2 = new JSeparator();
		mnuHeThong.add(sep1_2);

		JMenuItem mniKetThuc = new JMenuItem("Kết Thúc");
		mniKetThuc.setMnemonic(KeyEvent.VK_2);
		mniKetThuc.setIcon(new ImageIcon("C:\\Users\\thuon\\eclipse-workspace\\EduSys\\src\\icon\\Stop.png"));
		mniKetThuc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openKetThuc();
			}
		});
		mniKetThuc.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		mnuHeThong.add(mniKetThuc);

		JMenu mnuQuanLy = new JMenu("Quản Lý");
		mnuQuanLy.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		mnuBar.add(mnuQuanLy);

		JMenuItem mniChuyenDe = new JMenuItem("Chuyên Đề ");
		mniChuyenDe.setMnemonic(KeyEvent.VK_F1);
		mniChuyenDe.setIcon(new ImageIcon("C:\\Users\\thuon\\eclipse-workspace\\EduSys\\src\\icon\\List.png"));
		mniChuyenDe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openChuyenDe();
			}
		});
		mniChuyenDe.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		mnuQuanLy.add(mniChuyenDe);

		JSeparator sep2_1 = new JSeparator();
		mnuQuanLy.add(sep2_1);

		JMenuItem mniKhoaHoc = new JMenuItem("Khóa Học");
		mniKhoaHoc.setMnemonic(KeyEvent.VK_F2);
		mniKhoaHoc.setIcon(new ImageIcon("C:\\Users\\thuon\\eclipse-workspace\\EduSys\\src\\icon\\Certificate.png"));
		mniKhoaHoc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openKhoaHoc();
			}
		});
		mniKhoaHoc.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		mnuQuanLy.add(mniKhoaHoc);

		JSeparator sep2_2 = new JSeparator();
		mnuQuanLy.add(sep2_2);

		JMenuItem mniNguoiHoc = new JMenuItem("Người Học");
		mniNguoiHoc.setMnemonic(KeyEvent.VK_F3);
		mniNguoiHoc.setIcon(new ImageIcon("C:\\Users\\thuon\\eclipse-workspace\\EduSys\\src\\icon\\Conference.png"));
		mniNguoiHoc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openNguoiHoc();
			}
		});
		mniNguoiHoc.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		mnuQuanLy.add(mniNguoiHoc);

		JSeparator sep2_3 = new JSeparator();
		mnuQuanLy.add(sep2_3);

		JMenuItem mniHocVien = new JMenuItem("Học Viên");
		mniHocVien.setMnemonic(KeyEvent.VK_F4);
		mniHocVien.setIcon(new ImageIcon("C:\\Users\\thuon\\eclipse-workspace\\EduSys\\src\\icon\\User.png"));
		mniHocVien.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openHocVien();
			}
		});
		mniHocVien.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		mnuQuanLy.add(mniHocVien);

		JSeparator sep2_4 = new JSeparator();
		mnuQuanLy.add(sep2_4);

		JMenuItem mniNhanVien = new JMenuItem("Nhân Viên");
		mniNhanVien.setMnemonic(KeyEvent.VK_F5);
		mniNhanVien.setIcon(new ImageIcon("C:\\Users\\thuon\\eclipse-workspace\\EduSys\\src\\icon\\User group.png"));
		mniNhanVien.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openNhanVien();
			}
		});
		mniNhanVien.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		mnuQuanLy.add(mniNhanVien);

		JMenu mnuThongKe = new JMenu("Thống Kê");
		mnuThongKe.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		mnuBar.add(mnuThongKe);

		JMenuItem mniBangDiem = new JMenuItem("Bảng Điểm");
		mniBangDiem.setMnemonic(KeyEvent.VK_F6);
		mniBangDiem.setIcon(new ImageIcon("C:\\Users\\thuon\\eclipse-workspace\\EduSys\\src\\icon\\Numbered list.png"));
		mniBangDiem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openThongKe(0);
			}
		});
		mniBangDiem.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		mnuThongKe.add(mniBangDiem);

		JSeparator sep3_1 = new JSeparator();
		mnuThongKe.add(sep3_1);

		JMenuItem mniLuongNguoiHoc = new JMenuItem("Lượng Người Học");
		mniLuongNguoiHoc.setMnemonic(KeyEvent.VK_F7);
		mniLuongNguoiHoc.setIcon(new ImageIcon("C:\\Users\\thuon\\eclipse-workspace\\EduSys\\src\\icon\\Clien list.png"));
		mniLuongNguoiHoc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openThongKe(1);
			}
		});
		mniLuongNguoiHoc.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		mnuThongKe.add(mniLuongNguoiHoc);

		JSeparator sep3_2 = new JSeparator();
		mnuThongKe.add(sep3_2);

		JMenuItem mniDiemChuyenDe = new JMenuItem("Điểm Chuyên Đề");
		mniDiemChuyenDe.setMnemonic(KeyEvent.VK_F8);
		mniDiemChuyenDe.setIcon(new ImageIcon("C:\\Users\\thuon\\eclipse-workspace\\EduSys\\src\\icon\\Bar chart.png"));
		mniDiemChuyenDe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openThongKe(2);
			}
		});
		mniDiemChuyenDe.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		mnuThongKe.add(mniDiemChuyenDe);

		JSeparator sep3_3 = new JSeparator();
		mnuThongKe.add(sep3_3);

		JMenuItem mniDoanhThu = new JMenuItem("Doanh Thu");
		mniDoanhThu.setMnemonic(KeyEvent.VK_F9);
		mniDoanhThu.setIcon(new ImageIcon("C:\\Users\\thuon\\eclipse-workspace\\EduSys\\src\\icon\\Dollar.png"));
		mniDoanhThu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openThongKe(3);
			}
		});
		mniDoanhThu.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		mnuThongKe.add(mniDoanhThu);

		JMenu mnuHelp = new JMenu("Trợ Giúp");
		mnuHelp.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		mnuBar.add(mnuHelp);

		JMenuItem mniHuongDan = new JMenuItem("Hướng Dẫn Sử Dụng");
		mniHuongDan.setMnemonic(KeyEvent.VK_F10);
		mniHuongDan.setIcon(new ImageIcon("C:\\Users\\thuon\\eclipse-workspace\\EduSys\\src\\icon\\Help.png"));
		mniHuongDan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openHuongDan();
			}
		});
		mniHuongDan.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		mnuHelp.add(mniHuongDan);

		JSeparator sep4_1 = new JSeparator();
		mnuHelp.add(sep4_1);

		JMenuItem mniGioiThieu = new JMenuItem("Giới Thiệu Sản Phẩm");
		mniGioiThieu.setMnemonic(KeyEvent.VK_F11);
		mniGioiThieu.setIcon(new ImageIcon("C:\\Users\\thuon\\eclipse-workspace\\EduSys\\src\\icon\\Favourites.png"));
		mniGioiThieu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openGioiThieu();
			}
		});
		mniGioiThieu.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		mnuHelp.add(mniGioiThieu);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JToolBar toolBar = new JToolBar();
		toolBar.setBounds(0, 0, 1005, 50);
		toolBar.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		contentPane.add(toolBar);

		JButton btnDangXuat = new JButton("Đăng Xuất ");
		btnDangXuat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openDangXuat();
			}
		});
		btnDangXuat.setIcon(new ImageIcon(EduSysJFrame.class.getResource("/icon/Log out.png")));
		btnDangXuat.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		toolBar.add(btnDangXuat);

		JButton btnKetThuc = new JButton("Kết Thúc");
		btnKetThuc.setIcon(new ImageIcon(EduSysJFrame.class.getResource("/icon/Stop.png")));
		btnKetThuc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openKetThuc();
			}
		});
		btnKetThuc.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		toolBar.add(btnKetThuc);

		JButton btnChuyenDe = new JButton("Chuyên Đề ");
		btnChuyenDe.setIcon(new ImageIcon(EduSysJFrame.class.getResource("/icon/Lists.png")));
		btnChuyenDe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openChuyenDe();
			}
		});

		JSeparator sep = new JSeparator();
		sep.setForeground(new Color(0, 0, 0));
		sep.setMaximumSize(new Dimension(1, 30));
		sep.setPreferredSize(new Dimension(7, 3));
		sep.setBorder(new LineBorder(new Color(0, 0, 0)));
		toolBar.add(sep);
		btnChuyenDe.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		toolBar.add(btnChuyenDe);

		JButton btnNguoiHoc = new JButton("Người Học");
		btnNguoiHoc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openNguoiHoc();
			}
		});
		btnNguoiHoc.setIcon(new ImageIcon(EduSysJFrame.class.getResource("/icon/Conference.png")));
		btnNguoiHoc.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		toolBar.add(btnNguoiHoc);

		JButton btnKhoaHoc = new JButton("Khoá Học");
		btnKhoaHoc.setIcon(new ImageIcon(EduSysJFrame.class.getResource("/icon/Certificate.png")));
		btnKhoaHoc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openKhoaHoc();
			}
		});
		btnKhoaHoc.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		toolBar.add(btnKhoaHoc);

		JButton btnHocVien = new JButton("Học Viên");
		btnHocVien.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openHocVien();
			}
		});
		btnHocVien.setIcon(new ImageIcon("C:\\Users\\thuon\\eclipse-workspace\\EduSys\\src\\icon\\User.png"));
		btnHocVien.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		toolBar.add(btnHocVien);

		JSeparator sep1 = new JSeparator();
		sep1.setMaximumSize(new Dimension(1, 30));
		sep1.setPreferredSize(new Dimension(7, 3));
		sep1.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		toolBar.add(sep1);

		JButton btnHuongDan = new JButton("Hướng Dẫn ");
		btnHuongDan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openHuongDan();
			}
		});
		btnHuongDan.setIcon(new ImageIcon(EduSysJFrame.class.getResource("/icon/Globe.png")));
		btnHuongDan.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		toolBar.add(btnHuongDan);

		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(0, 487, 1026, 59);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblTrangThai = new JLabel("HỆ QUẢN LÝ ĐÀO TẠO");
		lblTrangThai.setIcon(new ImageIcon("C:\\Users\\thuon\\eclipse-workspace\\EduSys\\src\\icon\\i-24.png"));
		lblTrangThai.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		lblTrangThai.setBounds(10, 10, 174, 30);
		panel.add(lblTrangThai);

		lblDongHo = new JLabel("12:12:12:AM");
		lblDongHo.setIcon(new ImageIcon(EduSysJFrame.class.getResource("/icon/Alarm.png")));
		lblDongHo.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		lblDongHo.setBounds(821, 10, 174, 30);
		panel.add(lblDongHo);
		
		JLabel lblLogoMain = new JLabel("");
		lblLogoMain.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogoMain.setIcon(new ImageIcon("C:\\Users\\thuon\\eclipse-workspace\\EduSys\\src\\icon\\poly.png"));
		lblLogoMain.setBounds(10, 60, 995, 417);
		contentPane.add(lblLogoMain);
	}
}
