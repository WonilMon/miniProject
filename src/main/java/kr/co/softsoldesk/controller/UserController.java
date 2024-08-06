package kr.co.softsoldesk.controller;

import javax.annotation.Resource;	
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.softsoldesk.beans.UserBean;
import kr.co.softsoldesk.service.UserService;
import kr.co.softsoldesk.validator.UserValidator;

@Controller
@RequestMapping("/user") // user로 다 잡아와서
public class UserController {

	@Autowired
	private UserService userService;

	@Resource(name = "loginUserBean")
	private UserBean loginUserBean;

//	-------------------------------------------

	@GetMapping("/login") // getMapping 각각 연결
	private String login(@ModelAttribute("tempLoginUserBean") UserBean tempLoginUserBean,
						@RequestParam(value = "fail", defaultValue = "false") boolean fail, Model model) {
		model.addAttribute("fail", fail);
		return "user/login";
	}

	@PostMapping("/login_pro")
	private String login_pro(@ModelAttribute("tempLoginUserBean") @Valid UserBean joinUserBean, BindingResult result) {

		if (result.hasErrors()) {
			return "user/login";
		}

//		유효성 검사가 통과되면 userService에게 던져주고
		
		userService.getLoginUserInfo(joinUserBean);
		
		if (loginUserBean.isUserLogin() == true) {
			return "user/login_success";
		} else {
			return "user/login_fail";
		}
	}
	
	@GetMapping("/not_login")
	public String not_login() {
		return "user/login_login";
	}

	@GetMapping("/join")
	private String join(@ModelAttribute("joinUserBean") UserBean joinUserBean) {

		return "user/join";
	}

	@PostMapping("/join_pro")
	private String join_pro(@ModelAttribute("joinUserBean") @Valid UserBean joinUserBean, BindingResult result) {

		if (result.hasErrors()) {
			return "user/join";
		}

		userService.addUser(joinUserBean);

		return "user/join_success";
	}

	@GetMapping("/modify")
	private String modify(@ModelAttribute("modifyUserBean") UserBean modifyUserBean) {
		
		userService.getModifyUserInfo(modifyUserBean);
		
		return "user/modify";
		
	}
	
	@PostMapping("/modify_pro")
	private String modify_pro(@ModelAttribute("modifyUserBean") @Valid UserBean modifyUserBean, BindingResult result) {
		
		System.out.println("에러 :" + result.hasErrors());
		
		if(result.hasErrors()) {
			return "user/modify";
		}
		
		userService.modifyUserInfo(modifyUserBean);
		
		return "user/modify_success";
	}

	@GetMapping("/logout")
	private String logout() {
		
		loginUserBean.setUserLogin(false);
		
		return "user/logout";
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		UserValidator validator1 = new UserValidator();
		binder.addValidators(validator1);
	}

}
