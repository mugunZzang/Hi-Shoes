package admin.catalogue.jungyj.domain;

public class CatalogueDTO {

    private String pName;          // 제품명        NVARCHAR2(100),
    private int fk_catenum;        // 카테고리 코드   NUMBER,
    private int purPrice;          // 구매가        NUMBER NOT NULL,
    private int regPrice;          // 정가         NUMBER NOT NULL,
    private int salePrice;         // 판매가        NUMBER NOT NULL,
    private String brand;          // 브랜드명       NVARCHAR2(20)
    
    
    
    
	public String getpName() {
		return pName;
	}
	public void setpName(String pName) {
		this.pName = pName;
	}
	public int getFk_catenum() {
		return fk_catenum;
	}
	public void setFk_catenum(int fk_catenum) {
		this.fk_catenum = fk_catenum;
	}
	public int getPurPrice() {
		return purPrice;
	}
	public void setPurPrice(int purPrice) {
		this.purPrice = purPrice;
	}
	public int getRegPrice() {
		return regPrice;
	}
	public void setRegPrice(int regPrice) {
		this.regPrice = regPrice;
	}
	public int getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(int salePrice) {
		this.salePrice = salePrice;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
    
    
    
}
