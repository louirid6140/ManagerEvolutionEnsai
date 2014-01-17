package Manager.Evol.client;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.TreeMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * <b>GitHubAPI est la classe représentant les informations extraites de github. À partir des url de github on extrait des informations sur les commits et les bugs</b>
 * <p>
 * Gihub est caracterise par les informations suivantes :
 * <ul>
 * <li>le nombre de projet de la recherche initiale</li>
 * <li>le nombre de projet de la recherche finale</li>
 * </ul>
 * </p>
 * @see Recherche
 */
public class GitHubAPI extends Recherche implements Serializable {


	/**
	 * Cette classe est serializable
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Les constantes de la classe representant les caractères avant un commit et numéro de bug
	 */
	public static final String DEBUTCOMMIT = "]},{"; 
	public static final String NUMEROBUG = "\"number\":";

	/**
	 * Le nombre de projet sur la recherche initiale et sur la recherche finale
	 */
	private int nbProjetF=0;
	private int nbProjetT=0;

	/**
	 * Constructeur GitHubAPI a partir des paramètres de recherche.
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
	public GitHubAPI(String fromNom, String fromVersion, String toNom,
			String toVersion) {
		super(fromNom, fromVersion, toNom, toVersion);
	}

	

	/**
	 * Retourne le nombre de projets initiaux
	 * @return le nombre de projets initiaux
	 */
	public int getNbProjetF() {
		return nbProjetF;
	}
	
	/**
	 * Incremente le nombre de projets initiaux de 1
	 */
	public void addNbProjetF() {
		this.nbProjetF ++;
	}

	/**
	 * Retourne le nombre de projets finaux
	 * @return le nombre de projets finaux
	 */
	public int getNbProjetT() {
		return nbProjetT;
	}

	/**
	 * Incremente le nombre de projets finaux de 1
	 */
	public void addNbProjetT() {
		this.nbProjetT ++;
	}

	


}
