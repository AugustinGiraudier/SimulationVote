package PScrutins;
import java.util.Vector;

import PGeneral.CActeur;
import PGeneral.CResultScrutin;
import PGeneral.EAlgoProximite;

/**
 * Classe permettant de simuler un scrutin majoritaire à un tour
 * @author Augustin Giraudier et Arthur Secher Cabot
 */
public class CScrutinMajoritaire1Tour extends CScrutin{

	/**
	 * @param algo : algorithme de proximité à utiliser
	 * @param vecCandidats : vecteur d'acteurs contenant les candidats
	 * @param vecAll : vecteur d'acteurs contenant les electeurs
	 */
	public CScrutinMajoritaire1Tour(EAlgoProximite algo, Vector<CActeur> vecCandidats, Vector<CActeur> vecAll) {
		super(algo, vecCandidats, vecAll);
	}

	@Override
	public Vector<CResultScrutin> simuler() throws Exception {
		
		// Un scrutin majoritaire un tour revient à un scrutin borda avec une liste de 1 candidat : 
		CScrutinBorda sb = new CScrutinBorda(algoProximite, vecCandidats, vecAll, 1);
		return sb.simuler();
		
	}
	
	/**
	 * Compte le nombre d'occurence d'une valeur dans un vecteur
	 * @param vec : vecteur à observer
	 * @param intToCount : valeur dont on compte les occurences
	 * @return le nombre d'occurrence de 'intToCount' dans 'vec'
	 */
	private int NombreIteration(Vector<Integer> vec, int intToCount) {
		int count = 0;
		for(int i : vec) {
			if(i==intToCount)
				count++;
		}
		return count;
	}

}
