import java.util.Map;

/**
 * @author mathemage <ha@h2o.ai>
 *         created on 18.6.17
 */
public class Util {
	// TODO use https://logging.apache.org/log4j/2.0/manual/api.html instead of System.out.print
	public static void printVector(Double[] starterVector) {
		for (Double value : starterVector) {
			System.out.print(value + " ");
		}
		System.out.println();
	}
	
	// TODO use https://logging.apache.org/log4j/2.0/manual/api.html instead of System.out.print
	public static void printConversationalHistory(Map<Integer, ConversationalMessage> conversationalHistory) {
		for (int index = 0; index < conversationalHistory.size(); index++) {
			System.out.println(conversationalHistory.get(index));
		}
	}
}
