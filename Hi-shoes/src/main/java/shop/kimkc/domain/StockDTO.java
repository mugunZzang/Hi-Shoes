package shop.kimkc.domain;

public class StockDTO {
	private int snum;			// 제품번호
	private String fk_pname;	// 제품명
	private int ssize;			// 사이즈
	private String color;		// 색상
	private int sqty;			// 재고량
	
	private CatalogueDTO catalogueDTO;	// 제품명VO

	
	
	
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

	public CatalogueDTO getCatalogueDTO() {
		return catalogueDTO;
	}

	public void setCatalogueDTO(CatalogueDTO catalogueDTO) {
		this.catalogueDTO = catalogueDTO;
	}
	
}
