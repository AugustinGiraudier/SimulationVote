package PGeneral;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONObject;

import PExceptions.CAxeException;
import PExceptions.CFatalException;
import PExceptions.CUnknownParameterException;
import PScrutins.CScrutin;
import PScrutins.CScrutinAlternatif;
import PScrutins.CScrutinApprobation;
import PScrutins.CScrutinBorda;
import PScrutins.CScrutinMajoritaire1Tour;
import PScrutins.CScrutinMajoritaire2Tours;

/**
 * Classe Permettant de simuler une éléction
 * @author Augustin Giraudier et Arthur Secher Cabot
 */
public class CSimulateur {
	
	private Vector<CActeur> vecCandidats;
	private Vector<CActeur> vecElecteurs;
	private Vector<CActeur> vecAll;
	
	private Vector<String> vecStrAxes;
	
	private CScrutin scrutin;
	private EScrutinType typeScrutin;
	private EAlgoProximite algo;
	
	private static int CoefBorda = 1;
	
	
	public CSimulateur(
			EAlgoProximite algo,
			EScrutinType scrutin,
			String ConfigFilePath,
			Vector<CActeur> vecCandidats,
			Vector<CActeur> vecElecteurs,
			Vector<CActeur> vecAll
			) throws Exception {
		
		this.algo = algo;
		this.typeScrutin = scrutin;
		this.vecAll = vecAll;
		this.vecCandidats = vecCandidats;
		this.vecElecteurs = vecElecteurs;
		
		loadConfigFile(ConfigFilePath);
		changeScrutin(scrutin);
		
	}
	
	
	public CSimulateur(
			EAlgoProximite algo,
			EScrutinType scrutin, 
			String ConfigFilePath,
			int nbrCandidats,
			int nbrElecteurs
			)throws CFatalException{
		
		this.algo = algo;
		this.typeScrutin = scrutin;
		this.vecCandidats = new Vector<>(nbrCandidats);
		this.vecElecteurs = new Vector<>(nbrElecteurs);
		this.vecAll = new Vector<>(nbrElecteurs + nbrCandidats);
		this.vecStrAxes = new Vector<>();
		
		loadConfigFile(ConfigFilePath);
		
		Random RD = new Random();
		
		// Tirage Aleatoire des Candidats :
		for(int i_candidat=0 ; i_candidat < nbrCandidats ; i_candidat++) {
			CActeur cand = createRandomActor("Candidat-" + Integer.toString(i_candidat), RD);
			this.vecCandidats.add(cand);
			this.vecAll.add(cand);
		}
		
		// Tirage Aleatoire des Electeurs :
		for(int i_electeur=0 ; i_electeur < nbrElecteurs ; i_electeur++) {
			CActeur cand = createRandomActor("Electeur-" + Integer.toString(i_electeur), RD);
			this.vecElecteurs.add(cand);
			this.vecAll.add(cand);
		}
		
		changeScrutin(scrutin);
		
	}
	
	/**
	 * @param algo : algorithme de proximité à utiliser pour l'éléction
	 * @param scrutin : type de scrutin à mettre en place
	 * @param ConfigFilePath : chemin vers le fichier de configuration
	 * @param ActorsFilePath : chemin vers le fichier des acteurs
	 * @throws Exception 
	 */
	public CSimulateur(
			EAlgoProximite algo,
			EScrutinType scrutin, 
			String ConfigFilePath, 
			String ActorsFilePath
			) throws CFatalException {
		
		this.vecCandidats = new Vector<>();
		this.vecElecteurs = new Vector<>();
		this.vecAll = new Vector<>();
		this.vecStrAxes = new Vector<>();
		this.typeScrutin = scrutin;
		this.algo = algo;
		
		loadConfigFile(ConfigFilePath);
		loadActorsFile(ActorsFilePath);

		changeScrutin(scrutin);

	}
	
	private CActeur createRandomActor(String name, Random RD)throws CFatalException{
		Vector<CAxe> vecAxes = new Vector<>();
		for(String strAxe : this.vecStrAxes) {
			try {
				vecAxes.add(new CAxe(strAxe, RD.nextDouble()));
			}
			catch(CAxeException e) {
				throw new CFatalException("Random module doesn't seem to work properly");
			}
		}
		return new CActeur(name, vecAxes);
	}
	
	private void loadConfigFile(String ConfigFilePath) {
		JSONObject ConfigObj;
		try {
			
			ConfigObj = new JSONObject(Files.readString(Paths.get(ConfigFilePath)));
			CActeur.SeuilProximiteActeurs = ConfigObj.getDouble("Seuil_Proximite_Acteurs");
			CActeur.nbrAxesPrincipaux = ConfigObj.getInt("Nbr_Axes_Principaux");
			CActeur.SeuilDisatnceAbstention = ConfigObj.getInt("Seuil_Disatnce_Abstention");
			CActeur.CoefInteraction = ConfigObj.getDouble("Coef_interaction");
			CSimulateur.CoefBorda = ConfigObj.getInt("NbCandidatsListeBorda");
			
			JSONArray AxesArr = ConfigObj.getJSONArray("Axes");
			for(int i_axe = 0; i_axe < AxesArr.length(); i_axe++)
				vecStrAxes.add(AxesArr.getString(i_axe));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void loadActorsFile(String ActorsFilePath) {
		JSONObject ActeursObj;
		try {
			ActeursObj = new JSONObject(Files.readString(Paths.get(ActorsFilePath)));
			
			String[] keys = {"Candidats", "Electeurs"};
			
			for(String strKey : keys) {
				JSONArray ActeurArr = ActeursObj.getJSONArray(strKey);
				for(int i_candidat=0; i_candidat < ActeurArr.length(); i_candidat++) {
					JSONObject CandidatObj = ActeurArr.getJSONObject(i_candidat);
					Vector<CAxe> vecAxes = new Vector<CAxe>();
					for(String strAxe : this.vecStrAxes) {
						vecAxes.add(new CAxe(strAxe, CandidatObj.getDouble(strAxe)));
					}
					if(strKey == "Candidats")
						vecCandidats.add(new CActeur(CandidatObj.getString("id"),vecAxes));
					else
						vecElecteurs.add(new CActeur(CandidatObj.getString("id"),vecAxes));
					vecAll.add(new CActeur(CandidatObj.getString("id"),vecAxes));
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void changeAlgo(EAlgoProximite algo){
		this.algo = algo;
	}
	
	public void changeScrutin(EScrutinType scrutin) throws CFatalException {
		switch(scrutin) {
		case ALTERNATIF:
			this.scrutin = new CScrutinAlternatif(this.vecCandidats, this.vecAll);
			break;
		case APPROBATION:
			this.scrutin = new CScrutinApprobation(this.vecCandidats, this.vecAll);
			break;
		case BORDA:
			try {
				this.scrutin = new CScrutinBorda(this.vecCandidats, this.vecAll, CSimulateur.CoefBorda);
			}
			catch(CUnknownParameterException e) {
				System.out.println(e.getMessage());
				System.out.println("New try with 1 as Borda coef...");
				try {
					this.scrutin = new CScrutinBorda(this.vecCandidats, this.vecAll,1);
				}
				catch(CUnknownParameterException e2) {
					throw new CFatalException("You must have at least 1 canididate...");
				}
			}
			break;
		case MAJORITAIRE_1_TOUR:
			this.scrutin = new CScrutinMajoritaire1Tour(this.vecCandidats, this.vecAll);
			break;
		case MAJORITAIRE_2_TOURS:
			this.scrutin = new CScrutinMajoritaire2Tours(this.vecCandidats, this.vecAll);
			break;
		}
	}
	
	
	/**
	 * Lance le scrutin enregistré et affiche les résultats
	 * @throws CFatalException
	 */
	public void Simuler() throws CFatalException{
		Vector<CResultScrutin> VecResult = this.scrutin.simuler(this.algo);
		System.out.println("--- Results Scrutin " + this.typeScrutin.name() + " ---");
		DisplayResults(VecResult);
		System.out.println("ABSTENTION : " + Double.toString(this.scrutin.ComuteAbstention(VecResult)) + "%");
		System.out.println("---");
	}
	
	public void DisplayResults(Vector<CResultScrutin> VecResult) {
		// Tris des résultats :
		boolean changeHappent = true;
		while(changeHappent) {
			changeHappent = false;
			for(int j=0 ; j<VecResult.size()-1; j++)
				if(VecResult.get(j).getIscore() < VecResult.get(j+1).getIscore()) {
					changeHappent = true;
					CResultScrutin temp = VecResult.get(j);
					VecResult.set(j, VecResult.get(j+1));
					VecResult.set(j+1, temp);
				}
		}
		
		for (CResultScrutin rs : VecResult) {
		    System.out.println(rs);
		}
	}
	
	public void interact(int nbInteractions) throws CFatalException {
		
		Random rand = new Random(); 
		
		for(int i_interaction = 0; i_interaction < nbInteractions; i_interaction++) {
			// tirage au sort de 2 electeurs :
			int elec1 = rand.nextInt(this.vecAll.size());
			int elec2 = rand.nextInt(this.vecAll.size());
			
			this.vecAll.get(elec1).interact(this.vecAll.get(elec2), this.algo);
		}
	}
	
	public Vector<CResultScrutin> sonder(int popPercentage) throws CFatalException{
		
		if(popPercentage < 0 || popPercentage > 100) {
			throw new CFatalException("Percentage out of [0;100]");
		}
		
		// tirage d'une séquence aléatoire pour le sondage :
		int size = this.vecAll.size();
		List<Integer> indexes = new ArrayList<Integer>(size); 
		for (int i = 0; i < size; i++) 
		  indexes.add(i); 
		Collections.shuffle(indexes);
		
		// on recupere la proportion donnée :
		Vector<CActeur> vecSondes = new Vector<CActeur>();
		int nbSondes = (int)((double)popPercentage * size / 100.0d);
		for(int i_sondes = 0; i_sondes < nbSondes ; i_sondes++) {
			vecSondes.add(this.vecAll.get(indexes.get(i_sondes)));
		}
		
		CScrutinMajoritaire1Tour sondage = new CScrutinMajoritaire1Tour(this.vecCandidats, vecSondes);
		
		Vector<CResultScrutin> VecResult = sondage.simuler(this.algo);
		
		return VecResult;
	}
	
	public void interactWithSondage(Vector<CResultScrutin> sondageResults) throws CFatalException {
		
		// pour chaque electeur :
		for(CActeur electeur : this.vecElecteurs) {
			double utiliteMax = 0;
			int index = 0;
			
			// Récupération de l'utilite max
			for(int i_acteur=0 ; i_acteur < sondageResults.size(); i_acteur++) {
				CActeur candidat = sondageResults.get(i_acteur).getActeur();
				double score = sondageResults.get(i_acteur).getIscore();
				
				double utilite;
				try {
					utilite = (1/electeur.getDistance(candidat, this.algo)) * score;
				} catch (CUnknownParameterException e) {
					throw new CFatalException(e.getMessage());
				}
				
				if(utilite > utiliteMax) {
					utiliteMax = utilite;
					index = i_acteur;
				}
			}
			
			// Rapprochement avec le candidat ciblé : 
			electeur.rapprocherCandidat(sondageResults.get(index).getActeur(), algo);
		}
	}
	
	public void PrintInfos() {
		System.out.println("\n----------------- Infos Scrutin -----------------");
		System.out.println("Type de scrutin : " + this.typeScrutin.toString());
		System.out.println("Type d'algo de proximité : " + this.algo.toString());
		System.out.println("Nombre de candidats : " + Integer.toString(vecCandidats.size()));
		System.out.println("Nombre d'électeurs : " + Integer.toString(vecElecteurs.size()));
		System.out.println("Nombre total d'acteurs : " + Integer.toString(vecAll.size()));
		System.out.println("-------------------------------------------------\n");
	}
	
	public void PrintCandidates() {
		System.out.println("\n----------------- Infos Candidats -----------------");
		for(CActeur cand : this.vecCandidats) {
			System.out.println(cand);
		}
		System.out.println("---------------------------------------------------\n");
	}
	
}
