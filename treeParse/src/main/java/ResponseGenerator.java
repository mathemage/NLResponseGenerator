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
		Map<Integer, ConversationalMessage> conversationalHistory = new HashMap<>();
		try (Stream<String> lines = Files.lines(Paths.get(Constants.TRAINING_DATA_FILE), Charset.defaultCharset())) {
			lines.forEachOrdered(line -> ConversationalMessage.appendMessageToHistory(line, conversationalHistory));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Util.printConversationalHistory(conversationalHistory);
		
		generateResponses(Constants.MOCK_STARTER);
	}
	
}
