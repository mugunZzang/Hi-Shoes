package faq.km.model;

import java.util.List;
import java.util.Map;

import faq.km.domain.FaqDTO;

public interface FaqDAO {

	// faq 게시글 불러오기
	List<FaqDTO> select_faq_list(Map<String, String> paraMap) throws Exception;

}
