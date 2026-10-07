package member.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import member.domain.MemberDTO;

public interface MemberDAO {
	
	// 회원가입을 해주는 메서드 (tbl_member 테이블에 insert)
	int registerMember(MemberDTO member) throws SQLException;
	
	// ID 중복검사 (tbl_member 테이블에서 userid 가 존재하면 true 를 리턴해주고, userid 가 존재하지 않으면 false 를 리턴한다) 
	boolean idDuplicateCheck(String userid) throws SQLException;

	// email 중복검사 (tbl_member 테이블에서 email 이 존재하면 true 를 리턴해주고, email 이 존재하지 않으면 false 를 리턴한다) 
	boolean emailDuplicateCheck(String email) throws SQLException;

	// 로그인 처리
	MemberDTO login(Map<String, String> paraMap) throws SQLException;
	
}
