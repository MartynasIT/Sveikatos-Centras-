/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Naudotojai;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import Registravimas.Registracija;
import SveikatosCentras.Login;
import java.util.Arrays;
/**
 *
 * @author Martin
 */
public class Gydytojas extends javax.swing.JFrame {

    /**
     * Creates new form PrisijunDak
     */
    public Gydytojas() {
        initComponents();
        ParodykVizitus ();
        ParuostiUzsetintimui();
        day();
        time();
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
         vardas.setText(rs.getString("VARDAS"));
         pavarde.setText(rs.getString("PAVARDE"));
       }
       
      ps.close();
    }
    catch (Exception e)
    {
      System.err.println("Got an exception! ");
      System.err.println(e.getMessage());
    }
        
    }
    

    private int AsmensKodas;
   
   public void setCurrentUserAsmensKodas (int asm)
   {
       AsmensKodas = asm;
       knygele.setEditable(false);
         
   }
   
   
   
    void ParodykVizitus ()
    {
        new Timer(6000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
              
        int asm = 0;
        String laikas;
        String komentaras;
       
        
       try
    {
      // create our mysql database connection
      String myDriver = "org.apache.derby.jdbc.ClientDriver";
      String myUrl = "jdbc:derby://localhost:1527/Sveikatos Centras";
      Class.forName(myDriver);
      Connection conn = DriverManager.getConnection(myUrl, "sveikuoliai", "sveikuoliai");
      PreparedStatement ps = conn.prepareStatement ("SELECT PACIENTOASMKODAS, KOMENTARAS, PRADINISLAIKAS FROM APP.VIZITAI WHERE GYDYTOJOASMKODAS = ?");
      PreparedStatement ps2 = conn.prepareStatement ("SELECT * FROM APP.NAUDOTOJAI WHERE ASMENSKODAS = ?");
      ps.setString(1,Integer.toString(AsmensKodas));
      ResultSet rs = ps.executeQuery();
    
      DefaultListModel listas = new DefaultListModel();
      vizitai.setModel(new DefaultListModel());
      ((DefaultListModel)vizitai.getModel()).clear();
       listas.clear();
      while (rs.next())
      {    asm = rs.getInt("PACIENTOASMKODAS");
           laikas = rs.getString("PRADINISLAIKAS");
           komentaras = rs.getString("KOMENTARAS");
         
           
           
           ps2.setString(1,Integer.toString(asm));
           ResultSet rs2 = ps2.executeQuery();
      
          
      while (rs2.next())
              
         { 
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime now = LocalDateTime.now();
             java.util.Date time2 = null;
            try {
                time2 = new SimpleDateFormat("yyyy-MM-dd").parse(dtf.format(now));
            } catch (ParseException ex) {
                Logger.getLogger(Registracija.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            java.util.Date time1 = null;
            try {
                time1 = new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString("PRADINISLAIKAS"));
            } catch (ParseException ex) {
                Logger.getLogger(Registracija.class.getName()).log(Level.SEVERE, null, ex);
            }
             if (time1.equals(time2))
                 
             {    String[] laikasP = rs.getString("PRADINISLAIKAS").split("-");
                  String valandaP = laikasP [2];
                  String[] laikasP2 = valandaP.split(" ");
                  valandaP = laikasP2[1];
                  
                  String text = rs2.getString("VARDAS") + "  " + rs2.getString("PAVARDE")  + "  " + valandaP + "  " + komentaras;
             
                  listas.addElement(text);
             }
          }
          rs2.close();
      }
      
    Object [] dlma = listas.toArray();    
    Arrays.sort(dlma);  
    listas.clear();     
    for (Object x : dlma)
        listas.addElement(x); 
      
         vizitai.setModel(listas);
      
      rs.close();
      
    
 
      
    }
    catch (Exception e)
    {
      System.err.println("Got an exception! ");
      System.err.println(e.getMessage());
    }
        
    }
        }) .start();
                }
    
    
    void RemoveVizitas ()
    {
        
       String tekstas = vizitai.getSelectedValue();
       String[] itemai = tekstas.split("  ");
        String vardas = itemai[0].replaceAll("\\s","");
        String pavarde = itemai[1].replaceAll("\\s","");
        
        int kodas = 0;
         
         try { 
            String url = "jdbc:derby://localhost:1527/Sveikatos Centras"; 
            Connection conn = DriverManager.getConnection(url,"sveikuoliai","sveikuoliai"); 
            String query2 = "Select ASMENSKODAS from APP.NAUDOTOJAI where VARDAS = ? AND PAVARDE = ? ";
            String query = "delete from APP.VIZITAI where PACIENTOASMKODAS = ? AND PRADINISLAIKAS = ? ";
            
            PreparedStatement preparedStmt2 = conn.prepareStatement(query2);
            preparedStmt2.setString(1, vardas);
            preparedStmt2.setString(2, pavarde);
            ResultSet rs2 = preparedStmt2.executeQuery();
             while (rs2.next()) kodas = rs2.getInt("ASMENSKODAS");
            
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, kodas );
            
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime now = LocalDateTime.now();
            String laikas = dtf.format(now); 
            laikas = laikas + " " +itemai[2];
            
            preparedStmt.setString(2, laikas );
            preparedStmt.execute();
            JOptionPane.showMessageDialog(null,"Pavyko");
            ((DefaultListModel)vizitai.getModel()).clear();
            ParodykVizitus ();
            knygele.setText(null);
            
            
           
            conn.close();
           
        } catch (Exception e) { 
            JOptionPane.showMessageDialog(null, "Nepavyko", "Error", // if user does not enter name error windows pops up
                JOptionPane.ERROR_MESSAGE);
            System.err.println("Got an exception! "); 
            System.err.println(e.getMessage()); 
        } 
    }   
    
    int GetLigonisASM()
    {
        
        int kodas = 0;
        try { 
             
            String url = "jdbc:derby://localhost:1527/Sveikatos Centras"; 
            Connection conn = DriverManager.getConnection(url,"sveikuoliai","sveikuoliai");
            String query2 = "Select ASMENSKODAS from APP.NAUDOTOJAI where VARDAS = ? AND PAVARDE = ? ";
            PreparedStatement preparedStmt = conn.prepareStatement(query2);
            preparedStmt.setString(1,name.getText() );
            preparedStmt.setString(2, lastname.getText());
            ResultSet rs = preparedStmt.executeQuery();
            while (rs.next()) kodas = rs.getInt("ASMENSKODAS");
            conn.close(); 
            
        } catch (Exception e) { 
            JOptionPane.showMessageDialog(null, "Nepavyko", "Error", // if user does not enter name error windows pops up
                JOptionPane.ERROR_MESSAGE);
            System.err.println("Got an exception! "); 
            System.err.println(e.getMessage()); 
        }
        
        return kodas;
        
    }

    void addIrasas ()
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        
         try { 
             
            String url = "jdbc:derby://localhost:1527/Sveikatos Centras"; 
            Connection conn = DriverManager.getConnection(url,"sveikuoliai","sveikuoliai"); 
            PreparedStatement ps = conn.prepareStatement("INSERT INTO APP.SVEIKATOSKNYGELE VALUES (?, ?,?)");
          
            
            ps.setString(1, dtf.format(now));
            ps.setString(2, irasas.getText());
            ps.setInt(3, GetLigonisASM());
           
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null,"Pavyko");
            conn.close(); 
            
        } catch (Exception e) { 
            JOptionPane.showMessageDialog(null, "Nepavyko", "Error", // if user does not enter name error windows pops up
                JOptionPane.ERROR_MESSAGE);
            System.err.println("Got an exception! "); 
            System.err.println(e.getMessage()); 
        }
    }



    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        vardas = new javax.swing.JLabel();
        pavarde = new javax.swing.JLabel();
        Registracija = new javax.swing.JButton();
        lastname = new javax.swing.JTextField();
        name = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        irasas = new javax.swing.JTextField();
        Knygute = new javax.swing.JButton();
        SalintiVizita = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        vizitai = new javax.swing.JList<>();
        Data = new javax.swing.JLabel();
        Time = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        knygele = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        exit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        vardas.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        vardas.setText("Vardas");

        pavarde.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        pavarde.setText("pavarde");

        Registracija.setText("Registracija");
        Registracija.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RegistracijaActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Vardas:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Pavardė:");

        Knygute.setText("Užrašyti į knygutę");
        Knygute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KnyguteActionPerformed(evt);
            }
        });

        SalintiVizita.setText("Pašalinti vizita");
        SalintiVizita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalintiVizitaActionPerformed(evt);
            }
        });

        vizitai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                vizitaiMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(vizitai);

        Data.setText("Data");

        Time.setText("Time");

        knygele.setColumns(20);
        knygele.setRows(5);
        jScrollPane1.setViewportView(knygele);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Įrašas");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Vizitai");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Paciento knygelė");

        exit.setText("Atsijungti");
        exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lastname, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 558, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(198, 198, 198)
                                .addComponent(SalintiVizita, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(234, 234, 234)
                                .addComponent(jLabel2)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 629, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(jLabel3)
                                        .addGap(281, 281, 281)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(vardas, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(10, 10, 10)
                                                .addComponent(Time, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(pavarde)
                                            .addComponent(Data, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(exit, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(Registracija, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(153, 153, 153)
                                .addComponent(irasas, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(Knygute, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(397, 397, 397))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
                            .addComponent(jScrollPane2)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(pavarde)
                            .addComponent(vardas, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Data)
                            .addComponent(Time))
                        .addGap(33, 33, 33)
                        .addComponent(exit, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 238, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lastname, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(12, 12, 12)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Knygute, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(irasas, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Registracija, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SalintiVizita, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(57, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void RegistracijaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RegistracijaActionPerformed
        
        if (GetLigonisASM() != 0)
        {
        Registracija reg = new Registracija ();
        reg.setCurrentUserAsmensKodas(AsmensKodas);
        reg.setLigonisASM(GetLigonisASM());
        reg.show();
        this.setVisible(false);
        }
        else JOptionPane.showMessageDialog(null, "Blogai įvesti duomenys", "Error", JOptionPane.ERROR_MESSAGE);
    }//GEN-LAST:event_RegistracijaActionPerformed

    private void SalintiVizitaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalintiVizitaActionPerformed
       RemoveVizitas ();
    }//GEN-LAST:event_SalintiVizitaActionPerformed

    private void KnyguteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KnyguteActionPerformed
        if (GetLigonisASM() != 0)
        addIrasas ();
        else JOptionPane.showMessageDialog(null, "Blogai įvesti duomenys", "Error", JOptionPane.ERROR_MESSAGE);
    }//GEN-LAST:event_KnyguteActionPerformed

    private void vizitaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_vizitaiMouseClicked
        
        String textas = vizitai.getSelectedValue();
        String[] itemai = textas.split("  ");
        int kodas = 0;
        String vardas = itemai[0].replaceAll("\\s","");
        String pavarde = itemai[1].replaceAll("\\s","");
        
        
         try { 
             
            String url = "jdbc:derby://localhost:1527/Sveikatos Centras"; 
            Connection conn = DriverManager.getConnection(url,"sveikuoliai","sveikuoliai"); 
            
            String query = "Select ASMENSKODAS from APP.NAUDOTOJAI where VARDAS = ? AND PAVARDE = ? ";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1,vardas);
            preparedStmt.setString(2,pavarde);
            ResultSet rs = preparedStmt.executeQuery();
            while (rs.next()) kodas = rs.getInt("ASMENSKODAS");
            
            String query2 = "Select IRASAS, DATA from APP.SVEIKATOSKNYGELE where PACIENTOASM = ?";
            PreparedStatement preparedStmt2 = conn.prepareStatement(query2);
            preparedStmt2.setInt(1,kodas);
            ResultSet rs2 = preparedStmt2.executeQuery();
            knygele.setText(null);
            while (rs2.next())
            {
                
                knygele.append(rs2.getString("DATA") +"  " + rs2.getString("IRASAS") + "\n");
                
            }
            
           
            conn.close(); 
            
        } catch (Exception e) { 
           
            System.err.println("Got an exception! "); 
            System.err.println(e.getMessage()); 
        }
        
        
        
    }//GEN-LAST:event_vizitaiMouseClicked

    private void exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitActionPerformed
      
      Login log = new Login();
      log.show();
      super.dispose();
    }//GEN-LAST:event_exitActionPerformed

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
            java.util.logging.Logger.getLogger(Gydytojas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Gydytojas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Gydytojas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Gydytojas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
    
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Gydytojas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Data;
    private javax.swing.JButton Knygute;
    private javax.swing.JButton Registracija;
    private javax.swing.JButton SalintiVizita;
    private javax.swing.JLabel Time;
    private javax.swing.JButton exit;
    private javax.swing.JTextField irasas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea knygele;
    private javax.swing.JTextField lastname;
    private javax.swing.JTextField name;
    private javax.swing.JLabel pavarde;
    private javax.swing.JLabel vardas;
    private javax.swing.JList<String> vizitai;
    // End of variables declaration//GEN-END:variables
}
