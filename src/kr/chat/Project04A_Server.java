package kr.chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Project04A_Server {

	public static void main(String[] args) {

		ServerSocket ss = null;
		
		try {
			ss = new ServerSocket(9999);
			System.out.println("server ready");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		while(true) {
			
			try {
				Socket socket = ss.accept();
				System.out.println("client connect success!");
				
				//클라이언트의 정보를 받는 소켓 생성
				InputStream in = socket.getInputStream();
				
				//입력 스트림 : 클라이언트 신호를 받음
				DataInputStream dis = new DataInputStream(in);
				String message = dis.readUTF();
				//클라이언트에게 신호를 보내는 스트림
				OutputStream out = socket.getOutputStream();
				
				//출력 스트림
				DataOutputStream dos = new DataOutputStream(out);
				dos.writeUTF("[ECHO]" + message + "(from Server)");
				//에코로 받은 메세지 확인
				
				dos.close();
				dis.close();
				socket.close();
				System.err.println("client socket close");
				
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}	//while
		
	}	//main

}	//class
