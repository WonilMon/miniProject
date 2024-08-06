package kr.co.softsoldesk.DAO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.softsoldesk.beans.BoardInfoBean;
import kr.co.softsoldesk.beans.UserBean;
import kr.co.softsoldesk.mapper.TopMenuMapper;
import kr.co.softsoldesk.mapper.UserMapper;

@Repository
public class UserDAO {

	@Autowired
	private UserMapper userMapper;

	// ID 중복확인
	public String checkUserIdExist(String user_id) {
		return userMapper.checkUserIdExist(user_id);
	}

	// 회원가입
	public void addUser(UserBean joinUserBean) {
		userMapper.addUser(joinUserBean);
	}

	// 로그인
	public UserBean getLoginUserBeanInfo(UserBean tempLoginUserBean) {
		return userMapper.getLoginUserInfo(tempLoginUserBean);
	}
	
	// 정보수정
	public UserBean getModifyUserBeanInfo(int user_idx) {
		return userMapper.getModifyUserInfo(user_idx);
	}
	
	// 정보수정
	public void modifyUserInfo(UserBean modifyUserBean) {
		userMapper.modifyUserInfo(modifyUserBean);
	}
	
	
}


