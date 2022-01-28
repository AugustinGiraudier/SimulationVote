package PGeneral;

import java.util.Scanner;

import PExceptions.CFatalException;

/**
 * Classe regroupant les methodes statiques utiles pour afficher le menu 
 * et pour les decisions utilisateur
 * @author Augustin Giraudier et Arthur Secher Cabot
 *
 */
public class CMenuTools {

	private CMenuTools() {};
	private static final int LARGEUR_MENU = 42;
	private static Scanner SC;
	
	/**
	 * Enum des manieres de chosir les acteurs
	 * @author Augustin Giraudier et Arthur Secher Cabot
	 *
	 */
	public enum EChoixActeurs{
		/**
		 * Acteurs g�n�r�s avec le fichier Json
		 */
		ACTEURS_FICHIER,
		/**
		 * Acteurs g�n�r�s al�atoirement
		 */
		ACTEURS_ALEATOIRES
	}
	
	/**
	 * Classe conservant les donn�es pour g�n�rer des acteurs al�atoires
	 * @author Augustin Giraudier et Arthur Secher Cabot
	 *
	 */
	public static class CActeursAlea{
		/**
		 * nombre de candidats � cr�er
		 */
		public int nbCandidats;
		/**
		 * nombre d'�lecteurs � cr�er
		 */
		public int nbElecteurs;
		/**
		 * Cr�e un nouvel indice d'acteurs al�atoires
		 * @param nbCand nombre de candidats
		 * @param nbElec nombre d'electeurs
		 */
		public CActeursAlea(int nbCand, int nbElec) {
			nbCandidats = nbCand;
			nbElecteurs = nbElec;
		}
	}
	
	/**
	 * Enum repr�sentant les diff�rentes branches du menu principal
	 * @author Augustin Giraudier et Arthur Secher Cabot
	 *
	 */
	public enum EMainActions{
		/** Simuler un scrutin */
		SIMULATE,
		/** Afficher les infos */
		INFOS,
		/** R�aliser un sondage */
		SURVEY,
		/** G�n�rer des interactions */
		INTERACTIONS,
		/** Changer l'algo de proximit� */
		CHANGE_ALGO,
		/** Changer de scrutin */
		CHANGE_BALLOT,
		/** Changer les acteurs */
		CHANGE_ACTORS,
		/** Quitter l'application*/
		QUIT
	}
	
	/**
	 * Affiche le header du menu
	 */
	private static void PrintHeader() {
		System.out.println("+----------------------------------------+");
		System.out.println("|            Simulation  Vote            |");
		System.out.println("|             Menu Principal             |");
		System.out.println("+----------------------------------------+");
	}
	
	/**
	 * Affiche un titre centr� par rapport au header
	 * @param Title titre � afficher
	 */
	private static void PrintTitle(String Title) {
		int a = (int)Math.floor((CMenuTools.LARGEUR_MENU - Title.length() - 6)/2);
		String strSpaces = "";
		for(int i_space = 0; i_space < a; i_space++)
			strSpaces += " ";
		System.out.println("+- " + strSpaces + Title + strSpaces + (Title.length()%2 == 0 ? "" : " ") + " -+");
		System.out.println("+----------------------------------------+");
	}
	
	/**
	 * Demande une entr�e clavier � l'utilisateur et renvoie son choix
	 * @return l'input de l'utilisateur
	 * @throws CFatalException si erreur d'input
	 */
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
	
	/**
	 * G�nere une pause d'affichage ou l'utilisateur doit appuyer sur entrer pour continuer
	 */
	public static void Pause() {
		System.out.println("Appuyez sur Entrer pour continuer...");
		SC.nextLine();
		SC.nextLine();
	}
	
	/**
	 * Demande de choisir un scrutin
	 * @return le scrutin choisi
	 * @throws CFatalException /
	 */
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
	
	/**
	 * Demande de choisir un algo de proximit�
	 * @return l'algo choisi
	 * @throws CFatalException /
	 */
	public static EAlgoProximite PrintChoixAlgo() throws CFatalException {
		while(true) {
			PrintHeader();
			PrintTitle("Configurations");
			System.out.println("\nChoisissez votre algorithme de proximit� :\n");
			System.out.println("#1 - Distance de vecteurs");
			System.out.println("#2 - Somme des diff�rences par axe");
			System.out.println("#3 - Somme des diff�rences sur axes principaux");
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

	/**
	 * Demande comment choisir les acteurs
	 * @return la methode de choix d'acteur choisie
	 * @throws CFatalException /
	 */
	public static EChoixActeurs PrintChoixActeurs() throws CFatalException {
		while(true) {
			PrintHeader();
			PrintTitle("Configurations");
			System.out.println("\nChoisissez comment r�cup�rer les diff�rents acteurs :\n");
			System.out.println("#1 - Depuis le fichier JSON Acteurs.json");
			System.out.println("#2 - En les g�n�rant al�atoirement");
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
	
	/**
	 * Demande le nombre de candidats et d'electeurs � g�n�reer al�atoirement
	 * @return un objet contenant les deux valeurs
	 * @throws CFatalException /
	 */
	public static CActeursAlea BrancheActeursAleatoires() throws CFatalException {
		while(true) {
			PrintHeader();
			PrintTitle("Premieres Configurations");
			
			int nbCandidats = -1, nbElecteurs = -1;
			
			while(nbCandidats < 1) {
				System.out.println("\nCombiens de candidat(s) g�n�rer ?");
				try {
					nbCandidats = Integer.parseInt(getUserChoice());
				}
				catch(NumberFormatException e) {
					continue;
				}
			}
			
			while(nbElecteurs < 1) {
				System.out.println("\nCombiens d'Electeurs g�n�rer ?");
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
	
	/**
	 * Demande une action principale
	 * @return l'action choisie
	 * @throws CFatalException /
	 */
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
	
	/**
	 * Demande si affichage des candidats
	 * @return true si oui ; false sinon
	 * @throws CFatalException /
	 */
	public static boolean BrancheInfos() throws CFatalException {
		while(true) {
			PrintHeader();
			PrintTitle("Choix Infos");
			System.out.println("\nVoulez vous un r�sum� des candidats ?\n");
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
	
	/**
	 * Demande le pourcentage de la pop sur laquelle effectuer le sondage
	 * @return le pourcentage
	 * @throws CFatalException /
	 */
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
	
	/**
	 * Demande si le sondage doit intergagir avec la population
	 * @return true si oui ; false sinon
	 * @throws CFatalException /
	 */
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
	
	/**
	 * Demande le nombre d'interactions � simuler
	 * @return le nombre choisi
	 * @throws CFatalException /
	 */
	public static int BrancheInteractions() throws CFatalException {
		while(true) {
			PrintHeader();
			PrintTitle("Choix nombre interactions");
			System.out.println("\nCombien d'interactions voulez vous g�n�rer ?\n");
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
