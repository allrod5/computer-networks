package com.github.allrod5.computernetworks.tcpapplication.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    public static void main (String[] args) {
        try {
            ServerSocket server = new ServerSocket(9001);
            while (true) {
                Socket client = server.accept();
                ServerThread handler = new ServerThread(client);
                handler.start();
            }
        }
        catch (Exception e) {
            System.err.println("Exception caught:" + e);
        }
    }
}

class ServerThread extends Thread {

    Socket client;
    ServerThread (Socket client) {
        this.client = client;
    }

    public void run () {
        try {
            // waits for a new connection. Accepts connetion from multiple clients
            while (true) {
                System.out.println("Esperando conexão na porta 9000");
                System.out.println("Conexão estabelecida de " + client.getInetAddress());

                // create a BufferedReader object to read strings from
                // the socket. (read strings FROM CLIENT)
                BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
                String input;

                double weight, height, imc;

                while (true) {
                    input = br.readLine();

                    if ("tchau".equals(input)) {
                        break;
                    }

                    weight = Double.parseDouble(input);

                    input = br.readLine();
                    height = Double.parseDouble(input);

                    imc = weight / (height * height);

                    //create output stream to write to/send TO CLIENT
                    DataOutputStream output = new DataOutputStream(client.getOutputStream());

                    output.writeBytes(String.valueOf(imc) + "\n");
                }
                // close current connection
                client.close();
            }
        } catch (Exception e) {
            System.err.println("Exception caught: client disconnected.");
        } finally {
            try { client.close(); }
            catch (Exception e ){

            }
        }
    }
}
