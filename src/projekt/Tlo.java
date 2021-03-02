package projekt;


import static java.awt.Toolkit.getDefaultToolkit;

import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.io.FileWriter;
import java.io.IOException;

/**
 *  Klasa rozszerzajaca JPanel tworzaca tlo i obiekty gry
 */

public class Tlo extends JPanel implements Runnable 
{
	private static final long serialVersionUID = 1L;
	
	/** Tablica przechowywujaca obiekty Gry */
	private ArrayList<GameObject> tablicaObiektowGry = new ArrayList<GameObject>();
	
	/**Zmienna informujaca o liczbie obiektow */
	protected int liczbaObiektowGry;
	
	boolean stanGry;		
	private boolean wynik_koncowy;
	boolean stanPauzy;
	public Image img;
	
	/**Zmienna statyczna przechowujaca informacje o aktualnych wymiarach x okna */
	static int x_resized;
	
	/**Zmienna statyczna przechowujaca informacje o aktualnych wymiarach y okna */
	static int y_resized;

	/**Zmienna statyczna przechowujaca informacje o pomocniczym wyniku koncowym */
	static double end_score;
	
	/**Zmienna statyczna przechowujaca informacje o wyniku koncowym*/
	static int end_score_;
	
	/**Zmienna statyczna przechowujaca informacje o czasie trwania gry */
	static double elapsedTime;
	
	Thread thread=null;
	BufferStrategy strategy;

	/**Zmienna statyczna przechowujaca informacje o czasie usypiania animacji */
	private static int sleepTime;
	
	/**Zmienna statyczna przechowujaca informacje o wspolczynniku poziomu trudnosci */
	private static int difficulty_coefficient;
	
	public Tlo()
	{
		super();

		x_resized=Parsujaca.getPoczatkowaSzerokoscPlanszy();
		y_resized=Parsujaca.getPoczatkowaWysokoscPlanszy()-120;
		stanPauzy=false;
		stanGry=false;
		
		liczbaObiektowGry=5;
				
		/** Dodanie obiektow do tablicy */
		for (int i=0; i < liczbaObiektowGry; i++){
	    	  tablicaObiektowGry.add(new GameObject());
	    	  }
		
		img = new ImageIcon("Images/" + Parsujaca.getPlikTla()).getImage();
		
		setDoubleBuffered(true);
		
		/** Ustawienie celownika jako kursor */
		Toolkit tk = getDefaultToolkit();
		Image img = tk.getImage("Images/kursor.PNG");
		Cursor myCursor = tk.createCustomCursor(img, new Point(10, 10), "celownik");
		setCursor(myCursor);
		
		addMouseListener(new MouseHandler());
		
		setTlo();
		
	}
	
	 /**
	  *  Metoda nadajace nowe wartosci wymiarom planszy
	  */
public void set_size(int x_, int y_)
	{
		x_resized=x_;
		y_resized=y_;
		GameObject.szerokosc_obiektu = (int)Parsujaca.getPoczatkowaSzerokoscObiektuGryJakoProcentPoczatkowejSzerokosciPlanszy()*x_resized/100;
}
	
	/**
 	*  Metoda ustawiajaca kolor tla
 	*/
	public void setTlo()
	{
		if(Parsujaca.getTlo().equals("jednolite"))
		{
		this.setBackground(new Color(Parsujaca.getKolorTla(0),Parsujaca.getKolorTla(1),Parsujaca.getKolorTla(2)));
		}
		
	}
	
	 /**
	  *  Metoda uaktywniajca gre
	  */
	public void setStanGry(boolean czyWlanczona) {				
		stanGry=czyWlanczona;
		repaint();
	}
	
	 /**
	  *  Metoda ustawiajaca czas usypiania zaleznie od wybranego poziomu trudnosci
	  */
	static void setSleepTime()
	{
		if(Gra.selected_item.equals("Poziom 1"))
		{
			difficulty_coefficient=1;
			sleepTime=30-1*(((int)Parsujaca.getZmianaStopniaTrudnosci()/10));
		}
		if(Gra.selected_item.equals("Poziom 2"))
		{
			difficulty_coefficient=2;
			sleepTime=30-2*(((int)Parsujaca.getZmianaStopniaTrudnosci()/10));
		}
		if(Gra.selected_item.equals("Poziom 3"))
		{
			difficulty_coefficient=3;
			sleepTime=30-3*(((int)Parsujaca.getZmianaStopniaTrudnosci()/10));
		}
		if(Gra.selected_item.equals("Poziom 4"))
		{
			difficulty_coefficient=4;
			sleepTime=30-4*(((int)Parsujaca.getZmianaStopniaTrudnosci()/10));
		}
		if(Gra.selected_item.equals("Poziom 5"))
		{
			difficulty_coefficient=5;
			sleepTime=30-5*(((int)Parsujaca.getZmianaStopniaTrudnosci()/10));
		}
	}
	
	 /**
	  *  Metoda rozpoczynajaca watek
	  */
	public void start()
	{
		setStanGry(true);
	  if (thread == null) {
	    thread = new Thread(this);
	    thread.start();
	  }
	}

	/**
	  *  Metoda konczaca watek
	  */
	public void stop()
	{
		setStanGry(false);
	  if (thread != null){
	    thread = null;
	 }
	}

	/**
	  *  Metoda zatrzymujaca watek
	  */
	public void pauza()
	{
		try {
			thread.wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	  *  Metoda wznawiajaca watek
	  */
	public void wznowienie()
	{
		thread.notify();
	}
	
	/**
	  *  Metoda runnable: przemieszcza obiekty, usypia, repaintuje
	  */
	public void run()
	{
	  while(thread!=null)
	  {
		  for (int i=0; i<tablicaObiektowGry.size(); i++){
	    	  tablicaObiektowGry.get(i).move();
		  }
		  repaint();
		  try {
		      Thread.sleep(sleepTime);
	            }
		catch(InterruptedException e){}
	  }
	}

	/**
	  *  Metoda sluzaca do usuwania obiektow
	  */

	public void usunObiekt(int x,int y) {
		
		int wymiaryObiektu= GameObject.szerokosc_obiektu;		
		
		if(stanPauzy!=true) {
		for (int i=0; i<tablicaObiektowGry.size(); i++){
			
	        if( x >= tablicaObiektowGry.get(i).getX() && y >= tablicaObiektowGry.get(i).getY() &&  x <= (tablicaObiektowGry.get(i).getX()+wymiaryObiektu) && y <= (tablicaObiektowGry.get(i).getY()+wymiaryObiektu)) {
	        	Sound.playSound("Gun.wav");
	        	tablicaObiektowGry.remove(i);
	        	liczbaObiektowGry--;
	        	Gra.wynik=Gra.wynik+1;
	        	System.out.println(Gra.wynik);
	        	MenuNowe.setWynik();
	 	        repaint();
	 	        set_lvl();
	        	}
	    }}   
}
	
	/**
	  *  Metoda rysujaca tlo, obiekty oraz wynik koncowy
	  */
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		if(Parsujaca.getTlo().equals("plikGraficzny"))
		{
		g.drawImage(img, 0, 0, x_resized, y_resized, null);
		}
		
		if(stanGry==true) {
			for (int i=0; i<tablicaObiektowGry.size(); i++){
		        (tablicaObiektowGry.get(i)).paint(g);
			}
		}
		if(wynik_koncowy==true)
		{
			g.setColor(Color.red);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
			g.drawString("KONIEC GRY", 0, 100);
			g.drawString("Czas ukonczenia: "  +  elapsedTime + "s", 0, 200);
			g.drawString("Wynik koncowy: " + end_score_, 0, 300);
		}
	}
	
	/**
	  *  Metoda czyszczaca tablice obiektow i dodajaca zadana ilosc obiektow do niej
	  */
public void ResetObiektowGry(int liczbaObiektow) {

        tablicaObiektowGry.clear();
	 for (int i=0; i < liczbaObiektow; i++){
    	  tablicaObiektowGry.add(new GameObject());
    	  }
	 liczbaObiektowGry=liczbaObiektow;
}

/**
 *  Metoda pauzujaca animacje
 */
public void pauzaAnimacji() {
	if(stanPauzy==false) {
		stanPauzy=true;
		
		for (int i=0; i<tablicaObiektowGry.size(); i++){		
	     	tablicaObiektowGry.get(i).pauza();
	    }    
		
	}else {
		stanPauzy=false;
		
		for (int i=0; i<tablicaObiektowGry.size(); i++){		
	     	tablicaObiektowGry.get(i).reasume();
	    }
		
	}
}
/**
 *  Metoda tworzaca nowy pzoiom
 */
public void set_lvl()
{
	if(Gra.wynik==5)
	{
		Gra.aktualny_poziom=1;
		MenuNowe.setPoziom();
		ResetObiektowGry(10);
		start();
	}
	if(Gra.wynik==15)
	{
		Gra.aktualny_poziom=2;
		MenuNowe.setPoziom();
		ResetObiektowGry(15);
		start();
	}
	if(Gra.wynik==30)
	{
		Gra.aktualny_poziom=3;
		MenuNowe.setPoziom();
		ResetObiektowGry(20);
		start();
	}
	if(Gra.wynik==50)
	{
		Gra.aktualny_poziom=4;
		MenuNowe.setPoziom();
		ResetObiektowGry(25);
		start();
	}
	if(Gra.wynik==75)
	{
		Gra.aktualny_poziom=5;
		MenuNowe.setPoziom();
		ResetObiektowGry(30);
		start();
	}
	else if(Gra.wynik==105)
	{
		end_game();
	}
}

/**
 *  Metoda konczaca gre i ustanawiajaca wynik koncowy
 */
public void end_game()
{
	wynik_koncowy = true;
	repaint();
	elapsedTime = (double) (System.currentTimeMillis() - Gra.starttime)/1000;
	System.out.println(elapsedTime);
	end_score = (double)(1/elapsedTime)*Gra.wynik*10000*difficulty_coefficient;;
	end_score_ = (int)end_score;
	System.out.println(end_score);
	zapis();
}

/**
 *  Metoda zapisujaca wynik koncowy do pliku
 */
public void zapis()
{
	try
	{
		FileWriter fw = new FileWriter("users.txt", true);
        fw.write(elapsedTime + " ");
        fw.write(end_score_ + "\n");
        fw.close();
        
    } catch (NumberFormatException e1){
        System.out.println("Program nie dzia�a.");
    } catch (IOException e1){
        System.out.println("Program nie dzia�a.");
    }
}

/**
 *  Metoda dodajaca obiekt do tablicy
 */
public void dodaj() {								
	tablicaObiektowGry.add(new GameObject());
}

/**
 *  Klasa rozszerzajaca MouseAdapter
 */
private class MouseHandler extends MouseAdapter{
	
	public void mousePressed(MouseEvent event){}
	
	/**
	 *  Metoda wywolujaca metode usunObiekt dla miejsca gdzie kliknelismy myszka
	 */
	public void mouseClicked(MouseEvent event){
		System.out.println("Mouse clicked");	
		usunObiekt(event.getX(),event.getY());
	}
}

}


