package _02_Chat_Application;

import java.net.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.*;

public class SChatClient extends Thread implements ActionListener {
	JFrame servwindow = new JFrame("Samuel Chat- SERVER PORT 35001");
	JPanel p = new JPanel();
	JLabel status = new JLabel("Connected to client - port 35001");
	JTextField msg = new JTextField("Beep Beep, I'm a sheep!");
	JPanel chat = new JPanel();
	JLabel intromsg = new JLabel("Welcome to Samuel Chat. Please, no NSFW content, and be nice.");
	JButton send = new JButton("Send Message");
	JButton check = new JButton("Load Message");
	JLabel blank = new JLabel("\n");
	Socket socket;
	public SChatClient() throws IOException {
		String ip_address = JOptionPane.showInputDialog("Input the server IP Adddress.");
		int port_number = Integer.parseInt(JOptionPane.showInputDialog("Input the server port. (Hint: For SChat hosted servers, it's 35001)"));
		socket = new Socket(ip_address, port_number);
	}

	public void run() {
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

	public static void main(String[] args) {
		System.out.println("(!) Welcome to the SamuelChat Client app.");
			try {
				SChatClient t = new SChatClient();
				System.out.println("(!) Trying to connect. Please enter all details.");
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
	        	DataInputStream dis = new DataInputStream (socket.getInputStream());
	        	if(socket.isConnected()) {
	        		JLabel newmsg = new JLabel("<html><p style='color=rgb(120, 190, 32)'>Server: " + dis.readUTF() + "</p></html>");
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
				DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
				dos.writeUTF(msg.getText());
				JLabel newmsg = new JLabel("<html><p style='color=rgb(120, 190, 32)'>Client: " + msg.getText() + "</p></html>");
        		chat.add(newmsg);
        		servwindow.pack();
				
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		}
		
	}
}
