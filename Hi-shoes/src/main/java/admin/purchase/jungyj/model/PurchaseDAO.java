package admin.purchase.jungyj.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface PurchaseDAO {

	// 발주 전체 저장 메서드
	int purchaseAdd(Map<String, Object> paraMap) throws SQLException;

	// 발주 + 공급업체 1건 검색
	Map<String, String> selectPurchase(String purnum) throws SQLException;

	// 발주 상세 N 건 조회
	List<Map<String, String>> selectPurchaseDetail(String purnum) throws SQLException;

}
