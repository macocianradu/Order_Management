package Presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ErrorWindow {
    public ErrorWindow(String error){
        final JFrame frame = new JFrame("error");
        JPanel panel = new JPanel(new GridBagLayout());
        JButton ok = new JButton("OK");
        JLabel msg = new JLabel(error);
        msg.setForeground(Color.red);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(240, 180);
        frame.setLocationRelativeTo(null);
        frame.setContentPane(panel);
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        panel.add(new Label(error), c);
        c.gridy = 1;
        panel.add(ok, c);
        ok.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        frame.pack();
    }
}
