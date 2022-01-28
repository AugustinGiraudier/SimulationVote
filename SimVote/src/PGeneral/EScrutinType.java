package PGeneral;

/**
 * Enum des diff�rents scrutins impl�ment�s
 * @author Augustin Giraudier et Arthur Secher Cabot
 */
public enum EScrutinType {
	/**
	 * Scrutin majoritaire � tour 
	 */
	MAJORITAIRE_1_TOUR,
	/**
	 * Scrutin majoritaire � 2 tours
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
