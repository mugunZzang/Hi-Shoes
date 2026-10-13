package myshop.kth.domain;

public class AdminCatalogueDTO {
	

	

	    // 상품명
	    private String pname;

	    // 카테고리 번호
	    private int fk_catenum;

	    // 매입가
	    private int purprice;

	    // 정가
	    private int regprice;

	    // 판매가
	    private int saleprice;

	    // 브랜드
	    private String brand;


	    // 기본 생성자
	    public AdminCatalogueDTO() {
	    }


	    // 전체 생성자
	    public AdminCatalogueDTO(String pname, int fk_catenum, int purprice,
	                        int regprice, int saleprice, String brand) {
	        this.pname = pname;
	        this.fk_catenum = fk_catenum;
	        this.purprice = purprice;
	        this.regprice = regprice;
	        this.saleprice = saleprice;
	        this.brand = brand;
	    }


	    public String getPname() {
	        return pname;
	    }

	    public void setPname(String pname) {
	        this.pname = pname;
	    }


	    public int getFk_catenum() {
	        return fk_catenum;
	    }

	    public void setFk_catenum(int fk_catenum) {
	        this.fk_catenum = fk_catenum;
	    }


	    public int getPurprice() {
	        return purprice;
	    }

	    public void setPurprice(int purprice) {
	        this.purprice = purprice;
	    }


	    public int getRegprice() {
	        return regprice;
	    }

	    public void setRegprice(int regprice) {
	        this.regprice = regprice;
	    }


	    public int getSaleprice() {
	        return saleprice;
	    }

	    public void setSaleprice(int saleprice) {
	        this.saleprice = saleprice;
	    }


	    public String getBrand() {
	        return brand;
	    }

	    public void setBrand(String brand) {
	        this.brand = brand;
	    }
	}

