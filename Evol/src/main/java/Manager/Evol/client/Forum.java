package Manager.Evol.client;

import java.util.ArrayList;

public class Forum extends Recherche {
	
	private String nomForum;
	private ArrayList<String> listeTutoriels;
	private String langue;
	
	final String espaceGoogle="%20";
	final String guillemetGoogle="%22";

	public Forum(String fromNom, String fromVersion, String toNom,
			String toVersion) {
		super(fromNom, fromVersion, toNom, toVersion);
	}
	
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
		return motCles+"+"+guillemetGoogle+rech+guillemetGoogle+"|"+guillemetGoogle+rech_col+guillemetGoogle+"+site:";

	}
	
	
}
