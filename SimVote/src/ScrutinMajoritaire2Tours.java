import java.util.HashMap;
import java.util.Vector;

public class ScrutinMajoritaire2Tours extends Scrutin {

	public ScrutinMajoritaire2Tours(Vector<Acteur> vecCandidats, Vector<Acteur> vecElecteurs) {
		super(vecCandidats, vecElecteurs);
	}

	@Override
	public HashMap<Acteur,String> simuler() {
		return new HashMap<Acteur,String>();
	}

}
