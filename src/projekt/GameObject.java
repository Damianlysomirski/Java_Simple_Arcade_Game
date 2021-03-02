package projekt;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *  Klasa sluzaca do tworzenia obiektu gry
 */
public class GameObject{

    //member data
    private int x,y,xvel=2,yvel=2;
    private Color color;
    public Image img;
    private boolean zatrzymajObiekt;

    static int DEFAULT_WIDTH;
	static int DEFAULT_HEIGHT;
	static int szerokosc_obiektu;

  public GameObject(){
	  
	  	new Parsujaca();
	  	DEFAULT_HEIGHT=(int)Parsujaca.getPoczatkowaWysokoscPlanszy()-150;	
		DEFAULT_WIDTH=(int)Parsujaca.getPoczatkowaSzerokoscPlanszy()-45;		
	  
        x = (int) (Math.random( )*DEFAULT_WIDTH);
        y = (int) (Math.random( )*DEFAULT_HEIGHT);

        int R = (int) (Math.random( )*256);
        int G = (int)(Math.random( )*256);
        int B= (int)(Math.random( )*256);

        color = new Color (R, G, B);
        img = new ImageIcon("Images/ptak.png").getImage();
        zatrzymajObiekt=false;
    }
  
  /**
	 *  Metoda zmieniajaca wartosc zmiennej zatrzymajObiekt
	 */
  public void pauza() {
		zatrzymajObiekt=true;
	}
	
  	/**
	 *  Metoda zmieniajaca wartosc zmiennej zatrzymajObiekt
	 */
	public void reasume() {
		zatrzymajObiekt=false;
	}
	

	/**
	 *  Metoda sluzaca do przemieszczania obiektow
	 */
 public void move(){
	 if(zatrzymajObiekt==false) {
        x += xvel;
        y += yvel;

        if(x<1)
        {
            xvel = 1;
        }

        else if(y<1)
        {
            yvel = 1;
        }

        else if(x>=Tlo.x_resized-szerokosc_obiektu)
        {
            xvel = -1;
        }

        else if(y>=Tlo.y_resized-szerokosc_obiektu)
        {
            yvel = -1;
        }
	 }
}

 /**
  *  Metoda rysujaca obiekt w zaleznosci od pliku konfiguracyjnego
  */
public void paint(Graphics g){
	if(Parsujaca.getObiektyGry().equals("plikGraficzny"))
	{	
		g.setColor(color);
		g.drawImage(img, x, y, szerokosc_obiektu, szerokosc_obiektu, null);
	}
	else if(Parsujaca.getFiguraObiektuGry().equals("kwadraty"))
	{	
		g.setColor(color);
		g.fillRect(x, y, szerokosc_obiektu, szerokosc_obiektu);
	}
	else if(Parsujaca.getFiguraObiektuGry().equals("prostokaty"))
	{	
		g.setColor(color);
		g.fillRect(x, y, szerokosc_obiektu, szerokosc_obiektu);
	}
	else if(Parsujaca.getFiguraObiektuGry().equals("trojkaty"))
	{ 
		g.setColor(color);
		int x1[]={x,x+(szerokosc_obiektu/2),x+szerokosc_obiektu};
		int y1[]={y,y+szerokosc_obiektu,y};
		g.fillPolygon(x1, y1, 3);
		
	}
	else if(Parsujaca.getFiguraObiektuGry().equals("kolka"))
	{
    g.setColor(color);
    g.fillOval(x, y, szerokosc_obiektu, szerokosc_obiektu);
	}
}


/**
 *  Metoda zwracajaca wspolrzedna x obiektu
 */
public int getX() {
	return x;
}

/**
 *  Metoda zwracajaca wspolrzedna y obiektu
 */
public int getY() {
	return y;
}

}