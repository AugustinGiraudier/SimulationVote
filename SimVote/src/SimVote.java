import java.util.Vector;

public class SimVote {
	public static void main(String[] args) throws Exception {
		
//		// Acteur A
//		Vector<Axe> vecA = new Vector<Axe>();
//		vecA.add(new Axe("ecologie", 0.1));
//		vecA.add(new Axe("capitalisme", 0.8));
//		vecA.add(new Axe("societe", 0.7));
//		// Acteur B
//		Vector<Axe> vecB = new Vector<Axe>();
//		vecB.add(new Axe("ecologie", 0.9));
//		vecB.add(new Axe("capitalisme", 0.01));
//		vecB.add(new Axe("societe", 0.3));
//		// Acteur C
//		Vector<Axe> vecC = new Vector<Axe>();
//		vecC.add(new Axe("ecologie", 0.9));
//		vecC.add(new Axe("capitalisme", 0.01));
//		vecC.add(new Axe("societe", 0.3));
//		
//		
//		Acteur A = new Acteur("A", vecA);
//		Acteur B = new Acteur("B", vecB);
		
		//System.out.println(A.OpinionProche(B));
		//System.out.println(A);
		//System.out.println(B);
		
		Simulateur sc = new Simulateur(ScrutinType.MAJORITAIRE_1_TOUR, "Config.json","Acteurs.json");
		sc.Simuler();
	}
}
