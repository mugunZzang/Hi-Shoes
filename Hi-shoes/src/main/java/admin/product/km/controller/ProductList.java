package admin.product.km.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import product.km.model.ProductDAO;
import product.km.model.ProductDAO_imple;

public class ProductList extends AbstractController {

	private ProductDAO pdao = new ProductDAO_imple();
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		    int sizePerPage = 10;

		    int blockSize = 5;

		    int currentShowPageNo = 1;

		    String pageNo = request.getParameter("currentShowPageNo");

		    if(pageNo != null && !pageNo.trim().isEmpty()) {

		        try {
		            currentShowPageNo = Integer.parseInt(pageNo);
		        }
		        catch(NumberFormatException e) {
		            currentShowPageNo = 1;
		        }
		    }


		    // 검색어
		    String searchWord = request.getParameter("searchWord");

		    if(searchWord == null) {
		        searchWord = "";
		    }


		    // DAO에 전달할 Map
		    Map<String, String> paraMap = new HashMap<>();

		    paraMap.put("currentShowPageNo",String.valueOf(currentShowPageNo));

		    paraMap.put("searchWord",searchWord);


		    // 검색된 상품의 전체 개수
		    int totalCount = pdao.getTotalCountProduct(paraMap);


		    // 전체 페이지 수
		    int totalPage =(int)Math.ceil((double)totalCount / sizePerPage);


		    // 페이지 번호 보정
		    if(totalPage > 0 &&
		       currentShowPageNo > totalPage) {

		        currentShowPageNo = totalPage;

		        paraMap.put("currentShowPageNo",String.valueOf(currentShowPageNo));
		    }

		    if(currentShowPageNo < 1) {

		        currentShowPageNo = 1;

		        paraMap.put("currentShowPageNo","1");
		    }


		    // 상품 목록
		    List<Map<String, String>> productList = pdao.selectProductList(paraMap);


		    // 페이지바
		    String pageBar = "";

		    int startPage =((currentShowPageNo - 1) / blockSize)* blockSize + 1;

		    int endPage = startPage + blockSize - 1;

		    if(endPage > totalPage) {
		        endPage = totalPage;
		    }


		    // 이전
		    if(startPage > 1) {

		        pageBar +=
		              "<li class='page-item'>"
		            + "<a class='page-link' "
		            + "href='productList.go?currentShowPageNo="
		            + (startPage - 1)
		            + "&searchWord="
		            + java.net.URLEncoder.encode(
		                    searchWord, "UTF-8")
		            + "'>이전</a>"
		            + "</li>";

		    }
		    else {

		        pageBar +=
		              "<li class='page-item disabled'>"
		            + "<a class='page-link' href='#'>"
		            + "이전"
		            + "</a>"
		            + "</li>";
		    }


		    // 페이지 번호
		    for(int i = startPage; i <= endPage; i++) {

		        if(i == currentShowPageNo) {

		            pageBar +=
		                  "<li class='page-item active'>"
		                + "<a class='page-link' href='#'>"
		                + i
		                + "</a>"
		                + "</li>";
		        }
		        else {

		            pageBar +=
		                  "<li class='page-item'>"
		                + "<a class='page-link' "
		                + "href='productList.go?currentShowPageNo="
		                + i
		                + "&searchWord="
		                + java.net.URLEncoder.encode(
		                        searchWord, "UTF-8")
		                + "'>"
		                + i
		                + "</a>"
		                + "</li>";
		        }
		    }


		    // 다음
		    if(endPage < totalPage) {

		        pageBar +=
		              "<li class='page-item'>"
		            + "<a class='page-link' "
		            + "href='productList.go?currentShowPageNo="
		            + (endPage + 1)
		            + "&searchWord="
		            + java.net.URLEncoder.encode(
		                    searchWord, "UTF-8")
		            + "'>다음</a>"
		            + "</li>";

		    }
		    else {

		        pageBar +=
		              "<li class='page-item disabled'>"
		            + "<a class='page-link' href='#'>"
		            + "다음"
		            + "</a>"
		            + "</li>";
		    }


		    request.setAttribute("productList",productList);
		    request.setAttribute("productPageBar",pageBar);
		    request.setAttribute("currentShowPageNo",currentShowPageNo);
		    request.setAttribute("sizePerPage",sizePerPage);
		    request.setAttribute("totalCount",totalCount);
		    request.setAttribute("searchWord",searchWord);

		    super.setRedirect(false);
		    super.setViewPage("/WEB-INF/admin/product/productList.jsp");
		}
		
	}


