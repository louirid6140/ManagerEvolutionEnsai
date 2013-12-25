package Manager.Evol.client;

import Manager.Evol.client.GoogleHit;

import com.google.gwt.junit.client.GWTTestCase;

public  class GoogleHitTest extends GWTTestCase  {
	
	public GoogleHit hit = new GoogleHit("Junit", "3", "Junit", "4");
	
	  public String getModuleName() {
		    return "Manager.Evol.GoogleHitJUnit";
		  }

	public void creationRequeteGoogleTest(){
		assertFalse(hit.creationRequeteGoogle().equals("%22From%20Junit%203%20to%20Junit%204%22"));
	}
	
}
