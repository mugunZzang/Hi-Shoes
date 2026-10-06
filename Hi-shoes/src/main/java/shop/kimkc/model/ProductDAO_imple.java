package shop.kimkc.model;

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

import shop.kimkc.domain.CatalogueDTO;
import shop.kimkc.domain.ProductDTO;
import shop.kimkc.domain.Product_ImageDTO;


public class ProductDAO_imple implements ProductDAO {
	
	private DataSource ds; // DataSource ds 가 DBCP(DB Connection Pool)이다. 
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	
	// 생성자
	public ProductDAO_imple() {
		
		try {
			Context initContext = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
		    ds = (DataSource)envContext.lookup("jdbc/myoracle");
		    // lookup()속에 /MyMVC/src/main/webapp/META-INF/context.xml에 지정한 이름을 적어준다. 
		    
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

	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	

	// 검색키워드를 적용한 상품목록 가져오기
	@Override
	public List<ProductDTO> getProductList(String searchKeyword) throws SQLException {
		
		List<ProductDTO> productList = new ArrayList<>();
		
		try {
			conn = ds.getConnection();
					
			
			String sql = " SELECT A.pnum, A.pimage1, B.pname, B.brand, B.saleprice, B.regprice "
					+ " FROM tbl_product A JOIN tbl_catalogue B "
					+ " ON A.fk_pname = B.pname "
					+ " WHERE A.fk_pname LIKE ? "
					+ " ORDER BY A.pnum ASC "; 
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, "%" + searchKeyword + "%");
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				//System.out.println("값이 있긴함");
				ProductDTO pdto = new ProductDTO();
				pdto.setPnum(rs.getInt("pnum"));
				pdto.setPimage1(rs.getString("pimage1"));
				//System.out.println("판매번호 : " + rs.getInt("pnum"));
				
				CatalogueDTO cdto = new CatalogueDTO();
				cdto.setPname(rs.getString("pname"));
				cdto.setSaleprice(rs.getInt("saleprice"));
				cdto.setRegprice(rs.getInt("regprice"));
				cdto.setBrand(rs.getString("brand"));
				
				pdto.setCatalogueDTO(cdto);
				
				productList.add(pdto);
			}
			
		} finally {
			close();
		}
		
		return productList;
	}


	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 클릭한 상품의 정보를 갖고오기
	@Override
	public ProductDTO getProductInfo(String pnum) throws SQLException {
		
		ProductDTO pdto = new ProductDTO();
		
		try {
			
			conn = ds.getConnection();
			
			String sql = " select "
					+ "    A.fk_pname, A.pimage1, A.pimage2, A.pcontent, A.deliveryfee, A.WARRANTY_SYSTEMFILENAME, A.WARRANTY_ORIGINFILENAME, "
					+ "    B.regprice, B.saleprice, B.brand "
					+ " from "
					+ "    tbl_product A join tbl_catalogue B "
					+ " on  A.fk_pname = B.pname "
					+ " where A.pnum = to_number(?) ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, pnum);
			rs = pstmt.executeQuery();
			rs.next();

			pdto.setFk_pname(rs.getString("fk_pname"));
			pdto.setPimage1(rs.getString("pimage1"));
			pdto.setPimage2(rs.getString("pimage2"));
			pdto.setPcontent(rs.getString("pcontent"));
			pdto.setDeliveryfee(rs.getInt("deliveryfee"));
			pdto.setWarranty_systemFileName(rs.getString("WARRANTY_SYSTEMFILENAME"));
			pdto.setWarranty_originFileName(rs.getString("WARRANTY_ORIGINFILENAME"));
			
			CatalogueDTO cdto = new CatalogueDTO();
			cdto.setRegprice(rs.getInt("regprice"));
			cdto.setSaleprice(rs.getInt("saleprice"));
			cdto.setBrand(rs.getString("brand"));
			
			pdto.setCatalogueDTO(cdto);
			
			
			sql = " select distinct image_name from tbl_product_image "
					+ " where fk_pnum = to_number(?) ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, pnum);
			rs = pstmt.executeQuery();
			
			List<Product_ImageDTO> pidtoList = new ArrayList<>();
			
			while(rs.next()) {
				Product_ImageDTO pidto = new Product_ImageDTO();
				pidto.setImage_name(rs.getString("image_name"));
				pidtoList.add(pidto);
			}
			
			pdto.setProdImageDTOList(pidtoList);
			
			
			
			
		} finally {
			close();
		}
		
		return pdto;
	}

	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	

	// 선택한 상품의 사이즈목록 갖고오기
	@Override
	public List<Integer> getProductSizes(String pnum) throws SQLException {
		List<Integer> sizeList = new ArrayList<>();
		
		try {
			
			conn = ds.getConnection();
			
			String sql = " SELECT DISTINCT "
					+ "    S.SSIZE "
					+ " FROM TBL_PRODUCT P "
					+ " JOIN TBL_CATALOGUE C "
					+ "    ON P.FK_PNAME = C.PNAME "
					+ " JOIN TBL_STOCK S "
					+ "    ON C.PNAME = S.FK_PNAME "
					+ " WHERE P.PNUM = to_number(?) "
					+ "  AND S.SQTY > 0 "
					+ " ORDER BY SSIZE ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, pnum);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				sizeList.add(rs.getInt("ssize"));
			}
				
			
		} finally {
			close();
		}
		
		
		return sizeList;
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	// 사이즈, 상품명으로 색상목록 갖고오기
	@Override
	public List<String> getColorBySsize(Map<String, String> paraMap) throws SQLException {
		List<String> colorList = new ArrayList<>();
		
		try {
			conn = ds.getConnection();
			
			String sql = " SELECT DISTINCT color FROM tbl_stock "
					+ " where fk_pname = ? "
					+ " and ssize = to_number(?) ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, paraMap.get("pname"));
			pstmt.setString(2, paraMap.get("ssize"));
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				colorList.add(rs.getString("COLOR"));
			}
			
			
		} finally {
			close();
		}
		
		return colorList;
	}
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
