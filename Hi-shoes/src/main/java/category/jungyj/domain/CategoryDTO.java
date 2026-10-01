package category.jungyj.domain;

public class CategoryDTO {
    private int cateNum;        //NUMBER   카테고리코드
    private String cateName;    //NVARCHAR2(100) 카테고리명
    
    
    
	public int getCateNum() {
		return cateNum;
	}
	public void setCateNum(int cateNum) {
		this.cateNum = cateNum;
	}
	public String getCateName() {
		return cateName;
	}
	public void setCateName(String cateName) {
		this.cateName = cateName;
	}
    
    
}
