package kr.co.softsoldesk.service;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.softsoldesk.DAO.UserDAO;
import kr.co.softsoldesk.beans.UserBean;

@Service
public class UserService {

	@Resource(name = "loginUserBean") // RootAppContext에 올려놨던거 가져오기 (lazy를 줬기떄문에 이시점에서 생성)
	private UserBean loginUserBean;

	@Autowired
	private UserDAO userDao;

//	--------------------------------------------------------

//	중복확인
	public boolean checkUserIdExist(String user_id) {
//		값이 있으면 user_id가 들어올 것이고, 없으면 null값이 들어오겠죠
		String checkId = userDao.checkUserIdExist(user_id);
//		받은 값으로 로직을 짜는 곳임 여긴, 지금부터 짜 봄
		if (checkId == null) {
			return true;
		} else {
			return false;
		}
	}

//	회원가입
	public void addUser(UserBean joinUserBean) {
		userDao.addUser(joinUserBean);
	}

//	로그인
	public void getLoginUserInfo(UserBean tempLoginUserBean) {

//		있으면 해당 유저의 이름,id,pw가 들어있을 것이고, 없으면 깡통이겠죠
		UserBean tempLoginUserBean2 = userDao.getLoginUserBeanInfo(tempLoginUserBean);

		if (tempLoginUserBean2 != null) {
			loginUserBean.setUser_idx(tempLoginUserBean2.getUser_idx());
			loginUserBean.setUser_name(tempLoginUserBean2.getUser_name());
			loginUserBean.setUserLogin(true); //판별을 true로 바꾸기
			
		}
	}
	
//	modify
	public void getModifyUserInfo(UserBean modifyUserBean) {
		
		UserBean tempModifyUserBean = userDao.getModifyUserBeanInfo(loginUserBean.getUser_idx());
//		loginUserBean.getUser_idx() : 싱글톤이기에 아까 넣은 loginUserBean의 idx를 이용한다

		modifyUserBean.setUser_idx(tempModifyUserBean.getUser_idx());
		modifyUserBean.setUser_id(tempModifyUserBean.getUser_id());
		modifyUserBean.setUser_name(tempModifyUserBean.getUser_name());

	}
	
//	modify update
	public void modifyUserInfo(UserBean modifyUserBean) {
		
		modifyUserBean.setUser_idx(loginUserBean.getUser_idx());
		
		System.out.println("1:" +modifyUserBean.getUser_idx());
		System.out.println("2:" +modifyUserBean.getUser_id());
		System.out.println("3:" +modifyUserBean.getUser_name());
		System.out.println("4:" +modifyUserBean.getUser_pw());
		System.out.println("5:" +modifyUserBean.getUser_pw2());
		
		userDao.modifyUserInfo(modifyUserBean);
		
	}
	
}
