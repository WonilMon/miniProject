package kr.co.softsoldesk.Interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import kr.co.softsoldesk.beans.BoardInfoBean;
import kr.co.softsoldesk.beans.UserBean;
import kr.co.softsoldesk.service.TopMenuService;

public class TopMenuInterceptor implements HandlerInterceptor {

//	인터셉터는 autowired(자동주입)가 불가하기에 생성자를 통해 주입
	private TopMenuService topMenuService;
	
	private UserBean loginUserBean;

//	생성자
	public TopMenuInterceptor(TopMenuService topMenuService, UserBean loginUserBean) {
		this.topMenuService = topMenuService;
		this.loginUserBean = loginUserBean;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		List<BoardInfoBean> topMenuList = topMenuService.getTopMenuList(); // 게시판(자유,유머,정치,스포츠) 꺼내오고
		request.setAttribute("topMenuList", topMenuList);					// request에 담음
		request.setAttribute("loginUserBean", loginUserBean);				// 빈깡통+false 혹은 로그인+true를 request에 담음 (어떠한 jsp든 상관없이 다 갖다 쓸수있는거임!!!)
//		Model에 담을수 없고 request
		
		return true;
	}
}
