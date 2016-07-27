package com.example.hellowworld;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.println("Client");

		//String serverName = "54.186.224.239";
		
		//String serverName = "130.65.157.231";
		//String serverName = "127.0.0.1";

		String serverName = "10.0.0.240";
		int port = Integer.parseInt("6067");

		try {
			File file = new File("/home/pankaj/output/intel_edison_board_server_Verify_output_1.xlt");
			// creates the file
			// creates a FileWriter Object
			FileWriter writer = new FileWriter(file);
			writer.append("\nNumber of Iterations \t Time Taken by Server \t Latency Required");

			for (int i = 15; i <=20; i++) {

				long begin = System.nanoTime();
				
			

				// Writes the content to the file
				Socket client = new Socket(serverName, port);

				System.out.println("Connecting to " + serverName + " on port "
						+ port);
				System.out.println("Just connected to "
						+ client.getRemoteSocketAddress());
				OutputStream outToServer = client.getOutputStream();
				DataOutputStream out = new DataOutputStream(outToServer);
				// out.writeUTF("Join me Pleasezzzz +
				// client.getLocalSocketAddress());


			//	String process = "Create";
			//	String process = "Join";
				//String process = "Sign";

				String process = "Verify";

				int no_of_interations = i*5;
				
//				int no_of_interations = i;


				out.writeUTF(process);
				// out.writeUTF("Join");
				out.writeInt(no_of_interations);
				//writer.append("Process " + process);
				//out.writeInt(100);



				InputStream inFromServer = client.getInputStream();
				DataInputStream in = new DataInputStream(inFromServer);
				//writer.append("\n\nNumber Of Iteration " + no_of_interations);
				//writer.append(in.readUTF());
				//writer.append("\nClient Latency Time " + time_taken);
				//writer.append("\n****************************************************");
				double val=in.readDouble();
				double time_taken = System.nanoTime() - begin;  // Unit is in nanosecond

				System.out.println("\n"+no_of_interations+"\t"+val+"\t"+String.valueOf(time_taken));

				writer.append("\n"+no_of_interations+"\t"+val+"\t"+String.valueOf(time_taken));
				client.close();
				writer.flush();


			}
		//	writer.flush();
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
