package com.example.hellowworld;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

import org.iso200082.common.api.GroupSignatureScheme;
import org.iso200082.common.api.SchemeSelector;
import org.iso200082.common.api.ds.Signature;
import org.iso200082.common.api.exceptions.SchemeException;
import org.iso200082.common.api.parties.Issuer;
import org.iso200082.common.api.parties.Signer;
import org.iso200082.common.api.parties.Verifier;

public class TestByte {

	/**
	 * @param args
	 */
	private static int NUM_ITER = 30;
	static String identifier = "m4-nr-bigint-mixed";
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		
		System.out.println("Test!!!");
		
		/*byte[] byte_10 = "Any String".getBytes();
		
		
		byte[] byte_50 = "Any String Any String Any String Any String Any St".getBytes();
		
		byte[] byte_100 = "Any String Any String Any String Any String Any String Any String Any StringAny String Any Stringng ".getBytes();

		
		System.out.println(byte_10.length);
		System.out.println(byte_50.length);
		System.out.println(byte_100.length);*/
		
		System.setProperty("sig-scheme-impl",
				"org.iso200082.common.ISO20008Factory");
		

			long begin = System.nanoTime();
			
			int no_of_byte=Integer.parseInt(args[0]);
			
			testVerify_vary_message_size(no_of_byte);
			
			double time_taken = System.nanoTime() - begin;
			
			System.out.println("Time Taken is "+time_taken);
		
		


	}
	
	
	public static void testVerify_vary_message_size(int no_of_bytes)
	  {
		 NUM_ITER=30;
	    int numiter = NUM_ITER;
	    try {
	      GroupSignatureScheme scheme = SchemeSelector.load(identifier);
	      Issuer issuer  = scheme.createGroup();
	      Signer signer  = issuer.addMember("membr");
	      Verifier verif = scheme.getVerifier();
	      
	      BigInteger bsn = signer.getLinkingBase();
	      
	      
	      String[] byte_message={"Any String","Any String Any String Any String Any String Any St","Any String Any String Any String Any String Any String Any String Any StringAny String Any Stringng "};
	      
	      String message_b="";
	      if(no_of_bytes==10)
	    	  message_b=byte_message[0];
	      else if(no_of_bytes==50)
	    	  message_b=byte_message[1];
	      else if(no_of_bytes==100)
	    	  message_b=byte_message[2];

	      String[] msgs = new String[numiter];
	      for(int i = 0; i < numiter; i++)
	        msgs[i] = message_b;

	      System.out.println("Evaluating mechanism 4 verification");
	      System.out.println("-----------------------------------------"); 
	      System.out.println("Iterations:     " + NUM_ITER);
	      double[] results = new double[NUM_ITER];
	      for(int i = 0; i < numiter; i++)
	      {
	        Signature s = signer.signMessage(msgs[i]);
	        long begin = System.nanoTime();
	        boolean valid = verif.isSignatureValid(msgs[i], bsn, s);
	        results[i] = System.nanoTime() - begin;
	       // Assert.assertTrue(valid);
	      }
	     // Util.printMeanStdDev(results);
	      System.out.println("-----------------------------------------"); 
	    }
	    catch(Exception ex) {
	      ex.printStackTrace();
	      //Assert.fail(ex.getMessage());
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


}
