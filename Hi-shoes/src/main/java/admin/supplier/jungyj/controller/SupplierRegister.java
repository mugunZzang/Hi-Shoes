package admin.supplier.jungyj.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import admin.supplier.jungyj.model.SupplierDAO;
import admin.supplier.jungyj.model.SupplierDAO_imple;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SupplierRegister extends AbstractController {
	
	private SupplierDAO sdao = new SupplierDAO_imple();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod();
		
		if("POST".equals(method)) {
			// ajax 요청이 POST 올 떄
			String supplyName = request.getParameter("supplyName");
			String bizNo = request.getParameter("bizNo");
			String ceoName = request.getParameter("ceoName");
			String supplyTel = request.getParameter("supplyTel");
			String supplyEmail = request.getParameter("supplyEmail");
/*			
			System.out.println("~~~확인용 supplyName: " + supplyName);
			System.out.println("~~~확인용 bizNo: " + bizNo);
			System.out.println("~~~확인용 ceoName: " + ceoName);
			System.out.println("~~~확인용 supplyTel: " + supplyTel);
			System.out.println("~~~확인용 supplyEmail: " + supplyEmail);
			~~~확인용 supplyName: 테스트
			~~~확인용 bizNo: 1234567890
			~~~확인용 ceoName: 테스트
			~~~확인용 supplyTel: 01011112222
			~~~확인용 supplyEmail: text@company.com
*/			
			Map<String, String> paraMap = new HashMap<>();
			paraMap.put("supplyName", supplyName);
			paraMap.put("bizNo", bizNo);
			paraMap.put("ceoName", ceoName);
			paraMap.put("supplyTel", supplyTel);
			paraMap.put("supplyEmail", supplyEmail);
			
			try {
				int result = sdao.supplierRegister(paraMap);
				
				if(result == 1) {
					System.out.println("admin.supplier.jungyj.controller.SupllierRegister 공급업체 등록 성공");
				}

	            JSONObject jsonObj = new JSONObject();
	            jsonObj.put("result", result);
	            
	            request.setAttribute("json", jsonObj.toString());
	            
			} catch(SQLException e) {
				e.printStackTrace();
				System.out.println("admin.supplier.jungyj.controller.SupllierRegister 공급업체 등록 실패");
			}


            super.setRedirect(false);
            super.setViewPage("/WEB-INF/jsonview.jsp");
		}

	}

}
