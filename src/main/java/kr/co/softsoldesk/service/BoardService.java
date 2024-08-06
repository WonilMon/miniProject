package kr.co.softsoldesk.service;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.co.softsoldesk.DAO.BoardDAO;
//import kr.co.softsoldesk.DAO.BoardDAO;
import kr.co.softsoldesk.beans.ContentBean;
import kr.co.softsoldesk.beans.PageBean;
import kr.co.softsoldesk.beans.UserBean;

@Service
@PropertySource("/WEB-INF/properties/option.properties")
public class BoardService {

	@Autowired
	private BoardDAO boardDao;

	@Resource(name = "loginUserBean")
	private UserBean loginUserBean;

//	annotation으로 properties 끌고와서 value로 지정 후 path_upload에 경로를 주입
	@Value("${path.upload}")
	private String path_upload;

//	option.properties에 올려놨던 값 가져오기
	@Value("${page.listCnt}")
	private int page_list;
	@Value("${page.paginationCnt}")
	private int paginationCnt;

//	------------------------------------------------------------
//	여기서 쓸 메서드

	public String saveUploadFile(MultipartFile upload_file) {

//		현재시간 + 파일의 원래이름
		String file_name = System.currentTimeMillis() + "_" + upload_file.getOriginalFilename();

		try {

//			value로 잡아온 properties의 경로에다가 해당 파일을 저장하겠다
			upload_file.transferTo(new File(path_upload + "/" + file_name));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return file_name;
	}

//	------------------------------------------------------------

	public void addContentInfo(ContentBean writeContentBean) {

		MultipartFile upload_file = writeContentBean.getUpload_file();

		if (upload_file.getSize() > 0) {
//			file에 대한 이름을 saveUploadFile메서드에서 만들도록
			String file_name = saveUploadFile(upload_file);

			writeContentBean.setContent_file(file_name);
		}
		writeContentBean.setContent_writer_idx(loginUserBean.getUser_idx());
//		깡통의 file_name과 writer_idx는 여기서 설정

		boardDao.addContentInfo(writeContentBean);
	}
	
	public int getLatestIdx() {
		return boardDao.getLatestIdx();
	}

	public String getBoardInfoName(int board_info_idx) {
		return boardDao.getBoardInfoName(board_info_idx);
	}

	public List<ContentBean> getContentList(int board_info_idx, int page) {

//		1페이지 링크, 0
//		2페이지 링크, 10
//		3페이지 링크, 20

		int start = (page - 1) * page_list;
		RowBounds rowBounds = new RowBounds(start, page_list);

		return boardDao.getContentList(board_info_idx, rowBounds);
	}

	public ContentBean getReadContent(int content_idx) {
		return boardDao.getReadContentBean(content_idx);
	}

	public void updateContent(ContentBean modifyContentBean) {

		MultipartFile upload_file = modifyContentBean.getUpload_file();

		if (upload_file.getSize() > 0) {
			String file_name = saveUploadFile(upload_file);
			modifyContentBean.setContent_file(file_name);
		}

		boardDao.updateContent(modifyContentBean);
	}

	public void deleteContent(int content_idx) {
		boardDao.deleteContent(content_idx);
	}

	public ContentBean getContentWriterIdx(int content_idx) {
		return boardDao.getContentWriterIdx(content_idx);
	}
	
	public PageBean getContentCnt(int content_board_idx, int currentPage) {
		
		int content_cnt = boardDao.getContentCnt(content_board_idx); // 게시판 전체 글 개수 구해준 후
		
		PageBean pageBean = new PageBean(content_cnt, currentPage, page_list, paginationCnt);
		
		return pageBean;
	}

}
