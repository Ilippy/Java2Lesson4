package AdvanceInterface;


import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.awt.Font;

public class ChatWindow extends JFrame implements Runnable {

	private JPanel contentPane;
	private JTextField txtMessage;
	private JTextArea history;
	private JMenuBar menuBar;
	private JMenu mnFile;
	private JMenuItem mntmDeleteFile;
	private JMenuItem mntmExit;
	Font txtFont = new Font("Calibri", Font.PLAIN, 16);
	//private DefaultCaret caret;

	private Thread run, checkFileSize;
	private boolean running = false;
	private Files file;
	private double fileSize;
	private String fileName;
	private String login;

	private static final String CHAT_CLIENT = "Chat Client";
	private static final String CHECK_FILE_SIZE = "Check file size";
	private static final String RUNNING = "Running";
	private static final String FILE = "File";
	private static final String EXIT = "Exit";

	public ChatWindow(String login, String shortFileName) {
		this.login = login;
		this.fileName = "src/AdvanceInterface/" + shortFileName + ".txt";
		creatingWindow();
		file = new Files(fileName);
		try {
			history.setText(file.read());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		fileSize = file.checkFileSize();
		running = true;
		run = new Thread(this, RUNNING);
		run.start();
	}

	private void creatingWindow() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e1) {
            e1.printStackTrace();
        }
		setTitle(CHAT_CLIENT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(880, 570);
		setLocationRelativeTo(null);  // выводит окно по центру

		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		mnFile = new JMenu(FILE);
		menuBar.add(mnFile);
		mntmDeleteFile = new JMenuItem("Delete File");
		mntmDeleteFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				file.delete();
			}
		});
		mnFile.add(mntmDeleteFile);
		mntmExit = new JMenuItem(EXIT);
		mntmExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnFile.add(mntmExit);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{28, 815, 30, 7}; // SUM = 880
		gbl_contentPane.rowHeights = new int[]{35, 475, 40}; // SUM = 550
		contentPane.setLayout(gbl_contentPane);

		history = new JTextArea();
		history.setFont(txtFont);
		history.setEditable(false);
		JScrollPane scroll = new JScrollPane(history);
		/*caret = (DefaultCaret) history.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE); */
		GridBagConstraints scrollConstraints = new GridBagConstraints();
		scrollConstraints.insets = new Insets(0, 0, 5, 5);
		scrollConstraints.fill = GridBagConstraints.BOTH;
		scrollConstraints.gridx = 0;  // выбор столбца из массива {28, 815, 30, 7} (т.е будет 28)
		scrollConstraints.gridy = 0;  // выбор строки из массива {35, 475, 40} (т.е будет 35)
		scrollConstraints.gridwidth = 3; //обединяем 3 столбца и ширина столбца будет 28 + 815 + 30
		scrollConstraints.gridheight = 2; //обединяем 2 строки и высота строки будет 35 + 475
		scrollConstraints.weightx = 1; // маштабируемый по х при 1
		scrollConstraints.weighty = 1; // маштабируемый по у при 1
//        scrollConstraints.insets = new Insets(0, 5, 0, 0); // отступы
		contentPane.add(scroll, scrollConstraints);

		txtMessage = new JTextField();
		txtMessage.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					send(txtMessage.getText());
				}
			}
		});
		GridBagConstraints gbc_txtMessage = new GridBagConstraints();
		gbc_txtMessage.insets = new Insets(0, 0, 0, 5);
		gbc_txtMessage.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtMessage.gridx = 0;
		gbc_txtMessage.gridy = 2;
		gbc_txtMessage.gridwidth = 2;
		gbc_txtMessage.weightx = 1; // маштабируемый по х
		gbc_txtMessage.weighty = 0; // не маштабируемый по у при 0 (дефолтное значение 0, можно не писать)
		contentPane.add(txtMessage, gbc_txtMessage);
		txtMessage.setColumns(10);


		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				send(txtMessage.getText());
			}
		});
		GridBagConstraints gbc_btnSend = new GridBagConstraints();
		gbc_btnSend.insets = new Insets(0, 0, 0, 5);
		gbc_btnSend.gridx = 2;
		gbc_btnSend.gridy = 2;
		gbc_btnSend.weightx = 0; // не маштабируемый по x при 0 (дефолтное значение 0, можно не писать)
		gbc_btnSend.weighty = 0; // не маштабируемый по у при 0 (дефолтное значение 0, можно не писать)

		contentPane.add(btnSend, gbc_btnSend);

		setVisible(true);
		txtMessage.requestFocusInWindow();
	}

	private void send(String message) {
		if (message.equals("")) return;
		message= login+": "+message+ System.lineSeparator();
		history.setCaretPosition(history.getDocument().getLength()); // это позволяет переводить каретку в JTextArea каждый раз когда мы отправляем сообщение
		try {
			file.update(message);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		txtMessage.setText("");
	}


	@Override
	public void run() {
		checkFileSize();
	}

	//TODO выяснить почему при отправке сообщения file.checkFileSize() = 0, а потом уже реальному значению.
	private void checkFileSize() {
		checkFileSize = new Thread(CHECK_FILE_SIZE) {
			public void run() {
				while (running) {
					double newFileSize = file.checkFileSize();
					if (newFileSize != fileSize) {
						System.out.println("old:" + fileSize + " new:" + newFileSize);  // для тестирования
						if (newFileSize == 0.0) {
							history.setText("");
							fileSize = 0;
						} else {
							try {
								history.setText(file.read());
								fileSize = newFileSize;
							} catch (FileNotFoundException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		};
		checkFileSize.start();
	}
}
