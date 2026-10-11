package shop.kimkc.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import shop.kimkc.domain.CatalogueDTO;
import shop.kimkc.domain.ProductDTO;
import shop.kimkc.domain.QnADTO;
import shop.kimkc.domain.StockDTO;

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


	// 선택한 상품옵션의 재고를 가져온다
	int isStockEnough(Map<String, String> paraMap) throws SQLException;


	// 해당 상품을 찜했는지 알아오기
	int isWishlisted(Map<String, String> paraMap) throws SQLException;


	// 위시리스트 등록/해제
	int changeWishStatus(Map<String, String> paraMap) throws SQLException;


	// 해당 옵션을 갖는 재고번호(snum) 조회하기
	List<StockDTO> getSnumByOptions(List<StockDTO> optionList) throws SQLException;


	// 장바구니에 선택한 옵션들 집어넣기
	int insertCartList(Map<String, Object> paraMap) throws SQLException;


	// 해당 유저의 장바구니 목록 갖고오기
	List<Map<String, String>> getCartListByUserid(String userid) throws SQLException;


	// 특정 장바구니의 수량 변경하기
	int changeCartCqty(Map<String, String> paraMap) throws SQLException;


	// 변경하려는 장바구니 목록의 재고가 충분한지 검사한다.
	boolean isStockEnoughForCart(Map<String, String> paraMap) throws SQLException;


	// 특정 상품을 장바구니에서 삭제하기
	int deleteFromCartList(Map<String, String> paraMap) throws SQLException;


	// 상품의 QnA목록 총 페이지수 가져오기
	int getTotalQnAPage(String pnum) throws SQLException;


	// 특정 페이지번호에 해당하는 QnA 목록을 조회해온다.
	List<QnADTO> getQnAList(Map<String, String> paraMap) throws SQLException;

}
