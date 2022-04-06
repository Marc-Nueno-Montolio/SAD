import java.awt.LayoutManager;

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

    public static void main(String[] args) {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        listModel.addElement("Marc: hello");

        JList messageList = new JList<>(listModel);

        JFrame frame = new JFrame(APP_NAME);

        JPanel panel = new JPanel(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(messageList);
        messageList.setLayoutOrientation(JList.VERTICAL);
        

        JPanel writingBar = new JPanel(new BorderLayout());
        JTextField textField = new JTextField(); 
        JButton sendBtn = new JButton("SEND");
        
        writingBar.add(textField, BorderLayout.CENTER);
        writingBar.add(sendBtn, BorderLayout.EAST);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(writingBar, BorderLayout.SOUTH);
        frame.add(panel);
        frame.setSize(200, 300);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
}
