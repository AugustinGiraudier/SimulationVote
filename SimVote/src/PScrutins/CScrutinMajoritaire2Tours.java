package PScrutins;
import java.util.Vector;

import PExceptions.CFatalException;
import PGeneral.CActeur;
import PGeneral.CResultScrutin;
import PGeneral.EAlgoProximite;

/**
 * Classe permettant de simuler un scrutin majoritaire ? 2 tours
 * @author Augustin Giraudier et Arthur Secher Cabot
 */
public class CScrutinMajoritaire2Tours extends CScrutin {

	/**
	 * @param vecCandidats vecteur d'acteurs contenant les candidats
	 * @param vecAll vecteur d'acteurs contenant les electeurs
	 */
	public CScrutinMajoritaire2Tours(Vector<CActeur> vecCandidats, Vector<CActeur> vecAll) {
		super(vecCandidats, vecAll);
	}

	@Override
	public Vector<CResultScrutin> simuler(EAlgoProximite algoProximite) throws CFatalException {
		
		// On cr?e un scrutin majoritaire ? 1 tour pour simuler le premier tour :
		CScrutinMajoritaire1Tour tour1 = new CScrutinMajoritaire1Tour(this.vecCandidats, this.vecAll);
		
		// On simule le tour :
		Vector<CResultScrutin> result1 = tour1.simuler(algoProximite);
		
		// On cr?e des variables pour stocker les deux meilleurs candidats :
		CResultScrutin firstActor = new CResultScrutin(null, 0);
		CResultScrutin secondActor = new CResultScrutin(null, 0);
		
		for (CResultScrutin rs : result1) {
		    if(rs.getIscore() > firstActor.getIscore()) {
		    	secondActor = firstActor;
		    	firstActor = rs;
		    }
		    else if(rs.getIscore() > secondActor.getIscore())
		    	secondActor = rs;
		}
		
		// On pr?pare le vecteur pour le tour 2 :
		Vector<CActeur> vecCandidatsTour2 = new Vector<CActeur>();
		vecCandidatsTour2.add(firstActor.getActeur());
		vecCandidatsTour2.add(secondActor.getActeur());
		
		// Puis on simule le dernier tour :
		CScrutinMajoritaire1Tour tour2 = new CScrutinMajoritaire1Tour(vecCandidatsTour2, this.vecAll);
		return tour2.simuler(algoProximite);
	}

}
