/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webserver;

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
    public void processMessage(String message, Session session) {
        ActionsUser action = this.decoderJson(message);
        if(action.getType() == StaticVariable.REGISTER) {
            this.setNameUser(action.getValue(), session);
        } else if(action.getType() == StaticVariable.MESSAGE) {
            
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
