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
		// TODO if we hit the dashes, the previous message was the last one of the dialogue
		int indexOfLast = conversationalHistory.size() - 1;
		if (line.equals("------------")) {
			conversationalHistory.get(indexOfLast).isLastInConversation = true;
		} else {
			int indexOfNewMsg = indexOfLast + 1;
			conversationalHistory.put(indexOfNewMsg, new ConversationalMessage(line, false));
		}
	}
	
	@Override
	public String toString() {
		return text + " " + Arrays.toString(vectorRepresentation) + (isLastInConversation ? ("\n" + "------------") : "");
	}
}
