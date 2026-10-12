package admin.product.km.controller;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import product.km.model.ProductDAO;
import product.km.model.ProductDAO_imple;

public class ProductUpdate extends AbstractController {

	private ProductDAO pdao = new ProductDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String pnum = request.getParameter("pnum");
		String pcontent = request.getParameter("pcontent");
		pcontent = pcontent.replaceAll("<", "&lt;");
        pcontent = pcontent.replaceAll(">", "&gt;");
        
        // 입력한 내용에서 엔터는 <br>로 변환하기
        pcontent = pcontent.replaceAll("\r\n", "<br>");
		String deliveryfee = request.getParameter("deliveryfee");
		
		Map<String,String> paraMap = new HashMap<>();
		
		paraMap.put("pnum", pnum);
		paraMap.put("pcontent", pcontent);
		paraMap.put("deliveryfee", deliveryfee);
		
		int result = pdao.productUpdate(paraMap);
		
		JSONObject jsonObj = new JSONObject(); // {}
        jsonObj.put("result", result); // {"result":1}
        
        String json = jsonObj.toString(); // 문자열로 변환
        request.setAttribute("json", json);
		
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/jsonview.jsp");

	}

}
