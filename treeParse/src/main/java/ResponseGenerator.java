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
public class ResponseGenerator {
	
	private static Map<Integer, ConversationalMessage> conversationalHistory;
	
	private static void generateResponses(String inputSentence) {
		// score the sentence -> vec
		Double[] sentenceVector = getScoreSentence(inputSentence);
		System.out.print("[MOCK] got score: ");
		Util.printVector(sentenceVector);
		
		// "k-NN": priority queue
		int indexOfNearest = findNearestNeighbor(inputSentence, sentenceVector);
		printNearestNeighbor(inputSentence, indexOfNearest);
		
		// get responses
	}
	
	public static void printNearestNeighbor(String inputSentence, int indexOfNearest) {
		System.out.println(inputSentence);
		System.out.println(" -> ");
		System.out.println(conversationalHistory.get(indexOfNearest).text);
	}
	
	public static int findNearestNeighbor(String sentenceText, Double[] sentenceVector) {
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
	
/*	based on https://github.com/facebookresearch/fastText/blob/master/src/fasttext.cc#L442

	void FastText::findNN(const Matrix& wordVectors, const Vector& queryVec,
	                      int32_t k, const std::set<std::string>& banSet) {
		real queryNorm = queryVec.norm();
		if (std::abs(queryNorm) < 1e-8) {
			queryNorm = 1;
		}
		std::priority_queue<std::pair<real, std::string>> heap;
		Vector vec(args_->dim);
		for (int32_t i = 0; i < dict_->nwords(); i++) {
			std::string word = dict_->getWord(i);
			real dp = wordVectors.dotRow(queryVec, i);
			heap.push(std::make_pair(dp / queryNorm, word));
		}
		int32_t i = 0;
		while (i < k && heap.size() > 0) {
			auto it = banSet.find(heap.top().second);
			if (it == banSet.end()) {
				std::cout << heap.top().second << " " << heap.top().first << std::endl;
				i++;
			}
			heap.pop();
		}
	}
	
	void FastText::nn(int32_t k) {
		std::string queryWord;
		Vector queryVec(args_->dim);
		Matrix wordVectors(dict_->nwords(), args_->dim);
		precomputeWordVectors(wordVectors);
		std::set<std::string> banSet;
		std::cout << "Query word? ";
		while (std::cin >> queryWord) {
			banSet.clear();
			banSet.insert(queryWord);
			getVector(queryVec, queryWord);
			findNN(wordVectors, queryVec, k, banSet);
			std::cout << "Query word? ";
		}
	}*/
	
	public static void main(String[] args) {
		Map<Integer, ConversationalMessage> conversationalHistory = loadConversationalHistory();
		
		generateResponses(Constants.MOCK_STARTER);
	}
	
	public static Map<Integer, ConversationalMessage> loadConversationalHistory() {
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
