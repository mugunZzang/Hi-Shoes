package catalogue.jungyj.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface CatalogueDAO {

	// 카탈로그 등록(INSERT)
	int catalogueRegister(Map<String, String> paraMap) throws SQLException;

	// 제품명 중복검사
	boolean pnameDuplicateCheck(String pname) throws SQLException;

	// 카탈로그 개수 조회(SELECT)
	int getTotalCountCatalogue(Map<String, String> paraMap) throws SQLException;

	// 검색내역이 존재하면 존재하는 검색내용을 기준으로 구분하여 카탈로그를 페이징 처리하여 조회해온다.
	List<Map<String, String>> getCatalogueList(Map<String, String> paraMap) throws SQLException;

}
