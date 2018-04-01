/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebSocket;

import StaticVariable.StaticVariable;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.json.Json;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.Session;


/**
 *
 * @author mrtrung
 */
public class ClientEndpoint extends Endpoint{
    Session session = null;
    
    public ClientEndpoint(String message) throws URISyntaxException, DeploymentException, IOException {
        URI uri = new URI(StaticVariable.url);
        ContainerProvider.getWebSocketContainer().connectToServer(this, uri);
        session.addMessageHandler(new MessageHandlerCaRo());
    }
    
    
    @Override
    public void onOpen(Session session, EndpointConfig config) {
        this.session = session;
    }
    
    public void sendMessage(String type, String value) throws IOException {
        this.session.getBasicRemote().sendText(buldJsonData(type, value));
    }

    private String buldJsonData(String type, String value) {
        return Json.createObjectBuilder().add("type", type)
                   .add("value", value)
                   .build().toString();
    }
}
