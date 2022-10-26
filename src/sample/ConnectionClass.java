package sample;

import javax.swing.*;
import java.sql.*;

public class ConnectionClass {
    private static ConnectionClass connectionClass = null;
    private static Connection connection;
    private static Statement stmt;
    private ConnectionClass(){
        getConnection();
        setupMovieTable();
        setupMemberTable();
        setupRentedTable();
    }
    public static ConnectionClass getInstance() {
        if(connectionClass == null){
            connectionClass = new ConnectionClass();
        }
        return connectionClass;
    }
    public Connection getConnection() {
        String myURL = "jdbc:mysql://puff:3306/bradley-stewart_books?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDateTimeCode=false&serverTimezone=UTC&useSSL=FALSE";
        String userName = "bradley-stewart";
        String pw = "Dragons@91";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch  (ClassNotFoundException e){
            throw new RuntimeException("Cannot find driver", e);
        }
        connection = null;

        try {
            connection = DriverManager.getConnection(myURL, userName, pw);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
    public ResultSet execQuery(String query) {
        ResultSet result = null;
        try {
            stmt = connection.createStatement();
            result = stmt.executeQuery(query);
        }
        catch (SQLException ex) {
            System.out.println("Exception at execQuery:dataHandler" + ex.getLocalizedMessage());
        }
        return result;
    }
    public boolean execAction(String qu) {
        try {
            stmt = connection.createStatement();
            stmt.execute(qu);
            return true;
        }
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error:" + ex.getMessage(), "An Error Has Occurred!", JOptionPane.ERROR_MESSAGE);
            System.out.println("Exception at execQuery:dataHandler" + ex.getLocalizedMessage());
            return false;
        }
    }

    public void setupMemberTable(){
        String TABLE_NAME = "Members";
        try {
            DatabaseMetaData dbm = connection.getMetaData();
            stmt = connection.createStatement();
            ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);
            if (tableExist(connection,TABLE_NAME)) {
                System.out.println("Table " + TABLE_NAME + " already exists.");
            } else {
                stmt.execute("CREATE TABLE " + TABLE_NAME + "("
                        + "       id varchar(200) primary key,\n"
                        + "       name varchar(200),\n"
                        + "       phone varchar(20),\n"
                        + "       email varchar(100),\n"
                        + "       cost int(200) default 0"
                        + ")");
            }
        } catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    public void setupMovieTable(){
        String TABLE_NAME = "Movie";
        try {
            DatabaseMetaData dbm = connection.getMetaData();
            stmt = connection.createStatement();
            ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);
            if (tableExist(connection,TABLE_NAME)) {
                System.out.println("Table " + TABLE_NAME + " already exists.");
            } else {
                stmt.execute("CREATE TABLE " + TABLE_NAME + "("
                        + "       id varchar(200) primary key,\n"
                        + "       title varchar(200),\n"
                        + "       genre varchar(200),\n"
                        + "       year varchar(200),\n"
                        + "       isAvail boolean default true"
                        + "  )");
            }
        } catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    void setupRentedTable() {
        String TABLE_NAME = "Rented";
        try {
            DatabaseMetaData dbm = connection.getMetaData();
            stmt = connection.createStatement();
            ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);
            if (tableExist(connection,TABLE_NAME)) {
                System.out.println("Table " + TABLE_NAME + " already exists.");
            } else {
                stmt.execute("CREATE TABLE " + TABLE_NAME + "("
                        + "       movieID varchar(200) primary key,\n"
                        + "       memberID varchar(200),\n"
                        + "       rentTime timestamp default CURRENT_TIMESTAMP,\n"
                        + "       FOREIGN KEY (movieID) REFERENCES Movie(id),\n"
                        + "       FOREIGN KEY (memberID) REFERENCES Members(id)"
                        + ")");
            }
        } catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    public static boolean tableExist(Connection conn, String tableName) throws SQLException {
        boolean tExists = false;
        try (ResultSet rs = conn.getMetaData().getTables(null, null, tableName, null)) {
            while (rs.next()) {
                String tName = rs.getString("TABLE_NAME");
                if (tName != null && tName.equals(tableName)) {
                    tExists = true;
                    break;
                }
            }
        }
        return tExists;
    }
    public static void closeConnection() throws SQLException{
        try {
            if (connection != null && !connection.isClosed()){
                connection.close();
            }
        } catch (Exception e) {
            throw e;
        }
    }
}