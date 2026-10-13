package myshop.kth.domain;

public class AdminProductDTO {



	    // 상품번호
	    private int pnum;

	    // 상품명
	    private String fk_pname;

	    // 상품 이미지 1
	    private String pimage1;

	    // 상품 이미지 2
	    private String pimage2;

	    // 상품 설명
	    private String pcontent;

	    // 배송비
	    private int deliveryfee;

	    // 품질보증서 시스템 파일명
	    private String warranty_systemFileName;

	    // 품질보증서 원본 파일명
	    private String warranty_orginFileName;


	    // 기본 생성자
	    public AdminProductDTO() {
	    }


	    // 전체 생성자
	    public AdminProductDTO(int pnum, String fk_pname, String pimage1, String pimage2,
	                      String pcontent, int deliveryfee,
	                      String warranty_systemFileName, String warranty_orginFileName) {
	        this.pnum = pnum;
	        this.fk_pname = fk_pname;
	        this.pimage1 = pimage1;
	        this.pimage2 = pimage2;
	        this.pcontent = pcontent;
	        this.deliveryfee = deliveryfee;
	        this.warranty_systemFileName = warranty_systemFileName;
	        this.warranty_orginFileName = warranty_orginFileName;
	    }


	    public int getPnum() {
	        return pnum;
	    }

	    public void setPnum(int pnum) {
	        this.pnum = pnum;
	    }


	    public String getFk_pname() {
	        return fk_pname;
	    }

	    public void setFk_pname(String fk_pname) {
	        this.fk_pname = fk_pname;
	    }


	    public String getPimage1() {
	        return pimage1;
	    }

	    public void setPimage1(String pimage1) {
	        this.pimage1 = pimage1;
	    }


	    public String getPimage2() {
	        return pimage2;
	    }

	    public void setPimage2(String pimage2) {
	        this.pimage2 = pimage2;
	    }


	    public String getPcontent() {
	        return pcontent;
	    }

	    public void setPcontent(String pcontent) {
	        this.pcontent = pcontent;
	    }


	    public int getDeliveryfee() {
	        return deliveryfee;
	    }

	    public void setDeliveryfee(int deliveryfee) {
	        this.deliveryfee = deliveryfee;
	    }


	    public String getWarranty_systemFileName() {
	        return warranty_systemFileName;
	    }

	    public void setWarranty_systemFileName(String warranty_systemFileName) {
	        this.warranty_systemFileName = warranty_systemFileName;
	    }


	    public String getWarranty_orginFileName() {
	        return warranty_orginFileName;
	    }

	    public void setWarranty_orginFileName(String warranty_orginFileName) {
	        this.warranty_orginFileName = warranty_orginFileName;
	    }
	}

