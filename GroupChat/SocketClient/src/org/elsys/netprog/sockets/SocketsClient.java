package org.elsys.netprog.sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.channels.SocketChannel;


public class SocketsClient {

	public static void main(String[] args) {

		Socket echoSocket = null;

		try {
			echoSocket = new Socket("localhost", 10001);
			ClientThread thread = new ClientThread(echoSocket);
			thread.start();

			BufferedReader stdIn = new BufferedReader(
					new InputStreamReader(System.in));

			while (true) {

				String userInput = stdIn.readLine();
				Integer id = echoSocket.getLocalPort();
				thread.sendString(id + " said: " + userInput);
			}


		} catch (Throwable t) {
			System.out.println(t.getMessage());
		} finally {
			if (echoSocket != null && !echoSocket.isClosed()) {
				try {
					echoSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
