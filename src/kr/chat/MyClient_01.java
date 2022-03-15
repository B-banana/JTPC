package kr.chat;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class MyClient_01 {
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		
		Socket s = new Socket("192.168.0.2", 3000);
		
		
	}

}
