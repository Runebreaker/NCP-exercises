package ex8;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseAccessorThread extends Thread {
    private final Connection dbConnection;

    public DatabaseAccessorThread(PlayerDatabase playerDatabase) {
        this.dbConnection = playerDatabase.getConnection();
    }

    @Override
    public void run() {
        System.out.println("Thread " + getName() + " is running");
        try {
            ResultSet rs = getScores();
            while (rs.next()) {
                System.out.println("name = " + rs.getString("name"));
                System.out.println("score = " + rs.getInt("score"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ResultSet getScores() throws SQLException {
        Statement statement = dbConnection.createStatement();
        return statement.executeQuery("select * from player");
    }
}
