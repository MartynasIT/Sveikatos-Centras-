/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Registravimas;

import Naudotojai.Pacientas;
import Naudotojai.Gydytojas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 *
 * @author Martin
 */
public class Registracija extends javax.swing.JFrame {

    /**
     * Creates new form Registracija
     */
    public Registracija() {
        initComponents();
         day();
        time();
        uzsetintiGytytojus();
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
    
    private int intervalas = 15;
     private int gydASM;
    
    void uzsetintiGytytojus()
    {
        try
    {
      // create our mysql database connection
      String myDriver = "org.apache.derby.jdbc.ClientDriver";
      String myUrl = "jdbc:derby://localhost:1527/Sveikatos Centras";
      Class.forName(myDriver);
      Connection conn = DriverManager.getConnection(myUrl, "sveikuoliai", "sveikuoliai");
      String query = "SELECT DISTINCT  SPECIALYBE  FROM APP.NAUDOTOJAI WHERE ARDAKTARAS = TRUE";
      Statement st = conn.createStatement();
     
      ResultSet rs = st.executeQuery(query);
      String text = "Pasirinkite gydytoją";
      Vector comboBoxItems = new Vector();
      comboBoxItems.add(text);
      while (rs.next())
      {
          text = rs.getString("SPECIALYBE");
           
           comboBoxItems.add(text);
           
      }
      DefaultComboBoxModel model = new DefaultComboBoxModel( comboBoxItems );
      gydytojasCombo.setModel( model );
      st.close();
    }
    catch (Exception e)
    {
      System.err.println("Got an exception! ");
      System.err.println(e.getMessage());
    }
    }
    
    void uzsetintiGydVardus()
    {
        String specialybe = gydytojasCombo.getSelectedItem().toString();
        String text;
        
           try
    {
      // create our mysql database connection
      String myDriver = "org.apache.derby.jdbc.ClientDriver";
      String myUrl = "jdbc:derby://localhost:1527/Sveikatos Centras";
      Class.forName(myDriver);
      Connection conn = DriverManager.getConnection(myUrl, "sveikuoliai", "sveikuoliai");
       PreparedStatement ps = conn.prepareStatement ("SELECT VARDAS, PAVARDE, KABINETONR  FROM APP.NAUDOTOJAI WHERE SPECIALYBE = ?");
       ps.setString(1,specialybe);
       ResultSet rs = ps.executeQuery();
       Vector comboBoxItems= new Vector();
        
       while (rs.next())
       {
         text = rs.getString("VARDAS") + " " + rs.getString("PAVARDE") +  " " + "Kabinetas: " + rs.getString("KABINETONR");
         comboBoxItems.add(text);
       }
       DefaultComboBoxModel model = new DefaultComboBoxModel( comboBoxItems );
      gydvard.setModel( model );
       
      ps.close();
    }
    catch (Exception e)
    {
      System.err.println("Got an exception! ");
      System.err.println(e.getMessage());
    }
        
    }
    
    
    int GetGydytojasAsmKodas(String vardas, String pavarde)
    {
        int kod = 0;
         try
    {
      // create our mysql database connection
      String myDriver = "org.apache.derby.jdbc.ClientDriver";
      String myUrl = "jdbc:derby://localhost:1527/Sveikatos Centras";
      Class.forName(myDriver);
      Connection conn = DriverManager.getConnection(myUrl, "sveikuoliai", "sveikuoliai");
       PreparedStatement ps = conn.prepareStatement ("SELECT ASMENSKODAS FROM APP.NAUDOTOJAI WHERE VARDAS = ? AND PAVARDE = ?");
       ps.setString(1,vardas);
       ps.setString(2,pavarde);
       
       ResultSet rs = ps.executeQuery();
      while (rs.next())
      {
           kod =  rs.getInt("ASMENSKODAS");
      }
      rs.close();
      return kod; 
 
      
    }
    catch (Exception e)
    {
      System.err.println("Got an exception! ");
      System.err.println(e.getMessage());
    }
        return kod;
        
       
    }
    
    
    int tikrinti (String laikPrad, String laikGal, Integer asm, Integer k)
    {
        int stat = 0;
        
     try
    {
      // create our mysql database connection
      String myDriver = "org.apache.derby.jdbc.ClientDriver";
      String myUrl = "jdbc:derby://localhost:1527/Sveikatos Centras";
      Class.forName(myDriver);
      Connection conn = DriverManager.getConnection(myUrl, "sveikuoliai", "sveikuoliai");
      String query = "Select PRADINISLAIKAS, GALUTINISLAIKAS, GYDYTOJOASMKODAS, PACIENTOASMKODAS FROM APP.VIZITAI";
      
      Statement st = conn.createStatement();
      ResultSet rs = st.executeQuery(query);
      
      
      while (rs.next())
      {
            Date time1 = null;
            try {
                time1 = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(rs.getString("PRADINISLAIKAS"));              
            } catch (ParseException ex) {
                Logger.getLogger(Registracija.class.getName()).log(Level.SEVERE, null, ex);
            }
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(time1);
            
            Date time2 = null;
            try {
                time2 = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(rs.getString("GALUTINISLAIKAS"));
            } catch (ParseException ex) {
                Logger.getLogger(Registracija.class.getName()).log(Level.SEVERE, null, ex);
            }
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(time2);

            Date time3 = null;
            try {
                time3 = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(laikPrad);
            } catch (ParseException ex) {
                Logger.getLogger(Registracija.class.getName()).log(Level.SEVERE, null, ex);
            }
            Calendar calendar3 = Calendar.getInstance();
            calendar3.setTime(time3);

            Date time4 = null;
            try {
                time4 = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(laikGal);
            } catch (ParseException ex) {
                Logger.getLogger(Registracija.class.getName()).log(Level.SEVERE, null, ex);
            }
            Calendar calendar4 = Calendar.getInstance();
            calendar4.setTime(time4);
            
            //------------------------------------------------------------------------------------------
            if (LigonioASM == 0)
            { 
            String [] laikasPradX = laikPrad.split("-");
            String LaikasPrad = laikasPradX [0] +"-"+laikasPradX[1]+"-"+laikasPradX[2];
            String [] Finallaik = LaikasPrad.split(" ");
            LaikasPrad = Finallaik [0];
            Date timeX = null;
            
            try {
                timeX = new SimpleDateFormat("yyyy-MM-dd").parse(LaikasPrad);               // Tikrinama ar nera jau ta pacia diena uzsiregistraves pas ta pati gydytoja
            } catch (ParseException ex) {
                Logger.getLogger(Registracija.class.getName()).log(Level.SEVERE, null, ex);
            }
            
             String laikasX = rs.getString("PRADINISLAIKAS");
             
            String [] laikasPradX2 = laikasX.split("-");
            String LaikasPrad2 = laikasPradX2 [0] +"-"+laikasPradX2[1]+"-"+laikasPradX2[2];
            String [] Finallaik2 = LaikasPrad2.split(" ");
            LaikasPrad2 = Finallaik2 [0];
            Date timeY = null;
            
            try {
                timeY = new SimpleDateFormat("yyyy-MM-dd").parse( LaikasPrad2);              
            } catch (ParseException ex) {
                Logger.getLogger(Registracija.class.getName()).log(Level.SEVERE, null, ex);
            }
           
                
             
                if (AsmensKodas == rs.getInt("PACIENTOASMKODAS") && asm == rs.getInt("GYDYTOJOASMKODAS") && timeX.equals(timeY) )
            {
                stat = 1;
                 JOptionPane.showMessageDialog(null, "Du kartus pas tą pati gytytoją registruotis negalima", "Error", 
                JOptionPane.ERROR_MESSAGE);
                 break;
               
            }
            }
            
            
            //-----------------------------------------------------------------------------------------------------
            
            if (k == 0)
            { 
                if (asm == rs.getInt("GYDYTOJOASMKODAS") && (calendar1.before(calendar4) || calendar1.equals(calendar4))  
                    && (calendar2.after(calendar3) || (calendar2.equals(calendar3))))
            {
                stat = 1;
                JOptionPane.showMessageDialog(null, "Data negalima", "Error", // if user does not enter name error windows pops up
                JOptionPane.ERROR_MESSAGE);
               
                break;
            }
            }
            
            if (k == 1)
            {
                Date time11 = null;
                 try {
                     
                time11 = new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString("PRADINISLAIKAS"));               
            } catch (ParseException ex) {
                Logger.getLogger(Registracija.class.getName()).log(Level.SEVERE, null, ex);
            }
                 Date time22 = null;
            try {
                
                time22 = new SimpleDateFormat("yyyy-MM-dd").parse(laikGal);
                
            } catch (ParseException ex) {
                Logger.getLogger(Registracija.class.getName()).log(Level.SEVERE, null, ex);
            }
                
                if (asm == rs.getInt("GYDYTOJOASMKODAS") && time11.equals(time22))
                {
                  String[] laikasP = rs.getString("PRADINISLAIKAS").split("-");
                  String valandaP = laikasP [2];
                  String[] laikasP2 = valandaP.split(" ");
                  valandaP = laikasP2[1];
                  String[] laikasG = rs.getString("GALUTINISLAIKAS").split("-");
                  String valandaG = laikasG [2];
                  String[] laikasG2 = valandaG.split(" ");
                  valandaG = laikasG2[1];
             
                    TakenText.append(" Gytytojas šią dieną užimtas " + "nuo " + valandaP + " iki " + valandaG  + "\n");
                }
            }
            }
   
      
      st.close();
    }
    catch (Exception e)
    {
      System.err.println("Got an exception! ");
      System.err.println(e.getMessage());
    }
        return stat;

        
        
       
     }   
    

   private int AsmensKodas;
   private int LigonioASM = 0;
  
   
   public void setCurrentUserAsmensKodas (int asm)
   {
       AsmensKodas = asm;
     
   }
  public void setLigonisASM(int asmens)
   {
       LigonioASM = asmens;
    
   }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Clock = new javax.swing.JSpinner();
        Data = new javax.swing.JLabel();
        Time = new javax.swing.JLabel();
        YearSpinner = new javax.swing.JSpinner();
        Month = new javax.swing.JSpinner();
        Laikas = new javax.swing.JComboBox();
        PickedDates = new javax.swing.JLabel();
        PatikrintiGalimuma = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        TakenText = new javax.swing.JTextArea();
        Registruoti = new javax.swing.JButton();
        atgal = new javax.swing.JButton();
        CalendarScrollPane = new javax.swing.JScrollPane();
        Calendarr = new javax.swing.JTable();
        gydytojasCombo = new javax.swing.JComboBox<>();
        gydvard = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        komentarai = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Clock.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(), null, null, java.util.Calendar.HOUR_OF_DAY));
        Clock.setMaximumSize(new java.awt.Dimension(135, 26));
        Clock.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                ClockStateChanged(evt);
            }
        });

        Data.setText("Data");

        Time.setText("Time");

        YearSpinner.setModel(new javax.swing.SpinnerNumberModel((short)2017, (short)2017, null, (short)1));
        YearSpinner.setToolTipText("");
        YearSpinner.setMaximumSize(new java.awt.Dimension(63, 20));
        YearSpinner.setOpaque(false);

        Month.setModel(new javax.swing.SpinnerListModel(new String[] {"Sausis", "Vasaris", "Kovas", "Balandis", "Gegužė", "Birželis", "Liepa", "Rugpjūtis", "Rugsėjis", "Spalis", "Lapkritis", "Gruodis"}));
        Month.setFocusCycleRoot(true);
        Month.setMaximumSize(new java.awt.Dimension(67, 26));
        Month.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                MonthStateChanged(evt);
            }
        });

        Laikas.setMaximumRowCount(6);
        Laikas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Pasirinkite...", "7:00", "7:15", "7:30", "7:45", "8:00", "8:15", "8:30", "8:45", "9:00", "9:15", "9:30", "9:45", "10:00", "10:15", "10:30", "10:45", "11:00", "11:15", "11:30", "11:45", "12:00", "12:15", "12:30", "12:45", "13:00", "13:15", "13:30", "13:45", "14:00", "14:15", "14:30", "14:45", "15:00", "15:15", "15:30", "15:45", "16:00", "16:15", "16:30", "16:45", "17:00", "17:15", "17:30", "17:45", "18:00", "18:15", "18:30", "18:45", "19:00", "19:15", "19:30", " ", " " }));
        Laikas.setMaximumSize(new java.awt.Dimension(118, 26));

        PickedDates.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        PickedDates.setText("Užimtos datos:");

        PatikrintiGalimuma.setText("Patikrinti");
        PatikrintiGalimuma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PatikrintiGalimumaActionPerformed(evt);
            }
        });

        TakenText.setColumns(20);
        TakenText.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        TakenText.setForeground(new java.awt.Color(255, 51, 51));
        TakenText.setRows(5);
        jScrollPane1.setViewportView(TakenText);

        Registruoti.setText("Užregistruoti");
        Registruoti.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RegistruotiActionPerformed(evt);
            }
        });

        atgal.setText("Atgal");
        atgal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                atgalActionPerformed(evt);
            }
        });

        Calendarr.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N
        Calendarr.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, "1"},
                {"2", "3", "4", "5", "6", "7", "8"},
                {"9", "10", "11", "12", "13", "14", "15"},
                {"16", "17", "18", "19", "20", "21", "22"},
                {"23", "24", "25", "26", "27", "28", "29"},
                {"30", "31", null, null, null, null, null}
            },
            new String [] {
                "Pr", "An", "Tr", "Kt", "Pn", "Št", "Sk"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Calendarr.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        Calendarr.setAutoscrolls(false);
        Calendarr.setCellSelectionEnabled(true);
        Calendarr.setCursor(new java.awt.Cursor(java.awt.Cursor.CROSSHAIR_CURSOR));
        Calendarr.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        Calendarr.setDropMode(javax.swing.DropMode.ON);
        Calendarr.setEditingColumn(0);
        Calendarr.setEditingRow(0);
        Calendarr.setFocusTraversalPolicyProvider(true);
        Calendarr.setFocusable(false);
        Calendarr.setMaximumSize(new java.awt.Dimension(105, 96));
        Calendarr.setName(""); // NOI18N
        Calendarr.setSelectionBackground(new java.awt.Color(102, 102, 255));
        Calendarr.setSurrendersFocusOnKeystroke(true);
        Calendarr.getTableHeader().setReorderingAllowed(false);
        Calendarr.setUpdateSelectionOnSort(false);
        CalendarScrollPane.setViewportView(Calendarr);

        gydytojasCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Gydytojas" }));
        gydytojasCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gydytojasComboActionPerformed(evt);
            }
        });

        gydvard.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Vardas/Pavardė", " " }));

        komentarai.setColumns(20);
        komentarai.setRows(5);
        jScrollPane2.setViewportView(komentarai);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Komentarai");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(445, 445, 445)
                .addComponent(Registruoti, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 137, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(CalendarScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 791, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(37, 37, 37)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Data, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Clock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Time, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(YearSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Month, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Laikas, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(gydytojasCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(gydvard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(136, 136, 136)
                                .addComponent(atgal, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(333, 333, 333)
                                .addComponent(PickedDates, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(PatikrintiGalimuma, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(187, 187, 187)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 683, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(233, 233, 233)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 552, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(454, 454, 454)
                                .addComponent(jLabel1)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(atgal, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(YearSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Month, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Laikas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(gydytojasCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(gydvard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31)
                        .addComponent(Clock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(Data)
                                .addGap(18, 18, 18)
                                .addComponent(Time))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(CalendarScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PatikrintiGalimuma, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PickedDates, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(Registruoti, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ClockStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_ClockStateChanged

        Date date = (Date) Clock.getValue(); // save as date object so it can be formated
        Instant instant = date.toInstant();
        ZoneId z = ZoneId.of("Europe/Vilnius");  // Or ZoneId.systemDefault()
        ZonedDateTime zdt = instant.atZone(z); // we transform this to our time
        String laikas = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(zdt);
       
            try
    {
 
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
                time2 = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(laikas);
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
    

        
    }//GEN-LAST:event_ClockStateChanged

    private void MonthStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_MonthStateChanged
        if (Month.getValue() == "Sausis") {
            Calendarr.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                    {null, null, null, null, null, null, "1"},
                    {"2", "3", "4", "5", "6", "7", "8"},
                    {"9", "10", "11", "12", "13", "14", "15"},
                    {"16", "17", "18", "19", "20", "21", "22"},
                    {"23", "24", "25", "26", "27", "28", "29"},
                    {"30", "31"}

                },
                new String[]{
                    "Pr", "An", "Tr", "Kt", "Pn", "Št", "Sk"
                }
            ));
        }

        if (Month.getValue() == "Vasaris") {
            Calendarr.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                    {"", "", "1", "2", "3", "4", "5"},
                    {"6", "7", "8", "9", "10", "11", "12",},
                    {"13", "14", "15", "16", "17", "18", "19"},
                    {"20", "21", "22", "23", "24", "25", "26"},
                    {"27", "28"}
                },
                new String[]{
                    "Pr", "An", "Tr", "Kt", "Pn", "Št", "Sk"
                }
            ));
        }
        if (Month.getValue() == "Kovas") {
            Calendarr.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                    {null, null, null, "1", "2", "3", "4", "5"},
                    {"6", "7", "8", "9", "10", "11", "12"},
                    {"13", "14", "15", "16", "17", "18", "19"},
                    {"20", "21", "22", "23", "24", "25", "26",},
                    {"27", "28", "29", "30", "31"}
                },
                new String[]{
                    "Pr", "An", "Tr", "Kt", "Pn", "Št", "Sk"
                }
            ));
        }

        if (Month.getValue() == "Balandis") {
            Calendarr.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                    {"", "", "", "", "", "", "1", "2"},
                    {"3", "4", "5", "6", "7", "8", "9"},
                    {"10", "11", "12", "13", "14", "15", "16"},
                    {"17", "18", "19", "20", "21", "22", "23"},
                    {"24", "25", "26", "27", "28", "29", "30"}
                },
                new String[]{
                    "Pr", "An", "Tr", "Kt", "Pn", "Št", "Sk"
                }
            ));
        }

        if (Month.getValue() == "Gegužė") {
            Calendarr.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                    {"1", "2", "3", "4", "5", "6", "7"},
                    {"8", "9", "10", "11", "12", "13", "14"},
                    {"15", "16", "17", "18", "19", "20", "21"},
                    {"22", "23", "24", "25", "26", "27", "28"},
                    {"29", "30", "31", null, null, null, null}
                },
                new String[]{
                    "Pr", "An", "Tr", "Kt", "Pn", "Št", "Sk"
                }
            ));
        }

        if (Month.getValue() == "Birželis") {

            Calendarr.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                    {"", "", "", "", "1", "2", "3", "4"},
                    {"5", "6", "7", "8", "9", "10", "11"},
                    {"12", "13", "14", "15", "16", "17", "18"},
                    {"19", "20", "21", "22", "23", "24", "25"},
                    {"26", "27", "28", "29", "30", null, null}
                },
                new String[]{
                    "Pr", "An", "Tr", "Kt", "Pn", "Št", "Sk"
                }
            ));
        }
        if (Month.getValue() == "Liepa") {
            Calendarr.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                    {"", "", "", "", "", "", "1", "2"},
                    {"3", "4", "5", "6", "7", "8", "9"},
                    {"10", "11", "12", "13", "14", "15", "16"},
                    {"17", "18", "19", "20", "21", "22", "23"},
                    {"24", "25", "26", "27", "28", "29", "30"},
                    {"31"}
                },
                new String[]{
                    "Pr", "An", "Tr", "Kt", "Pn", "Št", "Sk"
                }
            ));
        }

        if (Month.getValue() == "Rugpjūtis") {
            Calendarr.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                    {"", "1", "2", "3", "4", "5", "6"},
                    {"7", "8", "9", "10", "11", "12", "13"},
                    {"14", "15", "16", "17", "18", "19", "20"},
                    {"21", "22", "23", "24", "25", "26", "27"},
                    {"28", "29", "30", "31"}
                },
                new String[]{
                    "Pr", "An", "Tr", "Kt", "Pn", "Št", "Sk"
                }
            ));
        }

        if (Month.getValue() == "Rugsėjis") {
            Calendarr.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                    {"", "", "", "", "", "1", "2", "3"},
                    {"4", "5", "6", "7", "8", "9", "10"},
                    {"11", "12", "13", "14", "15", "16", "17"},
                    {"18", "19", "20", "21", "22", "23", "24"},
                    {"25", "26", "27", "28", "29", "30"}
                },
                new String[]{
                    "Pr", "An", "Tr", "Kt", "Pn", "Št", "Sk"
                }
            ));
        }

        if (Month.getValue() == "Spalis") {
            Calendarr.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                    {null, null, null, null, null, null, "1"},
                    {"2", "3", "4", "5", "6", "7", "8"},
                    {"9", "10", "11", "12", "13", "14", "15"},
                    {"16", "17", "18", "19", "20", "21", "22"},
                    {"23", "24", "25", "26", "27", "28", "29",},
                    {"30", "31", null, null, null, null, null}
                },
                new String[]{
                    "Pr", "An", "Tr", "Kt", "Pn", "Št", "Sk"
                }
            ));
        }

        if (Month.getValue() == "Lapkritis") {
            Calendarr.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                    {"", "", "", "1", "2", "3", "4", "5"},
                    {"6", "7", "8", "9", "10", "11", "12"},
                    {"13", "14", "15", "16", "17", "18", "19"},
                    {"20", "21", "22", "23", "24", "25", "26"},
                    {"27", "28", "29", "30", null, null, null}
                },
                new String[]{
                    "Pr", "An", "Tr", "Kt", "Pn", "Št", "Sk"
                }
            ));
        }
        if (Month.getValue() == "Gruodis") {
            Calendarr.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                    {null, null, null, null, null, null, "1", "2", "3"},
                    {"4", "5", "6", "7", "8", "9", "10"},
                    {"11", "12", "13", "14", "15", "16", "17"},
                    {"18", "19", "20", "21", "22", "23", "24"},
                    {"25", "26", "27", "28", "29", "30", "31"}
                },
                new String[]{
                    "Pr", "An", "Tr", "Kt", "Pn", "Št", "Sk"
                }
            ));
        }
    }//GEN-LAST:event_MonthStateChanged

    private void PatikrintiGalimumaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PatikrintiGalimumaActionPerformed

     
        int stat = 0;
        int column = 0;
        int row = 0;
        String diena = "0";
        

        TakenText.setText(null);
        int PasirinktMetai = (short) YearSpinner.getValue();
        String laikas = (String) Laikas.getSelectedItem();
        String laikasGalinis;
        
                    String splitTime[] = laikas.split(":");
                    int minu, valanda;
        
                    String hours = splitTime[0];
                    String minutes = splitTime[1];
                    minu = Integer.parseInt(minutes);
                    minu+=intervalas;
                    valanda = Integer.parseInt (hours);
                            
                    if (minu  >= 60)
                    {
                     valanda = valanda + 1;
                     minu = minu - 60;
                    }
                    if (minu == 0)
                    laikasGalinis = valanda + ":" + minu + "0";
                    else laikasGalinis = valanda + ":" + minu;
        

        String month = (String) Month.getValue();
        int year = (short) YearSpinner.getValue();
        String pasirinktMenesis= (String) Month.getValue();
  
        try 
        
        {
            row = Calendarr.getSelectedRow();
            column = Calendarr.getSelectedColumn();
            diena = (String) Calendarr.getValueAt(row, column);
        } 
        catch (ArrayIndexOutOfBoundsException exception) 
        {}
       
        switch (pasirinktMenesis) {
            case "Sausis":
                pasirinktMenesis = "01";
                break;
            case "Vasaris":
                pasirinktMenesis = "02";
                break;
            case "Kovas":
                pasirinktMenesis = "03";
                break;
            case "Balandis":
                pasirinktMenesis = "04";
                break;
            case "Gegužė":
               pasirinktMenesis = "05";
                break;
            case "Birželis":
                pasirinktMenesis = "06";
                break;
            case "Liepa":
                pasirinktMenesis = "07";
                break;
            case "Rugpjūtis":
                pasirinktMenesis = "08";
                break;
            case "Rugsėjis":
                pasirinktMenesis = "09";
                break;
            case "Spalis":
                pasirinktMenesis = "10";
                break;
            case "Lapkritis":
                pasirinktMenesis = "11";
                break;
            case "Gruodis":
                pasirinktMenesis = "12";
                break;
            default:
                break;}
        
        
            String PasirnktMenesiss;
        
            char[] digits = pasirinktMenesis.toCharArray();
                    
            if (Character.getNumericValue(digits[0]) == 0)
            {
              PasirnktMenesiss = Character.toString (digits[1]);
            }
                    
            else
            
            PasirnktMenesiss = Character.toString (digits[0]) + Character.toString (digits[1]);
                    
            String vardaspilnas = gydvard.getSelectedItem().toString();
            String[] parts = vardaspilnas.split(" ");
            String vardas = parts[0];
            String pavarde = parts[1]; 
            gydASM =  GetGydytojasAsmKodas(vardas, pavarde);
                    
            String pradLaikas = PasirinktMetai + "-" + pasirinktMenesis + "-" + diena + " " + laikas;
            String galLaikas = PasirinktMetai + "-" + pasirinktMenesis + "-" + diena + " " + laikasGalinis;
                   
                   
            tikrinti(pradLaikas, galLaikas, gydASM, 1);
                   
                

        
    }//GEN-LAST:event_PatikrintiGalimumaActionPerformed

    private void RegistruotiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RegistruotiActionPerformed
        // This is where registering is performed
     
         int gydASM;
       
        int PasirinktMetai = (short) YearSpinner.getValue();
        int stat = 0;
        int column = 0;
        int row = 0;
        String valueInCell = null;
        
        if (Calendarr.getSelectionModel().isSelectionEmpty()) 
        {
            
            stat = 1;
            JOptionPane.showMessageDialog(null, "Nepasirinkta data", "Error", // if user does not enter name error windows pops up
                JOptionPane.ERROR_MESSAGE);
        }

        try 
        
        {
            row = Calendarr.getSelectedRow();
            column = Calendarr.getSelectedColumn();
            valueInCell = (String) Calendarr.getValueAt(row, column);
        } 
        catch (ArrayIndexOutOfBoundsException exception) 
        {}
        
        if (Laikas.getSelectedItem().equals("Pasirinkite..."))
        { 
            stat = 1;
           JOptionPane.showMessageDialog(null, "Nepasirinktas laikas", "Error",
               JOptionPane.ERROR_MESSAGE);
        }
        if (gydytojasCombo.getSelectedItem().equals("Pasirinkite gydytoją"))
        {
            stat = 1;
           JOptionPane.showMessageDialog(null, "Nepasirinktas gydytojas", "Error",
               JOptionPane.ERROR_MESSAGE);
        }
            

        String diena = valueInCell;
        String laikas = (String) Laikas.getSelectedItem();
        String laikasGalinis;
        
                    String splitTime[] = laikas.split(":");
                    int minu, valanda;
        
                    String hours = splitTime[0];
                    String minutes = splitTime[1];
                    minu = Integer.parseInt(minutes);
                    minu+=intervalas;
                    valanda = Integer.parseInt (hours);
                            
                    if (minu  >= 60)
                    {
                     valanda = valanda + 1;
                     minu = minu - 60;
                    }
                    if (minu == 0)
                    laikasGalinis = valanda + ":" + minu + "0";
                    else laikasGalinis = valanda + ":" + minu;
        
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int dabartMen = localDate.getMonthValue();
        int dabartDien = localDate.getDayOfMonth();
        int dabarMetai = localDate.getYear();
        
        
        String pasirinktMenesis= (String) Month.getValue();
  

       
        switch (pasirinktMenesis) {
            case "Sausis":
                pasirinktMenesis = "01";
                break;
            case "Vasaris":
                pasirinktMenesis = "02";
                break;
            case "Kovas":
                pasirinktMenesis = "03";
                break;
            case "Balandis":
                pasirinktMenesis = "04";
                break;
            case "Gegužė":
               pasirinktMenesis = "05";
                break;
            case "Birželis":
                pasirinktMenesis = "06";
                break;
            case "Liepa":
                pasirinktMenesis = "07";
                break;
            case "Rugpjūtis":
                pasirinktMenesis = "08";
                break;
            case "Rugsėjis":
                pasirinktMenesis = "09";
                break;
            case "Spalis":
                pasirinktMenesis = "10";
                break;
            case "Lapkritis":
                pasirinktMenesis = "11";
                break;
            case "Gruodis":
                pasirinktMenesis = "12";
                break;
            default:
                break;}
        
        
           String PasirnktMenesiss;
           char[] digits = pasirinktMenesis.toCharArray();
                    
           if (Character.getNumericValue(digits[0]) == 0)
           {
                 PasirnktMenesiss = Character.toString (digits[1]);
           }
                    
           else
          PasirnktMenesiss = Character.toString (digits[0]) + Character.toString (digits[1]);
                        
                    

       if (dabartMen > Integer.parseInt(PasirnktMenesiss) && dabarMetai == PasirinktMetai || 
             dabartMen == Integer.parseInt(PasirnktMenesiss) && Integer.parseInt(diena) < dabartDien &&
               dabarMetai == PasirinktMetai || dabarMetai > PasirinktMetai ) 
       {
           stat = 1;
           JOptionPane.showMessageDialog(null, "Pasirinkta diena arba mėnesis yra negalimi, nes ši data jau praėjo", "Error",
              JOptionPane.ERROR_MESSAGE);
       }
        
        
            String pradLaikas = PasirinktMetai + "-" + pasirinktMenesis + "-" + diena + " " + laikas;
            String galLaikas = PasirinktMetai + "-" + pasirinktMenesis + "-" + diena + " " + laikasGalinis;
        
            String vardaspilnas = gydvard.getSelectedItem().toString();
            String[] parts = vardaspilnas.split(" ");
            String vardas = parts[0];
            String pavarde = parts[1]; 
            gydASM =  GetGydytojasAsmKodas(vardas, pavarde);
            
           
            
            if (tikrinti(pradLaikas, galLaikas, gydASM, 0) > 0)
                stat = 1;
        
        if (stat == 0)
        {
            try { 
            String url = "jdbc:derby://localhost:1527/Sveikatos Centras"; 
            Connection conn = DriverManager.getConnection(url,"sveikuoliai","sveikuoliai"); 
            PreparedStatement ps = conn.prepareStatement("INSERT INTO APP.VIZITAI VALUES (?,?,?,?,?)");
            ps.setString(1,pradLaikas);
            ps.setString(2,galLaikas);
            if (LigonioASM != 0) 
            ps.setInt(3, LigonioASM);
            else ps.setInt(3, AsmensKodas);
            ps.setString (4, komentarai.getText());
            ps.setInt (5, gydASM);
             ps.executeUpdate();
             JOptionPane.showMessageDialog(null, "Užregistruota.");

            ps.close(); 
        } catch (Exception e) { 
            JOptionPane.showMessageDialog(null, "Nepavyko", "Error", // if user does not enter name error windows pops up
                JOptionPane.ERROR_MESSAGE);
            System.err.println("Got an exception! "); 
            System.err.println(e.getMessage()); 
        }
    }
        

      
           
        
    }//GEN-LAST:event_RegistruotiActionPerformed

    private void atgalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_atgalActionPerformed
       
           if (LigonioASM == 0) 
           {   
             Pacientas men = new Pacientas ();
             men.setVisible(true);     
             men.SetCurrentUserAsmensKodas(AsmensKodas);
             super.dispose();
           }
           else
           {
              Gydytojas dak = new  Gydytojas();
             dak.setVisible(true);     
             dak.setCurrentUserAsmensKodas(AsmensKodas);
             super.dispose(); 
           }
        
       
    }//GEN-LAST:event_atgalActionPerformed

    private void gydytojasComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gydytojasComboActionPerformed
        uzsetintiGydVardus();
    }//GEN-LAST:event_gydytojasComboActionPerformed

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
            java.util.logging.Logger.getLogger(Registracija.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Registracija.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Registracija.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Registracija.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

     
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Registracija().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane CalendarScrollPane;
    public static javax.swing.JTable Calendarr;
    private javax.swing.JSpinner Clock;
    private javax.swing.JLabel Data;
    private javax.swing.JComboBox Laikas;
    private static javax.swing.JSpinner Month;
    private javax.swing.JButton PatikrintiGalimuma;
    private javax.swing.JLabel PickedDates;
    private javax.swing.JButton Registruoti;
    private javax.swing.JTextArea TakenText;
    private javax.swing.JLabel Time;
    private javax.swing.JSpinner YearSpinner;
    private javax.swing.JButton atgal;
    private javax.swing.JComboBox<String> gydvard;
    private javax.swing.JComboBox<String> gydytojasCombo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea komentarai;
    // End of variables declaration//GEN-END:variables
}
