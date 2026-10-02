package notice.km.controller;

import java.io.File;

import common.controller.AbstractController;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import notice.km.domain.NoticeDTO;
import notice.km.model.NoticeDAO;
import notice.km.model.NoticeDAO_imple;

public class NoticeWrite extends AbstractController {

	private NoticeDAO ndao = new NoticeDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod(); // "GET" 또는 "POST" 
		
		if("GET".equals(method)) {
		
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/admin/callcenter/noticeWrite.jsp");
		}
		else {
			// post 방식으로 받아옴
			
			ServletContext svlCtx = request.getServletContext();
			String uploadFileDir = svlCtx.getRealPath("/images");
			
			String nsubject = request.getParameter("nsubject");
			String ncontents = request.getParameter("ncontents");
			Part image = request.getPart("image");
			
			String imageName = image.getSubmittedFileName(); // 이미지 이름
			
			image.write(uploadFileDir + File.separator + imageName); // 파일저장
			
		//	System.out.println("저장 경로 : " + uploadFileDir);
		//  System.out.println("파일 이름 : " + imageName);
		    
		    NoticeDTO ndto = new NoticeDTO();
		    ndto.setNsubject(nsubject);
		    ndto.setNcontents(ncontents);
		    ndto.setNimage(imageName);
		    
		    // 공지사항에 넣어주기
		    int n = ndao.noticeInsert(ndto);
		    
		    if(n==1) {
		    	super.setRedirect(false);
				super.setViewPage("/WEB-INF/admin/callcenter/callcenter.jsp");
		    }

	    		 
		}
		
	}

}
