package shop.kimkc.domain;

public class ReviewDTO {

	private String fk_userid;		// 작성자id(db에는 유지안함)
	
	private int fk_odnum;         	// 주문상세번호
	private int rating;				// 별점
	private String rcontents;		// 리뷰내용
	private String rwritedate;     	// 리뷰작성일자
	private String rimage;			// 리뷰이미지
	
	private StockDTO stockDTO;		// 재고 VO
	
	
	
	
	public int getFk_odnum() {
		return fk_odnum;
	}
	public void setFk_odnum(int fk_odnum) {
		this.fk_odnum = fk_odnum;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public String getRcontents() {
		return rcontents;
	}
	public void setRcontents(String rcontents) {
		this.rcontents = rcontents;
	}
	public String getRwritedate() {
		return rwritedate;
	}
	public void setRwritedate(String rwritedate) {
		this.rwritedate = rwritedate;
	}
	public String getRimage() {
		return rimage;
	}
	public void setRimage(String rimage) {
		this.rimage = rimage;
	}
	public StockDTO getStockDTO() {
		return stockDTO;
	}
	public void setStockDTO(StockDTO stockDTO) {
		this.stockDTO = stockDTO;
	}
	public String getFk_userid() {
		return fk_userid;
	}
	public void setFk_userid(String fk_userid) {
		this.fk_userid = fk_userid;
	}
}
