package Manager.Evol.client;

import java.util.ArrayList;

/**
 * <b> GoogleHit est la classe representant une requete Google.Elle herite de la classe recherche</b>
 * <p>
 * Un GoogleHit est caracterise par les informations suivantes :
 * <ul>
 * <li>une matrice (contenant les nombres de résultats entre les versions logiciels)</li>
 * </ul>
 * </p>
 * @see Recherche
 */

public class GoogleHit extends Recherche {

		private ArrayList<Integer> matrice ;
		
		/**
		 * Les constantes de la classe representant  le codage d'un espace (%20) ou
		 * d'un guillemet (%22) dans une requete Google
		 */

		final String ESPACE_GOOGLE="%20";
		final String GUILLEMET_GOOGLE="%22";
		
		/**
		 * Constructeur GoogleHit a partir des parametres de recherche.
		 * @param fromNom          
		 * @param fromVersion
		 * @param toNom    
		 * @param toVersion 
		 * 
		 * @see Recherche#fromNom
		 * @see Recherche#fromVersion
		 * @see Recherche#toNom
		 * @see Recherche#toVersion
		 */
		
		public GoogleHit(String fromNom, String fromVersion, String toNom,
				String toVersion) {
			super(fromNom, fromVersion, toNom, toVersion);
		}
		
		/**
		 * Retourne un requete google qui va permettre de recuperer le nombre de resultats renvoye par Google pour une recherche.
		 * 
		 * @return Une requete google sous forme de chaine de caractere a partir d'une recherche.
		 * @see Recherche
		 */

		public String creationRequeteGoogle(){
			return GUILLEMET_GOOGLE+"From"+ESPACE_GOOGLE+this.getFromNom()+ESPACE_GOOGLE+this.getFromVersion()+
					ESPACE_GOOGLE+"to"+ESPACE_GOOGLE+this.getToNom()+ESPACE_GOOGLE+this.getToVersion()+GUILLEMET_GOOGLE;

		}
		
}
