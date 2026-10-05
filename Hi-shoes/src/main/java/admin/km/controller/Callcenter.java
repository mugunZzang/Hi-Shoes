package admin.km.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.controller.AbstractController;
import faq.km.domain.FaqDTO;
import faq.km.model.FaqDAO;
import faq.km.model.FaqDAO_imple;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import member.km.domain.MemberDTO;
import notice.km.domain.NoticeDTO;
import notice.km.model.NoticeDAO;
import notice.km.model.NoticeDAO_imple;
import question.km.model.QuestionDAO;
import question.km.model.QuestionDAO_imple;


public class Callcenter extends AbstractController {


    private NoticeDAO ndao = new NoticeDAO_imple();

    private FaqDAO fdao = new FaqDAO_imple();

    private QuestionDAO qdao = new QuestionDAO_imple();


    @Override
    public void execute(HttpServletRequest request,
                        HttpServletResponse response) throws Exception {


        // =====================================================
        // 1. 기본값
        // =====================================================

        String tab = request.getParameter("tab");

        String searchType = request.getParameter("searchType");

        String searchWord = request.getParameter("searchWord");

        String currentShowPageNo =
                request.getParameter("currentShowPageNo");


        // 탭 기본값
        if(tab == null || tab.isBlank()) {

            tab = "notice";
        }


        // 검색조건 기본값
        if(searchType == null) {

            searchType = "";
        }


        // 검색어 기본값
        if(searchWord == null) {

            searchWord = "";
        }

        else {

            searchWord = searchWord.trim();
        }


        // 페이지 기본값
        if(currentShowPageNo == null
                || currentShowPageNo.isBlank()) {

            currentShowPageNo = "1";
        }


        // 잘못된 페이지 번호가 넘어오는 경우
        int pageNo;

        try {

            pageNo =
                Integer.parseInt(currentShowPageNo);

        }
        catch(NumberFormatException e) {

            pageNo = 1;
        }


        if(pageNo < 1) {

            pageNo = 1;
        }


        currentShowPageNo =
                String.valueOf(pageNo);


        // =====================================================
        // 2. 검색조건 Map
        // =====================================================

        Map<String, String> paraMap =
                new HashMap<>();


        paraMap.put(
                "searchType",
                searchType
        );


        paraMap.put(
                "searchWord",
                searchWord
        );


        paraMap.put(
                "currentShowPageNo",
                currentShowPageNo
        );


        // =====================================================
        // 3. 페이징 기본 설정
        // =====================================================

        int sizePerPage = 10;

        int blockSize = 10;

        int totalCount = 0;


        // =====================================================
        // 4. 공지사항
        // =====================================================

        if("notice".equals(tab)) {


            List<NoticeDTO> noticeList =
                    ndao.select_notice_list(paraMap);


            /*
             * 중요
             *
             * 검색조건까지 포함해서 전체 개수를 구해야 함.
             */
            totalCount =
                    ndao.getTotalCountOrder(paraMap);


            request.setAttribute(
                    "noticeList",
                    noticeList
            );


            int totalPage =
                    (int)Math.ceil(
                            (double)totalCount
                            / sizePerPage
                    );


            // 게시글이 없어도 페이지 계산 오류 방지
            if(totalPage == 0) {

                totalPage = 1;
            }


            String noticePageBar =
                    makePageBar(
                            tab,
                            pageNo,
                            totalPage,
                            blockSize,
                            searchType,
                            searchWord
                    );


            request.setAttribute(
                    "noticePageBar",
                    noticePageBar
            );

        }


        // =====================================================
        // 5. FAQ
        // =====================================================

        else if("faq".equals(tab)) {


            List<FaqDTO> faqList =
                    fdao.select_faq_list(paraMap);


            /*
             * FAQ DAO도 검색조건을 포함해서
             * 전체 개수를 구하도록 수정하는 것을 추천.
             */
            totalCount =
                    fdao.getTotalCountOrder(paraMap);


            request.setAttribute(
                    "faqList",
                    faqList
            );


            int totalPage =
                    (int)Math.ceil(
                            (double)totalCount
                            / sizePerPage
                    );


            if(totalPage == 0) {

                totalPage = 1;
            }


            String faqPageBar =
                    makePageBar(
                            tab,
                            pageNo,
                            totalPage,
                            blockSize,
                            searchType,
                            searchWord
                    );


            request.setAttribute(
                    "faqPageBar",
                    faqPageBar
            );

        }


        // =====================================================
        // 6. 문의사항
        // =====================================================

        else if("question".equals(tab)) {


            List<Map<String, String>> questionList =
                    qdao.select_question_list(paraMap);


            totalCount = qdao.getTotalCountOrder(paraMap);


            request.setAttribute(
                    "questionList",
                    questionList
            );


            int totalPage =
                    (int)Math.ceil(
                            (double)totalCount
                            / sizePerPage
                    );


            if(totalPage == 0) {

                totalPage = 1;
            }


            String questionPageBar =
                    makePageBar(
                            tab,
                            pageNo,
                            totalPage,
                            blockSize,
                            searchType,
                            searchWord
                    );


            request.setAttribute(
                    "questionPageBar",
                    questionPageBar
            );

        }


        // =====================================================
        // 7. JSP에서 사용할 값
        // =====================================================

        request.setAttribute(
                "tab",
                tab
        );


        request.setAttribute(
                "totalCountOrder",
                totalCount
        );


        request.setAttribute(
                "currentShowPageNo",
                currentShowPageNo
        );


        request.setAttribute(
                "sizePerPage",
                sizePerPage
        );


        request.setAttribute(
                "searchType",
                searchType
        );


        request.setAttribute(
                "searchWord",
                searchWord
        );


        // =====================================================
        // 8. 화면 이동
        // =====================================================

        super.setRedirect(false);

        super.setViewPage(
                "/WEB-INF/admin/callcenter/callcenter.jsp"
        );

    }


    // =========================================================
    // 페이지바 생성
    // =========================================================

    private String makePageBar(
            String tab,
            int currentPage,
            int totalPage,
            int blockSize,
            String searchType,
            String searchWord) {


        StringBuilder pageBar =
                new StringBuilder();


        // 현재 페이지가 어느 블럭에 속하는지 계산
        int startPage =
                ((currentPage - 1) / blockSize)
                * blockSize + 1;


        int endPage =
                Math.min(
                        startPage + blockSize - 1,
                        totalPage
                );


        // =====================================================
        // 검색조건을 URL에 유지
        // =====================================================

        String searchParam = "";


        if(searchType != null
                && !searchType.isBlank()
                && searchWord != null
                && !searchWord.isBlank()) {


            searchParam =
                    "&searchType="
                    + searchType
                    + "&searchWord="
                    + searchWord;

        }


        // =====================================================
        // 맨처음
        // =====================================================

        pageBar.append(
                "<li class='page-item'>"
        );


        pageBar.append(
                "<a class='page-link' "
        );


        pageBar.append(
                "href='callcenter.go?"
        );


        pageBar.append(
                "tab="
        );


        pageBar.append(
                tab
        );


        pageBar.append(
                "&currentShowPageNo=1"
        );


        pageBar.append(
                searchParam
        );


        pageBar.append(
                "'>처음</a>"
        );


        pageBar.append(
                "</li>"
        );


        // =====================================================
        // 이전
        // =====================================================

        if(startPage > 1) {


            pageBar.append(
                    "<li class='page-item'>"
            );


            pageBar.append(
                    "<a class='page-link' "
            );


            pageBar.append(
                    "href='callcenter.go?"
            );


            pageBar.append(
                    "tab="
            );


            pageBar.append(
                    tab
            );


            pageBar.append(
                    "&currentShowPageNo="
            );


            pageBar.append(
                    startPage - 1
            );


            pageBar.append(
                    searchParam
            );


            pageBar.append(
                    "'>이전</a>"
            );


            pageBar.append(
                    "</li>"
            );

        }


        // =====================================================
        // 페이지 번호
        // =====================================================

        for(int i = startPage;
                i <= endPage;
                i++) {


            if(i == currentPage) {


                pageBar.append(
                        "<li class='page-item active'>"
                );


                pageBar.append(
                        "<a class='page-link' href='#'>"
                );


                pageBar.append(
                        i
                );


                pageBar.append(
                        "</a>"
                );


                pageBar.append(
                        "</li>"
                );

            }


            else {


                pageBar.append(
                        "<li class='page-item'>"
                );


                pageBar.append(
                        "<a class='page-link' "
                );


                pageBar.append(
                        "href='callcenter.go?"
                );


                pageBar.append(
                        "tab="
                );


                pageBar.append(
                        tab
                );


                pageBar.append(
                        "&currentShowPageNo="
                );


                pageBar.append(
                        i
                );


                pageBar.append(
                        searchParam
                );


                pageBar.append(
                        "'>"
                );


                pageBar.append(
                        i
                );


                pageBar.append(
                        "</a>"
                );


                pageBar.append(
                        "</li>"
                );

            }

        }


        // =====================================================
        // 다음
        // =====================================================

        if(endPage < totalPage) {


            pageBar.append(
                    "<li class='page-item'>"
            );


            pageBar.append(
                    "<a class='page-link' "
            );


            pageBar.append(
                    "href='callcenter.go?"
            );


            pageBar.append(
                    "tab="
            );


            pageBar.append(
                    tab
            );


            pageBar.append(
                    "&currentShowPageNo="
            );


            pageBar.append(
                    endPage + 1
            );


            pageBar.append(
                    searchParam
            );


            pageBar.append(
                    "'>다음</a>"
            );


            pageBar.append(
                    "</li>"
            );

        }


        // =====================================================
        // 마지막
        // =====================================================

        pageBar.append(
                "<li class='page-item'>"
        );


        pageBar.append(
                "<a class='page-link' "
        );


        pageBar.append(
                "href='callcenter.go?"
        );


        pageBar.append(
                "tab="
        );


        pageBar.append(
                tab
        );


        pageBar.append(
                "&currentShowPageNo="
        );


        pageBar.append(
                totalPage
        );


        pageBar.append(
                searchParam
        );


        pageBar.append(
                "'>마지막</a>"
        );


        pageBar.append(
                "</li>"
        );


        return pageBar.toString();

    }

}