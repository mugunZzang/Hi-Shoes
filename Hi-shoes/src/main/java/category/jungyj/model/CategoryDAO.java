package category.jungyj.model;

import java.sql.SQLException;
import java.util.List;

import category.jungyj.domain.CategoryDTO;

public interface CategoryDAO {

	// 카테고리 목록 조회(SELECT)
	List<CategoryDTO> selectCategoryList() throws SQLException;

	// 카테고리 등록(INSERT)
	int categoryRegister(String catename) throws SQLException;

	// 카테고리 삭제(DELETE)
	int categoryDelete(String cateno) throws SQLException;

	
}
