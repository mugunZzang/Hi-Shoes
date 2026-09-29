package common.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface InterCommand {
	// 추상메서드(미완성 메서드)
	void execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	
}
