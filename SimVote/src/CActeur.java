import java.util.Vector;

/**
 * classe représentant un acteur (candidat ou electeur)
 * @author Augustin Giraudier et Arthur Secher Cabot
 */
public class CActeur {
	
	public static double SeuilProximiteActeurs = -1;
	public static int nbrAxesPrincipaux = -1;
	
	private String nom;
	protected Vector<CAxe> vecAxes;
	
	/**
	 * @return Le nom de l'acteur
	 */
	public String getNom() {return nom;}

	/**
	 * @param _nom : nom de l'acteur
	 * @param _vecAxes : vecteur des axes d'opinion de l'acteur
	 * @throws Exception
	 */
	public CActeur(String _nom, Vector<CAxe> _vecAxes) throws Exception {
		if(CActeur.SeuilProximiteActeurs == -1) {
			throw new Exception("Please Set The static variable CActeur.SeuilProximiteActeurs before instantiate it.");
		}
		else if(CActeur.nbrAxesPrincipaux == -1) {
			throw new Exception("Please Set The static variable CActeur.nbrAxesPrincipaux before instantiate it.");
		}
		this.nom = _nom;
		this.vecAxes = _vecAxes;
	}
	
	/**
	 * Permet de recuperer la distance d'opinion entre deux acteurs
	 * @param a : second acteur
	 * @param algo : algorithme de proximité d'opinion à utiliser
	 * @return une valeur représentant la distance d'opinion
	 * @throws Exception
	 */
	public double getDistance(CActeur a, EAlgoProximite algo) throws Exception {
		switch(algo) {
		case DISTANCE_VECTEUR:
			return vectorDistance(a);
		case SOMME_DIFFERENCES:
			return moyenneDiff(this.vecAxes, a.vecAxes);
		case SOMME_DIFFERENCES_AXES_PRINCIPAUX:
			return moyenneDiffAxesPrincipaux(a);
		default:
			throw new Exception("Erreur : algorithme de proximité inconnu...");
		}
	}
	
	/**
	 * @return le vecteur de double contenant les valeurs de tous les axes
	 */
	private double[] getVector() {
		double[] vec = new double[this.vecAxes.size()];
		for(int i_axe = 0; i_axe < this.vecAxes.size(); i_axe++) {
			vec[i_axe] = this.vecAxes.get(i_axe).getValeur();
		}
		return vec;
	}
			
	/**
	 * @param a : second acteur
	 * @return la distance entre les vecteurs d'opinion des deux acteurs
	 */
	private double vectorDistance(CActeur a) {
		double[] vec1 = this.getVector();
		double[] vec2 = a.getVector();
		double sum = 0;
		
		//TODO verifier qu'ils ont le meme nbr d'axe
		
		for(int i_axe = 0; i_axe < this.vecAxes.size(); i_axe++) {
			sum += Math.pow(vec2[i_axe] - vec1[i_axe], 2);
		}
		
		return Math.sqrt(sum);
	}
	
	/**
	 * Récupère les axes principaux d'opinion du votant et fait la myenne des différences 
	 * avec le second acteur sur ces axes.
	 * @param a : second acteur
	 * @return la moyenne des différences entre les axes principaux d'opinion
	 */
	private double moyenneDiffAxesPrincipaux(CActeur a) {
		
		Vector<CAxe> vecA = new Vector<CAxe>();
		Vector<CAxe> vecB = new Vector<CAxe>();
		
		int[] positionsMax = new int[nbrAxesPrincipaux];
		
		for(int i_axe = 0; i_axe < nbrAxesPrincipaux; i_axe++)
			positionsMax[i_axe] = 0;
		
		// On recupere les indexes de n axes principaux du votant :
		for(int i_axe = 0; i_axe < this.vecAxes.size(); i_axe++) {
			for(int i_pos = 0; i_pos < nbrAxesPrincipaux; i_pos++) {
				// Si la valeur est plus grande que la plus grande stockée
				if(this.vecAxes.get(i_axe).compareTo(a.vecAxes.get(positionsMax[i_pos])) == -1) {
					for(int i_decal = nbrAxesPrincipaux-1; i_decal > i_pos; i_decal--) {
						positionsMax[i_decal] = positionsMax[i_decal-1];
					}
					positionsMax[i_pos] = i_axe;
				}
			}
		}
		// On recupere les n axes prinipaux du votant :
		for(int i_axe : positionsMax)
			vecA.add(this.vecAxes.get(i_axe));
		
		// On recupere les memes axes ches le candidat :
		for(CAxe axe : vecA) {
			for(CAxe axeB : a.vecAxes) {
				if(axe.memeNom(axeB)) {
					vecB.add(axeB);
				}
			}
		}
		
		return moyenneDiff(vecA, vecB);
	}
	
	/**
	 * @param vecA : premier vecteur
	 * @param vecB : second vecteur
	 * @return la moyenne des differences entre chaque terme des vecteurs
	 */
	private double moyenneDiff(Vector<CAxe> vecA, Vector<CAxe> vecB) {
		Vector<Double> vecDiff = new Vector<Double>();
		//TODO verifier les dims des vecteurs
		for(CAxe axe1 : vecA) {
			for(CAxe axe2 : vecB) {
				if(axe1.memeNom(axe2)) {
					vecDiff.add(axe1.getDistance(axe2));
				}
			}
		}
		double sum = 0;
		for(Double d : vecDiff) {
			sum += d;
		}
		return (sum/vecDiff.size());
	}
	
	@Override
	public String toString() {
		String out =  "*******************\nActeur: " + this.nom + "\nAxes :\n";
		for(CAxe a : this.vecAxes) {
			out += a.toString();
		}
		return out + "*******************";
	}
	
	@Override
	protected CActeur clone() throws CloneNotSupportedException {
		try {
			CActeur out;
			out = new CActeur(this.nom, this.vecAxes);
			return out;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
