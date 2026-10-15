package shop.kimkc.domain;

public class Product_ImageDTO {
	private int imgnum;				// 상품추가이미지 번호
	private int fk_pnum;			// 상품번호
	private String image_name;		// 이미지명
	
	private ProductDTO productDTO;	// 상품번호VO
	
	
	
	public int getImgnum() {
		return imgnum;
	}
	public void setImgnum(int imgnum) {
		this.imgnum = imgnum;
	}
	public int getFk_pnum() {
		return fk_pnum;
	}
	public void setFk_pnum(int fk_pnum) {
		this.fk_pnum = fk_pnum;
	}
	public String getImage_name() {
		return image_name;
	}
	public void setImage_name(String image_name) {
		this.image_name = image_name;
	}
}
