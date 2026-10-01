package catalogue.jungyj.model;

import java.sql.SQLException;
import java.util.Map;

public interface CatalogueDAO {

	// 카탈로그 등록(INSERT)
	int catalogueRegister(Map<String, String> paraMap) throws SQLException;

	// 제품명 중복검사
	boolean pnameDuplicateCheck(String pname) throws SQLException;

}
