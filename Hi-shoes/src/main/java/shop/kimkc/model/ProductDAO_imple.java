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
import shop.kimkc.domain.CategoryDTO;
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
	
	// 검색결과로 나온 상품목록의 총 페이지수 가져오기
	@Override
	public int getTotalProductPage(String searchKeyword) throws SQLException {
		int totalProductPage = 0;
		
		try {
			conn = ds.getConnection();
			// 상품명, 브랜드에  키워드가 포함되는거
			String sql = "  SELECT CEIL(COUNT(*)/12) AS PAGE "
					+ " from tbl_product A join tbl_catalogue B "
					+ " on A.fk_pname = B.pname "
					+ " where B.brand like ? "
					+ " or A.fk_pname like ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			String keyword = "%" + searchKeyword + "%";
			pstmt.setString(1, keyword);
			pstmt.setString(2, keyword);
			
			rs = pstmt.executeQuery();
			
			rs.next();
			
			totalProductPage = rs.getInt("page");
			
		} finally {
			close();
		}
		
		return totalProductPage;
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	//특정 검색어에 의한 결과를 사용자가 보고자 하는 특정 페이지번호에 해당하는 제품들을 조회해온다.
	@Override
	public List<ProductDTO> selectProductByKeyword(Map<String, String> paraMap) throws SQLException{
		List<ProductDTO> productList = new ArrayList<>();
		
		try {
			conn = ds.getConnection();
					
			
			String sql = " SELECT A.pnum, A.pimage1, B.pname, B.brand, B.saleprice, B.regprice "
					+ " FROM tbl_product A JOIN tbl_catalogue B "
					+ " ON A.fk_pname = B.pname "
					+ " WHERE A.fk_pname LIKE ? "
					+ " OR B.brand LIKE ? "
					+ " ORDER BY A.pnum ASC "
					+ " OFFSET (?-1)*12 ROW "
					+ " FETCH NEXT 12 ROW ONLY "; 
			
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, "%" + paraMap.get("searchKeyword") + "%");
			pstmt.setString(2, "%" + paraMap.get("searchKeyword") + "%");
			pstmt.setInt(3, Integer.parseInt(paraMap.get("currentShowPageNo")));
			
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
	
	
	// 판매등록된 브랜드 목록 갖고오기
	@Override
	public List<CatalogueDTO> getBrandList() throws SQLException {
		List<CatalogueDTO> brandList = new ArrayList<>();
		
		try {
			conn = ds.getConnection();
			
			String sql = " select distinct A.brand "
					+ " from tbl_catalogue A JOIN tbl_product B "
					+ " ON A.pname = B.fk_pname ";
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				CatalogueDTO brand = new CatalogueDTO();
				brand.setBrand(rs.getString("brand"));
				brandList.add(brand);
			}
			
		} finally {
			close();
		}
		
		return brandList;
	}


	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public int getTotalProductPageByFilter(Map<String, String> paraMap) throws SQLException {
		int totalProductPage = 0;
		
		try {
			conn = ds.getConnection();
			
			String category = paraMap.get("category");	// 임의로 하나
			String str_ssize = paraMap.get("str_ssize");		// 사이즈 "270,275,290" 요런식으로 받아옴
			String str_brand = paraMap.get("str_brand");		// 브랜드 "아디다스,아식스,뉴발란스" 요런식으로 받아옴
			String str_color = paraMap.get("str_color");		// 색상 "BLACK,WHITE,SILVER" 
			String keyword = paraMap.get("keyword");		// 검색어
			String min_price = paraMap.get("min_price");		// 최소가격 "10000"
			String max_price = paraMap.get("max_price");
			
			// 상품명, 브랜드에  키워드가 포함되는거
			String sql = " select ceil(count(distinct A.fk_pname) / 12) as cnt "
					+ " from tbl_product A "
					+ "    JOIN tbl_catalogue B "
					+ "    ON A.fk_pname = B.pname "
					+ "    JOIN tbl_category C "
					+ "    ON B.fk_catenum = C.catenum "
					+ "    JOIN tbl_stock D "
					+ "    ON B.pname = D.fk_pname "
					+ " where "
					+ " C.catename like ? " ;
			if (!str_brand.isBlank()) {
				sql += " and B.brand in (" + str_brand + ") ";
			}
			if (!str_ssize.isBlank()) {
				sql += " and D.ssize in (" + str_ssize + ") ";
			}
			if (!str_color.isBlank()) {
				sql += " and D.color in (" + str_color + ") ";
			}
			
					
			sql += " and B.saleprice between ? and ? "
					+ " and A.fk_pname like ? " ;
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, "%" + category + "%");
			pstmt.setInt(2, Integer.parseInt(min_price));
			pstmt.setInt(3, Integer.parseInt(max_price));
			pstmt.setString(4, "%" + keyword + "%");
			
			rs = pstmt.executeQuery();
			rs.next();
			
			totalProductPage = rs.getInt("cnt");
			

			
		} finally {
			close();
		}
		
		return totalProductPage;
	}


	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	// 특정 필터에 의한 결과를 사용자가 보고자 하는 특정 페이지번호에 해당하는 제품들을 조회 
	@Override
	public List<ProductDTO> selectProductByFilter(Map<String, String> paraMap) throws SQLException {
		List<ProductDTO> productList = new ArrayList<>();
		
		try {
			conn = ds.getConnection();
			
			String category = paraMap.get("category");	// 임의로 하나
			String str_ssize = paraMap.get("str_ssize");		// 사이즈 "270,275,290" 요런식으로 받아옴
			String str_brand = paraMap.get("str_brand");		// 브랜드 "아디다스,아식스,뉴발란스" 요런식으로 받아옴
			String str_color = paraMap.get("str_color");		// 색상 "BLACK,WHITE,SILVER" 
			String keyword = paraMap.get("keyword");		// 검색어
			String min_price = paraMap.get("min_price");		// 최소가격 "10000"
			String max_price = paraMap.get("max_price");
			
			// 상품명, 브랜드에  키워드가 포함되는거
			String sql = " SELECT distinct A.pnum, A.pimage1, B.pname, B.brand, B.saleprice, B.regprice "
					+ " from tbl_product A "
					+ "    JOIN tbl_catalogue B "
					+ "    ON A.fk_pname = B.pname "
					+ "    JOIN tbl_category C "
					+ "    ON B.fk_catenum = C.catenum "
					+ "    JOIN tbl_stock D "
					+ "    ON B.pname = D.fk_pname "
					+ " where "
					+ " C.catename like ? ";
			if (!str_brand.isBlank()) {
				sql += " and B.brand in (" + str_brand + ") ";
			}
			if (!str_ssize.isBlank()) {
				sql += " and D.ssize in (" + str_ssize + ") ";
			}
			if (!str_color.isBlank()) {
				sql += " and D.color in (" + str_color + ") ";
			}
	
			sql 	+= " and B.saleprice between ? and ? "
					+ " and A.fk_pname like ? " 
					+ " ORDER BY A.pnum ASC "
					+ " OFFSET (?-1)*12 ROW "
					+ " FETCH NEXT 12 ROW ONLY ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, "%" + category + "%");
			pstmt.setInt(2, Integer.parseInt(min_price));
			pstmt.setInt(3, Integer.parseInt(max_price));
			pstmt.setString(4, "%" + keyword + "%");
			pstmt.setInt(5, Integer.parseInt(paraMap.get("currentShowPageNo")));
			
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
}
