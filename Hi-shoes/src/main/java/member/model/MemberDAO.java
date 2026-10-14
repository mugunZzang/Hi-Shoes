package member.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import member.domain.MemberDTO;

public interface MemberDAO {
	
	// 회원가입을 해주는 메서드 (tbl_member 테이블에 insert)
	int registerMember(MemberDTO member) throws SQLException;
	
	// 사업자등록번호중복검사
	boolean busiNumDuplicateCheck(String busiNum) throws SQLException;
	
	// ID 중복검사 (tbl_member 테이블에서 userid 가 존재하면 true 를 리턴해주고, userid 가 존재하지 않으면 false 를 리턴한다) 
	boolean idDuplicateCheck(String userid) throws SQLException;

	// email 중복검사 (tbl_member 테이블에서 email 이 존재하면 true 를 리턴해주고, email 이 존재하지 않으면 false 를 리턴한다) 
	boolean emailDuplicateCheck(String email) throws SQLException;

	// 로그인 처리
	MemberDTO login(Map<String, String> paraMap) throws SQLException;

	// 아이디 찾기(성명, 이메일을 입력받아서 해당 사용자의 아이디를 알려준다) 	
	String findUserid(Map<String, String> paraMap) throws SQLException;

	// 비밀번호 찾기(아이디, 이메일을 입력받아서 해당 사용자가 존재하는지 유무를 알려준다)
	boolean isUserExist(Map<String, String> paraMap) throws SQLException;

	// 비밀번호 변경하기 
	int pwdUpdate(Map<String, String> paraMap) throws SQLException;

	// 패스워드 일치여부 검사
	int authentication(Map<String, String> paraMap) throws SQLException;

	// 비밀번호 변경 시 현재 사용 중인 비밀번호인지 아닌지 알아오기(사용중인 비밀번호 이라면 true, 새로운 비밀번호라면 false)
	boolean duplicatePwdCheck(Map<String, String> paraMap) throws SQLException;

	// 회원의 개인 정보 변경하기 
	int updateMember(MemberDTO member) throws SQLException;	
	
}
