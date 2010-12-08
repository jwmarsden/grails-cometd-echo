package openecho;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.cometd.bayeux.server.BayeuxServer;
import org.cometd.bayeux.server.ServerSession;

/**
 *
 * @author jmarsden
 */
public class EchoClientStore {

	private static EchoClientStore instance = null;
	
	public Map<String, EchoClient> clients;
	
	private EchoClientStore() {
		clients = new HashMap<String, EchoClient>();
	}
	
	public static EchoClientStore getInstance() {
		if(instance == null) {
			instance = new EchoClientStore();
		}
		return instance;
	}
	
	public void addClient(String id, EchoClient client) {
		clients.put(id, client);
	}
	
	public EchoClient removeClient(String id) {
		return clients.remove(id);
	}
	
	public Collection<EchoClient> getClients() {
		return clients.values();
	}
}
