package admin.supplier.jungyj.domain;

public class SupplierDTO {

	private String supname;     // NVARCHAR2(100)   공급업체명
	private int sbusinum;       // number           사업자등록번호
	private String ceo;         // NVARCHAR2(10)    대표명
	private String smobile;     // VARCHAR(200)     전화번호 
	private String semail;      // VARCHAR(200)     이메일   
	
	
	public String getSupname() {
		return supname;
	}
	public void setSupname(String supname) {
		this.supname = supname;
	}
	public int getSbusinum() {
		return sbusinum;
	}
	public void setSbusinum(int sbusinum) {
		this.sbusinum = sbusinum;
	}
	public String getCeo() {
		return ceo;
	}
	public void setCeo(String ceo) {
		this.ceo = ceo;
	}
	public String getSmobile() {
		return smobile;
	}
	public void setSmobile(String smobile) {
		this.smobile = smobile;
	}
	public String getSemail() {
		return semail;
	}
	public void setSemail(String semail) {
		this.semail = semail;
	}
	
	
}
