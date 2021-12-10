public class Axe implements Comparable<Axe> {
	
	//public static double SeuilProximiteOpinion = -1;
	
	private String nom;
	private double valeur; 
	
	
	public Axe(String _nom, double _valeur) throws Exception {
		//if(Axe.SeuilProximiteOpinion == -1) {
		//	throw new Exception("Please Set The static variable Axe.SeuilProximiteOpinion before instantiate it.");
		//}
		this.nom = _nom;
		if(_valeur < 0 || _valeur > 1) {
			throw new Exception("An Axis has been created with a value out of [0,1]");
		}
		this.valeur = _valeur;
	};
	
	private double getValeur() {return valeur;}
	private String getNom() {return nom;}
	public double getDistance(Axe a) {
		return Math.abs(this.valeur - a.getValeur());
	}
	
	
//	public boolean estProche(Axe a) {
//		return getDistance(a) < Axe.SeuilProximiteOpinion;
//	}
	public boolean memeNom(Axe a) {
		return nom == a.getNom();
	}
	public double moyenne(Axe a) {
		return (this.getValeur() + a.getValeur()) / 2;
	}
	
	@Override
	public String toString() {
		return "---\nAxe: '" + this.nom + "' " + this.valeur + "\n---\n";
	}

	@Override
	public int compareTo(Axe o) {
		if(valeur == o.getValeur())
			return 0;
		if(valeur < o.getValeur())
			return 1;
		return -1;
	}
}
