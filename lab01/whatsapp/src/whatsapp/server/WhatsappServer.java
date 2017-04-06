package whatsapp.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Arrays;
import java.util.List;

public class WhatsappServer {
    
    private final int SERVER_PORT;
    
    private DatagramSocket serverSocket;
    
    public WhatsappServer() {
        SERVER_PORT = 9876;
    }
    
    public static void main(String[] args) throws SocketException, IOException {
        WhatsappServer server = new WhatsappServer();
        server.run();
    }
    
    private void run() throws SocketException, IOException {
        serverSocket = new DatagramSocket(9876);
        byte[] receiveData = new byte[1024];
        byte[] sendData = new byte[1024];
        
        while(true)
        {
            DatagramPacket receivePacket
                    = new DatagramPacket(receiveData, receiveData.length);
            System.out.println("Servidor aguardando..." );
            serverSocket.receive(receivePacket);
            InetAddress IPAddress = receivePacket.getAddress();
            
            if (blacklist.contains(IPAddress.toString())) {
                continue;
            }
                    
            String sentence = new String( receivePacket.getData());
            System.out.println("Mensagem recebida: " + sentence);
            int port = receivePacket.getPort();
            String capitalizedSentence = sentence.toUpperCase();
            sendData = capitalizedSentence.getBytes();
            DatagramPacket sendPacket
                    = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            serverSocket.send(sendPacket);
        }
    }
    
}
