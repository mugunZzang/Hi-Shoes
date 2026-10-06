package product.km.model;

import java.util.List;
import java.util.Map;

public interface ProductDAO {

	// 상품목록 가져오기
	List<Map<String,String>> selectProductList(Map<String,String> paraMap) throws Exception;

	// 페이징 처리할 갯수 구하기
	int getTotalCountProduct(Map<String,String> paraMap) throws Exception;
}
