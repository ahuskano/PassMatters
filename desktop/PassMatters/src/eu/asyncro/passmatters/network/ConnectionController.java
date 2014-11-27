/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.asyncro.passmatters.network;
/**
 *
 * @author Milan
 */
public abstract class ConnectionController {

    protected final String IP_ADDRESS = "178.62.212.164";
    protected final int PORT = 1337;
    
    protected boolean listening = true;
    
    public abstract void openConnection() throws Exception;
    public abstract void sendData(String data) throws Exception;
    public abstract void closeConnection() throws Exception;
    
    
}
