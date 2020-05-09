package gui;
/* Friends List GUI class
	CS 401 - Final Project
	FriendsGUI.java
  By: Christian Magpantay
  Code/Book Reference -
  https://www.youtube.com/watch?v=CqWorn8dR_A&list=PLdmXYkPMWIgCocLY-B4SvpQshQWC7Nc0C&index=5
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import db.DBHelper;

public class FriendsGUI extends JFrame {

    private static final long serialVersionUID = 1L;
    Container c;
    JMenuBar menuBarChat;
    JMenu username;
    JMenu editFriends;
    JMenu settings;
    JMenuItem addFriend;
    JTabbedPane chat;
    JList<String> online;
    JList<String> allFriends;
    ActionListener toAddFriend;
    JMenuItem viewProfile;
    JMenuItem editProfile;

    String[] friendsMockListOnline = { "TheToadKing", "Pokimane", "Maximilian_DOOD" };
    String[] emptyList = { "Empty" };

    public FriendsGUI(DBHelper mydb) throws SQLException {
        super("Friends List"); // title
        setSize(300, 500);
        Panel c = new Panel();
        c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));
        setContentPane(c);
        // create menu for chat
        MenuBar(mydb);
        ChatTabs(mydb);
        // settings
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void MenuBar(DBHelper mydb) {
        menuBarChat = new JMenuBar();
        // username menu
        username = new JMenu("chrisTaro");
        username.setMnemonic(KeyEvent.VK_A);
        viewProfile = new JMenuItem("View Profile");
        editProfile = new JMenuItem("Edit Profile");
        username.add(viewProfile);
        username.add(editProfile);
        menuBarChat.add(username);
        // friends
        editFriends = new JMenu("Edit Friends");
        editFriends.setMnemonic(KeyEvent.VK_A);
        editFriends.getAccessibleContext().setAccessibleDescription("Edit Friends");
        addFriend = new JMenuItem("Add Friend+");

        toAddFriend = new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String username = JOptionPane.showInputDialog("Enter Friends Username");
                if (username == null) {
                    return;
                }
                try {
                    mydb.addFriend(username);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        };

        addFriend.addActionListener(toAddFriend);
        editFriends.add(addFriend);
        
        menuBarChat.add(editFriends);
        setJMenuBar(menuBarChat);
    }


    public void ChatTabs(DBHelper mydb) throws SQLException {
        chat = new JTabbedPane();
        online = new JList<String>(friendsMockListOnline);
        allFriends = mydb.getAllFriends();

        online.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if(mouseEvent.getClickCount() == 2) {
                    String user = online.getSelectedValue();
                    MessageGUI messageGUI = new MessageGUI();
                    JFrame messageFrame = new JFrame("Messaging " + user);
                    messageFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    messageFrame.setSize(400,400);
                    messageFrame.getContentPane().add(messageGUI, BorderLayout.CENTER);
                    messageFrame.setVisible(true);
                }
            }
        });

        chat.addTab("Online", null, online, "Friends that are online");
        chat.addTab("All", null, allFriends, "All Friends");
        getContentPane().add(chat, BorderLayout.CENTER);
    }
}