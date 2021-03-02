package projekt;

import java.awt.*;
import javax.swing.*;

/**
 *  Klasa rozszerzajaca JPanel tworzaca menu gorne i przyciski
 */
public class MenuNowe extends JPanel 
{

		private static final long serialVersionUID = 1L;
		
		protected JButton newgame;
		protected JButton highscores;
		protected JButton exit;
		protected JButton pause;
		protected static JLabel Score_;
		protected static JLabel Poziom;
		
		MenuNowe()
		{
			setBackground(new Color(211,225,239));
			setLayout(new GridLayout(2,4,0,0));
			String nazwaGry=Parsujaca.nazwaGry;
			
			nazwaGry=nazwaGry.substring(0, nazwaGry.indexOf('[')); //skrócenie nazwy bez danych osobowych
			
			ImageIcon exitIcon = setIcon("Images/exit.png");
			ImageIcon highScoresIcon = setIcon("Images/high scores.png");
			ImageIcon NewGameIcon = setIcon("Images/resume.png");
			ImageIcon pauseIcon = setIcon("Images/pause.png");
			
			JLabel Wynik_ = new JLabel("Wynik: ");
			Score_ = new JLabel(Integer.toString(Gra.wynik));
			Score_.setForeground(Color.RED);
			Wynik_.setForeground(Color.RED);
			Poziom = new JLabel("Poziom: " + Gra.aktualny_poziom + "/" + Parsujaca.getLiczbaPoziomow());
			Poziom.setForeground(Color.RED);
			newgame=new JButton(NewGameIcon);
			highscores=new JButton(highScoresIcon);
			exit=new JButton(exitIcon);
			pause=new JButton(pauseIcon);
			pause.setVisible(false);
			
			
			add(newgame);
			add(pause);
			add(highscores);
			add(exit);
			add(Wynik_);
			add(Score_);
			add(Poziom);
			
			setVisible(true);
		}
		
		/**
		 *  Metoda ustawiajaca ikony
		 */
		private ImageIcon setIcon(String path) {
			try {
				ImageIcon icon = new ImageIcon(path);
				return icon;
			} catch (java.lang.NullPointerException e) {
				return null;
			}
		}
		
		/**
		 *  Metoda statyczna ustawiajaca wyswietlanie wyniku
		 */
		 static void setWynik()
		{
			Score_.setText(Integer.toString(Gra.wynik));
		}
		 
		 /**
		  * Metoda statyczna ustawiajaca wyswietlanie poziomu
		  */
		 static void setPoziom()
		 {
			 Poziom.setText("Poziom: " + Gra.aktualny_poziom + "/" + Parsujaca.getLiczbaPoziomow());
		 }
}




