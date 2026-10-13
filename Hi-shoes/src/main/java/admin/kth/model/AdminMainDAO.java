package admin.kth.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface AdminMainDAO {
	
	    //주문건 계산하기
		int getOrderCount() throws SQLException;
	    
		//승인건 계산하기
		int getApproveCount() throws SQLException;
	    
		//출고건 계산하기
		int getDeliveryCount() throws SQLException;

		
		
		//오늘 하루 매출 건수 계산하기 
	    int getTodayDeliveryCount() throws SQLException;
		
        // 오늘 하루 매출 건수 계산하기
		int getTodaySalesCount() throws SQLException;

		// 오늘 하루 매출 금액 계산하기
		int getTodaySalesSum() throws SQLException;
		

		//재고현황 조회하기
		List<Map<String, String>> getStockList() throws SQLException;
        //공지사항 조회하기
		List<Map<String, String>> getNoticeList() throws SQLException;
        //문의사항 조회하기
		List<Map<String, String>> getQnaList() throws SQLException;
     

}
