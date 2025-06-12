package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReloadButton extends JButton {
    public ReloadButton(Runnable onClick) {
        super("Recomeçar");
        setFont(new Font("Arial", Font.BOLD, 16));
        setBackground(Color.LIGHT_GRAY);
        setFocusPainted(false);

        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onClick.run();
            }
        });
    }
}
