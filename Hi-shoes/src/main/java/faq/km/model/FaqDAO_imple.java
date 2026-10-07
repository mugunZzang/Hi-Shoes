package faq.km.model;


import java.io.UnsupportedEncodingException;
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

import faq.km.domain.FaqDTO;
import util.security.AES256;
import util.security.SecretMyKey;


public class FaqDAO_imple implements FaqDAO {


    private DataSource ds;

    private Connection conn;

    private PreparedStatement pstmt;

    private ResultSet rs;


    private AES256 aes;


    // =====================================================
    // 생성자
    // =====================================================

    public FaqDAO_imple() {

        try {

            Context initContext =
                    new InitialContext();


            Context envContext =
                    (Context)initContext.lookup(
                            "java:/comp/env"
                    );


            ds =
                    (DataSource)envContext.lookup(
                            "jdbc/myoracle"
                    );


            aes =
                    new AES256(
                            SecretMyKey.KEY
                    );


        }
        catch(NamingException e) {

            e.printStackTrace();

        }
        catch(UnsupportedEncodingException e) {

            e.printStackTrace();

        }

    }


    // =====================================================
    // 자원 반납
    // =====================================================

    private void close() {

        try {

            if(rs != null) {

                rs.close();
                rs = null;

            }


            if(pstmt != null) {

                pstmt.close();
                pstmt = null;

            }


            if(conn != null) {

                conn.close();
                conn = null;

            }

        }
        catch(SQLException e) {

            e.printStackTrace();

        }

    }


    // =====================================================
    // FAQ 목록 조회
    // =====================================================

    @Override
    public List<FaqDTO> select_faq_list(
            Map<String, String> paraMap)
            throws Exception {


        List<FaqDTO> faqList =
                new ArrayList<>();


        try {

            conn =
                    ds.getConnection();


            String sql =
                    " select fnum, fsubject, fcontents, fcategory "
                  + " from tbl_faq ";


            String searchType =
                    paraMap.get("searchType");


            // =================================================
            // 카테고리 검색
            // =================================================

            if(searchType != null
                    && !searchType.isBlank()) {

                sql +=
                    " where fcategory = ? ";

            }


            sql +=
                    " order by fnum desc "
                  + " offset (? - 1) * 10 rows "
                  + " fetch next 10 rows only ";


            pstmt =
                    conn.prepareStatement(sql);


            int currentShowPageNo =
                    Integer.parseInt(
                        paraMap.get("currentShowPageNo")
                    );


            int index = 1;


            // =================================================
            // 검색조건
            // =================================================

            if(searchType != null
                    && !searchType.isBlank()) {


                pstmt.setString(
                    index++,
                    searchType
                );

            }


            // =================================================
            // 페이징
            // =================================================

            pstmt.setInt(
                index,
                currentShowPageNo
            );


            rs =
                    pstmt.executeQuery();


            while(rs.next()) {


                FaqDTO fdto =
                        new FaqDTO();


                fdto.setFnum(
                    rs.getInt("fnum")
                );


                fdto.setFsubject(
                    rs.getString("fsubject")
                );


                fdto.setFcontents(
                    rs.getString("fcontents")
                );


                fdto.setFcategory(
                    rs.getString("fcategory")
                );


                faqList.add(fdto);

            }


        }
        finally {

            close();

        }


        return faqList;

    }


    // =====================================================
    // FAQ 전체 게시글 수
    // =====================================================

    @Override
    public int getTotalCountOrder(Map<String, String> paraMap){


        int totalCountOrder = 0;


        try {
				conn =ds.getConnection();

            String sql =
                    " select count(*) as CNT "
                  + " from tbl_faq ";


            String searchType =
                    paraMap.get("searchType");


            // =================================================
            // 카테고리 검색
            // =================================================

            if(searchType != null
                    && !searchType.isBlank()) {

                sql +=
                    " where fcategory = ? ";

            }


            pstmt =
                    conn.prepareStatement(sql);


            if(searchType != null
                    && !searchType.isBlank()) {


                pstmt.setString(
                    1,
                    searchType
                );

            }


            rs =
                    pstmt.executeQuery();


            if(rs.next()) {

                totalCountOrder =
                        rs.getInt("CNT");

            }


        } catch(Exception e) {
        	
        } finally {

            close();

        }


        return totalCountOrder;

    }


    // =====================================================
    // FAQ 등록
    // =====================================================

    @Override
    public int faqInsert(
            FaqDTO fdto)
            throws Exception {


        int n = 0;


        try {

            conn =
                    ds.getConnection();


            String sql =
                    " insert into tbl_faq "
                  + " (fnum, fsubject, fcontents, fcategory) "
                  + " values (SEQ_FNUM.nextval, ?, ?, ?) ";


            pstmt =
                    conn.prepareStatement(sql);


            // 제목
            pstmt.setString(
                1,
                fdto.getFsubject()
            );


            // 내용
            pstmt.setString(
                2,
                fdto.getFcontents()
            );


            // 카테고리
            pstmt.setString(
                3,
                fdto.getFcategory()
            );


            n =
                    pstmt.executeUpdate();

        }
        finally {

            close();

        }


        return n;

    }
    
    @Override
    public FaqDTO selectFaqOne(int fnum) throws Exception {

        FaqDTO fdto = null;

        try {
            conn = ds.getConnection();

            String sql = " select fnum, fsubject, fcontents, fcategory "
                       + " from tbl_faq "
                       + " where fnum = ? ";

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, fnum);

            rs = pstmt.executeQuery();

            if (rs.next()) {

                fdto = new FaqDTO();

                fdto.setFnum(rs.getInt("fnum"));
                fdto.setFsubject(rs.getString("fsubject"));
                fdto.setFcontents(rs.getString("fcontents"));
                fdto.setFcategory(rs.getString("fcategory"));
            }

        } finally {
            close();
        }

        return fdto;
    }
    
    @Override
    public int faqUpdate(FaqDTO fdto) throws Exception {

        int result = 0;

        try {
            conn = ds.getConnection();

            String sql = " update tbl_faq "
                       + " set fsubject = ?, "
                       + "     fcontents = ?, "
                       + "     fcategory = ? "
                       + " where fnum = ? ";

            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, fdto.getFsubject());
            pstmt.setString(2, fdto.getFcontents());
            pstmt.setString(3, fdto.getFcategory());
            pstmt.setInt(4, fdto.getFnum());

            result = pstmt.executeUpdate();

        } finally {
            close();
        }

        return result;
    }


	@Override
	public int faqDelete(String num) throws Exception {
		
		int n = 0;
		
		try {
			
			conn = ds.getConnection();
			
			System.out.println(num);
			
			String sql = " delete from tbl_faq"
					   + " where fnum = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, num);
			
			n = pstmt.executeUpdate();
			
		} finally {
			close();
		}
		
		return n;
	}// end of public int faqDelete(String num) throws Exception

}