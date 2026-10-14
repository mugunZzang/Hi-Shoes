package member.kth.domain;

import java.time.LocalDate;

public class AdminMemberDTO {

	
	private String userid;             
	private int userseq;
	private int busiNum;
	private String pwd;                
	private String company;               
	private String email;            
	private String mobile;            
	private int postcode;           
	private String address;            
	private String detailaddress;      
	private String extraaddress;                  
	private LocalDate birthday;                         
	private String registerday;        
	private String lastpwdchangedate; 
	private String ban;
	private String status;                
	private String idle;                 
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	
	private boolean requirePwdChange = false;
	//마지막으로 비밀번호를 변경한 날짜가 현재시각으로 부터 6개월이 지났으면 true
	//마지막으로 비밀번호를 변경한 날짜가 현재시각으로 부터 6개월이 지나지 않았다면 false
	
	 
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	
	public int getUserseq() {
		return userseq;
	}
	public void setUserseq(int userseq) {
		this.userseq = userseq;
	}
	public int getBusiNum() {
		return busiNum;
	}
	public void setBusiNum(int busiNum) {
		this.busiNum = busiNum;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getBan() {
		return ban;
	}
	public void setBan(String ban) {
		this.ban = ban;
	}
	public int getPostcode() {
		return postcode;
	}
	public void setPostcode(int postcode) {
		this.postcode = postcode;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getIdle() {
		return ban;
	}
	public void setIdle(String idle) {
		this.idle = idle;
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
	
	
	
//////////////////////////////////////////////////////////////////
public boolean isRequirePwdChange() { 
	   return requirePwdChange;
	} 
public void setRequirePwdChange(boolean b) { 
	   this.requirePwdChange = b; 
	} 
/////////////////////////////////////////////////////////////////
/* public int getAge() {
	if(birthday == null) {
		return 0;
	}
	LocalDate today = LocalDate.now();//현재 날짜
	/*System.out.println("today=>" + today); //2026-09-11
	System.out.println("today.getYear()=>" + today.getYear());
	System.out.println("today.getMonth()=>" + today.getMonth());
	System.out.println("today.getMonthValue()=>" + today.getMonthValue());
	System.out.println("today.getDayOfMonth()=>" + today.getDayOfMonth());
	
	LocalDate today2 = LocalDate.now();//현재 날짜
	System.out.println("today2=>" + today2); //2026-09-11
	System.out.println("today2.getYear()=>" + today2.getYear());
	System.out.println("today2.getMonth()=>" + today2.getMonth());
	System.out.println("today2.getMonthValue()=>" + today2.getMonthValue());
	System.out.println("today.getDayOfMonth()=>" + today2.getDayOfMonth());
	System.out.println("today.getHour()=>" + today2.getHour());
	System.out.println("today.getMinute() =>"+ today2.getMinute());
	*/
	
	/*int age=today.getYear()- birthday.getYear();
	
	//LocalDate birth_day = LocalDate.of(1998,10,20);
	//int currentYear = 2026;
    
	//LocalDate thisYear_Birthday = birth_day.withYear(currentYear);
	
	//올해의 생일 날짜 객체 생성하기
	LocalDate currentYearBirthday = birthday.withYear(today.getYear());
	
	if(currentYearBirthday.isAfter(today))
	{//회원의 올해 생일이 현재날짜보다 이후이라면 
		
	  age--;	
	}
	return age; 
	
} 
*/
	
	
}
