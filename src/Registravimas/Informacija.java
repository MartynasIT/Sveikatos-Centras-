/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Registravimas;

import Info.Pagalba;
import Info.Imokos;
import Naudotojai.Pacientas;
import Info.SveikatosKnygute;

/**
 *
 * @author Martin
 */
public class Informacija extends javax.swing.JFrame {

    /**
     * Creates new form Informacija
     */
    public Informacija() {
        initComponents();
    }

   private int AsmensKodas;
     
    public void SetCurrentUserAsmensKodas(int asm)
    { 
      
       AsmensKodas = asm; 
       
        
    }
    
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ShowSveikatosKnygute = new javax.swing.JButton();
        showImokosLangas = new javax.swing.JButton();
        showPagalbosLangas = new javax.swing.JButton();
        Atgal = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        ShowSveikatosKnygute.setText("Sveikatos Knygutė");
        ShowSveikatosKnygute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ShowSveikatosKnyguteActionPerformed(evt);
            }
        });

        showImokosLangas.setText("Įmokos");
        showImokosLangas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showImokosLangasActionPerformed(evt);
            }
        });

        showPagalbosLangas.setText("Pagalba");
        showPagalbosLangas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showPagalbosLangasActionPerformed(evt);
            }
        });

        Atgal.setText("Atgal");
        Atgal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AtgalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Atgal, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(318, 318, 318)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(showPagalbosLangas, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(showImokosLangas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(ShowSveikatosKnygute, javax.swing.GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE)))
                        .addGap(0, 330, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Atgal, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(ShowSveikatosKnygute, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57)
                .addComponent(showImokosLangas, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(78, 78, 78)
                .addComponent(showPagalbosLangas, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(108, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void AtgalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AtgalActionPerformed
       Pacientas men = new Pacientas ();
       men.setVisible(true);
       men.SetCurrentUserAsmensKodas(AsmensKodas);
       super.dispose();
    }//GEN-LAST:event_AtgalActionPerformed

    private void ShowSveikatosKnyguteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ShowSveikatosKnyguteActionPerformed
       SveikatosKnygute knyg =  new  SveikatosKnygute();
       knyg.SetCurrentUserAsmensKodas(AsmensKodas);
       knyg.show();
       this.setVisible(false);
    }//GEN-LAST:event_ShowSveikatosKnyguteActionPerformed

    private void showImokosLangasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showImokosLangasActionPerformed
        Imokos im = new Imokos ();
        im.SetCurrentUserAsmensKodas(AsmensKodas);
        im.show();
        this.setVisible(false);
        
        
    }//GEN-LAST:event_showImokosLangasActionPerformed

    private void showPagalbosLangasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showPagalbosLangasActionPerformed
        Pagalba pag = new Pagalba ();
        pag.SetCurrentUserAsmensKodas(AsmensKodas);
        pag.show();
        this.setVisible(false);
    }//GEN-LAST:event_showPagalbosLangasActionPerformed

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
            java.util.logging.Logger.getLogger(Informacija.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Informacija.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Informacija.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Informacija.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Informacija().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Atgal;
    private javax.swing.JButton ShowSveikatosKnygute;
    private javax.swing.JButton showImokosLangas;
    private javax.swing.JButton showPagalbosLangas;
    // End of variables declaration//GEN-END:variables
}
