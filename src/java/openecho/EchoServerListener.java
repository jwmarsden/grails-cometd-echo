package openecho;

import java.util.Collection;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Map;

import openecho.json.JPath;
import openecho.json.JSON;

import org.apache.log4j.Logger;

import org.cometd.bayeux.server.ServerChannel;
import org.cometd.bayeux.server.ServerSession;
import org.cometd.bayeux.server.ServerMessage;
import org.cometd.bayeux.Bayeux;
import org.cometd.bayeux.server.BayeuxServer;

public class EchoServerListener implements ServerChannel.MessageListener {

    Logger log = Logger.getLogger("openecho.EchoServerListener");
    BayeuxServer bayeux;
    int messageNumber;
    
    public EchoServerListener(BayeuxServer bayeux) {
    	    this.bayeux = bayeux;
    	    this.messageNumber = 0;
    }
    
    public boolean onMessage(ServerSession from, ServerChannel channel, ServerMessage.Mutable message) {
        String id = from.getId();
    	log.info("Server Message From " + id + ":" + message.getJSON());
        EchoClientStore store = EchoClientStore.getInstance();
        Iterator<EchoClient> clientIterator = store.getClients().iterator();

        ServerChannel publishChannel = bayeux.getChannel("/openecho/echo/broadcast");
	
        String messageOutput = null;
        try {
		JSON json = JSON.parse(message.getJSON());
		JPath path = JPath.parse("/data/message");
		messageOutput = path.evaluate(json).getString();
	} catch (Exception e) {
		messageOutput = "Error Processing Message:" + e.toString();
	}
      
	Map<String, Object> data = new HashMap<String, Object>();
	data.put("from", id);
	data.put("message", messageOutput);
	publishChannel.publish(from, data, "" + messageNumber++);
        	
        return true;
    }

}
