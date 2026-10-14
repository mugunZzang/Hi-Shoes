package notice.km.domain;

public class NoticeDTO {
	private int nnum; // 공지번호
	private String nsubject; // 공지 글제목
	private String ncontents; // 공지 글내용 
	private String nwritedate; // 작성일자 
	private String nimage; // 이미지 
	
	public int getNnum() {
		return nnum;
	}
	public void setNnum(int nnum) {
		this.nnum = nnum;
	}
	public String getNsubject() {
		return nsubject;
	}
	public void setNsubject(String nsubject) {
		this.nsubject = nsubject;
	}
	public String getNcontents() {
		return ncontents;
	}
	public void setNcontents(String ncontents) {
		this.ncontents = ncontents;
	}
	public String getNwritedate() {
		return nwritedate;
	}
	public void setNwritedate(String nwritedate) {
		this.nwritedate = nwritedate;
	}
	public String getNimage() {
		return nimage;
	}
	public void setNimage(String nimage) {
		this.nimage = nimage;
	}

}
