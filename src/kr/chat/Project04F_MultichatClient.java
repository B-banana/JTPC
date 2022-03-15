package kr.chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Project04F_MultichatClient {

	public static void main(String[] args) {
		try {
			Socket socket = new Socket("192.168.0.2", 9100);
			Scanner scan = new Scanner(System.in);
			System.out.println("name : ");
			String name = scan.nextLine();
			Thread sender = new Thread(new ClientSender(socket, name));
			Thread receiver = new Thread(new ClientReceiver(socket));
			
			sender.start();
			receiver.start();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// Inner class
	static class ClientSender extends Thread {

		Socket socket;
		DataOutputStream out;
		String name;

		public ClientSender(Socket socket, String name) {
			this.socket = socket;
			this.name = name;

			try {
				// 보내는 스트림 생성
				out = new DataOutputStream(socket.getOutputStream());

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		@Override
		public void run() {

			Scanner scan = new Scanner(System.in);

			try {
				if (out != null)
					out.writeUTF(name);
				while (out != null) {
					String message = scan.nextLine();
					if (message.equals("quit"))
						break;
					out.writeUTF("[" + name + "]" + message);
				}
				out.close();
				socket.close();

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	// 받는 소켓
	// Inner Class
	static class ClientReceiver extends Thread {

		Socket socket;
		DataInputStream in;
		String name;

		ClientReceiver(Socket socket) {
			this.socket = socket;

			try {
				// 보내는 스트림 생성
				in = new DataInputStream(socket.getInputStream());

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		@Override
		public void run() {

			while (in != null) {	//메세지가 있다면
				try {
					System.out.println(in.readUTF()); // 출력되는 메세지

				} catch (Exception e) {
					e.printStackTrace();
				}

			}

			try {
				in.close();
				socket.close();

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}//ClientReciver

}//class
