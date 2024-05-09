package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.xml.crypto.AlgorithmMethod;

import utils.XImage;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;

import javax.swing.ImageIcon;
import javax.swing.JProgressBar;

public class HelloJDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JProgressBar pgbHello;

	public static void main(String[] args) {
		try {
			HelloJDialog dialog = new HelloJDialog();
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
		setTitle("HỆ THỐNG QUẢN LÝ ĐÀO TẠO EDUSYS");
		 new javax.swing.Timer(20, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int value = pgbHello.getValue();
				if (value < 100) {
					pgbHello.setValue(value + 1);
				} else {
					HelloJDialog.this.dispose();
				}
			}
		}).start();		
	}

	
	public HelloJDialog() {
		setBounds(100, 100, 595, 318);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblLogo = new JLabel("");
		lblLogo.setIcon(new ImageIcon(HelloJDialog.class.getResource("/icon/poly.png")));
		lblLogo.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		lblLogo.setBounds(10, 10, 561, 222);
		contentPanel.add(lblLogo);

		pgbHello = new JProgressBar();
		pgbHello.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		pgbHello.setBounds(0, 252, 581, 29);
		contentPanel.add(pgbHello);
	}
}
