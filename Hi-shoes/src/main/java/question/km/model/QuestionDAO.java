package question.km.model;

import java.util.List;
import java.util.Map;

import question.km.domin.QuestionDTO;

public interface QuestionDAO {

	// 문의사항 게시글 불러오기
	List<QuestionDTO> select_question_list(Map<String, String> paraMap) throws Exception;

}
