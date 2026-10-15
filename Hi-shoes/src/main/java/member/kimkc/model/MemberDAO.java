package member.kimkc.model;

import java.sql.SQLException;

import member.domain.MemberDTO;

public interface MemberDAO {

	// 구매자 정보 들고오기
	MemberDTO getMemberInfo(String userid) throws SQLException;
	
	
	
	
}
