import java.util.Vector;

public class Acteur {
	
	public static double SeuilProximiteActeurs = -1;
	public static int nbrAxesPrincipaux = -1;
	
	private String nom;
	protected Vector<Axe> vecAxes;
	
	public String getNom() {return nom;}
	
	public Acteur(String _nom, Vector<Axe> _vecAxes) throws Exception {
		if(Acteur.SeuilProximiteActeurs == -1) {
			throw new Exception("Please Set The static variable Acteur.SeuilProximiteActeurs before instantiate it.");
		}
		else if(Acteur.nbrAxesPrincipaux == -1) {
			throw new Exception("Please Set The static variable Acteur.nbrAxesPrincipaux before instantiate it.");
		}
		this.nom = _nom;
		this.vecAxes = _vecAxes;
	}
	
	public double OpinionProche(Acteur a) {
		return moyenneDiffOk(this.vecAxes, a.vecAxes);
	}
	
	public double affinite(Acteur c) {
		
		Vector<Axe> vecA = new Vector<Axe>();
		Vector<Axe> vecB = new Vector<Axe>();
		
		int[] positionsMax = new int[nbrAxesPrincipaux];
		
		for(int i_axe = 0; i_axe < nbrAxesPrincipaux; i_axe++)
			positionsMax[i_axe] = 0;
		
		// On recupere les indexes de n axes principaux du votant :
		for(int i_axe = 0; i_axe < this.vecAxes.size(); i_axe++) {
			for(int i_pos = 0; i_pos < nbrAxesPrincipaux; i_pos++) {
				// Si la valeur est plus grande que la plus grande stockée
				if(this.vecAxes.get(i_axe).compareTo(c.vecAxes.get(positionsMax[i_pos])) == -1) {
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
		for(Axe axe : vecA) {
			for(Axe axeB : c.vecAxes) {
				if(axe.memeNom(axeB)) {
					vecB.add(axeB);
				}
			}
		}
		
		return moyenneDiffOk(vecA, vecB);
	}
	
	private double moyenneDiffOk(Vector<Axe> vecA, Vector<Axe> vecB) {
		Vector<Double> vecDiff = new Vector<Double>();
		
		for(Axe axe1 : vecA) {
			for(Axe axe2 : vecB) {
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
		for(Axe a : this.vecAxes) {
			out += a.toString();
		}
		return out + "*******************";
	}
	
}
