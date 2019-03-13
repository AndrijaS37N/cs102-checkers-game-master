package cs102.pr.pack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GameDB {

    private static String player1;
    private static String player2;
    private static int time;
    private static int whitePoints;
    private static int redPoints;
    private static boolean isSurrW;
    private static boolean isSurrB;
    private static String winner;

    public GameDB(String player1, String player2, int time, int whitePoints, int redPoints, boolean isSurrW, boolean isSurrB, String winner) {
        this.player1 = player1;
        this.player2 = player2;
        this.time = time;
        this.whitePoints = whitePoints;
        this.redPoints = redPoints;
        this.isSurrW = isSurrW;
        this.isSurrB = isSurrB;
        this.winner = winner;
    }

    private static Connection getConnection() {
        try {
            String driver = "com.mysql.jdbc.Driver";
            // a db must be created in order to write all the stuff
            String url = "jdbc:mysql://localhost:3306/checkers";
            String username = "root";
            String password = "";
            Class.forName(driver);

            Connection conn = DriverManager.getConnection(url, username, password);
            System.out.println("Check: Connected to the database.");
            return conn;

        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public static void createTable() throws SQLException {
        try {
            // make a connection
            Connection con = getConnection();
            PreparedStatement create = con.prepareStatement("CREATE TABLE IF NOT EXISTS onlyTable(id int NOT NULL AUTO_INCREMENT, player1 varchar(255), player2 varchar(255)," + "time varchar(255), whitePoints varchar(255), redPoints varchar(255), isSurrW varchar(255), isSurrB varchar(255), winner varchar(255), PRIMARY KEY(id))");
            create.executeUpdate();

        } catch (Exception e) {
            System.out.println(e);
        } // and finally
        finally {
            System.out.println("Check: The table is created.");
        }
    }

    public static void insert() throws SQLException {
        try {
            // I really didn't know wtf I was doing back when I started making this
            Connection con = getConnection();
            PreparedStatement posted = con.prepareStatement("INSERT INTO onlyTable(player1, player2, time, whitePoints, redPoints, isSurrW, isSurrB, winner) VALUES ('" + player1 + "', '" + player2 + "'," + " '" + String.valueOf(time) + "', '" + String.valueOf(whitePoints) + "', '" + String.valueOf(redPoints) + "', '" + String.valueOf(isSurrW) + "', '" + String.valueOf(isSurrB) + "', '" + winner + "')");
            posted.executeUpdate();

        } catch (Exception e) {
            System.out.println(e);
        } // woo
        finally {
            System.out.println("Insert into the database completed (scroll up).");
        }
    }

    @Override
    public String toString() {
        return "GameDB{" + "player1=" + player1 + ", player2=" + player2 + ", time=" + time + ", whitePoints=" + whitePoints + ", redPoints=" + redPoints + ", isSurrW=" + isSurrW + ", isSurrB=" + isSurrB + '}';
    }
}
    


