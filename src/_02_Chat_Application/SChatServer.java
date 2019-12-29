package _02_Chat_Application;

import java.net.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.*;

public class SChatServer extends Thread implements ActionListener {
	ServerSocket sock;
	JFrame servwindow = new JFrame("Samuel Chat");
	JPanel p = new JPanel();
	JLabel status = new JLabel("Connected to server");
	JTextField msg = new JTextField("Beep Beep, I'm a sheep!");
	JPanel chat = new JPanel();
	JLabel intromsg = new JLabel("Welcome to Samuel Chat. Please, no NSFW content, and be nice.");
	JButton send = new JButton("Send Message");
	JButton check = new JButton("Load Message");
	JLabel blank = new JLabel("\n");
	Socket sock2;
	public SChatServer() throws IOException {
		sock = new ServerSocket(35001);
	}

	public void run() {
		boolean b = true;
		while(b) {
			try {
				sock2 = sock.accept();
				System.out.println("(!) Your client has connected to the server. Now launching chat...");
				chat.add(intromsg);
				p.add(status);
				p.add(blank);
				p.add(chat);
				p.add(blank);
				p.add(msg);
				p.add(send);
				p.add(check);
				check.addActionListener(this);
				send.addActionListener(this);
				servwindow.setDefaultCloseOperation(servwindow.DO_NOTHING_ON_CLOSE);
				servwindow.add(p);
				servwindow.setVisible(true);
				servwindow.pack();
			}
			catch(SocketTimeoutException e) {
				System.out.println("(!) Our connection has timed out! Please restart the server. \n Here's a more detailed log:");
				e.printStackTrace();
			}
			catch(IOException e) {
				System.out.println("(!) Our connection has encountered an input/output error! \n Here's a more detailed log:");
				e.printStackTrace();
			}
		}
		
	}

	public static void main(String[] args) {
		//16. In a new thread, create an object of the ServerGreeter class and start the thread. Don't forget the try-catch.
		System.out.println("(!) Welcome to the SamuelChat Server app.");
			try {
				SChatServer t = new SChatServer();
				System.out.println("(!) Server creation was successful. Tell users to enter port 35001 when connecting.");
				t.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		if(button == check) {
	        try {
	        	DataInputStream dis = new DataInputStream (sock2.getInputStream());
	        	if(sock2.isConnected()) {
	        		JLabel newmsg = new JLabel("<html><p style='color=rgb(120, 190, 32)'>Client: " + dis.readUTF() + "</p></html>");
	        		chat.add(newmsg);
	        		servwindow.pack();
	        	}
	        	else {
	        		JOptionPane.showMessageDialog(null, "No new messages...", "Oopsies!", JOptionPane.ERROR_MESSAGE);
	        	}
	        } catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		else {
			try {
				DataOutputStream dos = new DataOutputStream(sock2.getOutputStream());
				dos.writeUTF(msg.getText());
				JLabel newmsg = new JLabel("<html><p style='color=rgb(120, 190, 32)'>Server: " + msg.getText() + "</p></html>");
        		chat.add(newmsg);
        		servwindow.pack();
				
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		}
		
	}
}
