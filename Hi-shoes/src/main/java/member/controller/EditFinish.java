package member.controller;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import member.model.MemberDAO;
import member.model.MemberDAO_imple;

public class EditFinish extends AbstractController {

	private MemberDAO mdao = new MemberDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String method = request.getMethod();
		
		if("POST".equals(method)) {
			
			 String rating = request.getParameter("rating");
			 //System.out.println(rating);
			 String content = request.getParameter("content");
			 //System.out.println(content);
			 String odnum = request.getParameter("odnum");
			 Part imagePart = request.getPart("image");
			 
			 
			 String imagename = "";
			 // 이미지 파일이 있다면
			 if(imagePart != null && imagePart.getSize() > 0) {
				 imagename = imagePart.getSubmittedFileName();
				 
				 System.out.println("파일명" + imagename);
				 System.out.println("크기" + imagePart.getSize());
				 System.out.println("타입" + imagePart.getContentType());
				 
				// 저장될 파일 경로
				 String uploadPath = request.getServletContext().getRealPath("/images");
				 
				 java.io.File uploadDir = new java.io.File(uploadPath);
				 
				 // 오리지널 파일명
				 //String imagename = imagePart.getSubmittedFileName();
				 String extension = "";
				 
				 int dotIndex = imagename.lastIndexOf(".");
				 if(dotIndex > 0) {
					 extension = imagename.substring(dotIndex);
				 }

				 // 서버에 저장할 새로운 파일명을 만든다.
				 String savedName =  imagename.substring(0, imagename.lastIndexOf("."));

				 savedName += "_"+String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS", Calendar.getInstance());
				 savedName += System.nanoTime();
	             savedName += imagename.substring(imagename.lastIndexOf(".")); // 확장자 붙이기 
				 
	             // 실제 저장하기
	             imagePart.write(uploadPath + java.io.File.separator + savedName);
	             
	             // db 에도 실제이름으로 저장
	             imagename = savedName;
			 }
			 
			 System.out.println(imagename);
             
             Map<String, String> paraMap = new HashMap<String, String>();
             paraMap.put("rating", rating);
             paraMap.put("content", content);
             paraMap.put("odnum", odnum);
             paraMap.put("imagename", imagename);
             
             // db 에 수정정보 저장
             int n = mdao.editFinish(paraMap);
             
				
			 JSONObject jsonObj = new JSONObject(); jsonObj.put("n", n);
			 
			 String json = jsonObj.toString(); // {"n":1} 또는 {"n":-1} 또는 {"n":0}
			 
			 request.setAttribute("json", json);
			 
			 super.setRedirect(false); super.setViewPage("/WEB-INF/jsonview.jsp");
				 
		}
		else {
			// **** POST 방식으로 넘어온 것이 아니라면 **** //
	         
	         String message = "비정상적인 경로를 통해 들어왔습니다.!!";
	         String loc = "javascript:history.back()";
	         
	         request.setAttribute("message", message);
	         request.setAttribute("loc", loc);
	         
	         super.setRedirect(false);
	         super.setViewPage("/WEB-INF/msg.jsp");
		}
	}

}
