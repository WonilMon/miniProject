package kr.co.softsoldesk.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.co.softsoldesk.beans.UserBean;


public interface UserMapper {


	@Select("select user_id from user_table where user_id = #{user_id}")
	String checkUserIdExist(String user_id);
	
	// user_seq.nextval 시퀀스 이해하기
	@Insert("insert into user_table values(user_seq.nextval, #{user_name}, #{user_id}, #{user_pw})")
	void addUser(UserBean joinUserBean);
	
	// id, pw 넣으면 idx와 name 반환
	// 있으면 UserBean 담고 로그인 성공, 없으면 아무것도없는 UserBean 반환하여 로그인 실패
	@Select("select user_idx, user_name from user_table where user_id = #{user_id} and user_pw = #{user_pw}")
	UserBean getLoginUserInfo(UserBean tempLoginUserBean);
	
	// modify
	@Select("select user_id, user_name from user_table where user_idx = #{user_idx}")
	UserBean getModifyUserInfo(int user_idx);
	
	@Update("update user_table set user_pw = #{user_pw} where user_idx = #{user_idx}")
	void modifyUserInfo(UserBean modifyUserBean);
	
}
