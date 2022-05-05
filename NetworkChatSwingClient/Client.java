import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import java.awt.BorderLayout;
public class Client {
    static final String APP_NAME = "Xat SAD";

    public static void main(String[] args) throws NumberFormatException, UnknownHostException, IOException {

        MySocket sc = new MySocket(args[0], Integer.parseInt(args[1]));

        DefaultListModel<String> listModel = new DefaultListModel<>();

        JList messageList = new JList<>(listModel);

        JFrame frame = new JFrame(APP_NAME);

        JPanel panel = new JPanel();

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(messageList);
        messageList.setLayoutOrientation(JList.VERTICAL);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);



        JPanel writingBar = new JPanel(new BorderLayout());
        JTextField textField = new JTextField();
        JButton sendBtn = new JButton("SEND");

        writingBar.add(textField, BorderLayout.CENTER);
        writingBar.add(sendBtn, BorderLayout.EAST);

        JTextField nickField = new JTextField(10);
        JLabel nickLabel = new JLabel("Nick:");
		

        JButton loginBtn = new JButton("Login");

		panel.add(nickLabel);
        panel.add(nickField);
        panel.add(loginBtn);

        frame.add(panel);

        frame.setSize(300, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        sendBtn.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread() {
                    public void run() {
                        String line = textField.getText();
                        listModel.addElement(line);
                        sc.println(line);
                        textField.setText("");
                    }
                }.start();
            }
        });

        loginBtn.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                String nick = nickField.getText();
                sc.println(nick);
                new Thread() {
                    public void run() {
                        JLabel status = new JLabel("Connected as: " + nick + " to " + args[0] +":" + args[1]);
                        panel.removeAll();
                        panel.setLayout(new BorderLayout());
                        panel.revalidate();
                        panel.repaint();
                        panel.add(status, BorderLayout.PAGE_START);
                        panel.add(scrollPane, BorderLayout.CENTER);
                        panel.add(writingBar, BorderLayout.SOUTH);
                    }
                }.start();

            }
        });

        // Input Thread

        // Output Thread
        new Thread() {
            public void run() {
                String line;
                while ((line = sc.readLine()) != null) {
                    // Fem echo del que hem rebut
                    listModel.addElement(line);
                }
                // Al xat hauriem de fer el Close for write

            }
        }.start();

    }
}
