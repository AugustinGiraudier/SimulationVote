import java.util.HashMap;
import java.util.Vector;

public class ScrutinBorda extends Scrutin {

	public ScrutinBorda(Vector<Acteur> vecCandidats, Vector<Acteur> vecElecteurs) {
		super(vecCandidats, vecElecteurs);
	}

	@Override
	public HashMap<Acteur,String> simuler() {
		return new HashMap<Acteur,String>();
	}

}
