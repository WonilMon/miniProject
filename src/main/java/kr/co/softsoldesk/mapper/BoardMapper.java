package kr.co.softsoldesk.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

import kr.co.softsoldesk.beans.ContentBean;

public interface BoardMapper {

	@Insert("insert into content_table(content_idx, content_subject, content_text, content_file, content_writer_idx, content_board_idx, content_date) "
			+ "values(content_seq.nextval, #{content_subject}, #{content_text}, #{content_file, jdbcType=VARCHAR}, #{content_writer_idx}, #{content_board_idx}, sysdate)")
	void addContentInfo(ContentBean writeContentBean);
//	jdbcType=VARCHAR : null값을 문자로 인식하여 터지지않게 해줌
	
	@Select("select max(content_idx) from content_table")
	int getLatestIdx();

	@Select("select board_info_name from board_info_table where board_info_idx = #{board_info_idx}")
	String getBoardInfoName(int board_info_idx);

//	c.content_subject, c.content_date는 ContentBean이 담을 수 있지만, u.user_name은 담을 수 없다
//	(writer_idx라는 기본키를 담는거지 name을 담는게 아님) -> ContentBean에 변수 추가하고 as 로 변수명 통일
	@Select("select c.content_idx, c.content_subject, to_char(c.content_date, 'YYYY-MM-DD') as content_date, u.user_name as content_writer_name from content_table c, user_table u where c.content_writer_idx = u.user_idx and content_board_idx = #{board_info_idx} order by c.content_idx desc")
	List<ContentBean> getContentList(int board_info_idx, RowBounds rowBounds);

	@Select("select u.user_idx as content_writer_idx ,c.content_subject, content_text, to_char(c.content_date, 'YYYY-MM-DD') as content_date, u.user_name as content_writer_name "
			+ "from content_table c, user_table u " + "where c.content_writer_idx = u.user_idx "
			+ "and content_idx = #{content_idx}")
	ContentBean getReadContentBean(int content_idx);

	@Update("update content_table set content_subject = #{content_subject}, content_text = #{content_text}, content_file = #{content_file, jdbcType=VARCHAR} where content_idx = #{content_idx}")
	void updateContent(ContentBean modifyContentBean);

	@Delete("delete content_table where content_idx=#{content_idx}")
	void deleteContent(int content_idx);

	@Select("select u.user_idx as content_writer_idx ,c.content_subject " + "from content_table c, user_table u "
			+ "where c.content_writer_idx = u.user_idx " + "and content_idx = #{content_idx}")
	ContentBean getContentWriterIdx(int content_idx);

	@Select("select count(*) from content_table where content_board_idx = #{content_board_idx}")
	int getContentCnt(int content_board_idx);
}
