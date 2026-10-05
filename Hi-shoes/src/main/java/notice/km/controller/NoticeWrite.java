package notice.km.controller;

import java.io.File;
import java.util.UUID;

import common.controller.AbstractController;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import notice.km.domain.NoticeDTO;
import notice.km.model.NoticeDAO;
import notice.km.model.NoticeDAO_imple;


public class NoticeWrite extends AbstractController {


    private NoticeDAO ndao = new NoticeDAO_imple();


    @Override
    public void execute(HttpServletRequest request,
                        HttpServletResponse response) throws Exception {


        String method = request.getMethod();


        // =====================================================
        // GET
        // =====================================================

        if("GET".equalsIgnoreCase(method)) {


            super.setRedirect(false);

            super.setViewPage(
                "/WEB-INF/admin/callcenter/noticeWrite.jsp"
            );

            return;
        }


        // =====================================================
        // POST
        // =====================================================


        // 제목
        String nsubject = request.getParameter("nsubject");


        // 내용
        String ncontents = request.getParameter("ncontents");


        // 공백 제거
        if(nsubject != null) {
            nsubject = nsubject.trim();
        }

        if(ncontents != null) {
            ncontents = ncontents.trim();
        }


        // 제목 / 내용 검사
        if(nsubject == null || nsubject.isEmpty()
            || ncontents == null || ncontents.isEmpty()) {


            super.setRedirect(false);

            super.setViewPage(
                "/WEB-INF/admin/callcenter/noticeWrite.jsp"
            );

            return;
        }


        // =====================================================
        // 이미지 업로드
        // =====================================================

        String imageName = null;


        Part imagePart = request.getPart("image");


        if(imagePart != null
                && imagePart.getSize() > 0) {


            String originalFileName =
                imagePart.getSubmittedFileName();


            if(originalFileName != null
                    && !originalFileName.isBlank()) {


                // 확장자 가져오기
                String extension = "";

                int dotIndex =
                    originalFileName.lastIndexOf(".");


                if(dotIndex != -1) {

                    extension =
                        originalFileName.substring(dotIndex);
                }


                // 중복되지 않는 파일 이름 생성
                imageName =
                    UUID.randomUUID().toString()
                    + extension;


                // 저장 경로
                ServletContext svlCtx =
                    request.getServletContext();


                String uploadFileDir =
                    svlCtx.getRealPath("/images");


                File uploadDir =
                    new File(uploadFileDir);


                // images 폴더가 없으면 생성
                if(!uploadDir.exists()) {

                    uploadDir.mkdirs();
                }


                // 파일 저장
                imagePart.write(
                    uploadFileDir
                    + File.separator
                    + imageName
                );
            }
        }


        // =====================================================
        // DTO 생성
        // =====================================================

        NoticeDTO ndto = new NoticeDTO();


        ndto.setNsubject(nsubject);

        ndto.setNcontents(ncontents);

        ndto.setNimage(imageName);


        // =====================================================
        // DB INSERT
        // =====================================================

        int n = ndao.noticeInsert(ndto);


        // =====================================================
        // 성공
        // =====================================================

        if(n == 1) {

            super.setRedirect(false);

            super.setViewPage(
                "/WEB-INF/admin/callcenter/callcenter.jsp?tab=notice"
            );

        }

        else {


            // 실패
            super.setRedirect(false);

            super.setViewPage(
                "/WEB-INF/admin/callcenter/noticeWrite.jsp"
            );
        }

    }

}