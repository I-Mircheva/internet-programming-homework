package org.elsys.netprog.sockets;

import java.io.*;
import java.net.Socket;



public class ServerThread extends Thread {


	InputStream inp = null;
	BufferedReader brinp = null;
	PrintWriter out = null;

	protected Socket socket;
	protected EchoServer server;

	public ServerThread(Socket clientSocket, EchoServer server) {
		super("ServerThread");
		this.socket = clientSocket;
		this.server = server;
	}

	public void sendString(String text){
		out.println(text);
		out.flush(); //It has to go
	}

	public void sendStringToAll(String text){
		for(ServerThread e : server.connections) {
			e.sendString(text);
		}
	}

	public void run() {
		try {
			inp = socket.getInputStream();
			out = new PrintWriter(socket.getOutputStream(), true);
			brinp = new BufferedReader(new InputStreamReader(inp));


			String line;
				try {
					while ((line = brinp.readLine()) != null) {
						sendStringToAll(line);
					if ((line == null) || line.equalsIgnoreCase("QUIT")) {
						socket.close();
						return;
					}
					}
				} catch (IOException e) {
					e.printStackTrace();
					return;
				}

		} catch (IOException e) {
			return;
		}
	}
}