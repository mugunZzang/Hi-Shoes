package notice.km.model;


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

import notice.km.domain.NoticeDTO;


public class NoticeDAO_imple implements NoticeDAO {


    private DataSource ds;

    private Connection conn;

    private PreparedStatement pstmt;

    private ResultSet rs;


    // =====================================================
    // 생성자
    // =====================================================

    public NoticeDAO_imple() {

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


        }
        catch(NamingException e) {

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
    // 공지사항 목록
    // =====================================================

    @Override
    public List<NoticeDTO> select_notice_list(
            Map<String, String> paraMap) throws Exception {


        List<NoticeDTO> noticeList =
            new ArrayList<>();


        try {


            conn = ds.getConnection();


            String sql =
                " select nnum, nsubject, ncontents, "
              + "        nwritedate, nimage "
              + " from tbl_notice ";


            String colname =
                paraMap.get("searchType");


            String searchWord =
                paraMap.get("searchWord");


            boolean isSearch =
                colname != null
                && searchWord != null
                && !colname.isEmpty()
                && !searchWord.trim().isEmpty();


            // =================================================
            // 검색
            // =================================================

            if(isSearch) {


                if("subject".equals(colname)) {

                    sql +=
                        " where nsubject like '%' || ? || '%' ";
                }


                else if("content".equals(colname)) {

                    sql +=
                        " where ncontents like '%' || ? || '%' ";
                }

            }


            // =================================================
            // 정렬 + 페이징
            // =================================================

            sql +=
                " order by nnum desc "
              + " offset (? - 1) * 10 rows "
              + " fetch next 10 rows only ";


            pstmt =
                conn.prepareStatement(sql);


            int parameterIndex = 1;


            // 검색어
            if(isSearch) {

                pstmt.setString(
                    parameterIndex++,
                    searchWord.trim()
                );
            }


            // 현재 페이지
            int currentShowPageNo =
                Integer.parseInt(
                    paraMap.get("currentShowPageNo")
                );


            pstmt.setInt(
                parameterIndex,
                currentShowPageNo
            );


            rs =
                pstmt.executeQuery();


            // =================================================
            // 결과
            // =================================================

            while(rs.next()) {


                NoticeDTO ndto =
                    new NoticeDTO();


                ndto.setNnum(
                    rs.getInt("nnum")
                );


                ndto.setNsubject(
                    rs.getString("nsubject")
                );


                ndto.setNcontents(
                    rs.getString("ncontents")
                );


                ndto.setNwritedate(
                    rs.getString("nwritedate")
                );


                ndto.setNimage(
                    rs.getString("nimage")
                );


                noticeList.add(ndto);

            }

        }
        finally {

            close();
        }


        return noticeList;
    }


    // =====================================================
    // 전체 게시글 수
    // =====================================================

    @Override
    public int getTotalCountOrder(Map<String, String> paraMap) throws Exception {


        int totalCountOrder = 0;


        try {


            conn =
                ds.getConnection();


            String sql =
                " select count(*) as CNT "
              + " from tbl_notice ";


            String colname =
                paraMap.get("searchType");


            String searchWord =
                paraMap.get("searchWord");


            boolean isSearch =
                colname != null
                && searchWord != null
                && !colname.isEmpty()
                && !searchWord.trim().isEmpty();


            // 검색 조건
            if(isSearch) {


                if("subject".equals(colname)) {

                    sql +=
                        " where nsubject like '%' || ? || '%' ";
                }


                else if("content".equals(colname)) {

                    sql +=
                        " where ncontents like '%' || ? || '%' ";
                }

            }


            pstmt =
                conn.prepareStatement(sql);


            if(isSearch) {

                pstmt.setString(
                    1,
                    searchWord.trim()
                );
            }


            rs =
                pstmt.executeQuery();


            if(rs.next()) {

                totalCountOrder =
                    rs.getInt("CNT");
            }

        }
        finally {

            close();
        }


        return totalCountOrder;
    }


    // =====================================================
    // 공지사항 등록
    // =====================================================

    @Override
    public int noticeInsert(
            NoticeDTO ndto) throws Exception {


        int n = 0;


        try {


            conn =
                ds.getConnection();


            String sql =
                " insert into tbl_notice "
              + " (nnum, nsubject, ncontents, nimage) "
              + " values (SEQ_NNUM.nextval, ?, ?, ?) ";


            pstmt =
                conn.prepareStatement(sql);


            // 제목
            pstmt.setString(
                1,
                ndto.getNsubject()
            );


            // ★ 내용
            pstmt.setString(
                2,
                ndto.getNcontents()
            );


            // 이미지
            pstmt.setString(
                3,
                ndto.getNimage()
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
    public NoticeDTO selectNoticeOne(Map<String, String> paraMap) throws Exception {

        NoticeDTO ndto = null;

        try {
            conn = ds.getConnection();

            String sql = " select nnum, nsubject, ncontents "
                       + " from tbl_notice "
                       + " where nnum = ? ";

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, Integer.parseInt(paraMap.get("nnum")));

            rs = pstmt.executeQuery();

            if (rs.next()) {

                ndto = new NoticeDTO();

                ndto.setNnum(rs.getInt("nnum"));
                ndto.setNsubject(rs.getString("nsubject"));
                ndto.setNcontents(rs.getString("ncontents"));
            }

        } finally {
            close();
        }

        return ndto;
    }
    
    @Override
    public int noticeUpdate(NoticeDTO ndto) throws Exception {

        int result = 0;

        try {
            conn = ds.getConnection();

            String sql = " update tbl_notice "
                       + " set nsubject = ?, "
                       + "     ncontents = ? "
                       + " where nnum = ? ";

            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, ndto.getNsubject());
            pstmt.setString(2, ndto.getNcontents());
            pstmt.setInt(3, ndto.getNnum());

            result = pstmt.executeUpdate();

        } finally {
            close();
        }

        return result;
    }

}