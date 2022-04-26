package start;

/*
 * HelloWorldSwing.java requires no other files. 
 */
import java.awt.*;
import javax.swing.*;        

public class HelloWorldSwing {
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {

        String[] fonts = {"Verdana", "Times New Roman"};

        //Create and set up the window. Exit on close
        JFrame frame = new JFrame("Hello World Swing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add the ubiquitous "Hello World" label to frame.
        JLabel label = new JLabel("Hello World", SwingConstants.CENTER);

        String getFont = (String) JOptionPane.showInputDialog(
            null,
            "What fruit do you like the most?",
            "Choose Fruit",
            JOptionPane.QUESTION_MESSAGE,
            null,
            fonts,
            fonts[3]);

        

        frame.add(label, 0);

        label.setFont(new Font("Verdana", Font.PLAIN, 18));

        //Display the window. set size and center
        frame.setSize(300,300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
