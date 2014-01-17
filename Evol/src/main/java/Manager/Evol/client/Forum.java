package Manager.Evol.client;

import java.util.ArrayList;
/**
 * <b>Forum est la classe representant un forum informatique web.Elle herite de la classe recherche</b>
 * <p>
 * Un Forum est caracterise par les informations suivantes :
 * <ul>
 * <li>un nom (c'est a dire une URL)</li>
 * <li>une langue (fr, en)</li>
 * </ul>
 * </p>
 * <p>
 * De plus, un Forum a une liste de tutoriels.
 * </p>
 * @see Recherche
 */

public class Forum extends Recherche {
	/**
	 * nomForum correspond a l'url du forum. Le nomForum est inchangeable
	 * e.g www.developpez.com
	 * 

	 */
	private String nomForum;

	/**
	 * La liste des tutoriels du Forum.
	 * <p>
	 * Il est possible d'ajouter ou de retirer des amis dans cette liste.
	 * <p>
	 */
	private ArrayList<String> listeTutoriels;

	/**
	 * La langue correspond a  la langue utilisee dans les tags et mot-cles du forum. 
	 * e.g fr pour un forum français
	 * 
	 */
	private String langue;

	/**
	 * Les constantes de la classe representant  le codage d'un espace (%20) ou
	 * d'un guillemet (%22) dans une requete Google
	 */

	final String ESPACE_GOOGLE="%20";
	final String GUILLEMET_GOOGLE="%22";

	/**
	 * Constructeur Forum a partir des parametres de recherche.
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

	public Forum(String fromNom, String fromVersion, String toNom,
			String toVersion) {
		super(fromNom, fromVersion, toNom, toVersion);
	}

	/**
	 * Retourne la liste des tutoriels d'un forum.
	 * 
	 * @return La liste des tutoriels du forum.
	 */
	public ArrayList<String> getListeTutoriels() {
		return listeTutoriels;
	}

	/**
	 * Met a jour la liste des tutoriels d'un forum.
	 * 
	 * @param listeTutoriels
	 * 
	 */

	public void setListeTutoriels(ArrayList<String> listeTutoriels) {
		this.listeTutoriels = listeTutoriels;
	}

	/**
	 * Retourne le nom d'un forum.
	 * 
	 * @return nom du forum.

	 */
	public String getNomForum() {
		return nomForum;
	}

	/**
	 * Retourne la langue d'un forum.
	 * 
	 * @return Langue du forum.
	 * 
	 */
	public String getLangue() {
		return langue;
	}

	/**
	 * Retourne un requete google permettant de scrapper un forum.
	 * 
	 * @return Une requete google sous forme de chaine de caractere a partir .
	 * @see MotCles
	 * @see Recherche
	 */

	public String creationRequeteForums(){
		String motCles="";
		String rech="";
		String rech_col="";
		for (MotCles mot:MotCles.values()) {
			motCles+="|"+mot.toString();
		}
		motCles=motCles.substring(1);
		rech=this.getFromNom()+"+"+this.getFromVersion();
		rech=rech.replace(" ","+");
		rech_col=rech.replace("+","");
		String partie1=motCles+"+"+GUILLEMET_GOOGLE+rech+GUILLEMET_GOOGLE+"|"+GUILLEMET_GOOGLE+rech_col+GUILLEMET_GOOGLE+"+site:";
		
		String motCles2="";
		String rech2="";
		String rech_col2="";
		for (MotCles mot:MotCles.values()) {
			motCles2+="|"+mot.toString();
		}
		motCles2=motCles2.substring(1);
		rech2=this.getToNom()+"+"+this.getToVersion();
		rech2=rech2.replace(" ","+");
		rech_col2=rech2.replace("+","");
		String partie2=motCles2+"+"+GUILLEMET_GOOGLE+rech2+GUILLEMET_GOOGLE+"|"+GUILLEMET_GOOGLE+rech_col2+GUILLEMET_GOOGLE+"+site:";
		return partie1+"PARTIE2:"+partie2;

	}



}
