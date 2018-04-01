/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebSocket;

import javax.websocket.MessageHandler;

/**
 *
 * @author mrtrung
 */
public class MessageHandlerCaRo implements MessageHandler.Whole<String> {
    
    public MessageHandlerCaRo() {
    }
    
    @Override
    public void onMessage(String message) {
        StaticVariable.StaticVariable.message = message;
    }
    
}
