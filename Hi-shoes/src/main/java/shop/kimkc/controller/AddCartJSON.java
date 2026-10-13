package shop.kimkc.controller;

import java.util.List;
import java.util.Map;
import java.io.Console;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberDTO;
import shop.kimkc.domain.StockDTO;
import shop.kimkc.model.ProductDAO;
import shop.kimkc.model.ProductDAO_imple;

public class AddCartJSON extends AbstractController {

	private ProductDAO pdao = new ProductDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod();
		
		if (method.equals("POST")) {
			HttpSession session = request.getSession();
			String userid = ((MemberDTO) session.getAttribute("loginuser")).getUserid(); 
			
					
			String pnum = request.getParameter("pnum");
			String options = request.getParameter("options");

			// JSON 문자열을 JSON 배열로 변환
			JSONArray optionArray = new JSONArray(options);

			List<StockDTO> optionList = new ArrayList<>();
			
			for (int i = 0; i < optionArray.length(); i++) {

			    JSONObject option = optionArray.getJSONObject(i);

			    int ssize = Integer.parseInt(option.getString("ssize")); 
			    String color = option.getString("color");
			    int cqty = option.getInt("cqty");
			    
			    // 실제로는 재고 정보가 아니라 장바구니에 들어갈 정보인데, 그냥 재고 DTO써서 넘기는게 좋을듯
			    StockDTO sdto = new StockDTO();
			    sdto.setSsize(ssize);
			    sdto.setColor(color);
			    sdto.setSqty(cqty);
			    sdto.setFk_pname(pnum);
			    
			    optionList.add(sdto);

			    //System.out.println("상품번호: " + pnum);
			    //System.out.println("사이즈: " + ssize);
			    //System.out.println("색상: " + color);
			    //System.out.println("수량: " + cqty);
			}
			
			
			// 해당 옵션을 갖는 재고번호(snum) 조회하기 
			List<StockDTO> snumList = pdao.getSnumByOptions(optionList);
			
			// 실제로 장바구니에 담기
			Map<String, Object> paraMap = new HashMap<>();
			paraMap.put("userid", userid);
			paraMap.put("snumList", snumList);
			
			int n = pdao.insertCartList(paraMap);
			
			if (n == snumList.size()) {	
				// 제대로 들어감
				n = 1;
			}
			else {
				n = 0;
			}
			
			JSONObject jsonObj = new JSONObject();
		    jsonObj.put("n", n);
		    String json = jsonObj.toString();

		    request.setAttribute("json", json);

		    super.setRedirect(false);
		    super.setViewPage("/WEB-INF/jsonview.jsp");
		}
		
		else {
			request.setAttribute("message", "잘못된 접근입니다.");
		    request.setAttribute("loc", request.getContextPath() + "/index.go");
		         
		    super.setRedirect(false);
		    super.setViewPage("/WEB-INF/msg.jsp");
		         
		    return;
		}
		
		
		
	}

}
