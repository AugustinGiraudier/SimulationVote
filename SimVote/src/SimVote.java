import java.util.Vector;

import PExceptions.CFatalException;
import PGeneral.CMenuTools;
import PGeneral.CMenuTools.CActeursAlea;
import PGeneral.CResultScrutin;
import PGeneral.CSimulateur;
import PGeneral.EAlgoProximite;
import PGeneral.EScrutinType;

/**
 * Classe principale.
 * 
 * Simulaion de scrutins via interface textuelle.
 * 
 * @author Augustin Giraudier et Arthur Secher Cabot
 */
public class SimVote {
	/**
	 * chemin d'accès au fichier json de configuration
	 */
	private static String CONFIG_PATH = "Config.json";
	/**
	 * chemin d'accès au fichier json de liste des acteurs
	 */
	private static String ACTORS_PATH = "Acteurs.json";
	
	/**
	 * Main
	 * @param args [config_json_file, actors_json_file]
	 */
	public static void main(String[] args) {
		
		// récupération des arguments du programme :
		// 1er arg : config json path
		// 2nd arg : acteurs json path
		if(args.length > 0)
			CONFIG_PATH = args[0];
		if(args.length > 1)
			ACTORS_PATH = args[1];
			
		try {
			// simulateur principal
			CSimulateur sim;
			
			// Debut Interface textuelle :
			
			EScrutinType scrutinType = CMenuTools.PrintChoixScrutin();
			EAlgoProximite algo = CMenuTools.PrintChoixAlgo();
			
			// création du scrutin en fonction des choix utilisateur :
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
			
			/////// Menu principal ///////
			
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
			
			// Si une exception fatale remonte au main, arret du programme
			
			System.out.println("A fatal error has occurred...");
			e.printStackTrace();
		}
	}
}
