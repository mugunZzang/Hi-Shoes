package admin.supplier.jungyj.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import admin.supplier.jungyj.domain.SupplierDTO;

public interface SupplierDAO {

	// *** 페이징 처리를 안한 공급업체 목록 보여주기 *** //
	List<SupplierDTO> selectSuppliernopaging() throws SQLException;

	// 공급업체 등록(INSERT)
	int supplierRegister(Map<String, String> paraMap) throws SQLException;

}
