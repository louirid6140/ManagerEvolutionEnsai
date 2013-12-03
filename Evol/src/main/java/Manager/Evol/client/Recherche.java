package Manager.Evol.client;

public class Recherche {

	private String fromNom;
	private String fromVersion;
	private String toNom;
	private String toVersion;
	
	public Recherche(String fromNom, String fromVersion, String toNom,
			String toVersion) {
		super();
		this.fromNom = fromNom;
		this.fromVersion = fromVersion;
		this.toNom = toNom;
		this.toVersion = toVersion;
	}


	public String getFromNom() {
		return fromNom;
	}
	public void setFromNom(String fromNom) {
		this.fromNom = fromNom;
	}
	public String getFromVersion() {
		return fromVersion;
	}
	public void setFromVersion(String fromVersion) {
		this.fromVersion = fromVersion;
	}
	public String getToNom() {
		return toNom;
	}
	public void setToNom(String toNom) {
		this.toNom = toNom;
	}
	public String getToVersion() {
		return toVersion;
	}
	public void setToVersion(String toVersion) {
		this.toVersion = toVersion;
	}
}
