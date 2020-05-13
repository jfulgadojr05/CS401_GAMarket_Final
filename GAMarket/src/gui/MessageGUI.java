package gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import db.DBHelper;
/* Message GUI class
	CS 401 - Final Project
	MessageGUI.java
  By: Christian Magpantay
  Code/Book Reference -
  https://www.youtube.com/watch?v=CqWorn8dR_A&list=PLdmXYkPMWIgCocLY-B4SvpQshQWC7Nc0C&index=5
  https://docs.oracle.com/javase/7/docs/api/javax/swing/JList.html
   https://docs.oracle.com/javase/7/docs/api/javax/swing/JList.html#renderer
*/

public class MessageGUI extends JPanel {
    private static final long serialVersionUID = 1L;
    JPanel messagePanel = new JPanel();
    JScrollPane scroll;
    JTextField input = new JTextField();
    JList<String> mList;

    public MessageGUI(DBHelper dbh, String friend) throws SQLException {
        String sender = "chrisTaro";
        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));

        input.setMaximumSize(new Dimension(input.getMaximumSize().width, 50));
        input.setPreferredSize(new Dimension(input.getMaximumSize().width, 25));
        mList = new JList<>();
        mList = dbh.getAllMessages(sender, friend);

        input.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                messagePanel.removeAll();
                messagePanel.revalidate();
                messagePanel.repaint();

                String text = input.getText();
                String newInput = sender + ": " + text;
                try {
                    dbh.sendMessage(sender, friend, newInput);
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                try {
                    mList = dbh.getAllMessages(sender, friend);
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                input.setText("");
                mList.setCellRenderer(new MyCellRenderer());
                scroll = new JScrollPane(mList);
                messagePanel.add(scroll);
                messagePanel.add(input);
                messagePanel.revalidate();
                messagePanel.repaint();
            }
        });

        mList.setCellRenderer(new MyCellRenderer());
        scroll = new JScrollPane(mList);
        messagePanel.add(scroll);
        messagePanel.add(input);
        this.setLayout(new BorderLayout());
        this.add(messagePanel, BorderLayout.CENTER);
        this.setVisible(true);
    }


    class MyCellRenderer extends JLabel implements ListCellRenderer<Object> {
        /**
         *
         */
        private static final long serialVersionUID = 1L;
   
        // This is the only method defined by ListCellRenderer.
        // We just reconfigure the JLabel each time we're called.
   
        public Component getListCellRendererComponent(
          JList<?> list,           // the list
          Object value,            // value to display
          int index,               // cell index
          boolean isSelected,      // is the cell selected
          boolean cellHasFocus)    // does the cell have focus
        {
            Border paddingBorder = BorderFactory.createEmptyBorder(2, 3, 2, 3);
            String s = value.toString();
            setText(s);
            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(new Color(4, 151, 251));
                setForeground(Color.WHITE);
                setBorder(paddingBorder);
                setHorizontalAlignment(SwingConstants.RIGHT);
            }
            setEnabled(list.isEnabled());
            setFont(list.getFont());
            setOpaque(true);
            return this;
        }
    }
   
    
}