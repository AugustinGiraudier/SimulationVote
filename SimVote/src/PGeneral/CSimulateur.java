package PGeneral;
import java.nio.file.Files;
import java.nio.file.Paths;
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
	private CScrutin scrutin;
	private EScrutinType typeScrutin;
	private EAlgoProximite algo;
	
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
					vecElecteurs.add(new CActeur(CandidatObj.getString("id"),vecAxes));
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		switch(scrutin) {
		case ALTERNATIF:
			this.scrutin = new CScrutinAlternatif(algo, this.vecCandidats, this.vecElecteurs);
			break;
		case APPROBATION:
			this.scrutin = new CScrutinApprobation(algo, this.vecCandidats, this.vecElecteurs);
			break;
		case BORDA:
			this.scrutin = new CScrutinBorda(algo, this.vecCandidats, this.vecElecteurs, BordaCoef);
			break;
		case MAJORITAIRE_1_TOUR:
			this.scrutin = new CScrutinMajoritaire1Tour(algo, this.vecCandidats, this.vecElecteurs);
			break;
		case MAJORITAIRE_2_TOURS:
			this.scrutin = new CScrutinMajoritaire2Tours(algo, this.vecCandidats, this.vecElecteurs);
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
		for (CResultScrutin rs : VecResult) {
		    System.out.println(rs);
		}
		System.out.println("---");
	}
	
	public void interact(int nbInteractions) throws Exception {
		
		Random rand = new Random(); 
		
		for(int i_interaction = 0; i_interaction < nbInteractions; i_interaction++) {
			// tirage au sort de 2 electeurs :
			int elec1 = rand.nextInt(this.vecElecteurs.size());
			int elec2 = rand.nextInt(this.vecElecteurs.size());
			
			this.vecElecteurs.get(elec1).interact(this.vecElecteurs.get(elec2), this.algo);
		}
	}
	
}
