class BootStrap {

    def echoService

    def init = { servletContext ->
        String str = echoService.sayHello("Bootstrap")
    }
    def destroy = {
    }
}
