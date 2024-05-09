package ui;

import java.awt.BorderLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dao.ChuyenDeDAO;
import dao.HocVienDAO;
import dao.KhoaHocDAO;
import dao.NguoiHocDAO;
import entity.ChuyenDe;
import entity.HocVien;
import entity.KhoaHoc;
import entity.NguoiHoc;
import utils.Auth;
import utils.MsgBox;
import utils.XImage;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import java.awt.Font;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTabbedPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HocVienJDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JComboBox cboChuyenDe, cboKhoaHoc;
	ChuyenDeDAO cdDao = new ChuyenDeDAO();
	KhoaHocDAO khDao = new KhoaHocDAO();
	NguoiHocDAO nhDao = new NguoiHocDAO();
	HocVienDAO hvDao = new HocVienDAO();
	private JTable tblHocVien, tblNguoiHoc;
	private JTextField txtTimKiem;
	private int row = 0;
	private JTabbedPane tab;

	public static void main(String[] args) {
		try {
			HocVienJDialog dialog = new HocVienJDialog();
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
		setTitle("QUẢN LÝ HỌC VIÊN CỦA KHÓA HỌC");
		fillCboCD();
		fillTableNguoiHoc();
	}

	private void fillCboCD() {
		DefaultComboBoxModel model = (DefaultComboBoxModel) cboChuyenDe.getModel();
		model.removeAllElements();
		try {
			List<ChuyenDe> list = cdDao.selectAll();
			for (ChuyenDe cd : list) {
				model.addElement(cd);
			}
			fillCboKhoaHoc();
		} catch (Exception e) {
			MsgBox.alert(this, "Lỗi truy vấn dữ liệu chuyên đề!");
		}
	}

	private void fillCboKhoaHoc() {
		DefaultComboBoxModel model = (DefaultComboBoxModel) cboKhoaHoc.getModel();
		model.removeAllElements();
		try {
			ChuyenDe chuyenDe = (ChuyenDe) cboChuyenDe.getSelectedItem();
			if (chuyenDe != null) {
				List<KhoaHoc> list = khDao.selectKhoaHocByChuyenDe(chuyenDe.getMaCD());
				for (KhoaHoc khoaHoc : list) {
					model.addElement(khoaHoc);
				}
			}
			fillTableHocVien();
		} catch (Exception e) {
			MsgBox.alert(this, "Lỗi truy vấn dữ liệu khóa học!");
		}
	}

	private void fillTableHocVien() {
		DefaultTableModel model = (DefaultTableModel) tblHocVien.getModel();
		model.setRowCount(0);
		try {
			KhoaHoc kh = (KhoaHoc) cboKhoaHoc.getSelectedItem();
			if (kh != null) {
				List<HocVien> list = hvDao.SELECT_BY_KHOAHOC_SQL(kh.getMaKhoaHoc());
				for (int i = 0; i < list.size(); i++) {
					HocVien hv = list.get(i);
					String hoTen = nhDao.selectById(hv.getMaNguoiHoc()).getHoTen();

					Object[] row = { i + 1, hv.getMaHocVien(), hv.getMaNguoiHoc(), hoTen, hv.getDiemTB() };
					model.addRow(row);

				}
			}
			fillTableNguoiHoc();
		} catch (Exception e) {
			MsgBox.alert(this, "Lỗi truy vấn dữ liệu! fillTableHocVien");
		}
	}

	private void fillTableNguoiHoc() {
		DefaultTableModel model = (DefaultTableModel) tblNguoiHoc.getModel();
		model.setRowCount(0);
		try {
			KhoaHoc kh = (KhoaHoc) cboKhoaHoc.getSelectedItem();
			if (kh != null) {
				List<NguoiHoc> list = nhDao.selectNotInCourse(kh.getMaKhoaHoc());
				for (NguoiHoc nh : list) {
					Object[] row = { nh.getMaNguoiHoc(), nh.getHoTen(), nh.getNgaySinh(), nh.getGioiTinh(), nh.getSdt(),
							nh.getEmail() };
					model.addRow(row);
				}
			}
		} catch (Exception e) {
			MsgBox.alert(this, "Lỗi truy vấn dữ liệu! fillTableNguoiHoc");
		}
	}

	private void findBYname() {
		DefaultTableModel model = (DefaultTableModel) tblNguoiHoc.getModel();
		model.setRowCount(0);
		try {
			KhoaHoc kh = (KhoaHoc) cboKhoaHoc.getSelectedItem();
			System.out.println("hi");
			if (kh != null) {
				String name = txtTimKiem.getText();
				System.out.println(name);
				System.out.println(kh.getMaKhoaHoc());
				List<NguoiHoc> list = nhDao.selectByKeyword(kh.getMaKhoaHoc(), name);
				System.out.println("listFind:" + list.size());
				for (NguoiHoc nguoiHoc : list) {
					model.addRow(new Object[] { nguoiHoc.getMaNguoiHoc(), nguoiHoc.getHoTen(), nguoiHoc.getGioiTinh(),
							nguoiHoc.getNgaySinh(), nguoiHoc.getSdt(), nguoiHoc.getEmail() });
				}
			}
		} catch (Exception e) {
			MsgBox.alert(this, "Lỗi tìm kiếm theo tên!");
		}
	}

	private void addHocVien() {
		KhoaHoc khoaHoc = (KhoaHoc) cboKhoaHoc.getSelectedItem();
		for (int row : tblNguoiHoc.getSelectedRows()) {
			HocVien hv = new HocVien();
			hv.setMaKhoaHoc(khoaHoc.getMaKhoaHoc());
			hv.setDiemTB(0);
			hv.setMaNguoiHoc((String) tblNguoiHoc.getValueAt(row, 0));
			System.out.println("=>" + hv.getMaHocVien() + "-" + hv.getMaHocVien() + "-" + hv.getDiemTB());
			hvDao.insert(hv);
		}
		fillTableHocVien();
		tab.setSelectedIndex(0);
	}

	private void removeHocVien() {
		if (!Auth.isManager()) {
			MsgBox.alert(this, "Bạn không có đủ quyền để xóa!!");
		} else {
			if (MsgBox.confirm(this, "Bạn muốn xóa các học viên được chọn ?")) {
				for (int row : tblHocVien.getSelectedRows()) {
					int maHV = (Integer) tblHocVien.getValueAt(row, 1);
					hvDao.delete(maHV);
				}
				fillTableHocVien();
			}
		}
	}

	private void updateDiem() {
		int viTri = tblHocVien.getRowCount();
		for (int i = 0; i < tblHocVien.getRowCount(); i++) {
			int maHV = (Integer) tblHocVien.getValueAt(i, 1);
			HocVien hv = hvDao.selectById(maHV);
			hv.setDiemTB(Double.parseDouble(tblHocVien.getValueAt(i, 4).toString()));		    
			hvDao.update(hv);
		}
		fillTableHocVien();
		MsgBox.alert(this, "Cập nhật điểm thành công !");
	}

	public HocVienJDialog() {
		
		setBounds(100, 100, 898, 610);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblChuyenDe = new JLabel("Chuyên đề:");
		lblChuyenDe.setBounds(23, 23, 87, 22);
		lblChuyenDe.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		contentPanel.add(lblChuyenDe);

		cboChuyenDe = new JComboBox();
		cboChuyenDe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fillCboKhoaHoc();
			}
		});
		cboChuyenDe.setBounds(23, 55, 377, 51);
		cboChuyenDe.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		contentPanel.add(cboChuyenDe);

		JLabel lblKhoaHoc = new JLabel("Khóa học:");
		lblKhoaHoc.setBounds(470, 23, 87, 22);
		lblKhoaHoc.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		contentPanel.add(lblKhoaHoc);

		cboKhoaHoc = new JComboBox();
		cboKhoaHoc.setBounds(470, 55, 377, 51);
		cboKhoaHoc.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		contentPanel.add(cboKhoaHoc);

		tab = new JTabbedPane(JTabbedPane.TOP);
		tab.setBounds(10, 118, 864, 445);
		contentPanel.add(tab);

		JPanel pnHocVien = new JPanel();
		tab.addTab("HỌC VIÊN", null, pnHocVien, null);
		pnHocVien.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 839, 354);
		pnHocVien.add(scrollPane);

		tblHocVien = new JTable();
		tblHocVien.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getClickCount() == 2) {
					row = tblHocVien.rowAtPoint(e.getPoint());
					updateDiem();
				}
			}
		});
		scrollPane.setViewportView(tblHocVien);
		tblHocVien.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"TT", "M\u00C3 HV", "M\u00C3 NH", "H\u1ECC T\u00CAN", "\u0110I\u1EC2M"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tblHocVien.setFont(new Font("Times New Roman", Font.PLAIN, 13));

		JButton btnDeleteKH = new JButton("Xóa khỏi khóa học");
		btnDeleteKH.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeHocVien();
			}
		});
		btnDeleteKH.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		btnDeleteKH.setBounds(598, 374, 135, 34);
		pnHocVien.add(btnDeleteKH);

		JButton btnUpdateDiem = new JButton("Cập nhật điểm");
		btnUpdateDiem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateDiem();
			}
		});
		btnUpdateDiem.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		btnUpdateDiem.setBounds(743, 374, 106, 34);
		pnHocVien.add(btnUpdateDiem);
		

		JPanel pnNguoiHoc = new JPanel();
		tab.addTab("NGƯỜI HỌC", null, pnNguoiHoc, null);
		pnNguoiHoc.setLayout(null);

		JLabel lblTmKim = new JLabel("Tìm kiếm:");
		lblTmKim.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		lblTmKim.setBounds(10, 10, 87, 22);
		pnNguoiHoc.add(lblTmKim);

		txtTimKiem = new JTextField();
		txtTimKiem.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		txtTimKiem.setColumns(10);
		txtTimKiem.setBounds(10, 43, 655, 31);
		pnNguoiHoc.add(txtTimKiem);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 84, 839, 275);
		pnNguoiHoc.add(scrollPane_1);

		tblNguoiHoc = new JTable();
		tblNguoiHoc.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getClickCount() == 2) {
					row = tblHocVien.rowAtPoint(e.getPoint());
					addHocVien();
				}
			}
		});
		scrollPane_1.setViewportView(tblNguoiHoc);
		tblNguoiHoc.setModel(
				new DefaultTableModel(new Object[][] {}, new String[] { "M\u00C3 NH", "H\u1ECC V\u00C0 T\u00CAN",
						"GI\u1EDAI T\u00CDNH", "NG\u00C0Y SINH", "\u0110I\u1EC6N THO\u1EA0I", "EMAIL" }) {
					boolean[] columnEditables = new boolean[] { false, false, false, false, false, false };

					public boolean isCellEditable(int row, int column) {
						return columnEditables[column];
					}
				});
		tblNguoiHoc.setFont(new Font("Times New Roman", Font.PLAIN, 13));

		JButton btnTim = new JButton("Tìm");
		btnTim.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				findBYname();
			}
		});
		btnTim.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		btnTim.setBounds(733, 43, 85, 31);
		pnNguoiHoc.add(btnTim);

		JButton btnAddKH = new JButton("Thêm vào khóa học");
		btnAddKH.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addHocVien();
			}
		});
		btnAddKH.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		btnAddKH.setBounds(705, 374, 144, 34);
		pnNguoiHoc.add(btnAddKH);
	}
}
