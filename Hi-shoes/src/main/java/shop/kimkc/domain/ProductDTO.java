package shop.kimkc.domain;

import java.util.List;

public class ProductDTO {
	private int pnum;						// 판매번호
	private String fk_pname;				// 제품명
	private String pimage1;					// 제품이미지1
	private String pimage2;					// 제품이미지2
	private String pcontent;				// 제품 설명
	private int deliveryfee;				// 배송비
	private String warranty_systemFileName;	// 파일서버용 제품보증서
	private String warranty_originFileName;	// 클라이언트용 제품보증서
	
	private CatalogueDTO catalogueDTO;		// 카탈로그VO
	
	private List<StockDTO> stockDTOList;	// 재고VO 리스트
	private List<Product_ImageDTO> prodImageDTOList;	// 추가이미지 리스트
	
	
	
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

	public List<StockDTO> getStockDTOList() {
		return stockDTOList;
	}

	public void setStockDTOList(List<StockDTO> stockDTOList) {
		this.stockDTOList = stockDTOList;
	}

	public List<Product_ImageDTO> getProdImageDTOList() {
		return prodImageDTOList;
	}

	public void setProdImageDTOList(List<Product_ImageDTO> prodImageDTOList) {
		this.prodImageDTOList = prodImageDTOList;
	}


}
