package shop.kimkc.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import shop.kimkc.domain.CatalogueDTO;
import shop.kimkc.domain.CategoryDTO;
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


	// 검색결과로 나온 상품목록의 총 페이지수 가져오기
	int getTotalProductPage(String searchKeyword) throws SQLException;


	//특정 검색어에 의한 결과를 사용자가 보고자 하는 특정 페이지번호에 해당하는 제품들을 조회해온다.
	List<ProductDTO> selectProductByKeyword(Map<String, String> paraMap) throws SQLException;


	// 판매등록된 브랜드 목록 갖고오기
	List<CatalogueDTO> getBrandList() throws SQLException;


	// 검색결과로 나온 상품목록의 총 페이지수 가져오기
	int getTotalProductPageByFilter(Map<String, String> paraMap) throws SQLException;

	
	// 특정 필터에 의한 결과를 사용자가 보고자 하는 특정 페이지번호에 해당하는 제품들을 조회 
	List<ProductDTO> selectProductByFilter(Map<String, String> paraMap) throws SQLException;

}
