package admin.category.jungyj.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import admin.category.jungyj.domain.CategoryDTO;

public class CategoryDAO_imple implements CategoryDAO {

	private DataSource ds;                // DataSource ds 는 DBCP(Database Connection Pool) 이다.
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	// 생성자
	public CategoryDAO_imple() {
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
	
	
	@Override
	public List<CategoryDTO> selectCategoryList() throws SQLException {
		List<CategoryDTO> cateList = new ArrayList<>();
		
		try {
			conn = ds.getConnection();
			
			String sql = " SELECT catenum, catename "
					   + " FROM tbl_category "
					   + " ORDER BY catenum asc ";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				CategoryDTO cateDTO = new CategoryDTO();
				
				cateDTO.setCateNum(rs.getInt("catenum"));
				cateDTO.setCateName(rs.getString("catename"));
				
				cateList.add(cateDTO);
			}
		} finally {
			close();
		}
		
	
		return cateList;

	} // end of public List<CategoryDTO> selectCategoryList() throws SQLException--------------------------

	
	// 카테고리 등록(INSERT)
	@Override
	public int categoryRegister(String catename) throws SQLException {
		int result = 0;
		
		try {
			conn = ds.getConnection();
			
			String sql = " INSERT INTO tbl_category (catenum, catename)  "
					   + " VALUES (SEQ_CATENUM.nextval, ? ) ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, catename);
			
			result = pstmt.executeUpdate();
			
		} finally {
			close();
		}
		
		return result;
	} // end of public int categoryRegister(String catename) throws SQLException--------------------------

	
	// 카테고리 삭제(DELETE)
	@Override
	public int categoryDelete(String cateno) throws SQLException {
		int result = 0;
		
		try {
			conn = ds.getConnection();
			
			String sql = " DELETE FROM tbl_category "
					   + " WHERE catenum = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, Integer.parseInt(cateno));
			
			result = pstmt.executeUpdate();
			
		} finally {
			close();
		}
		
		return result;
	} // end of public int categoryDelete(String cateno) throws SQLException--------------------------

}
