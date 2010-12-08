package openecho

import grails.converters.JSON
import org.springframework.beans.factory.InitializingBean

class EchoService implements InitializingBean {

    def bayeux
    def bayeuxSession

    static transactional = true

    static scope = "singleton"

    void afterPropertiesSet() {
        log.info "Configuring EchoService (${bayeux}})."

        bayeux.addListener(new EchoSessionListener());

        bayeuxSession = bayeux.newLocalSession("EchoServer")
        
        if(bayeuxSession == null) {
            log.debug "Bayeux Session is null!"
        } else {
            log.debug "Created Bayeux Session (${bayeuxSession})"
        }

        bayeuxSession.handshake()
        log.debug "Completing Handshake!"

        def channel = bayeuxSession.getChannel('/openecho/echo/publish')
        log.debug "Looking up Channel (${channel}})!"

        channel.addListener(new EchoListener())
        log.debug "Adding EchoListener to channel!"

        //bayeuxSession.getChannel('/openecho/echo/broadcast').subscribe(new EchoListener())

       
        def serverChannel = bayeux.getChannel('/openecho/echo/publish', true)
        log.debug "Looking up Channel (${serverChannel}})!"
        serverChannel.addListener(new EchoServerListener(bayeux))
        log.debug "Adding EchoListener to channel!"
       
    }

    def String sayHello(String name) {
        log.error "Saying hello to ${name}."
        return "hello ${name}"
    }

}
