package member.kth.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import member.kth.domain.AdminMemberDTO;

public interface AdminMemberDAO {
	
	// 검색 조건에 따른 회원 목록의 전체 페이지 수 구하기
	int getTotalPage(Map<String, String> paraMap) throws SQLException;

	// 검색 조건에 따른 페이징 처리된 회원 목록 보여주기
	List<AdminMemberDTO> select_Member_paging(Map<String, String> paraMap) throws SQLException;

	// 검색 조건에 따른 전체 회원 수 구하기
	int getTotalMemberCount(Map<String, String> paraMap) throws SQLException;

	//회원 상세 정보 보여주기
	AdminMemberDTO selectOneMember(String userid) throws SQLException;
    
	
	
	
	

}
