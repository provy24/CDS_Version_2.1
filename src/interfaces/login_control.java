/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.Timer;
//import org.apache.commons.validator.routines.InetAddressValidator;
/*
 *
 * @author Sir Chikukwa
 */
public class login_control extends javax.swing.JFrame {
    int lblTextLength = 0;
    Timer tm;
    int counter = 0;                                  
    private static final long serialVersionUID = 1L;
    String DB_DRIVER = "org.postgresql.Driver";
    String DB_URL = "jdbc:postgresql:";
    String DB_USER = "postgres";
    String DB_PAS = "danieloh24";
    java.sql.Connection con = null;
    java.sql.Statement stm = null;
    java.sql.ResultSet rs = null;
    java.sql.PreparedStatement pst = null;
    String url = "jdbc:postgres://localhost:5432/";
    public void connect() {
        String filepath = "src/configuration_1.txt";
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
    
    String username = "root";
    String password = "";

   
    public login_control() {
        initComponents();
        this.setLocationRelativeTo(null);

    }

   
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jocBusyIcon1 = new com.xzq.osc.JocBusyIcon();
        jocAction1 = new com.xzq.osc.JocAction();
        jocTrayIcon1 = new com.xzq.osc.JocTrayIcon();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        user = new rojeru_san.RSMTextFull();
        pass = new rojeru_san.RSMPassView();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        configs1 = new javax.swing.JLabel();
        se = new javax.swing.JLabel();
        sa = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 207, 52), 2), "", javax.swing.border.TitledBorder.LEADING, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Yu Gothic UI Semilight", 0, 14))); // NOI18N
        jPanel1.setToolTipText("ExpoCurrency Enterprise");
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        configs.setText("jLabel7");
        jPanel1.add(configs, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 34, -1, 0));

        my_id.setText("jLabel7");
        jPanel1.add(my_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, 0));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N
        jPanel2.setOpaque(false);
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        user.setForeground(new java.awt.Color(0, 0, 0));
        user.setBordeColorFocus(new java.awt.Color(255, 204, 0));
        user.setBotonColor(new java.awt.Color(255, 204, 0));
        user.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        user.setOpaque(false);
        user.setPlaceholder("enter username");
        jPanel2.add(user, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 28, -1, -1));

        pass.setForeground(new java.awt.Color(0, 0, 0));
        pass.setBordeColorFocus(new java.awt.Color(255, 204, 0));
        pass.setBordeColorNoFocus(new java.awt.Color(255, 255, 255));
        pass.setBotonColor(new java.awt.Color(255, 204, 0));
        pass.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        pass.setOpaque(false);
        pass.setPlaceholder("enter your password");
        pass.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                passKeyPressed(evt);
            }
        });
        jPanel2.add(pass, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 88, -1, -1));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/clo.png"))); // NOI18N
        jLabel1.setText("close");
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 155, -1, -1));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/setupimg/unlock_32px.png"))); // NOI18N
        jLabel3.setText("login");
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel2.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 155, -1, -1));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/images (4).png"))); // NOI18N
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 210, 200));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 120, 530, 270));

        configs1.setText("jLabel3");
        jPanel1.add(configs1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 234, -1, 0));

        se.setText("jLabel3");
        jPanel1.add(se, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 74, -1, 0));

        sa.setText("jLabel3");
        jPanel1.add(sa, new org.netbeans.lib.awtextra.AbsoluteConstraints(234, 114, 80, 0));

        jLabel4.setText("Currency Dynamics Shell 2020");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 678, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 498, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void passKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_passKeyPressed
        int key = evt.getKeyCode();
        if(key==10){
            if(user.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Please enter username","Some information missing",JOptionPane.ERROR_MESSAGE);
            }
            if(pass.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Please enter password","Some information missing",JOptionPane.ERROR_MESSAGE);
            }
            if(!user.getText().isEmpty() && !pass.getText().isEmpty()){
                if(user.getText().matches("dev@bureaux") && pass.getText().matches("devpassword")){
                    Config_Control cc = new Config_Control();
                    cc.setVisible(true);
                    Config_Control.status.setText("You are working offline");
                    this.dispose();
                }
                else{
                    JOptionPane.showMessageDialog(null, "Incorrenct credentails","Access Denied",JOptionPane.ERROR_MESSAGE);
                }
            }
        }

    }//GEN-LAST:event_passKeyPressed

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        int input = JOptionPane.showConfirmDialog(null," Are you sure that you want to close this application","Providence says....", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (input == JOptionPane.NO_OPTION) {

        }
        if (input == JOptionPane.YES_OPTION) {
            System.exit(2);

        }
    }//GEN-LAST:event_jLabel1MouseClicked

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel3MouseClicked

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
            java.util.logging.Logger.getLogger(login_control.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(login_control.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(login_control.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(login_control.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new login_control().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static final javax.swing.JLabel configs = new javax.swing.JLabel();
    private javax.swing.JLabel configs1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private com.xzq.osc.JocAction jocAction1;
    private com.xzq.osc.JocBusyIcon jocBusyIcon1;
    private com.xzq.osc.JocTrayIcon jocTrayIcon1;
    public static final javax.swing.JLabel my_id = new javax.swing.JLabel();
    private rojeru_san.RSMPassView pass;
    private javax.swing.JLabel sa;
    private javax.swing.JLabel se;
    private rojeru_san.RSMTextFull user;
    // End of variables declaration//GEN-END:variables
}
