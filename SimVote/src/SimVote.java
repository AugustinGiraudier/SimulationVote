import java.util.Vector;

import PGeneral.CResultScrutin;
import PGeneral.CSimulateur;
import PGeneral.EAlgoProximite;
import PGeneral.EScrutinType;

/**
 * Classe principale
 * @author Augustin Giraudier et Arthur Secher Cabot
 */
public class SimVote {
	/**
	 * Main
	 * @param args : arguments
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		
		
//		CActeur.CoefInteraction = 0.05;
//		CActeur.nbrAxesPrincipaux = 2;
//		CActeur.SeuilDisatnceAbstention = 0.70;
//		CActeur.SeuilProximiteActeurs = 0.3;
//		
//		Vector<CAxe> vec1 = new Vector<CAxe>();
//		vec1.add(new CAxe("test", 0.1));
//		vec1.add(new CAxe("test2", 0.8));
//		CActeur act1 = new CActeur("bernard", vec1);
//		
//		Vector<CAxe> vec2 = new Vector<CAxe>();
//		vec2.add(new CAxe("test", 0.8));
//		vec2.add(new CAxe("test2", 0.1));
//		CActeur act2 = new CActeur("didier", vec2);
//		
//		System.out.println(act1);
//		System.out.println(act2);
//		
//		System.out.println(act1.getDistance(act2, EAlgoProximite.DISTANCE_VECTEUR));
//		
//		act1.interact(act2, EAlgoProximite.DISTANCE_VECTEUR);
//		
//		System.out.println(act1);
//		System.out.println(act2);
		
		
		
//		CSimulateur sc = new CSimulateur(EAlgoProximite.DISTANCE_VECTEUR, EScrutinType.MAJORITAIRE_1_TOUR, "Config.json","Acteurs.json");
//		sc.Simuler();
//		
//		CSimulateur sc1 = new CSimulateur(EAlgoProximite.DISTANCE_VECTEUR, EScrutinType.MAJORITAIRE_2_TOURS, "Config.json","Acteurs.json");
//		sc1.Simuler();
//		
//		CSimulateur sc2 = new CSimulateur(EAlgoProximite.DISTANCE_VECTEUR, EScrutinType.BORDA, "Config.json","Acteurs.json");
//		sc2.Simuler();
//		
//		CSimulateur sc3 = new CSimulateur(EAlgoProximite.DISTANCE_VECTEUR, EScrutinType.ALTERNATIF, "Config.json","Acteurs.json");
//		sc3.Simuler();
//		Vector<CResultScrutin> res = sc3.sonder(50);
//		sc3.interactWithSondage(res);
//		sc3.Simuler();
//		
//		CSimulateur sc4 = new CSimulateur(EAlgoProximite.DISTANCE_VECTEUR, EScrutinType.ALTERNATIF, "Config.json","Acteurs.json");
//		sc4.Simuler();
		
		CSimulateur sc5 = new CSimulateur(EAlgoProximite.DISTANCE_VECTEUR, EScrutinType.ALTERNATIF, "Config.json", 4, 500);
		sc5.Simuler();
	}
}
