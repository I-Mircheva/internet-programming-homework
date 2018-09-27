package org.elsys.netprog.sockets;

import java.io.*;
import java.net.Socket;


public class ServerThread extends Thread {

	private Socket socket;
	private EchoServer server;

	private InputStream in = null;
	private BufferedReader bufferRead = null;
	private PrintWriter out = null;


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
		for(ServerThread e : server.threads) {
			e.sendString(text);
		}
	}

	public void run() {
		try {
			in = socket.getInputStream();
			out = new PrintWriter(socket.getOutputStream(), true);
			bufferRead = new BufferedReader(new InputStreamReader(in));

			String line;
				while ((line = bufferRead.readLine()) != null) {

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
	}
}