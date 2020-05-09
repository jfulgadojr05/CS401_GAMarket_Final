package gui;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;



/* Message GUI class
	CS 401 - Final Project
	MessageGUI.java
  By: Christian Magpantay
  Code/Book Reference -
  https://www.youtube.com/watch?v=CqWorn8dR_A&list=PLdmXYkPMWIgCocLY-B4SvpQshQWC7Nc0C&index=5
  https://docs.oracle.com/javase/7/docs/api/javax/swing/JList.html
  https://www.tutorialspoint.com/how-can-we-add-insert-a-jbutton-to-jtable-cell-in-java
*/

// note jmodel
public class MessageGUI extends JPanel {
    private static final long serialVersionUID = 1L;
    JPanel messagePanel = new JPanel();
    String[] messageColumn = {"Messages"};
    JTable table;
    JScrollPane scroll;

    JTextField input = new JTextField();
    JButton chatBubble = new JButton();
    
    TableCellRenderer tableRenderer;

    public MessageGUI() {
        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));
        table = new JTable(new JTableButtonModel());
        scroll = new JScrollPane(table);
        tableRenderer = table.getDefaultRenderer(JButton.class);
        table.getColumn("Messages").setCellRenderer(tableRenderer);
        table.setDefaultRenderer(JButton.class, new JTableButtonRenderer(tableRenderer));
        table.setBackground(new Color(255,255,255));

        chatBubble.setHorizontalAlignment(SwingConstants.RIGHT);
        chatBubble.setForeground(Color.WHITE);
        chatBubble.setBackground(new Color(4, 151, 251));
        chatBubble.setOpaque(true);
        chatBubble.setBorderPainted(false);

        input.setMaximumSize(
            new Dimension(input.getMaximumSize().width, 
                        50)
        );
        input.setPreferredSize(
            new Dimension(input.getMaximumSize().width, 
                25)
        );

        input.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                messagePanel.removeAll();
                messagePanel.revalidate();
                messagePanel.repaint();

                String text = input.getText();
                chatBubble.setText("chrisTaro: " + text);
                table.add(chatBubble);
                input.setText("");

                messagePanel.add(scroll);
                messagePanel.add(input);
                messagePanel.revalidate();
                messagePanel.repaint();
            }
        });

        messagePanel.add(scroll);
        messagePanel.add(input);

        this.setLayout(new BorderLayout());
        this.add(messagePanel, BorderLayout.CENTER);
        this.setVisible(true);
    }
    
}

    class JTableButtonRenderer implements TableCellRenderer {
        private TableCellRenderer defaultRenderer;
        public JTableButtonRenderer(TableCellRenderer renderer) {
        defaultRenderer = renderer;
        }
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if(value instanceof Component)
            return (Component)value;
            return defaultRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }

    class JTableButtonModel extends AbstractTableModel {
        /**
         *
         */
        private static final long serialVersionUID = 1L;
        private Object[][] rows = {{}};
        private String[] columns = {"Messages"};
        public String getColumnName(int column) {
            return columns[column];
        }


    @Override
    public int getRowCount() {
        return rows.length;
    }
    @Override
    public int getColumnCount() {
        return columns.length;
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return rows[rowIndex][columnIndex];
    }
}