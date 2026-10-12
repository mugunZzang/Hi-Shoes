package admin.supplier.jungyj.model;

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

import admin.supplier.jungyj.domain.SupplierDTO;
import util.security.AES256;
import util.security.SecretMyKey;

public class SupplierDAO_imple implements SupplierDAO {

	// field
	private DataSource ds;                // DataSource ds 는 DBCP(Database Connection Pool) 이다.
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private AES256 aes;
	
	// 생성자
	public SupplierDAO_imple() {
		try {
			Context initContext = new InitialContext();
	        Context envContext  = (Context)initContext.lookup("java:/comp/env");
	        ds = (DataSource)envContext.lookup("jdbc/myoracle");
	        // lookup()속에 /MyMVC/src/main/webapp/META-INF/context.xml에 지정한 이름을 적어주면 자동 주입됩니다.
	        
	        aes = new AES256(SecretMyKey.KEY);
	        // SecretMyKey.KEY 은 우리가 만든 암호화/복호화 키이다.
	        // 왜 생성자에서 new? try-catch 는 메서드에서만 사용 가능함 필드에서는 선언을 하기 때문에 불가능해짐 
	        
		} catch (NamingException e) {
			e.printStackTrace();
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	}
	
	// method
	
	// 사용한 자원을 반납하는 close() 메소드 생성하기 
    private void close() {
       try {
          if(rs != null)    {rs.close();    rs=null;}
          if(pstmt != null) {pstmt.close(); pstmt=null;}
          if(conn != null)  {conn.close();  conn=null;}
       } catch(SQLException e) {
          e.printStackTrace();
       }
    } // end of private void close()---------------

    
    
    
	// *** 페이징 처리를 안한 공급업체 목록 보여주기 *** //
	@Override
	public List<SupplierDTO> selectSuppliernopaging() throws SQLException {
		
		List<SupplierDTO> supplierList = new ArrayList<>();
		
		try {
			conn = ds.getConnection();
			
			String sql = " SELECT supname, sbusinum, ceo, smobile, semail "
					   + " FROM tbl_supplier ";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				SupplierDTO sdto = new SupplierDTO();
				sdto.setSupname(rs.getString("supname"));
				sdto.setSbusinum(rs.getInt("sbusinum"));
				sdto.setCeo(rs.getString("ceo"));
				sdto.setSmobile(aes.decrypt(rs.getString("smobile")));
				sdto.setSemail(aes.decrypt( rs.getString("semail")));
				
				supplierList.add(sdto);
			} // end of while--------------------

			if(supplierList.size() > 0) {
				System.out.println("[INFO] admin.supplier.jungyj.controller.SupplierList 공급업체 조회 성공");
			}
		} catch (UnsupportedEncodingException | GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		
		return supplierList;
	} // end of public List<SupplierDTO> selectSuppliernopaging() throws SQLException-------------------------------

	
	// 공급업체 등록(INSERT)
	@Override
	public int supplierRegister(Map<String, String> paraMap) throws SQLException {
	    int result = 0;
	    
	    try {
			conn = ds.getConnection();
			
			String sql = " INSERT INTO tbl_supplier (supname, sbusinum, ceo, smobile, semail) "
					   + " VALUES (?, ?, ?, ?, ?) ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, paraMap.get("supplyName"));
			pstmt.setInt(2, Integer.parseInt(paraMap.get("bizNo")));
			pstmt.setString(3, paraMap.get("ceoName"));
			pstmt.setString(4, aes.encrypt(paraMap.get("supplyTel")));
			pstmt.setString(5, aes.encrypt(paraMap.get("supplyEmail")));
	    	
			result = pstmt.executeUpdate();
			
		} catch (UnsupportedEncodingException | GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
	    
		return result;
	} // end of public int supplierRegister(Map<String, String> paraMap) throws SQLException-------------------------

	
	// 공급업체 이름 조회(SELECT)
	@Override
	public List<String> selectSupplierNameList() throws SQLException {
	    List<String> nameList = new ArrayList<>();

	    try {
	        conn = ds.getConnection();

	        String sql = " SELECT supname FROM tbl_supplier ORDER BY supname ";
	        pstmt = conn.prepareStatement(sql);
	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	            nameList.add(rs.getString("supname"));
	        }

	    } finally {
	        close();
	    }

	    return nameList;
	} // end of public List<String> selectSupplierNameList() throws SQLException----------

}
