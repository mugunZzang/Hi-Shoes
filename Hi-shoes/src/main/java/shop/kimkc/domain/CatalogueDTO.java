package shop.kimkc.domain;

public class CatalogueDTO {
	private String pname;		// 제품명
	private int fk_catenum;		// 카테고리코드
	private int purprice;		// 구매가(재고로 들여올 때 지불값)
	private int regprice;		// 정가(구매가 + a)
	private int saleprice;		// 판매가(할인율을 적용한 실제 판매가)
	private String brand;		// 브랜드
	
	private CategoryDTO categoryDTO;	// 카테고리VO

	
	
	
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

	public CategoryDTO getCategoryDTO() {
		return categoryDTO;
	}

	public void setCategoryDTO(CategoryDTO categoryDTO) {
		this.categoryDTO = categoryDTO;
	}
	
	
	
}
