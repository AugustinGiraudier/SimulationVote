import java.util.Vector;

import PExceptions.CFatalException;
import PGeneral.CMenuTools;
import PGeneral.CMenuTools.CActeursAlea;
import PGeneral.CResultScrutin;
import PGeneral.CSimulateur;
import PGeneral.EAlgoProximite;
import PGeneral.EScrutinType;

/**
 * Classe principale
 * @author Augustin Giraudier et Arthur Secher Cabot
 */
public class SimVote {
	
	private static String CONFIG_PATH = "Config.json";
	private static String ACTORS_PATH = "Acteurs.json";
	
	/**
	 * Main
	 * @param args : [config_json_file, actors_json_file]
	 * @throws Exception
	 */
	public static void main(String[] args) {
		
		if(args.length > 0)
			CONFIG_PATH = args[0];
		if(args.length > 1)
			ACTORS_PATH = args[1];
			
		try {
			EScrutinType scrutinType = CMenuTools.PrintChoixScrutin();
			EAlgoProximite algo = CMenuTools.PrintChoixAlgo();
			
			CSimulateur sim;
		
			switch(CMenuTools.PrintChoixActeurs()) {
			case ACTEURS_ALEATOIRES:
				CActeursAlea infosActeurs = CMenuTools.BrancheActeursAleatoires();
				sim = new CSimulateur(algo, scrutinType, CONFIG_PATH, infosActeurs.nbCandidats, infosActeurs.nbElecteurs);
				break;
			case ACTEURS_FICHIER:
				sim = new CSimulateur(algo, scrutinType, CONFIG_PATH, ACTORS_PATH);
				break;
			default:
				throw new CFatalException("Invalid user input...");
			}
			
			boolean bQuit = false;
			
			while(!bQuit) {
				switch(CMenuTools.PrintMainMenu()) {
				case INFOS:
					sim.PrintInfos();
					CMenuTools.Pause();
					if(CMenuTools.BrancheInfos()) {
						sim.PrintCandidates();
						CMenuTools.Pause();
					}
					
					break;
				case SIMULATE:
					sim.Simuler();
					CMenuTools.Pause();
					break;
				case SURVEY:
					int popProp = CMenuTools.BrancheSondage();
					boolean interactions = CMenuTools.BrancheSondageInteractions();
					
					if(interactions) {
						System.out.println("---- Visualisation de la population avant Sondage ----");
						sim.DisplayResults(sim.sonder(100));
						System.out.println("------------------------------------------------------");
						System.out.println("---- Résultat sondage sur " + Integer.toString(popProp) + "% de la population ----");
						Vector<CResultScrutin> surveyResults = sim.sonder(popProp);
						sim.DisplayResults(surveyResults);
						sim.interactWithSondage(surveyResults);
						System.out.println("---------------------------------------------------");
						System.out.println("---- Visualisation de la population apres Sondage ----");
						sim.DisplayResults(sim.sonder(100));
						System.out.println("------------------------------------------------------");
					}
					else {
						System.out.println("---- Résultat sondage sur " + Integer.toString(popProp) + "% de la population ----");
						sim.DisplayResults(sim.sonder(popProp));
						System.out.println("---------------------------------------------------");
					}
					CMenuTools.Pause();
					break;
				case INTERACTIONS:
					int nbInteraction = CMenuTools.BrancheInteractions();
					System.out.println("---- Visualisation de la population avant Interaction ----");
					sim.DisplayResults(sim.sonder(100));
					System.out.println("----------------------------------------------------------");
					sim.interact(nbInteraction);
					System.out.println("---- Visualisation de la population après Interaction ----");
					sim.DisplayResults(sim.sonder(100));
					System.out.println("----------------------------------------------------------");
					CMenuTools.Pause();
					break;
				case CHANGE_ALGO:
					sim.changeAlgo(CMenuTools.PrintChoixAlgo());
					break;
				case CHANGE_BALLOT:
					sim.changeScrutin(CMenuTools.PrintChoixScrutin());
					break;
				case CHANGE_ACTORS:
					switch(CMenuTools.PrintChoixActeurs()) {
					case ACTEURS_ALEATOIRES:
						CActeursAlea infosActeurs = CMenuTools.BrancheActeursAleatoires();
						sim = new CSimulateur(algo, scrutinType, CONFIG_PATH, infosActeurs.nbCandidats, infosActeurs.nbElecteurs);
						break;
					case ACTEURS_FICHIER:
						sim = new CSimulateur(algo, scrutinType, CONFIG_PATH, ACTORS_PATH);
						break;
					default:
						throw new CFatalException("Invalid user input...");
					}
					break;
				case QUIT:
					bQuit = true;
					break;
				default:
					throw new CFatalException("Invalid user input...");
				}
			}
		}
		catch(CFatalException e) {
			System.out.println("A fatal error has occurred...");
			e.printStackTrace();
		}
		
	}
}
