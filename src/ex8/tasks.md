# Task 8.1: Thread-Local Database Connection
## Description
Thread-Local Variables can help with using non-thread-safe resources in multiple threads by simplifying the code.
In this exercise, you are asked to interact with an SQL Database.
- SQL is a standard language for storing, manipulating and retrieving data in databases.
- SQLite in-process library that implements a self-contained, serverless, zero-configuration, transactional SQL database engine. SQLite is actually an embedded SQL database engine. Unlike most other SQL databases, SQLite does not have a separate server process. SQLite reads and writes directly to ordinary disk files. A complete SQL database with multiple tables, indices, triggers, and views, is contained in a single disk file.

Please See the `SQLLiteExample.java` file below for a quick start on how to use SQL in Java. When checking out the project from git, you should already have the required Libraries included. Otherwise, see the instructions below.

You have to implement three classes:
- class `PlayerDatabase` represents the database in our application. It provides configured connection objects in a thread-safe Connection GetConnection() method – by using a variable of type ThreadLocal<Connection>. In this application, it also allows to initialize the database with the initDatabase method.
- class `DatabaseAccessorThread`: This thread will read player data from the database (see example of database table below), using the Connection provided by the PlayerDatabase class.
- class `DatabaseConnectionExercise`: Create and initialize a new PlayerDatabase instance. Then, create, start, and join two instances DatabaseAccessorThread that access the database (concurrently).

| name   | score    |
|--------|----------|
| JL     | 10000000 |
| Pierre | 2        |
| Pedro  | 40000    |

## How to use SQL
```java
import java.sql.*;

/**
 *  Getting Started Example for SQLite
 *  Adapted from: https://github.com/xerial/sqlite-jdbc#usage
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
```

## How To Add the ODBC SQL Connection
To add the SQL library (or any other library) to your project:
1. First, get the library, 2 options
    - From the [module code git repository](https://gitlab2.informatik.uni-wuerzburg.de/hci/students/network-and-concurrent-programming/ws24/ws-24-network-and-concurrent-programming-code-for-students/-/tree/main/ncp-stud-java-intellij-proj/) - if you have already cloned the repository, the library `sqlite-jdbc-3.47.1.0.jaris` already in the `/java/lib` directory or [Here](https://gitlab2.informatik.uni-wuerzburg.de/hci/students/network-and-concurrent-programming/ws24/ws-24-network-and-concurrent-programming-code-for-students/-/blob/main/ncp-stud-java-intellij-proj/lib/sqlite-jdbc-3.47.1.0.jar?ref_type=heads) on the master repository on git lab
    - From the library website - download the library’s Jar from the project website, e.g. [Xerial SQLite JDBC](https://github.com/xerial/sqlite-jdbc#usage).
2. Next, go to ``Project Structure`` -> ``Libraries``, hit the ``+`` Button and select “Java.”
3. Then, select the ``Jar`` file you downloaded.
