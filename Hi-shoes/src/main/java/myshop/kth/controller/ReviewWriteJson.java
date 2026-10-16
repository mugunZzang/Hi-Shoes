package myshop.kth.controller;

import org.json.JSONObject;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberDTO;
import myshop.kth.model.AdminProductDAO;
import myshop.kth.model.AdminProductDAO_imple;

public class ReviewWriteJson extends AbstractController {

    private AdminProductDAO pdao = null;

    public ReviewWriteJson() {
        pdao = new AdminProductDAO_imple();
    }

    @Override
    public void execute(HttpServletRequest request,
                        HttpServletResponse response) throws Exception {

        response.setContentType("application/json; charset=UTF-8");

        JSONObject jsonObj = new JSONObject();

        HttpSession session = request.getSession();
        MemberDTO loginuser =
                (MemberDTO) session.getAttribute("loginuser");

        // 로그인 여부 확인
        if (loginuser == null) {
            jsonObj.put("success", false);
            jsonObj.put("message", "로그인 후 이용해주세요.");
        }
        else {
            // JSP에서 전달받을 값
            String odnum = request.getParameter("odnum");
            String ratingStr = request.getParameter("rating");
            String rcontents = request.getParameter("rcontents");

            try {
                int rating = Integer.parseInt(ratingStr);

                if (odnum == null || odnum.isBlank()
                        || rating < 1 || rating > 5
                        || rcontents == null || rcontents.isBlank()) {

                    jsonObj.put("success", false);
                    jsonObj.put("message", "별점과 리뷰 내용을 확인해주세요.");

                }
                else {
                	Map<String, String> paraMap = new HashMap<>();

                	paraMap.put("odnum", odnum);
                	paraMap.put("rating", ratingStr);
                	paraMap.put("rcontents", rcontents);

                	int result = pdao.insertReview(paraMap);

                	if (result == 1) {
                	    jsonObj.put("result", 1);
                	    jsonObj.put("success", true);
                	    jsonObj.put("message", "리뷰가 등록되었습니다.");
                	}
                	else {
                	    jsonObj.put("result", 0);
                	    jsonObj.put("success", false);
                	    jsonObj.put("message", "리뷰 등록에 실패했습니다.");
                	}
                }

            } catch (NumberFormatException e) {
                jsonObj.put("success", false);
                jsonObj.put("message", "별점 값이 올바르지 않습니다.");
            }
        }

        // JSP로 포워딩하지 않고, 브라우저로 JSON 스트림을 직접 출력합니다.
        java.io.PrintWriter out = response.getWriter();
        out.print(jsonObj.toString());
        out.flush();
        out.close();

        // ⚠️ 아래 뷰페이지 이동 코드는 주석 처리하거나 지워줍니다.
        // super.setRedirect(false);
        // super.setViewPage("/WEB-INF/admin/adminJsonview.jsp");
    }
}
