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

	// 업체별 거래 갯수 가져오기
	@Override
	public List<Map<String, String>> sup_cnt() throws Exception{
		
		List<Map<String, String>> sup_map_List = new ArrayList<>();
		   
		   try {
			   conn = ds.getConnection();
			   
			   String sql = " select "
			   		+ "       p.fk_supname as sup, "
			   		+ "       round( "
			   		+ "           sum(d.purqty) / "
			   		+ "           (select sum(d2.purqty) "
			   		+ "              from tbl_purchase p2 "
			   		+ "              join tbl_purdetail d2 "
			   		+ "                on p2.purnum = d2.fk_purnum "
			   		+ "             where p2.instock = '입고' "
			   		+ "           ) * 100 "
			   		+ "       , 2) as pct "
			   		+ " from tbl_purchase p "
			   		+ " join tbl_purdetail d "
			   		+ "  on p.purnum = d.fk_purnum "
			   		+ " where p.instock = '입고' "
			   		+ " group by p.fk_supname "
			   		+ " order by p.fk_supname ";
			   
			   pstmt = conn.prepareStatement(sql);
			   
			   rs = pstmt.executeQuery();
			   
			   while(rs.next()) {
				   Map<String,String> paraMap = new HashMap<>();
				   paraMap.put("sup", rs.getString("sup") );
				   paraMap.put("pct", String.valueOf(rs.getDouble("pct")));
				   
				   sup_map_List.add(paraMap);
			   }
		   } finally {
			   close();
		   }
		   
		   return sup_map_List;
	}

	// 업체별 카테고리별 총 금액 가져오기
	@Override
	public List<Map<String, String>> sup_price() throws Exception {
		
		List<Map<String, String>> sup_price_map_List = new ArrayList<>();
		   
		   try {
			   conn = ds.getConnection();
			   
			   String sql = " select "
			   		+ "       p.fk_supname as sup, "
			   		+ "       c2.catename as category, "
			   		+ "       round(\r\n"
			   		+ "           sum(d.purqty * c1.purprice) / "
			   		+ "           ( "
			   		+ "               select sum(pd.purqty * ct.purprice) "
			   		+ "               from tbl_purchase pur "
			   		+ "               join tbl_purdetail pd "
			   		+ "                 on pur.purnum = pd.fk_purnum "
			   		+ "               join tbl_stock st "
			   		+ "                 on pd.fk_snum = st.snum "
			   		+ "               join tbl_catalogue ct "
			   		+ "                 on st.fk_pname = ct.pname "
			   		+ "               where pur.instock = '입고' "
			   		+ "					and pur.fk_supname = p.fk_supname "
			   		+ "           ) * 100 "
			   		+ "       , 2) as pct "
			   		+ " from tbl_purchase p "
			   		+ " join tbl_purdetail d "
			   		+ "  on p.purnum = d.fk_purnum "
			   		+ " join tbl_stock s "
			   		+ "  on d.fk_snum = s.snum "
			   		+ " join tbl_catalogue c1 "
			   		+ "  on s.fk_pname = c1.pname "
			   		+ " join tbl_category c2 "
			   		+ "  on c1.fk_catenum = c2.catenum "
			   		+ " where p.instock = '입고' "
			   		+ " group by p.fk_supname, c2.catename "
			   		+ " order by p.fk_supname, c2.catename; "
			   		+ " where p.instock = '입고' "
			   		+ " group by p.fk_supname "
			   		+ " order by p.fk_supname ";
			   
			   pstmt = conn.prepareStatement(sql);
			   
			   rs = pstmt.executeQuery();
			   
			   while(rs.next()) {
				   Map<String,String> paraMap = new HashMap<>();
				   paraMap.put("sup", rs.getString("sup") );
				   paraMap.put("pct", String.valueOf(rs.getDouble("pct")));
				   
				   sup_price_map_List.add(paraMap);
			   }
		   } finally {
			   close();
		   }
		   
		   return sup_price_map_List;
	}
}
