package rozlozeni;


import java.awt.*;
import java.awt.event.*;
import java.util.Locale;
import javax.swing.*;
import java.text.*;

    
class Cerna {       
    String name = "ČERNÁ";    
    int rychlosti [] = {150, //index 0 pro 1mm
        106, //index 1 pro 1,5mm
        90, //index 2 pro 2mm
        81, //index 3 pro 3mm        
        71, //index 4 pro 4mm        
        66, //index 5 pro 5mm
        55, //index 6 pro 6mm
        46, //index 7 pro 8mm
        40, //index 8 pro 10mm
        31, //index 9 pro 12mm
        25, //index 10 pro 15mm 
        18, //index 11 pro 20mm
        13}; //index 12 pro 25mm
    double ro = 7.85;    
}

class Nerez {
    String name = "NEREZ";    
    int rychlosti [] = {180, //index 0 pro 1mm
        130,//index 1 pro 1,5mm
        115,//index 2 pro 2mm
        80,//index 3 pro 3mm
        55, //index 4 pro 4mm 
        50, //index 5 pro 5mm
        40, //index 6 pro 6mm
        25, //index 7 pro 8mm
        18, //index 8 pro 10mm
        13, //index 9 pro 12mm
        10,//index 10 pro 15mm 
        6}; //index 11 pro 20mm
    double ro = 7.85;           
}

class Hlinik {
    String name = "HLINÍK"; 
    int rychlosti [] = {220, //index 0 pro 1mm
        170,//index 1 pro 1,5mm
        130,//index 2 pro 2mm
        90,//index 3 pro 3mm
        65,//index 4 pro 4mm 
        40,//index 5 pro 5mm
        30,//index 6 pro 6mm
        15,//index 7 pro 8mm
        12};//index 8 pro 10mm
    double ro = 2.7;    
}

class Format {
    int fa,fb; // rozměry plechu
    int vypa,vypb; //rozměry vypalku čtvrce
    int vypprum; //rozměr vypalku kruhu
    double hv,prum,mezera,ksfa1,ksfa2,ksfb1,ksfb2,ks1,ks2,ks,ksnam2;
    double kspravoa, kspravob,kspravo,h19,i19,nadelkutroju1,navyskutroju1,
            nadelkutroju2, navyskutroju2, kstroju;
    
    Format(int fora, int forb) {
        fa = Math.max(fora,forb);
        fb = Math.min(fora,forb);
            }
    
    
    //přetížená metoda pro výpočet kusů na 1m2 - ctverce
    double vypocitejksnam2 (int vypa, int vypb, double tloustka) {            
        this.vypa = vypa;
        this.vypb = vypb;
        fa-=5;
        fb-=5;
        mezera = tloustka;
        if (tloustka<=5) mezera = 5;
        vypa+=mezera;
        vypb+=mezera;        
        
        ksfa1 = Math.floor ((fa/vypa));
        ksfb1 = Math.floor ((fb/vypb));
        ks1 = ksfa1*ksfb1;
        ksfa2 = Math.floor ((fa/vypb));
        ksfb2 = Math.floor ((fb/vypa));
        ks2 = ksfa2*ksfb2;
        ks = Math.max (ks1,ks2);
        ksnam2 = ks/(((fa+5)*(fb+5))/1000000);
        return ksnam2;                         
    }
    
    //přetížená metoda pro výpočet kusů na 1m2 - kruhu
    double vypocitejksnam2 (double prum, double tloustka) {        
        this.prum=prum;
        mezera = tloustka;
        if (tloustka<=5) mezera = 5;
        prum+=mezera;
        
        //pravoúhlé naskládání
        kspravoa = Math.floor((fa/prum));
        kspravob = Math.floor((fb/prum));
        kspravo = Math.max(kspravoa,kspravob);
        
        //trojúhelníkové naskládání
        h19 = Math.sqrt((prum*prum)-((prum/2)*(prum/2)));
        i19 = ((prum+mezera)/2)+mezera/2;
         
        nadelkutroju1 = Math.floor((fa/(2*h19)));
        navyskutroju1 = Math.floor((fb/prum));
        nadelkutroju2 = Math.floor(((fa-(h19-((prum+mezera)/2)))/(2*h19)));
        navyskutroju2 = Math.floor(((fb-i19)/prum));
        
        kstroju = (nadelkutroju1*navyskutroju1)+
                (nadelkutroju2*navyskutroju2);
        
        ks = Math.max (kspravo,kstroju);
        ksnam2 = ks/(((fa+5)*(fb+5))/1000000);
        return ksnam2;
    }   
}
   

class Rozlozeni {    
    
    
    
    
    public static void main(String[] args) {    
       
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
		new Rozlozeni();
            }
	});
    }
    
    
    public Rozlozeni () {
        
        Cerna c = new Cerna();
        Nerez n = new Nerez();
        Hlinik h = new Hlinik();
        
        
        
        
        
        
	JFrame jfrm = new JFrame("Rozlozeni 3.0.0");
        jfrm.setSize(410,330);
        jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        GridBagLayout gbag = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        jfrm.setLayout(gbag);
        JLabel heading = new JLabel ("Zadejte parametry"); 
        
      
        //OBDÉLNÍK - zadání parametrů
        JRadioButton b1 = new JRadioButton();     
        
        JTextField tfstra = new JTextField("str a", 4);         
        FocusListener fl1 = new FocusListener () {
              public void focusGained(FocusEvent e) {
                  b1.setSelected(true);
              }              
              public void focusLost(FocusEvent f) {
              }              
        };    
        tfstra.addFocusListener(fl1);  
        
        JTextField tfstrb = new JTextField("str b", 4);        
        tfstrb.addFocusListener(fl1);
        
        JPanel obdelnik = new JPanel(new FlowLayout());        
        obdelnik.add(b1);
        obdelnik.add(tfstra);
        obdelnik.add(tfstrb);
        obdelnik.setBorder(BorderFactory.createTitledBorder
            (BorderFactory.createLineBorder(Color.LIGHT_GRAY),"OBDÉLNÍK"));        
       
        
        
        //KRUŽNICE - zadání parametrů
        JRadioButton b2 = new JRadioButton();    
        
        JTextField tfprum = new JTextField("průměr", 4);        
        FocusListener fl2 = new FocusListener () {
              public void focusGained(FocusEvent e) {
                  b2.setSelected(true);
              }              
              public void focusLost(FocusEvent f) {
              }              
        };        
        tfprum.addFocusListener(fl2); 
         
        JPanel kruznice = new JPanel(new FlowLayout());        
        kruznice.add(b2);
        kruznice.add(tfprum);
        kruznice.setBorder(BorderFactory.createTitledBorder
            (BorderFactory.createLineBorder(Color.LIGHT_GRAY),"KRUŽNICE"));
        
        
       
        // OTVORY TYP1 - zadání parametrů        
        JRadioButton btyp1 = new JRadioButton ( );
        
        JTextField tfprumtyp1 = new JTextField("průměr", 4);        
        FocusListener fltyp1 = new FocusListener () {
              public void focusGained(FocusEvent e) {
                  btyp1.setSelected(true);
              }              
              public void focusLost(FocusEvent f) {
              }              
        };        
        tfprumtyp1.addFocusListener(fltyp1);
     
        JTextField tfpoctyp1 = new JTextField("počet", 4);
        tfpoctyp1.addFocusListener(fltyp1);
          
        JPanel otvory1 = new JPanel(new FlowLayout());         
        otvory1.add(btyp1);
        otvory1.add(tfpoctyp1);
        otvory1.add(tfprumtyp1);
      
        otvory1.setBorder(BorderFactory.createTitledBorder
            (BorderFactory.createLineBorder(Color.LIGHT_GRAY),"Otvory 1"));
        
        
        
        // OTVORY TYP2 - zadání parametrů
        JRadioButton btyp2 = new JRadioButton ();
        
        JTextField tfprumtyp2 = new JTextField("průměr", 4);
        FocusListener fltyp2 = new FocusListener () {
              public void focusGained(FocusEvent e) {
                  btyp2.setSelected(true);
              }              
              public void focusLost(FocusEvent f) {
              }              
        };        
        tfprumtyp2.addFocusListener(fltyp2);
        
        JTextField tfpoctyp2 = new JTextField("počet", 4);
        tfpoctyp2.addFocusListener(fltyp2);       
        
        JPanel otvory2 = new JPanel(new FlowLayout());
        otvory2.add(btyp2);
        otvory2.add(tfpoctyp2);
        otvory2.add(tfprumtyp2);
       
        otvory2.setBorder(BorderFactory.createTitledBorder
            (BorderFactory.createLineBorder(Color.LIGHT_GRAY),"Otvory 2"));
       
        
        
        // DÉLKA NAVÍC - zadání parametrů        
        JRadioButton bnavic = new JRadioButton ();
        
        JTextField tfnavic = new JTextField("délka", 4);
        FocusListener flnavic = new FocusListener () {
              public void focusGained(FocusEvent e) {
                  bnavic.setSelected(true);
              }              
              public void focusLost(FocusEvent f) {
              }              
        };        
        tfnavic.addFocusListener(flnavic);
        
        JPanel navic = new JPanel(new FlowLayout());
        navic.add(bnavic); 
        navic.add(tfnavic);
        navic.setBorder(BorderFactory.createTitledBorder
            (BorderFactory.createLineBorder(Color.LIGHT_GRAY),"Řezání navíc"));
     
        
        
        //MATERIÁLY      
        JRadioButton jbtn1 = new JRadioButton("ČERNÁ");
        JRadioButton jbtn2 = new JRadioButton("NEREZ");
        JRadioButton jbtn3 = new JRadioButton("HLINÍK");
        
        JPanel materialy = new JPanel(new FlowLayout());
        materialy.add(jbtn1);
        materialy.add(jbtn2);
        materialy.add(jbtn3);
        materialy.setBorder(BorderFactory.createTitledBorder
            (BorderFactory.createLineBorder(Color.LIGHT_GRAY),"MATERIÁLY"));
    
        
        //TLOUŠŤKY
        JLabel jlt = new JLabel("TLOUŠŤKA");
        Double tloustky [] = {1.0,1.5,2.0,3.0,4.0,5.0,6.0,8.0,10.0,
            12.0,15.0,20.0,25.0}; 
        JComboBox<Double> jcb = new JComboBox<Double>(tloustky);
        JPanel tloustkyp = new JPanel(new FlowLayout());
        tloustkyp.add(jlt);
        tloustkyp.add(jcb);
        tloustkyp.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        
        
        JButton jbtn = new JButton("VYPOČTI");
        
        //umístění a rozvržení objektů hlavního framu je pod vnořenou třídou 
        //vypočti
        

class Vypocti {
    int strai, prumi, prumtyp1i, prumtyp2i, navici, strbi, poctyp1i, poctyp2i,
            tlporadi, v;
    double obvod =0, ttyp1=0, ttyp2=0, tnavic=0, tobvod=0;
    double t, ksnam2,ksnam2mf,ksnam2sf, ksnam2vf, hustota, hv,cv,
            obsahtyp1,obsahtyp2,obsahzakl,pu;
    String material, tvar; 
        
    
        
Vypocti() {   

//nacteni hodnot zadanych uzivatelem
    String stra = tfstra.getText();
    String prum = tfprum.getText();
    String prumtyp1 = tfprumtyp1.getText();
    String prumtyp2 = tfprumtyp2.getText();
    String navic = tfnavic.getText();
    String strb = tfstrb.getText();
    String poctyp1 = tfpoctyp1.getText();
    String poctyp2 = tfpoctyp2.getText();
    Double tl = (Double) jcb.getSelectedItem();
    
    
    
     //Vytvoreni objektu typu Locale
    Locale myLocale = new Locale ("cs", "CZ");
    //Vytvoreni ciselneho formatu
    NumberFormat nf;
    nf = NumberFormat.getInstance(myLocale);
    nf.setMinimumFractionDigits(2);
    nf.setMaximumFractionDigits(2);
    
//zjištění rychlosti v závislosti na vybraném materiálu
    if(tl==tloustky[0])
        tlporadi=0;
    else if (tl==tloustky[1])
        tlporadi=1;
    else if (tl==tloustky[2])
        tlporadi=2;
    else if (tl==tloustky[3])
        tlporadi=3;
    else if (tl==tloustky[4])
        tlporadi=4;
    else if (tl==tloustky[5])
        tlporadi=5;
    else if (tl==tloustky[6])
        tlporadi=6;
    else if (tl==tloustky[7])
        tlporadi=7;
    else if (tl==tloustky[8])
        tlporadi=8;
    else if (tl==tloustky[9])
        tlporadi=9;
    else if (tl==tloustky[10])
        tlporadi=10;
    else if (tl==tloustky[11])
        tlporadi=11;
    else if (tl==tloustky[12])
        tlporadi=12;
    
    
    //zajištění vyjímky omezení tloušťek plechu při řezání různých materiálů a
    //přiřazení hustoty a rychlosti a druhu materiálu
    try {
        if(jbtn1.isSelected()){
            v=c.rychlosti[tlporadi];
            hustota = c.ro;
            material = c.name;
        }
        
                else if (jbtn2.isSelected()) {
                    v=n.rychlosti[tlporadi];
                    hustota = n.ro;
                    material = n.name;
                }
                
                else if (jbtn3.isSelected()) {
                    v=h.rychlosti[tlporadi];
                    hustota = h.ro;
                    material = h.name;
                }
        
                    
    }
    catch(ArrayIndexOutOfBoundsException exc) {             
        JOptionPane.showMessageDialog(jfrm,"Neplatná tloušťka pro daný "
                + "materiál!");
        return;
    }
    
    
//vytvoření instancí dostupných formátů plechu
Format mf = new Format(1000,2000);
Format sf = new Format(1250,2500);
Format vf = new Format(1500,3000);


// Otvory typ1 - výpočet času
if(btyp1.isSelected()) {
    
    // nekompletní zadání
    if (prumtyp1.length() == 0 | prumtyp1.equals("průměr") | 
            poctyp1.length() == 0 | poctyp1.equals("počet")) {
        JOptionPane.showMessageDialog(jfrm,"Nekompletní zadání!");
        return;
    } // konec podmínky nekompletního zadání

try {
prumtyp1i = Integer.parseInt(prumtyp1);
poctyp1i = Integer.parseInt(poctyp1);
}
catch(NumberFormatException exc) {             
JOptionPane.showMessageDialog(jfrm,"Neplatné parametry!");
return;
}

//zohlednění tloušťky při době zápalu otvoru
if (tl<7) ttyp1 = (((3.14 * prumtyp1i)*poctyp1i)/v) + poctyp1i;//sek na zapal
ttyp1 = (((3.14 * prumtyp1i)*poctyp1i)/v) + (poctyp1i*2);//2sek na zapal
}


// Otvory typ2 - výpočet času
if(btyp2.isSelected()) {
    
    // nekompletní zadání
    if (prumtyp2.length() == 0 | prumtyp2.equals("průměr") | 
            poctyp2.length() == 0 | poctyp2.equals("počet")) {
        JOptionPane.showMessageDialog(jfrm,"Nekompletní zadání!");
        return;
    } // konec podmínky nekompletního zadání

try {
prumtyp2i = Integer.parseInt(prumtyp2);
poctyp2i = Integer.parseInt(poctyp2);
}
catch(NumberFormatException exc) {             
JOptionPane.showMessageDialog(jfrm,"Neplatné parametry!");
return;
}

//zohlednění tloušťky při době zápalu otvoru
if (tl<7) ttyp2 = (((3.14 * prumtyp2i)*poctyp2i)/v) + poctyp2i;//sek na zapal
ttyp2 = (((3.14 * prumtyp2i)*poctyp2i)/v) + (poctyp2i*2);//2sek na zapal
}
   

// Vzdálenost navíc - výpočet času
if(bnavic.isSelected()) {
    
    // nekompletní zadání
    if (navic.length() == 0 | navic.equals("délka")) {
        JOptionPane.showMessageDialog(jfrm,"Nekompletní zadání!");
        return;
    } // konec podmínky nekompletního zadání

try {
navici = Integer.parseInt(navic);
}
catch(NumberFormatException exc) {             
JOptionPane.showMessageDialog(jfrm,"Neplatné parametry!");
return;
}
tnavic=navici/v;
}

//O B D É L N Í K
if(b1.isSelected()) { 
    tvar = "Obdélník";
// nekompletní zadání
    if (stra.equals("str a") | strb.equals("str b") | stra.length()==0|
            strb.length()==0) {
        JOptionPane.showMessageDialog(jfrm,"Nekompletní zadání!");
        return;
    } // konec podmínky nekompletního zadání 
       
try {
strai = Integer.parseInt(stra);
strbi = Integer.parseInt(strb);
}
catch(NumberFormatException exc) {             
JOptionPane.showMessageDialog(jfrm,"Neplatné parametry!");
return;
}
obvod = strai*2 + strbi*2;
ksnam2mf = mf.vypocitejksnam2(strai,strbi,tl);
ksnam2sf = sf.vypocitejksnam2(strai,strbi,tl);
ksnam2vf = vf.vypocitejksnam2(strai,strbi,tl);
ksnam2 = Math.max((Math.max(ksnam2mf,ksnam2sf)),ksnam2vf);
}


//K R U Ž N I C E
if(b2.isSelected()) {
    tvar = "Kružnice";
    // nekompletní zadání
    if (prum.length() == 0 | prum.equals("průměr")) {
        JOptionPane.showMessageDialog(jfrm,"Nekompletní zadání!");
        return;
    } // konec podmínky nekompletního zadání

try {
prumi = Integer.parseInt(prum);
}
catch(NumberFormatException exc) {             
JOptionPane.showMessageDialog(jfrm,"Neplatné parametry!");
return;
}
obvod = 3.14 * prumi;
ksnam2mf = mf.vypocitejksnam2(prumi,tl);
ksnam2sf = sf.vypocitejksnam2(prumi,tl);
ksnam2vf = vf.vypocitejksnam2(prumi,tl);
ksnam2 = Math.max((Math.max(ksnam2mf,ksnam2sf)),ksnam2vf);
}



//VÝPOČET A ZOBRAZENÍ VÝSLEDKU
// Výpočet času proměnné obvod
tobvod = obvod/v;
// Výsledný čas
t = ttyp1+ttyp2+tnavic+tobvod;
// hustota vybraného materiálu
hv = (10*10*(tl/100)*hustota)/ksnam2;
// čistá váha
double pol = prumi/2;
double pold100 = pol/100;

double polt1 = prumtyp1i / 2;
double polt2 = prumtyp2i / 2;
double polt1d100 = polt1/100;
double polt2d100 = polt2/100;

    //obsah čtverce/kruhu v dm2
    obsahzakl = ((strai/100)*(strbi/100)) + 3.14 * Math.pow(pold100, 2.0);
    
    //obsahy kruhů typ1+typ2 v dm2
    obsahtyp1 = (3.14*Math.pow(polt1d100 ,2.0)) *poctyp1i;
    obsahtyp2 = (3.14*Math.pow(polt2d100 ,2.0)) *poctyp2i;   
cv = (obsahzakl-obsahtyp1-obsahtyp2)*(tl/100)*hustota; //minus obsah děr typ1+2
// cena povrchovky očištěná o kruhy typu1+2
pu = (obsahzakl-obsahtyp1-obsahtyp2)*5;




JDialog jd = new JDialog (jfrm,material+":"+tl+":"
                +tvar+":"+strai+":"+strbi+"::"+"t1:"+prumtyp1i+":"+poctyp1i+
                "t2:"+prumtyp2i+":"+poctyp2i+"n:"+navici,false);
    jd.setSize(340,260);
    jd.setLocation(100,100);
    GridBagLayout vgbag = new GridBagLayout();
    GridBagConstraints vgbc = new GridBagConstraints();
    jd.setLayout(vgbag);
    JLabel heading = new JLabel
    ("Výsledek výpočtů");
    JLabel tLab = new JLabel("Řezný čas: ");
    JLabel hvLab = new JLabel("Spotřeba materiálu: ");
    JLabel cvLab = new JLabel("Čistá váha: ");
    JLabel puLab = new JLabel("Cena PU (5,-Kč/m2): ");
                
    JTextField tText = new JTextField(10);
    JTextField hvText = new JTextField(10);
    JTextField cvText = new JTextField(10);
    JTextField puText = new JTextField(10);                
                
    tText.setEditable(false);
    tText.setText(Math.round(t) + " s");
    hvText.setEditable(false);
    hvText.setText(nf.format(hv) + " kg");
    cvText.setEditable(false);
    cvText.setText(nf.format(cv) + " kg");
    puText.setEditable(false);
    double pu1 = pu*10;
    double pu2 = Math.round(pu1);
    double pu3 = pu2/10;
    puText.setText(nf.format(pu3) + " Kč"); ///POKUS
                        
    JButton doIt = new JButton("OK");
        doIt.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent ae) {
        jd.setVisible(false);
            }
	}); 

    // Definice rozvrzeni.
    // Pouziti vahy radku ve vysi 1.0
    vgbc.weighty = 1.0;
    vgbc.gridwidth = GridBagConstraints.REMAINDER;
    vgbc.anchor = GridBagConstraints.NORTH;
    vgbag.setConstraints(heading, vgbc);

    // Ukotveni vetsiny komponent doprava.
    vgbc.anchor = GridBagConstraints.EAST;
                
    vgbc.gridwidth = GridBagConstraints.RELATIVE;
    vgbag.setConstraints(tLab, vgbc);
    vgbc.gridwidth = GridBagConstraints.REMAINDER;
    vgbag.setConstraints(tText, vgbc);

    vgbc.gridwidth = GridBagConstraints.RELATIVE;
    vgbag.setConstraints(hvLab, vgbc);
    vgbc.gridwidth = GridBagConstraints.REMAINDER;
    vgbag.setConstraints(hvText, vgbc);

    vgbc.gridwidth = GridBagConstraints.RELATIVE;
    vgbag.setConstraints(cvLab, vgbc);
    vgbc.gridwidth = GridBagConstraints.REMAINDER;
    vgbag.setConstraints(cvText, vgbc);

    vgbc.gridwidth = GridBagConstraints.RELATIVE;
    vgbag.setConstraints(puLab, vgbc);
    vgbc.gridwidth = GridBagConstraints.REMAINDER;
    vgbag.setConstraints(puText, vgbc);

    vgbc.anchor = GridBagConstraints.CENTER;
    vgbag.setConstraints(doIt, vgbc);

    // Pridani vsech komponent.
    jd.add(heading);
    jd.add(tLab);
    jd.add(tText);
    jd.add(hvLab);
    jd.add(hvText);
    jd.add(cvLab);
    jd.add(cvText);
    jd.add(puLab);
    jd.add(puText);             
    jd.add(doIt);
    
    jd.setVisible(true);

}
} // KONEC TŘÍDY VYPOČTI 


               
        // zadání posluchačů
        jbtn.addActionListener(new ActionListener()  {
            public void actionPerformed(ActionEvent ae) {
                new Vypocti();
            }
        });        
        tfstra.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                new Vypocti();
            }
        });        
        tfprum.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                new Vypocti();
            }
        });        
        tfprumtyp1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                new Vypocti();
            }
        });        
        tfprumtyp2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                new Vypocti();
            }
        });        
        tfnavic.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                new Vypocti();
            }
        });        
        tfstrb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                new Vypocti();
            }
        });        
        tfpoctyp1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                new Vypocti();
            }
        });        
        tfpoctyp2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                new Vypocti();
            }
        });
     
                
        // group pro tvary
        ButtonGroup bg1 = new ButtonGroup();
        bg1.add(b1);
        bg1.add(b2);
        b1.setSelected(true);
        
        // group pro materiály
        ButtonGroup bg2 = new ButtonGroup();
        bg2.add(jbtn1);
        bg2.add(jbtn2);
        bg2.add(jbtn3);
        jbtn1.setSelected(true);     
        
        
        // Definice rozvrzeni hlavního framu
    // Pouziti vahy radku ve vysi 1.0
    gbc.weighty = 1.0;
    gbc.gridwidth = GridBagConstraints.REMAINDER;
    gbc.anchor = GridBagConstraints.NORTH;
    gbag.setConstraints(heading, gbc);
    
    // Ukotveni vetsiny komponent doprava.
    gbc.anchor = GridBagConstraints.WEST;
                
    gbc.gridwidth = GridBagConstraints.RELATIVE;
    gbag.setConstraints(kruznice, gbc);
    gbc.gridwidth = GridBagConstraints.REMAINDER;
    gbag.setConstraints(otvory1, gbc);
    
    gbc.gridwidth = GridBagConstraints.RELATIVE;
    gbag.setConstraints(obdelnik, gbc);
    gbc.gridwidth = GridBagConstraints.REMAINDER;
    gbag.setConstraints(otvory2, gbc);

    gbc.gridwidth = GridBagConstraints.RELATIVE;
    gbag.setConstraints(materialy, gbc);
    gbc.gridwidth = GridBagConstraints.REMAINDER;
    gbag.setConstraints(navic, gbc);

    gbc.gridwidth = GridBagConstraints.RELATIVE;
    gbag.setConstraints(tloustkyp, gbc);

    gbc.anchor = GridBagConstraints.CENTER;
    gbag.setConstraints(jbtn, gbc);
    
        
    // Pridani vsech komponent.
    jfrm.add(heading);
    jfrm.add(obdelnik);
    jfrm.add(otvory1);
    jfrm.add(kruznice);
    jfrm.add(otvory2);
    jfrm.add(materialy);
    jfrm.add(navic);
    jfrm.add(tloustkyp);
    jfrm.add(jbtn);
        
    jfrm.setVisible(true);
    }

    
}