package AdvanceInterface;


import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.*;
//import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
//import javax.swing.text.DefaultCaret;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;

public class ChatWindow extends JFrame{

    private JPanel contentPane;
    private JTextField txtMessage;
    private JTextArea history;
    //private DefaultCaret caret;
    private static final String CHAT_CLIENT = "Chat Client";
    private static String fileName = "src/AdvanceInterface/history.txt";


    public ChatWindow()  {
        creatingWindow();
        try {
            history.setText(Files.read(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void creatingWindow() {
        /*try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e1) {
            e1.printStackTrace();
        }*/
        setTitle(CHAT_CLIENT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(880, 570);
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        GridBagLayout gbl_contentPane = new GridBagLayout();
        gbl_contentPane.columnWidths = new int[]{28, 815, 30, 7}; // SUM = 880
        gbl_contentPane.rowHeights = new int[]{35, 475, 40}; // SUM = 550
        contentPane.setLayout(gbl_contentPane);

        history = new JTextArea();
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
        scrollConstraints.insets = new Insets(0, 5, 0, 0); // отступы
        contentPane.add(scroll, scrollConstraints);

        txtMessage = new JTextField();
        txtMessage.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
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

    private void send(String message){
        if(message.equals("")) return;
        console(message);
        txtMessage.setText("");
    }

    private void console(String msg){
        msg += System.lineSeparator();
        history.append(msg);
        history.setCaretPosition(history.getDocument().getLength()); // это позволяет переводить каретку в JTextArea каждый раз когда мы отправляем сообщение
        try {
            Files.update(fileName, msg);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
