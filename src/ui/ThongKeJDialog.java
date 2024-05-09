package ui;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;

import dao.KhoaHocDAO;
import dao.ThongKeDAO;
import entity.ChuyenDe;
import entity.KhoaHoc;
import utils.JdbcHelper;
import utils.MsgBox;
import utils.XImage;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

public class ThongKeJDialog extends JDialog {
	private JTable tblNguoiHoc, tblDiemChuyenDe, tblBangDiem, tblDoanhThu;
	private JComboBox cboKhoaHoc, cboNam;
	private JTabbedPane tab;
	ThongKeDAO dao = new ThongKeDAO();
	KhoaHocDAO khDao = new KhoaHocDAO();

	public static void main(String[] args) {
		try {
			ThongKeJDialog dialog = new ThongKeJDialog();
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

	public void selectTab(int index) {
		tab.setSelectedIndex(index);
	}

	private void init() {
		setIconImage(XImage.getAppIcon());
		setTitle("TỔNG HỢP THỐNG KÊ");
		fillCboKhoaHoc();
		fillTableNguoiHoc();
		fillTableDiemChuyenDe();
		fillCboNam();
		fillTableDoanhThu();
	}

	private void fillCboKhoaHoc() {
		DefaultComboBoxModel model = (DefaultComboBoxModel) cboKhoaHoc.getModel();
		model.removeAllElements();

		List<KhoaHoc> list = khDao.selectAll();
		for (KhoaHoc kh : list) {
			model.addElement(kh);
		}
		cboKhoaHoc.setSelectedIndex(0);
	}

	String getXepLoai(double diem) {
		if (diem < 5) {
			return "Chưa đạt";
		}
		if (diem < 6.5) {
			return "Trung bình";
		}
		if (diem < 7.5) {
			return "Khá";
		}
		if (diem < 9) {
			return "Giỏi";
		}
		return "Xuất sắc";
	}

	private void fillTableBangDiem() {
		DefaultTableModel model = (DefaultTableModel) tblBangDiem.getModel();
		model.setRowCount(0);
		KhoaHoc kh = (KhoaHoc) cboKhoaHoc.getSelectedItem();
		List<Object[]> list = dao.getBangDiem(kh.getMaKhoaHoc());
		for (Object[] row : list) {
			double diem = (double) row[2];
			model.addRow(new Object[] { row[0], row[1], row[2], getXepLoai(diem) });
		}
	}

	private void fillTableNguoiHoc() {
		DefaultTableModel model = (DefaultTableModel) tblNguoiHoc.getModel();
		model.setRowCount(0);
		List<Object[]> list = dao.getLuongNguoiHoc();
		for (Object[] row : list) {
			model.addRow(row);
		}
	}

	private void fillTableDiemChuyenDe() {
		DefaultTableModel model = (DefaultTableModel) tblDiemChuyenDe.getModel();
		model.setRowCount(0);
		List<Object[]> list = dao.getDiemChuyenDe();
		for (Object[] row : list) {
			model.addRow(new Object[] { row[0], row[1], row[2], row[3], row[4] });
		}
	}

	private void fillCboNam() {
		DefaultComboBoxModel model = (DefaultComboBoxModel) cboNam.getModel();
		model.removeAllElements();
		List<Integer> list = khDao.selectYear();
		for (Integer year : list) {
			model.addElement(year);
		}
	}

	private void fillTableDoanhThu() {
		DefaultTableModel model = (DefaultTableModel) tblDoanhThu.getModel();
		model.setRowCount(0);
		int year = (Integer) cboNam.getSelectedItem();
		List<Object[]> list = dao.getDoanhThu(year);
		for (Object[] row : list) {
			model.addRow(row);
		}
	}

	public ThongKeJDialog() {
		setBounds(100, 100, 878, 502);
		getContentPane().setLayout(null);

		JLabel lblTitle = new JLabel("TỔNG HỢP THỐNG KÊ");
		lblTitle.setBounds(10, 10, 358, 31);
		lblTitle.setForeground(Color.BLUE);
		lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 18));
		getContentPane().add(lblTitle);

		tab = new JTabbedPane(JTabbedPane.TOP);
		tab.setBounds(0, 41, 861, 424);
		getContentPane().add(tab);

		JPanel pnBangDiem = new JPanel();
		tab.addTab("BẢNG ĐIỂM", null, pnBangDiem, null);
		pnBangDiem.setLayout(null);

		JLabel lblKhoaHoc = new JLabel("Khóa học:");
		lblKhoaHoc.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		lblKhoaHoc.setBounds(10, 21, 87, 22);
		pnBangDiem.add(lblKhoaHoc);

		cboKhoaHoc = new JComboBox();
		cboKhoaHoc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fillTableBangDiem();
			}
		});
		cboKhoaHoc.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		cboKhoaHoc.setBounds(107, 10, 715, 33);
		pnBangDiem.add(cboKhoaHoc);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 61, 836, 326);
		pnBangDiem.add(scrollPane);

		tblBangDiem = new JTable();
		scrollPane.setViewportView(tblBangDiem);
		tblBangDiem.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "M\u00C3 NH", "H\u1ECC V\u00C0 T\u00CAN", "\u0110I\u1EC2M", "X\u1EBEP LO\u1EA0I" }) {
			Class[] columnTypes = new Class[] { Object.class, String.class, Double.class, String.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		tblBangDiem.setFont(new Font("Times New Roman", Font.PLAIN, 13));

		JPanel pnNguoiHoc = new JPanel();
		tab.addTab("NGƯỜI HỌC", null, pnNguoiHoc, null);
		pnNguoiHoc.setLayout(null);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 10, 836, 377);
		pnNguoiHoc.add(scrollPane_2);

		tblNguoiHoc = new JTable();
		scrollPane_2.setViewportView(tblNguoiHoc);
		tblNguoiHoc.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "N\u0102M", "S\u1ED0 NH",
				"\u0110K S\u1EDAM NH\u1EA4T", "\u0110K MU\u1ED8N NH\u1EA4T" }));
		tblNguoiHoc.getColumnModel().getColumn(2).setPreferredWidth(95);
		tblNguoiHoc.getColumnModel().getColumn(3).setPreferredWidth(87);
		tblNguoiHoc.setFont(new Font("Times New Roman", Font.PLAIN, 13));

		JPanel pnDiemChuyenDe = new JPanel();
		tab.addTab("ĐIỂM CHUYÊN ĐỀ", null, pnDiemChuyenDe, null);
		pnDiemChuyenDe.setLayout(null);

		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(10, 10, 836, 377);
		pnDiemChuyenDe.add(scrollPane_3);

		tblDiemChuyenDe = new JTable();
		scrollPane_3.setViewportView(tblDiemChuyenDe);
		tblDiemChuyenDe.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "CHUY\u00CAN \u0110\u1EC0",
				"SL HV", "\u0110I\u1EC2M TN", "\u0110I\u1EC2M CN", "\u0110I\u1EC2M TB" }));
		tblDiemChuyenDe.setFont(new Font("Times New Roman", Font.PLAIN, 13));

		JPanel pnDoanhThu = new JPanel();
		tab.addTab("DOANH THU", null, pnDoanhThu, null);
		pnDoanhThu.setLayout(null);

		JLabel lblNam = new JLabel("Năm:");
		lblNam.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		lblNam.setBounds(10, 21, 87, 22);
		pnDoanhThu.add(lblNam);

		cboNam = new JComboBox();
		cboNam.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fillTableDoanhThu();
			}
		});
		cboNam.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		cboNam.setBounds(107, 10, 715, 33);
		pnDoanhThu.add(cboNam);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 53, 836, 334);
		pnDoanhThu.add(scrollPane_1);

		tblDoanhThu = new JTable();
		scrollPane_1.setViewportView(tblDoanhThu);
		tblDoanhThu.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "CHUY\u00CAN \u0110\u1EC0",
				"S\u1ED0 KH", "S\u1ED0 HV", "DOANH THU", "HP. TN", "HP. CN", "HP. TB" }) {
			boolean[] columnEditables = new boolean[] { false, false, false, false, false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tblDoanhThu.setFont(new Font("Times New Roman", Font.PLAIN, 13));
	}
}
