/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webserver;

import java.io.IOException;
import java.io.StringReader;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.json.Json;
import javax.websocket.Session;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author mrtrung
 */
@ServerEndpoint("/ca-ro")
public class ServerComunication {
    public static Set<User> userPlayGame = Collections.synchronizedSet(new HashSet<User>());    
    @OnOpen
    public void processOpen(Session session) {
        User user = new User();
        user.setSession(session);
        user.setId(StaticVariable.countUser ++);
        userPlayGame.add(user);
    }
    @OnClose
    public void processClose(Session session) {
        Iterator<User> iterator = userPlayGame.iterator();
        while(iterator.hasNext()) {
            User itemUser = iterator.next();
            if(itemUser.getSession() == session) {
                userPlayGame.remove(itemUser);
                break;
            }
        }
    }
    @OnMessage
    public void processMessage(String message, Session session) throws IOException {
        ActionsUser action = this.decoderJson(message);
        session.getBasicRemote().sendText("connect to server is success" + action.getType());
        if(StaticVariable.REGISTER.equals(action.getType())) {
            this.setNameUser(action.getValue(), session);
//            session.getBasicRemote().sendText();
        } else if(StaticVariable.MESSAGE.equals(action.getType())) {
            
        }
    }
    
    public void setNameUser(String message, Session session) {
        Iterator<User> iterator = userPlayGame.iterator();
        while(iterator.hasNext()) {
            User itemUser = iterator.next();
            if(itemUser.getSession() == session) {
                itemUser.setName(message);
                itemUser.setIsPlaying(false);
            }
        }
    }
    
    public ActionsUser decoderJson(String message) {
        ActionsUser action = new ActionsUser();
        action.setType(Json.createReader(new StringReader(message)).readObject().getString("type"));
        action.setValue(Json.createReader(new StringReader(message)).readObject().getString("value"));
        return action;
    }
    
}
