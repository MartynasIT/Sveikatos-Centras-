/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SveikatosCentras;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import Naudotojai.Adminai;
import Naudotojai.Gydytojas;
import Naudotojai.Pacientas;
import Registravimas.Registracija;

/**
 *
 * @author Martin
 */

public class Login extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame
     */
    public Login() 
    {
        initComponents();
       // TikrintiGaliojima ();
    }
    
    private int AsmensKodas;
    private int DraudimoNr, SocApsaugaNR; private boolean arAdmin, arDaktaras;
    
    
     void SetAsmensKodas(int asmeskodas)
    {
         AsmensKodas = asmeskodas;
    }
     
     
     void TikrintiGaliojima ()
{
     new Timer(6000, new ActionListener() { // every minute we check if the room can be vacated 
            @Override
            public void actionPerformed(ActionEvent ae) {
              
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime now = LocalDateTime.now();
               
                
              try
    {
      // create our mysql database connection
      String myDriver = "org.apache.derby.jdbc.ClientDriver";
      String myUrl = "jdbc:derby://localhost:1527/Sveikatos Centras";
      Class.forName(myDriver);
      Connection conn = DriverManager.getConnection(myUrl, "sveikuoliai", "sveikuoliai");
      String query = "Select GALUTINISLAIKAS FROM APP.VIZITAI";
      
      Statement st = conn.createStatement();
      ResultSet rs = st.executeQuery(query);
      
       while (rs.next())
      {
           String galLaik =  rs.getString("GALUTINISLAIKAS");
            
            java.util.Date time1 = null;
            try {
                time1 = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(galLaik);
            } catch (ParseException ex) {
                Logger.getLogger(Registracija.class.getName()).log(Level.SEVERE, null, ex);
            }
            Calendar calendarGal = Calendar.getInstance();
            calendarGal.setTime(time1);

            java.util.Date time2 = null;
            try {
                time2 = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(dtf.format(now));
            } catch (ParseException ex) {
                Logger.getLogger(Registracija.class.getName()).log(Level.SEVERE, null, ex);
            }
            Calendar calendarDabar = Calendar.getInstance();
            calendarDabar.setTime(time2);
              
              
   
            if (calendarDabar.after(calendarGal))
            {
                
               try { 
            
            String query2 = "delete from APP.VIZITAI where GALUTINISLAIKAS = ?";
            PreparedStatement preparedStmt = conn.prepareStatement(query2);
            preparedStmt.setString(1, galLaik);
            preparedStmt.execute();
           
           

        } catch (Exception e)
        { 
            System.err.println("Got an exception! "); 
            System.err.println(e.getMessage()); 
        }
             
        }
         
        }
   
      
     
    }
    catch (Exception e)
    {
      System.err.println("Got an exception! ");
      System.err.println(e.getMessage());
    }  
            }
        }).start();
}

  void prisijungti ()
  {
       int count = 0;
        
        if ((asmenskodas.getText().isEmpty() == false) && (sdn.getText().isEmpty() == false)   && (SAP.getText().isEmpty() == false )  )
        {   
   try
    {
      String myDriver = "org.apache.derby.jdbc.ClientDriver";
      String myUrl = "jdbc:derby://localhost:1527/Sveikatos Centras";
      Class.forName(myDriver);
      Connection conn = DriverManager.getConnection(myUrl, "sveikuoliai", "sveikuoliai");
      String query = "SELECT * FROM APP.NAUDOTOJAI";
      Statement st = conn.createStatement();
      Statement st2 = conn.createStatement();
      
      
      // execute the query, and get a java resultset
      ResultSet rs = st.executeQuery(query);
      ResultSet rs2 = st2.executeQuery("SELECT COUNT(ASMENSKODAS) AS count_name from APP.NAUDOTOJAI");
      while (rs2.next())
      { count = rs2.getInt("count_name");}
      int ciklai = 0;
      
      
      while (rs.next())
      {
        
          
      SetAsmensKodas(rs.getInt("ASMENSKODAS"));
      DraudimoNr = rs.getInt("SVEIKATOSDRAUDIMAS");
      SocApsaugaNR = rs.getInt("SOCAPSAUGA");
      arAdmin = rs.getBoolean("ARADMINAS");
      arDaktaras = rs.getBoolean("ARDAKTARAS");
       
       if (arDaktaras == false && arAdmin == false && AsmensKodas == Integer.parseInt(asmenskodas.getText()) 
               && DraudimoNr == Integer.parseInt(sdn.getText()) && SocApsaugaNR == Integer.parseInt(SAP.getText()))
       {
           Pacientas men1 = new Pacientas ();
            men1.show();
            men1.SetCurrentUserAsmensKodas(AsmensKodas);
            super.dispose();
            break;
           
       }
       
       
        else if (arDaktaras == true && AsmensKodas  == Integer.parseInt(asmenskodas.getText()) && 
                DraudimoNr == Integer.parseInt(sdn.getText()) && SocApsaugaNR == Integer.parseInt(SAP.getText()))
       { 
       
       Gydytojas dak = new Gydytojas();
       dak.show();
       dak.setCurrentUserAsmensKodas(AsmensKodas);
       super.dispose();
       break;
       
       }
       
        else if (arAdmin == true  && AsmensKodas == Integer.parseInt(asmenskodas.getText()) 
                && DraudimoNr == Integer.parseInt(sdn.getText()) && SocApsaugaNR == Integer.parseInt(SAP.getText()))
       {
        Adminai adm = new Adminai();
        adm.show();
        super.dispose();
        break;
       }
       
      ciklai++;
     
        if (ciklai == count) 
        {
            
       JOptionPane.showMessageDialog(null, "Blogai suvesti duomenys", "Error", // if user does not enter name error windows pops up
                JOptionPane.ERROR_MESSAGE);
           asmenskodas.setText(null); sdn.setText(null); SAP.setText(null);
       }
   
      }
      st.close();
    }
    catch (Exception e)
    {
      System.err.println("Got an exception! ");
      System.err.println(e.getMessage());
    }
   
     }
        else  JOptionPane.showMessageDialog(null, "Neįvesti duomenys", "Error", // if user does not enter name error windows pops up
                JOptionPane.ERROR_MESSAGE);
  }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        asmenskodas = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        sdn = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        SAP = new javax.swing.JTextField();
        showPacientasLangas = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        jLabel1.setText("Sveikatos sistema");

        jLabel2.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jLabel2.setText("Prisijungimas");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setText("Asmens Kodas:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setText("Sveikatos Draudimo Numeris:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setText("Socialinė Apsaugos Numeris:");

        showPacientasLangas.setText("Prisijungti");
        showPacientasLangas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showPacientasLangasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(SAP))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(68, 68, 68)
                                        .addComponent(jLabel3)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(asmenskodas, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                                    .addComponent(sdn)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 316, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(287, 287, 287))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(showPacientasLangas, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(307, 307, 307))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(sdn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(64, 64, 64)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(SAP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(asmenskodas, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                .addComponent(showPacientasLangas, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void showPacientasLangasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showPacientasLangasActionPerformed

     prisijungti();

    }//GEN-LAST:event_showPacientasLangasActionPerformed

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
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
        
       
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField SAP;
    private javax.swing.JTextField asmenskodas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JTextField sdn;
    private javax.swing.JButton showPacientasLangas;
    // End of variables declaration//GEN-END:variables
}
