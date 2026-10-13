package myshop.kth.model;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


import member.kth.domain.AdminMemberDTO;
import myshop.kth.domain.AdminProductDTO;
import myshop.kth.domain.AdminStockDTO;
import util.security.AES256;
import util.security.SecretMyKey;
import myshop.kth.domain.AdminCatalogueDTO;
import myshop.kth.domain.AdminCategoryDTO;

public class AdminProductDAO_imple implements AdminProductDAO {
	
	
	
	private DataSource ds; // DataSource ds 가 DBCP(DB Connection Pool)이다. 
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private AES256 aes;
	
	// 생성자
	public AdminProductDAO_imple() {
		
		try {
			Context initContext = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
		    ds = (DataSource)envContext.lookup("jdbc/myoracle");
		    // lookup()속에 /MyMVC/src/main/webapp/META-INF/context.xml에 지정한 이름을 적어준다. 
		    aes = new AES256(SecretMyKey.KEY);
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
	
	
	
	//총 주문수량 구하기
	@Override
	public int getTotalCountOrder(String userid) throws SQLException {
		
		int totalCountOrder = 0;
		try {
			conn = ds.getConnection();
			
			String sql=" SELECT COUNT(*) AS CNT "
					+ " FROM tbl_order o JOIN tbl_orderdetail od "
					+ " ON o.onum = od.fk_onum ";
			
			if("admin".equals(userid)) {
				pstmt = conn.prepareStatement(sql);
			}
				
			else{ //일반사용자일때
				sql += " WHERE fk_userid= ? ";

				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, userid);
			
			}
			
			rs=pstmt.executeQuery();
			rs.next();
			
			totalCountOrder = rs.getInt("CNT");
			
			
		} finally {
			close();
		}
		return totalCountOrder;
	}

	
	
	//주문내역 조회해서 가져오기 
	@Override
      public List<Map<String, String>> getOrderList(Map<String, String> paraMap) throws SQLException {

		    List<Map<String, String>> order_map_List = new ArrayList<>();
		
		      try {
		             conn = ds.getConnection();
		
		        String sql = " SELECT o.onum AS onum "                     //주문번호
		                   + "      , o.orderdate AS orderdate "               //주문상세번호
		                   + "      , o.orderstatus AS orderstatus "           //주문상태
		                   + "      , od.oqty AS oqty "                    //수량
		                   + "      , c.pname AS pname "                       //제품명
		                   + "      , c.regprice AS regprice "                 //정가
		                   + "      , c.saleprice AS saleprice "               //판매가
		                   + "      , (od.odrprice * od.oqty) AS ordertotal "  //주문총액
		                   + "      , p.pimage1 AS pimage1 "                   //이미지1
		                   + "      , o.fk_userid AS userid "                  //회원번호
		                   +"       , s.snum AS snum"                          //제품번호
		                   + " FROM tbl_order o "                         
		                   + " JOIN tbl_orderdetail od "
		                   + " ON o.onum = od.fk_onum "
		                   + " JOIN tbl_stock s "
		                   + " ON od.fk_snum = s.snum "
		                   + " JOIN tbl_catalogue c "
		                   + " ON s.fk_pname = c.pname "
		                   + " JOIN tbl_product p "
		                   + " ON c.pname = p.fk_pname "
		                   + " ORDER BY o.orderdate DESC, od.odnum ASC "
		                   + " OFFSET (?-1)*? ROWS "
		                   + " FETCH NEXT ? ROWS ONLY ";
		
		        pstmt = conn.prepareStatement(sql);
		
		        int currentShowPageNo =
		                Integer.parseInt(paraMap.get("currentShowPageNo"));
		
		        int sizePerPage = 4;
		
		        pstmt.setInt(1, currentShowPageNo);
		        pstmt.setInt(2, sizePerPage);
		        pstmt.setInt(3, sizePerPage);
		
		        rs = pstmt.executeQuery();
		
		        while (rs.next()) {
		
		            String ordernum = rs.getString("onum");
		            String orderdate = rs.getString("orderdate");
		            String orderstatus = rs.getString("orderstatus");
		            String orderqty = rs.getString("oqty");
		            String saleprice = rs.getString("saleprice");
		            String pname = rs.getString("pname");
		            String ordertotal = rs.getString("ordertotal");
		            String pimage1 = rs.getString("pimage1");
		            String userid = rs.getString("userid");
		            String snum = rs.getString("snum");
		            String regprice = rs.getString("regprice");
		
		            Map<String, String> odrmap = new HashMap<>();
		
		            odrmap.put("ONUM", ordernum);
		            odrmap.put("ORDERDATE", orderdate);
		            odrmap.put("ORDERSTATUS", orderstatus);
		            odrmap.put("OQTY", orderqty);
		            odrmap.put("SALEPRICE", saleprice);
		            odrmap.put("PNAME", pname);
		            odrmap.put("ORDERTOTAL", ordertotal);
		            odrmap.put("PIMAGE1", pimage1);
		            odrmap.put("USERID", userid);
		            odrmap.put("SNUM", snum);
		            odrmap.put("REGPRICE", regprice);
		
		            order_map_List.add(odrmap);

        }// end of while(rs.next())------------------------

	    }
	    finally {
	        close();
	    }

       return order_map_List;
     }//end of public List<Map<String, String>> getOrderList(Map<String, String> paraMap) throws SQLException
	
	
	//배송상태를 '상품 준비중'로 변경하기
	@Override
	public int updatePreparingProduct(String onumSnum) throws SQLException {
		

		   int n = 0;
		   try {
			   conn=ds.getConnection();
			   String sql = " UPDATE tbl_order o "
			              + " SET orderstatus = '상품 준비 중', arriveDate = SYSDATE "
			              + " WHERE o.onum IN ( "
			              + " SELECT od.fk_onum "
			              + " FROM tbl_orderdetail od "
			              + " WHERE od.fk_onum || '/' || od.fk_snum IN (" + onumSnum + ") "
			              + " ) ";
			   
			   pstmt = conn.prepareStatement(sql);
			  
		         
		       n = pstmt.executeUpdate();
		         
		   }finally {
			   close();
		   }
		return n;
	}

	
	//배송상태를 '출고 완료'로 변경하기
	@Override
	public int updateDeliverEnd(String onumSnum) throws SQLException {
		

		   int n = 0;
		   try {
			   conn=ds.getConnection();
			   String sql = " UPDATE tbl_order o "
			           + " SET orderstatus = '출고 완료', arriveDate = SYSDATE "
			           + " WHERE o.onum IN ( "
			           + " SELECT od.fk_onum "
			           + " FROM tbl_orderdetail od "
			           + " WHERE od.fk_onum || '/' || od.fk_snum IN (" + onumSnum + ") "
			           + " ) ";
			   
			   pstmt = conn.prepareStatement(sql);
			   
		         
		       n = pstmt.executeUpdate();
		         
		   }finally {
			   close();
		   }
		return n;
	}
	
	
	
	
	
	// 주문번호로 사용자 정보를 조회하기
	@Override
	public AdminMemberDTO onumOwnerMemberInfo(String onum) throws SQLException {

	      AdminMemberDTO mdto = null;
	      
	      try {
	         conn = ds.getConnection();
	                  
	         String sql = " select userid, company, email, mobile, postcode, address, detailaddress, extraaddress " + 
	                    "      , to_char(registerday, 'yyyy-mm-dd') AS registerday " +
	                       " from tbl_member " +
	                       " where userid = (select fk_userid " + 
	                      "                 from tbl_order " + 
	                      "                 where onum = ? ) ";
	         
	         pstmt = conn.prepareStatement(sql);
	         pstmt.setString(1, onum);
	         
	         rs = pstmt.executeQuery();
	         
	         boolean isExists = rs.next();
	         
	         if(isExists) {
	            mdto = new AdminMemberDTO();
	            
	            mdto.setUserid(rs.getString("userid"));
	            mdto.setCompany(rs.getString("company"));
	            mdto.setEmail(aes.decrypt(rs.getString("email")));    // 복호화 
	            mdto.setMobile(aes.decrypt(rs.getString("mobile"))); // 복호화 
	            mdto.setPostcode(rs.getInt("postcode"));
	            mdto.setAddress(rs.getString("address"));
	            mdto.setDetailaddress(rs.getString("detailaddress"));
	            mdto.setExtraaddress(rs.getString("extraaddress"));
	            
	         
	            mdto.setRegisterday(rs.getString("registerday"));
	         }
	         
	      } catch (GeneralSecurityException | UnsupportedEncodingException e) {
	         e.printStackTrace();
	      } finally {
	         close();
	      }
	      
	      return mdto;         
	   
	}

	

	
	
	  // 페이징 처리를 위한 특정 카테고리에 대한 총페이지수(totalPage) 알아오기 // 
		@Override
		public int getTotalPage(String cnum) throws SQLException {
			
			int totalPage = 0;
			
			try {
				conn = ds.getConnection();
				
				String sql = " SELECT CEIL(COUNT(*)/12) AS TOTALPAGE " // 12 가 sizePerPage 이다.
						   + " FROM tbl_order"
						   + " WHERE fk_cnum = to_number(?) ";
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, cnum);
				
				rs = pstmt.executeQuery();
				
				rs.next();
				
				totalPage = rs.getInt("TOTALPAGE");
				
			} finally {
				close();
			}
			
			return totalPage;
		}// end of public int getTotalPage(String cnum) throws SQLException-------- 


}
