package member.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberDTO;
import member.model.MemberDAO;
import member.model.MemberDAO_imple;

public class WishListJSON extends AbstractController {

    private MemberDAO mdao = new MemberDAO_imple();

    private static final int SIZE_PER_PAGE = 8;
    private static final int BLOCK_SIZE = 10;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        JSONObject jsonObj = new JSONObject();


	    HttpSession session = request.getSession();
	      
	    MemberDTO loginuser = (MemberDTO)session.getAttribute("loginuser");
        String userid = loginuser.getUserid();

        int currentPage;
        try {
            currentPage = Integer.parseInt(request.getParameter("currentShowPageNo"));
            if (currentPage < 1) currentPage = 1;
        } catch (NumberFormatException e) {
            currentPage = 1;
        }

        int totalCount = mdao.getTotalCountWishList(userid);
        int totalPage  = (int) Math.ceil((double) totalCount / SIZE_PER_PAGE);

        // 마지막 페이지 상품을 전부 삭제한 직후처럼 범위를 벗어나면 마지막 페이지로 보정
        if (totalPage > 0 && currentPage > totalPage) {
            currentPage = totalPage;
        }

        Map<String, String> paraMap = new HashMap<>();
        paraMap.put("userid", userid);
        paraMap.put("currentShowPageNo", String.valueOf(currentPage));
        paraMap.put("sizePerPage", String.valueOf(SIZE_PER_PAGE));

        List<Map<String, String>> wishList = mdao.wishListPaging(paraMap);

        jsonObj.put("wishList", wishList);
        jsonObj.put("totalCount", totalCount);
        jsonObj.put("totalPage", totalPage);
        jsonObj.put("currentShowPageNo", currentPage);
        jsonObj.put("pageBar", makePageBar(currentPage, totalPage));

        request.setAttribute("json", jsonObj.toString());
        super.setRedirect(false);
        super.setViewPage("/WEB-INF/jsonview.jsp");
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
    } // end of private String makePageBar(int currentPage, int totalPage)-----------------

    // 페이지바의 li 하나 생성
    private String pageItem(int page, String label, boolean active) {

        return "<li class='page-item" + (active ? " active" : "") + "'>"
             + "<a class='page-link' href='#' data-page='" + page + "'>"
             + label
             + "</a>"
             + "</li>";
    }
} // end of private String makePageBar(int currentPage, int totalPage)----------------------
