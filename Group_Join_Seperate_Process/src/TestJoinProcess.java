import org.iso200082.common.api.GroupSignatureScheme;
import org.iso200082.common.api.SchemeSelector;
import org.iso200082.common.api.exceptions.SchemeException;
import org.iso200082.common.api.parties.Issuer;
import org.iso200082.common.api.parties.Signer;


public class TestJoinProcess {

	/**
	 * @param args
	 */
	
	private static int NUM_ITER = 30;
	static String identifier = "m4-nr-bigint-mixed";
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.setProperty("sig-scheme-impl",
				"org.iso200082.common.ISO20008Factory");
		
		testJoin();

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

				System.out.println("***************************************");
				// Assert.assertNotNull(s);
			}
			double time_taken=System.nanoTime() - begin;
			
			// Util.printMeanStdDev(results);
			System.out.println("SERVER: Time taken for Joining  "+ time_taken);
			
			//SERVER_MESSAGE=time_taken;

			System.out.println("-----------------------------------------");
		} catch (SchemeException ex) {
			// Assert.fail(ex.getMessage());
			System.out.println(ex.getMessage());

		}
	}


}
