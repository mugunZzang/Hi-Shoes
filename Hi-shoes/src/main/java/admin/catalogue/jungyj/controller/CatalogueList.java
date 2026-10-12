package admin.catalogue.jungyj.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import admin.catalogue.jungyj.model.CatalogueDAO;
import admin.catalogue.jungyj.model.CatalogueDAO_imple;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CatalogueList extends AbstractController {

    private CatalogueDAO cataldao = new CatalogueDAO_imple();

    private static final int SIZE_PER_PAGE = 10;   // 한 페이지당 보여줄 개수
    private static final int BLOCK_SIZE = 10;      // 페이지바 한 블럭에 보여줄 페이지 수

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String productName  = request.getParameter("productName");
        String categoryName = request.getParameter("categoryName");
        String brandName    = request.getParameter("brandName");
        String isAjax       = request.getParameter("isAjax");   // AJAX 요청 여부

        String currentShowPageNo = request.getParameter("currentShowPageNo");

        if (currentShowPageNo == null) {
            currentShowPageNo = "1";
        }

        // 숫자가 아닌 값, 0 이하 값이 들어오는 경우 방어
        int currentPage;
        try {
            currentPage = Integer.parseInt(currentShowPageNo);
            if (currentPage < 1) {
                currentPage = 1;
            }
        } catch (NumberFormatException e) {
            currentPage = 1;
        }
        currentShowPageNo = String.valueOf(currentPage);


        // ============================================================
        //                    검색조건 및 페이징 정보
        // ============================================================

        Map<String, String> paraMap = new HashMap<>();

        paraMap.put("productName", productName);
        paraMap.put("categoryName", categoryName);
        paraMap.put("brandName", brandName);
        paraMap.put("currentShowPageNo", currentShowPageNo);

        // 전체 개수 (검색조건이 있으면 검색 결과 기준)
        int totalCountCatalogue = cataldao.getTotalCountCatalogue(paraMap);

        // 전체 페이지 수
        int totalPage = (int) Math.ceil((double) totalCountCatalogue / SIZE_PER_PAGE);

        // 페이징 처리된 목록
        List<Map<String, String>> catalogue_map_List = cataldao.getCatalogueList(paraMap);

        // 페이지바 (AJAX / 일반 요청 공용)
        String pageBar = makePageBar(currentPage, totalPage);


        //AJAX 요청이면 JSON으로 응답
        if ("1".equals(isAjax)) {

            JSONObject jsonObj = new JSONObject();

            jsonObj.put("catalogue_map_List", catalogue_map_List);
            jsonObj.put("totalCountCatalogue", totalCountCatalogue);
            jsonObj.put("currentShowPageNo", currentShowPageNo);
            jsonObj.put("sizePerPage", SIZE_PER_PAGE);
            jsonObj.put("totalPage", totalPage);
            jsonObj.put("pageBar", pageBar);

            request.setAttribute("json", jsonObj.toString());

            super.setRedirect(false);
            super.setViewPage("/WEB-INF/jsonview.jsp");

            return;
        }


        // 일반 요청이면 JSP로 이동
        request.setAttribute("catalogue_map_List", catalogue_map_List);
        request.setAttribute("pageBar", pageBar);
        request.setAttribute("totalCountCatalogue", totalCountCatalogue);
        request.setAttribute("currentShowPageNo", currentShowPageNo);
        request.setAttribute("sizePerPage", SIZE_PER_PAGE);

        super.setRedirect(false);
        super.setViewPage("/WEB-INF/admin/catalogue/adminCatalogueList.jsp");
    }



    // 페이지바 생성 메서드
    private String makePageBar(int currentPage, int totalPage) {

        // 검색 결과가 없으면 페이지바 없음
        if (totalPage == 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        int loop = 1;
        int pageNo = ((currentPage - 1) / BLOCK_SIZE) * BLOCK_SIZE + 1;

        // [맨처음]
        sb.append(pageItem(1, "[맨처음]", false));

        // [이전]
        if (pageNo != 1) {
            sb.append(pageItem(pageNo - 1, "[이전]", false));
        }

        // 페이지 번호
        while (!(loop > BLOCK_SIZE || pageNo > totalPage)) {
            sb.append(pageItem(pageNo, String.valueOf(pageNo), pageNo == currentPage));
            loop++;
            pageNo++;
        }

        // [다음]  (while 종료 후 pageNo 는 다음 블럭의 시작 페이지)
        if (pageNo <= totalPage) {
            sb.append(pageItem(pageNo, "[다음]", false));
        }

        // [마지막]
        sb.append(pageItem(totalPage, "[마지막]", false));

        return sb.toString();
    }

    // 페이지바의 li 하나 생성
    private String pageItem(int page, String label, boolean active) {

        return "<li class='page-item" + (active ? " active" : "") + "'>"
             + "<a class='page-link' href='#' data-page='" + page + "'>"
             + label
             + "</a>"
             + "</li>";
    }
} // end of private String makePageBar(int currentPage, int totalPage)----------------------