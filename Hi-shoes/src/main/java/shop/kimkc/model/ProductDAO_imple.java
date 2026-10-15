package shop.kimkc.model;

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

import shop.kimkc.domain.CatalogueDTO;
import shop.kimkc.domain.ProductDTO;
import shop.kimkc.domain.Product_ImageDTO;
import shop.kimkc.domain.QnADTO;
import shop.kimkc.domain.ReviewDTO;
import shop.kimkc.domain.StockDTO;


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
			
			pstmt = conn.prepareStatement(sql);
			
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
			String brand = paraMap.get("brand");		// 브랜드 "아디다스,아식스,뉴발란스" 요런식으로 받아옴
			String str_color = paraMap.get("str_color");		// 색상 "BLACK,WHITE,SILVER" 
			String keyword = paraMap.get("keyword");		// 검색어
			String min_price = paraMap.get("min_price");		// 최소가격 "10000"
			String max_price = paraMap.get("max_price");
			/*
			System.out.println("category : " +category );
			System.out.println("str_ssize : " +str_ssize);
			System.out.println("brand : " + brand );
			System.out.println("keyword : " + keyword);
			System.out.println("min_price : " + min_price);
			System.out.println("max_price : " + max_price);
			System.out.println("---------------------------");
			*/
			
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
			if (!brand.isBlank()) {
				sql += " and B.brand = ? ";
			}
			if (!str_ssize.isBlank()) {
				sql += " and D.ssize in (" + str_ssize + ") ";
			}
			if (!str_color.isBlank()) {
				if(str_color.contains("OTHER")) {
					// 색상이 SILVER, BLACK, WHITE, BROWN, RED, BLUE, YELLOW, IVORY 인 걸 제외하고 갖고온다.
					// str_color 가 만약 'BLACK','WHITE','OTHER' 이런식이면, 검정 흰색인 상품 + 위조건 만족하는 상품들  을 갖고오는 거임.
					sql += " and ( "
							+ " D.color in (" + str_color + ") "
							+ " or D.color not in ('SILVER','BLACK','WHITE','BROWN','RED','BLUE','YELLOW','IVORY') "
							+ ") ";
				}
				else {
					sql += " and D.color in (" + str_color + ") ";
				}
				
			}
			
					
			sql += " and B.saleprice between ? and ? "
					+ " and A.fk_pname like ? " ;
			
			
			
			pstmt = conn.prepareStatement(sql);
			
			if (!brand.isBlank()) {
				pstmt.setString(1, "%" + category + "%");
				pstmt.setString(2, brand);
				pstmt.setInt(3, Integer.parseInt(min_price));
				pstmt.setInt(4, Integer.parseInt(max_price));
				pstmt.setString(5, "%" + keyword + "%");

			} else {
				pstmt.setString(1, "%" + category + "%");
				pstmt.setInt(2, Integer.parseInt(min_price));
				pstmt.setInt(3, Integer.parseInt(max_price));
				pstmt.setString(4, "%" + keyword + "%");
			}

			rs = pstmt.executeQuery();
			rs.next();
			
			totalProductPage = rs.getInt("cnt");
			//System.out.println("페이지수 : " + rs.getInt("cnt"));

			
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
			String brand = paraMap.get("brand");		// 브랜드 "아디다스,아식스,뉴발란스" 요런식으로 받아옴
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
			if (!brand.isBlank()) {
				//sql += " and B.brand = '" + brand + "' ";
				sql += " and B.brand = ? ";
			}
			if (!str_ssize.isBlank()) {
				sql += " and D.ssize in (" + str_ssize + ") ";
			}
			if (!str_color.isBlank()) {
				if(str_color.contains("OTHER")) {
					// 색상이 SILVER, BLACK, WHITE, BROWN, RED, BLUE, YELLOW, IVORY 인 걸 제외하고 갖고온다.
					// str_color 가 만약 'BLACK','WHITE','OTHER' 이런식이면, 검정 흰색인 상품 + 위조건 만족하는 상품들  을 갖고오는 거임.
					sql += " and ( "
							+ " D.color in (" + str_color + ") "
							+ " or D.color not in ('SILVER','BLACK','WHITE','BROWN','RED','BLUE','YELLOW','IVORY') "
							+ ") ";
				}
				else {
					sql += " and D.color in (" + str_color + ") ";
				}
			}
	
			sql 	+= " and B.saleprice between ? and ? "
					+ " and A.fk_pname like ? " 
					+ " ORDER BY A.pnum ASC "
					+ " OFFSET (to_number(?)-1)*12 ROW "
					+ " FETCH NEXT 12 ROW ONLY ";
			
			pstmt = conn.prepareStatement(sql);
			
			if (!brand.isBlank()) {
				pstmt.setString(1, "%" + category + "%");
				pstmt.setString(2, brand);
				pstmt.setInt(3, Integer.parseInt(min_price));
				pstmt.setInt(4, Integer.parseInt(max_price));
				pstmt.setString(5, "%" + keyword + "%");
				pstmt.setString(6, paraMap.get("currentShowPageNo"));

			} else {
				pstmt.setString(1, "%" + category + "%");
				pstmt.setInt(2, Integer.parseInt(min_price));
				pstmt.setInt(3, Integer.parseInt(max_price));
				pstmt.setString(4, "%" + keyword + "%");
				pstmt.setString(5, paraMap.get("currentShowPageNo"));
			}
			
			
			/*
			 * pstmt.setString(1, "%" + category + "%"); pstmt.setInt(2,
			 * Integer.parseInt(min_price)); pstmt.setInt(3, Integer.parseInt(max_price));
			 * pstmt.setString(4, "%" + keyword + "%"); pstmt.setInt(5,
			 * Integer.parseInt(paraMap.get("currentShowPageNo")));
			 */
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
	
	// 선택한 상품옵션의 재고를 가져온다
	@Override
	public int isStockEnough(Map<String, String> paraMap) throws SQLException {
		int stock = 0;
		
		try {
			
			conn = ds.getConnection();
			
			String sql = " select sqty from tbl_stock "
					+ " where fk_pname = ? "
					+ " and ssize = to_number(?) "
					+ " and color = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, paraMap.get("pname"));
			pstmt.setString(2, paraMap.get("ssize"));
			pstmt.setString(3, paraMap.get("color"));
			
			rs = pstmt.executeQuery();
			rs.next();
			
			stock = rs.getInt("sqty");
			
			
		} finally {
			close();
		}
		
		return stock;
	}


	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	// 해당 상품을 찜했는지 알아오기
	@Override
	public int isWishlisted(Map<String, String> paraMap) throws SQLException {
		int isJjim = 0;
		
		try {
			conn = ds.getConnection();
			
			String sql = " select count(*) as cnt from tbl_wishlist where fk_userid = ? and fk_pnum = to_number(?) ";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, paraMap.get("userid"));
			pstmt.setString(2, paraMap.get("pnum"));
			
			rs = pstmt.executeQuery();
			
			rs.next();
			isJjim = rs.getInt("cnt");
			
			
					
 			
		} finally {
			close();
		}
		
		return isJjim;
	}


	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	// 위시리스트 등록/해제
	@Override
	public int changeWishStatus(Map<String, String> paraMap) throws SQLException {
		int n = 0;
		
		try {
			conn = ds.getConnection();
			
			String sql = "";
			
			// 1이면 테이블에서 삭제
			if (paraMap.get("isJjim").equals("1")) {
				sql = " delete from tbl_wishlist where fk_userid = ? and fk_pnum = to_number(?) ";
			}
			// 0이면 테이블에 추가
			else {
				sql = " insert into tbl_wishlist(fk_userid, fk_pnum) values(?, to_number(?)) ";
			}
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, paraMap.get("userid"));
			pstmt.setString(2, paraMap.get("pnum"));
			
			n = pstmt.executeUpdate();
			
			
		} finally {
			close();
		}
		
		return n;
	}

	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	
	// 해당 옵션을 갖는 재고번호(snum) 조회하기
	@Override
	public List<StockDTO> getSnumByOptions(List<StockDTO> optionList) throws SQLException {
		//List<Integer> snumList = new ArrayList<>();
		
		try {
			conn = ds.getConnection();
			
			String sql = " SELECT C.snum "
					+ " FROM tbl_product A "
					+ " JOIN tbl_catalogue B ON A.fk_pname = B.pname "
					+ " JOIN tbl_stock C ON B.pname = C.fk_pname "
					+ " where C.ssize = ? and C.color = ? and A.pnum = to_number(?) ";
			
			pstmt = conn.prepareStatement(sql);
			
			for(int i=0; i<optionList.size(); i++) {
				pstmt.setInt(1, optionList.get(i).getSsize());
				pstmt.setString(2, optionList.get(i).getColor());
				pstmt.setString(3, optionList.get(i).getFk_pname());
				
				rs = pstmt.executeQuery();
				rs.next();
				
				optionList.get(i).setSnum(rs.getInt("snum"));
				
			}
			
			
		} finally {
			close();
		}
		
		return optionList;
	}


	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@SuppressWarnings("unchecked")
	@Override
	public int insertCartList(Map<String, Object> paraMap) throws SQLException {
		int n = 0;
		
		String userid = (String) paraMap.get("userid");
		List<StockDTO> snumList = (List<StockDTO>) paraMap.get("snumList");
		
		try {
			conn = ds.getConnection();
			
			// 수량만 변경시
			String updateSql =
	                " UPDATE tbl_cart "
	              + " SET cqty = cqty + ? "
	              + " WHERE fk_userid = ? "
	              + " AND fk_snum = ? ";

			// 새로 삽입 시
	        String insertSql =
	                " INSERT INTO tbl_cart(fk_userid, fk_snum, cqty) "
	              + " VALUES(?, ?, ?) ";
			
			pstmt = conn.prepareStatement(updateSql);
			
			
			for (int i=0; i<snumList.size(); i++) {
				
				pstmt.setInt(1, snumList.get(i).getSqty());
				pstmt.setString(2, userid);
				pstmt.setInt(3, snumList.get(i).getSnum());
				
				// 업데이트 먼저 해보기 >> 변경된 행이 없으면 return 이 0이된다. >> 즉, 기존에 없던 옵션이라는 뜻
				int updatedRows = pstmt.executeUpdate();
				
				// 적용된 행이 없다면 새로 삽입하기
				if (updatedRows == 0) {
					pstmt.close();
					pstmt = conn.prepareStatement(insertSql);
					
					pstmt.setString(1, userid);
					pstmt.setInt(2, snumList.get(i).getSnum());
					pstmt.setInt(3, snumList.get(i).getSqty());
					
					n += pstmt.executeUpdate();
					
					pstmt.close();
		            pstmt = conn.prepareStatement(updateSql);
				}
				// 기존 행이 있다면
				else {
					n++;
				}
				
			}
			
		} finally {
			close();
		}
		
		return n;
	}


	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	// 해당 유저의 장바구니 목록 갖고오기
	@Override
	public List<Map<String, String>> getCartListByUserid(String userid) throws SQLException {
		List<Map<String, String>> myCartList = new ArrayList<>();
		
		try {
			conn = ds.getConnection();
			
			String sql = " select "
					+ "    A.pnum, B.pname, B.brand, A.pimage1, D.fk_snum, C.ssize, C.color, D.cqty, B.regprice, B.saleprice, A.deliveryfee "
					+ " from "
					+ "    tbl_product A join tbl_catalogue B "
					+ "    on A.fk_pname = B.pname "
					+ "    join tbl_stock C "
					+ "    on B.pname = C.fk_pname "
					+ "    join tbl_cart D "
					+ "    on D.fk_snum = C.snum "
					+ " where  "
					+ "    D.fk_userid = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				Map<String, String> map = new HashMap<>();
				map.put("pnum", String.valueOf(rs.getInt("pnum")));
				map.put("pname", rs.getString("pname"));
				map.put("brand", rs.getString("brand"));
				map.put("pimage1", rs.getString("pimage1"));
				map.put("snum", String.valueOf(rs.getInt("fk_snum")));
				map.put("ssize", String.valueOf(rs.getInt("ssize")));
				map.put("color", rs.getString("color"));
				map.put("cqty", String.valueOf(rs.getInt("cqty")));
				map.put("regprice", String.valueOf(rs.getInt("regprice")));
				map.put("saleprice", String.valueOf(rs.getInt("saleprice")));
				map.put("deliveryfee", String.valueOf(rs.getInt("deliveryfee")));
				
				myCartList.add(map);
			}
					
			
			
		} finally {
			close();
		}
		
		return myCartList;
	}


	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	// 특정 장바구니의 수량 변경하기
	@Override
	public int changeCartCqty(Map<String, String> paraMap) throws SQLException {
		int n = 0;
				
		try {
			conn = ds.getConnection();
			
			String sql = " update tbl_cart set cqty = to_number(?) "
					+ " where fk_userid = ? and fk_snum = to_number(?) ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, paraMap.get("cqty"));
			pstmt.setString(2, paraMap.get("userid"));
			pstmt.setString(3, paraMap.get("snum"));
			
			n = pstmt.executeUpdate();
			
		} finally {
			close();
		}
				
		return n;
	}


	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 변경하려는 장바구니 목록의 재고가 충분한지 검사한다.
	@Override
	public boolean isStockEnoughForCart(Map<String, String> paraMap) throws SQLException {
		boolean bool = false;
		
		try {
			conn = ds.getConnection();
			
			String sql = " select * from tbl_stock"
					+ " where snum = to_number(?) "
					+ " and sqty >= to_number(?) ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, paraMap.get("snum"));
			pstmt.setString(2, paraMap.get("cqty"));
			
			rs = pstmt.executeQuery();
			
			bool = rs.next();
			
		} finally {
			close();
		}
		
		return bool;
	}

	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 특정 상품을 장바구니에서 삭제하기
	@Override
	public int deleteFromCartList(Map<String, String> paraMap) throws SQLException {
		int n  = 0;
		
		try {
			conn = ds.getConnection();
			
			String sql = " delete from tbl_cart"
					+ " where fk_userid = ? and fk_snum = to_number(?) ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, paraMap.get("userid"));
			pstmt.setString(2, paraMap.get("snum"));
			
			n = pstmt.executeUpdate();
			
			
		} finally {
			close();
		}
		
		return n;
	}


//	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	// 상품의 QnA목록 총 페이지수 가져오기
	@Override
	public int getTotalQnAPage(String pnum) throws SQLException {
		int totalQnAPage = 0;
		
		try {
			conn = ds.getConnection();
			
			String sql = " SELECT CEIL(COUNT(*)/10) AS PAGE "
					+ " FROM tbl_qna "
					+ " where fk_pnum = to_number(?) ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, pnum);
			
			rs = pstmt.executeQuery();
			rs.next();
			
			totalQnAPage = rs.getInt("page");
			
		} finally {
			close();
		}
		
		
		return totalQnAPage;
	}

	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 특정 페이지번호에 해당하는 QnA 목록을 조회해온다.
	@Override
	public List<QnADTO> getQnAList(Map<String, String> paraMap) throws SQLException {
		List<QnADTO> qnaList = new ArrayList<>();
		
		try {
			conn = ds.getConnection();
			
			String sql = " select qnanum, fk_pnum, fk_userid, to_char(qwritedate, 'yyyy.mm.dd') as qwritedate, qwritedate as realdate, qtitle, qcontents, islock, qanswer "
					+ " from tbl_qna "
					+ " where fk_pnum = to_number(?) "
					+ " ORDER BY realdate desc, qnanum desc  "
					+ "	OFFSET (to_number(?)-1)*10 ROW "
					+ "	FETCH NEXT 10 ROW ONLY ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, paraMap.get("pnum"));
			pstmt.setString(2, paraMap.get("currentShowPageNo_QnA"));
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				QnADTO dto = new QnADTO();
				dto.setQnanum(rs.getInt("qnanum"));
				dto.setFk_pnum(rs.getInt("fk_pnum"));
				dto.setFk_userid(rs.getString("fk_userid"));
				dto.setQwritedate(rs.getString("qwritedate"));
				dto.setQtitle(rs.getString("qtitle"));
				dto.setQcontents(rs.getString("qcontents"));
				dto.setIslock(rs.getString("islock"));
				dto.setQanswer(rs.getString("qanswer"));
				qnaList.add(dto);
			}
			
		} finally {
			close();
		}
		
		return qnaList;
	}


	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 특정 상품에 대한 QnA 등록하기
	@Override
	public int addQnA(Map<String, String> paraMap) throws SQLException {
		int n = 0;
		
		try {
			conn = ds.getConnection();
			
			String sql = " INSERT INTO TBL_QNA(QNANUM, FK_PNUM, FK_USERID, QWRITEDATE, QTITLE, QCONTENTS, ISLOCK, QANSWER) "
						+ " VALUES(seq_qnanum.NEXTVAL, TO_NUMBER(?), ?, SYSDATE, ?, ?, ?, '') ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, paraMap.get("fk_pnum"));
			pstmt.setString(2, paraMap.get("fk_userid"));
			pstmt.setString(3, paraMap.get("qtitle"));
			pstmt.setString(4, paraMap.get("qcontents"));
			pstmt.setString(5, paraMap.get("islock"));
			
			n = pstmt.executeUpdate();
			
		} finally {
			close();
		}
		
		return n;
	}

	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 상품의 QnA목록 총 페이지수 가져오기
	@Override
	public int getTotalReviewPage(String pnum) throws SQLException {
		int totalReviewPage = 0;
		
		try {
			conn = ds.getConnection();
			
			String sql = " select ceil(count(*)/5) as page "
					+ " from tbl_product A join tbl_catalogue B "
					+ " on A.fk_pname = B.pname "
					+ " join tbl_stock C "
					+ " on B.pname = C.fk_pname "
					+ " join tbl_orderdetail D "
					+ " on C.snum = D.fk_snum "
					+ " join tbl_review E "
					+ " on D.odnum = E.fk_odnum "
					+ " where A.pnum = to_number(?) ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, pnum);
			
			rs = pstmt.executeQuery();
			rs.next();
			
			totalReviewPage = rs.getInt("page");
			
		} finally {
			close();
		}
		
		
		return totalReviewPage;
	}

	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 특정 페이지번호에 해당하는 리뷰 목록을 조회해온다.
	@Override
	public List<ReviewDTO> getReviewList(Map<String, String> paraMap) throws SQLException {
		List<ReviewDTO> reviewList = new ArrayList<>();
		
		try {
			conn = ds.getConnection();
			
			String sql = " select F.fk_userid, E.fk_odnum, E.rating, E.rcontents, "
					+ " TO_CHAR(E.rwritedate, 'yyyy.mm.dd hh24:mm:ss') as rwritedate, E.rimage, C.snum, C.ssize, C.color "
					+ " from tbl_product A join tbl_catalogue B "
					+ " on A.fk_pname = B.pname "
					+ " join tbl_stock C "
					+ " on B.pname = C.fk_pname "
					+ " join tbl_orderdetail D "
					+ " on C.snum = D.fk_snum "
					+ " join tbl_review E "
					+ " on D.odnum = E.fk_odnum "
					+ " join tbl_order F "
					+ " on D.fk_onum = F.onum "
					+ " where A.pnum = to_number(?)"
					+ " order by E.rwritedate asc, E.fk_odnum asc "
					+ "	OFFSET (to_number(?)-1)*5 ROW "
					+ "	FETCH NEXT 5 ROW ONLY ";
					
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, paraMap.get("pnum"));
			pstmt.setString(2, paraMap.get("currentShowPageNo_review"));
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				ReviewDTO rdto = new ReviewDTO();
				rdto.setFk_userid(rs.getString("fk_userid"));
				rdto.setFk_odnum(rs.getInt("fk_odnum"));
				rdto.setRating(rs.getInt("rating"));
				rdto.setRcontents(rs.getString("rcontents"));
				rdto.setRwritedate(rs.getString("rwritedate"));
				rdto.setRimage(rs.getString("rimage"));
				
				StockDTO sdto = new StockDTO();
				sdto.setSnum(rs.getInt("snum"));
				sdto.setSsize(rs.getInt("ssize"));
				sdto.setColor(rs.getString("color"));
				rdto.setStockDTO(sdto);
				
				reviewList.add(rdto);
			}
			
			
			
		} finally {
			close();
		}
		
		
		return reviewList;
	}

	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 구매하려는 제품 옵션의 재고가 충분한지 검사한다.
	@Override
	public String checkStockEnough(Map<String, String> paraMap) throws SQLException {
		String pname = "";
		
		try {
			conn = ds.getConnection();
			
			String sql = " select fk_pname "
					+ " from tbl_stock "
					+ " where snum = to_number(?) "
					+ " and sqty < to_number(?) ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, paraMap.get("snum"));
			pstmt.setString(2, paraMap.get("cqty"));
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				pname = rs.getString("fk_pname");
			}
			
			
		} finally {
			close();
		}
		
		return pname;
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 각종 DB에 구매정보 반영하기
	@SuppressWarnings("unchecked")
	@Override
	public int updateOrderList(Map<String, Object> paraMap) throws SQLException {

		int n = 0;
		int n1=0, n2=0, n3=0, n4=0;
		String sql = "";
		
		List<Map<String, String>> purchaseList = (List<Map<String, String>>) paraMap.get("purchaseList");
		String userid = (String) paraMap.get("userid");
		
		try {
			conn = ds.getConnection();
			conn.setAutoCommit(false);	// 수동커밋
			
			// onum 에 넣을값 채번하기
			sql = " SELECT seq_onum.NEXTVAL AS onum FROM dual ";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			rs.next();
			int onum = rs.getInt("onum");
			//System.out.println("채번한 onum :"+onum);
					
			
			// =========== 1. tbl_order에 삽입 =========== //
			sql = " INSERT INTO tbl_order(onum, fk_userid, orderstatus, orderdate, arrivaldate) "
					+ " VALUES(?, ?, default, default, sysdate + 2) ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, onum);
			pstmt.setString(2, userid);
			
			n1 = pstmt.executeUpdate();
			//System.out.println("n1 결과 : " + n1);
			
			
			if (n1 == 1) {
				// =========== 2. tbl_orderdetail에 각 snum으로 삽입 =========== //
				sql = " INSERT INTO tbl_orderdetail(odnum, fk_onum, fk_snum, oqty, odrprice)"
						+ " values(seq_odnum.NEXTVAL, ?, ?, ?, ?) ";
				pstmt = conn.prepareStatement(sql);
				
				for (Map<String, String> map : purchaseList) {
					pstmt.setInt(1, onum);
					pstmt.setInt(2, Integer.parseInt(map.get("snum")));
					pstmt.setInt(3, Integer.parseInt(map.get("cqty")));
					pstmt.setInt(4, Integer.parseInt(map.get("cqty")) * Integer.parseInt(map.get("saleprice")));
					
					n2 += pstmt.executeUpdate();
				}
				
			}
			
			//System.out.println("n2 결과 : " + n2);
			
			
			if (n2 == purchaseList.size()) {
				// =========== 3. tbl_stock에 각 cqty 만큼 수량 감소 =========== //
				sql = " UPDATE tbl_stock SET sqty = sqty - ? "
						+ " WHERE snum = ? ";
				pstmt = conn.prepareStatement(sql);
				
				for (Map<String, String> map : purchaseList) {
					pstmt.setInt(1, Integer.parseInt(map.get("cqty")));
					pstmt.setInt(2, Integer.parseInt(map.get("snum")));
					
					n3 += pstmt.executeUpdate();
				}
				
				n2 = 1;
			}
			
			
			
			
			if (n3 == purchaseList.size()) {
				// =========== 4. tbl_cart 에서 해당목록 삭제 =========== //
				
				// 장바구니에서 선택해 구매한 경우에만 삭제한다.
				// 바로구매는 삭제할거 없으니까 패스
				// 삭제하기 전, 장바구니에 해당하는 목록이 있는지 검사한다.
				// 검사해서 없으면 "바로구매" 로 간주한다.
				sql = " DELETE FROM tbl_cart "
						+ " WHERE fk_userid = ? "
						+ " AND fk_snum = ? ";
				
				for (Map<String, String> map : purchaseList) {
					
					if(map.get("purtype").equals("바로구매")) {
						n4++;
					}
					else {
						pstmt = conn.prepareStatement(sql);
						pstmt.setString(1, userid);
						pstmt.setInt(2, Integer.parseInt(map.get("snum")));
						
						n4 += pstmt.executeUpdate();
					}

				}	
				
				if(n4 == purchaseList.size()) {
					n4 = 1;
				}
				else {
					n4 = 0;
				}
				
				
				
				
				n3 = 1;
			}
			
			//System.out.println("n3 결과 : " + n3);
			//System.out.println("최종 n4 결과 : " + n4);
			
			// 모두 정상 실행되었다면 
			if(n1*n2*n3*n4 == 1) {
				conn.commit();
				conn.setAutoCommit(true);
				n = 1;
			}
			
			
			
		} 
		
		// **** SQL 장애 발생시 rollback 하기(rollback) ****	
		catch (SQLException e) {
			e.printStackTrace();
			conn.rollback();
			conn.setAutoCommit(true);
			n = 0;
		}
		
		finally {
			close();
		}
		
		return n;
	}


	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	
	
	// 가장 최근의 주문 정보를 갖고옴
	@Override
	public Map<String, String> getRecentOrder(String userid) throws SQLException {
		Map<String, String> orderMap = new HashMap<>();
		
		try {
			conn = ds.getConnection();
			
			String sql = " SELECT "
					+ "		  O.onum, "
					+ "       TO_CHAR(O.orderdate, 'yyyy.mm.dd hh24:mi:ss') AS orderdate, "
					+ "       SUM(OD.odrprice) AS totalprice "
					+ " FROM tbl_order O "
					+ " JOIN tbl_orderdetail OD "
					+ "    ON O.onum = OD.fk_onum "
					+ " WHERE O.fk_userid = ? "
					+ "  AND O.onum = ( "
					+ "      SELECT MAX(onum) "
					+ "      FROM tbl_order "
					+ "      WHERE fk_userid = ? "
					+ "  ) "
					+ " GROUP BY O.onum, O.orderdate ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			pstmt.setString(2, userid);
			
			rs = pstmt.executeQuery();
			
			rs.next();
			
			orderMap.put("onum", String.valueOf(rs.getInt("onum")));
			orderMap.put("orderdate", rs.getString("orderdate"));
			orderMap.put("totalprice", String.valueOf(rs.getInt("totalprice")));
			
			
		} finally {
			close();
		}
		
		return orderMap;
	}


	
	// snum 갖고오기
	@Override
	public String getSnumBySpec(Map<String, String> paraMap) throws SQLException {
		String snum = "";
		
		try {
			conn = ds.getConnection();
			
			String sql = " SELECT snum "
					+ " FROM tbl_stock "
					+ " WHERE fk_pname = ? "
					+ " AND ssize = TO_NUMBER(?) "
					+ " AND color = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, paraMap.get("fk_pname"));
			pstmt.setString(2, paraMap.get("ssize"));
			pstmt.setString(3, paraMap.get("color"));
			
			rs = pstmt.executeQuery();
			rs.next();
			
			snum = String.valueOf(rs.getInt("snum"));
					
		} finally {
			close();
		}
		
		return snum;
	}


	
	
	
	@Override
	public int getTotalReviewCnt(String pnum) throws SQLException {
		int totalReviewCnt = 0;
		
		try {
			conn = ds.getConnection();
			
			String sql = " select COUNT(*) AS cnt "
					+ " from tbl_product A join tbl_catalogue B "
					+ " on A.fk_pname = B.pname "
					+ " join tbl_stock C "
					+ " on B.pname = C.fk_pname "
					+ " join tbl_orderdetail D "
					+ " on C.snum = D.fk_snum "
					+ " join tbl_review E "
					+ " on D.odnum = E.fk_odnum "
					+ " join tbl_order F "
					+ " on D.fk_onum = F.onum "
					+ " where A.pnum = to_number(?) ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, pnum);

			rs = pstmt.executeQuery();
			rs.next();
			
			totalReviewCnt = rs.getInt("cnt");
			
		} finally {
			
		}
		
		return totalReviewCnt;
	}


	
	// 클릭한 QnA 항목의 내용을 들고온다.
	@Override
	public QnADTO getQnaDetail(String qnanum) throws SQLException {
		QnADTO qnaDetail = new QnADTO();
		
		try {
			conn = ds.getConnection();
			
			String sql = " SELECT fk_userid, islock, qcontents, qanswer "
					+ " FROM tbl_qna "
					+ " WHERE qnanum = TO_NUMBER(?) ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, qnanum);
			rs = pstmt.executeQuery();
			
			rs.next();
			qnaDetail.setFk_userid(rs.getString("fk_userid"));
			qnaDetail.setIslock(rs.getString("islock"));
			qnaDetail.setQcontents(rs.getString("qcontents"));
			qnaDetail.setQanswer(rs.getString("qanswer"));
					
			
			
		} finally {
			close();
		}
		
		return qnaDetail;
	}


	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 해당 QnA 를 삭제한다.
	@Override
	public int deleteQna(String qnanum) throws SQLException {
		int n = 0;
		
		try {
			conn = ds.getConnection();
			
			String sql = " DELETE FROM TBL_QNA "
					+ " WHERE QNANUM = TO_NUMBER(?) ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, qnanum);
			n = pstmt.executeUpdate();
			
		} finally {
			close();
		}
		
		return n;
	}


	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 해당 QnA 를 수정한다.
	@Override
	public int updateQna(Map<String, String> paraMap) throws SQLException {
		int n = 0;
		
		try {
			conn = ds.getConnection();
			String sql = " UPDATE tbl_qna SET "
					+ " qtitle = ?, qcontents = ?, islock = ?, qwritedate = sysdate "
					+ " WHERE qnanum = TO_NUMBER(?) ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, paraMap.get("qtitle"));
			pstmt.setString(2, paraMap.get("qcontents"));
			pstmt.setString(3, paraMap.get("islock"));
			pstmt.setString(4, paraMap.get("qnanum"));
			
			n = pstmt.executeUpdate();
			
		} finally {
			close();
		}
		
		return n;
	}


	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
