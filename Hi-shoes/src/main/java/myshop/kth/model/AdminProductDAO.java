package myshop.kth.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import member.kth.domain.AdminMemberDTO;

public interface AdminProductDAO {

	//총 주문수량 구하기
	int getTotalCountOrder(String userid) throws SQLException;
	
	
    //주문내역 조회해서 가져오기 
	List<Map<String, String>> getOrderList(Map<String, String> paraMap) throws SQLException;

	
	//배송상태를 '상품 준비중'로 변경하기
	int updatePreparingProduct(String onumSnum) throws SQLException;
	
	
    //배송상태를 '출고완료'로 변경하기
	int updateDeliverEnd(String onumSnum) throws SQLException;
	
	
	// 영수증전표 소유주에 대한 사용자 정보를 조회해오는 것. 
	AdminMemberDTO onumOwnerMemberInfo(String key) throws SQLException;

    // 페이징 처리를 위한 특정 카테고리에 대한 총페이지수(totalPage) 알아오기
	int getTotalPage(String cnum) throws SQLException;

	

}
