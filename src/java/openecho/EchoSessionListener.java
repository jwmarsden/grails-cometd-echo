package openecho;


import org.cometd.bayeux.server.BayeuxServer;
import org.cometd.bayeux.server.ServerSession;
/**
 *
 * @author jmarsden
 */
public class EchoSessionListener implements BayeuxServer.SessionListener {

    public void sessionAdded(ServerSession session) {
        EchoClientStore store = EchoClientStore.getInstance();
        if(session.isLocalSession()) {
        	System.out.println("Local Session.");
        } else {
        	String userAgent = session.getUserAgent();
		String id = session.getId();
		store.addClient(id, new EchoClient(id));
		System.out.println("Connection with id: " + id + " (" + userAgent + ")");
        }
    }

    public void sessionRemoved(ServerSession session, boolean timedout) {
    	EchoClientStore store = EchoClientStore.getInstance();
        if(session.isLocalSession()) {
        	System.out.println("Local Session.");
        } else {
		String id = session.getId();
		System.out.println("Clean out connection with id: " + id);
		store.removeClient(id);	
        }
    }
}
