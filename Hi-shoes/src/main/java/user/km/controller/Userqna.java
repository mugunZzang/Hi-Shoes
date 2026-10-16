package user.km.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberDTO;
import question.km.model.QuestionDAO;
import question.km.model.QuestionDAO_imple;

public class Userqna extends AbstractController {

	private QuestionDAO qdao = new QuestionDAO_imple();
	 
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// === 관리자(admin)로 로그인 했을때만 회원목록 조회가 가능하도록 해야 한다. === //
		HttpSession session = request.getSession();
		
		MemberDTO loginuser = (MemberDTO)session.getAttribute("loginuser");
		
		if(loginuser != null) {
			
			String userid = loginuser.getUserid();
			String searchType = request.getParameter("searchType");
	        String searchWord = request.getParameter("searchWord");
	        String currentShowPageNo = request.getParameter("currentShowPageNo");
	        
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
	        if(currentShowPageNo == null || currentShowPageNo.isBlank()) {
	
	            currentShowPageNo = "1";
	        }
	
	
	        // 잘못된 페이지 번호가 넘어오는 경우
	        int pageNo;
	
	        try {
	
	            pageNo = Integer.parseInt(currentShowPageNo);
	
	        }
	        catch(NumberFormatException e) {
	            pageNo = 1;
	        }
	
	
	        if(pageNo < 1) {
	
	            pageNo = 1;
	        }
	
	
	        currentShowPageNo = String.valueOf(pageNo);
	
	
	        // =====================================================
	        // 2. 검색조건 Map
	        // =====================================================
	
	        Map<String, String> paraMap = new HashMap<>();
	
	        paraMap.put("userid",userid);
	        paraMap.put("searchType",searchType);
	        paraMap.put("searchWord",searchWord);
	        paraMap.put("currentShowPageNo",currentShowPageNo);
	
	        int sizePerPage = 10;
	        int blockSize = 10;
	        int totalCount = 0;
	        
	        List<Map<String, String>> questionList = qdao.select_user_question_list(paraMap);
	    	
	    	
            totalCount = qdao.getTotalUserCountOrder(paraMap);


            request.setAttribute("questionList",questionList);


            int totalPage =(int)Math.ceil((double)totalCount/ sizePerPage);


            if(totalPage == 0) {

                totalPage = 1;
            }


            String questionPageBar = makePageBar(pageNo,
					                            totalPage,
					                            blockSize,
					                            searchType,
					                            searchWord
                    								);


            request.setAttribute("questionPageBar",questionPageBar);

				
            request.setAttribute("userid",userid);
	        request.setAttribute("totalCountOrder",totalCount);
	        request.setAttribute("currentShowPageNo",currentShowPageNo);
	        request.setAttribute("sizePerPage",sizePerPage);
	        request.setAttribute("searchType",searchType);
	        request.setAttribute("searchWord",searchWord);
        
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/user/userqna.jsp");
		
		}
		else {
	         String message = "로그인후 접근이 가능합니다.";
	         String loc = "javascript:history.back()";
	         
	         request.setAttribute("message", message);
	         request.setAttribute("loc", loc);
	         
	         
	         super.setRedirect(false);
	         super.setViewPage("/WEB-INF/msg.jsp");
		}

	}
	
	private String makePageBar( int currentPage,
					            int totalPage,
					            int blockSize,
					            String searchType,
					            String searchWord) {


        StringBuilder pageBar = new StringBuilder();

        int startPage = ((currentPage - 1) / blockSize)* blockSize + 1;

        int endPage =Math.min(startPage + blockSize - 1,totalPage);

        String searchParam = "";


        if(searchType != null
                && !searchType.isBlank()
                && searchWord != null
                && !searchWord.isBlank()) {


            searchParam ="&searchType="
	                    + searchType
	                    + "&searchWord="
	                    + searchWord;

        }

        // 맨처음

        pageBar.append("<li class='page-item'>");
        pageBar.append("<a class='page-link' ");
        pageBar.append("href='userqna.go?");
        pageBar.append("currentShowPageNo=1");
        pageBar.append(searchParam);
        pageBar.append("'>처음</a>");
        pageBar.append("</li>");

        // 이전

        if(startPage > 1) {

            pageBar.append("<li class='page-item'>");
            pageBar.append("<a class='page-link' ");
            pageBar.append("href='userqna.go?");
            pageBar.append("currentShowPageNo=");
            pageBar.append(startPage - 1);
            pageBar.append(searchParam);
            pageBar.append("'>이전</a>");
            pageBar.append("</li>");

        }


        // 페이지 번호

        for(int i = startPage;i <= endPage;i++) {

            if(i == currentPage) {

                pageBar.append("<li class='page-item active'>");
                pageBar.append("<a class='page-link' href='#'>");
                pageBar.append(i);
                pageBar.append("</a>");
                pageBar.append("</li>");
            }
            else {

                pageBar.append("<li class='page-item'>");
                pageBar.append("<a class='page-link' ");
                pageBar.append("href='userqna.go?");
                pageBar.append("currentShowPageNo=");
                pageBar.append(i);
                pageBar.append(searchParam);
                pageBar.append("'>");
                pageBar.append(i);
                pageBar.append("</a>");
                pageBar.append("</li>");

            }

        }

        // 다음

        if(endPage < totalPage) {

            pageBar.append("<li class='page-item'>");
            pageBar.append("<a class='page-link' ");
            pageBar.append("href='userqna.go?");
            pageBar.append("currentShowPageNo=");
            pageBar.append(endPage + 1);
            pageBar.append(searchParam);
            pageBar.append("'>다음</a>");
            pageBar.append("</li>");

        }

        // 마지막
        pageBar.append("<li class='page-item'>");
        pageBar.append("<a class='page-link' ");
        pageBar.append("href='userqna.go?");
        pageBar.append("currentShowPageNo=");
        pageBar.append(totalPage);
        pageBar.append(searchParam);
        pageBar.append("'>마지막</a>");
        pageBar.append("</li>");


        return pageBar.toString();

    }

}
