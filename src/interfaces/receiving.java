/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import db_com.SQLRun;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Sir Chikukwa
 */
public class receiving extends javax.swing.JFrame {

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
    
    public void printv(){
        getSum();
          JOptionPane.showMessageDialog(null, "Thank you " + " " + name.getText() + "\nTransactions performed at " + Timestamp, "Transaction was successful", JOptionPane.INFORMATION_MESSAGE);

        String filepath = "src/rcy.txt";
        File file = new File(filepath);
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            red.read(br, null);
            rup.setText(" " + " " + " " + " " + " " + "DANIELOH BUREAU DE CHANGE" + " " + " " + " " + " \n"
                    + " " + " " + " " + " " + " " + "Delivering Posibilities" + " " + " " + " " + " " + " \n"
                    + "---------------------------------------------------------------\n"
                    + " Danieloh Branch :" + Clerk.branch.getText() + " " + "\n " + "Counter Clerk :" + Clerk.name.getText() + "\n"
                    + "-----------------------------------------------------------------\n"
                    + " Date :" + Clerk.day.getText() + " " + " " + " " + Clerk.month.getText() + " " + " " + Clerk.year.getText() + "\n"
                    + " Time :" + " " + " " + Clerk.time.getText() + "\n\n"
                    + "------------------------------------------------------------------\n"
                    + " " + " " + "Denominations Received By The Counter Clerk\n"
                    + "-------------------------------------------------------------------\n"
                    + " Denominations " + " " + " " + "|" + " " + " " + "No. Denominations" + "|" + " " + " " + "Total\n"
                    + red.getText() + "\n\tTotal Money Received :$" + acc_total.getText() + "\n\n"
                    + " " + " " + "Denominations Received By The Customer\n"
                    + "-------------------------------------------------------------------\n"
                    + " Denominations " + " " + " " + "|" + " " + " " + "No. Denominations" + "|" + " " + " " + "Total\n"
                    + red.getText() + "\n\tTotal Money Received :$" + acc_total.getText() + "\n\n"
                    + "-------------------------------------------------------------------\n"
                    + "Customer Name : " + rc1.getText() + " " + " " + " " + rc2.getText() + "\n"
                    + "National ID Number :" + rc4.getText() + "\n"
                    + "Phone number       :" + rc3.getText());
            DB_USER = uname.getText();
                DB_PAS = upass.getText();
            try {
                    con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                pst = con.prepareStatement("insert into transactions_history values(?,?,?,?,?,?,?,?,?,?,?,?)");

                pst.setString(1, r1.getText());
                //pst.setDouble(2, a);
                //pst.setInt(3, b1);
                //pst.setDouble(4, c);
                pst.setString(5, Clerk.name.getText());

                int p = pst.executeUpdate();
                if (p > 0) {
//r5.setText(xy + "");
                    DefaultTableModel dm = (DefaultTableModel) cd_table.getModel();
                    //dm.addRow(new Object[]{a,b,c});
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Something went wrong", JOptionPane.ERROR_MESSAGE);
            }
            MessageFormat header = new MessageFormat("Bureay");
            MessageFormat footer = new MessageFormat("Thank you");
            Printable printable = rup.getPrintable(header, footer);

            PrinterJob job = PrinterJob.getPrinterJob();
            job.setPrintable(printable);

            boolean printAccepted = job.printDialog();
            if (printAccepted) {
                try {
                    job.print();

                } catch (PrinterException e) {
                    throw new RuntimeException(e);
                }
            }

        } catch (Exception e) {
        }
    }

    public receiving() {
        initComponents();
        this.setLocationRelativeTo(null);
        customer.setVisible(false);
        setIconImage(Toolkit.getDefaultToolkit().getImage("src/img/shop.png"));

        r4.setEditable(true);
    }

    public void getSum() {
        float sum = 0;
        for (int i = 0; i < cd_table.getRowCount(); i++) {
            sum = sum + Float.parseFloat(cd_table.getValueAt(i, 2).toString());
        }
        acc_total.setText(Float.toString(sum));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        simult = new javax.swing.JPanel();
        jLabel71 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel72 = new javax.swing.JLabel();
        jLabel82 = new javax.swing.JLabel();
        jLabel83 = new javax.swing.JLabel();
        r5 = new javax.swing.JFormattedTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        cd_table = new javax.swing.JTable();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        na_list = new javax.swing.JLabel();
        configs = new javax.swing.JLabel();
        customer = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        rc1 = new javax.swing.JFormattedTextField();
        rc2 = new javax.swing.JFormattedTextField();
        jLabel2 = new javax.swing.JLabel();
        rc3 = new javax.swing.JFormattedTextField();
        jLabel3 = new javax.swing.JLabel();
        rc4 = new javax.swing.JFormattedTextField();
        jLabel4 = new javax.swing.JLabel();
        ret = new javax.swing.JLabel();
        reading = new javax.swing.JScrollPane();
        red = new javax.swing.JEditorPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        rup = new javax.swing.JEditorPane();
        acc_total = new javax.swing.JLabel();
        my_pic = new javax.swing.JLabel();
        configs2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setBackground(new java.awt.Color(204, 255, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        setUndecorated(true);
        setResizable(false);
        setState(1);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        simult.setBackground(new java.awt.Color(255, 255, 255));
        simult.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 102, 255)));
        simult.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        r1.setEditable(false);
        r1.setBackground(new java.awt.Color(255, 255, 255));
        simult.add(r1, new org.netbeans.lib.awtextra.AbsoluteConstraints(157, 17, 166, 29));

        jLabel71.setText("Currency to be received");
        simult.add(jLabel71, new org.netbeans.lib.awtextra.AbsoluteConstraints(13, 24, -1, -1));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel72.setText("Amount to be received");
        jPanel1.add(jLabel72, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 20, -1, -1));

        r3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
        r3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                r3ActionPerformed(evt);
            }
        });
        jPanel1.add(r3, new org.netbeans.lib.awtextra.AbsoluteConstraints(144, 48, 164, 31));

        jLabel82.setText("Number of denominations");
        jPanel1.add(jLabel82, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 92, -1, -1));

        r2.setEditable(false);
        r2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.add(r2, new org.netbeans.lib.awtextra.AbsoluteConstraints(144, 13, 164, 29));

        jLabel83.setText("Select denominations");
        jPanel1.add(jLabel83, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 56, -1, -1));

        r4.setToolTipText("Enter the number of denominations you want to receive");
        r4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                r4KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                r4KeyTyped(evt);
            }
        });
        jPanel1.add(r4, new org.netbeans.lib.awtextra.AbsoluteConstraints(144, 85, 164, 29));

        r5.setEditable(false);
        r5.setBackground(new java.awt.Color(255, 255, 255));
        r5.setText("00000");
        r5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jPanel1.add(r5, new org.netbeans.lib.awtextra.AbsoluteConstraints(144, 120, 164, 29));

        cd_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Denominations", "No. denominations", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(cd_table);
        if (cd_table.getColumnModel().getColumnCount() > 0) {
            cd_table.getColumnModel().getColumn(0).setResizable(false);
            cd_table.getColumnModel().getColumn(1).setResizable(false);
            cd_table.getColumnModel().getColumn(2).setResizable(false);
        }

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 0, 60));

        jFormattedTextField1.setText("jFormattedTextField1");
        jPanel1.add(jFormattedTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, -1, 0));

        name.setText("jLabel6");
        jPanel1.add(name, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 44, -1, 0));

        branch.setText("jLabel6");
        jPanel1.add(branch, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 84, -1, 0));

        reg.setText("jLabel6");
        jPanel1.add(reg, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 134, -1, 0));
        jPanel1.add(na_list, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 10, 10, 10));

        configs.setText("jLabel50");
        jPanel1.add(configs, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 0));

        simult.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 376, -1));

        customer.setBackground(new java.awt.Color(255, 255, 255));
        customer.setBorder(javax.swing.BorderFactory.createTitledBorder("Customer Details"));
        customer.setOpaque(false);
        customer.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("Customer name");
        customer.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 34, -1, -1));

        rc1.setEditable(false);
        rc1.setBackground(new java.awt.Color(255, 255, 255));
        rc1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                rc1KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                rc1KeyTyped(evt);
            }
        });
        customer.add(rc1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 27, 164, 29));

        rc2.setEditable(false);
        rc2.setBackground(new java.awt.Color(255, 255, 255));
        rc2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                rc2KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                rc2KeyTyped(evt);
            }
        });
        customer.add(rc2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 67, 164, 29));

        jLabel2.setText("Surname");
        customer.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 74, -1, -1));

        rc3.setEditable(false);
        rc3.setBackground(new java.awt.Color(255, 255, 255));
        rc3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                rc3KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                rc3KeyTyped(evt);
            }
        });
        customer.add(rc3, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 107, 164, 29));

        jLabel3.setText("Phone number");
        customer.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 114, -1, -1));

        rc4.setEditable(false);
        rc4.setBackground(new java.awt.Color(255, 255, 255));
        rc4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                rc4KeyPressed(evt);
            }
        });
        customer.add(rc4, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 147, 164, 29));

        jLabel4.setText("National ID");
        customer.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 154, -1, -1));

        ret.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-return-32.png"))); // NOI18N
        ret.setText("RETURN");
        ret.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                retMouseClicked(evt);
            }
        });
        customer.add(ret, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 187, 80, 29));

        simult.add(customer, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 376, 230));

        red.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        reading.setViewportView(red);

        simult.add(reading, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 20, 10, 440));

        rup.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        jScrollPane2.setViewportView(rup);

        simult.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 20, 10, 440));

        acc_total.setText("jLabel7");
        simult.add(acc_total, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 30, -1, 0));

        my_pic.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        my_pic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/images (4)_1.png"))); // NOI18N
        simult.add(my_pic, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 230, 380, 240));

        bru.setText("jLabel6");
        simult.add(bru, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 54, -1, 0));

        rcc1.setText("jLabel6");
        simult.add(rcc1, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 24, -1, 0));

        rcout.setText("jLabel6");
        simult.add(rcout, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 44, -1, 0));

        rcrate.setText("jLabel6");
        simult.add(rcrate, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 54, -1, 0));

        getContentPane().add(simult, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 390, 471));

        uname.setText("jLabel1");
        getContentPane().add(uname, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 54, -1, 0));

        upass.setText("jLabel1");
        getContentPane().add(upass, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 54, -1, 0));

        configs2.setText("jLabel1");
        getContentPane().add(configs2, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 74, 230, 0));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
int y = 0;
    private void r4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_r4KeyPressed
       

        int key = evt.getKeyCode();
        if (key == 10) {
            if (!r4.getText().isEmpty()) {
                double d = Double.parseDouble(r2.getText());
                double a = Double.parseDouble(r3.getSelectedItem().toString());
                double b = Double.parseDouble(r4.getText());
                int b1 = Integer.parseInt(r4.getText());
                double c = a * b;
                double xa = Double.parseDouble(r5.getText());
                double xy = xa + c;
                if (na_list.getText().isEmpty()) {
                    if (d >= xy) {
DB_USER = uname.getText();
                DB_PAS = upass.getText();
                        String querie = "SELECT currency,denominations,no_denominations,total FROM clerk_accounts where account_holder='" + Clerk.name.getText() + "' and denominations='" + a + "' and currency='" + r1.getText() + "' ";
                        try {
                            Class.forName(DB_DRIVER);
                    con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                            stm = con.createStatement();
                            rs = stm.executeQuery(querie);
                            boolean bool = rs.isBeforeFirst();
                            while (rs.next()) {
                                if (bool == true) {
                                    JOptionPane.showMessageDialog(null, "Good one");
                                    double bx = rs.getDouble("total");
                                    int vx = rs.getInt("no_denominations");
                                    double dw1 = (bx + c);
                                    int dw2 = (vx + b1);

                                    SQLRun run = new SQLRun();
                                    String sqlq = "Update clerk_accounts set no_denominations='" + dw2 + "' , total='" + dw1 + "' where account_holder='" + Clerk.name.getText() + "' and denominations='" + a + "' and currency='" + r1.getText() + "'";
                                    int updated = run.sqlUpdate(sqlq);
                                    if (updated > 0) {
                                        r5.setText(xy + "");
                                        DefaultTableModel dm = (DefaultTableModel) cd_table.getModel();
                                        dm.addRow(new Object[]{a, b, c});
                                    }
                                }
                            }
                            if (bool == false) {
                                try {
                    con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                    pst = con.prepareStatement("insert into clerk_accounts values(?,?,?,?,?)");

                                    pst.setString(1, r1.getText());
                                    pst.setDouble(2, a);
                                    pst.setInt(3, b1);
                                    pst.setDouble(4, c);
                                    pst.setString(5, Clerk.name.getText());

                                    int p = pst.executeUpdate();
                                    if (p > 0) {
                                        r5.setText(xy + "");
                                        DefaultTableModel dm = (DefaultTableModel) cd_table.getModel();
                                        dm.addRow(new Object[]{a, b, c});
                                    }
                                } catch (HeadlessException | SQLException ev) {
                                    JOptionPane.showMessageDialog(null, ev.getMessage(), "An errorr occured", JOptionPane.ERROR_MESSAGE);

                                }
                            }
                        } catch (SQLException e) {
                            JOptionPane.showMessageDialog(null, e.getMessage(), "Something went wrong", JOptionPane.ERROR_MESSAGE);
                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(receiving.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    if (d == xy) {
                        String filepath = "src/rcy.txt";
                        File file = new File(filepath);
                        try {
                            file.createNewFile();
                            try {
                                FileWriter fw = new FileWriter(file);
                                BufferedWriter bw = new BufferedWriter(fw);
                                for (int i = 0; i < cd_table.getRowCount(); i++) {
                                    for (int j = 0; j < cd_table.getColumnCount(); j++) {
                                        bw.write(cd_table.getValueAt(i, j) + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " ");
                                    }
                                    bw.newLine();
                                }
                                bw.close();
                                fw.close();

                            } catch (IOException e) {
                                JOptionPane.showMessageDialog(null, e.getCause());
                            }
                        } catch (IOException ex) {
                            Logger.getLogger(receiving.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        if (y == 230) {
                            customer.setSize(376, 230);
                            Thread th1 = new Thread() {
                                public void run() {
                                    try {
                                        for (int i = 0; i >= y; i--) {
                                            Thread.sleep(7);
                                            //customer.show();
                                            customer.setSize(376, i);

                                        }
                                    } catch (Exception e) {
                                    }
                                }
                            };
                            th1.start();
                            y = 0;
                        }

                        if (y == 0) {
                            customer.setSize(376, y);
                            Thread th1 = new Thread() {
                                public void run() {
                                    try {
                                        for (int i = 0; i <= y; i++) {
                                            Thread.sleep(7);
                                            customer.show();
                                            customer.setSize(376, i);
                                            r1.setEditable(false);
                                            r2.setEditable(false);
                                            r3.setEditable(false);
                                            r4.setEditable(false);
                                            r5.setEditable(false);

                                            rc1.setEditable(true);
                                            rc2.setEditable(false);
                                            rc3.setEditable(false);
                                            rc4.setEditable(false);
                                            rc1.requestFocus();
                                            ret.setEnabled(false);

                                        }
                                    } catch (Exception e) {
                                    }
                                }
                            };
                            th1.start();
                            y = 230;
                            my_pic.setVisible(false);
                        }
                    }
                }
                if (!na_list.getText().isEmpty()) {
                    String abx = na_list.getText();
                    if (abx.contains(r3.getSelectedItem().toString())) {

                    }
                    if (!abx.contains(r3.getSelectedItem().toString())) {
                        if (d < xy) {

                        }
                        if (d >= xy) {

                            String querie = "SELECT currency,denominations,no_denominations,total FROM clerk_accounts where account_holder='" + Clerk.name.getText() + "' and denominations='" + a + "' and currency='" + r1.getText() + "' ";
                            try {
                                Class.forName(DB_DRIVER);
                    con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                stm = con.createStatement();
                                rs = stm.executeQuery(querie);
                                boolean bool = rs.isBeforeFirst();
                                while (rs.next()) {
                                    if (bool == true) {
                                        JOptionPane.showMessageDialog(null, "Good one");
                                        double bx = rs.getDouble("total");
                                        int vx = rs.getInt("no_denominations");
                                        double dw1 = (bx + c);
                                        int dw2 = (vx + b1);

                                        SQLRun run = new SQLRun();
                                        String sqlq = "Update clerk_accounts set no_denominations='" + dw2 + "' , total='" + dw1 + "' where account_holder='" + Clerk.name.getText() + "' and denominations='" + a + "' and currency='" + r1.getText() + "'";
                                        int updated = run.sqlUpdate(sqlq);
                                        if (updated > 0) {
                                            r5.setText(xy + "");
                                            DefaultTableModel dm = (DefaultTableModel) cd_table.getModel();
                                            dm.addRow(new Object[]{a, b, c});
                                        }
                                    }
                                }
                                if (bool == false) {
                                    try {
                    con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                                        pst = con.prepareStatement("insert into clerk_accounts values(?,?,?,?,?)");

                                        pst.setString(1, r1.getText());
                                        pst.setDouble(2, a);
                                        pst.setInt(3, b1);
                                        pst.setDouble(4, c);
                                        pst.setString(5, Clerk.name.getText());

                                        int p = pst.executeUpdate();
                                        if (p > 0) {
                                            r5.setText(xy + "");
                                            DefaultTableModel dm = (DefaultTableModel) cd_table.getModel();
                                            dm.addRow(new Object[]{a, b, c});
                                        }
                                    } catch (HeadlessException | SQLException ev) {
                                        JOptionPane.showMessageDialog(null, ev.getMessage(), "An errorr occured", JOptionPane.ERROR_MESSAGE);

                                    }
                                }
                            } catch (SQLException e) {
                                JOptionPane.showMessageDialog(null, e.getMessage(), "Something went wrong", JOptionPane.ERROR_MESSAGE);
                            } catch (ClassNotFoundException ex) {
                                Logger.getLogger(receiving.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        if (d == xy) {
                            String filepath = "src/rcy.txt";
                            File file = new File(filepath);
                            try {
                                file.createNewFile();
                                try {
                                    FileWriter fw = new FileWriter(file);
                                    BufferedWriter bw = new BufferedWriter(fw);
                                    for (int i = 0; i < cd_table.getRowCount(); i++) {
                                        for (int j = 0; j < cd_table.getColumnCount(); j++) {
                                            bw.write(cd_table.getValueAt(i, j) + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " ");
                                        }
                                        bw.newLine();
                                    }
                                    bw.close();
                                    fw.close();

                                } catch (IOException e) {
                                    JOptionPane.showMessageDialog(null, e.getCause());
                                }
                            } catch (IOException ex) {
                                Logger.getLogger(receiving.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            if (y == 230) {
                                customer.setSize(376, 230);
                                Thread th1 = new Thread() {
                                    public void run() {
                                        try {
                                            for (int i = 0; i >= y; i--) {
                                                Thread.sleep(7);
                                                //customer.show();
                                                customer.setSize(376, i);

                                            }
                                        } catch (Exception e) {
                                        }
                                    }
                                };
                                th1.start();
                                y = 0;
                            }

                            if (y == 0) {
                                customer.setSize(376, y);
                                Thread th1 = new Thread() {
                                    public void run() {
                                        try {
                                            for (int i = 0; i <= y; i++) {
                                                Thread.sleep(7);
                                                customer.show();
                                                customer.setSize(376, i);
                                                r1.setEditable(false);
                                                r2.setEditable(false);
                                                r3.setEditable(false);
                                                r4.setEditable(false);
                                                r5.setEditable(false);

                                                rc1.setEditable(true);
                                                rc2.setEditable(false);
                                                rc3.setEditable(false);
                                                rc4.setEditable(false);
                                                rc1.requestFocus();
                                                ret.setEnabled(false);

                                            }
                                        } catch (Exception e) {
                                        }
                                    }
                                };
                                th1.start();
                                y = 230;
                                my_pic.setVisible(false);
                            }
                        }
                    }
                }

            }
        }
    }//GEN-LAST:event_r4KeyPressed

    private void r4KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_r4KeyTyped
        char c = evt.getKeyChar();
        if (!Character.isDigit(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_r4KeyTyped

    private void retMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_retMouseClicked
        //      receiving r = new receiving();
        //     r.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_retMouseClicked

    private void rc4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rc4KeyPressed
        java.util.Date date = new java.util.Date();
        long t = date.getTime();
        java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(t);
        java.sql.Date sqlDate = new java.sql.Date(t);

        double a = Double.parseDouble(rcrate.getText());
        double b = Double.parseDouble(rcout.getText());
        double c = Double.parseDouble(r2.getText());
        int d = Integer.parseInt(rc3.getText(), 10);
        //double e1 = Double.parseDouble()
        int key = evt.getKeyCode();
        if (key == 10) {
            if (rc4.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter customer national ID number", "Some information missing", JOptionPane.WARNING_MESSAGE);
            }
            if (!rc4.getText().isEmpty()) {
                if (rc4.getText().length() < 10) {
                    JOptionPane.showMessageDialog(null, "Please, be advised that the national ID number consitsts of at least 10 characters", "Invalid national ID (length)", JOptionPane.WARNING_MESSAGE);
                }
                if (rc4.getText().length() >= 10) {
                   DB_USER = uname.getText();
                DB_PAS = upass.getText();
                    try {
                    con = DriverManager.getConnection(configs.getText(), DB_USER, DB_PAS);
                        pst = con.prepareStatement("insert into transactions_history values(?,?,?,?,?,?,?,?,?,?,?,?,?)");

                        pst.setString(1, rcc1.getText());
                        pst.setString(2, r1.getText());
                        pst.setString(3, reg.getText());
                        pst.setString(4, branch.getText());
                        pst.setString(5, name.getText());
                        pst.setDouble(6, c);
                        pst.setDouble(7, b);
                        pst.setDouble(8, a);
                        pst.setDate(9, sqlDate);
                        pst.setString(10, rc1.getText().concat(" ").concat(rc2.getText()));
                        pst.setString(11, rc4.getText());
                        pst.setInt(12, d);
                        pst.setString(13, "selling");

                        int p = pst.executeUpdate();
                        if (p > 0) {
                            printv();
                            this.dispose();
                        }
                    } catch (HeadlessException | SQLException e) {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "An Error Occured whilst trying to save data", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }//GEN-LAST:event_rc4KeyPressed

    private void rc3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rc3KeyTyped
        char c = evt.getKeyChar();
        if (!Character.isDigit(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_rc3KeyTyped

    private void rc3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rc3KeyPressed
        int key = evt.getKeyCode();
        if (key == 10) {
            if (rc3.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter customer phone number", "Some information missing", JOptionPane.WARNING_MESSAGE);
            }
            if (!rc3.getText().isEmpty()) {
                if (rc3.getText().length() < 10) {
                    JOptionPane.showMessageDialog(null, "Phone number consists of at least 10 digits", "Invalid phone number (length)", JOptionPane.WARNING_MESSAGE);
                }
                if (rc3.getText().length() >= 10) {
                    rc4.setEditable(true);
                    rc4.requestFocus();
                }
            }
        }
    }//GEN-LAST:event_rc3KeyPressed

    private void rc2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rc2KeyTyped
        int c = evt.getKeyChar();
        if (!Character.isAlphabetic(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_rc2KeyTyped

    private void rc2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rc2KeyPressed
        int key = evt.getKeyCode();
        if (key == 10) {
            if (rc2.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter customer surname", "Some information missing", JOptionPane.WARNING_MESSAGE);
            }
            if (!rc2.getText().isEmpty()) {
                if (rc2.getText().length() < 3) {
                    JOptionPane.showMessageDialog(null, "The surname of the customer must have at leat 3 characters", "Invalid name (length)", JOptionPane.WARNING_MESSAGE);
                }
                if (rc2.getText().length() >= 3) {
                    rc3.setEditable(true);
                    rc3.requestFocus();
                }
            }
        }
    }//GEN-LAST:event_rc2KeyPressed

    private void rc1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rc1KeyTyped
        int c = evt.getKeyChar();
        if (!Character.isAlphabetic(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_rc1KeyTyped

    private void rc1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rc1KeyPressed

        int key = evt.getKeyCode();
        if (key == 10) {
            if (rc1.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter the name of the customer", "Some information missing", JOptionPane.WARNING_MESSAGE);
            }
            if (!rc1.getText().isEmpty()) {
                if (rc1.getText().length() < 3) {
                    JOptionPane.showMessageDialog(null, "The name of the customer must have at leat 3 characters", "Invalid name (length)", JOptionPane.WARNING_MESSAGE);
                }
                if (rc1.getText().length() >= 3) {
                    rc2.setEditable(true);
                    rc2.requestFocus();
                }
            }
        }

    }//GEN-LAST:event_rc1KeyPressed

    private void r3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_r3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_r3ActionPerformed

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
            java.util.logging.Logger.getLogger(receiving.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(receiving.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(receiving.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(receiving.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new receiving().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel acc_total;
    public static final javax.swing.JLabel branch = new javax.swing.JLabel();
    public static final javax.swing.JLabel bru = new javax.swing.JLabel();
    private javax.swing.JTable cd_table;
    public static javax.swing.JLabel configs;
    private javax.swing.JLabel configs2;
    private javax.swing.JPanel customer;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel my_pic;
    private javax.swing.JLabel na_list;
    public static final javax.swing.JLabel name = new javax.swing.JLabel();
    public static final javax.swing.JFormattedTextField r1 = new javax.swing.JFormattedTextField();
    public static final javax.swing.JFormattedTextField r2 = new javax.swing.JFormattedTextField();
    public static final javax.swing.JComboBox<String> r3 = new javax.swing.JComboBox<>();
    public static final javax.swing.JFormattedTextField r4 = new javax.swing.JFormattedTextField();
    private javax.swing.JFormattedTextField r5;
    private javax.swing.JFormattedTextField rc1;
    private javax.swing.JFormattedTextField rc2;
    private javax.swing.JFormattedTextField rc3;
    private javax.swing.JFormattedTextField rc4;
    public static final javax.swing.JLabel rcc1 = new javax.swing.JLabel();
    public static final javax.swing.JLabel rcout = new javax.swing.JLabel();
    public static final javax.swing.JLabel rcrate = new javax.swing.JLabel();
    private javax.swing.JScrollPane reading;
    private javax.swing.JEditorPane red;
    public static final javax.swing.JLabel reg = new javax.swing.JLabel();
    private javax.swing.JLabel ret;
    private javax.swing.JEditorPane rup;
    private javax.swing.JPanel simult;
    public static final javax.swing.JLabel uname = new javax.swing.JLabel();
    public static final javax.swing.JLabel upass = new javax.swing.JLabel();
    // End of variables declaration//GEN-END:variables

}
