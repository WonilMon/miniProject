package kr.co.softsoldesk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import kr.co.softsoldesk.service.UserService;

@RestController
public class RestApiController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/user/checkUserIdExist/{user_id}") // 여기서 {user_id}는 동적 값
	public String checkUserIdExist (@PathVariable String user_id) { // @PathVariable은 동적값을 넣겠다. (즉, view에서 올려보낸 user_id가 여기 매개변수로 들어감)
		boolean chk = userService.checkUserIdExist(user_id);
//		ID가 DB에 있으면 true, 없으면 false
		return chk + ""; 
	}
	
}
