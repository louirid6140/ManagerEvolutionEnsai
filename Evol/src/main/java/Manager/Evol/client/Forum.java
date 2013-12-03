package Manager.Evol.client;

import java.util.ArrayList;

public class Forum extends Recherche {
	
	private String nomForum;
	private ArrayList<String> listeTutoriels;
	private String langue;
	
	public Forum() {
		super();
	}
	public Forum(String fromNom, String fromVersion, String toNom,
			String toVersion) {
		super(fromNom, fromVersion, toNom, toVersion);
	}
	
	
	
}
