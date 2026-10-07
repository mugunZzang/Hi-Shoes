package faq.km.model;

import java.util.List;
import java.util.Map;

import faq.km.domain.FaqDTO;

public interface FaqDAO {

	// faq 게시글 불러오기
	List<FaqDTO> select_faq_list(Map<String, String> paraMap) throws Exception;

	// === 전체 페이지 개수 ===
	int getTotalCountOrder(Map<String, String> paraMap) throws Exception;

	// faq 입력해주기
	int faqInsert(FaqDTO fdto) throws Exception;
	
	// faq 게시글 하나 클릭
	FaqDTO selectFaqOne(int fnum) throws Exception;

	// db 작성
	int faqUpdate(FaqDTO fdto) throws Exception;

	// faq 삭제
	int faqDelete(String num) throws Exception;

}
