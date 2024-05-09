package ui;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.List;
import java.awt.Color;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import dao.NhanVienDAO;
import entity.NhanVien;
import utils.Auth;
import utils.MsgBox;
import utils.XImage;

import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;

public class NhanVienJDialog extends JDialog {
	private JTable tblNhanVien;
	private JTextField txtMaNV, txtHoTen;
	private JPasswordField txtMatKhau, txtXacNhanMatKhau;
	private JRadioButton rdoTruongPhong, rdoNhanVien;
	private ButtonGroup btnGrVaiTro = new ButtonGroup();
	private JButton btnThem, btnSua, btnXoa, btnMoi, btnFrist, btnPre, btnNext, btnLast;
	private JTabbedPane tab;
	private JPanel pnCapNhat, pnDanhSach;
	NhanVienDAO dao = new NhanVienDAO();
	private int row = 0;
	private JScrollPane scrollPane;

	public static void main(String[] args) {
		try {
			NhanVienJDialog dialog = new NhanVienJDialog();
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
		setTitle("EDUSYS QUẢN LÝ NHÂN VIÊN");
		fillTable();
		updateStatus();
	}

	private void fillTable() {
		DefaultTableModel model = (DefaultTableModel) tblNhanVien.getModel();
		model.setRowCount(0);
		try {
			List<NhanVien> list = dao.selectAll();
			for (NhanVien nv : list) {
				Object[] row = { nv.getMaNV(), nv.getPass(), nv.getHoTen(),
						nv.isVaiTro() ? "Trưởng phòng" : "Nhân viên" };
				model.addRow(row);
			}
		} catch (Exception e) {
			MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
		}
	}

	private void edit() {
		try {
			String maNV = (String) tblNhanVien.getValueAt(row, 0);
			NhanVien model = dao.selectById(maNV);
			if (model != null) {
				setForm(model);
				updateStatus();
				tab.setSelectedIndex(0);
			}
		} catch (Exception e) {
			MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
		}
	}

	private void setForm(NhanVien model) {
		txtMaNV.setText(model.getMaNV());
		txtHoTen.setText(model.getHoTen());
		txtMatKhau.setText(model.getPass());
		txtXacNhanMatKhau.setText(model.getPass());
		rdoTruongPhong.setSelected(model.isVaiTro());
		rdoNhanVien.setSelected(!model.isVaiTro());
	}

	private NhanVien getForm() {
		NhanVien nv = new NhanVien();
		nv.setMaNV(txtMaNV.getText());
		nv.setHoTen(txtHoTen.getText());
		nv.setPass(new String(txtMatKhau.getPassword()));
		nv.setVaiTro(rdoTruongPhong.isSelected());
		return nv;
	}

	private void updateStatus() {
		boolean edit = this.row >= 0;
		boolean first = this.row == 0;
		boolean last = this.row == tblNhanVien.getRowCount() - 1;
		txtMaNV.setEditable(!edit);
		btnThem.setEnabled(!edit);
		btnSua.setEnabled(edit);
		btnXoa.setEnabled(edit);

		btnFrist.setEnabled(edit && !first);
		btnPre.setEnabled(edit && !first);
		btnNext.setEnabled(edit && !last);
		btnLast.setEnabled(edit && !last);
	}

	private void insert() {
		if (validation()) {
			NhanVien model = getForm();
			String confirm = new String(txtXacNhanMatKhau.getPassword());
			if (confirm.equals(model.getPass())) {
				try {
					dao.insert(model);
					this.fillTable();
					this.clearForm();
					MsgBox.alert(this, "Thên mới thành công");
				} catch (Exception e) {
					MsgBox.alert(this, "Thên mới thất bại");
				}
			} else {
				MsgBox.alert(this, "Xác nhận mật khẩu thất bại");
			}
		}
	}

	public boolean validation() {
		if (txtMaNV.getText().equals("")) {
			MsgBox.alert(this, "Vui lòng nhập mã nhân viên!!!");
			return false;
		}
		if (txtHoTen.getText().equals("")) {
			MsgBox.alert(this, "Vui nhập tên!!!");
			return false;
		}
		if (txtMatKhau.getPassword().equals("")) {
			MsgBox.alert(this, "Vui lòng không được bỏ trống!!!");
			return false;
		}
		if (txtXacNhanMatKhau.getPassword().equals("")) {
			MsgBox.alert(this, "Vui lòng không được bỏ trống!!!");
			return false;
		}
		return true;
	}

	private void update() {
		NhanVien model = getForm();
		String confirm = new String(txtXacNhanMatKhau.getPassword());
		if (!confirm.equals(model.getPass())) {
			MsgBox.alert(this, "Xác nhận mật khẩu không đúng");
		} else {
			try {
				dao.update(model);
				this.fillTable();
				MsgBox.alert(this, "Cập nhật thành công");
			} catch (Exception e) {
				MsgBox.alert(this, "Cập nhật thất bại");
			}
		}
	}

	private void delete() {
		if (!Auth.isManager()) {
			MsgBox.alert(this, "Bạn không có quyền xóa nhân viên!!!");
		} else {
			if (MsgBox.confirm(this, "Bạn muốn xóa nhân viên này?")) {
				String maNV = txtMaNV.getText();
				try {
					dao.delete(maNV);
					this.fillTable();
					this.clearForm();
					MsgBox.alert(this, "Xóa thành công");
				} catch (Exception e) {
					MsgBox.alert(this, "Xóa thất bại");
				}
			}
		}
	}

	private void clearForm() {
		NhanVien nv = new NhanVien();
		this.setForm(nv);
		this.row = -1;
		this.updateStatus();
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
		if (row < tblNhanVien.getRowCount() - 1) {
			row++;
			edit();
		}
	}

	private void last() {
		row = tblNhanVien.getRowCount() - 1;
		edit();
	}

	public NhanVienJDialog() {
		setBounds(100, 100, 885, 527);
		getContentPane().setLayout(null);

		JLabel lblTitle = new JLabel("QUẢN LÝ NHÂN VIÊN QUẢN TRỊ");
		lblTitle.setBounds(10, 0, 326, 32);
		lblTitle.setForeground(Color.BLUE);
		lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 18));
		getContentPane().add(lblTitle);

		tab = new JTabbedPane(JTabbedPane.TOP);
		tab.setBounds(10, 42, 851, 438);
		getContentPane().add(tab);

		pnCapNhat = new JPanel();
		tab.addTab("CẬP NHẬT", null, pnCapNhat, null);
		pnCapNhat.setLayout(null);

		JLabel lblMaNV = new JLabel("Mã nhân viên");
		lblMaNV.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		lblMaNV.setBounds(10, 10, 98, 13);
		pnCapNhat.add(lblMaNV);

		txtMaNV = new JTextField();
		txtMaNV.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		txtMaNV.setColumns(10);
		txtMaNV.setBounds(12, 33, 797, 35);
		pnCapNhat.add(txtMaNV);

		JLabel lblMatKhau = new JLabel("Mật khẩu");
		lblMatKhau.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		lblMatKhau.setBounds(10, 70, 98, 21);
		pnCapNhat.add(lblMatKhau);

		JLabel lblXacNhanMatKhau = new JLabel("Xác nhận mật khẩu");
		lblXacNhanMatKhau.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		lblXacNhanMatKhau.setBounds(10, 136, 114, 23);
		pnCapNhat.add(lblXacNhanMatKhau);

		JLabel lblHoTen = new JLabel("Họ và tên");
		lblHoTen.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		lblHoTen.setBounds(10, 214, 98, 13);
		pnCapNhat.add(lblHoTen);

		txtHoTen = new JTextField();
		txtHoTen.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		txtHoTen.setColumns(10);
		txtHoTen.setBounds(12, 237, 797, 35);
		pnCapNhat.add(txtHoTen);

		JLabel lblVaiTro = new JLabel("Vai trò");
		lblVaiTro.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		lblVaiTro.setBounds(10, 302, 98, 13);
		pnCapNhat.add(lblVaiTro);

		rdoTruongPhong = new JRadioButton("Trưởng phòng");
		btnGrVaiTro.add(rdoTruongPhong);
		rdoTruongPhong.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		rdoTruongPhong.setBounds(10, 334, 103, 21);
		pnCapNhat.add(rdoTruongPhong);

		rdoNhanVien = new JRadioButton("Nhân viên");
		rdoNhanVien.setSelected(true);
		btnGrVaiTro.add(rdoNhanVien);
		rdoNhanVien.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		rdoNhanVien.setBounds(141, 334, 103, 21);
		pnCapNhat.add(rdoNhanVien);

		btnThem = new JButton("Thêm");
		btnThem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insert();
			}
		});
		btnThem.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		btnThem.setBounds(10, 378, 66, 23);
		pnCapNhat.add(btnThem);

		btnSua = new JButton("Sửa");
		btnSua.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				update();
			}
		});
		btnSua.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		btnSua.setBounds(86, 378, 60, 23);
		pnCapNhat.add(btnSua);

		btnXoa = new JButton("Xóa");
		btnXoa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				delete();
			}
		});
		btnXoa.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		btnXoa.setBounds(153, 378, 60, 23);
		pnCapNhat.add(btnXoa);

		btnMoi = new JButton("Mới");
		btnMoi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearForm();
			}
		});
		btnMoi.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		btnMoi.setBounds(223, 378, 60, 23);
		pnCapNhat.add(btnMoi);

		btnFrist = new JButton("|<");
		btnFrist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				first();
			}
		});
		btnFrist.setFont(new Font("Times New Roman", Font.BOLD, 13));
		btnFrist.setBounds(518, 378, 60, 23);
		pnCapNhat.add(btnFrist);

		btnPre = new JButton("<");
		btnPre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				prev();
			}
		});
		btnPre.setFont(new Font("Times New Roman", Font.BOLD, 13));
		btnPre.setBounds(588, 378, 60, 23);
		pnCapNhat.add(btnPre);

		btnNext = new JButton(">");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				next();
			}
		});
		btnNext.setFont(new Font("Times New Roman", Font.BOLD, 13));
		btnNext.setBounds(658, 378, 60, 23);
		pnCapNhat.add(btnNext);

		btnLast = new JButton(">|");
		btnLast.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				last();
			}
		});
		btnLast.setFont(new Font("Times New Roman", Font.BOLD, 13));
		btnLast.setBounds(728, 378, 60, 23);
		pnCapNhat.add(btnLast);

		txtMatKhau = new JPasswordField();
		txtMatKhau.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		txtMatKhau.setBounds(10, 101, 797, 35);
		pnCapNhat.add(txtMatKhau);

		txtXacNhanMatKhau = new JPasswordField();
		txtXacNhanMatKhau.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		txtXacNhanMatKhau.setBounds(10, 169, 797, 35);
		pnCapNhat.add(txtXacNhanMatKhau);

		pnDanhSach = new JPanel();
		tab.addTab("DANH SÁCH", null, pnDanhSach, null);
		pnDanhSach.setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 826, 391);
		pnDanhSach.add(scrollPane);

		tblNhanVien = new JTable();
		scrollPane.setViewportView(tblNhanVien);
		tblNhanVien.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getClickCount() == 2) {
					row = tblNhanVien.rowAtPoint(e.getPoint());
					edit();
				}
			}
		});
		tblNhanVien.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "M\u00C3 NV", "M\u1EACT KH\u1EA8U", "H\u1ECC V\u00C0 T\u00CAN", "VAI TR\u00D2" }) {
			boolean[] columnEditables = new boolean[] { false, false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tblNhanVien.setFont(new Font("Times New Roman", Font.PLAIN, 13));
	}
}
