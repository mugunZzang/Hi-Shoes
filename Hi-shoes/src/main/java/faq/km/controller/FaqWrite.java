package faq.km.controller;


import common.controller.AbstractController;
import faq.km.domain.FaqDTO;
import faq.km.model.FaqDAO;
import faq.km.model.FaqDAO_imple;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class FaqWrite extends AbstractController {


    private FaqDAO fdao = new FaqDAO_imple();


    @Override
    public void execute(HttpServletRequest request,
                        HttpServletResponse response)
                        throws Exception {


        // =====================================================
        // GET / POST 구분
        // =====================================================

        String method = request.getMethod();


        // =====================================================
        // GET
        // =====================================================

        if("GET".equalsIgnoreCase(method)) {


            super.setRedirect(false);


            super.setViewPage(
                "/WEB-INF/admin/callcenter/faqWrite.jsp"
            );


            return;

        }


        // =====================================================
        // POST
        // =====================================================


        String category =
                request.getParameter("category");


        String fsubject =
                request.getParameter("fsubject");


        String fcontents =
                request.getParameter("fcontents");


        // =====================================================
        // 공백 제거
        // =====================================================

        if(category != null) {

            category = category.trim();

        }


        if(fsubject != null) {

            fsubject = fsubject.trim();

        }


        if(fcontents != null) {

            fcontents = fcontents.trim();

        }


        // =====================================================
        // 필수값 검사
        // =====================================================

        if(category == null
                || category.isEmpty()
                || fsubject == null
                || fsubject.isEmpty()
                || fcontents == null
                || fcontents.isEmpty()) {


            // 잘못된 요청이면 다시 작성 페이지
            super.setRedirect(false);


            super.setViewPage(
                "/WEB-INF/admin/callcenter/faqWrite.jsp"
            );


            return;

        }


        // =====================================================
        // DTO 생성
        // =====================================================

        FaqDTO fdto =
                new FaqDTO();


        fdto.setFcategory(
                category
        );


        fdto.setFsubject(
                fsubject
        );


        fdto.setFcontents(
                fcontents
        );


        // =====================================================
        // DB INSERT
        // =====================================================

        int n =
                fdao.faqInsert(fdto);


        // =====================================================
        // 성공
        // =====================================================

        if(n == 1) {

            super.setRedirect(false);


            super.setViewPage(
                "/admin/callcenter/callcenter.jsp?tab=faq"
            );


        }

        // =====================================================
        // 실패
        // =====================================================

        else {


            super.setRedirect(false);


            super.setViewPage(
                "/WEB-INF/admin/callcenter/faqWrite.jsp"
            );

        }

    }

}