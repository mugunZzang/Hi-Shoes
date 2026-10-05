package faq.km.controller;

import common.controller.AbstractController;
import faq.km.domain.FaqDTO;
import faq.km.model.FaqDAO;
import faq.km.model.FaqDAO_imple;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class FaqEdit extends AbstractController {

    private FaqDAO fdao = new FaqDAO_imple();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // GET : 수정 페이지 보여주기
        if ("GET".equalsIgnoreCase(request.getMethod())) {

            String fnum = request.getParameter("fnum");

            FaqDTO fdto = fdao.selectFaqOne(Integer.parseInt(fnum));

            request.setAttribute("fdto", fdto);

            super.setRedirect(false);
            super.setViewPage("/WEB-INF/admin/callcenter/faqEdit.jsp");
        }

        // POST : 수정하기
        else if ("POST".equalsIgnoreCase(request.getMethod())) {

            String fnum = request.getParameter("fnum");
            String category = request.getParameter("category");
            String fsubject = request.getParameter("fsubject");
            String fcontents = request.getParameter("fcontents");

            FaqDTO fdto = new FaqDTO();

            fdto.setFnum(Integer.parseInt(fnum));
            fdto.setFcategory(category);
            fdto.setFsubject(fsubject);
            fdto.setFcontents(fcontents);

            int n = fdao.faqUpdate(fdto);

            if (n == 1) {

                super.setRedirect(true);

                super.setViewPage(
                    request.getContextPath()
                    + "/admin/callcenter/callcenter.go?tab=faq"
                );
            }
        }
    }
}