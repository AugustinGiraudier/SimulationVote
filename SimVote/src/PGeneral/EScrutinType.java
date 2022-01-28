package PGeneral;

/**
 * Enum des différents scrutins implémentés
 * @author Augustin Giraudier et Arthur Secher Cabot
 */
public enum EScrutinType {
	/**
	 * Scrutin majoritaire à tour 
	 */
	MAJORITAIRE_1_TOUR,
	/**
	 * Scrutin majoritaire à 2 tours
	 */
	MAJORITAIRE_2_TOURS,
	/**
	 * Scrutin par approbation
	 */
	APPROBATION,
	/**
	 * Scrutin Alternatif
	 */
	ALTERNATIF,
	/**
	 * Scrutin de Borda
	 */
	BORDA
}
