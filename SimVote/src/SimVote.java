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
		
		CSimulateur sc = new CSimulateur(EAlgoProximite.DISTANCE_VECTEUR, EScrutinType.MAJORITAIRE_1_TOUR, "Config.json","Acteurs.json");
		sc.Simuler();
		
		CSimulateur sc1 = new CSimulateur(EAlgoProximite.DISTANCE_VECTEUR, EScrutinType.MAJORITAIRE_2_TOURS, "Config.json","Acteurs.json");
		sc1.Simuler();
		
		CSimulateur sc2 = new CSimulateur(EAlgoProximite.DISTANCE_VECTEUR, EScrutinType.BORDA, "Config.json","Acteurs.json");
		sc2.Simuler();
		
		CSimulateur sc3 = new CSimulateur(EAlgoProximite.DISTANCE_VECTEUR, EScrutinType.APPROBATION, "Config.json","Acteurs.json");
		sc3.Simuler();
		
		CSimulateur sc4 = new CSimulateur(EAlgoProximite.DISTANCE_VECTEUR, EScrutinType.ALTERNATIF, "Config.json","Acteurs.json");
		sc4.Simuler();
	}
}
