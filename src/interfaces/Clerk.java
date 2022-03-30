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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Sir Chikukwa
 */
public class Clerk extends javax.swing.JFrame {

    /**
     * Creates new form Admin
     */
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

   

    public Clerk() {
        initComponents();
        setIconImage(Toolkit.getDefaultToolkit().getImage("src/img/shop.png"));
        connect();
        connect1();
        db_users_connect();
        db_password_connect();
        shorttime();

        dashboard_rates.setVisible(false);
        dashboard_reports.setVisible(false);
        dashboard_master.setVisible(false);
        dashboard_operations.setVisible(false);

        withdraw.setVisible(false);
        opening_balances.setVisible(false);
        buying.setVisible(false);

        rates.setVisible(false);
        reports.setVisible(false);
        transfers.setVisible(false);
        selling.setVisible(false);
        requests.setVisible(false);
DB_USER = uname.getText();
                DB_PAS = upass.getText();
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
            //JOptionPane.showMessageDialog(null, e.getMessage(), "Something went wrong", JOptionPane.ERROR_MESSAGE);
        }

        String txt = my_text.getText();
        lblTextLength = txt.length();
        tm = new Timer(150, new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                counter++;
                if (counter > lblTextLength) {
                    my_text.setText(" ");
                    counter = 0;

                } else {
                    my_text.setText(txt.substring(0, counter));

                }

            }
        });

        tm.start();

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

    public void getSum() {
        int sum = 0;
        for (int i = 0; i < acc_table.getRowCount(); i++) {
            sum = sum + Integer.parseInt(acc_table.getValueAt(i, 2).toString());
        }
        acc_total.setText(Integer.toString(sum));
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
        posi = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        dashboard_home = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel106 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        my_id = new javax.swing.JLabel();
        header = new javax.swing.JPanel();
        animation = new javax.swing.JPanel();
        my_text = new javax.swing.JLabel();
        selling = new javax.swing.JPanel();
        jTabbedPane14 = new javax.swing.JTabbedPane();
        jPanel56 = new javax.swing.JPanel();
        jLabel60 = new javax.swing.JLabel();
        sell2 = new javax.swing.JComboBox<>();
        jLabel73 = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        sell3 = new javax.swing.JFormattedTextField();
        jLabel75 = new javax.swing.JLabel();
        sell5 = new javax.swing.JComboBox<>();
        jLabel77 = new javax.swing.JLabel();
        sell6 = new javax.swing.JFormattedTextField();
        jLabel78 = new javax.swing.JLabel();
        sell4 = new javax.swing.JFormattedTextField();
        jScrollPane9 = new javax.swing.JScrollPane();
        sell_table = new javax.swing.JTable();
        jLabel79 = new javax.swing.JLabel();
        sell7 = new javax.swing.JFormattedTextField();
        jLabel80 = new javax.swing.JLabel();
        swp = new javax.swing.JLabel();
        my_list = new javax.swing.JLabel();
        jLabel81 = new javax.swing.JLabel();
        carr = new javax.swing.JLabel();
        amtt = new javax.swing.JLabel();
        dmt = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel61 = new javax.swing.JPanel();
        jLabel71 = new javax.swing.JLabel();
        cse2 = new javax.swing.JComboBox<>();
        jLabel82 = new javax.swing.JLabel();
        jLabel83 = new javax.swing.JLabel();
        cse3 = new javax.swing.JFormattedTextField();
        jLabel84 = new javax.swing.JLabel();
        cse5 = new javax.swing.JComboBox<>();
        jLabel105 = new javax.swing.JLabel();
        cse6 = new javax.swing.JFormattedTextField();
        jLabel107 = new javax.swing.JLabel();
        cse4 = new javax.swing.JFormattedTextField();
        jScrollPane11 = new javax.swing.JScrollPane();
        cse_table = new javax.swing.JTable();
        jLabel108 = new javax.swing.JLabel();
        sell14 = new javax.swing.JFormattedTextField();
        jLabel117 = new javax.swing.JLabel();
        swp1 = new javax.swing.JLabel();
        my_list1 = new javax.swing.JLabel();
        jLabel118 = new javax.swing.JLabel();
        carr1 = new javax.swing.JLabel();
        amtt1 = new javax.swing.JLabel();
        dmt1 = new javax.swing.JLabel();
        cse1 = new javax.swing.JComboBox<>();
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
        reports = new javax.swing.JPanel();
        jTabbedPane13 = new javax.swing.JTabbedPane();
        jPanel55 = new javax.swing.JPanel();
        jLabel76 = new javax.swing.JLabel();
        jPanel57 = new javax.swing.JPanel();
        jPanel59 = new javax.swing.JPanel();
        jLabel109 = new javax.swing.JLabel();
        jFormattedTextField35 = new javax.swing.JFormattedTextField();
        jLabel110 = new javax.swing.JLabel();
        jFormattedTextField38 = new javax.swing.JFormattedTextField();
        jLabel111 = new javax.swing.JLabel();
        jComboBox15 = new javax.swing.JComboBox<>();
        jLabel112 = new javax.swing.JLabel();
        jFormattedTextField39 = new javax.swing.JFormattedTextField();
        jLabel113 = new javax.swing.JLabel();
        jFormattedTextField42 = new javax.swing.JFormattedTextField();
        jLabel114 = new javax.swing.JLabel();
        jFormattedTextField43 = new javax.swing.JFormattedTextField();
        jLabel115 = new javax.swing.JLabel();
        jFormattedTextField45 = new javax.swing.JFormattedTextField();
        jLabel116 = new javax.swing.JLabel();
        transfers = new javax.swing.JPanel();
        jTabbedPane7 = new javax.swing.JTabbedPane();
        jPanel18 = new javax.swing.JPanel();
        jTabbedPane8 = new javax.swing.JTabbedPane();
        jPanel30 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane19 = new javax.swing.JScrollPane();
        pk_table = new javax.swing.JTable();
        jPanel22 = new javax.swing.JPanel();
        jLabel95 = new javax.swing.JLabel();
        pk3 = new javax.swing.JFormattedTextField();
        jLabel103 = new javax.swing.JLabel();
        jLabel104 = new javax.swing.JLabel();
        pk1 = new javax.swing.JComboBox<>();
        jLabel96 = new javax.swing.JLabel();
        pk2 = new javax.swing.JComboBox<>();
        jPanel31 = new javax.swing.JPanel();
        jScrollPane21 = new javax.swing.JScrollPane();
        req_table = new javax.swing.JTable();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        cx_currency = new javax.swing.JLabel();
        cx_denom = new javax.swing.JLabel();
        cx_num = new javax.swing.JLabel();
        cx_total = new javax.swing.JLabel();
        cx_from = new javax.swing.JLabel();
        cx_id = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jTabbedPane10 = new javax.swing.JTabbedPane();
        jPanel65 = new javax.swing.JPanel();
        jLabel99 = new javax.swing.JLabel();
        rx1 = new javax.swing.JFormattedTextField();
        jScrollPane25 = new javax.swing.JScrollPane();
        rx2 = new javax.swing.JEditorPane();
        jLabel199 = new javax.swing.JLabel();
        rx3 = new javax.swing.JComboBox<>();
        jLabel200 = new javax.swing.JLabel();
        jScrollPane26 = new javax.swing.JScrollPane();
        rx4 = new javax.swing.JEditorPane();
        jLabel201 = new javax.swing.JLabel();
        rx5 = new javax.swing.JLabel();
        jPanel66 = new javax.swing.JPanel();
        jLabel202 = new javax.swing.JLabel();
        rz1 = new javax.swing.JComboBox<>();
        jLabel203 = new javax.swing.JLabel();
        rz2 = new javax.swing.JFormattedTextField();
        jScrollPane27 = new javax.swing.JScrollPane();
        rz4 = new javax.swing.JEditorPane();
        rz3 = new javax.swing.JFormattedTextField();
        jLabel204 = new javax.swing.JLabel();
        withdraw = new javax.swing.JPanel();
        jTabbedPane11 = new javax.swing.JTabbedPane();
        jPanel46 = new javax.swing.JPanel();
        jLabel61 = new javax.swing.JLabel();
        jFormattedTextField16 = new javax.swing.JFormattedTextField();
        jPanel47 = new javax.swing.JPanel();
        jLabel62 = new javax.swing.JLabel();
        jScrollPane13 = new javax.swing.JScrollPane();
        jEditorPane10 = new javax.swing.JEditorPane();
        jPanel48 = new javax.swing.JPanel();
        jLabel63 = new javax.swing.JLabel();
        jComboBox7 = new javax.swing.JComboBox<>();
        jLabel64 = new javax.swing.JLabel();
        jFormattedTextField17 = new javax.swing.JFormattedTextField();
        jLabel65 = new javax.swing.JLabel();
        jFormattedTextField18 = new javax.swing.JFormattedTextField();
        jScrollPane14 = new javax.swing.JScrollPane();
        jEditorPane11 = new javax.swing.JEditorPane();
        jPanel49 = new javax.swing.JPanel();
        jLabel66 = new javax.swing.JLabel();
        jPanel50 = new javax.swing.JPanel();
        jLabel67 = new javax.swing.JLabel();
        jComboBox8 = new javax.swing.JComboBox<>();
        jLabel68 = new javax.swing.JLabel();
        jFormattedTextField19 = new javax.swing.JFormattedTextField();
        jLabel69 = new javax.swing.JLabel();
        jFormattedTextField20 = new javax.swing.JFormattedTextField();
        jScrollPane15 = new javax.swing.JScrollPane();
        jEditorPane12 = new javax.swing.JEditorPane();
        jPanel51 = new javax.swing.JPanel();
        jLabel70 = new javax.swing.JLabel();
        opening_balances = new javax.swing.JPanel();
        jPanel52 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        acc_currency = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        acc_table = new javax.swing.JTable();
        jLabel39 = new javax.swing.JLabel();
        acc_total = new javax.swing.JFormattedTextField();
        buying = new javax.swing.JPanel();
        jTabbedPane15 = new javax.swing.JTabbedPane();
        jPanel60 = new javax.swing.JPanel();
        jLabel72 = new javax.swing.JLabel();
        by2 = new javax.swing.JComboBox<>();
        jLabel85 = new javax.swing.JLabel();
        jLabel86 = new javax.swing.JLabel();
        by3 = new javax.swing.JFormattedTextField();
        jLabel87 = new javax.swing.JLabel();
        by5 = new javax.swing.JComboBox<>();
        jLabel88 = new javax.swing.JLabel();
        by6 = new javax.swing.JFormattedTextField();
        jLabel89 = new javax.swing.JLabel();
        by4 = new javax.swing.JFormattedTextField();
        jScrollPane10 = new javax.swing.JScrollPane();
        by_table = new javax.swing.JTable();
        jLabel90 = new javax.swing.JLabel();
        by11 = new javax.swing.JFormattedTextField();
        jLabel91 = new javax.swing.JLabel();
        by8 = new javax.swing.JLabel();
        by_list = new javax.swing.JLabel();
        jLabel92 = new javax.swing.JLabel();
        by7 = new javax.swing.JLabel();
        by9 = new javax.swing.JLabel();
        by10 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jPanel62 = new javax.swing.JPanel();
        jLabel93 = new javax.swing.JLabel();
        na2 = new javax.swing.JComboBox<>();
        jLabel94 = new javax.swing.JLabel();
        jLabel119 = new javax.swing.JLabel();
        na3 = new javax.swing.JFormattedTextField();
        jLabel121 = new javax.swing.JLabel();
        na5 = new javax.swing.JComboBox<>();
        jLabel126 = new javax.swing.JLabel();
        na6 = new javax.swing.JFormattedTextField();
        jLabel127 = new javax.swing.JLabel();
        na4 = new javax.swing.JFormattedTextField();
        jScrollPane12 = new javax.swing.JScrollPane();
        na_table = new javax.swing.JTable();
        jLabel128 = new javax.swing.JLabel();
        na12 = new javax.swing.JFormattedTextField();
        jLabel129 = new javax.swing.JLabel();
        na9 = new javax.swing.JLabel();
        na_list = new javax.swing.JLabel();
        jLabel130 = new javax.swing.JLabel();
        na7 = new javax.swing.JLabel();
        na10 = new javax.swing.JLabel();
        na11 = new javax.swing.JLabel();
        na1 = new javax.swing.JComboBox<>();
        requests = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jTabbedPane9 = new javax.swing.JTabbedPane();
        jPanel32 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane20 = new javax.swing.JScrollPane();
        ck_table = new javax.swing.JTable();
        jPanel24 = new javax.swing.JPanel();
        jLabel122 = new javax.swing.JLabel();
        ck3 = new javax.swing.JFormattedTextField();
        jLabel123 = new javax.swing.JLabel();
        jLabel124 = new javax.swing.JLabel();
        ck1 = new javax.swing.JComboBox<>();
        jLabel37 = new javax.swing.JLabel();
        jLabel125 = new javax.swing.JLabel();
        ck4 = new javax.swing.JComboBox<>();
        ck5 = new javax.swing.JFormattedTextField();
        jLabel38 = new javax.swing.JLabel();
        ck2 = new javax.swing.JComboBox<>();
        mari = new javax.swing.JComboBox<>();
        rates = new javax.swing.JPanel();
        jPanel28 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel15 = new javax.swing.JPanel();
        jScrollPane28 = new javax.swing.JScrollPane();
        rb = new javax.swing.JTable();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane30 = new javax.swing.JScrollPane();
        kb = new javax.swing.JTable();
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

        posi.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        posi.setForeground(new java.awt.Color(248, 148, 6));
        posi.setText("Counter Clerk");

        jLabel3.setFont(new java.awt.Font("Yu Gothic UI Light", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 102, 102));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-profile-32.png"))); // NOI18N

        name.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        name.setText("jLabel32");

        branch.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        branch.setText("jLabel37");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(name, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(posi, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 1, Short.MAX_VALUE))
                            .addComponent(branch, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(posi)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(name)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(branch))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 7, 240, 200));

        dashboard_home.setBackground(new java.awt.Color(255, 255, 255));
        dashboard_home.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        dashboard_home.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-get-cash-32.png"))); // NOI18N
        jLabel5.setText("Buy Currencies");
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });
        dashboard_home.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 64, 224, 40));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-cash-in-hand-32.png"))); // NOI18N
        jLabel6.setText("Sell Currencies");
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });
        dashboard_home.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 115, 224, 40));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-dollar-euro-exchange-32.png"))); // NOI18N
        jLabel7.setText("Exchange Rates");
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });
        dashboard_home.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 166, 224, 40));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-deposit-32.png"))); // NOI18N
        jLabel8.setText("Request Cash");
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel8MouseClicked(evt);
            }
        });
        dashboard_home.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 217, 224, 40));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-notification-32.png"))); // NOI18N
        jLabel9.setText("Cash Transfers, Notifications");
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel9MouseClicked(evt);
            }
        });
        dashboard_home.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 268, -1, 40));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/lout.png"))); // NOI18N
        jLabel10.setText("Sign Out");
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
        });
        dashboard_home.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 360, 224, 40));

        jLabel106.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel106.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/clo.png"))); // NOI18N
        jLabel106.setText("Close Application");
        jLabel106.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel106MouseClicked(evt);
            }
        });
        dashboard_home.add(jLabel106, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 411, 224, 40));

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-contact-details-32.png"))); // NOI18N
        jLabel11.setText("My Personal Account");
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel11MouseEntered(evt);
            }
        });
        dashboard_home.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 13, 224, 40));

        jLabel32.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-privacy-32.png"))); // NOI18N
        jLabel32.setText("Change My Account Credentials");
        jLabel32.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel32MouseClicked(evt);
            }
        });
        dashboard_home.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 314, 224, 40));

        my_id.setText("jLabel4");
        dashboard_home.add(my_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(82, 512, -1, 0));

        phy.setText("jLabel2");
        dashboard_home.add(phy, new org.netbeans.lib.awtextra.AbsoluteConstraints(137, 505, -1, 0));

        configs.setText("jLabel50");
        dashboard_home.add(configs, new org.netbeans.lib.awtextra.AbsoluteConstraints(87, 469, -1, 0));

        jPanel1.add(dashboard_home, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 211, 240, 510));

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
        animation.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 102, 255)));
        animation.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        my_text.setFont(new java.awt.Font("Perpetua Titling MT", 0, 14)); // NOI18N
        my_text.setForeground(new java.awt.Color(255, 255, 255));
        my_text.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        my_text.setText("A bureau de change or currency exchange is a business whose customers exchange one currency for another.");
        animation.add(my_text, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 2, 998, 37));

        jPanel1.add(animation, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 100, 995, -1));

        selling.setBackground(new java.awt.Color(255, 255, 255));

        jTabbedPane14.setBackground(new java.awt.Color(255, 255, 255));

        jPanel56.setBackground(new java.awt.Color(255, 255, 255));
        jPanel56.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel60.setText("Quote currency");
        jPanel56.add(jLabel60, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 18, -1, -1));

        sell1.setEditable(false);
        jPanel56.add(sell1, new org.netbeans.lib.awtextra.AbsoluteConstraints(132, 11, 167, 28));

        sell2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
        sell2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sell2ActionPerformed(evt);
            }
        });
        jPanel56.add(sell2, new org.netbeans.lib.awtextra.AbsoluteConstraints(132, 45, 167, 28));

        jLabel73.setText("Base currency");
        jPanel56.add(jLabel73, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 52, -1, -1));

        jLabel74.setText("Enter amount");
        jPanel56.add(jLabel74, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 91, -1, -1));

        sell3.setToolTipText("Enter the amount of money you want to sell");
        sell3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                sell3KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                sell3KeyTyped(evt);
            }
        });
        jPanel56.add(sell3, new org.netbeans.lib.awtextra.AbsoluteConstraints(132, 84, 167, 28));

        jLabel75.setText("Select  denominations");
        jPanel56.add(jLabel75, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 158, -1, -1));

        sell5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
        jPanel56.add(sell5, new org.netbeans.lib.awtextra.AbsoluteConstraints(132, 151, 167, 28));

        jLabel77.setText("No. of denominations");
        jPanel56.add(jLabel77, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 197, -1, -1));

        sell6.setToolTipText("Enter the number of denominations the you want to sell");
        sell6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                sell6KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                sell6KeyTyped(evt);
            }
        });
        jPanel56.add(sell6, new org.netbeans.lib.awtextra.AbsoluteConstraints(132, 190, 167, 28));

        jLabel78.setText("You have to receive");
        jPanel56.add(jLabel78, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 125, -1, -1));

        sell4.setEditable(false);
        sell4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel56.add(sell4, new org.netbeans.lib.awtextra.AbsoluteConstraints(132, 118, 167, 28));

        sell_table.setAutoCreateRowSorter(true);
        sell_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "Denominations", "No. denominations", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        sell_table.setRowHeight(20);
        sell_table.setShowHorizontalLines(false);
        sell_table.setShowVerticalLines(false);
        sell_table.getTableHeader().setReorderingAllowed(false);
        sell_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sell_tableMouseClicked(evt);
            }
        });
        jScrollPane9.setViewportView(sell_table);
        if (sell_table.getColumnModel().getColumnCount() > 0) {
            sell_table.getColumnModel().getColumn(0).setResizable(false);
            sell_table.getColumnModel().getColumn(1).setResizable(false);
            sell_table.getColumnModel().getColumn(2).setResizable(false);
            sell_table.getColumnModel().getColumn(3).setResizable(false);
        }

        jPanel56.add(jScrollPane9, new org.netbeans.lib.awtextra.AbsoluteConstraints(306, 11, 679, 483));

        jLabel79.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel79.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel79.setText("Total");
        jPanel56.add(jLabel79, new org.netbeans.lib.awtextra.AbsoluteConstraints(647, 508, 167, -1));

        sell7.setEditable(false);
        sell7.setBackground(new java.awt.Color(255, 255, 255));
        sell7.setText("0000");
        sell7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jPanel56.add(sell7, new org.netbeans.lib.awtextra.AbsoluteConstraints(818, 501, 167, 28));

        jLabel80.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel80.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-delete-bin-32.png"))); // NOI18N
        jLabel80.setText("Delete");
        jLabel80.setToolTipText("Hint this icon to remove the denomination(s)");
        jLabel80.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel80MouseClicked(evt);
            }
        });
        jPanel56.add(jLabel80, new org.netbeans.lib.awtextra.AbsoluteConstraints(306, 500, -1, -1));

        swp.setText("Update No. Denominations");
        jPanel56.add(swp, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 569, 0, 0));

        rat.setText("jLabel71");
        jPanel56.add(rat, new org.netbeans.lib.awtextra.AbsoluteConstraints(154, 569, -1, 0));
        jPanel56.add(my_list, new org.netbeans.lib.awtextra.AbsoluteConstraints(227, 566, 20, -1));

        jLabel81.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel81.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-replace-32.png"))); // NOI18N
        jLabel81.setText("Update");
        jLabel81.setToolTipText("Hint this icon to remove the denomination(s)");
        jLabel81.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel81MouseClicked(evt);
            }
        });
        jPanel56.add(jLabel81, new org.netbeans.lib.awtextra.AbsoluteConstraints(532, 501, -1, 30));

        carr.setText("jLabel71");
        jPanel56.add(carr, new org.netbeans.lib.awtextra.AbsoluteConstraints(134, 564, -1, 0));

        amtt.setText("jLabel71");
        jPanel56.add(amtt, new org.netbeans.lib.awtextra.AbsoluteConstraints(137, 567, 27, 0));

        dmt.setText("jLabel71");
        jPanel56.add(dmt, new org.netbeans.lib.awtextra.AbsoluteConstraints(108, 565, -1, 0));

        reg.setText("jLabel18");
        jPanel56.add(reg, new org.netbeans.lib.awtextra.AbsoluteConstraints(36, 559, -1, 0));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("SELLING CURRENCIES ");
        jPanel56.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 229, 289, 44));

        jTabbedPane14.addTab("Use Local Currency", new javax.swing.ImageIcon(getClass().getResource("/img/ex.png")), jPanel56); // NOI18N

        jPanel61.setBackground(new java.awt.Color(255, 255, 255));
        jPanel61.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel71.setText("Quote currency");
        jPanel61.add(jLabel71, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 18, -1, -1));

        cse2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
        cse2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cse2ActionPerformed(evt);
            }
        });
        jPanel61.add(cse2, new org.netbeans.lib.awtextra.AbsoluteConstraints(132, 45, 167, 28));

        jLabel82.setText("Base currency");
        jPanel61.add(jLabel82, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 52, -1, -1));

        jLabel83.setText("Amount to be sold");
        jPanel61.add(jLabel83, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 91, -1, -1));

        cse3.setToolTipText("Enter the amount of money you want to sell");
        cse3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cse3KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                cse3KeyTyped(evt);
            }
        });
        jPanel61.add(cse3, new org.netbeans.lib.awtextra.AbsoluteConstraints(132, 84, 167, 28));

        jLabel84.setText("Select  denominations");
        jPanel61.add(jLabel84, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 158, -1, -1));

        cse5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
        jPanel61.add(cse5, new org.netbeans.lib.awtextra.AbsoluteConstraints(132, 151, 167, 28));

        jLabel105.setText("No. of denominations");
        jPanel61.add(jLabel105, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 197, -1, -1));

        cse6.setToolTipText("Enter the number of denominations the you want to sell");
        cse6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cse6KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                cse6KeyTyped(evt);
            }
        });
        jPanel61.add(cse6, new org.netbeans.lib.awtextra.AbsoluteConstraints(132, 190, 167, 28));

        jLabel107.setText("You have to receive");
        jPanel61.add(jLabel107, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 125, -1, -1));

        cse4.setEditable(false);
        cse4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel61.add(cse4, new org.netbeans.lib.awtextra.AbsoluteConstraints(132, 118, 167, 28));

        cse_table.setAutoCreateRowSorter(true);
        cse_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "Denominations", "No. denominations", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        cse_table.setRowHeight(20);
        cse_table.setShowHorizontalLines(false);
        cse_table.setShowVerticalLines(false);
        cse_table.getTableHeader().setReorderingAllowed(false);
        cse_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cse_tableMouseClicked(evt);
            }
        });
        jScrollPane11.setViewportView(cse_table);
        if (cse_table.getColumnModel().getColumnCount() > 0) {
            cse_table.getColumnModel().getColumn(0).setResizable(false);
            cse_table.getColumnModel().getColumn(1).setResizable(false);
            cse_table.getColumnModel().getColumn(2).setResizable(false);
            cse_table.getColumnModel().getColumn(3).setResizable(false);
        }

        jPanel61.add(jScrollPane11, new org.netbeans.lib.awtextra.AbsoluteConstraints(306, 11, 679, 483));

        jLabel108.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel108.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel108.setText("Total");
        jPanel61.add(jLabel108, new org.netbeans.lib.awtextra.AbsoluteConstraints(647, 508, 167, -1));

        sell14.setEditable(false);
        sell14.setBackground(new java.awt.Color(255, 255, 255));
        sell14.setText("0000");
        sell14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jPanel61.add(sell14, new org.netbeans.lib.awtextra.AbsoluteConstraints(818, 501, 167, 28));

        jLabel117.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel117.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-delete-bin-32.png"))); // NOI18N
        jLabel117.setText("Delete");
        jLabel117.setToolTipText("Hint this icon to remove the denomination(s)");
        jLabel117.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel117MouseClicked(evt);
            }
        });
        jPanel61.add(jLabel117, new org.netbeans.lib.awtextra.AbsoluteConstraints(306, 500, -1, -1));

        swp1.setText("Update No. Denominations");
        jPanel61.add(swp1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 563, 164, 0));

        rat1.setText("jLabel71");
        jPanel61.add(rat1, new org.netbeans.lib.awtextra.AbsoluteConstraints(154, 555, -1, 0));
        jPanel61.add(my_list1, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 550, 172, -1));

        jLabel118.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel118.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-replace-32.png"))); // NOI18N
        jLabel118.setText("Update");
        jLabel118.setToolTipText("Hint this icon to remove the denomination(s)");
        jLabel118.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel118MouseClicked(evt);
            }
        });
        jPanel61.add(jLabel118, new org.netbeans.lib.awtextra.AbsoluteConstraints(532, 501, -1, 30));

        carr1.setText("jLabel71");
        jPanel61.add(carr1, new org.netbeans.lib.awtextra.AbsoluteConstraints(134, 558, -1, 0));

        amtt1.setText("jLabel71");
        jPanel61.add(amtt1, new org.netbeans.lib.awtextra.AbsoluteConstraints(137, 561, -1, 0));

        dmt1.setText("jLabel71");
        jPanel61.add(dmt1, new org.netbeans.lib.awtextra.AbsoluteConstraints(108, 565, -1, 0));

        cse1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
        cse1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cse1ActionPerformed(evt);
            }
        });
        jPanel61.add(cse1, new org.netbeans.lib.awtextra.AbsoluteConstraints(132, 11, 167, 28));

        jTabbedPane14.addTab("Cross Currency", new javax.swing.ImageIcon(getClass().getResource("/img/ex.png")), jPanel61); // NOI18N

        javax.swing.GroupLayout sellingLayout = new javax.swing.GroupLayout(selling);
        selling.setLayout(sellingLayout);
        sellingLayout.setHorizontalGroup(
            sellingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1000, Short.MAX_VALUE)
            .addGroup(sellingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jTabbedPane14, javax.swing.GroupLayout.Alignment.TRAILING))
        );
        sellingLayout.setVerticalGroup(
            sellingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 615, Short.MAX_VALUE)
            .addGroup(sellingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jTabbedPane14, javax.swing.GroupLayout.Alignment.TRAILING))
        );

        jPanel1.add(selling, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 140, 1000, 580));

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
                .addContainerGap(199, Short.MAX_VALUE))
        );

        jPanel1.add(dashboard_master, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 211, -1, 510));

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
                .addContainerGap(301, Short.MAX_VALUE))
        );

        jPanel1.add(dashboard_rates, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 211, -1, 510));

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
                .addContainerGap(250, Short.MAX_VALUE))
        );

        jPanel1.add(dashboard_operations, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 211, -1, 510));

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
                .addContainerGap(301, Short.MAX_VALUE))
        );

        jPanel1.add(dashboard_reports, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 211, -1, 510));

        jTabbedPane13.setBackground(new java.awt.Color(255, 255, 255));

        jPanel55.setBackground(new java.awt.Color(255, 255, 255));
        jPanel55.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel76.setText("Number of notifictions");
        jPanel55.add(jLabel76, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 18, -1, -1));

        jTabbedPane13.addTab("Use Local Currency", new javax.swing.ImageIcon(getClass().getResource("/img/ex.png")), jPanel55); // NOI18N

        jPanel57.setBackground(new java.awt.Color(255, 255, 255));

        jPanel59.setBackground(new java.awt.Color(255, 255, 255));
        jPanel59.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel109.setText("Name");

        jFormattedTextField35.setEditable(false);

        jLabel110.setText("Surname");

        jFormattedTextField38.setEditable(false);

        jLabel111.setText("Gender");

        jComboBox15.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel112.setText("Phone number");

        jFormattedTextField39.setEditable(false);

        jLabel113.setText("ID number");

        jFormattedTextField42.setEditable(false);

        jLabel114.setText("Branch");

        jFormattedTextField43.setEditable(false);

        jLabel115.setText("Position");

        jFormattedTextField45.setEditable(false);

        jLabel116.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel116.setText("Change password");

        javax.swing.GroupLayout jPanel59Layout = new javax.swing.GroupLayout(jPanel59);
        jPanel59.setLayout(jPanel59Layout);
        jPanel59Layout.setHorizontalGroup(
            jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel59Layout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addGroup(jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel59Layout.createSequentialGroup()
                        .addComponent(jLabel113, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(20, 20, 20)
                        .addComponent(jFormattedTextField42, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel59Layout.createSequentialGroup()
                        .addComponent(jLabel112)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                        .addComponent(jFormattedTextField39, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel59Layout.createSequentialGroup()
                        .addComponent(jLabel111)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jComboBox15, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel59Layout.createSequentialGroup()
                        .addComponent(jLabel109)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jFormattedTextField35, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel59Layout.createSequentialGroup()
                        .addComponent(jLabel110)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jFormattedTextField38, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel116, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel59Layout.createSequentialGroup()
                        .addGroup(jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel114, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel115, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jFormattedTextField45)
                            .addComponent(jFormattedTextField43, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE))))
                .addContainerGap(98, Short.MAX_VALUE))
        );
        jPanel59Layout.setVerticalGroup(
            jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel59Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel109)
                    .addComponent(jFormattedTextField35, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel110)
                    .addComponent(jFormattedTextField38, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox15, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel111))
                .addGap(8, 8, 8)
                .addGroup(jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel59Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jLabel112))
                    .addComponent(jFormattedTextField39, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel113, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFormattedTextField42, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel114, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFormattedTextField43, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel115, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFormattedTextField45, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel116, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(63, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel57Layout = new javax.swing.GroupLayout(jPanel57);
        jPanel57.setLayout(jPanel57Layout);
        jPanel57Layout.setHorizontalGroup(
            jPanel57Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel57Layout.createSequentialGroup()
                .addGap(364, 364, 364)
                .addComponent(jPanel59, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(184, Short.MAX_VALUE))
        );
        jPanel57Layout.setVerticalGroup(
            jPanel57Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel57Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel59, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(144, Short.MAX_VALUE))
        );

        jTabbedPane13.addTab("Use Other Currencies", new javax.swing.ImageIcon(getClass().getResource("/img/rat.png")), jPanel57); // NOI18N

        javax.swing.GroupLayout reportsLayout = new javax.swing.GroupLayout(reports);
        reports.setLayout(reportsLayout);
        reportsLayout.setHorizontalGroup(
            reportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1000, Short.MAX_VALUE)
            .addGroup(reportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jTabbedPane13, javax.swing.GroupLayout.Alignment.TRAILING))
        );
        reportsLayout.setVerticalGroup(
            reportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 580, Short.MAX_VALUE)
            .addGroup(reportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jTabbedPane13, javax.swing.GroupLayout.Alignment.TRAILING))
        );

        jPanel1.add(reports, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 140, 1000, 580));

        jTabbedPane7.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane7.setFocusable(false);

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));
        jPanel18.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTabbedPane8.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane8.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);

        jPanel30.setBackground(new java.awt.Color(255, 255, 255));

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));

        pk_table.setAutoCreateRowSorter(true);
        pk_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Selected currencies", "Denominations", "No. denominations", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        pk_table.setRowHeight(20);
        pk_table.setShowHorizontalLines(false);
        pk_table.setShowVerticalLines(false);
        jScrollPane19.setViewportView(pk_table);

        jPanel22.setBackground(new java.awt.Color(255, 255, 255));
        jPanel22.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel22.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel95.setText("Select currency");
        jPanel22.add(jLabel95, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 28, -1, -1));

        pk3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                pk3KeyTyped(evt);
            }
        });
        jPanel22.add(pk3, new org.netbeans.lib.awtextra.AbsoluteConstraints(165, 91, 190, 28));

        jLabel103.setText("Denominations");
        jPanel22.add(jLabel103, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 63, -1, -1));

        jLabel104.setText("No. denominations");
        jPanel22.add(jLabel104, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 98, -1, -1));

        pk1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
        pk1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pk1ActionPerformed(evt);
            }
        });
        jPanel22.add(pk1, new org.netbeans.lib.awtextra.AbsoluteConstraints(165, 21, 190, 29));

        jLabel96.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel96.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/sn.png"))); // NOI18N
        jLabel96.setText("Send money");
        jLabel96.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel96MouseClicked(evt);
            }
        });
        jPanel22.add(jLabel96, new org.netbeans.lib.awtextra.AbsoluteConstraints(165, 130, 190, 28));

        pk2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
        pk2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pk2ActionPerformed(evt);
            }
        });
        jPanel22.add(pk2, new org.netbeans.lib.awtextra.AbsoluteConstraints(165, 56, 190, 29));

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane19, javax.swing.GroupLayout.DEFAULT_SIZE, 985, Short.MAX_VALUE))
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(245, 245, 245)
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, 456, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane19, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 995, Short.MAX_VALUE)
            .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 494, Short.MAX_VALUE)
            .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane8.addTab("Transfer to strong room operator", new javax.swing.ImageIcon(getClass().getResource("/img/sh.png")), jPanel30); // NOI18N

        jPanel31.setBackground(new java.awt.Color(255, 255, 255));
        jPanel31.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        req_table.setAutoCreateRowSorter(true);
        req_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Currency", "Denominations", "No. denominations", "Total", "Send to", "Requested on (date)", "Request ID"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        req_table.setRowHeight(20);
        req_table.setShowHorizontalLines(false);
        req_table.setShowVerticalLines(false);
        req_table.getTableHeader().setReorderingAllowed(false);
        req_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                req_tableMouseClicked(evt);
            }
        });
        req_table.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                req_tableKeyPressed(evt);
            }
        });
        jScrollPane21.setViewportView(req_table);
        if (req_table.getColumnModel().getColumnCount() > 0) {
            req_table.getColumnModel().getColumn(0).setResizable(false);
            req_table.getColumnModel().getColumn(1).setResizable(false);
            req_table.getColumnModel().getColumn(2).setResizable(false);
            req_table.getColumnModel().getColumn(3).setResizable(false);
            req_table.getColumnModel().getColumn(4).setResizable(false);
            req_table.getColumnModel().getColumn(5).setResizable(false);
            req_table.getColumnModel().getColumn(6).setResizable(false);
        }

        jPanel31.add(jScrollPane21, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 11, 995, 430));

        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-share-rounded-32.png"))); // NOI18N
        jLabel23.setText("Send as requested");
        jPanel31.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 449, -1, 38));

        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-feedly-32.png"))); // NOI18N
        jLabel24.setText("Custom b/c sending");
        jLabel24.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel24MouseClicked(evt);
            }
        });
        jPanel31.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(385, 452, 145, -1));

        jLabel40.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-remove-tag-32.png"))); // NOI18N
        jLabel40.setText("Revoke request");
        jLabel40.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel40MouseClicked(evt);
            }
        });
        jPanel31.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(801, 449, 146, -1));
        jPanel31.add(cx_currency, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 409, 112, -1));

        cx_denom.setText(" ");
        jPanel31.add(cx_denom, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 409, 112, 0));

        cx_num.setText(" ");
        jPanel31.add(cx_num, new org.netbeans.lib.awtextra.AbsoluteConstraints(274, 420, 112, 1));

        cx_total.setText(" ");
        jPanel31.add(cx_total, new org.netbeans.lib.awtextra.AbsoluteConstraints(396, 409, 112, 22));

        cx_from.setText(" ");
        jPanel31.add(cx_from, new org.netbeans.lib.awtextra.AbsoluteConstraints(526, 409, 66, 0));

        cx_id.setText(" ");
        jPanel31.add(cx_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 413, -1, -1));

        jTabbedPane8.addTab("Transfer to other cashiers", new javax.swing.ImageIcon(getClass().getResource("/img/csh.png")), jPanel31); // NOI18N

        jPanel18.add(jTabbedPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 540));

        jTabbedPane7.addTab("Cash Transfers", new javax.swing.ImageIcon(getClass().getResource("/img/sndm.png")), jPanel18); // NOI18N

        jPanel65.setBackground(new java.awt.Color(255, 255, 255));
        jPanel65.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel99.setText("Notification Subject");
        jPanel65.add(jLabel99, new org.netbeans.lib.awtextra.AbsoluteConstraints(303, 29, -1, -1));
        jPanel65.add(rx1, new org.netbeans.lib.awtextra.AbsoluteConstraints(414, 20, 217, 32));

        rx2.setBorder(javax.swing.BorderFactory.createTitledBorder("Write your notification here"));
        jScrollPane25.setViewportView(rx2);

        jPanel65.add(jScrollPane25, new org.netbeans.lib.awtextra.AbsoluteConstraints(303, 70, 360, 172));

        jLabel199.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-send-32.png"))); // NOI18N
        jLabel199.setText("Save Notification");
        jLabel199.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel199MouseClicked(evt);
            }
        });
        jPanel65.add(jLabel199, new org.netbeans.lib.awtextra.AbsoluteConstraints(537, 411, 126, -1));

        rx3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "Regional Managers", "Finance Personel", "Counter Clerk", "Strong Room Operator", "Branch Manager" }));
        rx3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rx3ActionPerformed(evt);
            }
        });
        jPanel65.add(rx3, new org.netbeans.lib.awtextra.AbsoluteConstraints(425, 248, 206, 28));

        jLabel200.setText("Select the audience");
        jPanel65.add(jLabel200, new org.netbeans.lib.awtextra.AbsoluteConstraints(303, 255, -1, -1));

        rx4.setEditable(false);
        rx4.setBorder(javax.swing.BorderFactory.createTitledBorder("List of your recipients"));
        jScrollPane26.setViewportView(rx4);

        jPanel65.add(jScrollPane26, new org.netbeans.lib.awtextra.AbsoluteConstraints(303, 294, 360, 111));

        jLabel201.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Clear-icon.png"))); // NOI18N
        jLabel201.setText("Remove Selecetd Audiences");
        jLabel201.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel201MouseClicked(evt);
            }
        });
        jPanel65.add(jLabel201, new org.netbeans.lib.awtextra.AbsoluteConstraints(313, 411, 180, -1));
        jPanel65.add(rx5, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 480, 0, -1));

        jTabbedPane10.addTab("Post Notifications", new javax.swing.ImageIcon(getClass().getResource("/img/icons8-megaphone-32.png")), jPanel65); // NOI18N

        jPanel66.setBackground(new java.awt.Color(255, 255, 255));

        jLabel202.setText("Select Subject");

        rz1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
        rz1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rz1ActionPerformed(evt);
            }
        });

        jLabel203.setText("Notification from");

        rz2.setEditable(false);
        rz2.setBackground(new java.awt.Color(255, 255, 255));

        rz4.setEditable(false);
        rz4.setBorder(javax.swing.BorderFactory.createTitledBorder("Notication content"));
        jScrollPane27.setViewportView(rz4);

        jLabel204.setText("Notification send on");

        javax.swing.GroupLayout jPanel66Layout = new javax.swing.GroupLayout(jPanel66);
        jPanel66.setLayout(jPanel66Layout);
        jPanel66Layout.setHorizontalGroup(
            jPanel66Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel66Layout.createSequentialGroup()
                .addContainerGap(352, Short.MAX_VALUE)
                .addGroup(jPanel66Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane27, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel66Layout.createSequentialGroup()
                        .addGroup(jPanel66Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel66Layout.createSequentialGroup()
                                .addGroup(jPanel66Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel202)
                                    .addComponent(jLabel204))
                                .addGap(33, 33, 33))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel66Layout.createSequentialGroup()
                                .addComponent(jLabel203, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(22, 22, 22)))
                        .addGroup(jPanel66Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(rz1, 0, 170, Short.MAX_VALUE)
                            .addComponent(rz2)
                            .addComponent(rz3))))
                .addGap(345, 345, 345))
        );
        jPanel66Layout.setVerticalGroup(
            jPanel66Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel66Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(jPanel66Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel202)
                    .addComponent(rz1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel66Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel203)
                    .addComponent(rz2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel66Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rz3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel204))
                .addGap(26, 26, 26)
                .addComponent(jScrollPane27, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(176, Short.MAX_VALUE))
        );

        jTabbedPane10.addTab("View Notifications", new javax.swing.ImageIcon(getClass().getResource("/img/icons8-received-32.png")), jPanel66); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1000, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jTabbedPane10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 540, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jTabbedPane10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jTabbedPane7.addTab("Notifications", new javax.swing.ImageIcon(getClass().getResource("/img/icons8-notification-32.png")), jPanel5); // NOI18N

        javax.swing.GroupLayout transfersLayout = new javax.swing.GroupLayout(transfers);
        transfers.setLayout(transfersLayout);
        transfersLayout.setHorizontalGroup(
            transfersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1005, Short.MAX_VALUE)
            .addGroup(transfersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jTabbedPane7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        transfersLayout.setVerticalGroup(
            transfersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 586, Short.MAX_VALUE)
            .addGroup(transfersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jTabbedPane7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel1.add(transfers, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 140, 1000, 580));

        jTabbedPane11.setBackground(new java.awt.Color(255, 255, 255));

        jPanel46.setBackground(new java.awt.Color(255, 255, 255));
        jPanel46.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel61.setText("Region name");
        jPanel46.add(jLabel61, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 18, -1, -1));
        jPanel46.add(jFormattedTextField16, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 11, 190, 28));

        jPanel47.setBackground(new java.awt.Color(102, 153, 255));

        jLabel62.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel62.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel62.setText("Save ");

        javax.swing.GroupLayout jPanel47Layout = new javax.swing.GroupLayout(jPanel47);
        jPanel47.setLayout(jPanel47Layout);
        jPanel47Layout.setHorizontalGroup(
            jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel47Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel62, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel47Layout.setVerticalGroup(
            jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel47Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel62, javax.swing.GroupLayout.DEFAULT_SIZE, 17, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel46.add(jPanel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 165, 270, -1));

        jScrollPane13.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Add a description of a region", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        jScrollPane13.setViewportView(jEditorPane10);

        jPanel46.add(jScrollPane13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 270, 110));

        jTabbedPane11.addTab("Add Curencies", new javax.swing.ImageIcon(getClass().getResource("/img/ex.png")), jPanel46); // NOI18N

        jPanel48.setBackground(new java.awt.Color(255, 255, 255));

        jLabel63.setText("Region name");

        jComboBox7.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel64.setText("Branch name");

        jLabel65.setText("Area code");

        jScrollPane14.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Add a description of a region", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        jScrollPane14.setViewportView(jEditorPane11);

        jPanel49.setBackground(new java.awt.Color(102, 153, 255));

        jLabel66.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel66.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel66.setText("Save ");

        javax.swing.GroupLayout jPanel49Layout = new javax.swing.GroupLayout(jPanel49);
        jPanel49.setLayout(jPanel49Layout);
        jPanel49Layout.setHorizontalGroup(
            jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel66, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
        );
        jPanel49Layout.setVerticalGroup(
            jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel66, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel48Layout = new javax.swing.GroupLayout(jPanel48);
        jPanel48.setLayout(jPanel48Layout);
        jPanel48Layout.setHorizontalGroup(
            jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel48Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel49, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel48Layout.createSequentialGroup()
                                .addComponent(jLabel63)
                                .addGap(18, 18, 18)
                                .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel48Layout.createSequentialGroup()
                                .addComponent(jLabel64)
                                .addGap(18, 18, 18)
                                .addComponent(jFormattedTextField17, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel48Layout.createSequentialGroup()
                                .addComponent(jLabel65)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jFormattedTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(715, Short.MAX_VALUE))
        );
        jPanel48Layout.setVerticalGroup(
            jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel48Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel63))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel48Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jLabel64))
                    .addComponent(jFormattedTextField17, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel65)
                    .addComponent(jFormattedTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel49, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(253, Short.MAX_VALUE))
        );

        jTabbedPane11.addTab("Update Currency Details", new javax.swing.ImageIcon(getClass().getResource("/img/rat.png")), jPanel48); // NOI18N

        jPanel50.setBackground(new java.awt.Color(255, 255, 255));

        jLabel67.setText("Region name");

        jComboBox8.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel68.setText("Branch name");

        jLabel69.setText("Area code");

        jScrollPane15.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Add a description of a region", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        jScrollPane15.setViewportView(jEditorPane12);

        jPanel51.setBackground(new java.awt.Color(102, 153, 255));

        jLabel70.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel70.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel70.setText("Save ");

        javax.swing.GroupLayout jPanel51Layout = new javax.swing.GroupLayout(jPanel51);
        jPanel51.setLayout(jPanel51Layout);
        jPanel51Layout.setHorizontalGroup(
            jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel70, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
        );
        jPanel51Layout.setVerticalGroup(
            jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel70, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel50Layout = new javax.swing.GroupLayout(jPanel50);
        jPanel50.setLayout(jPanel50Layout);
        jPanel50Layout.setHorizontalGroup(
            jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel50Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel51, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel50Layout.createSequentialGroup()
                                .addComponent(jLabel67)
                                .addGap(18, 18, 18)
                                .addComponent(jComboBox8, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel50Layout.createSequentialGroup()
                                .addComponent(jLabel68)
                                .addGap(18, 18, 18)
                                .addComponent(jFormattedTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel50Layout.createSequentialGroup()
                                .addComponent(jLabel69)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jFormattedTextField20, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(715, Short.MAX_VALUE))
        );
        jPanel50Layout.setVerticalGroup(
            jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel50Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox8, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel67))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel50Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jLabel68))
                    .addComponent(jFormattedTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel69)
                    .addComponent(jFormattedTextField20, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel51, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(253, Short.MAX_VALUE))
        );

        jTabbedPane11.addTab("Remove Currencies", new javax.swing.ImageIcon(getClass().getResource("/img/rat.png")), jPanel50); // NOI18N

        javax.swing.GroupLayout withdrawLayout = new javax.swing.GroupLayout(withdraw);
        withdraw.setLayout(withdrawLayout);
        withdrawLayout.setHorizontalGroup(
            withdrawLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1000, Short.MAX_VALUE)
            .addGroup(withdrawLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jTabbedPane11, javax.swing.GroupLayout.Alignment.TRAILING))
        );
        withdrawLayout.setVerticalGroup(
            withdrawLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 580, Short.MAX_VALUE)
            .addGroup(withdrawLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jTabbedPane11, javax.swing.GroupLayout.Alignment.TRAILING))
        );

        jPanel1.add(withdraw, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 140, 1000, 580));

        jPanel52.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("What you have in your account"));

        jLabel4.setText("Select currency");

        acc_currency.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
        acc_currency.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                acc_currencyActionPerformed(evt);
            }
        });

        acc_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Denominations", "No. denominations", "Total"
            }
        ));
        acc_table.setRowHeight(20);
        acc_table.setShowHorizontalLines(false);
        acc_table.setShowVerticalLines(false);
        jScrollPane1.setViewportView(acc_table);

        jLabel39.setText("Grand Total for the selected currency");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(83, 83, 83)
                        .addComponent(jLabel4)
                        .addGap(27, 27, 27)
                        .addComponent(acc_currency, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel39)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(acc_total, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(89, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(acc_currency, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(acc_total, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                    .addComponent(jLabel39))
                .addGap(39, 39, 39))
        );

        jPanel3.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(224, 84, 480, 290));

        jTabbedPane1.addTab("My Account", new javax.swing.ImageIcon(getClass().getResource("/img/sn.png")), jPanel3); // NOI18N

        javax.swing.GroupLayout jPanel52Layout = new javax.swing.GroupLayout(jPanel52);
        jPanel52.setLayout(jPanel52Layout);
        jPanel52Layout.setHorizontalGroup(
            jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        jPanel52Layout.setVerticalGroup(
            jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
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
            .addGap(0, 580, Short.MAX_VALUE)
            .addGroup(opening_balancesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel52, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(opening_balances, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 140, 1000, 580));

        jTabbedPane15.setBackground(new java.awt.Color(255, 255, 255));

        jPanel60.setBackground(new java.awt.Color(255, 255, 255));
        jPanel60.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel72.setText("Base currency");
        jPanel60.add(jLabel72, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 18, -1, -1));

        by1.setEditable(false);
        jPanel60.add(by1, new org.netbeans.lib.awtextra.AbsoluteConstraints(146, 11, 167, 28));

        by2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
        by2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                by2ActionPerformed(evt);
            }
        });
        jPanel60.add(by2, new org.netbeans.lib.awtextra.AbsoluteConstraints(146, 45, 167, 28));

        jLabel85.setText("Quote currency");
        jPanel60.add(jLabel85, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 52, -1, -1));

        jLabel86.setText("Amount to be purchased");
        jPanel60.add(jLabel86, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 91, -1, -1));

        by3.setToolTipText("Enter the amount of money you want to sell");
        by3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                by3KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                by3KeyTyped(evt);
            }
        });
        jPanel60.add(by3, new org.netbeans.lib.awtextra.AbsoluteConstraints(146, 84, 167, 28));

        jLabel87.setText("Select  denominations");
        jPanel60.add(jLabel87, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 158, -1, -1));

        by5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
        jPanel60.add(by5, new org.netbeans.lib.awtextra.AbsoluteConstraints(146, 151, 167, 28));

        jLabel88.setText("No. of denominations");
        jPanel60.add(jLabel88, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 199, -1, -1));

        by6.setToolTipText("Enter the number of denominations the you want to sell");
        by6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                by6ActionPerformed(evt);
            }
        });
        by6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                by6KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                by6KeyTyped(evt);
            }
        });
        jPanel60.add(by6, new org.netbeans.lib.awtextra.AbsoluteConstraints(146, 185, 167, 28));

        jLabel89.setText("You have to pay");
        jPanel60.add(jLabel89, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 125, -1, -1));

        by4.setEditable(false);
        by4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel60.add(by4, new org.netbeans.lib.awtextra.AbsoluteConstraints(146, 118, 167, 28));

        by_table.setAutoCreateRowSorter(true);
        by_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "Denominations", "No. denominations", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        by_table.setRowHeight(20);
        by_table.setShowHorizontalLines(false);
        by_table.setShowVerticalLines(false);
        by_table.getTableHeader().setReorderingAllowed(false);
        by_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                by_tableMouseClicked(evt);
            }
        });
        jScrollPane10.setViewportView(by_table);

        jPanel60.add(jScrollPane10, new org.netbeans.lib.awtextra.AbsoluteConstraints(319, 11, 686, 483));

        jLabel90.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel90.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel90.setText("Total");
        jPanel60.add(jLabel90, new org.netbeans.lib.awtextra.AbsoluteConstraints(689, 508, 167, -1));

        by11.setEditable(false);
        by11.setBackground(new java.awt.Color(255, 255, 255));
        by11.setText("0000");
        by11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jPanel60.add(by11, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 501, 145, 28));

        jLabel91.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel91.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-delete-bin-32.png"))); // NOI18N
        jLabel91.setText("Delete");
        jLabel91.setToolTipText("Hint this icon to remove the denomination(s)");
        jLabel91.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel91MouseClicked(evt);
            }
        });
        jPanel60.add(jLabel91, new org.netbeans.lib.awtextra.AbsoluteConstraints(319, 500, -1, -1));

        by8.setText("Update No. Denominations");
        jPanel60.add(by8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 419, 0, 0));

        by_rat.setText("jLabel71");
        jPanel60.add(by_rat, new org.netbeans.lib.awtextra.AbsoluteConstraints(167, 578, -1, 0));
        jPanel60.add(by_list, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 572, 0, -1));

        jLabel92.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel92.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-replace-32.png"))); // NOI18N
        jLabel92.setText("Update");
        jLabel92.setToolTipText("Hint this icon to remove the denomination(s)");
        jLabel92.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel92MouseClicked(evt);
            }
        });
        jPanel60.add(jLabel92, new org.netbeans.lib.awtextra.AbsoluteConstraints(574, 501, -1, 30));

        by7.setText("jLabel71");
        jPanel60.add(by7, new org.netbeans.lib.awtextra.AbsoluteConstraints(94, 580, -1, 0));

        by9.setText("jLabel71");
        jPanel60.add(by9, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 577, 40, 0));

        by10.setText("jLabel71");
        jPanel60.add(by10, new org.netbeans.lib.awtextra.AbsoluteConstraints(121, 498, -1, 0));

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("BUYING CURRENCIES ");
        jPanel60.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 219, 303, 51));

        jTabbedPane15.addTab("Use Local Currency", new javax.swing.ImageIcon(getClass().getResource("/img/ex.png")), jPanel60); // NOI18N

        jPanel62.setBackground(new java.awt.Color(255, 255, 255));
        jPanel62.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel93.setText("Base currency");
        jPanel62.add(jLabel93, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 18, -1, -1));

        na2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
        na2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                na2ActionPerformed(evt);
            }
        });
        jPanel62.add(na2, new org.netbeans.lib.awtextra.AbsoluteConstraints(146, 45, 167, 28));

        jLabel94.setText("Quote currency");
        jPanel62.add(jLabel94, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 52, -1, -1));

        jLabel119.setText("Amount to be purchased");
        jPanel62.add(jLabel119, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 91, -1, -1));

        na3.setToolTipText("Enter the amount of money you want to sell");
        na3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                na3KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                na3KeyTyped(evt);
            }
        });
        jPanel62.add(na3, new org.netbeans.lib.awtextra.AbsoluteConstraints(146, 84, 167, 28));

        jLabel121.setText("Select  denominations");
        jPanel62.add(jLabel121, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 158, -1, -1));

        na5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
        jPanel62.add(na5, new org.netbeans.lib.awtextra.AbsoluteConstraints(146, 151, 167, 28));

        jLabel126.setText("No. of denominations");
        jPanel62.add(jLabel126, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 197, -1, -1));

        na6.setToolTipText("Enter the number of denominations the you want to sell");
        na6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                na6ActionPerformed(evt);
            }
        });
        na6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                na6KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                na6KeyTyped(evt);
            }
        });
        jPanel62.add(na6, new org.netbeans.lib.awtextra.AbsoluteConstraints(146, 190, 167, 28));

        jLabel127.setText("You have to pay");
        jPanel62.add(jLabel127, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 125, -1, -1));

        na4.setEditable(false);
        na4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel62.add(na4, new org.netbeans.lib.awtextra.AbsoluteConstraints(146, 118, 167, 28));

        na_table.setAutoCreateRowSorter(true);
        na_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "Denominations", "No. denominations", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        na_table.setRowHeight(20);
        na_table.setShowHorizontalLines(false);
        na_table.setShowVerticalLines(false);
        na_table.getTableHeader().setReorderingAllowed(false);
        na_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                na_tableMouseClicked(evt);
            }
        });
        jScrollPane12.setViewportView(na_table);

        jPanel62.add(jScrollPane12, new org.netbeans.lib.awtextra.AbsoluteConstraints(319, 11, 685, 483));

        jLabel128.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel128.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel128.setText("Total");
        jPanel62.add(jLabel128, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 508, 167, -1));

        na12.setEditable(false);
        na12.setBackground(new java.awt.Color(255, 255, 255));
        na12.setText("0000");
        na12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jPanel62.add(na12, new org.netbeans.lib.awtextra.AbsoluteConstraints(861, 501, 143, 28));

        jLabel129.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel129.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-delete-bin-32.png"))); // NOI18N
        jLabel129.setText("Delete");
        jLabel129.setToolTipText("Hint this icon to remove the denomination(s)");
        jLabel129.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel129MouseClicked(evt);
            }
        });
        jPanel62.add(jLabel129, new org.netbeans.lib.awtextra.AbsoluteConstraints(319, 500, -1, -1));
        jPanel62.add(na9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 578, 164, -1));

        rat2.setText("jLabel71");
        jPanel62.add(rat2, new org.netbeans.lib.awtextra.AbsoluteConstraints(167, 574, -1, 0));
        jPanel62.add(na_list, new org.netbeans.lib.awtextra.AbsoluteConstraints(68, 582, 172, 0));

        jLabel130.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel130.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-replace-32.png"))); // NOI18N
        jLabel130.setText("Update");
        jLabel130.setToolTipText("Hint this icon to remove the denomination(s)");
        jLabel130.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel130MouseClicked(evt);
            }
        });
        jPanel62.add(jLabel130, new org.netbeans.lib.awtextra.AbsoluteConstraints(575, 501, -1, 30));

        na7.setText("jLabel71");
        jPanel62.add(na7, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 578, 100, 0));

        na10.setText("jLabel71");
        jPanel62.add(na10, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 576, -1, 0));

        na11.setText("jLabel71");
        jPanel62.add(na11, new org.netbeans.lib.awtextra.AbsoluteConstraints(121, 574, -1, 0));

        na1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
        na1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                na1ActionPerformed(evt);
            }
        });
        jPanel62.add(na1, new org.netbeans.lib.awtextra.AbsoluteConstraints(146, 11, 167, 28));

        jTabbedPane15.addTab("Cross Currency", new javax.swing.ImageIcon(getClass().getResource("/img/ex.png")), jPanel62); // NOI18N

        javax.swing.GroupLayout buyingLayout = new javax.swing.GroupLayout(buying);
        buying.setLayout(buyingLayout);
        buyingLayout.setHorizontalGroup(
            buyingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1033, Short.MAX_VALUE)
            .addGroup(buyingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(buyingLayout.createSequentialGroup()
                    .addComponent(jTabbedPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 1033, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        buyingLayout.setVerticalGroup(
            buyingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 620, Short.MAX_VALUE)
            .addGroup(buyingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(buyingLayout.createSequentialGroup()
                    .addComponent(jTabbedPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 620, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jPanel1.add(buying, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 140, 1000, 580));

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));

        jTabbedPane9.setBackground(new java.awt.Color(255, 255, 255));

        jPanel32.setBackground(new java.awt.Color(255, 255, 255));

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));

        ck_table.setAutoCreateRowSorter(true);
        ck_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Selected currencies", "Denominations", "No. denominations", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        ck_table.setRowHeight(20);
        ck_table.setShowHorizontalLines(false);
        ck_table.setShowVerticalLines(false);
        jScrollPane20.setViewportView(ck_table);

        jPanel24.setBackground(new java.awt.Color(255, 255, 255));
        jPanel24.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel24.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel122.setText("Select currency");
        jPanel24.add(jLabel122, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 28, -1, -1));
        jPanel24.add(ck3, new org.netbeans.lib.awtextra.AbsoluteConstraints(162, 91, 190, 28));

        jLabel123.setText("Denominations");
        jPanel24.add(jLabel123, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 63, -1, -1));

        jLabel124.setText("No. denominations");
        jPanel24.add(jLabel124, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 98, -1, -1));

        ck1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
        ck1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ck1ActionPerformed(evt);
            }
        });
        jPanel24.add(ck1, new org.netbeans.lib.awtextra.AbsoluteConstraints(162, 21, 190, 29));

        jLabel37.setText("Transfer to ");
        jPanel24.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 137, -1, -1));

        jLabel125.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel125.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-outgoing-data-32.png"))); // NOI18N
        jLabel125.setText("Send request");
        jLabel125.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel125MouseClicked(evt);
            }
        });
        jPanel24.add(jLabel125, new org.netbeans.lib.awtextra.AbsoluteConstraints(163, 199, 339, 28));

        ck4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
        ck4.setFocusable(false);
        ck4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ck4ActionPerformed(evt);
            }
        });
        jPanel24.add(ck4, new org.netbeans.lib.awtextra.AbsoluteConstraints(163, 130, 0, 29));

        ck5.setEditable(false);
        ck5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel24.add(ck5, new org.netbeans.lib.awtextra.AbsoluteConstraints(162, 165, 190, 28));

        jLabel38.setText("Position");
        jPanel24.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 172, -1, -1));

        ck2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
        jPanel24.add(ck2, new org.netbeans.lib.awtextra.AbsoluteConstraints(162, 56, 190, 29));

        mari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mariActionPerformed(evt);
            }
        });
        jPanel24.add(mari, new org.netbeans.lib.awtextra.AbsoluteConstraints(162, 130, 190, 28));

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(245, 245, 245)
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane20, javax.swing.GroupLayout.DEFAULT_SIZE, 981, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane20, javax.swing.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1001, Short.MAX_VALUE)
            .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 534, Short.MAX_VALUE)
            .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel32Layout.createSequentialGroup()
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jTabbedPane9.addTab("Send Request To The S.R.O", new javax.swing.ImageIcon(getClass().getResource("/img/sn.png")), jPanel32); // NOI18N

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1006, Short.MAX_VALUE)
            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel10Layout.createSequentialGroup()
                    .addComponent(jTabbedPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 1006, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 580, Short.MAX_VALUE)
            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jTabbedPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout requestsLayout = new javax.swing.GroupLayout(requests);
        requests.setLayout(requestsLayout);
        requestsLayout.setHorizontalGroup(
            requestsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1006, Short.MAX_VALUE)
            .addGroup(requestsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        requestsLayout.setVerticalGroup(
            requestsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 580, Short.MAX_VALUE)
            .addGroup(requestsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(requests, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 140, 1000, 580));

        jPanel28.setBackground(new java.awt.Color(255, 255, 255));

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));

        rb.setAutoCreateRowSorter(true);
        rb.setModel(new javax.swing.table.DefaultTableModel(
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
        rb.setRowHeight(20);
        rb.setShowHorizontalLines(false);
        rb.setShowVerticalLines(false);
        rb.getTableHeader().setReorderingAllowed(false);
        jScrollPane28.setViewportView(rb);

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(171, 171, 171)
                .addComponent(jScrollPane28, javax.swing.GroupLayout.PREFERRED_SIZE, 679, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(145, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jScrollPane28, javax.swing.GroupLayout.PREFERRED_SIZE, 449, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(57, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Local Currency", new javax.swing.ImageIcon(getClass().getResource("/img/vp.png")), jPanel15); // NOI18N

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));

        kb.setAutoCreateRowSorter(true);
        kb.setModel(new javax.swing.table.DefaultTableModel(
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
        kb.setRowHeight(20);
        kb.setShowHorizontalLines(false);
        kb.setShowVerticalLines(false);
        kb.getTableHeader().setReorderingAllowed(false);
        jScrollPane30.setViewportView(kb);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(171, 171, 171)
                .addComponent(jScrollPane30, javax.swing.GroupLayout.PREFERRED_SIZE, 679, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(145, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jScrollPane30, javax.swing.GroupLayout.PREFERRED_SIZE, 449, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(57, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Cross Currency", new javax.swing.ImageIcon(getClass().getResource("/img/up32.png")), jPanel13); // NOI18N

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2)
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2)
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
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(50, 50, 50)
                            .addComponent(uname)
                            .addGap(56, 56, 56)
                            .addComponent(upass)))
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 730, Short.MAX_VALUE)
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
                        by1.setText(b);

                    }
                    if (!c.matches(xz)) {
                        //sell2.addItem(" "); 
                        by2.addItem(b);
                        na1.addItem(b);

                    }

                }
            }
            if (bool == false) {

                JOptionPane.showMessageDialog(rootPane, "You have configured exchange rates against the base currency", "Base currency not available", JOptionPane.WARNING_MESSAGE);
            }

        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
        }

        rates.setVisible(false);
        reports.setVisible(false);
        transfers.setVisible(false);
        selling.setVisible(false);
        requests.setVisible(false);

        withdraw.setVisible(false);
        opening_balances.setVisible(false);
        //denominations.setVisible(false);
        if (y == 1000) {
            rates.setSize(1000, 580);
            reports.setSize(1000, 580);
            transfers.setSize(1000, 580);
            selling.setSize(1000, 580);
            requests.setSize(1000, 580);

            withdraw.setSize(1000, 580);
            opening_balances.setSize(1000, 580);
            buying.setSize(1000, 580);
            Thread th = new Thread() {
                public void run() {
                    try {
                        for (int i = 0; i >= 0; i--) {
                            Thread.sleep(5);
                            rates.setSize(i, 580);
                            reports.setSize(i, 580);
                            transfers.setSize(i, 580);
                            selling.setSize(i, 580);
                            requests.setSize(i, 580);

                            withdraw.setSize(i, 580);
                            opening_balances.setSize(i, 580);
                            buying.setSize(i, 580);
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
            selling.setSize(y, 580);
            requests.setSize(y, 580);
            transfers.setSize(y, 580);

            withdraw.setSize(y, 580);
            opening_balances.setSize(y, 580);
            buying.setSize(y, 580);
            Thread th1 = new Thread() {
                public void run() {
                    try {
                        for (int i = 0; i <= y; i++) {
                            Thread.sleep(5);
                            buying.show();
                            reports.setSize(y, 580);
                            transfers.setSize(y, 580);
                            selling.setSize(y, 580);
                            rates.setSize(y, 580);
                            transfers.setSize(y, 580);

                            withdraw.setSize(y, 580);
                            opening_balances.setSize(y, 580);
                            buying.setSize(i, 580);
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
    }//GEN-LAST:event_jLabel17MouseClicked

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
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
                        sell1.setText(b);
                    }
                    if (!c.matches(xz)) {
                        //sell2.addItem(" "); 
                        sell2.addItem(b);
                        cse1.addItem(b);

                    }

                }
            }
            if (bool == false) {

                JOptionPane.showMessageDialog(rootPane, "You have configured exchange rates against the base currency", "Base currency not available", JOptionPane.WARNING_MESSAGE);
            }

        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
        }

        rates.setVisible(false);
        reports.setVisible(false);
        transfers.setVisible(false);
        //branches.setVisible(false);
        requests.setVisible(false);

        withdraw.setVisible(false);
        opening_balances.setVisible(false);
        buying.setVisible(false);
        if (y == 1000) {
            rates.setSize(1000, 580);
            reports.setSize(1000, 580);
            transfers.setSize(1000, 580);
            selling.setSize(1000, 580);
            requests.setSize(1000, 580);

            withdraw.setSize(1000, 580);
            opening_balances.setSize(1000, 580);
            buying.setSize(1000, 580);
            Thread th = new Thread() {
                public void run() {
                    try {
                        for (int i = 0; i >= 0; i--) {
                            Thread.sleep(5);
                            rates.setSize(i, 580);
                            reports.setSize(i, 580);
                            transfers.setSize(i, 580);
                            selling.setSize(i, 580);
                            requests.setSize(i, 580);

                            withdraw.setSize(i, 580);
                            opening_balances.setSize(i, 580);
                            buying.setSize(i, 580);
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
            selling.setSize(y, 580);
            requests.setSize(y, 580);
            transfers.setSize(y, 580);

            withdraw.setSize(y, 580);
            opening_balances.setSize(y, 580);
            buying.setSize(y, 580);
            Thread th1 = new Thread() {
                public void run() {
                    try {
                        for (int i = 0; i <= y; i++) {
                            Thread.sleep(5);
                            selling.show();
                            reports.setSize(y, 580);
                            transfers.setSize(y, 580);
                            rates.setSize(y, 580);
                            selling.setSize(i, 580);
                            transfers.setSize(y, 580);

                            withdraw.setSize(y, 580);
                            opening_balances.setSize(y, 580);
                            buying.setSize(y, 580);
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
        DefaultTableModel dm1 = (DefaultTableModel) rb.getModel();
        dm1.setNumRows(0);
        DefaultTableModel dm = (DefaultTableModel) kb.getModel();
        dm.setNumRows(0);
        String xz = "true";
        DB_USER = uname.getText();
                DB_PAS = upass.getText();
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
                    float c = rs.getFloat("buying_rate");
                    float d = rs.getFloat("selling_rate");

                    //int f = rs.getInt("exchange_rate_id");
                    if (phy.getText().matches(a) || phy.getText().matches(b)) {
                        dm1.addRow(new Object[]{a, b, c, d});
                    }
                    if (!phy.getText().matches(a) && !phy.getText().matches(b)) {
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
        transfers.setVisible(false);
        selling.setVisible(false);
        requests.setVisible(false);

        withdraw.setVisible(false);
        opening_balances.setVisible(false);
        buying.setVisible(false);
        if (y == 1000) {
            rates.setSize(1000, 580);
            reports.setSize(1000, 580);
            transfers.setSize(1000, 580);
            selling.setSize(1000, 580);
            requests.setSize(1000, 580);

            withdraw.setSize(1000, 580);
            opening_balances.setSize(1000, 580);
            buying.setSize(1000, 580);
            Thread th = new Thread() {
                public void run() {
                    try {
                        for (int i = 0; i >= 0; i--) {
                            Thread.sleep(5);
                            rates.setSize(i, 580);
                            reports.setSize(i, 580);
                            transfers.setSize(i, 580);
                            selling.setSize(i, 580);
                            requests.setSize(i, 580);

                            withdraw.setSize(i, 580);
                            opening_balances.setSize(i, 580);
                            buying.setSize(i, 580);
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
            selling.setSize(y, 580);
            requests.setSize(y, 580);
            transfers.setSize(y, 580);

            withdraw.setSize(y, 580);
            opening_balances.setSize(y, 580);
            buying.setSize(y, 580);
            Thread th1 = new Thread() {
                public void run() {
                    try {
                        for (int i = 0; i <= y; i++) {
                            Thread.sleep(5);
                            rates.show();
                            reports.setSize(y, 580);
                            transfers.setSize(y, 580);
                            selling.setSize(y, 580);
                            rates.setSize(i, 580);
                            transfers.setSize(y, 580);

                            withdraw.setSize(y, 580);
                            opening_balances.setSize(y, 580);
                            buying.setSize(y, 580);
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
        DefaultComboBoxModel cb = new DefaultComboBoxModel();
        ck1.setModel(cb);
        DefaultComboBoxModel cb1 = new DefaultComboBoxModel();
        mari.setModel(cb1);
        DefaultComboBoxModel cb2 = new DefaultComboBoxModel();
        mari.setModel(cb2);
        DB_USER = uname.getText();
                DB_PAS = upass.getText();
        String query = "SELECT currency_name,short_code,base_currency,currency_id FROM currencies";
        String queryx = "SELECT fname,lname,position,branch FROM users where branch='" + branch.getText() + "'";
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
                    ck1.addItem(" ");
                    ck1.addItem(b);

                }
            }
            if (bool == false) {

                JOptionPane.showMessageDialog(rootPane, "Currently you don't have that currency at your branch", "Selected currency not available", JOptionPane.WARNING_MESSAGE);
            }

        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Something went wrong", JOptionPane.ERROR_MESSAGE);
        }
        try {
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
            stm = con.createStatement();
            rs = stm.executeQuery(queryx);
            boolean bool = rs.isBeforeFirst();
            while (rs.next()) {
                if (bool == true) {
                    String a = rs.getString("fname");
                    String b = rs.getString("lname");
                    String c = rs.getString("position");

                    mari.addItem(" ");
                    mari.addItem(a.concat(" ").concat(b));
                }
            }
            if (bool == false) {

                JOptionPane.showMessageDialog(rootPane, "Currently you don't have that currency at your branch", "Selected currency not available", JOptionPane.WARNING_MESSAGE);
            }

        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Something went wrong", JOptionPane.ERROR_MESSAGE);
        }

        rates.setVisible(false);
        reports.setVisible(false);
        transfers.setVisible(false);
        selling.setVisible(false);
        //operations.setVisible(false);
        withdraw.setVisible(false);
        opening_balances.setVisible(false);
        buying.setVisible(false);
        if (y == 1000) {
            rates.setSize(1000, 580);
            reports.setSize(1000, 580);
            transfers.setSize(1000, 580);
            selling.setSize(1000, 580);
            requests.setSize(1000, 580);

            withdraw.setSize(1000, 580);
            opening_balances.setSize(1000, 580);
            buying.setSize(1000, 580);
            Thread th = new Thread() {
                public void run() {
                    try {
                        for (int i = 0; i >= 0; i--) {
                            Thread.sleep(5);
                            rates.setSize(i, 580);
                            reports.setSize(i, 580);
                            transfers.setSize(i, 580);
                            selling.setSize(i, 580);
                            requests.setSize(i, 580);

                            withdraw.setSize(i, 580);
                            opening_balances.setSize(i, 580);
                            buying.setSize(i, 580);
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
            selling.setSize(y, 580);
            requests.setSize(y, 580);
            transfers.setSize(y, 580);

            withdraw.setSize(y, 580);
            opening_balances.setSize(y, 580);
            buying.setSize(y, 580);
            Thread th1 = new Thread() {
                public void run() {
                    try {
                        for (int i = 0; i <= y; i++) {
                            Thread.sleep(5);
                            requests.show();
                            reports.setSize(y, 580);
                            transfers.setSize(y, 580);
                            selling.setSize(y, 580);
                            requests.setSize(i, 580);
                            transfers.setSize(y, 580);

                            withdraw.setSize(y, 580);
                            opening_balances.setSize(y, 580);
                            buying.setSize(y, 580);
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
    }//GEN-LAST:event_jLabel30MouseClicked

    private void jLabel36MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel36MouseClicked
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
    }//GEN-LAST:event_jLabel36MouseClicked

    private void jLabel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseClicked
        rates.setVisible(false);
        reports.setVisible(false);
        transfers.setVisible(false);
        //branches.setVisible(false);
        requests.setVisible(false);

        withdraw.setVisible(false);
        opening_balances.setVisible(false);
        buying.setVisible(false);
        if (y == 1000) {
            rates.setSize(1000, 580);
            reports.setSize(1000, 580);
            transfers.setSize(1000, 580);
            selling.setSize(1000, 580);
            requests.setSize(1000, 580);

            withdraw.setSize(1000, 580);
            opening_balances.setSize(1000, 580);
            buying.setSize(1000, 580);
            Thread th = new Thread() {
                public void run() {
                    try {
                        for (int i = 0; i >= 0; i--) {
                            Thread.sleep(5);
                            rates.setSize(i, 580);
                            reports.setSize(i, 580);
                            transfers.setSize(i, 580);
                            selling.setSize(i, 580);
                            requests.setSize(i, 580);

                            withdraw.setSize(i, 580);
                            opening_balances.setSize(i, 580);
                            buying.setSize(i, 580);
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
            selling.setSize(y, 580);
            requests.setSize(y, 580);
            transfers.setSize(y, 580);

            withdraw.setSize(y, 580);
            opening_balances.setSize(y, 580);
            buying.setSize(y, 580);
            Thread th1 = new Thread() {
                public void run() {
                    try {
                        for (int i = 0; i <= y; i++) {
                            Thread.sleep(5);
                            selling.show();
                            reports.setSize(y, 580);
                            transfers.setSize(y, 580);
                            rates.setSize(y, 580);
                            selling.setSize(i, 580);
                            transfers.setSize(y, 580);

                            withdraw.setSize(y, 580);
                            opening_balances.setSize(y, 580);
                            buying.setSize(y, 580);
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

        selling.setVisible(false);
        requests.setVisible(false);

        withdraw.setVisible(false);
        opening_balances.setVisible(false);
        buying.setVisible(false);
        if (y == 1000) {
            rates.setSize(1000, 510);
            reports.setSize(1000, 510);
            transfers.setSize(1000, 510);
            selling.setSize(1000, 510);
            requests.setSize(1000, 510);

            withdraw.setSize(1000, 510);
            opening_balances.setSize(1000, 510);
            buying.setSize(1000, 510);
            Thread th = new Thread() {
                public void run() {
                    try {
                        for (int i = 0; i >= 0; i--) {
                            Thread.sleep(5);
                            rates.setSize(i, 580);
                            reports.setSize(i, 580);
                            transfers.setSize(i, 580);
                            selling.setSize(i, 580);
                            requests.setSize(i, 580);

                            withdraw.setSize(i, 580);
                            opening_balances.setSize(i, 580);
                            buying.setSize(i, 580);
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
            selling.setSize(y, 580);
            requests.setSize(y, 580);
            transfers.setSize(y, 580);

            withdraw.setSize(y, 580);
            opening_balances.setSize(y, 580);
            buying.setSize(y, 580);
            Thread th1 = new Thread() {
                public void run() {
                    try {
                        for (int i = 0; i <= y; i++) {
                            Thread.sleep(5);
                            transfers.show();
                            reports.setSize(y, 580);
                            transfers.setSize(y, 580);
                            selling.setSize(y, 580);
                            transfers.setSize(i, 580);
                            rates.setSize(y, 580);

                            withdraw.setSize(y, 580);
                            opening_balances.setSize(y, 580);
                            buying.setSize(y, 580);
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
        transfers.setVisible(false);
        selling.setVisible(false);
        requests.setVisible(false);

        //currencies.setVisible(false);
        opening_balances.setVisible(false);
        buying.setVisible(false);
        if (y == 1000) {
            rates.setSize(1000, 580);
            reports.setSize(1000, 580);
            transfers.setSize(1000, 580);
            selling.setSize(1000, 580);
            requests.setSize(1000, 580);

            withdraw.setSize(1000, 580);
            opening_balances.setSize(1000, 580);
            buying.setSize(1000, 580);
            Thread th = new Thread() {
                public void run() {
                    try {
                        for (int i = 0; i >= 0; i--) {
                            Thread.sleep(5);
                            rates.setSize(i, 580);
                            reports.setSize(i, 580);
                            transfers.setSize(i, 580);
                            selling.setSize(i, 580);
                            requests.setSize(i, 580);

                            withdraw.setSize(i, 580);
                            opening_balances.setSize(i, 580);
                            buying.setSize(i, 580);
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
            selling.setSize(y, 580);
            requests.setSize(y, 580);
            transfers.setSize(y, 580);

            withdraw.setSize(y, 580);
            opening_balances.setSize(y, 580);
            buying.setSize(y, 580);
            Thread th1 = new Thread() {
                public void run() {
                    try {
                        for (int i = 0; i <= y; i++) {
                            Thread.sleep(5);
                            withdraw.show();
                            reports.setSize(y, 580);
                            transfers.setSize(y, 580);
                            selling.setSize(y, 580);
                            rates.setSize(y, 580);
                            transfers.setSize(y, 580);

                            withdraw.setSize(i, 580);
                            opening_balances.setSize(y, 580);
                            buying.setSize(y, 580);
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
        transfers.setVisible(false);
        selling.setVisible(false);
        requests.setVisible(false);

        withdraw.setVisible(false);
        opening_balances.setVisible(false);
        //denominations.setVisible(false);
        if (y == 1000) {
            rates.setSize(1000, 580);
            reports.setSize(1000, 580);
            transfers.setSize(1000, 580);
            selling.setSize(1000, 580);
            requests.setSize(1000, 580);

            withdraw.setSize(1000, 580);
            opening_balances.setSize(1000, 580);
            buying.setSize(1000, 580);
            Thread th = new Thread() {
                public void run() {
                    try {
                        for (int i = 0; i >= 0; i--) {
                            Thread.sleep(5);
                            rates.setSize(i, 580);
                            reports.setSize(i, 580);
                            transfers.setSize(i, 580);
                            selling.setSize(i, 580);
                            requests.setSize(i, 580);

                            withdraw.setSize(i, 580);
                            opening_balances.setSize(i, 580);
                            buying.setSize(i, 580);
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
            selling.setSize(y, 580);
            requests.setSize(y, 580);
            transfers.setSize(y, 580);

            withdraw.setSize(y, 580);
            opening_balances.setSize(y, 580);
            buying.setSize(y, 580);
            Thread th1 = new Thread() {
                public void run() {
                    try {
                        for (int i = 0; i <= y; i++) {
                            Thread.sleep(5);
                            buying.show();
                            reports.setSize(y, 580);
                            transfers.setSize(y, 580);
                            selling.setSize(y, 580);
                            rates.setSize(y, 580);
                            transfers.setSize(y, 580);

                            withdraw.setSize(y, 580);
                            opening_balances.setSize(y, 580);
                            buying.setSize(i, 580);
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
        transfers.setVisible(false);
        selling.setVisible(false);
        requests.setVisible(false);

        withdraw.setVisible(false);
        //opening_balances.setVisible(false);
        buying.setVisible(false);
        if (y == 1000) {
            rates.setSize(1000, 580);
            reports.setSize(1000, 580);
            transfers.setSize(1000, 580);
            selling.setSize(1000, 580);
            requests.setSize(1000, 580);

            withdraw.setSize(1000, 580);
            opening_balances.setSize(1000, 580);
            buying.setSize(1000, 580);
            Thread th = new Thread() {
                public void run() {
                    try {
                        for (int i = 0; i >= 0; i--) {
                            Thread.sleep(5);
                            rates.setSize(i, 580);
                            reports.setSize(i, 580);
                            transfers.setSize(i, 580);
                            selling.setSize(i, 580);
                            requests.setSize(i, 580);

                            withdraw.setSize(i, 580);
                            opening_balances.setSize(i, 580);
                            buying.setSize(i, 580);
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
            selling.setSize(y, 580);
            requests.setSize(y, 580);
            transfers.setSize(y, 580);

            withdraw.setSize(y, 580);
            opening_balances.setSize(y, 580);
            buying.setSize(y, 580);
            Thread th1 = new Thread() {
                public void run() {
                    try {
                        for (int i = 0; i <= y; i++) {
                            Thread.sleep(5);
                            opening_balances.show();
                            reports.setSize(y, 580);
                            transfers.setSize(y, 580);
                            selling.setSize(y, 580);
                            rates.setSize(y, 580);
                            transfers.setSize(y, 580);

                            withdraw.setSize(y, 580);
                            opening_balances.setSize(i, 580);
                            buying.setSize(y, 580);
                        }
                    } catch (Exception e) {
                    }
                }
            };
            th1.start();
            y = 1000;
        }
    }//GEN-LAST:event_jLabel15MouseClicked

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked
        DefaultComboBoxModel cb = new DefaultComboBoxModel();
        pk1.setModel(cb);
        DefaultComboBoxModel modm = new DefaultComboBoxModel();
        rz1.setModel(modm);
        DB_USER = uname.getText();
                DB_PAS = upass.getText();
        String xz = "Strong Room Operator";
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
                    pk1.addItem(b);
                }
            }
            if (bool == false) {

                JOptionPane.showMessageDialog(rootPane, "Currently you don't have that currency at your branch", "Selected currency not available", JOptionPane.WARNING_MESSAGE);
            }

        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Something went wrong", JOptionPane.ERROR_MESSAGE);
        }

        DefaultTableModel dm1 = (DefaultTableModel) req_table.getModel();
        dm1.setNumRows(0);
        String query1 = "SELECT currency,denominations,no_denominations,total,date,request_from, request_id FROM requests where request_to='" + name.getText() + "' ";
        try {
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
            stm = con.createStatement();
            rs = stm.executeQuery(query1);
            boolean boolm = rs.isBeforeFirst();
            while (rs.next()) {
                if (boolm == true) {
                    String a = rs.getString("currency");
                    String b = rs.getString("denominations");
                    String c = rs.getString("no_denominations");
                    String d = rs.getString("total");
                    String e = rs.getString("request_from");
                    String f = rs.getString("date");
                    String g = rs.getString("request_id");

                    dm1.addRow(new Object[]{a, b, c, d, e, f, g});
                }
            }
            if (boolm == false) {

            }
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Something went wrong", JOptionPane.ERROR_MESSAGE);
        }

        String tgp = "Counter Clerk";
        String queryp = "SELECT subject,notification_message,recipients FROM notifications ";
        try {
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
            stm = con.createStatement();

            rs = stm.executeQuery(queryp);

            while (rs.next()) {

                String a = rs.getString("subject");
                String b = rs.getString("recipients");
                if (b.contains(tgp)) {
                    rz1.addItem(a);
                }

            }

        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Something went wrong", JOptionPane.ERROR_MESSAGE);
        }

        rates.setVisible(false);
        reports.setVisible(false);

        selling.setVisible(false);
        requests.setVisible(false);

        withdraw.setVisible(false);
        opening_balances.setVisible(false);
        buying.setVisible(false);
        if (y == 1000) {
            rates.setSize(1000, 510);
            reports.setSize(1000, 510);
            transfers.setSize(1000, 510);
            selling.setSize(1000, 510);
            requests.setSize(1000, 510);

            withdraw.setSize(1000, 510);
            opening_balances.setSize(1000, 510);
            buying.setSize(1000, 510);
            Thread th = new Thread() {
                public void run() {
                    try {
                        for (int i = 0; i >= 0; i--) {
                            Thread.sleep(5);
                            rates.setSize(i, 580);
                            reports.setSize(i, 580);
                            transfers.setSize(i, 580);
                            selling.setSize(i, 580);
                            requests.setSize(i, 580);

                            withdraw.setSize(i, 580);
                            opening_balances.setSize(i, 580);
                            buying.setSize(i, 580);
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
            selling.setSize(y, 580);
            requests.setSize(y, 580);
            transfers.setSize(y, 580);

            withdraw.setSize(y, 580);
            opening_balances.setSize(y, 580);
            buying.setSize(y, 580);
            Thread th1 = new Thread() {
                public void run() {
                    try {
                        for (int i = 0; i <= y; i++) {
                            Thread.sleep(5);
                            transfers.show();
                            reports.setSize(y, 580);
                            transfers.setSize(y, 580);
                            selling.setSize(y, 580);
                            transfers.setSize(i, 580);
                            rates.setSize(y, 580);

                            withdraw.setSize(y, 580);
                            opening_balances.setSize(y, 580);
                            buying.setSize(y, 580);
                        }
                    } catch (Exception e) {
                    }
                }
            };
            th1.start();
            y = 1000;
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

    private void jLabel106MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel106MouseClicked
        int input = JOptionPane.showConfirmDialog(null, " Are you sure that you want to close this application", "Providence says....", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (input == JOptionPane.NO_OPTION) {

        }
        if (input == JOptionPane.YES_OPTION) {
            System.exit(4);

        }
    }//GEN-LAST:event_jLabel106MouseClicked

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        DefaultComboBoxModel dk = new DefaultComboBoxModel();
        acc_currency.setModel(dk);
DB_USER = uname.getText();
                DB_PAS = upass.getText();
        String querie = "SELECT short_code FROM currencies";
        try {
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
            stm = con.createStatement();
            rs = stm.executeQuery(querie);
            boolean bool2 = rs.isBeforeFirst();
            while (rs.next()) {
                if (bool2 == true) {
                    String b = rs.getString("short_code");
                    acc_currency.addItem(" ");
                    acc_currency.addItem(b);

                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Clerk.class.getName()).log(Level.SEVERE, null, ex);
        }

        rates.setVisible(false);
        reports.setVisible(false);
        withdraw.setVisible(false);
        selling.setVisible(false);
        requests.setVisible(false);

        transfers.setVisible(false);
        //opening_balances.setVisible(false);
        buying.setVisible(false);
        if (y == 1000) {
            rates.setSize(1000, 580);
            reports.setSize(1000, 580);
            withdraw.setSize(1000, 580);
            selling.setSize(1000, 580);
            requests.setSize(1000, 580);

            transfers.setSize(1000, 580);
            opening_balances.setSize(1000, 580);
            buying.setSize(1000, 580);
            Thread th = new Thread() {
                public void run() {
                    try {
                        for (int i = 0; i >= 0; i--) {
                            Thread.sleep(5);
                            rates.setSize(i, 580);
                            reports.setSize(i, 580);
                            requests.setSize(i, 580);
                            selling.setSize(i, 580);
                            withdraw.setSize(i, 580);

                            transfers.setSize(i, 580);
                            opening_balances.setSize(i, 580);
                            buying.setSize(i, 580);
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
            selling.setSize(y, 580);
            requests.setSize(y, 580);
            withdraw.setSize(y, 580);

            transfers.setSize(y, 580);
            opening_balances.setSize(y, 580);
            buying.setSize(y, 580);
            Thread th1 = new Thread() {
                public void run() {
                    try {
                        for (int i = 0; i <= y; i++) {
                            Thread.sleep(5);
                            opening_balances.show();
                            reports.setSize(y, 580);
                            requests.setSize(y, 580);
                            selling.setSize(y, 580);
                            rates.setSize(y, 580);
                            withdraw.setSize(y, 580);

                            transfers.setSize(y, 580);
                            opening_balances.setSize(i, 580);
                            buying.setSize(y, 580);
                        }
                    } catch (Exception e) {
                    }
                }
            };
            th1.start();
            y = 1000;
        }
    }//GEN-LAST:event_jLabel11MouseClicked

    private void jLabel32MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel32MouseClicked
        int input = JOptionPane.showConfirmDialog(null, " Are you sure that you want to change your password", "Providence says....", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (input == JOptionPane.NO_OPTION) {

        }
        if (input == JOptionPane.YES_OPTION) {
            DB_USER = uname.getText();
                DB_PAS = upass.getText();
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
    }//GEN-LAST:event_jLabel32MouseClicked

    private void ck1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ck1ActionPerformed
        if (!ck1.getSelectedItem().equals(" ")) {
            DefaultComboBoxModel cb = new DefaultComboBoxModel();
            ck2.setModel(cb);
DB_USER = uname.getText();
                DB_PAS = upass.getText();
            String query = "SELECT currency,denominations,total FROM branch_accounts where branch='" + branch.getText() + "' and currency='" + ck1.getSelectedItem().toString() + "'";
            try {
                Class.forName(DB_DRIVER);
                con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                stm = con.createStatement();
                rs = stm.executeQuery(query);
                boolean bool = rs.isBeforeFirst();
                while (rs.next()) {
                    if (bool == true) {
                        String a = rs.getString("currency");
                        String b = rs.getString("denominations");
                        String c = rs.getString("total");
                        ck2.addItem(b);

                    }
                }
                if (bool == false) {

                    JOptionPane.showMessageDialog(rootPane, "Currently you don't have that currency at your branch", "Selected currency not available", JOptionPane.WARNING_MESSAGE);
                }

            } catch (ClassNotFoundException | SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Something went wrong", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (ck1.getSelectedItem().equals(" ")) {

        }
    }//GEN-LAST:event_ck1ActionPerformed

    private void jLabel125MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel125MouseClicked
        java.util.Date date = new java.util.Date();
        long t = date.getTime();
        java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(t);
        java.sql.Date sqlDate = new java.sql.Date(t);

        if (ck1.getSelectedItem().equals(" ")) {
            JOptionPane.showMessageDialog(rootPane, "Please select the currency you want to request ", "Some information missing to send your request", JOptionPane.WARNING_MESSAGE);
        }
        if (ck2.getSelectedItem().equals(" ")) {
            JOptionPane.showMessageDialog(rootPane, "Please select the denomations of the above selected currency you want to request ", "Some information missing to send your request", JOptionPane.WARNING_MESSAGE);
        }
        if (mari.getSelectedItem().equals(" ")) {
            JOptionPane.showMessageDialog(rootPane, "Who do you want to receive this request ? ", "Some information missing to send your request", JOptionPane.WARNING_MESSAGE);
        }
        if (ck3.getText().isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "How many number of denominations do you want from " + mari.getSelectedItem().toString(), "Some information missing to send your request", JOptionPane.WARNING_MESSAGE);
        }
        if (!ck1.getSelectedItem().equals(" ") && !ck2.getSelectedItem().equals(" ") && !mari.getSelectedItem().equals(" ") && !ck3.getText().isEmpty()) {
            DefaultTableModel dm = (DefaultTableModel) ck_table.getModel();
            float a = Float.parseFloat(ck2.getSelectedItem().toString());

            double ab = Double.parseDouble(ck2.getSelectedItem().toString());

            float b = Float.parseFloat(ck3.getText());
            float c = a * b;
            int ay = Integer.parseInt(ck3.getText(), 10);
            int ay1 = Integer.parseInt(ck3.getText(), 10);
            JOptionPane.showMessageDialog(rootPane, ay + ay1);
DB_USER = uname.getText();
                DB_PAS = upass.getText();
            String brn = "Strong Room Operator";
            String query1 = "SELECT currency,denominations,no_denominations,total,request_from FROM requests where currency='" + ck1.getSelectedItem().toString() + "' and denominations='" + ab + "' and request_from='" + name.getText() + "' ";
            try {
                Class.forName(DB_DRIVER);
                con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                stm = con.createStatement();
                rs = stm.executeQuery(query1);
                boolean boolm = rs.isBeforeFirst();
                while (rs.next()) {
                    if (boolm == true) {
                        int ax = rs.getInt("no_denominations");
                        float ax1 = rs.getFloat("no_denominations");
                        float ax2 = rs.getFloat("total");
                        int az = ax + ay;
                        float az1 = ax2 + c;
                        SQLRun run = new SQLRun();
                        String sqlq = "Update requests set no_denominations='" + az + "' , total='" + az1 + "' where request_from='" + name.getText() + "' and currency='" + ck1.getSelectedItem().toString() + "' and denominations='" + ab + "'";
                        int updated = run.sqlUpdate(sqlq);
                        if (updated > 0) {

                            try {
                                con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                pst = con.prepareStatement("insert into requests_history values(?,?,?,?,?,?,?)");

                                pst.setString(1, ck1.getSelectedItem().toString());
                                pst.setDouble(2, ab);
                                pst.setInt(3, ay);
                                pst.setDouble(4, c);
                                pst.setString(5, name.getText());
                                pst.setString(6, mari.getSelectedItem().toString());
                                pst.setTimestamp(7, sqlTimestamp);
                                int ip = pst.executeUpdate();
                                if (ip > 0) {
                                    JOptionPane.showMessageDialog(null, "You have successfully send a request to " + mari.getSelectedItem().toString());
                                    dm.addRow(new Object[]{ck1.getSelectedItem().toString(), ck2.getSelectedItem().toString(), b, c});
                                    ck3.setText("");
                                }
                            } catch (SQLException ev) {
                                JOptionPane.showMessageDialog(null, ev.getMessage());

                            }
                        }

                    }
                }
                if (boolm == false) {
                    try {
                        con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                        pst = con.prepareStatement("insert into requests values(?,?,?,?,?,?,?)");
                        pst.setString(1, ck1.getSelectedItem().toString());
                        pst.setDouble(2, ab);
                        pst.setInt(3, ay);
                        pst.setDouble(4, c);
                        pst.setString(5, name.getText());
                        pst.setString(6, mari.getSelectedItem().toString());
                        pst.setTimestamp(7, sqlTimestamp);
                        int i = pst.executeUpdate();
                        if (i > 0) {
                            JOptionPane.showMessageDialog(null, "You have successfully send a request to " + mari.getSelectedItem().toString());
                            dm.addRow(new Object[]{ck1.getSelectedItem().toString(), ck2.getSelectedItem().toString(), b, c});
                            ck3.setText("");
                        }
                    } catch (SQLException ev) {
                        JOptionPane.showMessageDialog(null, ev.getMessage());

                    }
                    try {
                        con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                        pst = con.prepareStatement("insert into requests_history values(?,?,?,?,?,?,?)");

                        pst.setString(1, ck1.getSelectedItem().toString());
                        pst.setDouble(2, ab);
                        pst.setInt(3, ay);
                        pst.setDouble(4, c);
                        pst.setString(5, name.getText());
                        pst.setString(6, mari.getSelectedItem().toString());
                        pst.setTimestamp(7, sqlTimestamp);
                        int ip = pst.executeUpdate();
                        if (ip > 0) {

                        }
                    } catch (SQLException ev) {
                        JOptionPane.showMessageDialog(null, ev.getMessage());

                    }
                }
            } catch (ClassNotFoundException | SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Something went wrong", JOptionPane.ERROR_MESSAGE);
            }

        }

    }//GEN-LAST:event_jLabel125MouseClicked

    private void jLabel11MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel11MouseEntered

    private void acc_currencyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_acc_currencyActionPerformed
        if (!acc_currency.getSelectedItem().equals(" ")) {
            DefaultTableModel dk = (DefaultTableModel) acc_table.getModel();
            dk.setNumRows(0);
DB_USER = uname.getText();
                DB_PAS = upass.getText();
            String querie = "SELECT account_holder,currency,denominations,no_denominations,total FROM clerk_accounts where account_holder='" + name.getText() + "' and currency='" + acc_currency.getSelectedItem().toString() + "'";
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
                        dk.addRow(new Object[]{b, c, d});
                        getSum();

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

    private void pk1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pk1ActionPerformed
        if (!pk1.getSelectedItem().equals(" ")) {
            DefaultComboBoxModel dk = new DefaultComboBoxModel();
            pk2.setModel(dk);
DB_USER = uname.getText();
                DB_PAS = upass.getText();
            String querie = "SELECT account_holder,currency,denominations,no_denominations,total FROM clerk_accounts where account_holder='" + name.getText() + "' and currency='" + pk1.getSelectedItem().toString() + "'";
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
                        pk2.addItem(" ");
                        pk2.addItem(b);

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
    }//GEN-LAST:event_pk1ActionPerformed

    private void pk2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pk2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pk2ActionPerformed

    private void jLabel96MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel96MouseClicked
        java.util.Date date = new java.util.Date();
        long t = date.getTime();
        java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(t);
        java.sql.Date sqlDate = new java.sql.Date(t);

        if (pk1.getSelectedItem().equals(" ")) {
            JOptionPane.showMessageDialog(null, "Please select the currency", "Some information missing", JOptionPane.WARNING_MESSAGE);
        }
        if (pk2.getSelectedItem().equals(" ")) {
            JOptionPane.showMessageDialog(null, "Please select the denomiantions", "Some information missing", JOptionPane.WARNING_MESSAGE);

        }
        if (pk3.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Enter the number of denominations you want to remit to the brach account", "Some information missing", JOptionPane.WARNING_MESSAGE);

        }

        if (!pk1.getSelectedItem().equals(" ") && !pk2.getSelectedItem().equals(" ") && !pk3.getText().isEmpty()) {
            float a_denom = Float.parseFloat(pk2.getSelectedItem().toString());
            int a_num = Integer.parseInt(pk3.getText(), 10);
DB_USER = uname.getText();
                DB_PAS = upass.getText();
            String query = "SELECT branch,currency,denominations,no_denominations,total FROM branch_accounts where branch='" + branch.getText() + "' and currency='" + pk1.getSelectedItem().toString() + "' and denominations='" + a_denom + "'";

            String querie = "SELECT account_holder,currency,denominations,no_denominations,total FROM clerk_accounts where account_holder='" + name.getText() + "' and currency='" + pk1.getSelectedItem().toString() + "' and denominations='" + a_denom + "'";
            try {
                Class.forName(DB_DRIVER);
                con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                stm = con.createStatement();

                rs = stm.executeQuery(querie);

                float nwd = Float.parseFloat(pk3.getText());
                float sub_total = nwd * a_denom;

                boolean bool2 = rs.isBeforeFirst();
                while (rs.next()) {
                    if (bool2 == true) {
                        String a = rs.getString("currency");
                        float b = rs.getFloat("denominations");
                        int c = rs.getInt("no_denominations");
                        float d = rs.getFloat("total");

                        if (d < sub_total) {
                            JOptionPane.showMessageDialog(null, "The amount of money you want to send is more than what you have ", "Insufficent funds", JOptionPane.WARNING_MESSAGE);
                        }
                        if (d >= sub_total) {
                            float new_total = d - sub_total;//new balance in clerk's account

                            int new_num = c - a_num;
                            SQLRun run = new SQLRun();
                            String sqlq = "Update clerk_accounts set no_denominations='" + new_num + "' , total='" + new_total + "' where account_holder='" + name.getText() + "' and denominations='" + a_denom + "'";
                            int updated = run.sqlUpdate(sqlq);
                            if (updated > 0) {
                                try {
                                    Class.forName(DB_DRIVER);
                                    con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                    stm = con.createStatement();

                                    rs = stm.executeQuery(query);
                                    boolean bool = rs.isBeforeFirst();
                                    while (rs.next()) {

                                        if (bool == true) {
                                            String a1 = rs.getString("currency");
                                            float b1 = rs.getFloat("denominations");
                                            int c1 = rs.getInt("no_denominations");
                                            float d1 = rs.getFloat("total");

                                            float total = d1 + sub_total;
                                            int num = c1 + a_num;

                                            SQLRun run1 = new SQLRun();
                                            String sqlq1 = "Update branch_accounts set no_denominations='" + num + "' , total='" + total + "' where branch='" + branch.getText() + "' and currency='" + pk1.getSelectedItem().toString() + "' and denominations='" + a_denom + "' ";
                                            int updated1 = run1.sqlUpdate(sqlq1);
                                            if (updated1 > 0) {

                                                try {
                                                    Class.forName(DB_DRIVER);
                                                    String recs = "Strong Room Operator";
                                                    con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                                    pst = con.prepareStatement("insert into clerks_operators values(?,?,?,?,?,?,?,?,?,?)");
                                                    pst.setString(1, name.getText());
                                                    pst.setString(2, posi.getText());
                                                    pst.setString(3, "null");
                                                    pst.setString(4, recs);
                                                    pst.setString(5, branch.getText());
                                                    pst.setString(6, pk1.getSelectedItem().toString());
                                                    float wq = Float.parseFloat(pk2.getSelectedItem().toString());
                                                    pst.setFloat(7, wq);
                                                    int wt = Integer.parseInt(pk3.getText(), 10);
                                                    pst.setInt(8, wt);
                                                    float av = (wt * wq);
                                                    pst.setFloat(9, av);
                                                    pst.setTimestamp(10, sqlTimestamp);

                                                    int i = pst.executeUpdate();
                                                    if (i > 0) {
                                                        JOptionPane.showMessageDialog(null, "You have successfully remitted money to the branch account", "Sending ...", JOptionPane.INFORMATION_MESSAGE);

                                                        //pk3.setText("");
                                                    }
                                                } catch (SQLException ev) {
                                                    JOptionPane.showMessageDialog(null, ev.getMessage());

                                                } catch (ClassNotFoundException ex) {
                                                    Logger.getLogger(Finance_Dep.class.getName()).log(Level.SEVERE, null, ex);
                                                }

                                                DefaultTableModel dm = (DefaultTableModel) pk_table.getModel();
                                                dm.addRow(new Object[]{pk2.getSelectedItem().toString(), a_denom, a_num, sub_total});
                                            }
                                        }
                                    }
                                    if (bool == false) {

                                        String sqlqp = "Update clerk_accounts set no_denominations='" + new_num + "' , total='" + new_total + "' where account_holder='" + name.getText() + "' and denominations='" + a_denom + "'";
                                        int updatedp = run.sqlUpdate(sqlqp);
                                        if (updatedp > 0) {
                                            try {
                                                Class.forName(DB_DRIVER);
                                                String recs = "Strong Room Operator";
                                                con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                                pst = con.prepareStatement("insert into clerks_operators values(?,?,?,?,?,?,?,?,?,?)");
                                                pst.setString(1, name.getText());
                                                pst.setString(2, posi.getText());
                                                pst.setString(3, "null");
                                                pst.setString(4, recs);
                                                pst.setString(5, branch.getText());
                                                pst.setString(6, pk1.getSelectedItem().toString());
                                                float wq = Float.parseFloat(pk2.getSelectedItem().toString());
                                                pst.setFloat(7, wq);
                                                int wt = Integer.parseInt(pk3.getText(), 10);
                                                pst.setInt(8, wt);
                                                float av = (wt * wq);
                                                pst.setFloat(9, av);
                                                pst.setTimestamp(10, sqlTimestamp);

                                                int i = pst.executeUpdate();
                                                if (i > 0) {
                                                    JOptionPane.showMessageDialog(null, "You have successfully remitted money to the branch account", "Sending ...", JOptionPane.INFORMATION_MESSAGE);

                                                    //pk3.setText("");
                                                }
                                            } catch (SQLException ev) {
                                                JOptionPane.showMessageDialog(null, ev.getMessage());

                                            } catch (ClassNotFoundException ex) {
                                                Logger.getLogger(Finance_Dep.class.getName()).log(Level.SEVERE, null, ex);
                                            }

                                            try {
                                                Class.forName(DB_DRIVER);
                                                String recs = "Strong Room Operator";
                                                con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                                pst = con.prepareStatement("insert into branch values(?,?,?,?,?,?,?)");
                                                pst.setString(1, branch.getText());
                                                pst.setString(2, pk1.getSelectedItem().toString());
                                                float wq = Float.parseFloat(pk2.getSelectedItem().toString());
                                                pst.setFloat(7, wq);
                                                int wt = Integer.parseInt(pk3.getText(), 10);
                                                pst.setInt(4, wt);
                                                float av = (wt * wq);
                                                pst.setFloat(5, av);
                                                pst.setString(6, name.getText());

                                                pst.setTimestamp(7, sqlTimestamp);

                                                int i = pst.executeUpdate();
                                                if (i > 0) {
                                                    JOptionPane.showMessageDialog(null, "You have successfully remitted money to the branch account", "Sending ...", JOptionPane.INFORMATION_MESSAGE);

                                                    pk3.setText("");

                                                }
                                            } catch (SQLException ev) {
                                                JOptionPane.showMessageDialog(null, ev.getMessage());

                                            } catch (ClassNotFoundException ex) {
                                                Logger.getLogger(Finance_Dep.class.getName()).log(Level.SEVERE, null, ex);
                                            }

                                        }
                                    }

                                } catch (Exception e) {
                                    JOptionPane.showMessageDialog(null, e.getMessage(), "Exception Error", JOptionPane.WARNING_MESSAGE);

                                }
                            }
                        }

                    }

                }
                if (bool2 == false) {
                    JOptionPane.showMessageDialog(null, "You don't have the currency you selected ", "Currency not found", JOptionPane.WARNING_MESSAGE);
                    //acc_total.setText(" ");
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
            } catch (ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "An errot occured", JOptionPane.ERROR_MESSAGE);
            }

        }

    }//GEN-LAST:event_jLabel96MouseClicked

    private void pk3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pk3KeyTyped
        char c = evt.getKeyChar();
        if (!Character.isDigit(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_pk3KeyTyped

    private void req_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_req_tableMouseClicked
        DefaultTableModel model = (DefaultTableModel) req_table.getModel();
        cx_currency.setText(model.getValueAt(req_table.getSelectedRow(), 0).toString());
        cx_denom.setText(model.getValueAt(req_table.getSelectedRow(), 1).toString());
        cx_num.setText(model.getValueAt(req_table.getSelectedRow(), 2).toString());
        cx_total.setText(model.getValueAt(req_table.getSelectedRow(), 3).toString());
        cx_from.setText(model.getValueAt(req_table.getSelectedRow(), 4).toString());
        cx_id.setText(model.getValueAt(req_table.getSelectedRow(), 6).toString());
    }//GEN-LAST:event_req_tableMouseClicked

    private void req_tableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_req_tableKeyPressed
        java.util.Date date = new java.util.Date();
        long t = date.getTime();
        java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(t);
        java.sql.Date sqlDate = new java.sql.Date(t);

        DefaultTableModel mode = (DefaultTableModel) req_table.getModel();
        if (req_table.getSelectedRow() == -1) {
            if (req_table.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "The table is empty", "Providence says....", JOptionPane.WARNING_MESSAGE);

            } else {
                JOptionPane.showMessageDialog(null, " You must select a request", "Providence says....", JOptionPane.WARNING_MESSAGE);
            }
        } else {

            int input = JOptionPane.showConfirmDialog(null, " Are you sure that you want to send " + cx_currency.getText() + " " + cx_id.getText() + " " + "from the system ?", "Providence says....", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (input == JOptionPane.NO_OPTION) {

            }
            if (input == JOptionPane.YES_OPTION) {
                int xb = Integer.parseInt(cx_num.getText());

                int xv = Integer.parseInt(cx_id.getText());
                float zm = Float.parseFloat(cx_total.getText());

                float zb = Float.parseFloat(cx_denom.getText());
                mode.removeRow(req_table.getSelectedRow());
DB_USER = uname.getText();
                DB_PAS = upass.getText();
                String query = "SELECT account_holder,currency,denominations,no_denominations,total FROM clerk_accounts where currency='" + cx_currency.getText() + "' and account_holder='" + name.getText() + "' and denominations='" + zb + "'";
                try {
                    Class.forName(DB_DRIVER);
                    con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                    stm = con.createStatement();
                    rs = stm.executeQuery(query);
                    boolean bool = rs.isBeforeFirst();
                    while (rs.next()) {
                        String xl = rs.getString("account_holder");
                        String x2 = rs.getString("currency");
                        float x3 = rs.getFloat("denominations");
                        int x4 = rs.getInt("no_denominations");
                        float x5 = rs.getFloat("total");

                        int x6 = x4 - xb;
                        float x7 = x5 - zm;

                        if (name.getText().matches(xl) && cx_currency.getText().matches(x2) && zb == x3) {
                            if (x5 < zm) {
                                JOptionPane.showMessageDialog(null, "The number of requested denomiantions of $" + cx_currency.getText() + " " + cx_denom.getText() + " " + "are more than what the S. R. O holds ", " Insufficient denominations....", JOptionPane.INFORMATION_MESSAGE);

                            }
                            if (x4 < xb) {
                                JOptionPane.showMessageDialog(null, "The amount requested is more than what the S. R. O have ", " Insufficient funds....", JOptionPane.INFORMATION_MESSAGE);

                            }
                            if (x5 >= zm && x4 >= xb) {
                                SQLRun run = new SQLRun();
                                String sqlq = "Update clerk_accounts set no_denominations='" + x6 + "' , total='" + x7 + "' where account_holder='" + name.getText() + "' and currency='" + cx_currency.getText() + "' and denominations='" + zb + "' ";
                                int updated = run.sqlUpdate(sqlq);
                                if (updated > 0) {
                                    JOptionPane.showMessageDialog(null, "You have successfully deposited subtracting script !!!!!!" + zm + " " + "into the account ", " Sending request....", JOptionPane.INFORMATION_MESSAGE);

                                }

                                String query1 = "SELECT currency,denominations,no_denominations,total, account_holder FROM clerk_accounts where account_holder='" + cx_from.getText() + "' and currency='" + cx_currency.getText() + "' and denominations='" + zb + "'";
                                try {
                                    rs = stm.executeQuery(query1);
                                    boolean boom = rs.isBeforeFirst();
                                    while (rs.next()) {
                                        if (boom == true) {

                                            int y3 = rs.getInt("no_denominations");
                                            float y4 = rs.getFloat("total");

                                            int y5 = y3 + xb;
                                            float y6 = y4 + zm;
                                            SQLRun run1 = new SQLRun();

                                            String sqlq1 = "Update clerk_accounts set no_denominations='" + y5 + "' , total='" + y6 + "' where account_holder='" + cx_from.getText() + "' and currency='" + cx_currency.getText() + "' and denominations='" + zb + "' ";

                                            int updated1 = run1.sqlUpdate(sqlq1);

                                            if (updated1 > 0) {
                                                try {
                                                    con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                                    pst = con.prepareStatement("insert into transfers_log values(?,?,?,?,?,?,?)");

                                                    pst.setString(1, name.getText());
                                                    pst.setString(2, cx_from.getText());
                                                    pst.setString(3, cx_currency.getText());
                                                    pst.setDouble(4, zb);
                                                    pst.setInt(5, xb);
                                                    pst.setDouble(6, zm);

                                                    pst.setTimestamp(7, sqlTimestamp);
                                                    int p = pst.executeUpdate();
                                                    if (p > 0) {
                                                        SQLRun objSQLRun = new SQLRun();
                                                        String sql = "DELETE FROM requests WHERE request_id='" + xv + "'";
                                                        int deleted = objSQLRun.sqlUpdate(sql);
                                                        if (deleted > 0) {
                                                            JOptionPane.showMessageDialog(null, "You have successfully deposited " + zm + " " + "into the account ", " Sending request....", JOptionPane.INFORMATION_MESSAGE);
                                                            cx_id.setText("");
                                                        } else {
                                                            if (cx_id.getText() == null) {

                                                            } else {
                                                                JOptionPane.showMessageDialog(null, "User with an ID :" + cx_id.getText() + " does not exist", "Providence says...", JOptionPane.ERROR_MESSAGE);

                                                            }
                                                        }
                                                    }
                                                } catch (HeadlessException | SQLException ev) {
                                                    JOptionPane.showMessageDialog(null, ev.getMessage(), "Providence says...", JOptionPane.ERROR_MESSAGE);

                                                }

                                                try {
                                                    con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                                    pst = con.prepareStatement("insert into clerks_operators values(?,?,?,?,?,?,?,?,?,?)");

                                                    pst.setString(1, name.getText());
                                                    pst.setString(2, posi.getText());
                                                    pst.setString(3, cx_from.getText());
                                                    pst.setString(4, posi.getText());
                                                    pst.setString(5, branch.getText());
                                                    pst.setString(6, cx_currency.getText());
                                                    pst.setDouble(7, zb);
                                                    pst.setInt(8, xb);
                                                    pst.setDouble(9, zm);

                                                    pst.setTimestamp(10, sqlTimestamp);
                                                    int p = pst.executeUpdate();
                                                    if (p > 0) {
                                                        JOptionPane.showMessageDialog(null, "You have successfully inserted in clerks operations table");
                                                    }
                                                } catch (HeadlessException | SQLException ev) {
                                                    JOptionPane.showMessageDialog(null, ev.getMessage(), "Providence says..XXXX.", JOptionPane.ERROR_MESSAGE);

                                                }

                                            }

                                        }
                                    }
                                    if (boom == false) {
                                        try {
                                            con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                            pst = con.prepareStatement("insert into clerk_accounts values(?,?,?,?,?)");

                                            pst.setString(1, cx_currency.getText());
                                            pst.setDouble(2, zb);
                                            pst.setInt(3, xb);
                                            pst.setDouble(4, zm);
                                            pst.setString(5, cx_from.getText());
                                            int i = pst.executeUpdate();
                                            if (i > 0) {

                                                try {
                                                    con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                                    pst = con.prepareStatement("insert into transfers_log values(?,?,?,?,?,?,?)");

                                                    pst.setString(1, name.getText());
                                                    pst.setString(2, cx_from.getText());
                                                    pst.setString(3, cx_currency.getText());
                                                    pst.setDouble(4, zb);
                                                    pst.setInt(5, xb);
                                                    pst.setDouble(6, zm);
                                                    pst.setTimestamp(7, sqlTimestamp);
                                                    int p = pst.executeUpdate();
                                                    if (p > 0) {
                                                        SQLRun objSQLRun = new SQLRun();
                                                        String sql = "DELETE FROM requests WHERE request_id='" + xv + "'";
                                                        int deleted = objSQLRun.sqlUpdate(sql);
                                                        if (deleted > 0) {
                                                            SQLRun run2 = new SQLRun();
                                                            String sqlq2 = "Update clerk_accounts set no_denominations='" + x6 + "' , total='" + x7 + "' where account_holder='" + name.getText() + "' and currency='" + cx_currency.getText() + "' and denominations='" + zb + "' ";
                                                            int updated2 = run2.sqlUpdate(sqlq2);
                                                            if (updated2 > 0) {
                                                                JOptionPane.showMessageDialog(null, "You have successfully deposited subtracting script !!!!!!" + zm + " " + "into the account ", " Sending request....", JOptionPane.INFORMATION_MESSAGE);

                                                            }
                                                            cx_id.setText("");
                                                        } else {
                                                            if (cx_id.getText() == null) {

                                                            } else {
                                                                JOptionPane.showMessageDialog(null, "User with an ID :" + cx_id.getText() + " does not exist", "Providence says...", JOptionPane.ERROR_MESSAGE);

                                                            }
                                                        }
                                                    }
                                                } catch (HeadlessException | SQLException ev) {
                                                    JOptionPane.showMessageDialog(null, ev.getMessage(), "Providence says...WWWW", JOptionPane.ERROR_MESSAGE);

                                                }

                                            }
                                        } catch (Exception e) {
                                            JOptionPane.showMessageDialog(null, e.getMessage(), " jskjckdc", JOptionPane.INFORMATION_MESSAGE);
                                        }
                                        try {
                                            con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                            pst = con.prepareStatement("insert into clerks_operators values(?,?,?,?,?,?,?,?,?,?)");

                                            pst.setString(1, name.getText());
                                            pst.setString(2, posi.getText());
                                            pst.setString(3, cx_from.getText());
                                            pst.setString(4, posi.getText());
                                            pst.setString(5, branch.getText());
                                            pst.setString(6, cx_currency.getText());
                                            pst.setDouble(7, zb);
                                            pst.setInt(8, xb);
                                            pst.setDouble(9, zm);

                                            pst.setTimestamp(10, sqlTimestamp);
                                            int p = pst.executeUpdate();
                                            if (p > 0) {

                                            }
                                        } catch (HeadlessException | SQLException ev) {
                                            JOptionPane.showMessageDialog(null, ev.getMessage(), "TTTProvidence says...", JOptionPane.ERROR_MESSAGE);

                                        }

                                    }
                                } catch (Exception e) {
                                }
                            }
                        }

                    }
                    if (bool == false) {
                        JOptionPane.showMessageDialog(null, "You don't have the currency that you wwant to send", "Providence says...", JOptionPane.ERROR_MESSAGE);

                    }

//                    String query1 = "SELECT currency,denominations,no_denominations,total, account_holder FROM clerk_accounts where account_holder='" + cx_from.getText() + "' and currency='" + cx_currency.getText() + "' and denominations='" + zb + "'";
//                    try {
//                        ResultSet rs1 = null;
//                        rs1 = stm.executeQuery(query1);
//                        boolean boom = rs1.isBeforeFirst();
//                        while (rs1.next()) {
//                            if (bool == true) {
//                                JOptionPane.showMessageDialog(null, "Something is wrong", "Providence says...", JOptionPane.ERROR_MESSAGE);
//                            }
//                        }
//                        if (boom == false) {
//                            JOptionPane.showMessageDialog(null, "Nothing in branch", "Providence says...", JOptionPane.ERROR_MESSAGE);
//
//                        }
//                    } catch (Exception e) {
//                    }
                } catch (ClassNotFoundException | SQLException e) {
                    //JOptionPane.showMessageDialog(null, e.getMessage(),"System Error",JOptionPane.ERROR_MESSAGE);
                }

            }
        }
    }//GEN-LAST:event_req_tableKeyPressed

    private void jLabel24MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel24MouseClicked
        java.util.Date date = new java.util.Date();
        long t = date.getTime();
        java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(t);
        java.sql.Date sqlDate = new java.sql.Date(t);
DB_USER = uname.getText();
                DB_PAS = upass.getText();
        DefaultTableModel dm = (DefaultTableModel) req_table.getModel();
        if (dm.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Your table is empty", "Process terminated", JOptionPane.ERROR_MESSAGE);
        }
        if (dm.getRowCount() > 0) {
            if (cx_currency.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please select the row with amount of money you want to send", "Waiting for your decision ...", JOptionPane.WARNING_MESSAGE);
            }
            if (!cx_currency.getText().isEmpty()) {
                int input = JOptionPane.showConfirmDialog(null, " Please note that you are about to send less than the requested. Do you want to proceeed ?", "Providence says....", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (input == JOptionPane.NO_OPTION) {

                }
                if (input == JOptionPane.YES_OPTION) {
                    try {
                        String num_num = JOptionPane.showInputDialog(null, "How much do you want to send", "Fill in the field below", JOptionPane.QUESTION_MESSAGE);
                        float a_ab = Float.parseFloat(num_num);

                        float a_ac = Float.parseFloat(cx_denom.getText());

                        String tot_total = JOptionPane.showInputDialog(null, "Enter the number of denomiantions that you want to add up to " + a_ab, "Fill in the field below", JOptionPane.QUESTION_MESSAGE);
                        float a_ad = Float.parseFloat(tot_total);
                        int a_ad1 = Integer.parseInt(tot_total);

                        int xv = Integer.parseInt(cx_id.getText(), 10);

                        float sumx = a_ac * a_ad;

                        float a_ae = Float.parseFloat(cx_total.getText());

                        if (a_ab >= a_ae || sumx > a_ae) {
                            JOptionPane.showMessageDialog(null, "Please be adviced that this option of Custom b/c is meant to send the money less than requested", "Reminder", JOptionPane.WARNING_MESSAGE);
                        }
                        
                        if (a_ab < a_ae || sumx > a_ae) {
                            String query = "SELECT account_holder,currency,denominations,no_denominations,total FROM clerk_accounts where account_holder='" + name.getText() + "' and denominations='" + a_ac + "' and currency='" + cx_currency.getText() + "'";
                            try {
                                Class.forName(DB_DRIVER);
                                con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                stm = con.createStatement();
                                rs = stm.executeQuery(query);
                                boolean bool = rs.isBeforeFirst();
                                while (rs.next()) {
                                    if (bool == true) {

                                        int x4 = rs.getInt("no_denominations");
                                        float x5 = rs.getFloat("total");

                                        if (x5 < sumx || x4 < a_ad) {

                                        }
                                        if (x5 >= sumx || x4 >= a_ad) {
                                            String query1 = "SELECT currency,denominations,no_denominations,total, account_holder FROM clerk_accounts where account_holder='" + cx_from.getText() + "' and currency='" + cx_currency.getText() + "' and denominations='" + a_ac + "'";
                                            try {
                                                ResultSet rs1 = null;
                                                rs1 = stm.executeQuery(query1);
                                                boolean boom = rs1.isBeforeFirst();
                                                while (rs1.next()) {
                                                    if (boom == true) {

                                                        int y3 = rs1.getInt("no_denominations");
                                                        float y4 = rs1.getFloat("total");

                                                        int y5 = y3 + a_ad1;
                                                        float y6 = y4 + sumx;

                                                        float new_sum = x5 - sumx;
                                                        int new_num = x4 - a_ad1;
                                                        SQLRun run = new SQLRun();
                                                        SQLRun run1 = new SQLRun();
                                                        SQLRun run2 = new SQLRun();

                                                        String sqlq = "Update clerk_accounts set no_denominations='" + y5 + "' , total='" + y6 + "' where account_holder='" + cx_from.getText() + "' and currency='" + cx_currency.getText() + "' and denominations='" + a_ac + "' ";
                                                        String sqlq1 = "Update clerk_accounts set no_denominations='" + new_num + "' , total='" + new_sum + "' where account_holder='" + name.getText() + "' and currency='" + cx_currency.getText() + "' and denominations='" + a_ac + "' ";

                                                        int updated = run.sqlUpdate(sqlq);
                                                        int updated1 = run1.sqlUpdate(sqlq1);
                                                        if (updated > 0) {
                                                            if (updated1 > 0) {
                                                                try {
                                                                    con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                                                    pst = con.prepareStatement("insert into transfers_log values(?,?,?,?,?,?,?)");

                                                                    pst.setString(1, name.getText());
                                                                    pst.setString(2, cx_from.getText());
                                                                    pst.setDouble(4, a_ac);
                                                                    pst.setInt(5, a_ad1);
                                                                    pst.setDouble(6, sumx);
                                                                    pst.setString(3, cx_currency.getText());
                                                                    pst.setTimestamp(7, sqlTimestamp);
                                                                    int p = pst.executeUpdate();
                                                                    if (p > 0) {

                                                                    }
                                                                    String query2 = "SELECT request_from,currency,denominations,no_denominations,total FROM requests where request_id='" + xv + "'";
                                                                    try {
                                                                        ResultSet rs2 = null;
                                                                        rs2 = stm.executeQuery(query2);
                                                                        boolean boox = rs2.isBeforeFirst();
                                                                        while (rs2.next()) {
                                                                            if (boox == true) {
                                                                                String z1 = rs2.getString("currency");
                                                                                String z2 = rs2.getString("denominations");
                                                                                int z3 = rs2.getInt("no_denominations");
                                                                                float z4 = rs2.getFloat("total");

                                                                                float new_tota = z4 - sumx;
                                                                                int new_nm = z3 - a_ad1;

                                                                                String sqlq3 = "Update requests set no_denominations='" + new_nm + "' , total='" + new_tota + "' where request_id='" + xv + "' ";
                                                                                int updated3 = run2.sqlUpdate(sqlq3);
                                                                                if (updated3 > 0) {
                                                                                    JOptionPane.showMessageDialog(null, "An updated on the total amount of money requested occured" + sumx + " " + "into the account ", " Sending request....", JOptionPane.INFORMATION_MESSAGE);

                                                                                }
                                                                            }
                                                                        }
                                                                    } catch (SQLException es) {

                                                                    }
                                                                } catch (HeadlessException | SQLException ev) {
                                                                    JOptionPane.showMessageDialog(null, ev.getMessage(), "Providence says...", JOptionPane.ERROR_MESSAGE);

                                                                }

                                                                try {
                                                                    con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                                                    pst = con.prepareStatement("insert into clerks_operators values(?,?,?,?,?,?,?,?,?,?)");

                                                                    pst.setString(1, name.getText());
                                                                    pst.setString(2, posi.getText());
                                                                    pst.setString(3, cx_from.getText());
                                                                    pst.setString(4, posi.getText());
                                                                    pst.setString(5, branch.getText());
                                                                    pst.setString(6, cx_currency.getText());
                                                                    pst.setDouble(7, a_ac);
                                                                    pst.setInt(8, a_ad1);
                                                                    pst.setDouble(9, sumx);

                                                                    pst.setTimestamp(10, sqlTimestamp);
                                                                    int p = pst.executeUpdate();
                                                                    if (p > 0) {

                                                                    }
                                                                } catch (HeadlessException | SQLException ev) {
                                                                    JOptionPane.showMessageDialog(null, ev.getMessage(), "Providence says...", JOptionPane.ERROR_MESSAGE);

                                                                }

                                                            }
                                                        }
                                                    }
                                                }
                                                if (boom == false) {
                                                    JOptionPane.showMessageDialog(null, "Nothing in clerk", "Providence says...", JOptionPane.ERROR_MESSAGE);
                                                    try {
                                                        con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                                        pst = con.prepareStatement("insert into clerk_accounts values(?,?,?,?,?)");

                                                        pst.setString(1, cx_currency.getText());
                                                        pst.setDouble(2, a_ac);
                                                        pst.setInt(3, a_ad1);
                                                        pst.setDouble(4, sumx);
                                                        pst.setString(5, cx_from.getText());
                                                        int i = pst.executeUpdate();
                                                        if (i > 0) {
                                                            JOptionPane.showMessageDialog(null, "Thanks you", "Providence says...", JOptionPane.INFORMATION_MESSAGE);

                                                            try {
                                                                con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                                                pst = con.prepareStatement("insert into transfers_log values(?,?,?,?,?,?,?)");

                                                                pst.setString(1, name.getText());
                                                                pst.setString(2, cx_from.getText());
                                                                pst.setDouble(4, a_ac);
                                                                pst.setInt(5, a_ad1);
                                                                pst.setDouble(6, sumx);
                                                                pst.setString(3, cx_currency.getText());
                                                                pst.setTimestamp(7, sqlTimestamp);
                                                                int p = pst.executeUpdate();
                                                                if (p > 0) {

                                                                }
                                                            } catch (HeadlessException | SQLException ev) {
                                                                JOptionPane.showMessageDialog(null, ev.getMessage(), "Providence says...", JOptionPane.ERROR_MESSAGE);

                                                            }
                                                            try {
                                                                con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                                                pst = con.prepareStatement("insert into clerks_operators values(?,?,?,?,?,?,?,?,?,?)");

                                                                pst.setString(1, name.getText());
                                                                pst.setString(2, posi.getText());
                                                                pst.setString(3, cx_from.getText());
                                                                pst.setString(4, posi.getText());
                                                                pst.setString(5, branch.getText());
                                                                pst.setString(6, cx_currency.getText());
                                                                pst.setDouble(7, a_ac);
                                                                pst.setInt(8, a_ad1);
                                                                pst.setDouble(9, sumx);

                                                                pst.setTimestamp(10, sqlTimestamp);
                                                                int p = pst.executeUpdate();
                                                                if (p > 0) {

                                                                }
                                                            } catch (HeadlessException | SQLException ev) {
                                                                JOptionPane.showMessageDialog(null, ev.getMessage(), "Providence says...", JOptionPane.ERROR_MESSAGE);

                                                            }

                                                            String query2 = "SELECT request_from,currency,denominations,no_denominations,total FROM requests where request_id='" + xv + "'";
                                                            try {
                                                                ResultSet rs2 = null;
                                                                rs2 = stm.executeQuery(query2);
                                                                boolean boox = rs2.isBeforeFirst();
                                                                while (rs2.next()) {
                                                                    if (boox == true) {
                                                                        String z1 = rs2.getString("currency");
                                                                        String z2 = rs2.getString("denominations");
                                                                        int z3 = rs2.getInt("no_denominations");
                                                                        float z4 = rs2.getFloat("total");

                                                                        float new_tota = z4 - sumx;
                                                                        int new_nm = z3 - a_ad1;
                                                                        SQLRun run2 = new SQLRun();
                                                                        String sqlq3 = "Update requests set no_denominations='" + new_nm + "' , total='" + new_tota + "' where request_id='" + xv + "' ";
                                                                        int updated3 = run2.sqlUpdate(sqlq3);
                                                                        if (updated3 > 0) {
                                                                            JOptionPane.showMessageDialog(null, "An updated on the total amount of money requested occured" + sumx + " " + "into the account ", " Sending request....", JOptionPane.INFORMATION_MESSAGE);

                                                                        }

                                                                        float new_sum = x5 - sumx;
                                                                        int new_num = x4 - a_ad1;
                                                                        String sqlq = "Update clerk_accounts set no_denominations='" + new_num + "' , total='" + new_sum + "' where account_holder='" + name.getText() + "' and currency='" + cx_currency.getText() + "' and denominations='" + a_ac + "' ";
                                                                        int updated = run2.sqlUpdate(sqlq);
                                                                        if (updated > 0) {
                                                                            JOptionPane.showMessageDialog(null, "An updated on the total amount of money requested occured" + sumx + " " + "into the account ", " Sending request....", JOptionPane.INFORMATION_MESSAGE);

                                                                        }
                                                                    }
                                                                }
                                                            } catch (SQLException es) {

                                                            }

                                                        }
                                                    } catch (Exception e) {
                                                        JOptionPane.showMessageDialog(null, e.getMessage(), " jskjckdc", JOptionPane.INFORMATION_MESSAGE);
                                                    }

                                                }
                                            } catch (Exception e) {
                                                JOptionPane.showMessageDialog(null, e.getMessage(), "Providence says...", JOptionPane.ERROR_MESSAGE);

                                            }
                                        }
                                    }

                                }
                            } catch (SQLException e) {
                                JOptionPane.showMessageDialog(null, e.getMessage(), "Providence says...", JOptionPane.ERROR_MESSAGE);

                            }

                        }

                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Providence says...", JOptionPane.ERROR_MESSAGE);

                    }

                }
            }

        }


    }//GEN-LAST:event_jLabel24MouseClicked

    private void jLabel40MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel40MouseClicked
        int xv = Integer.parseInt(cx_id.getText(), 10);
        SQLRun objSQLRun = new SQLRun();
        String sql = "DELETE FROM requests WHERE request_id='" + xv + "'";
        int deleted = objSQLRun.sqlUpdate(sql);
        if (deleted > 0) {
            JOptionPane.showMessageDialog(null, "Request remove from the stack ", " Sending request....", JOptionPane.INFORMATION_MESSAGE);
            cx_id.setText("");
        } else {
            if (cx_id.getText() == null) {

            } else {
                JOptionPane.showMessageDialog(null, "User with an ID :" + cx_id.getText() + " does not exist", "Providence says...", JOptionPane.ERROR_MESSAGE);

            }
        }
    }//GEN-LAST:event_jLabel40MouseClicked

    private void sell3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sell3KeyTyped
        char c = evt.getKeyChar();
        if (!Character.isDigit(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_sell3KeyTyped

    private void sell2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sell2ActionPerformed
        DB_USER = uname.getText();
                DB_PAS = upass.getText();
        if (!sell2.getSelectedItem().equals(" ")) {
            DefaultComboBoxModel dk = new DefaultComboBoxModel();
            sell5.setModel(dk);

            String querie = "SELECT base_currency,quote_currency,buying_rate,selling_rate FROM config_rates where base_currency='" + sell1.getText() + "' and quote_currency='" + sell2.getSelectedItem().toString() + "' ";
            String query = "SELECT denominations FROM clerk_accounts where currency='" + sell2.getSelectedItem().toString() + "' ";

            try {
                Class.forName(DB_DRIVER);
                con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                stm = con.createStatement();
                rs = stm.executeQuery(querie);
                boolean bool2 = rs.isBeforeFirst();
                while (rs.next()) {
                    if (bool2 == true) {
                        double a = rs.getDouble("selling_rate");
                        double a1 = Math.round(a * 10) / 10.0;
                        rat.setText(a1 + "");
                    }

                }
                if (bool2 == false) {
                    JOptionPane.showMessageDialog(null, "Exchange rates yet to be configured, please contact the Finance Personel ", "Exchange rates (null)", JOptionPane.WARNING_MESSAGE);
                    //acc_total.setText(" ");
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Clerk.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                Class.forName(DB_DRIVER);
                con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                stm = con.createStatement();

                rs = stm.executeQuery(query);
                boolean bool = rs.isBeforeFirst();
                while (rs.next()) {
                    if (bool == true) {
                        double b = rs.getDouble("denominations");

                        sell5.addItem(b + "");
                        sell3.setEditable(true);
                    }
                }
                if (bool == false) {
                    JOptionPane.showMessageDialog(null, "You don't have the denominations of the quote currency you seected\n, request " + sell2.getSelectedItem().toString() + " " + "from any of the strong room operator at your branch\n,highlighting the denominations you want ", "Denominations for" + sell2.getSelectedItem().toString() + " " + "not found", JOptionPane.WARNING_MESSAGE);
                    sell3.setEditable(false);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Clerk.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_sell2ActionPerformed

    private void sell3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sell3KeyPressed

        int key = evt.getKeyCode();
        if (key == 10) {
            if (!sell3.getText().isEmpty()) {
                int input = JOptionPane.showConfirmDialog(null, " Are you sure that you want to sell " + sell3.getText() + " " + "?", "Providence says....", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (input == JOptionPane.NO_OPTION) {

                }
                if (input == JOptionPane.YES_OPTION) {
                    double a = Double.parseDouble(rat.getText());
                    double b = Double.parseDouble(sell3.getText());
                    double c = a * b;
                    double ab = Math.round(c * 10) / 10.0;
                    sell4.setText(c + "");
                }

                if (sell3.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Enter the amountof money that you want to sell", "Exchange rates (null)", JOptionPane.WARNING_MESSAGE);

                }

            }

        }

    }//GEN-LAST:event_sell3KeyPressed

    private void sell6KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sell6KeyTyped
        char c = evt.getKeyChar();
        if (!Character.isDigit(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_sell6KeyTyped

    private void sell6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sell6KeyPressed
DB_USER = uname.getText();
                DB_PAS = upass.getText();
        java.util.Date date = new java.util.Date();
        long t = date.getTime();
        java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(t);
        java.sql.Date sqlDate = new java.sql.Date(t);

        DefaultTableModel dm = (DefaultTableModel) sell_table.getModel();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String randstring = "";
        Random random = new Random();
        int length = 5;
        char[] text = new char[length];
        for (int ip = 0; ip < length; ip++) {
            text[ip] = characters.charAt(random.nextInt(characters.length()));
        }
        for (int ip = 0; ip < text.length; ip++) {
            randstring += text[ip];
        }
        int key = evt.getKeyCode();
        if (key == 10) {
            if (!sell6.getText().isEmpty()) {

                double d = Double.parseDouble(sell3.getText());//AMOUNT TO BE SOLD
                double a = Double.parseDouble(sell5.getSelectedItem().toString());//DENOMINATION (OUT)
                double b = Double.parseDouble(sell6.getText());//NUMBER OF DENOMINATIONS (OUT)
                int b1 = Integer.parseInt(sell6.getText());
                double c = a * b;//[DENOMINATION VALUE * NUMBER]
                double xa = Double.parseDouble(sell7.getText());//ITERATIONS
                double xy = xa + c;//ITERATION + [DENOMINATION * NUMBER]

                ResultSet rs = null;

                String query = "SELECT currency,denominations,no_denominations,total FROM clerk_accounts where account_holder='" + name.getText() + "' and denominations='" + a + "' and currency='" + sell2.getSelectedItem().toString() + "' ";
                try {
                    Class.forName(DB_DRIVER);
                    con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                    stm = con.createStatement();
                    rs = stm.executeQuery(query);
                    boolean bool2 = rs.isBeforeFirst();
                    String op = "selling";
                    while (rs.next()) {
                        if (bool2 == true) {

                            double za = rs.getDouble("total"); //TOTAL BALANCE IN CLERK'S ACCOUNT
                            int ze = rs.getInt("no_denominations"); //NUMBER OF DENOMINATIONS IN CLERK'S ACCOUNT

                            double da = za - c;
                            int de = ze - b1;
                            if (ze < b1) {//
                                JOptionPane.showMessageDialog(null, "The denominations that you want you sell are more than what you have in your account", "Number of available denominations exceeded ", JOptionPane.ERROR_MESSAGE);
                            }
                            if (ze >= b1) {

                                if (d < xy) {

                                }
                                if (d >= xy) {
                                    if (my_list.getText().isEmpty()) {

                                        // #$%^&
                                        SQLRun run = new SQLRun();
                                        String sqlq = "Update clerk_accounts set no_denominations='" + de + "' , total='" + da + "' where account_holder='" + name.getText() + "' and denominations='" + a + "' and currency='" + sell2.getSelectedItem().toString() + "'";
                                        int updated = run.sqlUpdate(sqlq);
                                        if (updated > 0) {

                                            try {
                                                con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                                pst = con.prepareStatement("insert into transactions values(?,?,?,?,?,?,?,?,?,?)");

                                                double sg = Double.parseDouble(rat.getText());
                                                pst.setString(1, sell1.getText());
                                                pst.setDouble(2, a);
                                                pst.setInt(3, b1);
                                                pst.setDouble(4, c);
                                                pst.setString(5, op);
                                                pst.setDouble(6, sg);
                                                pst.setTimestamp(7, sqlTimestamp);
                                                pst.setString(8, name.getText());
                                                pst.setString(9, branch.getText());
                                                pst.setString(10, swp.getText());

                                                int p = pst.executeUpdate();
                                                if (p > 0) {
                                                    my_list.setText(sell5.getSelectedItem().toString().concat(" ").concat(","));
                                                    dm.addRow(new Object[]{randstring, sell5.getSelectedItem().toString(), b1, c});
                                                    sell7.setText(xy + "");
                                                }
                                            } catch (HeadlessException | SQLException ev) {
                                                JOptionPane.showMessageDialog(null, ev.getMessage(), "An errorr occured", JOptionPane.ERROR_MESSAGE);

                                            }
                                        }
                                    }
                                    if (!my_list.getText().isEmpty()) {
                                        String abx = my_list.getText();
                                        if (abx.contains(sell5.getSelectedItem().toString())) {

                                        }
                                        if (!abx.contains(sell5.getSelectedItem().toString())) {

                                            if (ze < b1) {
                                                JOptionPane.showMessageDialog(null, "The denominations that you want you sell are more than what you have in your account", "Number of available denominations exceeded ", JOptionPane.ERROR_MESSAGE);
                                            }
                                            if (ze >= b1) {
                                                SQLRun run = new SQLRun();
                                                String sqlq = "Update clerk_accounts set no_denominations='" + de + "' , total='" + da + "' where account_holder='" + name.getText() + "' and denominations='" + a + "' and currency='" + sell2.getSelectedItem().toString() + "'";
                                                int updated = run.sqlUpdate(sqlq);
                                                if (updated > 0) {

                                                    try {
                                                        con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                                        pst = con.prepareStatement("insert into transactions values(?,?,?,?,?,?,?,?,?,?)");

                                                        double sg = Double.parseDouble(rat.getText());
                                                        pst.setString(1, sell1.getText());
                                                        pst.setDouble(2, a);
                                                        pst.setInt(3, b1);
                                                        pst.setDouble(4, c);
                                                        pst.setString(5, op);
                                                        pst.setDouble(6, sg);
                                                        pst.setTimestamp(7, sqlTimestamp);
                                                        pst.setString(8, name.getText());
                                                        pst.setString(9, branch.getText());
                                                        pst.setString(10, swp.getText());

                                                        int p = pst.executeUpdate();
                                                        if (p > 0) {
                                                            my_list.setText(sell5.getSelectedItem().toString().concat(" ").concat(",").concat(abx));
                                                            dm.addRow(new Object[]{randstring, sell5.getSelectedItem().toString(), b1, c});
                                                            sell7.setText(xy + "");
                                                        }
                                                    } catch (HeadlessException | SQLException ev) {
                                                        JOptionPane.showMessageDialog(null, ev.getMessage(), "An errorr occured", JOptionPane.ERROR_MESSAGE);

                                                    }
                                                }
                                            }

                                            if (bool2 == false) {
                                                JOptionPane.showMessageDialog(null, "Please adviced that you don't have " + sell2.getSelectedItem().toString() + " " + a + " " + "in your account as a counter clerk.\nTry to liase with your strong room operator to send you so that you can serve the customer", "Denomination not found", JOptionPane.ERROR_MESSAGE);

                                            }
                                        }

                                    }

                                }
                                if (d == xy) {
                                    receiving r = new receiving();
                                    r.setVisible(true);
                                    double sg = Double.parseDouble(rat.getText());
                                    if (my_list.getText().isEmpty()) {
                                        receiving.r1.setText(sell1.getText());
                                        receiving.r2.setText(sell4.getText());
                                        receiving.rcc1.setText(sell2.getSelectedItem().toString());
                                        receiving.rcrate.setText(sg + "");
                                        receiving.rcout.setText(sell3.getText());
                                        receiving.reg.setText(reg.getText());
                                        receiving.configs.setText(configs.getText());
                                        receiving.branch.setText(branch.getText());
                                        receiving.name.setText(this.name.getText());

                                        dm.addRow(new Object[]{randstring, sell5.getSelectedItem().toString(), b1, c});

                                        ResultSet rs1 = null;
                                        String querie = "SELECT currency,denomination_value FROM denominations where currency='" + sell2.getSelectedItem().toString() + "' ";
                                        try {
                                            rs1 = stm.executeQuery(querie);
                                            boolean bool = rs1.isBeforeFirst();
                                            while (rs1.next()) {
                                                if (bool == true) {
                                                    String zv = rs1.getString("denomination_value");
                                                    receiving.r3.addItem(zv);
                                                }
                                            }
                                        } catch (SQLException xp) {

                                        }
                                    }
                                    if (!my_list.getText().isEmpty()) {
                                        receiving.r1.setText(sell1.getText());
                                        receiving.r2.setText(sell4.getText());
                                        receiving.rcc1.setText(sell2.getSelectedItem().toString());
                                        receiving.rcrate.setText(sg + "");
                                        receiving.rcout.setText(sell3.getText());
                                        receiving.reg.setText(reg.getText());
                                        receiving.branch.setText(branch.getText());
                                        receiving.name.setText(this.name.getText());
                                        receiving.configs.setText(configs.getText());

                                        ResultSet rs1 = null;
                                        String querie = "SELECT currency,denomination_value FROM denominations where currency='" + sell2.getSelectedItem().toString() + "' ";
                                        try {
                                            rs1 = stm.executeQuery(querie);
                                            boolean bool = rs1.isBeforeFirst();
                                            while (rs1.next()) {
                                                if (bool == true) {
                                                    String zv = rs1.getString("denomination_value");
                                                    receiving.r3.addItem(zv);
                                                }
                                            }
                                        } catch (SQLException xp) {

                                        }
                                    }

                                }

                            }

                        }
                    }
                    if (bool2 == false) {
                        JOptionPane.showMessageDialog(null, "Please adviced that you don't have " + sell2.getSelectedItem().toString() + " " + a + " " + "in your account as a counter clerk.\nTry to liase with your strong room operator to send you so that you can serve the customer", "Denomination not found", JOptionPane.ERROR_MESSAGE);

                    }
                } catch (ClassNotFoundException | SQLException ex) {
                    // JOptionPane.showMessageDialog(null, ex.getMessage(), "Error message", JOptionPane.ERROR_MESSAGE);
                }

            }
        }
    }//GEN-LAST:event_sell6KeyPressed

    private void sell_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sell_tableMouseClicked
        DefaultTableModel model = (DefaultTableModel) sell_table.getModel();
        swp.setText(model.getValueAt(sell_table.getSelectedRow(), 0).toString());
        carr.setText(model.getValueAt(sell_table.getSelectedRow(), 1).toString());
        dmt.setText(model.getValueAt(sell_table.getSelectedRow(), 2).toString());
        amtt.setText(model.getValueAt(sell_table.getSelectedRow(), 3).toString());
    }//GEN-LAST:event_sell_tableMouseClicked

    private void jLabel81MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel81MouseClicked
        DB_USER = uname.getText();
                DB_PAS = upass.getText();
        DefaultTableModel mode = (DefaultTableModel) sell_table.getModel();
        if (sell_table.getSelectedRow() == -1) {
            if (sell_table.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "The table is empty", "Providence says....", JOptionPane.WARNING_MESSAGE);

            } else {
                JOptionPane.showMessageDialog(null, " You must select denominations", "Providence says....", JOptionPane.WARNING_MESSAGE);
            }
        } else {

            int input = JOptionPane.showConfirmDialog(null, " Are you sure that you want update the number od denominations of  " + sell2.getSelectedItem().toString() + " " + sell2.getSelectedItem().toString() + " ?", "Providence says....", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (input == JOptionPane.NO_OPTION) {

            }
            if (input == JOptionPane.YES_OPTION) {
                mode.removeRow(sell_table.getSelectedRow());

                String a = JOptionPane.showInputDialog("How many denomiantions do you want ? ");

                try {
                    int a1 = Integer.parseInt(a);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Please enter integers for number of denominations", "Incorrect data format", JOptionPane.ERROR_MESSAGE);
                }

                int a1 = Integer.parseInt(a);
                int a2 = Integer.parseInt(dmt.getText(), 10);//NUMBER OF DENOMINATIONS
                double b1 = Double.parseDouble(amtt.getText());//TOTAL (DENOMINATION VALUE * NUMBER OF DENOMINATIONS)
                double c1 = Double.parseDouble(sell7.getText());//ITERATION
                double d1 = c1 - b1;
                double e1 = Double.parseDouble(carr.getText());//DENOMINATIONS
                double p1 = Double.parseDouble(sell4.getText());//AMOUNT SUPPOSED TO BE RECEIVED BY THE CASHIER
                double q1 = Double.parseDouble(sell3.getText());//AMOUNT TO BE SOLD
                double f1 = Double.parseDouble(a);
                double g1 = e1 * f1;
                double pr = d1 + g1;
                if ((d1 + g1) > q1) {
                    JOptionPane.showMessageDialog(null, "An update on the number of denominations that you want to sell exceeds " + sell2.getSelectedItem().toString() + " " + "$" + " " + q1, "", JOptionPane.WARNING_MESSAGE);
                }
                if ((d1 + g1) <= q1) {

                    ResultSet rs2 = null;
                    String querie = "SELECT currency,denominations,no_denominations,total FROM clerk_accounts where account_holder='" + name.getText() + "' and denominations='" + e1 + "' and currency='" + sell2.getSelectedItem().toString() + "' ";
                    try {
                        Class.forName(DB_DRIVER);
                        con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                        stm = con.createStatement();
                        rs2 = stm.executeQuery(querie);
                        boolean bool2 = rs2.isBeforeFirst();
                        while (rs2.next()) {
                            if (bool2 == true) {
                                //JOptionPane.showMessageDialog(null, "Good one");
                                double bx = rs2.getDouble("total");
                                int vx = rs2.getInt("no_denominations");

                                double dw1 = (bx + b1) - g1;
                                int dw2 = (vx + a2) - a1;

                                double pa = bx + b1;
                                int pe = vx + a1;

                                SQLRun run = new SQLRun();
                                String sqlq = "Update clerk_accounts set no_denominations='" + dw2 + "' , total='" + dw1 + "' where account_holder='" + name.getText() + "' and denominations='" + e1 + "' and currency='" + sell2.getSelectedItem().toString() + "'";
                                int updated = run.sqlUpdate(sqlq);
                                if (updated > 0) {
                                    String jdbcUrl = "jdbc:postgresql:" + configs.getText();
                                    String username = "postgres";
                                    String password = "danieloh24";
                                    String sql = "Update transactions set no_denominations='" + pe + "' , total='" + pa + "' where trans_id='" + swp.getText() + "' ";

                                    try (Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
                                            Statement stmt = conn.createStatement();) {

                                        stmt.executeUpdate(sql);
                                        mode.addRow(new Object[]{swp.getText(), e1, a, g1});
                                        sell7.setText(pr + "");
                                        double av = Double.parseDouble(sell7.getText());
                                        double aq = Double.parseDouble(sell3.getText());

                                        if (av == aq) {
                                            ResultSet rs = null;
                                            receiving r = new receiving();
                                            r.setVisible(true);

                                            receiving.r1.setText(sell1.getText());
                                            receiving.r2.setText(sell4.getText());
                                            ResultSet rsy = null;
                                            String query = "SELECT currency,denomination_value FROM denominations where currency='" + sell2.getSelectedItem().toString() + "' ";
                                            try {
                                                rsy = stm.executeQuery(query);
                                                boolean bool = rsy.isBeforeFirst();
                                                while (rsy.next()) {
                                                    if (bool == true) {
                                                        String zv = rsy.getString("denomination_value");
                                                        receiving.r3.addItem(zv);
                                                    }
                                                }
                                            } catch (SQLException xp) {

                                            }
                                        }
                                    } catch (SQLException e) {
                                        JOptionPane.showMessageDialog(null, e.getSQLState());
                                    }

                                }

                            }
                        }
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Something might gone wrong ", JOptionPane.ERROR_MESSAGE);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(Clerk.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

            }
        }

    }//GEN-LAST:event_jLabel81MouseClicked

    private void jLabel80MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel80MouseClicked
        DefaultTableModel mode = (DefaultTableModel) sell_table.getModel();
DB_USER = uname.getText();
                DB_PAS = upass.getText();
        if (swp.getText().isEmpty()) {
            if (mode.getRowCount() == 0) {
                JOptionPane.showMessageDialog(rootPane, "Your table is empty, there is nothing to remove", "An empty table detected", JOptionPane.ERROR_MESSAGE);
            }
            if (mode.getRowCount() > 0) {
                JOptionPane.showMessageDialog(rootPane, "Please select a row with the denominations you want to remove", "Some information missing", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (!swp.getText().isEmpty()) {
            double e1 = Double.parseDouble(carr.getText());//DENOMINATIONS
            int a2 = Integer.parseInt(dmt.getText(), 10);//NUMBER OF DENOMINATIONS
            double b1 = Double.parseDouble(amtt.getText());
            double g1 = Double.parseDouble(sell7.getText());
            double ab = g1 - b1;

            String querie = "SELECT currency,denominations,no_denominations,total FROM clerk_accounts where account_holder='" + name.getText() + "' and denominations='" + e1 + "' and currency='" + sell2.getSelectedItem().toString() + "' ";
            try {
                Class.forName(DB_DRIVER);
                con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                stm = con.createStatement();
                rs = stm.executeQuery(querie);
                boolean bool2 = rs.isBeforeFirst();
                while (rs.next()) {
                    if (bool2 == true) {
                        //JOptionPane.showMessageDialog(null, "Good one");
                        double bx = rs.getDouble("total");
                        int vx = rs.getInt("no_denominations");
                        double dw1 = (bx + b1);
                        int dw2 = (vx + a2);

                        SQLRun run = new SQLRun();
                        String sqlq = "Update clerk_accounts set no_denominations='" + dw2 + "' , total='" + dw1 + "' where account_holder='" + name.getText() + "' and denominations='" + e1 + "' and currency='" + sell2.getSelectedItem().toString() + "'";
                        int updated = run.sqlUpdate(sqlq);
                        if (updated > 0) {
                            if (sell_table.getSelectedRow() == -1) {
                                if (sell_table.getRowCount() == 0) {
                                    JOptionPane.showMessageDialog(null, "The table is empty", "Providence says....", JOptionPane.WARNING_MESSAGE);

                                } else {
                                    JOptionPane.showMessageDialog(null, " You must select denominations", "Providence says....", JOptionPane.WARNING_MESSAGE);
                                }
                            } else {

                                String jdbcUrl = "jdbc:postgresql:" + configs.getText();
                                String username = "postgres";
                                String password = "danieloh24";
                                String sqlp = "delete from transactions where trans_id='" + swp.getText() + "'";

                                try (Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
                                        Statement stmt = conn.createStatement();) {

                                    stmt.executeUpdate(sqlp);
                                    JOptionPane.showMessageDialog(null, " Transaction history reversed successfully from the system", "Povidence says....", JOptionPane.INFORMATION_MESSAGE);
                                    mode.removeRow(sell_table.getSelectedRow());
                                    swp.setText(ab + "");
                                    carr.setText("");
                                    amtt.setText("");
                                    sell7.setText("0000");
                                } catch (SQLException e) {
                                    JOptionPane.showMessageDialog(null, e.getMessage(), "Something went wrong", JOptionPane.ERROR_MESSAGE);
                                }
                            }

                        }
                    }
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Something went wrong ", JOptionPane.ERROR_MESSAGE);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Clerk.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }//GEN-LAST:event_jLabel80MouseClicked

    private void cse2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cse2ActionPerformed
        if (!cse2.getSelectedItem().equals(" ")) {
            DefaultComboBoxModel dk = new DefaultComboBoxModel();
            cse5.setModel(dk);
DB_USER = uname.getText();
                DB_PAS = upass.getText();
            String querie = "SELECT base_currency,quote_currency,buying_rate,selling_rate FROM config_rates where base_currency='" + cse1.getSelectedItem().toString() + "' and quote_currency='" + cse2.getSelectedItem().toString() + "' ";
            String query = "SELECT denominations FROM clerk_accounts where currency='" + cse2.getSelectedItem().toString() + "' ";

            try {
                Class.forName(DB_DRIVER);
                con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                stm = con.createStatement();
                rs = stm.executeQuery(querie);
                boolean bool2 = rs.isBeforeFirst();
                while (rs.next()) {
                    if (bool2 == true) {
                        double a = rs.getDouble("selling_rate");
                        double a1 = Math.round(a * 10) / 10.0;
                        rat1.setText(a1 + "");

                    }

                }
                if (bool2 == false) {
                    JOptionPane.showMessageDialog(null, "Exchange rates yet to be configured, please contact the Finance Personel ", "Exchange rates (null)", JOptionPane.WARNING_MESSAGE);
                    //acc_total.setText(" ");
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Clerk.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                Class.forName(DB_DRIVER);
                con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                stm = con.createStatement();

                rs = stm.executeQuery(query);
                boolean bool = rs.isBeforeFirst();
                while (rs.next()) {
                    if (bool == true) {
                        double b = rs.getDouble("denominations");

                        cse5.addItem(b + "");
                        cse3.setEditable(true);
                    }
                }
                if (bool == false) {
                    JOptionPane.showMessageDialog(null, "You don't have the denominations of the quote currency you seected\n, request " + cse2.getSelectedItem().toString() + " " + "from any of the strong room operator at your branch\n,highlighting the denominations you want ", "Denominations for" + cse2.getSelectedItem().toString() + " " + "not found", JOptionPane.WARNING_MESSAGE);
                    cse3.setEditable(false);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
            } catch (ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Something went wrong", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_cse2ActionPerformed

    private void cse3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cse3KeyPressed
        int key = evt.getKeyCode();
        if (key == 10) {
            if (!cse3.getText().isEmpty()) {
                int input = JOptionPane.showConfirmDialog(null, " Are you sure that you want to sell " + cse3.getText() + " " + "?", "Providence says....", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (input == JOptionPane.NO_OPTION) {

                }
                if (input == JOptionPane.YES_OPTION) {
                    double a = Double.parseDouble(rat1.getText());
                    double b = Double.parseDouble(cse3.getText());
                    double c = a * b;
                    double ab = Math.round(c * 10) / 10.0;
                    cse4.setText(c + "");
                }

                if (cse3.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Enter the amountof money that you want to sell", "Exchange rates (null)", JOptionPane.WARNING_MESSAGE);

                }

            }

        }
    }//GEN-LAST:event_cse3KeyPressed

    private void cse3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cse3KeyTyped
        char c = evt.getKeyChar();
        if (!Character.isDigit(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_cse3KeyTyped

    private void cse6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cse6KeyPressed
DB_USER = uname.getText();
                DB_PAS = upass.getText();
        java.util.Date date = new java.util.Date();
        long t = date.getTime();
        java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(t);
        java.sql.Date sqlDate = new java.sql.Date(t);

        DefaultTableModel dm = (DefaultTableModel) cse_table.getModel();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String randstring = "";
        Random random = new Random();
        int length = 5;
        char[] text = new char[length];
        for (int ip = 0; ip < length; ip++) {
            text[ip] = characters.charAt(random.nextInt(characters.length()));
        }
        for (int ip = 0; ip < text.length; ip++) {
            randstring += text[ip];
        }
        int key = evt.getKeyCode();
        if (key == 10) {
            if (!cse6.getText().isEmpty()) {
                //$#%^&
                String op = "selling";
                double d = Double.parseDouble(cse3.getText());
                double a = Double.parseDouble(cse5.getSelectedItem().toString());
                double b = Double.parseDouble(cse6.getText());
                int b1 = Integer.parseInt(cse6.getText());
                double c = a * b;
                double xa = Double.parseDouble(sell14.getText());
                double xy = xa + c;

                ResultSet rs = null;

                String query = "SELECT currency,denominations,no_denominations,total FROM clerk_accounts where account_holder='" + name.getText() + "' and denominations='" + a + "' and currency='" + cse2.getSelectedItem().toString() + "' ";
                try {
                    Class.forName(DB_DRIVER);
                    con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                    stm = con.createStatement();
                    rs = stm.executeQuery(query);
                    boolean bool2 = rs.isBeforeFirst();
                    //#@#$&&^#
                    while (rs.next()) {
                        if (bool2 == true) {

                            double za = rs.getDouble("total");
                            int ze = rs.getInt("no_denominations");

                            double da = za - c;
                            int de = ze - b1;
                            if (ze < b1) {
                                JOptionPane.showMessageDialog(null, "The denominations that you want you sell are more than what you have in your account", "Number of available denominations exceeded ", JOptionPane.ERROR_MESSAGE);
                            }
                            if (ze >= b1) {
                                if (d < xy) {

                                }
                                if (d >= xy) {
                                    if (my_list1.getText().isEmpty()) {
                                        SQLRun run = new SQLRun();
                                        String sqlq = "Update clerk_accounts set no_denominations='" + de + "' , total='" + da + "' where account_holder='" + name.getText() + "' and denominations='" + a + "' and currency='" + cse2.getSelectedItem().toString() + "'";
                                        int updated = run.sqlUpdate(sqlq);
                                        if (updated > 0) {

                                            try {
                                                con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                                pst = con.prepareStatement("insert into transactions values(?,?,?,?,?,?,?,?,?,?)");
                                                double sg = Double.parseDouble(rat1.getText());
                                                pst.setString(1, cse1.getSelectedItem().toString());
                                                pst.setDouble(2, a);
                                                pst.setInt(3, b1);
                                                pst.setDouble(4, c);
                                                pst.setString(5, op);
                                                pst.setDouble(6, sg);
                                                pst.setTimestamp(7, sqlTimestamp);
                                                pst.setString(8, name.getText());
                                                pst.setString(9, branch.getText());
                                                pst.setString(10, swp1.getText());

                                                int p = pst.executeUpdate();
                                                if (p > 0) {
                                                    my_list1.setText(cse5.getSelectedItem().toString().concat(" ").concat(","));
                                                    dm.addRow(new Object[]{randstring, cse5.getSelectedItem().toString(), b1, c});
                                                    sell14.setText(xy + "");
                                                }
                                            } catch (HeadlessException | SQLException ev) {
                                                JOptionPane.showMessageDialog(null, ev.getMessage(), "An errorr occured", JOptionPane.ERROR_MESSAGE);

                                            }
                                        }
                                    }
                                    //#$%^&**&
                                    if (!my_list1.getText().isEmpty()) {
                                        String abx = my_list.getText();
                                        if (abx.contains(cse5.getSelectedItem().toString())) {

                                        }
                                        if (!abx.contains(cse5.getSelectedItem().toString())) {
                                            if (ze < b1) {
                                                JOptionPane.showMessageDialog(null, "The denominations that you want you sell are more than what you have in your account", "Number of available denominations exceeded ", JOptionPane.ERROR_MESSAGE);
                                            }
                                            if (ze >= b1) {
                                                SQLRun run = new SQLRun();
                                                String sqlq = "Update clerk_accounts set no_denominations='" + de + "' , total='" + da + "' where account_holder='" + name.getText() + "' and denominations='" + a + "' and currency='" + sell2.getSelectedItem().toString() + "'";
                                                int updated = run.sqlUpdate(sqlq);
                                                if (updated > 0) {

                                                    try {
                                                        con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                                        pst = con.prepareStatement("insert into transactions values(?,?,?,?,?,?,?,?,?,?)");
                                                        double sg = Double.parseDouble(rat1.getText());
                                                        pst.setString(1, cse1.getSelectedItem().toString());
                                                        pst.setDouble(2, a);
                                                        pst.setInt(3, b1);
                                                        pst.setDouble(4, c);
                                                        pst.setString(5, op);
                                                        pst.setDouble(6, sg);
                                                        pst.setTimestamp(7, sqlTimestamp);
                                                        pst.setString(8, name.getText());
                                                        pst.setString(9, branch.getText());
                                                        pst.setString(10, swp1.getText());

                                                        int p = pst.executeUpdate();
                                                        if (p > 0) {
                                                            my_list1.setText(cse5.getSelectedItem().toString().concat(" ").concat(",").concat(abx));
                                                            dm.addRow(new Object[]{randstring, cse5.getSelectedItem().toString(), b1, c});
                                                            sell14.setText(xy + "");
                                                        }
                                                    } catch (HeadlessException | SQLException ev) {
                                                        JOptionPane.showMessageDialog(null, ev.getMessage(), "An errorr occured", JOptionPane.ERROR_MESSAGE);

                                                    }
                                                }
                                            }

                                        }
                                    }
                                    //#$%&^*%
                                    if (bool2 == false) {
                                        JOptionPane.showMessageDialog(null, "Please adviced that you don't have " + cse2.getSelectedItem().toString() + " " + a + " " + "in your account as a counter clerk.\nTry to liase with your strong room operator to send you so that you can serve the customer", "Denomination not found", JOptionPane.ERROR_MESSAGE);

                                    }
                                }
                                if (d == xy) {
                                    receiving r = new receiving();
                                    r.setVisible(true);
                                    double sg = Double.parseDouble(rat1.getText());
                                    if (my_list1.getText().isEmpty()) {
                                        sell14.setText(xy + "");

                                        r.setVisible(true);
                                        receiving.r2.setText(cse4.getText());
                                        receiving.r1.setText(cse2.getSelectedItem().toString());
                                        receiving.r4.setEditable(true);

                                        receiving.rcc1.setText(sell2.getSelectedItem().toString());
                                        receiving.rcrate.setText(sg + "");
                                        receiving.rcout.setText(sell3.getText());
                                        receiving.reg.setText(reg.getText());
                                        receiving.branch.setText(branch.getText());
                                        receiving.configs.setText(configs.getText());
                                        receiving.name.setText(this.name.getText());

                                        dm.addRow(new Object[]{randstring, cse5.getSelectedItem().toString(), b1, c});

                                        ResultSet rs2 = null;
                                        String querie = "SELECT currency,denomination_value FROM denominations where currency='" + cse1.getSelectedItem().toString() + "' ";
                                        try {
                                            Class.forName(DB_DRIVER);
                                            con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                            stm = con.createStatement();
                                            rs2 = stm.executeQuery(querie);
                                            boolean bool = rs2.isBeforeFirst();
                                            while (rs2.next()) {
                                                if (bool == true) {

                                                    String bx = rs2.getString("denomination_value");
                                                    receiving.r3.addItem(bx);

                                                }
                                            }

                                        } catch (SQLException e) {
                                            JOptionPane.showMessageDialog(null, e.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
                                        }
                                    }
                                    if (!my_list1.getText().isEmpty()) {
                                        receiving.r2.setText(cse4.getText());
                                        receiving.r1.setText(cse1.getSelectedItem().toString());
                                        receiving.r4.setEditable(true);
                                        receiving.rcc1.setText(cse2.getSelectedItem().toString());
                                        receiving.rcrate.setText(sg + "");
                                        receiving.rcout.setText(cse3.getText());
                                        receiving.reg.setText(reg.getText());
                                        receiving.branch.setText(branch.getText());
                                        receiving.name.setText(this.name.getText());
                                        receiving.configs.setText(configs.getText());

                                        //dm.addRow(new Object[]{randstring, cse5.getSelectedItem().toString(), b1, c});
                                        ResultSet rs2 = null;
                                        String querie = "SELECT currency,denomination_value FROM denominations where currency='" + cse1.getSelectedItem().toString() + "' ";
                                        try {
                                            Class.forName(DB_DRIVER);
                                            con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                            stm = con.createStatement();
                                            rs2 = stm.executeQuery(querie);
                                            boolean bool = rs2.isBeforeFirst();
                                            while (rs2.next()) {
                                                if (bool == true) {

                                                    String bx = rs2.getString("denomination_value");
                                                    receiving.r3.addItem(bx);
                                                    String abx = my_list.getText();

                                                }
                                            }

                                        } catch (SQLException e) {
                                            JOptionPane.showMessageDialog(null, e.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
                                        }
                                    }

                                }
                            }
                        }
                    }

                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Clerk.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }


    }//GEN-LAST:event_cse6KeyPressed

    private void cse6KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cse6KeyTyped
        char c = evt.getKeyChar();
        if (!Character.isDigit(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_cse6KeyTyped

    private void cse_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cse_tableMouseClicked
        DefaultTableModel model = (DefaultTableModel) cse_table.getModel();
        swp1.setText(model.getValueAt(cse_table.getSelectedRow(), 0).toString());
        carr1.setText(model.getValueAt(cse_table.getSelectedRow(), 1).toString());
        dmt1.setText(model.getValueAt(cse_table.getSelectedRow(), 2).toString());
        amtt1.setText(model.getValueAt(cse_table.getSelectedRow(), 3).toString());
    }//GEN-LAST:event_cse_tableMouseClicked

    private void jLabel117MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel117MouseClicked
        DefaultTableModel mode = (DefaultTableModel) cse_table.getModel();
DB_USER = uname.getText();
                DB_PAS = upass.getText();
        if (swp1.getText().isEmpty()) {
            if (mode.getRowCount() == 0) {
                JOptionPane.showMessageDialog(rootPane, "Your table is empty, there is nothing to remove", "An empty table detected", JOptionPane.ERROR_MESSAGE);
            }
            if (mode.getRowCount() > 0) {
                JOptionPane.showMessageDialog(rootPane, "Please select a row with the denominations you want to remove", "Some information missing", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (!swp1.getText().isEmpty()) {
            double e1 = Double.parseDouble(carr1.getText());//DENOMINATIONS
            int a2 = Integer.parseInt(dmt1.getText(), 10);//NUMBER OF DENOMINATIONS
            double b1 = Double.parseDouble(amtt1.getText());
            double g1 = Double.parseDouble(sell14.getText());
            double ab = g1 - b1;

            String querie = "SELECT currency,denominations,no_denominations,total FROM clerk_accounts where account_holder='" + name.getText() + "' and denominations='" + e1 + "' and currency='" + cse2.getSelectedItem().toString() + "' ";
            try {
                Class.forName(DB_DRIVER);
                con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                stm = con.createStatement();
                rs = stm.executeQuery(querie);
                boolean bool2 = rs.isBeforeFirst();
                while (rs.next()) {
                    if (bool2 == true) {
                        //JOptionPane.showMessageDialog(null, "Good one");
                        double bx = rs.getDouble("total");
                        int vx = rs.getInt("no_denominations");
                        double dw1 = (bx + b1);
                        int dw2 = (vx + a2);

                        SQLRun run = new SQLRun();
                        String sqlq = "Update clerk_accounts set no_denominations='" + dw2 + "' , total='" + dw1 + "' where account_holder='" + name.getText() + "' and denominations='" + e1 + "' and currency='" + cse2.getSelectedItem().toString() + "'";
                        int updated = run.sqlUpdate(sqlq);
                        if (updated > 0) {
                            if (cse_table.getSelectedRow() == -1) {
                                if (cse_table.getRowCount() == 0) {
                                    JOptionPane.showMessageDialog(null, "The table is empty", "Providence says....", JOptionPane.WARNING_MESSAGE);

                                } else {
                                    JOptionPane.showMessageDialog(null, " You must select denominations", "Providence says....", JOptionPane.WARNING_MESSAGE);
                                }
                            } else {

                                String jdbcUrl = "jdbc:postgresql:" + configs.getText();
                                String username = "postgres";
                                String password = "danieloh24";
                                String sqlp = "delete from transactions where trans_id='" + swp1.getText() + "'";

                                try (Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
                                        Statement stmt = conn.createStatement();) {

                                    stmt.executeUpdate(sqlp);
                                    JOptionPane.showMessageDialog(null, " Transaction history reversed successfully from the system", "Povidence says....", JOptionPane.INFORMATION_MESSAGE);
                                    mode.removeRow(cse_table.getSelectedRow());
                                    swp1.setText(ab + "");
                                    carr1.setText("");
                                    amtt1.setText("");
                                    sell14.setText("0000");
                                } catch (SQLException e) {
                                    JOptionPane.showMessageDialog(null, e.getMessage(), "Something went wrong", JOptionPane.ERROR_MESSAGE);
                                }
                            }

                        }
                    }
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Something went wrong ", JOptionPane.ERROR_MESSAGE);
            } catch (ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Something went wrong", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jLabel117MouseClicked

    private void jLabel118MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel118MouseClicked
        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        DefaultTableModel mode = (DefaultTableModel) cse_table.getModel();
        if (cse_table.getSelectedRow() == -1) {
            if (cse_table.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "The table is empty", "Providence says....", JOptionPane.WARNING_MESSAGE);

            } else {
                JOptionPane.showMessageDialog(null, " You must select denominations", "Providence says....", JOptionPane.WARNING_MESSAGE);
            }
        } else {

            int input = JOptionPane.showConfirmDialog(null, " Are you sure that you want update the number od denominations of  " + cse2.getSelectedItem().toString() + " " + sell5.getSelectedItem().toString() + " ?", "Providence says....", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (input == JOptionPane.NO_OPTION) {

            }
            if (input == JOptionPane.YES_OPTION) {
                mode.removeRow(cse_table.getSelectedRow());

                String a = JOptionPane.showInputDialog("How many denomiantions do you want ? ");

                try {
                    int a1 = Integer.parseInt(a);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Please enter integers for number of denominations", "Incorrect data format", JOptionPane.ERROR_MESSAGE);
                }

                int a1 = Integer.parseInt(a);
                int a2 = Integer.parseInt(dmt1.getText(), 10);//NUMBER OF DENOMINATIONS
                double b1 = Double.parseDouble(amtt1.getText());//TOTAL (DENOMINATION VALUE * NUMBER OF DENOMINATIONS)
                double c1 = Double.parseDouble(sell14.getText());//ITERATION
                double d1 = c1 - b1;
                double e1 = Double.parseDouble(carr1.getText());//DENOMINATIONS
                double p1 = Double.parseDouble(cse4.getText());//AMOUNT SUPPOSED TO BE RECEIVED BY THE CASHIER
                double q1 = Double.parseDouble(cse3.getText());//AMOUNT TO BE SOLD
                double f1 = Double.parseDouble(a);
                double g1 = e1 * f1;
                double pr = d1 + g1;
                if ((d1 + g1) > q1) {
                    JOptionPane.showMessageDialog(null, "An update on the number of denominations that you want to sell exceeds " + cse2.getSelectedItem().toString() + " " + "$" + " " + q1, "", JOptionPane.WARNING_MESSAGE);
                }
                if ((d1 + g1) <= q1) {

                    ResultSet rs2 = null;
                    String querie = "SELECT currency,denominations,no_denominations,total FROM clerk_accounts where account_holder='" + name.getText() + "' and denominations='" + e1 + "' and currency='" + cse2.getSelectedItem().toString() + "' ";
                    try {
                        Class.forName(DB_DRIVER);
                        con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                        stm = con.createStatement();
                        rs2 = stm.executeQuery(querie);
                        boolean bool2 = rs2.isBeforeFirst();
                        while (rs2.next()) {
                            if (bool2 == true) {
                                JOptionPane.showMessageDialog(null, "Good one");
                                double bx = rs2.getDouble("total");
                                int vx = rs2.getInt("no_denominations");

                                double dw1 = (bx + b1) - g1;
                                int dw2 = (vx + a2) - a1;

                                double pa = bx + b1;
                                int pe = vx + a1;

                                SQLRun run = new SQLRun();
                                String sqlq = "Update clerk_accounts set no_denominations='" + dw2 + "' , total='" + dw1 + "' where account_holder='" + name.getText() + "' and denominations='" + e1 + "' and currency='" + cse2.getSelectedItem().toString() + "'";
                                int updated = run.sqlUpdate(sqlq);
                                if (updated > 0) {
                                    String jdbcUrl = "jdbc:postgresql:" + configs.getText();
                                    String username = "postgres";
                                    String password = "danieloh24";
                                    String sql = "Update transactions set no_denominations='" + pe + "' , total='" + pa + "' where trans_id='" + swp1.getText() + "' ";

                                    try (Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
                                            Statement stmt = conn.createStatement();) {

                                        stmt.executeUpdate(sql);
                                        mode.addRow(new Object[]{swp1.getText(), e1, a, g1});
                                        sell14.setText(pr + "");
                                    } catch (SQLException e) {
                                        JOptionPane.showMessageDialog(null, e.getSQLState());
                                    }

                                }

                            }
                        }
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Something went wrong", JOptionPane.ERROR_MESSAGE);
                    } catch (ClassNotFoundException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Something went wrong", JOptionPane.ERROR_MESSAGE);
                    }

                }

            }
        }
    }//GEN-LAST:event_jLabel118MouseClicked

    private void cse1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cse1ActionPerformed
        DB_USER = uname.getText();
                DB_PAS = upass.getText();
        if (!cse1.getSelectedItem().equals(" ")) {

            
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

                        if (!c.matches(xz) && !cse1.getSelectedItem().equals(b)) {
                            //sell2.addItem(" "); 
                            //sell2.addItem(b);
                            cse2.addItem(b);

                        }

                    }
                }
                if (bool == false) {

                    //JOptionPane.showMessageDialog(rootPane, "You have configured exchange rates against the base currency","Base currency not available",JOptionPane.WARNING_MESSAGE);
                }

            } catch (ClassNotFoundException | SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "An error occured while trying to fetcg your data", JOptionPane.ERROR_MESSAGE);
            }
        }

    }//GEN-LAST:event_cse1ActionPerformed

    private void by2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_by2ActionPerformed
        DB_USER = uname.getText();
                DB_PAS = upass.getText();
        if (!by2.getSelectedItem().equals(" ")) {
            DefaultComboBoxModel dk = new DefaultComboBoxModel();
            by5.setModel(dk);

            String querie = "SELECT base_currency,quote_currency,buying_rate,buying_rate FROM config_rates where base_currency='" + by1.getText() + "' and quote_currency='" + by2.getSelectedItem().toString() + "' ";

            try {
                Class.forName(DB_DRIVER);
                con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                stm = con.createStatement();
                rs = stm.executeQuery(querie);
                boolean bool2 = rs.isBeforeFirst();
                while (rs.next()) {
                    if (bool2 == true) {
                        double a = rs.getDouble("buying_rate");
                        double a1 = Math.round(a * 10) / 10.0;
                        by_rat.setText(a1 + "");

                    }

                }
                if (bool2 == false) {
                    JOptionPane.showMessageDialog(null, "Exchange rates yet to be configured, please contact the Finance Personel ", "Exchange rates (null)", JOptionPane.WARNING_MESSAGE);
                    //acc_total.setText(" ");
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Clerk.class.getName()).log(Level.SEVERE, null, ex);
            }
            String query = "SELECT denominations FROM clerk_accounts where currency='" + by1.getText() + "' ";

            try {
                Class.forName(DB_DRIVER);
                con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                stm = con.createStatement();

                rs = stm.executeQuery(query);
                boolean bool = rs.isBeforeFirst();
                while (rs.next()) {
                    if (bool == true) {
                        double b = rs.getDouble("denominations");

                        by5.addItem(b + "");
                        by3.setEditable(true);
                    }
                }
                if (bool == false) {
                    JOptionPane.showMessageDialog(null, "You don't have the denominations of the quote currency you seected\n, request " + by2.getSelectedItem().toString() + " " + "from any of the strong room operator at your branch\n,highlighting the denominations you want ", "Denominations for" + by2.getSelectedItem().toString() + " " + "not found", JOptionPane.WARNING_MESSAGE);
                    by3.setEditable(false);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Clerk.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_by2ActionPerformed

    private void by3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_by3KeyPressed
DB_USER = uname.getText();
                DB_PAS = upass.getText();
        int key = evt.getKeyCode();
        if (key == 10) {
            if (!by3.getText().isEmpty()) {
                int input = JOptionPane.showConfirmDialog(null, " Are you sure that you want to sell " + by3.getText() + " " + "?", "Providence says....", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (input == JOptionPane.NO_OPTION) {

                }
                if (input == JOptionPane.YES_OPTION) {
                    double a = Double.parseDouble(by_rat.getText());
                    double b = Double.parseDouble(by3.getText());
                    double c = a * b;
                    double ab = Math.round(c * 10) / 10.0;
                    by4.setText(c + "");
                }

                if (by3.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Enter the amountof money that you want to sell", "Exchange rates (null)", JOptionPane.WARNING_MESSAGE);

                }

            }

        }
    }//GEN-LAST:event_by3KeyPressed

    private void by3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_by3KeyTyped
        char c = evt.getKeyChar();
        if (!Character.isDigit(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_by3KeyTyped

    private void by6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_by6KeyPressed
DB_USER = uname.getText();
                DB_PAS = upass.getText();
        java.util.Date date = new java.util.Date();
        long t = date.getTime();
        java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(t);
        java.sql.Date sqlDate = new java.sql.Date(t);

        DefaultTableModel dm = (DefaultTableModel) by_table.getModel();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String randstring = "";
        Random random = new Random();
        int length = 5;
        char[] text = new char[length];
        for (int ip = 0; ip < length; ip++) {
            text[ip] = characters.charAt(random.nextInt(characters.length()));
        }
        for (int ip = 0; ip < text.length; ip++) {
            randstring += text[ip];
        }
        int key = evt.getKeyCode();
        if (key == 10) {
            if (!by6.getText().isEmpty()) {

                double d = Double.parseDouble(by4.getText());
                double a = Double.parseDouble(by5.getSelectedItem().toString());
                double b = Double.parseDouble(by6.getText());
                int b1 = Integer.parseInt(by6.getText());
                double c = a * b;
                double xa = Double.parseDouble(by11.getText());
                double xy = xa + c;

                ResultSet rs, rs1 = null;

                String query = "SELECT currency,denominations,no_denominations,total FROM clerk_accounts where account_holder='" + name.getText() + "' and denominations='" + a + "' and currency='" + by1.getText() + "' ";
                try {
                    Class.forName(DB_DRIVER);
                    con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                    stm = con.createStatement();
                    rs = stm.executeQuery(query);
                    boolean bool2 = rs.isBeforeFirst();
                    String op = "buying";
                    while (rs.next()) {
                        if (bool2 == true) {

                            double za = rs.getDouble("total");
                            int ze = rs.getInt("no_denominations");

                            double da = za - c;
                            int de = ze - b1;
                            if (ze < b1) {
                                JOptionPane.showMessageDialog(null, "You have less $" + by5.getSelectedItem().toString() + " " + "denomiantions in your account.\nIf the client want $" + by5.getSelectedItem().toString() + " " + "send a request to the S.R.O and complete the transaction", "Number of available denominations exceeded ", JOptionPane.ERROR_MESSAGE);
                            }
                            if (ze >= b1) {
                                if (d < xy) {
                                    JOptionPane.showMessageDialog(null, "You have exceeded the amount of money you are supposed to pay", "Insufficient funds", JOptionPane.ERROR_MESSAGE);
                                }
                                if (d >= xy) {
                                    if (by_list.getText().isEmpty()) {
                                        // #$%^&*
                                        SQLRun run = new SQLRun();
                                        String sqlq = "Update clerk_accounts set no_denominations='" + de + "' , total='" + da + "' where account_holder='" + name.getText() + "' and denominations='" + a + "' and currency='" + by1.getText() + "'";
                                        int updated = run.sqlUpdate(sqlq);
                                        if (updated > 0) {

                                            try {
                                                con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                                pst = con.prepareStatement("insert into transactions values(?,?,?,?,?,?,?,?,?,?)");
                                                double sg = Double.parseDouble(by_rat.getText());
                                                pst.setString(1, by1.getText());
                                                pst.setDouble(2, a);
                                                pst.setInt(3, b1);
                                                pst.setDouble(4, c);
                                                pst.setString(5, op);
                                                pst.setDouble(6, sg);
                                                pst.setTimestamp(7, sqlTimestamp);
                                                pst.setString(8, name.getText());
                                                pst.setString(9, branch.getText());
                                                pst.setString(10, by8.getText());

                                                int p = pst.executeUpdate();
                                                if (p > 0) {
                                                    by_list.setText(by5.getSelectedItem().toString().concat(" ").concat(","));
                                                    dm.addRow(new Object[]{randstring, by5.getSelectedItem().toString(), b1, c});
                                                    by11.setText(xy + "");
                                                }
                                            } catch (HeadlessException | SQLException ev) {
                                                JOptionPane.showMessageDialog(null, ev.getMessage(), "An errorr occured", JOptionPane.ERROR_MESSAGE);

                                            }
                                        }
                                    }
                                    if (!by_list.getText().isEmpty()) {
                                        String abx = by_list.getText();
                                        if (abx.contains(by5.getSelectedItem().toString())) {

                                        }
                                        if (!abx.contains(by5.getSelectedItem().toString())) {

                                            if (ze < b1) {
                                                JOptionPane.showMessageDialog(null, "The denominations that you want you sell are more than what you have in your account", "Number of available denominations exceeded ", JOptionPane.ERROR_MESSAGE);
                                            }
                                            if (ze >= b1) {
                                                SQLRun run = new SQLRun();
                                                String sqlq = "Update clerk_accounts set no_denominations='" + de + "' , total='" + da + "' where account_holder='" + name.getText() + "' and denominations='" + a + "' and currency='" + by1.getText() + "'";
                                                int updated = run.sqlUpdate(sqlq);
                                                if (updated > 0) {

                                                    try {
                                                        con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                                        pst = con.prepareStatement("insert into transactions values(?,?,?,?,?,?,?,?,?,?)");
                                                        double sg = Double.parseDouble(by_rat.getText());
                                                        pst.setString(1, by1.getText());
                                                        pst.setDouble(2, a);
                                                        pst.setInt(3, b1);
                                                        pst.setDouble(4, c);
                                                        pst.setString(5, op);
                                                        pst.setDouble(6, sg);
                                                        pst.setTimestamp(7, sqlTimestamp);
                                                        pst.setString(8, name.getText());
                                                        pst.setString(9, branch.getText());
                                                        pst.setString(10, by8.getText());

                                                        int p = pst.executeUpdate();
                                                        if (p > 0) {
                                                            by_list.setText(by5.getSelectedItem().toString().concat(" ").concat(",").concat(abx));
                                                            dm.addRow(new Object[]{randstring, by5.getSelectedItem().toString(), b1, c});
                                                            by11.setText(xy + "");
                                                        }
                                                    } catch (HeadlessException | SQLException ev) {
                                                        JOptionPane.showMessageDialog(null, ev.getMessage(), "An errorr occured", JOptionPane.ERROR_MESSAGE);

                                                    }
                                                }
                                            }

                                        }
                                    }

                                }
                                if (d == xy) {
                                    sending r = new sending();
                                    r.setVisible(true);
                                    double sg = Double.parseDouble(by_rat.getText());
                                    if (!by_list.getText().isEmpty()) {
                                        sending.rcc1.setText(by1.getText());
                                        sending.r2.setText(by3.getText());
                                        sending.r1.setText(by2.getSelectedItem().toString());
                                        sending.rcrate.setText(sg + "");
                                        sending.rcout.setText(by4.getText());
                                        sending.reg.setText(reg.getText());
                                        sending.branch.setText(branch.getText());
                                        sending.configs.setText(configs.getText());
                                        sending.name.setText(this.name.getText());

                                        String querie = "SELECT currency,denomination_value FROM denominations where currency='" + by2.getSelectedItem().toString() + "' ";
                                        try {
                                            rs1 = stm.executeQuery(querie);
                                            boolean bool = rs1.isBeforeFirst();
                                            while (rs1.next()) {
                                                if (bool == true) {
                                                    String zv = rs1.getString("denomination_value");

                                                    sending.r3.addItem(zv);

                                                }
                                            }
                                        } catch (SQLException xp) {

                                        }
                                    }
                                    if (by_list.getText().isEmpty()) {
                                        sending.r1.setText(by1.getText());
                                        sending.r2.setText(by3.getText());
                                        sending.rcc1.setText(by2.getSelectedItem().toString());
                                        sending.rcrate.setText(sg + "");
                                        sending.rcout.setText(by4.getText());
                                        sending.reg.setText(reg.getText());
                                        sending.branch.setText(branch.getText());
                                        sending.name.setText(this.name.getText());
                                        sending.configs.setText(configs.getText());

                                        dm.addRow(new Object[]{randstring, by5.getSelectedItem().toString(), b1, c});

                                        String querie = "SELECT currency,denomination_value FROM denominations where currency='" + by2.getSelectedItem().toString() + "' ";
                                        try {
                                            rs1 = stm.executeQuery(querie);
                                            boolean bool = rs1.isBeforeFirst();
                                            while (rs1.next()) {
                                                if (bool == true) {
                                                    String zv = rs1.getString("denomination_value");

                                                    sending.r3.addItem(zv);

                                                }
                                            }
                                        } catch (SQLException xp) {

                                        }
                                    }

                                }

                            }
                        }
                    }
                    if (bool2 == false) {
                        JOptionPane.showMessageDialog(null, "Please adviced that you don't have " + by1.getText() + " " + a + " " + "in your account as a counter clerk.\nTry to liase with your strong room operator to send you so that you can serve the customer", "Denomination not found", JOptionPane.ERROR_MESSAGE);

                    }
                } catch (SQLException e) {
                    //JOptionPane.showMessageDialog(null, e.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Clerk.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_by6KeyPressed

    private void by6KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_by6KeyTyped
        char c = evt.getKeyChar();
        if (!Character.isDigit(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_by6KeyTyped

    private void by_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_by_tableMouseClicked
        DefaultTableModel model = (DefaultTableModel) by_table.getModel();
        by8.setText(model.getValueAt(by_table.getSelectedRow(), 0).toString());
        by7.setText(model.getValueAt(by_table.getSelectedRow(), 1).toString());
        by10.setText(model.getValueAt(by_table.getSelectedRow(), 2).toString());
        by9.setText(model.getValueAt(by_table.getSelectedRow(), 3).toString());
    }//GEN-LAST:event_by_tableMouseClicked

    private void jLabel91MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel91MouseClicked
        DefaultTableModel mode = (DefaultTableModel) by_table.getModel();
DB_USER = uname.getText();
                DB_PAS = upass.getText();
        if (by8.getText().isEmpty()) {
            if (mode.getRowCount() == 0) {
                JOptionPane.showMessageDialog(rootPane, "Your table is empty, there is nothing to remove", "An empty table detected", JOptionPane.ERROR_MESSAGE);
            }
            if (mode.getRowCount() > 0) {
                JOptionPane.showMessageDialog(rootPane, "Please select a row with the denominations you want to remove", "Some information missing", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (!by8.getText().isEmpty()) {
            double e1 = Double.parseDouble(by7.getText());//DENOMINATIONS
            int a2 = Integer.parseInt(by10.getText(), 10);//NUMBER OF DENOMINATIONS
            double b1 = Double.parseDouble(by9.getText());
            double g1 = Double.parseDouble(by11.getText());
            double ab = g1 - b1;

            String querie = "SELECT currency,denominations,no_denominations,total FROM clerk_accounts where account_holder='" + name.getText() + "' and denominations='" + e1 + "' and currency='" + by2.getSelectedItem().toString() + "' ";
            try {
                Class.forName(DB_DRIVER);
                con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                stm = con.createStatement();
                rs = stm.executeQuery(querie);
                boolean bool2 = rs.isBeforeFirst();
                while (rs.next()) {
                    if (bool2 == true) {
                        JOptionPane.showMessageDialog(null, "Good one");
                        double bx = rs.getDouble("total");
                        int vx = rs.getInt("no_denominations");
                        double dw1 = (bx + b1);
                        int dw2 = (vx + a2);

                        SQLRun run = new SQLRun();
                        String sqlq = "Update clerk_accounts set no_denominations='" + dw2 + "' , total='" + dw1 + "' where account_holder='" + name.getText() + "' and denominations='" + e1 + "' and currency='" + by2.getSelectedItem().toString() + "'";
                        int updated = run.sqlUpdate(sqlq);
                        if (updated > 0) {
                            if (by_table.getSelectedRow() == -1) {
                                if (by_table.getRowCount() == 0) {
                                    JOptionPane.showMessageDialog(null, "The table is empty", "Providence says....", JOptionPane.WARNING_MESSAGE);

                                } else {
                                    JOptionPane.showMessageDialog(null, " You must select denominations", "Providence says....", JOptionPane.WARNING_MESSAGE);
                                }
                            } else {
                                String jdbcUrl = "jdbc:postgresql:" + configs.getText();

                                String username = "postgres";
                                String password = "danieloh24";
                                String sqlp = "delete from transactions where trans_id='" + by8.getText() + "'";

                                try (Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
                                        Statement stmt = conn.createStatement();) {

                                    stmt.executeUpdate(sqlp);
                                    JOptionPane.showMessageDialog(null, " Transaction history removed successfully from the system", "Povidence says....", JOptionPane.INFORMATION_MESSAGE);
                                    mode.removeRow(by_table.getSelectedRow());
                                    by8.setText(ab + "");
                                    by7.setText("");
                                    by9.setText("");
                                    by11.setText("0000");
                                } catch (SQLException e) {
                                    JOptionPane.showMessageDialog(null, e.getMessage(), "Something went wrong", JOptionPane.ERROR_MESSAGE);
                                }
                            }

                        }
                    }
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Something went wrong ", JOptionPane.ERROR_MESSAGE);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Clerk.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jLabel91MouseClicked

    private void jLabel92MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel92MouseClicked
        DB_USER = uname.getText();
                DB_PAS = upass.getText();
        DefaultTableModel mode = (DefaultTableModel) by_table.getModel();
        if (by_table.getSelectedRow() == -1) {
            if (by_table.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "The table is empty", "Providence says....", JOptionPane.WARNING_MESSAGE);

            } else {
                JOptionPane.showMessageDialog(null, " You must select denominations", "Providence says....", JOptionPane.WARNING_MESSAGE);
            }
        } else {

            int input = JOptionPane.showConfirmDialog(null, " Are you sure that you want update the number od denominations of  " + by2.getSelectedItem().toString() + " " + by2.getSelectedItem().toString() + " ?", "Providence says....", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (input == JOptionPane.NO_OPTION) {

            }
            if (input == JOptionPane.YES_OPTION) {

                String a = JOptionPane.showInputDialog("How many denomiantions do you want ? ");

                try {
                    int a1 = Integer.parseInt(a);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Please enter integers for number of denominations", "Incorrect data format", JOptionPane.ERROR_MESSAGE);
                }

                int a1 = Integer.parseInt(a);
                int a2 = Integer.parseInt(by10.getText(), 10);//NUMBER OF DENOMINATIONS
                double b1 = Double.parseDouble(by9.getText());//TOTAL (DENOMINATION VALUE * NUMBER OF DENOMINATIONS)
                double c1 = Double.parseDouble(by11.getText());//ITERATION
                double d1 = c1 - b1;
                double e1 = Double.parseDouble(by7.getText());//DENOMINATIONS
                double p1 = Double.parseDouble(by4.getText());//AMOUNT SUPPOSED TO BE RECEIVED BY THE CASHIER
                double q1 = Double.parseDouble(by3.getText());//AMOUNT TO BE SOLD
                double f1 = Double.parseDouble(a);
                double g1 = e1 * f1;
                double pr = d1 + g1;
                if ((d1 + g1) > p1) {
                    JOptionPane.showMessageDialog(null, "An update on the number of denominations that you want to sell exceeds " + by2.getSelectedItem().toString() + " " + "$" + " " + q1, "", JOptionPane.WARNING_MESSAGE);
                }
                if ((d1 + g1) <= p1) {
                    mode.removeRow(by_table.getSelectedRow());
                    ResultSet rs2 = null;
                    String querie = "SELECT currency,denominations,no_denominations,total FROM clerk_accounts where account_holder='" + name.getText() + "' and denominations='" + e1 + "' and currency='" + by2.getSelectedItem().toString() + "' ";
                    try {
                        Class.forName(DB_DRIVER);
                        con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                        stm = con.createStatement();
                        rs2 = stm.executeQuery(querie);
                        boolean bool2 = rs2.isBeforeFirst();
                        while (rs2.next()) {
                            if (bool2 == true) {
                                //JOptionPane.showMessageDialog(null, "Good one");
                                double bx = rs2.getDouble("total");
                                int vx = rs2.getInt("no_denominations");

                                double dw1 = (bx + b1) - g1;
                                int dw2 = (vx + a2) - a1;

                                double pa = bx + b1;
                                int pe = vx + a1;

                                SQLRun run = new SQLRun();
                                String sqlq = "Update clerk_accounts set no_denominations='" + dw2 + "' , total='" + dw1 + "' where account_holder='" + name.getText() + "' and denominations='" + e1 + "' and currency='" + by2.getSelectedItem().toString() + "'";
                                int updated = run.sqlUpdate(sqlq);
                                if (updated > 0) {
                                    String jdbcUrl = "jdbc:postgresql:" + configs.getText();
                                    String username = "postgres";
                                    String password = "danieloh24";
                                    String sql = "Update transactions set no_denominations='" + pe + "' , total='" + pa + "' where trans_id='" + by8.getText() + "' ";

                                    try (Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
                                            Statement stmt = conn.createStatement();) {

                                        stmt.executeUpdate(sql);
                                        mode.addRow(new Object[]{by8.getText(), e1, a, g1});
                                        by11.setText(pr + "");
                                        double av = Double.parseDouble(by11.getText());
                                        double aq = Double.parseDouble(by4.getText());

                                        if (av == aq) {
                                            sending r = new sending();
                                            r.setVisible(true);
                                            ResultSet rs = null;
                                            sending.r1.setText(by2.getSelectedItem().toString());
                                            sending.r2.setText(by3.getText());
                                            sending.bru.setText(by2.getSelectedItem().toString());
                                            String query = "SELECT currency,denomination_value FROM denominations where currency='" + by2.getSelectedItem().toString() + "' ";
                                            try {
                                                rs = stm.executeQuery(query);
                                                boolean bool = rs.isBeforeFirst();
                                                while (rs.next()) {
                                                    if (bool == true) {
                                                        String zv = rs.getString("denomination_value");

                                                        sending.r3.addItem(zv);

                                                    }
                                                }
                                            } catch (SQLException xp) {

                                            }
                                        }
                                    } catch (SQLException e) {
                                        JOptionPane.showMessageDialog(null, e.getSQLState());
                                    }

                                }

                            }
                        }
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Something might have gone wrong ", JOptionPane.ERROR_MESSAGE);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(Clerk.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

            }
        }
    }//GEN-LAST:event_jLabel92MouseClicked

    private void na2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_na2ActionPerformed
       DB_USER = uname.getText();
                DB_PAS = upass.getText();
        if (!na2.getSelectedItem().equals(" ")) {
            DefaultComboBoxModel dk = new DefaultComboBoxModel();
            na5.setModel(dk);

            String querie = "SELECT base_currency,quote_currency,buying_rate,buying_rate FROM config_rates where base_currency='" + na1.getSelectedItem().toString() + "' and quote_currency='" + na2.getSelectedItem().toString() + "' ";
            String query = "SELECT denominations FROM clerk_accounts where currency='" + na1.getSelectedItem().toString() + "' ";

            try {
                Class.forName(DB_DRIVER);
                con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                stm = con.createStatement();
                rs = stm.executeQuery(querie);
                boolean bool2 = rs.isBeforeFirst();
                while (rs.next()) {
                    if (bool2 == true) {
                        double a = rs.getDouble("buying_rate");
                        double a1 = Math.round(a * 10) / 10.0;
                        rat1.setText(a1 + "");

                    }

                }
                if (bool2 == false) {
                    JOptionPane.showMessageDialog(null, "Exchange rates yet to be configured, please contact the Finance Personel ", "Exchange rates (null)", JOptionPane.WARNING_MESSAGE);
                    //acc_total.setText(" ");
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Clerk.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                Class.forName(DB_DRIVER);
                con = DriverManager.getConnection(DB_URL, DB_USER, DB_PAS);
                stm = con.createStatement();

                rs = stm.executeQuery(query);
                boolean bool = rs.isBeforeFirst();
                while (rs.next()) {
                    if (bool == true) {
                        double b = rs.getDouble("denominations");

                        na5.addItem(b + "");
                        na3.setEditable(true);
                    }
                }
                if (bool == false) {
                    JOptionPane.showMessageDialog(null, "You don't have the denominations of the quote currency you seected\n, request " + na2.getSelectedItem().toString() + " " + "from any of the strong room operator at your branch\n,highlighting the denominations you want ", "Denominations for" + na2.getSelectedItem().toString() + " " + "not found", JOptionPane.WARNING_MESSAGE);
                    na3.setEditable(false);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Clerk.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_na2ActionPerformed

    private void na3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_na3KeyPressed
        DB_USER = uname.getText();
                DB_PAS = upass.getText();
        int key = evt.getKeyCode();
        if (key == 10) {
            if (!na3.getText().isEmpty()) {
                int input = JOptionPane.showConfirmDialog(null, " Are you sure that you want to sell " + na3.getText() + " " + "?", "Providence says....", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (input == JOptionPane.NO_OPTION) {

                }
                if (input == JOptionPane.YES_OPTION) {
                    double a = Double.parseDouble(rat1.getText());
                    double b = Double.parseDouble(na3.getText());
                    double c = a * b;
                    double ab = Math.round(c * 10) / 10.0;
                    na4.setText(c + "");
                }

                if (na3.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Enter the amount of money that you want to sell", "Exchange rates (null)", JOptionPane.WARNING_MESSAGE);

                }

            }

        }
    }//GEN-LAST:event_na3KeyPressed

    private void na3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_na3KeyTyped
        char c = evt.getKeyChar();
        if (!Character.isDigit(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_na3KeyTyped

    private void na6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_na6KeyPressed
DB_USER = uname.getText();
                DB_PAS = upass.getText();
        java.util.Date date = new java.util.Date();
        long t = date.getTime();
        java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(t);
        java.sql.Date sqlDate = new java.sql.Date(t);

        DefaultTableModel dm = (DefaultTableModel) na_table.getModel();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String randstring = "";
        Random random = new Random();
        int length = 5;
        char[] text = new char[length];
        for (int ip = 0; ip < length; ip++) {
            text[ip] = characters.charAt(random.nextInt(characters.length()));
        }
        for (int ip = 0; ip < text.length; ip++) {
            randstring += text[ip];
        }
        int key = evt.getKeyCode();
        if (key == 10) {
            if (!na6.getText().isEmpty()) {
//                $ % ^  &
//                #
                String op = "buying";
                double d = Double.parseDouble(na4.getText());
                double a = Double.parseDouble(na5.getSelectedItem().toString());
                double b = Double.parseDouble(na6.getText());
                int b1 = Integer.parseInt(na6.getText());
                double c = a * b;
                double xa = Double.parseDouble(na12.getText());
                double xy = xa + c;

                ResultSet rs, rs1 = null;
                String query = "SELECT currency,denominations,no_denominations,total FROM clerk_accounts where account_holder='" + name.getText() + "' and denominations='" + a + "' and currency='" + na1.getSelectedItem().toString() + "' ";
                try {
                    Class.forName(DB_DRIVER);
                    con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                    stm = con.createStatement();
                    rs = stm.executeQuery(query);
                    boolean bool2 = rs.isBeforeFirst();
                    //*&*$%#@
                    while (rs.next()) {
                        if (bool2 == true) {
                            double za = rs.getDouble("total");
                            int ze = rs.getInt("no_denominations");
                            double da = za - c;
                            int de = ze - b1;
                            if (ze >= b1) {
                                if (d < xy) {

                                }
                                if (d >= xy) {
                                    if (na_list.getText().isEmpty()) {
                                        SQLRun run = new SQLRun();
                                        String sqlq = "Update clerk_accounts set no_denominations='" + de + "' , total='" + da + "' where account_holder='" + name.getText() + "' and denominations='" + a + "' and currency='" + na1.getSelectedItem().toString() + "'";
                                        int updated = run.sqlUpdate(sqlq);
                                        if (updated > 0) {
                                            try {
                                                con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                                pst = con.prepareStatement("insert into transactions values(?,?,?,?,?,?,?,?,?,?)");
                                                double sg = Double.parseDouble(rat1.getText());
                                                pst.setString(1, na1.getSelectedItem().toString());
                                                pst.setDouble(2, a);
                                                pst.setInt(3, b1);
                                                pst.setDouble(4, c);
                                                pst.setString(5, op);
                                                pst.setDouble(6, sg);
                                                pst.setTimestamp(7, sqlTimestamp);
                                                pst.setString(8, name.getText());
                                                pst.setString(9, branch.getText());
                                                pst.setString(10, by8.getText());

                                                int p = pst.executeUpdate();
                                                if (p > 0) {
                                                    na_list.setText(na5.getSelectedItem().toString().concat(" ").concat(","));
                                                    dm.addRow(new Object[]{randstring, na5.getSelectedItem().toString(), b1, c});
                                                    na12.setText(xy + "");
                                                }
                                            } catch (HeadlessException | SQLException ev) {
                                                JOptionPane.showMessageDialog(null, ev.getMessage(), "An errorr occured", JOptionPane.ERROR_MESSAGE);

                                            }
                                        }
                                    }
                                    if (!na_list.getText().isEmpty()) {
                                        String abx = na_list.getText();
                                        if (abx.contains(na5.getSelectedItem().toString())) {

                                        }
                                        if (!abx.contains(na5.getSelectedItem().toString())) {
                                            if (ze < b1) {
                                                JOptionPane.showMessageDialog(null, "The denominations that you want you sell are more than what you have in your account", "Number of available denominations exceeded ", JOptionPane.ERROR_MESSAGE);
                                            }
                                            if (ze >= b1) {
                                                SQLRun run = new SQLRun();
                                                String sqlq = "Update clerk_accounts set no_denominations='" + de + "' , total='" + da + "' where account_holder='" + name.getText() + "' and denominations='" + a + "' and currency='" + na1.getSelectedItem().toString() + "'";
                                                int updated = run.sqlUpdate(sqlq);
                                                if (updated > 0) {

                                                    try {
                                                        con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                                        pst = con.prepareStatement("insert into transactions values(?,?,?,?,?,?,?,?,?,?)");
                                                        double sg = Double.parseDouble(rat1.getText());
                                                        pst.setString(1, na1.getSelectedItem().toString());
                                                        pst.setDouble(2, a);
                                                        pst.setInt(3, b1);
                                                        pst.setDouble(4, c);
                                                        pst.setString(5, op);
                                                        pst.setDouble(6, sg);
                                                        pst.setTimestamp(7, sqlTimestamp);
                                                        pst.setString(8, name.getText());
                                                        pst.setString(9, branch.getText());
                                                        pst.setString(10, na9.getText());

                                                        int p = pst.executeUpdate();
                                                        if (p > 0) {
                                                            na_list.setText(na5.getSelectedItem().toString().concat(" ").concat(",").concat(abx));
                                                            dm.addRow(new Object[]{randstring, na5.getSelectedItem().toString(), b1, c});
                                                            na12.setText(xy + "");
                                                        }
                                                    } catch (HeadlessException | SQLException ev) {
                                                        JOptionPane.showMessageDialog(null, ev.getMessage(), "An errorr occured", JOptionPane.ERROR_MESSAGE);

                                                    }
                                                }

                                            }
                                        }
                                    }
                                    if (bool2 == false) {
                                        JOptionPane.showMessageDialog(null, "Please adviced that you don't have " + na2.getSelectedItem().toString() + " " + a + " " + "in your account as a counter clerk.\nTry to liase with your strong room operator to send you so that you can serve the customer", "Denomination not found", JOptionPane.ERROR_MESSAGE);

                                    }

                                }
                                if (d == xy) {
                                    //#$%^&*%
                                    sending r = new sending();
                                    r.setVisible(true);
                                    double sg = Double.parseDouble(rat1.getText());
                                    if (na_list.getText().isEmpty()) {
                                        na12.setText(xy + "");

                                        r.setVisible(true);
                                        sending.r2.setText(na3.getText());
                                        sending.r1.setText(na2.getSelectedItem().toString());
                                        sending.rcc1.setText(na1.getSelectedItem().toString());
                                        sending.r4.setEditable(true);
                                        sending.rcrate.setText(sg + "");
                                        sending.rcout.setText(na4.getText());
                                        sending.reg.setText(reg.getText());
                                        sending.branch.setText(branch.getText());
                                        sending.configs.setText(configs.getText());
                                        sending.name.setText(this.name.getText());

                                        dm.addRow(new Object[]{randstring, na5.getSelectedItem().toString(), b1, c});
                                        String querie = "SELECT currency,denomination_value FROM denominations where currency='" + na2.getSelectedItem().toString() + "' ";
                                        try {
                                            rs1 = stm.executeQuery(querie);
                                            boolean bool = rs1.isBeforeFirst();
                                            while (rs1.next()) {
                                                if (bool == true) {
                                                    String zv = rs1.getString("denomination_value");

                                                    receiving.r3.addItem(zv);

                                                }
                                            }
                                        } catch (SQLException xp) {

                                        }
                                    }
                                    if (!na_list.getText().isEmpty()) {
                                        r.setVisible(true);
                                        sending.r2.setText(na3.getText());
                                        sending.r1.setText(na2.getSelectedItem().toString());
                                        sending.rcc1.setText(na1.getSelectedItem().toString());
                                        sending.r4.setEditable(true);
                                        sending.rcrate.setText(sg + "");
                                        sending.rcout.setText(na4.getText());
                                        sending.reg.setText(reg.getText());
                                        sending.branch.setText(branch.getText());
                                        sending.configs.setText(configs.getText());
                                        sending.name.setText(this.name.getText());

                                        String querie = "SELECT currency,denomination_value FROM denominations where currency='" + na2.getSelectedItem().toString() + "' ";
                                        try {
                                            rs1 = stm.executeQuery(querie);
                                            boolean bool = rs1.isBeforeFirst();
                                            while (rs1.next()) {
                                                if (bool == true) {
                                                    String zv = rs1.getString("denomination_value");

                                                    sending.r3.addItem(zv);

                                                }
                                            }
                                        } catch (SQLException xp) {

                                        }
                                    }

                                }

                            }

                        }
                    }

                } catch (Exception e) {
                }

            }
        }
    }//GEN-LAST:event_na6KeyPressed

    private void na6KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_na6KeyTyped
        char c = evt.getKeyChar();
        if (!Character.isDigit(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_na6KeyTyped

    private void na_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_na_tableMouseClicked
        DefaultTableModel model = (DefaultTableModel) na_table.getModel();
        na9.setText(model.getValueAt(na_table.getSelectedRow(), 0).toString());
        na7.setText(model.getValueAt(na_table.getSelectedRow(), 1).toString());
        na11.setText(model.getValueAt(na_table.getSelectedRow(), 2).toString());
        na10.setText(model.getValueAt(na_table.getSelectedRow(), 3).toString());
    }//GEN-LAST:event_na_tableMouseClicked

    private void jLabel129MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel129MouseClicked
        DefaultTableModel mode = (DefaultTableModel) na_table.getModel();
DB_USER = uname.getText();
                DB_PAS = upass.getText();
        if (na9.getText().isEmpty()) {
            if (mode.getRowCount() == 0) {
                JOptionPane.showMessageDialog(rootPane, "Your table is empty, there is nothing to remove", "An empty table detected", JOptionPane.ERROR_MESSAGE);
            }
            if (mode.getRowCount() > 0) {
                JOptionPane.showMessageDialog(rootPane, "Please select a row with the denominations you want to remove", "Some information missing", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (!na9.getText().isEmpty()) {
            double e1 = Double.parseDouble(na7.getText());//DENOMINATIONS
            int a2 = Integer.parseInt(na11.getText(), 10);//NUMBER OF DENOMINATIONS
            double b1 = Double.parseDouble(na10.getText());
            double g1 = Double.parseDouble(na12.getText());
            double ab = g1 - b1;
            String querie = "SELECT currency,denominations,no_denominations,total FROM clerk_accounts where account_holder='" + name.getText() + "' and denominations='" + e1 + "' and currency='" + na2.getSelectedItem().toString() + "' ";
            try {
                Class.forName(DB_DRIVER);
                con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                stm = con.createStatement();
                rs = stm.executeQuery(querie);
                boolean bool2 = rs.isBeforeFirst();
                while (rs.next()) {
                    if (bool2 == true) {
                        //JOptionPane.showMessageDialog(null, "Good one");
                        double bx = rs.getDouble("total");
                        int vx = rs.getInt("no_denominations");
                        double dw1 = (bx + b1);
                        int dw2 = (vx + a2);

                        SQLRun run = new SQLRun();
                        String sqlq = "Update clerk_accounts set no_denominations='" + dw2 + "' , total='" + dw1 + "' where account_holder='" + name.getText() + "' and denominations='" + e1 + "' and currency='" + na2.getSelectedItem().toString() + "'";
                        int updated = run.sqlUpdate(sqlq);
                        if (updated > 0) {
                            if (na_table.getSelectedRow() == -1) {
                                if (na_table.getRowCount() == 0) {
                                    JOptionPane.showMessageDialog(null, "The table is empty", "Providence says....", JOptionPane.WARNING_MESSAGE);

                                } else {
                                    JOptionPane.showMessageDialog(null, " You must select denominations", "Providence says....", JOptionPane.WARNING_MESSAGE);
                                }
                            } else {
                                String jdbcUrl = "jdbc:postgresql:" + configs.getText();

                                String username = "postgres";
                                String password = "danieloh24";
                                String sqlp = "delete from transactions where trans_id='" + na9.getText() + "'";

                                try (Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
                                        Statement stmt = conn.createStatement();) {

                                    stmt.executeUpdate(sqlp);
                                    JOptionPane.showMessageDialog(null, " Transaction history removed successfully from the system", "Povidence says....", JOptionPane.INFORMATION_MESSAGE);
                                    mode.removeRow(na_table.getSelectedRow());
                                    na9.setText(ab + "");
                                    na7.setText("");
                                    na10.setText("");
                                    na12.setText("0000");
                                } catch (SQLException e) {
                                    JOptionPane.showMessageDialog(null, e.getMessage(), "Something went wrong", JOptionPane.ERROR_MESSAGE);
                                }
                            }

                        }
                    }
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Something went wrong ", JOptionPane.ERROR_MESSAGE);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Clerk.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jLabel129MouseClicked

    private void jLabel130MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel130MouseClicked
       DB_USER = uname.getText();
                DB_PAS = upass.getText();
        DefaultTableModel mode = (DefaultTableModel) na_table.getModel();
        if (na_table.getSelectedRow() == -1) {
            if (na_table.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "The table is empty", "Providence says....", JOptionPane.WARNING_MESSAGE);

            } else {
                JOptionPane.showMessageDialog(null, " You must select denominations", "Providence says....", JOptionPane.WARNING_MESSAGE);
            }
        } else {

            int input = JOptionPane.showConfirmDialog(null, " Are you sure that you want update the number od denominations of  " + na2.getSelectedItem().toString() + " " + by5.getSelectedItem().toString() + " ?", "Providence says....", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (input == JOptionPane.NO_OPTION) {

            }
            if (input == JOptionPane.YES_OPTION) {
                mode.removeRow(na_table.getSelectedRow());

                String a = JOptionPane.showInputDialog("How many denomiantions do you want ? ");

                int a1 = Integer.parseInt(a, 10);
                int a2 = Integer.parseInt(na11.getText(), 10);//NUMBER OF DENOMINATIONS
                double b1 = Double.parseDouble(na10.getText());//TOTAL (DENOMINATION VALUE * NUMBER OF DENOMINATIONS)
                double c1 = Double.parseDouble(na12.getText());//ITERATION
                double d1 = c1 - b1;
                double e1 = Double.parseDouble(na7.getText());//DENOMINATIONS
                double p1 = Double.parseDouble(na4.getText());//AMOUNT SUPPOSED TO BE RECEIVED BY THE CASHIER
                double q1 = Double.parseDouble(na3.getText());//AMOUNT TO BE SOLD
                double f1 = Double.parseDouble(a);
                double g1 = e1 * f1;
                double pr = d1 + g1;
                if ((d1 + g1) > p1) {
                    JOptionPane.showMessageDialog(null, "An update on the number of denominations that you want to sell exceeds " + na2.getSelectedItem().toString() + " " + "$" + " " + q1, "", JOptionPane.WARNING_MESSAGE);
                }
                if ((d1 + g1) <= p1) {

                    ResultSet rs2, rs = null;
                    String querie = "SELECT currency,denominations,no_denominations,total FROM clerk_accounts where account_holder='" + name.getText() + "' and denominations='" + e1 + "' and currency='" + na2.getSelectedItem().toString() + "' ";
                    try {
                        Class.forName(DB_DRIVER);
                        con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                        stm = con.createStatement();
                        rs2 = stm.executeQuery(querie);
                        boolean bool2 = rs2.isBeforeFirst();
                        while (rs2.next()) {
                            if (bool2 == true) {
                                //JOptionPane.showMessageDialog(null, "Good one");
                                double bx = rs2.getDouble("total");
                                int vx = rs2.getInt("no_denominations");

                                double dw1 = (bx + b1) - g1;
                                int dw2 = (vx + a2) - a1;

                                double pa = bx + b1;
                                int pe = vx + a1;

                                SQLRun run = new SQLRun();
                                String sqlq = "Update clerk_accounts set no_denominations='" + dw2 + "' , total='" + dw1 + "' where account_holder='" + name.getText() + "' and denominations='" + e1 + "' and currency='" + na2.getSelectedItem().toString() + "'";
                                int updated = run.sqlUpdate(sqlq);
                                if (updated > 0) {
                                    String jdbcUrl = "jdbc:postgresql:" + configs.getText();
                                    String username = "postgres";
                                    String password = "danieloh24";
                                    String sql = "Update transactions set no_denominations='" + pe + "' , total='" + pa + "' where trans_id='" + na9.getText() + "' ";

                                    try (Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
                                            Statement stmt = conn.createStatement();) {

                                        stmt.executeUpdate(sql);
                                        mode.addRow(new Object[]{na9.getText(), e1, a, g1});
                                        na12.setText(pr + "");
                                        double av = Double.parseDouble(na12.getText());
                                        double aq = Double.parseDouble(na4.getText());

                                        if (av == aq) {
                                            sending r = new sending();
                                            r.setVisible(true);

                                            sending.r1.setText(na2.getSelectedItem().toString());
                                            sending.r2.setText(na3.getText());
                                            sending.configs.setText(configs.getText());
                                            sending.r4.setEditable(true);
                                            //ResultSet  rs = null;
                                            String query = "SELECT currency,denomination_value FROM denominations where currency='" + na2.getSelectedItem().toString() + "' ";
                                            try {
                                                rs = stm.executeQuery(query);
                                                boolean bool = rs.isBeforeFirst();
                                                while (rs.next()) {
                                                    if (bool == true) {
                                                        String zv = rs.getString("denomination_value");

                                                        sending.r3.addItem(zv);

                                                    }
                                                }
                                            } catch (SQLException xp) {

                                            }
                                        }
                                    } catch (SQLException e) {
                                        JOptionPane.showMessageDialog(null, e.getSQLState());
                                    }

                                }

                            }
                        }
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Something might have gone wrong", JOptionPane.ERROR_MESSAGE);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(Clerk.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

            }
        }
    }//GEN-LAST:event_jLabel130MouseClicked

    private void na1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_na1ActionPerformed
        DB_USER = uname.getText();
                DB_PAS = upass.getText();
        if (!na1.getSelectedItem().equals(" ")) {

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

                        if (!c.matches(xz) && !na1.getSelectedItem().equals(b)) {
                            //sell2.addItem(" ");
                            //sell2.addItem(b);
                            na2.addItem(b);

                        }

                    }
                }
                if (bool == false) {

                    //JOptionPane.showMessageDialog(rootPane, "You have configured exchange rates against the base currency","Base currency not available",JOptionPane.WARNING_MESSAGE);
                }

            } catch (ClassNotFoundException | SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "An error occured while trying to fetch your data", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_na1ActionPerformed

    private void na6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_na6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_na6ActionPerformed

    private void by6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_by6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_by6ActionPerformed

    private void jLabel199MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel199MouseClicked
       DB_USER = uname.getText();
                DB_PAS = upass.getText();
        java.util.Date date = new java.util.Date();
        long t = date.getTime();
        java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(t);
        java.sql.Date sqlDate = new java.sql.Date(t);
        if (rx1.getText().isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Please enter the subject of your notification", "Incomplete Data", JOptionPane.ERROR_MESSAGE);
        }
        if (rx2.getText().isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Please provide the notification you want to send", "Incomplete Data", JOptionPane.ERROR_MESSAGE);
        }
        if (rx4.getText().isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Please enter the recipients of your notification", "Incomplete Data", JOptionPane.ERROR_MESSAGE);
        }
        if (!rx4.getText().isEmpty() && !rx2.getText().isEmpty() && !rx1.getText().isEmpty()) {

            try {
                Class.forName(DB_DRIVER);
                con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                pst = con.prepareStatement("insert into notifications values(?,?,?,?,?)");
                pst.setString(1, rx1.getText());
                pst.setString(2, rx2.getText());
                pst.setString(3, rx5.getText());
                pst.setString(4, name.getText());
                pst.setTimestamp(5, sqlTimestamp);

                int i = pst.executeUpdate();
                if (i > 0) {
                    JOptionPane.showMessageDialog(null, "Notification posted successfuly" + " " + " ", " Sending notification....", JOptionPane.INFORMATION_MESSAGE);

                    rx1.setText("");
                    rx2.setText("");
                    rx4.setText("");

                }
            } catch (SQLException ev) {
                JOptionPane.showMessageDialog(null, ev.getMessage());

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Clerk.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jLabel199MouseClicked

    private void rx3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rx3ActionPerformed
DB_USER = uname.getText();
                DB_PAS = upass.getText();
        if (!rx3.getSelectedItem().equals("")) {

            if (rx4.getText().isEmpty()) {
                rx4.setText(rx3.getSelectedItem().toString().concat("\t"));
                rx5.setText(rx3.getSelectedItem().toString().concat("\t"));
            }
            if (!rx4.getText().isEmpty()) {
                String av = rx4.getText();
                if (av.contains(rx3.getSelectedItem().toString())) {
                    //JOptionPane.showMessageDialog(rootPane, "You have already added "+n3.getSelectedItem().toString()+" "+"on your recipient list");

                }
                if (!av.contains(rx3.getSelectedItem().toString())) {
                    rx5.setText(rx3.getSelectedItem().toString().concat("\t").concat(av));
                    rx4.setText(rx3.getSelectedItem().toString().concat("\n").concat(av));
                }

            }

        }
    }//GEN-LAST:event_rx3ActionPerformed

    private void jLabel201MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel201MouseClicked
        rx4.setText("");
        rx5.setText("");
    }//GEN-LAST:event_jLabel201MouseClicked

    private void rz1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rz1ActionPerformed
DB_USER = uname.getText();
                DB_PAS = upass.getText();
        String query = "SELECT subject,notification_message,recipients,sender,date FROM notifications where subject='" + rz1.getSelectedItem().toString() + "' ";
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
                    rz2.setText(a);
                    rz3.setText(c);
                    rz4.setText(b);

                }
            }

        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Something went wrong", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_rz1ActionPerformed

    private void ck4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ck4ActionPerformed
       DB_USER = uname.getText();
                DB_PAS = upass.getText();
        if (ck4.getSelectedItem().equals("")) {

        }
        if (!ck4.getSelectedItem().equals("")) {
            String brn = "Branch Manager";
            String query1 = "SELECT fname,lname,gender,region,branch,position,phone_number,national_id,user_id FROM users where branch='" + branch.getText() + "' and position!='" + brn + "'";
            try {
                Class.forName(DB_DRIVER);
                con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                stm = con.createStatement();
                rs = stm.executeQuery(query1);
                boolean boolm = rs.isBeforeFirst();
                while (rs.next()) {
                    if (boolm == true) {
                        String a = rs.getString("fname");
                        String b = rs.getString("lname");
                        String d = rs.getString("position");
                        String c = a.concat(" ").concat(b);
                        if (ck4.getSelectedItem().equals(c)) {
                            ck5.setText(d);
                        }
                    }
                }
                if (boolm == false) {

                    JOptionPane.showMessageDialog(rootPane, "Currently you don't have that currency at your branch", "Selected currency not available", JOptionPane.WARNING_MESSAGE);
                }
            } catch (ClassNotFoundException | SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Something went wrong", JOptionPane.ERROR_MESSAGE);
            }
        }

    }//GEN-LAST:event_ck4ActionPerformed

    private void mariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mariActionPerformed


    }//GEN-LAST:event_mariActionPerformed
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
            java.util.logging.Logger.getLogger(Clerk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Clerk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Clerk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Clerk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Clerk().setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> acc_currency;
    private javax.swing.JTable acc_table;
    private javax.swing.JFormattedTextField acc_total;
    private javax.swing.JLabel amtt;
    private javax.swing.JLabel amtt1;
    private javax.swing.JPanel animation;
    public static final javax.swing.JLabel branch = new javax.swing.JLabel();
    private javax.swing.JPanel buying;
    public static final javax.swing.JFormattedTextField by1 = new javax.swing.JFormattedTextField();
    private javax.swing.JLabel by10;
    private javax.swing.JFormattedTextField by11;
    private javax.swing.JComboBox<String> by2;
    private javax.swing.JFormattedTextField by3;
    private javax.swing.JFormattedTextField by4;
    private javax.swing.JComboBox<String> by5;
    private javax.swing.JFormattedTextField by6;
    private javax.swing.JLabel by7;
    private javax.swing.JLabel by8;
    private javax.swing.JLabel by9;
    private javax.swing.JLabel by_list;
    public static final javax.swing.JLabel by_rat = new javax.swing.JLabel();
    private javax.swing.JTable by_table;
    private javax.swing.JLabel carr;
    private javax.swing.JLabel carr1;
    private javax.swing.JComboBox<String> ck1;
    private javax.swing.JComboBox<String> ck2;
    private javax.swing.JFormattedTextField ck3;
    private javax.swing.JComboBox<String> ck4;
    private javax.swing.JFormattedTextField ck5;
    private javax.swing.JTable ck_table;
    public static final javax.swing.JLabel configs = new javax.swing.JLabel();
    private javax.swing.JLabel configs2;
    private javax.swing.JComboBox<String> cse1;
    private javax.swing.JComboBox<String> cse2;
    private javax.swing.JFormattedTextField cse3;
    private javax.swing.JFormattedTextField cse4;
    private javax.swing.JComboBox<String> cse5;
    private javax.swing.JFormattedTextField cse6;
    private javax.swing.JTable cse_table;
    private javax.swing.JLabel cx_currency;
    private javax.swing.JLabel cx_denom;
    private javax.swing.JLabel cx_from;
    private javax.swing.JLabel cx_id;
    private javax.swing.JLabel cx_num;
    private javax.swing.JLabel cx_total;
    private javax.swing.JPanel dashboard_home;
    private javax.swing.JPanel dashboard_master;
    private javax.swing.JPanel dashboard_operations;
    private javax.swing.JPanel dashboard_rates;
    private javax.swing.JPanel dashboard_reports;
    public static final javax.swing.JLabel day = new javax.swing.JLabel();
    private javax.swing.JLabel dmt;
    private javax.swing.JLabel dmt1;
    private javax.swing.JPanel header;
    private javax.swing.JComboBox<String> jComboBox15;
    private javax.swing.JComboBox<String> jComboBox7;
    private javax.swing.JComboBox<String> jComboBox8;
    private javax.swing.JEditorPane jEditorPane10;
    private javax.swing.JEditorPane jEditorPane11;
    private javax.swing.JEditorPane jEditorPane12;
    private javax.swing.JFormattedTextField jFormattedTextField16;
    private javax.swing.JFormattedTextField jFormattedTextField17;
    private javax.swing.JFormattedTextField jFormattedTextField18;
    private javax.swing.JFormattedTextField jFormattedTextField19;
    private javax.swing.JFormattedTextField jFormattedTextField20;
    private javax.swing.JFormattedTextField jFormattedTextField35;
    private javax.swing.JFormattedTextField jFormattedTextField38;
    private javax.swing.JFormattedTextField jFormattedTextField39;
    private javax.swing.JFormattedTextField jFormattedTextField42;
    private javax.swing.JFormattedTextField jFormattedTextField43;
    private javax.swing.JFormattedTextField jFormattedTextField45;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
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
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel199;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel200;
    private javax.swing.JLabel jLabel201;
    private javax.swing.JLabel jLabel202;
    private javax.swing.JLabel jLabel203;
    private javax.swing.JLabel jLabel204;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
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
    private javax.swing.JLabel jLabel5;
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
    private javax.swing.JLabel jLabel99;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel46;
    private javax.swing.JPanel jPanel47;
    private javax.swing.JPanel jPanel48;
    private javax.swing.JPanel jPanel49;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel50;
    private javax.swing.JPanel jPanel51;
    private javax.swing.JPanel jPanel52;
    private javax.swing.JPanel jPanel55;
    private javax.swing.JPanel jPanel56;
    private javax.swing.JPanel jPanel57;
    private javax.swing.JPanel jPanel59;
    private javax.swing.JPanel jPanel60;
    private javax.swing.JPanel jPanel61;
    private javax.swing.JPanel jPanel62;
    private javax.swing.JPanel jPanel65;
    private javax.swing.JPanel jPanel66;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane19;
    private javax.swing.JScrollPane jScrollPane20;
    private javax.swing.JScrollPane jScrollPane21;
    private javax.swing.JScrollPane jScrollPane25;
    private javax.swing.JScrollPane jScrollPane26;
    private javax.swing.JScrollPane jScrollPane27;
    private javax.swing.JScrollPane jScrollPane28;
    private javax.swing.JScrollPane jScrollPane30;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane10;
    private javax.swing.JTabbedPane jTabbedPane11;
    private javax.swing.JTabbedPane jTabbedPane13;
    private javax.swing.JTabbedPane jTabbedPane14;
    private javax.swing.JTabbedPane jTabbedPane15;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane7;
    private javax.swing.JTabbedPane jTabbedPane8;
    private javax.swing.JTabbedPane jTabbedPane9;
    private javax.swing.JTable kb;
    private javax.swing.JComboBox<String> mari;
    public static final javax.swing.JLabel month = new javax.swing.JLabel();
    public static javax.swing.JLabel my_id;
    private javax.swing.JLabel my_list;
    private javax.swing.JLabel my_list1;
    private javax.swing.JLabel my_text;
    private javax.swing.JComboBox<String> na1;
    private javax.swing.JLabel na10;
    private javax.swing.JLabel na11;
    private javax.swing.JFormattedTextField na12;
    private javax.swing.JComboBox<String> na2;
    private javax.swing.JFormattedTextField na3;
    private javax.swing.JFormattedTextField na4;
    private javax.swing.JComboBox<String> na5;
    private javax.swing.JFormattedTextField na6;
    private javax.swing.JLabel na7;
    private javax.swing.JLabel na9;
    private javax.swing.JLabel na_list;
    private javax.swing.JTable na_table;
    public static final javax.swing.JLabel name = new javax.swing.JLabel();
    private javax.swing.JPanel opening_balances;
    public static final javax.swing.JLabel phy = new javax.swing.JLabel();
    private javax.swing.JComboBox<String> pk1;
    private javax.swing.JComboBox<String> pk2;
    private javax.swing.JFormattedTextField pk3;
    private javax.swing.JTable pk_table;
    private javax.swing.JLabel posi;
    public static final javax.swing.JLabel rat = new javax.swing.JLabel();
    public static final javax.swing.JLabel rat1 = new javax.swing.JLabel();
    public static final javax.swing.JLabel rat2 = new javax.swing.JLabel();
    private javax.swing.JPanel rates;
    private javax.swing.JTable rb;
    public static final javax.swing.JLabel reg = new javax.swing.JLabel();
    private javax.swing.JPanel reports;
    private javax.swing.JTable req_table;
    private javax.swing.JPanel requests;
    private javax.swing.JFormattedTextField rx1;
    private javax.swing.JEditorPane rx2;
    private static javax.swing.JComboBox<String> rx3;
    private javax.swing.JEditorPane rx4;
    private javax.swing.JLabel rx5;
    private javax.swing.JComboBox<String> rz1;
    private javax.swing.JFormattedTextField rz2;
    private javax.swing.JFormattedTextField rz3;
    private javax.swing.JEditorPane rz4;
    public static final javax.swing.JFormattedTextField sell1 = new javax.swing.JFormattedTextField();
    private javax.swing.JFormattedTextField sell14;
    private javax.swing.JComboBox<String> sell2;
    private javax.swing.JFormattedTextField sell3;
    private javax.swing.JFormattedTextField sell4;
    private javax.swing.JComboBox<String> sell5;
    private javax.swing.JFormattedTextField sell6;
    private javax.swing.JFormattedTextField sell7;
    private javax.swing.JTable sell_table;
    private javax.swing.JPanel selling;
    private javax.swing.JLabel swp;
    private javax.swing.JLabel swp1;
    public static final javax.swing.JLabel time = new javax.swing.JLabel();
    private javax.swing.JPanel transfers;
    public static final javax.swing.JLabel uname = new javax.swing.JLabel();
    public static final javax.swing.JLabel upass = new javax.swing.JLabel();
    private javax.swing.JPanel withdraw;
    public static final javax.swing.JLabel year = new javax.swing.JLabel();
    // End of variables declaration//GEN-END:variables

}
