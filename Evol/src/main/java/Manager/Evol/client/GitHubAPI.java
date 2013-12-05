package Manager.Evol.client;

public class GitHubAPI extends Recherche {
	private int nbCommitsAvant;
	private int nbCommitsApres;
	private long freqCommitAvant;
	private long freqCommitApres;
	private int nbBugsAvant;
	private int nbBugsApres;
	private int nbLignesCodeAvant;
	private int nbLignesCodeApres;

	

	public GitHubAPI(String fromNom, String fromVersion, String toNom,
			String toVersion) {
		super(fromNom, fromVersion, toNom, toVersion);
	}



}
