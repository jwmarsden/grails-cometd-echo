package openecho;

public class EchoClient {

	String clientId;

	public EchoClient() {
	}
	
	public EchoClient(String clientId) {
		this.clientId = clientId;
	}
	
	public String getClientId() {
		return clientId;
	}
	
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
}
