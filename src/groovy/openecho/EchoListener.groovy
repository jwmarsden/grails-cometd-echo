package openecho

import org.apache.log4j.*
import org.cometd.bayeux.Message
import org.cometd.bayeux.client.ClientSessionChannel

class EchoListener implements ClientSessionChannel.MessageListener {

    Logger log = Logger.getInstance(EchoListener.class)

    public void onMessage(ClientSessionChannel channel, Message message) {
        log.error "Message:" + message.getData()
        //channel.publish(message.getData())
    }

}

