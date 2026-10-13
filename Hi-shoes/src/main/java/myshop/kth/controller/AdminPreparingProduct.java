package myshop.kth.controller;

import java.sql.SQLException;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.kth.domain.AdminMemberDTO;
import myshop.kth.model.AdminProductDAO;
import myshop.kth.model.AdminProductDAO_imple;

public class AdminPreparingProduct extends AbstractController {

    private AdminProductDAO pdao = new AdminProductDAO_imple();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String method = request.getMethod();

        if(!"POST".equalsIgnoreCase(method)) {
            request.setAttribute("message", "비정상적인 경로로 들어왔습니다.");
            request.setAttribute("loc", "javascript:history.back();");
            super.setRedirect(false);
            super.setViewPage("/WEB-INF/admin/adminMsg.jsp");
            return;
        }

         // === 로그인 유무 검사하기 === //
        //(common.controller에 메서드 생성 아직 안함.)
        
       /* if(!super.checkLogin(request)) {
            request.setAttribute("message", "상품준비중 처리를 하시려면 먼저 로그인부터 하세요.");
            request.setAttribute("loc", "javascript:history.back();");
            super.setRedirect(false);
            super.setViewPage("/WEB-INF/msg.jsp");
            return;
        } */

        HttpSession session = request.getSession();
        AdminMemberDTO loginuser = (AdminMemberDTO) session.getAttribute("loginuser");
        
        // === 로그인 유무 검사하기 === //
        if(loginuser == null || !"admin".equals(loginuser.getUserid())) {
            request.setAttribute("message", "로그인 후 이용해 주세요.");
            request.setAttribute("loc", "javascript:history.back();");

            super.setRedirect(false);
            super.setViewPage("/WEB-INF/admin/adminMsg.jsp");
            return;
        }

      

        // 관리자 여부
        if(!"admin".equals(loginuser.getUserid())) {
            request.setAttribute("message", "접근불가!! 관리자가 아닙니다.");
            request.setAttribute("loc", "javascript:history.back();");
            super.setRedirect(false);
            super.setViewPage("/WEB-INF/msg.jsp");
            return;
        }

        String[] onumArr = request.getParameterValues("onum");
        String[] snumArr = request.getParameterValues("snum");

        StringBuilder sb = new StringBuilder();

        for(int i=0; i<onumArr.length; i++) {
            sb.append("'" + onumArr[i] + "/" + snumArr[i] + "',");
        }

        String onumSnum = sb.toString();

        // 마지막 콤마 제거
        onumSnum = onumSnum.substring(0, onumSnum.length()-1);

        int n = 0;

        try {
            n = pdao.updatePreparingProduct(onumSnum);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }

        if(n == onumArr.length) {

            request.setAttribute("message", "선택하신 제품들은 상품준비중으로 변경되었습니다.");
            request.setAttribute("loc", request.getContextPath() + "/admin/adminOrderList.up");

            super.setRedirect(false);
            super.setViewPage("/WEB-INF/admin/adminMsg.jsp");

        }
        else {

            request.setAttribute("message", "상품준비중 변경에 실패했습니다.");
            request.setAttribute("loc", "javascript:history.back();");

            super.setRedirect(false);
            super.setViewPage("/WEB-INF/admin/adminMsg.jsp");
        }
    }
}