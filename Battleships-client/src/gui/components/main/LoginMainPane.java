package gui.components.main;

import java.awt.Font;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.rmi.NotBoundException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import rmi.client.ClientGameController;

@SuppressWarnings("serial")
public class LoginMainPane extends JPanel
{
    private final Label _lblTitle;
    private final Label _lblSubTitle;
    private final Label _lblHost;
    private final JTextField _txtHost;
    private final Label _lblName;
    private final JTextField _txtName;
    private final JButton _btnSubmit;

    public LoginMainPane(final ClientGameController controller)
    {
        _lblTitle = new Label("LOBBY");
        _lblTitle.setAlignment(Label.CENTER);
        _lblTitle.setFont(getFont().deriveFont(36f).deriveFont(Font.BOLD));
        
        _lblSubTitle = new Label("select a server");
        _lblSubTitle.setAlignment(Label.CENTER);
        _lblSubTitle.setFont(getFont().deriveFont(32f));
        
        _lblHost = new Label("Host");
        _txtHost = new JTextField("localhost:1099");
        _txtHost.setHorizontalAlignment(JTextField.RIGHT);
        
        _lblName = new Label("Name");
        _txtName = new JTextField("John");
        _txtName.setHorizontalAlignment(JTextField.RIGHT);
        
        _btnSubmit = new JButton("Connect");
        _btnSubmit.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                final String host = _txtHost.getText().split(":")[0];
                final int port = Integer.parseInt(_txtHost.getText().split(":")[1]);
                final String name = _txtName.getText();
                try
                {
                    controller.connect(host, port, name);
                }
                catch (IOException ex)
                {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Unable to connect to the server", JOptionPane.ERROR_MESSAGE);
                }
                catch (NotBoundException ex)
                {
                    JOptionPane.showMessageDialog(null, "The server is not accepting any connections.", "Unable to connect to the server", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        final GroupLayout layout = new GroupLayout(this);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(layout
                .createParallelGroup(Alignment.CENTER)
                .addComponent(_lblTitle)
                .addComponent(_lblSubTitle)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(_lblHost, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(_txtHost, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, 200))
                .addGroup(layout.createSequentialGroup()
                        .addComponent(_lblName, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(_txtName, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, 200))
                .addComponent(_btnSubmit));
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(_lblTitle)
                .addComponent(_lblSubTitle)
                .addGroup(layout.createParallelGroup()
                        .addComponent(_lblHost, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(_txtHost, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup()
                        .addComponent(_lblName, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(_txtName, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
                .addComponent(_btnSubmit, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE));
        layout.linkSize(SwingConstants.HORIZONTAL, _lblHost, _lblName);
        this.setLayout(layout);
    }
}
