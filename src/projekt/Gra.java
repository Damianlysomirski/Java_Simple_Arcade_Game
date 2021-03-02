package projekt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;



/**
 * Klasa rozszerzająca JFrame - zawiera menu i okno glowne calej aplikacji
 *
 * @authors Damian Łysomirski
 * 13 kwietnia 2019 21:30
 */

public class Gra extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;
	
	Tlo tlo = new Tlo();
    MenuNowe menuPanel = new MenuNowe();
    
    /** Zmienna statyczna przechowujaca informacje o aktualnym wyniku */
     static int wynik=0;
    
    /** Zmienna statyczna przechowujaca informacje o aktualnym poziomie */
    static int aktualny_poziom=0;
    
    private Okno_Startowe okno_Startowe;
    private Okno_Wynikow okno_Wynikow;
    
    /** Zmienna statyczna przechowujaca informacje o czasie zaczecia gry */
    static long starttime; 
    
    /** Zmienna statyczna przechowujaca informacje o wybranym poziomie trudnosci */
    static String selected_item; 
    
    
	public Gra()
	{
		//addActionListener(this);
        Dimension size1 = new Dimension(Parsujaca.getPoczatkowaSzerokoscPlanszy(),Parsujaca.getPoczatkowaWysokoscPlanszy());
        setSize(size1);
        setTitle(Parsujaca.getNazwaGry());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
		setResizable(true);
		
		
		add(tlo,BorderLayout.CENTER);
        add(menuPanel,BorderLayout.NORTH);
        
        menuPanel.newgame.addActionListener(new newGame());
        menuPanel.exit.addActionListener(new Exit());
        menuPanel.pause.addActionListener(new Pause());
        menuPanel.highscores.addActionListener(new Wyniki());
        
        
        Image imgIc = new ImageIcon("Images/ptak.png").getImage();
		setIconImage(imgIc);
		
	    this.addComponentListener(new ComponentAdapter() {         
            @Override
            
            /**
        	 * Metoda ustawiajaca nowe wymiary okna gry po jego rozszerzeniu
        	 */
            public void componentResized(ComponentEvent componentEvent) {
            	tlo.set_size(tlo.getWidth(),tlo.getHeight());
            	tlo.repaint();
            }
            
            public void componentMoved(ComponentEvent componentEvent) {
                // Does nothing.
            }
	    });
}
	
 /**
  *  Klasa implementujaca funkcjonalnosc przycisku newgame
  */
 private class newGame implements ActionListener {
		public void actionPerformed(ActionEvent e)
		{	
			if(okno_Startowe==null)
				okno_Startowe= new Okno_Startowe(this);
			okno_Startowe.setVisible(true);
		}	
	}
 
 /**
  * Klasa implementujaca funkcjonalnosc przycisku exit
  */
 private class Exit implements ActionListener {
		public void actionPerformed(ActionEvent e)
		{	
			System.exit(1);
		}
	}
	
 /**
  * Klasa implementujaca funkcjonalnosc przycisku pause
  */
private class Pause implements ActionListener {
		public void actionPerformed(ActionEvent e)
		{	
			tlo.pauzaAnimacji();
		}
	}
	
/**
 * Klasa implementujaca funkcjonalnosc przycisku highscores
 */
private class Wyniki implements ActionListener {
		public void actionPerformed(ActionEvent e)
		{	
			if(okno_Wynikow==null)
				try {
					okno_Wynikow= new Okno_Wynikow(this);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			okno_Wynikow.setVisible(true);
		}
}

	/**
	 * Klasa rozszerzajaca JDialog zawierajaca wybor nazwy gracza i wybor poziomu trudnosci
	 */
	public class Okno_Startowe extends JDialog  
	{
		private static final long serialVersionUID = 1L;
		private JLabel User, Poziom;
		private JTextField Name;
		private JButton ok, cancel;
		private JComboBox<String> poziom;
		
		public Okno_Startowe(newGame owner)
		{
			
			super();
			setTitle("Wprowadz imie i wybierz poziom");
			setSize(300,300);
			setLocationRelativeTo(null);
			setLayout(null);
			User = new JLabel("Imie: ",JLabel.CENTER);
			User.setBounds(0, 10, 100, 20);
			Name = new JTextField();
			Name.setBounds(90, 10,100,20);
			add(User);
			add(Name);
			ok = new JButton("ok");
			cancel = new JButton("cancel");
			ok.setBounds(50,200,60,30);
			add(ok);
			ok.addActionListener(new ok());
			cancel.setBounds(150,200,80,30);
			add(cancel);
			cancel.addActionListener(new cancle());
			poziom = new JComboBox<String>();
			poziom.setBounds(60,100,100,20);
			add(poziom);
			Poziom = new JLabel("Wybr poziomu trudnoci:", JLabel.CENTER);
			Poziom.setBounds(60,70,150,20);
			add(Poziom);
			
			Image imgIc = new ImageIcon("Images/ptak.png").getImage(); //ikona Gry
			setIconImage(imgIc);
			
			if(Parsujaca.getLiczbaStopniTrudnosci()==1)
			{
				poziom.addItem("Poziom 1");
			}
			
			if(Parsujaca.getLiczbaStopniTrudnosci()==2)
			{
				poziom.addItem("Poziom 1");
				poziom.addItem("Poziom 2");
			}
			
			if(Parsujaca.getLiczbaStopniTrudnosci()==3)
			{
				poziom.addItem("Poziom 1");
				poziom.addItem("Poziom 2");
				poziom.addItem("Poziom 3");
			}
			
			if(Parsujaca.getLiczbaStopniTrudnosci()==4)
			{
				poziom.addItem("Poziom 1");
				poziom.addItem("Poziom 2");
				poziom.addItem("Poziom 3");
				poziom.addItem("Poziom 4");
			}
			
			if(Parsujaca.getLiczbaStopniTrudnosci()==5)
			{
				poziom.addItem("Poziom 1");
				poziom.addItem("Poziom 2");
				poziom.addItem("Poziom 3");
				poziom.addItem("Poziom 4");
				poziom.addItem("Poziom 5");
			}
			
		}
		
		 /**
    	 * Metoda pobierajaca wpisany nick z JTextField
    	 */
		public String getNick()
		{
			return Name.getText();
			
		}
		
		 /**
    	 * Klasa zawierajaca ActionListener ktora rozpoczyna nasza gre
    	 */
		public class ok implements ActionListener {
			public void actionPerformed(ActionEvent e)
			{
				setVisible(false);
				menuPanel.newgame.setVisible(false);
				menuPanel.pause.setVisible(true);
				MenuNowe.Poziom.setText("Poziom: " + aktualny_poziom + "/" + Parsujaca.getLiczbaPoziomow());
				Sound.playSound("Start.wav");		
				starttime = System.currentTimeMillis();
				selected_item = poziom.getSelectedItem().toString();
				Tlo.setSleepTime();
				tlo.start();
				
				try
				{
			        FileWriter fw = new FileWriter("users.txt", true);
			        fw.write(Name.getText()+ " ");
			        fw.close();
			     
			    } catch (NumberFormatException e1){
			        System.out.println("Program nie dzia�a.");
			    } catch (IOException e1){
			        System.out.println("Program nie dzia�a.");
			    }
			}
			}
		
		 /**
    	 * Klasa zawierajaca ActionListener ktora zamyka okno JDialog
    	 */
		private class cancle implements ActionListener {
			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
			}
	}
	
	 /**
	 * Klasa rozszerzajaca JDialog tworzaca "Okno high scores"
	 */
	private class Okno_Wynikow extends JDialog
{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private JLabel a,b,c,d,f,g;
		
		public Okno_Wynikow(Wyniki owner) throws FileNotFoundException 
		{
			
			super(); 
			setSize(300,200);
			setLayout(null);
			setTitle("Najlepsze wyniki");
			setLocationRelativeTo(null);
			
			File file = new File("users.txt");
	 		Scanner in = new Scanner(file);
	 		String result = in.nextLine();
	 		String result2 = in.nextLine();
	 		String result3 = in.nextLine();
	 		String result4 = in.nextLine();
			
			a = new JLabel("Najlepsze wyniki:", JLabel.CENTER);
			a.setBounds(20,0,100,20);
			add(a);
			
			b = new JLabel("NICK:"+"  "+"TIME:"+"  "+"RESULT:", JLabel.CENTER);
			b.setBounds(20,20,200,20);
			add(b);
			
			c = new JLabel(result, JLabel.CENTER);
			c.setBounds(20,40,200,20);
			add(c);
			
			d = new JLabel(result2, JLabel.CENTER);
			d.setBounds(20,50,200,20);
			add(d);
			
			f = new JLabel(result3, JLabel.CENTER);
			f.setBounds(20,60,200,20);
			add(f);
			
			g = new JLabel(result4, JLabel.CENTER);
			g.setBounds(20,80,200,20);
			add(g);
		}	
}
	

	
public static void main(String[] args)
	    {
	            new GeneratorPlikuParametrycznegoGry();
	            new Parsujaca();
	            Gra nowa = new Gra();
	            nowa.setVisible(true);
	    }



public void actionPerformed(ActionEvent e) {
}

}
 
