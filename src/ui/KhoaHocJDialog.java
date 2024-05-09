package ui;

import java.awt.BorderLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

import dao.ChuyenDeDAO;
import dao.KhoaHocDAO;
import entity.ChuyenDe;
import entity.KhoaHoc;
import entity.NguoiHoc;
import utils.Auth;
import utils.MsgBox;
import utils.XDate;
import utils.XImage;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class KhoaHocJDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtNguoiTao, txtThoiLuong, txtNgayKhaiGiang, txtHocPhi, txtNgayTao;
	private JTable tblKhoaHoc;
	private JComboBox cboChuyenDe;
	private JTextArea taraGhiChu;
	private JButton btnThem, btnSua, btnXoa, btnMoi, btnFrist, btnPre, btnNext, btnLast;
	private JTabbedPane tab;
	ChuyenDeDAO cdDao = new ChuyenDeDAO();
	KhoaHocDAO khDao = new KhoaHocDAO();
	private int row = -1;
	private String ngay = "\\d{4}/\\d{2}/\\d{2}";
	private String number = "\\d*";

	public static void main(String[] args) {
		try {
			KhoaHocJDialog dialog = new KhoaHocJDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			dialog.setLocationRelativeTo(null);
			dialog.init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void pubInit() {
		init();
	}

	private void init() {
		setIconImage(XImage.getAppIcon());
		setTitle("QUẢN LÝ KHÓA HỌC");
		fillCboChuyenDe();
		fillTable();
		fillCboChuyenDe();
	}

	private void fillTable() {
		DefaultTableModel model = (DefaultTableModel) tblKhoaHoc.getModel();
		model.setRowCount(0);
		try {
//			ChuyenDe cd = (ChuyenDe) cboChuyenDe.getSelectedItem();
//			List<KhoaHoc> list = khDao.selectKhoaHocByChuyenDe(cd.getMaCD());
			List<KhoaHoc> list = khDao.selectAll();

//			System.out.println("fillTable() is called f "+ cd.getMaCD()); 
//			System.out.println("fillTable() is called f "+ khDao.selectKhoaHocByChuyenDe(cd.getMaCD())); 
			for (KhoaHoc khoaHoc : list) {
				Object[] row = { khoaHoc.getMaKhoaHoc(), khoaHoc.getHocPhi(), khoaHoc.getThoiLuong(),
						XDate.toString(khoaHoc.getNgayKhaiGiang(), "yyyy/MM/dd"),
//						XDate.toString(khoaHoc.getNgayKhaiGiang()),

						khoaHoc.getMaNhanVien(), XDate.toString(khoaHoc.getNgayTao(), "yyyy/MM/dd") };
//				XDate.toString(khoaHoc.getNgayTao()) };

				model.addRow(row);
			}
		} catch (Exception e) {
			MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
		}
	}

	private void fillCboChuyenDe() {
		DefaultComboBoxModel model = (DefaultComboBoxModel) cboChuyenDe.getModel();
		model.removeAllElements();

		List<ChuyenDe> list = cdDao.selectAll();
		for (ChuyenDe cd : list) {
			model.addElement(cd);
		}
		cboChuyenDe.setSelectedIndex(0);
	}

	private void edit() {
//		Integer makh = (Integer) tblKhoaHoc.getValueAt(row, 0);
//		KhoaHoc kh = khDao.selectById(makh);
//		setForm(kh);
//		tab.setSelectedIndex(0);
//		updateStatus(); 
		try {
			Integer maKH = (Integer) tblKhoaHoc.getValueAt(row, 0);
			KhoaHoc model = khDao.selectById(maKH);
			if (model != null) {
				setForm(model);
				updateStatus();
				tab.setSelectedIndex(0);
			}
		} catch (Exception e) {
			MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
			throw new RuntimeException(e);
		}
	}

	private void updateStatus() {
		boolean edit = (this.row >= 0);
		boolean first = (this.row == 0);
		boolean last = (this.row == tblKhoaHoc.getRowCount() - 1);

		btnThem.setEnabled(!edit);
		btnSua.setEnabled(edit);
		btnXoa.setEnabled(edit);

		btnFrist.setEnabled(edit && !first);
		btnPre.setEnabled(edit && !first);
		btnNext.setEnabled(edit && !last);
		btnLast.setEnabled(edit && !last);
	}

	private KhoaHoc getForm() {
		KhoaHoc kh = new KhoaHoc();
		ChuyenDe cd = (ChuyenDe) cboChuyenDe.getSelectedItem();
		kh.setMaChuyenDe(cd.getMaCD());
		kh.setNgayKhaiGiang(XDate.toDate(txtNgayKhaiGiang.getText(), "yyyy/MM/dd"));
//		kh.setNgayKhaiGiang(XDate.toDate(txtNgayKhaiGiang.getText()));

		kh.setHocPhi(Double.valueOf(txtHocPhi.getText()));
		kh.setThoiLuong(Integer.valueOf(txtThoiLuong.getText()));
		kh.setMaNhanVien(Auth.user.getMaNV());
		kh.setNgayTao(XDate.now());
		kh.setGhiChu(taraGhiChu.getText());
		return kh;
	}

	private void setForm(KhoaHoc kh) {
		cboChuyenDe.setToolTipText(String.valueOf(kh.getMaKhoaHoc()));
//      cboChuyenDe.setSelectedItem(cdDao.selectById((String) tblKhoaHoc.getValueAt(row, 0)));

		txtNgayKhaiGiang.setText(XDate.toString(kh.getNgayKhaiGiang(), "yyyy/MM/dd"));
		txtHocPhi.setText(String.valueOf(kh.getHocPhi()));
		txtThoiLuong.setText(String.valueOf(kh.getThoiLuong()));
		txtNguoiTao.setText(kh.getMaNhanVien());
		txtNgayTao.setText(String.valueOf(kh.getNgayTao()));
		taraGhiChu.setText(kh.getGhiChu());
	}

	private void clearForm() {
		cboChuyenDe.setSelectedIndex(0); // Đặt lại chuyên đề về phần tử đầu tiên
		txtHocPhi.setText("");
		txtThoiLuong.setText("");
		txtNgayKhaiGiang.setText("");
		txtNgayTao.setText("");
		taraGhiChu.setText("");
		txtNguoiTao.setText(Auth.user.getMaNV());
		row = -1;
		updateStatus();
		tab.setSelectedIndex(0);
	}

	private void first() {
		row = 0;
		edit();
	}

	private void next() {
		if (row < tblKhoaHoc.getRowCount() - 1) {
			row++;
			edit();
		}
	}

	private void prev() {
		if (row > 0) {
			row--;
			edit();
		}
	}

	private void last() {
		row = tblKhoaHoc.getRowCount() - 1;
		edit();
	}

	private void insert() {
		if (validation()) {
			KhoaHoc kh = getForm();
			kh.setNgayTao(XDate.now());
			try {
				khDao.insert(kh);
				fillTable();
				clearForm();
				MsgBox.alert(this, "Thêm mới thành công!");
				tab.setSelectedIndex(1);
			} catch (Exception e) {
				e.printStackTrace();
				MsgBox.alert(this, "Thêm mới thất bại!");
			}
		}
	}

	private void update() {
		KhoaHoc kh = khDao.selectById((Integer) tblKhoaHoc.getValueAt(row, 0));
		if (validation()) {
			try {
				kh.setNgayKhaiGiang(XDate.toDate(txtNgayKhaiGiang.getText(), "yyyy/MM/dd"));
				kh.setGhiChu(taraGhiChu.getText());
				kh.setHocPhi(Double.parseDouble(txtHocPhi.getText()));
	            kh.setThoiLuong(Integer.parseInt(txtThoiLuong.getText()));
				khDao.update(kh);
				fillTable();
				System.out.println("makh: " + kh.getMaKhoaHoc() + kh.getMaChuyenDe() + kh.getMaNhanVien() + "Ngay kg:"
						+ XDate.toString(kh.getNgayKhaiGiang(), "yyyy-MM-dd") + "ghi chu:" + kh.getGhiChu());

				MsgBox.alert(this, "Cập nhật thành công!");
			} catch (Exception e) {
				MsgBox.alert(this, "Cập nhật thất bại!");
			}
		}
	}

	private void delete() {
		if (!Auth.isManager()) {
			MsgBox.alert(this, "Bạn không có quyền để xóa!");
		} else {
			MsgBox.confirm(this, "Bạn thực sự muốn xóa khóa học này!");
			Integer makh = Integer.valueOf(cboChuyenDe.getToolTipText());
			try {
				khDao.delete(makh);
				fillTable();
				clearForm();
				MsgBox.alert(this, "Xóa thành công!");
			} catch (Exception e) {
				MsgBox.alert(this, "Xóa thất bại!");
			}
		}
	}

	public boolean validation() {
		if (txtHocPhi.getText().equals("")) {
			MsgBox.alert(this, "Vui lòng ghi học phí!!!");
			return false;
		}
		Matcher reHocPhi = Pattern.compile(number).matcher(txtHocPhi.getText());
		if (!reHocPhi.matches()) {
			MsgBox.alert(this, "Học phí phải là số");
			return false;
		}
		if (txtNgayKhaiGiang.getText().equals("")) {
			MsgBox.alert(this, "Vui lòng ghi ngày khai giảng!!!");
			return false;
		}
		Matcher reNgayKG = Pattern.compile(ngay).matcher(txtNgayTao.getText());
		if (reNgayKG.matches()) {
			MsgBox.alert(this, "Ngày khai giảng không hợp lệ. Định dạng phải là yyyy/MM/dd");
			return false;
		}
		if (txtThoiLuong.getText().equals("")) {
			MsgBox.alert(this, "Vui lòng ghi thời lượng!!!");
			return false;
		}
		Matcher reThoiLuong = Pattern.compile(number).matcher(txtThoiLuong.getText());
		if (!reThoiLuong.matches()) {
			MsgBox.alert(this, "Thời lượng phải là số");
			return false;
		}
		if (txtNgayTao.getText().equals("")) {
			MsgBox.alert(this, "Vui lòng ghi ngày tạo!!!");
			return false;
		}
		Matcher reNgay = Pattern.compile(ngay).matcher(txtNgayTao.getText());
		if (reNgay.matches()) {
			MsgBox.alert(this, "Ngày tạo không hợp lệ. Định dạng phải là yyyy/MM/dd");
			return false;
		}
		if (txtNguoiTao.getText().equals("")) {
			MsgBox.alert(this, "Vui lòng ghi người tạo!!!");
			return false;
		}

		return true;
	}

	public KhoaHocJDialog() {
		setBounds(100, 100, 905, 627);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblQuanLyKhoaHoc = new JLabel("QUẢN LÝ KHÓA HỌC");
		lblQuanLyKhoaHoc.setForeground(new Color(0, 0, 128));
		lblQuanLyKhoaHoc.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblQuanLyKhoaHoc.setBounds(10, 0, 174, 35);
		contentPanel.add(lblQuanLyKhoaHoc);

		tab = new JTabbedPane(JTabbedPane.TOP);
		tab.setBounds(0, 33, 890, 556);
		contentPanel.add(tab);

		JPanel pnCapNhat = new JPanel();
		pnCapNhat.setLayout(null);
		tab.addTab("CẬP NHẬT", null, pnCapNhat, null);

		JLabel lblNguoiTao = new JLabel("Người tạo");
		lblNguoiTao.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		lblNguoiTao.setBounds(12, 160, 79, 32);
		pnCapNhat.add(lblNguoiTao);

		JLabel lblChuyenDe = new JLabel("Chuyên đề");
		lblChuyenDe.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		lblChuyenDe.setBounds(12, 10, 98, 26);
		pnCapNhat.add(lblChuyenDe);

		txtNguoiTao = new JTextField();
		txtNguoiTao.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		txtNguoiTao.setColumns(10);
		txtNguoiTao.setBounds(12, 202, 394, 35);
		pnCapNhat.add(txtNguoiTao);

		JLabel lblNgayKhaiGiang = new JLabel("Ngày khai giảng");
		lblNgayKhaiGiang.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		lblNgayKhaiGiang.setBounds(472, 10, 98, 26);
		pnCapNhat.add(lblNgayKhaiGiang);

		JLabel lblThoiLuong = new JLabel("Thời lượng (giờ)");
		lblThoiLuong.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		lblThoiLuong.setBounds(472, 78, 98, 26);
		pnCapNhat.add(lblThoiLuong);

		txtThoiLuong = new JTextField();
		txtThoiLuong.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		txtThoiLuong.setColumns(10);
		txtThoiLuong.setBounds(474, 114, 375, 35);
		pnCapNhat.add(txtThoiLuong);

		JLabel lblHocPhi = new JLabel("Học phí");
		lblHocPhi.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		lblHocPhi.setBounds(12, 88, 98, 16);
		pnCapNhat.add(lblHocPhi);

		txtNgayKhaiGiang = new JTextField();
		txtNgayKhaiGiang.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		txtNgayKhaiGiang.setColumns(10);
		txtNgayKhaiGiang.setBounds(474, 46, 375, 35);
		pnCapNhat.add(txtNgayKhaiGiang);

		txtHocPhi = new JTextField();
		txtHocPhi.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		txtHocPhi.setColumns(10);
		txtHocPhi.setBounds(12, 115, 394, 35);
		pnCapNhat.add(txtHocPhi);

		JLabel lblGhiChu = new JLabel("Ghi Chú");
		lblGhiChu.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		lblGhiChu.setBounds(12, 247, 98, 26);
		pnCapNhat.add(lblGhiChu);

		JPanel pnGhiChu = new JPanel();
		pnGhiChu.setLayout(null);
		pnGhiChu.setBounds(12, 283, 851, 197);
		pnCapNhat.add(pnGhiChu);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 831, 177);
		pnGhiChu.add(scrollPane);

		taraGhiChu = new JTextArea();
		taraGhiChu.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		scrollPane.setViewportView(taraGhiChu);

		JPanel pnPhimTat = new JPanel();
		pnPhimTat.setBounds(12, 490, 856, 43);
		pnCapNhat.add(pnPhimTat);
		pnPhimTat.setLayout(null);

		btnThem = new JButton("Thêm");
		btnThem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insert();
			}
		});
		btnThem.setBounds(10, 0, 66, 23);
		pnPhimTat.add(btnThem);
		btnThem.setFont(new Font("Times New Roman", Font.PLAIN, 13));

		btnSua = new JButton("Sửa");
		btnSua.setBounds(86, 0, 60, 23);
		pnPhimTat.add(btnSua);
		btnSua.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				update();
			}
		});
		btnSua.setFont(new Font("Times New Roman", Font.PLAIN, 13));

		btnMoi = new JButton("Mới");
		btnMoi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearForm();
			}
		});
		btnMoi.setBounds(223, 0, 60, 23);
		pnPhimTat.add(btnMoi);
		btnMoi.setFont(new Font("Times New Roman", Font.PLAIN, 13));

		btnXoa = new JButton("Xóa");
		btnXoa.setBounds(153, 0, 60, 23);
		pnPhimTat.add(btnXoa);
		btnXoa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				delete();
			}
		});
		btnXoa.setFont(new Font("Times New Roman", Font.PLAIN, 13));

		btnLast = new JButton(">|");
		btnLast.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				last();
			}
		});
		btnLast.setBounds(789, 0, 60, 23);
		pnPhimTat.add(btnLast);
		btnLast.setFont(new Font("Times New Roman", Font.BOLD, 13));

		btnNext = new JButton(">");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				next();
			}
		});
		btnNext.setBounds(719, 0, 60, 23);
		pnPhimTat.add(btnNext);
		btnNext.setFont(new Font("Times New Roman", Font.BOLD, 13));

		btnPre = new JButton("<");
		btnPre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				prev();
			}
		});
		btnPre.setBounds(649, 0, 60, 23);
		pnPhimTat.add(btnPre);
		btnPre.setFont(new Font("Times New Roman", Font.BOLD, 13));

		btnFrist = new JButton("|<");
		btnFrist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				first();
			}
		});
		btnFrist.setBounds(579, 0, 60, 23);
		pnPhimTat.add(btnFrist);
		btnFrist.setFont(new Font("Times New Roman", Font.BOLD, 13));

		cboChuyenDe = new JComboBox();
		cboChuyenDe.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		cboChuyenDe.setBounds(14, 46, 392, 35);
		pnCapNhat.add(cboChuyenDe);

		txtNgayTao = new JTextField();
		txtNgayTao.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		txtNgayTao.setColumns(10);
		txtNgayTao.setBounds(474, 202, 375, 35);
		pnCapNhat.add(txtNgayTao);

		JLabel lblNgayTao = new JLabel("Ngày tạo");
		lblNgayTao.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		lblNgayTao.setBounds(472, 160, 98, 32);
		pnCapNhat.add(lblNgayTao);

		JPanel pnDanhSach = new JPanel();
		pnDanhSach.setLayout(null);
		tab.addTab("DANH SÁCH", null, pnDanhSach, null);

		JPanel pnTbl = new JPanel();
		pnTbl.setLayout(null);
		pnTbl.setBounds(10, 0, 861, 519);
		pnDanhSach.add(pnTbl);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 10, 841, 499);
		pnTbl.add(scrollPane_1);

		tblKhoaHoc = new JTable();
		tblKhoaHoc.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getClickCount() == 2) {
					row = tblKhoaHoc.rowAtPoint(e.getPoint());
					edit();
				}
			}
		});
		scrollPane_1.setViewportView(tblKhoaHoc);
		tblKhoaHoc.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "M\u00C3 KH", "H\u1ECCC PH\u00CD",
				"TH\u1EDCI L\u01AF\u1EE2NG", "NG\u00C0Y KHAI GI\u1EA2NG", "M\u00C3 NV", "NG\u00C0Y T\u1EA0O" }) {
			boolean[] columnEditables = new boolean[] { false, false, false, false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tblKhoaHoc.getColumnModel().getColumn(1).setPreferredWidth(97);
		tblKhoaHoc.getColumnModel().getColumn(3).setPreferredWidth(105);
		tblKhoaHoc.getColumnModel().getColumn(4).setPreferredWidth(106);
		tblKhoaHoc.setFont(new Font("Times New Roman", Font.PLAIN, 13));
	}
}
