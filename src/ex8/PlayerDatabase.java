package ex8;

import java.sql.*;

public class PlayerDatabase {
    private final ThreadLocal<Connection> connection = ThreadLocal.withInitial(() -> {
        try {
            Class.forName("org.sqlite.JDBC");
            String DB_URL = "jdbc:sqlite:players.db";
            var dbConnection = DriverManager.getConnection(DB_URL);
            initDatabase(dbConnection);
            return dbConnection;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    });

    public Connection getConnection() {
        return connection.get();
    }

    private void initDatabase(Connection dbConnection) throws SQLException {
        Statement statement = dbConnection.createStatement();
        statement.setQueryTimeout(30);  // set timeout to 30 sec.
        // Delete old and create a new table
        statement.executeUpdate("drop table if exists player");
        statement.executeUpdate("create table player (name string, score int)");
        // Prepared statements for repeated parameterized execution
        PreparedStatement prep
                = dbConnection.prepareStatement("insert into player values (?, ?);");
        prep.setString(1, "JL");
        prep.setInt(2, 10000000);
        prep.addBatch();
        prep.setString(1, "Pierre");
        prep.setInt(2, 2);
        prep.addBatch();
        prep.setString(1, "Pedro");
        prep.setInt(2, 40000);
        prep.addBatch();
        // Safely the batched commands as a single commit (all or nothing)
        dbConnection.setAutoCommit(false);
        prep.executeBatch();
        dbConnection.setAutoCommit(true);
        // Query the database and print the results
        ResultSet rs = statement.executeQuery("select * from player");
        while (rs.next()) {
            // read the result set
            System.out.println("name = " + rs.getString("name"));
            System.out.println("score = " + rs.getInt("score"));
        }
    }
}
