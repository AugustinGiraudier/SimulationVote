import java.util.Vector;

public class Acteur {
	
	public static double SeuilProximiteActeurs = -1;
	public static int nbrAxesPrincipaux = -1;
	
	private String nom;
	protected Vector<Axe> vecAxes;
	
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
	
	public boolean OpinionProche(Acteur a) {
		return moyenneDiffOk(this.vecAxes, a.vecAxes);
	}
	
	public boolean peutVoter(Acteur c) {
		
		Vector<Axe> vecA = new Vector<Axe>();
		Vector<Axe> vecB = new Vector<Axe>();
		
		int[] positionsMax = new int[nbrAxesPrincipaux];
		
		for(int i_axe = 0; i_axe < nbrAxesPrincipaux; i_axe++)
			positionsMax[i_axe] = 0;
		
		// On recupere les indexes de n axes principaux du votant :
		for(int i_axe = 0; i_axe < this.vecAxes.size(); i_axe++) {
			for(int i_pos = 0; i_pos < nbrAxesPrincipaux; i_pos++) {
				// Si lavaleur est plus grande que la plus grande stockée
				if(this.vecAxes.get(i_axe).compareTo(c.vecAxes.get(positionsMax[i_axe])) == -1) {
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
			for(Axe axeB : vecB) {
				if(axe.memeNom(axeB)) {
					vecB.add(axeB);
					continue;
				}
			}
		}
		
		return moyenneDiffOk(vecA, vecB);
	}
	
	private boolean moyenneDiffOk(Vector<Axe> vecA, Vector<Axe> vecB) {
		Vector<Double> vecDiff = new Vector<Double>();
		
		for(Axe axe1 : vecA) {
			for(Axe axe2 : vecB) {
				if(axe1.memeNom(axe2)) {
					vecDiff.add(axe1.getDistance(axe2));
					continue;
				}
			}
		}
		double sum = 0;
		for(Double d : vecDiff) {
			sum += d;
		}
		return (sum/vecDiff.size()) < SeuilProximiteActeurs;
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
