package PGeneral;

import java.util.Scanner;

import PExceptions.CFatalException;

public class CMenuTools {

	private CMenuTools() {};
	private static final int LARGEUR_MENU = 42;
	private static Scanner SC;
	
	
	private static void PrintHeader() {
		System.out.println("+----------------------------------------+");
		System.out.println("|            Simulation  Vote            |");
		System.out.println("|             Menu Principal             |");
		System.out.println("+----------------------------------------+");
	}
	
	private static void PrintTitle(String Title) {
		int a = (int)Math.floor((CMenuTools.LARGEUR_MENU - Title.length() - 6)/2);
		String strSpaces = "";
		for(int i_space = 0; i_space < a; i_space++)
			strSpaces += " ";
		System.out.println("+- " + strSpaces + Title + strSpaces + (Title.length()%2 == 0 ? "" : " ") + " -+");
		System.out.println("+----------------------------------------+");
	}
	private static String getUserChoice() throws CFatalException {
		if(CMenuTools.SC == null)
			CMenuTools.SC = new Scanner(System.in);
		
		System.out.println("Votre Choix : ");
		try {
			return SC.next();
		}
		catch(Exception e) {
			throw new CFatalException("Invalid user input");
		}
	}
	public static void Pause() {
		System.out.println("Appuyez sur Entrer pour continuer...");
		SC.nextLine();
		SC.nextLine();
	}
	
	public static EScrutinType PrintChoixScrutin() throws CFatalException {
		while(true) {
			PrintHeader();
			PrintTitle("Configurations");
			System.out.println("\nChoisissez votre mode de scrutin :\n");
			System.out.println("#1 - Scrutin Majoritaire 1 tour");
			System.out.println("#2 - Scrutin Majoritaire 2 tours");
			System.out.println("#3 - Scrutin par Approbation");
			System.out.println("#4 - Scrutin Alternatif");
			System.out.println("#5 - Scrutin de Borda");
			switch(getUserChoice()) {
			case "1":
				return EScrutinType.MAJORITAIRE_1_TOUR;
			case "2":
				return EScrutinType.MAJORITAIRE_2_TOURS;
			case "3":
				return EScrutinType.APPROBATION;
			case "4":
				return EScrutinType.ALTERNATIF;
			case "5":
				return EScrutinType.BORDA;
			default:
				break;
			}
		}
		
	}
	
	public static EAlgoProximite PrintChoixAlgo() throws CFatalException {
		while(true) {
			PrintHeader();
			PrintTitle("Configurations");
			System.out.println("\nChoisissez votre algorithme de proximité :\n");
			System.out.println("#1 - Distance de vecteurs");
			System.out.println("#2 - Somme des différences par axe");
			System.out.println("#3 - Somme des différences sur axes principaux");
			switch(getUserChoice()) {
			case "1":
				return EAlgoProximite.DISTANCE_VECTEUR;
			case "2":
				return EAlgoProximite.SOMME_DIFFERENCES;
			case "3":
				return EAlgoProximite.SOMME_DIFFERENCES_AXES_PRINCIPAUX;
			default:
				break;
			}
		}
		
	}
	
	public enum EChoixActeurs{
		ACTEURS_FICHIER,
		ACTEURS_ALEATOIRES
	}
	public static EChoixActeurs PrintChoixActeurs() throws CFatalException {
		while(true) {
			PrintHeader();
			PrintTitle("Configurations");
			System.out.println("\nChoisissez comment récupérer les différents acteurs :\n");
			System.out.println("#1 - Depuis le fichier JSON Acteurs.json");
			System.out.println("#2 - En les générant aléatoirement");
			switch(getUserChoice()) {
			case "1":
				return EChoixActeurs.ACTEURS_FICHIER;
			case "2":
				return EChoixActeurs.ACTEURS_ALEATOIRES;
			default:
				break;
			}
		}
		
	}
	
	public static class CActeursAlea{
		public int nbCandidats;
		public int nbElecteurs;
		public CActeursAlea(int nbCand, int nbElec) {
			nbCandidats = nbCand;
			nbElecteurs = nbElec;
		}
	}
	
	public static CActeursAlea BrancheActeursAleatoires() throws CFatalException {
		while(true) {
			PrintHeader();
			PrintTitle("Premieres Configurations");
			
			int nbCandidats = -1, nbElecteurs = -1;
			
			while(nbCandidats < 1) {
				System.out.println("\nCombiens de candidat(s) générer ?");
				try {
					nbCandidats = Integer.parseInt(getUserChoice());
				}
				catch(NumberFormatException e) {
					continue;
				}
			}
			
			while(nbElecteurs < 1) {
				System.out.println("\nCombiens d'Electeurs générer ?");
				try {
					nbElecteurs = Integer.parseInt(getUserChoice());
				}
				catch(NumberFormatException e) {
					continue;
				}
			}
			
			return new CActeursAlea(nbCandidats, nbElecteurs);
		}	
	}
	
	public enum EMainActions{
		SIMULATE,
		INFOS,
		SURVEY,
		INTERACTIONS,
		CHANGE_ALGO,
		CHANGE_BALLOT,
		CHANGE_ACTORS,
		QUIT
	}
	
	public static EMainActions PrintMainMenu() throws CFatalException {
		while(true) {
			PrintHeader();
			PrintTitle("Menu Principal");
			System.out.println("\nQue voulez vous faire ?\n");
			System.out.println("#1 - Infos Scrutin");
			System.out.println("#2 - Simuler le Scrutin");
			System.out.println("#3 - Sonder la population");
			System.out.println("#4 - Faire interagir la population");
			System.out.println("#5 - Changer d'algorithme");
			System.out.println("#6 - Changer de scrutin");
			System.out.println("#7 - Changer d'acteurs");
			System.out.println("#8 - Quitter");
			switch(getUserChoice()) {
			case "1":
				return EMainActions.INFOS;
			case "2":
				return EMainActions.SIMULATE;
			case "3":
				return EMainActions.SURVEY;
			case "4":
				return EMainActions.INTERACTIONS;
			case "5":
				return EMainActions.CHANGE_ALGO;
			case "6":
				return EMainActions.CHANGE_BALLOT;
			case "7":
				return EMainActions.CHANGE_ACTORS;
			case "8":
				return EMainActions.QUIT;
			default:
				break;
			}
		}
	}
	
	public static boolean BrancheInfos() throws CFatalException {
		while(true) {
			PrintHeader();
			PrintTitle("Choix Infos");
			System.out.println("\nVoulez vous un résumé des candidats ?\n");
			System.out.println("#1 - oui");
			System.out.println("#2 - non");
			switch(getUserChoice()) {
			case "1":
				return true;
			case "2":
				return false;
			default:
				break;
			}
		}
	}
	
	public static int BrancheSondage() throws CFatalException {
		while(true) {
			PrintHeader();
			PrintTitle("Choix sondage");
			System.out.println("\nQuel pourcentage de la population voulez vous sonder ?\n");
			int percent;
			try {
				percent = Integer.parseInt(getUserChoice());
			}
			catch(NumberFormatException e) {
				continue;
			}
			if(percent < 0 || percent > 100)
				continue;
			return percent;
		}
	}
	public static boolean BrancheSondageInteractions() throws CFatalException {
		while(true) {
			PrintHeader();
			PrintTitle("Choix sondage");
			System.out.println("\nVoulez vous que la population interagisse avec ce sondage ?\n");
			System.out.println("#1 - oui");
			System.out.println("#2 - non");
			switch(getUserChoice()) {
			case "1":
				return true;
			case "2":
				return false;
			default:
				break;
			}
		}
	}
	
	public static int BrancheInteractions() throws CFatalException {
		while(true) {
			PrintHeader();
			PrintTitle("Choix nombre interactions");
			System.out.println("\nCombien d'interactions voulez vous générer ?\n");
			int nbrInter;
			try {
				nbrInter = Integer.parseInt(getUserChoice());
			}
			catch(NumberFormatException e) {
				continue;
			}
			if(nbrInter < 0)
				continue;
			return nbrInter;
		}
	}
	
}
