package projekt;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

/**
 * Klasa parsuj¹ca plik tekstowy
 * @author Damian £ysomirski Seweryn Tkacz
 *
 */

public class Parsujaca {
	private final PIN pin = PIN.PIN;
	
	static	int	kolorTla[];
	static	int liczbaPoziomow;
	static	int	numeracjaPoziomowZaczynaSieOd;
	static	int poczatkowaWysokoscPlanszy;
	static	double poczatkowaSzerokoscObiektuGryJakoProcentPoczatkowejSzerokosciPlanszy;
	static	int poczatkowaSzerokoscPlanszy;	
	static	int liczbaStopniTrudnosci;
	static	int zmianaStopniaTrudnosci;
	static	String rozszerzeniePlikuZOpisemPoziomu;
	static	String tlo;
	static	String nazwaGry;
	static	String figuraObiektuGry;
	static	String obiektyGry;
	static	String nazwaBazowaPlikuZOpisemPoziomu;
	static 	String plikTla;
	static  String plikObiektu;

	
	static int getKolorTla(int i) {
		return kolorTla[i];
		}
	
	static int getNumeracjaPoziomowZaczynaSieOd() {
		return numeracjaPoziomowZaczynaSieOd;
		}
	
	static int getLiczbaPoziomow() {
		return liczbaPoziomow;
		}
	
	static int getPoczatkowaWysokoscPlanszy() {
		return poczatkowaWysokoscPlanszy;
		}
	
	static double getPoczatkowaSzerokoscObiektuGryJakoProcentPoczatkowejSzerokosciPlanszy() {
		return poczatkowaSzerokoscObiektuGryJakoProcentPoczatkowejSzerokosciPlanszy;
		}
	
	static int getPoczatkowaSzerokoscPlanszy() {
		return poczatkowaSzerokoscPlanszy;
		}
	
	static int getLiczbaStopniTrudnosci() {
		return liczbaStopniTrudnosci;
		}
	
	static int getZmianaStopniaTrudnosci() {
		return zmianaStopniaTrudnosci;
		}
	
	static String getFiguraObiektuGry() {
		return figuraObiektuGry;
		}
	
	static String getObiektyGry() {
		return obiektyGry;
		}
	
	static String getTlo() {
		return tlo;
		}
	
	static String getNazwaGry() {
		return nazwaGry;
		}
	
	static String getNazwaBazowaPlikuZOpisemPoziomu() {
		return nazwaBazowaPlikuZOpisemPoziomu;
		}
	
	static String getPlikTla() {
		return plikTla;
		}
	
	static String getPlikObiektu() {
		return plikObiektu;
		}
	
	static String getRozszerzeniePlikuZOpisemPoziomu() {
		return rozszerzeniePlikuZOpisemPoziomu;
		}
	

	 public Parsujaca()
		{
		
		Properties props = new Properties();
		
		try (Reader r = new BufferedReader(new FileReader("par.txt"))) {
			props.load(r);
		} catch (FileNotFoundException fnfe) {
			pin.wypiszKomunikatyIZakoncz("Nie znaleziono pliku parametrycznego",
					"par.txt", fnfe); 
		} catch (IOException ioe) {
			pin.wypiszKomunikatyIZakoncz("Wystapil blad odczytu pliku parametrycznego",
					"par.txt", ioe);
		}

		numeracjaPoziomowZaczynaSieOd=Integer.parseInt(props.getProperty("numeracjaPoziomowZaczynaSieOd"));
		poczatkowaWysokoscPlanszy=Integer.parseInt(props.getProperty("poczatkowaWysokoscPlanszy"));
		tlo=props.getProperty("tlo");
				
		
		if(tlo.equals("jednolite")) 
			
		{
			String[] temp = (props.getProperty("klorTla").split(" "));
			
			kolorTla= new int[temp.length];
			for(int i=0; i<temp.length;i++)
			{
				kolorTla[i]=Integer.parseInt(temp[i]);
			}
		}
		
		
		if(tlo.equals("plikGraficzny")) {
			plikTla=props.getProperty("plikTla");
		}
		
		obiektyGry=props.getProperty("obiektyGry");
		
		if(obiektyGry.equals("figuryGeometryczne")) {
			figuraObiektuGry=props.getProperty("figuraObiektuGry");
		}
				
		if(obiektyGry.equals("plikGraficzny")) {
			plikObiektu=props.getProperty("plikObiektu");
		}
		
		poczatkowaSzerokoscObiektuGryJakoProcentPoczatkowejSzerokosciPlanszy=Double.parseDouble(props.getProperty("poczatkowaSzerokoscObiektuGryJakoProcentPoczatkowejSzerokosciPlanszy"));
		rozszerzeniePlikuZOpisemPoziomu=props.getProperty("rozszerzeniePlikuZOpisemPoziomu");
		poczatkowaSzerokoscPlanszy=Integer.parseInt(props.getProperty("poczatkowaSzerokoscPlanszy"));
		liczbaPoziomow=Integer.parseInt(props.getProperty("liczbaPoziomow"));
		nazwaGry=props.getProperty("nazwaGry");
		liczbaStopniTrudnosci=Integer.parseInt(props.getProperty("liczbaStopniTrudnosci"));
		zmianaStopniaTrudnosci=Integer.parseInt(props.getProperty("zmianaStopniaTrudnosci"));
		nazwaBazowaPlikuZOpisemPoziomu=props.getProperty("nazwaBazowaPlikuZOpisemPoziomu");
		}

	 public static void main(String[] args)
	{
	}
	
}

