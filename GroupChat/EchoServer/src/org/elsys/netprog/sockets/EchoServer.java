package org.elsys.netprog.sockets;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class
EchoServer {

	ServerSocket serverSocket = null;
	ArrayList<ServerThread> connections = new ArrayList<>();

	public static void main(String[] args) {
		new EchoServer();
	}

	public EchoServer() {

		try {
			Socket clientSocket;
			serverSocket = new ServerSocket(10001);

			while(true) {
				clientSocket = serverSocket.accept();

				ServerThread connection = new ServerThread(clientSocket, this);
				connection.start();
				connections.add(connection);
			}

		} catch (Throwable t) {
			System.out.println(t.getMessage());
		} finally {

			if (serverSocket != null && !serverSocket.isClosed()) {
				try {
					serverSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			System.out.println("Server closed");
		}

	}
}
