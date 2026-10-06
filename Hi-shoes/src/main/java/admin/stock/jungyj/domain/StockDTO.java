package admin.stock.jungyj.domain;

public class StockDTO {
    private int SNUM;           //  제품번호      NUMBER,
    private String FK_PNAME;    //  제품명       NVARCHAR2(100)
    private int SSIZE;          //  사이즈       NUMBER
    private String COLOR;       //  색상        NVARCHAR2(10)
    private int SQTY;           //  재고량       NUMBER 
    
    
	public int getSNUM() {
		return SNUM;
	}
	public void setSNUM(int sNUM) {
		SNUM = sNUM;
	}
	public String getFK_PNAME() {
		return FK_PNAME;
	}
	public void setFK_PNAME(String fK_PNAME) {
		FK_PNAME = fK_PNAME;
	}
	public int getSSIZE() {
		return SSIZE;
	}
	public void setSSIZE(int sSIZE) {
		SSIZE = sSIZE;
	}
	public String getCOLOR() {
		return COLOR;
	}
	public void setCOLOR(String cOLOR) {
		COLOR = cOLOR;
	}
	public int getSQTY() {
		return SQTY;
	}
	public void setSQTY(int sQTY) {
		SQTY = sQTY;
	}
}
