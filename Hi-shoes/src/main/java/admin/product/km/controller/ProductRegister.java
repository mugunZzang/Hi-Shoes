package admin.product.km.controller;

import java.io.File;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import product.km.domain.ProductDTO;
import product.km.model.ProductDAO;
import product.km.model.ProductDAO_imple;

public class ProductRegister extends AbstractController {
	
	private ProductDAO pdao = new ProductDAO_imple();
	
	private String extractFileName(String partHeader) {
	      for(String cd : partHeader.split("\\;")) {
	         if(cd.trim().startsWith("filename")) {
	            String fileName = cd.substring(cd.indexOf("=") + 1).trim().replace("\"", ""); 
	            int index = fileName.lastIndexOf(File.separator); // File.separator 란? OS가 Windows 이라면 \ 이고, OS가 Mac, Linux, Unix 이라면 / 을 말하는 것이다.       
	            return fileName.substring(index + 1);
	         }
	      }
	      return null;
	}// end of private String extractFileName(String partHeader)-------------------
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		
		String method = request.getMethod();
		if("GET".equals(method)) {
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/admin/product/productRegister.jsp");
		}
		else {// "POST 라면
		
			// 1. 첨부되어진 파일을 디스크의 어느 경로에 업로드 할 것인지 그 경로를 설정해야 한다.  
			ServletContext svlCtx = session.getServletContext();
			String uploadFileDir = svlCtx.getRealPath("/images");
			
		    // System.out.println("=== 첨부되어지는 이미지 파일이 올라가는 실제경로 uploadFileDir ==> " + uploadFileDir);
		    // === 첨부되어지는 이미지 파일이 올라가는 실제경로 uploadFileDir 
			// ==> C:\KDT\workspace_jsp\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\MyMVC\images
	
			// === >>> 파일을 업로드 해준다. <<< === //
			String pimage1 = null;
	        String pimage2 = null;
	        String warranty_systemfilename = null;
	        String warranty_originfilename = null;
	        
	        String attachCount = request.getParameter("attachCount");
	        // attachCount 가 추가이미지 파일의 개수이다. null "1" ~ "10"
	        // System.out.println("~~~ 확인용 attachCount :" + attachCount);
	        // ~~~ 확인용 attachCount :null
	        // ~~~ 확인용 attachCount :3
	        
	        int n_attachCount = 0;
	         
	        if(attachCount != null) {
	           n_attachCount = Integer.parseInt(attachCount);
	        }
	        
	        String[] arr_attachFileName = new String[n_attachCount]; // 추가이미지 파일명들을 저장시키는 용도
	        int idx_attach = 0;
	        
	        Collection<Part> parts = request.getParts();
	        // getParts()를 사용하여 form 태그로 부터 넘어온 데이터들을 각각의 Part로 하나하나씩 받는다.
	        
	        /*
	           Part
	           -----------------------------------------------------------------------------------------------------------------
	            메소드                                                설명
	           -----------------------------------------------------------------------------------------------------------------
	            public InputStream getInputStream()         Part에 대한 InputStream을 리턴한다. 직접 데이터를 꺼내어 올때 사용한다. 
	            throws IOException;
	
	            public String getContentType()              Content-Type을 리턴해준다.
	                                                        예를들어, 파일의 image/jpeg 또는 application/pdf 와 같은것을 말한다.
	
	            public String getName()                     파라미터명을 리턴해준다.
	
	            public String getSubmittedFileName()        업로드한 파일명을 리턴해준다. servlet 3.1(톰캣버전 8.5 이후)부터 사용 가능하다.
	
	            public long getSize();                      파일의 크기를 byte단위로 리턴해준다.
	            
	            public void write(String fileName)          임시저장되어 있는 파일 데이터를 복사하여 fileName 에 지정한 경로로 저장해준다.  
	            throws IOException                          임시저장 되어있는 파일데이터가 메모리상에 있든 디스크에 있든 신경쓰지 않아도 된다.
	            
	            public void delete()                        임시저장된 파일 데이터를 제거해준다.  
	            throws IOException                          HTTP요청이 처리되고 나면 자동으로 제거되지만 그 전에 메모리나 디스크 자원을 아끼고 싶다면 수동으로 제거할 수 있다. 
	            
	            public String getHeader(String name)        Part로부터 지정한 name헤더값을 리턴해준다.    
	           ------------------------------------------------------------------------------------------------------------------
	
	        */
	        
	        for(Part part : parts) {
	             // System.out.printf(">> 확인용   파라미터(name)명 : %s, contentType : %s, size : %d byte" ,part.getName(), part.getContentType(), part.getSize());
	             
	             /*   
	               >> 확인용   파라미터(name)명 : fk_cnum, contentType : null, size : 1 bytes 
	               >> 확인용   파라미터(name)명 : pname, contentType : null, size : 12 bytes 
	               >> 확인용   파라미터(name)명 : pcompany, contentType : null, size : 15 bytes 
	               >> 확인용   파라미터(name)명 : pimage1, contentType : image/jpeg, size : 71317 bytes 
	               >> 확인용   파라미터(name)명 : pimage2, contentType : image/jpeg, size : 58889 bytes 
	               >> 확인용   파라미터(name)명 : prdmanualFile, contentType : application/pdf, size : 791567 bytes 
	               >> 확인용   파라미터(name)명 : pqty, contentType : null, size : 1 bytes 
	               >> 확인용   파라미터(name)명 : price, contentType : null, size : 4 bytes 
	               >> 확인용   파라미터(name)명 : saleprice, contentType : null, size : 4 bytes 
	               >> 확인용   파라미터(name)명 : fk_snum, contentType : null, size : 1 bytes 
	               >> 확인용   파라미터(name)명 : pcontent, contentType : null, size : 6 bytes 
	               >> 확인용   파라미터(name)명 : point, contentType : null, size : 3 bytes 
	               >> 확인용   파라미터(name)명 : attachCount, contentType : null, size : 1 bytes 
	               >> 확인용   파라미터(name)명 : attach0, contentType : image/jpeg, size : 57641 bytes 
	               >> 확인용   파라미터(name)명 : attach1, contentType : image/jpeg, size : 41931 bytes 
	               >> 확인용   파라미터(name)명 : attach2, contentType : image/jpeg, size : 44338 bytes 
	               >> 확인용   파라미터(name)명 : attach3, contentType : image/jpeg, size : 48901 bytes
	           */
	        	
	        	if(part.getHeader("Content-Disposition").contains("filename=")) { // form 태그에서 전송되어온 것이 파일일 경우
	               // Content-Disposition 이란?
	               // 일반적인 HTTP 응답에서 Content-Disposition 헤더는 컨텐츠가 브라우저로 보여지는 웹페이지 자체이거나,
	               // 아니면 컨텐츠가 attachment 로써 다운로드 되어질 용도로 쓰이는 것인지를 알려주는 헤더이다.
	               // 첨부파일은 Header 부분에 Content-Disposition 설정을 아래와 같이 설정해준다.
	               // Content-Disposition: attachment; filename="filename.jpg"
	               
	               // 그래서, 업로드한 파일명을 구하려면 Content-Disposition 헤더의 값을 사용한다.
	        		
	        	   String fileName = extractFileName(part.getHeader("Content-Disposition"));
	        	   // 파일이름만 추출하기 위해 메서드를 생성후 추출함.
	        	   
	        	   if(part.getSize() > 0) {
	        		   // System.out.println("~~~ 확인용  업로드한 파일명 :  " + fileName);
	        		   /*
	            		  ~~~ 확인용  업로드한 파일명 :  berkelekle덩크04.jpg
	            		  ~~~ 확인용  업로드한 파일명 :  berkelekle디스트리뷰트06.jpg
	            		  ~~~ 확인용  업로드한 파일명 :  berkelekle심플V넥02.jpg
	            		  ~~~ 확인용  업로드한 파일명 :  berkelekle디스트리뷰트06.jpg
	            		  ~~~ 확인용  업로드한 파일명 :  berkelekle심플V넥02.jpg
	            		  ~~~ 확인용  업로드한 파일명 :  berkelekle단가라포인트03.jpg
	            		  ~~~ 확인용  업로드한 파일명 :  berkelekle덩크04.jpg
	        		   */
	        		   
	        		   // 서버에 저장할 새로운 파일명을 만든다.
	                   // 서버에 저장할 새로운 파일명이 동일한 파일명이 되지 않고 고유한 파일명이 되도록 하기 위해
	                   // 현재의 년월일시분초에다가 현재 나노세컨즈nanoseconds 값을 결합하여 확장자를 붙여서 만든다
	        		   
	        		   String newFilename = fileName.substring(0, fileName.lastIndexOf(".")); // 확장자를 뺀 파일명 알아오기
	        		   newFilename += "_"+String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS", Calendar.getInstance()); // 현재 년월일 시분초 포멧
	        		   newFilename += System.nanoTime();
	        		   newFilename += fileName.substring(fileName.lastIndexOf(".")); // 확장자 붙이기 
	        		   
	        		   // System.out.println("==== 확인용 실제 업로드 되어질 newFilename : " + newFilename);
	        		   /*
	        		     ==== 확인용 실제 업로드 되어질 newFilename : berkelekle단가라포인트03_20260918123830704197502656000.jpg
						 ==== 확인용 실제 업로드 되어질 newFilename : berkelekle단가라포인트03_20260918123830704197503049700.jpg 
	        		   */
	        		   
	        		   // >>> 파일을 지정된 디스크 경로에 저장해준다. 이것이 바로 파일을 업로드 해주는 작업이다. <<<
	                   part.write(uploadFileDir + File.separator + newFilename);   
	                  
	                   // >>> 임시저장된 파일 데이터를 제거해준다. <<<
	                   // 즉,  @MultipartConfig(location = "C:\\KDT\\workspace_jsp\\MyMVC\\images_temp_upload",
	                   //                      fileSizeThreshold = 1024) 와 같이 설정되었다면
	                   // C:\\KDT\\workspace_jsp\\MyMVC\\images_temp_upload 폴더에 임시 저장된 파일을 제거해야 한다.
	                   part.delete();
	                   
	                   if("pimage1".equals(part.getName())) {
	                	   pimage1 = newFilename;
	                   }
	                   else if("pimage2".equals(part.getName())){
	                	   pimage2 = newFilename;
	                   }
	                   else if("prdmanualFile".equals(part.getName())) {
	                	   warranty_systemfilename = newFilename;
	                	   warranty_originfilename = fileName;
	                   }
	                   else if(part.getName().startsWith("attach")) {
	                	   arr_attachFileName[idx_attach++] = newFilename;
	                   }
	   	            
	   	            
	        	   }// end of if(part.getSize() > 0)
	        	   
	        	   
	        	}
	        	
	        }// end of for(Part part : parts)
	        
	        // === 첨부 이미지 파일, 제품설명서 파일을 올렸으니 그 다음으로 제품정보를 (제품명, 정가, 제품수량,...) DB의 tbl_product 테이블에 insert 를 해주어야 한다.  ===
	        
	        String pname = request.getParameter("pname");       // 제품명
	        
	        
	        // !!!! 크로스 사이트 스크립트 공격에 대응하는 안전한 코드(시큐어코드) 작성하기 !!!! //
	        // 2024년 12월 기준 전후로 <script 가 들어오면 크롬(엣지)웹브라우저 차원에서
	        // Uncaught SyntaxError: Unexpected token '<' 라는 오류를 발생시켜서 차단을 시켜버리고 있음. 
	        String pcontent = request.getParameter("pcontent");   // 제품설명
	        pcontent = pcontent.replaceAll("<", "&lt;");
	        pcontent = pcontent.replaceAll(">", "&gt;");
	        
	        // 입력한 내용에서 엔터는 <br>로 변환하기
	        pcontent = pcontent.replaceAll("\r\n", "<br>");
	        
	        String deliveryfee = request.getParameter("deliveryfee"); // 배달료
	        
			
	        // 제품번호 채번 해오기
            int pnum = pdao.getPnumOfProduct();
            
            ProductDTO pdto = new ProductDTO();
            pdto.setPnum(pnum);   // 제품번호(Primary Key)
            pdto.setFk_pname(pname); // 제품명
            
            pdto.setPimage1(pimage1);   // 제품이미지1 
            pdto.setPimage2(pimage2);   // 제품이미지2
            pdto.setWarranty_systemfilename(warranty_systemfilename); // 파일서버에 업로드되어지는 실제 제품설명서 파일명 (Electrolux냉장고_사용설명서_202412270933481349948069064400.pdf) 
            pdto.setWarranty_originfilename(warranty_originfilename);  // 웹클라이언트의 웹브라우저에서 파일을 업로드 할때 올리는 제품설명서 파일명(Electrolux냉장고_사용설명서.pdf) 

            pdto.setPcontent(pcontent); // 제품설명
            pdto.setDeliveryfee(Integer.parseInt(deliveryfee)); // 배달료
            
            
            // tbl_product 테이블에 제품정보 insert 하기
            int n = pdao.productInsert(pdto);
            
            int result = 0;
            if(n==1) {
            	result = 1;
            }
            
            // === 추가이미지파일이 있다라면 tbl_product_imagefile 테이블에 제품의 추가이미지 파일명 insert 해주기 === //
            // 파일서버에 업로드 되어진 추가이미지파일의 실제파일명 알아오기 
            if(n==1 && n_attachCount > 0) {
            	result = 0;
            	
            	Map<String, String> paraMap = new HashMap<>();
            	paraMap.put("pnum", String.valueOf(pnum));
            	// pnum 은 위에서 채번해온 제품번호이다.
            	
            	int cnt = 0;
            	for(int i=0; i< n_attachCount; i++) {
            		
            		String attachFileName = arr_attachFileName[i];
            		paraMap.put("attachFileName", attachFileName);
            		
            		// >>> tbl_product_imagefile 테이블에 제품의 추가이미지 파일명 insert 하기 <<<
            		int attach_insert_result = pdao.product_imagefile_insert(paraMap);
            		if(attach_insert_result == 1) {
            			cnt++;
            		}

            	}// end of for 
            	
            	if(cnt == n_attachCount) {
            		result = 1;
            	}
            	
            }// end of if(n==1 && n_attachCount > 0)
            
            JSONObject jsonObj = new JSONObject(); // {}
            jsonObj.put("result", result); // {"result":1}
            
            String json = jsonObj.toString(); // 문자열로 변환
            request.setAttribute("json", json);
            
            super.setRedirect(false);
            super.setViewPage("/WEB-INF/jsonview.jsp");
		} 
		
	}

}
