/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import db_com.SQLRun;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import org.apache.commons.validator.routines.InetAddressValidator;

/*
 *
 * @author Sir Chikukwa
 */
public class Config_Control extends javax.swing.JFrame {

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

    public Config_Control() {
        initComponents();
        this.setLocationRelativeTo(null);
        connect();
        shorttime();
        connect1();
        db_users_connect();
        db_password_connect();
        server.setVisible(false);
        settings.setVisible(false);
        ld.setVisible(false);
        ld1.setVisible(false);
        ld2.setVisible(false);
        ld3.setVisible(false);
        ld4.setVisible(false);
        ld6.setVisible(false);
        ld7.setVisible(false);
        ld8.setVisible(false);
        ml.setVisible(false);
        jocBusyIcon1.setBusy(true);
        day.setText(now("EEEE"));
        month.setText(now("dd MMMM"));
        year.setText(now("YYYY"));
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

    public static String encrypt(String key) {
        String result = "";
        int l = key.length();
        char ch;
        for (int i = 0; i < l; i++) {
            ch = key.charAt(i);
            ch += 10;
            result += ch;
        }
        return result;
    }
//decrypting value

    public static String decrypt(String key) {
        String result = "";
        int l = key.length();
        char ch;
        for (int i = 0; i < l; i++) {
            ch = key.charAt(i);
            ch -= 10;
            result += ch;
        }
        return result;
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

    public void insert_admin() {
        system_tables();
        String ak1 = "	Providence";
        String ak2 = "Chikukwa";
        String ak3 = "male";
        String aky = "0784340477";
        int ak4 = Integer.parseInt(aky);
        String ak5 = "22-2035355 B22";
        String ak6 = "none";
        String ak7 = "none";
        String ak8 = "System Administrator";
        String ak9 = "developer@cds";
        String ak10 = "cds@2020";
        String ak11 = "Developer";
        DB_USER = uname.getText();
        DB_PAS = upass.getText();

        String en = encrypt("cds@2020");

        System.out.println("This is the encrypted key " + en);

        System.out.println("This is the dencrypted key " + decrypt(en));

        try {
            con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
            pst = con.prepareStatement("insert  into users(fname,lname,gender,phone_number,national_id,region,branch,position,username,password,created_by)"
                    + " values('" + ak1 + "','" + ak2 + "','" + ak3 + "','" + ak4 + "','" + ak5 + "','" + ak6 + "','" + ak7 + "','" + ak8 + "','" + ak9 + "','"+en+"','" + ak11 + "')");
            int p = pst.executeUpdate();
            if (p > 0) {
                JOptionPane.showMessageDialog(rootPane, ak9 + " " + "is your default username and\n" + ak10 + " " + "is your default password for System Administrator");
            }
        } catch (HeadlessException | SQLException ev) {
            JOptionPane.showMessageDialog(null, ev.getMessage(), "An error occured while creating a default account for the Developer", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void system_tables() {
        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        try {
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
            stm = con.createStatement();
            String sql = "CREATE TABLE users "
                    + "(fname VARCHAR(255), "
                    + " lname VARCHAR(255),"
                    + " gender VARCHAR(255),"
                    + " phone_number INTEGER,"
                    + " national_id VARCHAR(255),"
                    + " region VARCHAR(255),"
                    + " branch VARCHAR(255),"
                    + " position VARCHAR(255),"
                    + " username VARCHAR(255),"
                    + " password VARCHAR(255),"
                    + " created_by VARCHAR(255),"
                    + " user_id serial,"
                    + " PRIMARY KEY (user_id ))";
            stm.executeUpdate(sql);
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        try {
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
            stm = con.createStatement();
            String sql = "CREATE TABLE regions "
                    + "(region_name VARCHAR(255), "
                    + " description VARCHAR(255),"
                    + " created_by VARCHAR(255),"
                    + " creation_date timestamp without time zone,"
                    + " region_id serial,"
                    + " PRIMARY KEY (region_id))";
            stm.executeUpdate(sql);
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        try {
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
            stm = con.createStatement();
            String sql = "CREATE TABLE branches "
                    + "(region_name VARCHAR(255), "
                    + " branch_name VARCHAR(255),"
                    + " area_code INTEGER,"
                    + " description VARCHAR(255),"
                    + " created_by VARCHAR(255),"
                    + " creation_date timestamp without time zone,"
                    + " branch_id serial,"
                    + " PRIMARY KEY (branch_id ))";
            stm.executeUpdate(sql);
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        try {
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
            stm = con.createStatement();
            String sql = "CREATE TABLE currencies "
                    + "(currency_name VARCHAR(255), "
                    + " short_code VARCHAR(255),"
                    + " base_currency VARCHAR(255),"
                    + " currency_id serial,"
                    + " PRIMARY KEY (currency_id))";
            stm.executeUpdate(sql);
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        try {
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
            stm = con.createStatement();
            String sql = "CREATE TABLE config_rates "
                    + "(base_currency VARCHAR(255), "
                    + " quote_currency VARCHAR(255),"
                    + " buying_rate float,"
                    + " selling_rate float,"
                    + " configured_by VARCHAR(255),"
                    + " configuration_date timestamp without time zone,"
                    + " exchange_rate_id serial,"
                    + " PRIMARY KEY (exchange_rate_id))";
            stm.executeUpdate(sql);
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        try {
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
            stm = con.createStatement();
            String sql = "CREATE TABLE denominations "
                    + "(currency VARCHAR(255), "
                    + " denomination_value float,"
                    + " denomination_id serial,"
                    + " PRIMARY KEY (denomination_id ))";
            stm.executeUpdate(sql);
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        try {
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
            stm = con.createStatement();
            String sql = "CREATE TABLE notifications "
                    + "(subject VARCHAR(255), "
                    + " notification_message VARCHAR(255),"
                    + " recipients VARCHAR(255),"
                    + " sender VARCHAR(255),"
                    + " date timestamp without time zone,"
                    + " notification_id serial,"
                    + " PRIMARY KEY (notification_id))";
            stm.executeUpdate(sql);
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        try {
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
            stm = con.createStatement();
            String sql = "CREATE TABLE initial_balances "
                    + "(branch VARCHAR(255), "
                    + " currency VARCHAR(255),"
                    + " denominations float,"
                    + " no_denominations integer,"
                    + " total float,"
                    + " send_by VARCHAR(255),"
                    + " date timestamp without time zone,"
                    + " balance_id serial,"
                    + " PRIMARY KEY (balance_id ))";
            stm.executeUpdate(sql);
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        try {
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
            stm = con.createStatement();
            String sql = "CREATE TABLE branch_accounts "
                    + "(branch VARCHAR(255), "
                    + " currency VARCHAR(255),"
                    + " denominations float,"
                    + " no_denominations integer,"
                    + " total float,"
                    + " send_by VARCHAR(255),"
                    + " date timestamp without time zone,"
                    + " branch_account_id serial,"
                    + " PRIMARY KEY (branch_account_id ))";
            stm.executeUpdate(sql);
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        try {
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
            stm = con.createStatement();
            String sql = "CREATE TABLE transactions_history "
                    + "(base_currency VARCHAR(255), "
                    + " quote_currency VARCHAR(255),"
                    + " region VARCHAR(255),"
                    + " branch VARCHAR(255),"
                    + " cashier VARCHAR(255),"
                    + " amount_traded_out float,"
                    + " amount_traded_in float,"
                    + " exchange_rate float,"
                    + " date Date,"
                    + " customer VARCHAR(255),"
                    + " id_number VARCHAR(255),"
                    + " phone_number integer,"
                    + " operation VARCHAR(255),"
                    + " transactions_history_id serial,"
                    + " PRIMARY KEY (transactions_history_id))";
            stm.executeUpdate(sql);
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        try {
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
            stm = con.createStatement();
            String sql = "CREATE TABLE transactions "
                    + "(currency VARCHAR(255), "
                    + " denominations float,"
                    + " no_denominations integer,"
                    + " total float,"
                    + " operation VARCHAR(255),"
                    + " exchange_rate float,"
                    + " date Date,"
                    + " cashier VARCHAR(255),"
                    + " branch VARCHAR(255),"
                    + " trans_id VARCHAR(255))";
            stm.executeUpdate(sql);
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        try {
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
            stm = con.createStatement();
            String sql = "CREATE TABLE clerk_accounts "
                    + "(currency VARCHAR(255), "
                    + " denominations float,"
                    + " no_denominations integer,"
                    + " total float,"
                    + " account_holder VARCHAR(255),"
                    + " account_id serial,"
                    + " PRIMARY KEY (account_id))";
            stm.executeUpdate(sql);
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        try {
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
            stm = con.createStatement();
            String sql = "CREATE TABLE clerks_operators "
                    + "(send_by VARCHAR(255), "
                    + " sender_position VARCHAR(255),"
                    + " receiver VARCHAR(255),"
                    + " receiver_position VARCHAR(255),"
                    + " branch VARCHAR(255),"
                    + " currency VARCHAR(255),"
                    + " denominations float,"
                    + " no_denominations integer,"
                    + " total float,"
                    + " date Date,"
                    + " clerks_operators_id serial,"
                    + " PRIMARY KEY (clerks_operators_id))";
            stm.executeUpdate(sql);
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        try {
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
            stm = con.createStatement();
            String sql = "CREATE TABLE transfers_log "
                    + "(sender VARCHAR(255), "
                    + " receiver VARCHAR(255),"
                    + " currency VARCHAR(255),"
                    + " denominations float,"
                    + " no_denominations integer,"
                    + " total float,"
                    + " date Date,"
                    + " transfers_log_id serial,"
                    + " PRIMARY KEY (transfers_log_id))";
            stm.executeUpdate(sql);
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        try {
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
            stm = con.createStatement();
            String sql = "CREATE TABLE requests "
                    + "(currency VARCHAR(255), "
                    + " denominations float,"
                    + " no_denominations integer,"
                    + " total float,"
                    + " request_from VARCHAR(255),"
                    + " request_to VARCHAR(255),"
                    + " date Date,"
                    + " request_id serial,"
                    + " PRIMARY KEY (request_id))";
            stm.executeUpdate(sql);
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        try {
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
            stm = con.createStatement();
            String sql = "CREATE TABLE requests_history "
                    + "(currency VARCHAR(255), "
                    + " denominations float,"
                    + " no_denominations integer,"
                    + " total float,"
                    + " request_from VARCHAR(255),"
                    + " request_to VARCHAR(255),"
                    + " date Date,"
                    + " request_id serial,"
                    + " PRIMARY KEY (request_id))";
            stm.executeUpdate(sql);
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jocBusyIcon1 = new com.xzq.osc.JocBusyIcon();
        jocAction1 = new com.xzq.osc.JocAction();
        jocTrayIcon1 = new com.xzq.osc.JocTrayIcon();
        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jocTabbedPane1 = new com.xzq.osc.JocTabbedPane();
        server = new javax.swing.JPanel();
        jLabel117 = new javax.swing.JLabel();
        jLabel102 = new javax.swing.JLabel();
        jLabel103 = new javax.swing.JLabel();
        jLabel104 = new javax.swing.JLabel();
        jLabel105 = new javax.swing.JLabel();
        a3 = new javax.swing.JFormattedTextField();
        a2 = new javax.swing.JFormattedTextField();
        a1 = new javax.swing.JComboBox<>();
        a5 = new javax.swing.JFormattedTextField();
        a4 = new javax.swing.JFormattedTextField();
        settings = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        ld = new javax.swing.JLabel();
        ld1 = new javax.swing.JLabel();
        ld2 = new javax.swing.JLabel();
        ld3 = new javax.swing.JLabel();
        ld4 = new javax.swing.JLabel();
        ld6 = new javax.swing.JLabel();
        ld7 = new javax.swing.JLabel();
        ld8 = new javax.swing.JLabel();
        ml = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        day = new javax.swing.JLabel();
        month = new javax.swing.JLabel();
        year = new javax.swing.JLabel();
        time = new javax.swing.JLabel();
        configs2 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem2 = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuItem3 = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        jMenuItem4 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(51, 102, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 102, 255)));
        jPanel1.setToolTipText("ExpoCurrency Enterprise");
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Yu Gothic UI Semilight", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Developer Name :");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        name.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        name.setForeground(new java.awt.Color(248, 148, 6));
        name.setText("Developer");
        jPanel1.add(name, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 10, 130, 20));

        configs.setText("jLabel7");
        jPanel1.add(configs, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 34, -1, 0));

        my_id.setText("jLabel7");
        jPanel1.add(my_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, 0));

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setFont(new java.awt.Font("Yu Gothic UI Semilight", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Connection Status");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, -1, -1));

        status.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        status.setForeground(new java.awt.Color(248, 148, 6));
        status.setText("Online");
        jPanel1.add(status, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 50, 150, -1));

        jocTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));
        jocTabbedPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(248, 148, 6)));
        jocTabbedPane1.setBorderColor(new java.awt.Color(255, 255, 255));
        jocTabbedPane1.setShowCloseButton(false);
        jocTabbedPane1.setFocusable(false);
        jocTabbedPane1.setFont(new java.awt.Font("Yu Gothic UI Semilight", 0, 14)); // NOI18N
        jocTabbedPane1.setOpaque(true);

        server.setBackground(new java.awt.Color(255, 255, 255));
        server.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        server.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel117.setFont(new java.awt.Font("Yu Gothic UI Semilight", 0, 14)); // NOI18N
        jLabel117.setText("Server type");
        server.add(jLabel117, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 26, 100, -1));

        jLabel102.setFont(new java.awt.Font("Yu Gothic UI Semilight", 0, 14)); // NOI18N
        jLabel102.setText("Server IP Address");
        server.add(jLabel102, new org.netbeans.lib.awtextra.AbsoluteConstraints(9, 64, 110, -1));

        jLabel103.setFont(new java.awt.Font("Yu Gothic UI Semilight", 0, 14)); // NOI18N
        jLabel103.setText("Database Name");
        server.add(jLabel103, new org.netbeans.lib.awtextra.AbsoluteConstraints(9, 100, 110, -1));

        jLabel104.setFont(new java.awt.Font("Yu Gothic UI Semilight", 0, 14)); // NOI18N
        jLabel104.setText("Access Name");
        server.add(jLabel104, new org.netbeans.lib.awtextra.AbsoluteConstraints(3, 136, 100, -1));

        jLabel105.setFont(new java.awt.Font("Yu Gothic UI Semilight", 0, 14)); // NOI18N
        jLabel105.setText("Access Password");
        server.add(jLabel105, new org.netbeans.lib.awtextra.AbsoluteConstraints(3, 176, 122, -1));

        a3.setFont(new java.awt.Font("Yu Gothic UI Semilight", 0, 14)); // NOI18N
        a3.setOpaque(false);
        server.add(a3, new org.netbeans.lib.awtextra.AbsoluteConstraints(128, 93, 190, 28));

        a2.setFont(new java.awt.Font("Yu Gothic UI Semilight", 0, 14)); // NOI18N
        a2.setOpaque(false);
        a2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                a2KeyTyped(evt);
            }
        });
        server.add(a2, new org.netbeans.lib.awtextra.AbsoluteConstraints(128, 57, 190, 28));

        a1.setEditable(true);
        a1.setFont(new java.awt.Font("Yu Gothic UI Semilight", 0, 14)); // NOI18N
        a1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "Local Server", "Remote Server" }));
        a1.setOpaque(false);
        a1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                a1ActionPerformed(evt);
            }
        });
        server.add(a1, new org.netbeans.lib.awtextra.AbsoluteConstraints(128, 21, 190, 29));

        a5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        a5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                a5KeyPressed(evt);
            }
        });
        server.add(a5, new org.netbeans.lib.awtextra.AbsoluteConstraints(128, 170, 190, 30));

        a4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        server.add(a4, new org.netbeans.lib.awtextra.AbsoluteConstraints(128, 130, 190, 30));

        jocTabbedPane1.addTab("Server Configurations", server);

        settings.setBackground(new java.awt.Color(255, 255, 255));
        settings.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Yu Gothic UI Semilight", 0, 14)); // NOI18N
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/setupimg/add_database_32px.png"))); // NOI18N
        jLabel8.setText("Create Database");
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel8MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel8MouseEntered(evt);
            }
        });
        settings.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 60, 148, 34));

        jLabel9.setFont(new java.awt.Font("Yu Gothic UI Semilight", 0, 14)); // NOI18N
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/setupimg/table_1_32px.png"))); // NOI18N
        jLabel9.setText("   Create Tables");
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel9MouseClicked(evt);
            }
        });
        settings.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 60, 155, 34));

        jLabel10.setFont(new java.awt.Font("Yu Gothic UI Semilight", 0, 14)); // NOI18N
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/setupimg/return_book_32px.png"))); // NOI18N
        jLabel10.setText("Upload Contents");
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
        });
        settings.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 60, 142, 34));

        jLabel11.setFont(new java.awt.Font("Yu Gothic UI Semilight", 0, 14)); // NOI18N
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/setupimg/data_backup_32px.png"))); // NOI18N
        jLabel11.setText("Create a backup");
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
        });
        settings.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 160, 148, 34));

        jLabel12.setFont(new java.awt.Font("Yu Gothic UI Semilight", 0, 14)); // NOI18N
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/setupimg/remove_property_32px.png"))); // NOI18N
        jLabel12.setText("Truncanate Tables");
        jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel12MouseClicked(evt);
            }
        });
        settings.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 160, 155, 34));

        jLabel13.setFont(new java.awt.Font("Yu Gothic UI Semilight", 0, 14)); // NOI18N
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/setupimg/delete_table_32px.png"))); // NOI18N
        jLabel13.setText("   Drop tables");
        jLabel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel13MouseClicked(evt);
            }
        });
        settings.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 160, 142, 34));

        jLabel14.setFont(new java.awt.Font("Yu Gothic UI Semilight", 0, 14)); // NOI18N
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-delete-bin-32.png"))); // NOI18N
        jLabel14.setText("Drop database");
        jLabel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel14MouseClicked(evt);
            }
        });
        settings.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 270, 148, 34));

        jLabel15.setFont(new java.awt.Font("Yu Gothic UI Semilight", 0, 14)); // NOI18N
        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/setupimg/table_1_32px.png"))); // NOI18N
        jLabel15.setText("Database type");
        jLabel15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel15MouseClicked(evt);
            }
        });
        settings.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 270, 155, 34));

        jLabel16.setFont(new java.awt.Font("Yu Gothic UI Semilight", 0, 14)); // NOI18N
        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/setupimg/synchronize_32px.png"))); // NOI18N
        jLabel16.setText("   Synchronize");
        jLabel16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel16MouseClicked(evt);
            }
        });
        settings.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 270, 142, 34));

        ld.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ld.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        settings.add(ld, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 230, 130, 100));

        ld1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ld1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        settings.add(ld1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 10, 130, 100));

        ld2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ld2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        settings.add(ld2, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 120, 130, 100));

        ld3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ld3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        settings.add(ld3, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 120, 130, 100));

        ld4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ld4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        settings.add(ld4, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 120, 130, 100));

        ld6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ld6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        settings.add(ld6, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 10, 130, 100));

        ld7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ld7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        settings.add(ld7, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 230, 130, 100));

        ld8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ld8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        settings.add(ld8, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 230, 130, 100));
        settings.add(ml, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 40, 30, 20));

        jocTabbedPane1.addTab("Database Configurations", settings);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jTable1.setAutoCreateRowSorter(true);
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Table name", "Number of entries", "Number of columns", "Primary key"
            }
        ));
        jTable1.setRowHeight(20);
        jTable1.setShowHorizontalLines(false);
        jTable1.setShowVerticalLines(false);
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 988, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
        );

        jocTabbedPane1.addTab("System databases", jPanel2);

        jPanel1.add(jocTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 80, 995, 460));

        day.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        day.setForeground(new java.awt.Color(255, 255, 255));
        day.setText("jLabel2");
        jPanel1.add(day, new org.netbeans.lib.awtextra.AbsoluteConstraints(357, 10, 170, 20));

        month.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        month.setForeground(new java.awt.Color(255, 255, 255));
        month.setText("jLabel2");
        jPanel1.add(month, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 10, 100, 20));

        year.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        year.setForeground(new java.awt.Color(255, 255, 255));
        year.setText("jLabel2");
        jPanel1.add(year, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 10, 90, 20));

        time.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        time.setForeground(new java.awt.Color(255, 255, 255));
        time.setText("jLabel2");
        jPanel1.add(time, new org.netbeans.lib.awtextra.AbsoluteConstraints(357, 40, 290, 20));

        configs2.setText("jLabel1");
        jPanel1.add(configs2, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 74, 230, 0));

        uname.setText("jLabel1");
        jPanel1.add(uname, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 54, -1, 0));

        upass.setText("jLabel1");
        jPanel1.add(upass, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 54, -1, 0));

        jMenuBar1.setBackground(new java.awt.Color(51, 102, 255));
        jMenuBar1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 102, 255)));
        jMenuBar1.setFont(new java.awt.Font("Square721 BT", 0, 14)); // NOI18N
        jMenuBar1.setPreferredSize(new java.awt.Dimension(56, 30));

        jMenu1.setBackground(new java.awt.Color(255, 255, 255));
        jMenu1.setText("File");
        jMenu1.setFont(new java.awt.Font("Square721 BT", 0, 12)); // NOI18N

        jMenuItem1.setBackground(new java.awt.Color(255, 255, 255));
        jMenuItem1.setFont(new java.awt.Font("Yu Gothic UI Semilight", 0, 14)); // NOI18N
        jMenuItem1.setText("About ExpoCurrebcy Enterprise");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);
        jMenu1.add(jSeparator1);

        jMenuItem2.setBackground(new java.awt.Color(255, 255, 255));
        jMenuItem2.setFont(new java.awt.Font("Yu Gothic UI Semilight", 0, 14)); // NOI18N
        jMenuItem2.setText("Change credentials");
        jMenuItem2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenuItem2MouseClicked(evt);
            }
        });
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);
        jMenu1.add(jSeparator2);

        jMenuItem3.setBackground(new java.awt.Color(255, 255, 255));
        jMenuItem3.setFont(new java.awt.Font("Yu Gothic UI Semilight", 0, 14)); // NOI18N
        jMenuItem3.setText("Sign Out");
        jMenuItem3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenuItem3MouseClicked(evt);
            }
        });
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);
        jMenu1.add(jSeparator3);

        jMenuItem4.setBackground(new java.awt.Color(255, 255, 255));
        jMenuItem4.setFont(new java.awt.Font("Yu Gothic UI Semilight", 0, 14)); // NOI18N
        jMenuItem4.setText("Exit");
        jMenuItem4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenuItem4MouseClicked(evt);
            }
        });
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1000, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 542, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void a2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_a2KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_a2KeyTyped

    private void a1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_a1ActionPerformed
        if (a1.getSelectedItem().equals("Local Server")) {

            a2.setText("localhost");
            a2.setEditable(false);
        }
        if (a1.getSelectedItem().equals("Remote Server")) {
            a2.setText("");
            a2.setEditable(true);
        }
    }//GEN-LAST:event_a1ActionPerformed

    private void jMenuItem3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem3MouseClicked

    }//GEN-LAST:event_jMenuItem3MouseClicked

    private void jMenuItem4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem4MouseClicked

    }//GEN-LAST:event_jMenuItem4MouseClicked

    private void jMenuItem2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem2MouseClicked

    }//GEN-LAST:event_jMenuItem2MouseClicked

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        int input = JOptionPane.showConfirmDialog(null, " Are you sure that you want to change your password", "Providence says....", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (input == JOptionPane.NO_OPTION) {

        }
        if (input == JOptionPane.YES_OPTION) {
            String a = JOptionPane.showInputDialog(null, "Enter your currenct username", "Username Authentication", JOptionPane.INFORMATION_MESSAGE);
            String b = JOptionPane.showInputDialog(null, "Enter your currenct password", "Passowrd Authentication", JOptionPane.INFORMATION_MESSAGE);

            String query = "SELECT username,password FROM users where username='" + a + "' and password='" + b + "'";
            try {
                DB_USER = uname.getText();
                DB_PAS = upass.getText();
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
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        int input = JOptionPane.showConfirmDialog(null, " Are you sure that you want to close this application", "Providence says....", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (input == JOptionPane.NO_OPTION) {

        }
        if (input == JOptionPane.YES_OPTION) {
            System.exit(4);

        }
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        int input = JOptionPane.showConfirmDialog(null, " Are you sure that you want to sign out ?", "Providence says....", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (input == JOptionPane.NO_OPTION) {

        }
        if (input == JOptionPane.YES_OPTION) {
            Login l = new Login();
            l.setVisible(true);
            this.dispose();

        }
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked
        counter = 0;
        ld.setVisible(false);
        ld1.setVisible(true);
        ld2.setVisible(false);
        ld3.setVisible(false);
        ld4.setVisible(false);
        ld6.setVisible(false);
        ld7.setVisible(false);
        ld8.setVisible(false);

        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        try {
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(DB_URL, DB_USER, DB_PAS);
            String sql = "CREATE DATABASE  bureaux";
            pst = con.prepareStatement(sql);

            pst.execute();
            JOptionPane.showMessageDialog(null, "You have successfully created your database ", "Notification From Wealth Excel System", JOptionPane.INFORMATION_MESSAGE);
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }


    }//GEN-LAST:event_jLabel8MouseClicked

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked
        ld.setVisible(false);
        ld1.setVisible(false);
        ld2.setVisible(false);
        ld3.setVisible(false);
        ld4.setVisible(false);
        ml.setVisible(true);
        ld6.setVisible(false);
        ld7.setVisible(false);
        ld8.setVisible(false);
        insert_admin();


    }//GEN-LAST:event_jLabel9MouseClicked

    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseClicked
        ld.setVisible(false);
        ld1.setVisible(false);
        ld2.setVisible(false);
        ld3.setVisible(false);
        ld4.setVisible(false);
        ld6.setVisible(true);
        ld7.setVisible(false);
        ld8.setVisible(false);
    }//GEN-LAST:event_jLabel10MouseClicked

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        ld.setVisible(false);
        ld1.setVisible(false);
        ld2.setVisible(false);
        ld3.setVisible(false);
        ld4.setVisible(true);
        ld6.setVisible(false);
        ld7.setVisible(false);
        ld8.setVisible(false);
    }//GEN-LAST:event_jLabel11MouseClicked

    private void jLabel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseClicked
        SQLRun objSQLRun = new SQLRun();
        String sql1 = "DELETE FROM branch_accounts where branch_account_id>'" + 0 + "'";
        int deleted1 = objSQLRun.sqlUpdate(sql1);
        if (deleted1 > 0) {
        }
        SQLRun objSQLRun1 = new SQLRun();
        String sql2 = "DELETE FROM branches where branch_id>'" + 0 + "'";
        int deleted2 = objSQLRun1.sqlUpdate(sql2);
        if (deleted2 > 0) {
        }
        SQLRun objSQLRun3 = new SQLRun();
        String sql3 = "DELETE FROM clerk_accounts where account_id>'" + 0 + "'";
        int deleted3 = objSQLRun3.sqlUpdate(sql3);
        if (deleted3 > 0) {
        }
        SQLRun objSQLRun4 = new SQLRun();
        String sql4 = "DELETE FROM clerks_operators where clerks_operators_id>'" + 0 + "'";
        int deleted4 = objSQLRun4.sqlUpdate(sql4);
        if (deleted4 > 0) {
        }
        SQLRun objSQLRun5 = new SQLRun();
        String sql5 = "DELETE FROM config_rates where exchange_rate_id>'" + 0 + "'";
        int deleted5 = objSQLRun5.sqlUpdate(sql5);
        if (deleted5 > 0) {
        }
        SQLRun objSQLRun6 = new SQLRun();
        String sql6 = "DELETE FROM currencies where currency_id>'" + 0 + "'";
        int deleted6 = objSQLRun6.sqlUpdate(sql6);
        if (deleted6 > 0) {
        }
        SQLRun objSQLRun7 = new SQLRun();
        String sql7 = "DELETE FROM denominations where denomination_id>'" + 0 + "'";
        int deleted7 = objSQLRun7.sqlUpdate(sql7);
        if (deleted7 > 0) {
        }
        SQLRun objSQLRun8 = new SQLRun();
        String sql8 = "DELETE FROM initial_balances where balance_id>'" + 0 + "'";
        int deleted8 = objSQLRun8.sqlUpdate(sql8);
        if (deleted8 > 0) {
        }
        SQLRun objSQLRun9 = new SQLRun();
        String sql9 = "DELETE FROM notifications where notification_id>'" + 0 + "'";
        int deleted9 = objSQLRun9.sqlUpdate(sql9);
        if (deleted9 > 0) {
        }
        SQLRun objSQLRun10 = new SQLRun();
        String sql10 = "DELETE FROM regions where region_id>'" + 0 + "'";
        int deleted10 = objSQLRun10.sqlUpdate(sql10);
        if (deleted10 > 0) {
        }
        SQLRun objSQLRun11 = new SQLRun();
        String sql11 = "DELETE FROM requests where request_id>'" + 0 + "'";
        int deleted11 = objSQLRun11.sqlUpdate(sql11);
        if (deleted11 > 0) {
        }
        SQLRun objSQLRun12 = new SQLRun();
        String sql12 = "DELETE FROM requests_history where request_id>'" + 0 + "'";
        int deleted12 = objSQLRun12.sqlUpdate(sql12);
        if (deleted12 > 0) {
        }
        String abc = "ABC";
        SQLRun objSQLRun13 = new SQLRun();
        String sql13 = "DELETE FROM transactions where trans_id!='" + abc + "'";
        int deleted13 = objSQLRun13.sqlUpdate(sql13);
        if (deleted13 > 0) {
        }
        SQLRun objSQLRun14 = new SQLRun();
        String sql14 = "DELETE FROM transactions_history where transactions_history_id>'" + 0 + "'";
        int deleted14 = objSQLRun14.sqlUpdate(sql14);
        if (deleted14 > 0) {
        }
        SQLRun objSQLRun15 = new SQLRun();
        String sql15 = "DELETE FROM transfers_log where transfers_log_id>'" + 0 + "'";
        int deleted15 = objSQLRun15.sqlUpdate(sql15);
        if (deleted15 > 0) {
        }
        SQLRun objSQLRun16 = new SQLRun();
        String sql16 = "DELETE FROM users where user_id>'" + 0 + "'";
        int deleted16 = objSQLRun16.sqlUpdate(sql16);
        if (deleted16 > 0) {
        }
        SQLRun objSQLRun17 = new SQLRun();

    }//GEN-LAST:event_jLabel12MouseClicked

    private void jLabel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseClicked
        DB_USER = uname.getText();
        DB_PAS = upass.getText();
        try {
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(DB_URL, DB_USER, DB_PAS);
            //JOptionPane.showMessageDialog(null,"Database connection was successfully");
            stm = con.createStatement();
            String sql = "drop TABLE transactions";
            stm.executeUpdate(sql);
            //JOptionPane.showMessageDialog(null,"EFFICIENCY SCORES TABLE CREATED SUCCESSFULLY");
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        try {
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
            //JOptionPane.showMessageDialog(null,"Database connection was successfully");
            stm = con.createStatement();
            String sql = "drop TABLE users";
            stm.executeUpdate(sql);
            //JOptionPane.showMessageDialog(null,"EFFICIENCY SCORES TABLE CREATED SUCCESSFULLY");
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        try {
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
            //JOptionPane.showMessageDialog(null,"Database connection was successfully");
            stm = con.createStatement();
            String sql = "drop TABLE requests";
            stm.executeUpdate(sql);
            //JOptionPane.showMessageDialog(null,"EFFICIENCY SCORES TABLE CREATED SUCCESSFULLY");
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        try {
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
            //JOptionPane.showMessageDialog(null,"Database connection was successfully");
            stm = con.createStatement();
            String sql = "drop TABLE notifications";
            stm.executeUpdate(sql);
            //JOptionPane.showMessageDialog(null,"EFFICIENCY SCORES TABLE CREATED SUCCESSFULLY");
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        try {
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
            //JOptionPane.showMessageDialog(null,"Database connection was successfully");
            stm = con.createStatement();
            String sql = "drop TABLE currencies";
            stm.executeUpdate(sql);
            //JOptionPane.showMessageDialog(null,"EFFICIENCY SCORES TABLE CREATED SUCCESSFULLY");
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        try {
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
            //JOptionPane.showMessageDialog(null,"Database connection was successfully");
            stm = con.createStatement();
            String sql = "drop TABLE initial_balances";
            stm.executeUpdate(sql);
            //JOptionPane.showMessageDialog(null,"EFFICIENCY SCORES TABLE CREATED SUCCESSFULLY");
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        try {
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
            //JOptionPane.showMessageDialog(null,"Database connection was successfully");
            stm = con.createStatement();
            String sql = "drop TABLE denominations";
            stm.executeUpdate(sql);
            //JOptionPane.showMessageDialog(null,"EFFICIENCY SCORES TABLE CREATED SUCCESSFULLY");
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        try {
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
            //JOptionPane.showMessageDialog(null,"Database connection was successfully");
            stm = con.createStatement();
            String sql = "drop TABLE requests_history";
            stm.executeUpdate(sql);
            //JOptionPane.showMessageDialog(null,"EFFICIENCY SCORES TABLE CREATED SUCCESSFULLY");
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        try {
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
            //JOptionPane.showMessageDialog(null,"Database connection was successfully");
            stm = con.createStatement();
            String sql = "drop TABLE regions";
            stm.executeUpdate(sql);
            //JOptionPane.showMessageDialog(null,"EFFICIENCY SCORES TABLE CREATED SUCCESSFULLY");
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        try {
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
            //JOptionPane.showMessageDialog(null,"Database connection was successfully");
            stm = con.createStatement();
            String sql = "drop TABLE config_rates";
            stm.executeUpdate(sql);
            //JOptionPane.showMessageDialog(null,"EFFICIENCY SCORES TABLE CREATED SUCCESSFULLY");
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        try {
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
            //JOptionPane.showMessageDialog(null,"Database connection was successfully");
            stm = con.createStatement();
            String sql = "drop TABLE branches";
            stm.executeUpdate(sql);
            //JOptionPane.showMessageDialog(null,"EFFICIENCY SCORES TABLE CREATED SUCCESSFULLY");
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        try {
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
            //JOptionPane.showMessageDialog(null,"Database connection was successfully");
            stm = con.createStatement();
            String sql = "drop TABLE branch_accounts";
            stm.executeUpdate(sql);
            //JOptionPane.showMessageDialog(null,"EFFICIENCY SCORES TABLE CREATED SUCCESSFULLY");
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        try {
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
            //JOptionPane.showMessageDialog(null,"Database connection was successfully");
            stm = con.createStatement();
            String sql = "drop TABLE clerk_accounts";
            stm.executeUpdate(sql);
            //JOptionPane.showMessageDialog(null,"EFFICIENCY SCORES TABLE CREATED SUCCESSFULLY");
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        try {
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
            //JOptionPane.showMessageDialog(null,"Database connection was successfully");
            stm = con.createStatement();
            String sql = "drop TABLE clerks_operators";
            stm.executeUpdate(sql);
            //JOptionPane.showMessageDialog(null,"EFFICIENCY SCORES TABLE CREATED SUCCESSFULLY");
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        try {
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
            //JOptionPane.showMessageDialog(null,"Database connection was successfully");
            stm = con.createStatement();
            String sql = "drop TABLE transactions_history";
            stm.executeUpdate(sql);
            //JOptionPane.showMessageDialog(null,"EFFICIENCY SCORES TABLE CREATED SUCCESSFULLY");
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        try {
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
            //JOptionPane.showMessageDialog(null,"Database connection was successfully");
            stm = con.createStatement();
            String sql = "drop TABLE transfers_log";
            stm.executeUpdate(sql);
            //JOptionPane.showMessageDialog(null,"EFFICIENCY SCORES TABLE CREATED SUCCESSFULLY");
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }//GEN-LAST:event_jLabel13MouseClicked

    private void jLabel14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseClicked
        int input = JOptionPane.showConfirmDialog(null, " Are you sure that you want to drop the database of this system ?", "Currency Dynamics Shell.", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (input == JOptionPane.NO_OPTION) {

        }
        if (input == JOptionPane.YES_OPTION) {
            DB_USER = uname.getText();
            DB_PAS = upass.getText();
            try {
                Class.forName(DB_DRIVER);
                con = DriverManager.getConnection(configs2.getText(), DB_USER, DB_PAS);
                String sql = "drop DATABASE bureaux";
                pst = con.prepareStatement(sql);

                pst.execute();
                JOptionPane.showMessageDialog(null, "You have successfully deleted your database ", "Notification From Wealth Excel System", JOptionPane.INFORMATION_MESSAGE);
            } catch (HeadlessException | ClassNotFoundException | SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }

        }

    }//GEN-LAST:event_jLabel14MouseClicked

    private void jLabel15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseClicked
        ld.setVisible(true);
        ld1.setVisible(false);
        ld2.setVisible(false);
        ld3.setVisible(false);
        ld4.setVisible(false);
        ld6.setVisible(false);
        ld7.setVisible(false);
        ld8.setVisible(false);
    }//GEN-LAST:event_jLabel15MouseClicked

    private void jLabel16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel16MouseClicked
        ld.setVisible(false);
        ld1.setVisible(false);
        ld2.setVisible(false);
        ld3.setVisible(false);
        ld4.setVisible(false);
        ld6.setVisible(false);
        ld7.setVisible(true);
        ld8.setVisible(false);
    }//GEN-LAST:event_jLabel16MouseClicked

    private void jLabel8MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel8MouseEntered

    private void a5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_a5KeyPressed
        int key = evt.getKeyCode();
        if (key == 10) {
            if (a2.getText().isEmpty()) {
                if (a1.getSelectedItem().equals("")) {
                    JOptionPane.showMessageDialog(rootPane, "Please select server type", "System configurations failed", JOptionPane.ERROR_MESSAGE);
                }
                JOptionPane.showMessageDialog(rootPane, "Please enter server ip address", "System configurations failed", JOptionPane.ERROR_MESSAGE);
            }
            if (a3.getText().isEmpty()) {
                JOptionPane.showMessageDialog(rootPane, "Please enter the database name", "System configurations failed", JOptionPane.ERROR_MESSAGE);
            }
            if (a4.getText().isEmpty()) {
                JOptionPane.showMessageDialog(rootPane, "Please enter database access name", "System configurations failed", JOptionPane.ERROR_MESSAGE);
            }
            if (a5.getText().isEmpty()) {
                JOptionPane.showMessageDialog(rootPane, "Please enter dataase access password", "System configurations failed", JOptionPane.ERROR_MESSAGE);
            }
            if (!a1.getSelectedItem().equals("") && !a2.getText().isEmpty() && !a3.getText().isEmpty() && !a4.getText().isEmpty() && !a5.getText().isEmpty()) {
                int input = JOptionPane.showConfirmDialog(null, "Are you sure that the infromations that you provided is correct for system configurations", "Providence says...", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (input == JOptionPane.NO_OPTION) {

                }
                if (input == JOptionPane.YES_OPTION) {
                    InetAddressValidator inetAddressValidator = InetAddressValidator.getInstance();
                    String address = a2.getText();
                    boolean bool = inetAddressValidator.isValidInet4Address(address);
                    if (!a1.getSelectedItem().equals("Local Server")) {
                        if (bool == false) {
                            JOptionPane.showMessageDialog(null, "The IP address that you provide is not correct", "Incorrenct IP Address Format", JOptionPane.ERROR_MESSAGE);
                        }
                        if (bool == true) {
                            String filepath = "src/configuration.txt";
                            File file = new File(filepath);
                            try {
                                file.createNewFile();
                                /* try {*/
                                FileWriter fw = new FileWriter(file);
                                BufferedWriter bw = new BufferedWriter(fw);
                                String conf = "//" + a2.getText() + ":5432/" + a3.getText();
                                bw.write(conf);
                                bw.newLine();
                                //}
                                bw.close();
                                fw.close();

                            } catch (IOException e) {
                                JOptionPane.showMessageDialog(null, e.getCause());
                            }

                            String filepathy = "src/configuration_1.txt";
                            File filey = new File(filepathy);
                            try {
                                filey.createNewFile();
                                /* try {*/
                                FileWriter fw = new FileWriter(filey);
                                BufferedWriter bw = new BufferedWriter(fw);
                                String conf = "//" + a2.getText() + ":5432/";
                                bw.write(conf);
                                bw.newLine();
                                //}
                                bw.close();
                                fw.close();

                            } catch (IOException e) {
                                JOptionPane.showMessageDialog(null, e.getCause());
                            }

                            String filepath1 = "src/db_user.txt";
                            File file1 = new File(filepath1);
                            try {
                                file1.createNewFile();
                                /* try {*/
                                FileWriter fw = new FileWriter(file1);
                                BufferedWriter bw = new BufferedWriter(fw);
                                String conf = a4.getText();
                                bw.write(conf);
                                bw.newLine();
                                //}
                                bw.close();
                                fw.close();

                            } catch (IOException e) {
                                JOptionPane.showMessageDialog(null, e.getCause());
                            }

                            String filepath2 = "src/db_pass.txt";
                            File file2 = new File(filepath2);
                            try {
                                file2.createNewFile();
                                /* try {*/
                                FileWriter fw = new FileWriter(file2);
                                BufferedWriter bw = new BufferedWriter(fw);
                                String conf = a5.getText();
                                bw.write(conf);
                                bw.newLine();
                                //}
                                bw.close();
                                fw.close();

                            } catch (IOException e) {
                                JOptionPane.showMessageDialog(null, e.getCause());
                            }
                            db_users_connect();
                            db_password_connect();

                        }

                        System.out.println("Is Valid IP address : ");

                        System.out.println("Is Valid IPV4 address : ");
                        //                    System.out.println("Is Valid IPV6 address : "
                        //                            + inetAddressValidator.isValidInet6Address(address));
                    }
                    if (a1.getSelectedItem().equals("Local Server")) {

                        String filepath = "src/configuration.txt";
                        File file = new File(filepath);
                        try {
                            file.createNewFile();
                            /* try {*/
                            FileWriter fw = new FileWriter(file);
                            BufferedWriter bw = new BufferedWriter(fw);
                            String conf = "//" + a2.getText() + ":5432/" + a3.getText();
                            bw.write(conf);
                            bw.newLine();
                            //}
                            bw.close();
                            fw.close();

                        } catch (IOException e) {
                            JOptionPane.showMessageDialog(null, e.getCause());
                        }
                        String filepathy = "src/configuration_1.txt";
                        File filey = new File(filepathy);
                        try {
                            filey.createNewFile();
                            /* try {*/
                            FileWriter fw = new FileWriter(filey);
                            BufferedWriter bw = new BufferedWriter(fw);
                            String conf = "//" + a2.getText() + ":5432/";
                            bw.write(conf);
                            bw.newLine();
                            //}
                            bw.close();
                            fw.close();

                        } catch (IOException e) {
                            JOptionPane.showMessageDialog(null, e.getCause());
                        }
                        String filepath1 = "src/db_user.txt";
                        File file1 = new File(filepath1);
                        try {
                            file1.createNewFile();
                            /* try {*/
                            FileWriter fw = new FileWriter(file1);
                            BufferedWriter bw = new BufferedWriter(fw);
                            String conf = a4.getText();
                            bw.write(conf);
                            bw.newLine();
                            //}
                            bw.close();
                            fw.close();

                        } catch (IOException e) {
                            JOptionPane.showMessageDialog(null, e.getCause());
                        }

                        String filepath2 = "src/db_pass.txt";
                        File file2 = new File(filepath2);
                        try {
                            file2.createNewFile();
                            /* try {*/
                            FileWriter fw = new FileWriter(file2);
                            BufferedWriter bw = new BufferedWriter(fw);
                            String conf = a5.getText();
                            bw.write(conf);
                            bw.newLine();
                            //}
                            bw.close();
                            fw.close();

                        } catch (IOException e) {
                            JOptionPane.showMessageDialog(null, e.getCause());
                        }
                        db_users_connect();
                        db_password_connect();

                        System.out.println("Is Valid IP address : ");

                        System.out.println("Is Valid IPV4 address : ");
                        //                    System.out.println("Is Valid IPV6 address : "
                        //                            + inetAddressValidator.isValidInet6Address(address));
                    }

                }
            }
        }
    }//GEN-LAST:event_a5KeyPressed

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
            java.util.logging.Logger.getLogger(Config_Control.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Config_Control.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Config_Control.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Config_Control.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Config_Control().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> a1;
    private javax.swing.JFormattedTextField a2;
    private javax.swing.JFormattedTextField a3;
    private javax.swing.JFormattedTextField a4;
    private javax.swing.JFormattedTextField a5;
    public static final javax.swing.JLabel configs = new javax.swing.JLabel();
    private javax.swing.JLabel configs2;
    private javax.swing.JLabel day;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel102;
    private javax.swing.JLabel jLabel103;
    private javax.swing.JLabel jLabel104;
    private javax.swing.JLabel jLabel105;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel117;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JTable jTable1;
    private com.xzq.osc.JocAction jocAction1;
    private com.xzq.osc.JocBusyIcon jocBusyIcon1;
    private com.xzq.osc.JocTabbedPane jocTabbedPane1;
    private com.xzq.osc.JocTrayIcon jocTrayIcon1;
    private javax.swing.JLabel ld;
    private javax.swing.JLabel ld1;
    private javax.swing.JLabel ld2;
    private javax.swing.JLabel ld3;
    private javax.swing.JLabel ld4;
    private javax.swing.JLabel ld6;
    private javax.swing.JLabel ld7;
    private javax.swing.JLabel ld8;
    private javax.swing.JLabel ml;
    private javax.swing.JLabel month;
    public static final javax.swing.JLabel my_id = new javax.swing.JLabel();
    public static final javax.swing.JLabel name = new javax.swing.JLabel();
    private javax.swing.JPanel server;
    private javax.swing.JPanel settings;
    public static final javax.swing.JLabel status = new javax.swing.JLabel();
    private javax.swing.JLabel time;
    public static final javax.swing.JLabel uname = new javax.swing.JLabel();
    public static final javax.swing.JLabel upass = new javax.swing.JLabel();
    private javax.swing.JLabel year;
    // End of variables declaration//GEN-END:variables
}
