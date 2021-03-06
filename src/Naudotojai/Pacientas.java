/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Naudotojai;

import SveikatosCentras.Login;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.Timer;
import Registravimas.Informacija;
import Registravimas.Registracija;
import Registravimas.Vizitai;

/**
 *
 * @author Martin
 */
public class Pacientas extends javax.swing.JFrame {

    /**
     * Creates new form Menu
     */
   
    public Pacientas() {
        initComponents();
        day();
        time();
        ParuostiUzsetintimui();
    }
    
    private void day() {
        Date d = new Date();
        SimpleDateFormat s = new SimpleDateFormat("yyyy/MM/dd");
        Data.setText(s.format(d));
    }
    private void time() {
        new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Date d = new Date();
                SimpleDateFormat s = new SimpleDateFormat("HH:mm:ss");
                Time.setText(s.format(d));
            }
        }).start();
    }
    
     
    
    private void ParuostiUzsetintimui() { //kadangi pirma yra paleidziamas visas kodas ir tik poto gaunamas asmens kodas reikia asmens koda gauti truputelis veliau
        
        new Timer(50, new ActionListener() {
            
             int i = 0;
            public void actionPerformed(ActionEvent ae) {
                i++;
                if (i == 1)
                { 
                    SetintiInfo ();
                  
                
                }
                
            }
        }).start();
    }
    
    
     private int AsmensKodas;
     
    public void SetCurrentUserAsmensKodas(int asm)
    { 
      
       AsmensKodas = asm; 
        
    }
    
    
    void SetintiInfo ()
    {
        
         try
    {
      // create our mysql database connection
      String myDriver = "org.apache.derby.jdbc.ClientDriver";
      String myUrl = "jdbc:derby://localhost:1527/Sveikatos Centras";
      Class.forName(myDriver);
      Connection conn = DriverManager.getConnection(myUrl, "sveikuoliai", "sveikuoliai");
       PreparedStatement ps = conn.prepareStatement ("SELECT * FROM APP.NAUDOTOJAI WHERE ASMENSKODAS = ?");
       ps.setInt(1,AsmensKodas);
       ResultSet rs = ps.executeQuery();
       
       while (rs.next())
       {
         Vard.setText(rs.getString("VARDAS"));
         pavard.setText(rs.getString("PAVARDE"));
       }
       
      ps.close();
    }
    catch (Exception e)
    {
      System.err.println("Got an exception! ");
      System.err.println(e.getMessage());
    }
        
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Vard = new javax.swing.JLabel();
        pavard = new javax.swing.JLabel();
        ShowRegistracijosLangas = new javax.swing.JButton();
        showVizitaiLangas = new javax.swing.JButton();
        ShowInformacijaLangas = new javax.swing.JButton();
        Atsijungti = new javax.swing.JButton();
        Data = new javax.swing.JLabel();
        Time = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Vard.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Vard.setText("Vardas");

        pavard.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        pavard.setText("Pavarde");

        ShowRegistracijosLangas.setText("Registracija");
        ShowRegistracijosLangas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ShowRegistracijosLangasActionPerformed(evt);
            }
        });

        showVizitaiLangas.setText("Vizitai");
        showVizitaiLangas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showVizitaiLangasActionPerformed(evt);
            }
        });

        ShowInformacijaLangas.setText("Informacija");
        ShowInformacijaLangas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ShowInformacijaLangasActionPerformed(evt);
            }
        });

        Atsijungti.setText("Atsijungti");
        Atsijungti.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AtsijungtiActionPerformed(evt);
            }
        });

        Data.setText("Data");

        Time.setText("Time");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(230, 230, 230)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(showVizitaiLangas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ShowRegistracijosLangas, javax.swing.GroupLayout.DEFAULT_SIZE, 303, Short.MAX_VALUE)
                    .addComponent(ShowInformacijaLangas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Atsijungti, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(79, 278, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Vard, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Time, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Data, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pavard, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pavard)
                    .addComponent(Vard))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Data)
                    .addComponent(Time))
                .addGap(23, 23, 23)
                .addComponent(ShowRegistracijosLangas, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54)
                .addComponent(showVizitaiLangas, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(ShowInformacijaLangas, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addComponent(Atsijungti, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void showVizitaiLangasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showVizitaiLangasActionPerformed
        this.setVisible(false);
        Vizitai viz = new Vizitai ();
        viz.setCurrentUserAsmensKodas(AsmensKodas);
        viz.show();  
       
    }//GEN-LAST:event_showVizitaiLangasActionPerformed

    private void ShowInformacijaLangasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ShowInformacijaLangasActionPerformed
        this.setVisible(false);
        Informacija info = new Informacija ();
        info.SetCurrentUserAsmensKodas(AsmensKodas);
        info.show();
        
    }//GEN-LAST:event_ShowInformacijaLangasActionPerformed

    private void AtsijungtiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AtsijungtiActionPerformed
        Login log = new Login ();
        log.show();
        super.dispose();
    }//GEN-LAST:event_AtsijungtiActionPerformed

    private void ShowRegistracijosLangasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ShowRegistracijosLangasActionPerformed
       
        this.setVisible(false);
        Registracija reg = new Registracija ();
       reg.setCurrentUserAsmensKodas(AsmensKodas);
        reg.show();
       
        
    }//GEN-LAST:event_ShowRegistracijosLangasActionPerformed

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
            java.util.logging.Logger.getLogger(Pacientas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Pacientas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Pacientas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Pacientas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Pacientas().setVisible(true);
            }
        });
       
       
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Atsijungti;
    private javax.swing.JLabel Data;
    private javax.swing.JButton ShowInformacijaLangas;
    private javax.swing.JButton ShowRegistracijosLangas;
    private javax.swing.JLabel Time;
    private javax.swing.JLabel Vard;
    private javax.swing.JLabel pavard;
    private javax.swing.JButton showVizitaiLangas;
    // End of variables declaration//GEN-END:variables
}
