package notice.km.controller;

import java.util.HashMap;
import java.util.Map;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import notice.km.domain.NoticeDTO;
import notice.km.model.NoticeDAO;
import notice.km.model.NoticeDAO_imple;

public class NoticeEdit extends AbstractController {

    private NoticeDAO ndao = new NoticeDAO_imple();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String method = request.getMethod();

        // GET : 수정 페이지 보여주기
        if ("GET".equalsIgnoreCase(method)) {

            String nnum = request.getParameter("nnum");

            if (nnum == null || nnum.trim().isEmpty()) {
                super.setRedirect(true);
                super.setViewPage(request.getContextPath() + "/admin/callcenter/callcenter.go?tab=notice");
                return;
            }

            Map<String, String> paraMap = new HashMap<>();
            paraMap.put("nnum", nnum);

            NoticeDTO ndto = ndao.selectNoticeOne(paraMap);

            request.setAttribute("ndto", ndto);

            super.setRedirect(false);
            super.setViewPage("/WEB-INF/admin/callcenter/noticeEdit.jsp");
        }

        // POST : 수정 처리
        else if ("POST".equalsIgnoreCase(method)) {

            String nnum = request.getParameter("nnum");
            String nsubject = request.getParameter("nsubject");
            String ncontents = request.getParameter("ncontents");

            NoticeDTO ndto = new NoticeDTO();

            ndto.setNnum(Integer.parseInt(nnum));
            ndto.setNsubject(nsubject);
            ndto.setNcontents(ncontents);

            int n = ndao.noticeUpdate(ndto);

            if (n == 1) {
                super.setRedirect(false);
                super.setViewPage(
                    "WEB-INF/admin/callcenter/callcenter.jsp?tab=notice"
                );
            }
            else {
                request.setAttribute("message", "공지사항 수정에 실패했습니다.");
                super.setRedirect(false);
                super.setViewPage("/WEB-INF/admin/callcenter/noticeEdit.jsp");
            }
        }
    }
}