package admin.supplier.jungyj.controller;

import java.util.List;

import admin.supplier.jungyj.domain.SupplierDTO;
import admin.supplier.jungyj.model.SupplierDAO;
import admin.supplier.jungyj.model.SupplierDAO_imple;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SupplierList extends AbstractController {

	private SupplierDAO sdao = new SupplierDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod();
		
		if("GET".equals(method)) {
			
			// *** 페이징 처리를 안한 공급업체 목록 보여주기 *** //
			List<SupplierDTO> memberList = sdao.selectSuppliernopaging();
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/admin/supplier/adminsupplierList.jsp");
			
		}

	}

}
