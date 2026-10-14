package product.km.domain;

public class ProductDTO {
	
	private int pnum;
    private String fk_pname;
    private String pimage1;
    private String pimage2;
    private String pcontent;
    private int deliveryfee;
    private String warranty_systemfilename;
    private String warranty_originfilename;
	
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
	public String getWarranty_systemfilename() {
		return warranty_systemfilename;
	}
	public void setWarranty_systemfilename(String warranty_systemfilename) {
		this.warranty_systemfilename = warranty_systemfilename;
	}
	public String getWarranty_originfilename() {
		return warranty_originfilename;
	}
	public void setWarranty_originfilename(String warranty_originfilename) {
		this.warranty_originfilename = warranty_originfilename;
	}
}
