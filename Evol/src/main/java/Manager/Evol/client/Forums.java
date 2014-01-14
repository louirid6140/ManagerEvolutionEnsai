package Manager.Evol.client;
/**
 * <b>Forums est la classe enumeree des Forums</b>
 * <p>
 * A terme ces informations devraient se retrouver dans la base de données
 * </p>
 * @see Forum
 */

public enum Forums {
	
	open{String langue="fr";public String toString(){return "fr.openclassrooms.com";}} ,
	developpez{String langue="fr";public String toString(){return "developpez.com";}}
}
