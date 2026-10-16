package shop.kimkc.controller;

import common.controller.AbstractController;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import shop.kimkc.domain.QnADTO;
import shop.kimkc.model.ProductDAO;
import shop.kimkc.model.ProductDAO_imple;

public class GetQnAList extends AbstractController {

	private ProductDAO pdao = new ProductDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String pnum = request.getParameter("pnum");
        String currentPage = request.getParameter("currentShowPageNo_QnA");

        if (currentPage == null) {
            currentPage = "1";
        }

        int totalPage = pdao.getTotalQnAPage(pnum);

        // 페이지 번호 검증
        try {
            int page = Integer.parseInt(currentPage);

            if (page < 1 || page > totalPage) {
                currentPage = "1";
            }
        } catch (NumberFormatException e) {
            currentPage = "1";
        }

        // QnA 목록 조회
        Map<String, String> paraMap = new HashMap<>();
        paraMap.put("pnum", pnum);
        paraMap.put("currentShowPageNo_QnA", currentPage);

        List<QnADTO> qnaList = pdao.getQnAList(paraMap);

        request.setAttribute("qnaList", qnaList);
        request.setAttribute("pnum", pnum);
        request.setAttribute("currentShowPageNo_QnA", currentPage);

        // 페이지바 생성
        StringBuilder pageBar = new StringBuilder();

        int blockSize = 10;
        int currentPageNo = Integer.parseInt(currentPage);

        if (totalPage > 0) {

            int pageNo =
                ((currentPageNo - 1) / blockSize) * blockSize + 1;

            // 맨 처음
            pageBar.append(
                "<li class='page-item'>" +
                "<a class='page-link' href='javascript:void(0);' " +
                "onclick='getQnaList(" + pnum + ",1)'>&lt;&lt;</a></li>"
            );

            // 이전 블록
            if (pageNo > 1) {
                pageBar.append(
                    "<li class='page-item'>" +
                    "<a class='page-link' href='javascript:void(0);' " +
                    "onclick='getQnaList(" + pnum + "," + (pageNo - 1) +
                    ")'>&lt;</a></li>"
                );
            }

            int loop = 1;

            while (loop <= blockSize && pageNo <= totalPage) {

                if (pageNo == currentPageNo) {
                    pageBar.append(
                        "<li class='page-item active'>" +
                        "<a class='page-link' href='javascript:void(0);'>" +
                        "onclick='getQnaList(" + pnum + "," + pageNo + ")'>" +
                        pageNo + "</a></li>"
                    );
                } else {
                    pageBar.append(
                        "<li class='page-item'>" +
                        "<a class='page-link' href='javascript:void(0);' " +
                        "onclick='getQnaList(" + pnum + "," + pageNo + ")'>" +
                        pageNo + "</a></li>"
                    );
                }

                pageNo++;
                loop++;
            }

            // 다음 블록
            if (pageNo <= totalPage) {
                pageBar.append(
                    "<li class='page-item'>" +
                    "<a class='page-link' href='javascript:void(0);' " +
                    "onclick='getQnaList(" + pnum + "," + pageNo +
                    ")'>&gt;</a></li>"
                );
            }

            // 마지막 페이지
            pageBar.append(
                "<li class='page-item'>" +
                "<a class='page-link' href='javascript:void(0);' " +
                "onclick='getQnaList(" + pnum + "," + totalPage +
                ")'>&gt;&gt;</a></li>"
            );
        }

        request.setAttribute("pageBar_QnA", pageBar.toString());

        // 전체 페이지 대신 QnA 조각 JSP만 반환
        super.setRedirect(false);
        super.setViewPage("/WEB-INF/kimkc/shop/qnaList.jsp");

		
	}

}
