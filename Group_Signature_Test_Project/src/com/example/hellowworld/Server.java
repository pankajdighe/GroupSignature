package com.example.hellowworld;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.security.SecureRandom;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;

import org.iso200082.common.api.GroupSignatureScheme;
import org.iso200082.common.api.SchemeSelector;
import org.iso200082.common.api.ds.Signature;
import org.iso200082.common.api.exceptions.SchemeException;
import org.iso200082.common.api.parties.Issuer;
import org.iso200082.common.api.parties.Signer;
import org.iso200082.common.api.parties.Verifier;
import org.iso200082.common.util.Util;

public class Server extends Thread {

	private static int NUM_ITER = 10;
	static String identifier = "m4-nr-bigint-mixed";

	private ServerSocket serverSocket;
	
	static GroupSignatureScheme scheme;
	static Issuer issuer;
	static Signer signer;
	
	static double SERVER_MESSAGE;
	
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	
		System.setProperty("sig-scheme-impl",
				"org.iso200082.common.ISO20008Factory");
		// testJoin();

		// testCreate();

		// System.out.println("Hello World!!!");

		String serverName = "127.0.0.1";
		int port = Integer.parseInt("6067");

		try {
			Thread t = new Server(port);
			t.start();
			//testCreate();
		//	initiate();
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	
	  
	

	public Server(int port) throws IOException {
		serverSocket = new ServerSocket(port);
		serverSocket.setSoTimeout(10000);
	}

	public void run() {
		while (true) {
			try {
				//writer.append("Waiting for client on port "
						//+ serverSocket.getLocalPort() + "...");
				//writer.flush();
				//writer.close();
				Socket server = serverSocket.accept();
				System.out.println("Just connected to "
						+ server.getRemoteSocketAddress());
				DataInputStream in = new DataInputStream(
						server.getInputStream());
				//System.out.println(in.readInt());;
			
				//System.out.println("Output"+in.readInt());;

				String request=in.readUTF();
				
				System.out.println("Request From Client is "+request);
				
				if(request.equalsIgnoreCase("Create")){
					int id=in.readInt();

					NUM_ITER=id;
					System.out.println("Creating the Group..");
					testCreate();
					
					DataOutputStream out = new DataOutputStream(
							server.getOutputStream());
					out.writeDouble(SERVER_MESSAGE);
				}
				else if(request.equalsIgnoreCase("Join")){
					
					System.out.println("Joining the Group");
					int id=in.readInt();
					//joinRequest(id);
					NUM_ITER=id;

					testJoin();
					
					DataOutputStream out = new DataOutputStream(
							server.getOutputStream());
					out.writeDouble(SERVER_MESSAGE);
					
				}else if(request.equalsIgnoreCase("Sign")){
					
					System.out.println("Signing the Group");
					int id=in.readInt();
					
					int no_of_bytes=in.readInt();

					//joinRequest(id);
					NUM_ITER=id;

					testSign(no_of_bytes);
					
					DataOutputStream out = new DataOutputStream(
							server.getOutputStream());
					out.writeDouble(SERVER_MESSAGE);
					
				}else if(request.equalsIgnoreCase("Verify")){
					
					System.out.println("Signing the Group");
					int id=in.readInt();
					
					//int no_of_bytes=in.readInt();

					//joinRequest(id);
					NUM_ITER=id;

					testVerify();
					
					DataOutputStream out = new DataOutputStream(
							server.getOutputStream());
					out.writeDouble(SERVER_MESSAGE);
					
				}
				
				
				
				server.close();
			} catch (SocketTimeoutException s) {
				System.out.println("Socket timed out!");
				break;
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
		}
	}
	
	
	
	public static void initiate(){
		
		try {
			scheme = SchemeSelector.load(identifier);
			issuer = scheme.createGroup();
			
			System.out.println("SERVER Process Initiated");

		} catch (SchemeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	public static void joinRequest(int id){
		
		 try {
			signer = issuer.addMember(String.valueOf(id));
			
			System.out.println("Joined"+id);
			
		
		} catch (SchemeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}

	public static void testCreate() {

		try {

			// System.out.println(SchemeSelector.);
			GroupSignatureScheme scheme = SchemeSelector.load(identifier);

			System.out.println("Evaluating mechanism 4 creation");
			System.out.println("-----------------------------------------");
			System.out.println("Iterations: " + NUM_ITER);
			double[] results = new double[NUM_ITER];
			
			
			long begin = System.nanoTime();

			for (int i = 0; i < NUM_ITER; i++) {
				//System.out.println("--");
				Issuer is = scheme.createGroup();
				//results[i] = System.nanoTime() - begin;
				// Assert.assertNotNull(is);
			}
			
			// Util.printMeanStdDev(results);
			double time_taken=System.nanoTime() - begin;
			System.out.println("SERVER: Time taken for Creating "+time_taken);
			SERVER_MESSAGE=time_taken;
			System.out.println("-----------------------------------------");
		} catch (SchemeException ex) {
			// Assert.fail(ex.getMessage());
			System.out.println(ex.getMessage());
		}
	}

	public static void testJoin() {
		try {
			GroupSignatureScheme scheme = SchemeSelector.load(identifier);
			Issuer issuer = scheme.createGroup();

			System.out.println("Evaluating mechanism 4 joining");
			System.out.println("-----------------------------------------");
			System.out.println("Iterations: " + NUM_ITER);
			double[] results = new double[NUM_ITER];
			
			long begin = System.nanoTime();

			for (int i = 0; i < NUM_ITER; i++) {
				Signer s = issuer.addMember(String.valueOf(i));
				//results[i] = System.nanoTime() - begin;

				// Assert.assertNotNull(s);
			}
			double time_taken=System.nanoTime() - begin;
			
			// Util.printMeanStdDev(results);
			System.out.println("SERVER: Time taken for Joining  "+ time_taken);
			
			SERVER_MESSAGE=time_taken;

			System.out.println("-----------------------------------------");
		} catch (SchemeException ex) {
			// Assert.fail(ex.getMessage());
			System.out.println(ex.getMessage());

		}
	}
	
	
	 public static void testSign(int no_of_bytes)
	  {
	    try {
	    	  Random rnd = new SecureRandom();

	      GroupSignatureScheme scheme = SchemeSelector.load(identifier);
	      Issuer issuer     = scheme.createGroup();
	      Signer signer     = issuer.addMember("membr");
	      Verifier verifier = scheme.getVerifier();
	      BigInteger bsn    = signer.getLinkingBase();
	      String[] msgs = new String[NUM_ITER];
	      
	      System.out.println("Evaluating mechanism 4 signing");
	      System.out.println("-----------------------------------------"); 
	      System.out.println("Iterations:     " + NUM_ITER);
	      System.out.println("Precomputation: None");
	     // for(int i = 0; i < NUM_ITER; i++)
	       // msgs[i] = String.valueOf(rnd.nextInt());
	      
	      String[] byte_message={"Any String","Any String Any String Any String Any String Any St","Any String Any String Any String Any String Any String Any String Any StringAny String Any Stringng "};
	      
	      String message_b="";
	      if(no_of_bytes==10)
	    	  message_b=byte_message[0];
	      else if(no_of_bytes==50)
	    	  message_b=byte_message[1];
	      else if(no_of_bytes==100)
	    	  message_b=byte_message[2];			  
	    	  
	      
	      for(int i = 0; i < NUM_ITER; i++)
		        msgs[i] = message_b;
		      
	      
	      double[] results = new double[NUM_ITER];
	      for(int i = 0; i < NUM_ITER; i++)
	      {
	        long begin = System.nanoTime();
	        Signature s = signer.signMessage(msgs[i]);
	        results[i] = System.nanoTime() - begin;
	       // Assert.assertTrue(verifier.isSignatureValid(msgs[i], bsn, s));
	      }
	      
	     // Util.printMeanStdDev(results);
	      System.out.println("-----------------------------------------"); 
	    }
	    catch(SchemeException ex) {
	      //Assert.fail(ex.getMessage());
	    }    
	  }
	 
	 
	 public static void testVerify()
	  {
	    int numiter = NUM_ITER;
	    try {
	      GroupSignatureScheme scheme = SchemeSelector.load(identifier);
	      Issuer issuer  = scheme.createGroup();
	      Signer signer  = issuer.addMember("membr");
	      Verifier verif = scheme.getVerifier();
	      
	      BigInteger bsn = signer.getLinkingBase();

	      String[] msgs = new String[numiter];
	      for(int i = 0; i < numiter; i++)
	        msgs[i] = "Any String Any String Any String Any String Any St";

	      System.out.println("Evaluating mechanism 4 verification");
	      System.out.println("-----------------------------------------"); 
	      System.out.println("Iterations:     " + NUM_ITER);
	      double[] results = new double[NUM_ITER];
	        long begin = System.nanoTime();

	      for(int i = 0; i < numiter; i++)
	      {
	        Signature s = signer.signMessage(msgs[i]);
	        boolean valid = verif.isSignatureValid(msgs[i], bsn, s);
	        //results[i] = System.nanoTime() - begin;
	       // Assert.assertTrue(valid);
	      }
	     // Util.printMeanStdDev(results);
	      
	      double time_taken=System.nanoTime() - begin;
			
			// Util.printMeanStdDev(results);
			System.out.println("SERVER: Time taken for Joining  "+ time_taken);
			
			SERVER_MESSAGE=time_taken;
	      System.out.println("-----------------------------------------"); 
	    }
	    catch(Exception ex) {
	      ex.printStackTrace();
	      //Assert.fail(ex.getMessage());
	    }  
	  }

	 
	 

}
