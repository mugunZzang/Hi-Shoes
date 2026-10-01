package catalogue.jungyj.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import catalogue.jungyj.model.CatalogueDAO;
import catalogue.jungyj.model.CatalogueDAO_imple;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CatalogueList extends AbstractController {

    private CatalogueDAO cataldao = new CatalogueDAO_imple();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String productName = request.getParameter("productName");
        String categoryName = request.getParameter("categoryName");
        String brandName = request.getParameter("brandName");

        System.out.println("확인용 productName: "+productName);
        System.out.println("확인용 categoryName: "+categoryName);
        System.out.println("확인용 brandName: "+brandName);
        
        // AJAX 요청인지 확인
        String isAjax = request.getParameter("isAjax");


        // ============================================================
        //                            페이징
        // ============================================================

        String currentShowPageNo = request.getParameter("currentShowPageNo");

        if (currentShowPageNo == null) {
            currentShowPageNo = "1";
        }

        int sizePerPage = 10;

        // Map에 검색조건 및 페이징 정보 담기

        Map<String, String> paraMap = new HashMap<>();

        paraMap.put("productName", productName);
        paraMap.put("categoryName", categoryName);
        paraMap.put("brandName", brandName);
        paraMap.put("currentShowPageNo", currentShowPageNo);


        //  전체 카탈로그 개수 조회
        //  Ajax 요청시 검색 내역에 맞는 개수로 변환
        int totalCountCatalogue = cataldao.getTotalCountCatalogue(paraMap);


        // 전체 페이지 개수
        int totalPage =  (int) Math.ceil( (double) totalCountCatalogue / sizePerPage );


        // 페이징 처리된 카탈로그 목록 조회
        List<Map<String, String>> catalogue_map_List = cataldao.getCatalogueList(paraMap);


        // ============================================================
        //                  AJAX 요청이면 JSON으로 응답
        // ============================================================

        if ("1".equals(isAjax)) {

            JSONObject jsonObj = new JSONObject();

            jsonObj.put("catalogue_map_List", catalogue_map_List);
            jsonObj.put("totalCountCatalogue", totalCountCatalogue);
            jsonObj.put("currentShowPageNo", currentShowPageNo);
            jsonObj.put("sizePerPage", sizePerPage);
            jsonObj.put("totalPage", totalPage);

            String json = jsonObj.toString();

            request.setAttribute("json", json);
//            System.out.println("확인용 json" + json);

            super.setRedirect(false);
            super.setViewPage("/WEB-INF/jsonview.jsp");

            return;
        }


        // ============================================================
        //                     일반 요청이면 JSP로 이동
        // ============================================================

        request.setAttribute("catalogue_map_List", catalogue_map_List);


        // 페이지바 생성

        String pageBar = "";
        int blockSize = 10;
        int loop = 1;
        int pageNo =  ((Integer.parseInt(currentShowPageNo) - 1) / blockSize) * blockSize + 1;


        // [맨처음]
        pageBar += "<li class='page-item'>"
		        + "<a class='page-link' "
		        + "href='catalogueList.go?currentShowPageNo=1'>"
		        + "[맨처음]"
		        + "</a>"
		        + "</li>";

        // [이전]
        if (pageNo != 1) {
            pageBar += "<li class='page-item'>"
            		+ "<a class='page-link' "
            		+ "href='catalogueList.go?currentShowPageNo="
            		+ (pageNo - 1)
            		+ "'>"
            		+ "[이전]"
            		+ "</a>"
            		+ "</li>";
        }

        // 페이지 번호
        while (!(loop > blockSize || pageNo > totalPage)) {

            if (pageNo == Integer.parseInt(currentShowPageNo)) {

                pageBar += "<li class='page-item active'>"
                		+ "<a class='page-link' href='#'>"
                		+ pageNo
                		+ "</a>"
                		+ "</li>";

            } else {
                pageBar += "<li class='page-item'>"
                		+ "<a class='page-link' "
                		+ "href='catalogueList.go?currentShowPageNo="
                		+ pageNo
                		+ "'>"
                		+ pageNo
                		+ "</a>"
                		+ "</li>";
            }
            loop++;
            pageNo++;
        }


        // [다음]
        if (pageNo <= totalPage) {

            pageBar += "<li class='page-item'>"
            		+ "<a class='page-link' "
            		+ "href='catalogueList.go?currentShowPageNo="
            		+ pageNo
            		+ "'>"
            		+ "[다음]"
            		+ "</a>"
            		+ "</li>";
        }


        // [마지막]
        pageBar += "<li class='page-item'>"
        		+ "<a class='page-link' "
        		+ "href='catalogueList.go?currentShowPageNo="
        		+ totalPage
        		+ "'>"
        		+ "[마지막]"
        		+ "</a>"
        		+ "</li>";


        // JSP에서 사용할 데이터 저장
        request.setAttribute("pageBar", pageBar);
        request.setAttribute("totalCountCatalogue", totalCountCatalogue);

        request.setAttribute("currentShowPageNo", currentShowPageNo);

        request.setAttribute("sizePerPage", sizePerPage);

        
        super.setRedirect(false);
        super.setViewPage("/WEB-INF/admin/catalogue/adminCatalogueList.jsp");
    }
}
