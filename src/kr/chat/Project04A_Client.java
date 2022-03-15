package kr.chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Project04A_Client {

	public static void main(String[] args) {

		try {
			Socket socekt = new Socket("192.168.0.2", 9999);
			System.out.println("Connection Sucess");
					
			Scanner scan = new Scanner(System.in);
			String message = scan.nextLine();
			
			OutputStream out = socekt.getOutputStream();
			
			//출력 스트림
			DataOutputStream dos = new DataOutputStream(out);
			dos.writeUTF(message); //메세지 보냄
			
			//입력 소켓
			InputStream in = socekt.getInputStream();
			
			//입력 스트림
			DataInputStream dis = new DataInputStream(in);
			System.out.println("Receve : " + dis.readUTF());
			
					
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

}
