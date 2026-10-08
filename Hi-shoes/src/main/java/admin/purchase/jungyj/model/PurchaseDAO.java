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

	// 발주 목록 조회(SELECT) 페이징 처리 X
	List<Map<String, String>> selectPurchaseList() throws SQLException;
	
    // 전체 발주 개수
	int getTotalCountPurchase(Map<String, String> paraMap) throws SQLException;

    // 현재 페이지의 발주 목록 페이징 처리 O
	List<Map<String, String>> selectPurchaseList(Map<String, String> paraMap) throws SQLException;

	// 입고처리 버튼 클릭시 재고 테이블의 수량 UPDATE
	int purdetailStockUpdate(String purnum) throws SQLException;

}
