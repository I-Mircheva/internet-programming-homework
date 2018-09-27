package org.elsys.netprog.sockets;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class EchoServer {

	ArrayList<ServerThread> threads = new ArrayList<>(); // Just for the server thread code

	public static void main(String[] args) {
		new EchoServer(); // So I could use this
	}

	public EchoServer() {

		Socket clientSocket;
		ServerSocket serverSocket = null;

		try {
			serverSocket = new ServerSocket(10001);

			while(true) {

				clientSocket = serverSocket.accept();
				ServerThread thread = new ServerThread(clientSocket, this);
				thread.start();
				threads.add(thread);
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

// ORIGINAL

//public class EchoServer {
//	public static void main(String[] args) throws IOException {
//		ServerSocket serverSocket = null;
//		try {
//			serverSocket = new ServerSocket(10001);
//			Socket clientSocket = serverSocket.accept();
//			System.out.println("client connected from " + clientSocket.getInetAddress());
//			PrintWriter out =
//					new PrintWriter(clientSocket.getOutputStream(), true);
//			BufferedReader in = new BufferedReader(
//					new InputStreamReader(clientSocket.getInputStream()));
//
//			String inputLine;
//			while ((inputLine = in.readLine()) != null) {
//				out.println(inputLine);
//				System.out.println(inputLine);
//				if (inputLine.equals("exit"))
//					break;
//			}
//		} catch (Throwable t) {
//			System.out.println(t.getMessage());
//		} finally {
//			if (serverSocket != null && !serverSocket.isClosed()) {
//				serverSocket.close();
//			}
//
//			System.out.println("Server closed");
//		}
//	}
//}
