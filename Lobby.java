
import java.net.*;

public class Lobby {

    MulticastSocket mcSocket;
    byte[] receiveBytes = new byte[512];
    byte[] JoinAck = new byte[512];
    DatagramPacket dpReceive;
    DatagramPacket dpSend;

    public Lobby(String mcAddress, int port) throws Exception {
        mcSocket = new MulticastSocket(port);
        mcSocket.joinGroup(InetAddress.getByName(mcAddress));
        System.out.println("Lobby Created");

    }

    public void sendStatus() throws Exception {
        //create data packet to send
    }

    public void waitForPlayers() throws Exception {
        //Receive join request
        //send ack

    }

    public static void main(String[] args) throws Exception {

    }

}
