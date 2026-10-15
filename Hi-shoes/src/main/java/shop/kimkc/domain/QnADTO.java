package shop.kimkc.domain;

public class QnADTO {
	
	private int qnanum;       		// QnA 번호  
	private int fk_pnum;         	// 판매번호
	private String fk_userid;  		// 작성자 id
	private String qwritedate;      // 작성일자   
	private String qtitle;			// 제목
	private String qcontents;  		// QnA 내용
	private String islock;     		// 공개여부
	private String qanswer;         // 답글내용
	
	
	
	
	
	public int getQnanum() {
		return qnanum;
	}
	public void setQnanum(int qnanum) {
		this.qnanum = qnanum;
	}
	public int getFk_pnum() {
		return fk_pnum;
	}
	public void setFk_pnum(int fk_pnum) {
		this.fk_pnum = fk_pnum;
	}
	public String getFk_userid() {
		return fk_userid;
	}
	public void setFk_userid(String fk_userid) {
		this.fk_userid = fk_userid;
	}
	public String getQwritedate() {
		return qwritedate;
	}
	public void setQwritedate(String qwritedate) {
		this.qwritedate = qwritedate;
	}
	public String getQcontents() {
		return qcontents;
	}
	public void setQcontents(String qcontents) {
		this.qcontents = qcontents;
	}
	public String getIslock() {
		return islock;
	}
	public void setIslock(String islock) {
		this.islock = islock;
	}
	public String getQanswer() {
		return qanswer;
	}
	public void setQanswer(String qanswer) {
		this.qanswer = qanswer;
	}
	public String getQtitle() {
		return qtitle;
	}
	public void setQtitle(String qtitle) {
		this.qtitle = qtitle;
	}
	
	
}
