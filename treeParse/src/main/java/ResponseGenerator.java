/**
 * @author mathemage <ha@h2o.ai>
 *         created on 18.6.17
 */
public class ResponseGenerator {
	private static final String mockStarter = "The thing is, Cameron -- I'm at the mercy of a particularly hideous breed of loser. " +
		"My sister. I can't date until she does. ";
	private static final Double[] mockVector = new Double[]{1.0, 2.0, 3.14159, 4.0};
	
	private static void generateResponses(String starterSentence) {
		// score the sentence -> vec
		Double[] starterVector = getScoreSentence(starterSentence);
		System.out.print("[MOCK] got score: ");
		Util.printVector(starterVector);
		
		// "k-NN": priority queue
		
		// get responses
	}
	
	private static Double[] getScoreSentence(String sentence) {
		// TODO replace mock
		System.out.println("[MOCK] scoring sentece: \"" + sentence + "\"");
		return mockVector;
	}
	
	public static void main(String[] args) {
		generateResponses(mockStarter);
	}
}
