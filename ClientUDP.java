
import java.net.*;
import java.io.*;

//John Wirth

public class ClientUDP {

	public static void main(String[] args){
		if(args.length !=2)
		{
			System.out.println("Usage: java ClientUDP <hostname> <port>");
			return;
		}
		
		try{
			String hostname =args[0];
			int port = Integer.parseInt(args[1]);
			
			DatagramSocket socket = new DatagramSocket();
			
			BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Enter a Message:");
			String message = userInput.readLine();
			
			byte[] sendBuffer = message.getBytes();
			
			InetAddress serverAddress = InetAddress.getByName(hostname);
			DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, serverAddress, port);
			socket.send(sendPacket);
			
			byte[] receiveBuffer = new byte[1024];
			DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
			
			socket.receive(receivePacket);
			
			String response = new String(receivePacket.getData(), 0 , receivePacket.getLength());
			System.out.println("Response from server: "+ response);
			
			socket.close();
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
