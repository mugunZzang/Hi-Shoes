
package myshop.kth.controller;

import java.util.Map;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberDTO;
import myshop.kth.model.AdminProductDAO;
import myshop.kth.model.AdminProductDAO_imple;

public class ReviewInfoJson extends AbstractController {

    private AdminProductDAO pdao = null;

    public ReviewInfoJson() {
        pdao = new AdminProductDAO_imple();
    }

    @Override
    public void execute(HttpServletRequest request,
                        HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        MemberDTO loginuser =
                (MemberDTO) session.getAttribute("loginuser");

     /*   // 관리자 로그인 여부 검사
        if (loginuser == null || !"admin".equals(loginuser.getUserid())) {

            request.setAttribute("message", "관리자만 이용할 수 있습니다.");
            request.setAttribute("loc", "javascript:history.back();");

            super.setRedirect(false);
            super.setViewPage("/WEB-INF/admin/adminMsg.jsp");
            return;
        } */

        // 주문상세번호 가져오기
        String odnum = request.getParameter("odnum");

        // 주문상세번호에 해당하는 리뷰 조회
        Map<String, String> reviewMap = pdao.getReviewInfo(odnum);

        // Map을 JSON으로 변환
        JSONObject jsonObj = new JSONObject(reviewMap);

        String json = jsonObj.toString();
        request.setAttribute("json", json);

        super.setRedirect(false);
        super.setViewPage("/WEB-INF/admin/adminJsonview.jsp");
    }
}
