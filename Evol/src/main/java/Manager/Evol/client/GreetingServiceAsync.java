package Manager.Evol.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	void googleScrap(String recherche,AsyncCallback<String> callback);
	void forumScrap(Forums nomForum, String recherche,AsyncCallback<String> callback);
	void gitHubScrap(String fn,String fv,String tn,String tv, AsyncCallback<String> callback);
}
