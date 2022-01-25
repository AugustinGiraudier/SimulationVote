package PGeneral;
public class CResultScrutin {

	private CActeur acteur;
	private double Fscore;
	private String strScore;
	
	
	public CResultScrutin(CActeur _acteur, double _Fscore) {
		this(_acteur, _Fscore, null);
	}
	public CResultScrutin(CActeur _acteur, double _Fscore, String _strScore) {
		this.acteur = _acteur;
		this.Fscore = _Fscore;
		this.strScore = _strScore;
	}
	
	public CActeur getActeur() {
		return acteur;
	}
	public double getIscore() {
		return Fscore;
	}
	public String getStrScore() {
		return strScore;
	}
	
	@Override
	public String toString() {
		String out = this.acteur.getNom() + " : ";
		
		if(this.strScore == null) 
			out += Double.toString(this.Fscore);
		else
			out += this.strScore;
		
		return out;
	}
	
}
