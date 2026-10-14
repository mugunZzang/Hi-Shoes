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

import product.km.domain.ProductDTO;
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
		            + " from tbl_product ";

		        String searchWord = paraMap.get("searchWord");

		        if(searchWord != null && !searchWord.trim().isEmpty()) {

		            sql += " where fk_pname like '%' || ? || '%' ";
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
	            + " from tbl_product ";

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

	// 제품 삭제
	@Override
	public int productDelete(String pnum) throws Exception {
		int n = 0;
		
		try {
			conn = ds.getConnection();
			
			String sql = " delete from tbl_product "
					 +   " where pnum = to_number(?) ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, pnum);
			
			n = pstmt.executeUpdate();
			
		} finally {
			close();
		}
		
		return n;
	}

	// 카테고리 제품명 읽어오기
	@Override
	public List<Map<String, String>> ProductNameSelect(String searchWord) throws Exception {
		
		List<Map<String, String>> ProductNameList = new ArrayList<>();
		
		try {
			
			conn = ds.getConnection();
			
			String sql = " select pname from tbl_catalogue "
					  +  " where pname like '%' || ? || '%' " ;
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, searchWord);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Map<String,String> map = new HashMap<>();
				
				map.put("pname", rs.getString("pname"));
				
				ProductNameList.add(map);
			}
			
		} finally {
			close();
		}
		
		return ProductNameList;
	}

	// 판매번호 채번하기
	@Override
	public int getPnumOfProduct() throws Exception {
		
		int pnum = 0;
		   
		   try {
			   
			   conn = ds.getConnection();
			   
			   String sql = " Select seq_pnum.nextval AS PNUM "
					      + " From dual ";
			   
			   pstmt = conn.prepareStatement(sql);
			   rs = pstmt.executeQuery();
			   
			   rs.next();
			   pnum = rs.getInt("PNUM");
			   
			   
		   } finally {
			   close();
		   }
		   
		   return pnum;
	}

	// 판매상품 insert 해주기
	@Override
	public int productInsert(ProductDTO pdto) throws Exception{
		
		int n = 0;
	      
	      try {
	         conn = ds.getConnection();
	         
	         String sql = " insert into tbl_product(pnum, fk_pname, pimage1, pimage2, warranty_systemfilename, warranty_originfilename, deliveryfee, pcontent ) "
	                  + " values(?,?,?,?,?,?,?,?) ";
	         
	         pstmt = conn.prepareStatement(sql);
	         
	         pstmt.setInt(1, pdto.getPnum());
	         pstmt.setString(2, pdto.getFk_pname());
	         pstmt.setString(3, pdto.getPimage1());    
	         pstmt.setString(4, pdto.getPimage2()); 
	         pstmt.setString(5, pdto.getWarranty_systemfilename());
	         pstmt.setString(6, pdto.getWarranty_originfilename());
	         pstmt.setInt(7, pdto.getDeliveryfee());
	         pstmt.setString(8, pdto.getPcontent());
	         
	         n = pstmt.executeUpdate();
	         
	      } finally {
	         close();
	      }
	      
	      return n;
	}
	 // 추가이미지 테이블 insert
	 @Override
	   public int product_imagefile_insert(Map<String, String> paraMap) throws SQLException {
		
		      int n = 0;
		      
		      try {
		         conn = ds.getConnection();
		         
		         String sql = " insert into tbl_product_image(imgnum, fk_pnum, image_name) "
		                    + " values(seq_imgnum.nextval, ?, ?) ";
		         
		         pstmt = conn.prepareStatement(sql);
		         
		         pstmt.setInt(1, Integer.parseInt(paraMap.get("pnum")) );
		         pstmt.setString(2, paraMap.get("attachFileName"));
		         
		         n = pstmt.executeUpdate();
		         
		      } finally {
		         close();
		      }
		      
		      return n;
	   }
	
	 	// 최근 7일 업체별 입고 완료 총금액
		@Override
		public List<Map<String, String>> selectPurchaseChart() throws Exception {

		    List<Map<String, String>> chartList = new ArrayList<>();

		    try {
		        conn = ds.getConnection();

		        String sql =
		              " select to_char(trunc(p.purtime), 'yyyy-mm-dd') as purdate "
		            + "      , p.fk_supname as supname "
		            + "      , sum(pd.purqty * pd.purdprice) as totalamount "
		            + " from tbl_purchase p "
		            + " join tbl_purdetail pd "
		            + "   on p.purnum = pd.fk_purnum "
		            + " where p.instock = '입고' "
		            + "   and p.purtime >= trunc(sysdate) - 6 "
		            + "   and p.purtime < trunc(sysdate) + 1 "
		            + " group by trunc(p.purtime), p.fk_supname "
		            + " order by trunc(p.purtime), p.fk_supname ";

		        pstmt = conn.prepareStatement(sql);

		        rs = pstmt.executeQuery();

		        while (rs.next()) {

		            Map<String, String> map = new HashMap<>();

		            map.put("purdate", rs.getString("purdate"));
		            map.put("supname", rs.getString("supname"));
		            map.put("totalamount", rs.getString("totalamount"));

		            chartList.add(map);
		        }

		    } finally {
		        close();
		    }

		    return chartList;
		}
		
		// 최근 7일 업체별 입고완료 갯수
		@Override
		public List<Map<String, String>> selectcntChart() throws Exception {

		    List<Map<String, String>> chartList = new ArrayList<>();

		    try {
		        conn = ds.getConnection();

		        String sql = " select p.fk_supname as supname "
		        			   + "      , sum(pd.purqty) as totalqty "
		        	           + " from tbl_purchase p "
		        	           + " join tbl_purdetail pd on p.purnum = pd.fk_purnum "
		        	           + " where p.instock = '입고' "
		        	           + " group by p.fk_supname "
		        	           + " order by p.fk_supname ";

		        	pstmt = conn.prepareStatement(sql);
		        	rs = pstmt.executeQuery();

		        	while (rs.next()) { Map<String, String> map = new HashMap<>();

		        	    map.put("supname", rs.getString("supname"));
		        	    map.put("totalqty", rs.getString("totalqty"));

		        	    chartList.add(map);
		        }

		    } finally {
		        close();
		    }

		    return chartList;
		}

		// 해당 pnum에 해당하는 제품 정보를 불러온다.
		@Override
		public ProductDTO selectOneProd(String pnum) throws Exception {
			
			 ProductDTO pdto = null;

			    try {
			        conn = ds.getConnection();

			        String sql = " select pnum "
			                   + "      , fk_pname "
			                   + "      , pimage1 "
			                   + "      , pimage2 "
			                   + "      , warranty_systemfilename "
			                   + "      , warranty_originfilename "
			                   + "      , deliveryfee "
			                   + "      , pcontent "
			                   + " from tbl_product "
			                   + " where pnum = to_number(?) ";

			        pstmt = conn.prepareStatement(sql);
			        pstmt.setString(1, pnum);

			        rs = pstmt.executeQuery();

			        if (rs.next()) {
			            pdto = new ProductDTO();

			            pdto.setPnum(rs.getInt("pnum"));
			            pdto.setFk_pname(rs.getString("fk_pname"));
			            pdto.setPimage1(rs.getString("pimage1"));
			            pdto.setPimage2(rs.getString("pimage2"));
			            pdto.setWarranty_systemfilename(
			                rs.getString("warranty_systemfilename")
			            );
			            pdto.setWarranty_originfilename(
			                rs.getString("warranty_originfilename")
			            );
			            pdto.setDeliveryfee(rs.getInt("deliveryfee"));
			            pdto.setPcontent(rs.getString("pcontent"));
			        }

			    } finally {
			        close();
			    }

			    return pdto;
		}


		// 해당 pnum에 해당하는 추가이미지 제품 정보를 불러온다.
		@Override
		public List<String> selectProductImages(String pnum) throws Exception {
			List<String> imageList = new ArrayList<>();

		    try {
		        conn = ds.getConnection();

		        String sql = " select image_name "
		                   + " from tbl_product_image "
		                   + " where fk_pnum = to_number(?) ";

		        pstmt = conn.prepareStatement(sql);
		        pstmt.setString(1, pnum);

		        rs = pstmt.executeQuery();

		        while (rs.next()) {
		            imageList.add(rs.getString("image_name"));
		        }

		    } finally {
		        close();
		    }

		    return imageList;
		}

		// 제품 테이블 업데이트
		@Override
		public int productUpdate(Map<String, String> paraMap) throws Exception {
			
			int n = 0;
			
			try {
				
				conn = ds.getConnection();
				
				String sql = " UPDATE tbl_product SET pcontent = ?, deliveryfee = to_number(?) "
						   + " WHERE pnum = to_number(?) ";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1,paraMap.get("pcontent"));
				pstmt.setString(2,paraMap.get("deliveryfee"));
				pstmt.setString(3,paraMap.get("pnum"));
				
				n = pstmt.executeUpdate();
				
				
			} finally {
				close();
			}
			
			return n;
			
		}

		// 같은 제품명이 있으면 거르는 코드
		@Override
		public boolean sameNameProd(String pname) throws Exception{
			boolean bl = false;
			
			try {
				
				conn = ds.getConnection();
				
				String sql = " select * from tbl_product"
						   + " where fk_pname = ? ";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, pname);
				
				rs = pstmt.executeQuery();
				
				bl = rs.next();
				
			} finally {
				close();
			}
			return bl;
		}

}
