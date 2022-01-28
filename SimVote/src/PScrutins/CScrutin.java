package PScrutins;

import java.util.Vector;

import PExceptions.CFatalException;
import PGeneral.CActeur;
import PGeneral.CResultScrutin;
import PGeneral.EAlgoProximite;

/**
 * classe abstraite servant de base aux différents scrutins
 * @author Augustin Giraudier et Arthur Secher Cabot
 */
public abstract class CScrutin {
	
	protected Vector<CActeur> vecCandidats;
	protected Vector<CActeur> vecAll;
	
	/**
	 * @param vecCandidats vecteur d'acteurs contenant les candidats
	 * @param vecAll vecteur d'acteurs contenant les electeurs
	 */
	public CScrutin(Vector<CActeur> vecCandidats, Vector<CActeur> vecAll) {
		this.vecCandidats = vecCandidats;
		this.vecAll = vecAll;
	}

	/**
	 * @param algoProximite : algo de proximité
	 * @return le résultat du Scrutin
	 * @throws CFatalException /
	 */
	public abstract Vector<CResultScrutin> simuler(EAlgoProximite algoProximite) throws CFatalException;
	
	/**
	 * Calcule l'abstention en fonction du résultat d'un scrutin
	 * @param res résultat d'un scrutin
	 * @return le pourcentage d'abstention
	 */
	public double ComuteAbstention(Vector<CResultScrutin> res){
		double sum = 0;
		for(CResultScrutin RS : res)
			sum += RS.getIscore();
		return 100 - sum < 0.001 ? 0.0d : 100 - sum;
	}
	
}
