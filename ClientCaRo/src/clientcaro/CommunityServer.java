/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientcaro;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.websocket.ClientEndpoint;
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
    
    public CommunityServer() throws URISyntaxException {
        URI uri = new URI(StaticVariable.url);
    }
    
    @OnOpen
    public void processOpen(Session session) {
        
    }
    
    @OnClose
    public void processClose(Session session) {
        
    }
    
    @OnMessage
    public void processMessage(String message, Session session) {
    
    }
    
    public void sendMessage(String type, String value) throws IOException {
        this.session.getBasicRemote().sendText(value);
    }
    
    private String 
}
