package question.km.model;

import java.util.List;
import java.util.Map;


public interface QuestionDAO {

	// 문의사항 게시글 불러오기
	List<Map<String,String>> select_question_list(Map<String, String> paraMap) throws Exception;

	// === 전체 페이지 개수 ===
	int getTotalCountOrder(Map<String, String> paraMap) throws Exception;
	
	// 문의사항 하나 불러오기
	Map<String,String> selectQuestionOne(int qnanum) throws Exception;

	// 문의사항 작성하기
	int questionAnswer(Map<String,String> paraMap) throws Exception;

}
