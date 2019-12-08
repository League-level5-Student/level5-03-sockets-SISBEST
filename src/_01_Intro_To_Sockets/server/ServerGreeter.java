package _01_Intro_To_Sockets.server;

import java.net.*;
import java.io.*;

public class ServerGreeter extends Thread {
	//1. Create an object of the ServerSocket class
	ServerSocket sock;
	public ServerGreeter() throws IOException {
		//2. Initialize the ServerSocket object. In the parameters,
		//   you must define the port at which the server will listen for connections.
		sock = new ServerSocket(2326);
		//*OPTIONAL* you can set a time limit for the server to wait by using the 
		//  ServerSocket's setSoTimeout(int timeInMilliSeconds) method
	}

	public void run() {
		//3. Create a boolean variable and initialize it to true.
		boolean b = true;
		//4. Make a while loop that continues looping as long as the boolean created in the previous step is true.
		while(b) {
			//5. Make a try-catch block that checks for two types Exceptions: SocketTimeoutException and IOException.
			//   Put steps 8 - 15 in the try block.
			try {
				//8. Let the user know that the server is waiting for a client to connect.
				System.out.println("(?) Waiting for a client to connect...");
				//9. Create an object of the Socket class and initialize it to serverSocket.accept();
				//   Change serverSocket to match the ServerSocket member variable you created in step 1.
				//   The program will wait her until either a client connects or the timeout expires.
				Socket sock2 = sock.accept();
				//10. Let the user know that the client has connected.
				System.out.println("(?) Client connected!");
				//11. Create a DataInputStream object. When initializing it, use the Socket object you created in step 9 to call the getInputStream() method.
				//12. Print the message from the DataInputStream object using the readUTF() method
				//13. Create a DataOutputStream object. When initializing it, use the Server object you created in step 9 to call the getOutputStream() method.
				//14. Use the DataOutputStream object to send a message to the client using the writeUTF(String message) method.
				DataInputStream dis = (DataInputStream) sock2.getInputStream();
		        dis.readUTF();
				DataOutputStream dos = (DataOutputStream) sock2.getOutputStream();
		    	dos.writeUTF("beep beep");
				//15. Close the client server
		    	sock2.close();
			}
			//6. If the program catches a SocketTimeoutException, let the user know about it and set loop's boolean variable to false.
			catch(SocketTimeoutException e) {
				System.out.println("(!) Our connection has timed out! Please restart the server. \n Here's a more detailed log:");
				e.printStackTrace();
			}
			//7. If the program catches a IOException, let the user know about it and set the loop's boolean variable to false.
			catch(IOException e) {
				System.out.println("(!) Our connection has encountered an input/output error! \n Here's a more detailed log:");
				e.printStackTrace();
			}
		}
		
	}

	public static void main(String[] args) {
		//16. In a new thread, create an object of the ServerGreeter class and start the thread. Don't forget the try-catch.
		System.out.println("(?) Starting server. Port 2326.");
			try {
				ServerGreeter t = new ServerGreeter();
				System.out.println("(D) ServerGreeter Created");
				t.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
	}
}
