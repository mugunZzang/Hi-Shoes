package admin.purchase.jungyj.model;

import java.sql.SQLException;
import java.util.Map;

public interface PurchaseDAO {

	// 발주 전체 저장 메서드
	int purchaseAdd(Map<String, Object> paraMap) throws SQLException;

}
