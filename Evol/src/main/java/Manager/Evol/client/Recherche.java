package Manager.Evol.client;

/**
 * <b> Recherche represente une recherche d'informations via Manager evolution</b>
 * <p>
 * Une Recherche est caracterise par les informations suivantes :
 * <ul>
 * <li>fromNom: le nom du logiciel passe</li>
 * <li>toNom: le nom du logiciel futur</li>
 * <li>fromVersion: la version du logiciel passe</li>
 * <li>toVersion: la version du logiciel futur</li>
 * </ul>
 * </p>
 * @see Recherche
 */
public class Recherche {
	
	/**
	 * fromNom correspond au nom du logiciel passe. Le fromNom est inchangeable
	 * e.g Junit
	 * 
	 */
	private String fromNom;
	
	/**
	 * fromVersion correspond à la version du logiciel passe. Le fromVersion est inchangeable
	 * e.g 3
	 * 
	 */
	private String fromVersion;
	
	/**
	 * toNom correspond au nom du logiciel futur (cible). Le toNom est inchangeable
	 * e.g Junit
	 * 
	 */
	private String toNom;
	
	/**
	 * toVersion correspond à la version du logiciel futur (cible). Le toVersion est inchangeable
	 * e.g 4
	 * 
	 */
	private String toVersion;

	/**
	 * Constructeur Recherche a partir de ses paramètres.
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
	public Recherche(String fromNom, String fromVersion, String toNom,
			String toVersion) {
		super();
		this.fromNom = fromNom;
		this.fromVersion = fromVersion;
		this.toNom = toNom;
		this.toVersion = toVersion;
	}
	
	/**
	 * Retourne nom du logiciel passe.
	 * 
	 * @return nom du logiciel passe.

	 */

	public String getFromNom() {
		return fromNom;
	}
	
	/**
	 * Retourne la version du logiciel passe.
	 * 
	 * @return la version du logiciel passe.

	 */
	public String getFromVersion() {
		return fromVersion;
	}
	
	/**
	 * Retourne nom du logiciel cible.
	 * 
	 * @return nom du logiciel cible.

	 */
	public String getToNom() {
		return toNom;
	}
	
	/**
	 * Retourne la version du logiciel cible.
	 * 
	 * @return la version du logiciel cible.

	 */
	public String getToVersion() {
		return toVersion;
	}
}
