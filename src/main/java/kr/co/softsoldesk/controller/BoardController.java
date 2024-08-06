package kr.co.softsoldesk.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.swing.text.AbstractDocument.Content;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.softsoldesk.beans.ContentBean;
import kr.co.softsoldesk.beans.PageBean;
import kr.co.softsoldesk.beans.UserBean;
import kr.co.softsoldesk.service.BoardService;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	BoardService boardService;

	@Resource(name = "loginUserBean")
	private UserBean loginUserBean;

	@GetMapping("/main") // 매핑으로 이게 포함된 것들을 받고
	private String main(@RequestParam("board_info_idx") int board_info_idx, Model model,
						@RequestParam(value = "page", defaultValue = "1") int page) {
//		jsp에서 끌고온 파라미터인 Boar_info_idx 를 RequestParam으로 받는거 

		String boardInfoName = boardService.getBoardInfoName(board_info_idx);
		List<ContentBean> contentList = boardService.getContentList(board_info_idx, page);

//		게시판 index 번호 가져오기
		model.addAttribute("board_info_idx", board_info_idx);
		model.addAttribute("board_info_name", boardInfoName);
		model.addAttribute("content_list", contentList);
		
		PageBean pageBean = boardService.getContentCnt(board_info_idx, page);
		model.addAttribute("pageBean", pageBean);

		return "board/main"; // 사용자에게 뿌려준다
	}

	@GetMapping("/read")
	private String read(ContentBean readContentBean, @RequestParam("board_info_idx") int board_info_idx,
			@RequestParam("content_idx") int content_idx, Model model) {

		readContentBean = boardService.getReadContent(content_idx);

		int user_idx = loginUserBean.getUser_idx();

		model.addAttribute("readContentBean", readContentBean);
		model.addAttribute("board_info_idx", board_info_idx);
		model.addAttribute("content_idx", content_idx);
		model.addAttribute("user_idx", user_idx);

		return "board/read";
	}

	@GetMapping("/write") // 폼 형식의 주입을 위해 ContentBean을 같이 끌고 내려감
	private String write(@ModelAttribute("writeContentBean") ContentBean writeContentBean,
			@RequestParam("board_info_idx") int board_info_idx) {

		// 여기서 깡통의 board_idx 설정, 나머진 service에서
		writeContentBean.setContent_board_idx(board_info_idx);

		return "board/write";
	}

	@PostMapping("/write_pro")
	private String write_pro(@Valid @ModelAttribute("writeContentBean") ContentBean writeContentBean,
			BindingResult result, Model model) {

		if (result.hasErrors()) {
			return "board/write";
		}
		boardService.addContentInfo(writeContentBean);

		int board_info_idx = writeContentBean.getContent_board_idx();
		int content_idx = boardService.getLatestIdx();
		
		model.addAttribute("board_info_idx", board_info_idx);
		model.addAttribute("content_idx", content_idx);

		return "board/write_success";
	}

	@GetMapping("/modify")
	private String modify(@ModelAttribute("modifyContentBean") ContentBean modifyContentBean, @RequestParam("content_idx") int content_idx, Model model,
			HttpServletRequest request) {

		request.setAttribute("content_idx", content_idx);

		modifyContentBean = boardService.getReadContent(content_idx);
		modifyContentBean.setContent_idx(content_idx);

		model.addAttribute("modifyContentBean", modifyContentBean);

		return "board/modify";
	}

	@PostMapping("/modify_pro")
	private String modify_pro(@Valid @ModelAttribute("modifyContentBean") ContentBean modifyContentBean,
			BindingResult result) {
		if (result.hasErrors()) {
			return "board/modify";
		}
		boardService.updateContent(modifyContentBean);
		return "board/modify_success";
	}

	@GetMapping("/delete")
	private String delete(ContentBean deleteContentBean, @RequestParam("board_info_idx") int board_info_idx,
			@RequestParam("content_idx") int content_idx, Model model) {

		boardService.deleteContent(content_idx);

		model.addAttribute("board_info_idx", board_info_idx);
		
		return "board/delete";
	}
	
	@GetMapping("/not_equal_user")
	public String not_equal_user() {
		return "board/not_equal_user";
	}
}
