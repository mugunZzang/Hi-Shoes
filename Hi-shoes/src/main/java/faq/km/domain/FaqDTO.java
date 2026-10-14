package faq.km.domain;

public class FaqDTO {
	private int fnum; // faq번호
	private String fsubject; // faq 글제목
	private String fcontents; // faq 글내용
	private String fcategory; // faq 카테고리
	
	public int getFnum() {
		return fnum;
	}
	public void setFnum(int fnum) {
		this.fnum = fnum;
	}
	public String getFsubject() {
		return fsubject;
	}
	public void setFsubject(String fsubject) {
		this.fsubject = fsubject;
	}
	public String getFcontents() {
		return fcontents;
	}
	public void setFcontents(String fcontents) {
		this.fcontents = fcontents;
	}
	public String getFcategory() {
		return fcategory;
	}
	public void setFcategory(String fcategory) {
		this.fcategory = fcategory;
	}
}
