import java.util.Arrays;
import java.util.Map;

/**
 * @author mathemage <ha@h2o.ai>
 *         created on 18.6.17
 */
public class ConversationalMessage {
	public String text;
	public Double[] vectorRepresentation;
	public boolean isLastInConversation;
	
	public ConversationalMessage(String text, boolean isLastInConversation) {
		this.text = text;
		this.isLastInConversation = isLastInConversation;
	}
	
	public static void appendMessageToHistory(String line, Map<Integer, ConversationalMessage> conversationalHistory) {
		int indexOfLast = conversationalHistory.size() - 1;
		if (line.equals(Constants.SEPARATOR_OF_CONVERSATIONS)) {
			conversationalHistory.get(indexOfLast).isLastInConversation = true;
		} else {
			int indexOfNewMsg = indexOfLast + 1;
			ConversationalMessage newMessage = new ConversationalMessage(line, false);
			newMessage.setVectorRepresentation(scoreSentence(newMessage.text));
			conversationalHistory.put(indexOfNewMsg, newMessage);
		}
	}
	
	public static Double[] scoreSentence(String sentence) {
		/* TODO replace mock with actual engines:
		 * - RNN
		 * - fastText
		 * - sentence similarity via basic NLP approches
		 * */
		return Constants.MOCK_VECTOR;
	}
	
	public void setVectorRepresentation(Double[] vectorRepresentation) {
		this.vectorRepresentation = vectorRepresentation;
	}
	
	@Override
	public String toString() {
		return text + " " + Arrays.toString(vectorRepresentation) +
			(isLastInConversation ? ("\n" + Constants.SEPARATOR_OF_CONVERSATIONS) : "");
	}
}
