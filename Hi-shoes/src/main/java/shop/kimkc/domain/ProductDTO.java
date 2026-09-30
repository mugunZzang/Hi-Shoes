package shop.kimkc.domain;

public class ProductDTO {
	private int pnum;						// 판매번호
	private String fk_pname;				// 제품명
	private String piamge1;					// 제품이미지1
	private String pimage2;					// 제품이미지2
	private String pcontent;				// 제품 설명
	private int deliveryfee;				// 배송비
	private String warranty_systemFileName;	// 파일서버용 제품보증서
	private String warranty_originFileName;	// 클라이언트용 제품보증서
	
	private CatalogueDTO catalogueDTO;		// 카탈로그VO
	
	
	
	
	
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

	public String getPiamge1() {
		return piamge1;
	}

	public void setPiamge1(String piamge1) {
		this.piamge1 = piamge1;
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

	public String getWarranty_originFileName() {
		return warranty_originFileName;
	}

	public void setWarranty_originFileName(String warranty_originFileName) {
		this.warranty_originFileName = warranty_originFileName;
	}

	public CatalogueDTO getCatalogueDTO() {
		return catalogueDTO;
	}

	public void setCatalogueDTO(CatalogueDTO catalogueDTO) {
		this.catalogueDTO = catalogueDTO;
	}


}
