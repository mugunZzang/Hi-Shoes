package product.km.model;

import java.util.List;
import java.util.Map;

import product.km.domain.ProductDTO;

public interface ProductDAO {

	// 상품목록 가져오기
	List<Map<String,String>> selectProductList(Map<String,String> paraMap) throws Exception;

	// 페이징 처리할 갯수 구하기
	int getTotalCountProduct(Map<String,String> paraMap) throws Exception;

	// 업체별 거래 갯수 가져오기
	List<Map<String, String>> sup_cnt() throws Exception;

	// 업체별 카테고리별 총 금액 가져오기
	List<Map<String, String>> sup_price() throws Exception;

	// 제품 삭제
	int productDelete(String pnum) throws Exception;

	// 카테고리 제품명 읽어오기
	List<Map<String, String>> ProductNameSelect(String searchWord) throws Exception;

	// 판매번호 채번 해오기
	int getPnumOfProduct() throws Exception;

	// 판매상품 insert 해주기
	int productInsert(ProductDTO pdto);
}
