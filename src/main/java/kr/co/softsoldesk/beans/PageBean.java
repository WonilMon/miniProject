package kr.co.softsoldesk.beans;

public class PageBean {

	private int min; 		// 최소 페이지 번호
	private int max; 		// 최대 페이지 번호
	private int prevPage; 	// 이전
	private int nextPage; 	// 다음
	private int pageCnt; 	// 전체 페이지 개수
	private int currentPage;// 현재 페이지 번호

	public PageBean(int contentCnt, int currentPage, int contentPageCnt, int paginationCnt) {

//		contentCnt : 전체글 개수
//		currentPage : 현재 페이지 번호
//		contentPageCnt : 페이지 당 글의 개수 (option.properties - 10)
//		paginationCnt : 한번에 표시할 페이지 버튼의 개수 (ex. 글이 21개면 3개의 링크 버튼)
		
		this.currentPage = currentPage;
		
		pageCnt = contentCnt/contentPageCnt; // 게시글 수에 따른 페이지의 수 (ex. 27/10 = 2)
		
		if(contentCnt % contentPageCnt > 0) {// pageCnt가 10의 단위가 아니면
			pageCnt++;
		}
		
		min = ((currentPage - 1)/contentPageCnt) * contentPageCnt + 1; // 최소페이지 번호를 1,11,21,,,로 맞춰줌
//		1페이지 : ((1-1)/10) + 1 => 1
//		10페이지 : ((10-1)/10) + 1 => 1
//		11페이지 : ((11-1)/10) + 1 => 2
	
		max = min + paginationCnt - 1; // 최대 페이지
		
		if(max > pageCnt) {
			max = pageCnt;
		} // 전체 페이지 제한하기
		
		prevPage = min - 1;
		nextPage = max + 1;
//		min, max 페이지를 움직여서 전부 조정해주기
		
		if(prevPage < pageCnt) {
			prevPage = pageCnt;
		}
		if(nextPage > pageCnt) {
			nextPage = pageCnt;
		}
		
		
	}

//	--------------------------

	public int getMin() {
		return min;
	}

	public int getMax() {
		return max;
	}

	public int getPrevPage() {
		return prevPage;
	}

	public int getNextPage() {
		return nextPage;
	}

	public int getPageCnt() {
		return pageCnt;
	}

	public int getCurrentPage() {
		return currentPage;
	}

}
