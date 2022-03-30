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
public class G_Managers extends javax.swing.JFrame {

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

    public G_Managers() {
        initComponents();
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

        String query = "SELECT currency_name,short_code,base_currency FROM currencies where base_currency='" + true + "'";
        try {
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
            stm = con.createStatement();
            rs = stm.executeQuery(query);
            boolean bool = rs.isBeforeFirst();
            while (rs.next()) {
                if (bool == true) {
                    String a = rs.getString("short_code");
                    phy.setText(a);
                }
            }
            if (bool == false) {

            }

        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Something went wrong", JOptionPane.ERROR_MESSAGE);
        }

        currencies.setVisible(false);
        opening_balances.setVisible(false);
        denominations.setVisible(false);

        rates.setVisible(false);
        reports.setVisible(false);
        users.setVisible(false);
        branches.setVisible(false);
        operations.setVisible(false);

        day.setText(now("EEEE").toUpperCase());
        month.setText(now("dd MMMM").toUpperCase());
        year.setText(now("YYYY").toUpperCase());
        time.setText(now("hh : mm : ss : aa").toUpperCase());
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
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
        jLabel11 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        phy = new javax.swing.JLabel();
        header = new javax.swing.JPanel();
        day = new javax.swing.JLabel();
        month = new javax.swing.JLabel();
        year = new javax.swing.JLabel();
        time = new javax.swing.JLabel();
        animation = new javax.swing.JPanel();
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
        operations = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel42 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jPanel58 = new javax.swing.JPanel();
        jLabel73 = new javax.swing.JLabel();
        reports = new javax.swing.JPanel();
        jTabbedPane7 = new javax.swing.JTabbedPane();
        sales_rep = new javax.swing.JPanel();
        jTabbedPane8 = new javax.swing.JTabbedPane();
        jPanel30 = new javax.swing.JPanel();
        jScrollPane21 = new javax.swing.JScrollPane();
        jm_table1 = new javax.swing.JTable();
        jLabel91 = new javax.swing.JLabel();
        jLabel93 = new javax.swing.JLabel();
        fa1 = new javax.swing.JCheckBox();
        fa2 = new javax.swing.JCheckBox();
        jLabel94 = new javax.swing.JLabel();
        mj1 = new com.toedter.calendar.JDateChooser();
        mj2 = new com.toedter.calendar.JDateChooser();
        jLabel95 = new javax.swing.JLabel();
        jLabel96 = new javax.swing.JLabel();
        fa3 = new javax.swing.JCheckBox();
        fa4 = new javax.swing.JCheckBox();
        jLabel75 = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        fa6 = new javax.swing.JComboBox<>();
        jLabel128 = new javax.swing.JLabel();
        bscd = new javax.swing.JLabel();
        jPanel31 = new javax.swing.JPanel();
        jScrollPane36 = new javax.swing.JScrollPane();
        fb_table = new javax.swing.JTable();
        jLabel130 = new javax.swing.JLabel();
        jLabel131 = new javax.swing.JLabel();
        jLabel132 = new javax.swing.JLabel();
        fb1 = new javax.swing.JCheckBox();
        fb2 = new javax.swing.JCheckBox();
        jLabel133 = new javax.swing.JLabel();
        fb7 = new com.toedter.calendar.JDateChooser();
        jLabel135 = new javax.swing.JLabel();
        fb3 = new javax.swing.JCheckBox();
        fb4 = new javax.swing.JCheckBox();
        jLabel77 = new javax.swing.JLabel();
        jLabel78 = new javax.swing.JLabel();
        fb6 = new javax.swing.JComboBox<>();
        fw1 = new javax.swing.JLabel();
        fw2 = new javax.swing.JLabel();
        jPanel37 = new javax.swing.JPanel();
        jScrollPane37 = new javax.swing.JScrollPane();
        zm_table = new javax.swing.JTable();
        jLabel138 = new javax.swing.JLabel();
        jLabel139 = new javax.swing.JLabel();
        jLabel140 = new javax.swing.JLabel();
        fc1 = new javax.swing.JCheckBox();
        fc2 = new javax.swing.JCheckBox();
        jLabel141 = new javax.swing.JLabel();
        jLabel143 = new javax.swing.JLabel();
        fc3 = new javax.swing.JCheckBox();
        fc4 = new javax.swing.JCheckBox();
        jLabel79 = new javax.swing.JLabel();
        jLabel80 = new javax.swing.JLabel();
        fc6 = new javax.swing.JComboBox<>();
        zm1 = new javax.swing.JSpinner();
        zm5 = new javax.swing.JLabel();
        sym = new javax.swing.JSpinner();
        jLabel144 = new javax.swing.JLabel();
        jPanel38 = new javax.swing.JPanel();
        jScrollPane38 = new javax.swing.JScrollPane();
        fd_table = new javax.swing.JTable();
        jLabel146 = new javax.swing.JLabel();
        jLabel147 = new javax.swing.JLabel();
        jLabel148 = new javax.swing.JLabel();
        fd1 = new javax.swing.JCheckBox();
        fd2 = new javax.swing.JCheckBox();
        jLabel149 = new javax.swing.JLabel();
        jLabel151 = new javax.swing.JLabel();
        fd3 = new javax.swing.JCheckBox();
        fd4 = new javax.swing.JCheckBox();
        jLabel81 = new javax.swing.JLabel();
        jLabel82 = new javax.swing.JLabel();
        fd6 = new javax.swing.JComboBox<>();
        fd7 = new javax.swing.JSpinner();
        purchases_rep = new javax.swing.JPanel();
        jTabbedPane9 = new javax.swing.JTabbedPane();
        jPanel39 = new javax.swing.JPanel();
        jScrollPane39 = new javax.swing.JScrollPane();
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
        jPanel40 = new javax.swing.JPanel();
        jScrollPane40 = new javax.swing.JScrollPane();
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
        jLabel164 = new javax.swing.JLabel();
        wy6 = new javax.swing.JComboBox<>();
        ts1 = new javax.swing.JLabel();
        ts2 = new javax.swing.JLabel();
        jPanel42 = new javax.swing.JPanel();
        jScrollPane41 = new javax.swing.JScrollPane();
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
        jLabel173 = new javax.swing.JLabel();
        zh6 = new javax.swing.JComboBox<>();
        zh7 = new javax.swing.JSpinner();
        zh8 = new javax.swing.JLabel();
        pym = new javax.swing.JSpinner();
        jLabel174 = new javax.swing.JLabel();
        jPanel53 = new javax.swing.JPanel();
        jScrollPane42 = new javax.swing.JScrollPane();
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
        jLabel182 = new javax.swing.JLabel();
        px6 = new javax.swing.JComboBox<>();
        px7 = new javax.swing.JSpinner();
        profit_and_loses2 = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        jLabel129 = new javax.swing.JLabel();
        acc_currency = new javax.swing.JComboBox<>();
        jScrollPane10 = new javax.swing.JScrollPane();
        acc_table = new javax.swing.JTable();
        jLabel136 = new javax.swing.JLabel();
        acc_total = new javax.swing.JFormattedTextField();
        opb1 = new javax.swing.JCheckBox();
        opb2 = new javax.swing.JCheckBox();
        opb4 = new javax.swing.JComboBox<>();
        jPanel29 = new javax.swing.JPanel();
        jPanel34 = new javax.swing.JPanel();
        jLabel194 = new javax.swing.JLabel();
        cp3 = new javax.swing.JComboBox<>();
        jScrollPane17 = new javax.swing.JScrollPane();
        cp_table = new javax.swing.JTable();
        jLabel195 = new javax.swing.JLabel();
        cp4 = new javax.swing.JFormattedTextField();
        jCheckBox10 = new javax.swing.JCheckBox();
        jCheckBox11 = new javax.swing.JCheckBox();
        cp2 = new javax.swing.JComboBox<>();
        jPanel43 = new javax.swing.JPanel();
        jPanel45 = new javax.swing.JPanel();
        jLabel196 = new javax.swing.JLabel();
        cg3 = new javax.swing.JComboBox<>();
        jScrollPane18 = new javax.swing.JScrollPane();
        cg_table = new javax.swing.JTable();
        jLabel197 = new javax.swing.JLabel();
        cg4 = new javax.swing.JFormattedTextField();
        cta = new javax.swing.JCheckBox();
        ctb = new javax.swing.JCheckBox();
        cg2 = new javax.swing.JComboBox<>();
        profit_and_loses4 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jLabel157 = new javax.swing.JLabel();
        tw5 = new javax.swing.JComboBox<>();
        jScrollPane22 = new javax.swing.JScrollPane();
        tw_table = new javax.swing.JTable();
        jLabel165 = new javax.swing.JLabel();
        tw6 = new javax.swing.JFormattedTextField();
        tw1 = new javax.swing.JCheckBox();
        tw2 = new javax.swing.JCheckBox();
        tw4 = new javax.swing.JComboBox<>();
        currencies = new javax.swing.JPanel();
        jTabbedPane5 = new javax.swing.JTabbedPane();
        jPanel47 = new javax.swing.JPanel();
        jLabel74 = new javax.swing.JLabel();
        n1 = new javax.swing.JFormattedTextField();
        jScrollPane7 = new javax.swing.JScrollPane();
        n2 = new javax.swing.JEditorPane();
        jLabel188 = new javax.swing.JLabel();
        n3 = new javax.swing.JComboBox<>();
        jLabel189 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        n4 = new javax.swing.JEditorPane();
        jLabel190 = new javax.swing.JLabel();
        mk4 = new javax.swing.JLabel();
        jPanel60 = new javax.swing.JPanel();
        jLabel187 = new javax.swing.JLabel();
        ntf1 = new javax.swing.JComboBox<>();
        jLabel191 = new javax.swing.JLabel();
        ntf2 = new javax.swing.JFormattedTextField();
        jScrollPane14 = new javax.swing.JScrollPane();
        ntf4 = new javax.swing.JEditorPane();
        ntf3 = new javax.swing.JFormattedTextField();
        jLabel192 = new javax.swing.JLabel();
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
        branches = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        b2 = new javax.swing.JFormattedTextField();
        jLabel32 = new javax.swing.JLabel();
        b3 = new javax.swing.JFormattedTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        b4 = new javax.swing.JEditorPane();
        jLabel37 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        bu2 = new javax.swing.JFormattedTextField();
        jLabel40 = new javax.swing.JLabel();
        bu3 = new javax.swing.JFormattedTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        bu_table = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        bu4 = new javax.swing.JEditorPane();
        bu1 = new javax.swing.JFormattedTextField();
        jLabel43 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        bv_table = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        bd_table = new javax.swing.JTable();
        bid = new javax.swing.JLabel();
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
        jScrollPane34 = new javax.swing.JScrollPane();
        a_table = new javax.swing.JTable();
        jLabel103 = new javax.swing.JLabel();
        a8 = new javax.swing.JFormattedTextField();
        a9 = new javax.swing.JPasswordField();
        a10 = new javax.swing.JPasswordField();
        jLabel104 = new javax.swing.JLabel();
        jLabel105 = new javax.swing.JLabel();
        a6 = new javax.swing.JComboBox<>();
        jLabel117 = new javax.swing.JLabel();
        a7 = new javax.swing.JFormattedTextField();
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
        u6 = new javax.swing.JComboBox<>();
        jLabel49 = new javax.swing.JLabel();
        uid = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        u7 = new javax.swing.JFormattedTextField();
        jLabel18 = new javax.swing.JLabel();
        jPanel35 = new javax.swing.JPanel();
        jScrollPane35 = new javax.swing.JScrollPane();
        d_table = new javax.swing.JTable();
        na = new javax.swing.JLabel();
        nb = new javax.swing.JLabel();
        rates = new javax.swing.JPanel();
        jPanel28 = new javax.swing.JPanel();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel17 = new javax.swing.JPanel();
        jScrollPane28 = new javax.swing.JScrollPane();
        rtl_table = new javax.swing.JTable();
        jPanel18 = new javax.swing.JPanel();
        jScrollPane30 = new javax.swing.JScrollPane();
        rtl_table1 = new javax.swing.JTable();
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
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Regional Manager");

        name.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        name.setForeground(new java.awt.Color(51, 102, 255));
        name.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        name.setText("Providence");

        reg.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        reg.setForeground(new java.awt.Color(51, 102, 255));
        reg.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        reg.setText("jLabel18");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(name, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addComponent(reg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(5, 5, 5)
                .addComponent(name)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 12, Short.MAX_VALUE)
                .addComponent(reg)
                .addContainerGap())
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 7, 240, 200));

        dashboard_home.setBackground(new java.awt.Color(255, 255, 255));
        dashboard_home.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        dashboard_home.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-city-32.png"))); // NOI18N
        jLabel5.setText("Regions & Branches");
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });
        dashboard_home.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 13, 224, 40));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-dollar-euro-exchange-32.png"))); // NOI18N
        jLabel6.setText("Configured Exchange Rates");
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
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/us.png"))); // NOI18N
        jLabel8.setText("Users");
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel8MouseClicked(evt);
            }
        });
        dashboard_home.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 166, 224, 40));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-contact-details-32.png"))); // NOI18N
        jLabel9.setText("My Profile");
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel9MouseClicked(evt);
            }
        });
        dashboard_home.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 267, 224, 40));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/lout.png"))); // NOI18N
        jLabel10.setText("Sign Out");
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
        });
        dashboard_home.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 318, 224, 40));

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/clo.png"))); // NOI18N
        jLabel11.setText("Close Application");
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
        });
        dashboard_home.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 369, 224, 40));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-notification-32.png"))); // NOI18N
        jLabel4.setText("Notifications");
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });
        dashboard_home.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 217, 224, 39));

        my_id.setText("jLabel44");
        dashboard_home.add(my_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(58, 447, -1, 0));

        phy.setText("jLabel44");
        dashboard_home.add(phy, new org.netbeans.lib.awtextra.AbsoluteConstraints(135, 447, -1, 0));

        configs.setText("jLabel50");
        dashboard_home.add(configs, new org.netbeans.lib.awtextra.AbsoluteConstraints(4, 471, 160, 0));

        jPanel1.add(dashboard_home, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 211, 240, 505));

        header.setBackground(new java.awt.Color(255, 255, 255));
        header.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        day.setFont(new java.awt.Font("PMingLiU-ExtB", 1, 18)); // NOI18N
        day.setText("jLabel48");

        month.setFont(new java.awt.Font("PMingLiU-ExtB", 1, 18)); // NOI18N
        month.setText("jLabel48");

        year.setFont(new java.awt.Font("PMingLiU-ExtB", 1, 18)); // NOI18N
        year.setText("jLabel48");

        time.setFont(new java.awt.Font("PMingLiU-ExtB", 1, 18)); // NOI18N
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
                .addContainerGap(114, Short.MAX_VALUE))
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

        jPanel1.add(header, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 8, 1000, 90));

        animation.setBackground(new java.awt.Color(51, 102, 255));

        javax.swing.GroupLayout animationLayout = new javax.swing.GroupLayout(animation);
        animation.setLayout(animationLayout);
        animationLayout.setHorizontalGroup(
            animationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        animationLayout.setVerticalGroup(
            animationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 37, Short.MAX_VALUE)
        );

        jPanel1.add(animation, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 100, 1000, -1));

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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(dashboard_master, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 211, -1, 505));

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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(dashboard_rates, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 211, -1, 505));

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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(dashboard_operations, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 211, -1, 505));

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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(dashboard_reports, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 211, -1, 505));

        jTabbedPane2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel11.setBackground(new java.awt.Color(102, 153, 255));

        jLabel42.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel42.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel42.setText("Day begin");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel42, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel42, javax.swing.GroupLayout.DEFAULT_SIZE, 17, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel10.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 260, 270, -1));

        jTabbedPane2.addTab("Start Day Operations", new javax.swing.ImageIcon(getClass().getResource("/img/ex.png")), jPanel10); // NOI18N

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));

        jPanel58.setBackground(new java.awt.Color(102, 153, 255));

        jLabel73.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel73.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel73.setText("Day Ending");

        javax.swing.GroupLayout jPanel58Layout = new javax.swing.GroupLayout(jPanel58);
        jPanel58.setLayout(jPanel58Layout);
        jPanel58Layout.setHorizontalGroup(
            jPanel58Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel58Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel73, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel58Layout.setVerticalGroup(
            jPanel58Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel58Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel73, javax.swing.GroupLayout.DEFAULT_SIZE, 17, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 995, Short.MAX_VALUE)
            .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel12Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel58, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 534, Short.MAX_VALUE)
            .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel12Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel58, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jTabbedPane2.addTab("End Day Operations", new javax.swing.ImageIcon(getClass().getResource("/img/rat.png")), jPanel12); // NOI18N

        javax.swing.GroupLayout operationsLayout = new javax.swing.GroupLayout(operations);
        operations.setLayout(operationsLayout);
        operationsLayout.setHorizontalGroup(
            operationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1000, Short.MAX_VALUE)
            .addGroup(operationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.Alignment.TRAILING))
        );
        operationsLayout.setVerticalGroup(
            operationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 580, Short.MAX_VALUE)
            .addGroup(operationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.Alignment.TRAILING))
        );

        jPanel1.add(operations, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 140, 1000, 580));

        jTabbedPane7.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane7.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jTabbedPane7.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        jTabbedPane7.setFocusable(false);

        sales_rep.setBackground(new java.awt.Color(255, 255, 255));
        sales_rep.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTabbedPane8.setBackground(new java.awt.Color(255, 255, 255));

        jPanel30.setBackground(new java.awt.Color(255, 255, 255));
        jPanel30.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jm_table1.setAutoCreateRowSorter(true);
        jm_table1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Currency Out", "Currency In", "Region", "Branch", "Exchange Rate", "Amount Traded Out", "Amount Traded In", "Cashier", "Date"
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

        jPanel30.add(jScrollPane21, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 975, 343));

        jLabel91.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/excel.png"))); // NOI18N
        jLabel91.setText("Save as CSV File");
        jLabel91.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel91MouseClicked(evt);
            }
        });
        jPanel30.add(jLabel91, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 92, -1, 35));

        jLabel93.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/print.png"))); // NOI18N
        jLabel93.setText("Print / Save as XPS");
        jLabel93.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel93MouseClicked(evt);
            }
        });
        jPanel30.add(jLabel93, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 92, -1, 35));

        fa1.setBackground(new java.awt.Color(255, 255, 255));
        fa1.setText(" L. C Used ?");
        fa1.setFocusable(false);
        fa1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fa1ActionPerformed(evt);
            }
        });
        jPanel30.add(fa1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 6, 99, 30));

        fa2.setBackground(new java.awt.Color(255, 255, 255));
        fa2.setText("Cross Currency");
        fa2.setFocusable(false);
        fa2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fa2ActionPerformed(evt);
            }
        });
        jPanel30.add(fa2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 52, -1, 30));

        jLabel94.setText("Start date");
        jPanel30.add(jLabel94, new org.netbeans.lib.awtextra.AbsoluteConstraints(565, 6, 70, 30));

        mj1.setDateFormatString("y-MM-dd hh:mm:ss.ml");
        jPanel30.add(mj1, new org.netbeans.lib.awtextra.AbsoluteConstraints(649, 6, 150, 30));

        mj2.setDateFormatString("y-MM-dd");
        jPanel30.add(mj2, new org.netbeans.lib.awtextra.AbsoluteConstraints(649, 52, 152, 30));

        jLabel95.setText("End date");
        jPanel30.add(jLabel95, new org.netbeans.lib.awtextra.AbsoluteConstraints(565, 52, 68, 30));

        jLabel96.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/dbv.png"))); // NOI18N
        jLabel96.setText("Fetch Data");
        jLabel96.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel96MouseClicked(evt);
            }
        });
        jPanel30.add(jLabel96, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 92, -1, 35));

        fa3.setBackground(new java.awt.Color(255, 255, 255));
        fa3.setText("Regional Sales");
        fa3.setFocusable(false);
        fa3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fa3ActionPerformed(evt);
            }
        });
        jPanel30.add(fa3, new org.netbeans.lib.awtextra.AbsoluteConstraints(137, 6, 145, 30));

        fa4.setBackground(new java.awt.Color(255, 255, 255));
        fa4.setText("Branch Sales");
        fa4.setFocusable(false);
        fa4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fa4ActionPerformed(evt);
            }
        });
        jPanel30.add(fa4, new org.netbeans.lib.awtextra.AbsoluteConstraints(137, 52, 145, 30));

        jLabel75.setText("Select Region");
        jPanel30.add(jLabel75, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 6, -1, 30));

        fa5.setEditable(true);
        fa5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
        fa5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fa5ActionPerformed(evt);
            }
        });
        jPanel30.add(fa5, new org.netbeans.lib.awtextra.AbsoluteConstraints(375, 6, 155, 30));

        jLabel76.setText("Select Branch");
        jPanel30.add(jLabel76, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 52, -1, 30));

        fa6.setEditable(true);
        fa6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
        jPanel30.add(fa6, new org.netbeans.lib.awtextra.AbsoluteConstraints(375, 52, 155, 30));

        jLabel128.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/phist.png"))); // NOI18N
        jLabel128.setText("Specify Currency");
        jLabel128.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel128MouseClicked(evt);
            }
        });
        jPanel30.add(jLabel128, new org.netbeans.lib.awtextra.AbsoluteConstraints(325, 92, 151, 35));

        w1.setText("jLabel163");
        jPanel30.add(w1, new org.netbeans.lib.awtextra.AbsoluteConstraints(888, 14, -1, 0));

        w2.setText("jLabel164");
        jPanel30.add(w2, new org.netbeans.lib.awtextra.AbsoluteConstraints(888, 60, -1, 0));

        bscd.setText("jLabel92");
        jPanel30.add(bscd, new org.netbeans.lib.awtextra.AbsoluteConstraints(733, 92, -1, 0));

        jTabbedPane8.addTab("Purchases Between Dates", new javax.swing.ImageIcon(getClass().getResource("/img/bd.png")), jPanel30); // NOI18N

        jPanel31.setBackground(new java.awt.Color(255, 255, 255));
        jPanel31.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        fb_table.setAutoCreateRowSorter(true);
        fb_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Currency Out", "Currency In", "Region", "Branch", "Exchange Rate", "Amount Traded Out", "Amount Traded In", "Cashier", "Date"
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
        jScrollPane36.setViewportView(fb_table);

        jPanel31.add(jScrollPane36, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 975, 343));

        jLabel130.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/excel.png"))); // NOI18N
        jLabel130.setText("Save as CSV File");
        jLabel130.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel130MouseClicked(evt);
            }
        });
        jPanel31.add(jLabel130, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 92, -1, 35));

        jLabel131.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/print.png"))); // NOI18N
        jLabel131.setText("Print / Save as XPS");
        jLabel131.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel131MouseClicked(evt);
            }
        });
        jPanel31.add(jLabel131, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 92, 130, 35));

        jLabel132.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/phist.png"))); // NOI18N
        jLabel132.setText("Specify Currency");
        jLabel132.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel132MouseClicked(evt);
            }
        });
        jPanel31.add(jLabel132, new org.netbeans.lib.awtextra.AbsoluteConstraints(325, 92, 155, 35));

        fb1.setBackground(new java.awt.Color(255, 255, 255));
        fb1.setText(" L. C Used ?");
        fb1.setFocusable(false);
        fb1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fb1ActionPerformed(evt);
            }
        });
        jPanel31.add(fb1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 6, 99, 30));

        fb2.setBackground(new java.awt.Color(255, 255, 255));
        fb2.setText("Cross Currency");
        fb2.setFocusable(false);
        fb2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fb2ActionPerformed(evt);
            }
        });
        jPanel31.add(fb2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 52, -1, 30));

        jLabel133.setText("Select Date");
        jPanel31.add(jLabel133, new org.netbeans.lib.awtextra.AbsoluteConstraints(565, 6, 70, 30));

        fb7.setDateFormatString("y-MM-dd");
        jPanel31.add(fb7, new org.netbeans.lib.awtextra.AbsoluteConstraints(649, 6, 150, 30));

        jLabel135.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/dbv.png"))); // NOI18N
        jLabel135.setText("Fetch Data");
        jLabel135.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel135MouseClicked(evt);
            }
        });
        jPanel31.add(jLabel135, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 92, 133, 35));

        fb3.setBackground(new java.awt.Color(255, 255, 255));
        fb3.setText("Regional Sales");
        fb3.setFocusable(false);
        fb3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fb3ActionPerformed(evt);
            }
        });
        jPanel31.add(fb3, new org.netbeans.lib.awtextra.AbsoluteConstraints(137, 6, -1, 30));

        fb4.setBackground(new java.awt.Color(255, 255, 255));
        fb4.setText("Branch Sales");
        fb4.setFocusable(false);
        fb4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fb4ActionPerformed(evt);
            }
        });
        jPanel31.add(fb4, new org.netbeans.lib.awtextra.AbsoluteConstraints(137, 52, 95, 30));

        jLabel77.setText("Select Region");
        jPanel31.add(jLabel77, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 6, -1, 30));

        fb5.setEditable(true);
        fb5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
        fb5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fb5ActionPerformed(evt);
            }
        });
        jPanel31.add(fb5, new org.netbeans.lib.awtextra.AbsoluteConstraints(375, 6, 155, 30));

        jLabel78.setText("Select Branch");
        jPanel31.add(jLabel78, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 52, -1, 30));

        fb6.setEditable(true);
        fb6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
        jPanel31.add(fb6, new org.netbeans.lib.awtextra.AbsoluteConstraints(375, 52, 155, 30));

        fw1.setText("jLabel134");
        jPanel31.add(fw1, new org.netbeans.lib.awtextra.AbsoluteConstraints(861, 20, -1, 0));

        fw2.setText("jLabel163");
        jPanel31.add(fw2, new org.netbeans.lib.awtextra.AbsoluteConstraints(985, 78, 0, 4));

        jTabbedPane8.addTab("Daily Purchases", new javax.swing.ImageIcon(getClass().getResource("/img/ym.png")), jPanel31); // NOI18N

        jPanel37.setBackground(new java.awt.Color(255, 255, 255));
        jPanel37.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        zm_table.setAutoCreateRowSorter(true);
        zm_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Currency Out", "Currency In", "Region", "Branch", "Exchange Rate", "Amount Traded Out", "Amount Traded In", "Cashier", "Date"
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
        jScrollPane37.setViewportView(zm_table);

        jPanel37.add(jScrollPane37, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 975, 343));

        jLabel138.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/excel.png"))); // NOI18N
        jLabel138.setText("Save as CSV File");
        jLabel138.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel138MouseClicked(evt);
            }
        });
        jPanel37.add(jLabel138, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 92, -1, 35));

        jLabel139.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/print.png"))); // NOI18N
        jLabel139.setText("Print / Save as XPS");
        jLabel139.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel139MouseClicked(evt);
            }
        });
        jPanel37.add(jLabel139, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 92, -1, 35));

        jLabel140.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/phist.png"))); // NOI18N
        jLabel140.setText("Specify Currency");
        jLabel140.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel140MouseClicked(evt);
            }
        });
        jPanel37.add(jLabel140, new org.netbeans.lib.awtextra.AbsoluteConstraints(325, 92, -1, 35));

        fc1.setBackground(new java.awt.Color(255, 255, 255));
        fc1.setText(" L. C Used ?");
        fc1.setFocusable(false);
        fc1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fc1ActionPerformed(evt);
            }
        });
        jPanel37.add(fc1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 6, 99, 30));

        fc2.setBackground(new java.awt.Color(255, 255, 255));
        fc2.setText("Cross Currency");
        fc2.setFocusable(false);
        fc2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fc2ActionPerformed(evt);
            }
        });
        jPanel37.add(fc2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 52, -1, 30));

        jLabel141.setText("Select month");
        jPanel37.add(jLabel141, new org.netbeans.lib.awtextra.AbsoluteConstraints(565, 6, 70, 30));

        jLabel143.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/dbv.png"))); // NOI18N
        jLabel143.setText("Fetch Data");
        jLabel143.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel143MouseClicked(evt);
            }
        });
        jPanel37.add(jLabel143, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 92, -1, 35));

        fc3.setBackground(new java.awt.Color(255, 255, 255));
        fc3.setText("Regional Sales");
        fc3.setFocusable(false);
        fc3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fc3ActionPerformed(evt);
            }
        });
        jPanel37.add(fc3, new org.netbeans.lib.awtextra.AbsoluteConstraints(137, 6, -1, 30));

        fc4.setBackground(new java.awt.Color(255, 255, 255));
        fc4.setText("Branch Sales");
        fc4.setFocusable(false);
        fc4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fc4ActionPerformed(evt);
            }
        });
        jPanel37.add(fc4, new org.netbeans.lib.awtextra.AbsoluteConstraints(137, 52, 95, 30));

        jLabel79.setText("Select Region");
        jPanel37.add(jLabel79, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 6, -1, 30));

        fc5.setEditable(true);
        fc5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
        fc5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fc5ActionPerformed(evt);
            }
        });
        jPanel37.add(fc5, new org.netbeans.lib.awtextra.AbsoluteConstraints(375, 6, 155, 30));

        jLabel80.setText("Select Branch");
        jPanel37.add(jLabel80, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 52, -1, 30));

        fc6.setEditable(true);
        fc6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
        jPanel37.add(fc6, new org.netbeans.lib.awtextra.AbsoluteConstraints(375, 52, 155, 30));

        zm1.setModel(new javax.swing.SpinnerListModel(new String[] {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"}));
        jPanel37.add(zm1, new org.netbeans.lib.awtextra.AbsoluteConstraints(649, 6, 138, 30));

        zm5.setText("jLabel134");
        jPanel37.add(zm5, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 4, -1, 0));

        sym.setModel(new javax.swing.SpinnerListModel(new String[] {"2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030", "2031", "2032", "2033", "2034", "2035", "2036", "2037", "2040", "2050", "2051", "2052", "2053", "2054", "2055", "2056", "2057", "2058", "2059", "2060", "2061", "2062", "2063", "2064", "2065", "2066", "2067", "2068", "2069", "2070", "2071", "2072", "2073", "2073", "2074", "2075", "2076", "2077", "2078", "2079", "2080", "2081", "2082", "2083", "2084", "2085", "2086", "2087", "2088", "2089", "2090", "2091", "2092", "2093", "2094", "2095", "2096", "2097", "2098", "2099"}));
        jPanel37.add(sym, new org.netbeans.lib.awtextra.AbsoluteConstraints(649, 52, 138, 30));

        jLabel144.setText("Select year");
        jPanel37.add(jLabel144, new org.netbeans.lib.awtextra.AbsoluteConstraints(565, 52, 70, 30));

        jTabbedPane8.addTab("Monthly Purchases", new javax.swing.ImageIcon(getClass().getResource("/img/td.png")), jPanel37); // NOI18N

        jPanel38.setBackground(new java.awt.Color(255, 255, 255));
        jPanel38.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        fd_table.setAutoCreateRowSorter(true);
        fd_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Currency Out", "Currency In ", "Region", "Branch", "Exchange Rate", "Amount Traded Out", "Amount Traded In", "Cashier", "Date"
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
        jScrollPane38.setViewportView(fd_table);

        jPanel38.add(jScrollPane38, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 975, 343));

        jLabel146.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/excel.png"))); // NOI18N
        jLabel146.setText("Save as CSV File");
        jLabel146.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel146MouseClicked(evt);
            }
        });
        jPanel38.add(jLabel146, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 92, -1, 35));

        jLabel147.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/print.png"))); // NOI18N
        jLabel147.setText("Print / Save as XPS");
        jLabel147.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel147MouseClicked(evt);
            }
        });
        jPanel38.add(jLabel147, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 92, -1, 35));

        jLabel148.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/phist.png"))); // NOI18N
        jLabel148.setText("Specify Currency");
        jLabel148.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel148MouseClicked(evt);
            }
        });
        jPanel38.add(jLabel148, new org.netbeans.lib.awtextra.AbsoluteConstraints(325, 92, -1, 35));

        fd1.setBackground(new java.awt.Color(255, 255, 255));
        fd1.setText(" L. C Used ?");
        fd1.setFocusable(false);
        fd1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fd1ActionPerformed(evt);
            }
        });
        jPanel38.add(fd1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 6, 99, 30));

        fd2.setBackground(new java.awt.Color(255, 255, 255));
        fd2.setText("Cross Currency");
        fd2.setFocusable(false);
        fd2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fd2ActionPerformed(evt);
            }
        });
        jPanel38.add(fd2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 52, -1, 30));

        jLabel149.setText("Start date");
        jPanel38.add(jLabel149, new org.netbeans.lib.awtextra.AbsoluteConstraints(565, 6, 70, 30));

        jLabel151.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/dbv.png"))); // NOI18N
        jLabel151.setText("Fetch Data");
        jLabel151.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel151MouseClicked(evt);
            }
        });
        jPanel38.add(jLabel151, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 92, -1, 35));

        fd3.setBackground(new java.awt.Color(255, 255, 255));
        fd3.setText("Regional Sales");
        fd3.setFocusable(false);
        fd3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fd3ActionPerformed(evt);
            }
        });
        jPanel38.add(fd3, new org.netbeans.lib.awtextra.AbsoluteConstraints(137, 6, -1, 30));

        fd4.setBackground(new java.awt.Color(255, 255, 255));
        fd4.setText("Branch Sales");
        fd4.setFocusable(false);
        fd4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fd4ActionPerformed(evt);
            }
        });
        jPanel38.add(fd4, new org.netbeans.lib.awtextra.AbsoluteConstraints(137, 52, 95, 30));

        jLabel81.setText("Select Region");
        jPanel38.add(jLabel81, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 6, -1, 30));

        fd5.setEditable(true);
        fd5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
        fd5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fd5ActionPerformed(evt);
            }
        });
        jPanel38.add(fd5, new org.netbeans.lib.awtextra.AbsoluteConstraints(375, 6, 155, 30));

        jLabel82.setText("Select Branch");
        jPanel38.add(jLabel82, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 52, -1, 30));

        fd6.setEditable(true);
        fd6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
        jPanel38.add(fd6, new org.netbeans.lib.awtextra.AbsoluteConstraints(375, 52, 155, 30));

        fd7.setModel(new javax.swing.SpinnerListModel(new String[] {"2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030", "2031", "2032", "2033", "2034", "2035", "2036", "2037", "2040", "2050", "2051", "2052", "2053", "2054", "2055", "2056", "2057", "2058", "2059", "2060", "2061", "2062", "2063", "2064", "2065", "2066", "2067", "2068", "2069", "2070", "2071", "2072", "2073", "2073", "2074", "2075", "2076", "2077", "2078", "2079", "2080", "2081", "2082", "2083", "2084", "2085", "2086", "2087", "2088", "2089", "2090", "2091", "2092", "2093", "2094", "2095", "2096", "2097", "2098", "2099"}));
        jPanel38.add(fd7, new org.netbeans.lib.awtextra.AbsoluteConstraints(649, 6, 138, 30));

        jTabbedPane8.addTab("Annualy Purchases", new javax.swing.ImageIcon(getClass().getResource("/img/mm.png")), jPanel38); // NOI18N

        sales_rep.add(jTabbedPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 540));

        jTabbedPane7.addTab("Purchases Reports", new javax.swing.ImageIcon(getClass().getResource("/img/sp.png")), sales_rep); // NOI18N

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
                "Currency Out", "Currency In", "Region", "Branch", "Exchange Rate", "Amount Traded Out", "Amount Traded In", "Cashier", "Date"
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
        jScrollPane39.setViewportView(py_table);

        jPanel39.add(jScrollPane39, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 975, 343));

        jLabel92.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/excel.png"))); // NOI18N
        jLabel92.setText("Save as CSV File");
        jLabel92.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel92MouseClicked(evt);
            }
        });
        jPanel39.add(jLabel92, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 92, -1, 35));

        jLabel134.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/print.png"))); // NOI18N
        jLabel134.setText("Print / Save as XPS");
        jLabel134.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel134MouseClicked(evt);
            }
        });
        jPanel39.add(jLabel134, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 92, -1, 35));

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
        jPanel39.add(jLabel142, new org.netbeans.lib.awtextra.AbsoluteConstraints(565, 6, 70, 30));

        py7.setDateFormatString("y-MM-dd hh:mm:ss.ml");
        jPanel39.add(py7, new org.netbeans.lib.awtextra.AbsoluteConstraints(649, 6, 150, 30));

        py8.setDateFormatString("y-MM-dd");
        jPanel39.add(py8, new org.netbeans.lib.awtextra.AbsoluteConstraints(649, 52, 152, 30));

        jLabel150.setText("End date");
        jPanel39.add(jLabel150, new org.netbeans.lib.awtextra.AbsoluteConstraints(565, 52, 68, 30));

        jLabel154.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/dbv.png"))); // NOI18N
        jLabel154.setText("Fetch Data");
        jLabel154.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel154MouseClicked(evt);
            }
        });
        jPanel39.add(jLabel154, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 92, -1, 35));

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
        jPanel39.add(jLabel83, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 6, -1, 30));

        py5.setEditable(true);
        py5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
        py5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                py5ActionPerformed(evt);
            }
        });
        jPanel39.add(py5, new org.netbeans.lib.awtextra.AbsoluteConstraints(375, 6, 155, 30));

        jLabel155.setText("Select Branch");
        jPanel39.add(jLabel155, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 52, -1, 30));

        py6.setEditable(true);
        py6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
        jPanel39.add(py6, new org.netbeans.lib.awtextra.AbsoluteConstraints(375, 52, 155, 30));

        jLabel156.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/phist.png"))); // NOI18N
        jLabel156.setText("Specify Currency");
        jLabel156.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel156MouseClicked(evt);
            }
        });
        jPanel39.add(jLabel156, new org.netbeans.lib.awtextra.AbsoluteConstraints(325, 92, 151, 35));

        kb1.setText("jLabel163");
        jPanel39.add(kb1, new org.netbeans.lib.awtextra.AbsoluteConstraints(838, 34, -1, 0));

        kb2.setText("jLabel164");
        jPanel39.add(kb2, new org.netbeans.lib.awtextra.AbsoluteConstraints(838, 80, -1, 0));

        bfc.setText("jLabel92");
        jPanel39.add(bfc, new org.netbeans.lib.awtextra.AbsoluteConstraints(683, 98, -1, 0));

        jTabbedPane9.addTab("Sales Between Dates", new javax.swing.ImageIcon(getClass().getResource("/img/bd.png")), jPanel39); // NOI18N

        jPanel40.setBackground(new java.awt.Color(255, 255, 255));
        jPanel40.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        wy_table.setAutoCreateRowSorter(true);
        wy_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Currency Out", "Currency In", "Region", "Branch", "Exchange Rate", "Amount Traded Out", "Amount Traded In", "Cashier", "Date"
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
        jScrollPane40.setViewportView(wy_table);

        jPanel40.add(jScrollPane40, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 975, 343));

        jLabel158.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/excel.png"))); // NOI18N
        jLabel158.setText("Save as CSV File");
        jLabel158.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel158MouseClicked(evt);
            }
        });
        jPanel40.add(jLabel158, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 92, -1, 35));

        jLabel159.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/print.png"))); // NOI18N
        jLabel159.setText("Print / Save as XPS");
        jLabel159.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel159MouseClicked(evt);
            }
        });
        jPanel40.add(jLabel159, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 92, -1, 35));

        jLabel160.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/phist.png"))); // NOI18N
        jLabel160.setText("Specify Currency");
        jPanel40.add(jLabel160, new org.netbeans.lib.awtextra.AbsoluteConstraints(325, 92, -1, 35));

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
        jPanel40.add(jLabel161, new org.netbeans.lib.awtextra.AbsoluteConstraints(565, 6, 70, 30));

        wy7.setDateFormatString("y-MM-dd");
        jPanel40.add(wy7, new org.netbeans.lib.awtextra.AbsoluteConstraints(649, 6, 150, 30));

        jLabel162.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/dbv.png"))); // NOI18N
        jLabel162.setText("Fetch Data");
        jLabel162.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel162MouseClicked(evt);
            }
        });
        jPanel40.add(jLabel162, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 92, -1, 35));

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
        jPanel40.add(jLabel163, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 6, -1, 30));

        wy5.setEditable(true);
        wy5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
        wy5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wy5ActionPerformed(evt);
            }
        });
        jPanel40.add(wy5, new org.netbeans.lib.awtextra.AbsoluteConstraints(375, 6, 155, 30));

        jLabel164.setText("Select Branch");
        jPanel40.add(jLabel164, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 52, -1, 30));

        wy6.setEditable(true);
        wy6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
        jPanel40.add(wy6, new org.netbeans.lib.awtextra.AbsoluteConstraints(375, 52, 155, 30));

        ts1.setText("jLabel134");
        jPanel40.add(ts1, new org.netbeans.lib.awtextra.AbsoluteConstraints(939, 45, 4, 2));

        ts2.setText("jLabel163");
        jPanel40.add(ts2, new org.netbeans.lib.awtextra.AbsoluteConstraints(939, 53, -1, 0));

        jTabbedPane9.addTab("Daily Sales", new javax.swing.ImageIcon(getClass().getResource("/img/ym.png")), jPanel40); // NOI18N

        jPanel42.setBackground(new java.awt.Color(255, 255, 255));
        jPanel42.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        zh_table.setAutoCreateRowSorter(true);
        zh_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Currency Out", "Currency In", "Region", "Branch", "Exchange Rate", "Amount Traded Out", "Amount Traded In ", "Cashier", "Date"
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
        jScrollPane41.setViewportView(zh_table);

        jPanel42.add(jScrollPane41, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 975, 3434));

        jLabel167.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/excel.png"))); // NOI18N
        jLabel167.setText("Save as CSV File");
        jLabel167.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel167MouseClicked(evt);
            }
        });
        jPanel42.add(jLabel167, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 92, -1, 35));

        jLabel168.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/print.png"))); // NOI18N
        jLabel168.setText("Print / Save as XPS");
        jLabel168.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel168MouseClicked(evt);
            }
        });
        jPanel42.add(jLabel168, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 92, 150, 35));

        jLabel169.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/phist.png"))); // NOI18N
        jLabel169.setText("Specify Currency");
        jPanel42.add(jLabel169, new org.netbeans.lib.awtextra.AbsoluteConstraints(325, 92, -1, 35));

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
        jPanel42.add(jLabel170, new org.netbeans.lib.awtextra.AbsoluteConstraints(565, 6, 70, 30));

        jLabel171.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/dbv.png"))); // NOI18N
        jLabel171.setText("Fetch Data");
        jLabel171.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel171MouseClicked(evt);
            }
        });
        jPanel42.add(jLabel171, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 92, 111, 35));

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
        jPanel42.add(jLabel172, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 6, -1, 30));

        zh5.setEditable(true);
        zh5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
        zh5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zh5ActionPerformed(evt);
            }
        });
        jPanel42.add(zh5, new org.netbeans.lib.awtextra.AbsoluteConstraints(375, 6, 155, 30));

        jLabel173.setText("Select Branch");
        jPanel42.add(jLabel173, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 52, -1, 30));

        zh6.setEditable(true);
        zh6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
        jPanel42.add(zh6, new org.netbeans.lib.awtextra.AbsoluteConstraints(375, 52, 155, 30));

        zh7.setModel(new javax.swing.SpinnerListModel(new String[] {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"}));
        jPanel42.add(zh7, new org.netbeans.lib.awtextra.AbsoluteConstraints(649, 6, 138, 30));

        zh8.setText("jLabel134");
        jPanel42.add(zh8, new org.netbeans.lib.awtextra.AbsoluteConstraints(638, 4, -1, 0));

        pym.setModel(new javax.swing.SpinnerListModel(new String[] {"2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030", "2031", "2032", "2033", "2034", "2035", "2036", "2037", "2040", "2050", "2051", "2052", "2053", "2054", "2055", "2056", "2057", "2058", "2059", "2060", "2061", "2062", "2063", "2064", "2065", "2066", "2067", "2068", "2069", "2070", "2071", "2072", "2073", "2073", "2074", "2075", "2076", "2077", "2078", "2079", "2080", "2081", "2082", "2083", "2084", "2085", "2086", "2087", "2088", "2089", "2090", "2091", "2092", "2093", "2094", "2095", "2096", "2097", "2098", "2099"}));
        jPanel42.add(pym, new org.netbeans.lib.awtextra.AbsoluteConstraints(649, 52, 138, 30));

        jLabel174.setText("Select year");
        jPanel42.add(jLabel174, new org.netbeans.lib.awtextra.AbsoluteConstraints(565, 52, 70, 30));

        jTabbedPane9.addTab("Monthly Sales", new javax.swing.ImageIcon(getClass().getResource("/img/td.png")), jPanel42); // NOI18N

        jPanel53.setBackground(new java.awt.Color(255, 255, 255));
        jPanel53.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        px_table.setAutoCreateRowSorter(true);
        px_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Currency Out", "Currency In", "Region", "Branch", "Exchange Rate", "Amount Traded Out", "Amount Traded In", "Cashier", "Date"
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
        jScrollPane42.setViewportView(px_table);

        jPanel53.add(jScrollPane42, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 975, 343));

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
        jPanel53.add(jLabel176, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 92, -1, 35));

        jLabel177.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/print.png"))); // NOI18N
        jLabel177.setText("Print / Save as XPS");
        jLabel177.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel177MouseClicked(evt);
            }
        });
        jPanel53.add(jLabel177, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 92, -1, 35));

        jLabel178.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/phist.png"))); // NOI18N
        jLabel178.setText("Specify Currency");
        jPanel53.add(jLabel178, new org.netbeans.lib.awtextra.AbsoluteConstraints(325, 92, -1, 35));

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
        jPanel53.add(jLabel179, new org.netbeans.lib.awtextra.AbsoluteConstraints(565, 6, 70, 30));

        jLabel180.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/dbv.png"))); // NOI18N
        jLabel180.setText("Fetch Data");
        jLabel180.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel180MouseClicked(evt);
            }
        });
        jPanel53.add(jLabel180, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 92, -1, 35));

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
        jPanel53.add(jLabel181, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 6, -1, 30));

        px5.setEditable(true);
        px5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
        px5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                px5ActionPerformed(evt);
            }
        });
        jPanel53.add(px5, new org.netbeans.lib.awtextra.AbsoluteConstraints(375, 6, 155, 30));

        jLabel182.setText("Select Branch");
        jPanel53.add(jLabel182, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 52, -1, 30));

        px6.setEditable(true);
        px6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
        jPanel53.add(px6, new org.netbeans.lib.awtextra.AbsoluteConstraints(375, 52, 155, 30));

        px7.setModel(new javax.swing.SpinnerListModel(new String[] {"2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030", "2031", "2032", "2033", "2034", "2035", "2036", "2037", "2040", "2050", "2051", "2052", "2053", "2054", "2055", "2056", "2057", "2058", "2059", "2060", "2061", "2062", "2063", "2064", "2065", "2066", "2067", "2068", "2069", "2070", "2071", "2072", "2073", "2073", "2074", "2075", "2076", "2077", "2078", "2079", "2080", "2081", "2082", "2083", "2084", "2085", "2086", "2087", "2088", "2089", "2090", "2091", "2092", "2093", "2094", "2095", "2096", "2097", "2098", "2099"}));
        jPanel53.add(px7, new org.netbeans.lib.awtextra.AbsoluteConstraints(649, 6, 108, 30));

        jTabbedPane9.addTab("Annual Sales", new javax.swing.ImageIcon(getClass().getResource("/img/mm.png")), jPanel53); // NOI18N

        purchases_rep.add(jTabbedPane9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 550));

        jTabbedPane7.addTab("Sales Reports", new javax.swing.ImageIcon(getClass().getResource("/img/up32.png")), purchases_rep); // NOI18N

        profit_and_loses2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel22.setBackground(new java.awt.Color(255, 255, 255));
        jPanel22.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel24.setBackground(new java.awt.Color(255, 255, 255));
        jPanel24.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "What Regions or Branches Holds", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

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

        jLabel136.setText("Grand Total for the selected currency");

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jLabel129)
                        .addGap(53, 53, 53)
                        .addComponent(acc_currency, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addComponent(jLabel136)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(acc_total, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(89, Short.MAX_VALUE))
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(acc_currency, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                    .addComponent(jLabel129))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(acc_total, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                    .addComponent(jLabel136))
                .addGap(39, 39, 39))
        );

        jPanel22.add(jPanel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(224, 84, 480, 290));

        opb1.setBackground(new java.awt.Color(255, 255, 255));
        opb1.setText("Regions");
        jPanel22.add(opb1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 10, 170, -1));

        opb2.setBackground(new java.awt.Color(255, 255, 255));
        opb2.setText("Branches");
        jPanel22.add(opb2, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 10, 160, -1));

        opb4.setEditable(true);
        jPanel22.add(opb4, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 40, 170, 30));

        opb3.setEditable(true);
        opb3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
        opb3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opb3ActionPerformed(evt);
            }
        });
        jPanel22.add(opb3, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 40, 170, 30));

        javax.swing.GroupLayout profit_and_loses2Layout = new javax.swing.GroupLayout(profit_and_loses2);
        profit_and_loses2.setLayout(profit_and_loses2Layout);
        profit_and_loses2Layout.setHorizontalGroup(
            profit_and_loses2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1000, Short.MAX_VALUE)
            .addGroup(profit_and_loses2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(profit_and_loses2Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, 995, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        profit_and_loses2Layout.setVerticalGroup(
            profit_and_loses2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 655, Short.MAX_VALUE)
            .addGroup(profit_and_loses2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(profit_and_loses2Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, 534, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jTabbedPane7.addTab("Branch Accounts", new javax.swing.ImageIcon(getClass().getResource("/img/icons8-bank-building-32.png")), profit_and_loses2); // NOI18N

        jPanel29.setBackground(new java.awt.Color(255, 255, 255));
        jPanel29.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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

        jPanel34.add(jScrollPane17, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 960, 350));

        jLabel195.setText("Grand Total for the selected currency");
        jPanel34.add(jLabel195, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 410, -1, 20));
        jPanel34.add(cp4, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 410, 130, 27));

        jPanel29.add(jPanel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 990, 580));

        jCheckBox10.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox10.setText("Regions only");
        jPanel29.add(jCheckBox10, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 10, 170, -1));

        jCheckBox11.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox11.setText("Regions only");
        jPanel29.add(jCheckBox11, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 10, 160, -1));

        cp2.setEditable(true);
        jPanel29.add(cp2, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 40, 170, 30));

        cp1.setEditable(true);
        cp1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
        cp1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cp1ActionPerformed(evt);
            }
        });
        jPanel29.add(cp1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 40, 170, 30));

        jTabbedPane7.addTab("Clerk <-> Operator", new javax.swing.ImageIcon(getClass().getResource("/img/icons8-insert-white-space-32.png")), jPanel29); // NOI18N

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

        cta.setBackground(new java.awt.Color(255, 255, 255));
        cta.setText("Regions only");
        jPanel43.add(cta, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 10, 170, -1));

        ctb.setBackground(new java.awt.Color(255, 255, 255));
        ctb.setText("Regions only");
        jPanel43.add(ctb, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 10, 160, -1));

        cg2.setEditable(true);
        jPanel43.add(cg2, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 40, 170, 30));

        cg1.setEditable(true);
        cg1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
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

        jLabel157.setText("Select currency");

        tw5.setEditable(true);
        tw5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
        tw5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tw5ActionPerformed(evt);
            }
        });

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

        jLabel165.setText("Grand Total for the selected currency");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jLabel157)
                        .addGap(53, 53, 53)
                        .addComponent(tw5, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel165)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tw6, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(89, Short.MAX_VALUE))
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane22, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tw5, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                    .addComponent(jLabel157))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane22, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tw6, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                    .addComponent(jLabel165))
                .addGap(39, 39, 39))
        );

        jPanel13.add(jPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(224, 84, 480, 290));

        tw1.setBackground(new java.awt.Color(255, 255, 255));
        tw1.setText("Regions");
        jPanel13.add(tw1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 10, 170, -1));

        tw2.setBackground(new java.awt.Color(255, 255, 255));
        tw2.setText("Branches");
        jPanel13.add(tw2, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 10, 160, -1));

        tw4.setEditable(true);
        jPanel13.add(tw4, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 40, 170, 30));

        tw3.setEditable(true);
        tw3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
        tw3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tw3ActionPerformed(evt);
            }
        });
        jPanel13.add(tw3, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 40, 170, 30));

        javax.swing.GroupLayout profit_and_loses4Layout = new javax.swing.GroupLayout(profit_and_loses4);
        profit_and_loses4.setLayout(profit_and_loses4Layout);
        profit_and_loses4Layout.setHorizontalGroup(
            profit_and_loses4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1000, Short.MAX_VALUE)
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

        javax.swing.GroupLayout reportsLayout = new javax.swing.GroupLayout(reports);
        reports.setLayout(reportsLayout);
        reportsLayout.setHorizontalGroup(
            reportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1000, Short.MAX_VALUE)
            .addGroup(reportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jTabbedPane7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 1000, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        reportsLayout.setVerticalGroup(
            reportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 580, Short.MAX_VALUE)
            .addGroup(reportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jTabbedPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 580, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel1.add(reports, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 140, 1000, 580));

        jPanel47.setBackground(new java.awt.Color(255, 255, 255));
        jPanel47.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel74.setText("Notification Subject");
        jPanel47.add(jLabel74, new org.netbeans.lib.awtextra.AbsoluteConstraints(303, 29, -1, -1));
        jPanel47.add(n1, new org.netbeans.lib.awtextra.AbsoluteConstraints(414, 20, 217, 32));

        n2.setBorder(javax.swing.BorderFactory.createTitledBorder("Write your notification here"));
        jScrollPane7.setViewportView(n2);

        jPanel47.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(303, 70, 360, 172));

        jLabel188.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-send-32.png"))); // NOI18N
        jLabel188.setText("Save Notification");
        jLabel188.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel188MouseClicked(evt);
            }
        });
        jPanel47.add(jLabel188, new org.netbeans.lib.awtextra.AbsoluteConstraints(537, 411, 126, -1));

        n3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "Regional Managers", "Finance Personel", "Counter Clerk", "Strong Room Operator", "Branch Manager" }));
        n3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                n3ActionPerformed(evt);
            }
        });
        jPanel47.add(n3, new org.netbeans.lib.awtextra.AbsoluteConstraints(425, 248, 206, 28));

        jLabel189.setText("Select the audience");
        jPanel47.add(jLabel189, new org.netbeans.lib.awtextra.AbsoluteConstraints(303, 255, -1, -1));

        n4.setEditable(false);
        n4.setBorder(javax.swing.BorderFactory.createTitledBorder("List of your recipients"));
        jScrollPane8.setViewportView(n4);

        jPanel47.add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(303, 294, 360, 111));

        jLabel190.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Clear-icon.png"))); // NOI18N
        jLabel190.setText("Remove Selecetd Audiences");
        jLabel190.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel190MouseClicked(evt);
            }
        });
        jPanel47.add(jLabel190, new org.netbeans.lib.awtextra.AbsoluteConstraints(313, 411, 180, -1));
        jPanel47.add(mk4, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 480, 0, -1));

        jTabbedPane5.addTab("Post Notifications", new javax.swing.ImageIcon(getClass().getResource("/img/icons8-megaphone-32.png")), jPanel47); // NOI18N

        jPanel60.setBackground(new java.awt.Color(255, 255, 255));

        jLabel187.setText("Select Subject");

        ntf1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
        ntf1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ntf1ActionPerformed(evt);
            }
        });

        jLabel191.setText("Notification from");

        ntf2.setEditable(false);
        ntf2.setBackground(new java.awt.Color(255, 255, 255));

        ntf4.setEditable(false);
        ntf4.setBorder(javax.swing.BorderFactory.createTitledBorder("Notication content"));
        jScrollPane14.setViewportView(ntf4);

        jLabel192.setText("Notification send on");

        javax.swing.GroupLayout jPanel60Layout = new javax.swing.GroupLayout(jPanel60);
        jPanel60.setLayout(jPanel60Layout);
        jPanel60Layout.setHorizontalGroup(
            jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel60Layout.createSequentialGroup()
                .addContainerGap(342, Short.MAX_VALUE)
                .addGroup(jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane14, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel60Layout.createSequentialGroup()
                        .addGroup(jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel60Layout.createSequentialGroup()
                                .addGroup(jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel187)
                                    .addComponent(jLabel192))
                                .addGap(33, 33, 33))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel60Layout.createSequentialGroup()
                                .addComponent(jLabel191, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(22, 22, 22)))
                        .addGroup(jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(ntf1, 0, 170, Short.MAX_VALUE)
                            .addComponent(ntf2)
                            .addComponent(ntf3))))
                .addGap(345, 345, 345))
        );
        jPanel60Layout.setVerticalGroup(
            jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel60Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel187)
                    .addComponent(ntf1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel191)
                    .addComponent(ntf2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ntf3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel192))
                .addGap(26, 26, 26)
                .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(205, Short.MAX_VALUE))
        );

        jTabbedPane5.addTab("View Notifications", new javax.swing.ImageIcon(getClass().getResource("/img/icons8-received-32.png")), jPanel60); // NOI18N

        javax.swing.GroupLayout currenciesLayout = new javax.swing.GroupLayout(currencies);
        currencies.setLayout(currenciesLayout);
        currenciesLayout.setHorizontalGroup(
            currenciesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1000, Short.MAX_VALUE)
            .addGroup(currenciesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, currenciesLayout.createSequentialGroup()
                    .addComponent(jTabbedPane5)
                    .addContainerGap()))
        );
        currenciesLayout.setVerticalGroup(
            currenciesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 580, Short.MAX_VALUE)
            .addGroup(currenciesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, currenciesLayout.createSequentialGroup()
                    .addComponent(jTabbedPane5)
                    .addContainerGap()))
        );

        jPanel1.add(currencies, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 140, 1000, 580));

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
                .addContainerGap(533, Short.MAX_VALUE))
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
                .addContainerGap(249, Short.MAX_VALUE))
        );
        jPanel56Layout.setVerticalGroup(
            jPanel56Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel56Layout.createSequentialGroup()
                .addGap(107, 107, 107)
                .addComponent(jScrollPane24, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(94, Short.MAX_VALUE))
        );

        jTabbedPane12.addTab("Delete Denomination", new javax.swing.ImageIcon(getClass().getResource("/img/rat.png")), jPanel56); // NOI18N

        javax.swing.GroupLayout denominationsLayout = new javax.swing.GroupLayout(denominations);
        denominations.setLayout(denominationsLayout);
        denominationsLayout.setHorizontalGroup(
            denominationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1000, Short.MAX_VALUE)
            .addGroup(denominationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jTabbedPane12, javax.swing.GroupLayout.Alignment.TRAILING))
        );
        denominationsLayout.setVerticalGroup(
            denominationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 580, Short.MAX_VALUE)
            .addGroup(denominationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jTabbedPane12, javax.swing.GroupLayout.Alignment.TRAILING))
        );

        jPanel1.add(denominations, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 140, 1000, 580));

        jPanel52.setBackground(new java.awt.Color(255, 255, 255));

        jLabel71.setText("Select branch");

        jComboBox16.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel113.setText("Enter amount");

        jLabel114.setText("Select currency");

        jComboBox17.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jComboBox18.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel115.setText("Select denominations");

        jLabel116.setText("No. denominations");

        jTable14.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Branch", "Currency", "Denominations", "No. denominations", "Total"
            }
        ));
        jScrollPane26.setViewportView(jTable14);

        javax.swing.GroupLayout jPanel52Layout = new javax.swing.GroupLayout(jPanel52);
        jPanel52.setLayout(jPanel52Layout);
        jPanel52Layout.setHorizontalGroup(
            jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel52Layout.createSequentialGroup()
                .addGroup(jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel52Layout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addComponent(jScrollPane26, javax.swing.GroupLayout.PREFERRED_SIZE, 862, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel52Layout.createSequentialGroup()
                        .addGap(342, 342, 342)
                        .addGroup(jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel71)
                            .addComponent(jLabel113)
                            .addComponent(jLabel115)
                            .addComponent(jLabel114)
                            .addComponent(jLabel116))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jComboBox18, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jFormattedTextField6)
                            .addComponent(jComboBox16, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBox17, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jFormattedTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(62, Short.MAX_VALUE))
        );
        jPanel52Layout.setVerticalGroup(
            jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel52Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel71)
                    .addComponent(jComboBox16, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel114)
                    .addComponent(jComboBox17, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jFormattedTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel113))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox18, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel115))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jFormattedTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel116))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane26, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );

        javax.swing.GroupLayout opening_balancesLayout = new javax.swing.GroupLayout(opening_balances);
        opening_balances.setLayout(opening_balancesLayout);
        opening_balancesLayout.setHorizontalGroup(
            opening_balancesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1000, Short.MAX_VALUE)
            .addGroup(opening_balancesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel52, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        opening_balancesLayout.setVerticalGroup(
            opening_balancesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 588, Short.MAX_VALUE)
            .addGroup(opening_balancesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel52, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(opening_balances, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 140, 1000, 580));

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel25.setText("Region name");

        jLabel31.setText("Branch name");

        jLabel32.setText("Area code");

        b3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                b3KeyTyped(evt);
            }
        });

        jScrollPane3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Add a description of the branch", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        jScrollPane3.setViewportView(b4);

        jLabel37.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-new-document-32.png"))); // NOI18N
        jLabel37.setText("Save ");
        jLabel37.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel37MouseClicked(evt);
            }
        });

        b1.setEditable(false);
        b1.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel25)
                            .addGap(18, 18, 18)
                            .addComponent(b1))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel31)
                            .addGap(18, 18, 18)
                            .addComponent(b2, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel32)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(b3, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(696, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(b1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jLabel31))
                    .addComponent(b2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(b3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel37, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                .addGap(248, 248, 248))
        );

        jTabbedPane1.addTab("Add branches", new javax.swing.ImageIcon(getClass().getResource("/img/icons8-city-32.png")), jPanel3); // NOI18N

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel38.setText("Region name");

        jLabel39.setText("Branch name");

        jLabel40.setText("Area code");

        bu_table.setAutoCreateRowSorter(true);
        bu_table.setModel(new javax.swing.table.DefaultTableModel(
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
        bu_table.setRowHeight(20);
        bu_table.setShowHorizontalLines(false);
        bu_table.setShowVerticalLines(false);
        bu_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bu_tableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(bu_table);

        bu4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Add a description", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        jScrollPane4.setViewportView(bu4);

        bu1.setEditable(false);
        bu1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel43.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-update-32.png"))); // NOI18N
        jLabel43.setText("Update");
        jLabel43.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel43MouseClicked(evt);
            }
        });

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
                        .addComponent(bu3, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane4)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel38)
                                .addGap(18, 18, 18)
                                .addComponent(bu1))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel39)
                                .addGap(18, 18, 18)
                                .addComponent(bu2, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel43)))
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
                            .addComponent(bu1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel38))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addComponent(jLabel39))
                            .addComponent(bu2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel40)
                            .addComponent(bu3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 512, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Update branch info", new javax.swing.ImageIcon(getClass().getResource("/img/comp.png")), jPanel4); // NOI18N

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        bv_table.setAutoCreateRowSorter(true);
        bv_table.setModel(new javax.swing.table.DefaultTableModel(
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
        bv_table.setRowHeight(20);
        bv_table.setShowHorizontalLines(false);
        bv_table.setShowVerticalLines(false);
        jScrollPane5.setViewportView(bv_table);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 995, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addGap(154, 154, 154)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 687, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(154, Short.MAX_VALUE)))
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

        jTabbedPane1.addTab("View branches", new javax.swing.ImageIcon(getClass().getResource("/img/icons8-city-32.png")), jPanel5); // NOI18N

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        bd_table.setAutoCreateRowSorter(true);
        bd_table.setModel(new javax.swing.table.DefaultTableModel(
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
        bd_table.setRowHeight(20);
        bd_table.setShowHorizontalLines(false);
        bd_table.setShowVerticalLines(false);
        bd_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bd_tableMouseClicked(evt);
            }
        });
        bd_table.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                bd_tableKeyPressed(evt);
            }
        });
        jScrollPane6.setViewportView(bd_table);

        bid.setText("jLabel23");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(bid)
                .addContainerGap(907, Short.MAX_VALUE))
            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel6Layout.createSequentialGroup()
                    .addGap(154, 154, 154)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 687, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(154, Short.MAX_VALUE)))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(195, 195, 195)
                .addComponent(bid, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(339, Short.MAX_VALUE))
            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel6Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 512, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        jTabbedPane1.addTab("Delete branches", new javax.swing.ImageIcon(getClass().getResource("/img/comp.png")), jPanel6); // NOI18N

        javax.swing.GroupLayout branchesLayout = new javax.swing.GroupLayout(branches);
        branches.setLayout(branchesLayout);
        branchesLayout.setHorizontalGroup(
            branchesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1000, Short.MAX_VALUE)
            .addGroup(branchesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING))
        );
        branchesLayout.setVerticalGroup(
            branchesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 580, Short.MAX_VALUE)
            .addGroup(branchesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING))
        );

        jPanel1.add(branches, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 140, 1000, 580));

        jTabbedPane10.setBackground(new java.awt.Color(255, 255, 255));

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));

        jLabel47.setText("Name");

        a1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                a1KeyTyped(evt);
            }
        });

        jLabel98.setText("Surname");

        a2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                a2KeyTyped(evt);
            }
        });

        jLabel99.setText("Gender");

        a3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "male", "female" }));

        jLabel100.setText("Phone number");

        a4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                a4KeyTyped(evt);
            }
        });

        jLabel101.setText("ID number");

        jLabel102.setText("Position");

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
        jScrollPane34.setViewportView(a_table);

        jLabel103.setText("Create username");

        a10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                a10KeyPressed(evt);
            }
        });

        jLabel104.setText("Create password");

        jLabel105.setText("Confirm password");

        a6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));

        jLabel117.setText("Select branch");

        a7.setEditable(false);
        a7.setBackground(new java.awt.Color(255, 255, 255));
        a7.setText("Branch Manager");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane34, javax.swing.GroupLayout.DEFAULT_SIZE, 975, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel101, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel100, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE)
                    .addComponent(jLabel99, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel98, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel47, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(a1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(a2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(a3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(a4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(a5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel104)
                            .addComponent(jLabel105))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(a10, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(a9, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel103, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel102, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel117, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(a8, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                            .addComponent(a6, 0, 190, Short.MAX_VALUE)
                            .addComponent(a7))))
                .addGap(0, 400, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel47)
                    .addComponent(a1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel117)
                    .addComponent(a6, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel98)
                    .addComponent(a2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel102)
                    .addComponent(a7, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(a3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel99)
                    .addComponent(jLabel103)
                    .addComponent(a8, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jLabel100))
                    .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(a4, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel104)
                        .addComponent(a9, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel101, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(a5, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel105)
                    .addComponent(a10, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addComponent(jScrollPane34, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane10.addTab("Add Users", new javax.swing.ImageIcon(getClass().getResource("/img/icons8-registration-32.png")), jPanel14); // NOI18N

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));
        jPanel16.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        u_table.setAutoCreateRowSorter(true);
        u_table.setModel(new javax.swing.table.DefaultTableModel(
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

        jPanel16.add(jScrollPane9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 975, 332));

        jLabel48.setText("Name");
        jPanel16.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, 77, -1));

        jLabel106.setText("Surname");
        jPanel16.add(jLabel106, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 385, 77, -1));

        jLabel107.setText("Gender");
        jPanel16.add(jLabel107, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 421, 77, -1));

        jLabel108.setText("Phone number");
        jPanel16.add(jLabel108, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 462, 77, -1));

        jLabel109.setText("ID number");
        jPanel16.add(jLabel109, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 490, 77, 26));
        jPanel16.add(u5, new org.netbeans.lib.awtextra.AbsoluteConstraints(97, 489, 190, 28));
        jPanel16.add(u4, new org.netbeans.lib.awtextra.AbsoluteConstraints(97, 455, 190, 28));

        u3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "male", "female" }));
        jPanel16.add(u3, new org.netbeans.lib.awtextra.AbsoluteConstraints(97, 414, 190, 29));
        jPanel16.add(u2, new org.netbeans.lib.awtextra.AbsoluteConstraints(97, 378, 190, 28));
        jPanel16.add(u1, new org.netbeans.lib.awtextra.AbsoluteConstraints(97, 343, 190, 28));

        u6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
        jPanel16.add(u6, new org.netbeans.lib.awtextra.AbsoluteConstraints(401, 343, 190, 29));

        jLabel49.setText("Select Branch");
        jPanel16.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(305, 350, -1, -1));
        jPanel16.add(uid, new org.netbeans.lib.awtextra.AbsoluteConstraints(808, 523, 10, -1));

        jLabel52.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel52.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-new-document-32.png"))); // NOI18N
        jLabel52.setText("Update Account");
        jLabel52.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel52MouseClicked(evt);
            }
        });
        jPanel16.add(jLabel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(401, 412, 190, -1));

        jLabel53.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/excel.png"))); // NOI18N
        jLabel53.setText("Export to excel");
        jPanel16.add(jLabel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(401, 489, 118, 28));

        jLabel54.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/pdf.png"))); // NOI18N
        jLabel54.setText("Export to pdf");
        jPanel16.add(jLabel54, new org.netbeans.lib.awtextra.AbsoluteConstraints(529, 489, 118, 28));

        jLabel55.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/print.png"))); // NOI18N
        jLabel55.setText("Print user details");
        jPanel16.add(jLabel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(657, 489, 118, 28));

        u7.setEditable(false);
        u7.setBackground(new java.awt.Color(255, 255, 255));
        u7.setText("Branch Manager");
        u7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                u7ActionPerformed(evt);
            }
        });
        jPanel16.add(u7, new org.netbeans.lib.awtextra.AbsoluteConstraints(401, 378, 190, 28));

        jLabel18.setText("Position");
        jPanel16.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(305, 385, -1, -1));

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
        jScrollPane35.setViewportView(d_table);

        jPanel35.add(jScrollPane35, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, 975, 510));
        jPanel35.add(na, new org.netbeans.lib.awtextra.AbsoluteConstraints(597, 534, -1, -1));
        jPanel35.add(nb, new org.netbeans.lib.awtextra.AbsoluteConstraints(633, 528, -1, -1));

        jTabbedPane10.addTab("Delete User", new javax.swing.ImageIcon(getClass().getResource("/img/icons8-denied-32.png")), jPanel35); // NOI18N

        javax.swing.GroupLayout usersLayout = new javax.swing.GroupLayout(users);
        users.setLayout(usersLayout);
        usersLayout.setHorizontalGroup(
            usersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1000, Short.MAX_VALUE)
            .addGroup(usersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jTabbedPane10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        usersLayout.setVerticalGroup(
            usersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 580, Short.MAX_VALUE)
            .addGroup(usersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jTabbedPane10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel1.add(users, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 140, 1000, 580));

        jPanel28.setBackground(new java.awt.Color(255, 255, 255));

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));

        rtl_table.setAutoCreateRowSorter(true);
        rtl_table.setModel(new javax.swing.table.DefaultTableModel(
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
        rtl_table.setRowHeight(20);
        rtl_table.setShowHorizontalLines(false);
        rtl_table.setShowVerticalLines(false);
        rtl_table.getTableHeader().setReorderingAllowed(false);
        jScrollPane28.setViewportView(rtl_table);

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(171, 171, 171)
                .addComponent(jScrollPane28, javax.swing.GroupLayout.PREFERRED_SIZE, 679, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(145, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jScrollPane28, javax.swing.GroupLayout.PREFERRED_SIZE, 449, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(57, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("Local Currency", new javax.swing.ImageIcon(getClass().getResource("/img/vp.png")), jPanel17); // NOI18N

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));

        rtl_table1.setAutoCreateRowSorter(true);
        rtl_table1.setModel(new javax.swing.table.DefaultTableModel(
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
        rtl_table1.setRowHeight(20);
        rtl_table1.setShowHorizontalLines(false);
        rtl_table1.setShowVerticalLines(false);
        rtl_table1.getTableHeader().setReorderingAllowed(false);
        jScrollPane30.setViewportView(rtl_table1);

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(171, 171, 171)
                .addComponent(jScrollPane30, javax.swing.GroupLayout.PREFERRED_SIZE, 679, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(145, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jScrollPane30, javax.swing.GroupLayout.PREFERRED_SIZE, 449, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(57, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("Cross Currency", new javax.swing.ImageIcon(getClass().getResource("/img/up32.png")), jPanel18); // NOI18N

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane3)
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane3)
        );

        javax.swing.GroupLayout ratesLayout = new javax.swing.GroupLayout(rates);
        rates.setLayout(ratesLayout);
        ratesLayout.setHorizontalGroup(
            ratesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1000, Short.MAX_VALUE)
            .addGroup(ratesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        ratesLayout.setVerticalGroup(
            ratesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 580, Short.MAX_VALUE)
            .addGroup(ratesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(rates, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 140, 1000, 580));

        upass.setText("jLabel1");

        configs2.setText("jLabel1");

        uname.setText("jLabel1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(upass)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(configs2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(uname)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 730, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(upass, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(configs2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(uname, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        DefaultTableModel dm = (DefaultTableModel) bu_table.getModel();
        dm.setNumRows(0);
        DefaultTableModel dm1 = (DefaultTableModel) bd_table.getModel();
        dm1.setNumRows(0);
        DefaultTableModel dm2 = (DefaultTableModel) bv_table.getModel();
        dm2.setNumRows(0);
DB_USER = uname.getText();
        DB_PAS = upass.getText();
        String query1 = "SELECT region_name,branch_name,area_code,description,created_by,branch_id FROM branches";
        try {
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
            stm = con.createStatement();

            rs = stm.executeQuery(query1);
            boolean boolm = rs.isBeforeFirst();
            while (rs.next()) {
                if (boolm == true) {
                    String a = rs.getString("region_name");
                    String b = rs.getString("branch_name");
                    String c = rs.getString("area_code");
                    String d = rs.getString("description");
                    String e = rs.getString("created_by");
                    String f = rs.getString("branch_id");

                    dm.addRow(new Object[]{a, b, c, f});
                    dm1.addRow(new Object[]{a, b, c, f});
                    dm2.addRow(new Object[]{a, b, c, f});

                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
        }

        rates.setVisible(false);
        reports.setVisible(false);
        users.setVisible(false);
        //branches.setVisible(false);
        operations.setVisible(false);

        currencies.setVisible(false);
        opening_balances.setVisible(false);
        denominations.setVisible(false);
        if (y == 1000) {
            rates.setSize(1000, 580);
            reports.setSize(1000, 580);
            users.setSize(1000, 580);
            branches.setSize(1000, 580);
            operations.setSize(1000, 580);

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
                            operations.setSize(i, 580);

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
            operations.setSize(y, 580);
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
        DefaultTableModel dm1 = (DefaultTableModel) rtl_table.getModel();
        dm1.setNumRows(0);
        DefaultTableModel dm = (DefaultTableModel) rtl_table1.getModel();
        dm.setNumRows(0);
DB_USER = uname.getText();
        DB_PAS = upass.getText();
        String xz = "true";

        String query1 = "SELECT base_currency,quote_currency,buying_rate,selling_rate FROM config_rates";

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

                    //int f = rs.getInt("exchange_rate_id");
                    if (a.matches(phy.getText())) {
                        dm1.addRow(new Object[]{a, b, c, d});
                    }
                    if (!a.matches(phy.getText())) {
                        dm.addRow(new Object[]{a, b, c, d});
                    }

                }
            }
            if (bool == false) {
                JOptionPane.showMessageDialog(null, "Exchange rate table is empty please configure ", "Exchange rate notification", JOptionPane.WARNING_MESSAGE);
            }

        } catch (ClassNotFoundException | SQLException ez) {
            JOptionPane.showMessageDialog(null, ez.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
        }
//rates.setVisible(false);
        reports.setVisible(false);
        users.setVisible(false);
        branches.setVisible(false);
        operations.setVisible(false);

        currencies.setVisible(false);
        opening_balances.setVisible(false);
        denominations.setVisible(false);
        if (y == 1000) {
            rates.setSize(1000, 580);
            reports.setSize(1000, 580);
            users.setSize(1000, 580);
            branches.setSize(1000, 580);
            operations.setSize(1000, 580);

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
                            operations.setSize(i, 580);

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
            operations.setSize(y, 580);
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
        operations.setVisible(false);

        currencies.setVisible(false);
        opening_balances.setVisible(false);
        denominations.setVisible(false);
        if (y == 1000) {
            rates.setSize(1000, 580);
            reports.setSize(1000, 580);
            users.setSize(1000, 580);
            branches.setSize(1000, 580);
            operations.setSize(1000, 580);

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
                            operations.setSize(i, 580);

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
            operations.setSize(y, 580);
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
        DefaultComboBoxModel dc1 = new DefaultComboBoxModel();
        a6.setModel(dc1);
        DefaultComboBoxModel dc2 = new DefaultComboBoxModel();
        u6.setModel(dc2);
        DefaultTableModel dm = (DefaultTableModel) u_table.getModel();
        dm.setNumRows(0);
        DefaultTableModel dm1 = (DefaultTableModel) d_table.getModel();
        dm1.setNumRows(0);
DB_USER = uname.getText();
        DB_PAS = upass.getText();
        String pos = "Branch Manager";

        String query1 = "SELECT fname,lname,gender,region,branch,position,phone_number,national_id,user_id FROM users where region='" + reg.getText() + "' and position='" + pos + "'";
        String query = "SELECT region_name,branch_name FROM branches where region_name='" + reg.getText() + "'";
        try {
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
            stm = con.createStatement();

            rs = stm.executeQuery(query);
            boolean bool = rs.isBeforeFirst();
            while (rs.next()) {
                if (bool == true) {
                    String a = rs.getString("branch_name");
                    //a6.addItem(""); 
                    a6.addItem(a);
                    //u6.addItem("");
                    u6.addItem(a);
                }
            }

            rs = stm.executeQuery(query1);
            boolean boom = rs.isBeforeFirst();
            while (rs.next()) {
                if (boom == true) {
                    String a = rs.getString("fname");
                    String b = rs.getString("lname");
                    String c = rs.getString("gender");

                    String d = rs.getString("region");
                    String e = rs.getString("branch");
                    String f = rs.getString("position");
                    String g = rs.getString("phone_number");
                    String h = rs.getString("national_id");
                    String i = rs.getString("user_id");
                    dm.addRow(new Object[]{a, b, c, e, f, g, h, i});
                    dm1.addRow(new Object[]{a, b, c, e, f, g, h, i});

                }
            }

        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Something went wrong", JOptionPane.ERROR_MESSAGE);
        }

        rates.setVisible(false);
        reports.setVisible(false);

        branches.setVisible(false);
        operations.setVisible(false);

        currencies.setVisible(false);
        opening_balances.setVisible(false);
        denominations.setVisible(false);
        if (y == 1000) {
            rates.setSize(1000, 510);
            reports.setSize(1000, 510);
            users.setSize(1000, 510);
            branches.setSize(1000, 510);
            operations.setSize(1000, 510);

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
                            operations.setSize(i, 580);

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
            operations.setSize(y, 580);
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
            //xk = 7 +9;
        }
    }//GEN-LAST:event_jLabel8MouseClicked

    private void jLabel22MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel22MouseClicked
        int xk = 10;
        dashboard_rates.setVisible(false);
        dashboard_reports.setVisible(false);
        dashboard_master.setVisible(false);
        //dashboard_home.setVisible(false);
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
        rates.setVisible(false);
        reports.setVisible(false);
        users.setVisible(false);
        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        operations.setVisible(false);

        currencies.setVisible(false);
        opening_balances.setVisible(false);
        denominations.setVisible(false);
        if (y == 1000) {
            rates.setSize(1000, 580);
            reports.setSize(1000, 580);
            users.setSize(1000, 580);
            branches.setSize(1000, 580);
            operations.setSize(1000, 580);

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
                            operations.setSize(i, 580);

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
            operations.setSize(y, 580);
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
        operations.setVisible(false);

        currencies.setVisible(false);
        opening_balances.setVisible(false);
        denominations.setVisible(false);
        if (y == 1000) {
            rates.setSize(1000, 510);
            reports.setSize(1000, 510);
            users.setSize(1000, 510);
            branches.setSize(1000, 510);
            operations.setSize(1000, 510);

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
                            operations.setSize(i, 580);

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
            operations.setSize(y, 580);
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
        operations.setVisible(false);

        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        opening_balances.setVisible(false);
        denominations.setVisible(false);
        if (y == 1000) {
            rates.setSize(1000, 580);
            reports.setSize(1000, 580);
            users.setSize(1000, 580);
            branches.setSize(1000, 580);
            operations.setSize(1000, 580);

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
                            operations.setSize(i, 580);

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
            operations.setSize(y, 580);
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
        operations.setVisible(false);

        currencies.setVisible(false);
        opening_balances.setVisible(false);
     DB_USER = uname.getText();
        DB_PAS = upass.getText();
        if (y == 1000) {
            rates.setSize(1000, 580);
            reports.setSize(1000, 580);
            users.setSize(1000, 580);
            branches.setSize(1000, 580);
            operations.setSize(1000, 580);

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
                            operations.setSize(i, 580);

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
            operations.setSize(y, 580);
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
        operations.setVisible(false);

        currencies.setVisible(false);
       DB_USER = uname.getText();
        DB_PAS = upass.getText();
        denominations.setVisible(false);
        if (y == 1000) {
            rates.setSize(1000, 580);
            reports.setSize(1000, 580);
            users.setSize(1000, 580);
            branches.setSize(1000, 580);
            operations.setSize(1000, 580);

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
                            operations.setSize(i, 580);

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
            operations.setSize(y, 580);
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

    private void jLabel37MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel37MouseClicked
        java.util.Date date = new java.util.Date();
        long t = date.getTime();
        java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(t);
DB_USER = uname.getText();
        DB_PAS = upass.getText();
        if (b2.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter the region name", "Error Message", JOptionPane.ERROR_MESSAGE);
        }
        if (b3.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter the code number", "Error Message", JOptionPane.ERROR_MESSAGE);
        }

        if (!b2.getText().isEmpty() && !b3.getText().isEmpty()) {
            int ax = Integer.parseInt(b3.getText(), 10);

            String query = "SELECT region_name,branch_name,area_code FROM branches where branch_name='" + b2.getText() + "'";
            try {
                Class.forName(DB_DRIVER);
                con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                stm = con.createStatement();
                rs = stm.executeQuery(query);
                boolean bool = rs.isBeforeFirst();
                while (rs.next()) {
                    if (bool == true) {
                        String a = rs.getString("branch_name");
                        int b = rs.getInt("area_code");
                        if (b2.getText().equalsIgnoreCase(a)) {
                            JOptionPane.showMessageDialog(null, "Branch " + a + "already exist in the system", "System Error", JOptionPane.ERROR_MESSAGE);
                            b2.setText("");

                        }
                        if (ax == b) {
                            JOptionPane.showMessageDialog(null, "Area code " + b + "already exist in the system", "System Error", JOptionPane.ERROR_MESSAGE);
                            b3.setText("");

                        }
                        if (!b2.getText().equalsIgnoreCase(a) && ax != b) {
                            try {
                                con = DriverManager.getConnection(DB_URL, DB_USER, DB_PAS);
                                pst = con.prepareStatement("insert into branches values(?,?,?,?,?,?)");

                                pst.setString(1, b1.getText());
                                pst.setString(2, b2.getText());
                                pst.setInt(3, ax);
                                pst.setString(4, b4.getText());
                                pst.setString(5, name.getText());
                                pst.setTimestamp(6, sqlTimestamp);

                                int i = pst.executeUpdate();
                                if (i > 0) {
                                    JOptionPane.showMessageDialog(null, "You have successfully added a branch / site into the system" + " " + " ", " Providence says....", JOptionPane.INFORMATION_MESSAGE);

                                    //b1.setText("");
                                    b2.setText("");
                                    b3.setText("");
                                    b4.setText("");

                                }
                            } catch (SQLException ev) {
                                JOptionPane.showMessageDialog(null, ev.getMessage());

                            }
                        }

                    }
                }
                if (bool == false) {
                    try {
                        con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                        pst = con.prepareStatement("insert into branches values(?,?,?,?,?,?)");

                        pst.setString(1, b1.getText());
                        pst.setString(2, b2.getText());
                        pst.setInt(3, ax);
                        pst.setString(4, b4.getText());
                        pst.setString(5, name.getText());
                        pst.setTimestamp(6, sqlTimestamp);

                        int i = pst.executeUpdate();
                        if (i > 0) {
                            JOptionPane.showMessageDialog(null, "You have successfully added a branch / site into the system" + " " + " ", " Providence says....", JOptionPane.INFORMATION_MESSAGE);

                            //b1.setText("");
                            b2.setText("");
                            b3.setText("");
                            b4.setText("");

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

    private void a4KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_a4KeyTyped
        char key = evt.getKeyChar();
        if (!Character.isDigit(key)) {
            evt.consume();
        }
    }//GEN-LAST:event_a4KeyTyped

    private void a10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_a10KeyPressed
        int key = evt.getKeyCode();
        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        if (key == 10) {
            if (a1.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter the name", "Some information missing", JOptionPane.ERROR_MESSAGE);
            }
            if (a1.getText().length() < 3) {
                JOptionPane.showMessageDialog(null, "The name must have at least 3 characters ", "Some information missing", JOptionPane.ERROR_MESSAGE);
            }
            if (a2.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter the surname", "Some information missing", JOptionPane.ERROR_MESSAGE);
            }
            if (a2.getText().length() < 3) {
                JOptionPane.showMessageDialog(null, "The surname must have at least 3 characters ", "Some information missing", JOptionPane.ERROR_MESSAGE);
            }
            if (a3.getSelectedItem().equals("")) {
                JOptionPane.showMessageDialog(null, "Select gender", "Some information missing", JOptionPane.ERROR_MESSAGE);
            }
            if (a4.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "User phone number is required", "Some information missing", JOptionPane.ERROR_MESSAGE);
            }
            if (a4.getText().length() < 10) {
                JOptionPane.showMessageDialog(null, "Please be advised thst the phone number must have at least 10 digits ", "Some information missing", JOptionPane.ERROR_MESSAGE);
            }
            if (a5.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Enter national identity number ", "Some information missing", JOptionPane.ERROR_MESSAGE);
            }
            if (a5.getText().length() < 10) {
                JOptionPane.showMessageDialog(null, "User national identity number must have at least 10 characters ", "Some information missing", JOptionPane.ERROR_MESSAGE);
            }
            if (a6.getSelectedItem().equals("")) {
                JOptionPane.showMessageDialog(null, "Select branch", "Some information missing", JOptionPane.ERROR_MESSAGE);
            }
            if (a8.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Create username ", "Some information missing", JOptionPane.ERROR_MESSAGE);
            }
            if (a8.getText().length() < 6) {
                JOptionPane.showMessageDialog(null, "The username must have at least 6 characters ", "Some information missing", JOptionPane.ERROR_MESSAGE);
            }
            if (a9.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Create user password ", "Some information missing", JOptionPane.ERROR_MESSAGE);
            }
            if (a9.getText().length() < 6) {
                JOptionPane.showMessageDialog(null, "The password must have at least 6 characters ", "Some information missing", JOptionPane.ERROR_MESSAGE);
            }
            if (a10.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Confirm user password ", "Some information missing", JOptionPane.ERROR_MESSAGE);
            }
            if (!a4.getText().startsWith("0")) {
                JOptionPane.showMessageDialog(null, "Please be advised that user phone number must begin with a zero ", "Some information missing", JOptionPane.ERROR_MESSAGE);
            }
            if (!a1.getText().isEmpty() && a1.getText().length() >= 3 && !a2.getText().isEmpty() && a2.getText().length() >= 3
                    && !a4.getText().isEmpty() && a4.getText().length() >= 10 && !a5.getText().isEmpty() && a5.getText().length() >= 10
                    && !a8.getText().isEmpty() && a8.getText().length() >= 6 && !a9.getText().isEmpty() && a9.getText().length() >= 6
                    && !a10.getText().isEmpty() && !a3.getSelectedItem().equals("") && !a6.getSelectedItem().equals("") && a4.getText().startsWith("0")) {

                String zc = a4.getText();
                int ct1 = Integer.parseInt(zc, 10);
                String query = "SELECT fname,lname,username,phone_number,national_id FROM users where fname='" + a1.getText() + "' or lname='" + a2.getText() + "' or national_id='" + a5.getText() + "' or username='" + a8.getText() + "' or phone_number='" + ct1 + "'";
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
                            if (a1.getText().matches(a) && a2.getText().matches(b)) {
                                JOptionPane.showMessageDialog(null, "User already exist in the system ", "Some information missing", JOptionPane.ERROR_MESSAGE);
                            }
                            String c = rs.getString("username");
                            if (a8.getText().matches(c)) {
                                JOptionPane.showMessageDialog(null, "Username already exist in the system ", "Some information missing", JOptionPane.ERROR_MESSAGE);
                            }
                            String d = rs.getString("phone_number");
                            String xc = "0".concat(d);

                            if (a4.getText().matches(xc)) {
                                JOptionPane.showMessageDialog(null, "Phone number already exists ", "Some information missing", JOptionPane.ERROR_MESSAGE);
                            }
                            String ef = rs.getString("national_id");
                            if (a5.getText().matches(ef)) {
                                JOptionPane.showMessageDialog(null, "National Identity " + a5.getText() + " " + "already exist ", "Some information missing", JOptionPane.ERROR_MESSAGE);
                            }
                            if ((!a1.getText().matches(a) && a2.getText().matches(b) || a1.getText().matches(a) && !a2.getText().matches(b)) && !a8.getText().matches(c) && !a4.getText().matches(xc) && !a5.getText().matches(ef)) {
                                try {
                                    con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                    pst = con.prepareStatement("insert into users values(?,?,?,?,?,?,?,?,?,?,?)");
                                    pst.setString(1, a1.getText());
                                    pst.setString(2, a2.getText());
                                    pst.setString(3, a3.getSelectedItem().toString());
                                    pst.setInt(4, Integer.parseInt(a4.getText(), 10));
                                    pst.setString(5, a5.getText());
                                    pst.setString(6, reg.getText());
                                    pst.setString(7, a6.getSelectedItem().toString());
                                    pst.setString(8, a7.getText());

                                    pst.setString(9, a8.getText());
                                    pst.setString(10, a10.getText());
                                    pst.setString(11, name.getText());

                                    int i = pst.executeUpdate();
                                    if (i > 0) {
                                        JOptionPane.showMessageDialog(null, "You have successfully added a new user into the system" + " " + " ", " Providence says....", JOptionPane.INFORMATION_MESSAGE);
                                        DefaultTableModel dm = (DefaultTableModel) a_table.getModel();
                                        dm.addRow(new Object[]{a1.getText(), a2.getText(), a3.getSelectedItem().toString(), a4.getText(), a6.getSelectedItem().toString(), a7.getText()});
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
                    if (bool == false) {
                        try {
                            con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                            pst = con.prepareStatement("insert into users values(?,?,?,?,?,?,?,?,?,?,?)");
                            pst.setString(1, a1.getText());
                            pst.setString(2, a2.getText());
                            pst.setString(3, a3.getSelectedItem().toString());
                            pst.setInt(4, Integer.parseInt(a4.getText(), 10));
                            pst.setString(5, a5.getText());
                            pst.setString(6, reg.getText());
                            pst.setString(7, a6.getSelectedItem().toString());
                            pst.setString(8, a7.getText());

                            pst.setString(9, a8.getText());
                            pst.setString(10, a10.getText());
                            pst.setString(11, name.getText());

                            int i = pst.executeUpdate();
                            if (i > 0) {
                                JOptionPane.showMessageDialog(null, "You have successfully added a new user into the system" + " " + " ", " Providence says....", JOptionPane.INFORMATION_MESSAGE);
                                DefaultTableModel dm = (DefaultTableModel) a_table.getModel();
                                dm.addRow(new Object[]{a1.getText(), a2.getText(), a3.getSelectedItem().toString(), a4.getText(), a6.getSelectedItem().toString(), a7.getText()});
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
                } catch (Exception e) {
                }
            }

        }

    }//GEN-LAST:event_a10KeyPressed

    private void u_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_u_tableMouseClicked
        DefaultTableModel model = (DefaultTableModel) u_table.getModel();
        u1.setText(model.getValueAt(u_table.getSelectedRow(), 0).toString());
        u2.setText(model.getValueAt(u_table.getSelectedRow(), 1).toString());
        u4.setText(model.getValueAt(u_table.getSelectedRow(), 5).toString());
        u5.setText(model.getValueAt(u_table.getSelectedRow(), 6).toString());
        uid.setText(model.getValueAt(u_table.getSelectedRow(), 7).toString());
    }//GEN-LAST:event_u_tableMouseClicked

    private void jLabel52MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel52MouseClicked

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
                && !u6.getSelectedItem().equals("")) {
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
                String sqlq = "Update users set fname='" + u1.getText() + "' , lname='" + u2.getText() + "' , gender='" + u3.getSelectedItem().toString() + "' , phone_number='" + sa + "' , national_id='" + u5.getText() + "', position='" + u7.getText() + "', region='" + u6.getSelectedItem().toString() + "'  where user_id='" + uid.getText() + "'";
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

    }//GEN-LAST:event_jLabel52MouseClicked

    private void d_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_d_tableMouseClicked
        DefaultTableModel model = (DefaultTableModel) d_table.getModel();
        na.setText(model.getValueAt(d_table.getSelectedRow(), 0).toString());
        nb.setText(model.getValueAt(d_table.getSelectedRow(), 1).toString());
        uid.setText(model.getValueAt(d_table.getSelectedRow(), 7).toString());
    }//GEN-LAST:event_d_tableMouseClicked

    private void d_tableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_d_tableKeyPressed
        int xv = Integer.parseInt(uid.getText(), 10);
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

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        int input = JOptionPane.showConfirmDialog(null, " Are you sure that you want to close this application", "Providence says....", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (input == JOptionPane.NO_OPTION) {

        }
        if (input == JOptionPane.YES_OPTION) {
            System.exit(4);

        }
    }//GEN-LAST:event_jLabel11MouseClicked

    private void b3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_b3KeyTyped
        char c = evt.getKeyChar();
        if (Character.isAlphabetic(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_b3KeyTyped

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
DB_USER = uname.getText();
        DB_PAS = upass.getText();
        String tg = "Regional";
        String query = "SELECT subject,notification_message,recipients FROM notifications  ";
        try {
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
            stm = con.createStatement();

            rs = stm.executeQuery(query);
            boolean bool = rs.isBeforeFirst();
            while (rs.next()) {
                if (bool == true) {
                    String a = rs.getString("subject");
                    String b = rs.getString("recipients");
                    if (b.contains(tg)) {
                        ntf1.addItem(a);
                    }

                }
            }

        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Something went wrong", JOptionPane.ERROR_MESSAGE);
        }
        rates.setVisible(false);
        reports.setVisible(false);
        users.setVisible(false);
        branches.setVisible(false);
        operations.setVisible(false);

        //currencies.setVisible(false);
        opening_balances.setVisible(false);
        denominations.setVisible(false);
        if (y == 1000) {
            rates.setSize(1000, 580);
            reports.setSize(1000, 580);
            users.setSize(1000, 580);
            branches.setSize(1000, 580);
            operations.setSize(1000, 580);

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
                            operations.setSize(i, 580);

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
            operations.setSize(y, 580);
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
    }//GEN-LAST:event_jLabel4MouseClicked

    private void u7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_u7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_u7ActionPerformed

    private void jLabel43MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel43MouseClicked
        if (bu2.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter the branch name", "Incomplete information provided", JOptionPane.ERROR_MESSAGE);
        }
        if (bu3.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter the branch name", "Incomplete information provided", JOptionPane.ERROR_MESSAGE);
        }
        if (!bu2.getText().isEmpty() && !bu3.getText().isEmpty()) {
            int ds = Integer.parseInt(bid.getText(), 10);
            int dst = Integer.parseInt(bu3.getText(), 10);

            SQLRun run = new SQLRun();

            String sqlq = "Update branches set branch_name='" + bu2.getText() + "', area_code='" + dst + "' where  branch_id='" + ds + "'";
            int updated = run.sqlUpdate(sqlq);
            if (updated > 0) {
                JOptionPane.showMessageDialog(null, " You have successfuly changed information about", "Updated ...", JOptionPane.INFORMATION_MESSAGE);

            }
        }

    }//GEN-LAST:event_jLabel43MouseClicked

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked
        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        int input = JOptionPane.showConfirmDialog(null, " Are you sure that you want to change your password", "Providence says....", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (input == JOptionPane.NO_OPTION) {

        }
        if (input == JOptionPane.YES_OPTION) {
            String a = JOptionPane.showInputDialog(null, "Enter your currenct username", "Username Authentication", JOptionPane.INFORMATION_MESSAGE);
            String b = JOptionPane.showInputDialog(null, "Enter your currenct password", "Passowrd Authentication", JOptionPane.INFORMATION_MESSAGE);

            String query = "SELECT username,password FROM users where username='" + a + "' and password='" + b + "'";
            try {
                Class.forName(DB_DRIVER);
                con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                stm = con.createStatement();

                rs = stm.executeQuery(query);
                boolean bool = rs.isBeforeFirst();
                while (rs.next()) {
                    if (bool == true) {
                        //String mx = rs.getString("username");
                        String c = JOptionPane.showInputDialog(null, "Enter your new username", "Username Authentication", JOptionPane.INFORMATION_MESSAGE);
                        String d = JOptionPane.showInputDialog(null, "Enter your new password", "Passowrd Authentication", JOptionPane.INFORMATION_MESSAGE);
                        if (c.length() < 6) {
                            JOptionPane.showMessageDialog(null, "Your username must have at least 6 character", "Username validation", JOptionPane.WARNING_MESSAGE);
                        }
                        if (d.length() < 5) {
                            JOptionPane.showMessageDialog(null, "Your password must have at least 5 character", "Password validation", JOptionPane.WARNING_MESSAGE);
                        }
                        if (d.length() >= 5 && c.length() >= 6) {
                            int ds = Integer.parseInt(my_id.getText(), 10);
                            SQLRun run = new SQLRun();

                            String sqlq = "Update users set username='" + c + "', password='" + d + "' where  user_id='" + ds + "'";
                            int updated = run.sqlUpdate(sqlq);
                            if (updated > 0) {
                                JOptionPane.showMessageDialog(null, " You have successfuly change your credentials for the BUREAU DE CHANGE SYSTEM", "User Account Updated", JOptionPane.INFORMATION_MESSAGE);

                            }
                        }
                    }
                }
                if (bool == false) {
                    JOptionPane.showMessageDialog(null, "Incorrect credentials", "User Account could not be found ", JOptionPane.WARNING_MESSAGE);

                }
            } catch (ClassNotFoundException | SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jLabel9MouseClicked

    private void jLabel91MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel91MouseClicked
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
                    Logger.getLogger(Finance_Dep.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Finance_Dep.class.getName()).log(Level.SEVERE, null, ex);
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
                        Logger.getLogger(Finance_Dep.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
        }
    }//GEN-LAST:event_jLabel91MouseClicked

    private void jLabel93MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel93MouseClicked
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
        }
    }//GEN-LAST:event_jLabel93MouseClicked

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

    private void jLabel96MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel96MouseClicked
        DefaultTableModel mod = (DefaultTableModel) jm_table1.getModel();
        mod.setRowCount(0);
        String dg = mj1.getDate().toString();
        w1.setText(dg);
        DateFormat dateFormat2 = new SimpleDateFormat("y-MM-dd");
        mj1.setDateFormatString("y-MM-dd");
        String dte = dateFormat2.format(mj1.getDate());
        w1.setText(dte);
DB_USER = uname.getText();
        DB_PAS = upass.getText();
        String dg1 = mj2.getDate().toString();
        w2.setText(dg1);
        DateFormat dateFormat = new SimpleDateFormat("y-MM-dd");
        mj2.setDateFormatString("y-MM-dd");
        String dte1 = dateFormat.format(mj2.getDate());
        w2.setText(dte1);
        java.util.Date date = new java.util.Date();
        long t = date.getTime();

        String op = "buying";
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
                                String myDriver = "org.postgresql.Driver";
                                String myUrl = "jdbc:postgresql:" + configs.getText();
                                Class.forName(DB_DRIVER);
                                java.sql.Connection conn = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                String query1 = "SELECT base_currency,quote_currency,region,branch,exchange_rate,amount_traded_in,amount_traded_out,date,cashier FROM transactions_history where date between'" + w1.getText() + "' and '" + w2.getText() + "' and region='" + fa5.getSelectedItem().toString() + "' and branch='" + fa6.getSelectedItem().toString() + "' and base_currency='" + a + "' and operation='" + op + "' ";

                                java.sql.Statement st = conn.createStatement();

                                // execute the query, and get a java resultset
                                ResultSet rsq = st.executeQuery(query1);

                                // iterate through the java resultset
                                while (rsq.next()) {
                                    bscd.setText(a);
                                    String s1 = rsq.getString("base_currency");
                                    String s2 = rsq.getString("quote_currency");
                                    String s3 = rsq.getString("region");
                                    String s4 = rsq.getString("branch");
                                    double s5 = rsq.getDouble("exchange_rate");
                                    double s6 = rsq.getDouble("amount_traded_out");
                                    double s7 = rsq.getDouble("amount_traded_in");
                                    java.sql.Date sqlDate = rsq.getDate("date");
                                    String s8 = rsq.getString("cashier");

                                    mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                    int sum = 0;
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
                                String myDriver = "org.postgresql.Driver";
                                String myUrl = "jdbc:postgresql:" + configs.getText();
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
                                    String s3 = rsq.getString("region");
                                    String s4 = rsq.getString("branch");
                                    double s5 = rsq.getDouble("exchange_rate");
                                    double s6 = rsq.getDouble("amount_traded_out");
                                    double s7 = rsq.getDouble("amount_traded_in");
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
                                String myDriver = "org.postgresql.Driver";
                                String myUrl = "jdbc:postgresql:" + configs.getText();
                                Class.forName(DB_DRIVER);
                                java.sql.Connection conn = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                String query1 = "SELECT base_currency,quote_currency,region,branch,exchange_rate,amount_traded_in,amount_traded_out,date,cashier FROM transactions_history where date between'" + w1.getText() + "' and '" + w2.getText() + "' and operation='" + op + "' and region='" + fa5.getSelectedItem().toString() + "'  and base_currency='" + a + "' and quote_currency!='" + a + "' ";

                                java.sql.Statement st = conn.createStatement();

                                // execute the query, and get a java resultset
                                ResultSet rsq = st.executeQuery(query1);
                                boolean swm = rsq.isBeforeFirst();
                                // iterate through the java resultset
                                while (rsq.next()) {
                                    bscd.setText(a);
                                    String s1 = rsq.getString("base_currency");
                                    String s2 = rsq.getString("quote_currency");
                                    String s3 = rsq.getString("region");
                                    String s4 = rsq.getString("branch");
                                    double s5 = rsq.getDouble("exchange_rate");
                                    double s6 = rsq.getDouble("amount_traded_out");
                                    double s7 = rsq.getDouble("amount_traded_in");
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
                                String myDriver = "org.postgresql.Driver";
                                String myUrl = "jdbc:postgresql:" + configs.getText();
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
                                    String s3 = rsq.getString("region");
                                    String s4 = rsq.getString("branch");
                                    double s5 = rsq.getDouble("exchange_rate");
                                    double s6 = rsq.getDouble("amount_traded_out");
                                    double s7 = rsq.getDouble("amount_traded_in");
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
        }
    }//GEN-LAST:event_jLabel96MouseClicked

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

    private void jLabel128MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel128MouseClicked

    }//GEN-LAST:event_jLabel128MouseClicked

    private void jLabel130MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel130MouseClicked
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
                    Logger.getLogger(Finance_Dep.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Finance_Dep.class.getName()).log(Level.SEVERE, null, ex);
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
    }//GEN-LAST:event_jLabel130MouseClicked

    private void jLabel131MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel131MouseClicked
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
    }//GEN-LAST:event_jLabel131MouseClicked

    private void jLabel132MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel132MouseClicked

    }//GEN-LAST:event_jLabel132MouseClicked

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

    private void jLabel135MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel135MouseClicked
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
        String op = "buying";

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
                                String myDriver = "org.postgresql.Driver";
                                String myUrl = "jdbc:postgresql:" + configs.getText();
                                Class.forName(DB_DRIVER);
                                java.sql.Connection conn = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                String query1 = "SELECT base_currency,quote_currency,region,branch,exchange_rate,amount_traded_in,amount_traded_out,date,cashier FROM transactions_history where date='" + fw1.getText() + "' and operation='" + op + "'  and region='" + fb5.getSelectedItem().toString() + "' and branch='" + fb6.getSelectedItem().toString() + "' and base_currency='" + a + "'";

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
                                String myDriver = "org.postgresql.Driver";
                                String myUrl = "jdbc:postgresql:" + configs.getText();
                                Class.forName(DB_DRIVER);
                                java.sql.Connection conn = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                String query1 = "SELECT base_currency,quote_currency,region,branch,exchange_rate,amount_traded_in,amount_traded_out,date,cashier FROM transactions_history where date='" + fw1.getText() + "' and operation='" + op + "'  and region='" + fb5.getSelectedItem().toString() + "' and quote_currency!='" + a + "' and base_currency!='" + a + "'";

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
                } catch (HeadlessException | ClassNotFoundException | SQLException e) {
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
                                String myDriver = "org.postgresql.Driver";
                                String myUrl = "jdbc:postgresql:" + configs.getText();
                                Class.forName(DB_DRIVER);
                                java.sql.Connection conn = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                String query1 = "SELECT base_currency,quote_currency,region,branch,exchange_rate,amount_traded_in,amount_traded_out,date,cashier FROM transactions_history where date='" + fw1.getText() + "' and operation='" + op + "'  and region='" + fb5.getSelectedItem().toString() + "' and quote_currency!='" + a + "' and base_currency='" + a + "'";

                                java.sql.Statement st = conn.createStatement();

                                // execute the query, and get a java resultset
                                ResultSet rsq = st.executeQuery(query1);
                                boolean swm = rsq.isBeforeFirst();
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
                                String myDriver = "org.postgresql.Driver";
                                String myUrl = "jdbc:postgresql:" + configs.getText();
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
                                    String s3 = rsq.getString("region");
                                    String s4 = rsq.getString("branch");
                                    double s5 = rsq.getDouble("exchange_rate");
                                    double s6 = rsq.getDouble("amount_traded_out");
                                    double s7 = rsq.getDouble("amount_traded_in");
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
                    JOptionPane.showMessageDialog(null, e.getMessage(), " CURRENCIES ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_jLabel135MouseClicked

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
        DefaultComboBoxModel dm = new DefaultComboBoxModel();
        fb6.setModel(dm);
DB_USER = uname.getText();
        DB_PAS = upass.getText();
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

    private void jLabel138MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel138MouseClicked
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
                    Logger.getLogger(Finance_Dep.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Finance_Dep.class.getName()).log(Level.SEVERE, null, ex);
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
    }//GEN-LAST:event_jLabel138MouseClicked

    private void jLabel139MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel139MouseClicked
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
    }//GEN-LAST:event_jLabel139MouseClicked

    private void jLabel140MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel140MouseClicked

    }//GEN-LAST:event_jLabel140MouseClicked

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

    private void jLabel143MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel143MouseClicked
        DefaultTableModel mod = (DefaultTableModel) zm_table.getModel();
        mod.setRowCount(0);
        java.util.Date date = new java.util.Date();
        long t = date.getTime();
DB_USER = uname.getText();
        DB_PAS = upass.getText();
        String op = "buying";

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
                                String myDriver = "org.postgresql.Driver";
                                String myUrl = "jdbc:postgresql:" + configs.getText();
                                Class.forName(DB_DRIVER);
                                java.sql.Connection conn = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                String query1 = "SELECT base_currency,quote_currency,region,branch,exchange_rate,amount_traded_in,amount_traded_out,date,cashier FROM transactions_history where region='" + fc5.getSelectedItem().toString() + "' and operation='" + op + "' and branch='" + fc6.getSelectedItem().toString() + "' and base_currency='" + a + "'";

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

                            } catch (ClassNotFoundException | SQLException er) {
                                JOptionPane.showMessageDialog(null, er.getMessage());

                            }

                        }
                    }
                } catch (HeadlessException | ClassNotFoundException | NumberFormatException | SQLException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), " CURRENCIES ERROR", JOptionPane.ERROR_MESSAGE);
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
                                String myDriver = "org.postgresql.Driver";
                                String myUrl = "jdbc:postgresql:" + configs.getText();
                                Class.forName(DB_DRIVER);
                                java.sql.Connection conn = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                String query1 = "SELECT base_currency,quote_currency,region,branch,exchange_rate,amount_traded_in,amount_traded_out,date,cashier FROM transactions_history where region='" + fc5.getSelectedItem().toString() + "' and operation='" + op + "' and quote_currency!='" + a + "' and base_currency!='" + a + "' ";

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

                            } catch (ClassNotFoundException | SQLException er) {
                                JOptionPane.showMessageDialog(null, er.getMessage());
                            }

                        }
                    }
                } catch (HeadlessException | ClassNotFoundException | NumberFormatException | SQLException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), " CURRENCIES ERROR", JOptionPane.ERROR_MESSAGE);
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
                                String myDriver = "org.postgresql.Driver";
                                String myUrl = "jdbc:postgresql:" + configs.getText();
                                Class.forName(DB_DRIVER);
                                java.sql.Connection conn = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                String query1 = "SELECT base_currency,quote_currency,region,branch,exchange_rate,amount_traded_in,amount_traded_out,date,cashier FROM transactions_history where region='" + fc5.getSelectedItem().toString() + "' and operation='" + op + "'  and quote_currency!='" + a + "' and base_currency='" + a + "'";

                                java.sql.Statement st = conn.createStatement();

                                // execute the query, and get a java resultset
                                ResultSet rsq = st.executeQuery(query1);
                                boolean swm = rsq.isBeforeFirst();
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

                            } catch (ClassNotFoundException | SQLException er) {
                                JOptionPane.showMessageDialog(null, er.getMessage());
                            }

                        }
                    }
                } catch (HeadlessException | ClassNotFoundException | NumberFormatException | SQLException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), " CURRENCIES ERROR", JOptionPane.ERROR_MESSAGE);
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
                                String myDriver = "org.postgresql.Driver";
                                String myUrl = "jdbc:postgresql:" + configs.getText();
                                Class.forName(DB_DRIVER);
                                java.sql.Connection conn = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                String query1 = "SELECT base_currency,quote_currency,region,branch,exchange_rate,amount_traded_in,amount_traded_out,date,cashier FROM transactions_history where region='" + fc5.getSelectedItem().toString() + "' and operation='" + op + "' and quote_currency!='" + a + "' and base_currency!='" + a + "' ";

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

                            } catch (ClassNotFoundException | SQLException er) {
                                JOptionPane.showMessageDialog(null, er.getMessage());
                            }

                        }
                    }
                } catch (HeadlessException | ClassNotFoundException | NumberFormatException | SQLException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), " CURRENCIES ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_jLabel143MouseClicked

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
                    JOptionPane.showMessageDialog(null, e.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_fc4ActionPerformed

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

    private void jLabel146MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel146MouseClicked
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
                    Logger.getLogger(Finance_Dep.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Finance_Dep.class.getName()).log(Level.SEVERE, null, ex);
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
    }//GEN-LAST:event_jLabel146MouseClicked

    private void jLabel147MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel147MouseClicked
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
    }//GEN-LAST:event_jLabel147MouseClicked

    private void jLabel148MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel148MouseClicked

    }//GEN-LAST:event_jLabel148MouseClicked

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

    private void jLabel151MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel151MouseClicked
        DefaultTableModel mod = (DefaultTableModel) fd_table.getModel();
        mod.setRowCount(0);
        java.util.Date date = new java.util.Date();
        long t = date.getTime();
DB_USER = uname.getText();
        DB_PAS = upass.getText();
        String op = "buying";

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
                                String myDriver = "org.postgresql.Driver";
                                String myUrl = "jdbc:postgresql:" + configs.getText();
                                Class.forName(DB_DRIVER);
                                java.sql.Connection conn = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                String query1 = "SELECT base_currency,quote_currency,region,branch,exchange_rate,amount_traded_in,amount_traded_out,date,cashier FROM transactions_history where region='" + fd5.getSelectedItem().toString() + "' and branch='" + fd6.getSelectedItem().toString() + "' and operation='" + op + "' and base_currency='" + a + "'";

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
                                    java.sql.Date sqlDatep = rsq.getDate("date");
                                    String s8 = rsq.getString("cashier");
                                    String ht = sqlDatep.toString();
                                    String dt = ht.substring(0, 4);
                                    int af = Integer.parseInt(fd7.getValue().toString());
                                    int ag = Integer.parseInt(dt);
                                    if (af == ag) {
                                        mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                    }                         //int sum = 0;
                                }

                            } catch (ClassNotFoundException | SQLException er) {
                                JOptionPane.showMessageDialog(null, er.getMessage());

                            }

                        }
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), " CURRENCIES ERROR", JOptionPane.ERROR_MESSAGE);
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
                                String myDriver = "org.postgresql.Driver";
                                String myUrl = "jdbc:postgresql:" + configs.getText();
                                Class.forName(DB_DRIVER);
                                java.sql.Connection conn = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                String query1 = "SELECT base_currency,quote_currency,region,branch,exchange_rate,amount_traded_in,amount_traded_out,date,cashier FROM transactions_history where region='" + fd5.getSelectedItem().toString() + "' and quote_currency!='" + a + "' and base_currency!='" + a + "' and operation='" + op + "' ";

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
                                    java.sql.Date sqlDatep = rsq.getDate("date");
                                    String s8 = rsq.getString("cashier");
                                    String ht = sqlDatep.toString();
                                    String dt = ht.substring(0, 4);
                                    int af = Integer.parseInt(fd7.getValue().toString());
                                    int ag = Integer.parseInt(dt);
                                    if (af == ag) {
                                        mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                    }
                                }

                            } catch (ClassNotFoundException | SQLException er) {
                                JOptionPane.showMessageDialog(null, er.getMessage());
                            }

                        }
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), " CURRENCIES ERROR", JOptionPane.ERROR_MESSAGE);
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
                                String myDriver = "org.postgresql.Driver";
                                String myUrl = "jdbc:postgresql:" + configs.getText();
                                Class.forName(DB_DRIVER);
                                java.sql.Connection conn = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                String query1 = "SELECT base_currency,quote_currency,region,branch,exchange_rate,amount_traded_in,amount_traded_out,date,cashier FROM transactions_history where region='" + fd5.getSelectedItem().toString() + "' and operation='" + op + "' and base_currency='" + a + "' and quote_currency!='" + a + "'";

                                java.sql.Statement st = conn.createStatement();

                                // execute the query, and get a java resultset
                                ResultSet rsq = st.executeQuery(query1);
                                boolean swm = rsq.isBeforeFirst();
                                // iterate through the java resultset
                                while (rsq.next()) {
                                    String s1 = rsq.getString("base_currency");
                                    String s2 = rsq.getString("quote_currency");
                                    String s3 = rsq.getString("region");
                                    String s4 = rsq.getString("branch");
                                    double s5 = rsq.getDouble("exchange_rate");
                                    double s6 = rsq.getDouble("amount_traded_out");
                                    double s7 = rsq.getDouble("amount_traded_in");
                                    java.sql.Date sqlDatep = rsq.getDate("date");
                                    String s8 = rsq.getString("cashier");
                                    String ht = sqlDatep.toString();
                                    String dt = ht.substring(0, 4);
                                    int af = Integer.parseInt(fd7.getValue().toString());
                                    int ag = Integer.parseInt(dt);
                                    if (af == ag) {
                                        mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                    }
                                }

                            } catch (ClassNotFoundException | SQLException er) {
                                JOptionPane.showMessageDialog(null, er.getMessage());
                            }

                        }
                    }
                } catch (HeadlessException | ClassNotFoundException | NumberFormatException | SQLException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), " CURRENCIES ERROR", JOptionPane.ERROR_MESSAGE);
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
                                String myDriver = "org.postgresql.Driver";
                                String myUrl = "jdbc:postgresql:" + configs.getText();
                                Class.forName(DB_DRIVER);
                                java.sql.Connection conn = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                String query1 = "SELECT base_currency,quote_currency,region,branch,exchange_rate,amount_traded_in,amount_traded_out,date,cashier FROM transactions_history where region='" + fd5.getSelectedItem().toString() + "' and operation='" + op + "' and quote_currency!='" + a + "' and base_currency!='" + a + "' ";

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
                                    java.sql.Date sqlDatep = rsq.getDate("date");
                                    String s8 = rsq.getString("cashier");
                                    String ht = sqlDatep.toString();
                                    String dt = ht.substring(0, 4);
                                    int af = Integer.parseInt(fd7.getValue().toString());
                                    int ag = Integer.parseInt(dt);
                                    if (af == ag) {
                                        mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                    }

                                }

                            } catch (ClassNotFoundException | SQLException er) {
                                JOptionPane.showMessageDialog(null, er.getMessage());
                            }

                        }
                    }
                } catch (HeadlessException | ClassNotFoundException | NumberFormatException | SQLException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), " CURRENCIES ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_jLabel151MouseClicked

    private void fd3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fd3ActionPerformed
        //        DefaultComboBoxModel dm = new DefaultComboBoxModel();
        //        fd5.setModel(dm);
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
        //                    fd5.addItem(a);
        //
        //                }
        //            }
        //        } catch (ClassNotFoundException | SQLException e) {
        //            JOptionPane.showMessageDialog(null, e.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
        //        }
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
            JOptionPane.showMessageDialog(null, e.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_fd5ActionPerformed

    private void jLabel92MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel92MouseClicked
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
                } catch (FileNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                    Logger.getLogger(Finance_Dep.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Finance_Dep.class.getName()).log(Level.SEVERE, null, ex);
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
                        Logger.getLogger(Finance_Dep.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
        }
    }//GEN-LAST:event_jLabel92MouseClicked

    private void jLabel134MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel134MouseClicked
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

        String op = "selling";
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
                                String query1 = "SELECT base_currency,quote_currency,region,branch,exchange_rate,amount_traded_in,amount_traded_out,date,cashier FROM transactions_history where date between'" + kb1.getText() + "' and '" + kb2.getText() + "' and region='" + py5.getSelectedItem().toString() + "' and branch='" + py6.getSelectedItem().toString() + "' and base_currency!='" + a + "' and quote_currency='" + a + "' and operation='" + op + "' ";

                                java.sql.Statement st = conn.createStatement();

                                // execute the query, and get a java resultset
                                ResultSet rsq = st.executeQuery(query1);

                                // iterate through the java resultset
                                while (rsq.next()) {
                                    bfc.setText(a);
                                    String s1 = rsq.getString("base_currency");
                                    String s2 = rsq.getString("quote_currency");
                                    String s3 = rsq.getString("region");
                                    String s4 = rsq.getString("branch");
                                    double s5 = rsq.getDouble("exchange_rate");
                                    double s6 = rsq.getDouble("amount_traded_out");
                                    double s7 = rsq.getDouble("amount_traded_in");
                                    java.sql.Date sqlDate = rsq.getDate("date");
                                    String s8 = rsq.getString("cashier");

                                    mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                    int sum = 0;
                                }
                                boolean mb = rsq.isAfterLast();
                                if (mb == false) {
                                    JOptionPane.showMessageDialog(null, "There are no purchases recorded between " + py7.getDate() + " " + "&" + " " + py8.getDate() + " " + "\nfor " + py6.getSelectedItem().toString() + " " + "purchasing local curency using foreign currency", "Transaction record not found", JOptionPane.WARNING_MESSAGE);
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
                                String myDriver = "org.postgresql.Driver";
                                String myUrl = "jdbc:postgresql:" + configs.getText();
                                Class.forName(DB_DRIVER);
                                java.sql.Connection conn = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                String query1 = "SELECT base_currency,quote_currency,region,branch,exchange_rate,amount_traded_in,amount_traded_out,date,cashier FROM transactions_history where date between'" + kb1.getText() + "' and '" + kb2.getText() + "' and region='" + py5.getSelectedItem().toString() + "' and quote_currency=!'" + a + "' and base_currency!='" + a + "' and operation='" + op + "' ";

                                java.sql.Statement st = conn.createStatement();

                                // execute the query, and get a java resultset
                                ResultSet rsq = st.executeQuery(query1);

                                // iterate through the java resultset
                                while (rsq.next()) {
                                    bfc.setText(a);
                                    String s1 = rsq.getString("base_currency");
                                    String s2 = rsq.getString("quote_currency");
                                    String s3 = rsq.getString("region");
                                    String s4 = rsq.getString("branch");
                                    double s5 = rsq.getDouble("exchange_rate");
                                    double s6 = rsq.getDouble("amount_traded_out");
                                    double s7 = rsq.getDouble("amount_traded_in");
                                    java.sql.Date sqlDate = rsq.getDate("date");
                                    String s8 = rsq.getString("cashier");

                                    mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                    int sum = 0;
                                }
                                boolean mb = rsq.isAfterLast();
                                if (mb == false) {
                                    JOptionPane.showMessageDialog(null, "There are no purchases recorded between " + py7.getDate() + " " + "&" + " " + py8.getDate() + " " + "\nfor " + py6.getSelectedItem().toString() + " " + "purchasing foreign curencies using other foreign currencies", "Transaction record not found", JOptionPane.WARNING_MESSAGE);
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
                                String myUrl = "jdbc:postgresql:" + configs.getText();
                                Class.forName(DB_DRIVER);
                                java.sql.Connection conn = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                String query1 = "SELECT base_currency,quote_currency,region,branch,exchange_rate,amount_traded_in,amount_traded_out,date,cashier FROM transactions_history where date between'" + kb1.getText() + "'  and '" + kb2.getText() + "' and region='" + py5.getSelectedItem().toString() + "'  and quote_currency='" + a + "' and base_currency!='" + a + "' and operation='" + op + "' ";

                                java.sql.Statement st = conn.createStatement();

                                // execute the query, and get a java resultset
                                ResultSet rsq = st.executeQuery(query1);
                                boolean swm = rsq.isBeforeFirst();
                                // iterate through the java resultset
                                while (rsq.next()) {
                                    bfc.setText(a);
                                    String s1 = rsq.getString("base_currency");
                                    String s2 = rsq.getString("quote_currency");
                                    String s3 = rsq.getString("region");
                                    String s4 = rsq.getString("branch");
                                    double s5 = rsq.getDouble("exchange_rate");
                                    double s6 = rsq.getDouble("amount_traded_out");
                                    double s7 = rsq.getDouble("amount_traded_in");
                                    java.sql.Date sqlDate = rsq.getDate("date");
                                    String s8 = rsq.getString("cashier");

                                    mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                    int sum = 0;
                                }
                                boolean mb = rsq.isAfterLast();
                                if (mb == false) {
                                    JOptionPane.showMessageDialog(null, "There are no purchases recorded between " + py7.getDate() + " " + "&" + " " + py8.getDate() + " " + "\nwithin " + py6.getSelectedItem().toString() + " " + "purchasing local currency using other foreign currencies", "Transaction record not found", JOptionPane.WARNING_MESSAGE);
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
                                String myDriver = "org.postgresql.Driver";
                                String myUrl = "jdbc:postgresql:" + configs.getText();
                                Class.forName(DB_DRIVER);
                                java.sql.Connection conn = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                String query1 = "SELECT base_currency,quote_currency,region,branch,exchange_rate,amount_traded_in,amount_traded_out,date,cashier FROM transactions_history where date between'" + kb1.getText() + "'  and '" + kb2.getText() + "' and region='" + py5.getSelectedItem().toString() + "' and quote_currency!='" + a + "' and base_currency!='" + a + "' and operation='" + op + "' ";

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
                                    String s8 = rsq.getString("cashier");

                                    mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                    int sum = 0;
                                }
                                boolean mb = rsq.isAfterLast();
                                if (mb == false) {
                                    JOptionPane.showMessageDialog(null, "There are no purchases recorded between " + py7.getDate() + " " + "&" + " " + py8.getDate() + " " + "\nwithin " + py6.getSelectedItem().toString() + " " + "purchasing foreign currencies using other foreign currencies", "Transaction record not found", JOptionPane.WARNING_MESSAGE);
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
                    Logger.getLogger(Finance_Dep.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Finance_Dep.class.getName()).log(Level.SEVERE, null, ex);
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
        String op = "selling";

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
                                String myDriver = "org.postgresql.Driver";
                                String myUrl = "jdbc:postgresql:" + configs.getText();
                                Class.forName(DB_DRIVER);
                                java.sql.Connection conn = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                String query1 = "SELECT base_currency,quote_currency,region,branch,exchange_rate,amount_traded_in,amount_traded_out,date,cashier FROM transactions_history where date='" + ts1.getText() + "'   and region='" + wy5.getSelectedItem().toString() + "' and branch='" + wy6.getSelectedItem().toString() + "' and base_currency!='" + a + "' and quote_currency='" + a + "' and operation='" + op + "'";

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
                                    String s8 = rsq.getString("cashier");

                                    mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                    int sum = 0;
                                }
                                boolean mb = rsq.isAfterLast();
                                if (mb == false) {
                                    JOptionPane.showMessageDialog(null, "There are no purcahses recorded on " + wy7.getDate() + "\nfor " + wy6.getSelectedItem().toString() + " " + "purchasing local curency using foreign currency", "Transaction record not found", JOptionPane.WARNING_MESSAGE);
                                }

                            } catch (ClassNotFoundException | SQLException er) {
                                JOptionPane.showMessageDialog(null, er.getMessage());

                            }

                        }
                    }
                } catch (HeadlessException | ClassNotFoundException | SQLException e) {
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
                                String myDriver = "org.postgresql.Driver";
                                String myUrl = "jdbc:postgresql:" + configs.getText();
                                Class.forName(DB_DRIVER);
                                java.sql.Connection conn = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                String query1 = "SELECT base_currency,quote_currency,region,branch,exchange_rate,amount_traded_in,amount_traded_out,date,cashier FROM transactions_history where date='" + ts1.getText() + "'  and region='" + wy5.getSelectedItem().toString() + "' and base_currency!='" + a + "' and quote_currency!='" + a + "' and operation='" + op + "' ";

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
                                    String s8 = rsq.getString("cashier");

                                    mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                    int sum = 0;
                                }
                                boolean mb = rsq.isAfterLast();
                                if (mb == false) {
                                    JOptionPane.showMessageDialog(null, "There are no purchases recorded on " + wy7.getDate() + "\nfor " + wy6.getSelectedItem().toString() + " " + "purchasing foreign curencies using other foreign currency", "Transaction record not found", JOptionPane.WARNING_MESSAGE);
                                }

                            } catch (ClassNotFoundException | SQLException er) {
                                JOptionPane.showMessageDialog(null, er.getMessage());

                            }

                        }
                    }
                } catch (HeadlessException | ClassNotFoundException | SQLException e) {
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
                                String myDriver = "org.postgresql.Driver";
                                String myUrl = "jdbc:postgresql:" + configs.getText();
                                Class.forName(DB_DRIVER);
                                java.sql.Connection conn = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                String query1 = "SELECT base_currency,quote_currency,region,branch,exchange_rate,amount_traded_in,amount_traded_out,date,cashier FROM transactions_history where date='" + ts1.getText() + "'  and region='" + wy5.getSelectedItem().toString() + "'  and quote_currency='" + a + "' and base_currency!='" + a + "' and operation='" + op + "'";

                                java.sql.Statement st = conn.createStatement();

                                // execute the query, and get a java resultset
                                ResultSet rsq = st.executeQuery(query1);
                                boolean swm = rsq.isBeforeFirst();
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
                                    String s8 = rsq.getString("cashier");

                                    mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                    int sum = 0;
                                }
                                boolean mb = rsq.isAfterLast();
                                if (mb == false) {
                                    JOptionPane.showMessageDialog(null, "There are no purchases recorded on " + wy7.getDate() + "\nwithin " + wy5.getSelectedItem().toString() + " " + "purchasing local currency using other foreign currency", "Transaction record not found", JOptionPane.WARNING_MESSAGE);
                                }

                            } catch (ClassNotFoundException | SQLException er) {
                                JOptionPane.showMessageDialog(null, er.getMessage());

                            }

                        }
                    }
                } catch (HeadlessException | ClassNotFoundException | SQLException e) {
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
                                String myDriver = "org.postgresql.Driver";
                                String myUrl = "jdbc:postgresql:" + configs.getText();
                                Class.forName(DB_DRIVER);
                                java.sql.Connection conn = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                String query1 = "SELECT base_currency,quote_currency,region,branch,exchange_rate,amount_traded_in,amount_traded_out,date,cashier FROM transactions_history where date='" + ts1.getText() + "'  and region='" + wy5.getSelectedItem().toString() + "' and quote_currency!='" + a + "' and base_currency!='" + a + "' and operation='" + op + "' ";

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
                                    String s8 = rsq.getString("cashier");

                                    mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                    int sum = 0;
                                }
                                boolean mb = rsq.isAfterLast();
                                if (mb == false) {
                                    JOptionPane.showMessageDialog(null, "There are no purchases recorded on " + wy7.getDate() + "\nwithin " + wy5.getSelectedItem().toString() + " " + "purchasing foreign curencies using other foreign currency", "Transaction record not found", JOptionPane.WARNING_MESSAGE);
                                }

                            } catch (ClassNotFoundException | SQLException er) {
                                JOptionPane.showMessageDialog(null, er.getMessage());

                            }

                        }
                    }
                } catch (HeadlessException | ClassNotFoundException | SQLException e) {
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
                    Logger.getLogger(Finance_Dep.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Finance_Dep.class.getName()).log(Level.SEVERE, null, ex);
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
    }//GEN-LAST:event_jLabel167MouseClicked

    private void jLabel168MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel168MouseClicked
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
        String op = "selling";

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
                                String query1 = "SELECT base_currency,quote_currency,region,branch,exchange_rate,amount_traded_in,amount_traded_out,date,cashier FROM transactions_history where region='" + zh5.getSelectedItem().toString() + "' and operation='" + op + "' and base_currency!='" + a + "' and  quote_currency='" + a + "' and branch='" + zh6.getSelectedItem().toString() + "' ";

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
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 5).toString());
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
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 5).toString());
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
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 5).toString());
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
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 5).toString());
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
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 5).toString());
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
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 5).toString());
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
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 5).toString());
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
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 5).toString());
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
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 5).toString());
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
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 5).toString());
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
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 5).toString());
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
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 5).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }

                                    //int sum = 0;
                                }

                            } catch (ClassNotFoundException | SQLException er) {
                                JOptionPane.showMessageDialog(null, er.getMessage());

                            }

                        }
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), " Something went wrong, please contact your database administrator or your vendor ", JOptionPane.ERROR_MESSAGE);
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
                                    String s1 = rsq.getString("base_currency");
                                    String s2 = rsq.getString("quote_currency");
                                    String s3 = rsq.getString("region");
                                    String s4 = rsq.getString("branch");
                                    double s5 = rsq.getDouble("exchange_rate");
                                    double s6 = rsq.getDouble("amount_traded_out");
                                    double s7 = rsq.getDouble("amount_traded_in");
                                    java.sql.Date sqlDate = rsq.getDate("date");
                                    String s8 = rsq.getString("cashier");
                                    double sum = 0;
                                    if (zh7.getValue().equals("September")) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "09";

                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 5).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }

                                    if (zh7.getValue().equals("January")) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "01";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 5).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("February")) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "02";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 5).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("March")) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "03";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 5).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("April")) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "04";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 5).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("May")) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "05";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 5).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("June")) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "06";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 5).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("July")) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "07";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 5).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("August")) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "08";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 5).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("October")) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "10";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 5).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("November")) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "11";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 5).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("December")) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "12";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 5).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                }

                            } catch (ClassNotFoundException | SQLException er) {
                                JOptionPane.showMessageDialog(null, er.getMessage());
                            }

                        }
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), " Something went wrong, please contact your database administrator or the vendor ", JOptionPane.ERROR_MESSAGE);
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
                                String query1 = "SELECT base_currency,quote_currency,region,branch,exchange_rate,amount_traded_in,amount_traded_out,date,cashier FROM transactions_history where region='" + zh5.getSelectedItem().toString() + "' and operation='" + op + "' and  quote_currency='" + a + "' and base_currency!='" + a + "'";

                                java.sql.Statement st = conn.createStatement();

                                // execute the query, and get a java resultset
                                ResultSet rsq = st.executeQuery(query1);
                                boolean swm = rsq.isBeforeFirst();
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
                                    String s8 = rsq.getString("cashier");

                                    double sum = 0;
                                    if (zh7.getValue().equals("September")) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "09";

                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 5).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }

                                    if (zh7.getValue().equals("January")) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "01";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 5).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("February")) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "02";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 5).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("March")) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "03";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 5).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("April")) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "04";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 5).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("May")) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "05";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 5).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("June")) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "06";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 5).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("July")) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "07";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 5).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("August")) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "08";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 5).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("October")) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "10";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 5).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("November")) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "11";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 5).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("December")) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "12";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 5).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                }

                            } catch (ClassNotFoundException | SQLException er) {
                                JOptionPane.showMessageDialog(null, er.getMessage());
                            }

                        }
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), " Something went wrong, please contact your database administraor or the vendor ", JOptionPane.ERROR_MESSAGE);
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
                                    String s1 = rsq.getString("base_currency");
                                    String s2 = rsq.getString("quote_currency");
                                    String s3 = rsq.getString("region");
                                    String s4 = rsq.getString("branch");
                                    double s5 = rsq.getDouble("exchange_rate");
                                    double s6 = rsq.getDouble("amount_traded_out");
                                    double s7 = rsq.getDouble("amount_traded_in");
                                    java.sql.Date sqlDate = rsq.getDate("date");
                                    String s8 = rsq.getString("cashier");

                                    double sum = 0;
                                    if (zh7.getValue().equals("September")) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "09";

                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 5).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }

                                    if (zh7.getValue().equals("January")) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "01";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 5).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("February")) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "02";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 5).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("March")) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "03";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 5).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("April")) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "04";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 5).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("May")) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "05";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 5).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("June")) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "06";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 5).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("July")) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "07";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 5).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("August")) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "08";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 5).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("October")) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "10";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 5).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("November")) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "11";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 5).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("December")) {
                                        String ht = sqlDate.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "12";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 5).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                }

                            } catch (ClassNotFoundException | SQLException er) {
                                JOptionPane.showMessageDialog(null, er.getMessage());
                            }

                        }
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), " CURRENCIES ERROR", JOptionPane.ERROR_MESSAGE);
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

    private void jLabel176MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel176MouseClicked
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
                    Logger.getLogger(Finance_Dep.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Finance_Dep.class.getName()).log(Level.SEVERE, null, ex);
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
    }//GEN-LAST:event_jLabel176MouseClicked

    private void jLabel176MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel176MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel176MouseEntered

    private void jLabel177MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel177MouseClicked
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
        DefaultTableModel mod = (DefaultTableModel) px_table.getModel();
        mod.setRowCount(0);
        java.util.Date date = new java.util.Date();
        long t = date.getTime();
DB_USER = uname.getText();
        DB_PAS = upass.getText();
        String op = "selling";

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
                                String myDriver = "org.postgresql.Driver";
                                String myUrl = "jdbc:postgresql:" + configs.getText();
                                Class.forName(DB_DRIVER);
                                java.sql.Connection conn = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                String query1 = "SELECT base_currency,quote_currency,region,branch,exchange_rate,amount_traded_in,amount_traded_out,date,cashier FROM transactions_history where region='" + px5.getSelectedItem().toString() + "' and branch='" + px6.getSelectedItem().toString() + "' and operation='" + op + "' and base_currency!='" + a + "' and quote_currency='" + a + "'";

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
                                    String s8 = rsq.getString("cashier");
                                    String ht = sqlDate.toString();
                                    String dt = ht.substring(0, 4);
                                    int af = Integer.parseInt(px7.getValue().toString());
                                    int ag = Integer.parseInt(dt);
                                    if (af == ag) {
                                        mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDate});
                                    }                         //int sum = 0;
                                }

                            } catch (ClassNotFoundException | SQLException er) {
                                JOptionPane.showMessageDialog(null, er.getMessage());

                            }

                        }
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), " CURRENCIES ERROR", JOptionPane.ERROR_MESSAGE);
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
                                String myDriver = "org.postgresql.Driver";
                                String myUrl = "jdbc:postgresql:" + configs.getText();
                                Class.forName(DB_DRIVER);
                                java.sql.Connection conn = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                String query1 = "SELECT base_currency,quote_currency,region,branch,exchange_rate,amount_traded_in,amount_traded_out,date,cashier FROM transactions_history where region='" + px5.getSelectedItem().toString() + "' and branch='" + px6.getSelectedItem().toString() + "' and base_currency!='" + a + "' and quote_currency!='" + a + "' and operation='" + op + "' ";

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
                                    String s8 = rsq.getString("cashier");
                                    String ht = sqlDate.toString();
                                    String dt = ht.substring(0, 4);
                                    int af = Integer.parseInt(px7.getValue().toString());
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
                    JOptionPane.showMessageDialog(null, e.getMessage(), " Something went wrong, please contact your database administartor or the vendor ", JOptionPane.ERROR_MESSAGE);
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
                                String myDriver = "org.postgresql.Driver";
                                String myUrl = "jdbc:postgresql:" + configs.getText();
                                Class.forName(DB_DRIVER);
                                java.sql.Connection conn = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                String query1 = "SELECT base_currency,quote_currency,region,branch,exchange_rate,amount_traded_in,amount_traded_out,date,cashier FROM transactions_history where region='" + px5.getSelectedItem().toString() + "' and operation='" + op + "'  and quote_currency='" + a + "' and base_currency!='" + a + "'";

                                java.sql.Statement st = conn.createStatement();

                                // execute the query, and get a java resultset
                                ResultSet rsq = st.executeQuery(query1);
                                boolean swm = rsq.isBeforeFirst();
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
                                    String s8 = rsq.getString("cashier");
                                    String ht = sqlDate.toString();
                                    String dt = ht.substring(0, 4);
                                    int af = Integer.parseInt(px7.getValue().toString());
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
                    JOptionPane.showMessageDialog(null, e.getMessage(), " Something went wrong, please contact your database administrator or the vendor ", JOptionPane.ERROR_MESSAGE);
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
                                String myDriver = "org.postgresql.Driver";
                                String myUrl = "jdbc:postgresql:" + configs.getText();
                                Class.forName(DB_DRIVER);
                                java.sql.Connection conn = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                String query1 = "SELECT base_currency,quote_currency,region,branch,exchange_rate,amount_traded_in,amount_traded_out,date,cashier FROM transactions_history where region='" + px5.getSelectedItem().toString() + "' and operation='" + op + "' and quote_currency!='" + a + "' and base_currency!='" + a + "' ";

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
                                    String s8 = rsq.getString("cashier");
                                    String ht = sqlDate.toString();
                                    String dt = ht.substring(0, 4);
                                    int af = Integer.parseInt(px7.getValue().toString());
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
                    JOptionPane.showMessageDialog(null, e.getMessage(), " Something went wron, please contact your database administrator or the vendor ", JOptionPane.ERROR_MESSAGE);
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

    private void acc_currencyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_acc_currencyActionPerformed
        if (!acc_currency.getSelectedItem().equals(" ")) {
            DefaultTableModel dk = (DefaultTableModel) acc_table.getModel();
            dk.setNumRows(0);
DB_USER = uname.getText();
        DB_PAS = upass.getText();
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

    private void cp3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cp3ActionPerformed
        java.util.Date date = new java.util.Date();
        long t = date.getTime();

        java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(t);
        java.sql.Date sqlDate = new java.sql.Date(t);
DB_USER = uname.getText();
        DB_PAS = upass.getText();
        if (!cp3.getSelectedItem().equals(" ")) {
            DefaultTableModel dk = (DefaultTableModel) cp_table.getModel();
            dk.setNumRows(0);

            String querie = "SELECT branch,currency,denominations,no_denominations,total, send_by,sender_position,receiver,receiver_position,date FROM clerks_operators where branch='" + cp2.getSelectedItem().toString() + "'";
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
                JOptionPane.showMessageDialog(null, e.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
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

    private void jLabel188MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel188MouseClicked
        java.util.Date date = new java.util.Date();
        long t = date.getTime();
        java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(t);
        java.sql.Date sqlDate = new java.sql.Date(t);
        DB_USER = uname.getText();
        DB_PAS = upass.getText();
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
                Logger.getLogger(G_Managers.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jLabel188MouseClicked

    private void n3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_n3ActionPerformed

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

    private void jLabel190MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel190MouseClicked
        n4.setText("");
        mk4.setText("");
    }//GEN-LAST:event_jLabel190MouseClicked

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

    private void bd_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bd_tableMouseClicked
        DefaultTableModel model = (DefaultTableModel) bu_table.getModel();
        bid.setText(model.getValueAt(bd_table.getSelectedRow(), 3).toString());
    }//GEN-LAST:event_bd_tableMouseClicked

    private void bu_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bu_tableMouseClicked
        DefaultTableModel model = (DefaultTableModel) bu_table.getModel();
        bu1.setText(model.getValueAt(bu_table.getSelectedRow(), 0).toString());
        bu2.setText(model.getValueAt(bu_table.getSelectedRow(), 1).toString());
        bu3.setText(model.getValueAt(bu_table.getSelectedRow(), 2).toString());
        bid.setText(model.getValueAt(bu_table.getSelectedRow(), 3).toString());
    }//GEN-LAST:event_bu_tableMouseClicked

    private void bd_tableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_bd_tableKeyPressed
        int xv = Integer.parseInt(bid.getText(), 10);
        DefaultTableModel mode = (DefaultTableModel) bu_table.getModel();
        if (bu_table.getSelectedRow() == -1) {
            if (bu_table.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "The table is empty", "Providence says....", JOptionPane.WARNING_MESSAGE);

            } else {
                JOptionPane.showMessageDialog(null, " You must select a branch to remove", "Providence says....", JOptionPane.WARNING_MESSAGE);
            }
        } else {

            int input = JOptionPane.showConfirmDialog(null, " Are you sure that you want to remove " + na.getText() + " " + nb.getText() + " " + "from the system ?", "Providence says....", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (input == JOptionPane.NO_OPTION) {

            }
            if (input == JOptionPane.YES_OPTION) {
                mode.removeRow(bu_table.getSelectedRow());
                SQLRun objSQLRun = new SQLRun();
                String sql = "DELETE FROM branches WHERE branch_id='" + xv + "'";

                int deleted = objSQLRun.sqlUpdate(sql);
                //getSum();
                if (deleted > 0) {
                    JOptionPane.showMessageDialog(null, "  branch removed successfully from the system", "Povidence says....", JOptionPane.INFORMATION_MESSAGE);
                    bid.setText("");

                } else {
                    if (bid.getText() == null) {

                    } else {
                        JOptionPane.showMessageDialog(null, "Branch with an ID :" + bid.getText() + " does not exist", "Providence says...", JOptionPane.ERROR_MESSAGE);

                    }
                }
            }
        }
    }//GEN-LAST:event_bd_tableKeyPressed
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
            java.util.logging.Logger.getLogger(G_Managers.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(G_Managers.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(G_Managers.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(G_Managers.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new G_Managers().setVisible(true);
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
    private javax.swing.JFormattedTextField a7;
    private javax.swing.JFormattedTextField a8;
    private javax.swing.JPasswordField a9;
    private javax.swing.JTable a_table;
    private javax.swing.JComboBox<String> acc_currency;
    private javax.swing.JTable acc_table;
    private javax.swing.JFormattedTextField acc_total;
    private javax.swing.JPanel animation;
    public static final javax.swing.JFormattedTextField b1 = new javax.swing.JFormattedTextField();
    private javax.swing.JFormattedTextField b2;
    private javax.swing.JFormattedTextField b3;
    private javax.swing.JEditorPane b4;
    private javax.swing.JTable bd_table;
    private javax.swing.JLabel bfc;
    private javax.swing.JLabel bid;
    private javax.swing.JPanel branches;
    private javax.swing.JLabel bscd;
    private javax.swing.JFormattedTextField bu1;
    private javax.swing.JFormattedTextField bu2;
    private javax.swing.JFormattedTextField bu3;
    private javax.swing.JEditorPane bu4;
    private javax.swing.JTable bu_table;
    private javax.swing.JTable bv_table;
    public static final javax.swing.JComboBox<String> cg1 = new javax.swing.JComboBox<>();
    private javax.swing.JComboBox<String> cg2;
    private javax.swing.JComboBox<String> cg3;
    private javax.swing.JFormattedTextField cg4;
    private javax.swing.JTable cg_table;
    protected static final javax.swing.JLabel configs = new javax.swing.JLabel();
    private javax.swing.JLabel configs2;
    public static final javax.swing.JComboBox<String> cp1 = new javax.swing.JComboBox<>();
    private javax.swing.JComboBox<String> cp2;
    private javax.swing.JComboBox<String> cp3;
    private javax.swing.JFormattedTextField cp4;
    private javax.swing.JTable cp_table;
    private javax.swing.JCheckBox cta;
    private javax.swing.JCheckBox ctb;
    private javax.swing.JPanel currencies;
    private javax.swing.JTable d_table;
    private javax.swing.JPanel dashboard_home;
    private javax.swing.JPanel dashboard_master;
    private javax.swing.JPanel dashboard_operations;
    private javax.swing.JPanel dashboard_rates;
    private javax.swing.JPanel dashboard_reports;
    private javax.swing.JLabel day;
    private javax.swing.JPanel denominations;
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
    public static final javax.swing.JComboBox<String> fb5 = new javax.swing.JComboBox<>();
    private javax.swing.JComboBox<String> fb6;
    private com.toedter.calendar.JDateChooser fb7;
    private javax.swing.JTable fb_table;
    private javax.swing.JCheckBox fc1;
    private javax.swing.JCheckBox fc2;
    private javax.swing.JCheckBox fc3;
    private javax.swing.JCheckBox fc4;
    public static final javax.swing.JComboBox<String> fc5 = new javax.swing.JComboBox<>();
    private javax.swing.JComboBox<String> fc6;
    private javax.swing.JCheckBox fd1;
    private javax.swing.JCheckBox fd2;
    private javax.swing.JCheckBox fd3;
    private javax.swing.JCheckBox fd4;
    public static final javax.swing.JComboBox<String> fd5 = new javax.swing.JComboBox<>();
    private javax.swing.JComboBox<String> fd6;
    private javax.swing.JSpinner fd7;
    private javax.swing.JTable fd_table;
    private javax.swing.JLabel fw1;
    private javax.swing.JLabel fw2;
    private javax.swing.JPanel header;
    private javax.swing.JCheckBox jCheckBox10;
    private javax.swing.JCheckBox jCheckBox11;
    private javax.swing.JComboBox<String> jComboBox14;
    private javax.swing.JComboBox<String> jComboBox16;
    private javax.swing.JComboBox<String> jComboBox17;
    private javax.swing.JComboBox<String> jComboBox18;
    private javax.swing.JFormattedTextField jFormattedTextField44;
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
    private javax.swing.JLabel jLabel111;
    private javax.swing.JLabel jLabel112;
    private javax.swing.JLabel jLabel113;
    private javax.swing.JLabel jLabel114;
    private javax.swing.JLabel jLabel115;
    private javax.swing.JLabel jLabel116;
    private javax.swing.JLabel jLabel117;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel128;
    private javax.swing.JLabel jLabel129;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel130;
    private javax.swing.JLabel jLabel131;
    private javax.swing.JLabel jLabel132;
    private javax.swing.JLabel jLabel133;
    private javax.swing.JLabel jLabel134;
    private javax.swing.JLabel jLabel135;
    private javax.swing.JLabel jLabel136;
    private javax.swing.JLabel jLabel138;
    private javax.swing.JLabel jLabel139;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel140;
    private javax.swing.JLabel jLabel141;
    private javax.swing.JLabel jLabel142;
    private javax.swing.JLabel jLabel143;
    private javax.swing.JLabel jLabel144;
    private javax.swing.JLabel jLabel146;
    private javax.swing.JLabel jLabel147;
    private javax.swing.JLabel jLabel148;
    private javax.swing.JLabel jLabel149;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel150;
    private javax.swing.JLabel jLabel151;
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
    private javax.swing.JLabel jLabel167;
    private javax.swing.JLabel jLabel168;
    private javax.swing.JLabel jLabel169;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel170;
    private javax.swing.JLabel jLabel171;
    private javax.swing.JLabel jLabel172;
    private javax.swing.JLabel jLabel173;
    private javax.swing.JLabel jLabel174;
    private javax.swing.JLabel jLabel176;
    private javax.swing.JLabel jLabel177;
    private javax.swing.JLabel jLabel178;
    private javax.swing.JLabel jLabel179;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel180;
    private javax.swing.JLabel jLabel181;
    private javax.swing.JLabel jLabel182;
    private javax.swing.JLabel jLabel187;
    private javax.swing.JLabel jLabel188;
    private javax.swing.JLabel jLabel189;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel190;
    private javax.swing.JLabel jLabel191;
    private javax.swing.JLabel jLabel192;
    private javax.swing.JLabel jLabel194;
    private javax.swing.JLabel jLabel195;
    private javax.swing.JLabel jLabel196;
    private javax.swing.JLabel jLabel197;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
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
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel71;
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
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
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
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel24;
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
    private javax.swing.JPanel jPanel47;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel52;
    private javax.swing.JPanel jPanel53;
    private javax.swing.JPanel jPanel54;
    private javax.swing.JPanel jPanel56;
    private javax.swing.JPanel jPanel58;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel60;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane21;
    private javax.swing.JScrollPane jScrollPane22;
    private javax.swing.JScrollPane jScrollPane24;
    private javax.swing.JScrollPane jScrollPane25;
    private javax.swing.JScrollPane jScrollPane26;
    private javax.swing.JScrollPane jScrollPane28;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane30;
    private javax.swing.JScrollPane jScrollPane34;
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
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTabbedPane jTabbedPane5;
    private javax.swing.JTabbedPane jTabbedPane7;
    private javax.swing.JTabbedPane jTabbedPane8;
    private javax.swing.JTabbedPane jTabbedPane9;
    private javax.swing.JTable jTable12;
    private javax.swing.JTable jTable13;
    private javax.swing.JTable jTable14;
    private javax.swing.JTable jm_table1;
    public static final javax.swing.JLabel kb1 = new javax.swing.JLabel();
    public static final javax.swing.JLabel kb2 = new javax.swing.JLabel();
    private com.toedter.calendar.JDateChooser mj1;
    private com.toedter.calendar.JDateChooser mj2;
    private javax.swing.JLabel mk4;
    private javax.swing.JLabel month;
    public static final javax.swing.JLabel my_id = new javax.swing.JLabel();
    private javax.swing.JFormattedTextField n1;
    private javax.swing.JEditorPane n2;
    private static javax.swing.JComboBox<String> n3;
    private javax.swing.JEditorPane n4;
    private javax.swing.JLabel na;
    public static final javax.swing.JLabel name = new javax.swing.JLabel();
    private javax.swing.JLabel nb;
    private javax.swing.JComboBox<String> ntf1;
    private javax.swing.JFormattedTextField ntf2;
    private javax.swing.JFormattedTextField ntf3;
    private javax.swing.JEditorPane ntf4;
    private javax.swing.JCheckBox opb1;
    private javax.swing.JCheckBox opb2;
    public static final javax.swing.JComboBox<String> opb3 = new javax.swing.JComboBox<>();
    private javax.swing.JComboBox<String> opb4;
    private javax.swing.JPanel opening_balances;
    private javax.swing.JPanel operations;
    private javax.swing.JLabel phy;
    private javax.swing.JPanel profit_and_loses2;
    private javax.swing.JPanel profit_and_loses4;
    private javax.swing.JPanel purchases_rep;
    private javax.swing.JCheckBox px1;
    private javax.swing.JCheckBox px2;
    private javax.swing.JCheckBox px3;
    private javax.swing.JCheckBox px4;
    public static final javax.swing.JComboBox<String> px5 = new javax.swing.JComboBox<>();
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
    private javax.swing.JPanel rates;
    public static final javax.swing.JLabel reg = new javax.swing.JLabel();
    private javax.swing.JPanel reports;
    private javax.swing.JTable rtl_table;
    private javax.swing.JTable rtl_table1;
    private javax.swing.JPanel sales_rep;
    private javax.swing.JSpinner sym;
    private javax.swing.JLabel time;
    private javax.swing.JLabel ts1;
    private javax.swing.JLabel ts2;
    private javax.swing.JCheckBox tw1;
    private javax.swing.JCheckBox tw2;
    public static final javax.swing.JComboBox<String> tw3 = new javax.swing.JComboBox<>();
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
    private javax.swing.JFormattedTextField u7;
    private javax.swing.JTable u_table;
    private javax.swing.JLabel uid;
    public static final javax.swing.JLabel uname = new javax.swing.JLabel();
    public static final javax.swing.JLabel upass = new javax.swing.JLabel();
    private javax.swing.JPanel users;
    public static final javax.swing.JLabel w1 = new javax.swing.JLabel();
    public static final javax.swing.JLabel w2 = new javax.swing.JLabel();
    private javax.swing.JCheckBox wy1;
    private javax.swing.JCheckBox wy2;
    private javax.swing.JCheckBox wy3;
    private javax.swing.JCheckBox wy4;
    public static final javax.swing.JComboBox<String> wy5 = new javax.swing.JComboBox<>();
    private javax.swing.JComboBox<String> wy6;
    private com.toedter.calendar.JDateChooser wy7;
    private javax.swing.JTable wy_table;
    private javax.swing.JLabel year;
    private javax.swing.JCheckBox zh1;
    private javax.swing.JCheckBox zh2;
    private javax.swing.JCheckBox zh3;
    private javax.swing.JCheckBox zh4;
    public static final javax.swing.JComboBox<String> zh5 = new javax.swing.JComboBox<>();
    private javax.swing.JComboBox<String> zh6;
    private javax.swing.JSpinner zh7;
    private javax.swing.JLabel zh8;
    private javax.swing.JTable zh_table;
    private javax.swing.JSpinner zm1;
    private javax.swing.JLabel zm5;
    private javax.swing.JTable zm_table;
    // End of variables declaration//GEN-END:variables

}
