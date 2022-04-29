/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/* ListDemo.java requires no other files. */
public class JListDemoGeneric extends JPanel implements ActionListener {

    // define a JList and a DefaultListModel
    JList<String> list;
    DefaultListModel<String> listModel;
    JTextField text;
    JButton addBtn, removeBtn;

    public JListDemoGeneric() {
        super(new BorderLayout());

        // create initial listModel
        listModel = new DefaultListModel<>();

        // Create the list and put it in a scroll pane.
        list = new JList<String>(listModel);
        listModel.addElement("Marc");
        listModel.addElement("Marina");

        JScrollPane listScrollPane = new JScrollPane(list);

        add(listScrollPane, BorderLayout.CENTER);

        JPanel bar = new JPanel();
        bar.setLayout(new BoxLayout(bar, BoxLayout.X_AXIS));

        text = new JTextField();
        addBtn = new JButton("ADD");
        removeBtn = new JButton("Remove");

        // Add ActionListeners
        addBtn.addActionListener(this);
        removeBtn.addActionListener(this);

        bar.add(text);
        bar.add(addBtn);
        bar.add(removeBtn);

        add(bar, BorderLayout.SOUTH);
    }

    /**
     * Create the GUI and show it. For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     * 
     * @throws UnsupportedLookAndFeelException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws ClassNotFoundException
     */
    private static void createAndShowGUI() {

        // Set the look and feel.
        JFrame.setDefaultLookAndFeelDecorated(true);

        // Make sure we have nice window decorations.

        // Create and set up the window.
        JFrame frame = new JFrame("ListDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create and set up the content pane.
        JComponent newContentPane = new JListDemoGeneric();
        newContentPane.setOpaque(true); // Content panes must be opaque

        frame.setContentPane(newContentPane);

        // Display the window.
        frame.setSize(300, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    public static void main(String[] args) {
        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();

            }
        });
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(addBtn)) {

            if (!listModel.contains(text.getText())) {
                listModel.addElement(text.getText());
                text.setText("");
            }

        } else {
            if (listModel.contains(text.getText())) {
                listModel.removeElement(text.getText());
                text.setText("");
            }
        }
    }

}
