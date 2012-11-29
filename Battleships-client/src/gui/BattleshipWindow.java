package gui;

import java.awt.Dimension;
import java.awt.Toolkit;

import gui.components.MainPane;

import javax.swing.JFrame;

import rmi.client.ClientGameController;

@SuppressWarnings("serial")
public class BattleshipWindow extends JFrame
{
    public BattleshipWindow(final ClientGameController controller)
    {
        this.setTitle("Battleships");
        this.setContentPane(new MainPane(controller));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(getRootPane());
        this.setVisible(true);
        this.pack();

        // center the window center screen
        final Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((dim.width - getSize().width) / 2, (dim.height - getSize().height) / 2);
    }
}