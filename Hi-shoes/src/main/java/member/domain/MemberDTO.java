package member.domain;

import java.time.LocalDate;

public class MemberDTO {
	
	private String userid;               // 회원아이디
	private String pwd;                  // 비밀번호 (SHA-256 암호화 대상)
	private String company;				 // 사명
	private String busiNum;				 // 사업자등록번호
	private String email;                // 이메일 (AES-256 암호화/복호화 대상)
	private String mobile;               // 연락처 (AES-256 암호화/복호화 대상)
	private String postcode;             // 우편번호
	private String address;              // 주소
	private String detailaddress;        // 상세주소
	private String extraaddress;         // 참고항목
	
	
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
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getBusiNum() {
		return busiNum;
	}
	public void setBusiNum(String busiNum) {
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
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
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
	
	////////////////////////////////////////////////////////////
	
	private boolean requirePwdChange = false;
	// 마지막으로 비밀번호를 변경한 날짜가 현재시각으로 부터 6개월이 지났으면 true 
	// 마지막으로 비밀번호를 변경한 날짜가 현재시각으로 부터 6개월이 지나지 않았으면 false 

	public boolean isRequirePwdChange() {
	return requirePwdChange;
	}
	
	public void setRequirePwdChange(boolean requirePwdChange) {
	this.requirePwdChange = requirePwdChange;
	}	

	//////////////////////////////////////////////////
	
}
