package notice.km.model;

import java.util.List;
import java.util.Map;

import notice.km.domain.NoticeDTO;

public interface NoticeDAO {
	
	// 공지사항 게시글 불러오기
	List<NoticeDTO> select_notice_list(Map<String, String> paraMap) throws Exception;

	// === 전체 페이지 개수 ===
	int getTotalCountOrder() throws Exception;

	// 공지사항에 넣어주기
	int noticeInsert(NoticeDTO ndto) throws Exception;

}
