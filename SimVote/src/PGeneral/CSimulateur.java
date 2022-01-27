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
	private CScrutin scrutin;
	private EScrutinType typeScrutin;
	private EAlgoProximite algo;
	//private String ConfigFilePath;
	//private String ActorsFilePath;
	
	/**
	 * @param algo : algorithme de proximité à utiliser pour l'éléction
	 * @param scrutin : type de scrutin à mettre en place
	 * @param ConfigFilePath : chemin vers le fichier de configuration
	 * @param ActorsFilePath : chemin vers le fichier des acteurs
	 * @throws Exception 
	 */
	public CSimulateur(EAlgoProximite algo, EScrutinType scrutin, String ConfigFilePath, String ActorsFilePath) throws Exception {
		
		this.vecCandidats = new Vector<CActeur>();
		this.vecElecteurs = new Vector<CActeur>();
		this.vecAll = new Vector<CActeur>();
		this.typeScrutin = scrutin;
		this.algo = algo;
		
		JSONObject ConfigObj;
		JSONObject ActeursObj;
		int BordaCoef = 1;
		try {
			
			ConfigObj = new JSONObject(Files.readString(Paths.get(ConfigFilePath)));
			CActeur.SeuilProximiteActeurs = ConfigObj.getDouble("Seuil_Proximite_Acteurs");
			CActeur.nbrAxesPrincipaux = ConfigObj.getInt("Nbr_Axes_Principaux");
			CActeur.SeuilDisatnceAbstention = ConfigObj.getInt("Seuil_Disatnce_Abstention");
			CActeur.CoefInteraction = ConfigObj.getDouble("Coef_interaction");
			BordaCoef = ConfigObj.getInt("NbCandidatsListeBorda");
			
			ActeursObj = new JSONObject(Files.readString(Paths.get(ActorsFilePath)));
			
			JSONArray AxesArr = ActeursObj.getJSONArray("Axes");
			
			String[] keys = {"Candidats", "Electeurs"};
			
			for(String strKey : keys) {
				JSONArray ActeurArr = ActeursObj.getJSONArray(strKey);
				for(int i_candidat=0; i_candidat < ActeurArr.length(); i_candidat++) {
					JSONObject CandidatObj = ActeurArr.getJSONObject(i_candidat);
					Vector<CAxe> vecAxes = new Vector<CAxe>();
					for(int i_axe = 0; i_axe < AxesArr.length(); i_axe++) {
						vecAxes.add(new CAxe(AxesArr.getString(i_axe), CandidatObj.getDouble(AxesArr.getString(i_axe))));
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
		
		switch(scrutin) {
		case ALTERNATIF:
			this.scrutin = new CScrutinAlternatif(algo, this.vecCandidats, this.vecAll);
			break;
		case APPROBATION:
			this.scrutin = new CScrutinApprobation(algo, this.vecCandidats, this.vecAll);
			break;
		case BORDA:
			this.scrutin = new CScrutinBorda(algo, this.vecCandidats, this.vecAll, BordaCoef);
			break;
		case MAJORITAIRE_1_TOUR:
			this.scrutin = new CScrutinMajoritaire1Tour(algo, this.vecCandidats, this.vecAll);
			break;
		case MAJORITAIRE_2_TOURS:
			this.scrutin = new CScrutinMajoritaire2Tours(algo, this.vecCandidats, this.vecAll);
			break;
		}

	}
	
	
	/**
	 * Lance le scrutin enregistré et affiche les résultats
	 * @throws Exception 
	 */
	public void Simuler() throws Exception {
		Vector<CResultScrutin> VecResult = this.scrutin.simuler();
		System.out.println("--- Results Scrutin " + this.typeScrutin.name() + " ---");
		DisplayResults(VecResult);
		System.out.println("---");
	}
	
	private void DisplayResults(Vector<CResultScrutin> VecResult) {
		for (CResultScrutin rs : VecResult) {
		    System.out.println(rs);
		}
	}
	
	public void interact(int nbInteractions) throws Exception {
		
		Random rand = new Random(); 
		
		for(int i_interaction = 0; i_interaction < nbInteractions; i_interaction++) {
			// tirage au sort de 2 electeurs :
			int elec1 = rand.nextInt(this.vecAll.size());
			int elec2 = rand.nextInt(this.vecAll.size());
			
			this.vecAll.get(elec1).interact(this.vecAll.get(elec2), this.algo);
		}
	}
	
	public Vector<CResultScrutin> sonder(int popPercentage/*, int nbInteractions*/) throws Exception {
		
		if(popPercentage < 0 || popPercentage > 100) {
			throw new Exception("Percentage out of [0;100]");
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
		
		CScrutinMajoritaire1Tour sondage = new CScrutinMajoritaire1Tour(algo, this.vecCandidats, vecSondes);
		
		Vector<CResultScrutin> VecResult = sondage.simuler();
		
//		System.out.println("--- Results Sondage sur " + Integer.toString(popPercentage) 
//			+ "% de la population (soit " + Integer.toString(nbSondes) + " personnes) ---");
//		DisplayResults(VecResult);
//		System.out.println("---");
		
		return VecResult;
	}
	
	public void interactWithSondage(Vector<CResultScrutin> sondageResults) throws Exception {
		
		// pour chaque electeur :
		for(CActeur electeur : this.vecElecteurs) {
			double utiliteMax = 0;
			int index = 0;
			
			// Récupération de l'utilite max
			for(int i_acteur=0 ; i_acteur < sondageResults.size(); i_acteur++) {
				CActeur candidat = sondageResults.get(i_acteur).getActeur();
				double score = sondageResults.get(i_acteur).getIscore();
				
				double utilite = (1/electeur.getDistance(candidat, algo)) * score;
				
				if(utilite > utiliteMax) {
					utiliteMax = utilite;
					index = i_acteur;
				}
			}
			
			// Rapprochement avec le candidat ciblé : 
			electeur.rapprocherCandidat(sondageResults.get(index).getActeur(), algo);
		}
	}
	
}
