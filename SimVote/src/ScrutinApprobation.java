import java.util.HashMap;
import java.util.Vector;

public class ScrutinApprobation extends Scrutin{

	public ScrutinApprobation(Vector<Acteur> vecCandidats, Vector<Acteur> vecElecteurs) {
		super(vecCandidats, vecElecteurs);
	}

	@Override
	public HashMap<Acteur,String> simuler() {
		return new HashMap<Acteur,String>();
	}

}
