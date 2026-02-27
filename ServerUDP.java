import java.net.*;
import java.io.*;

//Jessica Lyons
public class ServerUDP {

    public static void main(String[] args) {

        if (args.length != 1) {
            System.err.println("Usage: java ServerUDP <port>");
            System.exit(1);
        }

        int port = Integer.parseInt(args[0]);
        DatagramSocket sock = null;

        try {
            sock = new DatagramSocket(port);
            System.out.println("Server is listening on port " + port);
            System.out.println("Waiting for the client message...\n");

            String hostName = InetAddress.getLocalHost().getHostName();

            while (true) {

                byte[] receiveBuff = new byte[1024];

                DatagramPacket receivePack = new DatagramPacket(receiveBuff, receiveBuff.length);

                sock.receive(receivePack);

                String receivedMsg = new String(receivePack.getData(), 0, receivePack.getLength());
                System.out.println("Received from the client: " + receivedMsg);

                String upperCaseMsg = receivedMsg.toUpperCase();

                String responseMsg = upperCaseMsg + " from " + hostName;
                System.out.println("sending to the client: " + responseMsg + "\n");

                InetAddress clientAddress = receivePack.getAddress();
                int clientPort = receivePack.getPort();

                byte[] sendBuff = responseMsg.getBytes();

                DatagramPacket sendPack = new DatagramPacket(sendBuff, sendBuff.length, clientAddress,
                        clientPort);

                sock.send(sendPack);
            }

        } catch (NumberFormatException e) {
            System.err.println("Error: Port number must be an integer.");
            System.exit(1);
        } catch (SocketException e) {
            System.err.println("Socket error: " + e.getMessage());
        } catch (UnknownHostException e) {
            System.err.println("Couldn't get the local hostname: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("I/O error: " + e.getMessage());
        } finally {
            if (sock != null && !sock.isClosed()) {
                sock.close();
                System.out.println("Server socket is closed");
            }
        }
    }
}