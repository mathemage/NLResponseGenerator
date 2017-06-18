import java.util.Map;

class NextResponse {

    private final static ConversationalMessage defaultMessage = new ConversationalMessage("", true);

    static String getNextMessage(Map<Integer, ConversationalMessage> conversationalHistory, int id) {
        ConversationalMessage conversationalMessage = conversationalHistory.get(id + 1);
        if (conversationalMessage == null) {
            conversationalMessage = defaultMessage;
        }
        return conversationalMessage.text;
    }

}
