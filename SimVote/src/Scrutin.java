import java.util.HashMap;
import java.util.Vector;

public abstract class Scrutin {
	
	protected Vector<Acteur> vecCandidats;
	protected Vector<Acteur> vecElecteurs;
	
	public Scrutin(Vector<Acteur> vecCandidats, Vector<Acteur> vecElecteurs) {
		this.vecCandidats = vecCandidats;
		this.vecElecteurs = vecElecteurs;
	}

	public abstract HashMap<Acteur,String> simuler();
}
