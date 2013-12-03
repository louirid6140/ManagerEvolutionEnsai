package Manager.Evol.client;

import java.util.ArrayList;


public class GoogleHit extends Recherche {

		private ArrayList<Integer> matrice ;
		private  String commentaire ;
		
		final String espaceGoogle="%20";
		final String guillemetGoogle="%22";
		
		
		public GoogleHit() {
			super();
		}


		public GoogleHit(String fromNom, String fromVersion, String toNom,
				String toVersion) {
			super(fromNom, fromVersion, toNom, toVersion);
		}


		public String creationRequeteGoogle(){
			return guillemetGoogle+"From"+espaceGoogle+this.getFromNom()+espaceGoogle+this.getFromVersion()+
					espaceGoogle+"to"+espaceGoogle+this.getToNom()+espaceGoogle+this.getToVersion()+guillemetGoogle;

		}
		
}
