/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientcaro;

import WebSocket.ClientEndpoint;
import java.io.IOException;
import java.net.URISyntaxException;
import javax.websocket.DeploymentException;

/**
 *
 * @author mrtrung
 */
public class ClientCaRo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws URISyntaxException, DeploymentException, IOException, InterruptedException {
        // TODO code application logic here
        FromRegister from = new FromRegister();
        from.setVisible(true);
    }
    
}
