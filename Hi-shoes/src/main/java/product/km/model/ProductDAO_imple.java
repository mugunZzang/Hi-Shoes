package product.km.model;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import util.security.AES256;
import util.security.SecretMyKey;

public class ProductDAO_imple implements ProductDAO {
	
	private DataSource ds; // DataSource ds 가 DBCP(DB Connection Pool)이다. 
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private AES256 aes;
	
	// 생성자
	public ProductDAO_imple() {
		
		try {
			Context initContext = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
		    ds = (DataSource)envContext.lookup("jdbc/myoracle");
		    // lookup()속에 /MyMVC/src/main/webapp/META-INF/context.xml에 지정한 이름을 적어준다. 
		    
		    aes = new AES256(SecretMyKey.KEY);
		    // SecretMyKey.KEY 은 우리가 만든 암호화/복호화 키이다.
		    
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
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
	
	
	// 상품목록 가져오기
	@Override
	public List<Map<String, String>> selectProductList(Map<String, String> paraMap) throws Exception {

		 List<Map<String, String>> productList = new ArrayList<>();

		    try {

		        conn = ds.getConnection();

		        String sql =
		              " select pnum "
		            + "      , fk_pname "
		            + "      , pcontent "
		            + "      , deliveryfee "
		            + " from tbl_product "
		            + " where 1 = 1 ";

		        String searchWord = paraMap.get("searchWord");

		        if(searchWord != null && !searchWord.trim().isEmpty()) {

		            sql += " and fk_pname like '%' || ? || '%' ";
		        }

		        sql +=
		              " order by pnum desc "
		            + " offset (? - 1) * 10 rows "
		            + " fetch next 10 rows only ";

		        pstmt = conn.prepareStatement(sql);

		        int index = 1;

		        if(searchWord != null && !searchWord.trim().isEmpty()) {

		            pstmt.setString(index++, searchWord);
		        }

		        int currentShowPageNo =Integer.parseInt(paraMap.get("currentShowPageNo"));

		        pstmt.setInt(index, currentShowPageNo);

		        rs = pstmt.executeQuery();

		        while(rs.next()) {

		            Map<String,String> productMap = new HashMap<>();

		            productMap.put("pnum",String.valueOf(rs.getInt("pnum")));
		            productMap.put("fk_pname",rs.getString("fk_pname"));
		            productMap.put("pcontent",rs.getString("pcontent"));
		            productMap.put("deliveryfee",String.valueOf(rs.getInt("deliveryfee")));
		            productList.add(productMap);
		        }

		    }
		    finally {
		        close();
		    }

		    return productList;
	}
	
    // 페이징 처리할 갯수 가져오기
	@Override
	public int getTotalCountProduct(Map<String,String> paraMap) throws Exception {

		int totalCountProduct = 0;

	    try {

	        conn = ds.getConnection();

	        String sql =
	              " select count(*) as CNT "
	            + " from tbl_product "
	            + " where 1 = 1 ";

	        String searchWord = paraMap.get("searchWord");

	        if(searchWord != null && !searchWord.trim().isEmpty()) {

	            sql += " and fk_pname like '%' || ? || '%' ";
	        }

	        pstmt = conn.prepareStatement(sql);

	        if(searchWord != null && !searchWord.trim().isEmpty()) {

	            pstmt.setString(1, searchWord);
	        }

	        rs = pstmt.executeQuery();

	        rs.next();

	        totalCountProduct = rs.getInt("CNT");

	    }
	    finally {
	        close();
	    }

	    return totalCountProduct;
	}

	// 업체별 신발 갯수 가져오기
	@Override
	public List<Map<String, String>> sup_cnt() {
		// TODO Auto-generated method stub
		return null;
	}
}
