package PGeneral;

/**
 * Enum des différents algorithmes de calcul de proximité d'opinion implémentés
 * @author Augustin Giraudier et Arthur Secher Cabot
 */
public enum EAlgoProximite {
	/** Calcul des distances brutes des axes */
	DISTANCE_VECTEUR,
	/** Calcul la somme des différences entre les axes */
	SOMME_DIFFERENCES,
	/** Calcul la somme des différences des axes principaux */
	SOMME_DIFFERENCES_AXES_PRINCIPAUX
}
