package ui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dao.NhanVienDAO;
import entity.NhanVien;
import utils.Auth;
import utils.MsgBox;
import utils.XImage;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginJDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtTenLogin;
	private JPasswordField txtPass;
	NhanVienDAO dao = new NhanVienDAO();

	public static void main(String[] args) {
		try {
			LoginJDialog dialog = new LoginJDialog();
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
		setTitle("EDUSYS ĐĂNG NHẬP HỆ THỐNG");
	}

	void dangNhap() {
		if (validation()) {
		String maNV = txtTenLogin.getText();
		String pass = new String(txtPass.getPassword());
		NhanVien nv = dao.selectById(maNV);
		if (nv == null) {
			MsgBox.alert(this, "Sai tên đăng nhập!!");
		}else {
			if (!nv.getPass().equalsIgnoreCase(pass)) {
				MsgBox.alert(this, "Sai mật khẩu!!");

			}else {
				Auth.user= nv;
				this.dispose();
			}
		}
		}
	}

	void ketThuc() {
		if (MsgBox.confirm(this, "Bạn muốn kết thúc ứng dụng??")) {
			System.out.println("lỗi");
			System.exit(0);
		}
	}

	public boolean validation() {
		if (txtTenLogin.getText().equals("")) {
			MsgBox.alert(this, "Vui nhập tên!!!");
			return false;
		}
		if (txtPass.getPassword().equals("")) {
			MsgBox.alert(this, "Vui lòng nhập mật khẩu!!!");
			return false;
		}
		return true;
	}
	
	public LoginJDialog() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblAnh = new JLabel("");
			lblAnh.setIcon(new ImageIcon(LoginJDialog.class.getResource("/icon/trump-small.png")));
			lblAnh.setFont(new Font("Times New Roman", Font.PLAIN, 10));
			lblAnh.setBounds(42, 42, 111, 179);
			contentPanel.add(lblAnh);
		}
		{
			JLabel lblTenLogin = new JLabel("Tên Đăng Nhập:");
			lblTenLogin.setFont(new Font("Times New Roman", Font.PLAIN, 13));
			lblTenLogin.setBounds(183, 44, 98, 13);
			contentPanel.add(lblTenLogin);
		}

		txtTenLogin = new JTextField();
		txtTenLogin.setText("HNHT1");
		txtTenLogin.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		txtTenLogin.setBounds(185, 67, 241, 35);
		contentPanel.add(txtTenLogin);
		txtTenLogin.setColumns(10);

		JLabel lblPass = new JLabel("Mật Khẩu:");
		lblPass.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		lblPass.setBounds(183, 111, 98, 13);
		contentPanel.add(lblPass);

		JButton btnLogin = new JButton("Đăng Nhập");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dangNhap();
			}
		});
		btnLogin.setIcon(new ImageIcon(LoginJDialog.class.getResource("/icon/Key.png")));
		btnLogin.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		btnLogin.setBounds(183, 186, 118, 50);
		contentPanel.add(btnLogin);

		JButton btnExit = new JButton("Thoát");
		btnExit.setIcon(new ImageIcon(LoginJDialog.class.getResource("/icon/Log out.png")));
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ketThuc();
			}
		});
		btnExit.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		btnExit.setBounds(311, 186, 115, 50);
		contentPanel.add(btnExit);

		txtPass = new JPasswordField();
		txtPass.setText("123");
		txtPass.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		txtPass.setBounds(184, 133, 242, 28);
		contentPanel.add(txtPass);
	}
}
