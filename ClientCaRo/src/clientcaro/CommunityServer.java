/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientcaro;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import javax.json.Json;
import javax.websocket.ClientEndpoint;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
/**
 *
 * @author mrtrung
 */
@ClientEndpoint
public class CommunityServer {
    
    Session session = null;
    
    public CommunityServer() throws URISyntaxException, DeploymentException, IOException {
        URI uri = new URI(StaticVariable.url);
        ContainerProvider.getWebSocketContainer().connectToServer(this, uri);
    }
    
    @OnOpen
    public void processOpen(Session session) {
        this.session = session;
    }
    
    @OnClose
    public void processClose(Session session) {
        this.session = null;
    }
    
    @OnMessage
    public void processMessage(String message, Session session) {
        System.out.println(message);
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
