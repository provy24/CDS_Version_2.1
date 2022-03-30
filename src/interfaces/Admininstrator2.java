/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import db_com.SQLRun;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.security.Key;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Sir Chikukwa
 */
public class Admininstrator2 extends javax.swing.JFrame {

    java.util.Date date = new java.util.Date();

    long t = date.getTime();
    java.sql.Timestamp Timestamp = new java.sql.Timestamp(t);
    java.sql.Date sqlDate = new java.sql.Date(t);

    public void connect() {
        String filepath = "src/configuration.txt";
        File file = new File(filepath);
        try {
            FileReader fr = null;
            fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String zp = br.readLine();
            configs.setText(zp);
            String sx = DB_URL + configs.getText();
            configs.setText(sx);

        } catch (IOException ep) {
            JOptionPane.showMessageDialog(null, ep.getMessage());
        }
    }

    public void connect1() {
        String filepath = "src/configuration_1.txt";
        File file = new File(filepath);
        try {
            FileReader fr = null;
            fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String zp = br.readLine();
            configs2.setText(zp);
            String sx = DB_URL + configs2.getText();
            configs2.setText(sx);

        } catch (IOException ep) {
            JOptionPane.showMessageDialog(null, ep.getMessage());
        }
    }

    int lblTextLength = 0;
    Timer tm;
    int counter = 0;
    private static final long serialVersionUID = 1L;
    String DB_DRIVER = "org.postgresql.Driver";
    String DB_URL = "jdbc:postgresql:";
    String DB_USER;
    String DB_PAS;
    java.sql.Connection con = null;
    java.sql.Statement stm = null;
    java.sql.ResultSet rs = null;
    java.sql.PreparedStatement pst = null;

    public void db_users_connect() {
        String filepath = "src/db_user.txt";
        File file = new File(filepath);
        try {
            FileReader fr = null;
            fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String zp = br.readLine();
            uname.setText(zp);

        } catch (IOException ep) {
            JOptionPane.showMessageDialog(null, ep.getMessage());
        }
    }

    public void db_password_connect() {
        String filepath = "src/db_pass.txt";
        File file = new File(filepath);
        try {
            FileReader fr = null;
            fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String zp = br.readLine();
            upass.setText(zp);

        } catch (IOException ep) {
            JOptionPane.showMessageDialog(null, ep.getMessage());
        }
    }

    public Admininstrator2() {
        initComponents();
        this.setLocationRelativeTo(null);
        setIconImage(Toolkit.getDefaultToolkit().getImage("src/img/shop.png"));
        connect();
        connect1();
        db_users_connect();
        db_password_connect();
        shorttime();
        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        dashboard_rates.setVisible(false);
        dashboard_reports.setVisible(false);
        dashboard_master.setVisible(false);
        dashboard_operations.setVisible(false);

        currencies.setVisible(false);
        opening_balances.setVisible(false);
        denominations.setVisible(false);

        rates.setVisible(false);
        reports.setVisible(false);
        users.setVisible(false);
        branches.setVisible(false);
        simult.setVisible(false);
        notifications.setVisible(false);

        day.setText(now("EEEE").toUpperCase());
        month.setText(now("dd MMMM").toUpperCase());
        year.setText(now("YYYY").toUpperCase());
        time.setText(now("hh : mm : ss : aa").toUpperCase());

        String txt = animat.getText();
        lblTextLength = txt.length();
        tm = new Timer(150, new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                counter++;
                if (counter > lblTextLength) {
                    animat.setText("hELLO");
                    counter = 0;
                    DefaultTableModel dm = (DefaultTableModel) u_table.getModel();
                    dm.setNumRows(0);
                    DefaultTableModel dm1 = (DefaultTableModel) d_table.getModel();
                    dm1.setNumRows(0);
                    DefaultTableModel dm2 = (DefaultTableModel) table_reset.getModel();
                    dm2.setNumRows(0);
                    DB_USER = uname.getText();
                    DB_PAS = upass.getText();

                    String brn = "none";

                    String query = "SELECT region_name FROM regions";
                    String query1 = "SELECT fname,lname,gender,region,branch,position,phone_number,national_id,user_id FROM users where branch='" + brn + "'";
                    try {
                        Class.forName(DB_DRIVER);
                        con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                        stm = con.createStatement();
                        rs = stm.executeQuery(query);
                        boolean bool = rs.isBeforeFirst();
                        while (rs.next()) {
                            if (bool == true) {
                                String a = rs.getString("region_name");
                                b1.addItem(a);

                            }
                        }

                        rs = stm.executeQuery(query1);
                        boolean boolm = rs.isBeforeFirst();
                        while (rs.next()) {
                            if (boolm == true) {
                                String a = rs.getString("fname");
                                String b = rs.getString("lname");
                                String c = rs.getString("gender");
                                String d = rs.getString("region");
                                String e = rs.getString("branch");
                                String f = rs.getString("position");
                                String g = rs.getString("phone_number");
                                String h = rs.getString("national_id");
                                String j = rs.getString("user_id");

                                dm.addRow(new Object[]{a, b, c, d, e, f, g, h, j});
                                dm1.addRow(new Object[]{a, b, c, e, f, g, h, j});
                                String ab = a.concat(" ").concat(b);
                                dm2.addRow(new Object[]{ab, d, e, f, j});

                            }
                        }
                    } catch (ClassNotFoundException | SQLException e) {
                        //JOptionPane.showMessageDialog(null, e.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
                    }
                    my_select1();
                    
                } else {
                    animat.setText(txt.substring(0, counter));
                }

            }
        });

        tm.start();
    }
public void my_select1(){
    DefaultTableModel dm = (DefaultTableModel) rtu.getModel();
        dm.setNumRows(0);
        DefaultTableModel dm1 = (DefaultTableModel) rtv.getModel();
        dm1.setNumRows(0);
        DefaultComboBoxModel cb = new DefaultComboBoxModel();
        ccr1.setModel(cb);
        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        String xz = "true";

        String query = "SELECT currency_name,short_code,base_currency,currency_id FROM currencies ";
        try {
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
            stm = con.createStatement();
            rs = stm.executeQuery(query);
            boolean bool = rs.isBeforeFirst();
            while (rs.next()) {
                if (bool == true) {
                    String a = rs.getString("currency_name");
                    String b = rs.getString("short_code");
                    String c = rs.getString("base_currency");

                    if (c.matches(xz)) {
                        rt1.setText(b);
                    }
                    if (!c.matches(xz)) {
                        rt2.addItem(b);
                        ccr1.addItem("");
                        ccr1.addItem(b);
                    }

                }
            }
            if (bool == false) {

                //JOptionPane.showMessageDialog(rootPane, "You haven't configured exchange rates against the base currency", "Base currency not available", JOptionPane.WARNING_MESSAGE);
            }

        } catch (ClassNotFoundException | SQLException e) {
            //JOptionPane.showMessageDialog(null, e.getMessage(), "Something went wrong while trying to retrieve exchange rates", JOptionPane.ERROR_MESSAGE);
        }
        String query1 = "SELECT base_currency,quote_currency,buying_rate,selling_rate,exchange_rate_id FROM config_rates";

        try {
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
            stm = con.createStatement();
            rs = stm.executeQuery(query1);
            boolean bool = rs.isBeforeFirst();
            while (rs.next()) {
                if (bool == true) {
                    String a = rs.getString("base_currency");
                    String b = rs.getString("quote_currency");
                    double c = rs.getDouble("buying_rate");
                    double d = rs.getDouble("selling_rate");
                    //double e = rs.getDouble("rate");
                    double rounded_buy = Math.round(c * 10) / 10.0;
                    double rounded_sell = Math.round(d * 10) / 10.0;
                    int f = rs.getInt("exchange_rate_id");

                    dm.addRow(new Object[]{a, b, rounded_buy, rounded_sell, f});

                    dm1.addRow(new Object[]{a, b, rounded_buy, rounded_sell, f});
                }
            }
            if (bool == false) {
                //JOptionPane.showMessageDialog(null, "Exchange rate table is empty please configure ", "Exchange rate notification", JOptionPane.WARNING_MESSAGE);
            }

        } catch (ClassNotFoundException | SQLException ez) {
            //JOptionPane.showMessageDialog(null, ez.getMessage(), "Something went wrong ....", JOptionPane.ERROR_MESSAGE);
        }
}

public void my_select2(){
    
}
    public static String now(String dateFormat) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(cal.getTime());
    }

    public void currentdate() {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        day.setText(sdf.format(d));
    }

    void shorttime() {
        new Timer(0, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Date d = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("hh : mm : ss : aa");
                time.setText(sdf.format(d));
                //lala.setText(sdf.format(d)); 
            }

        }).start();

    }

    private String md5(char[] c) {
        try {
            MessageDigest digs = MessageDigest.getInstance("MD5");
            digs.update((new String(c)).getBytes("UTF8"));
            String str = new String(digs.digest());
            return str;
        } catch (Exception e) {
            return "";
        }
    }
    String ALGO = "AES";

    byte[] keyValue;

    public void AESExample(String key) {
        keyValue = key.getBytes();
    }

    public Key generateKey() throws Exception {
        Key key = new SecretKeySpec(keyValue, ALGO);
        return key;
    }

    public void getSum() {
        float sum = 0;
        for (int i = 0; i < cg_table.getRowCount(); i++) {
            sum = sum + Float.parseFloat(cg_table.getValueAt(i, 3).toString());
        }
        cg4.setText(Float.toString(sum));
    }

    public void getSum1() {
        float sum = 0;
        for (int i = 0; i < cp_table.getRowCount(); i++) {
            sum = sum + Float.parseFloat(cp_table.getValueAt(i, 3).toString());
        }
        cp4.setText(Float.toString(sum));
    }

    public void getSum2() {
        float sum = 0;
        for (int i = 0; i < tw_table.getRowCount(); i++) {
            sum = sum + Float.parseFloat(tw_table.getValueAt(i, 2).toString());
        }
        tw6.setText(Float.toString(sum));
    }

    public void getSum3() {
        float sum = 0;
        for (int i = 0; i < acc_table.getRowCount(); i++) {
            sum = sum + Float.parseFloat(acc_table.getValueAt(i, 2).toString());
        }
        acc_total.setText(Float.toString(sum));
    }

    int x = 0;

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        dashboard_home = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        sa = new javax.swing.JLabel();
        se = new javax.swing.JLabel();
        animat = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        header = new javax.swing.JPanel();
        day = new javax.swing.JLabel();
        month = new javax.swing.JLabel();
        year = new javax.swing.JLabel();
        time = new javax.swing.JLabel();
        animation = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        branches = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel7 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        r1 = new javax.swing.JFormattedTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        r2 = new javax.swing.JEditorPane();
        jLabel24 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        b1 = new javax.swing.JComboBox<>();
        jLabel31 = new javax.swing.JLabel();
        b2 = new javax.swing.JFormattedTextField();
        jLabel32 = new javax.swing.JLabel();
        b3 = new javax.swing.JFormattedTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        b4 = new javax.swing.JEditorPane();
        jLabel37 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel39 = new javax.swing.JLabel();
        jFormattedTextField4 = new javax.swing.JFormattedTextField();
        jLabel40 = new javax.swing.JLabel();
        jFormattedTextField5 = new javax.swing.JFormattedTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        jEditorPane3 = new javax.swing.JEditorPane();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        dashboard_master = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        dashboard_rates = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        dashboard_operations = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        dashboard_reports = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        notifications = new javax.swing.JPanel();
        jTabbedPane5 = new javax.swing.JTabbedPane();
        jPanel10 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        n1 = new javax.swing.JFormattedTextField();
        jScrollPane7 = new javax.swing.JScrollPane();
        n2 = new javax.swing.JEditorPane();
        jLabel4 = new javax.swing.JLabel();
        n3 = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        n4 = new javax.swing.JEditorPane();
        jLabel41 = new javax.swing.JLabel();
        mk4 = new javax.swing.JLabel();
        jPanel23 = new javax.swing.JPanel();
        jLabel187 = new javax.swing.JLabel();
        ntf1 = new javax.swing.JComboBox<>();
        jLabel188 = new javax.swing.JLabel();
        ntf2 = new javax.swing.JFormattedTextField();
        jScrollPane14 = new javax.swing.JScrollPane();
        ntf4 = new javax.swing.JEditorPane();
        ntf3 = new javax.swing.JFormattedTextField();
        jLabel189 = new javax.swing.JLabel();
        currencies = new javax.swing.JPanel();
        jPanel46 = new javax.swing.JPanel();
        jLabel61 = new javax.swing.JLabel();
        jFormattedTextField16 = new javax.swing.JFormattedTextField();
        jLabel72 = new javax.swing.JLabel();
        jFormattedTextField21 = new javax.swing.JFormattedTextField();
        jLabel73 = new javax.swing.JLabel();
        jFormattedTextField22 = new javax.swing.JFormattedTextField();
        jLabel74 = new javax.swing.JLabel();
        jFormattedTextField25 = new javax.swing.JFormattedTextField();
        denominations = new javax.swing.JPanel();
        jTabbedPane12 = new javax.swing.JTabbedPane();
        jPanel54 = new javax.swing.JPanel();
        jLabel111 = new javax.swing.JLabel();
        jLabel112 = new javax.swing.JLabel();
        jFormattedTextField44 = new javax.swing.JFormattedTextField();
        jComboBox14 = new javax.swing.JComboBox<>();
        jScrollPane25 = new javax.swing.JScrollPane();
        jTable13 = new javax.swing.JTable();
        jPanel56 = new javax.swing.JPanel();
        jScrollPane24 = new javax.swing.JScrollPane();
        jTable12 = new javax.swing.JTable();
        opening_balances = new javax.swing.JPanel();
        jPanel52 = new javax.swing.JPanel();
        jLabel71 = new javax.swing.JLabel();
        jComboBox16 = new javax.swing.JComboBox<>();
        jFormattedTextField6 = new javax.swing.JFormattedTextField();
        jLabel113 = new javax.swing.JLabel();
        jLabel114 = new javax.swing.JLabel();
        jComboBox17 = new javax.swing.JComboBox<>();
        jComboBox18 = new javax.swing.JComboBox<>();
        jLabel115 = new javax.swing.JLabel();
        jFormattedTextField7 = new javax.swing.JFormattedTextField();
        jLabel116 = new javax.swing.JLabel();
        jScrollPane26 = new javax.swing.JScrollPane();
        jTable14 = new javax.swing.JTable();
        users = new javax.swing.JPanel();
        jTabbedPane10 = new javax.swing.JTabbedPane();
        jPanel14 = new javax.swing.JPanel();
        jLabel47 = new javax.swing.JLabel();
        a1 = new javax.swing.JFormattedTextField();
        jLabel98 = new javax.swing.JLabel();
        a2 = new javax.swing.JFormattedTextField();
        jLabel99 = new javax.swing.JLabel();
        a3 = new javax.swing.JComboBox<>();
        jLabel100 = new javax.swing.JLabel();
        a4 = new javax.swing.JFormattedTextField();
        jLabel101 = new javax.swing.JLabel();
        a5 = new javax.swing.JFormattedTextField();
        jLabel102 = new javax.swing.JLabel();
        jScrollPane29 = new javax.swing.JScrollPane();
        a_table = new javax.swing.JTable();
        jLabel103 = new javax.swing.JLabel();
        a8 = new javax.swing.JFormattedTextField();
        a9 = new javax.swing.JPasswordField();
        a10 = new javax.swing.JPasswordField();
        jLabel104 = new javax.swing.JLabel();
        jLabel105 = new javax.swing.JLabel();
        a6 = new javax.swing.JComboBox<>();
        jLabel117 = new javax.swing.JLabel();
        a7 = new javax.swing.JComboBox<>();
        jPanel16 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        u_table = new javax.swing.JTable();
        jLabel48 = new javax.swing.JLabel();
        jLabel106 = new javax.swing.JLabel();
        jLabel107 = new javax.swing.JLabel();
        jLabel108 = new javax.swing.JLabel();
        jLabel109 = new javax.swing.JLabel();
        u5 = new javax.swing.JFormattedTextField();
        u4 = new javax.swing.JFormattedTextField();
        u3 = new javax.swing.JComboBox<>();
        u2 = new javax.swing.JFormattedTextField();
        u1 = new javax.swing.JFormattedTextField();
        jLabel110 = new javax.swing.JLabel();
        u6 = new javax.swing.JComboBox<>();
        jLabel49 = new javax.swing.JLabel();
        u7 = new javax.swing.JComboBox<>();
        uid = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jPanel35 = new javax.swing.JPanel();
        jScrollPane30 = new javax.swing.JScrollPane();
        d_table = new javax.swing.JTable();
        na = new javax.swing.JLabel();
        nb = new javax.swing.JLabel();
        jPanel27 = new javax.swing.JPanel();
        jScrollPane13 = new javax.swing.JScrollPane();
        table_reset = new javax.swing.JTable();
        wtq = new javax.swing.JLabel();
        reports = new javax.swing.JPanel();
        jTabbedPane7 = new javax.swing.JTabbedPane();
        sales_rep = new javax.swing.JPanel();
        jTabbedPane8 = new javax.swing.JTabbedPane();
        jPanel30 = new javax.swing.JPanel();
        jScrollPane21 = new javax.swing.JScrollPane();
        jm_table1 = new javax.swing.JTable();
        fa1 = new javax.swing.JCheckBox();
        fa2 = new javax.swing.JCheckBox();
        jLabel94 = new javax.swing.JLabel();
        mj1 = new com.toedter.calendar.JDateChooser();
        mj2 = new com.toedter.calendar.JDateChooser();
        jLabel95 = new javax.swing.JLabel();
        fa3 = new javax.swing.JCheckBox();
        fa4 = new javax.swing.JCheckBox();
        jLabel75 = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        fa6 = new javax.swing.JComboBox<>();
        jLabel128 = new javax.swing.JLabel();
        bscd = new javax.swing.JLabel();
        eco1 = new javax.swing.JPanel();
        jLabel45 = new javax.swing.JLabel();
        eco2 = new javax.swing.JPanel();
        jLabel50 = new javax.swing.JLabel();
        eco3 = new javax.swing.JPanel();
        jLabel51 = new javax.swing.JLabel();
        eco4 = new javax.swing.JPanel();
        jLabel52 = new javax.swing.JLabel();
        eco5 = new javax.swing.JPanel();
        jLabel53 = new javax.swing.JLabel();
        jPanel31 = new javax.swing.JPanel();
        fb1 = new javax.swing.JCheckBox();
        fb2 = new javax.swing.JCheckBox();
        jLabel133 = new javax.swing.JLabel();
        fb7 = new com.toedter.calendar.JDateChooser();
        fb3 = new javax.swing.JCheckBox();
        fb4 = new javax.swing.JCheckBox();
        jLabel77 = new javax.swing.JLabel();
        fb5 = new javax.swing.JComboBox<>();
        jLabel78 = new javax.swing.JLabel();
        fb6 = new javax.swing.JComboBox<>();
        fw1 = new javax.swing.JLabel();
        fw2 = new javax.swing.JLabel();
        jScrollPane35 = new javax.swing.JScrollPane();
        fb_table = new javax.swing.JTable();
        eco6 = new javax.swing.JPanel();
        jLabel54 = new javax.swing.JLabel();
        eco7 = new javax.swing.JPanel();
        jLabel55 = new javax.swing.JLabel();
        eco8 = new javax.swing.JPanel();
        jLabel89 = new javax.swing.JLabel();
        eco9 = new javax.swing.JPanel();
        jLabel91 = new javax.swing.JLabel();
        eco10 = new javax.swing.JPanel();
        jLabel93 = new javax.swing.JLabel();
        jPanel37 = new javax.swing.JPanel();
        jScrollPane36 = new javax.swing.JScrollPane();
        zm_table = new javax.swing.JTable();
        fc1 = new javax.swing.JCheckBox();
        fc2 = new javax.swing.JCheckBox();
        jLabel141 = new javax.swing.JLabel();
        fc3 = new javax.swing.JCheckBox();
        fc4 = new javax.swing.JCheckBox();
        jLabel79 = new javax.swing.JLabel();
        fc5 = new javax.swing.JComboBox<>();
        jLabel80 = new javax.swing.JLabel();
        fc6 = new javax.swing.JComboBox<>();
        zm1 = new javax.swing.JSpinner();
        zm5 = new javax.swing.JLabel();
        sym = new javax.swing.JSpinner();
        jLabel144 = new javax.swing.JLabel();
        eco11 = new javax.swing.JPanel();
        jLabel96 = new javax.swing.JLabel();
        eco12 = new javax.swing.JPanel();
        jLabel137 = new javax.swing.JLabel();
        eco13 = new javax.swing.JPanel();
        jLabel145 = new javax.swing.JLabel();
        eco14 = new javax.swing.JPanel();
        jLabel152 = new javax.swing.JLabel();
        eco15 = new javax.swing.JPanel();
        jLabel153 = new javax.swing.JLabel();
        jPanel38 = new javax.swing.JPanel();
        jScrollPane37 = new javax.swing.JScrollPane();
        fd_table = new javax.swing.JTable();
        fd1 = new javax.swing.JCheckBox();
        fd2 = new javax.swing.JCheckBox();
        jLabel149 = new javax.swing.JLabel();
        fd3 = new javax.swing.JCheckBox();
        fd4 = new javax.swing.JCheckBox();
        jLabel81 = new javax.swing.JLabel();
        fd5 = new javax.swing.JComboBox<>();
        jLabel82 = new javax.swing.JLabel();
        fd6 = new javax.swing.JComboBox<>();
        fd7 = new javax.swing.JSpinner();
        eco16 = new javax.swing.JPanel();
        jLabel166 = new javax.swing.JLabel();
        eco17 = new javax.swing.JPanel();
        jLabel175 = new javax.swing.JLabel();
        eco18 = new javax.swing.JPanel();
        jLabel198 = new javax.swing.JLabel();
        eco19 = new javax.swing.JPanel();
        jLabel199 = new javax.swing.JLabel();
        eco20 = new javax.swing.JPanel();
        jLabel200 = new javax.swing.JLabel();
        purchases_rep = new javax.swing.JPanel();
        jTabbedPane9 = new javax.swing.JTabbedPane();
        jPanel39 = new javax.swing.JPanel();
        jScrollPane38 = new javax.swing.JScrollPane();
        py_table = new javax.swing.JTable();
        jLabel92 = new javax.swing.JLabel();
        jLabel134 = new javax.swing.JLabel();
        py1 = new javax.swing.JCheckBox();
        py2 = new javax.swing.JCheckBox();
        jLabel142 = new javax.swing.JLabel();
        py7 = new com.toedter.calendar.JDateChooser();
        py8 = new com.toedter.calendar.JDateChooser();
        jLabel150 = new javax.swing.JLabel();
        jLabel154 = new javax.swing.JLabel();
        py3 = new javax.swing.JCheckBox();
        py4 = new javax.swing.JCheckBox();
        jLabel83 = new javax.swing.JLabel();
        jLabel155 = new javax.swing.JLabel();
        py6 = new javax.swing.JComboBox<>();
        jLabel156 = new javax.swing.JLabel();
        bfc = new javax.swing.JLabel();
        eco21 = new javax.swing.JPanel();
        jLabel201 = new javax.swing.JLabel();
        eco22 = new javax.swing.JPanel();
        jLabel202 = new javax.swing.JLabel();
        eco23 = new javax.swing.JPanel();
        jLabel203 = new javax.swing.JLabel();
        eco24 = new javax.swing.JPanel();
        jLabel204 = new javax.swing.JLabel();
        eco25 = new javax.swing.JPanel();
        jLabel205 = new javax.swing.JLabel();
        jPanel40 = new javax.swing.JPanel();
        jScrollPane39 = new javax.swing.JScrollPane();
        wy_table = new javax.swing.JTable();
        jLabel158 = new javax.swing.JLabel();
        jLabel159 = new javax.swing.JLabel();
        jLabel160 = new javax.swing.JLabel();
        wy1 = new javax.swing.JCheckBox();
        wy2 = new javax.swing.JCheckBox();
        jLabel161 = new javax.swing.JLabel();
        wy7 = new com.toedter.calendar.JDateChooser();
        jLabel162 = new javax.swing.JLabel();
        wy3 = new javax.swing.JCheckBox();
        wy4 = new javax.swing.JCheckBox();
        jLabel163 = new javax.swing.JLabel();
        wy5 = new javax.swing.JComboBox<>();
        jLabel164 = new javax.swing.JLabel();
        wy6 = new javax.swing.JComboBox<>();
        ts1 = new javax.swing.JLabel();
        ts2 = new javax.swing.JLabel();
        eco26 = new javax.swing.JPanel();
        jLabel206 = new javax.swing.JLabel();
        eco27 = new javax.swing.JPanel();
        jLabel207 = new javax.swing.JLabel();
        eco28 = new javax.swing.JPanel();
        jLabel208 = new javax.swing.JLabel();
        eco29 = new javax.swing.JPanel();
        jLabel209 = new javax.swing.JLabel();
        eco30 = new javax.swing.JPanel();
        jLabel210 = new javax.swing.JLabel();
        jPanel42 = new javax.swing.JPanel();
        jScrollPane40 = new javax.swing.JScrollPane();
        zh_table = new javax.swing.JTable();
        jLabel167 = new javax.swing.JLabel();
        jLabel168 = new javax.swing.JLabel();
        jLabel169 = new javax.swing.JLabel();
        zh1 = new javax.swing.JCheckBox();
        zh2 = new javax.swing.JCheckBox();
        jLabel170 = new javax.swing.JLabel();
        jLabel171 = new javax.swing.JLabel();
        zh3 = new javax.swing.JCheckBox();
        zh4 = new javax.swing.JCheckBox();
        jLabel172 = new javax.swing.JLabel();
        zh5 = new javax.swing.JComboBox<>();
        jLabel173 = new javax.swing.JLabel();
        zh6 = new javax.swing.JComboBox<>();
        zh7 = new javax.swing.JSpinner();
        zh8 = new javax.swing.JLabel();
        pym = new javax.swing.JSpinner();
        jLabel174 = new javax.swing.JLabel();
        eco31 = new javax.swing.JPanel();
        jLabel211 = new javax.swing.JLabel();
        eco32 = new javax.swing.JPanel();
        jLabel212 = new javax.swing.JLabel();
        eco33 = new javax.swing.JPanel();
        jLabel213 = new javax.swing.JLabel();
        eco34 = new javax.swing.JPanel();
        jLabel214 = new javax.swing.JLabel();
        eco35 = new javax.swing.JPanel();
        jLabel215 = new javax.swing.JLabel();
        jPanel53 = new javax.swing.JPanel();
        jScrollPane41 = new javax.swing.JScrollPane();
        px_table = new javax.swing.JTable();
        jLabel176 = new javax.swing.JLabel();
        jLabel177 = new javax.swing.JLabel();
        jLabel178 = new javax.swing.JLabel();
        px1 = new javax.swing.JCheckBox();
        px2 = new javax.swing.JCheckBox();
        jLabel179 = new javax.swing.JLabel();
        jLabel180 = new javax.swing.JLabel();
        px3 = new javax.swing.JCheckBox();
        px4 = new javax.swing.JCheckBox();
        jLabel181 = new javax.swing.JLabel();
        px5 = new javax.swing.JComboBox<>();
        jLabel182 = new javax.swing.JLabel();
        px6 = new javax.swing.JComboBox<>();
        px7 = new javax.swing.JSpinner();
        eco36 = new javax.swing.JPanel();
        jLabel216 = new javax.swing.JLabel();
        eco37 = new javax.swing.JPanel();
        jLabel217 = new javax.swing.JLabel();
        eco38 = new javax.swing.JPanel();
        jLabel218 = new javax.swing.JLabel();
        eco39 = new javax.swing.JPanel();
        jLabel219 = new javax.swing.JLabel();
        eco40 = new javax.swing.JPanel();
        jLabel220 = new javax.swing.JLabel();
        profit_and_loses = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel97 = new javax.swing.JLabel();
        prf1 = new javax.swing.JComboBox<>();
        prf2 = new javax.swing.JComboBox<>();
        jLabel118 = new javax.swing.JLabel();
        jLabel119 = new javax.swing.JLabel();
        prf5 = new javax.swing.JComboBox<>();
        jPanel9 = new javax.swing.JPanel();
        prf6 = new javax.swing.JCheckBox();
        prf7 = new javax.swing.JCheckBox();
        prf8 = new javax.swing.JCheckBox();
        jLabel120 = new javax.swing.JLabel();
        prf9 = new javax.swing.JFormattedTextField();
        jLabel122 = new javax.swing.JLabel();
        jLabel123 = new javax.swing.JLabel();
        prf11 = new javax.swing.JFormattedTextField();
        prf10 = new javax.swing.JFormattedTextField();
        jLabel124 = new javax.swing.JLabel();
        jLabel125 = new javax.swing.JLabel();
        jLabel126 = new javax.swing.JLabel();
        prf12 = new javax.swing.JFormattedTextField();
        jLabel127 = new javax.swing.JLabel();
        prf13 = new javax.swing.JRadioButton();
        prf14 = new javax.swing.JRadioButton();
        prf15 = new javax.swing.JFormattedTextField();
        prf16 = new javax.swing.JFormattedTextField();
        pfr1 = new javax.swing.JSpinner();
        jLabel183 = new javax.swing.JLabel();
        jScrollPane15 = new javax.swing.JScrollPane();
        prof_table = new javax.swing.JTable();
        jScrollPane16 = new javax.swing.JScrollPane();
        prof_table1 = new javax.swing.JTable();
        profit_and_loses2 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel129 = new javax.swing.JLabel();
        acc_currency = new javax.swing.JComboBox<>();
        jScrollPane10 = new javax.swing.JScrollPane();
        acc_table = new javax.swing.JTable();
        jLabel136 = new javax.swing.JLabel();
        acc_total = new javax.swing.JFormattedTextField();
        opb1 = new javax.swing.JCheckBox();
        opb2 = new javax.swing.JCheckBox();
        opb4 = new javax.swing.JComboBox<>();
        opb3 = new javax.swing.JComboBox<>();
        jPanel28 = new javax.swing.JPanel();
        jPanel34 = new javax.swing.JPanel();
        jLabel194 = new javax.swing.JLabel();
        cp3 = new javax.swing.JComboBox<>();
        jScrollPane17 = new javax.swing.JScrollPane();
        cp_table = new javax.swing.JTable();
        jLabel195 = new javax.swing.JLabel();
        cp4 = new javax.swing.JFormattedTextField();
        cp2 = new javax.swing.JComboBox<>();
        cp1 = new javax.swing.JComboBox<>();
        jPanel43 = new javax.swing.JPanel();
        jPanel45 = new javax.swing.JPanel();
        jLabel196 = new javax.swing.JLabel();
        cg3 = new javax.swing.JComboBox<>();
        jScrollPane18 = new javax.swing.JScrollPane();
        cg_table = new javax.swing.JTable();
        jLabel197 = new javax.swing.JLabel();
        cg4 = new javax.swing.JFormattedTextField();
        cg2 = new javax.swing.JComboBox<>();
        cg1 = new javax.swing.JComboBox<>();
        profit_and_loses4 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jLabel157 = new javax.swing.JLabel();
        tw5 = new javax.swing.JComboBox<>();
        jScrollPane22 = new javax.swing.JScrollPane();
        tw_table = new javax.swing.JTable();
        jLabel165 = new javax.swing.JLabel();
        tw6 = new javax.swing.JFormattedTextField();
        tw4 = new javax.swing.JComboBox<>();
        tw3 = new javax.swing.JComboBox<>();
        simult = new javax.swing.JPanel();
        dv1 = new javax.swing.JFormattedTextField();
        jLabel56 = new javax.swing.JLabel();
        dv2 = new javax.swing.JFormattedTextField();
        jLabel57 = new javax.swing.JLabel();
        dv3 = new javax.swing.JFormattedTextField();
        jLabel58 = new javax.swing.JLabel();
        dv4 = new javax.swing.JFormattedTextField();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        sv2 = new javax.swing.JFormattedTextField();
        sv1 = new javax.swing.JFormattedTextField();
        jLabel63 = new javax.swing.JLabel();
        sv3 = new javax.swing.JFormattedTextField();
        jLabel68 = new javax.swing.JLabel();
        sv4 = new javax.swing.JFormattedTextField();
        jLabel69 = new javax.swing.JLabel();
        jLabel90 = new javax.swing.JLabel();
        rates = new javax.swing.JPanel();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel17 = new javax.swing.JPanel();
        jTabbedPane4 = new javax.swing.JTabbedPane();
        jPanel24 = new javax.swing.JPanel();
        jLabel184 = new javax.swing.JLabel();
        rt1 = new javax.swing.JFormattedTextField();
        rt2 = new javax.swing.JComboBox<>();
        jLabel185 = new javax.swing.JLabel();
        jLabel186 = new javax.swing.JLabel();
        rt5 = new javax.swing.JFormattedTextField();
        jScrollPane42 = new javax.swing.JScrollPane();
        rt_table = new javax.swing.JTable();
        zp = new javax.swing.JLabel();
        xp = new javax.swing.JLabel();
        rt6 = new javax.swing.JFormattedTextField();
        jLabel64 = new javax.swing.JLabel();
        jPanel25 = new javax.swing.JPanel();
        jLabel190 = new javax.swing.JLabel();
        ccr2 = new javax.swing.JComboBox<>();
        jLabel65 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        ccr3 = new javax.swing.JFormattedTextField();
        jScrollPane32 = new javax.swing.JScrollPane();
        rt_table1 = new javax.swing.JTable();
        zp1 = new javax.swing.JLabel();
        xp1 = new javax.swing.JLabel();
        ccr4 = new javax.swing.JFormattedTextField();
        jLabel67 = new javax.swing.JLabel();
        ccr1 = new javax.swing.JComboBox<>();
        jLabel191 = new javax.swing.JLabel();
        jpy1 = new javax.swing.JLabel();
        jpb1 = new javax.swing.JLabel();
        opt2 = new javax.swing.JLabel();
        opt3 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        jPanel26 = new javax.swing.JPanel();
        jScrollPane19 = new javax.swing.JScrollPane();
        rtu = new javax.swing.JTable();
        ru5 = new javax.swing.JFormattedTextField();
        jLabel192 = new javax.swing.JLabel();
        jLabel84 = new javax.swing.JLabel();
        jLabel85 = new javax.swing.JLabel();
        ru1 = new javax.swing.JFormattedTextField();
        ru2 = new javax.swing.JFormattedTextField();
        ru3 = new javax.swing.JCheckBox();
        ru4 = new javax.swing.JCheckBox();
        ru6 = new javax.swing.JFormattedTextField();
        jLabel121 = new javax.swing.JLabel();
        xch = new javax.swing.JLabel();
        nhy = new javax.swing.JLabel();
        rid = new javax.swing.JLabel();
        jLabel193 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        jPanel22 = new javax.swing.JPanel();
        jPanel29 = new javax.swing.JPanel();
        jScrollPane20 = new javax.swing.JScrollPane();
        rtv = new javax.swing.JTable();
        jLabel86 = new javax.swing.JLabel();
        jLabel87 = new javax.swing.JLabel();
        jLabel88 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel130 = new javax.swing.JLabel();
        configs2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 102, 255)));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/user.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("System Administrator");

        name.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        name.setForeground(new java.awt.Color(51, 102, 255));
        name.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        name.setText("Providence");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(38, 38, 38)
                                .addComponent(jLabel2))
                            .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 7, 240, 200));

        dashboard_home.setBackground(new java.awt.Color(255, 255, 255));
        dashboard_home.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        dashboard_home.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/us.png"))); // NOI18N
        jLabel5.setText("Top Level Users");
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });
        dashboard_home.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 13, 224, 40));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-dollar-euro-exchange-32.png"))); // NOI18N
        jLabel6.setText("Configured Rates");
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });
        dashboard_home.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 64, 224, 40));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-product-documents-32.png"))); // NOI18N
        jLabel7.setText("Generated Reports");
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });
        dashboard_home.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 115, 224, 40));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-notification-32.png"))); // NOI18N
        jLabel8.setText("Post Notifications");
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel8MouseClicked(evt);
            }
        });
        dashboard_home.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 166, 224, 40));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-privacy-32.png"))); // NOI18N
        jLabel9.setText("Change Password");
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel9MouseClicked(evt);
            }
        });
        dashboard_home.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 217, 224, 40));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/lout.png"))); // NOI18N
        jLabel10.setText("Sign Out");
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
        });
        dashboard_home.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 319, 224, 40));

        jLabel46.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel46.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/power.png"))); // NOI18N
        jLabel46.setText("Close Application");
        jLabel46.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel46MouseClicked(evt);
            }
        });
        dashboard_home.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 370, 224, 40));

        kms.setText("jLabel50");
        dashboard_home.add(kms, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 458, -1, 0));

        sa.setText("jLabel50");
        dashboard_home.add(sa, new org.netbeans.lib.awtextra.AbsoluteConstraints(27, 377, -1, 0));

        se.setText("jLabel51");
        dashboard_home.add(se, new org.netbeans.lib.awtextra.AbsoluteConstraints(124, 377, -1, 0));

        configs.setText("jLabel50");
        dashboard_home.add(configs, new org.netbeans.lib.awtextra.AbsoluteConstraints(67, 409, -1, 0));

        animat.setText("jLabel43");
        dashboard_home.add(animat, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 440, -1, 0));

        jLabel43.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel43.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/help.png"))); // NOI18N
        jLabel43.setText("Help | TroubleShoot");
        jLabel43.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel43MouseClicked(evt);
            }
        });
        dashboard_home.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 268, 224, 40));

        jPanel1.add(dashboard_home, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 211, 240, 507));

        header.setBackground(new java.awt.Color(255, 255, 255));
        header.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        day.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        day.setText("jLabel48");

        month.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        month.setText("jLabel48");

        year.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        year.setText("jLabel48");

        time.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        time.setText("jLabel48");

        javax.swing.GroupLayout headerLayout = new javax.swing.GroupLayout(header);
        header.setLayout(headerLayout);
        headerLayout.setHorizontalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerLayout.createSequentialGroup()
                .addGap(108, 108, 108)
                .addComponent(day, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(month, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(year, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(time, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(204, Short.MAX_VALUE))
        );
        headerLayout.setVerticalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, headerLayout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addGroup(headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(day, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(month, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(year, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(time, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23))
        );

        jPanel1.add(header, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 8, 1090, 90));

        animation.setBackground(new java.awt.Color(51, 102, 255));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-notification-32.png"))); // NOI18N
        jLabel3.setText("0");

        javax.swing.GroupLayout animationLayout = new javax.swing.GroupLayout(animation);
        animation.setLayout(animationLayout);
        animationLayout.setHorizontalGroup(
            animationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, animationLayout.createSequentialGroup()
                .addContainerGap(1012, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(33, 33, 33))
        );
        animationLayout.setVerticalGroup(
            animationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(animationLayout.createSequentialGroup()
                .addComponent(jLabel3)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel1.add(animation, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 100, 1090, -1));

        branches.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel23.setText("Region name");
        jPanel7.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 18, -1, -1));
        jPanel7.add(r1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 11, 190, 28));

        jScrollPane2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Add a description of a region", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        jScrollPane2.setViewportView(r2);

        jPanel7.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 300, 110));

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-new-document-32.png"))); // NOI18N
        jLabel24.setText("Save ");
        jLabel24.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel24MouseClicked(evt);
            }
        });
        jPanel7.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 170, 110, 40));

        jTabbedPane1.addTab("Add Regions", new javax.swing.ImageIcon(getClass().getResource("/img/comp.png")), jPanel7); // NOI18N

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));
        jPanel18.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel18.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel25.setText("Region name");
        jPanel18.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, -1, 30));

        b1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
        jPanel18.add(b1, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 10, 190, 30));

        jLabel31.setText("Branch name");
        jPanel18.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 60, -1, 30));
        jPanel18.add(b2, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 60, 190, 30));

        jLabel32.setText("Area code");
        jPanel18.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 110, -1, 30));
        jPanel18.add(b3, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 110, 190, 30));

        jScrollPane3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Add a description of a region", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        jScrollPane3.setViewportView(b4);

        jPanel18.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 160, 300, 110));

        jLabel37.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-new-document-32.png"))); // NOI18N
        jLabel37.setText("Save ");
        jLabel37.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel37MouseClicked(evt);
            }
        });
        jPanel18.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 280, 120, 39));

        jPanel3.add(jPanel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 80, 560, 340));

        jTabbedPane1.addTab("Add branches", new javax.swing.ImageIcon(getClass().getResource("/img/comp.png")), jPanel3); // NOI18N

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel38.setText("Region name");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel39.setText("Branch name");

        jLabel40.setText("Area code");

        jTable1.setAutoCreateRowSorter(true);
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Region name", "Branch name", "Area code", "Branch ID"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setRowHeight(20);
        jTable1.setShowHorizontalLines(false);
        jTable1.setShowVerticalLines(false);
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
        }

        jEditorPane3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Add a description", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        jScrollPane4.setViewportView(jEditorPane3);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel40)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jFormattedTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane4)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel38)
                                .addGap(18, 18, 18)
                                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel39)
                                .addGap(18, 18, 18)
                                .addComponent(jFormattedTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 687, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel38))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addComponent(jLabel39))
                            .addComponent(jFormattedTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel40)
                            .addComponent(jFormattedTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 512, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Update branch info", new javax.swing.ImageIcon(getClass().getResource("/img/comp.png")), jPanel4); // NOI18N

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jTable2.setAutoCreateRowSorter(true);
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Region name", "Branch name", "Area code", "Branch ID"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.setRowHeight(20);
        jTable2.setShowHorizontalLines(false);
        jTable2.setShowVerticalLines(false);
        jScrollPane5.setViewportView(jTable2);
        if (jTable2.getColumnModel().getColumnCount() > 0) {
            jTable2.getColumnModel().getColumn(0).setResizable(false);
            jTable2.getColumnModel().getColumn(1).setResizable(false);
            jTable2.getColumnModel().getColumn(2).setResizable(false);
            jTable2.getColumnModel().getColumn(3).setResizable(false);
        }

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 985, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addGap(154, 154, 154)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 687, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(144, Short.MAX_VALUE)))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 534, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 512, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        jTabbedPane1.addTab("View branches", new javax.swing.ImageIcon(getClass().getResource("/img/comp.png")), jPanel5); // NOI18N

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jTable3.setAutoCreateRowSorter(true);
        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Region name", "Branch name", "Area code", "Branch ID"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable3.setRowHeight(20);
        jTable3.setShowHorizontalLines(false);
        jTable3.setShowVerticalLines(false);
        jScrollPane6.setViewportView(jTable3);
        if (jTable3.getColumnModel().getColumnCount() > 0) {
            jTable3.getColumnModel().getColumn(0).setResizable(false);
            jTable3.getColumnModel().getColumn(1).setResizable(false);
            jTable3.getColumnModel().getColumn(2).setResizable(false);
            jTable3.getColumnModel().getColumn(3).setResizable(false);
        }

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 985, Short.MAX_VALUE)
            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel6Layout.createSequentialGroup()
                    .addGap(154, 154, 154)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 687, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(144, Short.MAX_VALUE)))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 534, Short.MAX_VALUE)
            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel6Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 512, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        jTabbedPane1.addTab("Delete branches", new javax.swing.ImageIcon(getClass().getResource("/img/comp.png")), jPanel6); // NOI18N

        branches.add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 990, -1));

        jPanel1.add(branches, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 140, 995, 580));

        dashboard_master.setBackground(new java.awt.Color(255, 255, 255));
        dashboard_master.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/comp.png"))); // NOI18N
        jLabel12.setText("Branches");
        jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel12MouseClicked(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ex.png"))); // NOI18N
        jLabel13.setText("Currencies");
        jLabel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel13MouseClicked(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/phist.png"))); // NOI18N
        jLabel14.setText("Denominations");
        jLabel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel14MouseClicked(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/sndm.png"))); // NOI18N
        jLabel15.setText("Opening balances");
        jLabel15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel15MouseClicked(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/us.png"))); // NOI18N
        jLabel16.setText("General managers");
        jLabel16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel16MouseClicked(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/hm.png"))); // NOI18N
        jLabel17.setText("Dashboard");
        jLabel17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel17MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout dashboard_masterLayout = new javax.swing.GroupLayout(dashboard_master);
        dashboard_master.setLayout(dashboard_masterLayout);
        dashboard_masterLayout.setHorizontalGroup(
            dashboard_masterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboard_masterLayout.createSequentialGroup()
                .addGroup(dashboard_masterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        dashboard_masterLayout.setVerticalGroup(
            dashboard_masterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboard_masterLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(196, Short.MAX_VALUE))
        );

        jPanel1.add(dashboard_master, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 211, -1, 507));

        dashboard_rates.setBackground(new java.awt.Color(255, 255, 255));
        dashboard_rates.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/st.png"))); // NOI18N
        jLabel19.setText("Set Exchange Rates");

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/rat.png"))); // NOI18N
        jLabel20.setText("Update Exchange Rates");

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/opr.png"))); // NOI18N
        jLabel21.setText("View Exchange Rates");

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/hm.png"))); // NOI18N
        jLabel22.setText("Dashboard");
        jLabel22.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel22MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout dashboard_ratesLayout = new javax.swing.GroupLayout(dashboard_rates);
        dashboard_rates.setLayout(dashboard_ratesLayout);
        dashboard_ratesLayout.setHorizontalGroup(
            dashboard_ratesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboard_ratesLayout.createSequentialGroup()
                .addGroup(dashboard_ratesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        dashboard_ratesLayout.setVerticalGroup(
            dashboard_ratesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboard_ratesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(298, Short.MAX_VALUE))
        );

        jPanel1.add(dashboard_rates, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 211, -1, 507));

        dashboard_operations.setBackground(new java.awt.Color(255, 255, 255));
        dashboard_operations.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel26.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/st.png"))); // NOI18N
        jLabel26.setText("Start Day Operations");

        jLabel27.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/rat.png"))); // NOI18N
        jLabel27.setText("End Day Operations");

        jLabel28.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/opr.png"))); // NOI18N
        jLabel28.setText("Bank Remittance");

        jLabel29.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/rat.png"))); // NOI18N
        jLabel29.setText("Bank Drawing");

        jLabel30.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/hm.png"))); // NOI18N
        jLabel30.setText("Dashboard");
        jLabel30.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel30MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout dashboard_operationsLayout = new javax.swing.GroupLayout(dashboard_operations);
        dashboard_operations.setLayout(dashboard_operationsLayout);
        dashboard_operationsLayout.setHorizontalGroup(
            dashboard_operationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboard_operationsLayout.createSequentialGroup()
                .addGroup(dashboard_operationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                    .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        dashboard_operationsLayout.setVerticalGroup(
            dashboard_operationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboard_operationsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(247, Short.MAX_VALUE))
        );

        jPanel1.add(dashboard_operations, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 211, -1, 507));

        dashboard_reports.setBackground(new java.awt.Color(255, 255, 255));
        dashboard_reports.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel33.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/st.png"))); // NOI18N
        jLabel33.setText("Sales Reports");

        jLabel34.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/rat.png"))); // NOI18N
        jLabel34.setText("Purchasing Reports");

        jLabel35.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel35.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/opr.png"))); // NOI18N
        jLabel35.setText("Profit & Loss");

        jLabel36.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel36.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/hm.png"))); // NOI18N
        jLabel36.setText("Dashboard");
        jLabel36.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel36MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout dashboard_reportsLayout = new javax.swing.GroupLayout(dashboard_reports);
        dashboard_reports.setLayout(dashboard_reportsLayout);
        dashboard_reportsLayout.setHorizontalGroup(
            dashboard_reportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboard_reportsLayout.createSequentialGroup()
                .addGroup(dashboard_reportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                    .addComponent(jLabel34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        dashboard_reportsLayout.setVerticalGroup(
            dashboard_reportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboard_reportsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(298, Short.MAX_VALUE))
        );

        jPanel1.add(dashboard_reports, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 211, -1, 507));

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel11.setText("Notification Subject");
        jPanel10.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(303, 29, -1, -1));
        jPanel10.add(n1, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 20, 217, 32));

        n2.setBorder(javax.swing.BorderFactory.createTitledBorder("Write your notification here"));
        jScrollPane7.setViewportView(n2);

        jPanel10.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(303, 70, 360, 172));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-send-32.png"))); // NOI18N
        jLabel4.setText("Save Notification");
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });
        jPanel10.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 360, 180, -1));

        n3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "Regional Managers", "Finance Personel", "Counter Clerk", "Strong Room Operator", "Branch Manager" }));
        n3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                n3ActionPerformed(evt);
            }
        });
        jPanel10.add(n3, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 248, 206, 28));

        jLabel18.setText("Select the audience");
        jPanel10.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(303, 255, -1, -1));

        n4.setEditable(false);
        n4.setBorder(javax.swing.BorderFactory.createTitledBorder("List of your recipients"));
        jScrollPane8.setViewportView(n4);

        jPanel10.add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(303, 294, 360, 111));

        jLabel41.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Clear-icon.png"))); // NOI18N
        jLabel41.setText("Remove Selecetd Audiences");
        jLabel41.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel41MouseClicked(evt);
            }
        });
        jPanel10.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 300, 250, -1));
        jPanel10.add(mk4, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 480, 0, -1));

        jTabbedPane5.addTab("Post Notifications", new javax.swing.ImageIcon(getClass().getResource("/img/icons8-megaphone-32.png")), jPanel10); // NOI18N

        jPanel23.setBackground(new java.awt.Color(255, 255, 255));
        jPanel23.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel187.setText("Select Subject");
        jPanel23.add(jLabel187, new org.netbeans.lib.awtextra.AbsoluteConstraints(342, 53, 150, -1));

        ntf1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
        ntf1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ntf1ActionPerformed(evt);
            }
        });
        jPanel23.add(ntf1, new org.netbeans.lib.awtextra.AbsoluteConstraints(536, 49, 190, 29));

        jLabel188.setText("Notification from");
        jPanel23.add(jLabel188, new org.netbeans.lib.awtextra.AbsoluteConstraints(342, 98, 150, -1));

        ntf2.setEditable(false);
        ntf2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel23.add(ntf2, new org.netbeans.lib.awtextra.AbsoluteConstraints(536, 94, 190, 29));

        ntf4.setEditable(false);
        ntf4.setBorder(javax.swing.BorderFactory.createTitledBorder("Notication content"));
        jScrollPane14.setViewportView(ntf4);

        jPanel23.add(jScrollPane14, new org.netbeans.lib.awtextra.AbsoluteConstraints(342, 195, 385, 133));
        jPanel23.add(ntf3, new org.netbeans.lib.awtextra.AbsoluteConstraints(536, 139, 190, 30));

        jLabel189.setText("Notification send on");
        jPanel23.add(jLabel189, new org.netbeans.lib.awtextra.AbsoluteConstraints(342, 144, 150, -1));

        jTabbedPane5.addTab("View Notifications", new javax.swing.ImageIcon(getClass().getResource("/img/icons8-received-32.png")), jPanel23); // NOI18N

        javax.swing.GroupLayout notificationsLayout = new javax.swing.GroupLayout(notifications);
        notifications.setLayout(notificationsLayout);
        notificationsLayout.setHorizontalGroup(
            notificationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 995, Short.MAX_VALUE)
            .addGroup(notificationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, notificationsLayout.createSequentialGroup()
                    .addComponent(jTabbedPane5)
                    .addContainerGap()))
        );
        notificationsLayout.setVerticalGroup(
            notificationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 595, Short.MAX_VALUE)
            .addGroup(notificationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, notificationsLayout.createSequentialGroup()
                    .addComponent(jTabbedPane5)
                    .addContainerGap()))
        );

        jPanel1.add(notifications, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 140, 995, 580));

        jPanel46.setBackground(new java.awt.Color(255, 255, 255));
        jPanel46.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel61.setText("Name");
        jPanel46.add(jLabel61, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 18, -1, -1));
        jPanel46.add(jFormattedTextField16, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 11, 160, 28));

        jLabel72.setText("Surname");
        jPanel46.add(jLabel72, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 54, -1, -1));
        jPanel46.add(jFormattedTextField21, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 50, 160, 28));

        jLabel73.setText("Gender");
        jPanel46.add(jLabel73, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 94, -1, -1));
        jPanel46.add(jFormattedTextField22, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 87, 160, 28));

        jLabel74.setText("ID number");
        jPanel46.add(jLabel74, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 140, -1, -1));
        jPanel46.add(jFormattedTextField25, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 133, 160, 28));

        javax.swing.GroupLayout currenciesLayout = new javax.swing.GroupLayout(currencies);
        currencies.setLayout(currenciesLayout);
        currenciesLayout.setHorizontalGroup(
            currenciesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 995, Short.MAX_VALUE)
            .addGroup(currenciesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(currenciesLayout.createSequentialGroup()
                    .addComponent(jPanel46, javax.swing.GroupLayout.DEFAULT_SIZE, 980, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        currenciesLayout.setVerticalGroup(
            currenciesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 593, Short.MAX_VALUE)
            .addGroup(currenciesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel46, javax.swing.GroupLayout.DEFAULT_SIZE, 593, Short.MAX_VALUE))
        );

        jPanel1.add(currencies, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 140, 995, 580));

        jTabbedPane12.setBackground(new java.awt.Color(255, 255, 255));

        jPanel54.setBackground(new java.awt.Color(255, 255, 255));

        jLabel111.setText("Select currency");

        jLabel112.setText("Denomination value");

        jComboBox14.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jTable13.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Currency", "Denomination value"
            }
        ));
        jScrollPane25.setViewportView(jTable13);

        javax.swing.GroupLayout jPanel54Layout = new javax.swing.GroupLayout(jPanel54);
        jPanel54.setLayout(jPanel54Layout);
        jPanel54Layout.setHorizontalGroup(
            jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel54Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel54Layout.createSequentialGroup()
                            .addComponent(jLabel111)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBox14, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel54Layout.createSequentialGroup()
                            .addComponent(jLabel112)
                            .addGap(18, 18, 18)
                            .addComponent(jFormattedTextField44, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(523, Short.MAX_VALUE))
        );
        jPanel54Layout.setVerticalGroup(
            jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel54Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox14, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel111))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel54Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jLabel112))
                    .addComponent(jFormattedTextField44, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane25, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(165, Short.MAX_VALUE))
        );

        jTabbedPane12.addTab("Add Denominations", new javax.swing.ImageIcon(getClass().getResource("/img/ex.png")), jPanel54); // NOI18N

        jPanel56.setBackground(new java.awt.Color(255, 255, 255));

        jTable12.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Currency", "Denomination value", "Denomination ID"
            }
        ));
        jScrollPane24.setViewportView(jTable12);

        javax.swing.GroupLayout jPanel56Layout = new javax.swing.GroupLayout(jPanel56);
        jPanel56.setLayout(jPanel56Layout);
        jPanel56Layout.setHorizontalGroup(
            jPanel56Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel56Layout.createSequentialGroup()
                .addGap(193, 193, 193)
                .addComponent(jScrollPane24, javax.swing.GroupLayout.PREFERRED_SIZE, 553, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(244, Short.MAX_VALUE))
        );
        jPanel56Layout.setVerticalGroup(
            jPanel56Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel56Layout.createSequentialGroup()
                .addGap(107, 107, 107)
                .addComponent(jScrollPane24, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(99, Short.MAX_VALUE))
        );

        jTabbedPane12.addTab("Delete Denomination", new javax.swing.ImageIcon(getClass().getResource("/img/rat.png")), jPanel56); // NOI18N

        javax.swing.GroupLayout denominationsLayout = new javax.swing.GroupLayout(denominations);
        denominations.setLayout(denominationsLayout);
        denominationsLayout.setHorizontalGroup(
            denominationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 995, Short.MAX_VALUE)
            .addGroup(denominationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jTabbedPane12, javax.swing.GroupLayout.Alignment.TRAILING))
        );
        denominationsLayout.setVerticalGroup(
            denominationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 585, Short.MAX_VALUE)
            .addGroup(denominationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jTabbedPane12, javax.swing.GroupLayout.Alignment.TRAILING))
        );

        jPanel1.add(denominations, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 140, 995, 580));

        jPanel52.setBackground(new java.awt.Color(255, 255, 255));
        jPanel52.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel71.setText("Select branch");
        jPanel52.add(jLabel71, new org.netbeans.lib.awtextra.AbsoluteConstraints(342, 19, -1, -1));

        jPanel52.add(jComboBox16, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 15, 190, 28));
        jPanel52.add(jFormattedTextField6, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 94, 190, 28));

        jLabel113.setText("Enter amount");
        jPanel52.add(jLabel113, new org.netbeans.lib.awtextra.AbsoluteConstraints(342, 98, -1, -1));

        jLabel114.setText("Select currency");
        jPanel52.add(jLabel114, new org.netbeans.lib.awtextra.AbsoluteConstraints(342, 54, -1, -1));

        jPanel52.add(jComboBox17, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 50, 190, 28));

        jPanel52.add(jComboBox18, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 131, 190, 28));

        jLabel115.setText("Select denominations");
        jPanel52.add(jLabel115, new org.netbeans.lib.awtextra.AbsoluteConstraints(342, 135, -1, -1));
        jPanel52.add(jFormattedTextField7, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 168, 190, 28));

        jLabel116.setText("No. denominations");
        jPanel52.add(jLabel116, new org.netbeans.lib.awtextra.AbsoluteConstraints(342, 172, -1, -1));

        jTable14.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Branch", "Currency", "Denominations", "No. denominations", "Total"
            }
        ));
        jTable14.setRowHeight(22);
        jScrollPane26.setViewportView(jTable14);

        jPanel52.add(jScrollPane26, new org.netbeans.lib.awtextra.AbsoluteConstraints(76, 214, 862, 364));

        javax.swing.GroupLayout opening_balancesLayout = new javax.swing.GroupLayout(opening_balances);
        opening_balances.setLayout(opening_balancesLayout);
        opening_balancesLayout.setHorizontalGroup(
            opening_balancesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 995, Short.MAX_VALUE)
            .addGroup(opening_balancesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel52, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        opening_balancesLayout.setVerticalGroup(
            opening_balancesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 588, Short.MAX_VALUE)
            .addGroup(opening_balancesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel52, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(opening_balances, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 140, 995, 580));

        users.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTabbedPane10.setBackground(new java.awt.Color(255, 255, 255));

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel47.setText("Name");
        jPanel14.add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 8, 120, 30));

        a1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                a1KeyTyped(evt);
            }
        });
        jPanel14.add(a1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 8, 190, 30));

        jLabel98.setText("Surname");
        jPanel14.add(jLabel98, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 54, 120, 30));

        a2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                a2KeyTyped(evt);
            }
        });
        jPanel14.add(a2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 54, 190, 30));

        jLabel99.setText("Gender");
        jPanel14.add(jLabel99, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 100, 120, 30));

        a3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "male", "female" }));
        jPanel14.add(a3, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 100, 190, 30));

        jLabel100.setText("Phone number");
        jPanel14.add(jLabel100, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 146, 120, 30));

        a4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                a4KeyTyped(evt);
            }
        });
        jPanel14.add(a4, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 146, 190, 30));

        jLabel101.setText("ID number");
        jPanel14.add(jLabel101, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 192, 120, 30));
        jPanel14.add(a5, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 192, 190, 30));

        jLabel102.setText("Position");
        jPanel14.add(jLabel102, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 54, 140, 30));

        a_table.setAutoCreateRowSorter(true);
        a_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Surname", "Gender", "Phone number", "Region", "Position"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        a_table.setRowHeight(20);
        a_table.setShowHorizontalLines(false);
        a_table.setShowVerticalLines(false);
        jScrollPane29.setViewportView(a_table);

        jPanel14.add(jScrollPane29, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 233, 990, 295));

        jLabel103.setText("Create username");
        jPanel14.add(jLabel103, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 100, 140, 30));
        jPanel14.add(a8, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 100, 190, 30));
        jPanel14.add(a9, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 146, 190, 30));

        a10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                a10KeyPressed(evt);
            }
        });
        jPanel14.add(a10, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 192, 193, 30));

        jLabel104.setText("Create password");
        jPanel14.add(jLabel104, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 146, 140, 30));

        jLabel105.setText("Confirm password");
        jPanel14.add(jLabel105, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 192, 140, 30));

        a6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "Northern region", "Southern region", "Eastern region", "Western region" }));
        jPanel14.add(a6, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 8, 190, 30));

        jLabel117.setText("Select region");
        jPanel14.add(jLabel117, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 8, 140, 30));

        a7.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "System Adminstrator", "Regional Manager", "Finance Personel" }));
        jPanel14.add(a7, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 54, 190, 30));

        jTabbedPane10.addTab("Add Users", new javax.swing.ImageIcon(getClass().getResource("/img/icons8-registration-32.png")), jPanel14); // NOI18N

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));
        jPanel16.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        u_table.setAutoCreateRowSorter(true);
        u_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Surname", "Gender", "Region", "Branch", "Position", "Phone number", "ID number", "User ID"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        u_table.setRowHeight(20);
        u_table.setShowHorizontalLines(false);
        u_table.setShowVerticalLines(false);
        u_table.getTableHeader().setReorderingAllowed(false);
        u_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                u_tableMouseClicked(evt);
            }
        });
        jScrollPane9.setViewportView(u_table);
        if (u_table.getColumnModel().getColumnCount() > 0) {
            u_table.getColumnModel().getColumn(0).setResizable(false);
            u_table.getColumnModel().getColumn(1).setResizable(false);
            u_table.getColumnModel().getColumn(2).setResizable(false);
            u_table.getColumnModel().getColumn(3).setResizable(false);
            u_table.getColumnModel().getColumn(4).setResizable(false);
            u_table.getColumnModel().getColumn(5).setResizable(false);
            u_table.getColumnModel().getColumn(6).setResizable(false);
            u_table.getColumnModel().getColumn(7).setResizable(false);
            u_table.getColumnModel().getColumn(8).setResizable(false);
        }

        jPanel16.add(jScrollPane9, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 0, 990, 348));

        jLabel48.setText("Name");
        jPanel16.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, 77, 30));

        jLabel106.setText("Surname");
        jPanel16.add(jLabel106, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 405, 77, 30));

        jLabel107.setText("Gender");
        jPanel16.add(jLabel107, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 450, 77, 30));

        jLabel108.setText("Phone number");
        jPanel16.add(jLabel108, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 360, 120, 30));

        jLabel109.setText("ID number");
        jPanel16.add(jLabel109, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 495, 77, 26));
        jPanel16.add(u5, new org.netbeans.lib.awtextra.AbsoluteConstraints(97, 495, 190, 28));
        jPanel16.add(u4, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 360, 190, 30));

        u3.setEditable(true);
        u3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "male", "female" }));
        u3.setFocusable(false);
        jPanel16.add(u3, new org.netbeans.lib.awtextra.AbsoluteConstraints(97, 450, 190, 30));
        jPanel16.add(u2, new org.netbeans.lib.awtextra.AbsoluteConstraints(97, 405, 190, 30));
        jPanel16.add(u1, new org.netbeans.lib.awtextra.AbsoluteConstraints(97, 360, 190, 30));

        jLabel110.setText("Position");
        jPanel16.add(jLabel110, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 450, 120, 30));

        u6.setEditable(true);
        u6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "", "Northern region", "Southern region", "Eastern region", "Western region" }));
        u6.setFocusable(false);
        jPanel16.add(u6, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 405, 190, 30));

        jLabel49.setText("Select Region");
        jPanel16.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 405, 120, 30));

        u7.setEditable(true);
        u7.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "System Adminstrator", "Regional Manager", "Finance Personel" }));
        u7.setFocusable(false);
        jPanel16.add(u7, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 450, 190, 30));
        jPanel16.add(uid, new org.netbeans.lib.awtextra.AbsoluteConstraints(721, 533, 60, -1));

        jLabel42.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel42.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-new-document-32.png"))); // NOI18N
        jLabel42.setText("Update Account");
        jLabel42.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel42MouseClicked(evt);
            }
        });
        jPanel16.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 495, 190, -1));

        jTabbedPane10.addTab("Update Users", new javax.swing.ImageIcon(getClass().getResource("/img/icons8-more-info-32.png")), jPanel16); // NOI18N

        jPanel35.setBackground(new java.awt.Color(255, 255, 255));
        jPanel35.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        d_table.setAutoCreateRowSorter(true);
        d_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Surname", "Gender", "Branch", "Position", "Phone number", "ID number", "User ID"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        d_table.setRowHeight(20);
        d_table.setShowHorizontalLines(false);
        d_table.setShowVerticalLines(false);
        d_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                d_tableMouseClicked(evt);
            }
        });
        d_table.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                d_tableKeyPressed(evt);
            }
        });
        jScrollPane30.setViewportView(d_table);
        if (d_table.getColumnModel().getColumnCount() > 0) {
            d_table.getColumnModel().getColumn(0).setResizable(false);
            d_table.getColumnModel().getColumn(1).setResizable(false);
            d_table.getColumnModel().getColumn(2).setResizable(false);
            d_table.getColumnModel().getColumn(3).setResizable(false);
            d_table.getColumnModel().getColumn(4).setResizable(false);
            d_table.getColumnModel().getColumn(5).setResizable(false);
            d_table.getColumnModel().getColumn(6).setResizable(false);
            d_table.getColumnModel().getColumn(7).setResizable(false);
        }

        jPanel35.add(jScrollPane30, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 0, 990, 530));
        jPanel35.add(na, new org.netbeans.lib.awtextra.AbsoluteConstraints(597, 534, -1, -1));
        jPanel35.add(nb, new org.netbeans.lib.awtextra.AbsoluteConstraints(633, 528, -1, -1));

        jTabbedPane10.addTab("Delete User", new javax.swing.ImageIcon(getClass().getResource("/img/icons8-denied-32.png")), jPanel35); // NOI18N

        jPanel27.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        table_reset.setAutoCreateRowSorter(true);
        table_reset.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Account Holder", "Region", "Branch", "Position", "User ID"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table_reset.setRowHeight(20);
        table_reset.setShowHorizontalLines(false);
        table_reset.setShowVerticalLines(false);
        table_reset.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_resetMouseClicked(evt);
            }
        });
        table_reset.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                table_resetKeyPressed(evt);
            }
        });
        jScrollPane13.setViewportView(table_reset);
        if (table_reset.getColumnModel().getColumnCount() > 0) {
            table_reset.getColumnModel().getColumn(0).setResizable(false);
            table_reset.getColumnModel().getColumn(1).setResizable(false);
            table_reset.getColumnModel().getColumn(2).setResizable(false);
            table_reset.getColumnModel().getColumn(3).setResizable(false);
            table_reset.getColumnModel().getColumn(4).setResizable(false);
        }

        jPanel27.add(jScrollPane13, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 0, 990, 530));

        wtq.setText("jLabel183");
        jPanel27.add(wtq, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 536, 1, 14));

        jTabbedPane10.addTab("Reset password", new javax.swing.ImageIcon(getClass().getResource("/img/icons8-keepass-32.png")), jPanel27); // NOI18N

        users.add(jTabbedPane10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 580));

        jPanel1.add(users, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 140, 995, 580));

        reports.setBackground(new java.awt.Color(255, 255, 255));
        reports.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTabbedPane7.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane7.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jTabbedPane7.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        jTabbedPane7.setFocusable(false);

        sales_rep.setBackground(new java.awt.Color(255, 255, 255));
        sales_rep.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTabbedPane8.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane8.setFocusable(false);

        jPanel30.setBackground(new java.awt.Color(255, 255, 255));
        jPanel30.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jm_table1.setAutoCreateRowSorter(true);
        jm_table1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Currency Out", "Currency In", "Exchange rate", "Received", "Send", "Region", "Branch", "Cashier", "Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jm_table1.setRowHeight(20);
        jm_table1.setShowHorizontalLines(false);
        jm_table1.setShowVerticalLines(false);
        jm_table1.getTableHeader().setReorderingAllowed(false);
        jScrollPane21.setViewportView(jm_table1);

        jPanel30.add(jScrollPane21, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 143, 1070, 340));

        fa1.setBackground(new java.awt.Color(255, 255, 255));
        fa1.setText(" L. C Used ");
        fa1.setFocusable(false);
        fa1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fa1ActionPerformed(evt);
            }
        });
        jPanel30.add(fa1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 6, 140, 30));

        fa2.setBackground(new java.awt.Color(255, 255, 255));
        fa2.setText("Cross Currency");
        fa2.setFocusable(false);
        fa2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fa2ActionPerformed(evt);
            }
        });
        jPanel30.add(fa2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 52, 140, 30));

        jLabel94.setText("Start date");
        jPanel30.add(jLabel94, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 6, 70, 30));

        mj1.setDateFormatString("y-MM-dd hh:mm:ss.ml");
        jPanel30.add(mj1, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 6, 150, 30));

        mj2.setDateFormatString("y-MM-dd");
        jPanel30.add(mj2, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 52, 152, 30));

        jLabel95.setText("End date");
        jPanel30.add(jLabel95, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 52, 68, 30));

        fa3.setBackground(new java.awt.Color(255, 255, 255));
        fa3.setText("Regional Sales");
        fa3.setFocusable(false);
        fa3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fa3ActionPerformed(evt);
            }
        });
        jPanel30.add(fa3, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 6, 140, 30));

        fa4.setBackground(new java.awt.Color(255, 255, 255));
        fa4.setText("Branch Sales");
        fa4.setFocusable(false);
        fa4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fa4ActionPerformed(evt);
            }
        });
        jPanel30.add(fa4, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 52, 140, 30));

        jLabel75.setText("Select Region");
        jPanel30.add(jLabel75, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 6, -1, 30));

        fa5.setEditable(true);
        fa5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "Northern region", "Southern region", "Eastern region", "Western region" }));
        fa5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fa5ActionPerformed(evt);
            }
        });
        jPanel30.add(fa5, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 6, 155, 30));

        jLabel76.setText("Select Branch");
        jPanel30.add(jLabel76, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 52, -1, 30));

        fa6.setEditable(true);
        fa6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
        jPanel30.add(fa6, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 52, 155, 30));

        jLabel128.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/phist.png"))); // NOI18N
        jLabel128.setText("Specify Currency");
        jLabel128.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel128MouseClicked(evt);
            }
        });
        jPanel30.add(jLabel128, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 20, 155, 40));

        w1.setText("jLabel163");
        jPanel30.add(w1, new org.netbeans.lib.awtextra.AbsoluteConstraints(888, 14, -1, 0));

        w2.setText("jLabel164");
        jPanel30.add(w2, new org.netbeans.lib.awtextra.AbsoluteConstraints(888, 60, -1, 0));

        bscd.setText("jLabel92");
        jPanel30.add(bscd, new org.netbeans.lib.awtextra.AbsoluteConstraints(733, 92, -1, 0));

        eco1.setBackground(new java.awt.Color(0, 102, 153));
        eco1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        eco1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel45.setForeground(new java.awt.Color(255, 255, 255));
        jLabel45.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/pdf.png"))); // NOI18N
        jLabel45.setText("save as pdf");
        eco1.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 140, 40));

        jPanel30.add(eco1, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 90, 140, 40));

        eco2.setBackground(new java.awt.Color(0, 153, 102));
        eco2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        eco2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel50.setForeground(new java.awt.Color(255, 255, 255));
        jLabel50.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/excel.png"))); // NOI18N
        jLabel50.setText("save as excel");
        jLabel50.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel50MouseClicked(evt);
            }
        });
        eco2.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 140, 40));

        jPanel30.add(eco2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 140, 40));

        eco3.setBackground(new java.awt.Color(255, 204, 0));
        eco3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        eco3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel51.setBackground(new java.awt.Color(255, 255, 0));
        jLabel51.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/print.png"))); // NOI18N
        jLabel51.setText("print file");
        jLabel51.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel51MouseClicked(evt);
            }
        });
        eco3.add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 140, 40));

        jPanel30.add(eco3, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 90, 140, 40));

        eco4.setBackground(new java.awt.Color(255, 255, 204));
        eco4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        eco4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel52.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-transaction-32.png"))); // NOI18N
        jLabel52.setText("fetch data");
        jLabel52.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel52MouseClicked(evt);
            }
        });
        eco4.add(jLabel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 140, 40));

        jPanel30.add(eco4, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 90, 140, 40));

        eco5.setBackground(new java.awt.Color(255, 102, 51));
        eco5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        eco5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel53.setBackground(new java.awt.Color(255, 255, 0));
        jLabel53.setForeground(new java.awt.Color(255, 255, 255));
        jLabel53.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/print.png"))); // NOI18N
        jLabel53.setText("more ...");
        jLabel53.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel53MouseClicked(evt);
            }
        });
        eco5.add(jLabel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 140, 40));

        jPanel30.add(eco5, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 90, 140, 40));

        jTabbedPane8.addTab("Sales Between Dates", new javax.swing.ImageIcon(getClass().getResource("/img/bd.png")), jPanel30); // NOI18N

        jPanel31.setBackground(new java.awt.Color(255, 255, 255));
        jPanel31.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        fb1.setBackground(new java.awt.Color(255, 255, 255));
        fb1.setText(" L. C Used ?");
        fb1.setFocusable(false);
        fb1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fb1ActionPerformed(evt);
            }
        });
        jPanel31.add(fb1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 6, 140, 30));

        fb2.setBackground(new java.awt.Color(255, 255, 255));
        fb2.setText("Cross Currency");
        fb2.setFocusable(false);
        fb2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fb2ActionPerformed(evt);
            }
        });
        jPanel31.add(fb2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 52, 140, 30));

        jLabel133.setText("Select Date");
        jPanel31.add(jLabel133, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 6, 80, 30));

        fb7.setDateFormatString("y-MM-dd");
        jPanel31.add(fb7, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 6, 150, 30));

        fb3.setBackground(new java.awt.Color(255, 255, 255));
        fb3.setText("Regional Sales");
        fb3.setFocusable(false);
        fb3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fb3ActionPerformed(evt);
            }
        });
        jPanel31.add(fb3, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 6, -1, 30));

        fb4.setBackground(new java.awt.Color(255, 255, 255));
        fb4.setText("Branch Sales");
        fb4.setFocusable(false);
        fb4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fb4ActionPerformed(evt);
            }
        });
        jPanel31.add(fb4, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 52, 140, 30));

        jLabel77.setText("Select Region");
        jPanel31.add(jLabel77, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 6, -1, 30));

        fb5.setEditable(true);
        fb5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "Northern region", "Southern region", "Eastern region", "Western region" }));
        fb5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fb5ActionPerformed(evt);
            }
        });
        jPanel31.add(fb5, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 6, 155, 30));

        jLabel78.setText("Select Branch");
        jPanel31.add(jLabel78, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 52, -1, 30));

        fb6.setEditable(true);
        fb6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
        jPanel31.add(fb6, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 52, 155, 30));

        fw1.setText("jLabel134");
        jPanel31.add(fw1, new org.netbeans.lib.awtextra.AbsoluteConstraints(861, 20, -1, 0));

        fw2.setText("jLabel163");
        jPanel31.add(fw2, new org.netbeans.lib.awtextra.AbsoluteConstraints(985, 78, 0, 4));

        fb_table.setAutoCreateRowSorter(true);
        fb_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Currency Out", "Currency In", "Exchange rate", "Amount Traded In", "Amount Traded Out", "Region", "Branch", "Cashier", "Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        fb_table.setRowHeight(20);
        fb_table.setShowHorizontalLines(false);
        fb_table.setShowVerticalLines(false);
        fb_table.getTableHeader().setReorderingAllowed(false);
        jScrollPane35.setViewportView(fb_table);

        jPanel31.add(jScrollPane35, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 143, 1070, 340));

        eco6.setBackground(new java.awt.Color(0, 102, 153));
        eco6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        eco6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel54.setForeground(new java.awt.Color(255, 255, 255));
        jLabel54.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/pdf.png"))); // NOI18N
        jLabel54.setText("save as pdf");
        eco6.add(jLabel54, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 140, 40));

        jPanel31.add(eco6, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 90, 140, 40));

        eco7.setBackground(new java.awt.Color(0, 153, 102));
        eco7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        eco7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel55.setForeground(new java.awt.Color(255, 255, 255));
        jLabel55.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/excel.png"))); // NOI18N
        jLabel55.setText("save as excel");
        jLabel55.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel55MouseClicked(evt);
            }
        });
        eco7.add(jLabel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 140, 40));

        jPanel31.add(eco7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 140, 40));

        eco8.setBackground(new java.awt.Color(255, 204, 0));
        eco8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        eco8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel89.setBackground(new java.awt.Color(255, 255, 0));
        jLabel89.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/print.png"))); // NOI18N
        jLabel89.setText("print file");
        jLabel89.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel89MouseClicked(evt);
            }
        });
        eco8.add(jLabel89, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 140, 40));

        jPanel31.add(eco8, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 90, 140, 40));

        eco9.setBackground(new java.awt.Color(255, 255, 204));
        eco9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        eco9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel91.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-transaction-32.png"))); // NOI18N
        jLabel91.setText("fetch data");
        jLabel91.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel91MouseClicked(evt);
            }
        });
        eco9.add(jLabel91, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 140, 40));

        jPanel31.add(eco9, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 90, 140, 40));

        eco10.setBackground(new java.awt.Color(255, 102, 51));
        eco10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        eco10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel93.setBackground(new java.awt.Color(255, 255, 0));
        jLabel93.setForeground(new java.awt.Color(255, 255, 255));
        jLabel93.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/print.png"))); // NOI18N
        jLabel93.setText("more ...");
        jLabel93.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel93MouseClicked(evt);
            }
        });
        eco10.add(jLabel93, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 140, 40));

        jPanel31.add(eco10, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 90, 140, 40));

        jTabbedPane8.addTab("Daily Sales", new javax.swing.ImageIcon(getClass().getResource("/img/ym.png")), jPanel31); // NOI18N

        jPanel37.setBackground(new java.awt.Color(255, 255, 255));
        jPanel37.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        zm_table.setAutoCreateRowSorter(true);
        zm_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Currency Out", "Currency In", "Exchange rate", "Amount Traded In", "Amount Traded Out", "Region", "Branch", "Cashier", "Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        zm_table.setRowHeight(20);
        zm_table.setShowHorizontalLines(false);
        zm_table.setShowVerticalLines(false);
        zm_table.getTableHeader().setReorderingAllowed(false);
        jScrollPane36.setViewportView(zm_table);

        jPanel37.add(jScrollPane36, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 143, 1070, 340));

        fc1.setBackground(new java.awt.Color(255, 255, 255));
        fc1.setText(" L. C Used ?");
        fc1.setFocusable(false);
        fc1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fc1ActionPerformed(evt);
            }
        });
        jPanel37.add(fc1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 6, 140, 30));

        fc2.setBackground(new java.awt.Color(255, 255, 255));
        fc2.setText("Cross Currency");
        fc2.setFocusable(false);
        fc2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fc2ActionPerformed(evt);
            }
        });
        jPanel37.add(fc2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 52, 140, 30));

        jLabel141.setText("Select month");
        jPanel37.add(jLabel141, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 6, 100, 30));

        fc3.setBackground(new java.awt.Color(255, 255, 255));
        fc3.setText("Regional Sales");
        fc3.setFocusable(false);
        fc3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fc3ActionPerformed(evt);
            }
        });
        jPanel37.add(fc3, new org.netbeans.lib.awtextra.AbsoluteConstraints(175, 6, 140, 30));

        fc4.setBackground(new java.awt.Color(255, 255, 255));
        fc4.setText("Branch Sales");
        fc4.setFocusable(false);
        fc4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fc4ActionPerformed(evt);
            }
        });
        jPanel37.add(fc4, new org.netbeans.lib.awtextra.AbsoluteConstraints(175, 52, 140, 30));

        jLabel79.setText("Select Region");
        jPanel37.add(jLabel79, new org.netbeans.lib.awtextra.AbsoluteConstraints(315, 6, -1, 30));

        fc5.setEditable(true);
        fc5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "Northern region", "Southern region", "Eastern region", "Western region" }));
        fc5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fc5ActionPerformed(evt);
            }
        });
        jPanel37.add(fc5, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 6, 155, 30));

        jLabel80.setText("Select Branch");
        jPanel37.add(jLabel80, new org.netbeans.lib.awtextra.AbsoluteConstraints(315, 52, -1, 30));

        fc6.setEditable(true);
        fc6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
        jPanel37.add(fc6, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 52, 155, 30));

        zm1.setModel(new javax.swing.SpinnerListModel(new String[] {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"}));
        jPanel37.add(zm1, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 6, 138, 30));

        zm5.setText("jLabel134");
        jPanel37.add(zm5, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 4, -1, 0));

        sym.setModel(new javax.swing.SpinnerListModel(new String[] {"2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030", "2031", "2032", "2033", "2034", "2035", "2036", "2037", "2040", "2050", "2051", "2052", "2053", "2054", "2055", "2056", "2057", "2058", "2059", "2060", "2061", "2062", "2063", "2064", "2065", "2066", "2067", "2068", "2069", "2070", "2071", "2072", "2073", "2073", "2074", "2075", "2076", "2077", "2078", "2079", "2080", "2081", "2082", "2083", "2084", "2085", "2086", "2087", "2088", "2089", "2090", "2091", "2092", "2093", "2094", "2095", "2096", "2097", "2098", "2099"}));
        jPanel37.add(sym, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 52, 138, 30));

        jLabel144.setText("Select year");
        jPanel37.add(jLabel144, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 52, 100, 30));

        eco11.setBackground(new java.awt.Color(0, 102, 153));
        eco11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        eco11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel96.setForeground(new java.awt.Color(255, 255, 255));
        jLabel96.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/pdf.png"))); // NOI18N
        jLabel96.setText("save as pdf");
        eco11.add(jLabel96, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 140, 40));

        jPanel37.add(eco11, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 90, 140, 40));

        eco12.setBackground(new java.awt.Color(0, 153, 102));
        eco12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        eco12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel137.setForeground(new java.awt.Color(255, 255, 255));
        jLabel137.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/excel.png"))); // NOI18N
        jLabel137.setText("save as excel");
        jLabel137.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel137MouseClicked(evt);
            }
        });
        eco12.add(jLabel137, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 140, 40));

        jPanel37.add(eco12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 140, 40));

        eco13.setBackground(new java.awt.Color(255, 204, 0));
        eco13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        eco13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel145.setBackground(new java.awt.Color(255, 255, 0));
        jLabel145.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/print.png"))); // NOI18N
        jLabel145.setText("print file");
        jLabel145.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel145MouseClicked(evt);
            }
        });
        eco13.add(jLabel145, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 140, 40));

        jPanel37.add(eco13, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 90, 140, 40));

        eco14.setBackground(new java.awt.Color(255, 255, 204));
        eco14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        eco14.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel152.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-transaction-32.png"))); // NOI18N
        jLabel152.setText("fetch data");
        jLabel152.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel152MouseClicked(evt);
            }
        });
        eco14.add(jLabel152, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 140, 40));

        jPanel37.add(eco14, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 90, 140, 40));

        eco15.setBackground(new java.awt.Color(255, 102, 51));
        eco15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        eco15.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel153.setBackground(new java.awt.Color(255, 255, 0));
        jLabel153.setForeground(new java.awt.Color(255, 255, 255));
        jLabel153.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/print.png"))); // NOI18N
        jLabel153.setText("more ...");
        jLabel153.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel153MouseClicked(evt);
            }
        });
        eco15.add(jLabel153, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 140, 40));

        jPanel37.add(eco15, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 90, 140, 40));

        jTabbedPane8.addTab("Monthly Sales", new javax.swing.ImageIcon(getClass().getResource("/img/td.png")), jPanel37); // NOI18N

        jPanel38.setBackground(new java.awt.Color(255, 255, 255));
        jPanel38.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        fd_table.setAutoCreateRowSorter(true);
        fd_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Currency Out", "Currency In ", "Exchange rate", "Amount Traded In", "Amount Traded Out", "Region", "Branch", "Cashier", "Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        fd_table.setRowHeight(20);
        fd_table.setShowHorizontalLines(false);
        fd_table.setShowVerticalLines(false);
        fd_table.getTableHeader().setReorderingAllowed(false);
        jScrollPane37.setViewportView(fd_table);

        jPanel38.add(jScrollPane37, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 143, 1070, 340));

        fd1.setBackground(new java.awt.Color(255, 255, 255));
        fd1.setText(" L. C Used ?");
        fd1.setFocusable(false);
        fd1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fd1ActionPerformed(evt);
            }
        });
        jPanel38.add(fd1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 6, 140, 30));

        fd2.setBackground(new java.awt.Color(255, 255, 255));
        fd2.setText("Cross Currency");
        fd2.setFocusable(false);
        fd2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fd2ActionPerformed(evt);
            }
        });
        jPanel38.add(fd2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 52, 140, 30));

        jLabel149.setText("Start date");
        jPanel38.add(jLabel149, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 6, 70, 30));

        fd3.setBackground(new java.awt.Color(255, 255, 255));
        fd3.setText("Regional Sales");
        fd3.setFocusable(false);
        fd3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fd3ActionPerformed(evt);
            }
        });
        jPanel38.add(fd3, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 6, 140, 30));

        fd4.setBackground(new java.awt.Color(255, 255, 255));
        fd4.setText("Branch Sales");
        fd4.setFocusable(false);
        fd4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fd4ActionPerformed(evt);
            }
        });
        jPanel38.add(fd4, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 52, 140, 30));

        jLabel81.setText("Select Region");
        jPanel38.add(jLabel81, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 6, -1, 30));

        fd5.setEditable(true);
        fd5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "Northern region", "Southern region", "Eastern region", "Western region" }));
        fd5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fd5ActionPerformed(evt);
            }
        });
        jPanel38.add(fd5, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 6, 155, 30));

        jLabel82.setText("Select Branch");
        jPanel38.add(jLabel82, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 52, -1, 30));

        fd6.setEditable(true);
        fd6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
        jPanel38.add(fd6, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 52, 155, 30));

        fd7.setModel(new javax.swing.SpinnerListModel(new String[] {"2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030", "2031", "2032", "2033", "2034", "2035", "2036", "2037", "2040", "2050", "2051", "2052", "2053", "2054", "2055", "2056", "2057", "2058", "2059", "2060", "2061", "2062", "2063", "2064", "2065", "2066", "2067", "2068", "2069", "2070", "2071", "2072", "2073", "2073", "2074", "2075", "2076", "2077", "2078", "2079", "2080", "2081", "2082", "2083", "2084", "2085", "2086", "2087", "2088", "2089", "2090", "2091", "2092", "2093", "2094", "2095", "2096", "2097", "2098", "2099"}));
        jPanel38.add(fd7, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 6, 139, 30));

        eco16.setBackground(new java.awt.Color(0, 102, 153));
        eco16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        eco16.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel166.setForeground(new java.awt.Color(255, 255, 255));
        jLabel166.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/pdf.png"))); // NOI18N
        jLabel166.setText("save as pdf");
        eco16.add(jLabel166, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 140, 40));

        jPanel38.add(eco16, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 90, 140, 40));

        eco17.setBackground(new java.awt.Color(0, 153, 102));
        eco17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        eco17.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel175.setForeground(new java.awt.Color(255, 255, 255));
        jLabel175.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/excel.png"))); // NOI18N
        jLabel175.setText("save as excel");
        jLabel175.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel175MouseClicked(evt);
            }
        });
        eco17.add(jLabel175, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 140, 40));

        jPanel38.add(eco17, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 140, 40));

        eco18.setBackground(new java.awt.Color(255, 204, 0));
        eco18.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        eco18.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel198.setBackground(new java.awt.Color(255, 255, 0));
        jLabel198.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/print.png"))); // NOI18N
        jLabel198.setText("print file");
        jLabel198.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel198MouseClicked(evt);
            }
        });
        eco18.add(jLabel198, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 140, 40));

        jPanel38.add(eco18, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 90, 140, 40));

        eco19.setBackground(new java.awt.Color(255, 255, 204));
        eco19.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        eco19.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel199.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-transaction-32.png"))); // NOI18N
        jLabel199.setText("fetch data");
        jLabel199.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel199MouseClicked(evt);
            }
        });
        eco19.add(jLabel199, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 140, 40));

        jPanel38.add(eco19, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 90, 140, 40));

        eco20.setBackground(new java.awt.Color(255, 102, 51));
        eco20.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        eco20.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel200.setBackground(new java.awt.Color(255, 255, 0));
        jLabel200.setForeground(new java.awt.Color(255, 255, 255));
        jLabel200.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/print.png"))); // NOI18N
        jLabel200.setText("more ...");
        jLabel200.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel200MouseClicked(evt);
            }
        });
        eco20.add(jLabel200, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 140, 40));

        jPanel38.add(eco20, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 90, 140, 40));

        jTabbedPane8.addTab("Annualy Sales", new javax.swing.ImageIcon(getClass().getResource("/img/mm.png")), jPanel38); // NOI18N

        sales_rep.add(jTabbedPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1090, 540));

        jTabbedPane7.addTab("Sales Reports", new javax.swing.ImageIcon(getClass().getResource("/img/sp.png")), sales_rep); // NOI18N

        purchases_rep.setBackground(new java.awt.Color(255, 255, 255));
        purchases_rep.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTabbedPane9.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane9.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jTabbedPane9.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        jTabbedPane9.setFocusable(false);

        jPanel39.setBackground(new java.awt.Color(255, 255, 255));
        jPanel39.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        py_table.setAutoCreateRowSorter(true);
        py_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Currency In", "Currency Out", "Amount Traded In", "Exchange Rate", "Amount Traded Out", "Region", "Branch", "Cashier", "Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        py_table.setRowHeight(20);
        py_table.setShowHorizontalLines(false);
        py_table.setShowVerticalLines(false);
        py_table.getTableHeader().setReorderingAllowed(false);
        jScrollPane38.setViewportView(py_table);

        jPanel39.add(jScrollPane38, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 403, 1070, 80));

        jLabel92.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/excel.png"))); // NOI18N
        jLabel92.setText("Save as CSV File");
        jLabel92.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel92MouseClicked(evt);
            }
        });
        jPanel39.add(jLabel92, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 92, -1, 40));

        jLabel134.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/print.png"))); // NOI18N
        jLabel134.setText("Print / Save as XPS");
        jLabel134.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel134MouseClicked(evt);
            }
        });
        jPanel39.add(jLabel134, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 92, -1, 40));

        py1.setBackground(new java.awt.Color(255, 255, 255));
        py1.setText(" L. C Received ?");
        py1.setFocusable(false);
        py1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                py1ActionPerformed(evt);
            }
        });
        jPanel39.add(py1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 6, 110, 30));

        py2.setBackground(new java.awt.Color(255, 255, 255));
        py2.setText("Cross Currency");
        py2.setFocusable(false);
        py2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                py2ActionPerformed(evt);
            }
        });
        jPanel39.add(py2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 52, -1, 30));

        jLabel142.setText("Start date");
        jPanel39.add(jLabel142, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 6, 70, 30));

        py7.setDateFormatString("y-MM-dd hh:mm:ss.ml");
        jPanel39.add(py7, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 6, 150, 30));

        py8.setDateFormatString("y-MM-dd");
        jPanel39.add(py8, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 52, 152, 30));

        jLabel150.setText("End date");
        jPanel39.add(jLabel150, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 52, 68, 30));

        jLabel154.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/dbv.png"))); // NOI18N
        jLabel154.setText("Fetch Data");
        jLabel154.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel154MouseClicked(evt);
            }
        });
        jPanel39.add(jLabel154, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 92, -1, 40));

        py3.setBackground(new java.awt.Color(255, 255, 255));
        py3.setText("Regional Sales");
        py3.setFocusable(false);
        py3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                py3ActionPerformed(evt);
            }
        });
        jPanel39.add(py3, new org.netbeans.lib.awtextra.AbsoluteConstraints(137, 6, -1, 30));

        py4.setBackground(new java.awt.Color(255, 255, 255));
        py4.setText("Branch Sales");
        py4.setFocusable(false);
        py4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                py4ActionPerformed(evt);
            }
        });
        jPanel39.add(py4, new org.netbeans.lib.awtextra.AbsoluteConstraints(137, 52, 95, 30));

        jLabel83.setText("Select Region");
        jPanel39.add(jLabel83, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 6, -1, 30));

        py5.setEditable(true);
        py5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "Northern region", "Southern region", "Eastern region", "Western region" }));
        py5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                py5ActionPerformed(evt);
            }
        });
        jPanel39.add(py5, new org.netbeans.lib.awtextra.AbsoluteConstraints(325, 6, 155, 30));

        jLabel155.setText("Select Branch");
        jPanel39.add(jLabel155, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 52, -1, 30));

        py6.setEditable(true);
        py6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
        jPanel39.add(py6, new org.netbeans.lib.awtextra.AbsoluteConstraints(325, 52, 155, 30));

        jLabel156.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/phist.png"))); // NOI18N
        jLabel156.setText("Specify Currency");
        jLabel156.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel156MouseClicked(evt);
            }
        });
        jPanel39.add(jLabel156, new org.netbeans.lib.awtextra.AbsoluteConstraints(325, 92, 151, 40));

        kb1.setText("jLabel163");
        jPanel39.add(kb1, new org.netbeans.lib.awtextra.AbsoluteConstraints(838, 34, -1, 0));

        kb2.setText("jLabel164");
        jPanel39.add(kb2, new org.netbeans.lib.awtextra.AbsoluteConstraints(838, 80, -1, 0));

        bfc.setText("jLabel92");
        jPanel39.add(bfc, new org.netbeans.lib.awtextra.AbsoluteConstraints(683, 98, -1, 0));

        eco21.setBackground(new java.awt.Color(0, 102, 153));
        eco21.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        eco21.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel201.setForeground(new java.awt.Color(255, 255, 255));
        jLabel201.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/pdf.png"))); // NOI18N
        jLabel201.setText("save as pdf");
        eco21.add(jLabel201, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 140, 40));

        jPanel39.add(eco21, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 150, 140, 40));

        eco22.setBackground(new java.awt.Color(0, 153, 102));
        eco22.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        eco22.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel202.setForeground(new java.awt.Color(255, 255, 255));
        jLabel202.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/excel.png"))); // NOI18N
        jLabel202.setText("save as excel");
        jLabel202.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel202MouseClicked(evt);
            }
        });
        eco22.add(jLabel202, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 140, 40));

        jPanel39.add(eco22, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 140, 40));

        eco23.setBackground(new java.awt.Color(255, 204, 0));
        eco23.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        eco23.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel203.setBackground(new java.awt.Color(255, 255, 0));
        jLabel203.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/print.png"))); // NOI18N
        jLabel203.setText("print file");
        jLabel203.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel203MouseClicked(evt);
            }
        });
        eco23.add(jLabel203, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 140, 40));

        jPanel39.add(eco23, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 150, 140, 40));

        eco24.setBackground(new java.awt.Color(255, 255, 204));
        eco24.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        eco24.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel204.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-transaction-32.png"))); // NOI18N
        jLabel204.setText("fetch data");
        jLabel204.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel204MouseClicked(evt);
            }
        });
        eco24.add(jLabel204, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 140, 40));

        jPanel39.add(eco24, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 150, 140, 40));

        eco25.setBackground(new java.awt.Color(255, 102, 51));
        eco25.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        eco25.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel205.setBackground(new java.awt.Color(255, 255, 0));
        jLabel205.setForeground(new java.awt.Color(255, 255, 255));
        jLabel205.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/print.png"))); // NOI18N
        jLabel205.setText("more ...");
        jLabel205.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel205MouseClicked(evt);
            }
        });
        eco25.add(jLabel205, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 140, 40));

        jPanel39.add(eco25, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 150, 140, 40));

        jTabbedPane9.addTab("Purchases Between Dates", new javax.swing.ImageIcon(getClass().getResource("/img/bd.png")), jPanel39); // NOI18N

        jPanel40.setBackground(new java.awt.Color(255, 255, 255));
        jPanel40.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        wy_table.setAutoCreateRowSorter(true);
        wy_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Currency In", "Currency Out", "Amount Traded In ", "Exchange Rate", "Amount Traded Out", "Region", "Branch", "Cashier", "Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        wy_table.setRowHeight(20);
        wy_table.setShowHorizontalLines(false);
        wy_table.setShowVerticalLines(false);
        wy_table.getTableHeader().setReorderingAllowed(false);
        jScrollPane39.setViewportView(wy_table);

        jPanel40.add(jScrollPane39, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 353, 1070, 130));

        jLabel158.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/excel.png"))); // NOI18N
        jLabel158.setText("Save as CSV File");
        jLabel158.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel158MouseClicked(evt);
            }
        });
        jPanel40.add(jLabel158, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 92, -1, 40));

        jLabel159.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/print.png"))); // NOI18N
        jLabel159.setText("Print / Save as XPS");
        jLabel159.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel159MouseClicked(evt);
            }
        });
        jPanel40.add(jLabel159, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 92, -1, 40));

        jLabel160.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/phist.png"))); // NOI18N
        jLabel160.setText("Specify Currency");
        jPanel40.add(jLabel160, new org.netbeans.lib.awtextra.AbsoluteConstraints(325, 92, -1, 40));

        wy1.setBackground(new java.awt.Color(255, 255, 255));
        wy1.setText(" L. C Received ?");
        wy1.setFocusable(false);
        wy1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wy1ActionPerformed(evt);
            }
        });
        jPanel40.add(wy1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 6, 110, 30));

        wy2.setBackground(new java.awt.Color(255, 255, 255));
        wy2.setText("Cross Currency");
        wy2.setFocusable(false);
        wy2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wy2ActionPerformed(evt);
            }
        });
        jPanel40.add(wy2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 52, -1, 30));

        jLabel161.setText("Select Date");
        jPanel40.add(jLabel161, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 6, 70, 30));

        wy7.setDateFormatString("y-MM-dd");
        jPanel40.add(wy7, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 6, 150, 30));

        jLabel162.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/dbv.png"))); // NOI18N
        jLabel162.setText("Fetch Data");
        jLabel162.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel162MouseClicked(evt);
            }
        });
        jPanel40.add(jLabel162, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 92, -1, 40));

        wy3.setBackground(new java.awt.Color(255, 255, 255));
        wy3.setText("Regional Sales");
        wy3.setFocusable(false);
        wy3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wy3ActionPerformed(evt);
            }
        });
        jPanel40.add(wy3, new org.netbeans.lib.awtextra.AbsoluteConstraints(137, 6, 111, 30));

        wy4.setBackground(new java.awt.Color(255, 255, 255));
        wy4.setText("Branch Sales");
        wy4.setFocusable(false);
        wy4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wy4ActionPerformed(evt);
            }
        });
        jPanel40.add(wy4, new org.netbeans.lib.awtextra.AbsoluteConstraints(137, 52, 111, 30));

        jLabel163.setText("Select Region");
        jPanel40.add(jLabel163, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 6, -1, 30));

        wy5.setEditable(true);
        wy5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "", "Northern region", "Southern region", "Eastern region", "Western region" }));
        wy5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wy5ActionPerformed(evt);
            }
        });
        jPanel40.add(wy5, new org.netbeans.lib.awtextra.AbsoluteConstraints(325, 6, 155, 30));

        jLabel164.setText("Select Branch");
        jPanel40.add(jLabel164, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 52, -1, 30));

        wy6.setEditable(true);
        wy6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
        jPanel40.add(wy6, new org.netbeans.lib.awtextra.AbsoluteConstraints(325, 52, 155, 30));

        ts1.setText("jLabel134");
        jPanel40.add(ts1, new org.netbeans.lib.awtextra.AbsoluteConstraints(939, 45, 4, 2));

        ts2.setText("jLabel163");
        jPanel40.add(ts2, new org.netbeans.lib.awtextra.AbsoluteConstraints(939, 53, -1, 0));

        eco26.setBackground(new java.awt.Color(0, 102, 153));
        eco26.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        eco26.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel206.setForeground(new java.awt.Color(255, 255, 255));
        jLabel206.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/pdf.png"))); // NOI18N
        jLabel206.setText("save as pdf");
        eco26.add(jLabel206, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 140, 40));

        jPanel40.add(eco26, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 150, 140, 40));

        eco27.setBackground(new java.awt.Color(0, 153, 102));
        eco27.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        eco27.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel207.setForeground(new java.awt.Color(255, 255, 255));
        jLabel207.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/excel.png"))); // NOI18N
        jLabel207.setText("save as excel");
        jLabel207.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel207MouseClicked(evt);
            }
        });
        eco27.add(jLabel207, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 140, 40));

        jPanel40.add(eco27, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 140, 40));

        eco28.setBackground(new java.awt.Color(255, 204, 0));
        eco28.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        eco28.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel208.setBackground(new java.awt.Color(255, 255, 0));
        jLabel208.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/print.png"))); // NOI18N
        jLabel208.setText("print file");
        jLabel208.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel208MouseClicked(evt);
            }
        });
        eco28.add(jLabel208, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 140, 40));

        jPanel40.add(eco28, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 150, 140, 40));

        eco29.setBackground(new java.awt.Color(255, 255, 204));
        eco29.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        eco29.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel209.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-transaction-32.png"))); // NOI18N
        jLabel209.setText("fetch data");
        jLabel209.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel209MouseClicked(evt);
            }
        });
        eco29.add(jLabel209, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 140, 40));

        jPanel40.add(eco29, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 150, 140, 40));

        eco30.setBackground(new java.awt.Color(255, 102, 51));
        eco30.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        eco30.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel210.setBackground(new java.awt.Color(255, 255, 0));
        jLabel210.setForeground(new java.awt.Color(255, 255, 255));
        jLabel210.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/print.png"))); // NOI18N
        jLabel210.setText("more ...");
        jLabel210.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel210MouseClicked(evt);
            }
        });
        eco30.add(jLabel210, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 140, 40));

        jPanel40.add(eco30, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 150, 140, 40));

        jTabbedPane9.addTab("Daily Purchases", new javax.swing.ImageIcon(getClass().getResource("/img/ym.png")), jPanel40); // NOI18N

        jPanel42.setBackground(new java.awt.Color(255, 255, 255));
        jPanel42.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        zh_table.setAutoCreateRowSorter(true);
        zh_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Currency In", "Currency Out", "Amount Traded In", "Exchange Rate", "Amount Traded Out", "Region", "Branch", "Cashier", "Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        zh_table.setRowHeight(20);
        zh_table.setShowHorizontalLines(false);
        zh_table.setShowVerticalLines(false);
        zh_table.getTableHeader().setReorderingAllowed(false);
        jScrollPane40.setViewportView(zh_table);

        jPanel42.add(jScrollPane40, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 363, 1070, 120));

        jLabel167.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/excel.png"))); // NOI18N
        jLabel167.setText("Save as CSV File");
        jLabel167.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel167MouseClicked(evt);
            }
        });
        jPanel42.add(jLabel167, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 92, -1, 40));

        jLabel168.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/print.png"))); // NOI18N
        jLabel168.setText("Print / Save as XPS");
        jLabel168.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel168MouseClicked(evt);
            }
        });
        jPanel42.add(jLabel168, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 92, 160, 40));

        jLabel169.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/phist.png"))); // NOI18N
        jLabel169.setText("Specify Currency");
        jPanel42.add(jLabel169, new org.netbeans.lib.awtextra.AbsoluteConstraints(325, 92, -1, 40));

        zh1.setBackground(new java.awt.Color(255, 255, 255));
        zh1.setText(" L. C Received ?");
        zh1.setFocusable(false);
        zh1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zh1ActionPerformed(evt);
            }
        });
        jPanel42.add(zh1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 6, 110, 30));

        zh2.setBackground(new java.awt.Color(255, 255, 255));
        zh2.setText("Cross Currency");
        zh2.setFocusable(false);
        zh2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zh2ActionPerformed(evt);
            }
        });
        jPanel42.add(zh2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 52, -1, 30));

        jLabel170.setText("Select month");
        jPanel42.add(jLabel170, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 6, 70, 30));

        jLabel171.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/dbv.png"))); // NOI18N
        jLabel171.setText("Fetch Data");
        jLabel171.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel171MouseClicked(evt);
            }
        });
        jPanel42.add(jLabel171, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 92, 111, 40));

        zh3.setBackground(new java.awt.Color(255, 255, 255));
        zh3.setText("Regional Sales");
        zh3.setFocusable(false);
        zh3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zh3ActionPerformed(evt);
            }
        });
        jPanel42.add(zh3, new org.netbeans.lib.awtextra.AbsoluteConstraints(137, 6, -1, 30));

        zh4.setBackground(new java.awt.Color(255, 255, 255));
        zh4.setText("Branch Sales");
        zh4.setFocusable(false);
        zh4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zh4ActionPerformed(evt);
            }
        });
        jPanel42.add(zh4, new org.netbeans.lib.awtextra.AbsoluteConstraints(137, 52, 95, 30));

        jLabel172.setText("Select Region");
        jPanel42.add(jLabel172, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 6, -1, 30));

        zh5.setEditable(true);
        zh5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "Northern region", "Southern region", "Eastern region", "Western region" }));
        zh5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zh5ActionPerformed(evt);
            }
        });
        jPanel42.add(zh5, new org.netbeans.lib.awtextra.AbsoluteConstraints(325, 6, 155, 30));

        jLabel173.setText("Select Branch");
        jPanel42.add(jLabel173, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 52, -1, 30));

        zh6.setEditable(true);
        zh6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
        jPanel42.add(zh6, new org.netbeans.lib.awtextra.AbsoluteConstraints(325, 52, 155, 30));

        zh7.setModel(new javax.swing.SpinnerListModel(new String[] {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"}));
        jPanel42.add(zh7, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 6, 138, 30));

        zh8.setText("jLabel134");
        jPanel42.add(zh8, new org.netbeans.lib.awtextra.AbsoluteConstraints(638, 4, -1, 0));

        pym.setModel(new javax.swing.SpinnerListModel(new String[] {"2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030", "2031", "2032", "2033", "2034", "2035", "2036", "2037", "2040", "2050", "2051", "2052", "2053", "2054", "2055", "2056", "2057", "2058", "2059", "2060", "2061", "2062", "2063", "2064", "2065", "2066", "2067", "2068", "2069", "2070", "2071", "2072", "2073", "2073", "2074", "2075", "2076", "2077", "2078", "2079", "2080", "2081", "2082", "2083", "2084", "2085", "2086", "2087", "2088", "2089", "2090", "2091", "2092", "2093", "2094", "2095", "2096", "2097", "2098", "2099"}));
        jPanel42.add(pym, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 52, 138, 30));

        jLabel174.setText("Select year");
        jPanel42.add(jLabel174, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 52, 70, 30));

        eco31.setBackground(new java.awt.Color(0, 102, 153));
        eco31.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        eco31.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel211.setForeground(new java.awt.Color(255, 255, 255));
        jLabel211.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/pdf.png"))); // NOI18N
        jLabel211.setText("save as pdf");
        eco31.add(jLabel211, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 140, 40));

        jPanel42.add(eco31, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 150, 140, 40));

        eco32.setBackground(new java.awt.Color(0, 153, 102));
        eco32.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        eco32.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel212.setForeground(new java.awt.Color(255, 255, 255));
        jLabel212.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/excel.png"))); // NOI18N
        jLabel212.setText("save as excel");
        jLabel212.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel212MouseClicked(evt);
            }
        });
        eco32.add(jLabel212, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 140, 40));

        jPanel42.add(eco32, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 140, 40));

        eco33.setBackground(new java.awt.Color(255, 204, 0));
        eco33.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        eco33.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel213.setBackground(new java.awt.Color(255, 255, 0));
        jLabel213.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/print.png"))); // NOI18N
        jLabel213.setText("print file");
        jLabel213.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel213MouseClicked(evt);
            }
        });
        eco33.add(jLabel213, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 140, 40));

        jPanel42.add(eco33, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 150, 140, 40));

        eco34.setBackground(new java.awt.Color(255, 255, 204));
        eco34.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        eco34.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel214.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-transaction-32.png"))); // NOI18N
        jLabel214.setText("fetch data");
        jLabel214.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel214MouseClicked(evt);
            }
        });
        eco34.add(jLabel214, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 140, 40));

        jPanel42.add(eco34, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 150, 140, 40));

        eco35.setBackground(new java.awt.Color(255, 102, 51));
        eco35.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        eco35.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel215.setBackground(new java.awt.Color(255, 255, 0));
        jLabel215.setForeground(new java.awt.Color(255, 255, 255));
        jLabel215.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/print.png"))); // NOI18N
        jLabel215.setText("more ...");
        jLabel215.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel215MouseClicked(evt);
            }
        });
        eco35.add(jLabel215, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 140, 40));

        jPanel42.add(eco35, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 150, 140, 40));

        jTabbedPane9.addTab("Monthly Purchases", new javax.swing.ImageIcon(getClass().getResource("/img/td.png")), jPanel42); // NOI18N

        jPanel53.setBackground(new java.awt.Color(255, 255, 255));
        jPanel53.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        px_table.setAutoCreateRowSorter(true);
        px_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Currency In", "Currency Out", "Amount Traded In", "Exchange Rate", "Amount Traded Out", "Region", "Branch", "Cashier", "Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        px_table.setRowHeight(20);
        px_table.setShowHorizontalLines(false);
        px_table.setShowVerticalLines(false);
        px_table.getTableHeader().setReorderingAllowed(false);
        jScrollPane41.setViewportView(px_table);

        jPanel53.add(jScrollPane41, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 313, 1070, 170));

        jLabel176.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/excel.png"))); // NOI18N
        jLabel176.setText("Save as CSV File");
        jLabel176.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel176MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel176MouseEntered(evt);
            }
        });
        jPanel53.add(jLabel176, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 92, -1, 40));

        jLabel177.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/print.png"))); // NOI18N
        jLabel177.setText("Print / Save as XPS");
        jLabel177.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel177MouseClicked(evt);
            }
        });
        jPanel53.add(jLabel177, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 92, -1, 40));

        jLabel178.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/phist.png"))); // NOI18N
        jLabel178.setText("Specify Currency");
        jPanel53.add(jLabel178, new org.netbeans.lib.awtextra.AbsoluteConstraints(325, 92, -1, 40));

        px1.setBackground(new java.awt.Color(255, 255, 255));
        px1.setText(" L. C Received ?");
        px1.setFocusable(false);
        px1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                px1ActionPerformed(evt);
            }
        });
        jPanel53.add(px1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 6, 110, 30));

        px2.setBackground(new java.awt.Color(255, 255, 255));
        px2.setText("Cross Currency");
        px2.setFocusable(false);
        px2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                px2ActionPerformed(evt);
            }
        });
        jPanel53.add(px2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 52, -1, 30));

        jLabel179.setText("Start date");
        jPanel53.add(jLabel179, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 6, 70, 30));

        jLabel180.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/dbv.png"))); // NOI18N
        jLabel180.setText("Fetch Data");
        jLabel180.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel180MouseClicked(evt);
            }
        });
        jPanel53.add(jLabel180, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 92, -1, 40));

        px3.setBackground(new java.awt.Color(255, 255, 255));
        px3.setText("Regional Sales");
        px3.setFocusable(false);
        px3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                px3ActionPerformed(evt);
            }
        });
        jPanel53.add(px3, new org.netbeans.lib.awtextra.AbsoluteConstraints(137, 6, -1, 30));

        px4.setBackground(new java.awt.Color(255, 255, 255));
        px4.setText("Branch Sales");
        px4.setFocusable(false);
        px4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                px4ActionPerformed(evt);
            }
        });
        jPanel53.add(px4, new org.netbeans.lib.awtextra.AbsoluteConstraints(137, 52, 95, 30));

        jLabel181.setText("Select Region");
        jPanel53.add(jLabel181, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 6, -1, 30));

        px5.setEditable(true);
        px5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "Northern region", "Southern region", "Eastern region", "Western region" }));
        px5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                px5ActionPerformed(evt);
            }
        });
        jPanel53.add(px5, new org.netbeans.lib.awtextra.AbsoluteConstraints(325, 6, 155, 30));

        jLabel182.setText("Select Branch");
        jPanel53.add(jLabel182, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 52, -1, 30));

        px6.setEditable(true);
        px6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
        jPanel53.add(px6, new org.netbeans.lib.awtextra.AbsoluteConstraints(325, 52, 155, 30));

        px7.setModel(new javax.swing.SpinnerListModel(new String[] {"2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030", "2031", "2032", "2033", "2034", "2035", "2036", "2037", "2040", "2050", "2051", "2052", "2053", "2054", "2055", "2056", "2057", "2058", "2059", "2060", "2061", "2062", "2063", "2064", "2065", "2066", "2067", "2068", "2069", "2070", "2071", "2072", "2073", "2073", "2074", "2075", "2076", "2077", "2078", "2079", "2080", "2081", "2082", "2083", "2084", "2085", "2086", "2087", "2088", "2089", "2090", "2091", "2092", "2093", "2094", "2095", "2096", "2097", "2098", "2099"}));
        jPanel53.add(px7, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 6, 138, 30));

        eco36.setBackground(new java.awt.Color(0, 102, 153));
        eco36.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        eco36.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel216.setForeground(new java.awt.Color(255, 255, 255));
        jLabel216.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/pdf.png"))); // NOI18N
        jLabel216.setText("save as pdf");
        eco36.add(jLabel216, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 140, 40));

        jPanel53.add(eco36, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 150, 140, 40));

        eco37.setBackground(new java.awt.Color(0, 153, 102));
        eco37.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        eco37.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel217.setForeground(new java.awt.Color(255, 255, 255));
        jLabel217.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/excel.png"))); // NOI18N
        jLabel217.setText("save as excel");
        jLabel217.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel217MouseClicked(evt);
            }
        });
        eco37.add(jLabel217, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 140, 40));

        jPanel53.add(eco37, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 140, 40));

        eco38.setBackground(new java.awt.Color(255, 204, 0));
        eco38.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        eco38.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel218.setBackground(new java.awt.Color(255, 255, 0));
        jLabel218.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/print.png"))); // NOI18N
        jLabel218.setText("print file");
        jLabel218.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel218MouseClicked(evt);
            }
        });
        eco38.add(jLabel218, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 140, 40));

        jPanel53.add(eco38, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 150, 140, 40));

        eco39.setBackground(new java.awt.Color(255, 255, 204));
        eco39.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        eco39.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel219.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-transaction-32.png"))); // NOI18N
        jLabel219.setText("fetch data");
        jLabel219.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel219MouseClicked(evt);
            }
        });
        eco39.add(jLabel219, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 140, 40));

        jPanel53.add(eco39, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 150, 140, 40));

        eco40.setBackground(new java.awt.Color(255, 102, 51));
        eco40.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        eco40.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel220.setBackground(new java.awt.Color(255, 255, 0));
        jLabel220.setForeground(new java.awt.Color(255, 255, 255));
        jLabel220.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/print.png"))); // NOI18N
        jLabel220.setText("more ...");
        jLabel220.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel220MouseClicked(evt);
            }
        });
        eco40.add(jLabel220, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 140, 40));

        jPanel53.add(eco40, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 150, 140, 40));

        jTabbedPane9.addTab("Annual Purchases", new javax.swing.ImageIcon(getClass().getResource("/img/mm.png")), jPanel53); // NOI18N

        purchases_rep.add(jTabbedPane9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1090, 540));

        jTabbedPane7.addTab("Purchases Reports", new javax.swing.ImageIcon(getClass().getResource("/img/up32.png")), purchases_rep); // NOI18N

        profit_and_loses.setBackground(new java.awt.Color(255, 255, 255));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel97.setText("Select Trading Month");

        prf1.setEditable(true);
        prf1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" }));

        prf2.setEditable(true);
        prf2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prf2ActionPerformed(evt);
            }
        });

        jLabel118.setText("Currency Received");

        jLabel119.setText("Currency Sold");

        prf5.setEditable(true);
        prf5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prf5ActionPerformed(evt);
            }
        });

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cost Evaluation Methods", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        prf6.setBackground(new java.awt.Color(255, 255, 255));
        prf6.setText("Last In First Out");
        prf6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prf6ActionPerformed(evt);
            }
        });

        prf7.setBackground(new java.awt.Color(255, 255, 255));
        prf7.setText("Average Cost");
        prf7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prf7ActionPerformed(evt);
            }
        });

        prf8.setBackground(new java.awt.Color(255, 255, 255));
        prf8.setText("First In First Out");
        prf8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prf8ActionPerformed(evt);
            }
        });

        jLabel120.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel120.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-calculator-32.png"))); // NOI18N
        jLabel120.setText("Calculate Profit / Losses");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel120, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(prf6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                        .addComponent(prf7, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(prf8, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(prf6)
                    .addComponent(prf7)
                    .addComponent(prf8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel120)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel122.setText("Total Sales");

        jLabel123.setText("Total Purchases");

        jLabel124.setText("At");

        jLabel125.setText("(%)");

        jLabel126.setText("At");

        jLabel127.setText("(%)");

        prf13.setBackground(new java.awt.Color(255, 255, 255));
        prf13.setForeground(new java.awt.Color(153, 0, 51));
        prf13.setText("Profit");

        prf14.setText("Loses)");

        pfr1.setModel(new javax.swing.SpinnerListModel(new String[] {"2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030", "2031", "2032", "2033", "2034", "2035", "2036", "2037", "2040", "2050", "2051", "2052", "2053", "2054", "2055", "2056", "2057", "2058", "2059", "2060", "2061", "2062", "2063", "2064", "2065", "2066", "2067", "2068", "2069", "2070", "2071", "2072", "2073", "2073", "2074", "2075", "2076", "2077", "2078", "2079", "2080", "2081", "2082", "2083", "2084", "2085", "2086", "2087", "2088", "2089", "2090", "2091", "2092", "2093", "2094", "2095", "2096", "2097", "2098", "2099"}));

        jLabel183.setText("Select trading year");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(jLabel97)
                                .addGap(18, 18, 18)
                                .addComponent(prf1, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel118, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(0, 63, Short.MAX_VALUE)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(jPanel8Layout.createSequentialGroup()
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel119, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(21, 21, 21)
                                    .addComponent(prf5, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel8Layout.createSequentialGroup()
                                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(prf15, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(prf13))
                                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(jPanel8Layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addComponent(prf16))
                                            .addGroup(jPanel8Layout.createSequentialGroup()
                                                .addGap(106, 106, 106)
                                                .addComponent(prf14, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(jPanel8Layout.createSequentialGroup()
                                        .addComponent(jLabel124)
                                        .addGap(18, 18, 18)
                                        .addComponent(prf10, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel125)
                                        .addGap(12, 12, 12))
                                    .addGroup(jPanel8Layout.createSequentialGroup()
                                        .addComponent(jLabel126)
                                        .addGap(18, 18, 18)
                                        .addComponent(prf12, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel127)
                                        .addGap(13, 13, 13))
                                    .addGroup(jPanel8Layout.createSequentialGroup()
                                        .addComponent(jLabel122, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(34, 34, 34)
                                        .addComponent(prf9, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel8Layout.createSequentialGroup()
                                        .addComponent(jLabel123, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(34, 34, 34)
                                        .addComponent(prf11, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel183)
                                .addGap(18, 18, 18)
                                .addComponent(pfr1, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(prf2, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(79, 79, 79))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pfr1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel183))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel97)
                    .addComponent(prf1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel118)
                    .addComponent(prf2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel119)
                    .addComponent(prf5, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(prf9, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel122))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(prf10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel124)
                    .addComponent(jLabel125))
                .addGap(8, 8, 8)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(prf11, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel123))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(prf12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel126)
                    .addComponent(jLabel127))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(prf13)
                    .addComponent(prf14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(prf15, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(prf16, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(40, Short.MAX_VALUE))
        );

        prof_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Total", "Rates"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane15.setViewportView(prof_table);
        if (prof_table.getColumnModel().getColumnCount() > 0) {
            prof_table.getColumnModel().getColumn(0).setResizable(false);
            prof_table.getColumnModel().getColumn(1).setResizable(false);
        }

        prof_table1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Total", "Rates"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane16.setViewportView(prof_table1);
        if (prof_table1.getColumnModel().getColumnCount() > 0) {
            prof_table1.getColumnModel().getColumn(0).setResizable(false);
            prof_table1.getColumnModel().getColumn(1).setResizable(false);
        }

        javax.swing.GroupLayout profit_and_losesLayout = new javax.swing.GroupLayout(profit_and_loses);
        profit_and_loses.setLayout(profit_and_losesLayout);
        profit_and_losesLayout.setHorizontalGroup(
            profit_and_losesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(profit_and_losesLayout.createSequentialGroup()
                .addContainerGap(192, Short.MAX_VALUE)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane16, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(279, 279, 279)
                .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        profit_and_losesLayout.setVerticalGroup(
            profit_and_losesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(profit_and_losesLayout.createSequentialGroup()
                .addGroup(profit_and_losesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(profit_and_losesLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(profit_and_losesLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane16, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jTabbedPane7.addTab("Profit & Losses", new javax.swing.ImageIcon(getClass().getResource("/img/vp.png")), profit_and_loses); // NOI18N

        profit_and_loses2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "What Regions or Branches Holds", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        jLabel129.setText("Select currency");

        acc_currency.setEditable(true);
        acc_currency.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
        acc_currency.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                acc_currencyActionPerformed(evt);
            }
        });

        acc_table.setAutoCreateRowSorter(true);
        acc_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Denominations", "No. denominations", "Total"
            }
        ));
        acc_table.setRowHeight(20);
        acc_table.setShowHorizontalLines(false);
        acc_table.setShowVerticalLines(false);
        jScrollPane10.setViewportView(acc_table);
        if (acc_table.getColumnModel().getColumnCount() > 0) {
            acc_table.getColumnModel().getColumn(0).setResizable(false);
            acc_table.getColumnModel().getColumn(1).setResizable(false);
            acc_table.getColumnModel().getColumn(2).setResizable(false);
        }

        jLabel136.setText("Grand Total for the selected currency");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jLabel129)
                        .addGap(53, 53, 53)
                        .addComponent(acc_currency, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel136)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(acc_total, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(acc_currency, javax.swing.GroupLayout.PREFERRED_SIZE, 25, Short.MAX_VALUE)
                    .addComponent(jLabel129))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(acc_total, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                    .addComponent(jLabel136))
                .addGap(39, 39, 39))
        );

        jPanel11.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(224, 84, 480, 290));

        opb1.setBackground(new java.awt.Color(255, 255, 255));
        opb1.setText("Regions");
        jPanel11.add(opb1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 10, 170, -1));

        opb2.setBackground(new java.awt.Color(255, 255, 255));
        opb2.setText("Branches");
        jPanel11.add(opb2, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 10, 160, -1));

        opb4.setEditable(true);
        jPanel11.add(opb4, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 40, 170, 30));

        opb3.setEditable(true);
        opb3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "Northern region", "Southern region", "Eastern region", "Western region" }));
        opb3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opb3ActionPerformed(evt);
            }
        });
        jPanel11.add(opb3, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 40, 170, 30));

        javax.swing.GroupLayout profit_and_loses2Layout = new javax.swing.GroupLayout(profit_and_loses2);
        profit_and_loses2.setLayout(profit_and_loses2Layout);
        profit_and_loses2Layout.setHorizontalGroup(
            profit_and_loses2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1093, Short.MAX_VALUE)
            .addGroup(profit_and_loses2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(profit_and_loses2Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 995, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        profit_and_loses2Layout.setVerticalGroup(
            profit_and_loses2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 655, Short.MAX_VALUE)
            .addGroup(profit_and_loses2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(profit_and_loses2Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 534, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jTabbedPane7.addTab("Branch Accounts", new javax.swing.ImageIcon(getClass().getResource("/img/icons8-bank-building-32.png")), profit_and_loses2); // NOI18N

        jPanel28.setBackground(new java.awt.Color(255, 255, 255));
        jPanel28.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel34.setBackground(new java.awt.Color(255, 255, 255));
        jPanel34.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Transferes between Counter Clerk & Strong Room Operator", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        jPanel34.setPreferredSize(new java.awt.Dimension(1000, 580));
        jPanel34.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel194.setText("Select currency");
        jPanel34.add(jLabel194, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 24, -1, 20));

        cp3.setEditable(true);
        cp3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "All" }));
        cp3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cp3ActionPerformed(evt);
            }
        });
        jPanel34.add(cp3, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 20, 166, 25));

        cp_table.setAutoCreateRowSorter(true);
        cp_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Currency", "Denominations", "No. denominations", "Total", "Sender", "Position", "Receiver A/C", "Date & Time"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        cp_table.setRowHeight(20);
        cp_table.setShowHorizontalLines(false);
        cp_table.setShowVerticalLines(false);
        cp_table.getTableHeader().setReorderingAllowed(false);
        jScrollPane17.setViewportView(cp_table);
        if (cp_table.getColumnModel().getColumnCount() > 0) {
            cp_table.getColumnModel().getColumn(0).setResizable(false);
            cp_table.getColumnModel().getColumn(1).setResizable(false);
            cp_table.getColumnModel().getColumn(2).setResizable(false);
            cp_table.getColumnModel().getColumn(3).setResizable(false);
            cp_table.getColumnModel().getColumn(4).setResizable(false);
            cp_table.getColumnModel().getColumn(5).setResizable(false);
            cp_table.getColumnModel().getColumn(6).setResizable(false);
            cp_table.getColumnModel().getColumn(7).setResizable(false);
        }

        jPanel34.add(jScrollPane17, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 960, 350));

        jLabel195.setText("Grand Total for the selected currency");
        jPanel34.add(jLabel195, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 410, -1, 20));
        jPanel34.add(cp4, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 410, 130, 27));

        jPanel28.add(jPanel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 990, 580));

        cp2.setEditable(true);
        jPanel28.add(cp2, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 40, 170, 30));

        cp1.setEditable(true);
        cp1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "", "Northern region", "Southern region", "Eastern region", "Western region" }));
        cp1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cp1ActionPerformed(evt);
            }
        });
        jPanel28.add(cp1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 40, 170, 30));

        jTabbedPane7.addTab("Clerk <-> Operator", new javax.swing.ImageIcon(getClass().getResource("/img/icons8-insert-white-space-32.png")), jPanel28); // NOI18N

        jPanel43.setBackground(new java.awt.Color(255, 255, 255));
        jPanel43.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel45.setBackground(new java.awt.Color(255, 255, 255));
        jPanel45.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Transferes between Counter Clerk & Strong Room Operator", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        jPanel45.setPreferredSize(new java.awt.Dimension(1000, 580));
        jPanel45.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel196.setText("Select currency");
        jPanel45.add(jLabel196, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 24, -1, 20));

        cg3.setEditable(true);
        cg3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "All" }));
        cg3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cg3ActionPerformed(evt);
            }
        });
        jPanel45.add(cg3, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 20, 166, 25));

        cg_table.setAutoCreateRowSorter(true);
        cg_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Currency", "Denominations", "No. denominations", "Total", "Sender", "Position", "Receiver A/C", "Date & Time"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        cg_table.setRowHeight(20);
        cg_table.setShowHorizontalLines(false);
        cg_table.setShowVerticalLines(false);
        cg_table.getTableHeader().setReorderingAllowed(false);
        jScrollPane18.setViewportView(cg_table);

        jPanel45.add(jScrollPane18, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 960, 350));

        jLabel197.setText("Grand Total for the selected currency");
        jPanel45.add(jLabel197, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 410, -1, 20));
        jPanel45.add(cg4, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 410, 130, 27));

        jPanel43.add(jPanel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 990, 580));

        cg2.setEditable(true);
        jPanel43.add(cg2, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 40, 170, 30));

        cg1.setEditable(true);
        cg1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "", "Northern region", "Southern region", "Eastern region", "Western region" }));
        cg1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cg1ActionPerformed(evt);
            }
        });
        jPanel43.add(cg1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 40, 170, 30));

        jTabbedPane7.addTab("Clerk <-> Clerk", new javax.swing.ImageIcon(getClass().getResource("/img/icons8-insert-white-space-32.png")), jPanel43); // NOI18N

        profit_and_loses4.setBackground(new java.awt.Color(255, 255, 255));

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));
        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "What Regions or Branches Holds", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        jPanel15.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel157.setText("Select currency");
        jPanel15.add(jLabel157, new org.netbeans.lib.awtextra.AbsoluteConstraints(72, 32, -1, -1));

        tw5.setEditable(true);
        tw5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
        tw5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tw5ActionPerformed(evt);
            }
        });
        jPanel15.add(tw5, new org.netbeans.lib.awtextra.AbsoluteConstraints(199, 27, 166, 32));

        tw_table.setAutoCreateRowSorter(true);
        tw_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Denominations", "No. denominations", "Total"
            }
        ));
        tw_table.setRowHeight(20);
        tw_table.setShowHorizontalLines(false);
        tw_table.setShowVerticalLines(false);
        jScrollPane22.setViewportView(tw_table);

        jPanel15.add(jScrollPane22, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 72, 448, 150));

        jLabel165.setText("Grand Total for the selected currency");
        jPanel15.add(jLabel165, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 246, -1, 32));
        jPanel15.add(tw6, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 246, 145, 32));

        jPanel13.add(jPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(224, 84, 480, 290));

        tw4.setEditable(true);
        jPanel13.add(tw4, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 40, 170, 32));

        tw3.setEditable(true);
        tw3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "Northern region", "Southern region", "Eastern region", "Western region" }));
        tw3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tw3ActionPerformed(evt);
            }
        });
        jPanel13.add(tw3, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 40, 170, 32));

        javax.swing.GroupLayout profit_and_loses4Layout = new javax.swing.GroupLayout(profit_and_loses4);
        profit_and_loses4.setLayout(profit_and_loses4Layout);
        profit_and_loses4Layout.setHorizontalGroup(
            profit_and_loses4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1093, Short.MAX_VALUE)
            .addGroup(profit_and_loses4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(profit_and_loses4Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, 995, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        profit_and_loses4Layout.setVerticalGroup(
            profit_and_loses4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 655, Short.MAX_VALUE)
            .addGroup(profit_and_loses4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(profit_and_loses4Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, 534, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jTabbedPane7.addTab("Initial Branch A/C Deposits", new javax.swing.ImageIcon(getClass().getResource("/img/icons8-bank-building-32.png")), profit_and_loses4); // NOI18N

        reports.add(jTabbedPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1100, 580));

        jPanel1.add(reports, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 140, 1100, 580));

        simult.setBackground(new java.awt.Color(255, 255, 255));
        simult.setBorder(javax.swing.BorderFactory.createTitledBorder("Please fill in the fields below"));

        dv1.setEditable(false);

        jLabel56.setText("Base currency");

        dv2.setEditable(false);

        jLabel57.setText("Quote currency");

        dv3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                dv3KeyTyped(evt);
            }
        });

        jLabel58.setText("Buying rate");

        dv4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                dv4KeyTyped(evt);
            }
        });

        jLabel59.setText("Selling rate");

        jLabel60.setText("Base currency");

        jLabel62.setText("Quote currency");

        sv2.setEditable(false);

        sv1.setEditable(false);

        jLabel63.setText("Buying rate");

        sv3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                sv3KeyTyped(evt);
            }
        });

        jLabel68.setText("Selling rate");

        sv4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sv4ActionPerformed(evt);
            }
        });
        sv4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                sv4KeyTyped(evt);
            }
        });

        jLabel69.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-replace-32.png"))); // NOI18N
        jLabel69.setText("Simultenous Rates Update");
        jLabel69.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel69MouseClicked(evt);
            }
        });

        jLabel90.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-return-32.png"))); // NOI18N
        jLabel90.setText("Return ");
        jLabel90.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel90MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout simultLayout = new javax.swing.GroupLayout(simult);
        simult.setLayout(simultLayout);
        simultLayout.setHorizontalGroup(
            simultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(simultLayout.createSequentialGroup()
                .addGroup(simultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, simultLayout.createSequentialGroup()
                        .addGroup(simultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel56, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel57, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel58, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel59, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addGroup(simultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dv4, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dv3, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dv2, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dv1, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(simultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel60, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel62, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel63, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel68, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addGroup(simultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(sv4, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sv3, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sv2, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sv1, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, simultLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel90, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel69, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        simultLayout.setVerticalGroup(
            simultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(simultLayout.createSequentialGroup()
                .addGroup(simultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(simultLayout.createSequentialGroup()
                        .addGroup(simultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(dv1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel56))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(simultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(dv2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel57))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(simultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(dv3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel58))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(simultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(dv4, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel59)))
                    .addGroup(simultLayout.createSequentialGroup()
                        .addGroup(simultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(sv1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel60))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(simultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(sv2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel62))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(simultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(sv3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel63))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(simultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(sv4, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel68))))
                .addGap(33, 33, 33)
                .addGroup(simultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel69, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel90, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 66, Short.MAX_VALUE))
        );

        jPanel1.add(simult, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 200, 550, 290));

        jTabbedPane3.setBackground(new java.awt.Color(255, 255, 255));

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));
        jPanel17.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTabbedPane4.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane4.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);

        jPanel24.setBackground(new java.awt.Color(255, 255, 255));

        jLabel184.setText("Base currency :");

        rt1.setEditable(false);
        rt1.setBackground(new java.awt.Color(255, 255, 255));

        rt2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));

        jLabel185.setText("Quote currency");

        jLabel186.setText("Buying rate");

        rt5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                rt5KeyPressed(evt);
            }
        });

        rt_table.setAutoCreateRowSorter(true);
        rt_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Base currency", "Quote currency", "Buying", "Selling", "Exchange rate"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        rt_table.setRowHeight(20);
        rt_table.setShowHorizontalLines(false);
        rt_table.setShowVerticalLines(false);
        rt_table.getTableHeader().setReorderingAllowed(false);
        jScrollPane42.setViewportView(rt_table);

        zp.setText("jLabel8");

        xp.setText("jLabel8");

        rt6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                rt6KeyPressed(evt);
            }
        });

        jLabel64.setText("Selling rate");

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addGap(92, 92, 92)
                        .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(xp)
                            .addGroup(jPanel24Layout.createSequentialGroup()
                                .addGap(190, 190, 190)
                                .addComponent(zp))))
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addComponent(jLabel184)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rt1, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel185)
                            .addComponent(jLabel186, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel64, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(rt6)
                            .addComponent(rt5)
                            .addComponent(rt2, 0, 172, Short.MAX_VALUE))))
                .addGap(48, 48, 48))
            .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel24Layout.createSequentialGroup()
                    .addContainerGap(267, Short.MAX_VALUE)
                    .addComponent(jScrollPane42, javax.swing.GroupLayout.PREFERRED_SIZE, 718, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel184)
                    .addComponent(rt1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rt2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel185))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rt5, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel186))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rt6, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel64))
                .addGap(51, 51, 51)
                .addComponent(zp, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(xp, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(261, Short.MAX_VALUE))
            .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel24Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane42, javax.swing.GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        jTabbedPane4.addTab("Local Currency", new javax.swing.ImageIcon(getClass().getResource("/img/opr.png")), jPanel24); // NOI18N

        jPanel25.setBackground(new java.awt.Color(255, 255, 255));

        jLabel190.setText("Base currency :");

        ccr2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
        ccr2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ccr2ActionPerformed(evt);
            }
        });

        jLabel65.setText("Quote currency");

        jLabel66.setText("Buying rate");

        ccr3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ccr3KeyPressed(evt);
            }
        });

        rt_table1.setAutoCreateRowSorter(true);
        rt_table1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Base currency", "Quote currency", "Buying rate", "Selling rate"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        rt_table1.setRowHeight(20);
        rt_table1.setShowHorizontalLines(false);
        rt_table1.setShowVerticalLines(false);
        rt_table1.getTableHeader().setReorderingAllowed(false);
        jScrollPane32.setViewportView(rt_table1);

        zp1.setText("jLabel8");

        xp1.setText("jLabel8");

        ccr4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ccr4KeyPressed(evt);
            }
        });

        jLabel67.setText("Selling rate");

        ccr1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
        ccr1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ccr1ActionPerformed(evt);
            }
        });

        jLabel191.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-installing-updates-32.png"))); // NOI18N
        jLabel191.setText("Save Exchange Rates");
        jLabel191.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel191MouseClicked(evt);
            }
        });

        jpy1.setText("jLabel4");

        jpb1.setText("jLabel8");

        opt2.setText("jLabel8");

        opt3.setText("jLabel8");

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel25Layout.createSequentialGroup()
                        .addGap(92, 92, 92)
                        .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(xp1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel25Layout.createSequentialGroup()
                                .addGap(190, 190, 190)
                                .addComponent(zp1))
                            .addComponent(jpb1)
                            .addComponent(jpy1)
                            .addGroup(jPanel25Layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(opt3)
                                    .addComponent(opt2)))))
                    .addGroup(jPanel25Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel25Layout.createSequentialGroup()
                                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel190)
                                    .addComponent(jLabel65))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(ccr2, 0, 183, Short.MAX_VALUE)
                                    .addComponent(ccr1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel25Layout.createSequentialGroup()
                                .addComponent(jLabel66, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(ccr3, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel25Layout.createSequentialGroup()
                                .addComponent(jLabel67, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel191, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ccr4, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(679, Short.MAX_VALUE))
            .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel25Layout.createSequentialGroup()
                    .addGap(303, 303, 303)
                    .addComponent(jScrollPane32, javax.swing.GroupLayout.DEFAULT_SIZE, 682, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel190)
                    .addComponent(ccr1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ccr2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel65))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel66)
                    .addComponent(ccr3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ccr4, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel67))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel191)
                .addGap(22, 22, 22)
                .addComponent(zp1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addComponent(jpy1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jpb1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addComponent(opt2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(opt3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(xp1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(73, Short.MAX_VALUE))
            .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel25Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane32, javax.swing.GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        jTabbedPane4.addTab("Cross Currency Exchange Rates", new javax.swing.ImageIcon(getClass().getResource("/img/icons8-installing-updates-32.png")), jPanel25); // NOI18N

        jPanel17.add(jTabbedPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 540));

        jTabbedPane3.addTab("Configure Exchange Rates", new javax.swing.ImageIcon(getClass().getResource("/img/opr.png")), jPanel17); // NOI18N

        jPanel19.setBackground(new java.awt.Color(255, 255, 255));

        jPanel26.setBackground(new java.awt.Color(255, 255, 255));
        jPanel26.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        rtu.setAutoCreateRowSorter(true);
        rtu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Base currency", "Quote currency", "Buying rate", "Selling rate", "Exchange rate id"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        rtu.setRowHeight(20);
        rtu.setShowHorizontalLines(false);
        rtu.setShowVerticalLines(false);
        rtu.getTableHeader().setReorderingAllowed(false);
        rtu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rtuMouseClicked(evt);
            }
        });
        jScrollPane19.setViewportView(rtu);

        jPanel26.add(jScrollPane19, new org.netbeans.lib.awtextra.AbsoluteConstraints(308, 11, 677, 472));

        ru5.setEditable(false);
        ru5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel26.add(ru5, new org.netbeans.lib.awtextra.AbsoluteConstraints(116, 135, 174, 29));

        jLabel192.setText("Buying rate");
        jPanel26.add(jLabel192, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 142, -1, -1));

        jLabel84.setText("Quote currency");
        jPanel26.add(jLabel84, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 65, -1, -1));

        jLabel85.setText("Base currency :");
        jPanel26.add(jLabel85, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 18, -1, -1));

        ru1.setEditable(false);
        ru1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel26.add(ru1, new org.netbeans.lib.awtextra.AbsoluteConstraints(116, 11, 174, 29));

        ru2.setEditable(false);
        ru2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel26.add(ru2, new org.netbeans.lib.awtextra.AbsoluteConstraints(116, 58, 174, 29));

        ru3.setBackground(new java.awt.Color(255, 255, 255));
        ru3.setText("Buying");
        ru3.setEnabled(false);
        ru3.setFocusable(false);
        jPanel26.add(ru3, new org.netbeans.lib.awtextra.AbsoluteConstraints(116, 94, 82, 34));

        ru4.setBackground(new java.awt.Color(255, 255, 255));
        ru4.setText("Selling");
        ru4.setEnabled(false);
        ru4.setFocusable(false);
        jPanel26.add(ru4, new org.netbeans.lib.awtextra.AbsoluteConstraints(208, 94, 82, 34));

        ru6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ru6KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                ru6KeyTyped(evt);
            }
        });
        jPanel26.add(ru6, new org.netbeans.lib.awtextra.AbsoluteConstraints(116, 170, 174, 29));

        jLabel121.setText("Selling");
        jPanel26.add(jLabel121, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 177, 96, -1));

        xch.setText("jLabel4");
        jPanel26.add(xch, new org.netbeans.lib.awtextra.AbsoluteConstraints(243, 217, -1, 0));

        nhy.setText("jLabel8");
        jPanel26.add(nhy, new org.netbeans.lib.awtextra.AbsoluteConstraints(256, 292, -1, 0));

        rid.setText("jLabel4");
        jPanel26.add(rid, new org.netbeans.lib.awtextra.AbsoluteConstraints(152, 379, -1, 0));

        jLabel193.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-calculator-32.png"))); // NOI18N
        jLabel193.setText("Automated calculator");
        jLabel193.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel193MouseClicked(evt);
            }
        });
        jPanel26.add(jLabel193, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 220, 140, 38));

        jLabel70.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-replace-32.png"))); // NOI18N
        jLabel70.setText("Update Rate");
        jLabel70.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel70MouseClicked(evt);
            }
        });
        jPanel26.add(jLabel70, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 220, 100, 40));

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1005, Short.MAX_VALUE)
            .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel19Layout.createSequentialGroup()
                    .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, 995, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 10, Short.MAX_VALUE)))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 540, Short.MAX_VALUE)
            .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel19Layout.createSequentialGroup()
                    .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, 511, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 29, Short.MAX_VALUE)))
        );

        jTabbedPane3.addTab("Update Exchange Rates", new javax.swing.ImageIcon(getClass().getResource("/img/icons8-update-32.png")), jPanel19); // NOI18N

        jPanel22.setBackground(new java.awt.Color(255, 255, 255));

        jPanel29.setBackground(new java.awt.Color(255, 255, 255));

        rtv.setAutoCreateRowSorter(true);
        rtv.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Base currency", "Other currency", "Buying rate", "Selling rate"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        rtv.setRowHeight(20);
        rtv.setShowHorizontalLines(false);
        rtv.setShowVerticalLines(false);
        rtv.getTableHeader().setReorderingAllowed(false);
        jScrollPane20.setViewportView(rtv);

        jLabel86.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/excel.png"))); // NOI18N
        jLabel86.setText("Export as excel file");

        jLabel87.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/pdf.png"))); // NOI18N
        jLabel87.setText("Export as pdf file");

        jLabel88.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/print.png"))); // NOI18N
        jLabel88.setText("Export as pdf file");

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addGap(172, 172, 172)
                .addComponent(jLabel86)
                .addGap(145, 145, 145)
                .addComponent(jLabel87, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 200, Short.MAX_VALUE)
                .addComponent(jLabel88, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(123, 123, 123))
            .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel29Layout.createSequentialGroup()
                    .addGap(170, 170, 170)
                    .addComponent(jScrollPane20, javax.swing.GroupLayout.PREFERRED_SIZE, 715, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(120, Short.MAX_VALUE)))
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel29Layout.createSequentialGroup()
                .addContainerGap(492, Short.MAX_VALUE)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel86, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel87, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel88, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel29Layout.createSequentialGroup()
                    .addGap(34, 34, 34)
                    .addComponent(jScrollPane20, javax.swing.GroupLayout.PREFERRED_SIZE, 449, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(57, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1005, Short.MAX_VALUE)
            .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel22Layout.createSequentialGroup()
                    .addComponent(jPanel29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 540, Short.MAX_VALUE)
            .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel29, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("View Exchange Rates", new javax.swing.ImageIcon(getClass().getResource("/img/rpp.png")), jPanel22); // NOI18N

        javax.swing.GroupLayout ratesLayout = new javax.swing.GroupLayout(rates);
        rates.setLayout(ratesLayout);
        ratesLayout.setHorizontalGroup(
            ratesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1010, Short.MAX_VALUE)
            .addGroup(ratesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jTabbedPane3, javax.swing.GroupLayout.Alignment.TRAILING))
        );
        ratesLayout.setVerticalGroup(
            ratesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 586, Short.MAX_VALUE)
            .addGroup(ratesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jTabbedPane3, javax.swing.GroupLayout.Alignment.TRAILING))
        );

        jPanel1.add(rates, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 140, 995, 580));

        jLabel44.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel44.setText("Currency Dynamics Shell Bureau De Change System");
        jPanel1.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 200, 1090, 110));

        jLabel130.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel130.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel130.setText("Version 3.1 Enterprise");
        jPanel1.add(jLabel130, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 300, 1090, 60));

        upass.setText("jLabel1");

        configs2.setText("jLabel1");

        uname.setText("jLabel1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1364, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 898, Short.MAX_VALUE)
                    .addComponent(uname)
                    .addGap(56, 56, 56)
                    .addComponent(upass)
                    .addGap(0, 308, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(uname, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(upass, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        DefaultTableModel dm = (DefaultTableModel) u_table.getModel();
        dm.setNumRows(0);
        DefaultTableModel dm1 = (DefaultTableModel) d_table.getModel();
        dm1.setNumRows(0);
        DefaultTableModel dm2 = (DefaultTableModel) table_reset.getModel();
        dm2.setNumRows(0);
        DB_USER = uname.getText();
        DB_PAS = upass.getText();

        String brn = "none";

        String query = "SELECT region_name FROM regions";
        String query1 = "SELECT fname,lname,gender,region,branch,position,phone_number,national_id,user_id FROM users where branch='" + brn + "'";
        try {
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
            stm = con.createStatement();
            rs = stm.executeQuery(query);
            boolean bool = rs.isBeforeFirst();
            while (rs.next()) {
                if (bool == true) {
                    String a = rs.getString("region_name");
                    b1.addItem(a);

                }
            }

            rs = stm.executeQuery(query1);
            boolean boolm = rs.isBeforeFirst();
            while (rs.next()) {
                if (boolm == true) {
                    String a = rs.getString("fname");
                    String b = rs.getString("lname");
                    String c = rs.getString("gender");
                    String d = rs.getString("region");
                    String e = rs.getString("branch");
                    String f = rs.getString("position");
                    String g = rs.getString("phone_number");
                    String h = rs.getString("national_id");
                    String j = rs.getString("user_id");

                    dm.addRow(new Object[]{a, b, c, d, e, f, g, h, j});
                    dm1.addRow(new Object[]{a, b, c, e, f, g, h, j});
                    String ab = a.concat(" ").concat(b);
                    dm2.addRow(new Object[]{ab, d, e, f, j});

                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
        }

        rates.setVisible(false);
        reports.setVisible(false);

        branches.setVisible(false);
        notifications.setVisible(false);

        currencies.setVisible(false);
        opening_balances.setVisible(false);
        denominations.setVisible(false);
        if (y == 1000) {
            rates.setSize(1000, 510);
            reports.setSize(1000, 510);
            users.setSize(1000, 510);
            branches.setSize(1000, 510);
            notifications.setSize(1000, 510);

            currencies.setSize(1000, 510);
            opening_balances.setSize(1000, 510);
            denominations.setSize(1000, 510);
            Thread th = new Thread() {
                public void run() {
                    try {
                        for (int i = 0; i >= 0; i--) {
                            Thread.sleep(5);
                            rates.setSize(i, 580);
                            reports.setSize(i, 580);
                            users.setSize(i, 580);
                            branches.setSize(i, 580);
                            notifications.setSize(i, 580);

                            currencies.setSize(i, 580);
                            opening_balances.setSize(i, 580);
                            denominations.setSize(i, 580);
                        }
                    } catch (Exception e) {
                    }
                }
            };
            th.start();
            y = 0;
        }

        if (y == 0) {
            rates.setSize(y, 580);
            reports.setSize(y, 580);
            branches.setSize(y, 580);
            notifications.setSize(y, 580);
            users.setSize(y, 580);

            currencies.setSize(y, 580);
            opening_balances.setSize(y, 580);
            denominations.setSize(y, 580);
            Thread th1 = new Thread() {
                public void run() {
                    try {
                        for (int i = 0; i <= y; i++) {
                            Thread.sleep(5);
                            users.show();
                            reports.setSize(y, 580);
                            users.setSize(y, 580);
                            branches.setSize(y, 580);
                            users.setSize(i, 580);
                            rates.setSize(y, 580);

                            currencies.setSize(y, 580);
                            opening_balances.setSize(y, 580);
                            denominations.setSize(y, 580);
                        }
                    } catch (Exception e) {
                    }
                }
            };
            th1.start();
            y = 1000;
        }
    }//GEN-LAST:event_jLabel5MouseClicked

    private void jLabel17MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel17MouseClicked
        dashboard_rates.setVisible(false);
        dashboard_reports.setVisible(false);
        dashboard_master.setVisible(false);
        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        dashboard_operations.setVisible(false);
        if (x == 240) {
            dashboard_rates.setSize(240, 510);
            dashboard_operations.setSize(240, 510);
            dashboard_reports.setSize(240, 510);
            dashboard_master.setSize(240, 510);
            dashboard_home.setSize(240, 510);
            Thread th = new Thread() {
                public void run() {
                    try {
                        for (int i = 0; i >= 0; i--) {
                            Thread.sleep(5);
                            dashboard_rates.setSize(i, 510);
                            dashboard_reports.setSize(i, 510);
                            dashboard_master.setSize(i, 510);
                            dashboard_home.setSize(i, 510);
                            dashboard_operations.setSize(i, 510);
                        }
                    } catch (Exception e) {
                    }
                }
            };
            th.start();
            x = 0;
        }

        if (x == 0) {
            dashboard_rates.setSize(x, 510);
            dashboard_reports.setSize(x, 510);
            dashboard_master.setSize(x, 510);
            dashboard_home.setSize(x, 510);
            dashboard_operations.setSize(x, 510);
            Thread th1 = new Thread() {
                public void run() {
                    try {
                        for (int i = 0; i <= x; i++) {
                            Thread.sleep(5);
                            dashboard_home.show();
                            dashboard_rates.setSize(x, 510);
                            dashboard_reports.setSize(x, 510);
                            dashboard_operations.setSize(x, 510);
                            dashboard_home.setSize(i, 510);
                            dashboard_master.setSize(x, 510);
                        }
                    } catch (Exception e) {
                    }
                }
            };
            th1.start();
            x = 240;
        }
    }//GEN-LAST:event_jLabel17MouseClicked

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        

//rates.setVisible(false);
        reports.setVisible(false);
        users.setVisible(false);
        branches.setVisible(false);
        //operations.setVisible(false);

        currencies.setVisible(false);
        opening_balances.setVisible(false);
        denominations.setVisible(false);
        if (y == 1000) {
            rates.setSize(1000, 580);
            reports.setSize(1000, 580);
            users.setSize(1000, 580);
            branches.setSize(1000, 580);
            //operations.setSize(1000, 580);

            currencies.setSize(1000, 580);
            opening_balances.setSize(1000, 580);
            denominations.setSize(1000, 580);
            Thread th = new Thread() {
                public void run() {
                    try {
                        for (int i = 0; i >= 0; i--) {
                            Thread.sleep(5);
                            rates.setSize(i, 580);
                            reports.setSize(i, 580);
                            users.setSize(i, 580);
                            branches.setSize(i, 580);
                            //operations.setSize(i, 580);

                            currencies.setSize(i, 580);
                            opening_balances.setSize(i, 580);
                            denominations.setSize(i, 580);
                        }
                    } catch (Exception e) {
                    }
                }
            };
            th.start();
            y = 0;
        }

        if (y == 0) {
            rates.setSize(y, 580);
            reports.setSize(y, 580);
            branches.setSize(y, 580);
            //operations.setSize(y, 580);
            users.setSize(y, 580);

            currencies.setSize(y, 580);
            opening_balances.setSize(y, 580);
            denominations.setSize(y, 580);
            Thread th1 = new Thread() {
                public void run() {
                    try {
                        for (int i = 0; i <= y; i++) {
                            Thread.sleep(5);
                            rates.show();
                            reports.setSize(y, 580);
                            users.setSize(y, 580);
                            branches.setSize(y, 580);
                            rates.setSize(i, 580);
                            users.setSize(y, 580);

                            currencies.setSize(y, 580);
                            opening_balances.setSize(y, 580);
                            denominations.setSize(y, 580);
                        }
                    } catch (Exception e) {
                    }
                }
            };
            th1.start();
            y = 1000;
        }
    }//GEN-LAST:event_jLabel6MouseClicked

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        String query = "SELECT short_code FROM currencies";
        try {
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
            stm = con.createStatement();
            rs = stm.executeQuery(query);
            boolean bool = rs.isBeforeFirst();
            while (rs.next()) {
                if (bool == true) {
                    String a = rs.getString("short_code");
                    prf2.addItem(" ");
                    prf2.addItem(a);

                    acc_currency.addItem(" ");
                    acc_currency.addItem(a);

                    cp3.addItem(" ");
                    cp3.addItem(a);

                    cg3.addItem(" ");
                    cg3.addItem(a);

                    tw5.addItem(" ");
                    tw5.addItem(a);

                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
        }

        rates.setVisible(false);
        //reports.setVisible(false);
        users.setVisible(false);
        branches.setVisible(false);
        notifications.setVisible(false);

        currencies.setVisible(false);
        opening_balances.setVisible(false);
        denominations.setVisible(false);
        if (y == 1000) {
            rates.setSize(1000, 580);
            reports.setSize(1100, 580);
            users.setSize(1000, 580);
            branches.setSize(1000, 580);
            notifications.setSize(1000, 580);

            currencies.setSize(1000, 580);
            opening_balances.setSize(1000, 580);
            denominations.setSize(1000, 580);
            Thread th = new Thread() {
                public void run() {
                    try {
                        for (int i = 0; i >= 0; i--) {
                            Thread.sleep(5);
                            rates.setSize(i, 580);
                            reports.setSize(i, 580);
                            users.setSize(i, 580);
                            branches.setSize(i, 580);
                            notifications.setSize(i, 580);

                            currencies.setSize(i, 580);
                            opening_balances.setSize(i, 580);
                            denominations.setSize(i, 580);
                        }
                    } catch (Exception e) {
                    }
                }
            };
            th.start();
            y = 0;
        }

        if (y == 0) {
            rates.setSize(y, 580);
            reports.setSize(y, 580);
            branches.setSize(y, 580);
            notifications.setSize(y, 580);
            users.setSize(y, 580);

            currencies.setSize(y, 580);
            opening_balances.setSize(y, 580);
            denominations.setSize(y, 580);
            Thread th1 = new Thread() {
                public void run() {
                    try {
                        for (int i = 0; i <= y; i++) {
                            Thread.sleep(5);
                            reports.show();
                            rates.setSize(y, 580);
                            users.setSize(y, 580);
                            branches.setSize(y, 580);
                            reports.setSize(i, 580);
                            users.setSize(y, 580);

                            currencies.setSize(y, 580);
                            opening_balances.setSize(y, 580);
                            denominations.setSize(y, 580);
                        }
                    } catch (Exception e) {
                    }
                }
            };
            th1.start();
            y = 1000;
        }
    }//GEN-LAST:event_jLabel7MouseClicked

    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked
        rates.setVisible(false);
        reports.setVisible(false);
        users.setVisible(false);
        branches.setVisible(false);
        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        currencies.setVisible(false);
        opening_balances.setVisible(false);
        denominations.setVisible(false);
        if (y == 1000) {
            rates.setSize(1000, 580);
            reports.setSize(1000, 580);
            users.setSize(1000, 580);
            branches.setSize(1000, 580);
            notifications.setSize(1000, 580);

            currencies.setSize(1000, 580);
            opening_balances.setSize(1000, 580);
            denominations.setSize(1000, 580);
            Thread th = new Thread() {
                public void run() {
                    try {
                        for (int i = 0; i >= 0; i--) {
                            Thread.sleep(5);
                            rates.setSize(i, 580);
                            reports.setSize(i, 580);
                            users.setSize(i, 580);
                            branches.setSize(i, 580);
                            notifications.setSize(i, 580);

                            currencies.setSize(i, 580);
                            opening_balances.setSize(i, 580);
                            denominations.setSize(i, 580);
                        }
                    } catch (Exception e) {
                    }
                }
            };
            th.start();
            y = 0;
        }

        if (y == 0) {
            rates.setSize(y, 580);
            reports.setSize(y, 580);
            branches.setSize(y, 580);
            notifications.setSize(y, 580);
            users.setSize(y, 580);

            currencies.setSize(y, 580);
            opening_balances.setSize(y, 580);
            denominations.setSize(y, 580);
            Thread th1 = new Thread() {
                public void run() {
                    try {
                        for (int i = 0; i <= y; i++) {
                            Thread.sleep(5);
                            notifications.show();
                            reports.setSize(y, 580);
                            users.setSize(y, 580);
                            branches.setSize(y, 580);
                            notifications.setSize(i, 580);
                            users.setSize(y, 580);

                            currencies.setSize(y, 580);
                            opening_balances.setSize(y, 580);
                            denominations.setSize(y, 580);
                        }
                    } catch (Exception e) {
                    }
                }
            };
            th1.start();
            y = 1000;
        }
    }//GEN-LAST:event_jLabel8MouseClicked

    private void jLabel22MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel22MouseClicked
        dashboard_rates.setVisible(false);
        dashboard_reports.setVisible(false);
        dashboard_master.setVisible(false);
        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        dashboard_operations.setVisible(false);
        if (x == 240) {
            dashboard_rates.setSize(240, 510);
            dashboard_operations.setSize(240, 510);
            dashboard_reports.setSize(240, 510);
            dashboard_master.setSize(240, 510);
            dashboard_home.setSize(240, 510);
            Thread th = new Thread() {
                public void run() {
                    try {
                        for (int i = 0; i >= 0; i--) {
                            Thread.sleep(5);
                            dashboard_rates.setSize(i, 510);
                            dashboard_reports.setSize(i, 510);
                            dashboard_master.setSize(i, 510);
                            dashboard_home.setSize(i, 510);
                            dashboard_operations.setSize(i, 510);
                        }
                    } catch (Exception e) {
                    }
                }
            };
            th.start();
            x = 0;
        }

        if (x == 0) {
            dashboard_rates.setSize(x, 510);
            dashboard_reports.setSize(x, 510);
            dashboard_master.setSize(x, 510);
            dashboard_home.setSize(x, 510);
            dashboard_operations.setSize(x, 510);
            Thread th1 = new Thread() {
                public void run() {
                    try {
                        for (int i = 0; i <= x; i++) {
                            Thread.sleep(5);
                            dashboard_home.show();
                            dashboard_rates.setSize(x, 510);
                            dashboard_reports.setSize(x, 510);
                            dashboard_operations.setSize(x, 510);
                            dashboard_home.setSize(i, 510);
                            dashboard_master.setSize(x, 510);
                        }
                    } catch (Exception e) {
                    }
                }
            };
            th1.start();
            x = 240;
        }
    }//GEN-LAST:event_jLabel22MouseClicked

    private void jLabel30MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel30MouseClicked
        dashboard_rates.setVisible(false);
        dashboard_reports.setVisible(false);
        dashboard_master.setVisible(false);
        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        dashboard_operations.setVisible(false);
        if (x == 240) {
            dashboard_rates.setSize(240, 510);
            dashboard_operations.setSize(240, 510);
            dashboard_reports.setSize(240, 510);
            dashboard_master.setSize(240, 510);
            dashboard_home.setSize(240, 510);
            Thread th = new Thread() {
                public void run() {
                    try {
                        for (int i = 0; i >= 0; i--) {
                            Thread.sleep(5);
                            dashboard_rates.setSize(i, 510);
                            dashboard_reports.setSize(i, 510);
                            dashboard_master.setSize(i, 510);
                            dashboard_home.setSize(i, 510);
                            dashboard_operations.setSize(i, 510);
                        }
                    } catch (Exception e) {
                    }
                }
            };
            th.start();
            x = 0;
        }

        if (x == 0) {
            dashboard_rates.setSize(x, 510);
            dashboard_reports.setSize(x, 510);
            dashboard_master.setSize(x, 510);
            dashboard_home.setSize(x, 510);
            dashboard_operations.setSize(x, 510);
            Thread th1 = new Thread() {
                public void run() {
                    try {
                        for (int i = 0; i <= x; i++) {
                            Thread.sleep(5);
                            dashboard_home.show();
                            dashboard_rates.setSize(x, 510);
                            dashboard_reports.setSize(x, 510);
                            dashboard_operations.setSize(x, 510);
                            dashboard_home.setSize(i, 510);
                            dashboard_master.setSize(x, 510);
                        }
                    } catch (Exception e) {
                    }
                }
            };
            th1.start();
            x = 240;
        }
    }//GEN-LAST:event_jLabel30MouseClicked

    private void jLabel36MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel36MouseClicked
        dashboard_rates.setVisible(false);
        dashboard_reports.setVisible(false);
        dashboard_master.setVisible(false);
        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        dashboard_operations.setVisible(false);
        if (x == 240) {
            dashboard_rates.setSize(240, 510);
            dashboard_operations.setSize(240, 510);
            dashboard_reports.setSize(240, 510);
            dashboard_master.setSize(240, 510);
            dashboard_home.setSize(240, 510);
            Thread th = new Thread() {
                public void run() {
                    try {
                        for (int i = 0; i >= 0; i--) {
                            Thread.sleep(5);
                            dashboard_rates.setSize(i, 510);
                            dashboard_reports.setSize(i, 510);
                            dashboard_master.setSize(i, 510);
                            dashboard_home.setSize(i, 510);
                            dashboard_operations.setSize(i, 510);
                        }
                    } catch (Exception e) {
                    }
                }
            };
            th.start();
            x = 0;
        }

        if (x == 0) {
            dashboard_rates.setSize(x, 510);
            dashboard_reports.setSize(x, 510);
            dashboard_master.setSize(x, 510);
            dashboard_home.setSize(x, 510);
            dashboard_operations.setSize(x, 510);
            Thread th1 = new Thread() {
                public void run() {
                    try {
                        for (int i = 0; i <= x; i++) {
                            Thread.sleep(5);
                            dashboard_home.show();
                            dashboard_rates.setSize(x, 510);
                            dashboard_reports.setSize(x, 510);
                            dashboard_operations.setSize(x, 510);
                            dashboard_home.setSize(i, 510);
                            dashboard_master.setSize(x, 510);
                        }
                    } catch (Exception e) {
                    }
                }
            };
            th1.start();
            x = 240;
        }
    }//GEN-LAST:event_jLabel36MouseClicked

    private void jLabel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseClicked
        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        String query = "SELECT region_name FROM regions";
        try {
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
            stm = con.createStatement();
            rs = stm.executeQuery(query);
            boolean bool = rs.isBeforeFirst();
            while (rs.next()) {
                if (bool == true) {
                    String a = rs.getString("region_name");
                    b1.addItem(a);

                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
        }

        rates.setVisible(false);
        reports.setVisible(false);
        users.setVisible(false);
        //branches.setVisible(false);
        notifications.setVisible(false);

        currencies.setVisible(false);
        opening_balances.setVisible(false);
        denominations.setVisible(false);
        if (y == 1000) {
            rates.setSize(1000, 580);
            reports.setSize(1000, 580);
            users.setSize(1000, 580);
            branches.setSize(1000, 580);
            notifications.setSize(1000, 580);

            currencies.setSize(1000, 580);
            opening_balances.setSize(1000, 580);
            denominations.setSize(1000, 580);
            Thread th = new Thread() {
                public void run() {
                    try {
                        for (int i = 0; i >= 0; i--) {
                            Thread.sleep(5);
                            rates.setSize(i, 580);
                            reports.setSize(i, 580);
                            users.setSize(i, 580);
                            branches.setSize(i, 580);
                            notifications.setSize(i, 580);

                            currencies.setSize(i, 580);
                            opening_balances.setSize(i, 580);
                            denominations.setSize(i, 580);
                        }
                    } catch (Exception e) {
                    }
                }
            };
            th.start();
            y = 0;
        }

        if (y == 0) {
            rates.setSize(y, 580);
            reports.setSize(y, 580);
            branches.setSize(y, 580);
            notifications.setSize(y, 580);
            users.setSize(y, 580);

            currencies.setSize(y, 580);
            opening_balances.setSize(y, 580);
            denominations.setSize(y, 580);
            Thread th1 = new Thread() {
                public void run() {
                    try {
                        for (int i = 0; i <= y; i++) {
                            Thread.sleep(5);
                            branches.show();
                            reports.setSize(y, 580);
                            users.setSize(y, 580);
                            rates.setSize(y, 580);
                            branches.setSize(i, 580);
                            users.setSize(y, 580);

                            currencies.setSize(y, 580);
                            opening_balances.setSize(y, 580);
                            denominations.setSize(y, 580);
                        }
                    } catch (Exception e) {
                    }
                }
            };
            th1.start();
            y = 1000;
        }
    }//GEN-LAST:event_jLabel12MouseClicked

    private void jLabel16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel16MouseClicked
        rates.setVisible(false);
        reports.setVisible(false);
        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        branches.setVisible(false);
        notifications.setVisible(false);

        currencies.setVisible(false);
        opening_balances.setVisible(false);
        denominations.setVisible(false);
        if (y == 1000) {
            rates.setSize(1000, 510);
            reports.setSize(1000, 510);
            users.setSize(1000, 510);
            branches.setSize(1000, 510);
            notifications.setSize(1000, 510);

            currencies.setSize(1000, 510);
            opening_balances.setSize(1000, 510);
            denominations.setSize(1000, 510);
            Thread th = new Thread() {
                public void run() {
                    try {
                        for (int i = 0; i >= 0; i--) {
                            Thread.sleep(5);
                            rates.setSize(i, 580);
                            reports.setSize(i, 580);
                            users.setSize(i, 580);
                            branches.setSize(i, 580);
                            notifications.setSize(i, 580);

                            currencies.setSize(i, 580);
                            opening_balances.setSize(i, 580);
                            denominations.setSize(i, 580);
                        }
                    } catch (Exception e) {
                    }
                }
            };
            th.start();
            y = 0;
        }

        if (y == 0) {
            rates.setSize(y, 580);
            reports.setSize(y, 580);
            branches.setSize(y, 580);
            notifications.setSize(y, 580);
            users.setSize(y, 580);

            currencies.setSize(y, 580);
            opening_balances.setSize(y, 580);
            denominations.setSize(y, 580);
            Thread th1 = new Thread() {
                public void run() {
                    try {
                        for (int i = 0; i <= y; i++) {
                            Thread.sleep(5);
                            users.show();
                            reports.setSize(y, 580);
                            users.setSize(y, 580);
                            branches.setSize(y, 580);
                            users.setSize(i, 580);
                            rates.setSize(y, 580);

                            currencies.setSize(y, 580);
                            opening_balances.setSize(y, 580);
                            denominations.setSize(y, 580);
                        }
                    } catch (Exception e) {
                    }
                }
            };
            th1.start();
            y = 1000;
        }
    }//GEN-LAST:event_jLabel16MouseClicked

    private void jLabel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseClicked
        rates.setVisible(false);
        reports.setVisible(false);
        users.setVisible(false);
        branches.setVisible(false);
        notifications.setVisible(false);

        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        opening_balances.setVisible(false);
        denominations.setVisible(false);
        if (y == 1000) {
            rates.setSize(1000, 580);
            reports.setSize(1000, 580);
            users.setSize(1000, 580);
            branches.setSize(1000, 580);
            notifications.setSize(1000, 580);

            currencies.setSize(1000, 580);
            opening_balances.setSize(1000, 580);
            denominations.setSize(1000, 580);
            Thread th = new Thread() {
                public void run() {
                    try {
                        for (int i = 0; i >= 0; i--) {
                            Thread.sleep(5);
                            rates.setSize(i, 580);
                            reports.setSize(i, 580);
                            users.setSize(i, 580);
                            branches.setSize(i, 580);
                            notifications.setSize(i, 580);

                            currencies.setSize(i, 580);
                            opening_balances.setSize(i, 580);
                            denominations.setSize(i, 580);
                        }
                    } catch (Exception e) {
                    }
                }
            };
            th.start();
            y = 0;
        }

        if (y == 0) {
            rates.setSize(y, 580);
            reports.setSize(y, 580);
            branches.setSize(y, 580);
            notifications.setSize(y, 580);
            users.setSize(y, 580);

            currencies.setSize(y, 580);
            opening_balances.setSize(y, 580);
            denominations.setSize(y, 580);
            Thread th1 = new Thread() {
                public void run() {
                    try {
                        for (int i = 0; i <= y; i++) {
                            Thread.sleep(5);
                            currencies.show();
                            reports.setSize(y, 580);
                            users.setSize(y, 580);
                            branches.setSize(y, 580);
                            rates.setSize(y, 580);
                            users.setSize(y, 580);

                            currencies.setSize(i, 580);
                            opening_balances.setSize(y, 580);
                            denominations.setSize(y, 580);
                        }
                    } catch (Exception e) {
                    }
                }
            };
            th1.start();
            y = 1000;
        }
    }//GEN-LAST:event_jLabel13MouseClicked

    private void jLabel14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseClicked
        rates.setVisible(false);
        reports.setVisible(false);
        users.setVisible(false);
        branches.setVisible(false);
        notifications.setVisible(false);

        currencies.setVisible(false);
        opening_balances.setVisible(false);
        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        if (y == 1000) {
            rates.setSize(1000, 580);
            reports.setSize(1000, 580);
            users.setSize(1000, 580);
            branches.setSize(1000, 580);
            notifications.setSize(1000, 580);

            currencies.setSize(1000, 580);
            opening_balances.setSize(1000, 580);
            denominations.setSize(1000, 580);
            Thread th = new Thread() {
                public void run() {
                    try {
                        for (int i = 0; i >= 0; i--) {
                            Thread.sleep(5);
                            rates.setSize(i, 580);
                            reports.setSize(i, 580);
                            users.setSize(i, 580);
                            branches.setSize(i, 580);
                            notifications.setSize(i, 580);

                            currencies.setSize(i, 580);
                            opening_balances.setSize(i, 580);
                            denominations.setSize(i, 580);
                        }
                    } catch (Exception e) {
                    }
                }
            };
            th.start();
            y = 0;
        }

        if (y == 0) {
            rates.setSize(y, 580);
            reports.setSize(y, 580);
            branches.setSize(y, 580);
            notifications.setSize(y, 580);
            users.setSize(y, 580);

            currencies.setSize(y, 580);
            opening_balances.setSize(y, 580);
            denominations.setSize(y, 580);
            Thread th1 = new Thread() {
                public void run() {
                    try {
                        for (int i = 0; i <= y; i++) {
                            Thread.sleep(5);
                            denominations.show();
                            reports.setSize(y, 580);
                            users.setSize(y, 580);
                            branches.setSize(y, 580);
                            rates.setSize(y, 580);
                            users.setSize(y, 580);

                            currencies.setSize(y, 580);
                            opening_balances.setSize(y, 580);
                            denominations.setSize(i, 580);
                        }
                    } catch (Exception e) {
                    }
                }
            };
            th1.start();
            y = 1000;
        }
    }//GEN-LAST:event_jLabel14MouseClicked

    private void jLabel15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseClicked
        rates.setVisible(false);
        reports.setVisible(false);
        users.setVisible(false);
        branches.setVisible(false);
        notifications.setVisible(false);

        currencies.setVisible(false);
        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        denominations.setVisible(false);
        if (y == 1000) {
            rates.setSize(1000, 580);
            reports.setSize(1000, 580);
            users.setSize(1000, 580);
            branches.setSize(1000, 580);
            notifications.setSize(1000, 580);

            currencies.setSize(1000, 580);
            opening_balances.setSize(1000, 580);
            denominations.setSize(1000, 580);
            Thread th = new Thread() {
                public void run() {
                    try {
                        for (int i = 0; i >= 0; i--) {
                            Thread.sleep(5);
                            rates.setSize(i, 580);
                            reports.setSize(i, 580);
                            users.setSize(i, 580);
                            branches.setSize(i, 580);
                            notifications.setSize(i, 580);

                            currencies.setSize(i, 580);
                            opening_balances.setSize(i, 580);
                            denominations.setSize(i, 580);
                        }
                    } catch (Exception e) {
                    }
                }
            };
            th.start();
            y = 0;
        }

        if (y == 0) {
            rates.setSize(y, 580);
            reports.setSize(y, 580);
            branches.setSize(y, 580);
            notifications.setSize(y, 580);
            users.setSize(y, 580);

            currencies.setSize(y, 580);
            opening_balances.setSize(y, 580);
            denominations.setSize(y, 580);
            Thread th1 = new Thread() {
                public void run() {
                    try {
                        for (int i = 0; i <= y; i++) {
                            Thread.sleep(5);
                            opening_balances.show();
                            reports.setSize(y, 580);
                            users.setSize(y, 580);
                            branches.setSize(y, 580);
                            rates.setSize(y, 580);
                            users.setSize(y, 580);

                            currencies.setSize(y, 580);
                            opening_balances.setSize(i, 580);
                            denominations.setSize(y, 580);
                        }
                    } catch (Exception e) {
                    }
                }
            };
            th1.start();
            y = 1000;
        }
    }//GEN-LAST:event_jLabel15MouseClicked

    private void a4KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_a4KeyTyped
        char key = evt.getKeyChar();
        if (!Character.isDigit(key)) {
            evt.consume();
        }
    }//GEN-LAST:event_a4KeyTyped

    private void a1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_a1KeyTyped
        char key = evt.getKeyChar();
        if (Character.isDigit(key)) {
            evt.consume();
        }
    }//GEN-LAST:event_a1KeyTyped

    private void a2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_a2KeyTyped
        char key = evt.getKeyChar();
        if (Character.isDigit(key)) {
            evt.consume();
        }
    }//GEN-LAST:event_a2KeyTyped

    private void a10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_a10KeyPressed
        int key = evt.getKeyCode();
        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        if (key == 10) {
            if (a1.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Do not leave the name field empty", "Error Message", JOptionPane.ERROR_MESSAGE);
            }
            if (a2.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Do not leave the surname field empty", "Error Message", JOptionPane.ERROR_MESSAGE);
            }
            if (a3.getSelectedItem().equals("")) {
                JOptionPane.showMessageDialog(null, "Please select the gender", "Error Message", JOptionPane.ERROR_MESSAGE);
            }

            if (a4.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "User phone number is required, please fill in the field", "Error Message", JOptionPane.ERROR_MESSAGE);
            }
            if (a5.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter national ID number", "Error Message", JOptionPane.ERROR_MESSAGE);
            }
            if (a6.getSelectedItem().equals("")) {
                JOptionPane.showMessageDialog(null, "Please select the region", "Error Message", JOptionPane.ERROR_MESSAGE);
            }
            if (a7.getSelectedItem().equals("")) {
                JOptionPane.showMessageDialog(null, "Please select the position for the user", "Error Message", JOptionPane.ERROR_MESSAGE);
            }
            if (a8.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Enter the prefered username", "Error Message", JOptionPane.ERROR_MESSAGE);
            }
            if (a9.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please create a password", "Error Message", JOptionPane.ERROR_MESSAGE);
            }
            if (a10.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please confirm the password", "Error Message", JOptionPane.ERROR_MESSAGE);
            }
            if (!a9.getText().matches(a10.getText())) {
                JOptionPane.showMessageDialog(null, "The passwords doesn't match, please try again", "Error Message", JOptionPane.ERROR_MESSAGE);
                a9.setText("");
                a10.setText("");
            }
            if (!a1.getText().isEmpty() && !a2.getText().isEmpty() && !a3.getSelectedItem().equals("") && !a4.getText().isEmpty() && !a5.getText().isEmpty()
                    && !a6.getSelectedItem().equals("") && !a7.getSelectedItem().equals("") && !a8.getText().isEmpty() && !a9.getText().isEmpty() && !a10.getText().isEmpty() && a9.getText().matches(a10.getText())) {
                int sa = Integer.parseInt(a4.getText(), 10);
                if (a4.getText().length() != 10) {
                    JOptionPane.showMessageDialog(null, "The phone number must have 10 digit", "Error Message", JOptionPane.ERROR_MESSAGE);
                }
//                if(sa>10){
//                  JOptionPane.showMessageDialog(null, "The phone number consists of 10 digit only and not more than", "Error Message", JOptionPane.ERROR_MESSAGE);  
//                }
                if (a1.getText().length() < 3) {
                    JOptionPane.showMessageDialog(null, "The name must have at least 3 character", "Error Message", JOptionPane.ERROR_MESSAGE);
                }
                if (a2.getText().length() < 3) {
                    JOptionPane.showMessageDialog(null, "The surname must have at least 3 character", "Error Message", JOptionPane.ERROR_MESSAGE);
                }
                if (a5.getText().length() < 10) {
                    JOptionPane.showMessageDialog(null, "The national id have at least 10 character", "Error Message", JOptionPane.ERROR_MESSAGE);
                }
                if (a4.getText().length() == 10 && a1.getText().length() >= 3 && a2.getText().length() >= 3 && a5.getText().length() >= 10) {

                    String query = "SELECT fname,lname,username,password,phone_number,national_id FROM users where fname='" + a1.getText() + "' and lname='" + a2.getText() + "' or national_id='" + a5.getText() + "' or username='" + a8.getText() + "' and phone_number='" + sa + "'";
                    try {
                        Class.forName(DB_DRIVER);
                        con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                        stm = con.createStatement();
                        rs = stm.executeQuery(query);
                        boolean bool = rs.isBeforeFirst();
                        while (rs.next()) {
                            if (bool == true) {
                                String a = rs.getString("fname");
                                String b = rs.getString("lname");
                                String d = rs.getString("national_id");
                                int e = rs.getInt("phone_number");
                                String f = rs.getString("username");
                                String g = rs.getString("password");
                                if (a1.getText().matches(a) && a2.getText().matches(b)) {
                                    JOptionPane.showMessageDialog(null, a + " " + b + " " + "already exist in the system", "System Error", JOptionPane.ERROR_MESSAGE);
                                    a1.setText("");
                                    a2.setText("");
                                }
                                if (a5.getText().matches(d)) {
                                    JOptionPane.showMessageDialog(null, " National ID " + a5.getText() + " " + "already exist in the system", "System Error", JOptionPane.ERROR_MESSAGE);
                                    a5.setText("");

                                }
                                if (sa == e) {
                                    JOptionPane.showMessageDialog(null, " Phone_number  " + a4.getText() + " " + "already exist in the system", "System Error", JOptionPane.ERROR_MESSAGE);
                                    a4.setText("");

                                }
                                if (a8.getText().matches(f)) {
                                    JOptionPane.showMessageDialog(null, " Username  " + a8.getText() + " " + "already exist in the system", "System Error", JOptionPane.ERROR_MESSAGE);
                                    a8.setText("");

                                }
                                if ((!a1.getText().matches(a) && a2.getText().matches(b) || a1.getText().matches(a) && !a2.getText().matches(b)) && !a5.getText().matches(d) && sa != e && !a8.getText().matches(f)) {
                                    if (!a6.getSelectedItem().equals("Finance Personel")) {
                                        try {
                                            con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                            pst = con.prepareStatement("insert into users values(?,?,?,?,?,?,?,?,?,?,?)");
                                            String brn = "none";
                                            pst.setString(1, a1.getText());
                                            pst.setString(2, a2.getText());
                                            pst.setString(3, a3.getSelectedItem().toString());
                                            pst.setInt(4, sa);
                                            pst.setString(5, a5.getText());
                                            pst.setString(6, a6.getSelectedItem().toString());
                                            pst.setString(7, brn);
                                            pst.setString(8, a7.getSelectedItem().toString());
                                            pst.setString(9, a8.getText());
                                            pst.setString(10, a10.getText());
                                            pst.setString(11, name.getText());

                                            int i = pst.executeUpdate();
                                            if (i > 0) {
                                                JOptionPane.showMessageDialog(null, "You have successfully added a new user into the system" + " " + " ", " Providence says....", JOptionPane.INFORMATION_MESSAGE);
                                                DefaultTableModel dm = (DefaultTableModel) a_table.getModel();
                                                dm.addRow(new Object[]{a1.getText(), a2.getText(), a3.getSelectedItem().toString(), a4.getText(), a6.getSelectedItem().toString(), a7.getSelectedItem().toString()});
                                                a1.setText("");
                                                a2.setText("");
                                                a4.setText("");
                                                a5.setText("");

                                                a8.setText("");
                                                a9.setText("");
                                                a10.setText("");
                                            }
                                        } catch (SQLException ev) {
                                            JOptionPane.showMessageDialog(null, ev.getMessage());

                                        }
                                    }
                                    if (a6.getSelectedItem().equals("Finance Personel")) {
                                        try {
                                            con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                            pst = con.prepareStatement("insert into users values(?,?,?,?,?,?,?,?,?,?,?)");
                                            String brn = "none";
                                            pst.setString(1, a1.getText());
                                            pst.setString(2, a2.getText());
                                            pst.setString(3, a3.getSelectedItem().toString());
                                            pst.setInt(4, sa);
                                            pst.setString(5, a5.getText());
                                            pst.setString(6, "none");
                                            pst.setString(7, brn);
                                            pst.setString(8, a7.getSelectedItem().toString());
                                            pst.setString(9, a8.getText());
                                            pst.setString(10, a10.getText());
                                            pst.setString(11, name.getText());

                                            int i = pst.executeUpdate();
                                            if (i > 0) {
                                                JOptionPane.showMessageDialog(null, "You have successfully added a new user into the system" + " " + " ", " Providence says....", JOptionPane.INFORMATION_MESSAGE);
                                                DefaultTableModel dm = (DefaultTableModel) a_table.getModel();
                                                dm.addRow(new Object[]{a1.getText(), a2.getText(), a3.getSelectedItem().toString(), a4.getText(), a6.getSelectedItem().toString(), a7.getSelectedItem().toString()});
                                                a1.setText("");
                                                a2.setText("");
                                                a4.setText("");
                                                a5.setText("");

                                                a8.setText("");
                                                a9.setText("");
                                                a10.setText("");
                                            }
                                        } catch (SQLException ev) {
                                            JOptionPane.showMessageDialog(null, ev.getMessage());

                                        }
                                    }

                                }

                            }
                        }
                        if (bool == false) {
                            try {
                                con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                pst = con.prepareStatement("insert into users values(?,?,?,?,?,?,?,?,?,?,?)");
                                String brn = "none";
                                pst.setString(1, a1.getText());
                                pst.setString(2, a2.getText());
                                pst.setString(3, a3.getSelectedItem().toString());
                                pst.setInt(4, sa);
                                pst.setString(5, a5.getText());
                                pst.setString(6, a6.getSelectedItem().toString());
                                pst.setString(7, brn);
                                pst.setString(8, a7.getSelectedItem().toString());
                                pst.setString(9, a8.getText());
                                pst.setString(10, a10.getText());
                                pst.setString(11, name.getText());

                                int i = pst.executeUpdate();
                                if (i > 0) {
                                    JOptionPane.showMessageDialog(null, "You have successfully added a new user into the system" + " " + " ", " Providence says....", JOptionPane.INFORMATION_MESSAGE);
                                    DefaultTableModel dm = (DefaultTableModel) a_table.getModel();
                                    dm.addRow(new Object[]{a1.getText(), a2.getText(), a3.getSelectedItem().toString(), a4.getText(), a6.getSelectedItem().toString(), a7.getSelectedItem().toString()});
                                    a1.setText("");
                                    a2.setText("");
                                    a4.setText("");
                                    a5.setText("");

                                    a8.setText("");
                                    a9.setText("");
                                    a10.setText("");
                                }
                            } catch (SQLException ev) {
                                JOptionPane.showMessageDialog(null, ev.getMessage());

                            }

                        }

                    } catch (ClassNotFoundException | SQLException e) {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

            }

        }
    }//GEN-LAST:event_a10KeyPressed

    private void jLabel24MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel24MouseClicked
        java.util.Date date = new java.util.Date();
        long t = date.getTime();
        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(t);
        if (r1.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter the region name", "Error Message", JOptionPane.ERROR_MESSAGE);
        }

        if (!r1.getText().isEmpty()) {

            String query = "SELECT region_name FROM regions where region_name='" + r1.getText() + "'";
            try {
                Class.forName(DB_DRIVER);
                con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                stm = con.createStatement();
                rs = stm.executeQuery(query);
                boolean bool = rs.isBeforeFirst();
                while (rs.next()) {
                    if (bool == true) {
                        String a = rs.getString("region");

                        if (r1.getText().matches(a)) {
                            JOptionPane.showMessageDialog(null, "Region " + a + "already exist in the system", "System Error", JOptionPane.ERROR_MESSAGE);
                            r1.setText("");

                        }

                    }
                }
                if (bool == false) {
                    try {
                        con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                        pst = con.prepareStatement("insert into regions values(?,?,?,?)");

                        pst.setString(1, r1.getText());
                        pst.setString(2, r2.getText());
                        pst.setString(3, name.getText());
                        pst.setTimestamp(4, sqlTimestamp);

                        int i = pst.executeUpdate();
                        if (i > 0) {
                            JOptionPane.showMessageDialog(null, "You have successfully added a new region into the system" + " " + " ", " Providence says....", JOptionPane.INFORMATION_MESSAGE);

                            r1.setText("");
                            r2.setText("");

                        }
                    } catch (SQLException ev) {
                        JOptionPane.showMessageDialog(null, ev.getMessage());

                    }

                }

            } catch (ClassNotFoundException | SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jLabel24MouseClicked

    private void jLabel37MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel37MouseClicked
        java.util.Date date = new java.util.Date();
        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        long t = date.getTime();
        java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(t);
        if (b1.getSelectedItem().equals("")) {
            JOptionPane.showMessageDialog(null, "Please select the branch name ", "Error Message", JOptionPane.ERROR_MESSAGE);
        }
        if (b2.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter the region name", "Error Message", JOptionPane.ERROR_MESSAGE);
        }
        if (b3.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter the code number", "Error Message", JOptionPane.ERROR_MESSAGE);
        }

        if (!b2.getText().isEmpty() && !b3.getText().isEmpty() && !b1.getSelectedItem().equals("")) {

            String query = "SELECT region_name,branch_name,area_code FROM branches where branch_name='" + b2.getText() + "' and area_code='" + b3.getText() + "'";
            try {
                Class.forName(DB_DRIVER);
                con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                stm = con.createStatement();
                rs = stm.executeQuery(query);
                boolean bool = rs.isBeforeFirst();
                while (rs.next()) {
                    if (bool == true) {
                        String a = rs.getString("branch_name");
                        String b = rs.getString("area_code");
                        if (b2.getText().equalsIgnoreCase(a)) {
                            JOptionPane.showMessageDialog(null, "Region " + a + "already exist in the system", "System Error", JOptionPane.ERROR_MESSAGE);
                            b2.setText("");

                        }
                        if (b3.getText().equalsIgnoreCase(b)) {
                            JOptionPane.showMessageDialog(null, "Area code " + b + "already exist in the system", "System Error", JOptionPane.ERROR_MESSAGE);
                            b3.setText("");

                        }

                    }
                }
                if (bool == false) {
                    try {
                        con = DriverManager.getConnection(DB_URL, DB_USER, DB_PAS);
                        pst = con.prepareStatement("insert into regions values(?,?,?,?)");

                        pst.setString(1, r1.getText());
                        pst.setString(2, r2.getText());
                        pst.setString(3, name.getText());
                        pst.setTimestamp(4, sqlTimestamp);

                        int i = pst.executeUpdate();
                        if (i > 0) {
                            JOptionPane.showMessageDialog(null, "You have successfully added a new region into the system" + " " + " ", " Providence says....", JOptionPane.INFORMATION_MESSAGE);

                            r1.setText("");
                            r2.setText("");

                        }
                    } catch (SQLException ev) {
                        JOptionPane.showMessageDialog(null, ev.getMessage());

                    }

                }

            } catch (ClassNotFoundException | SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jLabel37MouseClicked

    private void u_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_u_tableMouseClicked
        DefaultTableModel model = (DefaultTableModel) u_table.getModel();
        u1.setText(model.getValueAt(u_table.getSelectedRow(), 0).toString());
        u2.setText(model.getValueAt(u_table.getSelectedRow(), 1).toString());
        u4.setText(model.getValueAt(u_table.getSelectedRow(), 6).toString());
        u5.setText(model.getValueAt(u_table.getSelectedRow(), 7).toString());
        uid.setText(model.getValueAt(u_table.getSelectedRow(), 8).toString());
    }//GEN-LAST:event_u_tableMouseClicked

    private void jLabel42MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel42MouseClicked

        if (u1.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Do not leave the name field empty", "Error Message", JOptionPane.ERROR_MESSAGE);
        }
        if (u2.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Do not leave the surname field empty", "Error Message", JOptionPane.ERROR_MESSAGE);
        }
        if (u3.getSelectedItem().equals("")) {
            JOptionPane.showMessageDialog(null, "Please select the gender", "Error Message", JOptionPane.ERROR_MESSAGE);
        }

        if (u4.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "User phone number is required, please fill in the field", "Error Message", JOptionPane.ERROR_MESSAGE);
        }
        if (u5.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter national ID number", "Error Message", JOptionPane.ERROR_MESSAGE);
        }
        if (u6.getSelectedItem().equals("")) {
            JOptionPane.showMessageDialog(null, "Please select the region", "Error Message", JOptionPane.ERROR_MESSAGE);
        }
        if (!u1.getText().isEmpty() && !u2.getText().isEmpty() && !u3.getSelectedItem().equals("") && !u4.getText().isEmpty() && !u5.getText().isEmpty()
                && !u6.getSelectedItem().equals("") && !u7.getSelectedItem().equals("")) {
            int sa = Integer.parseInt(u4.getText());

            if (u4.getText().length() != 10) {
                JOptionPane.showMessageDialog(null, "The phone number must have 10 digit", "Error Message", JOptionPane.ERROR_MESSAGE);
            }

            if (u1.getText().length() < 3) {
                JOptionPane.showMessageDialog(null, "The name must have at least 3 character", "Error Message", JOptionPane.ERROR_MESSAGE);
            }
            if (u2.getText().length() < 3) {
                JOptionPane.showMessageDialog(null, "The surname must have at least 3 character", "Error Message", JOptionPane.ERROR_MESSAGE);
            }
            if (u5.getText().length() < 10) {
                JOptionPane.showMessageDialog(null, "The national id have at least 10 character", "Error Message", JOptionPane.ERROR_MESSAGE);
            }
            if (u4.getText().length() == 10 && u1.getText().length() >= 3 && u2.getText().length() >= 3 && u5.getText().length() >= 10) {
                SQLRun run = new SQLRun();
                String sqlq = "Update users set fname='" + u1.getText() + "' , lname='" + u2.getText() + "' , gender='" + u3.getSelectedItem().toString() + "' , phone_number='" + sa + "' , national_id='" + u5.getText() + "', position='" + u7.getSelectedItem().toString() + "', region='" + u6.getSelectedItem().toString() + "'  where user_id='" + uid.getText() + "'";
                int updated = run.sqlUpdate(sqlq);
                if (updated > 0) {
                    JOptionPane.showMessageDialog(null, "You have successfully updated user information", "User Details Update Notification", JOptionPane.INFORMATION_MESSAGE);
                    u1.setText("");
                    u2.setText("");
                    u4.setText("");
                    u5.setText("");
                    uid.setText("");

                }
            }
        }


    }//GEN-LAST:event_jLabel42MouseClicked

    private void d_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_d_tableMouseClicked
        DefaultTableModel model = (DefaultTableModel) d_table.getModel();
        na.setText(model.getValueAt(d_table.getSelectedRow(), 0).toString());
        nb.setText(model.getValueAt(d_table.getSelectedRow(), 1).toString());
        uid.setText(model.getValueAt(d_table.getSelectedRow(), 7).toString());
    }//GEN-LAST:event_d_tableMouseClicked

    private void d_tableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_d_tableKeyPressed
        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        int xv = Integer.parseInt(uid.getText());
        DefaultTableModel mode = (DefaultTableModel) d_table.getModel();
        if (d_table.getSelectedRow() == -1) {
            if (d_table.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "The table is empty", "Providence says....", JOptionPane.WARNING_MESSAGE);

            } else {
                JOptionPane.showMessageDialog(null, " You must select an account to remove", "Providence says....", JOptionPane.WARNING_MESSAGE);
            }
        } else {

            int input = JOptionPane.showConfirmDialog(null, " Are you sure that you want to remove " + na.getText() + " " + nb.getText() + " " + "from the system ?", "Providence says....", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (input == JOptionPane.NO_OPTION) {

            }
            if (input == JOptionPane.YES_OPTION) {
                mode.removeRow(d_table.getSelectedRow());
                SQLRun objSQLRun = new SQLRun();
                String sql = "DELETE FROM users WHERE user_id='" + xv + "'";

                int deleted = objSQLRun.sqlUpdate(sql);
                //getSum();
                if (deleted > 0) {
                    JOptionPane.showMessageDialog(null, " User account removed successfully from the system", "Povidence says....", JOptionPane.INFORMATION_MESSAGE);
                    uid.setText("");
                    na.setText("");
                    nb.setText("");

                } else {
                    if (uid.getText() == null) {

                    } else {
                        JOptionPane.showMessageDialog(null, "User with an ID :" + uid.getText() + " does not exist", "Providence says...", JOptionPane.ERROR_MESSAGE);

                    }
                }
            }
        }
    }//GEN-LAST:event_d_tableKeyPressed

    private void n3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_n3ActionPerformed
        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        if (!n3.getSelectedItem().equals("")) {

            if (n4.getText().isEmpty()) {
                n4.setText(n3.getSelectedItem().toString().concat("\t"));
                mk4.setText(n3.getSelectedItem().toString().concat("\t"));
            }
            if (!n4.getText().isEmpty()) {
                String av = n4.getText();
                if (av.contains(n3.getSelectedItem().toString())) {
                    //JOptionPane.showMessageDialog(rootPane, "You have already added "+n3.getSelectedItem().toString()+" "+"on your recipient list");

                }
                if (!av.contains(n3.getSelectedItem().toString())) {
                    mk4.setText(n3.getSelectedItem().toString().concat("\t").concat(av));
                    n4.setText(n3.getSelectedItem().toString().concat("\n").concat(av));
                }

            }

        }
    }//GEN-LAST:event_n3ActionPerformed

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        java.util.Date date = new java.util.Date();
        long t = date.getTime();
        java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(t);
        java.sql.Date sqlDate = new java.sql.Date(t);
        if (n1.getText().isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Please enter the subject of your notification", "Incomplete Data", JOptionPane.ERROR_MESSAGE);
        }
        if (n2.getText().isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Please provide the notification you want to send", "Incomplete Data", JOptionPane.ERROR_MESSAGE);
        }
        if (n4.getText().isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Please enter the recipients of your notification", "Incomplete Data", JOptionPane.ERROR_MESSAGE);
        }
        if (!n4.getText().isEmpty() && !n2.getText().isEmpty() && !n1.getText().isEmpty()) {

            try {
                Class.forName(DB_DRIVER);
                con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                pst = con.prepareStatement("insert into notifications values(?,?,?,?,?)");
                pst.setString(1, n1.getText());
                pst.setString(2, n2.getText());
                pst.setString(3, mk4.getText());
                pst.setString(4, name.getText());
                pst.setTimestamp(5, sqlTimestamp);

                int i = pst.executeUpdate();
                if (i > 0) {
                    JOptionPane.showMessageDialog(null, "Notification posted successfuly" + " " + " ", " Sending notification....", JOptionPane.INFORMATION_MESSAGE);

                    n1.setText("");
                    n2.setText("");
                    n4.setText("");

                }
            } catch (SQLException ev) {
                JOptionPane.showMessageDialog(null, ev.getMessage());

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Admininstrator2.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked
        String usn = JOptionPane.showInputDialog(null, "Enter your username", "Authentication", JOptionPane.QUESTION_MESSAGE);
        String pswd = JOptionPane.showInputDialog(null, "Enter your password", "Authentication", JOptionPane.QUESTION_MESSAGE);
        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        String query = "SELECT username,password,user_id FROM users where username='" + usn + "' and password='" + pswd + "'";
        try {
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
            stm = con.createStatement();
            rs = stm.executeQuery(query);
            boolean bool = rs.isBeforeFirst();
            while (rs.next()) {
                if (bool == true) {
                    String a = rs.getString("username");
                    String b = rs.getString("password");
                    String c = rs.getString("user_id");
                    if (usn.matches(a) && pswd.matches(b)) {
                        int input = JOptionPane.showConfirmDialog(null, " Are you sure that you want to change your password ?", "Providence says....", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                        if (input == JOptionPane.NO_OPTION) {

                        }
                        int v = Integer.parseInt(kms.getText(), 10);
                        if (input == JOptionPane.YES_OPTION) {
                            String usn1 = JOptionPane.showInputDialog(null, "Enter your new username", "Authentication", JOptionPane.QUESTION_MESSAGE);
                            String pswd1 = JOptionPane.showInputDialog(null, "Enter your new password", "Authentication", JOptionPane.QUESTION_MESSAGE);
                            String sd = md5(pswd1.toCharArray());
                            SQLRun run = new SQLRun();
                            String sqlq = "Update users set username='" + usn1 + "' , password='" + pswd1 + "'  where user_id='" + v + "'";
                            int updated = run.sqlUpdate(sqlq);
                            if (updated > 0) {
                                JOptionPane.showMessageDialog(null, "You have successfully chnaged your cedentials", "User Details Update Notification", JOptionPane.INFORMATION_MESSAGE);

                            }
                        }
                    }

                }
            }
            if (bool == false) {
                JOptionPane.showMessageDialog(null, "user not found");
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Admininstrator2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Admininstrator2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jLabel9MouseClicked

    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseClicked
        int input = JOptionPane.showConfirmDialog(null, " Are you sure that you want to sign out ?", "Providence says....", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (input == JOptionPane.NO_OPTION) {

        }
        if (input == JOptionPane.YES_OPTION) {
            Login l = new Login();
            l.setVisible(true);
            this.dispose();

        }
    }//GEN-LAST:event_jLabel10MouseClicked

    private void jLabel46MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel46MouseClicked
        int input = JOptionPane.showConfirmDialog(null, " Are you sure that you want to close this application", "Providence says....", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (input == JOptionPane.NO_OPTION) {

        }
        if (input == JOptionPane.YES_OPTION) {
            System.exit(4);

        }
    }//GEN-LAST:event_jLabel46MouseClicked

    private void jLabel41MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel41MouseClicked
        n4.setText("");
        mk4.setText("");
    }//GEN-LAST:event_jLabel41MouseClicked

    private void fa1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fa1ActionPerformed
        if (fa1.isSelected()) {
            fa2.setSelected(false);
        }
    }//GEN-LAST:event_fa1ActionPerformed

    private void fa2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fa2ActionPerformed
        if (fa2.isSelected()) {
            fa1.setSelected(false);
        }
    }//GEN-LAST:event_fa2ActionPerformed

    private void fa3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fa3ActionPerformed

    }//GEN-LAST:event_fa3ActionPerformed

    private void fa4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fa4ActionPerformed
        DefaultComboBoxModel dm = new DefaultComboBoxModel();
        if (fa4.isSelected()) {
            fa6.setEnabled(true);
        }
        if (!fa4.isSelected()) {
            fa6.setModel(dm);

            fa6.setEnabled(false);
        }

    }//GEN-LAST:event_fa4ActionPerformed

    private void jLabel128MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel128MouseClicked

    }//GEN-LAST:event_jLabel128MouseClicked

    private void fb1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fb1ActionPerformed
        if (fb1.isSelected()) {
            fb2.setSelected(false);
        }
    }//GEN-LAST:event_fb1ActionPerformed

    private void fb2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fb2ActionPerformed
        if (fb2.isSelected()) {
            fb1.setSelected(false);
        }
    }//GEN-LAST:event_fb2ActionPerformed

    private void fb3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fb3ActionPerformed
//        DefaultComboBoxModel dm = new DefaultComboBoxModel();
//        fb5.setModel(dm);
//        String DB_DRIVER = "org.postgresql.Driver";
//        String DB_URL = "jdbc:postgresql://localhost:5432/bureau";
//        String DB_USER = "postgres";
//        String DB_PAS = "danieloh24";
//
//        Connection con = null;
//        java.sql.Statement stm = null;
//        ResultSet rs = null;
//        ResultSet rs1 = null;
//        PreparedStatement pst = null;
//
//        String query1 = "SELECT region_name,branch_name,area_code,description,created_by,branch_id FROM branches";
//        try {
//            Class.forName(DB_DRIVER);
//            con = DriverManager.getConnection(DB_URL, DB_USER, DB_PAS);
//            stm = con.createStatement();
//
//            rs1 = stm.executeQuery(query1);
//            boolean boolm = rs1.isBeforeFirst();
//            while (rs1.next()) {
//                if (boolm == true) {
//                    String a = rs1.getString("region_name");
//                    fb5.addItem(a);
//
//                }
//            }
//        } catch (ClassNotFoundException | SQLException e) {
//            JOptionPane.showMessageDialog(null, e.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
//        }
    }//GEN-LAST:event_fb3ActionPerformed

    private void fb4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fb4ActionPerformed
        DefaultComboBoxModel dm = new DefaultComboBoxModel();
        if (fb4.isSelected()) {
            fb6.setEnabled(true);
        }
        if (!fb4.isSelected()) {

            fb6.setEnabled(false);
        }
    }//GEN-LAST:event_fb4ActionPerformed

    private void fb5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fb5ActionPerformed
        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        DefaultComboBoxModel dm = new DefaultComboBoxModel();
        fb6.setModel(dm);
        String query1 = "SELECT region_name,branch_name,area_code,description,created_by,branch_id FROM branches where region_name='" + fb5.getSelectedItem().toString() + "'";
        try {
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
            stm = con.createStatement();

            rs = stm.executeQuery(query1);
            boolean boolm = rs.isBeforeFirst();
            while (rs.next()) {
                if (boolm == true) {
                    String a = rs.getString("branch_name");
                    fb6.addItem(" ");
                    fb6.addItem(a);

                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_fb5ActionPerformed

    private void fc1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fc1ActionPerformed
        if (fc1.isSelected()) {
            fc2.setSelected(false);
        }
    }//GEN-LAST:event_fc1ActionPerformed

    private void fc2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fc2ActionPerformed
        if (fc2.isSelected()) {
            fc1.setSelected(false);
        }
    }//GEN-LAST:event_fc2ActionPerformed

    private void fc3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fc3ActionPerformed
//        DefaultComboBoxModel dm = new DefaultComboBoxModel();
//        fc5.setModel(dm);
//        String DB_DRIVER = "org.postgresql.Driver";
//        String DB_URL = "jdbc:postgresql://localhost:5432/bureau";
//        String DB_USER = "postgres";
//        String DB_PAS = "danieloh24";
//
//        Connection con = null;
//        java.sql.Statement stm = null;
//        ResultSet rs = null;
//        ResultSet rs1 = null;
//        PreparedStatement pst = null;
//
//        String query1 = "SELECT region_name,branch_name,area_code,description,created_by,branch_id FROM branches";
//        try {
//            Class.forName(DB_DRIVER);
//            con = DriverManager.getConnection(DB_URL, DB_USER, DB_PAS);
//            stm = con.createStatement();
//
//            rs1 = stm.executeQuery(query1);
//            boolean boolm = rs1.isBeforeFirst();
//            while (rs1.next()) {
//                if (boolm == true) {
//                    String a = rs1.getString("region_name");
//                    fc5.addItem(a);
//
//                }
//            }
//        } catch (ClassNotFoundException | SQLException e) {
//            JOptionPane.showMessageDialog(null, e.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
//        }
    }//GEN-LAST:event_fc3ActionPerformed

    private void fc4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fc4ActionPerformed
        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        if (!fc4.isSelected()) {
            fc6.setEnabled(false);
        }
        if (fc4.isSelected()) {
            fc6.setEnabled(true);
            DefaultComboBoxModel dm = new DefaultComboBoxModel();
            fc6.setModel(dm);
            //fb6.setEnabled(false);
            if (fc5.getSelectedItem().equals(" ")) {
                JOptionPane.showMessageDialog(null, "Please select the region first", "Some information missing to execute the query", JOptionPane.WARNING_MESSAGE);
            }
            if (!fc5.getSelectedItem().equals(" ")) {

                //DefaultComboBoxModel dm = new DefaultComboBoxModel();
                fc6.setModel(dm);

                String query1 = "SELECT region_name,branch_name,area_code,description,created_by,branch_id FROM branches where region_name='" + fc5.getSelectedItem().toString() + "'";
                try {
                    Class.forName(DB_DRIVER);
                    con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                    stm = con.createStatement();

                    rs = stm.executeQuery(query1);
                    boolean boolm = rs.isBeforeFirst();
                    while (rs.next()) {
                        if (boolm == true) {
                            String b = rs.getString("branch_name");
                            fc6.addItem(b);
                        }
                    }
                } catch (ClassNotFoundException | SQLException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Something went wrong ", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_fc4ActionPerformed

    private void fd1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fd1ActionPerformed
        if (fd1.isSelected()) {
            fd2.setSelected(false);
        }
    }//GEN-LAST:event_fd1ActionPerformed

    private void fd2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fd2ActionPerformed
        if (fd2.isSelected()) {
            fd1.setSelected(false);
        }
    }//GEN-LAST:event_fd2ActionPerformed

    private void fd3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fd3ActionPerformed

    }//GEN-LAST:event_fd3ActionPerformed

    private void fd4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fd4ActionPerformed
        DefaultComboBoxModel dm = new DefaultComboBoxModel();
        if (fd4.isSelected()) {
            fd6.setEnabled(true);
        }
        if (!fd4.isSelected()) {

            fd6.setEnabled(false);
        }
    }//GEN-LAST:event_fd4ActionPerformed

    private void jLabel92MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel92MouseClicked
        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        if (py_table.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "The table is empty, there's nothing to save. I can not allow you to save empty files of files with 0KB", "Providence says...", JOptionPane.ERROR_MESSAGE);
        }
        if (py_table.getRowCount() > 0) {
            String system_name = System.getProperty("user.name");
            FileOutputStream fstream = null;
            BufferedOutputStream bstream = null;
            XSSFWorkbook wk_book = null;
            JFileChooser jfile = new JFileChooser("C:\\" + system_name + "\\Desktop");
            jfile.setDialogTitle("Save your files");
            FileNameExtensionFilter fext = new FileNameExtensionFilter("EXCEL FILES", "xlsx", "xls", "xlsm");
            jfile.setFileFilter(fext);
            //jfile.showSaveDialog(null);
            int excel = jfile.showSaveDialog(null);
            if (excel == JFileChooser.APPROVE_OPTION) {

                try {
                    wk_book = new XSSFWorkbook();
                    XSSFSheet sheet = wk_book.createSheet("My sheet");
                    for (int i = 0; i < py_table.getRowCount(); i++) {
                        XSSFRow excelRow = sheet.createRow(i);
                        for (int j = 0; j < py_table.getColumnCount(); j++) {
                            XSSFCell excelCell = excelRow.createCell(j);

                            excelCell.setCellValue(py_table.getValueAt(i, j).toString());
                        }
                    }
                    fstream = new FileOutputStream(jfile.getSelectedFile() + ".xlsx");
                    bstream = new BufferedOutputStream(fstream);
                    wk_book.write(bstream);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                } finally {
                    try {
                        if (bstream != null) {
                            bstream.close();
                        }
                        if (fstream != null) {
                            fstream.close();
                        }

                        if (wk_book != null) {
                            wk_book.close();
                        }

                    } catch (IOException ex) {
                        Logger.getLogger(Finance_Dep.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
        }
    }//GEN-LAST:event_jLabel92MouseClicked

    private void jLabel134MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel134MouseClicked
        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        if (py_table.getRowCount() < 1) {
            JOptionPane.showMessageDialog(null, "The table is empty, so you can not print an empty file", "P rovidence says....", JOptionPane.ERROR_MESSAGE);
        }
        if (py_table.getRowCount() >= 1) {
            MessageFormat header = new MessageFormat("\t\tBureau De Change System : Reports Between " + kb1.getText() + " " + "&" + " " + kb2.getText());
            MessageFormat footer = new MessageFormat("Page{0, number, integer}");
            try {
                py_table.print(JTable.PrintMode.NORMAL, header, footer);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Printing Terminated", e.getMessage(), JOptionPane.ERROR_MESSAGE);
            }
        }

    }//GEN-LAST:event_jLabel134MouseClicked

    private void py1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_py1ActionPerformed
        if (py1.isSelected()) {
            py2.setSelected(false);
        }
    }//GEN-LAST:event_py1ActionPerformed

    private void py2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_py2ActionPerformed
        if (py2.isSelected()) {
            py1.setSelected(false);
        }
    }//GEN-LAST:event_py2ActionPerformed

    private void jLabel154MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel154MouseClicked
        DefaultTableModel mod = (DefaultTableModel) py_table.getModel();
        mod.setRowCount(0);
        String dg = py7.getDate().toString();
        kb1.setText(dg);
        DateFormat dateFormat2 = new SimpleDateFormat("y-MM-dd");
        py7.setDateFormatString("y-MM-dd");
        String dte = dateFormat2.format(py7.getDate());
        kb1.setText(dte);
        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        String dg1 = py8.getDate().toString();
        kb2.setText(dg1);
        DateFormat dateFormat = new SimpleDateFormat("y-MM-dd");
        py8.setDateFormatString("y-MM-dd");
        String dte1 = dateFormat.format(py8.getDate());
        kb2.setText(dte1);
        java.util.Date date = new java.util.Date();
        long t = date.getTime();

        String op = "buying";
        if (py4.isSelected()) {
            if (py1.isSelected() && !py2.isSelected()) {
                String query = "SELECT base_currency,short_code FROM currencies where base_currency='" + true + "'";
                try {
                    Class.forName(DB_DRIVER);
                    con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                    stm = con.createStatement();
                    rs = stm.executeQuery(query);
                    boolean bool = rs.isBeforeFirst();
                    while (rs.next()) {
                        if (bool == true) {
                            String a = rs.getString("short_code");
                            bfc.setText(a);
                            try {
                                String myDriver = "org.postgresql.Driver";
                                String myUrl = "jdbc:postgresql:" + configs.getText();
                                Class.forName(DB_DRIVER);
                                java.sql.Connection conn = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                String query1 = "SELECT base_currency,quote_currency,region,branch,exchange_rate,amount_traded_in,amount_traded_out,date,cashier FROM transactions_history where date between'" + kb1.getText() + "' and '" + kb2.getText() + "' and region='" + py5.getSelectedItem().toString() + "' and branch='" + py6.getSelectedItem().toString() + "' and base_currency='" + a + "' and operation='" + op + "' ";

                                java.sql.Statement st = conn.createStatement();

                                // execute the query, and get a java resultset
                                ResultSet rsq = st.executeQuery(query1);

                                // iterate through the java resultset
                                while (rsq.next()) {
                                    bfc.setText(a);
                                    String s1 = rsq.getString("quote_currency");
                                    String s2 = rsq.getString("base_currency");
                                    double s3 = rsq.getDouble("amount_traded_in");
                                    double s4 = rsq.getDouble("exchange_rate");
                                    double s5 = rsq.getDouble("amount_traded_out");
                                    String s6 = rsq.getString("region");
                                    String s7 = rsq.getString("branch");
                                    java.sql.Date sqlDate = rsq.getDate("date");
                                    String s8 = rsq.getString("cashier");

                                    mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                    int sum = 0;
                                }
                                boolean mb = rsq.isAfterLast();
                                if (mb == false) {
                                    JOptionPane.showMessageDialog(null, "There are no purchases recorded in the month of " + zh7.getValue() + "\n for " + zh6.getSelectedItem().toString() + " " + "purchasing foreign curency using local currency", "Transaction record not found", JOptionPane.WARNING_MESSAGE);
                                }

                            } catch (Exception er) {

                            }

                        }
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), " Something went  wrong, please contact your database administrator or the vendor", JOptionPane.ERROR_MESSAGE);
                }

            }
            if (!py1.isSelected() && py2.isSelected()) {
                String query = "SELECT base_currency,short_code FROM currencies where base_currency='" + true + "'";
                try {
                    Class.forName(DB_DRIVER);
                    con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                    stm = con.createStatement();
                    rs = stm.executeQuery(query);
                    boolean bool = rs.isBeforeFirst();
                    while (rs.next()) {
                        if (bool == true) {
                            String a = rs.getString("short_code");
                            try {
                                Class.forName(DB_DRIVER);
                                java.sql.Connection conn = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                String query1 = "SELECT base_currency,quote_currency,region,branch,exchange_rate,amount_traded_in,amount_traded_out,date,cashier FROM transactions_history where date between'" + kb1.getText() + "' and '" + kb2.getText() + "' and region='" + py5.getSelectedItem().toString() + "' and base_currency=!'" + a + "' and quote_currency=!'" + a + "' and operation='" + op + "' ";

                                java.sql.Statement st = conn.createStatement();

                                // execute the query, and get a java resultset
                                ResultSet rsq = st.executeQuery(query1);

                                // iterate through the java resultset
                                while (rsq.next()) {
                                    bfc.setText(a);
                                    String s1 = rsq.getString("quote_currency");
                                    String s2 = rsq.getString("base_currency");
                                    double s3 = rsq.getDouble("amount_traded_in");
                                    double s4 = rsq.getDouble("exchange_rate");
                                    double s5 = rsq.getDouble("amount_traded_out");
                                    String s6 = rsq.getString("region");
                                    String s7 = rsq.getString("branch");
                                    java.sql.Date sqlDate = rsq.getDate("date");
                                    String s8 = rsq.getString("cashier");

                                    mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                    int sum = 0;
                                }
                                boolean mb = rsq.isAfterLast();
                                if (mb == false) {
                                    JOptionPane.showMessageDialog(null, "There are no purchases recorded in the month of " + zh7.getValue() + "\n for " + zh6.getSelectedItem().toString() + " " + "purchasing foreign curency using local currency", "Transaction record not found", JOptionPane.WARNING_MESSAGE);
                                }

                            } catch (Exception er) {

                            }

                        }
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), " Something went  wrong, please contact your database administrator or the vendor", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        if (!py4.isSelected()) {
            if (py1.isSelected() && !py2.isSelected()) {
                String query = "SELECT base_currency,short_code FROM currencies where base_currency='" + true + "'";
                try {
                    Class.forName(DB_DRIVER);
                    con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                    stm = con.createStatement();
                    rs = stm.executeQuery(query);
                    boolean bool = rs.isBeforeFirst();
                    while (rs.next()) {
                        if (bool == true) {
                            String a = rs.getString("short_code");
                            try {
                                String myDriver = "org.postgresql.Driver";
                                Class.forName(DB_DRIVER);
                                java.sql.Connection conn = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                String query1 = "SELECT base_currency,quote_currency,region,branch,exchange_rate,amount_traded_in,amount_traded_out,date,cashier FROM transactions_history where date between'" + kb1.getText() + "'  and '" + kb2.getText() + "' and region='" + py5.getSelectedItem().toString() + "' and base_currency='" + a + "' and operation='" + op + "' ";

                                java.sql.Statement st = conn.createStatement();

                                // execute the query, and get a java resultset
                                ResultSet rsq = st.executeQuery(query1);
                                boolean swm = rsq.isBeforeFirst();
                                // iterate through the java resultset
                                while (rsq.next()) {
                                    bfc.setText(a);
                                    String s1 = rsq.getString("quote_currency");
                                    String s2 = rsq.getString("base_currency");
                                    double s3 = rsq.getDouble("amount_traded_in");
                                    double s4 = rsq.getDouble("exchange_rate");
                                    double s5 = rsq.getDouble("amount_traded_out");
                                    String s6 = rsq.getString("region");
                                    String s7 = rsq.getString("branch");
                                    java.sql.Date sqlDate = rsq.getDate("date");
                                    String s8 = rsq.getString("cashier");

                                    mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                    int sum = 0;
                                }
                                boolean mb = rsq.isAfterLast();
                                if (mb == false) {
                                    JOptionPane.showMessageDialog(null, "There are no purchases recorded in the month of " + zh7.getValue() + "\n for " + zh6.getSelectedItem().toString() + " " + "purchasing foreign curency using local currency", "Transaction record not found", JOptionPane.WARNING_MESSAGE);
                                }

                            } catch (Exception er) {

                            }

                        }
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), " Something went  wrong, please contact your database administrator or the vendor", JOptionPane.ERROR_MESSAGE);
                }

            }
            if (!py1.isSelected() && py2.isSelected()) {
                String query = "SELECT base_currency,short_code FROM currencies where base_currency='" + true + "'";
                try {
                    Class.forName(DB_DRIVER);
                    con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                    stm = con.createStatement();
                    rs = stm.executeQuery(query);
                    boolean bool = rs.isBeforeFirst();
                    while (rs.next()) {
                        if (bool == true) {
                            String a = rs.getString("short_code");
                            try {
                                Class.forName(DB_DRIVER);
                                java.sql.Connection conn = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                String query1 = "SELECT base_currency,quote_currency,region,branch,exchange_rate,amount_traded_in,amount_traded_out,date,cashier FROM transactions_history where date between'" + kb1.getText() + "'  and '" + kb2.getText() + "' and region='" + py5.getSelectedItem().toString() + "' and quote_currency!='" + a + "' and base_currency!='" + a + "' and operation='" + op + "' ";

                                java.sql.Statement st = conn.createStatement();

                                // execute the query, and get a java resultset
                                ResultSet rsq = st.executeQuery(query1);

                                // iterate through the java resultset
                                while (rsq.next()) {
                                    String s1 = rsq.getString("quote_currency");
                                    String s2 = rsq.getString("base_currency");
                                    double s3 = rsq.getDouble("amount_traded_in");
                                    double s4 = rsq.getDouble("exchange_rate");
                                    double s5 = rsq.getDouble("amount_traded_out");
                                    String s6 = rsq.getString("region");
                                    String s7 = rsq.getString("branch");
                                    java.sql.Date sqlDate = rsq.getDate("date");
                                    String s8 = rsq.getString("cashier");

                                    mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                    int sum = 0;
                                }
                                boolean mb = rsq.isAfterLast();
                                if (mb == false) {
                                    JOptionPane.showMessageDialog(null, "There are no purchases recorded in the month of " + zh7.getValue() + "\n for " + zh6.getSelectedItem().toString() + " " + "purchasing foreign curency using local currency", "Transaction record not found", JOptionPane.WARNING_MESSAGE);
                                }

                            } catch (Exception er) {

                            }

                        }
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), " Something went  wrong, please contact your database administrator or the vendor", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_jLabel154MouseClicked

    private void py3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_py3ActionPerformed

    }//GEN-LAST:event_py3ActionPerformed

    private void py4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_py4ActionPerformed
        DefaultComboBoxModel dm = new DefaultComboBoxModel();
        if (py4.isSelected()) {
            py6.setEnabled(true);
        }
        if (!py4.isSelected()) {
            py6.setModel(dm);

            py6.setEnabled(false);
        }
    }//GEN-LAST:event_py4ActionPerformed

    private void py5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_py5ActionPerformed
        DefaultComboBoxModel dm = new DefaultComboBoxModel();
        py6.setModel(dm);
        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        String query1 = "SELECT region_name,branch_name,area_code,description,created_by,branch_id FROM branches where region_name='" + py5.getSelectedItem().toString() + "'";
        try {
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
            stm = con.createStatement();

            rs = stm.executeQuery(query1);
            boolean boolm = rs.isBeforeFirst();
            while (rs.next()) {
                if (boolm == true) {
                    String a = rs.getString("branch_name");
                    py6.addItem(" ");
                    py6.addItem(a);

                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Something went wrong !!!!!", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_py5ActionPerformed

    private void jLabel156MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel156MouseClicked

    }//GEN-LAST:event_jLabel156MouseClicked

    private void jLabel158MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel158MouseClicked
        if (wy_table.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "The table is empty, there's nothing to save. I can not allow you to save empty files of files with 0KB", "Providence says...", JOptionPane.ERROR_MESSAGE);
        }
        if (wy_table.getRowCount() > 0) {
            String system_name = System.getProperty("user.name");
            FileOutputStream fstream = null;
            BufferedOutputStream bstream = null;
            XSSFWorkbook wk_book = null;
            JFileChooser jfile = new JFileChooser("C:\\" + system_name + "\\Desktop");
            jfile.setDialogTitle("Save your files");
            FileNameExtensionFilter fext = new FileNameExtensionFilter("EXCEL FILES", "xlsx", "xls", "xlsm");
            jfile.setFileFilter(fext);
            //jfile.showSaveDialog(null);
            int excel = jfile.showSaveDialog(null);
            if (excel == JFileChooser.APPROVE_OPTION) {

                try {
                    wk_book = new XSSFWorkbook();
                    XSSFSheet sheet = wk_book.createSheet("My sheet");
                    for (int i = 0; i < wy_table.getRowCount(); i++) {
                        XSSFRow excelRow = sheet.createRow(i);
                        for (int j = 0; j < wy_table.getColumnCount(); j++) {
                            XSSFCell excelCell = excelRow.createCell(j);

                            excelCell.setCellValue(wy_table.getValueAt(i, j).toString());
                        }
                    }
                    fstream = new FileOutputStream(jfile.getSelectedFile() + ".xlsx");
                    bstream = new BufferedOutputStream(fstream);
                    wk_book.write(bstream);
                } catch (FileNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                } finally {
                    try {
                        if (bstream != null) {
                            bstream.close();
                        }
                        if (fstream != null) {
                            fstream.close();
                        }

                        if (wk_book != null) {
                            wk_book.close();
                        }

                    } catch (IOException ex) {
                        Logger.getLogger(Finance_Dep.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
        }
    }//GEN-LAST:event_jLabel158MouseClicked

    private void jLabel159MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel159MouseClicked
        if (wy_table.getRowCount() < 1) {
            JOptionPane.showMessageDialog(null, "The table is empty, so you can not print an empty file", "P rovidence says....", JOptionPane.ERROR_MESSAGE);
        }
        if (wy_table.getRowCount() >= 1) {
            MessageFormat header = new MessageFormat("\t\tBureau De Change System : Report Dated " + ts1.getText());
            MessageFormat footer = new MessageFormat("Page{0, number, integer}");
            try {
                wy_table.print(JTable.PrintMode.NORMAL, header, footer);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Printing Terminated", e.getMessage(), JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jLabel159MouseClicked

    private void wy1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wy1ActionPerformed
        if (wy1.isSelected()) {
            wy2.setSelected(false);
        }
    }//GEN-LAST:event_wy1ActionPerformed

    private void wy2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wy2ActionPerformed
        if (wy2.isSelected()) {
            wy1.setSelected(false);
        }
    }//GEN-LAST:event_wy2ActionPerformed

    private void jLabel162MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel162MouseClicked
        DefaultTableModel mod = (DefaultTableModel) wy_table.getModel();
        mod.setRowCount(0);
        String dg = wy7.getDate().toString();
        ts1.setText(dg);
        DateFormat dateFormat2 = new SimpleDateFormat("y-MM-dd");
        wy7.setDateFormatString("y-MM-dd");
        String dte = dateFormat2.format(wy7.getDate());
        ts1.setText(dte);
        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        String op = "buying";

        java.util.Date date = new java.util.Date();
        long t = date.getTime();

        if (wy4.isSelected()) {
            if (wy1.isSelected() && !wy2.isSelected()) {
                String query = "SELECT base_currency,short_code FROM currencies where base_currency='" + true + "'";
                try {
                    Class.forName(DB_DRIVER);
                    con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                    stm = con.createStatement();
                    rs = stm.executeQuery(query);
                    boolean bool = rs.isBeforeFirst();
                    while (rs.next()) {
                        if (bool == true) {
                            String a = rs.getString("short_code");
                            try {
                                Class.forName(DB_DRIVER);
                                java.sql.Connection conn = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                String query1 = "SELECT base_currency,quote_currency,region,branch,exchange_rate,amount_traded_in,amount_traded_out,date,cashier FROM transactions_history where date='" + ts1.getText() + "'   and region='" + wy5.getSelectedItem().toString() + "' and branch='" + wy6.getSelectedItem().toString() + "' and quote_currency!='" + a + "' and base_currency='" + a + "' and operation='" + op + "'";

                                java.sql.Statement st = conn.createStatement();

                                // execute the query, and get a java resultset
                                ResultSet rsq = st.executeQuery(query1);

                                // iterate through the java resultset
                                while (rsq.next()) {
                                    String s1 = rsq.getString("quote_currency");
                                    String s2 = rsq.getString("base_currency");
                                    double s3 = rsq.getDouble("amount_traded_in");
                                    double s4 = rsq.getDouble("exchange_rate");
                                    double s5 = rsq.getDouble("amount_traded_out");
                                    String s6 = rsq.getString("region");
                                    String s7 = rsq.getString("branch");
                                    java.sql.Date sqlDate = rsq.getDate("date");
                                    String s8 = rsq.getString("cashier");

                                    mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                    int sum = 0;
                                }
                                boolean mb = rsq.isAfterLast();
                                if (mb == false) {
                                    JOptionPane.showMessageDialog(null, "There are no purchases recorded in the month of " + zh7.getValue() + "\n for " + zh6.getSelectedItem().toString() + " " + "purchasing foreign curency using local currency", "Transaction record not found", JOptionPane.WARNING_MESSAGE);
                                }

                            } catch (ClassNotFoundException | SQLException er) {
                                JOptionPane.showMessageDialog(null, er.getMessage());

                            }

                        }
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), " Something went wrong please contact your database administrator ir the vendor ERROR", JOptionPane.ERROR_MESSAGE);
                }

            }
            if (!wy1.isSelected() && wy2.isSelected()) {
                String query = "SELECT base_currency,short_code FROM currencies where base_currency='" + true + "'";
                try {
                    Class.forName(DB_DRIVER);
                    con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                    stm = con.createStatement();
                    rs = stm.executeQuery(query);
                    boolean bool = rs.isBeforeFirst();
                    while (rs.next()) {
                        if (bool == true) {
                            String a = rs.getString("short_code");
                            try {
                                Class.forName(DB_DRIVER);
                                java.sql.Connection conn = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                String query1 = "SELECT base_currency,quote_currency,region,branch,exchange_rate,amount_traded_in,amount_traded_out,date,cashier FROM transactions_history where date='" + ts1.getText() + "'  and region='" + wy5.getSelectedItem().toString() + "' and quote_currency!='" + a + "' and base_currency!='" + a + "' and operation='" + op + "' ";

                                java.sql.Statement st = conn.createStatement();

                                // execute the query, and get a java resultset
                                ResultSet rsq = st.executeQuery(query1);

                                // iterate through the java resultset
                                while (rsq.next()) {
                                    String s1 = rsq.getString("quote_currency");
                                    String s2 = rsq.getString("base_currency");
                                    double s3 = rsq.getDouble("amount_traded_in");
                                    double s4 = rsq.getDouble("exchange_rate");
                                    double s5 = rsq.getDouble("amount_traded_out");
                                    String s6 = rsq.getString("region");
                                    String s7 = rsq.getString("branch");
                                    java.sql.Date sqlDate = rsq.getDate("date");
                                    String s8 = rsq.getString("cashier");

                                    mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                    int sum = 0;
                                }
                                boolean mb = rsq.isAfterLast();
                                if (mb == false) {
                                    JOptionPane.showMessageDialog(null, "There are no purchases recorded in the month of " + zh7.getValue() + "\n for " + zh6.getSelectedItem().toString() + " " + "purchasing foreign curency using local currency", "Transaction record not found", JOptionPane.WARNING_MESSAGE);
                                }

                            } catch (ClassNotFoundException | SQLException er) {
                                JOptionPane.showMessageDialog(null, er.getMessage());

                            }

                        }
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), " Something went wrong please contact your database administrator ir the vendor ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        if (!wy4.isSelected()) {
            if (wy1.isSelected() && !wy2.isSelected()) {
                String query = "SELECT base_currency,short_code FROM currencies where base_currency='" + true + "'";
                try {
                    Class.forName(DB_DRIVER);
                    con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                    stm = con.createStatement();
                    rs = stm.executeQuery(query);
                    boolean bool = rs.isBeforeFirst();
                    while (rs.next()) {
                        if (bool == true) {
                            String a = rs.getString("short_code");
                            try {
                                Class.forName(DB_DRIVER);
                                java.sql.Connection conn = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                String query1 = "SELECT base_currency,quote_currency,region,branch,exchange_rate,amount_traded_in,amount_traded_out,date,cashier FROM transactions_history where date='" + ts1.getText() + "'  and region='" + wy5.getSelectedItem().toString() + "'  and quote_currency!='" + a + "' and base_currency='" + a + "' and operation='" + op + "'";

                                java.sql.Statement st = conn.createStatement();

                                // execute the query, and get a java resultset
                                ResultSet rsq = st.executeQuery(query1);
                                boolean swm = rsq.isBeforeFirst();
                                // iterate through the java resultset
                                while (rsq.next()) {
                                    String s1 = rsq.getString("quote_currency");
                                    String s2 = rsq.getString("base_currency");
                                    double s3 = rsq.getDouble("amount_traded_in");
                                    double s4 = rsq.getDouble("exchange_rate");
                                    double s5 = rsq.getDouble("amount_traded_out");
                                    String s6 = rsq.getString("region");
                                    String s7 = rsq.getString("branch");
                                    java.sql.Date sqlDate = rsq.getDate("date");
                                    String s8 = rsq.getString("cashier");

                                    mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                    int sum = 0;
                                }
                                boolean mb = rsq.isAfterLast();
                                if (mb == false) {
                                    JOptionPane.showMessageDialog(null, "There are no purchases recorded in the month of " + zh7.getValue() + "\n for " + zh6.getSelectedItem().toString() + " " + "purchasing foreign curency using local currency", "Transaction record not found", JOptionPane.WARNING_MESSAGE);
                                }

                            } catch (ClassNotFoundException | SQLException er) {
                                JOptionPane.showMessageDialog(null, er.getMessage());

                            }

                        }
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), " Something went wrong please contact your database administrator ir the vendor ERROR", JOptionPane.ERROR_MESSAGE);
                }

            }
            if (!wy1.isSelected() && wy2.isSelected()) {
                String query = "SELECT base_currency,short_code FROM currencies where base_currency='" + true + "'";
                try {
                    Class.forName(DB_DRIVER);
                    con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                    stm = con.createStatement();
                    rs = stm.executeQuery(query);
                    boolean bool = rs.isBeforeFirst();
                    while (rs.next()) {
                        if (bool == true) {
                            String a = rs.getString("short_code");
                            try {
                                Class.forName(DB_DRIVER);
                                java.sql.Connection conn = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                String query1 = "SELECT base_currency,quote_currency,region,branch,exchange_rate,amount_traded_in,amount_traded_out,date,cashier FROM transactions_history where date='" + ts1.getText() + "'  and region='" + wy5.getSelectedItem().toString() + "' and quote_currency!='" + a + "' and base_currency!='" + a + "' and operation='" + op + "' ";

                                java.sql.Statement st = conn.createStatement();

                                // execute the query, and get a java resultset
                                ResultSet rsq = st.executeQuery(query1);

                                // iterate through the java resultset
                                while (rsq.next()) {
                                    String s1 = rsq.getString("quote_currency");
                                    String s2 = rsq.getString("base_currency");
                                    double s3 = rsq.getDouble("amount_traded_in");
                                    double s4 = rsq.getDouble("exchange_rate");
                                    double s5 = rsq.getDouble("amount_traded_out");
                                    String s6 = rsq.getString("region");
                                    String s7 = rsq.getString("branch");
                                    java.sql.Date sqlDate = rsq.getDate("date");
                                    String s8 = rsq.getString("cashier");

                                    mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                    int sum = 0;
                                }
                                boolean mb = rsq.isAfterLast();
                                if (mb == false) {
                                    JOptionPane.showMessageDialog(null, "There are no purchases recorded in the month of " + zh7.getValue() + "\n for " + zh6.getSelectedItem().toString() + " " + "purchasing foreign curency using local currency", "Transaction record not found", JOptionPane.WARNING_MESSAGE);
                                }

                            } catch (ClassNotFoundException | SQLException er) {
                                JOptionPane.showMessageDialog(null, er.getMessage());

                            }

                        }
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), " Something went wrong please contact your database administrator ir the vendor ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_jLabel162MouseClicked

    private void wy3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wy3ActionPerformed

    }//GEN-LAST:event_wy3ActionPerformed

    private void wy4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wy4ActionPerformed
        DefaultComboBoxModel dm = new DefaultComboBoxModel();
        if (wy4.isSelected()) {
            wy6.setEnabled(true);
        }
        if (!wy4.isSelected()) {
            wy6.setModel(dm);

            wy6.setEnabled(false);
        }
    }//GEN-LAST:event_wy4ActionPerformed

    private void wy5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wy5ActionPerformed
        DefaultComboBoxModel dm = new DefaultComboBoxModel();
        wy6.setModel(dm);
        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        String query1 = "SELECT region_name,branch_name,area_code,description,created_by,branch_id FROM branches where region_name='" + wy5.getSelectedItem().toString() + "'";
        try {
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
            stm = con.createStatement();

            rs = stm.executeQuery(query1);
            boolean boolm = rs.isBeforeFirst();
            while (rs.next()) {
                if (boolm == true) {
                    String a = rs.getString("branch_name");
                    wy6.addItem(" ");
                    wy6.addItem(a);

                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Something went wrong !!!!!", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_wy5ActionPerformed

    private void jLabel167MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel167MouseClicked
        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        if (zh_table.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "The table is empty, there's nothing to save. I can not allow you to save empty files of files with 0KB", "Providence says...", JOptionPane.ERROR_MESSAGE);
        }
        if (zh_table.getRowCount() > 0) {
            String system_name = System.getProperty("user.name");
            FileOutputStream fstream = null;
            BufferedOutputStream bstream = null;
            XSSFWorkbook wk_book = null;
            JFileChooser jfile = new JFileChooser("C:\\" + system_name + "\\Desktop");
            jfile.setDialogTitle("Save your files");
            FileNameExtensionFilter fext = new FileNameExtensionFilter("EXCEL FILES", "xlsx", "xls", "xlsm");
            jfile.setFileFilter(fext);
            //jfile.showSaveDialog(null);
            int excel = jfile.showSaveDialog(null);
            if (excel == JFileChooser.APPROVE_OPTION) {

                try {
                    wk_book = new XSSFWorkbook();
                    XSSFSheet sheet = wk_book.createSheet("My sheet");
                    for (int i = 0; i < zh_table.getRowCount(); i++) {
                        XSSFRow excelRow = sheet.createRow(i);
                        for (int j = 0; j < zh_table.getColumnCount(); j++) {
                            XSSFCell excelCell = excelRow.createCell(j);

                            excelCell.setCellValue(zh_table.getValueAt(i, j).toString());
                        }
                    }
                    fstream = new FileOutputStream(jfile.getSelectedFile() + ".xlsx");
                    bstream = new BufferedOutputStream(fstream);
                    wk_book.write(bstream);
                } catch (FileNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                } finally {
                    try {
                        if (bstream != null) {
                            bstream.close();
                        }
                        if (fstream != null) {
                            fstream.close();
                        }

                        if (wk_book != null) {
                            wk_book.close();
                        }

                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    }
                }

            }
        }
    }//GEN-LAST:event_jLabel167MouseClicked

    private void jLabel168MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel168MouseClicked
        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        if (zh_table.getRowCount() < 1) {
            JOptionPane.showMessageDialog(null, "The table is empty, so you can not print an empty file", "P rovidence says....", JOptionPane.ERROR_MESSAGE);
        }
        if (zh_table.getRowCount() >= 1) {
            MessageFormat header = new MessageFormat("\tBureau De Change System : Purchases Report For the Month Of " + zh7.getValue());
            MessageFormat footer = new MessageFormat("Page{0, number, integer}");
            try {
                zh_table.print(JTable.PrintMode.NORMAL, header, footer);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Printing Terminated", e.getMessage(), JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jLabel168MouseClicked

    private void zh1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zh1ActionPerformed
        if (zh1.isSelected()) {
            zh2.setSelected(false);
        }
    }//GEN-LAST:event_zh1ActionPerformed

    private void zh2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zh2ActionPerformed
        if (zh2.isSelected()) {
            zh1.setSelected(false);
        }
    }//GEN-LAST:event_zh2ActionPerformed

    private void jLabel171MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel171MouseClicked
        DefaultTableModel mod = (DefaultTableModel) zh_table.getModel();
        mod.setRowCount(0);
        java.util.Date date = new java.util.Date();
        long t = date.getTime();
        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        String op = "buying";

        if (zh4.isSelected()) {
            if (zh1.isSelected() && !zh2.isSelected()) {
                String query = "SELECT base_currency,short_code FROM currencies where base_currency='" + true + "'";
                try {
                    Class.forName(DB_DRIVER);
                    con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                    stm = con.createStatement();
                    rs = stm.executeQuery(query);
                    boolean bool = rs.isBeforeFirst();
                    while (rs.next()) {
                        if (bool == true) {
                            String a = rs.getString("short_code");
                            try {
                                String myDriver = "org.postgresql.Driver";
                                String myUrl = "jdbc:postgresql:" + configs.getText();
                                Class.forName(DB_DRIVER);
                                java.sql.Connection conn = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                String query1 = "SELECT base_currency,quote_currency,region,branch,exchange_rate,amount_traded_in,amount_traded_out,date,cashier FROM transactions_history where region='" + zh5.getSelectedItem().toString() + "' and operation='" + op + "' and quote_currency!='" + a + "' and  base_currency='" + a + "' and branch='" + zh6.getSelectedItem().toString() + "' ";

                                java.sql.Statement st = conn.createStatement();

                                // execute the query, and get a java resultset
                                ResultSet rsq = st.executeQuery(query1);

                                // iterate through the java resultset
                                while (rsq.next()) {
                                    String s1 = rsq.getString("quote_currency");
                                    String s2 = rsq.getString("base_currency");
                                    double s3 = rsq.getDouble("amount_traded_in");
                                    double s4 = rsq.getDouble("exchange_rate");
                                    double s5 = rsq.getDouble("amount_traded_out");
                                    String s6 = rsq.getString("region");
                                    String s7 = rsq.getString("branch");
                                    java.sql.Date sqlDate = rsq.getDate("date");
                                    String s8 = rsq.getString("cashier");
                                    double sum = 0;

                                    String wq = sqlDate.toString();
                                    String qw = wq.substring(0, 4);
                                    int af = Integer.parseInt(pym.getValue().toString());
                                    int ag = Integer.parseInt(qw);

                                    if (zh7.getValue().equals("September") && af == ag) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "09";

                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 4).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }

                                    if (zh7.getValue().equals("January") && af == ag) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "01";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 4).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("February") && af == ag) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "02";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 4).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("March") && af == ag) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "03";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 4).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("April") && af == ag) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "04";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 4).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("May") && af == ag) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "05";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 4).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("June") && af == ag) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "06";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 4).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("July") && af == ag) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "07";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 4).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("August") && af == ag) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "08";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 4).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("October") && af == ag) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "10";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 4).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("November") && af == ag) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "11";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 4).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("December") && af == ag) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "12";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 4).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }

                                    //int sum = 0;
                                }
                                boolean mb = rsq.isAfterLast();
                                if (mb == false) {
                                    JOptionPane.showMessageDialog(null, "There are no purchases recorded in the month of " + zh7.getValue() + "\n for " + zh6.getSelectedItem().toString() + " " + "purchasing foreign curency using local currency", "Transaction record not found", JOptionPane.WARNING_MESSAGE);
                                }

                            } catch (ClassNotFoundException | SQLException er) {
                                JOptionPane.showMessageDialog(null, er.getMessage());

                            }

                        }
                    }
                } catch (HeadlessException | ClassNotFoundException | NumberFormatException | SQLException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), " Something went wrong please contact your database administrator or the vendor", JOptionPane.ERROR_MESSAGE);
                }

            }
            if (!zh1.isSelected() && zh2.isSelected()) {
                String query = "SELECT base_currency,short_code FROM currencies where base_currency='" + true + "'";
                try {
                    Class.forName(DB_DRIVER);
                    con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                    stm = con.createStatement();
                    rs = stm.executeQuery(query);
                    boolean bool = rs.isBeforeFirst();
                    while (rs.next()) {
                        if (bool == true) {
                            String a = rs.getString("short_code");
                            try {
                                String myDriver = "org.postgresql.Driver";
                                String myUrl = "jdbc:postgresql:" + configs.getText();
                                Class.forName(DB_DRIVER);
                                java.sql.Connection conn = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                String query1 = "SELECT base_currency,quote_currency,region,branch,exchange_rate,amount_traded_in,amount_traded_out,date,cashier FROM transactions_history where region='" + zh5.getSelectedItem().toString() + "' and branch='" + zh6.getSelectedItem().toString() + "' and operation='" + op + "' and quote_currency!='" + a + "' and base_currency!='" + a + "' ";

                                java.sql.Statement st = conn.createStatement();

                                // execute the query, and get a java resultset
                                ResultSet rsq = st.executeQuery(query1);

                                // iterate through the java resultset
                                while (rsq.next()) {
                                    String s1 = rsq.getString("quote_currency");
                                    String s2 = rsq.getString("base_currency");
                                    double s3 = rsq.getDouble("amount_traded_in");
                                    double s4 = rsq.getDouble("exchange_rate");
                                    double s5 = rsq.getDouble("amount_traded_out");
                                    String s6 = rsq.getString("region");
                                    String s7 = rsq.getString("branch");
                                    java.sql.Date sqlDate = rsq.getDate("date");
                                    String s8 = rsq.getString("cashier");
                                    double sum = 0;

                                    String wq = sqlDate.toString();
                                    String qw = wq.substring(0, 4);
                                    int af = Integer.parseInt(pym.getValue().toString());
                                    int ag = Integer.parseInt(qw);
                                    if (zh7.getValue().equals("September") && af == ag) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "09";

                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 4).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }

                                    if (zh7.getValue().equals("January") && af == ag) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "01";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 4).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("February") && af == ag) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "02";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 4).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("March") && af == ag) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "03";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 4).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("April") && af == ag) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "04";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 4).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("May") && af == ag) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "05";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 4).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("June") && af == ag) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "06";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 4).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("July") && af == ag) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "07";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 4).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("August") && af == ag) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "08";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 4).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("October") && af == ag) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "10";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 4).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("November") && af == ag) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "11";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 4).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("December") && af == ag) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "12";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 4).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                }
                                boolean mb = rsq.isAfterLast();
                                if (mb == false) {
                                    JOptionPane.showMessageDialog(null, "There are no purchases recorded in the month of " + zh7.getValue() + "\n for " + zh6.getSelectedItem().toString() + " " + "purchasing foreign curency using local currency", "Transaction record not found", JOptionPane.WARNING_MESSAGE);
                                }

                            } catch (ClassNotFoundException | SQLException er) {
                                JOptionPane.showMessageDialog(null, er.getMessage());

                            }

                        }
                    }
                } catch (HeadlessException | ClassNotFoundException | NumberFormatException | SQLException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), " Something went wrong please contact your database administrator or the vendor", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        if (!zh4.isSelected()) {
            if (zh1.isSelected() && !zh2.isSelected()) {
                String query = "SELECT base_currency,short_code FROM currencies where base_currency='" + true + "'";
                try {
                    Class.forName(DB_DRIVER);
                    con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                    stm = con.createStatement();
                    rs = stm.executeQuery(query);
                    boolean bool = rs.isBeforeFirst();
                    while (rs.next()) {
                        if (bool == true) {
                            String a = rs.getString("short_code");
                            try {
                                String myDriver = "org.postgresql.Driver";
                                String myUrl = "jdbc:postgresql:" + configs.getText();
                                Class.forName(DB_DRIVER);
                                java.sql.Connection conn = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                String query1 = "SELECT base_currency,quote_currency,region,branch,exchange_rate,amount_traded_in,amount_traded_out,date,cashier FROM transactions_history where region='" + zh5.getSelectedItem().toString() + "' and operation='" + op + "' and  quote_currency!='" + a + "' and base_currency='" + a + "'";

                                java.sql.Statement st = conn.createStatement();

                                // execute the query, and get a java resultset
                                ResultSet rsq = st.executeQuery(query1);
                                boolean swm = rsq.isBeforeFirst();
                                // iterate through the java resultset
                                while (rsq.next()) {
                                    String s1 = rsq.getString("quote_currency");
                                    String s2 = rsq.getString("base_currency");
                                    double s3 = rsq.getDouble("amount_traded_in");
                                    double s4 = rsq.getDouble("exchange_rate");
                                    double s5 = rsq.getDouble("amount_traded_out");
                                    String s6 = rsq.getString("region");
                                    String s7 = rsq.getString("branch");
                                    java.sql.Date sqlDate = rsq.getDate("date");
                                    String s8 = rsq.getString("cashier");

                                    double sum = 0;

                                    String wq = sqlDate.toString();
                                    String qw = wq.substring(0, 4);
                                    int af = Integer.parseInt(pym.getValue().toString());
                                    int ag = Integer.parseInt(qw);
                                    if (zh7.getValue().equals("September") && af == ag) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "09";

                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 4).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }

                                    if (zh7.getValue().equals("January") && af == ag) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "01";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 4).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("February") && af == ag) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "02";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 4).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("March") && af == ag) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "03";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 4).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("April") && af == ag) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "04";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 4).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("May") && af == ag) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "05";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 4).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("June") && af == ag) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "06";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 4).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("July") && af == ag) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "07";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 4).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("August") && af == ag) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "08";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 4).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("October") && af == ag) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "10";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 4).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("November") && af == ag) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "11";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 4).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("December") && af == ag) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "12";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 4).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                }
                                boolean mb = rsq.isAfterLast();
                                if (mb == false) {
                                    JOptionPane.showMessageDialog(null, "There are no purchases recorded in the month of " + zh7.getValue() + "\n for " + zh6.getSelectedItem().toString() + " " + "purchasing foreign curency using local currency", "Transaction record not found", JOptionPane.WARNING_MESSAGE);
                                }

                            } catch (ClassNotFoundException | SQLException er) {
                                JOptionPane.showMessageDialog(null, er.getMessage());

                            }

                        }
                    }
                } catch (HeadlessException | ClassNotFoundException | NumberFormatException | SQLException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), " Something went wrong please contact your database administrator or the vendor", JOptionPane.ERROR_MESSAGE);
                }

            }
            if (!zh1.isSelected() && zh2.isSelected()) {
                String query = "SELECT base_currency,short_code FROM currencies where base_currency='" + true + "'";
                try {
                    Class.forName(DB_DRIVER);
                    con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                    stm = con.createStatement();
                    rs = stm.executeQuery(query);
                    boolean bool = rs.isBeforeFirst();
                    while (rs.next()) {
                        if (bool == true) {
                            String a = rs.getString("short_code");
                            try {
                                String myDriver = "org.postgresql.Driver";
                                String myUrl = "jdbc:postgresql:" + configs.getText();
                                Class.forName(DB_DRIVER);
                                java.sql.Connection conn = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                String query1 = "SELECT base_currency,quote_currency,region,branch,exchange_rate,amount_traded_in,amount_traded_out,date,cashier FROM transactions_history where region='" + zh5.getSelectedItem().toString() + "' and operation='" + op + "' and quote_currency!='" + a + "' and base_currency!='" + a + "' ";

                                java.sql.Statement st = conn.createStatement();

                                // execute the query, and get a java resultset
                                ResultSet rsq = st.executeQuery(query1);

                                // iterate through the java resultset
                                while (rsq.next()) {
                                    String s1 = rsq.getString("quote_currency");
                                    String s2 = rsq.getString("base_currency");
                                    double s3 = rsq.getDouble("amount_traded_in");
                                    double s4 = rsq.getDouble("exchange_rate");
                                    double s5 = rsq.getDouble("amount_traded_out");
                                    String s6 = rsq.getString("region");
                                    String s7 = rsq.getString("branch");
                                    java.sql.Date sqlDate = rsq.getDate("date");
                                    String s8 = rsq.getString("cashier");

                                    double sum = 0;

                                    String wq = sqlDate.toString();
                                    String qw = wq.substring(0, 4);
                                    int af = Integer.parseInt(pym.getValue().toString());
                                    int ag = Integer.parseInt(qw);
                                    if (zh7.getValue().equals("September") && af == ag) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "09";

                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 4).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }

                                    if (zh7.getValue().equals("January") && af == ag) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "01";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 4).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("February") && af == ag) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "02";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 4).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("March") && af == ag) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "03";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 4).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("April") && af == ag) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "04";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 4).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("May") && af == ag) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "05";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 4).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("June") && af == ag) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "06";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 4).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("July") && af == ag) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "07";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 4).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("August") && af == ag) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "08";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 4).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("October") && af == ag) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "10";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 4).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("November") && af == ag) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "11";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 4).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("December") && af == ag) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "12";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 4).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                }
                                boolean mb = rsq.isAfterLast();
                                if (mb == false) {
                                    JOptionPane.showMessageDialog(null, "There are no purchases recorded in the month of " + zh7.getValue() + "\n for " + zh6.getSelectedItem().toString() + " " + "purchasing foreign curency using local currency", "Transaction record not found", JOptionPane.WARNING_MESSAGE);
                                }

                            } catch (ClassNotFoundException | SQLException er) {
                                JOptionPane.showMessageDialog(null, er.getMessage());

                            }

                        }
                    }
                } catch (HeadlessException | ClassNotFoundException | NumberFormatException | SQLException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), " Something went wrong please contact your database administrator or the vendor", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_jLabel171MouseClicked

    private void zh3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zh3ActionPerformed

    }//GEN-LAST:event_zh3ActionPerformed

    private void zh4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zh4ActionPerformed
        DefaultComboBoxModel dm = new DefaultComboBoxModel();
        if (zh4.isSelected()) {
            zh6.setEnabled(true);
        }
        if (!zh4.isSelected()) {
            zh6.setModel(dm);

            zh6.setEnabled(false);
        }
    }//GEN-LAST:event_zh4ActionPerformed

    private void jLabel176MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel176MouseClicked
        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        if (px_table.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "The table is empty, there's nothing to save. I can not allow you to save empty files of files with 0KB", "Providence says...", JOptionPane.ERROR_MESSAGE);
        }
        if (px_table.getRowCount() > 0) {
            String system_name = System.getProperty("user.name");
            FileOutputStream fstream = null;
            BufferedOutputStream bstream = null;
            XSSFWorkbook wk_book = null;
            JFileChooser jfile = new JFileChooser("C:\\" + system_name + "\\Desktop");
            jfile.setDialogTitle("Save your files");
            FileNameExtensionFilter fext = new FileNameExtensionFilter("EXCEL FILES", "xlsx", "xls", "xlsm");
            jfile.setFileFilter(fext);
            //jfile.showSaveDialog(null);
            int excel = jfile.showSaveDialog(null);
            if (excel == JFileChooser.APPROVE_OPTION) {

                try {
                    wk_book = new XSSFWorkbook();
                    XSSFSheet sheet = wk_book.createSheet("My sheet");
                    for (int i = 0; i < px_table.getRowCount(); i++) {
                        XSSFRow excelRow = sheet.createRow(i);
                        for (int j = 0; j < px_table.getColumnCount(); j++) {
                            XSSFCell excelCell = excelRow.createCell(j);

                            excelCell.setCellValue(px_table.getValueAt(i, j).toString());
                        }
                    }
                    fstream = new FileOutputStream(jfile.getSelectedFile() + ".xlsx");
                    bstream = new BufferedOutputStream(fstream);
                    wk_book.write(bstream);
                } catch (FileNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                } finally {
                    try {
                        if (bstream != null) {
                            bstream.close();
                        }
                        if (fstream != null) {
                            fstream.close();
                        }

                        if (wk_book != null) {
                            wk_book.close();
                        }

                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    }
                }

            }
        }
    }//GEN-LAST:event_jLabel176MouseClicked

    private void jLabel176MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel176MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel176MouseEntered

    private void jLabel177MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel177MouseClicked
        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        if (px_table.getRowCount() < 1) {
            JOptionPane.showMessageDialog(null, "The table is empty, so you can not print an empty file", "P rovidence says....", JOptionPane.ERROR_MESSAGE);
        }
        if (px_table.getRowCount() >= 1) {
            MessageFormat header = new MessageFormat("\tBureau De Change System : Purchases Report For the Year Of " + px7.getValue());
            MessageFormat footer = new MessageFormat("Page{0, number, integer}");
            try {
                px_table.print(JTable.PrintMode.NORMAL, header, footer);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Printing Terminated", e.getMessage(), JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jLabel177MouseClicked

    private void px1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_px1ActionPerformed
        if (px1.isSelected()) {
            px2.setSelected(false);
        }
    }//GEN-LAST:event_px1ActionPerformed

    private void px2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_px2ActionPerformed
        if (px2.isSelected()) {
            px1.setSelected(false);
        }
    }//GEN-LAST:event_px2ActionPerformed

    private void jLabel180MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel180MouseClicked
        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        DefaultTableModel mod = (DefaultTableModel) px_table.getModel();
        mod.setRowCount(0);
        java.util.Date date = new java.util.Date();
        long t = date.getTime();
        String op = "buying";
        if (px4.isSelected()) {
            if (px1.isSelected() && !px2.isSelected()) {
                String query = "SELECT base_currency,short_code FROM currencies where base_currency='" + true + "'";
                try {
                    Class.forName(DB_DRIVER);
                    con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                    stm = con.createStatement();
                    rs = stm.executeQuery(query);
                    boolean bool = rs.isBeforeFirst();
                    while (rs.next()) {
                        if (bool == true) {
                            String a = rs.getString("short_code");
                            try {
                                Class.forName(DB_DRIVER);
                                java.sql.Connection conn = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                String query1 = "SELECT base_currency,quote_currency,region,branch,exchange_rate,amount_traded_in,amount_traded_out,date,cashier FROM transactions_history where region='" + px5.getSelectedItem().toString() + "' and branch='" + px6.getSelectedItem().toString() + "' and operation='" + op + "' and quote_currency!='" + a + "' and base_currency='" + a + "'";

                                java.sql.Statement st = conn.createStatement();

                                // execute the query, and get a java resultset
                                ResultSet rsq = st.executeQuery(query1);

                                // iterate through the java resultset
                                while (rsq.next()) {
                                    String s1 = rsq.getString("quote_currency");
                                    String s2 = rsq.getString("base_currency");
                                    double s3 = rsq.getDouble("amount_traded_in");
                                    double s4 = rsq.getDouble("exchange_rate");
                                    double s5 = rsq.getDouble("amount_traded_out");
                                    String s6 = rsq.getString("region");
                                    String s7 = rsq.getString("branch");
                                    java.sql.Date sqlDate = rsq.getDate("date");
                                    String s8 = rsq.getString("cashier");
                                    String ht = sqlDate.toString();
                                    String dt = ht.substring(0, 4);
                                    int af = Integer.parseInt(px7.getValue().toString());
                                    int ag = Integer.parseInt(dt);
                                    if (af == ag) {
                                        mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                    }                         //int sum = 0;
                                }
                                boolean mb = rsq.isAfterLast();
                                if (mb == false) {
                                    JOptionPane.showMessageDialog(null, "There are no purchases recorded in the month of " + zh7.getValue() + "\n for " + zh6.getSelectedItem().toString() + " " + "purchasing foreign curency using local currency", "Transaction record not found", JOptionPane.WARNING_MESSAGE);
                                }

                            } catch (ClassNotFoundException | SQLException er) {
                                JOptionPane.showMessageDialog(null, er.getMessage());

                            }

                        }
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), " Something went wrong", JOptionPane.ERROR_MESSAGE);
                }

            }
            if (!px1.isSelected() && px2.isSelected()) {
                String query = "SELECT base_currency,short_code FROM currencies where base_currency='" + true + "'";
                try {
                    Class.forName(DB_DRIVER);
                    con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                    stm = con.createStatement();
                    rs = stm.executeQuery(query);
                    boolean bool = rs.isBeforeFirst();
                    while (rs.next()) {
                        if (bool == true) {
                            String a = rs.getString("short_code");
                            try {
                                Class.forName(DB_DRIVER);
                                java.sql.Connection conn = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                String query1 = "SELECT base_currency,quote_currency,region,branch,exchange_rate,amount_traded_in,amount_traded_out,date,cashier FROM transactions_history where region='" + px5.getSelectedItem().toString() + "' and branch='" + px6.getSelectedItem().toString() + "' and base_currency!='" + a + "' and quote_currency=!'" + a + "' and operation='" + op + "' ";

                                java.sql.Statement st = conn.createStatement();

                                // execute the query, and get a java resultset
                                ResultSet rsq = st.executeQuery(query1);

                                // iterate through the java resultset
                                while (rsq.next()) {
                                    String s1 = rsq.getString("quote_currency");
                                    String s2 = rsq.getString("base_currency");
                                    double s3 = rsq.getDouble("amount_traded_in");
                                    double s4 = rsq.getDouble("exchange_rate");
                                    double s5 = rsq.getDouble("amount_traded_out");
                                    String s6 = rsq.getString("region");
                                    String s7 = rsq.getString("branch");
                                    java.sql.Date sqlDate = rsq.getDate("date");
                                    String s8 = rsq.getString("cashier");
                                    String ht = sqlDate.toString();
                                    String dt = ht.substring(0, 4);
                                    int af = Integer.parseInt(px7.getValue().toString());
                                    int ag = Integer.parseInt(dt);
                                    if (af == ag) {
                                        mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                    }
                                }
                                boolean mb = rsq.isAfterLast();
                                if (mb == false) {
                                    JOptionPane.showMessageDialog(null, "There are no purchases recorded in the month of " + zh7.getValue() + "\n for " + zh6.getSelectedItem().toString() + " " + "purchasing foreign curency using local currency", "Transaction record not found", JOptionPane.WARNING_MESSAGE);
                                }

                            } catch (ClassNotFoundException | SQLException er) {
                                JOptionPane.showMessageDialog(null, er.getMessage());
                            }

                        }
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), " Something went wrong ", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        if (!px4.isSelected()) {
            if (px1.isSelected() && !px2.isSelected()) {
                String query = "SELECT base_currency,short_code FROM currencies where base_currency='" + true + "'";
                try {
                    Class.forName(DB_DRIVER);
                    con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                    stm = con.createStatement();
                    rs = stm.executeQuery(query);
                    boolean bool = rs.isBeforeFirst();
                    while (rs.next()) {
                        if (bool == true) {
                            String a = rs.getString("short_code");
                            try {
                                Class.forName(DB_DRIVER);
                                java.sql.Connection conn = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                String query1 = "SELECT base_currency,quote_currency,region,branch,exchange_rate,amount_traded_in,amount_traded_out,date,cashier FROM transactions_history where region='" + px5.getSelectedItem().toString() + "' and operation='" + op + "'  and quote_currency!='" + a + "' and base_currency='" + a + "'";

                                java.sql.Statement st = conn.createStatement();

                                // execute the query, and get a java resultset
                                ResultSet rsq = st.executeQuery(query1);
                                boolean swm = rsq.isBeforeFirst();
                                // iterate through the java resultset
                                while (rsq.next()) {
                                    String s1 = rsq.getString("quote_currency");
                                    String s2 = rsq.getString("base_currency");
                                    double s3 = rsq.getDouble("amount_traded_in");
                                    double s4 = rsq.getDouble("exchange_rate");
                                    double s5 = rsq.getDouble("amount_traded_out");
                                    String s6 = rsq.getString("region");
                                    String s7 = rsq.getString("branch");
                                    java.sql.Date sqlDate = rsq.getDate("date");
                                    String s8 = rsq.getString("cashier");
                                    String ht = sqlDate.toString();
                                    String dt = ht.substring(0, 4);
                                    int af = Integer.parseInt(px7.getValue().toString());
                                    int ag = Integer.parseInt(dt);
                                    if (af == ag) {
                                        mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                    }
                                }
                                boolean mb = rsq.isAfterLast();
                                if (mb == false) {
                                    JOptionPane.showMessageDialog(null, "There are no purchases recorded in the month of " + zh7.getValue() + "\n for " + zh6.getSelectedItem().toString() + " " + "purchasing foreign curency using local currency", "Transaction record not found", JOptionPane.WARNING_MESSAGE);
                                }

                            } catch (ClassNotFoundException | SQLException er) {
                                JOptionPane.showMessageDialog(null, er.getMessage());
                            }

                        }
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), " Something went wrong, please contact your database administrator or the vendor", JOptionPane.ERROR_MESSAGE);
                }

            }
            if (!px1.isSelected() && px2.isSelected()) {
                String query = "SELECT base_currency,short_code FROM currencies where base_currency='" + true + "'";
                try {
                    Class.forName(DB_DRIVER);
                    con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                    stm = con.createStatement();
                    rs = stm.executeQuery(query);
                    boolean bool = rs.isBeforeFirst();
                    while (rs.next()) {
                        if (bool == true) {
                            String a = rs.getString("short_code");
                            try {
                                Class.forName(DB_DRIVER);
                                java.sql.Connection conn = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                String query1 = "SELECT base_currency,quote_currency,region,branch,exchange_rate,amount_traded_in,amount_traded_out,date,cashier FROM transactions_history where region='" + px5.getSelectedItem().toString() + "' and operation='" + op + "' and quote_currency!='" + a + "' and base_currency!='" + a + "' ";

                                java.sql.Statement st = conn.createStatement();

                                // execute the query, and get a java resultset
                                ResultSet rsq = st.executeQuery(query1);

                                // iterate through the java resultset
                                while (rsq.next()) {
                                    String s1 = rsq.getString("quote_currency");
                                    String s2 = rsq.getString("base_currency");
                                    double s3 = rsq.getDouble("amount_traded_in");
                                    double s4 = rsq.getDouble("exchange_rate");
                                    double s5 = rsq.getDouble("amount_traded_out");
                                    String s6 = rsq.getString("region");
                                    String s7 = rsq.getString("branch");
                                    java.sql.Date sqlDate = rsq.getDate("date");
                                    String s8 = rsq.getString("cashier");
                                    String ht = sqlDate.toString();
                                    String dt = ht.substring(0, 4);
                                    int af = Integer.parseInt(px7.getValue().toString());
                                    int ag = Integer.parseInt(dt);
                                    if (af == ag) {
                                        mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                    }

                                }
                                boolean mb = rsq.isAfterLast();
                                if (mb == false) {
                                    JOptionPane.showMessageDialog(null, "There are no purchases recorded in the month of " + zh7.getValue() + "\n for " + zh6.getSelectedItem().toString() + " " + "purchasing foreign curency using local currency", "Transaction record not found", JOptionPane.WARNING_MESSAGE);
                                }

                            } catch (ClassNotFoundException | SQLException er) {
                                JOptionPane.showMessageDialog(null, er.getMessage());
                            }

                        }
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), " Something went wrong", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_jLabel180MouseClicked

    private void px3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_px3ActionPerformed

    }//GEN-LAST:event_px3ActionPerformed

    private void px4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_px4ActionPerformed
        DefaultComboBoxModel dm = new DefaultComboBoxModel();
        if (px4.isSelected()) {
            px6.setEnabled(true);
        }
        if (!px4.isSelected()) {
            px6.setModel(dm);

            px6.setEnabled(false);
        }
    }//GEN-LAST:event_px4ActionPerformed

    private void dv3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dv3KeyTyped
        char c = evt.getKeyChar();
        if (!Character.isDigit(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_dv3KeyTyped

    private void dv4KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dv4KeyTyped
        char c = evt.getKeyChar();
        if (!Character.isDigit(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_dv4KeyTyped

    private void sv3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sv3KeyTyped
        char c = evt.getKeyChar();
        if (!Character.isDigit(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_sv3KeyTyped

    private void sv4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sv4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sv4ActionPerformed

    private void sv4KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sv4KeyTyped
        char c = evt.getKeyChar();
        if (!Character.isDigit(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_sv4KeyTyped

    private void jLabel69MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel69MouseClicked
        java.util.Date date = new java.util.Date();
        long t = date.getTime();
        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(t);
        java.sql.Date sqlDate = new java.sql.Date(t);

        if (dv3.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter the buying rate for " + dv1.getText() + " " + "against " + dv2.getText(), "Some infomation missing", JOptionPane.WARNING_MESSAGE);
        }
        if (dv4.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter the selling rate for " + dv1.getText() + " " + "against " + dv2.getText(), "Some infomation missing", JOptionPane.WARNING_MESSAGE);
        }
        if (sv3.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter the buying rate for " + sv1.getText() + " " + "against " + sv2.getText(), "Some infomation missing", JOptionPane.WARNING_MESSAGE);
        }
        if (sv4.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter the buying rate for " + sv3.getText() + " " + "against " + sv4.getText(), "Some infomation missing", JOptionPane.WARNING_MESSAGE);
        }
        if (!sv4.getText().isEmpty() && !sv3.getText().isEmpty() && !dv4.getText().isEmpty() && !dv3.getText().isEmpty()) {
            double a = Double.parseDouble(dv3.getText());
            double a1 = Math.round(a * 10) / 10.0;

            double b = Double.parseDouble(sv3.getText());
            double b1 = Math.round(b * 10) / 10.0;
            double ab = b / a;
            double new_buying = Math.round(ab * 10) / 10.0;

            double c = Double.parseDouble(dv4.getText());
            double c1 = Math.round(c * 10) / 10.0;
            double d = Double.parseDouble(sv4.getText());
            double d1 = Math.round(a * 10) / 10.0;
            double cd = d / c;
            double new_selling = Math.round(cd * 10) / 10.0;
            if (new_buying > new_selling) {
                String query = "SELECT base_currency,quote_currency,buying_rate,selling_rate FROM config_rates ";
                try {
                    Class.forName(DB_DRIVER);
                    con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                    stm = con.createStatement();
                    rs = stm.executeQuery(query);
                    boolean bool = rs.isBeforeFirst();
                    while (rs.next()) {
                        if (bool == true) {
                            String zg = rs.getString("base_currency");
                            String zg1 = rs.getString("quote_currency");
                            if (dv1.getText().matches(zg) && dv2.getText().matches(zg1)) {
                                SQLRun run = new SQLRun();

                                String sqlq = "Update config_rates set buying_rate='" + a1 + "', selling_rate='" + b1 + "' where  base_currency='" + dv1.getText() + "' and  quote_currency='" + dv2.getText() + "'";
                                int updated = run.sqlUpdate(sqlq);
                                if (updated > 0) {
                                    ru5.setText(new_selling + "");
                                    ru6.setText(new_buying + "");

                                    simult.setVisible(false);
                                    rates.setVisible(true);
                                    ru5.setEnabled(false);
                                    ru6.setEnabled(false);
                                    dv1.setText("");
                                    dv2.setText("");
                                    dv3.setText("");
                                    dv4.setText("");

                                    sv1.setText("");
                                    sv2.setText("");
                                    sv3.setText("");
                                    sv4.setText("");
                                }
                            }
                            if (sv1.getText().matches(zg) && sv2.getText().matches(zg1)) {
                                SQLRun run = new SQLRun();

                                String sqlq = "Update config_rates set buying_rate='" + c1 + "', selling_rate='" + d1 + "' where  base_currency='" + sv1.getText() + "' and  quote_currency='" + sv2.getText() + "'";
                                int updated = run.sqlUpdate(sqlq);
                                if (updated > 0) {
                                    ru5.setText(new_selling + "");
                                    ru6.setText(new_buying + "");

                                    simult.setVisible(false);
                                    rates.setVisible(true);
                                    ru5.setEnabled(false);
                                    ru6.setEnabled(false);
                                    dv1.setText("");
                                    dv2.setText("");
                                    dv3.setText("");
                                    dv4.setText("");

                                    sv1.setText("");
                                    sv2.setText("");
                                    sv3.setText("");
                                    sv4.setText("");
                                }
                            }
                            if (dv2.getText().matches(zg) && sv2.getText().matches(zg1)) {
                                SQLRun run = new SQLRun();

                                String sqlq = "Update config_rates set buying_rate='" + new_selling + "', selling_rate='" + new_buying + "' where  base_currency='" + dv2.getText() + "' and  quote_currency='" + sv2.getText() + "'";
                                int updated = run.sqlUpdate(sqlq);
                                if (updated > 0) {
                                    ru5.setText(new_selling + "");
                                    ru6.setText(new_buying + "");

                                    simult.setVisible(false);
                                    rates.setVisible(true);
                                    ru5.setEnabled(false);
                                    ru6.setEnabled(false);
                                    dv1.setText("");
                                    dv2.setText("");
                                    dv3.setText("");
                                    dv4.setText("");

                                    sv1.setText("");
                                    sv2.setText("");
                                    sv3.setText("");
                                    sv4.setText("");
                                }

                            }
                        }
                    }
                } catch (ClassNotFoundException | SQLException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
                }

            }
            if (new_buying < new_selling) {
                String query = "SELECT base_currency,quote_currency,buying_rate,selling_rate FROM config_rates ";
                try {
                    Class.forName(DB_DRIVER);
                    con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                    stm = con.createStatement();
                    rs = stm.executeQuery(query);
                    boolean bool = rs.isBeforeFirst();
                    while (rs.next()) {
                        if (bool == true) {
                            String zg = rs.getString("base_currency");
                            String zg1 = rs.getString("quote_currency");
                            if (dv1.getText().matches(zg) && dv2.getText().matches(zg1)) {
                                SQLRun run = new SQLRun();

                                String sqlq = "Update config_rates set buying_rate='" + a1 + "', selling_rate='" + b1 + "' where  base_currency='" + dv1.getText() + "' and  quote_currency='" + dv2.getText() + "'";
                                int updated = run.sqlUpdate(sqlq);
                                if (updated > 0) {
                                    ru6.setText(new_selling + "");
                                    ru5.setText(new_buying + "");
                                    simult.setVisible(false);
                                    rates.setVisible(true);
                                    ru5.setEnabled(false);
                                    ru6.setEnabled(false);
                                    dv1.setText("");
                                    dv2.setText("");
                                    dv3.setText("");
                                    dv4.setText("");

                                    sv1.setText("");
                                    sv2.setText("");
                                    sv3.setText("");
                                    sv4.setText("");
                                }
                            }
                            if (sv1.getText().matches(zg) && sv2.getText().matches(zg1)) {
                                SQLRun run = new SQLRun();

                                String sqlq = "Update config_rates set buying_rate='" + a1 + "', selling_rate='" + b1 + "' where  base_currency='" + dv1.getText() + "' and  quote_currency='" + dv2.getText() + "'";
                                int updated = run.sqlUpdate(sqlq);
                                if (updated > 0) {
                                    ru6.setText(new_selling + "");
                                    ru5.setText(new_buying + "");
                                    simult.setVisible(false);
                                    rates.setVisible(true);
                                    ru5.setEnabled(false);
                                    ru6.setEnabled(false);
                                    dv1.setText("");
                                    dv2.setText("");
                                    dv3.setText("");
                                    dv4.setText("");

                                    sv1.setText("");
                                    sv2.setText("");
                                    sv3.setText("");
                                    sv4.setText("");
                                }
                            }
                            if (dv2.getText().matches(zg) && sv2.getText().matches(zg1)) {
                                SQLRun run = new SQLRun();

                                String sqlq = "Update config_rates set buying_rate='" + new_buying + "', selling_rate='" + new_selling + "' where  base_currency='" + dv2.getText() + "' and  quote_currency='" + sv2.getText() + "'";
                                int updated = run.sqlUpdate(sqlq);
                                if (updated > 0) {
                                    ru6.setText(new_selling + "");
                                    ru5.setText(new_buying + "");
                                    simult.setVisible(false);
                                    rates.setVisible(true);
                                    ru5.setEnabled(false);
                                    ru6.setEnabled(false);
                                    dv1.setText("");
                                    dv2.setText("");
                                    dv3.setText("");
                                    dv4.setText("");

                                    sv1.setText("");
                                    sv2.setText("");
                                    sv3.setText("");
                                    sv4.setText("");
                                }
                            }
                        }
                    }
                } catch (ClassNotFoundException | SQLException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
                }

            }
        }

    }//GEN-LAST:event_jLabel69MouseClicked

    private void jLabel90MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel90MouseClicked
        simult.setVisible(false);
        rates.setVisible(true);
        ru5.setEnabled(false);
        ru6.setEnabled(false);
        dv1.setText("");
        dv2.setText("");
        dv3.setText("");
        dv4.setText("");

        sv1.setText("");
        sv2.setText("");
        sv3.setText("");
        sv4.setText("");
    }//GEN-LAST:event_jLabel90MouseClicked

    private void ntf1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ntf1ActionPerformed
        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        String query = "SELECT subject,notification_message,recipients,sender,date FROM notifications where subject='" + ntf1.getSelectedItem().toString() + "' ";
        try {
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
            stm = con.createStatement();

            rs = stm.executeQuery(query);
            boolean bool = rs.isBeforeFirst();
            while (rs.next()) {
                if (bool == true) {
                    String a = rs.getString("sender");
                    String b = rs.getString("notification_message");
                    String c = rs.getString("date");
                    ntf2.setText(a);
                    ntf3.setText(c);
                    ntf4.setText(b);

                }
            }

        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Something went wrong", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_ntf1ActionPerformed

    private void table_resetKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_table_resetKeyPressed
        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        if (table_reset.getRowCount() < 1) {

        }
        if (table_reset.getRowCount() >= 1) {
            int a = Integer.parseInt(wtq.getText(), 10);
            String b = "Bureau@2020";
            int input = JOptionPane.showConfirmDialog(null, " Are you sure that you want to reset the password of the selected user", "Providence says....", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (input == JOptionPane.NO_OPTION) {

            }
            if (input == JOptionPane.YES_OPTION) {
                SQLRun run = new SQLRun();
                String sqlq = "Update users set password='" + b + "'   where user_id='" + a + "'";
                int updated = run.sqlUpdate(sqlq);
                if (updated > 0) {
                    JOptionPane.showMessageDialog(null, "You have successfully resetted the password\nYour default password is " + b + " " + "and it should be changed within 48 hours", "User Password Update Notification", JOptionPane.INFORMATION_MESSAGE);
                    wtq.setText("");

                }

            }

        }
    }//GEN-LAST:event_table_resetKeyPressed

    private void table_resetMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_resetMouseClicked
        DefaultTableModel model = (DefaultTableModel) table_reset.getModel();
        wtq.setText(model.getValueAt(table_reset.getSelectedRow(), 4).toString());

    }//GEN-LAST:event_table_resetMouseClicked

    private void prf2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prf2ActionPerformed
        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        if (prf2.getSelectedItem().equals(" ")) {
            //JOptionPane.showMessageDialog(null, "Please select a currency","Providence says",JOptionPane.WARNING_MESSAGE);  
        }
        if (!prf2.getSelectedItem().equals(" ")) {
            String sf = prf2.getSelectedItem().toString();
            DefaultComboBoxModel dm = new DefaultComboBoxModel();
            prf5.setModel(dm);

            String query = "SELECT short_code FROM currencies where short_code!='" + sf + "'";
            try {
                Class.forName(DB_DRIVER);
                con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                stm = con.createStatement();
                rs = stm.executeQuery(query);
                boolean bool = rs.isBeforeFirst();
                while (rs.next()) {
                    if (bool == true) {
                        String a = rs.getString("short_code");
                        prf5.addItem(" ");
                        prf5.addItem(a);

                    }
                }
            } catch (ClassNotFoundException | SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_prf2ActionPerformed

    private void prf5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prf5ActionPerformed
        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        DefaultTableModel mod = (DefaultTableModel) prof_table.getModel();
        DefaultTableModel mod1 = (DefaultTableModel) prof_table1.getModel();
        try {
            String myDriver = "org.postgresql.Driver";
            String myUrl = "jdbc:postgresql:" + configs.getText();

            Class.forName(DB_DRIVER);
            java.sql.Connection conn = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
            String query1 = "SELECT base_currency,quote_currency,region,branch,operation,exchange_rate,amount_traded_in,amount_traded_out,date,cashier FROM transactions_history where  base_currency='" + prf5.getSelectedItem().toString() + "' and quote_currency='" + prf2.getSelectedItem().toString() + "' ";

            java.sql.Statement st = conn.createStatement();

            // execute the query, and get a java resultset
            ResultSet rsq = st.executeQuery(query1);

            // iterate through the java resultset
            while (rsq.next()) {
                String s1 = rsq.getString("base_currency");
                String s2 = rsq.getString("quote_currency");
                String s3 = rsq.getString("region");
                String s4 = rsq.getString("branch");
                double s5 = rsq.getDouble("exchange_rate");
                double s6 = rsq.getDouble("amount_traded_out");
                double s7 = rsq.getDouble("amount_traded_in");
                java.sql.Date sqlDate = rsq.getDate("date");
                String s8 = rsq.getString("operation");
                double sum = 0;

                String wq = sqlDate.toString();
                String qw = wq.substring(0, 4);
                int af = Integer.parseInt(pfr1.getValue().toString());
                int ag = Integer.parseInt(qw);

                String s9 = "buying";
                String s10 = "selling";

                if (prf1.getSelectedItem().equals("September") && af == ag && s8.equals(s9)) {
                    String ht = sqlDate.toString();
                    String dt = ht.substring(5, 7);
                    String v1 = "09";

                    if (dt.matches(v1)) {
                        mod.addRow(new Object[]{s7, s5});
                        for (int i = 0; i < prof_table.getRowCount(); i++) {
                            sum = sum + Double.parseDouble(prof_table.getValueAt(i, 0).toString());
                            prf9.setText(Double.toString(sum));
                        }
                    }
                }

                if (prf1.getSelectedItem().equals("January") && af == ag && s8.equals(s9)) {
                    String ht = sqlDate.toString();
                    String dt = ht.substring(5, 7);
                    String v1 = "01";
                    if (dt.matches(v1)) {
                        mod.addRow(new Object[]{s7, s5});
                        for (int i = 0; i < prof_table.getRowCount(); i++) {
                            sum = sum + Double.parseDouble(prof_table.getValueAt(i, 0).toString());
                            prf9.setText(Double.toString(sum));
                        }
                    }
                }
                if (prf1.getSelectedItem().equals("February") && af == ag && s8.matches(s9)) {
                    String ht = sqlDate.toString();
                    String dt = ht.substring(5, 7);
                    String v1 = "02";
                    if (dt.matches(v1)) {
                        mod.addRow(new Object[]{s7, s5});
                        for (int i = 0; i < prof_table.getRowCount(); i++) {
                            sum = sum + Double.parseDouble(prof_table.getValueAt(i, 0).toString());
                            prf11.setText(Double.toString(sum));
                        }
                    }
                }
                if (prf1.getSelectedItem().equals("March") && af == ag && s8.equals(s9)) {
                    String ht = sqlDate.toString();
                    String dt = ht.substring(5, 7);
                    String v1 = "03";
                    if (dt.matches(v1)) {
                        mod.addRow(new Object[]{s7, s5});
                        for (int i = 0; i < prof_table.getRowCount(); i++) {
                            sum = sum + Double.parseDouble(prof_table.getValueAt(i, 0).toString());
                            prf11.setText(Double.toString(sum));
                        }
                    }
                }
                if (prf1.getSelectedItem().equals("April") && af == ag && s8.equals(s9)) {
                    String ht = sqlDate.toString();
                    String dt = ht.substring(5, 7);
                    String v1 = "04";
                    if (dt.matches(v1)) {
                        mod.addRow(new Object[]{s7, s5});
                        for (int i = 0; i < prof_table.getRowCount(); i++) {
                            sum = sum + Double.parseDouble(prof_table.getValueAt(i, 0).toString());
                            prf11.setText(Double.toString(sum));
                        }
                    }
                }
                if (prf1.getSelectedItem().equals("May") && af == ag && s8.equals(s9)) {
                    String ht = sqlDate.toString();
                    String dt = ht.substring(5, 7);
                    String v1 = "05";
                    if (dt.matches(v1)) {
                        mod.addRow(new Object[]{s7, s5});
                        for (int i = 0; i < prof_table.getRowCount(); i++) {
                            sum = sum + Double.parseDouble(prof_table.getValueAt(i, 0).toString());
                            prf11.setText(Double.toString(sum));
                        }
                    }
                }
                if (prf1.getSelectedItem().equals("June") && af == ag && s8.equals(s9)) {
                    String ht = sqlDate.toString();
                    String dt = ht.substring(5, 7);
                    String v1 = "06";
                    if (dt.matches(v1)) {
                        mod.addRow(new Object[]{s7, s5});
                        for (int i = 0; i < prof_table.getRowCount(); i++) {
                            sum = sum + Double.parseDouble(prof_table.getValueAt(i, 0).toString());
                            prf11.setText(Double.toString(sum));
                        }
                    }
                }
                if (prf1.getSelectedItem().equals("July") && af == ag && s8.equals(s9)) {
                    String ht = sqlDate.toString();
                    String dt = ht.substring(5, 7);
                    String v1 = "07";
                    if (dt.matches(v1)) {
                        mod.addRow(new Object[]{s7, s5});
                        for (int i = 0; i < prof_table.getRowCount(); i++) {
                            sum = sum + Double.parseDouble(prof_table.getValueAt(i, 0).toString());
                            prf11.setText(Double.toString(sum));
                        }
                    }
                }
                if (prf1.getSelectedItem().equals("August") && af == ag && s8.equals(s9)) {
                    String ht = sqlDate.toString();
                    String dt = ht.substring(5, 7);
                    String v1 = "08";
                    if (dt.matches(v1)) {
                        mod.addRow(new Object[]{s7});
                        for (int i = 0; i < prof_table.getRowCount(); i++) {
                            sum = sum + Double.parseDouble(prof_table.getValueAt(i, 0).toString());
                            prf11.setText(Double.toString(sum));
                        }
                    }
                }
                if (prf1.getSelectedItem().equals("October") && af == ag && s8.equals(s9)) {
                    String ht = sqlDate.toString();
                    String dt = ht.substring(5, 7);
                    String v1 = "10";
                    if (dt.matches(v1)) {
                        mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                        mod.addRow(new Object[]{s7, s5});
                        for (int i = 0; i < prof_table.getRowCount(); i++) {
                            sum = sum + Double.parseDouble(prof_table.getValueAt(i, 0).toString());
                            prf11.setText(Double.toString(sum));
                        }
                    }
                }
                if (prf1.getSelectedItem().equals("November") && af == ag && s8.equals(s9)) {
                    String ht = sqlDate.toString();
                    String dt = ht.substring(5, 7);
                    String v1 = "11";
                    if (dt.matches(v1)) {
                        mod.addRow(new Object[]{s7, s5});
                        mod.addRow(new Object[]{s7});
                        for (int i = 0; i < prof_table.getRowCount(); i++) {
                            sum = sum + Double.parseDouble(prof_table.getValueAt(i, 0).toString());
                            prf11.setText(Double.toString(sum));
                        }
                    }
                }
                if (prf1.getSelectedItem().equals("December") && af == ag && s8.equals(s9)) {
                    String ht = sqlDate.toString();
                    String dt = ht.substring(5, 7);
                    String v1 = "12";
                    if (dt.matches(v1)) {
                        mod.addRow(new Object[]{s7, s5});
                        for (int i = 0; i < prof_table.getRowCount(); i++) {
                            sum = sum + Double.parseDouble(prof_table.getValueAt(i, 0).toString());
                            prf11.setText(Double.toString(sum));
                        }
                    }
                }

                //int sum = 0;
                if (prf1.getSelectedItem().equals("September") && af == ag && s8.equals(s10)) {
                    String ht = sqlDate.toString();
                    String dt = ht.substring(5, 7);
                    String v1 = "09";

                    if (dt.matches(v1)) {
                        mod1.addRow(new Object[]{s7, s5});
                        for (int i = 0; i < prof_table1.getRowCount(); i++) {
                            sum = sum + Double.parseDouble(prof_table1.getValueAt(i, 0).toString());
                            prf9.setText(Double.toString(sum));
                        }
                    }
                }

                if (prf1.getSelectedItem().equals("January") && af == ag && s8.equals(s10)) {
                    String ht = sqlDate.toString();
                    String dt = ht.substring(5, 7);
                    String v1 = "01";
                    if (dt.matches(v1)) {
                        mod1.addRow(new Object[]{s7, s5});
                        for (int i = 0; i < prof_table1.getRowCount(); i++) {
                            sum = sum + Double.parseDouble(prof_table1.getValueAt(i, 0).toString());
                            prf9.setText(Double.toString(sum));
                        }
                    }
                }
                if (prf1.getSelectedItem().equals("February") && af == ag && s8.matches(s10)) {
                    String ht = sqlDate.toString();
                    String dt = ht.substring(5, 7);
                    String v1 = "02";
                    if (dt.matches(v1)) {
                        mod1.addRow(new Object[]{s7, s5});
                        for (int i = 0; i < prof_table1.getRowCount(); i++) {
                            sum = sum + Double.parseDouble(prof_table1.getValueAt(i, 0).toString());
                            prf9.setText(Double.toString(sum));
                        }
                    }
                }
                if (prf1.getSelectedItem().equals("March") && af == ag && s8.equals(s10)) {
                    String ht = sqlDate.toString();
                    String dt = ht.substring(5, 7);
                    String v1 = "03";
                    if (dt.matches(v1)) {
                        mod1.addRow(new Object[]{s7, s5});
                        for (int i = 0; i < prof_table1.getRowCount(); i++) {
                            sum = sum + Double.parseDouble(prof_table1.getValueAt(i, 0).toString());
                            prf9.setText(Double.toString(sum));
                        }
                    }
                }
                if (prf1.getSelectedItem().equals("April") && af == ag && s8.equals(s10)) {
                    String ht = sqlDate.toString();
                    String dt = ht.substring(5, 7);
                    String v1 = "04";
                    if (dt.matches(v1)) {
                        mod1.addRow(new Object[]{s7, s5});
                        for (int i = 0; i < prof_table1.getRowCount(); i++) {
                            sum = sum + Double.parseDouble(prof_table1.getValueAt(i, 0).toString());
                            prf9.setText(Double.toString(sum));
                        }
                    }
                }
                if (prf1.getSelectedItem().equals("May") && af == ag && s8.equals(s10)) {
                    String ht = sqlDate.toString();
                    String dt = ht.substring(5, 7);
                    String v1 = "05";
                    if (dt.matches(v1)) {
                        mod1.addRow(new Object[]{s7, s5});
                        for (int i = 0; i < prof_table1.getRowCount(); i++) {
                            sum = sum + Double.parseDouble(prof_table1.getValueAt(i, 0).toString());
                            prf9.setText(Double.toString(sum));
                        }
                    }
                }
                if (prf1.getSelectedItem().equals("June") && af == ag && s8.equals(s10)) {
                    String ht = sqlDate.toString();
                    String dt = ht.substring(5, 7);
                    String v1 = "06";
                    if (dt.matches(v1)) {
                        mod1.addRow(new Object[]{s7, s5});
                        for (int i = 0; i < prof_table1.getRowCount(); i++) {
                            sum = sum + Double.parseDouble(prof_table1.getValueAt(i, 0).toString());
                            prf9.setText(Double.toString(sum));
                        }
                    }
                }
                if (prf1.getSelectedItem().equals("July") && af == ag && s8.equals(s10)) {
                    String ht = sqlDate.toString();
                    String dt = ht.substring(5, 7);
                    String v1 = "07";
                    if (dt.matches(v1)) {
                        mod1.addRow(new Object[]{s7, s5});
                        for (int i = 0; i < prof_table1.getRowCount(); i++) {
                            sum = sum + Double.parseDouble(prof_table1.getValueAt(i, 0).toString());
                            prf9.setText(Double.toString(sum));
                        }
                    }
                }
                if (prf1.getSelectedItem().equals("August") && af == ag && s8.equals(s10)) {
                    String ht = sqlDate.toString();
                    String dt = ht.substring(5, 7);
                    String v1 = "08";
                    if (dt.matches(v1)) {
                        mod1.addRow(new Object[]{s7, s5});
                        for (int i = 0; i < prof_table1.getRowCount(); i++) {
                            sum = sum + Double.parseDouble(prof_table1.getValueAt(i, 0).toString());
                            prf9.setText(Double.toString(sum));
                        }
                    }
                }
                if (prf1.getSelectedItem().equals("October") && af == ag && s8.equals(s10)) {
                    String ht = sqlDate.toString();
                    String dt = ht.substring(5, 7);
                    String v1 = "10";
                    if (dt.matches(v1)) {
                        mod1.addRow(new Object[]{s7, s5});
                        for (int i = 0; i < prof_table1.getRowCount(); i++) {
                            sum = sum + Double.parseDouble(prof_table1.getValueAt(i, 0).toString());
                            prf9.setText(Double.toString(sum));
                        }
                    }
                }
                if (prf1.getSelectedItem().equals("November") && af == ag && s8.equals(s10)) {
                    String ht = sqlDate.toString();
                    String dt = ht.substring(5, 7);
                    String v1 = "11";
                    if (dt.matches(v1)) {
                        mod1.addRow(new Object[]{s7, s5});
                        mod.addRow(new Object[]{s7});
                        for (int i = 0; i < prof_table1.getRowCount(); i++) {
                            sum = sum + Double.parseDouble(prof_table1.getValueAt(i, 0).toString());
                            prf9.setText(Double.toString(sum));
                        }
                    }
                }
                if (prf1.getSelectedItem().equals("December") && af == ag && s8.equals(s10)) {
                    String ht = sqlDate.toString();
                    String dt = ht.substring(5, 7);
                    String v1 = "12";
                    if (dt.matches(v1)) {
                        mod1.addRow(new Object[]{s7, s5});
                        for (int i = 0; i < prof_table1.getRowCount(); i++) {
                            sum = sum + Double.parseDouble(prof_table1.getValueAt(i, 0).toString());
                            prf9.setText(Double.toString(sum));
                        }
                    }
                }

            }

        } catch (ClassNotFoundException | SQLException er) {
            JOptionPane.showMessageDialog(null, er.getMessage());

        }

    }//GEN-LAST:event_prf5ActionPerformed

    private void prf6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prf6ActionPerformed
        if (prf6.isSelected()) {
            prf7.setSelected(false);
            prf8.setSelected(false);
            if (prf9.getText().isEmpty() && !prf11.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "There are sales recorded ", "Cost Evaluation Failed", JOptionPane.WARNING_MESSAGE);
            }
            if (!prf9.getText().isEmpty() && prf11.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "There are purchases recorded ", "Cost Evaluation Failed", JOptionPane.WARNING_MESSAGE);
            }
            if (prf9.getText().isEmpty() && prf11.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "There are sales & purchases recorded ", "Cost Evaluation Failed", JOptionPane.WARNING_MESSAGE);
            }
            if (!prf9.getText().isEmpty() && !prf11.getText().isEmpty()) {
                float sum = 0;
                float mus = 0;
                int i = prof_table.getRowCount();
                int ip = i - 1;

                int h = prof_table1.getRowCount();
                int hp = h - 1;

                sum = sum + Float.parseFloat(prof_table.getValueAt(ip, 1).toString());
                float awm = prof_table.getRowCount();
                float sum1 = sum / awm;
                prf12.setText(Double.toString(sum1));
                float a = Float.parseFloat(prf9.getText());
                float b = Float.parseFloat(prf11.getText());
                float c = a - b;
                if (a > b) {
                    prf15.setText(c + "");
                    prf16.setText("0");
                }
                if (b > a) {
                    float d = c * -1;
                    prf16.setText(d + "");
                    prf15.setText("0");

                }

                mus = mus + Float.parseFloat(prof_table1.getValueAt(hp, 1).toString());
                float awm1 = prof_table1.getRowCount();
                float sum2 = mus / awm1;
                prf10.setText(Double.toString(sum2));
                float ab = Float.parseFloat(prf9.getText());
                float bb = Float.parseFloat(prf11.getText());
                float cb = a - b;
                if (ab > bb) {
                    prf15.setText(cb + "");
                    prf16.setText("0");
                }
                if (bb > ab) {
                    float d = cb * -1;
                    prf16.setText(d + "");
                    prf15.setText("0");
                }
            }

        }
        if (!prf6.isSelected()) {
            prf7.setSelected(false);
            prf8.setSelected(false);
        }
    }//GEN-LAST:event_prf6ActionPerformed

    private void prf7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prf7ActionPerformed
        if (prf7.isSelected()) {
            prf6.setSelected(false);
            prf8.setSelected(false);

            if (prf9.getText().isEmpty() && !prf11.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "There are sales recorded ", "Cost Evaluation Failed", JOptionPane.WARNING_MESSAGE);
            }
            if (!prf9.getText().isEmpty() && prf11.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "There are purchases recorded ", "Cost Evaluation Failed", JOptionPane.WARNING_MESSAGE);
            }
            if (prf9.getText().isEmpty() && prf11.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "There are sales & purchases recorded ", "Cost Evaluation Failed", JOptionPane.WARNING_MESSAGE);
            }
            if (prf9.getText().isEmpty() && prf11.getText().isEmpty()) {
                float sum = 0;
                float mus = 0;
                for (int i = 0; i < prof_table.getRowCount(); i++) {
                    sum = sum + Float.parseFloat(prof_table.getValueAt(i, 1).toString());
                    float awm = prof_table.getRowCount();
                    float sum1 = sum / awm;
                    prf12.setText(Double.toString(sum1));
                    float a = Float.parseFloat(prf9.getText());
                    float b = Float.parseFloat(prf11.getText());
                    float c = a - b;
                    prf15.setText(c + "");
                    prf16.setText("0");
                }
                for (int i = 0; i < prof_table1.getRowCount(); i++) {
                    mus = mus + Float.parseFloat(prof_table1.getValueAt(i, 1).toString());
                    float awm = prof_table1.getRowCount();
                    float sum1 = mus / awm;
                    prf10.setText(Double.toString(sum1));
                    float a = Float.parseFloat(prf9.getText());
                    float b = Float.parseFloat(prf11.getText());
                    float c = a - b;
                    prf15.setText(c + "");
                    prf16.setText("0");
                }
            }

        }
        if (!prf7.isSelected()) {
            prf6.setSelected(false);
            prf8.setSelected(false);

        }
    }//GEN-LAST:event_prf7ActionPerformed

    private void prf8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prf8ActionPerformed
        if (prf8.isSelected()) {
            prf7.setSelected(false);
            prf6.setSelected(false);
            if (prf9.getText().isEmpty() && !prf11.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "There are sales recorded ", "Cost Evaluation Failed", JOptionPane.WARNING_MESSAGE);
            }
            if (!prf9.getText().isEmpty() && prf11.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "There are purchases recorded ", "Cost Evaluation Failed", JOptionPane.WARNING_MESSAGE);
            }
            if (prf9.getText().isEmpty() && prf11.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "There are sales & purchases recorded ", "Cost Evaluation Failed", JOptionPane.WARNING_MESSAGE);
            }
            if (!prf9.getText().isEmpty() && !prf11.getText().isEmpty()) {
                float sum = 0;
                float mus = 0;
                int i = prof_table.getRowCount();
                int ip = i - 1;

                int h = prof_table1.getRowCount();
                int hp = h - 1;

                sum = sum + Float.parseFloat(prof_table.getValueAt(0, 1).toString());
                float awm = prof_table.getRowCount();
                float sum1 = sum / awm;
                prf12.setText(Double.toString(sum1));
                float a = Float.parseFloat(prf9.getText());
                float b = Float.parseFloat(prf11.getText());
                float c = a - b;
                if (a > b) {
                    prf15.setText(c + "");
                    prf16.setText("0");
                }
                if (b > a) {
                    float d = c * -1;
                    prf16.setText(d + "");
                    prf15.setText("0");
                }

                mus = mus + Float.parseFloat(prof_table1.getValueAt(0, 1).toString());
                float awm1 = prof_table1.getRowCount();
                float sum2 = mus / awm1;
                prf10.setText(Double.toString(sum2));
                float ab = Float.parseFloat(prf9.getText());
                float bb = Float.parseFloat(prf11.getText());
                float cb = a - b;
                if (ab > bb) {
                    prf15.setText(cb + "");
                    prf16.setText("0");
                }
                if (bb > ab) {
                    float d = cb * -1;
                    prf16.setText(d + "");
                    prf15.setText("0");
                }
            }

        }
        if (!prf8.isSelected()) {
            prf7.setSelected(false);
            prf6.setSelected(false);

        }
    }//GEN-LAST:event_prf8ActionPerformed

    private void fa5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fa5ActionPerformed
        DefaultComboBoxModel dm = new DefaultComboBoxModel();
        fa6.setModel(dm);
        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        String query1 = "SELECT region_name,branch_name,area_code,description,created_by,branch_id FROM branches where region_name='" + fa5.getSelectedItem().toString() + "'";
        try {
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
            stm = con.createStatement();

            rs = stm.executeQuery(query1);
            boolean boolm = rs.isBeforeFirst();
            while (rs.next()) {
                if (boolm == true) {
                    String a = rs.getString("branch_name");
                    fa6.addItem(" ");
                    fa6.addItem(a);

                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_fa5ActionPerformed

    private void rt5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rt5KeyPressed

    }//GEN-LAST:event_rt5KeyPressed

    private void rt6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rt6KeyPressed
        java.util.Date date = new java.util.Date();
        long t = date.getTime();
        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(t);
        java.sql.Date sqlDate = new java.sql.Date(t);

        int key = evt.getKeyCode();
        if (key == 10) {
            if (rt5.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter the exchange rate eg 10 or 7.5", "Incomplete data ", JOptionPane.ERROR_MESSAGE);
            }

            if (!rt5.getText().isEmpty()) {
                if (rt5.getText().contains("$") && rt5.getText().contains("|")
                        && rt5.getText().contains("?") && rt5.getText().contains("/")
                        && rt5.getText().contains(">") && rt5.getText().contains("<")
                        && rt5.getText().contains(",") && rt5.getText().contains("\\")
                        && rt5.getText().contains(":") && rt5.getText().contains(";")
                        && rt5.getText().contains("'") && rt5.getText().contains("]")
                        && rt5.getText().contains("[") && rt5.getText().contains("}")
                        && rt5.getText().contains("{") && rt5.getText().contains("=")
                        && rt5.getText().contains("+") && rt5.getText().contains("-")
                        && rt5.getText().contains("_") && rt5.getText().contains(")")
                        && rt5.getText().contains("(") && rt5.getText().contains("*")
                        && rt5.getText().contains("&") && rt5.getText().contains("^")
                        && rt5.getText().contains("%") && rt5.getText().contains("#")
                        && rt5.getText().contains("@") && rt5.getText().contains("!")
                        && rt5.getText().contains("~")) {
                    rt5.setText("");
                    JOptionPane.showMessageDialog(null, "Please don't enter special character ", "Incorrect data", JOptionPane.ERROR_MESSAGE);
                }
                if (!rt5.getText().contains("$") && !rt5.getText().contains("|")
                        && !rt5.getText().contains("?") && !rt5.getText().contains("/")
                        && !rt5.getText().contains(">") && !rt5.getText().contains("<")
                        && !rt5.getText().contains(",") && !rt5.getText().contains("\\")
                        && !rt5.getText().contains(":") && !rt5.getText().contains(";")
                        && !rt5.getText().contains("'") && !rt5.getText().contains("]")
                        && !rt5.getText().contains("[") && !rt5.getText().contains("}")
                        && !rt5.getText().contains("{") && !rt5.getText().contains("=")
                        && !rt5.getText().contains("+") && !rt5.getText().contains("-")
                        && !rt5.getText().contains("_") && !rt5.getText().contains(")")
                        && !rt5.getText().contains("(") && !rt5.getText().contains("*")
                        && !rt5.getText().contains("&") && !rt5.getText().contains("^")
                        && !rt5.getText().contains("%") && !rt5.getText().contains("#")
                        && !rt5.getText().contains("@") && !rt5.getText().contains("!")
                        && !rt5.getText().contains("~")) {
                    double asd = Double.parseDouble(rt5.getText());
                    double asd1 = Double.parseDouble(rt6.getText());
                    String query = "SELECT base_currency,quote_currency,buying_rate,selling_rate FROM config_rates where base_currency='" + rt1.getText() + "' and quote_currency='" + rt2.getSelectedItem().toString() + "'";
                    try {
                        Class.forName(DB_DRIVER);
                        con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                        stm = con.createStatement();
                        rs = stm.executeQuery(query);
                        boolean bool = rs.isBeforeFirst();
                        while (rs.next()) {
                            if (bool == true) {
                                String a = rs.getString("base_currency");
                                String b = rs.getString("quote_currency");
                                boolean c = rs.getBoolean("buying_rate");
                                boolean d = rs.getBoolean("selling_rate");
                                boolean boom = false;

                                JOptionPane.showMessageDialog(rootPane, "Exchange rates already configured", "Duplication error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        if (bool == false) {
                            try {
                                Class.forName(DB_DRIVER);

                                con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                pst = con.prepareStatement("insert into config_rates values(?,?,?,?,?,?)");
                                pst.setString(1, rt1.getText());
                                pst.setString(2, rt2.getSelectedItem().toString());
                                pst.setDouble(3, asd);
                                pst.setDouble(4, asd1);
                                pst.setString(5, name.getText());
                                pst.setTimestamp(6, sqlTimestamp);

                                int i = pst.executeUpdate();
                                if (i > 0) {
                                    JOptionPane.showMessageDialog(null, "Exchange rates have been configured successfully", " Providence says....", JOptionPane.INFORMATION_MESSAGE);
                                    DefaultTableModel dm = (DefaultTableModel) rt_table.getModel();
                                    dm.addRow(new Object[]{rt1.getText(), rt2.getSelectedItem().toString(), asd, asd1, asd});

                                    //                                    Finance_Dep fd = new Finance_Dep();
                                    //                                    fd.setVisible(true);
                                    //                                    this.dispose();
                                }
                            } catch (SQLException ev) {
                                JOptionPane.showMessageDialog(null, ev.getMessage());

                            }
                        }

                    } catch (ClassNotFoundException | SQLException e) {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }//GEN-LAST:event_rt6KeyPressed

    private void ccr2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ccr2ActionPerformed
        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        if (ccr2.getSelectedItem().equals("")) {

        }
        String bools = "false";
        String query = "SELECT base_currency, short_code FROM currencies ";
        try {
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
            stm = con.createStatement();
            rs = stm.executeQuery(query);
            boolean bool = rs.isBeforeFirst();
            while (rs.next()) {
                if (bool == true) {
                    String a = rs.getString("short_code");
                    boolean a1 = rs.getBoolean("base_currency");

                    if (!ccr2.getSelectedItem().equals(a) && a1 == true) {
                        String querie = "SELECT base_currency,quote_currency,buying_rate,selling_rate FROM config_rates where quote_currency='" + ccr2.getSelectedItem().toString() + "' and base_currency='" + a + "' ";
                        try {
                            con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                            stm = con.createStatement();
                            rs = stm.executeQuery(querie);
                            boolean boom = rs.isBeforeFirst();
                            while (rs.next()) {
                                if (boom == true) {
                                    double b = rs.getFloat("buying_rate");
                                    double c = rs.getFloat("selling_rate");

                                    double rounded_buy = Math.round(b * 10) / 10.0;
                                    double rounded_sell = Math.round(c * 10) / 10.0;

                                    opt2.setText(rounded_buy + "");
                                    opt3.setText(rounded_sell + "");

                                    float buying = Float.parseFloat(jpy1.getText());
                                    float selling = Float.parseFloat(jpb1.getText());

                                    float buying1 = Float.parseFloat(opt2.getText());
                                    float selling1 = Float.parseFloat(opt3.getText());

                                    float new_buying = buying1 / buying;
                                    float new_selling = selling1 / selling;

                                    double rounded_buy1 = Math.round(new_buying * 10) / 10.0;
                                    double rounded_sell1 = Math.round(new_selling * 10) / 10.0;

                                    if (rounded_buy1 > rounded_sell1) {
                                        ccr4.setText(rounded_buy1 + "");
                                        ccr3.setText(rounded_sell1 + "");
                                    }
                                    if (rounded_buy1 < rounded_sell1) {
                                        ccr3.setText(rounded_buy1 + "");
                                        ccr4.setText(rounded_sell1 + "");
                                    }

                                }

                            }
                        } catch (Exception e) {
                        }
                    }

                }
            }
            if (bool == false) {

            }

        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_ccr2ActionPerformed

    private void ccr3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ccr3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ccr3KeyPressed

    private void ccr4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ccr4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ccr4KeyPressed

    private void ccr1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ccr1ActionPerformed
        DefaultComboBoxModel cb = new DefaultComboBoxModel();
        ccr2.setModel(cb);
        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        if (ccr1.getSelectedItem().equals("")) {

        }
        String bools = "false";
        String query = "SELECT base_currency, short_code FROM currencies ";
        try {
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
            stm = con.createStatement();
            rs = stm.executeQuery(query);
            boolean bool = rs.isBeforeFirst();
            while (rs.next()) {
                if (bool == true) {
                    String a = rs.getString("short_code");
                    boolean a1 = rs.getBoolean("base_currency");
                    if (!ccr1.getSelectedItem().equals(a) && a1 == false) {
                        ccr2.addItem("");
                        ccr2.addItem(a);
                    }
                    if (!ccr1.getSelectedItem().equals(a) && a1 == true) {
                        String querie = "SELECT base_currency,quote_currency,buying_rate,selling_rate FROM config_rates where quote_currency='" + ccr1.getSelectedItem().toString() + "' and base_currency='" + a + "' ";
                        try {
                            con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                            stm = con.createStatement();
                            rs = stm.executeQuery(querie);
                            boolean boom = rs.isBeforeFirst();
                            while (rs.next()) {
                                if (boom == true) {
                                    float b = rs.getFloat("buying_rate");
                                    float c = rs.getFloat("selling_rate");
                                    jpy1.setText(b + "");
                                    jpb1.setText(c + "");
                                }

                            }
                        } catch (Exception e) {
                        }
                    }

                }
            }
            if (bool == false) {

            }

        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_ccr1ActionPerformed

    private void jLabel191MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel191MouseClicked
        java.util.Date date = new java.util.Date();
        long t = date.getTime();
        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(t);
        java.sql.Date sqlDate = new java.sql.Date(t);

        if (ccr1.getSelectedItem().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Please select the base currency", "Incomplete data", JOptionPane.ERROR_MESSAGE);
        }
        if (ccr2.getSelectedItem().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Please select the quote currency", "Incomplete data", JOptionPane.ERROR_MESSAGE);
        }
        if (ccr3.getText().isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Enter the exchange rate", "Incomplete data", JOptionPane.ERROR_MESSAGE);
        }
        if (ccr4.getText().isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Enter the exchange rate", "Incomplete data", JOptionPane.ERROR_MESSAGE);
        }
        if (!ccr1.getSelectedItem().equals("") && !ccr1.getSelectedItem().equals("") && !jpy1.getText().isEmpty() && !jpb1.getText().isEmpty()) {
            if (ccr3.getText().contains("$") && ccr3.getText().contains("|")
                    && ccr3.getText().contains("?") && ccr3.getText().contains("/")
                    && ccr3.getText().contains(">") && ccr3.getText().contains("<")
                    && ccr3.getText().contains(",") && ccr3.getText().contains("\\")
                    && ccr3.getText().contains(":") && ccr3.getText().contains(";")
                    && ccr3.getText().contains("'") && ccr3.getText().contains("]")
                    && ccr3.getText().contains("[") && ccr3.getText().contains("}")
                    && ccr3.getText().contains("{") && ccr3.getText().contains("=")
                    && ccr3.getText().contains("+") && ccr3.getText().contains("-")
                    && ccr3.getText().contains("_") && ccr3.getText().contains(")")
                    && ccr3.getText().contains("(") && ccr3.getText().contains("*")
                    && ccr3.getText().contains("&") && ccr3.getText().contains("^")
                    && ccr3.getText().contains("%") && ccr3.getText().contains("#")
                    && ccr3.getText().contains("@") && ccr3.getText().contains("!")
                    && ccr3.getText().contains("~")) {
                ccr3.setText("");
                JOptionPane.showMessageDialog(null, "Please don't enter special character ", "Incorrect data format", JOptionPane.ERROR_MESSAGE);
            }
            if (!ccr4.getText().contains("$") && !ccr4.getText().contains("|")
                    && !ccr4.getText().contains("?") && !ccr4.getText().contains("/")
                    && !ccr4.getText().contains(">") && !ccr4.getText().contains("<")
                    && !ccr4.getText().contains(",") && !ccr4.getText().contains("\\")
                    && !ccr4.getText().contains(":") && !ccr4.getText().contains(";")
                    && !ccr4.getText().contains("'") && !ccr4.getText().contains("]")
                    && !ccr4.getText().contains("[") && !ccr4.getText().contains("}")
                    && !ccr4.getText().contains("{") && !ccr4.getText().contains("=")
                    && !ccr4.getText().contains("+") && !ccr4.getText().contains("-")
                    && !ccr4.getText().contains("_") && !ccr4.getText().contains(")")
                    && !ccr4.getText().contains("(") && !ccr4.getText().contains("*")
                    && !ccr4.getText().contains("&") && !ccr4.getText().contains("^")
                    && !ccr4.getText().contains("%") && !ccr4.getText().contains("#")
                    && !ccr4.getText().contains("@") && !ccr4.getText().contains("!")
                    && !ccr4.getText().contains("~")) {
                double asd = Double.parseDouble(ccr4.getText());
                String query = "SELECT base_currency,quote_currency,buying_rate,selling_rate FROM config_rates where base_currency='" + ccr1.getSelectedItem().toString() + "' and quote_currency='" + ccr2.getSelectedItem().toString() + "'";
                try {
                    Class.forName(DB_DRIVER);
                    con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                    stm = con.createStatement();
                    rs = stm.executeQuery(query);
                    boolean bool = rs.isBeforeFirst();
                    while (rs.next()) {
                        if (bool == true) {
                            //                String a = rs.getString("base_currency");
                            //                String b = rs.getString("quote_currency");
                            //                boolean c = rs.getBoolean("buying_rate");
                            //                boolean d = rs.getBoolean("selling_rate");
                            //                boolean boom = false;

                            JOptionPane.showMessageDialog(rootPane, "Exchange rates already exist", "Duplication error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    if (bool == false) {
                        try {
                            Class.forName(DB_DRIVER);

                            con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                            pst = con.prepareStatement("insert into config_rates values(?,?,?,?,?,?)");
                            pst.setString(1, ccr1.getSelectedItem().toString());
                            pst.setString(2, ccr2.getSelectedItem().toString());
                            float buying = Float.parseFloat(ccr3.getText());
                            float selling = Float.parseFloat(ccr4.getText());
                            pst.setDouble(3, buying);
                            pst.setDouble(4, selling);
                            pst.setString(5, name.getText());
                            pst.setTimestamp(6, sqlTimestamp);

                            int i = pst.executeUpdate();
                            if (i > 0) {
                                JOptionPane.showMessageDialog(null, "Exchange rates configured successfully", " Providence says....", JOptionPane.INFORMATION_MESSAGE);
                                DefaultTableModel dm = (DefaultTableModel) rt_table1.getModel();
                                dm.addRow(new Object[]{ccr1.getSelectedItem().toString(), ccr2.getSelectedItem().toString(), buying, selling});
                                ccr4.setText("");
                                ccr3.setText("");
                                jpy1.setText("");
                                jpb1.setText("");
                                opt2.setText("");
                                opt3.setText("");
                                int input = JOptionPane.showConfirmDialog(null, " You need to refresh the app to notice the changes and or additions you applied,\ndo you want to refresh ?", "Confirmation message....", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                                if (input == JOptionPane.NO_OPTION) {

                                }
                                if (input == JOptionPane.YES_OPTION) {
                                    Finance_Dep fd = new Finance_Dep();
                                    fd.setVisible(true);
                                    this.dispose();
                                }
                            }
                        } catch (SQLException ev) {
                            JOptionPane.showMessageDialog(null, ev.getMessage());

                        }
                    }

                } catch (ClassNotFoundException | SQLException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_jLabel191MouseClicked

    private void rtuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rtuMouseClicked
        DefaultTableModel model = (DefaultTableModel) rtu.getModel();
        ru5.setText(model.getValueAt(rtu.getSelectedRow(), 2).toString());
        ru1.setText(model.getValueAt(rtu.getSelectedRow(), 0).toString());
        ru2.setText(model.getValueAt(rtu.getSelectedRow(), 1).toString());
        ru6.setText(model.getValueAt(rtu.getSelectedRow(), 3).toString());
        rid.setText(model.getValueAt(rtu.getSelectedRow(), 4).toString());

        ru5.setEditable(true);
        ru6.setEditable(true);

    }//GEN-LAST:event_rtuMouseClicked

    private void ru6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ru6KeyPressed
        int key = evt.getKeyCode();
        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        if (key == 10) {
            if (ru6.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Enter the new exchange rate that you want to set", "Incomplte data", JOptionPane.ERROR_MESSAGE);
            }
            if (!ru6.getText().isEmpty()) {
                if (ru6.getText().contains("$") && ru6.getText().contains("|")
                        && rt5.getText().contains("?") && ru6.getText().contains("/")
                        && ru6.getText().contains(">") && ru6.getText().contains("<")
                        && ru6.getText().contains(",") && ru6.getText().contains("\\")
                        && ru6.getText().contains(":") && ru6.getText().contains(";")
                        && ru6.getText().contains("'") && ru6.getText().contains("]")
                        && ru6.getText().contains("[") && ru6.getText().contains("}")
                        && ru6.getText().contains("{") && ru6.getText().contains("=")
                        && ru6.getText().contains("+") && ru6.getText().contains("-")
                        && ru6.getText().contains("_") && ru6.getText().contains(")")
                        && ru6.getText().contains("(") && ru6.getText().contains("*")
                        && ru6.getText().contains("&") && ru6.getText().contains("^")
                        && ru6.getText().contains("%") && ru6.getText().contains("#")
                        && ru6.getText().contains("@") && ru6.getText().contains("!")
                        && ru6.getText().contains("~")) {
                    ru6.setText("");
                    JOptionPane.showMessageDialog(null, "Please don't enter special character ", "Incorrect data", JOptionPane.ERROR_MESSAGE);
                }
                if (!ru6.getText().contains("$") && !ru6.getText().contains("|")
                        && !ru6.getText().contains("?") && !ru6.getText().contains("/")
                        && !ru6.getText().contains(">") && !ru6.getText().contains("<")
                        && !ru6.getText().contains(",") && !ru6.getText().contains("\\")
                        && !ru6.getText().contains(":") && !ru6.getText().contains(";")
                        && !ru6.getText().contains("'") && !ru6.getText().contains("]")
                        && !ru6.getText().contains("[") && !ru6.getText().contains("}")
                        && !ru6.getText().contains("{") && !ru6.getText().contains("=")
                        && !ru6.getText().contains("+") && !ru6.getText().contains("-")
                        && !ru6.getText().contains("_") && !ru6.getText().contains(")")
                        && !ru6.getText().contains("(") && !ru6.getText().contains("*")
                        && !ru6.getText().contains("&") && !ru6.getText().contains("^")
                        && !ru6.getText().contains("%") && !ru6.getText().contains("#")
                        && !ru6.getText().contains("@") && !ru6.getText().contains("!")
                        && !ru6.getText().contains("~")) {
                    double asd = Double.parseDouble(ru6.getText());
                    int nhu = Integer.parseInt(nhy.getText());
                    int input = JOptionPane.showConfirmDialog(null, " Are you sure that you want to update the exchange rates ?", "Confirmation message....", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                    if (input == JOptionPane.NO_OPTION) {

                    }
                    //int v = Integer.parseInt(cy_id.getText());
                    if (input == JOptionPane.YES_OPTION) {

                        SQLRun run = new SQLRun();

                        String sqlq = "Update exchange_rates set rate='" + asd + "'   where exchange_rate_id='" + nhu + "' ";
                        int updated = run.sqlUpdate(sqlq);
                        if (updated > 0) {
                            JOptionPane.showMessageDialog(null, "You have successfully currency info ", "Currency Info Update Notification", JOptionPane.INFORMATION_MESSAGE);
                            Finance_Dep fd = new Finance_Dep();
                            fd.setVisible(true);
                            this.dispose();
                        }
                    }

                }
            }
        }
    }//GEN-LAST:event_ru6KeyPressed

    private void ru6KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ru6KeyTyped
        char c = evt.getKeyChar();
        if (Character.isAlphabetic(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_ru6KeyTyped

    private void jLabel193MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel193MouseClicked
        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        if (ru1.getText().matches(rt1.getText()) && !ru1.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please be adviced that this option only works for cross currency exchange rates", "Validation", JOptionPane.WARNING_MESSAGE);
        }
        if (ru1.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please select a row in the table", "Validation", JOptionPane.WARNING_MESSAGE);
        }
        if (!ru1.getText().matches(rt1.getText()) && !ru1.getText().isEmpty()) {
            rates.setVisible(false);
            reports.setVisible(false);
            users.setVisible(false);
            branches.setVisible(false);
            //operations.setVisible(false);

            currencies.setVisible(false);
            opening_balances.setVisible(false);
            denominations.setVisible(false);
            simult.setVisible(true);
            dv1.setText(rt1.getText());
            sv1.setText(rt1.getText());

            dv2.setText(ru1.getText());
            sv2.setText(ru2.getText());
        }
    }//GEN-LAST:event_jLabel193MouseClicked

    private void jLabel70MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel70MouseClicked
        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        int input = JOptionPane.showConfirmDialog(null, " Are you sure that you want to update exchange rates ?", "Providence says....", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (input == JOptionPane.NO_OPTION) {

        }
        if (input == JOptionPane.YES_OPTION) {
            SQLRun run = new SQLRun();
            double a = Double.parseDouble(ru5.getText());
            double a1 = Math.round(a * 10) / 10.0;
            double b = Double.parseDouble(ru6.getText());
            double b1 = Math.round(b * 10) / 10.0;

            String sqlq = "Update config_rates set buying_rate='" + a1 + "', selling_rate='" + b1 + "' where  base_currency='" + ru1.getText() + "' and  quote_currency='" + ru2.getText() + "'";
            int updated = run.sqlUpdate(sqlq);
            if (updated > 0) {
                JOptionPane.showMessageDialog(null, "You have successfully updated the exchnage rates", "Updating....", JOptionPane.INFORMATION_MESSAGE);
                ru1.setText("");
                ru2.setText("");
                ru5.setText("");
                ru6.setText("");
            }

        }
    }//GEN-LAST:event_jLabel70MouseClicked

    private void fc5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fc5ActionPerformed
        DefaultComboBoxModel dm = new DefaultComboBoxModel();
        fc6.setModel(dm);
        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        String query1 = "SELECT region_name,branch_name,area_code,description,created_by,branch_id FROM branches where region_name='" + fc5.getSelectedItem().toString() + "'";
        try {
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
            stm = con.createStatement();

            rs = stm.executeQuery(query1);
            boolean boolm = rs.isBeforeFirst();
            while (rs.next()) {
                if (boolm == true) {
                    String a = rs.getString("branch_name");
                    fc6.addItem(" ");
                    fc6.addItem(a);

                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_fc5ActionPerformed

    private void fd5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fd5ActionPerformed
        DefaultComboBoxModel dm = new DefaultComboBoxModel();
        fd6.setModel(dm);
        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        String query1 = "SELECT region_name,branch_name,area_code,description,created_by,branch_id FROM branches where region_name='" + fd5.getSelectedItem().toString() + "'";
        try {
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
            stm = con.createStatement();

            rs = stm.executeQuery(query1);
            boolean boolm = rs.isBeforeFirst();
            while (rs.next()) {
                if (boolm == true) {
                    String a = rs.getString("branch_name");
                    fd6.addItem(" ");
                    fd6.addItem(a);

                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Something went wrong", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_fd5ActionPerformed

    private void zh5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zh5ActionPerformed
        DefaultComboBoxModel dm = new DefaultComboBoxModel();
        zh6.setModel(dm);
        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        String query1 = "SELECT region_name,branch_name,area_code,description,created_by,branch_id FROM branches where region_name='" + zh5.getSelectedItem().toString() + "'";
        try {
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
            stm = con.createStatement();

            rs = stm.executeQuery(query1);
            boolean boolm = rs.isBeforeFirst();
            while (rs.next()) {
                if (boolm == true) {
                    String a = rs.getString("branch_name");
                    zh6.addItem(" ");
                    zh6.addItem(a);

                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Something went wrong !!!!!", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_zh5ActionPerformed

    private void px5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_px5ActionPerformed
        DefaultComboBoxModel dm = new DefaultComboBoxModel();
        px6.setModel(dm);
        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        String query1 = "SELECT region_name,branch_name,area_code,description,created_by,branch_id FROM branches where region_name='" + px5.getSelectedItem().toString() + "'";
        try {
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
            stm = con.createStatement();

            rs = stm.executeQuery(query1);
            boolean boolm = rs.isBeforeFirst();
            while (rs.next()) {
                if (boolm == true) {
                    String a = rs.getString("branch_name");
                    px6.addItem(" ");
                    px6.addItem(a);

                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Something went wrong !!!!!", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_px5ActionPerformed

    private void cp3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cp3ActionPerformed
        java.util.Date date = new java.util.Date();
        long t = date.getTime();
        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(t);
        java.sql.Date sqlDate = new java.sql.Date(t);

        if (!cp3.getSelectedItem().equals(" ")) {
            DefaultTableModel dk = (DefaultTableModel) cp_table.getModel();
            dk.setNumRows(0);

            String DB_DRIVER = "org.postgresql.Driver";
            String DB_URL = "jdbc:postgresql:" + configs.getText();
            String DB_USER = "postgres";
            String DB_PAS = "danieloh24";

            Connection con = null;
            java.sql.Statement stm = null;
            ResultSet rs2 = null;
            PreparedStatement pst = null;
            String querie = "SELECT branch,currency,denominations,no_denominations,total, send_by,sender_position,receiver,receiver_position,date FROM clerks_operators where branch='" + cp2.getSelectedItem().toString() + "'";
            try {
                Class.forName(DB_DRIVER);
                con = DriverManager.getConnection(DB_URL, DB_USER, DB_PAS);
                stm = con.createStatement();
                rs2 = stm.executeQuery(querie);
                boolean bool2 = rs2.isBeforeFirst();
                while (rs2.next()) {
                    if (bool2 == true) {
                        String a = rs2.getString("currency");
                        String b = rs2.getString("denominations");
                        String c = rs2.getString("no_denominations");
                        String d = rs2.getString("total");
                        String e = rs2.getString("send_by");
                        String f = rs2.getString("sender_position");
                        String g = rs2.getString("receiver_position");
                        java.sql.Timestamp h = rs2.getTimestamp("date");

                        String j = rs2.getString("branch");

                        if (cp3.getSelectedItem().equals(a) && !f.equals(g)) {
                            dk.addRow(new Object[]{a, b, c, d, e, f, g, h});
                            getSum1();
                        }
                        if (cp3.getSelectedItem().equals("All") && !f.equals(g)) {
                            dk.addRow(new Object[]{a, b, c, d, e, f, g, h});
                            cp4.setText("");
                        }

                        //getSum();
                    }
                }
                if (bool2 == false) {
                    JOptionPane.showMessageDialog(null, "You don't have the currency you selected ", "Currency not found", JOptionPane.WARNING_MESSAGE);
                    //acc_total.setText(" ");
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Clerk.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_cp3ActionPerformed

    private void cp1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cp1ActionPerformed
        DefaultComboBoxModel dm = new DefaultComboBoxModel();
        cp2.setModel(dm);
        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        String query1 = "SELECT region_name,branch_name,area_code,description,created_by,branch_id FROM branches where region_name='" + cp1.getSelectedItem().toString() + "'";
        try {
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
            stm = con.createStatement();

            rs = stm.executeQuery(query1);
            boolean boolm = rs.isBeforeFirst();
            while (rs.next()) {
                if (boolm == true) {
                    String a = rs.getString("branch_name");
                    cp2.addItem(" ");
                    cp2.addItem(a);

                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Something went wrong !!!!!", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_cp1ActionPerformed

    private void cg3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cg3ActionPerformed
        java.util.Date date = new java.util.Date();
        long t = date.getTime();
        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(t);
        java.sql.Date sqlDate = new java.sql.Date(t);

        if (!cg3.getSelectedItem().equals(" ")) {
            DefaultTableModel dk = (DefaultTableModel) cg_table.getModel();
            dk.setNumRows(0);

            String querie = "SELECT branch,currency,denominations,no_denominations,total, send_by,sender_position,receiver,receiver_position,date FROM clerks_operators where branch='" + cg2.getSelectedItem().toString() + "'";
            try {
                Class.forName(DB_DRIVER);
                con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                stm = con.createStatement();
                rs = stm.executeQuery(querie);
                boolean bool2 = rs.isBeforeFirst();
                while (rs.next()) {
                    if (bool2 == true) {
                        String a = rs.getString("currency");
                        String b = rs.getString("denominations");
                        String c = rs.getString("no_denominations");
                        String d = rs.getString("total");
                        String e = rs.getString("send_by");
                        String f = rs.getString("sender_position");
                        String g = rs.getString("receiver_position");
                        java.sql.Timestamp h = rs.getTimestamp("date");

                        String j = rs.getString("branch");

                        if (cg3.getSelectedItem().equals(a) && f.equals(g)) {
                            dk.addRow(new Object[]{a, b, c, d, e, f, g, h});
                            getSum();
                        }
                        if (cg3.getSelectedItem().equals("All") && f.equals(g)) {
                            dk.addRow(new Object[]{a, b, c, d, e, f, g, h});
                            cg4.setText("");
                        }

                    }
                }
                if (bool2 == false) {
                    JOptionPane.showMessageDialog(null, "You don't have the currency you selected ", "Currency not found", JOptionPane.WARNING_MESSAGE);
                    cg4.setText(" ");
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Something went wrong", JOptionPane.ERROR_MESSAGE);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Clerk.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_cg3ActionPerformed

    private void cg1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cg1ActionPerformed
        DefaultComboBoxModel dm = new DefaultComboBoxModel();
        cg2.setModel(dm);
        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        String query1 = "SELECT region_name,branch_name,area_code,description,created_by,branch_id FROM branches where region_name='" + cg1.getSelectedItem().toString() + "'";
        try {
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
            stm = con.createStatement();

            rs = stm.executeQuery(query1);
            boolean boolm = rs.isBeforeFirst();
            while (rs.next()) {
                if (boolm == true) {
                    String a = rs.getString("branch_name");
                    cg2.addItem(" ");
                    cg2.addItem(a);

                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Something went wrong !!!!!", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_cg1ActionPerformed

    private void opb3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opb3ActionPerformed
        DefaultComboBoxModel dm = new DefaultComboBoxModel();
        opb4.setModel(dm);
        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        String query1 = "SELECT region_name,branch_name,area_code,description,created_by,branch_id FROM branches where region_name='" + opb3.getSelectedItem().toString() + "'";
        try {
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
            stm = con.createStatement();

            rs = stm.executeQuery(query1);
            boolean boolm = rs.isBeforeFirst();
            while (rs.next()) {
                if (boolm == true) {
                    String a = rs.getString("branch_name");
                    opb4.addItem(" ");
                    opb4.addItem(a);

                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Something went wrong !!!!!", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_opb3ActionPerformed

    private void acc_currencyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_acc_currencyActionPerformed
        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        if (!acc_currency.getSelectedItem().equals(" ")) {
            DefaultTableModel dk = (DefaultTableModel) acc_table.getModel();
            dk.setNumRows(0);

            String querie = "SELECT branch,currency,denominations,no_denominations,total,send_by FROM branch_accounts where branch='" + opb4.getSelectedItem().toString() + "' and currency='" + acc_currency.getSelectedItem().toString() + "'";
            try {
                Class.forName(DB_DRIVER);
                con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                stm = con.createStatement();
                rs = stm.executeQuery(querie);
                boolean bool2 = rs.isBeforeFirst();
                while (rs.next()) {
                    if (bool2 == true) {
                        String a = rs.getString("currency");
                        String b = rs.getString("denominations");
                        String c = rs.getString("no_denominations");
                        String d = rs.getString("total");
                        String e = rs.getString("send_by");
                        dk.addRow(new Object[]{b, c, d, e});
                        getSum3();

                    }
                }
                if (bool2 == false) {
                    JOptionPane.showMessageDialog(null, "You don't have the currency you selected ", "Currency not found", JOptionPane.WARNING_MESSAGE);
                    acc_total.setText(" ");
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Clerk.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_acc_currencyActionPerformed

    private void tw5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tw5ActionPerformed
        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        if (!tw5.getSelectedItem().equals(" ")) {
            DefaultTableModel dk = (DefaultTableModel) tw_table.getModel();
            dk.setNumRows(0);

            String querie = "SELECT branch,currency,denominations,no_denominations,total,send_by FROM initial_balances where branch='" + tw4.getSelectedItem().toString() + "' and currency='" + tw5.getSelectedItem().toString() + "'";
            try {
                Class.forName(DB_DRIVER);
                con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                stm = con.createStatement();
                rs = stm.executeQuery(querie);
                boolean bool2 = rs.isBeforeFirst();
                while (rs.next()) {
                    if (bool2 == true) {
                        String a = rs.getString("currency");
                        String b = rs.getString("denominations");
                        String c = rs.getString("no_denominations");
                        String d = rs.getString("total");
                        String e = rs.getString("send_by");
                        dk.addRow(new Object[]{b, c, d, e});
                        getSum2();

                    }
                }
                if (bool2 == false) {
                    JOptionPane.showMessageDialog(null, "You don't have the currency you selected ", "Currency not found", JOptionPane.WARNING_MESSAGE);
                    tw6.setText(" ");
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Clerk.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_tw5ActionPerformed

    private void tw3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tw3ActionPerformed
        DefaultComboBoxModel dm = new DefaultComboBoxModel();
        tw4.setModel(dm);
        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        String query1 = "SELECT region_name,branch_name,area_code,description,created_by,branch_id FROM branches where region_name='" + tw3.getSelectedItem().toString() + "'";
        try {
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
            stm = con.createStatement();

            rs = stm.executeQuery(query1);
            boolean boolm = rs.isBeforeFirst();
            while (rs.next()) {
                if (boolm == true) {
                    String a = rs.getString("branch_name");
                    tw4.addItem(" ");
                    tw4.addItem(a);

                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Something went wrong !!!!!", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_tw3ActionPerformed

    private void jLabel50MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel50MouseClicked
DB_USER = uname.getText();
        DB_PAS = upass.getText();
        if (jm_table1.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "The table is empty, there's nothing to save. I can not allow you to save empty files of files with 0KB", "Providence says...", JOptionPane.ERROR_MESSAGE);
        }
        if (jm_table1.getRowCount() > 0) {
            String system_name = System.getProperty("user.name");
            FileOutputStream fstream = null;
            BufferedOutputStream bstream = null;
            XSSFWorkbook wk_book = null;
            JFileChooser jfile = new JFileChooser("C:\\" + system_name + "\\Desktop");
            jfile.setDialogTitle("Save your files");
            FileNameExtensionFilter fext = new FileNameExtensionFilter("EXCEL FILES", "xlsx", "xls", "xlsm");
            jfile.setFileFilter(fext);
            //jfile.showSaveDialog(null);
            int excel = jfile.showSaveDialog(null);
            if (excel == JFileChooser.APPROVE_OPTION) {

                try {
                    wk_book = new XSSFWorkbook();
                    XSSFSheet sheet = wk_book.createSheet("My sheet");
                    for (int i = 0; i < jm_table1.getRowCount(); i++) {
                        XSSFRow excelRow = sheet.createRow(i);
                        for (int j = 0; j < jm_table1.getColumnCount(); j++) {
                            XSSFCell excelCell = excelRow.createCell(j);

                            excelCell.setCellValue(jm_table1.getValueAt(i, j).toString());
                        }
                    }
                    fstream = new FileOutputStream(jfile.getSelectedFile() + ".xlsx");
                    bstream = new BufferedOutputStream(fstream);
                    wk_book.write(bstream);
                } catch (FileNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                } finally {
                    try {
                        if (bstream != null) {
                            bstream.close();
                        }
                        if (fstream != null) {
                            fstream.close();
                        }

                        if (wk_book != null) {
                            wk_book.close();
                        }

                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    }
                }

            }
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel50MouseClicked

    private void jLabel51MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel51MouseClicked
DB_USER = uname.getText();
        DB_PAS = upass.getText();
        if (jm_table1.getRowCount() < 1) {
            JOptionPane.showMessageDialog(null, "The table is empty, so you can not print an empty file", "P rovidence says....", JOptionPane.ERROR_MESSAGE);
        }
        if (jm_table1.getRowCount() >= 1) {
            MessageFormat header = new MessageFormat("\t\tBureau De Change System : Reports ");
            MessageFormat footer = new MessageFormat("Page{0, number, integer}");
            try {
                jm_table1.print(JTable.PrintMode.NORMAL, header, footer);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Printing Terminated", e.getMessage(), JOptionPane.ERROR_MESSAGE);
            }
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel51MouseClicked

    private void jLabel52MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel52MouseClicked
DB_USER = uname.getText();
        DB_PAS = upass.getText();
        DefaultTableModel mod = (DefaultTableModel) jm_table1.getModel();
        mod.setRowCount(0);
        String dg = mj1.getDate().toString();
        w1.setText(dg);
        DateFormat dateFormat2 = new SimpleDateFormat("y-MM-dd");
        mj1.setDateFormatString("y-MM-dd");
        String dte = dateFormat2.format(mj1.getDate());
        w1.setText(dte);

        String dg1 = mj2.getDate().toString();
        w2.setText(dg1);
        DateFormat dateFormat = new SimpleDateFormat("y-MM-dd");
        mj2.setDateFormatString("y-MM-dd");
        String dte1 = dateFormat.format(mj2.getDate());
        w2.setText(dte1);
        java.util.Date date = new java.util.Date();
        long t = date.getTime();

        String op = "selling";
        if (fa4.isSelected()) {
            if (fa1.isSelected() && !fa2.isSelected()) {
                String query = "SELECT base_currency,short_code FROM currencies where base_currency='" + true + "'";
                try {
                    Class.forName(DB_DRIVER);
                    con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                    stm = con.createStatement();
                    rs = stm.executeQuery(query);
                    boolean bool = rs.isBeforeFirst();
                    while (rs.next()) {
                        if (bool == true) {
                            String a = rs.getString("short_code");
                            bscd.setText(a);
                            try {
                                Class.forName(DB_DRIVER);
                                java.sql.Connection conn = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                String query1 = "SELECT base_currency,quote_currency,region,branch,exchange_rate,amount_traded_in,amount_traded_out,date,cashier FROM transactions_history where date between'" + w1.getText() + "' and '" + w2.getText() + "' and region='" + fa5.getSelectedItem().toString() + "' and branch='" + fa6.getSelectedItem().toString() + "' and quote_currency='" + a + "' and operation='" + op + "' ";

                                java.sql.Statement st = conn.createStatement();

                                // execute the query, and get a java resultset
                                ResultSet rsq = st.executeQuery(query1);

                                // iterate through the java resultset
                                while (rsq.next()) {
                                    bscd.setText(a);
                                    String s1 = rsq.getString("base_currency");
                                    String s2 = rsq.getString("quote_currency");
                                    double s3 = rsq.getDouble("exchange_rate");
                                    double s4 = rsq.getDouble("amount_traded_in");
                                    double s5 = rsq.getDouble("amount_traded_out");

                                    String s6 = rsq.getString("region");
                                    String s7 = rsq.getString("branch");
                                    java.sql.Date sqlDate = rsq.getDate("date");
                                    String s8 = rsq.getString("cashier");

                                    mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});

                                }
                                boolean mb = rsq.isAfterLast();
                                if (mb == false) {
                                    JOptionPane.showMessageDialog(null, "There are no sales recorded between " + mj1.getDate() + " " + "&" + " " + mj2.getDate() + " " + "\nfor " + fa6.getSelectedItem().toString() + " " + "receiving the local curency whilst selling foreign currency", "Transaction record not found", JOptionPane.WARNING_MESSAGE);
                                }

                            } catch (Exception er) {

                            }

                        }
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), " Something went  wrong, please contact your database administrator or the vendor", JOptionPane.ERROR_MESSAGE);
                }

            }
            if (!fa1.isSelected() && fa2.isSelected()) {
                String query = "SELECT base_currency,short_code FROM currencies where base_currency='" + true + "'";
                try {
                    Class.forName(DB_DRIVER);
                    con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                    stm = con.createStatement();
                    rs = stm.executeQuery(query);
                    boolean bool = rs.isBeforeFirst();
                    while (rs.next()) {
                        if (bool == true) {
                            String a = rs.getString("short_code");
                            try {
                                Class.forName(DB_DRIVER);
                                java.sql.Connection conn = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                String query1 = "SELECT base_currency,quote_currency,region,branch,exchange_rate,amount_traded_in,amount_traded_out,date,cashier FROM transactions_history where date between'" + w1.getText() + "' and '" + w2.getText() + "' and region='" + fa5.getSelectedItem().toString() + "' and branch='" + fa6.getSelectedItem().toString() + "' quote_currency=!'" + a + "' and base_currency='" + a + "' and operation='" + op + "' ";

                                java.sql.Statement st = conn.createStatement();

                                // execute the query, and get a java resultset
                                ResultSet rsq = st.executeQuery(query1);

                                // iterate through the java resultset
                                while (rsq.next()) {
                                    bscd.setText(a);
                                    String s1 = rsq.getString("base_currency");
                                    String s2 = rsq.getString("quote_currency");
                                    double s3 = rsq.getDouble("exchange_rate");
                                    double s4 = rsq.getDouble("amount_traded_in");
                                    double s5 = rsq.getDouble("amount_traded_out");

                                    String s6 = rsq.getString("region");
                                    String s7 = rsq.getString("branch");
                                    java.sql.Date sqlDate = rsq.getDate("date");
                                    String s8 = rsq.getString("cashier");

                                    mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                    int sum = 0;
                                }
                                boolean mb = rsq.isAfterLast();
                                if (mb == false) {
                                    JOptionPane.showMessageDialog(null, "There are no sales recorded between " + mj1.getDate() + " " + "&" + " " + mj2.getDate() + " " + "\nfor " + fa6.getSelectedItem().toString() + " " + "receiving other foreign curencies whilst selling foreign currencies", "Transaction record not found", JOptionPane.WARNING_MESSAGE);
                                }

                            } catch (Exception er) {

                            }

                        }
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), " Something went wrong please contact your database administrator or the vendor", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        if (!fa4.isSelected()) {
            if (fa1.isSelected() && !fa2.isSelected()) {
                String query = "SELECT base_currency,short_code FROM currencies where base_currency='" + true + "'";
                try {
                    Class.forName(DB_DRIVER);
                    con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                    stm = con.createStatement();
                    rs = stm.executeQuery(query);
                    boolean bool = rs.isBeforeFirst();
                    while (rs.next()) {
                        if (bool == true) {
                            String a = rs.getString("short_code");
                            try {
                                Class.forName(DB_DRIVER);
                                java.sql.Connection conn = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                String query1 = "SELECT base_currency,quote_currency,region,branch,exchange_rate,amount_traded_in,amount_traded_out,date,cashier FROM transactions_history where date between'" + w1.getText() + "' and '" + w2.getText() + "' and operation='" + op + "' and region='" + fa5.getSelectedItem().toString() + "'  and quote_currency='" + a + "' and base_currency!='" + a + "' ";

                                java.sql.Statement st = conn.createStatement();

                                // execute the query, and get a java resultset
                                ResultSet rsq = st.executeQuery(query1);
                                boolean swm = rsq.isBeforeFirst();
                                // iterate through the java resultset
                                while (rsq.next()) {
                                    bscd.setText(a);
                                    String s1 = rsq.getString("base_currency");
                                    String s2 = rsq.getString("quote_currency");
                                    double s3 = rsq.getDouble("exchange_rate");
                                    double s4 = rsq.getDouble("amount_traded_in");
                                    double s5 = rsq.getDouble("amount_traded_out");

                                    String s6 = rsq.getString("region");
                                    String s7 = rsq.getString("branch");
                                    java.sql.Date sqlDate = rsq.getDate("date");
                                    String s8 = rsq.getString("cashier");

                                    mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                    int sum = 0;
                                }
                                boolean mb = rsq.isAfterLast();
                                if (mb == false) {
                                    JOptionPane.showMessageDialog(null, "There are no sales recorded between " + mj1.getDate() + " " + "&" + " " + mj2.getDate() + " " + "\n within " + fa5.getSelectedItem().toString() + " " + "receiving local curency whilst selling foreign currencies", "Transaction record not found", JOptionPane.WARNING_MESSAGE);
                                }
                            } catch (Exception er) {

                            }

                        }
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), " Something went wrong please contact your database administrator or the vendor", JOptionPane.ERROR_MESSAGE);
                }

            }
            if (!fa1.isSelected() && fa2.isSelected()) {
                String query = "SELECT base_currency,short_code FROM currencies where base_currency='" + true + "'";
                try {
                    Class.forName(DB_DRIVER);
                    con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                    stm = con.createStatement();
                    rs = stm.executeQuery(query);
                    boolean bool = rs.isBeforeFirst();
                    while (rs.next()) {
                        if (bool == true) {
                            String a = rs.getString("short_code");
                            try {
                                Class.forName(DB_DRIVER);
                                java.sql.Connection conn = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                String query1 = "SELECT base_currency,quote_currency,region,branch,exchange_rate,amount_traded_in,amount_traded_out,date,cashier FROM transactions_history where date between'" + w1.getText() + "' and  '" + w2.getText() + "' and operation='" + op + "'  and region='" + fa5.getSelectedItem().toString() + "' and quote_currency!='" + a + "' and base_currency!='" + a + "' ";

                                java.sql.Statement st = conn.createStatement();

                                // execute the query, and get a java resultset
                                ResultSet rsq = st.executeQuery(query1);

                                // iterate through the java resultset
                                while (rsq.next()) {
                                    String s1 = rsq.getString("base_currency");
                                    String s2 = rsq.getString("quote_currency");
                                    double s3 = rsq.getDouble("exchange_rate");
                                    double s4 = rsq.getDouble("amount_traded_in");
                                    double s5 = rsq.getDouble("amount_traded_out");

                                    String s6 = rsq.getString("region");
                                    String s7 = rsq.getString("branch");
                                    java.sql.Date sqlDate = rsq.getDate("date");
                                    String s8 = rsq.getString("cashier");

                                    mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                    int sum = 0;
                                }
                                boolean mb = rsq.isAfterLast();
                                if (mb == false) {
                                    JOptionPane.showMessageDialog(null, "There are no sales recorded between " + mj1.getDate() + " " + "&" + " " + mj2.getDate() + " " + "\n within " + fa5.getSelectedItem().toString() + " " + "receiving other foreign currencies whilst selling foreign currencies", "Transaction record not found", JOptionPane.WARNING_MESSAGE);
                                }
                            } catch (Exception er) {

                            }

                        }
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), " Something went wrong please contact your database administrator or the vendor", JOptionPane.ERROR_MESSAGE);
                }
            }
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel52MouseClicked

    private void jLabel53MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel53MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel53MouseClicked

    private void jLabel55MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel55MouseClicked
        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        if (fb_table.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "The table is empty, there's nothing to save. I can not allow you to save empty files of files with 0KB", "Providence says...", JOptionPane.ERROR_MESSAGE);
        }
        if (fb_table.getRowCount() > 0) {
            String system_name = System.getProperty("user.name");
            FileOutputStream fstream = null;
            BufferedOutputStream bstream = null;
            XSSFWorkbook wk_book = null;
            JFileChooser jfile = new JFileChooser("C:\\" + system_name + "\\Desktop");
            jfile.setDialogTitle("Save your files");
            FileNameExtensionFilter fext = new FileNameExtensionFilter("EXCEL FILES", "xlsx", "xls", "xlsm");
            jfile.setFileFilter(fext);
            //jfile.showSaveDialog(null);
            int excel = jfile.showSaveDialog(null);
            if (excel == JFileChooser.APPROVE_OPTION) {

                try {
                    wk_book = new XSSFWorkbook();
                    XSSFSheet sheet = wk_book.createSheet("My sheet");
                    for (int i = 0; i < fb_table.getRowCount(); i++) {
                        XSSFRow excelRow = sheet.createRow(i);
                        for (int j = 0; j < fb_table.getColumnCount(); j++) {
                            XSSFCell excelCell = excelRow.createCell(j);

                            excelCell.setCellValue(fb_table.getValueAt(i, j).toString());
                        }
                    }
                    fstream = new FileOutputStream(jfile.getSelectedFile() + ".xlsx");
                    bstream = new BufferedOutputStream(fstream);
                    wk_book.write(bstream);
                } catch (FileNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                } finally {
                    try {
                        if (bstream != null) {
                            bstream.close();
                        }
                        if (fstream != null) {
                            fstream.close();
                        }

                        if (wk_book != null) {
                            wk_book.close();
                        }

                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    }
                }

            }
        }
    }//GEN-LAST:event_jLabel55MouseClicked

    private void jLabel89MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel89MouseClicked
             DB_USER = uname.getText();
        DB_PAS = upass.getText();
        if (fb_table.getRowCount() < 1) {
            JOptionPane.showMessageDialog(null, "The table is empty, so you can not print an empty file", "Providence says....", JOptionPane.ERROR_MESSAGE);
        }
        if (fb_table.getRowCount() >= 1) {
            MessageFormat header = new MessageFormat("\t\tBureau De Change System : Daily Sales Reports ");
            MessageFormat footer = new MessageFormat("Page{0, number, integer}");
            try {
                fb_table.print(JTable.PrintMode.NORMAL, header, footer);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Printing Terminated", e.getMessage(), JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jLabel89MouseClicked

    private void jLabel91MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel91MouseClicked
       DefaultTableModel mod = (DefaultTableModel) fb_table.getModel();
        mod.setRowCount(0);
        String dg = fb7.getDate().toString();
        fw1.setText(dg);
        DateFormat dateFormat2 = new SimpleDateFormat("y-MM-dd");
        fb7.setDateFormatString("y-MM-dd");
        String dte = dateFormat2.format(fb7.getDate());
        fw1.setText(dte);
        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        String op = "selling";

        java.util.Date date = new java.util.Date();
        long t = date.getTime();
        if (fb4.isSelected()) {
            if (fb1.isSelected() && !fb2.isSelected()) {
                String query = "SELECT base_currency,short_code FROM currencies where base_currency='" + true + "'";
                try {
                    Class.forName(DB_DRIVER);
                    con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                    stm = con.createStatement();
                    rs = stm.executeQuery(query);
                    boolean bool = rs.isBeforeFirst();
                    while (rs.next()) {
                        if (bool == true) {
                            String a = rs.getString("short_code");
                            bscd.setText(a);
                            try {
                                Class.forName(DB_DRIVER);
                                java.sql.Connection conn = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                String query1 = "SELECT base_currency,quote_currency,region,branch,exchange_rate,amount_traded_in,amount_traded_out,date,cashier FROM transactions_history where date='" + fw1.getText() + "' and operation='" + op + "'  and region='" + fb5.getSelectedItem().toString() + "' and branch='" + fb6.getSelectedItem().toString() + "' and quote_currency='" + a + "'";

                                java.sql.Statement st = conn.createStatement();

                                // execute the query, and get a java resultset
                                ResultSet rsq = st.executeQuery(query1);

                                // iterate through the java resultset
                                while (rsq.next()) {
                                    String s1 = rsq.getString("base_currency");
                                    String s2 = rsq.getString("quote_currency");
                                    double s3 = rsq.getDouble("exchange_rate");
                                    double s4 = rsq.getDouble("amount_traded_in");
                                    double s5 = rsq.getDouble("amount_traded_out");

                                    String s6 = rsq.getString("region");
                                    String s7 = rsq.getString("branch");
                                    java.sql.Date sqlDate = rsq.getDate("date");
                                    String s8 = rsq.getString("cashier");

                                    mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                    int sum = 0;
                                }
                                boolean mb = rsq.isAfterLast();
                                if (mb == false) {
                                    JOptionPane.showMessageDialog(null, "There are no sales recorded on " + fb7.getDate() + "\nfor " + fb6.getSelectedItem().toString() + " " + "receiving the local curency whilst selling foreign currency", "Transaction record not found", JOptionPane.WARNING_MESSAGE);
                                }

                            } catch (ClassNotFoundException | SQLException er) {
                                JOptionPane.showMessageDialog(null, er.getMessage());

                            }

                        }
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), " Something went wrong please contact your database administrator ir the vendor ERROR", JOptionPane.ERROR_MESSAGE);
                }

            }
            if (!fb1.isSelected() && fb2.isSelected()) {
                String query = "SELECT base_currency,short_code FROM currencies where base_currency='" + true + "'";
                try {
                    Class.forName(DB_DRIVER);
                    con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                    stm = con.createStatement();
                    rs = stm.executeQuery(query);
                    boolean bool = rs.isBeforeFirst();
                    while (rs.next()) {
                        if (bool == true) {
                            String a = rs.getString("short_code");
                            try {
                                Class.forName(DB_DRIVER);
                                java.sql.Connection conn = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                String query1 = "SELECT base_currency,quote_currency,region,branch,exchange_rate,amount_traded_in,amount_traded_out,date,cashier FROM transactions_history where date='" + fw1.getText() + "' and operation='" + op + "'  and region='" + fb5.getSelectedItem().toString() + "' and quote_currency='" + a + "' ";

                                java.sql.Statement st = conn.createStatement();

                                // execute the query, and get a java resultset
                                ResultSet rsq = st.executeQuery(query1);

                                // iterate through the java resultset
                                while (rsq.next()) {
                                    String s1 = rsq.getString("base_currency");
                                    String s2 = rsq.getString("quote_currency");
                                    double s3 = rsq.getDouble("exchange_rate");
                                    double s4 = rsq.getDouble("amount_traded_in");
                                    double s5 = rsq.getDouble("amount_traded_out");

                                    String s6 = rsq.getString("region");
                                    String s7 = rsq.getString("branch");
                                    java.sql.Date sqlDate = rsq.getDate("date");
                                    String s8 = rsq.getString("cashier");

                                    mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                    int sum = 0;
                                }
                                boolean mb = rsq.isAfterLast();
                                if (mb == false) {
                                    JOptionPane.showMessageDialog(null, "There are no sales recorded on " + fb7.getDate() + "\nfor " + fb6.getSelectedItem().toString() + " " + "receiving other foreign curencies whilst selling foreign currencies", "Transaction record not found", JOptionPane.WARNING_MESSAGE);
                                }

                            } catch (ClassNotFoundException | SQLException er) {
                                JOptionPane.showMessageDialog(null, er.getMessage());
                            }

                        }
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), " Something went wrong please contact your database administrator or the vendor", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        if (!fb4.isSelected()) {
            if (fb1.isSelected() && !fb2.isSelected()) {
                String query = "SELECT base_currency,short_code FROM currencies where base_currency='" + true + "'";
                try {
                    Class.forName(DB_DRIVER);
                    con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                    stm = con.createStatement();
                    rs = stm.executeQuery(query);
                    boolean bool = rs.isBeforeFirst();
                    while (rs.next()) {
                        if (bool == true) {
                            String a = rs.getString("short_code");
                            try {
                                Class.forName(DB_DRIVER);
                                java.sql.Connection conn = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                String query1 = "SELECT base_currency,quote_currency,region,branch,exchange_rate,amount_traded_in,amount_traded_out,date,cashier FROM transactions_history where date='" + fw1.getText() + "' and operation='" + op + "'  and region='" + fb5.getSelectedItem().toString() + "' and quote_currency='" + a + "' and base_currency!='" + a + "'";

                                java.sql.Statement st = conn.createStatement();

                                // execute the query, and get a java resultset
                                ResultSet rsq = st.executeQuery(query1);
                                boolean swm = rsq.isBeforeFirst();
                                // iterate through the java resultset
                                while (rsq.next()) {
                                    String s1 = rsq.getString("base_currency");
                                    String s2 = rsq.getString("quote_currency");
                                    double s3 = rsq.getDouble("exchange_rate");
                                    double s4 = rsq.getDouble("amount_traded_in");
                                    double s5 = rsq.getDouble("amount_traded_out");

                                    String s6 = rsq.getString("region");
                                    String s7 = rsq.getString("branch");
                                    java.sql.Date sqlDate = rsq.getDate("date");
                                    String s8 = rsq.getString("cashier");

                                    mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                    int sum = 0;
                                }
                                boolean mb = rsq.isAfterLast();
                                if (mb == false) {
                                    JOptionPane.showMessageDialog(null, "There are no sales recorded on " + fb7.getDate() + "\n within " + fb5.getSelectedItem().toString() + " " + "receiving local curency whilst selling foreign currencies", "Transaction record not found", JOptionPane.WARNING_MESSAGE);
                                }

                            } catch (ClassNotFoundException | SQLException er) {
                                JOptionPane.showMessageDialog(null, er.getMessage());
                            }

                        }
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), " Something went wrong please contact your database administrator or the vendor", JOptionPane.ERROR_MESSAGE);
                }

            }
            if (!fb1.isSelected() && fb2.isSelected()) {
                String query = "SELECT base_currency,short_code FROM currencies where base_currency='" + true + "'";
                try {
                    Class.forName(DB_DRIVER);
                    con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                    stm = con.createStatement();
                    rs = stm.executeQuery(query);
                    boolean bool = rs.isBeforeFirst();
                    while (rs.next()) {
                        if (bool == true) {
                            String a = rs.getString("short_code");
                            try {
                                Class.forName(DB_DRIVER);
                                java.sql.Connection conn = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                String query1 = "SELECT base_currency,quote_currency,region,branch,exchange_rate,amount_traded_in,amount_traded_out,date,cashier FROM transactions_history where date='" + fw1.getText() + "' and operation='" + op + "'  and region='" + fb5.getSelectedItem().toString() + "' and quote_currency!='" + a + "' and base_currency!='" + a + "' ";

                                java.sql.Statement st = conn.createStatement();

                                // execute the query, and get a java resultset
                                ResultSet rsq = st.executeQuery(query1);

                                // iterate through the java resultset
                                while (rsq.next()) {
                                    String s1 = rsq.getString("base_currency");
                                    String s2 = rsq.getString("quote_currency");
                                    double s3 = rsq.getDouble("exchange_rate");
                                    double s4 = rsq.getDouble("amount_traded_in");
                                    double s5 = rsq.getDouble("amount_traded_out");

                                    String s6 = rsq.getString("region");
                                    String s7 = rsq.getString("branch");
                                    java.sql.Date sqlDate = rsq.getDate("date");
                                    String s8 = rsq.getString("cashier");

                                    mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                    int sum = 0;
                                }
                                boolean mb = rsq.isAfterLast();
                                if (mb == false) {
                                    JOptionPane.showMessageDialog(null, "There are no sales recorded on " + fb7.getDate() + "\n within " + fb5.getSelectedItem().toString() + " " + "receiving foreign currencie whilst selling other foreign currencies", "Transaction record not found", JOptionPane.WARNING_MESSAGE);
                                }

                            } catch (ClassNotFoundException | SQLException er) {
                                JOptionPane.showMessageDialog(null, er.getMessage());
                            }

                        }
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), " Something went wrong ", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_jLabel91MouseClicked

    private void jLabel93MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel93MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel93MouseClicked

    private void jLabel137MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel137MouseClicked
       DB_USER = uname.getText();
        DB_PAS = upass.getText();
        if (zm_table.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "The table is empty, there's nothing to save. I can not allow you to save empty files of files with 0KB", "Providence says...", JOptionPane.ERROR_MESSAGE);
        }
        if (zm_table.getRowCount() > 0) {
            String system_name = System.getProperty("user.name");
            FileOutputStream fstream = null;
            BufferedOutputStream bstream = null;
            XSSFWorkbook wk_book = null;
            JFileChooser jfile = new JFileChooser("C:\\" + system_name + "\\Desktop");
            jfile.setDialogTitle("Save your files");
            FileNameExtensionFilter fext = new FileNameExtensionFilter("EXCEL FILES", "xlsx", "xls", "xlsm");
            jfile.setFileFilter(fext);
            //jfile.showSaveDialog(null);
            int excel = jfile.showSaveDialog(null);
            if (excel == JFileChooser.APPROVE_OPTION) {

                try {
                    wk_book = new XSSFWorkbook();
                    XSSFSheet sheet = wk_book.createSheet("My sheet");
                    for (int i = 0; i < zm_table.getRowCount(); i++) {
                        XSSFRow excelRow = sheet.createRow(i);
                        for (int j = 0; j < zm_table.getColumnCount(); j++) {
                            XSSFCell excelCell = excelRow.createCell(j);

                            excelCell.setCellValue(zm_table.getValueAt(i, j).toString());
                        }
                    }
                    fstream = new FileOutputStream(jfile.getSelectedFile() + ".xlsx");
                    bstream = new BufferedOutputStream(fstream);
                    wk_book.write(bstream);
                } catch (FileNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                } finally {
                    try {
                        if (bstream != null) {
                            bstream.close();
                        }
                        if (fstream != null) {
                            fstream.close();
                        }

                        if (wk_book != null) {
                            wk_book.close();
                        }

                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    }
                }

            }
        }
    }//GEN-LAST:event_jLabel137MouseClicked

    private void jLabel145MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel145MouseClicked
       DB_USER = uname.getText();
        DB_PAS = upass.getText();
        if (zm_table.getRowCount() < 1) {
            JOptionPane.showMessageDialog(null, "The table is empty, so you can not print an empty file", "P rovidence says....", JOptionPane.ERROR_MESSAGE);
        }
        if (zm_table.getRowCount() >= 1) {
            MessageFormat header = new MessageFormat("\t\tBureau De Change System : Monthly Sales Reports ");
            MessageFormat footer = new MessageFormat("Page{0, number, integer}");
            try {
                zm_table.print(JTable.PrintMode.NORMAL, header, footer);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Printing Terminated", e.getMessage(), JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jLabel145MouseClicked

    private void jLabel152MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel152MouseClicked
        DefaultTableModel mod = (DefaultTableModel) zm_table.getModel();
        mod.setRowCount(0);
        java.util.Date date = new java.util.Date();
        long t = date.getTime();
        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        String op = "selling";

        if (fc4.isSelected()) {
            if (fc1.isSelected() && !fc2.isSelected()) {
                String query = "SELECT base_currency,short_code FROM currencies where base_currency='" + true + "'";
                try {
                    Class.forName(DB_DRIVER);
                    con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                    stm = con.createStatement();
                    rs = stm.executeQuery(query);
                    boolean bool = rs.isBeforeFirst();
                    while (rs.next()) {
                        if (bool == true) {
                            String a = rs.getString("short_code");
                            try {
                                Class.forName(DB_DRIVER);
                                java.sql.Connection conn = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                String query1 = "SELECT base_currency,quote_currency,region,branch,exchange_rate,amount_traded_in,amount_traded_out,date,cashier FROM transactions_history where region='" + fc5.getSelectedItem().toString() + "' and operation='" + op + "' and branch='" + fc6.getSelectedItem().toString() + "' and quote_currency='" + a + "'";

                                java.sql.Statement st = conn.createStatement();

                                // execute the query, and get a java resultset
                                ResultSet rsq = st.executeQuery(query1);

                                // iterate through the java resultset
                                while (rsq.next()) {
                                    String s1 = rsq.getString("base_currency");
                                    String s2 = rsq.getString("quote_currency");
                                    double s3 = rsq.getDouble("exchange_rate");
                                    double s4 = rsq.getDouble("amount_traded_in");
                                    double s5 = rsq.getDouble("amount_traded_out");

                                    String s6 = rsq.getString("region");
                                    String s7 = rsq.getString("branch");
                                    java.sql.Date sqlDate = rsq.getDate("date");
                                    String s8 = rsq.getString("cashier");
                                    double sum = 0;

                                    String wq = sqlDate.toString();
                                    String qw = wq.substring(0, 4);
                                    int af = Integer.parseInt(sym.getValue().toString());
                                    int ag = Integer.parseInt(qw);

                                    if (zm1.getValue().equals("September") && af == ag) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "09";

                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }

                                    if (zm1.getValue().equals("January") && af == ag) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "01";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("February") && af == ag) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "02";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("March") && af == ag) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "03";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("April") && af == ag) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "04";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("May") && af == ag) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "05";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("June") && af == ag) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "06";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("July") && af == ag) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "07";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("August") && af == ag) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "08";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("October") && af == ag) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "10";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("November") && af == ag) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "11";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("December") && af == ag) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "12";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }

                                    //int sum = 0;
                                }
                                boolean mb = rsq.isAfterLast();
                                if (mb == false) {
                                    JOptionPane.showMessageDialog(null, "There are no sales recorded in the month of " + zm1.getValue() + "\n for " + fc6.getSelectedItem().toString() + " " + "receiving local curency whilst selling foreign currencies", "Transaction record not found", JOptionPane.WARNING_MESSAGE);
                                }

                            } catch (ClassNotFoundException | SQLException er) {
                                JOptionPane.showMessageDialog(null, er.getMessage());

                            }

                        }
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), " Something went wrong please contact your database administrator or the vendor", JOptionPane.ERROR_MESSAGE);
                }

            }
            if (!fc1.isSelected() && fc2.isSelected()) {
                String query = "SELECT base_currency,short_code FROM currencies where base_currency='" + true + "'";
                try {
                    Class.forName(DB_DRIVER);
                    con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                    stm = con.createStatement();
                    rs = stm.executeQuery(query);
                    boolean bool = rs.isBeforeFirst();
                    while (rs.next()) {
                        if (bool == true) {
                            String a = rs.getString("short_code");
                            try {
                                Class.forName(DB_DRIVER);
                                java.sql.Connection conn = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                String query1 = "SELECT base_currency,quote_currency,region,branch,exchange_rate,amount_traded_in,amount_traded_out,date,cashier FROM transactions_history where region='" + fc5.getSelectedItem().toString() + "' and operation='" + op + "' and quote_currency='" + a + "' ";

                                java.sql.Statement st = conn.createStatement();

                                // execute the query, and get a java resultset
                                ResultSet rsq = st.executeQuery(query1);

                                // iterate through the java resultset
                                while (rsq.next()) {
                                    String s1 = rsq.getString("base_currency");
                                    String s2 = rsq.getString("quote_currency");
                                    double s3 = rsq.getDouble("exchange_rate");
                                    double s4 = rsq.getDouble("amount_traded_in");
                                    double s5 = rsq.getDouble("amount_traded_out");

                                    String s6 = rsq.getString("region");
                                    String s7 = rsq.getString("branch");
                                    java.sql.Date sqlDate = rsq.getDate("date");
                                    String s8 = rsq.getString("cashier");
                                    double sum = 0;
                                    if (zm1.getValue().equals("September")) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "09";

                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }

                                    if (zm1.getValue().equals("January")) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "01";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("February")) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "02";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("March")) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "03";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("April")) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "04";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("May")) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "05";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("June")) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "06";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("July")) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "07";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("August")) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "08";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("October")) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "10";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("November")) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "11";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("December")) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "12";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                }
                                boolean mb = rsq.isAfterLast();
                                if (mb == false) {
                                    JOptionPane.showMessageDialog(null, "There are no sales recorded in the month of " + zm1.getValue() + "\n for " + fc6.getSelectedItem().toString() + " " + "receiving foreign currencies whilst selling other foreign currencies", "Transaction record not found", JOptionPane.WARNING_MESSAGE);
                                }

                            } catch (ClassNotFoundException | SQLException er) {
                                JOptionPane.showMessageDialog(null, er.getMessage());
                            }

                        }
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), " Something went wrong please contact your database administrator or your vendor ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        if (!fc4.isSelected()) {
            if (fc1.isSelected() && !fc2.isSelected()) {
                String query = "SELECT base_currency,short_code FROM currencies where base_currency='" + true + "'";
                try {
                    Class.forName(DB_DRIVER);
                    con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                    stm = con.createStatement();
                    rs = stm.executeQuery(query);
                    boolean bool = rs.isBeforeFirst();
                    while (rs.next()) {
                        if (bool == true) {
                            String a = rs.getString("short_code");
                            try {
                                Class.forName(DB_DRIVER);
                                java.sql.Connection conn = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                String query1 = "SELECT base_currency,quote_currency,region,branch,exchange_rate,amount_traded_in,amount_traded_out,date,cashier FROM transactions_history where region='" + fc5.getSelectedItem().toString() + "' and operation='" + op + "'  and quote_currency='" + a + "' and base_currency!='" + a + "'";

                                java.sql.Statement st = conn.createStatement();

                                // execute the query, and get a java resultset
                                ResultSet rsq = st.executeQuery(query1);
                                boolean swm = rsq.isBeforeFirst();
                                // iterate through the java resultset
                                while (rsq.next()) {
                                    String s1 = rsq.getString("base_currency");
                                    String s2 = rsq.getString("quote_currency");
                                    double s3 = rsq.getDouble("exchange_rate");
                                    double s4 = rsq.getDouble("amount_traded_in");
                                    double s5 = rsq.getDouble("amount_traded_out");

                                    String s6 = rsq.getString("region");
                                    String s7 = rsq.getString("branch");
                                    java.sql.Date sqlDate = rsq.getDate("date");
                                    String s8 = rsq.getString("cashier");

                                    double sum = 0;
                                    if (zm1.getValue().equals("September")) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "09";

                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }

                                    if (zm1.getValue().equals("January")) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "01";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("February")) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "02";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("March")) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "03";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("April")) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "04";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("May")) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "05";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("June")) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "06";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("July")) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "07";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("August")) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "08";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("October")) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "10";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("November")) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "11";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("December")) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "12";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                }
                                boolean mb = rsq.isAfterLast();
                                if (mb == false) {
                                    JOptionPane.showMessageDialog(null, "There are no sales recorded in the month of " + zm1.getValue() + "\n within " + fc5.getSelectedItem().toString() + " " + "receiving local curency whilst selling foreign currencies", "Transaction record not found", JOptionPane.WARNING_MESSAGE);
                                }

                            } catch (ClassNotFoundException | SQLException er) {
                                JOptionPane.showMessageDialog(null, er.getMessage());
                            }

                        }
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), " Something went wrong ", JOptionPane.ERROR_MESSAGE);
                }

            }
            if (!fc1.isSelected() && fc2.isSelected()) {
                String query = "SELECT base_currency,short_code FROM currencies where base_currency='" + true + "'";
                try {
                    Class.forName(DB_DRIVER);
                    con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                    stm = con.createStatement();
                    rs = stm.executeQuery(query);
                    boolean bool = rs.isBeforeFirst();
                    while (rs.next()) {
                        if (bool == true) {
                            String a = rs.getString("short_code");
                            try {
                                Class.forName(DB_DRIVER);
                                java.sql.Connection conn = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                String query1 = "SELECT base_currency,quote_currency,region,branch,exchange_rate,amount_traded_in,amount_traded_out,date,cashier FROM transactions_history where region='" + fc5.getSelectedItem().toString() + "' and operation='" + op + "' and quote_currency='" + a + "' and base_currency!='" + a + "' ";

                                java.sql.Statement st = conn.createStatement();

                                // execute the query, and get a java resultset
                                ResultSet rsq = st.executeQuery(query1);

                                // iterate through the java resultset
                                while (rsq.next()) {
                                    String s1 = rsq.getString("base_currency");
                                    String s2 = rsq.getString("quote_currency");
                                    double s3 = rsq.getDouble("exchange_rate");
                                    double s4 = rsq.getDouble("amount_traded_in");
                                    double s5 = rsq.getDouble("amount_traded_out");

                                    String s6 = rsq.getString("region");
                                    String s7 = rsq.getString("branch");
                                    java.sql.Date sqlDate = rsq.getDate("date");
                                    String s8 = rsq.getString("cashier");

                                    double sum = 0;
                                    if (zm1.getValue().equals("September")) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "09";

                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }

                                    if (zm1.getValue().equals("January")) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "01";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("February")) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "02";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("March")) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "03";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("April")) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "04";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("May")) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "05";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("June")) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "06";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("July")) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "07";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("August")) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "08";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("October")) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "10";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("November")) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "11";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("December")) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "12";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                }
                                boolean mb = rsq.isAfterLast();
                                if (mb == false) {
                                    JOptionPane.showMessageDialog(null, "There are no sales recorded in the month of " + zm1.getValue() + "\n within " + fc5.getSelectedItem().toString() + " " + "receiving foreign currencies whilst selling other foreign currencies", "Transaction record not found", JOptionPane.WARNING_MESSAGE);
                                }

                            } catch (ClassNotFoundException | SQLException er) {
                                JOptionPane.showMessageDialog(null, er.getMessage());
                            }

                        }
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), " Something went wrong please contact your database administrator or the vendor ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_jLabel152MouseClicked

    private void jLabel153MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel153MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel153MouseClicked

    private void jLabel175MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel175MouseClicked
       DB_USER = uname.getText();
        DB_PAS = upass.getText();
        if (fd_table.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "The table is empty, there's nothing to save. I can not allow you to save empty files of files with 0KB", "Providence says...", JOptionPane.ERROR_MESSAGE);
        }
        if (fd_table.getRowCount() > 0) {
            String system_name = System.getProperty("user.name");
            FileOutputStream fstream = null;
            BufferedOutputStream bstream = null;
            XSSFWorkbook wk_book = null;
            JFileChooser jfile = new JFileChooser("C:\\" + system_name + "\\Desktop");
            jfile.setDialogTitle("Save your files");
            FileNameExtensionFilter fext = new FileNameExtensionFilter("EXCEL FILES", "xlsx", "xls", "xlsm");
            jfile.setFileFilter(fext);
            //jfile.showSaveDialog(null);
            int excel = jfile.showSaveDialog(null);
            if (excel == JFileChooser.APPROVE_OPTION) {

                try {
                    wk_book = new XSSFWorkbook();
                    XSSFSheet sheet = wk_book.createSheet("My sheet");
                    for (int i = 0; i < fd_table.getRowCount(); i++) {
                        XSSFRow excelRow = sheet.createRow(i);
                        for (int j = 0; j < fd_table.getColumnCount(); j++) {
                            XSSFCell excelCell = excelRow.createCell(j);

                            excelCell.setCellValue(fd_table.getValueAt(i, j).toString());
                        }
                    }
                    fstream = new FileOutputStream(jfile.getSelectedFile() + ".xlsx");
                    bstream = new BufferedOutputStream(fstream);
                    wk_book.write(bstream);
                } catch (FileNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                } finally {
                    try {
                        if (bstream != null) {
                            bstream.close();
                        }
                        if (fstream != null) {
                            fstream.close();
                        }

                        if (wk_book != null) {
                            wk_book.close();
                        }

                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    }
                }

            }
        }
    }//GEN-LAST:event_jLabel175MouseClicked

    private void jLabel198MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel198MouseClicked
            if (fd_table.getRowCount() < 1) {
            JOptionPane.showMessageDialog(null, "The table is empty, so you can not print an empty file", "P rovidence says....", JOptionPane.ERROR_MESSAGE);
        }
        if (fd_table.getRowCount() >= 1) {
            MessageFormat header = new MessageFormat("\t\tBureau De Change System : Annual Sales Reports ");
            MessageFormat footer = new MessageFormat("Page{0, number, integer}");
            try {
                fd_table.print(JTable.PrintMode.NORMAL, header, footer);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Printing Terminated", e.getMessage(), JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jLabel198MouseClicked

    private void jLabel199MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel199MouseClicked
       DefaultTableModel mod = (DefaultTableModel) fd_table.getModel();
        mod.setRowCount(0);
        java.util.Date date = new java.util.Date();
        long t = date.getTime();
        String op = "selling";
        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        if (fd4.isSelected()) {
            if (fd1.isSelected() && !fd2.isSelected()) {
                String query = "SELECT base_currency,short_code FROM currencies where base_currency='" + true + "'";
                try {
                    Class.forName(DB_DRIVER);
                    con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                    stm = con.createStatement();
                    rs = stm.executeQuery(query);
                    boolean bool = rs.isBeforeFirst();
                    while (rs.next()) {
                        if (bool == true) {
                            String a = rs.getString("short_code");
                            try {
                                Class.forName(DB_DRIVER);
                                java.sql.Connection conn = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                String query1 = "SELECT base_currency,quote_currency,region,branch,exchange_rate,amount_traded_in,amount_traded_out,date,cashier FROM transactions_history where region='" + fd5.getSelectedItem().toString() + "' and branch='" + fd6.getSelectedItem().toString() + "' and operation='" + op + "' and quote_currency='" + a + "'";

                                java.sql.Statement st = conn.createStatement();

                                // execute the query, and get a java resultset
                                ResultSet rsq = st.executeQuery(query1);

                                // iterate through the java resultset
                                while (rsq.next()) {
                                    String s1 = rsq.getString("base_currency");
                                    String s2 = rsq.getString("quote_currency");
                                    double s3 = rsq.getDouble("exchange_rate");
                                    double s4 = rsq.getDouble("amount_traded_in");
                                    double s5 = rsq.getDouble("amount_traded_out");

                                    String s6 = rsq.getString("region");
                                    String s7 = rsq.getString("branch");
                                    java.sql.Date sqlDate = rsq.getDate("date");
                                    String s8 = rsq.getString("cashier");
                                    String ht = sqlDate.toString();
                                    String dt = ht.substring(0, 4);
                                    int af = Integer.parseInt(fd7.getValue().toString());
                                    int ag = Integer.parseInt(dt);
                                    if (af == ag) {
                                        mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                    }
                                    //int sum = 0;
                                }

                            } catch (ClassNotFoundException | SQLException er) {
                                JOptionPane.showMessageDialog(null, er.getMessage());

                            }

                        }
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), " Something went wrong pleasec contact your database adminiistrator or the vendor", JOptionPane.ERROR_MESSAGE);
                }

            }
            if (!fd1.isSelected() && fd2.isSelected()) {
                String query = "SELECT base_currency,short_code FROM currencies where base_currency='" + true + "'";
                try {
                    Class.forName(DB_DRIVER);
                    con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                    stm = con.createStatement();
                    rs = stm.executeQuery(query);
                    boolean bool = rs.isBeforeFirst();
                    while (rs.next()) {
                        if (bool == true) {
                            String a = rs.getString("short_code");
                            try {
                                Class.forName(DB_DRIVER);
                                java.sql.Connection conn = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                String query1 = "SELECT base_currency,quote_currency,region,branch,exchange_rate,amount_traded_in,amount_traded_out,date,cashier FROM transactions_history where region='" + fd5.getSelectedItem().toString() + "' and quote_currency='" + a + "' and operation='" + op + "' ";

                                java.sql.Statement st = conn.createStatement();

                                // execute the query, and get a java resultset
                                ResultSet rsq = st.executeQuery(query1);

                                // iterate through the java resultset
                                while (rsq.next()) {
                                    String s1 = rsq.getString("base_currency");
                                    String s2 = rsq.getString("quote_currency");
                                    double s3 = rsq.getDouble("exchange_rate");
                                    double s4 = rsq.getDouble("amount_traded_in");
                                    double s5 = rsq.getDouble("amount_traded_out");

                                    String s6 = rsq.getString("region");
                                    String s7 = rsq.getString("branch");
                                    java.sql.Date sqlDate = rsq.getDate("date");
                                    String s8 = rsq.getString("cashier");
                                    String ht = sqlDate.toString();
                                    String dt = ht.substring(0, 4);
                                    int af = Integer.parseInt(fd7.getValue().toString());
                                    int ag = Integer.parseInt(dt);
                                    if (af == ag) {
                                        mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                    }
                                }

                            } catch (ClassNotFoundException | SQLException er) {
                                JOptionPane.showMessageDialog(null, er.getMessage());
                            }

                        }
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), " Something went wrong please contact your dataase administrator ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        if (!fd4.isSelected()) {
            if (fd1.isSelected() && !fd2.isSelected()) {
                String query = "SELECT base_currency,short_code FROM currencies where base_currency='" + true + "'";
                try {
                    Class.forName(DB_DRIVER);
                    con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                    stm = con.createStatement();
                    rs = stm.executeQuery(query);
                    boolean bool = rs.isBeforeFirst();
                    while (rs.next()) {
                        if (bool == true) {
                            String a = rs.getString("short_code");
                            try {
                                Class.forName(DB_DRIVER);
                                java.sql.Connection conn = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                String query1 = "SELECT base_currency,quote_currency,region,branch,exchange_rate,amount_traded_in,amount_traded_out,date,cashier FROM transactions_history where region='" + fd5.getSelectedItem().toString() + "' and operation='" + op + "' and quote_currency='" + a + "' and base_currency!='" + a + "'";

                                java.sql.Statement st = conn.createStatement();

                                // execute the query, and get a java resultset
                                ResultSet rsq = st.executeQuery(query1);
                                boolean swm = rsq.isBeforeFirst();
                                // iterate through the java resultset
                                while (rsq.next()) {
                                    String s1 = rsq.getString("base_currency");
                                    String s2 = rsq.getString("quote_currency");
                                    double s3 = rsq.getDouble("exchange_rate");
                                    double s4 = rsq.getDouble("amount_traded_in");
                                    double s5 = rsq.getDouble("amount_traded_out");

                                    String s6 = rsq.getString("region");
                                    String s7 = rsq.getString("branch");
                                    java.sql.Date sqlDate = rsq.getDate("date");
                                    String s8 = rsq.getString("cashier");
                                    String ht = sqlDate.toString();
                                    String dt = ht.substring(0, 4);
                                    int af = Integer.parseInt(fd7.getValue().toString());
                                    int ag = Integer.parseInt(dt);
                                    if (af == ag) {
                                        mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                    }
                                }

                            } catch (ClassNotFoundException | SQLException er) {
                                JOptionPane.showMessageDialog(null, er.getMessage());
                            }

                        }
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), " Something went wrong please contact your database administrator or the vendor ", JOptionPane.ERROR_MESSAGE);
                }

            }
            if (!fd1.isSelected() && fd2.isSelected()) {
                String query = "SELECT base_currency,short_code FROM currencies where base_currency='" + true + "'";
                try {
                    Class.forName(DB_DRIVER);
                    con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                    stm = con.createStatement();
                    rs = stm.executeQuery(query);
                    boolean bool = rs.isBeforeFirst();
                    while (rs.next()) {
                        if (bool == true) {
                            String a = rs.getString("short_code");
                            try {
                                Class.forName(DB_DRIVER);
                                java.sql.Connection conn = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                String query1 = "SELECT base_currency,quote_currency,region,branch,exchange_rate,amount_traded_in,amount_traded_out,date,cashier FROM transactions_history where region='" + fd5.getSelectedItem().toString() + "' and operation='" + op + "' and quote_currency='" + a + "' and base_currency!='" + a + "' ";

                                java.sql.Statement st = conn.createStatement();

                                // execute the query, and get a java resultset
                                ResultSet rsq = st.executeQuery(query1);

                                // iterate through the java resultset
                                while (rsq.next()) {
                                    String s1 = rsq.getString("base_currency");
                                    String s2 = rsq.getString("quote_currency");
                                    double s3 = rsq.getDouble("exchange_rate");
                                    double s4 = rsq.getDouble("amount_traded_in");
                                    double s5 = rsq.getDouble("amount_traded_out");

                                    String s6 = rsq.getString("region");
                                    String s7 = rsq.getString("branch");
                                    java.sql.Date sqlDate = rsq.getDate("date");
                                    String s8 = rsq.getString("cashier");
                                    String ht = sqlDate.toString();
                                    String dt = ht.substring(0, 4);
                                    int af = Integer.parseInt(fd7.getValue().toString());
                                    int ag = Integer.parseInt(dt);
                                    if (af == ag) {
                                        mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                    }

                                }

                            } catch (ClassNotFoundException | SQLException er) {
                                JOptionPane.showMessageDialog(null, er.getMessage());
                            }

                        }
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), " Something went wrong please contact your database administrator or the vendor", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_jLabel199MouseClicked

    private void jLabel200MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel200MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel200MouseClicked

    private void jLabel202MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel202MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel202MouseClicked

    private void jLabel203MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel203MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel203MouseClicked

    private void jLabel204MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel204MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel204MouseClicked

    private void jLabel205MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel205MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel205MouseClicked

    private void jLabel207MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel207MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel207MouseClicked

    private void jLabel208MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel208MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel208MouseClicked

    private void jLabel209MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel209MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel209MouseClicked

    private void jLabel210MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel210MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel210MouseClicked

    private void jLabel212MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel212MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel212MouseClicked

    private void jLabel213MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel213MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel213MouseClicked

    private void jLabel214MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel214MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel214MouseClicked

    private void jLabel215MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel215MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel215MouseClicked

    private void jLabel217MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel217MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel217MouseClicked

    private void jLabel218MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel218MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel218MouseClicked

    private void jLabel219MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel219MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel219MouseClicked

    private void jLabel220MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel220MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel220MouseClicked

    private void jLabel43MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel43MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel43MouseClicked
    int y = 0;

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Admininstrator2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Admininstrator2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Admininstrator2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Admininstrator2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Admininstrator2().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFormattedTextField a1;
    private javax.swing.JPasswordField a10;
    private javax.swing.JFormattedTextField a2;
    private javax.swing.JComboBox<String> a3;
    private javax.swing.JFormattedTextField a4;
    private javax.swing.JFormattedTextField a5;
    private javax.swing.JComboBox<String> a6;
    private javax.swing.JComboBox<String> a7;
    private javax.swing.JFormattedTextField a8;
    private javax.swing.JPasswordField a9;
    private javax.swing.JTable a_table;
    private javax.swing.JComboBox<String> acc_currency;
    private javax.swing.JTable acc_table;
    private javax.swing.JFormattedTextField acc_total;
    private javax.swing.JLabel animat;
    private javax.swing.JPanel animation;
    private javax.swing.JComboBox<String> b1;
    private javax.swing.JFormattedTextField b2;
    private javax.swing.JFormattedTextField b3;
    private javax.swing.JEditorPane b4;
    private javax.swing.JLabel bfc;
    private javax.swing.JPanel branches;
    private javax.swing.JLabel bscd;
    private javax.swing.JComboBox<String> ccr1;
    private javax.swing.JComboBox<String> ccr2;
    private javax.swing.JFormattedTextField ccr3;
    private javax.swing.JFormattedTextField ccr4;
    private javax.swing.JComboBox<String> cg1;
    private javax.swing.JComboBox<String> cg2;
    private javax.swing.JComboBox<String> cg3;
    private javax.swing.JFormattedTextField cg4;
    private javax.swing.JTable cg_table;
    public static final javax.swing.JLabel configs = new javax.swing.JLabel();
    private javax.swing.JLabel configs2;
    private javax.swing.JComboBox<String> cp1;
    private javax.swing.JComboBox<String> cp2;
    private javax.swing.JComboBox<String> cp3;
    private javax.swing.JFormattedTextField cp4;
    private javax.swing.JTable cp_table;
    private javax.swing.JPanel currencies;
    private javax.swing.JTable d_table;
    private javax.swing.JPanel dashboard_home;
    private javax.swing.JPanel dashboard_master;
    private javax.swing.JPanel dashboard_operations;
    private javax.swing.JPanel dashboard_rates;
    private javax.swing.JPanel dashboard_reports;
    private javax.swing.JLabel day;
    private javax.swing.JPanel denominations;
    private javax.swing.JFormattedTextField dv1;
    private javax.swing.JFormattedTextField dv2;
    private javax.swing.JFormattedTextField dv3;
    private javax.swing.JFormattedTextField dv4;
    private javax.swing.JPanel eco1;
    private javax.swing.JPanel eco10;
    private javax.swing.JPanel eco11;
    private javax.swing.JPanel eco12;
    private javax.swing.JPanel eco13;
    private javax.swing.JPanel eco14;
    private javax.swing.JPanel eco15;
    private javax.swing.JPanel eco16;
    private javax.swing.JPanel eco17;
    private javax.swing.JPanel eco18;
    private javax.swing.JPanel eco19;
    private javax.swing.JPanel eco2;
    private javax.swing.JPanel eco20;
    private javax.swing.JPanel eco21;
    private javax.swing.JPanel eco22;
    private javax.swing.JPanel eco23;
    private javax.swing.JPanel eco24;
    private javax.swing.JPanel eco25;
    private javax.swing.JPanel eco26;
    private javax.swing.JPanel eco27;
    private javax.swing.JPanel eco28;
    private javax.swing.JPanel eco29;
    private javax.swing.JPanel eco3;
    private javax.swing.JPanel eco30;
    private javax.swing.JPanel eco31;
    private javax.swing.JPanel eco32;
    private javax.swing.JPanel eco33;
    private javax.swing.JPanel eco34;
    private javax.swing.JPanel eco35;
    private javax.swing.JPanel eco36;
    private javax.swing.JPanel eco37;
    private javax.swing.JPanel eco38;
    private javax.swing.JPanel eco39;
    private javax.swing.JPanel eco4;
    private javax.swing.JPanel eco40;
    private javax.swing.JPanel eco5;
    private javax.swing.JPanel eco6;
    private javax.swing.JPanel eco7;
    private javax.swing.JPanel eco8;
    private javax.swing.JPanel eco9;
    private javax.swing.JCheckBox fa1;
    private javax.swing.JCheckBox fa2;
    private javax.swing.JCheckBox fa3;
    private javax.swing.JCheckBox fa4;
    public static final javax.swing.JComboBox<String> fa5 = new javax.swing.JComboBox<>();
    private javax.swing.JComboBox<String> fa6;
    private javax.swing.JCheckBox fb1;
    private javax.swing.JCheckBox fb2;
    private javax.swing.JCheckBox fb3;
    private javax.swing.JCheckBox fb4;
    private javax.swing.JComboBox<String> fb5;
    private javax.swing.JComboBox<String> fb6;
    private com.toedter.calendar.JDateChooser fb7;
    private javax.swing.JTable fb_table;
    private javax.swing.JCheckBox fc1;
    private javax.swing.JCheckBox fc2;
    private javax.swing.JCheckBox fc3;
    private javax.swing.JCheckBox fc4;
    private javax.swing.JComboBox<String> fc5;
    private javax.swing.JComboBox<String> fc6;
    private javax.swing.JCheckBox fd1;
    private javax.swing.JCheckBox fd2;
    private javax.swing.JCheckBox fd3;
    private javax.swing.JCheckBox fd4;
    private javax.swing.JComboBox<String> fd5;
    private javax.swing.JComboBox<String> fd6;
    private javax.swing.JSpinner fd7;
    private javax.swing.JTable fd_table;
    private javax.swing.JLabel fw1;
    private javax.swing.JLabel fw2;
    private javax.swing.JPanel header;
    private javax.swing.JComboBox<String> jComboBox14;
    private javax.swing.JComboBox<String> jComboBox16;
    private javax.swing.JComboBox<String> jComboBox17;
    private javax.swing.JComboBox<String> jComboBox18;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JEditorPane jEditorPane3;
    private javax.swing.JFormattedTextField jFormattedTextField16;
    private javax.swing.JFormattedTextField jFormattedTextField21;
    private javax.swing.JFormattedTextField jFormattedTextField22;
    private javax.swing.JFormattedTextField jFormattedTextField25;
    private javax.swing.JFormattedTextField jFormattedTextField4;
    private javax.swing.JFormattedTextField jFormattedTextField44;
    private javax.swing.JFormattedTextField jFormattedTextField5;
    private javax.swing.JFormattedTextField jFormattedTextField6;
    private javax.swing.JFormattedTextField jFormattedTextField7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel102;
    private javax.swing.JLabel jLabel103;
    private javax.swing.JLabel jLabel104;
    private javax.swing.JLabel jLabel105;
    private javax.swing.JLabel jLabel106;
    private javax.swing.JLabel jLabel107;
    private javax.swing.JLabel jLabel108;
    private javax.swing.JLabel jLabel109;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel110;
    private javax.swing.JLabel jLabel111;
    private javax.swing.JLabel jLabel112;
    private javax.swing.JLabel jLabel113;
    private javax.swing.JLabel jLabel114;
    private javax.swing.JLabel jLabel115;
    private javax.swing.JLabel jLabel116;
    private javax.swing.JLabel jLabel117;
    private javax.swing.JLabel jLabel118;
    private javax.swing.JLabel jLabel119;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel120;
    private javax.swing.JLabel jLabel121;
    private javax.swing.JLabel jLabel122;
    private javax.swing.JLabel jLabel123;
    private javax.swing.JLabel jLabel124;
    private javax.swing.JLabel jLabel125;
    private javax.swing.JLabel jLabel126;
    private javax.swing.JLabel jLabel127;
    private javax.swing.JLabel jLabel128;
    private javax.swing.JLabel jLabel129;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel130;
    private javax.swing.JLabel jLabel133;
    private javax.swing.JLabel jLabel134;
    private javax.swing.JLabel jLabel136;
    private javax.swing.JLabel jLabel137;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel141;
    private javax.swing.JLabel jLabel142;
    private javax.swing.JLabel jLabel144;
    private javax.swing.JLabel jLabel145;
    private javax.swing.JLabel jLabel149;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel150;
    private javax.swing.JLabel jLabel152;
    private javax.swing.JLabel jLabel153;
    private javax.swing.JLabel jLabel154;
    private javax.swing.JLabel jLabel155;
    private javax.swing.JLabel jLabel156;
    private javax.swing.JLabel jLabel157;
    private javax.swing.JLabel jLabel158;
    private javax.swing.JLabel jLabel159;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel160;
    private javax.swing.JLabel jLabel161;
    private javax.swing.JLabel jLabel162;
    private javax.swing.JLabel jLabel163;
    private javax.swing.JLabel jLabel164;
    private javax.swing.JLabel jLabel165;
    private javax.swing.JLabel jLabel166;
    private javax.swing.JLabel jLabel167;
    private javax.swing.JLabel jLabel168;
    private javax.swing.JLabel jLabel169;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel170;
    private javax.swing.JLabel jLabel171;
    private javax.swing.JLabel jLabel172;
    private javax.swing.JLabel jLabel173;
    private javax.swing.JLabel jLabel174;
    private javax.swing.JLabel jLabel175;
    private javax.swing.JLabel jLabel176;
    private javax.swing.JLabel jLabel177;
    private javax.swing.JLabel jLabel178;
    private javax.swing.JLabel jLabel179;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel180;
    private javax.swing.JLabel jLabel181;
    private javax.swing.JLabel jLabel182;
    private javax.swing.JLabel jLabel183;
    private javax.swing.JLabel jLabel184;
    private javax.swing.JLabel jLabel185;
    private javax.swing.JLabel jLabel186;
    private javax.swing.JLabel jLabel187;
    private javax.swing.JLabel jLabel188;
    private javax.swing.JLabel jLabel189;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel190;
    private javax.swing.JLabel jLabel191;
    private javax.swing.JLabel jLabel192;
    private javax.swing.JLabel jLabel193;
    private javax.swing.JLabel jLabel194;
    private javax.swing.JLabel jLabel195;
    private javax.swing.JLabel jLabel196;
    private javax.swing.JLabel jLabel197;
    private javax.swing.JLabel jLabel198;
    private javax.swing.JLabel jLabel199;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel200;
    private javax.swing.JLabel jLabel201;
    private javax.swing.JLabel jLabel202;
    private javax.swing.JLabel jLabel203;
    private javax.swing.JLabel jLabel204;
    private javax.swing.JLabel jLabel205;
    private javax.swing.JLabel jLabel206;
    private javax.swing.JLabel jLabel207;
    private javax.swing.JLabel jLabel208;
    private javax.swing.JLabel jLabel209;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel210;
    private javax.swing.JLabel jLabel211;
    private javax.swing.JLabel jLabel212;
    private javax.swing.JLabel jLabel213;
    private javax.swing.JLabel jLabel214;
    private javax.swing.JLabel jLabel215;
    private javax.swing.JLabel jLabel216;
    private javax.swing.JLabel jLabel217;
    private javax.swing.JLabel jLabel218;
    private javax.swing.JLabel jLabel219;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel220;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel45;
    private javax.swing.JPanel jPanel46;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel52;
    private javax.swing.JPanel jPanel53;
    private javax.swing.JPanel jPanel54;
    private javax.swing.JPanel jPanel56;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane19;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane20;
    private javax.swing.JScrollPane jScrollPane21;
    private javax.swing.JScrollPane jScrollPane22;
    private javax.swing.JScrollPane jScrollPane24;
    private javax.swing.JScrollPane jScrollPane25;
    private javax.swing.JScrollPane jScrollPane26;
    private javax.swing.JScrollPane jScrollPane29;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane30;
    private javax.swing.JScrollPane jScrollPane32;
    private javax.swing.JScrollPane jScrollPane35;
    private javax.swing.JScrollPane jScrollPane36;
    private javax.swing.JScrollPane jScrollPane37;
    private javax.swing.JScrollPane jScrollPane38;
    private javax.swing.JScrollPane jScrollPane39;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane40;
    private javax.swing.JScrollPane jScrollPane41;
    private javax.swing.JScrollPane jScrollPane42;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane10;
    private javax.swing.JTabbedPane jTabbedPane12;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTabbedPane jTabbedPane4;
    private javax.swing.JTabbedPane jTabbedPane5;
    private javax.swing.JTabbedPane jTabbedPane7;
    private javax.swing.JTabbedPane jTabbedPane8;
    private javax.swing.JTabbedPane jTabbedPane9;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable12;
    private javax.swing.JTable jTable13;
    private javax.swing.JTable jTable14;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jm_table1;
    private javax.swing.JLabel jpb1;
    private javax.swing.JLabel jpy1;
    public static final javax.swing.JLabel kb1 = new javax.swing.JLabel();
    public static final javax.swing.JLabel kb2 = new javax.swing.JLabel();
    public static final javax.swing.JLabel kms = new javax.swing.JLabel();
    private com.toedter.calendar.JDateChooser mj1;
    private com.toedter.calendar.JDateChooser mj2;
    private javax.swing.JLabel mk4;
    private javax.swing.JLabel month;
    private javax.swing.JFormattedTextField n1;
    private javax.swing.JEditorPane n2;
    private static javax.swing.JComboBox<String> n3;
    private javax.swing.JEditorPane n4;
    private javax.swing.JLabel na;
    public static final javax.swing.JLabel name = new javax.swing.JLabel();
    private javax.swing.JLabel nb;
    private javax.swing.JLabel nhy;
    private javax.swing.JPanel notifications;
    private javax.swing.JComboBox<String> ntf1;
    private javax.swing.JFormattedTextField ntf2;
    private javax.swing.JFormattedTextField ntf3;
    private javax.swing.JEditorPane ntf4;
    private javax.swing.JCheckBox opb1;
    private javax.swing.JCheckBox opb2;
    private javax.swing.JComboBox<String> opb3;
    private javax.swing.JComboBox<String> opb4;
    private javax.swing.JPanel opening_balances;
    private javax.swing.JLabel opt2;
    private javax.swing.JLabel opt3;
    private javax.swing.JSpinner pfr1;
    private javax.swing.JComboBox<String> prf1;
    private javax.swing.JFormattedTextField prf10;
    private javax.swing.JFormattedTextField prf11;
    private javax.swing.JFormattedTextField prf12;
    private javax.swing.JRadioButton prf13;
    private javax.swing.JRadioButton prf14;
    private javax.swing.JFormattedTextField prf15;
    private javax.swing.JFormattedTextField prf16;
    private javax.swing.JComboBox<String> prf2;
    private javax.swing.JComboBox<String> prf5;
    private javax.swing.JCheckBox prf6;
    private javax.swing.JCheckBox prf7;
    private javax.swing.JCheckBox prf8;
    private javax.swing.JFormattedTextField prf9;
    private javax.swing.JTable prof_table;
    private javax.swing.JTable prof_table1;
    private javax.swing.JPanel profit_and_loses;
    private javax.swing.JPanel profit_and_loses2;
    private javax.swing.JPanel profit_and_loses4;
    private javax.swing.JPanel purchases_rep;
    private javax.swing.JCheckBox px1;
    private javax.swing.JCheckBox px2;
    private javax.swing.JCheckBox px3;
    private javax.swing.JCheckBox px4;
    private javax.swing.JComboBox<String> px5;
    private javax.swing.JComboBox<String> px6;
    private javax.swing.JSpinner px7;
    private javax.swing.JTable px_table;
    private javax.swing.JCheckBox py1;
    private javax.swing.JCheckBox py2;
    private javax.swing.JCheckBox py3;
    private javax.swing.JCheckBox py4;
    public static final javax.swing.JComboBox<String> py5 = new javax.swing.JComboBox<>();
    private javax.swing.JComboBox<String> py6;
    private com.toedter.calendar.JDateChooser py7;
    private com.toedter.calendar.JDateChooser py8;
    private javax.swing.JTable py_table;
    private javax.swing.JSpinner pym;
    private javax.swing.JFormattedTextField r1;
    private javax.swing.JEditorPane r2;
    private javax.swing.JPanel rates;
    private javax.swing.JPanel reports;
    private javax.swing.JLabel rid;
    private javax.swing.JFormattedTextField rt1;
    private javax.swing.JComboBox<String> rt2;
    private javax.swing.JFormattedTextField rt5;
    private javax.swing.JFormattedTextField rt6;
    private javax.swing.JTable rt_table;
    private javax.swing.JTable rt_table1;
    private javax.swing.JTable rtu;
    private javax.swing.JTable rtv;
    private javax.swing.JFormattedTextField ru1;
    private javax.swing.JFormattedTextField ru2;
    private javax.swing.JCheckBox ru3;
    private javax.swing.JCheckBox ru4;
    private javax.swing.JFormattedTextField ru5;
    private javax.swing.JFormattedTextField ru6;
    private javax.swing.JLabel sa;
    private javax.swing.JPanel sales_rep;
    private javax.swing.JLabel se;
    private javax.swing.JPanel simult;
    private javax.swing.JFormattedTextField sv1;
    private javax.swing.JFormattedTextField sv2;
    private javax.swing.JFormattedTextField sv3;
    private javax.swing.JFormattedTextField sv4;
    private javax.swing.JSpinner sym;
    private javax.swing.JTable table_reset;
    private javax.swing.JLabel time;
    private javax.swing.JLabel ts1;
    private javax.swing.JLabel ts2;
    private javax.swing.JComboBox<String> tw3;
    private javax.swing.JComboBox<String> tw4;
    private javax.swing.JComboBox<String> tw5;
    private javax.swing.JFormattedTextField tw6;
    private javax.swing.JTable tw_table;
    private javax.swing.JFormattedTextField u1;
    private javax.swing.JFormattedTextField u2;
    private javax.swing.JComboBox<String> u3;
    private javax.swing.JFormattedTextField u4;
    private javax.swing.JFormattedTextField u5;
    private javax.swing.JComboBox<String> u6;
    private javax.swing.JComboBox<String> u7;
    private javax.swing.JTable u_table;
    private javax.swing.JLabel uid;
    public static final javax.swing.JLabel uname = new javax.swing.JLabel();
    public static final javax.swing.JLabel upass = new javax.swing.JLabel();
    private javax.swing.JPanel users;
    public static final javax.swing.JLabel w1 = new javax.swing.JLabel();
    public static final javax.swing.JLabel w2 = new javax.swing.JLabel();
    private javax.swing.JLabel wtq;
    private javax.swing.JCheckBox wy1;
    private javax.swing.JCheckBox wy2;
    private javax.swing.JCheckBox wy3;
    private javax.swing.JCheckBox wy4;
    private javax.swing.JComboBox<String> wy5;
    private javax.swing.JComboBox<String> wy6;
    private com.toedter.calendar.JDateChooser wy7;
    private javax.swing.JTable wy_table;
    private javax.swing.JLabel xch;
    private javax.swing.JLabel xp;
    private javax.swing.JLabel xp1;
    private javax.swing.JLabel year;
    private javax.swing.JCheckBox zh1;
    private javax.swing.JCheckBox zh2;
    private javax.swing.JCheckBox zh3;
    private javax.swing.JCheckBox zh4;
    private javax.swing.JComboBox<String> zh5;
    private javax.swing.JComboBox<String> zh6;
    private javax.swing.JSpinner zh7;
    private javax.swing.JLabel zh8;
    private javax.swing.JTable zh_table;
    private javax.swing.JSpinner zm1;
    private javax.swing.JLabel zm5;
    private javax.swing.JTable zm_table;
    private javax.swing.JLabel zp;
    private javax.swing.JLabel zp1;
    // End of variables declaration//GEN-END:variables

}
