package app;

import db.DBHelper;

import javax.swing.*;
import java.sql.SQLException;

// GUI that will provide a display of all games in a collection
// This will required a database file that will host the games
public class GameCollection {

    // member variables
    // the store collection of games, array
    // count of games in a collection/store
    private Game[] gameArray;
    private int numberOfGames;

    public GameCollection() {
        gameArray = new Game[7];
        numberOfGames = 0;
    }

    public Game[] getGameArray() {
        return gameArray;
    }

    public int getNumberOfGames() {
        return numberOfGames;
    }

    public void setGameArray(Game[] gameArray) {
        this.gameArray = gameArray;
    }

    public void setNumberOfGames(int numberOfGames) {
        this.numberOfGames = numberOfGames;
    }

    public void deleteGame(String gameName, DBHelper dbh, String userID) throws SQLException {
        String confirmMessage = "Delete " + gameName + " from library?";
        int confirmOption = JOptionPane.showConfirmDialog(null, confirmMessage);
        if (confirmOption == JOptionPane.YES_OPTION){
            dbh.deleteGame(gameName, userID);
        }
    }

    public String searchForGame(String gameTitle, DBHelper dbh) throws SQLException {
        String temp = null;
        Game[] tempGameArray = dbh.getAllGameObjects();
        for (Game game : tempGameArray) {
            if (game.getName().toUpperCase().equals(gameTitle.toUpperCase())) {
                temp = Integer.toString(game.getId());
                break;
            }
        }
        if(temp == null){
            temp = "Not found";
        }
        return temp;
    }
    public void purchaseGame(String gameName, String userID, DBHelper dbh) throws SQLException {
        // Function to purchase game, which adds to store
        String confirmMessage = "Purchase " + gameName + "?";
        int confirmOption = JOptionPane.showConfirmDialog(null, confirmMessage);
        if (confirmOption == JOptionPane.YES_OPTION){
            dbh.purchaseGame(gameName, userID);
        }

    }
    public JList<String> filterGameGenre(String aGenre, DBHelper dbh) throws SQLException {
        return dbh.getFilterGameGenre(aGenre);

    }

    public JList<String> filterGameRating(String aRating, DBHelper dbh) throws SQLException {
        return dbh.getFilterGameRating(aRating);
    }

}
