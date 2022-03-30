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
import java.sql.DriverManager;
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
public class Finance_Dep extends javax.swing.JFrame {

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

    public Finance_Dep() {
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

        simult.setVisible(false);
        fa6.setEnabled(false);

        //if(!fb4.isSelected()){
        fb6.setEnabled(false);

        currencies.setVisible(false);
        opening_balances.setVisible(false);
        denominations.setVisible(false);

        rates.setVisible(false);
        reports.setVisible(false);
        users.setVisible(false);
        branches.setVisible(false);
        operations.setVisible(false);

        b5.setEditable(false);

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

    public void clearOptions2() {
        //Make sure the check boxes are not checked
        c3.setSelected(false);
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
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        header = new javax.swing.JPanel();
        day = new javax.swing.JLabel();
        month = new javax.swing.JLabel();
        year = new javax.swing.JLabel();
        time = new javax.swing.JLabel();
        animation = new javax.swing.JPanel();
        branches = new javax.swing.JPanel();
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
        rates = new javax.swing.JPanel();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel17 = new javax.swing.JPanel();
        jTabbedPane4 = new javax.swing.JTabbedPane();
        jPanel24 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        rt1 = new javax.swing.JFormattedTextField();
        rt2 = new javax.swing.JComboBox<>();
        jLabel41 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        rt5 = new javax.swing.JFormattedTextField();
        jScrollPane29 = new javax.swing.JScrollPane();
        rt_table = new javax.swing.JTable();
        zp = new javax.swing.JLabel();
        xp = new javax.swing.JLabel();
        rt6 = new javax.swing.JFormattedTextField();
        jLabel64 = new javax.swing.JLabel();
        jPanel25 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
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
        jLabel32 = new javax.swing.JLabel();
        jpy1 = new javax.swing.JLabel();
        jpb1 = new javax.swing.JLabel();
        opt2 = new javax.swing.JLabel();
        opt3 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        jPanel26 = new javax.swing.JPanel();
        jScrollPane19 = new javax.swing.JScrollPane();
        rtu = new javax.swing.JTable();
        ru5 = new javax.swing.JFormattedTextField();
        jLabel83 = new javax.swing.JLabel();
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
        jLabel4 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        jPanel22 = new javax.swing.JPanel();
        jPanel29 = new javax.swing.JPanel();
        jScrollPane20 = new javax.swing.JScrollPane();
        rtv = new javax.swing.JTable();
        jLabel86 = new javax.swing.JLabel();
        jLabel87 = new javax.swing.JLabel();
        jLabel88 = new javax.swing.JLabel();
        currencies = new javax.swing.JPanel();
        jTabbedPane11 = new javax.swing.JTabbedPane();
        jPanel46 = new javax.swing.JPanel();
        jLabel61 = new javax.swing.JLabel();
        c1 = new javax.swing.JFormattedTextField();
        jLabel89 = new javax.swing.JLabel();
        c2 = new javax.swing.JFormattedTextField();
        c3 = new javax.swing.JCheckBox();
        jLabel5 = new javax.swing.JLabel();
        tst = new javax.swing.JLabel();
        fdw = new javax.swing.JLabel();
        jPanel48 = new javax.swing.JPanel();
        jLabel90 = new javax.swing.JLabel();
        jLabel97 = new javax.swing.JLabel();
        cy2 = new javax.swing.JFormattedTextField();
        cy1 = new javax.swing.JFormattedTextField();
        cy3 = new javax.swing.JCheckBox();
        jScrollPane22 = new javax.swing.JScrollPane();
        c_table = new javax.swing.JTable();
        cy_id = new javax.swing.JLabel();
        dec = new javax.swing.JLabel();
        cn = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jPanel50 = new javax.swing.JPanel();
        jScrollPane23 = new javax.swing.JScrollPane();
        dy_table = new javax.swing.JTable();
        did = new javax.swing.JLabel();
        ddname = new javax.swing.JLabel();
        denominations = new javax.swing.JPanel();
        jTabbedPane12 = new javax.swing.JTabbedPane();
        jPanel54 = new javax.swing.JPanel();
        jLabel111 = new javax.swing.JLabel();
        jLabel112 = new javax.swing.JLabel();
        denv = new javax.swing.JFormattedTextField();
        denom = new javax.swing.JComboBox<>();
        jScrollPane25 = new javax.swing.JScrollPane();
        den_table = new javax.swing.JTable();
        jPanel55 = new javax.swing.JPanel();
        jScrollPane31 = new javax.swing.JScrollPane();
        denu_table = new javax.swing.JTable();
        jPanel56 = new javax.swing.JPanel();
        jScrollPane24 = new javax.swing.JScrollPane();
        dend_table = new javax.swing.JTable();
        opening_balances = new javax.swing.JPanel();
        jTabbedPane6 = new javax.swing.JTabbedPane();
        jPanel52 = new javax.swing.JPanel();
        jLabel71 = new javax.swing.JLabel();
        b1 = new javax.swing.JComboBox<>();
        b3 = new javax.swing.JFormattedTextField();
        jLabel113 = new javax.swing.JLabel();
        jLabel114 = new javax.swing.JLabel();
        b2 = new javax.swing.JComboBox<>();
        b4 = new javax.swing.JComboBox<>();
        jLabel115 = new javax.swing.JLabel();
        b5 = new javax.swing.JFormattedTextField();
        jLabel116 = new javax.swing.JLabel();
        jScrollPane26 = new javax.swing.JScrollPane();
        b_table = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        iter = new javax.swing.JFormattedTextField();
        jLabel44 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane30 = new javax.swing.JScrollPane();
        bal_table = new javax.swing.JTable();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jFormattedTextField6 = new javax.swing.JFormattedTextField();
        users = new javax.swing.JPanel();
        jTabbedPane10 = new javax.swing.JTabbedPane();
        jPanel14 = new javax.swing.JPanel();
        jLabel47 = new javax.swing.JLabel();
        jFormattedTextField9 = new javax.swing.JFormattedTextField();
        jLabel98 = new javax.swing.JLabel();
        jFormattedTextField36 = new javax.swing.JFormattedTextField();
        jLabel99 = new javax.swing.JLabel();
        jComboBox13 = new javax.swing.JComboBox<>();
        jLabel100 = new javax.swing.JLabel();
        jFormattedTextField37 = new javax.swing.JFormattedTextField();
        jLabel101 = new javax.swing.JLabel();
        jFormattedTextField38 = new javax.swing.JFormattedTextField();
        jLabel102 = new javax.swing.JLabel();
        jScrollPane27 = new javax.swing.JScrollPane();
        jTable9 = new javax.swing.JTable();
        jLabel103 = new javax.swing.JLabel();
        jFormattedTextField39 = new javax.swing.JFormattedTextField();
        jPasswordField1 = new javax.swing.JPasswordField();
        jPasswordField2 = new javax.swing.JPasswordField();
        jLabel104 = new javax.swing.JLabel();
        jLabel105 = new javax.swing.JLabel();
        jLabel117 = new javax.swing.JLabel();
        jFormattedTextField48 = new javax.swing.JFormattedTextField();
        jComboBox21 = new javax.swing.JComboBox<>();
        jPanel16 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        jTable10 = new javax.swing.JTable();
        jLabel48 = new javax.swing.JLabel();
        jLabel106 = new javax.swing.JLabel();
        jLabel107 = new javax.swing.JLabel();
        jLabel108 = new javax.swing.JLabel();
        jLabel109 = new javax.swing.JLabel();
        jFormattedTextField40 = new javax.swing.JFormattedTextField();
        jFormattedTextField41 = new javax.swing.JFormattedTextField();
        jComboBox15 = new javax.swing.JComboBox<>();
        jFormattedTextField43 = new javax.swing.JFormattedTextField();
        jFormattedTextField45 = new javax.swing.JFormattedTextField();
        jLabel110 = new javax.swing.JLabel();
        jLabel118 = new javax.swing.JLabel();
        jLabel119 = new javax.swing.JLabel();
        jLabel120 = new javax.swing.JLabel();
        jPasswordField3 = new javax.swing.JPasswordField();
        jPasswordField4 = new javax.swing.JPasswordField();
        jFormattedTextField46 = new javax.swing.JFormattedTextField();
        jFormattedTextField47 = new javax.swing.JFormattedTextField();
        jComboBox20 = new javax.swing.JComboBox<>();
        jLabel49 = new javax.swing.JLabel();
        jPanel35 = new javax.swing.JPanel();
        jScrollPane28 = new javax.swing.JScrollPane();
        jTable11 = new javax.swing.JTable();
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
        jScrollPane35 = new javax.swing.JScrollPane();
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
        fb5 = new javax.swing.JComboBox<>();
        jLabel78 = new javax.swing.JLabel();
        fb6 = new javax.swing.JComboBox<>();
        fw1 = new javax.swing.JLabel();
        fw2 = new javax.swing.JLabel();
        jPanel37 = new javax.swing.JPanel();
        jScrollPane36 = new javax.swing.JScrollPane();
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
        fc5 = new javax.swing.JComboBox<>();
        jLabel80 = new javax.swing.JLabel();
        fc6 = new javax.swing.JComboBox<>();
        zm1 = new javax.swing.JSpinner();
        zm5 = new javax.swing.JLabel();
        sym = new javax.swing.JSpinner();
        jLabel144 = new javax.swing.JLabel();
        jPanel38 = new javax.swing.JPanel();
        jScrollPane37 = new javax.swing.JScrollPane();
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
        fd5 = new javax.swing.JComboBox<>();
        jLabel82 = new javax.swing.JLabel();
        fd6 = new javax.swing.JComboBox<>();
        fd7 = new javax.swing.JSpinner();
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
        jLabel175 = new javax.swing.JLabel();
        jLabel155 = new javax.swing.JLabel();
        py6 = new javax.swing.JComboBox<>();
        jLabel156 = new javax.swing.JLabel();
        bfc = new javax.swing.JLabel();
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
        profit_and_loses = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel184 = new javax.swing.JLabel();
        prf1 = new javax.swing.JComboBox<>();
        prf2 = new javax.swing.JComboBox<>();
        jLabel185 = new javax.swing.JLabel();
        jLabel186 = new javax.swing.JLabel();
        prf5 = new javax.swing.JComboBox<>();
        jPanel9 = new javax.swing.JPanel();
        prf6 = new javax.swing.JCheckBox();
        prf7 = new javax.swing.JCheckBox();
        prf8 = new javax.swing.JCheckBox();
        jLabel193 = new javax.swing.JLabel();
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
        jPanel15 = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
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
        jPanel49 = new javax.swing.JPanel();
        jPanel51 = new javax.swing.JPanel();
        jLabel157 = new javax.swing.JLabel();
        tw5 = new javax.swing.JComboBox<>();
        jScrollPane34 = new javax.swing.JScrollPane();
        tw_table = new javax.swing.JTable();
        jLabel165 = new javax.swing.JLabel();
        tw6 = new javax.swing.JFormattedTextField();
        tw4 = new javax.swing.JComboBox<>();
        tw3 = new javax.swing.JComboBox<>();
        simult = new javax.swing.JPanel();
        dv1 = new javax.swing.JFormattedTextField();
        jLabel37 = new javax.swing.JLabel();
        dv2 = new javax.swing.JFormattedTextField();
        jLabel38 = new javax.swing.JLabel();
        dv3 = new javax.swing.JFormattedTextField();
        jLabel39 = new javax.swing.JLabel();
        dv4 = new javax.swing.JFormattedTextField();
        jLabel40 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        sv2 = new javax.swing.JFormattedTextField();
        sv1 = new javax.swing.JFormattedTextField();
        jLabel60 = new javax.swing.JLabel();
        sv3 = new javax.swing.JFormattedTextField();
        jLabel68 = new javax.swing.JLabel();
        sv4 = new javax.swing.JFormattedTextField();
        jLabel69 = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
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
        jLabel2.setText("Finance Personel");

        name.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        name.setForeground(new java.awt.Color(51, 102, 255));
        name.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        name.setText("Providence Chikukwa");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(name, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(name)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 7, 240, 200));

        dashboard_home.setBackground(new java.awt.Color(255, 255, 255));
        dashboard_home.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        dashboard_home.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-dollar-euro-exchange-32.png"))); // NOI18N
        jLabel6.setText("Rates Configuration");
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });
        dashboard_home.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 154, 224, 40));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-product-documents-32.png"))); // NOI18N
        jLabel7.setText("Generated Reports");
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });
        dashboard_home.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 205, 224, 40));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-contact-details-32.png"))); // NOI18N
        jLabel9.setText("Change My Credentials");
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel9MouseClicked(evt);
            }
        });
        dashboard_home.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 263, 224, 40));

        jLabel50.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel50.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-notification-32.png"))); // NOI18N
        jLabel50.setText("Notifications");
        jLabel50.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel50MouseClicked(evt);
            }
        });
        jLabel50.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jLabel50KeyPressed(evt);
            }
        });
        dashboard_home.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 314, 224, 40));

        jLabel51.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel51.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ex.png"))); // NOI18N
        jLabel51.setText("Currencies");
        jLabel51.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel51MouseClicked(evt);
            }
        });
        dashboard_home.add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 2, 224, 41));

        jLabel52.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel52.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/sn.png"))); // NOI18N
        jLabel52.setText("Denominations");
        jLabel52.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel52MouseClicked(evt);
            }
        });
        dashboard_home.add(jLabel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 54, 224, 39));

        jLabel53.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel53.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/sndm.png"))); // NOI18N
        jLabel53.setText("Branch Balances");
        jLabel53.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel53MouseClicked(evt);
            }
        });
        dashboard_home.add(jLabel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 104, 224, 39));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/lout.png"))); // NOI18N
        jLabel10.setText("Sign Out");
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
        });
        dashboard_home.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 365, 224, 40));

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/clo.png"))); // NOI18N
        jLabel11.setText("Close Application");
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
        });
        dashboard_home.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 416, 224, 40));

        my_id.setText("jLabel45");
        dashboard_home.add(my_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(33, 488, -1, 0));

        configs.setText("jLabel50");
        dashboard_home.add(configs, new org.netbeans.lib.awtextra.AbsoluteConstraints(82, 474, -1, 0));

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
                .addContainerGap(109, Short.MAX_VALUE))
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

        jPanel1.add(header, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 8, 995, 90));

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

        jPanel1.add(animation, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 100, 995, -1));

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

        javax.swing.GroupLayout branchesLayout = new javax.swing.GroupLayout(branches);
        branches.setLayout(branchesLayout);
        branchesLayout.setHorizontalGroup(
            branchesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1000, Short.MAX_VALUE)
            .addGroup(branchesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, branchesLayout.createSequentialGroup()
                    .addComponent(jTabbedPane5)
                    .addContainerGap()))
        );
        branchesLayout.setVerticalGroup(
            branchesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 580, Short.MAX_VALUE)
            .addGroup(branchesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, branchesLayout.createSequentialGroup()
                    .addComponent(jTabbedPane5)
                    .addContainerGap()))
        );

        jPanel1.add(branches, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 140, 1000, 580));

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
                .addContainerGap(194, Short.MAX_VALUE))
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
                .addContainerGap(296, Short.MAX_VALUE))
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
                .addContainerGap(245, Short.MAX_VALUE))
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
                .addContainerGap(296, Short.MAX_VALUE))
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

        jTabbedPane3.setBackground(new java.awt.Color(255, 255, 255));

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));
        jPanel17.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTabbedPane4.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane4.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);

        jPanel24.setBackground(new java.awt.Color(255, 255, 255));

        jLabel18.setText("Base currency :");

        rt1.setEditable(false);
        rt1.setBackground(new java.awt.Color(255, 255, 255));

        rt2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));

        jLabel41.setText("Quote currency");

        jLabel43.setText("Buying rate");

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
        jScrollPane29.setViewportView(rt_table);
        if (rt_table.getColumnModel().getColumnCount() > 0) {
            rt_table.getColumnModel().getColumn(0).setResizable(false);
            rt_table.getColumnModel().getColumn(1).setResizable(false);
            rt_table.getColumnModel().getColumn(2).setResizable(false);
            rt_table.getColumnModel().getColumn(3).setResizable(false);
            rt_table.getColumnModel().getColumn(4).setResizable(false);
            rt_table.getColumnModel().getColumn(4).setHeaderValue("Exchange rate");
        }

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
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rt1, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel41)
                            .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(jScrollPane29, javax.swing.GroupLayout.PREFERRED_SIZE, 718, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(rt1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rt2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel41))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rt5, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel43))
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
                    .addComponent(jScrollPane29, javax.swing.GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        jTabbedPane4.addTab("Local Currency", new javax.swing.ImageIcon(getClass().getResource("/img/opr.png")), jPanel24); // NOI18N

        jPanel25.setBackground(new java.awt.Color(255, 255, 255));

        jLabel31.setText("Base currency :");

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
        if (rt_table1.getColumnModel().getColumnCount() > 0) {
            rt_table1.getColumnModel().getColumn(0).setResizable(false);
            rt_table1.getColumnModel().getColumn(1).setResizable(false);
            rt_table1.getColumnModel().getColumn(2).setResizable(false);
            rt_table1.getColumnModel().getColumn(3).setResizable(false);
        }

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

        jLabel32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-installing-updates-32.png"))); // NOI18N
        jLabel32.setText("Save Exchange Rates");
        jLabel32.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel32MouseClicked(evt);
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
                                    .addComponent(jLabel31)
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
                                    .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(jLabel31)
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
                .addComponent(jLabel32)
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
        if (rtu.getColumnModel().getColumnCount() > 0) {
            rtu.getColumnModel().getColumn(0).setResizable(false);
            rtu.getColumnModel().getColumn(1).setResizable(false);
            rtu.getColumnModel().getColumn(2).setResizable(false);
            rtu.getColumnModel().getColumn(3).setResizable(false);
            rtu.getColumnModel().getColumn(4).setResizable(false);
        }

        jPanel26.add(jScrollPane19, new org.netbeans.lib.awtextra.AbsoluteConstraints(308, 11, 677, 472));

        ru5.setEditable(false);
        ru5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel26.add(ru5, new org.netbeans.lib.awtextra.AbsoluteConstraints(116, 135, 174, 29));

        jLabel83.setText("Buying rate");
        jPanel26.add(jLabel83, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 142, -1, -1));

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

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-calculator-32.png"))); // NOI18N
        jLabel4.setText("Automated calculator");
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });
        jPanel26.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 220, 140, 38));

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
        if (rtv.getColumnModel().getColumnCount() > 0) {
            rtv.getColumnModel().getColumn(0).setResizable(false);
            rtv.getColumnModel().getColumn(1).setResizable(false);
            rtv.getColumnModel().getColumn(2).setResizable(false);
            rtv.getColumnModel().getColumn(3).setResizable(false);
        }

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

        jPanel1.add(rates, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 140, 1000, 580));

        jTabbedPane11.setBackground(new java.awt.Color(255, 255, 255));

        jPanel46.setBackground(new java.awt.Color(255, 255, 255));

        jLabel61.setText("Enter currency name");

        jLabel89.setText("Currency short code");

        c3.setBackground(new java.awt.Color(255, 255, 255));
        c3.setText("Click here if the currency is a local currency");
        c3.setFocusable(false);
        c3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                c3ActionPerformed(evt);
            }
        });

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-new-document-32.png"))); // NOI18N
        jLabel5.setText("save");
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });

        tst.setText("jLabel53");
        tst.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tstMouseClicked(evt);
            }
        });

        fdw.setText("jLabel53");

        javax.swing.GroupLayout jPanel46Layout = new javax.swing.GroupLayout(jPanel46);
        jPanel46.setLayout(jPanel46Layout);
        jPanel46Layout.setHorizontalGroup(
            jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel46Layout.createSequentialGroup()
                .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel46Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel61)
                        .addGap(10, 10, 10)
                        .addComponent(c1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel46Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(c3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel46Layout.createSequentialGroup()
                                .addComponent(jLabel89)
                                .addGap(10, 10, 10)
                                .addComponent(c2, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tst, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel46Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(fdw)))
                .addGap(715, 715, 715))
        );
        jPanel46Layout.setVerticalGroup(
            jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel46Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel46Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jLabel61))
                    .addComponent(c1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel46Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jLabel89))
                    .addComponent(c2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(c3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(tst, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addComponent(fdw, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane11.addTab("Add Curencies", new javax.swing.ImageIcon(getClass().getResource("/img/ex.png")), jPanel46); // NOI18N

        jPanel48.setBackground(new java.awt.Color(255, 255, 255));

        jLabel90.setText("Enter currency name");

        jLabel97.setText("Currency short code");

        cy3.setBackground(new java.awt.Color(255, 255, 255));
        cy3.setText("Click here if the currency is a local currency");
        cy3.setFocusable(false);
        cy3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cy3ActionPerformed(evt);
            }
        });

        c_table.setAutoCreateRowSorter(true);
        c_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Currency name", "Short code", "Local currency", "Currency ID"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        c_table.setRowHeight(20);
        c_table.setShowHorizontalLines(false);
        c_table.setShowVerticalLines(false);
        c_table.getTableHeader().setReorderingAllowed(false);
        c_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                c_tableMouseClicked(evt);
            }
        });
        jScrollPane22.setViewportView(c_table);
        if (c_table.getColumnModel().getColumnCount() > 0) {
            c_table.getColumnModel().getColumn(0).setResizable(false);
            c_table.getColumnModel().getColumn(1).setResizable(false);
            c_table.getColumnModel().getColumn(2).setResizable(false);
            c_table.getColumnModel().getColumn(3).setResizable(false);
        }

        cy_id.setText("jLabel54");

        dec.setText("false");

        cn.setText("jLabel54");

        jLabel54.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-update-32.png"))); // NOI18N
        jLabel54.setText("Update");
        jLabel54.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel54MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel48Layout = new javax.swing.GroupLayout(jPanel48);
        jPanel48.setLayout(jPanel48Layout);
        jPanel48Layout.setHorizontalGroup(
            jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel48Layout.createSequentialGroup()
                .addGroup(jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel48Layout.createSequentialGroup()
                        .addGap(128, 128, 128)
                        .addComponent(jScrollPane22, javax.swing.GroupLayout.PREFERRED_SIZE, 740, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel48Layout.createSequentialGroup()
                        .addGap(396, 396, 396)
                        .addGroup(jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel48Layout.createSequentialGroup()
                                .addComponent(jLabel90)
                                .addGap(10, 10, 10)
                                .addComponent(cy1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel48Layout.createSequentialGroup()
                                .addComponent(jLabel97)
                                .addGap(10, 10, 10)
                                .addGroup(jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cy2, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel48Layout.createSequentialGroup()
                                        .addComponent(cy3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cy_id)
                    .addComponent(cn)
                    .addComponent(dec, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(83, 83, 83))
        );
        jPanel48Layout.setVerticalGroup(
            jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel48Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel48Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jLabel90))
                    .addComponent(cy1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel48Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jLabel97))
                    .addComponent(cy2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cy3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel48Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane22, javax.swing.GroupLayout.DEFAULT_SIZE, 371, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel48Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(cy_id, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(dec, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cn, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jTabbedPane11.addTab("Update Currency Details", new javax.swing.ImageIcon(getClass().getResource("/img/rat.png")), jPanel48); // NOI18N

        jPanel50.setBackground(new java.awt.Color(255, 255, 255));

        dy_table.setAutoCreateRowSorter(true);
        dy_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Currency name", "Short code", "Local currency", "Currency ID"
            }
        ));
        dy_table.setRowHeight(20);
        dy_table.setShowHorizontalLines(false);
        dy_table.setShowVerticalLines(false);
        dy_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dy_tableMouseClicked(evt);
            }
        });
        dy_table.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                dy_tableKeyPressed(evt);
            }
        });
        jScrollPane23.setViewportView(dy_table);

        did.setText("jLabel54");

        ddname.setText("jLabel54");

        javax.swing.GroupLayout jPanel50Layout = new javax.swing.GroupLayout(jPanel50);
        jPanel50.setLayout(jPanel50Layout);
        jPanel50Layout.setHorizontalGroup(
            jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel50Layout.createSequentialGroup()
                .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel50Layout.createSequentialGroup()
                        .addGap(179, 179, 179)
                        .addComponent(jScrollPane23, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel50Layout.createSequentialGroup()
                        .addGap(382, 382, 382)
                        .addComponent(did, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(92, 92, 92)
                        .addComponent(ddname, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(216, Short.MAX_VALUE))
        );
        jPanel50Layout.setVerticalGroup(
            jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel50Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ddname, javax.swing.GroupLayout.PREFERRED_SIZE, 2, Short.MAX_VALUE)
                    .addComponent(did, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(55, Short.MAX_VALUE))
        );

        jTabbedPane11.addTab("Remove Currencies", new javax.swing.ImageIcon(getClass().getResource("/img/rat.png")), jPanel50); // NOI18N

        javax.swing.GroupLayout currenciesLayout = new javax.swing.GroupLayout(currencies);
        currencies.setLayout(currenciesLayout);
        currenciesLayout.setHorizontalGroup(
            currenciesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1000, Short.MAX_VALUE)
            .addGroup(currenciesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jTabbedPane11, javax.swing.GroupLayout.Alignment.TRAILING))
        );
        currenciesLayout.setVerticalGroup(
            currenciesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 580, Short.MAX_VALUE)
            .addGroup(currenciesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jTabbedPane11, javax.swing.GroupLayout.Alignment.TRAILING))
        );

        jPanel1.add(currencies, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 140, 1000, 580));

        jTabbedPane12.setBackground(new java.awt.Color(255, 255, 255));

        jPanel54.setBackground(new java.awt.Color(255, 255, 255));

        jLabel111.setText("Select currency");

        jLabel112.setText("Denomination value");

        denv.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                denvKeyPressed(evt);
            }
        });

        denom.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));

        den_table.setAutoCreateRowSorter(true);
        den_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Currency", "Denomination value"
            }
        ));
        den_table.setRowHeight(25);
        den_table.setShowHorizontalLines(false);
        den_table.setShowVerticalLines(false);
        jScrollPane25.setViewportView(den_table);

        javax.swing.GroupLayout jPanel54Layout = new javax.swing.GroupLayout(jPanel54);
        jPanel54.setLayout(jPanel54Layout);
        jPanel54Layout.setHorizontalGroup(
            jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel54Layout.createSequentialGroup()
                .addContainerGap(278, Short.MAX_VALUE)
                .addGroup(jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel54Layout.createSequentialGroup()
                            .addComponent(jLabel111)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(denom, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel54Layout.createSequentialGroup()
                            .addComponent(jLabel112)
                            .addGap(18, 18, 18)
                            .addComponent(denv, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(265, 265, 265))
        );
        jPanel54Layout.setVerticalGroup(
            jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel54Layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addGroup(jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(denom, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel111))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel54Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jLabel112))
                    .addComponent(denv, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane25, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(116, Short.MAX_VALUE))
        );

        jTabbedPane12.addTab("Add Denominations", new javax.swing.ImageIcon(getClass().getResource("/img/sp.png")), jPanel54); // NOI18N

        jPanel55.setBackground(new java.awt.Color(255, 255, 255));

        denu_table.setAutoCreateRowSorter(true);
        denu_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Currency", "Denomination value"
            }
        ));
        denu_table.setRowHeight(25);
        denu_table.setShowHorizontalLines(false);
        denu_table.setShowVerticalLines(false);
        jScrollPane31.setViewportView(denu_table);

        javax.swing.GroupLayout jPanel55Layout = new javax.swing.GroupLayout(jPanel55);
        jPanel55.setLayout(jPanel55Layout);
        jPanel55Layout.setHorizontalGroup(
            jPanel55Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel55Layout.createSequentialGroup()
                .addContainerGap(224, Short.MAX_VALUE)
                .addComponent(jScrollPane31, javax.swing.GroupLayout.PREFERRED_SIZE, 568, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(203, 203, 203))
        );
        jPanel55Layout.setVerticalGroup(
            jPanel55Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel55Layout.createSequentialGroup()
                .addGap(129, 129, 129)
                .addComponent(jScrollPane31, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(116, Short.MAX_VALUE))
        );

        jTabbedPane12.addTab("Available Denominations", new javax.swing.ImageIcon(getClass().getResource("/img/vp.png")), jPanel55); // NOI18N

        jPanel56.setBackground(new java.awt.Color(255, 255, 255));

        dend_table.setAutoCreateRowSorter(true);
        dend_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Currency", "Denomination value", "Denomination ID"
            }
        ));
        dend_table.setRowHeight(25);
        dend_table.setShowHorizontalLines(false);
        dend_table.setShowVerticalLines(false);
        jScrollPane24.setViewportView(dend_table);

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

        jTabbedPane12.addTab("Delete Denomination", new javax.swing.ImageIcon(getClass().getResource("/img/up32.png")), jPanel56); // NOI18N

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

        b1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));

        b3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                b3KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                b3KeyTyped(evt);
            }
        });

        jLabel113.setText("Enter amount");

        jLabel114.setText("Select currency");

        b2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
        b2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b2ActionPerformed(evt);
            }
        });

        b4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));

        jLabel115.setText("Select denominations");

        b5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                b5KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                b5KeyTyped(evt);
            }
        });

        jLabel116.setText("No. denominations");

        b_table.setAutoCreateRowSorter(true);
        b_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Branch", "Currency", "Denominations", "No. denominations", "Total"
            }
        ));
        b_table.setRowHeight(20);
        b_table.setShowHorizontalLines(false);
        b_table.setShowVerticalLines(false);
        jScrollPane26.setViewportView(b_table);

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/sndm.png"))); // NOI18N

        iter.setEditable(false);
        iter.setText("000000");
        iter.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel44.setText("Amount Received");

        javax.swing.GroupLayout jPanel52Layout = new javax.swing.GroupLayout(jPanel52);
        jPanel52.setLayout(jPanel52Layout);
        jPanel52Layout.setHorizontalGroup(
            jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel52Layout.createSequentialGroup()
                .addGroup(jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                            .addComponent(b4, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(b3)
                            .addComponent(b1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(b2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(b5, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 169, Short.MAX_VALUE)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(iter, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel44)))
                    .addGroup(jPanel52Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane26)))
                .addContainerGap())
        );
        jPanel52Layout.setVerticalGroup(
            jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel52Layout.createSequentialGroup()
                .addGroup(jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel52Layout.createSequentialGroup()
                            .addGap(15, 15, 15)
                            .addGroup(jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel71)
                                .addComponent(b1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel52Layout.createSequentialGroup()
                            .addComponent(jLabel44, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(iter, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel114)
                    .addComponent(b2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(b3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel113))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(b4, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel115))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(b5, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel116))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane26, javax.swing.GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE))
        );

        jTabbedPane6.addTab("Send Opening Balance", new javax.swing.ImageIcon(getClass().getResource("/img/sndm.png")), jPanel52); // NOI18N

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jPanel13MouseMoved(evt);
            }
        });

        bal_table.setAutoCreateRowSorter(true);
        bal_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Branch", "Currency", "Denominations", "No. denominations", "Total"
            }
        ));
        bal_table.setRowHeight(20);
        bal_table.setShowHorizontalLines(false);
        bal_table.setShowVerticalLines(false);
        jScrollPane30.setViewportView(bal_table);

        jLabel55.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/pdf.png"))); // NOI18N
        jLabel55.setText("Export to pdf");

        jLabel56.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/print.png"))); // NOI18N
        jLabel56.setText("Print ");

        jLabel57.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/excel.png"))); // NOI18N
        jLabel57.setText("Export to excel");

        jFormattedTextField6.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)), null));
        jFormattedTextField6.setText("Enter branch name eg Harare");
        jFormattedTextField6.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jFormattedTextField6MouseMoved(evt);
            }
        });
        jFormattedTextField6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField6KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(113, 113, 113)
                .addComponent(jLabel57)
                .addGap(215, 215, 215)
                .addComponent(jLabel55)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 252, Short.MAX_VALUE)
                .addComponent(jLabel56)
                .addGap(145, 145, 145))
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jFormattedTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel13Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane30, javax.swing.GroupLayout.DEFAULT_SIZE, 975, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addComponent(jFormattedTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 428, Short.MAX_VALUE)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel55)
                    .addComponent(jLabel56)
                    .addComponent(jLabel57))
                .addGap(45, 45, 45))
            .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel13Layout.createSequentialGroup()
                    .addGap(37, 37, 37)
                    .addComponent(jScrollPane30, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(96, Short.MAX_VALUE)))
        );

        jTabbedPane6.addTab("Branc Balances", new javax.swing.ImageIcon(getClass().getResource("/img/sn.png")), jPanel13); // NOI18N

        javax.swing.GroupLayout opening_balancesLayout = new javax.swing.GroupLayout(opening_balances);
        opening_balances.setLayout(opening_balancesLayout);
        opening_balancesLayout.setHorizontalGroup(
            opening_balancesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1000, Short.MAX_VALUE)
            .addGroup(opening_balancesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jTabbedPane6))
        );
        opening_balancesLayout.setVerticalGroup(
            opening_balancesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 580, Short.MAX_VALUE)
            .addGroup(opening_balancesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jTabbedPane6))
        );

        jPanel1.add(opening_balances, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 140, 1000, 580));

        jTabbedPane10.setBackground(new java.awt.Color(255, 255, 255));

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));

        jLabel47.setText("Name");

        jLabel98.setText("Surname");

        jLabel99.setText("Gender");

        jComboBox13.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "male", "female" }));

        jLabel100.setText("Phone number");

        jLabel101.setText("ID number");

        jLabel102.setText("Position");

        jTable9.setAutoCreateRowSorter(true);
        jTable9.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Surname", "Gender", "Branch", "Phone number", "Region", "Position"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable9.setRowHeight(20);
        jTable9.setShowHorizontalLines(false);
        jTable9.setShowVerticalLines(false);
        jScrollPane27.setViewportView(jTable9);
        if (jTable9.getColumnModel().getColumnCount() > 0) {
            jTable9.getColumnModel().getColumn(0).setResizable(false);
            jTable9.getColumnModel().getColumn(1).setResizable(false);
            jTable9.getColumnModel().getColumn(2).setResizable(false);
            jTable9.getColumnModel().getColumn(3).setResizable(false);
            jTable9.getColumnModel().getColumn(5).setResizable(false);
            jTable9.getColumnModel().getColumn(6).setResizable(false);
        }

        jLabel103.setText("Create username");

        jLabel104.setText("Create password");

        jLabel105.setText("Confirm password");

        jLabel117.setText("Branch name");

        jFormattedTextField48.setEditable(false);
        jFormattedTextField48.setBackground(new java.awt.Color(255, 255, 255));

        jComboBox21.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "Counter Clerk", "Strong Room Operator" }));

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane27, javax.swing.GroupLayout.DEFAULT_SIZE, 975, Short.MAX_VALUE))
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
                            .addComponent(jFormattedTextField9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jFormattedTextField36, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jFormattedTextField37, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jFormattedTextField38, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel104)
                                    .addComponent(jLabel105))
                                .addGap(15, 15, 15)
                                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jPasswordField2, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
                                    .addComponent(jPasswordField1)))
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel103, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel102, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel117, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jComboBox21, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jFormattedTextField48, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
                                    .addComponent(jFormattedTextField39, javax.swing.GroupLayout.Alignment.LEADING))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel47)
                    .addComponent(jFormattedTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel117)
                    .addComponent(jFormattedTextField48, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel98)
                    .addComponent(jFormattedTextField36, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel102)
                    .addComponent(jComboBox21, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox13, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel99)
                    .addComponent(jLabel103)
                    .addComponent(jFormattedTextField39, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jLabel100))
                    .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jFormattedTextField37, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel104)
                        .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel101, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFormattedTextField38, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel105)
                    .addComponent(jPasswordField2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(jScrollPane27, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane10.addTab("Add Users", new javax.swing.ImageIcon(getClass().getResource("/img/ex.png")), jPanel14); // NOI18N

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));

        jTable10.setAutoCreateRowSorter(true);
        jTable10.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Surname", "Gender", "Branch", "Phone number", "ID number", "Position"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable10.setRowHeight(20);
        jTable10.setShowHorizontalLines(false);
        jTable10.setShowVerticalLines(false);
        jScrollPane9.setViewportView(jTable10);
        if (jTable10.getColumnModel().getColumnCount() > 0) {
            jTable10.getColumnModel().getColumn(1).setResizable(false);
            jTable10.getColumnModel().getColumn(2).setResizable(false);
            jTable10.getColumnModel().getColumn(3).setResizable(false);
            jTable10.getColumnModel().getColumn(5).setResizable(false);
            jTable10.getColumnModel().getColumn(6).setResizable(false);
        }

        jLabel48.setText("Name");

        jLabel106.setText("Surname");

        jLabel107.setText("Gender");

        jLabel108.setText("Phone number");

        jLabel109.setText("ID number");

        jComboBox15.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel110.setText("Position");

        jLabel118.setText("Create username");

        jLabel119.setText("Create password");

        jLabel120.setText("Confirm password");

        jFormattedTextField47.setEditable(false);

        jComboBox20.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel49.setText("Select branch");

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 975, Short.MAX_VALUE)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel109, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel108, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE)
                            .addComponent(jLabel107, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel106, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel48, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jFormattedTextField45, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jFormattedTextField43, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jFormattedTextField41, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jFormattedTextField40, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel120, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel119))
                                    .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jLabel110, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel118, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGap(18, 18, 18))
                            .addGroup(jPanel16Layout.createSequentialGroup()
                                .addComponent(jLabel49)
                                .addGap(39, 39, 39)))
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jFormattedTextField47, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jFormattedTextField46)
                                .addComponent(jPasswordField4)
                                .addComponent(jPasswordField3, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jComboBox20, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 349, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel48)
                    .addComponent(jFormattedTextField45, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel49)
                    .addComponent(jFormattedTextField47, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel106)
                    .addComponent(jFormattedTextField43, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel110)
                    .addComponent(jComboBox20, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox15, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel107)
                    .addComponent(jFormattedTextField46, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel118))
                .addGap(11, 11, 11)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jLabel108))
                    .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jFormattedTextField41, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPasswordField4, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel119)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel109, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFormattedTextField40, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel120)
                    .addComponent(jPasswordField3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jTabbedPane10.addTab("Update Users", new javax.swing.ImageIcon(getClass().getResource("/img/rat.png")), jPanel16); // NOI18N

        jPanel35.setBackground(new java.awt.Color(255, 255, 255));

        jTable11.setAutoCreateRowSorter(true);
        jTable11.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Surname", "Gender", "Branch", "Phone number", "ID number", "Position"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable11.setRowHeight(20);
        jTable11.setShowHorizontalLines(false);
        jTable11.setShowVerticalLines(false);
        jScrollPane28.setViewportView(jTable11);
        if (jTable11.getColumnModel().getColumnCount() > 0) {
            jTable11.getColumnModel().getColumn(0).setResizable(false);
            jTable11.getColumnModel().getColumn(1).setResizable(false);
            jTable11.getColumnModel().getColumn(3).setResizable(false);
            jTable11.getColumnModel().getColumn(4).setResizable(false);
            jTable11.getColumnModel().getColumn(5).setResizable(false);
            jTable11.getColumnModel().getColumn(6).setResizable(false);
        }

        javax.swing.GroupLayout jPanel35Layout = new javax.swing.GroupLayout(jPanel35);
        jPanel35.setLayout(jPanel35Layout);
        jPanel35Layout.setHorizontalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 995, Short.MAX_VALUE)
            .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel35Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane28, javax.swing.GroupLayout.DEFAULT_SIZE, 975, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel35Layout.setVerticalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 534, Short.MAX_VALUE)
            .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel35Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane28, javax.swing.GroupLayout.DEFAULT_SIZE, 523, Short.MAX_VALUE)))
        );

        jTabbedPane10.addTab("Delete User", new javax.swing.ImageIcon(getClass().getResource("/img/rat.png")), jPanel35); // NOI18N

        javax.swing.GroupLayout usersLayout = new javax.swing.GroupLayout(users);
        users.setLayout(usersLayout);
        usersLayout.setHorizontalGroup(
            usersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1000, Short.MAX_VALUE)
            .addGroup(usersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jTabbedPane10, javax.swing.GroupLayout.Alignment.TRAILING))
        );
        usersLayout.setVerticalGroup(
            usersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 580, Short.MAX_VALUE)
            .addGroup(usersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jTabbedPane10, javax.swing.GroupLayout.Alignment.TRAILING))
        );

        jPanel1.add(users, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 140, 1000, 580));

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
        fa1.setText(" L. C Received ?");
        fa1.setFocusable(false);
        fa1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fa1ActionPerformed(evt);
            }
        });
        jPanel30.add(fa1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 6, 110, 30));

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
        jPanel30.add(mj1, new org.netbeans.lib.awtextra.AbsoluteConstraints(653, 6, 150, 30));

        mj2.setDateFormatString("y-MM-dd");
        jPanel30.add(mj2, new org.netbeans.lib.awtextra.AbsoluteConstraints(651, 52, 152, 30));

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
        jPanel30.add(jLabel75, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 6, -1, 30));

        fa5.setEditable(true);
        fa5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "Northern region", "Southern region", "Eastern region", "Western region" }));
        fa5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fa5ActionPerformed(evt);
            }
        });
        jPanel30.add(fa5, new org.netbeans.lib.awtextra.AbsoluteConstraints(375, 6, 155, 30));

        jLabel76.setText("Select Branch");
        jPanel30.add(jLabel76, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 52, -1, 30));

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

        jPanel31.add(jScrollPane35, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 975, 343));

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
        jPanel31.add(jLabel131, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 92, 161, 35));

        jLabel132.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/phist.png"))); // NOI18N
        jLabel132.setText("Specify Currency");
        jLabel132.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel132MouseClicked(evt);
            }
        });
        jPanel31.add(jLabel132, new org.netbeans.lib.awtextra.AbsoluteConstraints(325, 92, 155, 35));

        fb1.setBackground(new java.awt.Color(255, 255, 255));
        fb1.setText(" L. C Received ?");
        fb1.setFocusable(false);
        fb1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fb1ActionPerformed(evt);
            }
        });
        jPanel31.add(fb1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 6, 110, 30));

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
        jPanel31.add(fb7, new org.netbeans.lib.awtextra.AbsoluteConstraints(653, 6, 150, 30));

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
        jPanel31.add(jLabel77, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 6, -1, 30));

        fb5.setEditable(true);
        fb5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "Northern region", "Southern region", "Eastern region", "Western region" }));
        fb5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fb5ActionPerformed(evt);
            }
        });
        jPanel31.add(fb5, new org.netbeans.lib.awtextra.AbsoluteConstraints(375, 6, 155, 30));

        jLabel78.setText("Select Branch");
        jPanel31.add(jLabel78, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 52, -1, 30));

        fb6.setEditable(true);
        fb6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
        jPanel31.add(fb6, new org.netbeans.lib.awtextra.AbsoluteConstraints(375, 52, 155, 30));

        fw1.setText("jLabel134");
        jPanel31.add(fw1, new org.netbeans.lib.awtextra.AbsoluteConstraints(861, 20, -1, 0));

        fw2.setText("jLabel163");
        jPanel31.add(fw2, new org.netbeans.lib.awtextra.AbsoluteConstraints(985, 78, 0, 4));

        jTabbedPane8.addTab("Purchases Sales", new javax.swing.ImageIcon(getClass().getResource("/img/ym.png")), jPanel31); // NOI18N

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

        jPanel37.add(jScrollPane36, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 975, 343));

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
        fc1.setText(" L. C Received ?");
        fc1.setFocusable(false);
        fc1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fc1ActionPerformed(evt);
            }
        });
        jPanel37.add(fc1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 6, 110, 30));

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
        jPanel37.add(jLabel79, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 6, -1, 30));

        fc5.setEditable(true);
        fc5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "Northern region", "Southern region", "Eastern region", "Western region" }));
        fc5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fc5ActionPerformed(evt);
            }
        });
        jPanel37.add(fc5, new org.netbeans.lib.awtextra.AbsoluteConstraints(375, 6, 155, 30));

        jLabel80.setText("Select Branch");
        jPanel37.add(jLabel80, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 52, -1, 30));

        fc6.setEditable(true);
        fc6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
        jPanel37.add(fc6, new org.netbeans.lib.awtextra.AbsoluteConstraints(375, 52, 155, 30));

        zm1.setModel(new javax.swing.SpinnerListModel(new String[] {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"}));
        jPanel37.add(zm1, new org.netbeans.lib.awtextra.AbsoluteConstraints(653, 6, 138, 30));

        zm5.setText("jLabel134");
        jPanel37.add(zm5, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 4, -1, 0));

        sym.setModel(new javax.swing.SpinnerListModel(new String[] {"2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030", "2031", "2032", "2033", "2034", "2035", "2036", "2037", "2040", "2050", "2051", "2052", "2053", "2054", "2055", "2056", "2057", "2058", "2059", "2060", "2061", "2062", "2063", "2064", "2065", "2066", "2067", "2068", "2069", "2070", "2071", "2072", "2073", "2073", "2074", "2075", "2076", "2077", "2078", "2079", "2080", "2081", "2082", "2083", "2084", "2085", "2086", "2087", "2088", "2089", "2090", "2091", "2092", "2093", "2094", "2095", "2096", "2097", "2098", "2099"}));
        jPanel37.add(sym, new org.netbeans.lib.awtextra.AbsoluteConstraints(653, 52, 138, 30));

        jLabel144.setText("Select year");
        jPanel37.add(jLabel144, new org.netbeans.lib.awtextra.AbsoluteConstraints(565, 52, 70, 30));

        jTabbedPane8.addTab("Purchases Sales", new javax.swing.ImageIcon(getClass().getResource("/img/td.png")), jPanel37); // NOI18N

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

        jPanel38.add(jScrollPane37, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 975, 343));

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
        fd1.setText(" L. C Received ?");
        fd1.setFocusable(false);
        fd1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fd1ActionPerformed(evt);
            }
        });
        jPanel38.add(fd1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 6, 110, 30));

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
        jPanel38.add(jLabel81, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 6, -1, 30));

        fd5.setEditable(true);
        fd5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "Northern region", "Southern region", "Eastern region", "Western region" }));
        fd5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fd5ActionPerformed(evt);
            }
        });
        jPanel38.add(fd5, new org.netbeans.lib.awtextra.AbsoluteConstraints(375, 6, 155, 30));

        jLabel82.setText("Select Branch");
        jPanel38.add(jLabel82, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 52, -1, 30));

        fd6.setEditable(true);
        fd6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
        jPanel38.add(fd6, new org.netbeans.lib.awtextra.AbsoluteConstraints(375, 52, 155, 30));

        fd7.setModel(new javax.swing.SpinnerListModel(new String[] {"2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030", "2031", "2032", "2033", "2034", "2035", "2036", "2037", "2040", "2050", "2051", "2052", "2053", "2054", "2055", "2056", "2057", "2058", "2059", "2060", "2061", "2062", "2063", "2064", "2065", "2066", "2067", "2068", "2069", "2070", "2071", "2072", "2073", "2073", "2074", "2075", "2076", "2077", "2078", "2079", "2080", "2081", "2082", "2083", "2084", "2085", "2086", "2087", "2088", "2089", "2090", "2091", "2092", "2093", "2094", "2095", "2096", "2097", "2098", "2099"}));
        jPanel38.add(fd7, new org.netbeans.lib.awtextra.AbsoluteConstraints(653, 6, 94, 30));

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

        jPanel39.add(jScrollPane38, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 975, 343));

        jLabel92.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/excel.png"))); // NOI18N
        jLabel92.setText("Save as CSV File");
        jLabel92.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel92MouseClicked(evt);
            }
        });
        jPanel39.add(jLabel92, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 92, -1, 39));

        jLabel134.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/print.png"))); // NOI18N
        jLabel134.setText("Print / Save as XPS");
        jLabel134.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel134MouseClicked(evt);
            }
        });
        jPanel39.add(jLabel134, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 92, -1, 39));

        py1.setBackground(new java.awt.Color(255, 255, 255));
        py1.setText(" L. C Used ?");
        py1.setFocusable(false);
        py1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                py1ActionPerformed(evt);
            }
        });
        jPanel39.add(py1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 6, 99, 30));

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

        py7.setDateFormatString("y-MM-dd");
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
        jPanel39.add(jLabel154, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 92, -1, 39));

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

        jLabel175.setText("Select Region");
        jPanel39.add(jLabel175, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 6, -1, 30));

        py5.setEditable(true);
        py5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "Northern region", "Southern region", "Eastern region", "Western region" }));
        py5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                py5ActionPerformed(evt);
            }
        });
        jPanel39.add(py5, new org.netbeans.lib.awtextra.AbsoluteConstraints(375, 6, 155, 30));

        jLabel155.setText("Select Branch");
        jPanel39.add(jLabel155, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 52, -1, 30));

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
        jPanel39.add(jLabel156, new org.netbeans.lib.awtextra.AbsoluteConstraints(325, 92, 151, 39));

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
        wy_table.setRowHeight(20);
        wy_table.setShowHorizontalLines(false);
        wy_table.setShowVerticalLines(false);
        wy_table.getTableHeader().setReorderingAllowed(false);
        jScrollPane39.setViewportView(wy_table);

        jPanel40.add(jScrollPane39, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 975, 343));

        jLabel158.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/excel.png"))); // NOI18N
        jLabel158.setText("Save as CSV File");
        jLabel158.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel158MouseClicked(evt);
            }
        });
        jPanel40.add(jLabel158, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 92, -1, 39));

        jLabel159.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/print.png"))); // NOI18N
        jLabel159.setText("Print / Save as XPS");
        jLabel159.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel159MouseClicked(evt);
            }
        });
        jPanel40.add(jLabel159, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 92, -1, 39));

        jLabel160.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/phist.png"))); // NOI18N
        jLabel160.setText("Specify Currency");
        jPanel40.add(jLabel160, new org.netbeans.lib.awtextra.AbsoluteConstraints(325, 92, -1, 39));

        wy1.setBackground(new java.awt.Color(255, 255, 255));
        wy1.setText(" L. C Used ?");
        wy1.setFocusable(false);
        wy1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wy1ActionPerformed(evt);
            }
        });
        jPanel40.add(wy1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 6, 99, 30));

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
        jPanel40.add(jLabel162, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 92, -1, 39));

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
        jPanel40.add(jLabel163, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 6, -1, 30));

        wy5.setEditable(true);
        wy5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "", "Northern region", "Southern region", "Eastern region", "Western region" }));
        wy5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wy5ActionPerformed(evt);
            }
        });
        jPanel40.add(wy5, new org.netbeans.lib.awtextra.AbsoluteConstraints(375, 6, 155, 30));

        jLabel164.setText("Select Branch");
        jPanel40.add(jLabel164, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 52, -1, 30));

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

        jPanel42.add(jScrollPane40, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 975, 343));

        jLabel167.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/excel.png"))); // NOI18N
        jLabel167.setText("Save as CSV File");
        jLabel167.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel167MouseClicked(evt);
            }
        });
        jPanel42.add(jLabel167, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 92, -1, 39));

        jLabel168.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/print.png"))); // NOI18N
        jLabel168.setText("Print / Save as XPS");
        jLabel168.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel168MouseClicked(evt);
            }
        });
        jPanel42.add(jLabel168, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 92, 150, 39));

        jLabel169.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/phist.png"))); // NOI18N
        jLabel169.setText("Specify Currency");
        jPanel42.add(jLabel169, new org.netbeans.lib.awtextra.AbsoluteConstraints(325, 92, -1, 39));

        zh1.setBackground(new java.awt.Color(255, 255, 255));
        zh1.setText(" L. C Used ?");
        zh1.setFocusable(false);
        zh1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zh1ActionPerformed(evt);
            }
        });
        jPanel42.add(zh1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 6, 99, 30));

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
        jPanel42.add(jLabel171, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 92, 111, 39));

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
        jPanel42.add(jLabel172, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 6, -1, 30));

        zh5.setEditable(true);
        zh5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "", "Northern region", "Southern region", "Eastern region", "Western region" }));
        zh5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zh5ActionPerformed(evt);
            }
        });
        jPanel42.add(zh5, new org.netbeans.lib.awtextra.AbsoluteConstraints(375, 6, 155, 30));

        jLabel173.setText("Select Branch");
        jPanel42.add(jLabel173, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 52, -1, 30));

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

        jPanel53.add(jScrollPane41, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 975, 343));

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
        jPanel53.add(jLabel176, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 92, -1, 39));

        jLabel177.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/print.png"))); // NOI18N
        jLabel177.setText("Print / Save as XPS");
        jLabel177.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel177MouseClicked(evt);
            }
        });
        jPanel53.add(jLabel177, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 92, -1, 39));

        jLabel178.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/phist.png"))); // NOI18N
        jLabel178.setText("Specify Currency");
        jPanel53.add(jLabel178, new org.netbeans.lib.awtextra.AbsoluteConstraints(325, 92, -1, 39));

        px1.setBackground(new java.awt.Color(255, 255, 255));
        px1.setText(" L. C Used ?");
        px1.setFocusable(false);
        px1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                px1ActionPerformed(evt);
            }
        });
        jPanel53.add(px1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 6, 99, 30));

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
        jPanel53.add(jLabel180, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 92, -1, 39));

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
        jPanel53.add(jLabel181, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 6, -1, 30));

        px5.setEditable(true);
        px5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "Northern region", "Southern region", "Eastern region", "Western region" }));
        px5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                px5ActionPerformed(evt);
            }
        });
        jPanel53.add(px5, new org.netbeans.lib.awtextra.AbsoluteConstraints(375, 6, 155, 30));

        jLabel182.setText("Select Branch");
        jPanel53.add(jLabel182, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 52, -1, 30));

        px6.setEditable(true);
        px6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
        jPanel53.add(px6, new org.netbeans.lib.awtextra.AbsoluteConstraints(375, 52, 155, 30));

        px7.setModel(new javax.swing.SpinnerListModel(new String[] {"2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030", "2031", "2032", "2033", "2034", "2035", "2036", "2037", "2040", "2050", "2051", "2052", "2053", "2054", "2055", "2056", "2057", "2058", "2059", "2060", "2061", "2062", "2063", "2064", "2065", "2066", "2067", "2068", "2069", "2070", "2071", "2072", "2073", "2073", "2074", "2075", "2076", "2077", "2078", "2079", "2080", "2081", "2082", "2083", "2084", "2085", "2086", "2087", "2088", "2089", "2090", "2091", "2092", "2093", "2094", "2095", "2096", "2097", "2098", "2099"}));
        jPanel53.add(px7, new org.netbeans.lib.awtextra.AbsoluteConstraints(649, 6, 108, 30));

        jTabbedPane9.addTab("Annual Sales", new javax.swing.ImageIcon(getClass().getResource("/img/mm.png")), jPanel53); // NOI18N

        purchases_rep.add(jTabbedPane9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 540));

        jTabbedPane7.addTab("Sales Reports", new javax.swing.ImageIcon(getClass().getResource("/img/up32.png")), purchases_rep); // NOI18N

        profit_and_loses.setBackground(new java.awt.Color(255, 255, 255));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel184.setText("Select Trading Month");

        prf1.setEditable(true);
        prf1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" }));

        prf2.setEditable(true);
        prf2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prf2ActionPerformed(evt);
            }
        });

        jLabel185.setText("Currency Received");

        jLabel186.setText("Currency Sold");

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

        jLabel193.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel193.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-calculator-32.png"))); // NOI18N
        jLabel193.setText("Calculate Profit / Losses");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel193, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addComponent(jLabel193)
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
                                .addComponent(jLabel184)
                                .addGap(18, 18, 18)
                                .addComponent(prf1, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel185, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(0, 63, Short.MAX_VALUE)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(jPanel8Layout.createSequentialGroup()
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel186, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(jLabel184)
                    .addComponent(prf1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel185)
                    .addComponent(prf2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel186)
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

        javax.swing.GroupLayout profit_and_losesLayout = new javax.swing.GroupLayout(profit_and_loses);
        profit_and_loses.setLayout(profit_and_losesLayout);
        profit_and_losesLayout.setHorizontalGroup(
            profit_and_losesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(profit_and_losesLayout.createSequentialGroup()
                .addContainerGap(151, Short.MAX_VALUE)
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

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));
        jPanel15.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel21.setBackground(new java.awt.Color(255, 255, 255));
        jPanel21.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "What Regions or Branches Holds", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

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

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jLabel129)
                        .addGap(53, 53, 53)
                        .addComponent(acc_currency, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addComponent(jLabel136)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(acc_total, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(89, Short.MAX_VALUE))
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(acc_currency, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                    .addComponent(jLabel129))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(acc_total, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                    .addComponent(jLabel136))
                .addGap(39, 39, 39))
        );

        jPanel15.add(jPanel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(224, 84, 480, 290));

        opb1.setBackground(new java.awt.Color(255, 255, 255));
        opb1.setText("Regions");
        jPanel15.add(opb1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 10, 170, -1));

        opb2.setBackground(new java.awt.Color(255, 255, 255));
        opb2.setText("Branches");
        jPanel15.add(opb2, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 10, 160, -1));

        opb4.setEditable(true);
        jPanel15.add(opb4, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 40, 170, 30));

        opb3.setEditable(true);
        opb3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "Northern region", "Southern region", "Eastern region", "Western region" }));
        opb3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opb3ActionPerformed(evt);
            }
        });
        jPanel15.add(opb3, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 40, 170, 30));

        javax.swing.GroupLayout profit_and_loses2Layout = new javax.swing.GroupLayout(profit_and_loses2);
        profit_and_loses2.setLayout(profit_and_loses2Layout);
        profit_and_loses2Layout.setHorizontalGroup(
            profit_and_loses2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 995, Short.MAX_VALUE)
            .addGroup(profit_and_loses2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(profit_and_loses2Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, 995, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        profit_and_loses2Layout.setVerticalGroup(
            profit_and_loses2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 534, Short.MAX_VALUE)
            .addGroup(profit_and_loses2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(profit_and_loses2Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, 534, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        jPanel49.setBackground(new java.awt.Color(255, 255, 255));
        jPanel49.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel51.setBackground(new java.awt.Color(255, 255, 255));
        jPanel51.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "What Regions or Branches Holds", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

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
        jScrollPane34.setViewportView(tw_table);

        jLabel165.setText("Grand Total for the selected currency");

        javax.swing.GroupLayout jPanel51Layout = new javax.swing.GroupLayout(jPanel51);
        jPanel51.setLayout(jPanel51Layout);
        jPanel51Layout.setHorizontalGroup(
            jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel51Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel51Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jLabel157)
                        .addGap(53, 53, 53)
                        .addComponent(tw5, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel51Layout.createSequentialGroup()
                        .addComponent(jLabel165)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tw6, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(89, Short.MAX_VALUE))
            .addGroup(jPanel51Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane34, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel51Layout.setVerticalGroup(
            jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel51Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tw5, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                    .addComponent(jLabel157))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane34, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tw6, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                    .addComponent(jLabel165))
                .addGap(39, 39, 39))
        );

        jPanel49.add(jPanel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(224, 84, 480, 290));

        tw4.setEditable(true);
        jPanel49.add(tw4, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 40, 170, 30));

        tw3.setEditable(true);
        tw3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "Northern region", "Southern region", "Eastern region", "Western region" }));
        tw3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tw3ActionPerformed(evt);
            }
        });
        jPanel49.add(tw3, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 40, 170, 30));

        javax.swing.GroupLayout profit_and_loses4Layout = new javax.swing.GroupLayout(profit_and_loses4);
        profit_and_loses4.setLayout(profit_and_loses4Layout);
        profit_and_loses4Layout.setHorizontalGroup(
            profit_and_loses4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 995, Short.MAX_VALUE)
            .addGroup(profit_and_loses4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(profit_and_loses4Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel49, javax.swing.GroupLayout.PREFERRED_SIZE, 995, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        profit_and_loses4Layout.setVerticalGroup(
            profit_and_loses4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 534, Short.MAX_VALUE)
            .addGroup(profit_and_loses4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(profit_and_loses4Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel49, javax.swing.GroupLayout.PREFERRED_SIZE, 534, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jTabbedPane7.addTab("Initial Branch A/C Deposits", new javax.swing.ImageIcon(getClass().getResource("/img/icons8-bank-building-32.png")), profit_and_loses4); // NOI18N

        reports.add(jTabbedPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 580));

        jPanel1.add(reports, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 140, 1000, 580));

        simult.setBackground(new java.awt.Color(255, 255, 255));
        simult.setBorder(javax.swing.BorderFactory.createTitledBorder("Please fill in the fields below"));

        dv1.setEditable(false);

        jLabel37.setText("Base currency");

        dv2.setEditable(false);

        jLabel38.setText("Quote currency");

        dv3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                dv3KeyTyped(evt);
            }
        });

        jLabel39.setText("Buying rate");

        dv4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                dv4KeyTyped(evt);
            }
        });

        jLabel40.setText("Selling rate");

        jLabel58.setText("Base currency");

        jLabel59.setText("Quote currency");

        sv2.setEditable(false);

        sv1.setEditable(false);

        jLabel60.setText("Buying rate");

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

        jLabel72.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-return-32.png"))); // NOI18N
        jLabel72.setText("Return ");
        jLabel72.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel72MouseClicked(evt);
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
                            .addComponent(jLabel37, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel38, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel39, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel40, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addGroup(simultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dv4, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dv3, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dv2, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dv1, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(simultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel58, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel59, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel60, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel68, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addGroup(simultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(sv4, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sv3, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sv2, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sv1, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, simultLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel72, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                            .addComponent(jLabel37))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(simultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(dv2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel38))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(simultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(dv3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel39))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(simultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(dv4, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel40)))
                    .addGroup(simultLayout.createSequentialGroup()
                        .addGroup(simultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(sv1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel58))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(simultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(sv2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel59))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(simultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(sv3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel60))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(simultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(sv4, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel68))))
                .addGap(33, 33, 33)
                .addGroup(simultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel69, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel72, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 66, Short.MAX_VALUE))
        );

        jPanel1.add(simult, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 200, 550, 290));

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

                JOptionPane.showMessageDialog(rootPane, "You have configured exchange rates against the base currency", "Base currency not available", JOptionPane.WARNING_MESSAGE);
            }

        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
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

    private void jLabel51MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel51MouseClicked
        DefaultTableModel dm = (DefaultTableModel) c_table.getModel();
        dm.setNumRows(0);
        DefaultTableModel dm1 = (DefaultTableModel) dy_table.getModel();
        dm1.setNumRows(0);
DB_USER = uname.getText();
        DB_PAS = upass.getText();
        String xz = "true";

        String query = "SELECT currency_name,short_code,base_currency,currency_id FROM currencies where base_currency='" + xz + "'";
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
                    String d = rs.getString("base_currency");

                    fdw.setText("false");
                    c3.setEnabled(false);

                }
            }
            if (bool == false) {

                fdw.setText("false");
            }

        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
        }

        String query1 = "SELECT currency_name,short_code,base_currency,currency_id FROM currencies";
        try {
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
            stm = con.createStatement();
            rs = stm.executeQuery(query1);
            boolean bool = rs.isBeforeFirst();
            while (rs.next()) {
                if (bool == true) {
                    String a = rs.getString("currency_name");
                    String b = rs.getString("short_code");
                    String c = rs.getString("base_currency");
                    String d = rs.getString("currency_id");
                    dm.addRow(new Object[]{a, b, c, d});
                    dm1.addRow(new Object[]{a, b, c, d});
                    fdw.setText("false");
                    //c3.setEnabled(false);

                }
            }
            if (bool == false) {

                fdw.setText("false");
            }

        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
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
    }//GEN-LAST:event_jLabel51MouseClicked

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
DB_USER = uname.getText();
        DB_PAS = upass.getText();
        if (c1.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter currency name", "Icomplete data", JOptionPane.ERROR_MESSAGE);
        }
        if (c2.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Enter currency short code eg USD", "Icomplete data", JOptionPane.ERROR_MESSAGE);
        }
        if (!c1.getText().isEmpty() && !c2.getText().isEmpty()) {

            String xz = "false";

            String query = "SELECT currency_name,short_code,base_currency FROM currencies WHERE currency_name='" + c1.getText() + "' or short_code='" + c2.getText() + "'";
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

                        if (c1.getText().equalsIgnoreCase(a)) {
                            JOptionPane.showMessageDialog(null, "Currency already exist in the system", "Duplication check", JOptionPane.ERROR_MESSAGE);
                        }
                        if (c2.getText().equalsIgnoreCase(b)) {
                            JOptionPane.showMessageDialog(null, "This short code is of another currency in the system", "Duplication check", JOptionPane.ERROR_MESSAGE);
                        }

                    }
                }
                if (bool == false) {
                    JOptionPane.showMessageDialog(null, "The table is empty", "Duplication check", JOptionPane.ERROR_MESSAGE);

                    try {
                        con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                        pst = con.prepareStatement("insert into currencies values(?,?,?)");
                        pst.setString(1, c1.getText());
                        pst.setString(2, c2.getText());
                        pst.setString(3, fdw.getText());

                        int i = pst.executeUpdate();
                        if (i > 0) {
                            JOptionPane.showMessageDialog(null, "You have successfully added a currency into the system" + " " + " ", " Providence says....", JOptionPane.INFORMATION_MESSAGE);
                            c1.setText("");
                            c2.setText("");
                            //fdw.setText("false");
                            c3.setSelected(false);
                            Finance_Dep fd = new Finance_Dep();
                            fd.setVisible(true);
                            this.dispose();
                        }
                    } catch (SQLException ev) {
                        JOptionPane.showMessageDialog(null, ev.getMessage());

                    }
                }

            } catch (ClassNotFoundException | SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Something went wrong", JOptionPane.ERROR_MESSAGE);
            }

        }
    }//GEN-LAST:event_jLabel5MouseClicked

    private void tstMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tstMouseClicked
        if (c3.isSelected()) {
            JOptionPane.showMessageDialog(null, "selected", "System Error", JOptionPane.ERROR_MESSAGE);

        }
        if (!c3.isSelected()) {
            JOptionPane.showMessageDialog(null, "null", "System Error", JOptionPane.ERROR_MESSAGE);

        }
    }//GEN-LAST:event_tstMouseClicked

    private void c3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_c3ActionPerformed
        if (c3.isSelected()) {
            fdw.setText("true");
        }
        if (!c3.isSelected()) {
            fdw.setText("false");
        }
    }//GEN-LAST:event_c3ActionPerformed

    private void jLabel53MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel53MouseClicked
        DefaultTableModel dm = (DefaultTableModel) bal_table.getModel();
        dm.setNumRows(0);
        DefaultComboBoxModel cb = new DefaultComboBoxModel();
        b1.setModel(cb);
        DefaultComboBoxModel cb1 = new DefaultComboBoxModel();
        b2.setModel(cb1);
DB_USER = uname.getText();
        DB_PAS = upass.getText();
        String query = "SELECT branch,currency,denominations,no_denominations,total FROM branch_accounts";
        String querie = "SELECT short_code FROM currencies";
        String query1 = "SELECT region_name,branch_name,area_code,description,created_by,branch_id FROM branches";
        try {
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
            stm = con.createStatement();

            rs = stm.executeQuery(query);
            boolean bool = rs.isBeforeFirst();
            while (rs.next()) {
                if (bool == true) {
                    String a = rs.getString("branch");
                    String b = rs.getString("currency");
                    float c = rs.getFloat("denominations");
                    int d = rs.getInt("no_denominations");
                    float e = rs.getFloat("total");
                    dm.addRow(new Object[]{a, b, c, d, e});
                }
            }

            rs = stm.executeQuery(query1);
            boolean boolm = rs.isBeforeFirst();
            while (rs.next()) {
                if (boolm == true) {
                    String b = rs.getString("branch_name");
                    b1.addItem("");
                    b1.addItem(b);
                }
            }

            rs = stm.executeQuery(querie);
            boolean bool2 = rs.isBeforeFirst();
            while (rs.next()) {
                if (bool2 == true) {
                    String b = rs.getString("short_code");
                    b2.addItem("");
                    b2.addItem(b);
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
        }

        rates.setVisible(false);
        reports.setVisible(false);
        users.setVisible(false);
        branches.setVisible(false);
        operations.setVisible(false);

        currencies.setVisible(false);
        //opening_balances.setVisible(false);
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
    }//GEN-LAST:event_jLabel53MouseClicked

    private void dy_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dy_tableMouseClicked
        DefaultTableModel model = (DefaultTableModel) dy_table.getModel();
        did.setText(model.getValueAt(dy_table.getSelectedRow(), 3).toString());
        ddname.setText(model.getValueAt(dy_table.getSelectedRow(), 0).toString());

    }//GEN-LAST:event_dy_tableMouseClicked

    private void dy_tableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dy_tableKeyPressed
        int xv = Integer.parseInt(did.getText());
        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        DefaultTableModel mode = (DefaultTableModel) dy_table.getModel();
        if (dy_table.getSelectedRow() == -1) {
            if (dy_table.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "The table is empty", "Providence says....", JOptionPane.WARNING_MESSAGE);

            } else {
                JOptionPane.showMessageDialog(null, " You must select a currency  to remove", "Providence says....", JOptionPane.WARNING_MESSAGE);
            }
        } else {

            int input = JOptionPane.showConfirmDialog(null, " Are you sure that you want to remove " + ddname.getText() + " " + " " + "from the system ?", "Providence says....", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (input == JOptionPane.NO_OPTION) {

            }
            if (input == JOptionPane.YES_OPTION) {
                mode.removeRow(dy_table.getSelectedRow());
                SQLRun objSQLRun = new SQLRun();
                String sql = "DELETE FROM currencies WHERE currency_id='" + xv + "'";

                int deleted = objSQLRun.sqlUpdate(sql);
                //getSum();
                if (deleted > 0) {
                    JOptionPane.showMessageDialog(null, " Curreny removed successfully from the system", "Povidence says....", JOptionPane.INFORMATION_MESSAGE);
                    did.setText("");
                    Finance_Dep fd = new Finance_Dep();
                    fd.setVisible(true);
                    this.dispose();
                } else {
                    if (did.getText() == null) {

                    } else {
                        JOptionPane.showMessageDialog(null, "Currency with an ID :" + did.getText() + " does not exist", "Providence says...", JOptionPane.ERROR_MESSAGE);

                    }
                }
            }
        }
    }//GEN-LAST:event_dy_tableKeyPressed

    private void cy3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cy3ActionPerformed
        if (cy3.isSelected()) {
            dec.setText("true");
        }
        if (!cy3.isSelected()) {
            dec.setText("false");
        }
    }//GEN-LAST:event_cy3ActionPerformed

    private void c_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_c_tableMouseClicked
        DefaultTableModel model = (DefaultTableModel) c_table.getModel();
        cy_id.setText(model.getValueAt(c_table.getSelectedRow(), 3).toString());
        cn.setText(model.getValueAt(c_table.getSelectedRow(), 0).toString());
        cy1.setText(model.getValueAt(c_table.getSelectedRow(), 0).toString());
        cy2.setText(model.getValueAt(c_table.getSelectedRow(), 1).toString());
    }//GEN-LAST:event_c_tableMouseClicked

    private void jLabel54MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel54MouseClicked
        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        int input = JOptionPane.showConfirmDialog(null, " Are you sure that you want to update the currency you selected ?", "Providence says....", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (input == JOptionPane.NO_OPTION) {

        }
        int v = Integer.parseInt(cy_id.getText());
        if (input == JOptionPane.YES_OPTION) {

            String query = "SELECT currency_name,short_code,base_currency FROM currencies where base_currency='" + true + "'";
            try {
                Class.forName(DB_DRIVER);
                con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                stm = con.createStatement();
                rs = stm.executeQuery(query);
                boolean bool = rs.isBeforeFirst();
                while (rs.next()) {
                    if (bool == true) {
                        SQLRun run = new SQLRun();
                        String sqlq = "Update currencies set base_currency='" + false + "'  where base_currency='" + true + "'";
                        int updated = run.sqlUpdate(sqlq);
                        if (updated > 0) {
                            SQLRun run1 = new SQLRun();
                            String sql1 = "Update currencies set currency_name='" + cy1.getText() + "' , short_code='" + cy2.getText() + "' , base_currency='" + dec.getText() + "'  where currency_id='" + v + "'";
                            int updated1 = run1.sqlUpdate(sql1);
                            if (updated1 > 0) {
                                JOptionPane.showMessageDialog(null, "You have successfully currency info ", "Currency Info Update Notification", JOptionPane.INFORMATION_MESSAGE);

                            }
                            //JOptionPane.showMessageDialog(null, "You have successfully currency info ","Currency Info Update Notification",JOptionPane.INFORMATION_MESSAGE); 

                        }
                    }
                }
                if (bool == false) {
                    SQLRun run = new SQLRun();
                    String sqlq = "Update currencies set currency_name='" + cy1.getText() + "' , short_code='" + cy2.getText() + "' , base_currency='" + dec.getText() + "'  where currency_id='" + v + "'";
                    int updated = run.sqlUpdate(sqlq);
                    if (updated > 0) {
                        JOptionPane.showMessageDialog(null, "You have successfully currency info ", "Currency Info Update Notification", JOptionPane.INFORMATION_MESSAGE);

                    }
                }

            } catch (ClassNotFoundException | SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Something went wrong", JOptionPane.ERROR_MESSAGE);
            }

        }
    }//GEN-LAST:event_jLabel54MouseClicked

    private void jLabel52MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel52MouseClicked
        DefaultTableModel dm = (DefaultTableModel) denu_table.getModel();
        dm.setNumRows(0);
        DefaultTableModel dm1 = (DefaultTableModel) dend_table.getModel();
        dm1.setNumRows(0);
DB_USER = uname.getText();
        DB_PAS = upass.getText();
        String query = "SELECT currency,denomination_value,denomination_id FROM denominations";
        try {
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
            stm = con.createStatement();
            rs = stm.executeQuery(query);
            boolean bool = rs.isBeforeFirst();
            while (rs.next()) {
                if (bool == true) {
                    String a = rs.getString("currency");
                    String b = rs.getString("denomination_value");
                    String c = rs.getString("denomination_id");
                    dm.addRow(new Object[]{a, b});
                    dm1.addRow(new Object[]{a, b, c});
                }
            }
            if (bool == false) {

            }

        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
        }
        String query1 = "SELECT currency_name,short_code FROM currencies";

        try {
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
            stm = con.createStatement();
            rs = stm.executeQuery(query1);
            boolean bool = rs.isBeforeFirst();
            while (rs.next()) {
                if (bool == true) {
                    String a = rs.getString("currency_name");
                    String b = rs.getString("short_code");

                    denom.addItem(b);
                }
            }
            if (bool == false) {

                fdw.setText("false");
            }

        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
        }

        rates.setVisible(false);
        reports.setVisible(false);
        users.setVisible(false);
        branches.setVisible(false);
        operations.setVisible(false);

        currencies.setVisible(false);
        opening_balances.setVisible(false);
        //denominations.setVisible(false);
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
    }//GEN-LAST:event_jLabel52MouseClicked

    private void denvKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_denvKeyPressed
DB_USER = uname.getText();
        DB_PAS = upass.getText();
        int key = evt.getKeyCode();
        if (key == 10) {
            if (denv.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter the denomination value", "Icompletion data", JOptionPane.ERROR_MESSAGE);
            }
            if (!denv.getText().isEmpty()) {
                if (denv.getText().contains("$") && denv.getText().contains("|")
                        && denv.getText().contains("?") && denv.getText().contains("/")
                        && denv.getText().contains(">") && denv.getText().contains("<")
                        && denv.getText().contains(",") && denv.getText().contains("\\")
                        && denv.getText().contains(":") && denv.getText().contains(";")
                        && denv.getText().contains("'") && denv.getText().contains("]")
                        && denv.getText().contains("[") && denv.getText().contains("}")
                        && denv.getText().contains("{") && denv.getText().contains("=")
                        && denv.getText().contains("+") && denv.getText().contains("-")
                        && denv.getText().contains("_") && denv.getText().contains(")")
                        && denv.getText().contains("(") && denv.getText().contains("*")
                        && denv.getText().contains("&") && denv.getText().contains("^")
                        && denv.getText().contains("%") && denv.getText().contains("#")
                        && denv.getText().contains("@") && denv.getText().contains("!")
                        && denv.getText().contains("~")) {
                    JOptionPane.showMessageDialog(null, "Please don't enter special character ", "Incorrect data", JOptionPane.ERROR_MESSAGE);
                }

                if (!denv.getText().contains("$") && !denv.getText().contains("|")
                        && !denv.getText().contains("?") && !denv.getText().contains("/")
                        && !denv.getText().contains(">") && !denv.getText().contains("<")
                        && !denv.getText().contains(",") && !denv.getText().contains("\\")
                        && !denv.getText().contains(":") && !denv.getText().contains(";")
                        && !denv.getText().contains("'") && !denv.getText().contains("]")
                        && !denv.getText().contains("[") && !denv.getText().contains("}")
                        && !denv.getText().contains("{") && !denv.getText().contains("=")
                        && !denv.getText().contains("+") && !denv.getText().contains("-")
                        && !denv.getText().contains("_") && !denv.getText().contains(")")
                        && !denv.getText().contains("(") && !denv.getText().contains("*")
                        && !denv.getText().contains("&") && !denv.getText().contains("^")
                        && !denv.getText().contains("%") && !denv.getText().contains("#")
                        && !denv.getText().contains("@") && !denv.getText().contains("!")
                        && !denv.getText().contains("~")) {
                    double asd = Double.parseDouble(denv.getText());
                    String query = "SELECT currency,denomination_value,denomination_id FROM denominations where currency='" + denom.getSelectedItem().toString() + "' and denomination_value='" + asd + "'";
                    try {
                        Class.forName(DB_DRIVER);
                        con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                        stm = con.createStatement();
                        rs = stm.executeQuery(query);
                        boolean bool = rs.isBeforeFirst();
                        while (rs.next()) {
                            if (bool == true) {
                                String a = rs.getString("currency");
                                String b = rs.getString("denomination_value");
                                JOptionPane.showMessageDialog(rootPane, "Denomination alreday exist in the system", "Duplication error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        if (bool == false) {
                            try {
                                Class.forName(DB_DRIVER);

                                con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                pst = con.prepareStatement("insert into denominations values(?,?)");
                                pst.setString(1, denom.getSelectedItem().toString());
                                pst.setDouble(2, asd);
                                //pst.setString(3, fdw.getText());

                                int i = pst.executeUpdate();
                                if (i > 0) {
                                    JOptionPane.showMessageDialog(null, "You have successfully added a denominations into the system" + " " + " ", " Providence says....", JOptionPane.INFORMATION_MESSAGE);
                                    DefaultTableModel dm = (DefaultTableModel) den_table.getModel();
                                    dm.addRow(new Object[]{denom.getSelectedItem().toString(), asd});
                                    denv.setText("");
                                    //c2.setText("");
                                    //fdw.setText("false");
                                    //c3.setSelected(false);
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
    }//GEN-LAST:event_denvKeyPressed

    private void rt5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rt5KeyPressed

    }//GEN-LAST:event_rt5KeyPressed

    private void ru6KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ru6KeyTyped
        char c = evt.getKeyChar();
        if (Character.isAlphabetic(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_ru6KeyTyped

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

    private void b2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b2ActionPerformed
        DefaultComboBoxModel cb1 = new DefaultComboBoxModel();
        b4.setModel(cb1);
        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        if (b2.getSelectedItem().equals(" ")) {

        }
        if (!b2.getSelectedItem().equals("")) {

            String querie = "SELECT denomination_value FROM denominations where currency='" + b2.getSelectedItem().toString() + "'";

            try {
                Class.forName(DB_DRIVER);
                con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                stm = con.createStatement();

                rs = stm.executeQuery(querie);
                boolean bool2 = rs.isBeforeFirst();
                while (rs.next()) {
                    if (bool2 == true) {
                        String b = rs.getString("denomination_value");
                        b4.addItem(b);
                        b5.setEditable(true);
                    }
                }
                if (bool2 == false) {
                    JOptionPane.showMessageDialog(null, "Please be advised that you haven't add any denominatioon into the system for :" + b2.getSelectedItem().toString() + "\n1. Go to Denominations & select " + b2.getSelectedItem().toString() + "\n2. Enter the denomination value eg 5 for " + b2.getSelectedItem().toString() + " " + "$ 5", "Some information missing", JOptionPane.ERROR_MESSAGE);
                    b5.setEditable(false);
                }
            } catch (ClassNotFoundException | SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    }//GEN-LAST:event_b2ActionPerformed

    private void b3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_b3KeyTyped
        char c = evt.getKeyChar();
        if (!Character.isDigit(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_b3KeyTyped

    private void b3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_b3KeyPressed
        int key = evt.getKeyCode();
        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        if (key == 10) {
            if (b3.getText().isEmpty()) {
                JOptionPane.showMessageDialog(rootPane, "How much do you want to send to :" + b1.getSelectedItem().toString() + " ", "Some information missing", JOptionPane.ERROR_MESSAGE);
            }
            if (!b3.getText().isEmpty()) {
                int input = JOptionPane.showConfirmDialog(null, "Want to send :" + b2.getSelectedItem().toString() + " " + "$" + b3.getText() + " " + "to " + b1.getSelectedItem().toString(), "Providence says....", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (input == JOptionPane.NO_OPTION) {

                }
                if (input == JOptionPane.YES_OPTION) {
                    b3.setEditable(false);

                }
            }
        }
    }//GEN-LAST:event_b3KeyPressed

    private void b5KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_b5KeyTyped
        char c = evt.getKeyChar();
        if (!Character.isDigit(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_b5KeyTyped

    private void b5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_b5KeyPressed
        java.util.Date date = new java.util.Date();
        long t = date.getTime();
        java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(t);
        java.sql.Date sqlDate = new java.sql.Date(t);
DB_USER = uname.getText();
        DB_PAS = upass.getText();
        int key = evt.getKeyCode();
        if (key == 10) {
            if (b5.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Enter the number of denominations of " + b2.getSelectedItem().toString() + " " + b4.getSelectedItem().toString() + " " + "you want to send to " + b1.getSelectedItem().toString(), "Some information missing", JOptionPane.ERROR_MESSAGE);
            }
            if (b4.getSelectedItem().equals("")) {
                JOptionPane.showMessageDialog(null, "Select the denomination of " + b2.getSelectedItem().toString() + " " + "you want to send to " + b1.getSelectedItem().toString(), "Some information missing", JOptionPane.ERROR_MESSAGE);
            }
            if (b3.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Enter the number of denominations of " + b2.getSelectedItem().toString() + " " + b4.getSelectedItem().toString() + " " + "you want to send to " + b1.getSelectedItem().toString(), "Some information missing", JOptionPane.ERROR_MESSAGE);
            }
            if (!b5.getText().isEmpty() && !b3.getText().isEmpty() && !b4.getSelectedItem().equals("")) {
                int cg = Integer.parseInt(b5.getText());
                float a = Float.parseFloat(b3.getText());
                float b = Float.parseFloat(b4.getSelectedItem().toString());
                float c = Float.parseFloat(b5.getText());
                float d = Float.parseFloat(iter.getText());
                float e = d + (b * c);
                float g = b * c;

                if (a >= e) {
                    iter.setText(e + "");

                    String query = "SELECT branch,currency,denominations,no_denominations,total FROM branch_accounts where branch='" + b1.getSelectedItem().toString() + "' and currency='" + b2.getSelectedItem().toString() + "' and denominations='" + b4.getSelectedItem().toString() + "'";

                    try {
                        Class.forName(DB_DRIVER);
                        con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                        stm = con.createStatement();

                        rs = stm.executeQuery(query);
                        boolean bool = rs.isBeforeFirst();
                        while (rs.next()) {
                            if (bool == true) {
                                String k1 = rs.getString("branch");
                                String k2 = rs.getString("currency");
                                float k3 = rs.getFloat("denominations");
                                int k4 = rs.getInt("no_denominations");
                                float k5 = rs.getFloat("total");
                                int dh = cg + k4;
                                float df = k5 + g;
                                SQLRun run = new SQLRun();

                                String sqlq = "Update branch_accounts set no_denominations='" + dh + "', total='" + df + "' where branch='" + b1.getSelectedItem().toString() + "' and currency='" + b2.getSelectedItem().toString() + "' and denominations='" + b + "'";
                                int updated = run.sqlUpdate(sqlq);
                                if (updated > 0) {
                                    JOptionPane.showMessageDialog(null, b2.getSelectedItem().toString() + " " + "$" + e + " was successfully deposited into  " + b1.getSelectedItem().toString() + "'s account", "Opening balance send ", JOptionPane.INFORMATION_MESSAGE);
                                    b5.setText(" ");
                                }
                                try {
                                    Class.forName(DB_DRIVER);

                                    con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                    pst = con.prepareStatement("insert into initial_balances values(?,?,?,?,?,?,?)");
                                    pst.setString(1, b1.getSelectedItem().toString());
                                    pst.setString(2, b2.getSelectedItem().toString());
                                    pst.setDouble(3, b);
                                    pst.setInt(4, cg);
                                    pst.setDouble(5, g);
                                    pst.setString(6, name.getText());
                                    pst.setTimestamp(7, sqlTimestamp);

                                    int i = pst.executeUpdate();
                                    if (i > 0) {
                                        JOptionPane.showMessageDialog(null, b2.getSelectedItem().toString() + " " + "$" + e + " was successfully deposited into  " + b1.getSelectedItem().toString() + "'s account", "Opening balance send ", JOptionPane.INFORMATION_MESSAGE);
                                        DefaultTableModel dm = (DefaultTableModel) b_table.getModel();
                                        dm.addRow(new Object[]{b1.getSelectedItem().toString(), b2.getSelectedItem().toString(), b, cg, g});

                                    }
                                } catch (SQLException ev) {
                                    JOptionPane.showMessageDialog(null, ev.getMessage());

                                }

                            }
                        }
                        if (bool == false) {
                            try {
                                Class.forName(DB_DRIVER);

                                con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                pst = con.prepareStatement("insert into branch_accounts values(?,?,?,?,?,?,?)");
                                pst.setString(1, b1.getSelectedItem().toString());
                                pst.setString(2, b2.getSelectedItem().toString());
                                pst.setDouble(3, b);
                                pst.setInt(4, cg);
                                pst.setDouble(5, g);
                                pst.setString(6, name.getText());
                                pst.setTimestamp(7, sqlTimestamp);

                                int i = pst.executeUpdate();
                                if (i > 0) {

                                }
                            } catch (SQLException ev) {
                                JOptionPane.showMessageDialog(null, ev.getMessage());

                            }

                            try {
                                Class.forName(DB_DRIVER);

                                con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                pst = con.prepareStatement("insert into initial_balances values(?,?,?,?,?,?,?)");
                                pst.setString(1, b1.getSelectedItem().toString());
                                pst.setString(2, b2.getSelectedItem().toString());
                                pst.setDouble(3, b);
                                pst.setInt(4, cg);
                                pst.setDouble(5, g);
                                pst.setString(6, name.getText());
                                pst.setTimestamp(7, sqlTimestamp);

                                int i = pst.executeUpdate();
                                if (i > 0) {
                                    JOptionPane.showMessageDialog(null, b2.getSelectedItem().toString() + " " + "$" + e + " was successfully deposited into  " + b1.getSelectedItem().toString() + "'s account", "Opening balance send ", JOptionPane.INFORMATION_MESSAGE);

                                }
                            } catch (SQLException ev) {
                                JOptionPane.showMessageDialog(null, ev.getMessage());

                            }
                        }

                    } catch (ClassNotFoundException | SQLException ek) {
                        JOptionPane.showMessageDialog(null, ek.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
                    }

                }
                if (a < e) {
                    //JOptionPane.showMessageDialog(null, "You need to add  "+b2.getSelectedItem().toString()+" "+"$"+g,"Some information missing",JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_b5KeyPressed

    private void jFormattedTextField6MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jFormattedTextField6MouseMoved

        if (jFormattedTextField6.getText().matches("Enter branch name eg Harare")) {
            jFormattedTextField6.setText("");
        }
    }//GEN-LAST:event_jFormattedTextField6MouseMoved

    private void jPanel13MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel13MouseMoved
        if (jFormattedTextField6.getText().isEmpty()) {
            jFormattedTextField6.setText("Enter branch name eg Harare");
        }
    }//GEN-LAST:event_jPanel13MouseMoved

    private void jFormattedTextField6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextField6KeyPressed
        int key = evt.getKeyCode();
        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        if (key == 10) {
            if (jFormattedTextField6.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please the name of the branch", "SearchIing task terminated", JOptionPane.ERROR_MESSAGE);
            }
            if (!jFormattedTextField6.getText().isEmpty()) {
                DefaultTableModel dm = (DefaultTableModel) bal_table.getModel();
                dm.setNumRows(0);

                String query = "SELECT branch,currency,denominations,no_denominations,total FROM branch_accounts where branch='" + jFormattedTextField6.getText() + "'";
                try {
                    Class.forName(DB_DRIVER);
                    con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                    stm = con.createStatement();

                    rs = stm.executeQuery(query);
                    boolean bool = rs.isBeforeFirst();
                    while (rs.next()) {
                        if (bool == true) {
                            String a = rs.getString("branch");
                            String b = rs.getString("currency");
                            float c = rs.getFloat("denominations");
                            int d = rs.getInt("no_denominations");
                            float e = rs.getFloat("total");
                            dm.addRow(new Object[]{a, b, c, d, e});
                        }
                    }
                    if (bool == false) {
                        JOptionPane.showMessageDialog(null, "Branch :" + jFormattedTextField6.getText() + " " + "does not exist in the system or the branch account is empty", "Branch could not be found | Empty Account", JOptionPane.WARNING_MESSAGE);

                    }
                } catch (ClassNotFoundException | SQLException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_jFormattedTextField6KeyPressed

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

    private void jLabel50KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jLabel50KeyPressed

    }//GEN-LAST:event_jLabel50KeyPressed

    private void jLabel50MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel50MouseClicked
DB_USER = uname.getText();
        DB_PAS = upass.getText();
        String tg = "Finance";
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
    }//GEN-LAST:event_jLabel50MouseClicked

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

    private void ccr2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ccr2ActionPerformed
DB_USER = uname.getText();
        DB_PAS = upass.getText();
        if (ccr2.getSelectedItem().equals("")) {

        }
        String bools = "false";
        String query = "SELECT base_currency, short_code FROM currencies ";
        try {
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(DB_URL, DB_USER, DB_PAS);
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

    private void jLabel32MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel32MouseClicked
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
                JOptionPane.showMessageDialog(null, "Please don't enter special character ", "Incorrect data", JOptionPane.ERROR_MESSAGE);
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

    }//GEN-LAST:event_jLabel32MouseClicked

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
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
            operations.setVisible(false);

            currencies.setVisible(false);
            opening_balances.setVisible(false);
            denominations.setVisible(false);
            simult.setVisible(true);
            dv1.setText(rt1.getText());
            sv1.setText(rt1.getText());

            dv2.setText(ru1.getText());
            sv2.setText(ru2.getText());
        }
    }//GEN-LAST:event_jLabel4MouseClicked

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
            double bV = Math.round(b * 10) / 10.0;
            double ab = bV / a1;
            double new_buying = Math.round(ab * 10) / 10.0;

            double c = Double.parseDouble(dv4.getText());
            double cx = Math.round(c * 10) / 10.0;

            double d = Double.parseDouble(sv4.getText());
            double d1 = Math.round(d * 10) / 10.0;
            double cd = d1 / cx;
            double new_selling = Math.round(cd * 10) / 10.0;
            if (new_buying > new_selling) {
                JOptionPane.showMessageDialog(null, "The buying rate should be an amount less than the selling rate", "Exchange rates configuration failure", JOptionPane.WARNING_MESSAGE);

            }
            if (new_buying <= new_selling) {
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

                                String sqlq = "Update config_rates set buying_rate='" + a1 + "', selling_rate='" + cx + "' where  base_currency='" + dv1.getText() + "' and  quote_currency='" + dv2.getText() + "'";
                                int updated = run.sqlUpdate(sqlq);
                                if (updated > 0) {
                                    // JOptionPane.showMessageDialog(null, "First script");
                                }
                            }
                            if (sv1.getText().matches(zg) && sv2.getText().matches(zg1)) {
                                SQLRun runq = new SQLRun();

                                String sqlp = "Update config_rates set buying_rate='" + bV + "', selling_rate='" + d1 + "' where  base_currency='" + sv1.getText() + "' and  quote_currency='" + sv2.getText() + "'";
                                int updatedp = runq.sqlUpdate(sqlp);
                                if (updatedp > 0) {
                                    //JOptionPane.showMessageDialog(null, "Second script");

                                }
                            }
                            if (dv2.getText().matches(zg) && sv2.getText().matches(zg1)) {
                                SQLRun runp = new SQLRun();

                                String sqlq = "Update config_rates set buying_rate='" + new_buying + "', selling_rate='" + new_selling + "' where  base_currency='" + dv2.getText() + "' and  quote_currency='" + sv2.getText() + "'";
                                int updated = runp.sqlUpdate(sqlq);
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

    private void jLabel72MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel72MouseClicked
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
    }//GEN-LAST:event_jLabel72MouseClicked

    private void jLabel70MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel70MouseClicked
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

    private void jLabel188MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel188MouseClicked
        java.util.Date date = new java.util.Date();
        long t = date.getTime();
        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(t);
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
                Logger.getLogger(Finance_Dep.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jLabel188MouseClicked

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
                                    double s3 = rsq.getDouble("exchange_rate");
                                    double s4 = rsq.getDouble("amount_traded_in");
                                    double s5 = rsq.getDouble("amount_traded_out");

                                    String s6 = rsq.getString("region");
                                    String s7 = rsq.getString("branch");
                                    java.sql.Date sqlDatep = rsq.getDate("date");
                                    String s8 = rsq.getString("cashier");

                                    mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});

                                }
                                boolean mb = rsq.isAfterLast();
                                if (mb == false) {
                                    JOptionPane.showMessageDialog(null, "There are no sales recorded between " + mj1.getDate() + " " + "&" + " " + mj2.getDate() + " " + "\nfor " + fa6.getSelectedItem().toString() + " " + "receiving the local curency whilst selling foreign currency", "Transaction record not found", JOptionPane.WARNING_MESSAGE);
                                }

                            } catch (Exception er) {

                            }

                        }
                    }
                } catch (ClassNotFoundException | SQLException e) {
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
                                Class.forName(DB_DRIVER);
                                java.sql.Connection conn = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                String query1 = "SELECT base_currency,quote_currency,region,branch,exchange_rate,amount_traded_in,amount_traded_out,date,cashier FROM transactions_history where date between'" + w1.getText() + "' and '" + w2.getText() + "' and region='" + fa5.getSelectedItem().toString() + "' and branch='" + fa6.getSelectedItem().toString() + "' quote_currency!='" + a + "' and base_currency='" + a + "' and operation='" + op + "' ";

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
                                    java.sql.Date sqlDatep = rsq.getDate("date");
                                    String s8 = rsq.getString("cashier");

                                    mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
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
                                String query1 = "SELECT base_currency,quote_currency,region,branch,exchange_rate,amount_traded_in,amount_traded_out,date,cashier FROM transactions_history where date between'" + w1.getText() + "' and '" + w2.getText() + "' and operation='" + op + "' and region='" + fa5.getSelectedItem().toString() + "'  and quote_currency!='" + a + "' and base_currency='" + a + "' ";

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
                                    java.sql.Date sqlDatep = rsq.getDate("date");
                                    String s8 = rsq.getString("cashier");

                                    mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                    int sum = 0;
                                }
                                boolean mb = rsq.isAfterLast();
                                if (mb == false) {
                                    JOptionPane.showMessageDialog(null, "There are no sales recorded between " + mj1.getDate() + " " + "&" + " " + mj2.getDate() + " " + "\n within " + fa5.getSelectedItem().toString() + " " + "receiving local curency whilst selling foreign currencies", "Transaction record not found", JOptionPane.WARNING_MESSAGE);
                                }
                            } catch (HeadlessException | ClassNotFoundException | SQLException er) {
                                JOptionPane.showMessageDialog(null, er.getMessage(), "An error occured while trying to retrieving data from the database", JOptionPane.WARNING_MESSAGE);

                            }

                        }
                    }
                } catch (HeadlessException | ClassNotFoundException | SQLException e) {
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
                                    double s3 = rsq.getDouble("exchange_rate");
                                    double s4 = rsq.getDouble("amount_traded_in");
                                    double s5 = rsq.getDouble("amount_traded_out");

                                    String s6 = rsq.getString("region");
                                    String s7 = rsq.getString("branch");
                                    java.sql.Date sqlDatep = rsq.getDate("date");
                                    String s8 = rsq.getString("cashier");

                                    mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                    int sum = 0;
                                }
                                boolean mb = rsq.isAfterLast();
                                if (mb == false) {
                                    JOptionPane.showMessageDialog(null, "There are no sales recorded between " + mj1.getDate() + " " + "&" + " " + mj2.getDate() + " " + "\n within " + fa5.getSelectedItem().toString() + " " + "receiving other foreign currencies whilst selling foreign currencies", "Transaction record not found", JOptionPane.WARNING_MESSAGE);
                                }
                            } catch (HeadlessException | ClassNotFoundException | SQLException er) {

                            }

                        }
                    }
                } catch (ClassNotFoundException | SQLException e) {
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
                                    double s3 = rsq.getDouble("exchange_rate");
                                    double s4 = rsq.getDouble("amount_traded_in");
                                    double s5 = rsq.getDouble("amount_traded_out");

                                    String s6 = rsq.getString("region");
                                    String s7 = rsq.getString("branch");
                                    java.sql.Date sqlDatep = rsq.getDate("date");
                                    String s8 = rsq.getString("cashier");

                                    mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
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
                } catch (HeadlessException | ClassNotFoundException | SQLException e) {
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
                                String query1 = "SELECT base_currency,quote_currency,region,branch,exchange_rate,amount_traded_in,amount_traded_out,date,cashier FROM transactions_history where date='" + fw1.getText() + "' and operation='" + op + "'  and region='" + fb5.getSelectedItem().toString() + "' and branch='" + fb6.getSelectedItem().toString() + "' and base_currency!='" + a + "' ";

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
                                    java.sql.Date sqlDatep = rsq.getDate("date");
                                    String s8 = rsq.getString("cashier");

                                    mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
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
                                    double s3 = rsq.getDouble("exchange_rate");
                                    double s4 = rsq.getDouble("amount_traded_in");
                                    double s5 = rsq.getDouble("amount_traded_out");

                                    String s6 = rsq.getString("region");
                                    String s7 = rsq.getString("branch");
                                    java.sql.Date sqlDatep = rsq.getDate("date");
                                    String s8 = rsq.getString("cashier");

                                    mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
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
                } catch (HeadlessException | ClassNotFoundException | SQLException e) {
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
                                    double s3 = rsq.getDouble("exchange_rate");
                                    double s4 = rsq.getDouble("amount_traded_in");
                                    double s5 = rsq.getDouble("amount_traded_out");

                                    String s6 = rsq.getString("region");
                                    String s7 = rsq.getString("branch");
                                    java.sql.Date sqlDatep = rsq.getDate("date");
                                    String s8 = rsq.getString("cashier");

                                    mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
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
                } catch (HeadlessException | ClassNotFoundException | SQLException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), " Something went wrong ", JOptionPane.ERROR_MESSAGE);
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
                                    double s3 = rsq.getDouble("exchange_rate");
                                    double s4 = rsq.getDouble("amount_traded_in");
                                    double s5 = rsq.getDouble("amount_traded_out");

                                    String s6 = rsq.getString("region");
                                    String s7 = rsq.getString("branch");
                                    java.sql.Date sqlDatep = rsq.getDate("date");
                                    String s8 = rsq.getString("cashier");
                                    double sum = 0;

                                    String wq = sqlDatep.toString();
                                    String qw = wq.substring(0, 4);
                                    int af = Integer.parseInt(sym.getValue().toString());
                                    int ag = Integer.parseInt(qw);

                                    if (zm1.getValue().equals("September") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "09";

                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }

                                    if (zm1.getValue().equals("January") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "01";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("February") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "02";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("March") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "03";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("April") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "04";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("May") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "05";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("June") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "06";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("July") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "07";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("August") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "08";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("October") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "10";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("November") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "11";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("December") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "12";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
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
                } catch (HeadlessException | ClassNotFoundException | NumberFormatException | SQLException e) {
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
                                String myDriver = "org.postgresql.Driver";
                                String myUrl = "jdbc:postgresql:" + configs.getText();
                                Class.forName(DB_DRIVER);
                                java.sql.Connection conn = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                String query1 = "SELECT base_currency,quote_currency,region,branch,exchange_rate,amount_traded_in,amount_traded_out,date,cashier FROM transactions_history where region='" + fc5.getSelectedItem().toString() + "' and operation='" + op + "' and base_currency='" + a + "' ";

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
                                    java.sql.Date sqlDatep = rsq.getDate("date");
                                    String s8 = rsq.getString("cashier");
                                    double sum = 0;
                                    String wq = sqlDatep.toString();
                                    String qw = wq.substring(0, 4);
                                    int af = Integer.parseInt(sym.getValue().toString());
                                    int ag = Integer.parseInt(qw);

                                    if (zm1.getValue().equals("September") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "09";

                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }

                                    if (zm1.getValue().equals("January") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "01";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("February") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "02";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("March") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "03";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("April") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "04";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("May") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "05";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("June") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "06";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("July") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "07";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("August") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "08";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("October") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "10";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("November") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "11";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("December") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "12";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
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
                                String myDriver = "org.postgresql.Driver";
                                String myUrl = "jdbc:postgresql:" + configs.getText();
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
                                    java.sql.Date sqlDatep = rsq.getDate("date");
                                    String s8 = rsq.getString("cashier");

                                    double sum = 0;
                                    String wq = sqlDatep.toString();
                                    String qw = wq.substring(0, 4);
                                    int af = Integer.parseInt(sym.getValue().toString());
                                    int ag = Integer.parseInt(qw);

                                    if (zm1.getValue().equals("September") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "09";

                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }

                                    if (zm1.getValue().equals("January") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "01";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("February") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "02";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("March") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "03";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("April") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "04";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("May") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "05";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("June") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "06";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("July") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "07";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("August") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "08";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("October") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "10";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("November") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "11";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("December") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "12";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
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
                                String myDriver = "org.postgresql.Driver";
                                String myUrl = "jdbc:postgresql:" + configs.getText();
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
                                    java.sql.Date sqlDatep = rsq.getDate("date");
                                    String s8 = rsq.getString("cashier");

                                    double sum = 0;
                                    String wq = sqlDatep.toString();
                                    String qw = wq.substring(0, 4);
                                    int af = Integer.parseInt(sym.getValue().toString());
                                    int ag = Integer.parseInt(qw);

                                    if (zm1.getValue().equals("September") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "09";

                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }

                                    if (zm1.getValue().equals("January") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "01";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("February") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "02";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("March") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "03";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("April") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "04";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("May") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "05";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("June") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "06";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("July") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "07";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("August") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "08";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("October") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "10";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("November") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "11";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zm_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zm_table.getValueAt(i, 5).toString());
                                                zm5.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zm1.getValue().equals("December") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "12";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
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
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Something went wrong ", JOptionPane.ERROR_MESSAGE);
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
        String op = "selling";

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
                                Class.forName(myDriver);
                                java.sql.Connection conn = DriverManager.getConnection(myUrl, "postgres", "danieloh24");
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
                                String myDriver = "org.postgresql.Driver";
                                String myUrl = "jdbc:postgresql:" + configs.getText();
                                Class.forName(myDriver);
                                java.sql.Connection conn = DriverManager.getConnection(myUrl, "postgres", "danieloh24");
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
                                String myDriver = "org.postgresql.Driver";
                                String myUrl = "jdbc:postgresql:" + configs.getText();
                                Class.forName(myDriver);
                                java.sql.Connection conn = DriverManager.getConnection(myUrl, "postgres", "danieloh24");
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
                                String myDriver = "org.postgresql.Driver";
                                String myUrl = "jdbc:postgresql:" + configs.getText();
                                Class.forName(myDriver);
                                java.sql.Connection conn = DriverManager.getConnection(myUrl, "postgres", "danieloh24");
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
    }//GEN-LAST:event_jLabel151MouseClicked

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
                                String query1 = "SELECT base_currency,quote_currency,region,branch,exchange_rate,amount_traded_in,amount_traded_out,date,cashier FROM transactions_history where date between'" + kb1.getText() + "' and '" + kb2.getText() + "' and region='" + py5.getSelectedItem().toString() + "' and branch='" + py6.getSelectedItem().toString() + "' and quote_currency='" + a + "' and operation='" + op + "' ";

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
                                    java.sql.Date sqlDatep = rsq.getDate("date");
                                    String s8 = rsq.getString("cashier");

                                    mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                    int sum = 0;
                                }
                                boolean mb = rsq.isAfterLast();
                                if (mb == false) {
                                    JOptionPane.showMessageDialog(null, "There are no purchases recorded between " + py7.getDate() + " " + "&" + " " + py8.getDate() + " " + "\nfor " + py6.getSelectedItem().toString() + " " + "purchasing local curency using foreign currency", "Transaction record not found", JOptionPane.WARNING_MESSAGE);
                                }

                            } catch (HeadlessException | ClassNotFoundException | SQLException er) {

                            }

                        }
                    }
                } catch (ClassNotFoundException | SQLException e) {
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
                                    java.sql.Date sqlDatep = rsq.getDate("date");
                                    String s8 = rsq.getString("cashier");

                                    mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                    int sum = 0;
                                }
                                boolean mb = rsq.isAfterLast();
                                if (mb == false) {
                                    JOptionPane.showMessageDialog(null, "There are no purchases recorded between " + py7.getDate() + " " + "&" + " " + py8.getDate() + " " + "\nfor " + py6.getSelectedItem().toString() + " " + "purchasing foreign curencies using other foreign currencies", "Transaction record not found", JOptionPane.WARNING_MESSAGE);
                                }

                            } catch (HeadlessException | ClassNotFoundException | SQLException er) {

                            }

                        }
                    }
                } catch (ClassNotFoundException | SQLException e) {
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
                                String query1 = "SELECT base_currency,quote_currency,region,branch,exchange_rate,amount_traded_in,amount_traded_out,date,cashier FROM transactions_history where date between'" + kb1.getText() + "'  and '" + kb2.getText() + "' and region='" + py5.getSelectedItem().toString() + "' and quote_currency='" + a + "' and operation='" + op + "' ";

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
                                    java.sql.Date sqlDatep = rsq.getDate("date");
                                    String s8 = rsq.getString("cashier");

                                    mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
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
                } catch (ClassNotFoundException | SQLException e) {
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
                                    String s1 = rsq.getString("quote_currency");
                                    String s2 = rsq.getString("base_currency");
                                    double s3 = rsq.getDouble("amount_traded_in");
                                    double s4 = rsq.getDouble("exchange_rate");
                                    double s5 = rsq.getDouble("amount_traded_out");
                                    String s6 = rsq.getString("region");
                                    String s7 = rsq.getString("branch");
                                    java.sql.Date sqlDatep = rsq.getDate("date");
                                    String s8 = rsq.getString("cashier");

                                    mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                    int sum = 0;
                                }
                                boolean mb = rsq.isAfterLast();
                                if (mb == false) {
                                    JOptionPane.showMessageDialog(null, "There are no purchases recorded between " + py7.getDate() + " " + "&" + " " + py8.getDate() + " " + "\nwithin " + py6.getSelectedItem().toString() + " " + "purchasing foreign currencies using other foreign currencies", "Transaction record not found", JOptionPane.WARNING_MESSAGE);
                                }

                            } catch (HeadlessException | ClassNotFoundException | SQLException er) {

                            }

                        }
                    }
                } catch (ClassNotFoundException | SQLException e) {
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
                                String query1 = "SELECT base_currency,quote_currency,region,branch,exchange_rate,amount_traded_in,amount_traded_out,date,cashier FROM transactions_history where date='" + ts1.getText() + "'   and region='" + wy5.getSelectedItem().toString() + "' and branch='" + wy6.getSelectedItem().toString() + "' and quote_currency='" + a + "' and base_currency!='" + a + "' and operation='" + op + "'";

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
                                    java.sql.Date sqlDatep = rsq.getDate("date");
                                    String s8 = rsq.getString("cashier");

                                    mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
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
                                    JOptionPane.showMessageDialog(null, "There are no purchases recorded on " + wy7.getDate() + "\nfor " + wy6.getSelectedItem().toString() + " " + "purchasing foreign curencies using other foreign currency", "Transaction record not found", JOptionPane.WARNING_MESSAGE);
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
                                    JOptionPane.showMessageDialog(null, "There are no purchases recorded on " + wy7.getDate() + "\nwithin " + wy5.getSelectedItem().toString() + " " + "purchasing local currency using other foreign currency", "Transaction record not found", JOptionPane.WARNING_MESSAGE);
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
                                    JOptionPane.showMessageDialog(null, "There are no purchases recorded on " + wy7.getDate() + "\nwithin " + wy5.getSelectedItem().toString() + " " + "purchasing foreign curencies using other foreign currency", "Transaction record not found", JOptionPane.WARNING_MESSAGE);
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
                                String query1 = "SELECT base_currency,quote_currency,region,branch,exchange_rate,amount_traded_in,amount_traded_out,date,cashier FROM transactions_history where region='" + zh5.getSelectedItem().toString() + "' and operation='" + op + "' and quote_currency='" + a + "' and  base_currency!='" + a + "' and branch='" + zh6.getSelectedItem().toString() + "' ";

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
                                    java.sql.Date sqlDatep = rsq.getDate("date");
                                    String s8 = rsq.getString("cashier");
                                    double sum = 0;

                                    String wq = sqlDatep.toString();
                                    String qw = wq.substring(0, 4);
                                    int af = Integer.parseInt(pym.getValue().toString());
                                    int ag = Integer.parseInt(qw);

                                    if (zh7.getValue().equals("September") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "09";

                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 2).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }

                                    if (zh7.getValue().equals("January") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "01";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 2).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("February") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "02";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 5).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("March") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "03";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 2).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("April") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "04";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 2).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("May") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "05";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 2).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("June") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "06";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 2).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("July") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "07";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 2).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("August") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "08";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 2).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("October") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "10";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 2).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("November") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "11";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 2).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("December") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "12";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 2).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }

                                    //int sum = 0;
                                }
                                boolean mb = rsq.isAfterLast();
                                if (mb == false) {
                                    JOptionPane.showMessageDialog(null, "There are no purchases recorded in the month of " + zh7.getValue() + "\n for " + zh6.getSelectedItem().toString() + " " + "purchasing local curency using foreign currencies", "Transaction record not found", JOptionPane.WARNING_MESSAGE);
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
                                    java.sql.Date sqlDatep = rsq.getDate("date");
                                    String s8 = rsq.getString("cashier");
                                    double sum = 0;
                                    String wq = sqlDatep.toString();
                                    String qw = wq.substring(0, 4);
                                    int af = Integer.parseInt(pym.getValue().toString());
                                    int ag = Integer.parseInt(qw);

                                    if (zh7.getValue().equals("September") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "09";

                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 2).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }

                                    if (zh7.getValue().equals("January") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "01";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 2).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("February") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "02";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 5).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("March") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "03";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 2).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("April") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "04";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 2).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("May") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "05";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 2).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("June") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "06";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 2).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("July") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "07";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 2).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("August") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "08";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 2).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("October") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "10";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 2).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("November") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "11";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 2).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("December") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "12";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 2).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                }
                                boolean mb = rsq.isAfterLast();
                                if (mb == false) {
                                    JOptionPane.showMessageDialog(null, "There are no purchases recorded in the month of " + zh7.getValue() + "\n for " + zh6.getSelectedItem().toString() + " " + "purchasing foreign currency using other foreign currencies", "Transaction record not found", JOptionPane.WARNING_MESSAGE);
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
                                    String s1 = rsq.getString("quote_currency");
                                    String s2 = rsq.getString("base_currency");
                                    double s3 = rsq.getDouble("amount_traded_in");
                                    double s4 = rsq.getDouble("exchange_rate");
                                    double s5 = rsq.getDouble("amount_traded_out");
                                    String s6 = rsq.getString("region");
                                    String s7 = rsq.getString("branch");
                                    java.sql.Date sqlDatep = rsq.getDate("date");
                                    String s8 = rsq.getString("cashier");

                                    double sum = 0;
                                    String wq = sqlDatep.toString();
                                    String qw = wq.substring(0, 4);
                                    int af = Integer.parseInt(pym.getValue().toString());
                                    int ag = Integer.parseInt(qw);

                                    if (zh7.getValue().equals("September") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "09";

                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 2).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }

                                    if (zh7.getValue().equals("January") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "01";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 2).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("February") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "02";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 5).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("March") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "03";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 2).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("April") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "04";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 2).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("May") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "05";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 2).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("June") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "06";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 2).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("July") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "07";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 2).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("August") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "08";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 2).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("October") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "10";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 2).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("November") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "11";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 2).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("December") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "12";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 2).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                }
                                boolean mb = rsq.isAfterLast();
                                if (mb == false) {
                                    JOptionPane.showMessageDialog(null, "There are no purchases recorded in the month of " + zh7.getValue() + "\n within " + zh5.getSelectedItem().toString() + " " + "purchasing local currency using foreign currencies", "Transaction record not found", JOptionPane.WARNING_MESSAGE);
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
                                    java.sql.Date sqlDatep = rsq.getDate("date");
                                    String s8 = rsq.getString("cashier");

                                    double sum = 0;
                                    String wq = sqlDatep.toString();
                                    String qw = wq.substring(0, 4);
                                    int af = Integer.parseInt(pym.getValue().toString());
                                    int ag = Integer.parseInt(qw);

                                    if (zh7.getValue().equals("September") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "09";

                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 2).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }

                                    if (zh7.getValue().equals("January") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "01";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 2).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("February") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "02";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 5).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("March") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "03";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 2).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("April") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "04";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 2).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("May") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "05";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 2).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("June") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "06";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 2).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("July") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "07";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 2).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("August") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "08";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 2).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("October") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "10";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 2).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("November") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "11";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 2).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                    if (zh7.getValue().equals("December") && af == ag) {
                                        String ht = sqlDatep.toString();
                                        String dt = ht.substring(5, 7);
                                        String v1 = "12";
                                        if (dt.matches(v1)) {
                                            mod.addRow(new Object[]{s1, s2, s3, s4, s5, s6, s7, s8, sqlDatep});
                                            for (int i = 0; i < zh_table.getRowCount(); i++) {
                                                sum = sum + Double.parseDouble(zh_table.getValueAt(i, 2).toString());
                                                zh8.setText(Double.toString(sum));
                                            }
                                        }
                                    }
                                }
                                boolean mb = rsq.isAfterLast();
                                if (mb == false) {
                                    JOptionPane.showMessageDialog(null, "There are no purchases recorded in the month of " + zh7.getValue() + "\n within " + zh5.getSelectedItem().toString() + " " + "purchasing foreign curency using other foreign currencies", "Transaction record not found", JOptionPane.WARNING_MESSAGE);
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
                                String query1 = "SELECT base_currency,quote_currency,region,branch,exchange_rate,amount_traded_in,amount_traded_out,date,cashier FROM transactions_history where region='" + px5.getSelectedItem().toString() + "' and branch='" + px6.getSelectedItem().toString() + "' and operation='" + op + "' and quote_currency='" + a + "' and base_currency!='" + a + "'";

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
                                    java.sql.Date sqlDatep = rsq.getDate("date");
                                    String s8 = rsq.getString("cashier");
                                    String ht = sqlDatep.toString();
                                    String dt = ht.substring(0, 4);
                                    int af = Integer.parseInt(px7.getValue().toString());
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
                                String myDriver = "org.postgresql.Driver";
                                String myUrl = "jdbc:postgresql:" + configs.getText();
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
                                    java.sql.Date sqlDatep = rsq.getDate("date");
                                    String s8 = rsq.getString("cashier");
                                    String ht = sqlDatep.toString();
                                    String dt = ht.substring(0, 4);
                                    int af = Integer.parseInt(px7.getValue().toString());
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
                                    String s1 = rsq.getString("quote_currency");
                                    String s2 = rsq.getString("base_currency");
                                    double s3 = rsq.getDouble("amount_traded_in");
                                    double s4 = rsq.getDouble("exchange_rate");
                                    double s5 = rsq.getDouble("amount_traded_out");
                                    String s6 = rsq.getString("region");
                                    String s7 = rsq.getString("branch");
                                    java.sql.Date sqlDatep = rsq.getDate("date");
                                    String s8 = rsq.getString("cashier");
                                    String ht = sqlDatep.toString();
                                    String dt = ht.substring(0, 4);
                                    int af = Integer.parseInt(px7.getValue().toString());
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
                                    String s1 = rsq.getString("quote_currency");
                                    String s2 = rsq.getString("base_currency");
                                    double s3 = rsq.getDouble("amount_traded_in");
                                    double s4 = rsq.getDouble("exchange_rate");
                                    double s5 = rsq.getDouble("amount_traded_out");
                                    String s6 = rsq.getString("region");
                                    String s7 = rsq.getString("branch");
                                    java.sql.Date sqlDatep = rsq.getDate("date");
                                    String s8 = rsq.getString("cashier");
                                    String ht = sqlDatep.toString();
                                    String dt = ht.substring(0, 4);
                                    int af = Integer.parseInt(px7.getValue().toString());
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
                con = DriverManager.getConnection(DB_URL, DB_USER, DB_PAS);
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

    private void opb3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opb3ActionPerformed
        DefaultComboBoxModel dm = new DefaultComboBoxModel();
        opb4.setModel(dm);
DB_USER = uname.getText();
        DB_PAS = upass.getText();DB_USER = uname.getText();
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
DB_USER = uname.getText();
        DB_PAS = upass.getText();
        java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(t);
        java.sql.Date sqlDate = new java.sql.Date(t);

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
            java.util.logging.Logger.getLogger(Finance_Dep.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Finance_Dep.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Finance_Dep.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Finance_Dep.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Finance_Dep().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> acc_currency;
    private javax.swing.JTable acc_table;
    private javax.swing.JFormattedTextField acc_total;
    private javax.swing.JPanel animation;
    private javax.swing.JComboBox<String> b1;
    private javax.swing.JComboBox<String> b2;
    private javax.swing.JFormattedTextField b3;
    private javax.swing.JComboBox<String> b4;
    private javax.swing.JFormattedTextField b5;
    private javax.swing.JTable b_table;
    private javax.swing.JTable bal_table;
    private javax.swing.JLabel bfc;
    private javax.swing.JPanel branches;
    private javax.swing.JLabel bscd;
    private javax.swing.JFormattedTextField c1;
    private javax.swing.JFormattedTextField c2;
    private javax.swing.JCheckBox c3;
    private javax.swing.JTable c_table;
    private javax.swing.JComboBox<String> ccr1;
    private javax.swing.JComboBox<String> ccr2;
    private javax.swing.JFormattedTextField ccr3;
    private javax.swing.JFormattedTextField ccr4;
    private javax.swing.JComboBox<String> cg1;
    private javax.swing.JComboBox<String> cg2;
    private javax.swing.JComboBox<String> cg3;
    private javax.swing.JFormattedTextField cg4;
    private javax.swing.JTable cg_table;
    private javax.swing.JLabel cn;
    public static final javax.swing.JLabel configs = new javax.swing.JLabel();
    private javax.swing.JLabel configs2;
    private javax.swing.JComboBox<String> cp1;
    private javax.swing.JComboBox<String> cp2;
    private javax.swing.JComboBox<String> cp3;
    private javax.swing.JFormattedTextField cp4;
    private javax.swing.JTable cp_table;
    private javax.swing.JPanel currencies;
    private javax.swing.JFormattedTextField cy1;
    private javax.swing.JFormattedTextField cy2;
    private javax.swing.JCheckBox cy3;
    private javax.swing.JLabel cy_id;
    private javax.swing.JPanel dashboard_home;
    private javax.swing.JPanel dashboard_master;
    private javax.swing.JPanel dashboard_operations;
    private javax.swing.JPanel dashboard_rates;
    private javax.swing.JPanel dashboard_reports;
    private javax.swing.JLabel day;
    private javax.swing.JLabel ddname;
    private javax.swing.JLabel dec;
    private javax.swing.JTable den_table;
    private javax.swing.JTable dend_table;
    private javax.swing.JComboBox<String> denom;
    private javax.swing.JPanel denominations;
    private javax.swing.JTable denu_table;
    private javax.swing.JFormattedTextField denv;
    private javax.swing.JLabel did;
    private javax.swing.JFormattedTextField dv1;
    private javax.swing.JFormattedTextField dv2;
    private javax.swing.JFormattedTextField dv3;
    private javax.swing.JFormattedTextField dv4;
    private javax.swing.JTable dy_table;
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
    private javax.swing.JLabel fdw;
    private javax.swing.JLabel fw1;
    private javax.swing.JLabel fw2;
    private javax.swing.JPanel header;
    private javax.swing.JFormattedTextField iter;
    private javax.swing.JComboBox<String> jComboBox13;
    private javax.swing.JComboBox<String> jComboBox15;
    private javax.swing.JComboBox<String> jComboBox20;
    private javax.swing.JComboBox<String> jComboBox21;
    private javax.swing.JFormattedTextField jFormattedTextField36;
    private javax.swing.JFormattedTextField jFormattedTextField37;
    private javax.swing.JFormattedTextField jFormattedTextField38;
    private javax.swing.JFormattedTextField jFormattedTextField39;
    private javax.swing.JFormattedTextField jFormattedTextField40;
    private javax.swing.JFormattedTextField jFormattedTextField41;
    private javax.swing.JFormattedTextField jFormattedTextField43;
    private javax.swing.JFormattedTextField jFormattedTextField45;
    private javax.swing.JFormattedTextField jFormattedTextField46;
    private javax.swing.JFormattedTextField jFormattedTextField47;
    private javax.swing.JFormattedTextField jFormattedTextField48;
    private javax.swing.JFormattedTextField jFormattedTextField6;
    private javax.swing.JFormattedTextField jFormattedTextField9;
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
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
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
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
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
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel45;
    private javax.swing.JPanel jPanel46;
    private javax.swing.JPanel jPanel47;
    private javax.swing.JPanel jPanel48;
    private javax.swing.JPanel jPanel49;
    private javax.swing.JPanel jPanel50;
    private javax.swing.JPanel jPanel51;
    private javax.swing.JPanel jPanel52;
    private javax.swing.JPanel jPanel53;
    private javax.swing.JPanel jPanel54;
    private javax.swing.JPanel jPanel55;
    private javax.swing.JPanel jPanel56;
    private javax.swing.JPanel jPanel58;
    private javax.swing.JPanel jPanel60;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JPasswordField jPasswordField2;
    private javax.swing.JPasswordField jPasswordField3;
    private javax.swing.JPasswordField jPasswordField4;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane19;
    private javax.swing.JScrollPane jScrollPane20;
    private javax.swing.JScrollPane jScrollPane21;
    private javax.swing.JScrollPane jScrollPane22;
    private javax.swing.JScrollPane jScrollPane23;
    private javax.swing.JScrollPane jScrollPane24;
    private javax.swing.JScrollPane jScrollPane25;
    private javax.swing.JScrollPane jScrollPane26;
    private javax.swing.JScrollPane jScrollPane27;
    private javax.swing.JScrollPane jScrollPane28;
    private javax.swing.JScrollPane jScrollPane29;
    private javax.swing.JScrollPane jScrollPane30;
    private javax.swing.JScrollPane jScrollPane31;
    private javax.swing.JScrollPane jScrollPane32;
    private javax.swing.JScrollPane jScrollPane34;
    private javax.swing.JScrollPane jScrollPane35;
    private javax.swing.JScrollPane jScrollPane36;
    private javax.swing.JScrollPane jScrollPane37;
    private javax.swing.JScrollPane jScrollPane38;
    private javax.swing.JScrollPane jScrollPane39;
    private javax.swing.JScrollPane jScrollPane40;
    private javax.swing.JScrollPane jScrollPane41;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane10;
    private javax.swing.JTabbedPane jTabbedPane11;
    private javax.swing.JTabbedPane jTabbedPane12;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTabbedPane jTabbedPane4;
    private javax.swing.JTabbedPane jTabbedPane5;
    private javax.swing.JTabbedPane jTabbedPane6;
    private javax.swing.JTabbedPane jTabbedPane7;
    private javax.swing.JTabbedPane jTabbedPane8;
    private javax.swing.JTabbedPane jTabbedPane9;
    private javax.swing.JTable jTable10;
    private javax.swing.JTable jTable11;
    private javax.swing.JTable jTable9;
    private javax.swing.JTable jm_table1;
    private javax.swing.JLabel jpb1;
    private javax.swing.JLabel jpy1;
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
    public static final javax.swing.JLabel name = new javax.swing.JLabel();
    private javax.swing.JLabel nhy;
    private javax.swing.JComboBox<String> ntf1;
    private javax.swing.JFormattedTextField ntf2;
    private javax.swing.JFormattedTextField ntf3;
    private javax.swing.JEditorPane ntf4;
    private javax.swing.JCheckBox opb1;
    private javax.swing.JCheckBox opb2;
    private javax.swing.JComboBox<String> opb3;
    private javax.swing.JComboBox<String> opb4;
    private javax.swing.JPanel opening_balances;
    private javax.swing.JPanel operations;
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
    private javax.swing.JPanel sales_rep;
    private javax.swing.JPanel simult;
    private javax.swing.JFormattedTextField sv1;
    private javax.swing.JFormattedTextField sv2;
    private javax.swing.JFormattedTextField sv3;
    private javax.swing.JFormattedTextField sv4;
    private javax.swing.JSpinner sym;
    private javax.swing.JLabel time;
    private javax.swing.JLabel ts1;
    private javax.swing.JLabel ts2;
    private javax.swing.JLabel tst;
    private javax.swing.JComboBox<String> tw3;
    private javax.swing.JComboBox<String> tw4;
    private javax.swing.JComboBox<String> tw5;
    private javax.swing.JFormattedTextField tw6;
    private javax.swing.JTable tw_table;
    public static final javax.swing.JLabel uname = new javax.swing.JLabel();
    public static final javax.swing.JLabel upass = new javax.swing.JLabel();
    private javax.swing.JPanel users;
    public static final javax.swing.JLabel w1 = new javax.swing.JLabel();
    public static final javax.swing.JLabel w2 = new javax.swing.JLabel();
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
