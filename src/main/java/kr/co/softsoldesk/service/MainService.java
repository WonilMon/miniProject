package kr.co.softsoldesk.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.softsoldesk.DAO.BoardDAO;
import kr.co.softsoldesk.beans.ContentBean;

@Service
public class MainService {

	@Autowired
	private BoardDAO boardDao;

	public List<ContentBean> getMainList(int board_info_idx) {

		RowBounds rowBounds = new RowBounds(0, 5);
		
		return boardDao.getContentList(board_info_idx, rowBounds);
//		ex. 1번 게시판을 가져다주면 딱 5개만 가져다 주는

	}

}
