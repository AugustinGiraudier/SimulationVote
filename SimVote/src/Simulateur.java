import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Classe Permettant de simuler une éléction
 * @author Augustin Giraudier et Arthur Secher Cabot
 */
public class Simulateur {
	
	private Vector<Acteur> vecCandidats;
	private Vector<Acteur> vecElecteurs;
	private Scrutin scrutin;
	
	/**
	 * @param algo : algorithme de proximité à utiliser pour l'éléction
	 * @param scrutin : type de scrutin à mettre en place
	 * @param ConfigFilePath : chemin vers le fichier de configuration
	 * @param ActorsFilePath : chemin vers le fichier des acteurs
	 */
	public Simulateur(AlgoProximite algo, ScrutinType scrutin, String ConfigFilePath, String ActorsFilePath) {
		
		this.vecCandidats = new Vector<Acteur>();
		this.vecElecteurs = new Vector<Acteur>();
		
		JSONObject ConfigObj;
		JSONObject ActeursObj;
		try {
			
			ConfigObj = new JSONObject(Files.readString(Paths.get(ConfigFilePath)));
			Acteur.SeuilProximiteActeurs = ConfigObj.getDouble("Seuil_Proximite_Acteurs");
			Acteur.nbrAxesPrincipaux = ConfigObj.getInt("Nbr_Axes_Principaux");
		
			
			ActeursObj = new JSONObject(Files.readString(Paths.get(ActorsFilePath)));
			
			JSONArray AxesArr = ActeursObj.getJSONArray("Axes");
			
			String[] keys = {"Candidats", "Electeurs"};
			
			for(String strKey : keys) {
				JSONArray ActeurArr = ActeursObj.getJSONArray(strKey);
				for(int i_candidat=0; i_candidat < ActeurArr.length(); i_candidat++) {
					JSONObject CandidatObj = ActeurArr.getJSONObject(i_candidat);
					Vector<Axe> vecAxes = new Vector<Axe>();
					for(int i_axe = 0; i_axe < AxesArr.length(); i_axe++) {
						vecAxes.add(new Axe(AxesArr.getString(i_axe), CandidatObj.getDouble(AxesArr.getString(i_axe))));
					}
					if(strKey == "Candidats")
						vecCandidats.add(new Acteur(CandidatObj.getString("id"),vecAxes));
					else
						vecElecteurs.add(new Acteur(CandidatObj.getString("id"),vecAxes));
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		switch(scrutin) {
		case ALTERNATIF:
			this.scrutin = new ScrutinAlternatif(algo, this.vecCandidats, this.vecElecteurs);
			break;
		case APPROBATION:
			this.scrutin = new ScrutinApprobation(algo, this.vecCandidats, this.vecElecteurs);
			break;
		case BORDA:
			this.scrutin = new ScrutinBorda(algo, this.vecCandidats, this.vecElecteurs);
			break;
		case MAJORITAIRE_1_TOUR:
			this.scrutin = new ScrutinMajoritaire1Tour(algo, this.vecCandidats, this.vecElecteurs);
			break;
		case MAJORITAIRE_2_TOURS:
			this.scrutin = new ScrutinMajoritaire2Tours(algo, this.vecCandidats, this.vecElecteurs);
			break;
		}

	}
	
	
	/**
	 * Lance le scrutin enregistré et affiche les résultats
	 */
	public void Simuler() {
		HashMap<Acteur,String> MapResult = this.scrutin.simuler();
		for (Acteur actor : MapResult.keySet()) {
		    System.out.println(actor.getNom() + " : " + MapResult.get(actor) + "");
		}
	}
	
}
