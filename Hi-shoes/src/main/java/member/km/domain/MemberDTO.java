package member.km.domain;

public class MemberDTO {
	private int userseq; // 사용자번호
	private String userid; // 아이디
	private String pwd; // 비밀번호 (SHA-256 암호화 대상)
	private int busiNum; // 사업자등록번호
	private String email; // 이메일 (AES-256 암호화/복호화 대상)
	private String mobile; // 연락처 (AES-256 암호화/복호화 대상) 
	private int postcode; // 우편번호
	private String address; // 주소
	private String detailaddress; // 상세주소
	private String extraaddress; // 참고항목
	private String company; // 사명    
	private String registerday; // 가입일자 
	private String lastpwdchangedate; // 마지막으로 암호를 변경한 날짜  
	private String status; // 탈퇴유무    
	private String idle; // 휴면유무   
	private String ban; // 정지유무
	
	public int getUserseq() {
		return userseq;
	}
	public void setUserseq(int userseq) {
		this.userseq = userseq;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public int getBusiNum() {
		return busiNum;
	}
	public void setBusiNum(int busiNum) {
		this.busiNum = busiNum;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public int getPostcode() {
		return postcode;
	}
	public void setPostcode(int postcode) {
		this.postcode = postcode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDetailaddress() {
		return detailaddress;
	}
	public void setDetailaddress(String detailaddress) {
		this.detailaddress = detailaddress;
	}
	public String getExtraaddress() {
		return extraaddress;
	}
	public void setExtraaddress(String extraaddress) {
		this.extraaddress = extraaddress;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getRegisterday() {
		return registerday;
	}
	public void setRegisterday(String registerday) {
		this.registerday = registerday;
	}
	public String getLastpwdchangedate() {
		return lastpwdchangedate;
	}
	public void setLastpwdchangedate(String lastpwdchangedate) {
		this.lastpwdchangedate = lastpwdchangedate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getIdle() {
		return idle;
	}
	public void setIdle(String idle) {
		this.idle = idle;
	}
	public String getBan() {
		return ban;
	}
	public void setBan(String ban) {
		this.ban = ban;
	}
}
