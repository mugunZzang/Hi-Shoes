package myshop.kth.domain;

public class AdminStockDTO {

	

	    private int snum;
	    private String fk_pname;
	    private int ssize;
	    private String color;
	    private int sqty;


	    // 기본 생성자
	    public AdminStockDTO() {
	    }


	    // 전체 생성자
	    public AdminStockDTO(int snum, String fk_pname, int ssize,
	                    String color, int sqty) {
	        this.snum = snum;
	        this.fk_pname = fk_pname;
	        this.ssize = ssize;
	        this.color = color;
	        this.sqty = sqty;
	    }


	    public int getSnum() {
	        return snum;
	    }

	    public void setSnum(int snum) {
	        this.snum = snum;
	    }


	    public String getFk_pname() {
	        return fk_pname;
	    }

	    public void setFk_pname(String fk_pname) {
	        this.fk_pname = fk_pname;
	    }


	    public int getSsize() {
	        return ssize;
	    }

	    public void setSsize(int ssize) {
	        this.ssize = ssize;
	    }


	    public String getColor() {
	        return color;
	    }

	    public void setColor(String color) {
	        this.color = color;
	    }


	    public int getSqty() {
	        return sqty;
	    }

	    public void setSqty(int sqty) {
	        this.sqty = sqty;
	    }
	}

