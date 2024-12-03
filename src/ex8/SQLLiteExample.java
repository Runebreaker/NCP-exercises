package ex8;

import java.sql.*;

/**
 * Getting Started Example for SQLite
 * Adapted from: https://github.com/xerial/sqlite-jdbc#usage
 */
public class SQLLiteExample {
    public static void main(String[] args) throws Exception {
        Connection connection = null;
        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:test.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            // Delete old and create a new table
            statement.executeUpdate("drop table if exists person");
            statement.executeUpdate("create table person (name string, occupation string)");
            // Prepared statements for repeated parameterized execution
            PreparedStatement prep
                    = connection.prepareStatement("insert into person values (?, ?);");
            prep.setString(1, "Gandhi");
            prep.setString(2, "politics");
            prep.addBatch();
            prep.setString(1, "Turing");
            prep.setString(2, "computers");
            prep.addBatch();
            prep.setString(1, "Wittgenstein");
            prep.setString(2, "smartypants");
            prep.addBatch();
            // Safely the batched commands as a single commit (all or nothing)
            connection.setAutoCommit(false);
            prep.executeBatch();
            connection.setAutoCommit(true);
            // Query the database and print the results
            ResultSet rs = statement.executeQuery("select * from person");
            while (rs.next()) {
                // read the result set
                System.out.println("name = " + rs.getString("name"));
                System.out.println("occupation = " + rs.getString("occupation"));
            }
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
    }
}


