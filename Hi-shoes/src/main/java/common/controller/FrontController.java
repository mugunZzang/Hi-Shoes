package common.controller;

//import jakarta.annotation.Resource;            // 어노테이션은 톰캣11 규격(jakarta)
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;	// 서블릿도 톰캣11 규격(jakarta)
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
//import java.sql.Connection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
//import javax.sql.DataSource;                   // ★ DB 연결은 JDK 표준인 'javax' 유지 ★

/*
SQL 데이터소스는 Java 표준 스펙이기 때문에 Tomcat 버전과 무관하게 언제나 javax.sql.DataSource를 사용해야 한다.
톰캣 10, 11로 올라가면서 웹 관련 라이브러리(servlet, jsp, annotation 등)는 javax에서 jakarta 패키지로 전부 바뀌었지만, 
데이터베이스 연결과 관련된 JDBC 인터페이스(DataSource, Connection 등)는 Java 기본 SDK(JDK)가 제공하는 표준 기술이므로 
여전히 javax.sql 패키지에 포함되어 있다. jakarta.sql이라는 패키지는 아예 존재하지 않는다. 
*/

/*
@MultipartConfig(location = "C:\\KDT\\workspace_jsp\\MyMVC\\images_temp_upload",
                 fileSizeThreshold = 1024,  // 이 크기 값(1024 byte)을 넘지 않으면 업로드된 데이터를 메모리상에 가지고 있지만, 이값을 넘는 경우 위의 location 로 지정된 경로에 임시파일로 저장된다.  
                                            // 메모리상에 저장된 파일 데이터는 언젠가 제거된다. 하지만 크기가 큰 파일을 메모리상에 올리게 되면 서버에 부하를 줄 수 있으므로 적당한 크기를 지정해주고, 그 이상크기의 파일은 임시파일로 저장하는것이 좋다.    
                                            // 만약에 기재 하지 않으면 기본값은 0 이다. 0 을 쓰면 무조건 임시디렉토리에 저장된다.
                 maxFileSize = 20971520,    // 업로드 되어질 파일들을 합친 최대 크기. 단위는 byte 임. 20*1024*1024 즉, 20MB. 기본은 -1L 즉, 제한이 없음.   
                 maxRequestSize = 31457280  // multipart/form-data 상태인 폼태그에 요청되어지는 모든 전송데이터 및 모든 파일들을 합친 크기. 단위는 byte 임. 30*1024*1024 즉, 30MB. 기본은 -1L 즉, 제한이 없음.
                )                                               
*/
@MultipartConfig ( // 위의 location 을 기입하지 않으면 Windows 는 자동적으로 C:\Windows\Temp 디렉토리를 사용하도록 되어있다.
      maxFileSize = 20971520,
      maxRequestSize = 31457280) 

@WebServlet(
		description = "사용자가 웹에서 *.go을 했을 경우 이 서블릿이 응답을 해주도록 한다.", 
		urlPatterns = { "*.go" }, 
		initParams = { 
				@WebInitParam(name = "propertyConfig", value = "C:/git/Hi-Shoes/Hi-shoes/src/main/webapp/WEB-INF/Command.properties", description = "*.go 에 대한 클래스의 매핑파일")
		})
public class FrontController extends HttpServlet {
	
	// name에 /MyMVC/src/main/webapp/META-INF/context.xml에 지정한 JNDI 이름을 적어주면 자동 주입됩니다.
	// ★ type 속성에 javax.sql.DataSource.class 를 명시해야 합니다 ★
	/*	확인용
    @Resource(name = "jdbc/myoracle", 
              type = javax.sql.DataSource.class)
    private DataSource dataSource;
    */
	
	private static final long serialVersionUID = 1L;

	private Map<String, Object> cmdMap = new HashMap<>();
	
	public void init(ServletConfig config) throws ServletException {
		/*
	        웹브라우저 주소창에서  *.up 을 하면 FrontController 서블릿이 응대를 해오는데 
	        맨 처음에 자동적으로 실행되어지는 메소드가 init(ServletConfig config) 이다.
	        여기서 중요한 것은 init(ServletConfig config) 메소드는 WAS(톰캣)가 구동되어진 후
	        딱 1번만 init(ServletConfig config) 메소드가 실행되어지고, 그 이후에는 실행이 되지 않는다. 
	        그러므로 init(ServletConfig config) 메소드에는 FrontController 서블릿이 동작해야할 환경설정을 잡아주는데 사용된다.
	  */ 
		
		// *** 확인용 *** //
		//System.out.println("~~~ 확인용 => 서블릿 FrontController 의 init(ServletConfig config) 메소드가 실행됨.");
		
		FileInputStream fis = null;
		// 특정 파일에 있는 내용을 읽어오기 위한 용도로 객체
		
		String props = config.getInitParameter("propertyConfig");
		//System.out.println("~~~ 확인용 props => " + props);
		//~~~ 확인용 props => C:/KDT/workspace_jsp/MyMVC/src/main/webapp/WEB-INF/Command.properites
		
		try {
			fis = new FileInputStream(props);
			// fis 는 C:/KDT/workspace_jsp/MyMVC/src/main/webapp/WEB-INF/Command.properites 파일의 내용을 읽어오기 위한 용도로 쓰이는 객체이다.
			
			Properties pr = new Properties();
			// Properties 는 Collection 중 HashMap 계열중의 하나로써
	         // "key","value"으로 이루어져 있는것이다.
	         // 그런데 중요한 것은 Properties 는 key도 String 타입이고, value도 String 타입만 가능하다는 것이다.
	         // key는 중복을 허락하지 않는다. value 값을 얻어오기 위해서는 key값 만 알면 된다.
			 
			pr.load(fis);
			/*
	           pr.load(fis); 은 fis 객체를 사용하여 C:/KDT/workspace_jsp/MyMVC/src/main/webapp/WEB-INF/Command.properties 파일의 내용을 읽어다가   
	         Properties 클래스의 객체인 pr 에 로드시킨다.
	         그러면 pr 은 읽어온 파일(Command.properties)의 내용에서 
	         = 을 기준으로 왼쪽은 key로 보고, 오른쪽은 value 로 인식한다.
	       */
			
			Enumeration<Object> en = pr.keys();
			/*
	          pr.keys(); 은
	          C:/KDT/workspace_jsp/MyMVC/src/main/webapp/WEB-INF/Command.properties 파일의 내용물에서 
	          = 을 기준으로 왼쪽에 있는 모든 key 들만 가져오는 것이다.   
	        */
			
			while(en.hasMoreElements()) {
				
				String key = (String) en.nextElement();
								
				//System.out.println("~~~ 확인용 key => " + key);
				//~~~ 확인용 key => /test/test2.up
				//~~~ 확인용 key => /test3.up
				//~~~ 확인용 key => /test1.up
				 
				//System.out.println("~~~ 확인용 value => " + pr.getProperty(key));
				//~~~ 확인용 value => test.controller.Test2Controller
				//~~~ 확인용 value => test.controller.Test3Controller
				//~~~ 확인용 value => test.controller.Test1Controller
								
				String className = pr.getProperty(key);
				
				if(className != null) {
					
					className = className.trim();
					
					Class<?> cls = Class.forName(className);	//properties에서 클래스는 적어뒀는데, 실제 빈 껍데기 클래스 파일이라도 만들지 않은경우를 대비해 선언.
					// <?> 은 generic 인데 어떤 클래스 타입인지는 모르지만 하여튼 클래스 타입이 들어온다는 뜻이다.
		            // String 타입으로 되어진 className 을 클래스화 시켜주는 것이다.
		            // 주의할 점은 실제로 String 으로 되어져 있는 문자열이 클래스로 존재해야만 한다는 것이다.
					
					Constructor<?> constrt = cls.getDeclaredConstructor();
					// 생성자 만들기
					
					Object obj = constrt.newInstance();
					// 생성자로부터 실제 객체(인스턴스)를 생성해주는 것이다.
					/*
					    $$$ 확인용 Test2Controller 클래스에서 기본생성자 호출함 $$$
						@@@ 확인용 Test3Controller 클래스에서 기본생성자 호출함 @@@
						### 확인용 Test1Controller 클래스에서 기본생성자 호출함 ###
					 */
					
					cmdMap.put(key, obj);
					
				} // end of if(className != null)------------
				
			}	// end of while(en.hasMoreElements())----------
			
		} catch (FileNotFoundException e) {
			System.out.println(">>> C:/KDT/workspace_jsp/MyMVC/src/main/webapp/WEB-INF/Command.properites 파일이 존재하지 않습니다.<<<");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println(">>> 문자열로 명명되어진 클래스가 존재하지 않습니다. <<<");			
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}

		
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/*
		try (Connection conn = dataSource.getConnection()) {
            // 비즈니스 로직 및 SQL 수행
            if (conn != null) {
            //  System.out.println("DBCP 연결 성공! (Tomcat 11)");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
		*/
		// 확인용
		//System.out.println("### 확인용 => 서블릿 FrontController의 doGet 메서드가 실행됨. ###");		
	//  웹브라우저의 주소 입력창에서 
	   //  http://localhost:9090/MyMVC/member/idDuplicateCheck.up?userid=leess 와 같이 입력되었더라면
       //String url = request.getRequestURL().toString();
       //System.out.println("~~~ 확인용 url => " + url);
	   //   ~~~ 확인용 url => http://localhost:9090/MyMVC/member/idDuplicateCheck.up
		
	//  웹브라우저의 주소 입력창에서 
	   //  http://localhost:9090/MyMVC/member/idDuplicateCheck.up?userid=leess 와 같이 입력되었더라면
       String uri = request.getRequestURI();
       //System.out.println("~~~ 확인용 uri => " + uri);       
       // ~~~ 확인용 uri => /MyMVC/member/idDuplicateCheck.up
       // ~~~ 확인용 uri => /MyMVC/test1.up
       //~~~ 확인용 uri => /MyMVC/test/test2.up
       //~~~ 확인용 uri => /MyMVC/test3.up
       
       String key = uri.substring(request.getContextPath().length());
       /*
        * /member/idDuplicateCheck.up
        * /test1.up
        * /test/test2.up
        * /test3.up
        */
       
       AbstractController action = (AbstractController) cmdMap.get(key);
       
       if(action == null) {
    	   System.out.println(">>> " + key + " 은 URI 패턴에 매핑된 클래스는 없습니다. <<<");
       }
       else {
    	   try {
			action.execute(request, response);
			
			boolean bool = action.isRedirect();
			String viewPage = action.getViewPage();
			
			if(!bool) {
				// viewPage 에 명기된 view단 페이지로 forward(dispatcher)를 하겠다는 말이다.
               // forward 되어지면 웹브라우저의 URL주소 변경되지 않고 그대로 이면서 화면에 보여지는 내용은 forward 되어지는 jsp 파일이다.
               // 또한 forward 방식은 forward 되어지는 페이지로 데이터를 전달할 수 있다는 것이다.
				if(viewPage != null) {
					RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
					dispatcher.forward(request, response);
				}
			}
			else {
				// viewPage 에 명기된 주소로 sendRedirect(웹브라우저의 URL주소 변경됨)를 하겠다는 말이다.
               // 즉, 단순히 페이지이동을 하겠다는 말이다. 
               // 암기할 내용은 sendRedirect 방식은 sendRedirect 되어지는 페이지로 데이터를 전달할 수가 없다는 것이다.
				if(viewPage != null) {
					response.sendRedirect(viewPage);
				}
			}
			
		   } catch (Exception e) {
			e.printStackTrace();
		   }
       }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
