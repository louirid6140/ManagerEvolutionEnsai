package Manager.Evol.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	void googleScrap(String recherche,AsyncCallback<String> callback);
	void ForumScrapOpenClassrooms(String recherche,AsyncCallback<String> callback);
}
