package ui;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;

import dao.NguoiHocDAO;
import entity.ChuyenDe;
import entity.NguoiHoc;
import utils.Auth;
import utils.MsgBox;
import utils.XDate;
import utils.XImage;

import javax.swing.border.BevelBorder;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.SwingConstants;

public class NguoiHocJDialog extends JDialog {
	private JTable tblNguoiHoc;
	private JTextField txtTim, txtMaNguoiHoc, txtHoVaTen, txtSDT, txtEmail, txtNgaySinh;
	private JTextArea taraGhiChu;
	private int row = 0;
	private JTabbedPane tab;
	NguoiHocDAO dao = new NguoiHocDAO();
	private final ButtonGroup btnGrGioiTinh = new ButtonGroup();
	private JRadioButton rdoNam, rdoNu;
	private JButton btnThem, btnSua, btnXoa, btnMoi, btnFrist, btnPre, btnNext, btnLast;
	private static final String checkTen = "^[a-zA-Z]+$";
	private static final String checkNgaySinh = "^\\d{4}/\\d{2}/\\d{2}$";
	private static final String checkMail = "^^\\w+@\\w+(\\.\\w+){1,2}$";
	private static final String checkSDT = "0[0-9]{9}";
	public static void main(String[] args) {
		try {
			NguoiHocJDialog dialog = new NguoiHocJDialog();
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
		setTitle("QUẢN LÝ NGƯỜI HỌC");
		fillTable();
	}

	private void fillTable() {
		DefaultTableModel model = (DefaultTableModel) tblNguoiHoc.getModel();
		model.setRowCount(0);
		try {
			String keyword = txtTim.getText();
			List<NguoiHoc> list = dao.selectByName(keyword);
			for (NguoiHoc nh : list) {
				Object[] row = { nh.getMaNguoiHoc(), nh.getHoTen(), nh.getGioiTinh(),
						XDate.toString(nh.getNgaySinh(), "yyyy/MM/dd"), nh.getSdt(), nh.getEmail(), nh.getMaNhanVien(),
						XDate.toString(nh.getNgayDK(), "yyyy/MM/dd") };
				model.addRow(row);
			}
		} catch (Exception e) {
			MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
		}
	}

	private void edit() {
		try {
			String maNV = (String) tblNguoiHoc.getValueAt(row, 0);
			NguoiHoc nh = dao.selectById(maNV);
			if (nh != null) {
				setForm(nh);
				updateStatus();
				tab.setSelectedIndex(0);
			}
		} catch (Exception e) {
			MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
		}
	}

	private void setForm(NguoiHoc model) {
		txtMaNguoiHoc.setText(model.getMaNguoiHoc());
		txtHoVaTen.setText(model.getHoTen());

		String gioiTinh = model.getGioiTinh();
		if (gioiTinh != null) {
			if (gioiTinh.equals("Nam")) {
				rdoNam.setSelected(true);
			} else if (gioiTinh.equals("Nữ")) {
				rdoNu.setSelected(true);
			}
		}

		Date ngaySinh = model.getNgaySinh();
		if (ngaySinh != null) {
			txtNgaySinh.setText(XDate.toString(ngaySinh, "yyyy/MM/dd"));
		} else {
			txtNgaySinh.setText(""); // Đặt giá trị rỗng nếu ngaySinh là null
		}

		txtSDT.setText(model.getSdt());
		txtEmail.setText(model.getEmail());
		taraGhiChu.setText(model.getGhiChu());
	}

	private void clearForm() {
		setForm(new NguoiHoc());
		rdoNam.setSelected(true);
		row = -1;
		updateStatus();
	}

	private NguoiHoc getForm() {
		NguoiHoc nh = new NguoiHoc();
		nh.setMaNguoiHoc(txtMaNguoiHoc.getText());
		nh.setHoTen(txtHoVaTen.getText());
		String gioiTinh = rdoNam.isSelected() ? "Nam" : "Nữ";
		nh.setGioiTinh(gioiTinh);
		nh.setNgaySinh(XDate.toDate(txtNgaySinh.getText(), "yyyy/MM/dd"));
		nh.setSdt(txtSDT.getText());
		nh.setEmail(txtEmail.getText());
		nh.setGhiChu(taraGhiChu.getText());
		nh.setMaNhanVien(Auth.user.getMaNV());
		nh.setNgayDK(new Date());
		return nh;
	}

	private void updateStatus() {
		boolean edit = this.row >= 0;
		boolean first = this.row == 0;
		boolean last = this.row == tblNguoiHoc.getRowCount() - 1;
		txtMaNguoiHoc.setEditable(!edit);
		btnThem.setEnabled(!edit);
		btnSua.setEnabled(edit);
		btnXoa.setEnabled(edit);

		btnFrist.setEnabled(edit && !first);
		btnPre.setEnabled(edit && !first);
		btnNext.setEnabled(edit && !last);
		btnLast.setEnabled(edit && !last);
	}

	private void first() {
		row = 0;
		edit();
	}

	private void prev() {
		if (row > 0) {
			row--;
			edit();
		}
	}

	private void next() {
		if (row < tblNguoiHoc.getRowCount() - 1) {
			row++;
			edit();
		}
	}

	private void last() {
		row = tblNguoiHoc.getRowCount() - 1;
		edit();
	}

	private void insert() {
		if (validation()) {
			NguoiHoc model = getForm();
			try {
				dao.insert(model);
				this.fillTable();
				this.clearForm();
				MsgBox.alert(this, "Thêm mới thành công");
			} catch (Exception e) {
				e.printStackTrace();
				MsgBox.alert(this, "Thêm mới thất bại");
			}
		}
	}

	private void update() {
		if (validation()) {
		NguoiHoc model = getForm();
		try {
			dao.update(model);
			this.fillTable();
			MsgBox.alert(this, "Cập nhật thành công");
		} catch (Exception ex) {
			MsgBox.alert(this, "Cập nhật thất bại");
		}
	}}

	private void delete() {
		if (!Auth.isManager()) {
			MsgBox.alert(this, "Bạn không có quyền để xóa!!");
		} else if (MsgBox.confirm(this, "Bạn thực sự muốn xóa người học này !!")) {
			String maNH = txtMaNguoiHoc.getText();
			try {
				dao.delete(maNH);
				this.fillTable();
				this.clearForm();
				MsgBox.alert(this, "Xóa thành công!");
			} catch (Exception e) {
				MsgBox.alert(this, "Xóa thất bại!!");
			}
		}
	}

	private void timKiem() {
		this.fillTable();
		this.clearForm();
		this.row = -1;
		this.updateStatus();
	}

	public boolean validation() {
		if (txtMaNguoiHoc.getText().equals("")) {
			MsgBox.alert(this, "Vui lòng nhập mã người học!!!");
			return false;
		}
		if (txtHoVaTen.getText().equals("")) {
			MsgBox.alert(this, "Vui lòng ghi họ tên!!!");
			return false;
		}
		Matcher reName = Pattern.compile(checkTen).matcher(txtHoVaTen.getText());
		if (reName.matches()) {
			MsgBox.alert(this, "Tên không hợp lệ!!!");
			return false;
		}
		if (txtNgaySinh.getText().equals("")) {
			MsgBox.alert(this, "Vui lòng ghi thời lượng!!!");
			return false;
		}
		Matcher reNgaySinh = Pattern.compile(checkNgaySinh).matcher(txtNgaySinh.getText());
		if (reName.matches()) {
			MsgBox.alert(this, "Ngày sinh không đúng định dạng (yyyy-MM-dd)");
			return false;
		}
		if (txtSDT.getText().equals("")) {
			MsgBox.alert(this, "Vui lòng nhập SĐT!!!");
			return false;
		}
		Matcher reSDT = Pattern.compile(checkSDT).matcher(txtSDT.getText());
		if (!reSDT.matches()) {
			MsgBox.alert(this, "SĐT không đúng dịnh dạng(0xx – xxxx – xxxx)");
			return false;
		}
		if (txtEmail.getText().equals("")) {
			MsgBox.alert(this, "Vui lòng nhập Email!!!");
			return false;
		}
		Matcher reEmail = Pattern.compile(checkMail).matcher(txtEmail.getText());
		if (!reEmail.matches()) {
			MsgBox.alert(this, "Email không đúng định dạng (name@gmail.com)");
			return false;
		}
		return true;
	}

	public NguoiHocJDialog() {
		setBounds(100, 100, 911, 678);
		getContentPane().setLayout(null);
		{
			JLabel lblQuanLyNguoiHoc = new JLabel("QUẢN LÝ NGƯỜI HỌC");
			lblQuanLyNguoiHoc.setForeground(new Color(0, 0, 128));
			lblQuanLyNguoiHoc.setFont(new Font("Times New Roman", Font.BOLD, 15));
			lblQuanLyNguoiHoc.setBounds(10, 0, 174, 23);
			getContentPane().add(lblQuanLyNguoiHoc);
		}

		tab = new JTabbedPane(JTabbedPane.TOP);
		tab.setBounds(10, 33, 876, 593);
		getContentPane().add(tab);

		JPanel pnCapNhat = new JPanel();
		tab.addTab("CẬP NHẬT", null, pnCapNhat, null);
		pnCapNhat.setLayout(null);

		JLabel lblMaNguoiHoc = new JLabel("Mã người học:");
		lblMaNguoiHoc.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		lblMaNguoiHoc.setBounds(14, 8, 98, 23);
		pnCapNhat.add(lblMaNguoiHoc);

		txtMaNguoiHoc = new JTextField();
		txtMaNguoiHoc.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		txtMaNguoiHoc.setColumns(10);
		txtMaNguoiHoc.setBounds(14, 36, 847, 35);
		pnCapNhat.add(txtMaNguoiHoc);

		JLabel lblHoVaTen = new JLabel("Họ và tên:");
		lblHoVaTen.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		lblHoVaTen.setBounds(14, 81, 98, 24);
		pnCapNhat.add(lblHoVaTen);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		lblEmail.setBounds(20, 210, 49, 16);
		pnCapNhat.add(lblEmail);

		txtHoVaTen = new JTextField();
		txtHoVaTen.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		txtHoVaTen.setColumns(10);
		txtHoVaTen.setBounds(14, 103, 847, 35);
		pnCapNhat.add(txtHoVaTen);

		txtEmail = new JTextField();
		txtEmail.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		txtEmail.setColumns(10);
		txtEmail.setBounds(20, 236, 300, 35);
		pnCapNhat.add(txtEmail);

		JLabel lblGhiChu = new JLabel("Ghi chú:");
		lblGhiChu.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		lblGhiChu.setBounds(10, 281, 98, 23);
		pnCapNhat.add(lblGhiChu);

		JPanel pnMoTa = new JPanel();
		pnMoTa.setBounds(10, 314, 851, 197);
		pnCapNhat.add(pnMoTa);
		pnMoTa.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 831, 177);
		pnMoTa.add(scrollPane);

		taraGhiChu = new JTextArea();
		taraGhiChu.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		scrollPane.setViewportView(taraGhiChu);

		JPanel panel = new JPanel();
		panel.setBounds(0, 521, 876, 45);
		pnCapNhat.add(panel);
		panel.setLayout(null);

		btnThem = new JButton("Thêm");
		btnThem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insert();
			}
		});
		btnThem.setBounds(10, 10, 66, 23);
		panel.add(btnThem);
		btnThem.setFont(new Font("Times New Roman", Font.PLAIN, 13));

		btnSua = new JButton("Sửa");
		btnSua.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				update();
			}
		});
		btnSua.setBounds(96, 10, 60, 23);
		panel.add(btnSua);
		btnSua.setFont(new Font("Times New Roman", Font.PLAIN, 13));

		btnMoi = new JButton("Mới");
		btnMoi.setBounds(240, 10, 60, 23);
		panel.add(btnMoi);
		btnMoi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearForm();
			}
		});
		btnMoi.setFont(new Font("Times New Roman", Font.PLAIN, 13));

		btnXoa = new JButton("Xóa");
		btnXoa.setBounds(170, 10, 60, 23);
		panel.add(btnXoa);
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
		btnLast.setBounds(793, 10, 60, 23);
		panel.add(btnLast);
		btnLast.setFont(new Font("Times New Roman", Font.BOLD, 13));

		btnNext = new JButton(">");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				next();
			}
		});
		btnNext.setBounds(723, 10, 60, 23);
		panel.add(btnNext);
		btnNext.setFont(new Font("Times New Roman", Font.BOLD, 13));

		btnPre = new JButton("<");
		btnPre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				prev();
			}
		});
		btnPre.setBounds(653, 10, 60, 23);
		panel.add(btnPre);
		btnPre.setFont(new Font("Times New Roman", Font.BOLD, 13));

		btnFrist = new JButton("|<");
		btnFrist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				first();
			}
		});
		btnFrist.setBounds(583, 10, 60, 23);
		panel.add(btnFrist);
		btnFrist.setFont(new Font("Times New Roman", Font.BOLD, 13));

		JLabel lblGioiTinh = new JLabel("Giới tính:");
		lblGioiTinh.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		lblGioiTinh.setBounds(14, 148, 98, 22);
		pnCapNhat.add(lblGioiTinh);

		rdoNam = new JRadioButton("Nam");
		btnGrGioiTinh.add(rdoNam);
		rdoNam.setSelected(true);
		rdoNam.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		rdoNam.setBounds(24, 181, 59, 21);
		pnCapNhat.add(rdoNam);

		rdoNu = new JRadioButton("Nữ");
		btnGrGioiTinh.add(rdoNu);
		rdoNu.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		rdoNu.setBounds(86, 180, 59, 21);
		pnCapNhat.add(rdoNu);

		JLabel lblSDT = new JLabel("Số điện thoại");
		lblSDT.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		lblSDT.setBounds(782, 217, 79, 17);
		pnCapNhat.add(lblSDT);

		txtSDT = new JTextField();
		txtSDT.setHorizontalAlignment(SwingConstants.RIGHT);
		txtSDT.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		txtSDT.setColumns(10);
		txtSDT.setBounds(561, 242, 300, 35);
		pnCapNhat.add(txtSDT);

		txtNgaySinh = new JTextField();
		txtNgaySinh.setHorizontalAlignment(SwingConstants.RIGHT);
		txtNgaySinh.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		txtNgaySinh.setColumns(10);
		txtNgaySinh.setBounds(561, 165, 300, 35);
		pnCapNhat.add(txtNgaySinh);

		JLabel lblNgaySinh = new JLabel("Ngày Sinh");
		lblNgaySinh.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		lblNgaySinh.setBounds(795, 148, 66, 17);
		pnCapNhat.add(lblNgaySinh);

		JPanel pnDanhSach = new JPanel();
		tab.addTab("DANH SÁCH", null, pnDanhSach, null);
		pnDanhSach.setLayout(null);

		JPanel pnTimKiem = new JPanel();
		pnTimKiem.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"T\u00CCM KI\u1EBEM", TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, new Color(0, 0, 0)));
		pnTimKiem.setBounds(10, 10, 851, 71);
		pnDanhSach.add(pnTimKiem);
		pnTimKiem.setLayout(null);

		txtTim = new JTextField();
		txtTim.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		txtTim.setBounds(10, 25, 681, 31);
		pnTimKiem.add(txtTim);
		txtTim.setColumns(10);

		JButton btnTim = new JButton("Tìm");
		btnTim.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				timKiem();
			}
		});
		btnTim.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		btnTim.setBounds(718, 25, 105, 31);
		pnTimKiem.add(btnTim);

		JPanel pnTbl = new JPanel();
		pnTbl.setBounds(10, 91, 861, 465);
		pnDanhSach.add(pnTbl);
		pnTbl.setLayout(null);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 10, 841, 445);
		pnTbl.add(scrollPane_1);

		tblNguoiHoc = new JTable();
		tblNguoiHoc.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getClickCount() == 2) {
					row = tblNguoiHoc.rowAtPoint(e.getPoint());
					edit();
				}
			}
		});
		scrollPane_1.setViewportView(tblNguoiHoc);
		tblNguoiHoc.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "M\u00C3 NH", "H\u1ECC V\u00C0 T\u00CAN", "GI\u1EDAI T\u00CDNH", "NG\u00C0Y SINH",
						"\u0110I\u1EC6N THO\u1EA0I", "EMAIL", "M\u00C3 NV", "NG\u00C0Y \u0110K" }) {
			boolean[] columnEditables = new boolean[] { false, false, false, false, false, false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tblNguoiHoc.setFont(new Font("Times New Roman", Font.PLAIN, 13));
	}
}
