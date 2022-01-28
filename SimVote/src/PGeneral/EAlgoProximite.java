package PGeneral;

/**
 * Enum des diff�rents algorithmes de calcul de proximit� d'opinion impl�ment�s
 * @author Augustin Giraudier et Arthur Secher Cabot
 */
public enum EAlgoProximite {
	/** Calcul des distances brutes des axes */
	DISTANCE_VECTEUR,
	/** Calcul la somme des diff�rences entre les axes */
	SOMME_DIFFERENCES,
	/** Calcul la somme des diff�rences des axes principaux */
	SOMME_DIFFERENCES_AXES_PRINCIPAUX
}
