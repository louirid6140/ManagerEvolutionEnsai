package Manager.Evol.client;
/**
 * <b>Tags est la classe enumeree des tags d'un Forums e.g "resolu"</b>
 * <p>
 * A terme ces informations devraient se retrouver dans la base de données
 * </p>
 * @see Forum
 */
public enum Tags {
	
	resolu{public String toString(){return "[Résolu]";}},
	solved{public String toString(){return "[Solved]";}},
	tutoriel{public String toString(){return "Tutoriel";}},
	tutorial{public String toString(){return "Tutorial";}}
}
