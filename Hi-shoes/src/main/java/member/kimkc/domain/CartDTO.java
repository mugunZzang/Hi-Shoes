package member.kimkc.domain;

import shop.kimkc.domain.StockDTO;

public class CartDTO {
	private String fk_userid;		// 사용자 id
	private int fk_snum;			// 제품번호
	private int cqty;				// 수량(장바구니 담은)
	
	private MemberDTO memberDTO;	// 사용자VO
	private StockDTO stockDTO;		// 재고(제품)VO
	
	
	
	public String getFk_userid() {
		return fk_userid;
	}
	public void setFk_userid(String fk_userid) {
		this.fk_userid = fk_userid;
	}
	public int getFk_snum() {
		return fk_snum;
	}
	public void setFk_snum(int fk_snum) {
		this.fk_snum = fk_snum;
	}
	public int getCqty() {
		return cqty;
	}
	public void setCqty(int cqty) {
		this.cqty = cqty;
	}
	public MemberDTO getMemberDTO() {
		return memberDTO;
	}
	public void setMemberDTO(MemberDTO memberDTO) {
		this.memberDTO = memberDTO;
	}
	public StockDTO getStockDTO() {
		return stockDTO;
	}
	public void setStockDTO(StockDTO stockDTO) {
		this.stockDTO = stockDTO;
	}
	
	
}
