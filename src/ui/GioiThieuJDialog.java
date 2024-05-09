package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class GioiThieuJDialog extends JDialog {

	public static void main(String[] args) {
		try {
            GioiThieuJDialog dialog = new GioiThieuJDialog(new EduSysJFrame(), true);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			dialog.setLocationRelativeTo(null);
			dialog.setTitle("GIỚI THIỆU EDUSYS");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public GioiThieuJDialog(EduSysJFrame parent, boolean modal) {
		super(parent, modal);
		setBounds(100, 100, 600, 416);
		getContentPane().setLayout(null);
		
		JLabel lblLogo = new JLabel("");
		lblLogo.setIcon(new ImageIcon("C:\\Users\\thuon\\eclipse-workspace\\EduSys\\src\\icon\\poly.png"));
		lblLogo.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		lblLogo.setBounds(10, 10, 561, 222);
		getContentPane().add(lblLogo);
		{
			JTextPane txtIntro = new JTextPane();
			txtIntro.setEnabled(false);
			txtIntro.setBounds(56, 228, 500, 141);
			getContentPane().add(txtIntro);
			txtIntro.setText("Polypro là dự án mẫu. Mục tiêu chính là huấn luyện sinh viên qui trình thực hiện dự án. Mục tiêu của dự án này là để rèn luyện kỹ năng IO (CDIO) tức không yêu cầu sinh viên phải thu thập phân tích mà chỉ  thực hiện và vận hành một phần mềm chuẩn bị cho các dự án sau này. Các kỹ năng CD (trong CDIO) sẽ được huấn luyện ở dự án 1 và dự án 2. \r\nYêu cầu về môi trường:\r\n1. Hệ điều hành bất kỳ\r\n2. JDK 1.8 trở lên\r\n3. SQL Server 2008 trở lên");
			txtIntro.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		}
	}
}
