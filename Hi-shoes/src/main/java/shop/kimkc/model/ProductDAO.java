package shop.kimkc.model;

import java.sql.SQLException;
import java.util.List;

import shop.kimkc.domain.ProductDTO;

public interface ProductDAO {

	// 검색키워드를 적용한 상품목록 가져오기
	List<ProductDTO> getProductList(String searchKeyword) throws SQLException;

}
