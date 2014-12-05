/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.asyncro.passmatters.network;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Maintains raw TCP connection to remote server via TCP socket.
 * @author Milan
 */
public class TCPSocketConnectionController extends ConnectionController {
    
    private Socket clientSocket;
    private DataOutputStream outputToServer;
    private BufferedReader inputFromServer;

    @Override
    public boolean openConnection() throws IOException 
    {
        
        clientSocket = new Socket(IP_ADDRESS, PORT);
        openStreams();

        String messageFromServer;
        messageFromServer = inputFromServer.readLine();
        return messageFromServer.equals(Protocol.CONNECTED);
    }
    
    private void openStreams() throws IOException 
    {
        outputToServer = new DataOutputStream(clientSocket.getOutputStream());
        inputFromServer = new BufferedReader(new InputStreamReader(clientSocket.
                getInputStream()));
    }
    
    @Override
    public String sendData(String data) throws IOException 
    {
        outputToServer.flush();
        outputToServer.write(data.getBytes());

        // TODO - waiting for message ??
        String messageFromServer = null;
        if(!data.equals(Protocol.LOGOUT)) {
            messageFromServer = inputFromServer.readLine();
        }
        
        return messageFromServer;
    }
    
    /**
     * Closes the TCP connection with remote server. 
     * @throws IOException 
     */
    @Override
    public boolean closeConnection() throws IOException 
    {
        sendData(Protocol.LOGOUT);
        System.out.println("before close"); // TODO remove
        clientSocket.close();
        System.out.println("after close"); // TODO remove
        return true;
    }
    
    // new Class ?
    @Override
    public void startListening() {
        new Thread(new Runnable() {
            // TODO variable listening...
            @Override
            public void run() {
                try(BufferedReader listener = new BufferedReader(
                            new InputStreamReader(clientSocket.getInputStream()))) 
                {
                    String message;
                    System.out.println("listening...");
                    while((message = listener.readLine()) != null) {
                        System.out.println("From listener: " + message);
                        // TODO handle password fill...
                    }
                    
                } catch (IOException ex) {
                    // TODO fix ??
                    System.out.println("EXCEPTION IN LISTENER: " + ex.getMessage());
                }
            }
        }).start();
    }
    
 
}