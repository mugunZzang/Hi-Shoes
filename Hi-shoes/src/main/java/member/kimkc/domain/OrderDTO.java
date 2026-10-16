package member.kimkc.domain;

public class OrderDTO {
	private int onum;			// 주문번호
	private String oderdate;	// 주문시간
	private String fk_userid;	// 사용자 id
	private String orderstatus;	// 주문상태
	private String arrivaldate;	// 도착예정일
	
	private MemberDTO memberDTO;	// 사용자VO

	
	
	public int getOnum() {
		return onum;
	}

	public void setOnum(int onum) {
		this.onum = onum;
	}

	public String getOderdate() {
		return oderdate;
	}

	public void setOderdate(String oderdate) {
		this.oderdate = oderdate;
	}

	public String getFk_userid() {
		return fk_userid;
	}

	public void setFk_userid(String fk_userid) {
		this.fk_userid = fk_userid;
	}

	public String getOrderstatus() {
		return orderstatus;
	}

	public void setOrderstatus(String orderstatus) {
		this.orderstatus = orderstatus;
	}

	public String getArrivaldate() {
		return arrivaldate;
	}

	public void setArrivaldate(String arrivaldate) {
		this.arrivaldate = arrivaldate;
	}

	public MemberDTO getMemberDTO() {
		return memberDTO;
	}

	public void setMemberDTO(MemberDTO memberDTO) {
		this.memberDTO = memberDTO;
	}

	
	
	
}
