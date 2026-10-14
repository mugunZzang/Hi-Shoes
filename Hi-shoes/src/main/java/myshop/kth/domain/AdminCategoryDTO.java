package myshop.kth.domain;

public class AdminCategoryDTO {

	private int    catenum;   // 카테고리 대분류 번호
	
	private String catename;  // 카테고리명
	
	public AdminCategoryDTO() { }
	
	public AdminCategoryDTO(int catenum,  String catename) {
		this.catenum = catenum;
		
		this.catename = catename;
	}

	public int getCatenum() {
		return catenum;
	}

	public void setCatenum(int catenum) {
		this.catenum = catenum;
	}

	

	public String getCatename() {
		return catename;
	}

	public void setCatename(String catename) {
		this.catename = catename;
	}
	
}
