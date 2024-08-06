package kr.co.softsoldesk.DAO;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.softsoldesk.beans.ContentBean;
import kr.co.softsoldesk.mapper.BoardMapper;

@Repository
public class BoardDAO {

	@Autowired
	private BoardMapper boardMapper;

//	글 등록
	public void addContentInfo(ContentBean writeContentBean) {
			boardMapper.addContentInfo(writeContentBean);
	}
	public int getLatestIdx() {
		return boardMapper.getLatestIdx();
	}

	public String getBoardInfoName(int board_info_idx) {
		return boardMapper.getBoardInfoName(board_info_idx);
	}

	public List<ContentBean> getContentList(int board_info_idx,	RowBounds rowBounds) {
		return boardMapper.getContentList(board_info_idx, rowBounds);
	}

	public ContentBean getReadContentBean(int content_idx) {
		return boardMapper.getReadContentBean(content_idx);
	}

	public void updateContent(ContentBean modifyContentBean) {
		boardMapper.updateContent(modifyContentBean);
	}

	public void deleteContent(int content_idx) {
		boardMapper.deleteContent(content_idx);
	}

	public ContentBean getContentWriterIdx(int content_idx) {
		return boardMapper.getContentWriterIdx(content_idx);
	}
	
	public int getContentCnt(int content_board_idx) {
		return boardMapper.getContentCnt(content_board_idx);
	}
}
