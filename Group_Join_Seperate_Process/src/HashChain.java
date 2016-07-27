import java.io.ObjectInputStream.GetField;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class HashChain {

	/**
	 * @param args
	 */
	static String pwd="1233444";
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double sum=0;
		
		for(int j=0;j<30;j++){

		long begin = System.nanoTime();
		for(int i=0;i<100;i++){
			pwd=gen_hash(pwd);
			
			//System.out.println(pwd);
			
		}
		double time_taken = System.nanoTime() - begin;
		System.out.println("Time Taken by Temporal ID generations for 100 iterations "+ time_taken);
		System.out.println("***********************************************************");
		
		sum+=time_taken;
		}
		
		System.out.println("Avg of 30 interations:  "+sum/30);
		
		
	}
	
	
	public static String gen_hash(String password){
	
		  StringBuffer hexString = new StringBuffer();
        MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-256");
		
        md.update(password.getBytes());
        
        byte byteData[] = md.digest();
 
        //convert the byte to hex format method 1
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
     
        //System.out.println("Hex format : " + sb.toString());
        
        //convert the byte to hex format method 2
      
    	for (int i=0;i<byteData.length;i++) {
    		String hex=Integer.toHexString(0xff & byteData[i]);
   	     	if(hex.length()==1) hexString.append('0');
   	     	hexString.append(hex);
    	}
    	//System.out.println("Hex format : " + hexString.toString());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hexString.toString();
    	
	}

}
