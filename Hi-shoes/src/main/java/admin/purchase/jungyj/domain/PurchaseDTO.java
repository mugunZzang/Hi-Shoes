package admin.purchase.jungyj.domain;

public class PurchaseDTO {

	private int purnum;                     //   발주번호
	private String fk_supname;              //   공급업체명 NVARCHAR2(100)
	private String purtime;                 //   발주시간  DATE default sysdate 
	private String purdeadline;             //   납품기한 DATE not null 
	private String instock;                 //   입고여부  NVARCHAR2(10) default '미입고' not null   
	
	
	public int getPurnum() {
		return purnum;
	}
	public void setPurnum(int purnum) {
		this.purnum = purnum;
	}
	public String getFk_supname() {
		return fk_supname;
	}
	public void setFk_supname(String fk_supname) {
		this.fk_supname = fk_supname;
	}
	public String getPurtime() {
		return purtime;
	}
	public void setPurtime(String purtime) {
		this.purtime = purtime;
	}
	public String getPurdeadline() {
		return purdeadline;
	}
	public void setPurdeadline(String purdeadline) {
		this.purdeadline = purdeadline;
	}
	public String getInstock() {
		return instock;
	}
	public void setInstock(String instock) {
		this.instock = instock;
	}
	
	
}
