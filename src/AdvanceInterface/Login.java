package AdvanceInterface;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {

	private static final String LOGIN_LABEL = "Login";
	private static final String DEFAULT_TXT_NAME = "history";
	private JPanel contentPane;
	private JTextField txtLogin;
	private JLabel lblLogin;
	private JTextField txtFileName;
	private JLabel lblFileName;
	private Font txtFont = new Font("SansSerif", Font.BOLD, 20);


	public Login() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		setResizable(false);
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 250);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		txtLogin = new JTextField();
		txtLogin.setBounds(67, 50, 165, 28);
		//txtLogin.setFont();
		contentPane.add(txtLogin);
		txtLogin.setColumns(10);

		lblLogin = new JLabel(LOGIN_LABEL);
		lblLogin.setBounds(128, 25, 45, 18);
		lblLogin.setFont(lblLogin.getFont().deriveFont(16f));
		contentPane.add(lblLogin);

		txtFileName = new JTextField();
		txtFileName.setBounds(67, 116, 165, 28);
		txtFileName.setText(DEFAULT_TXT_NAME);
		contentPane.add(txtFileName);
		txtFileName.setColumns(10);

		lblFileName = new JLabel("File Name");
		lblFileName.setBounds(111, 96, 77, 18);
		lblFileName.setFont(lblFileName.getFont().deriveFont(16f));
		contentPane.add(lblFileName);


		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String login = txtLogin.getText();
				String fileName = txtFileName.getText();
				login(login,fileName);
			}
		});
		/*btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = txtLogin.getText();
				String address = txtFileName.getText();
				int port = Integer.parseInt(txtPort.getText());
				login(name, address, port);
			}
		});*/
		btnLogin.setBounds(91, 180, 117, 29);
		contentPane.add(btnLogin);
		setVisible(true);

	}

	private void login(String login, String fileName) {
		dispose();
		new ChatWindow(login,fileName);
	}

	public static void main(String[] args) {
		new Login();
		//new ChatWindow();
	}
}
