package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dao.NhanVienDAO;
import utils.Auth;
import utils.MsgBox;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JPasswordField;

public class DoiPassJDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtTenDangNhap;
	private JPasswordField txtMatKhauHienTai, txtXacNhanMatKhau, txtMatKhauMoi;
	NhanVienDAO dao = new NhanVienDAO();

	public static void main(String[] args) {
		try {
			DoiPassJDialog dialog = new DoiPassJDialog(new JFrame(), true);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			dialog.setLocationRelativeTo(null);
			dialog.setTitle("EdySys - Đổi mật khẩu");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void openHuyBo() {
		this.dispose();
	}

	private void clearForm() {
		txtTenDangNhap.setText("");
		txtMatKhauHienTai.setText("");
		txtMatKhauMoi.setText("");
		txtXacNhanMatKhau.setText("");
	}

	private void doiMatKhau() {
		if (validation()) {
			String manv = txtTenDangNhap.getText();
			String matkhau = new String(txtMatKhauHienTai.getPassword());
			String matkhaumoi = new String(txtMatKhauMoi.getPassword());
			String xacNhanPass = new String(txtXacNhanMatKhau.getPassword());
			if (!manv.equalsIgnoreCase(Auth.user.getMaNV())) {
				MsgBox.alert(this, "Sai tên đăng nhập!!!");
			} else if (!matkhau.equals(Auth.user.getPass())) {
				MsgBox.alert(this, "Sai mật khẩu!!!");
			} else if (!matkhaumoi.equals(xacNhanPass)) {
				MsgBox.alert(this, "Mật khẩu xác nhận không khớp!!!");
			} else {
				Auth.user.setPass(matkhaumoi);
				dao.update(Auth.user);
				MsgBox.alert(this, "Đổi mật khẩu thành công!");
				clearForm();
			}
		}
	}

	public boolean validation() {
		if (txtTenDangNhap.getText().equals("")) {
			MsgBox.alert(this, "Vui nhập tên!!!");
			return false;
		}
		if (txtMatKhauHienTai.getPassword().equals("")) {
			MsgBox.alert(this, "Vui lòng không được bỏ trống!!!");
			return false;
		}
		if (txtMatKhauMoi.getPassword().equals("")) {
			MsgBox.alert(this, "Vui lòng không được bỏ trống!!!");
			return false;
		}
		if (txtXacNhanMatKhau.getPassword().equals("")) {
			MsgBox.alert(this, "Vui lòng không được bỏ trống!!!");
			return false;
		}
		return true;
	}

	public DoiPassJDialog(JFrame parent, boolean modal) {
		super(parent, modal);
		setBounds(100, 100, 450, 278);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		txtTenDangNhap = new JTextField();
		txtTenDangNhap.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		txtTenDangNhap.setBounds(10, 86, 191, 27);
		contentPanel.add(txtTenDangNhap);
		txtTenDangNhap.setColumns(10);

		JLabel lblDoiMatKhau = new JLabel("ĐỔI MẬT KHẨU");
		lblDoiMatKhau.setForeground(new Color(0, 128, 64));
		lblDoiMatKhau.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblDoiMatKhau.setBounds(10, 10, 150, 36);
		contentPanel.add(lblDoiMatKhau);

		JLabel lblTenDangNhap = new JLabel("Tên đăng nhập");
		lblTenDangNhap.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		lblTenDangNhap.setBounds(10, 63, 89, 13);
		contentPanel.add(lblTenDangNhap);

		JLabel lblMatKhauMoi = new JLabel("Mật khẩu mới");
		lblMatKhauMoi.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		lblMatKhauMoi.setBounds(10, 132, 89, 13);
		contentPanel.add(lblMatKhauMoi);

		JLabel lblXacNhanMatKhau = new JLabel("Xác nhận mật khẩu mới");
		lblXacNhanMatKhau.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		lblXacNhanMatKhau.setBounds(235, 132, 141, 13);
		contentPanel.add(lblXacNhanMatKhau);

		JLabel lblMatKhauHienTai = new JLabel("Mật khẩu hiện tại");
		lblMatKhauHienTai.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		lblMatKhauHienTai.setBounds(235, 63, 113, 13);
		contentPanel.add(lblMatKhauHienTai);

		JButton btnHuyBo = new JButton("Hủy bỏ");
		btnHuyBo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openHuyBo();
			}
		});
		btnHuyBo.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		btnHuyBo.setIcon(new ImageIcon("C:\\Users\\thuon\\eclipse-workspace\\EduSys\\src\\icon\\No.png"));
		btnHuyBo.setBounds(313, 192, 113, 33);
		contentPanel.add(btnHuyBo);

		JButton btnDongY = new JButton("Đồng ý");
		btnDongY.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		btnDongY.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doiMatKhau();
			}
		});
		btnDongY.setIcon(new ImageIcon("C:\\Users\\thuon\\eclipse-workspace\\EduSys\\src\\icon\\Refresh.png"));
		btnDongY.setBounds(195, 192, 108, 33);
		contentPanel.add(btnDongY);

		txtMatKhauHienTai = new JPasswordField();
		txtMatKhauHienTai.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		txtMatKhauHienTai.setBounds(235, 90, 191, 27);
		contentPanel.add(txtMatKhauHienTai);

		txtXacNhanMatKhau = new JPasswordField();
		txtXacNhanMatKhau.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		txtXacNhanMatKhau.setBounds(235, 155, 191, 27);
		contentPanel.add(txtXacNhanMatKhau);

		txtMatKhauMoi = new JPasswordField();
		txtMatKhauMoi.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		txtMatKhauMoi.setBounds(10, 155, 191, 27);
		contentPanel.add(txtMatKhauMoi);
	}
}
