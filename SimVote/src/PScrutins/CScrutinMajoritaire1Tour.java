package PScrutins;
import java.util.Vector;

import PExceptions.CFatalException;
import PExceptions.CUnknownParameterException;
import PGeneral.CActeur;
import PGeneral.CResultScrutin;
import PGeneral.EAlgoProximite;

/**
 * Classe permettant de simuler un scrutin majoritaire à un tour
 * @author Augustin Giraudier et Arthur Secher Cabot
 */
public class CScrutinMajoritaire1Tour extends CScrutin{

	/**
	 * @param vecCandidats vecteur d'acteurs contenant les candidats
	 * @param vecAll vecteur d'acteurs contenant les electeurs
	 */
	public CScrutinMajoritaire1Tour(Vector<CActeur> vecCandidats, Vector<CActeur> vecAll) {
		super(vecCandidats, vecAll);
	}

	@Override
	public Vector<CResultScrutin> simuler(EAlgoProximite algoProximite) throws CFatalException {
		
		// Un scrutin majoritaire un tour revient à un scrutin borda avec une liste de 1 candidat : 
		CScrutinBorda sb;
		try {
			sb = new CScrutinBorda(vecCandidats, vecAll, 1);
		} catch (CUnknownParameterException e) {
			throw new CFatalException("You cannot simulate a ballot without any candidate...");
		}
		return sb.simuler(algoProximite);
		
	}
}
