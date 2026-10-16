package product.km.model;

import java.util.List;
import java.util.Map;

import member.km.domain.MemberDTO;
import product.km.domain.ProductDTO;

public interface ProductDAO {

	// 상품목록 가져오기
	List<Map<String,String>> selectProductList(Map<String,String> paraMap) throws Exception;

	// 페이징 처리할 갯수 구하기
	int getTotalCountProduct(Map<String,String> paraMap) throws Exception;

	// 제품 삭제
	int productDelete(String pnum) throws Exception;

	// 카테고리 제품명 읽어오기
	List<Map<String, String>> ProductNameSelect(String searchWord) throws Exception;

	// 판매번호 채번 해오기
	int getPnumOfProduct() throws Exception;

	// 판매상품 insert 해주기
	int productInsert(ProductDTO pdto) throws Exception;
	
	// 업체별 입고 완료 총금액  
	List<Map<String, String>> selectPurchaseChart() throws Exception;
	
	// 업체별 입고 완료 갯수 
	List<Map<String, String>> selectcntChart() throws Exception;

	// 추가이미지 테이블 insert
	int product_imagefile_insert(Map<String, String> paraMap) throws Exception;

	// 해당 pnum에 해당하는 제품 정보를 불러온다.
	ProductDTO selectOneProd(String pnum) throws Exception;

	// 해당 pnum에 해당하는 추가이미지 제품 정보를 불러온다.
	List<String> selectProductImages(String pnum) throws Exception;

	// 제품 테이블 업데이트
	int productUpdate(ProductDTO pdto) throws Exception;

	// 같은 제품명이 있으면 거르는 코드
	boolean sameNameProd(String pname) throws Exception;

	// 추가이미지 해당제품에 이미지가 있으면 삭제해주기
	boolean selectAddImage(String pnum) throws Exception;

	// 추가이미지 삭제해주기
	void deleteImage(String pnum) throws Exception;

	
	
}
