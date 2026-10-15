package member.kimkc.domain;


import shop.kimkc.domain.ProductDTO;

public class WishlistDTO {
	private String fk_userid;		// 사용자 id
	private String fk_pnum;			// 판매번호
	
	private MemberDTO memberDTO;	// 사용자VO
	private ProductDTO productDTO;	// 판매상품VO
	
	
	
	
	public String getFk_userid() {
		return fk_userid;
	}
	public void setFk_userid(String fk_userid) {
		this.fk_userid = fk_userid;
	}
	public String getFk_pnum() {
		return fk_pnum;
	}
	public void setFk_pnum(String fk_pnum) {
		this.fk_pnum = fk_pnum;
	}
	public MemberDTO getMemberDTO() {
		return memberDTO;
	}
	public void setMemberDTO(MemberDTO memberDTO) {
		this.memberDTO = memberDTO;
	}
	public ProductDTO getProductDTO() {
		return productDTO;
	}
	public void setProductDTO(ProductDTO productDTO) {
		this.productDTO = productDTO;
	}
	
	
	
}
