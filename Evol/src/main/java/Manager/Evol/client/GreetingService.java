package Manager.Evol.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {
	String googleScrap(String recherche) ;
	String forumScrap(Forums nomForum,String recherche) ;
	String gitHubScrap(String fn,String fv,String tn,String tv);
}