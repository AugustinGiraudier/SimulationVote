import java.util.HashMap;
import java.util.Vector;

public class ScrutinAlternatif extends Scrutin {

	public ScrutinAlternatif(Vector<Acteur> vecCandidats, Vector<Acteur> vecElecteurs) {
		super(vecCandidats, vecElecteurs);
	}

	@Override
	public HashMap<Acteur,String> simuler() {
		return new HashMap<Acteur,String>();
	}

}
