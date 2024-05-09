package ui;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import dao.ChuyenDeDAO;
import entity.ChuyenDe;
import utils.Auth;
import utils.MsgBox;
import utils.XImage;

import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import javax.swing.border.BevelBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ChuyenDeJDialog extends JDialog {
	private JTable tblChuyenDe;
	private JTextField txtMaCD, txtThoiLuong, txtTenCD, txtHocPhi;
	private JLabel lblAnh;
	private JTextArea txtMoTa;
	private JButton btnThem, btnSua, btnXoa, btnMoi, btnFrist, btnPre, btnNext, btnLast;
	private JTabbedPane tab;
	ChuyenDeDAO dao = new ChuyenDeDAO();
	JFileChooser fileChooser = new JFileChooser();
	private int row = 0;
	private JScrollPane scrollPane_1;
	private String number = "\\d*";

	public static void main(String[] args) {
		try {
			ChuyenDeJDialog dialog = new ChuyenDeJDialog();
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
		setTitle("EDUSYS QUẢN LÝ CHUYÊN ĐỀ");
		fillTable();
		updateStatus();
	}

	private void chonAnh() {
		if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			XImage.save(file);
			ImageIcon icon = XImage.read(file.getName());
			lblAnh.setIcon(icon);
			lblAnh.setToolTipText(file.getName());
		}
	}

	private void fillTable() {
		DefaultTableModel model = (DefaultTableModel) tblChuyenDe.getModel();
		model.setRowCount(0);
		try {
			List<ChuyenDe> list = dao.selectAll();
			for (ChuyenDe cd : list) {
				Object[] row = { cd.getMaCD(), cd.getTenCD(), cd.getHocPhi(), cd.getThoiLuong(), cd.getHinhLogo() };
				model.addRow(row);
			}
		} catch (Exception e) {
			MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
		}
	}

	private void edit() {
		try {
			String maNV = (String) tblChuyenDe.getValueAt(row, 0);
			ChuyenDe cd = dao.selectById(maNV);
			if (cd != null) {
				setForm(cd);
				updateStatus();
				tab.setSelectedIndex(0);
			}
		} catch (Exception e) {
			MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
		}
	}

	private void setForm(ChuyenDe model) {
		txtMaCD.setText(model.getMaCD());
		txtTenCD.setText(model.getTenCD());
		txtHocPhi.setText(String.valueOf(model.getHocPhi()));
		txtThoiLuong.setText(String.valueOf(model.getThoiLuong()));
		txtMoTa.setText(model.getMoTa());
		String hinhLogo = model.getHinhLogo();
		if (hinhLogo != null) {
			lblAnh.setIcon(XImage.read(model.getHinhLogo()));
			lblAnh.setToolTipText(model.getHinhLogo());
		}
	}

	private ChuyenDe getForm() {
		ChuyenDe cd = new ChuyenDe();
		cd.setMaCD(txtMaCD.getText());
		cd.setTenCD(txtTenCD.getText());
		cd.setHocPhi(Double.parseDouble(txtHocPhi.getText()));
		cd.setThoiLuong(Integer.parseInt(txtThoiLuong.getText()));
		cd.setMoTa(txtMoTa.getText());
		cd.setHinhLogo(lblAnh.getToolTipText());
		return cd;
	}

	private void updateStatus() {
		boolean edit = this.row >= 0;
		boolean first = this.row == 0;
		boolean last = this.row == tblChuyenDe.getRowCount() - 1;
		txtMaCD.setEditable(!edit);
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
			ChuyenDe model = getForm();
			try {
				dao.insert(model);
				fillTable();
				clearForm();
				MsgBox.alert(this, "Thên mới thành công");
			} catch (Exception e) {
				MsgBox.alert(this, "Thên mới thất bại");
			}
		}
	}

	private void update() {
		if (validation()) {
			ChuyenDe model = getForm();
			try {
				dao.update(model);
				fillTable();
				MsgBox.alert(this, "Cập nhật thành công");
			} catch (Exception e) {
				MsgBox.alert(this, "Cập nhật thất bại");
			}
		}
	}

	private void delete() {
		if (!Auth.isManager()) {
			MsgBox.alert(this, "Bạn không có quyền xóa chuyên đề!!!");
		} else {
			if (MsgBox.confirm(this, "Bạn muốn xóa chuyên đề này?")) {
				String maNV = txtMaCD.getText();
				try {
					dao.delete(maNV);
					fillTable();
					clearForm();
					MsgBox.alert(this, "Xóa thành công");
				} catch (Exception e) {
					MsgBox.alert(this, "Xóa thất bại");
				}
			}
		}
	}

	private void clearForm() {
		ChuyenDe cd = new ChuyenDe();
		setForm(cd);
		row = -1;
		updateStatus();
		lblAnh.setIcon(null);
	    lblAnh.setToolTipText(null);
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
		if (row < tblChuyenDe.getRowCount() - 1) {
			row++;
			edit();
		}
	}

	private void last() {
		row = tblChuyenDe.getRowCount() - 1;
		edit();
	}

	public boolean validation() {
		if (txtMaCD.getText().equals("")) {
			MsgBox.alert(this, "Vui lòng ghi mã chuyên đề!!!");
			return false;
		}
		if (txtTenCD.getText().equals("")) {
			MsgBox.alert(this, "Vui lòng ghi tên chuyên đề!!!");
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
		if (txtHocPhi.getText().equals("")) {
			MsgBox.alert(this, "Vui lòng ghi học phí!!!");
			return false;
		}
		Matcher reHocPhi = Pattern.compile(number).matcher(txtHocPhi.getText());
		if (!reHocPhi.matches()) {
			MsgBox.alert(this, "Học phí phải là số");
			return false;
		}
		return true;
	}

	public ChuyenDeJDialog() {
		setBounds(100, 100, 925, 683);
		getContentPane().setLayout(null);

		JLabel lblTitle = new JLabel("QUẢN LÝ CHUYÊN ĐỀ");
		lblTitle.setForeground(Color.BLUE);
		lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblTitle.setBounds(10, 0, 326, 32);
		getContentPane().add(lblTitle);

		tab = new JTabbedPane(JTabbedPane.TOP);
		tab.setBounds(10, 42, 891, 594);
		getContentPane().add(tab);

		JPanel pnCapNhat = new JPanel();
		pnCapNhat.setLayout(null);
		tab.addTab("CẬP NHẬT", null, pnCapNhat, null);

		lblAnh = new JLabel("No image");
		lblAnh.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getClickCount() == 2) {
					chonAnh();
				}
			}
		});
		lblAnh.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblAnh.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lblAnh.setBounds(10, 31, 183, 241);
		pnCapNhat.add(lblAnh);

		JLabel lblHinhLogo = new JLabel("Hình logo");
		lblHinhLogo.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		lblHinhLogo.setBounds(17, 10, 59, 13);
		pnCapNhat.add(lblHinhLogo);

		JLabel lblMaChuyenDe = new JLabel("Mã chuyên đề");
		lblMaChuyenDe.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		lblMaChuyenDe.setBounds(201, 10, 98, 13);
		pnCapNhat.add(lblMaChuyenDe);

		txtMaCD = new JTextField();
		txtMaCD.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		txtMaCD.setColumns(10);
		txtMaCD.setBounds(203, 33, 658, 35);
		pnCapNhat.add(txtMaCD);

		JLabel lblTenChuyenDe = new JLabel("Tên chuyên đề");
		lblTenChuyenDe.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		lblTenChuyenDe.setBounds(201, 77, 98, 13);
		pnCapNhat.add(lblTenChuyenDe);

		JLabel lblThoiLuong = new JLabel("Thời lượng (giờ)");
		lblThoiLuong.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		lblThoiLuong.setBounds(201, 147, 98, 13);
		pnCapNhat.add(lblThoiLuong);

		txtThoiLuong = new JTextField();
		txtThoiLuong.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		txtThoiLuong.setColumns(10);
		txtThoiLuong.setBounds(203, 170, 658, 35);
		pnCapNhat.add(txtThoiLuong);

		JLabel lblHocPhi = new JLabel("Học phí");
		lblHocPhi.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		lblHocPhi.setBounds(201, 215, 98, 13);
		pnCapNhat.add(lblHocPhi);

		txtTenCD = new JTextField();
		txtTenCD.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		txtTenCD.setColumns(10);
		txtTenCD.setBounds(203, 100, 658, 35);
		pnCapNhat.add(txtTenCD);

		txtHocPhi = new JTextField();
		txtHocPhi.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		txtHocPhi.setColumns(10);
		txtHocPhi.setBounds(201, 239, 660, 35);
		pnCapNhat.add(txtHocPhi);

		JLabel lblMoTaChuyenDe = new JLabel("Mô tả chuyên đề");
		lblMoTaChuyenDe.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		lblMoTaChuyenDe.setBounds(20, 291, 98, 13);
		pnCapNhat.add(lblMoTaChuyenDe);

		JPanel pnMoTa = new JPanel();
		pnMoTa.setBounds(10, 314, 851, 197);
		pnCapNhat.add(pnMoTa);
		pnMoTa.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 831, 177);
		pnMoTa.add(scrollPane);

		txtMoTa = new JTextArea();
		txtMoTa.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		scrollPane.setViewportView(txtMoTa);

		btnThem = new JButton("Thêm");
		btnThem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insert();
			}
		});
		btnThem.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		btnThem.setBounds(17, 531, 66, 23);
		pnCapNhat.add(btnThem);

		btnSua = new JButton("Sửa");
		btnSua.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				update();
			}
		});
		btnSua.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		btnSua.setBounds(96, 531, 60, 23);
		pnCapNhat.add(btnSua);

		btnXoa = new JButton("Xóa");
		btnXoa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				delete();
			}
		});
		btnXoa.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		btnXoa.setBounds(170, 531, 60, 23);
		pnCapNhat.add(btnXoa);

		btnMoi = new JButton("Mới");
		btnMoi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearForm();
			}
		});
		btnMoi.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		btnMoi.setBounds(240, 531, 60, 23);
		pnCapNhat.add(btnMoi);

		btnFrist = new JButton("|<");
		btnFrist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				first();
			}
		});
		btnFrist.setFont(new Font("Times New Roman", Font.BOLD, 13));
		btnFrist.setBounds(583, 531, 60, 23);
		pnCapNhat.add(btnFrist);

		btnPre = new JButton("<");
		btnPre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				prev();
			}
		});
		btnPre.setFont(new Font("Times New Roman", Font.BOLD, 13));
		btnPre.setBounds(653, 531, 60, 23);
		pnCapNhat.add(btnPre);

		btnNext = new JButton(">");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				next();
			}
		});
		btnNext.setFont(new Font("Times New Roman", Font.BOLD, 13));
		btnNext.setBounds(723, 531, 60, 23);
		pnCapNhat.add(btnNext);

		btnLast = new JButton(">|");
		btnLast.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				last();
			}
		});
		btnLast.setFont(new Font("Times New Roman", Font.BOLD, 13));
		btnLast.setBounds(793, 531, 60, 23);
		pnCapNhat.add(btnLast);

		JPanel pnDanhSach = new JPanel();
		pnDanhSach.setLayout(null);
		tab.addTab("DANH SÁCH", null, pnDanhSach, null);

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 10, 866, 547);
		pnDanhSach.add(scrollPane_1);

		tblChuyenDe = new JTable();
		scrollPane_1.setViewportView(tblChuyenDe);
		tblChuyenDe.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getClickCount() == 2) {
					row = tblChuyenDe.rowAtPoint(e.getPoint());
					edit();
				}
			}
		});
		tblChuyenDe.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "M\u00C3 C\u0110",
				"T\u00CAN C\u0110", "H\u1ECCC PH\u00CD", "TH\u1EDCI L\u01AF\u1EE2NG", "H\u00CCNH" }) {
			boolean[] columnEditables = new boolean[] { false, false, false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tblChuyenDe.setFont(new Font("Times New Roman", Font.PLAIN, 13));
	}
}
