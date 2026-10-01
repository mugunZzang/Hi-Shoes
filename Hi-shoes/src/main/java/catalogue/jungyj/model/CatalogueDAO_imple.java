package catalogue.jungyj.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class CatalogueDAO_imple implements CatalogueDAO {

	private DataSource ds;                // DataSource ds 는 DBCP(Database Connection Pool) 이다.
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	// 생성자
	public CatalogueDAO_imple() {
		try {
			Context initContext = new InitialContext();
	        Context envContext  = (Context)initContext.lookup("java:/comp/env");
	        ds = (DataSource)envContext.lookup("jdbc/myoracle");
	        // lookup()속에 /MyMVC/src/main/webapp/META-INF/context.xml에 지정한 이름을 적어주면 자동 주입됩니다.
		} catch (NamingException e) {
			e.printStackTrace();
			
		}
		
	}
	
	// 사용한 자원을 반납하는 close() 메소드 생성하기 
    private void close() {
       try {
          if(rs != null)    {rs.close();    rs=null;}
          if(pstmt != null) {pstmt.close(); pstmt=null;}
          if(conn != null)  {conn.close();  conn=null;}
       } catch(SQLException e) {
          e.printStackTrace();
       }
    }

    // =====================================================================================================================================
	// 카탈로그 등록(INSERT)
	@Override
	public int catalogueRegister(Map<String, String> paraMap) throws SQLException {
		int result = 0;
		
		try {
			conn = ds.getConnection();
			
			String sql = " INSERT INTO tbl_catalogue(pname, fk_catenum, purprice, regprice, saleprice, brand) "
					   + " VALUES (?, TO_NUMBER(?), TO_NUMBER(?), TO_NUMBER(?), TO_NUMBER(?), ?) ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, paraMap.get("pname"));
			pstmt.setString(2, paraMap.get("fk_catenum"));
			pstmt.setString(3, paraMap.get("pnamepurprice"));
			pstmt.setString(4, paraMap.get("regprice"));
			pstmt.setString(5, paraMap.get("saleprice"));
			pstmt.setString(6, paraMap.get("brand"));

			result = pstmt.executeUpdate();
			
			if(result == 1) {
				System.out.println("[INFO] CatalogueDAO_imple Class tbl_catalogue INSERT Success");
			} else {
				System.out.println("[ERROR] CatalogueDAO_imple Class tbl_catalogue INSERT Fail");
			}
			
		} finally {
			close();
		}
		
		return result;
	} // end of public int catalogueRegister(Map<String, String> paraMap) throws SQLException -------------------------------

	
	// 제품명 중복검사
	@Override
	public boolean pnameDuplicateCheck(String pname) throws SQLException {
		
		boolean isExists = false;
		
		try {
			conn = ds.getConnection();
			
			String sql = " SELECT pname " 
					   + " FROM tbl_catalogue "
					   + " WHERE pname = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, pname);
			
			rs = pstmt.executeQuery();
			
			
			isExists = rs.next();  // 행이 있으면(중복된 pname) true,
			                       // 행이 없으면(사용가능한 pname) false,
		} finally {
			close();
		}
			
		return isExists;

	} // end of public boolean pnameDuplicateCheck(String pname) throws SQLException-------------------------------------
	
	
    
}
