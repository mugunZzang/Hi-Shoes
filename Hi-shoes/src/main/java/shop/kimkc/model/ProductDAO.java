package shop.kimkc.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import shop.kimkc.domain.ProductDTO;

public interface ProductDAO {

	// 검색키워드를 적용한 상품목록 가져오기
	List<ProductDTO> getProductList(String searchKeyword) throws SQLException;

	
	// 클릭한 상품의 정보를 갖고오기
	ProductDTO getProductInfo(String pnum) throws SQLException;

	// 선택한 상품의 사이즈목록 갖고오기
	List<Integer> getProductSizes(String pnum) throws SQLException;


	// 사이즈, 상품명으로 색상목록 갖고오기
	List<String> getColorBySsize(Map<String, String> paraMap) throws SQLException;

}
