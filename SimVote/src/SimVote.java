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
		
		CSimulateur sc2 = new CSimulateur(EAlgoProximite.DISTANCE_VECTEUR, EScrutinType.BORDA, "Config.json","Acteurs.json");
		sc2.Simuler();
	}
}
