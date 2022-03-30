package db_com;

import java.sql.*;
import javax.swing.JOptionPane;

public class DbConnection {

    static Connection conn = null;

    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_USER_NAME = "postgres";
    static final String DB_PASSWORD = "danieloh24";
    static final String DB_NAME = "bureaux";
    static final String DB_URL = "jdbc:postgresql://localhost/";

    public static Connection getDbConnection() {

        try {

            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL + "" + DB_NAME, DB_USER_NAME, DB_PASSWORD);
            return conn;

        } catch (ClassNotFoundException ex1) {
            JOptionPane.showMessageDialog(null, "Error! Failed to Establish Connection! Please contact your System Administrator!\n\n" + ex1.getMessage(), "Module functionality interrupted", JOptionPane.ERROR_MESSAGE);
            return null;

        } catch (SQLException ex2) {
            JOptionPane.showMessageDialog(null, "Error! Failed to Establish Connection! Please contact your System Administrator or the Vendor!\n\n" + ex2.getMessage(), "Server-API Connection Failure", JOptionPane.ERROR_MESSAGE);
            return null;

        }
//        catch (Exception ex3) {
//            JOptionPane.showMessageDialog(null, "Error! Failed to Establish Connection! Please contact your System Administrator!\n\n" + ex3.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
//            return null;
//        }
    }
}
