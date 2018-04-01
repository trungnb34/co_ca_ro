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
        if(StaticVariable.REGISTER.equals(action.getType())) {
            if(this.checkNameUser(action.getValue())) {
                this.setNameUser(action.getValue(), session);
                session.getUserProperties().put("name", action.getValue());
                session.getBasicRemote().sendText("true");
            } else {
                session.getBasicRemote().sendText("false");
            }
        } else if(StaticVariable.MESSAGE.equals(action.getType())) {
            
        }
    }
    
    private boolean checkNameUser(String name) {
        Iterator<User> userRegister = userPlayGame.iterator();
        boolean checkUserName = true;
        while(userRegister.hasNext()) {
            if(name.equals(userRegister.next().getName())) {
                checkUserName = false;
                break;
            }
        }
        return checkUserName;
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
