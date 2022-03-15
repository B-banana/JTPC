package kr.chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

public class Project04F_MultiChatServer {

	HashMap clients;

	public Project04F_MultiChatServer() {
		clients = new HashMap();
		Collections.synchronizedMap(clients); // 동기화를 맞추어서 thread의 충돌을 막음

	}

	public void start() {
		ServerSocket serverSocket = null;
		Socket socket = null;

		try {
			serverSocket = new ServerSocket(9100);
			System.out.println("start server..");

			while (true) {

				socket = serverSocket.accept(); // 서버지만 클라이언트 기본정보 들어옴
				// ip와 포트번호출력
				System.out.println(socket.getInetAddress() + ":" + socket.getPort() + " connect!");

				// Thread 생성
				ServerReceiver thread = new ServerReceiver(socket);
				thread.start();

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 브로드캐스팅 기능 구현
	void sendToAll(String msg) {

		Iterator iterator = clients.keySet().iterator();

		while (iterator.hasNext()) { // 모든 접속자에게 알림
			try {
				DataOutputStream out = (DataOutputStream) clients.get(iterator.next());
				out.writeUTF(msg);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} // while

	}

	public static void main(String[] args) {

		new Project04F_MultiChatServer().start();

	}

	class ServerReceiver extends Thread {

		Socket socket;
		DataInputStream in;
		DataOutputStream out;

		public ServerReceiver(Socket socket) {
			this.socket = socket;

			try {
				// 클라이언트 입출력 생성
				in = new DataInputStream(socket.getInputStream());
				out = new DataOutputStream(socket.getOutputStream());

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		// chatting strat
		@Override
		public void run() {
			String name = "";

			try {
				name = in.readUTF();
				if (clients.get(name) != null) { // 같은 이름이 존재한다면
					out.writeUTF("#Already exist name : " + name);
					out.writeUTF("#Please reconnect by other name");
					System.out.println(socket.getInetAddress() + ":" + socket.getPort() + "disconnect!");

					in.close();
					out.close();
					socket.close();
					socket = null;

				} else { // 같은 이름이 존재하지 않을 경우
					sendToAll("#" + name + "Join!");
					clients.put(name, out);
					while (in != null) {
						sendToAll(in.readUTF());
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (socket != null) {
					sendToAll("#" + name + "Exit!");
					clients.remove(name);
					System.out.println(socket.getInetAddress() + ":" + socket.getPort() + "disconnect!");

				}
			}

		}

	}

}
