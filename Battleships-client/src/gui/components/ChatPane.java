package gui.components;

import gui.layout.CWTabbedPaneUI;

import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout.Alignment;

import rmi.client.ClientGameController;
import domain.ChatModel;
import domain.GlobalChatModel;
import domain.TeamChatModel;
import domain.util.Model;
import domain.util.NotifyPropertyChangedAdapter;

@SuppressWarnings("serial")
public final class ChatPane extends JTabbedPane
{
    public ChatPane(final ClientGameController controller)
    {
        addTab("Global", new ChatTab(this, new GlobalChatModel(controller)));
        addTab("Team", new ChatTab(this, new TeamChatModel(controller)));
        setUI(new CWTabbedPaneUI());
    }

    public final class ChatTab extends JPanel
    {
        private final JTextArea _text;
        private final JScrollPane _textContainer;
        private final JTextField _input;
        private final JButton _submit;

        public ChatTab(final JTabbedPane parent, final ChatModel model)
        {
            _text = new JTextArea();
            _text.setMargin(new Insets(5, 5, 5, 5));
            _text.setEditable(false);
            _text.setOpaque(false);
            _textContainer = new JScrollPane(_text);
            _textContainer.setBorder(BorderFactory.createLineBorder(Color.darkGray));
            _textContainer.setHorizontalScrollBar(null);
            _input = new JTextField();
            _input.setBorder(BorderFactory.createLineBorder(Color.darkGray));
            _submit = new JButton("Send");
            _submit.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.darkGray), BorderFactory.createEmptyBorder(4, 4, 4, 4)));

            final GroupLayout layout = new GroupLayout(this);
            layout.setAutoCreateGaps(true);
            layout.setAutoCreateContainerGaps(true);
            layout.setHorizontalGroup(layout.createParallelGroup()
                    .addGroup(layout.createSequentialGroup()
                            .addComponent(_input)
                            .addComponent(_submit))
                     .addComponent(_textContainer));
            layout.setVerticalGroup(layout
                    .createSequentialGroup()
                    .addGroup(layout.createParallelGroup(Alignment.CENTER)
                            .addComponent(_input, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(_submit, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
                     .addComponent(_textContainer));
            layout.linkSize(SwingConstants.VERTICAL, _input, _submit);
            this.setLayout(layout);
            
            _submit.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    final String message = _input.getText().trim();
                    if (!message.equals(""))
                    {
                        model.sendChatMessage(message);
                    }
                }
            });
            model.addObserver(new NotifyPropertyChangedAdapter() 
            {
                @Override
                public void changed(Model m)
                {
                    final List<String> messages = model.getMessages();
                    _text.append(messages.get(messages.size() - 1) + "\n");
                    _text.setCaretPosition(_text.getDocument().getLength());
                    parent.setSelectedComponent(ChatTab.this);
                    _input.setText("");
                }
            });
        }
    }
}