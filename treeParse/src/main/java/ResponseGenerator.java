import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author mathemage <ha@h2o.ai>
 *         created on 18.6.17
 */
class ResponseGenerator {
	
	private Map<Integer, ConversationalMessage> conversationalHistory;
	
	ResponseGenerator() {
		conversationalHistory = this.loadConversationalHistory();
		generateResponses(Constants.MOCK_STARTER);
	}
	
	private static Double getCosineDistance(Double[] vectorA, Double[] vectorB) {
		double dotProduct = 0.0;
		double normA = 0.0;
		double normB = 0.0;
		for (int i = 0; i < vectorA.length; i++) {
			dotProduct += vectorA[i] * vectorB[i];
			normA += Math.pow(vectorA[i], 2);
			normB += Math.pow(vectorB[i], 2);
		}
		return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
	}
	
	private static Double[] getScoreSentence(String sentence) {
		// TODO replace mock
		System.out.println("[MOCK] scoring sentence: \"" + sentence + "\"");
		return Constants.MOCK_VECTOR;
	}
	
	String generateResponses(String inputSentence) {
		// score the sentence -> vec
		Double[] sentenceVector = getScoreSentence(inputSentence);
		System.out.print("[MOCK] got score: ");
		Util.printVector(sentenceVector);
		
		// "k-NN": priority queue
		int indexOfNearest = findNearestNeighbor(inputSentence, sentenceVector);
		printNearestNeighbor(inputSentence, indexOfNearest);
		
		// get responses
		return NextResponse.getNextMessage(this.conversationalHistory, 3);
	}
	
	public void printNearestNeighbor(String inputSentence, int indexOfNearest) {
		System.out.println(inputSentence);
		System.out.println(" -> ");
		System.out.println(this.conversationalHistory.get(indexOfNearest).text);
	}
	
	public int findNearestNeighbor(String sentenceText, Double[] sentenceVector) {
		Double minDist = 1000.0;
		int minIndex = -1;
		for (int index = 0; index < conversationalHistory.size(); index++) {
			ConversationalMessage candidateNeighbor = conversationalHistory.get(index);
			if (candidateNeighbor.isLastInConversation || candidateNeighbor.text.equals(sentenceText)) {
				continue;
			}
			Double distance = getCosineDistance(sentenceVector, candidateNeighbor.vectorRepresentation);
			if (distance < minDist) {
				minDist = distance;
				minIndex = index;
			}
		}
		return minIndex;
	}
	
	private Map<Integer, ConversationalMessage> loadConversationalHistory() {
		conversationalHistory = new HashMap<>();
		try (Stream<String> lines = Files.lines(Paths.get(Constants.TRAINING_DATA_FILE), Charset.defaultCharset())) {
			lines.forEachOrdered(line -> Util.appendMessageToHistory(line, conversationalHistory));
		} catch (IOException e) {
			e.printStackTrace();
		}
//		Util.printConversationalHistory(conversationalHistory);
		return conversationalHistory;
	}
	
}
