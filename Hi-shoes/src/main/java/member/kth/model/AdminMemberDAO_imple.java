package member.kth.model;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


import member.kth.domain.AdminMemberDTO;
import member.kth.model.AdminMemberDAO;
import util.security.AES256;
import util.security.SecretMyKey;

public class AdminMemberDAO_imple implements AdminMemberDAO{
	
	private DataSource ds; // DataSource ds 가 DBCP(DB Connection Pool)이다. 
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private AES256 aes;
	
	//생성자
	public AdminMemberDAO_imple() {

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
	
	//사용한 자원을 반납하는 close() 메소드 생성하기 
		private void close() {
			try {
				if(rs != null)    {rs.close();    rs=null;}
				if(pstmt != null) {pstmt.close(); pstmt=null;}
				if(conn != null)  {conn.close();  conn=null;}
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		
		

	// === 검색 조건에 따른 회원 목록의 전체 페이지 수 구하기 === //
	@Override
	public int getTotalPage(Map<String, String> paraMap) throws SQLException {
        int totalPage = 0;
	    
	    try {
	        conn = ds.getConnection();
	        
	        // 1. 기본 SQL문 구성 (admin에 작은따옴표 추가)
	        String sql = " SELECT CEIL(COUNT(*)/?) AS TOTALPAGE "
	                   + " FROM tbl_member "
	                   + " WHERE userid != 'admin' "; 
	        
	        String colname = paraMap.get("searchType");
	        String searchWord = paraMap.get("searchWord");
	        
	        // 2. 검색 조건에 따른 SQL문 동적 결합
	        if(colname != null && !"".equals(colname) && searchWord != null && !"".equals(searchWord)) {
	            if("company".equals(colname)) {
	                sql += " AND company LIKE '%' || ? || '%' "; 
	            } else if("userid".equals(colname)) {
	                sql += " AND LOWER(userid) LIKE '%' || LOWER(?) || '%' ";
	            } else if("email".equals(colname)) {
	                sql += " AND EMAIL = ? ";
	            }
	        }
	        
	        pstmt = conn.prepareStatement(sql);
	        
	        // 3. 위치 홀더(?)에 값 바인딩
	        if(colname != null && !"".equals(colname) && searchWord != null && !"".equals(searchWord)) {
	            if("email".equals(colname)) {
	                pstmt.setInt(1, Integer.parseInt(paraMap.get("sizePerPage")));
	                pstmt.setString(2, aes.encrypt(searchWord));
	            } else {
	                pstmt.setInt(1, Integer.parseInt(paraMap.get("sizePerPage")));
	                pstmt.setString(2, searchWord);
	            }
	        } else {
	            pstmt.setInt(1, Integer.parseInt(paraMap.get("sizePerPage")));
	        }
	        
	        // 4. SQL 실행 및 결과 추출 (rs.next() 필수)
	        rs = pstmt.executeQuery();
	        
	        if(rs.next()) {
	            totalPage = rs.getInt("TOTALPAGE");
	        }
	        
	    } catch (UnsupportedEncodingException | GeneralSecurityException e) {
	        e.printStackTrace();
	    } finally {
	        // 5. 자원 반납
	        close();
	    } // <-- try-catch-finally 블록 끝
	    
	      return totalPage;
	      
		
	}//end of public int getTotalPage(Map<String, String> paraMap) throws SQLException ---
	
	
	
	
	
	//=== 검색 조건에 따른 페이징 처리된 회원 목록 보여주기 ===//
	@Override
	public List<AdminMemberDTO> select_Member_paging(Map<String, String> paraMap) throws SQLException {
		
   List<AdminMemberDTO> memberList = new ArrayList<>();
		
		try {
			conn= ds.getConnection();
			String sql=" SELECT userid, company, email "
					  +" FROM tbl_member "
					  +" WHERE userid != 'admin' AND status = '가입중' ";
					 
			
			String colname =paraMap.get("searchType");
			String searchWord =paraMap.get("searchWord");
			
			if(!"".equals(colname) && !"".equals(searchWord)) {
				//검색대상 및 검색어가 있는 경우
				
				if("company".equals(colname)) {
				sql +=" AND company Like '%' || ? || '%' ";
				//컬럼명과 테이블명은 위치홀더로 사용하면 꽝이다.
				//위치홀더로 들어오는 것은 컬럼명과 테이블명이 아닌 데이터값만 들어온다.
				} 
				else if("userid".equals(colname)) {
					sql +=" AND LOWER(userid) LIKE '%' || LOWER(?) || '%' ";
				}
				else if ("email".equals(colname)) {
					sql += " AND EMAIL = ? ";
				}
			}//end of if(!"".equals(colname) && !"".equals(searchWord))---
			
			sql += " ORDER BY registerday DESC " //ORDER BY 다음에 나오는 컬럼의 값은 고유한 값을 가지도록 만들어야 한다.
				   +" OFFSET (?-1)*? ROW "
				   +" FETCH NEXT ? ROW ONLY ";
			
			
			pstmt = conn.prepareStatement(sql);
			
			if(!"".equals(colname) && !"".equals(searchWord)) {
				
				if("email".equals(colname)) { //검색대상이 email인 경우
					
					pstmt.setString(1,aes.encrypt(searchWord));
				}
				else { //검색대상이 email이 아닌 경우
					pstmt.setString(1, searchWord);
				}
				
				
				
				
				pstmt.setInt(2, Integer.parseInt(paraMap.get("currentShowPageNo")));
				pstmt.setInt(3,Integer.parseInt(paraMap.get("sizePerPage")));
				pstmt.setInt(4,Integer.parseInt(paraMap.get("sizePerPage")));
				
			} else {
				pstmt.setInt(1, Integer.parseInt(paraMap.get("currentShowPageNo")));
				pstmt.setInt(2,Integer.parseInt(paraMap.get("sizePerPage")));
				pstmt.setInt(3,Integer.parseInt(paraMap.get("sizePerPage")));
				
				}
				
			 //end of if(!"".equals(colname) && !"".equals(searchWord))---
			
			rs= pstmt.executeQuery();
			
			while(rs.next()) {
				
				
				AdminMemberDTO mbrdto = new AdminMemberDTO();
				mbrdto.setUserid(rs.getString("userid"));
				mbrdto.setCompany(rs.getString("company"));
				mbrdto.setEmail(aes.decrypt(rs.getString("email")));
				
				
				memberList.add(mbrdto);
				
			}//end of while(rs.next())---
			
			
		} catch(UnsupportedEncodingException | GeneralSecurityException e) {
			e.printStackTrace();
		
		}finally {
			close();
		}
		
		return memberList;
		
		
	
	}//end of public List<AdminMemberDTO> select_Member_paging(Map<String, String> paraMap) throws SQLException---

	
	
	
	
	
	//=== 검색 조건에 따른 전체 회원 수 구하기 ===//
	@Override
	public int getTotalMemberCount(Map<String, String> paraMap) throws SQLException {
	    int totalPage = 0; // 최종 리턴할 총 페이지 수
	    
	    try {
	        conn = ds.getConnection();
	        
	        
	        String sql = " SELECT COUNT(*) AS TOTALPAGE "
	                   + " FROM tbl_member "
	                   + " WHERE userid != 'admin' ";
	                   
	        
	        String colname = paraMap.get("searchType");
	        String searchWord = paraMap.get("searchWord");
	        
	        if( !"".equals(colname) && !"".equals(searchWord) ) {
	            if("company".equals(colname)) {
	                sql += " AND company LIKE '%' || ? || '%' "; 
	            } else if("userid".equals(colname)) {
	                sql += " AND LOWER(userid) LIKE '%' || LOWER(?) || '%' ";
	            } else if("email".equals(colname)) {
	                sql += " AND email = ? ";
	            }
	        }
	        
	        pstmt = conn.prepareStatement(sql);
	        
	        if( !"".equals(colname) && !"".equals(searchWord) ) {
	            if("email".equals(colname)) {
	                pstmt.setString(1, aes.encrypt(searchWord)); 
	            } else {
	                pstmt.setString(1, searchWord);
	            }
	        }
	        
	        rs = pstmt.executeQuery();
	        
	        if(rs.next()) {
	           
	            totalPage = rs.getInt("TOTALPAGE"); 
	            
	            // 한 페이지당 보여줄 행의 개수 (예: 10)
	            //int sizePerPage = Integer.parseInt(paraMap.get("sizePerPage"));
	            
	           
	        }
	        
	    } catch (UnsupportedEncodingException | GeneralSecurityException e) {
	        e.printStackTrace();
	    } finally {
	        close();
	    } 
	    
	    return totalPage;
	

	      
	}//end of public int getTotalMemberCount(Map<String, String> paraMap) throws SQLException---

	
	
	//=== 회원 상세 정보 보여주기 ===//
	@Override
	public AdminMemberDTO selectOneMember(String userid) throws SQLException {
		
             AdminMemberDTO member = null;
		
		try {
			conn=ds.getConnection();
			
			String sql = " select userid, company, "
	                  + " to_char(registerday, 'yyyy-mm-dd hh24:mi:ss') AS registerday, "
	                  + " idle,ban, email, mobile, postcode, address, detailaddress, extraaddress "
	                  + " from tbl_member "
	                  + " where status = '가입중' and userid = ? " ;
	                  
				
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			
			
			rs= pstmt.executeQuery();
			
			if(rs.next()) {
				member= new AdminMemberDTO();
				
				member.setUserid(rs.getString("userid"));
				member.setCompany(rs.getString("company"));
				
				member.setRegisterday(rs.getString("registerday"));
				member.setIdle(rs.getString("idle"));
				member.setBan(rs.getString("ban"));
				
				member.setEmail(aes.decrypt(rs.getString("email")) );
				member.setMobile(aes.decrypt(rs.getString("mobile")) );
				
				member.setPostcode(rs.getInt("postcode"));
				member.setAddress(rs.getString("address"));
				member.setDetailaddress(rs.getString("detailaddress"));
				member.setExtraaddress(rs.getString("extraaddress"));
				
				
			
				
			}//end of if(rs.next())---
			
		} catch(UnsupportedEncodingException | GeneralSecurityException e) {
			e.printStackTrace();
			
		}
			finally {
			close();
		}		
		
		return member;
		
		
	}//end of public AdminMemberDTO selectOneMember(String userid) throws SQLException---

	

}
