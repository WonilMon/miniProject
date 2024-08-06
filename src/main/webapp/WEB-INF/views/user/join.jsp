<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!-- 절대경로 (어떤 폴더건 상관없이) -->
<c:set var="root" value="${pageContext.request.contextPath }/" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>미니 프로젝트</title>
<!-- Bootstrap CDN -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
<script>
	function checkUserIdExist() {

		let user_id = $("#user_id").val() // 사용자가 입력한 id 값 가져오기

		if (user_id.length == 0) {
			alert("아이디를 입력하세요")
			return
		}

		/* 새로고침 없이 바로 반응할수있게 하는게 에이잭스 */
		$.ajax({

			/* RestController, api는 페이지를 반환하는 게 아니라 데이터를 반환함 */
			url : '${root}/user/checkUserIdExist/' + user_id, // 요청할 페이지의 주소 (아이디를 붙인 주소를 getmapping을통해 RestApiController로)
			type : 'get', // 요청타입
			dataType : 'text',
			success : function(result) {

				if (result.trim() == "true") {
					alert("사용할 수 있는 아이디입니다")
					$("#userIdExist").val("true")
				} else {
					alert("사용할 수 없는 아이디입니다")
					$("#userIdExist").val("false")
				}
			}

		})

	}

	//사용자 ID란에 키보드 입력 시 무조건 false 만드는
	function resetUserIdExist() {
		$("#userIdExist").val("false")
	}
</script>
</head>
<body>

	<!-- 상단 -->
	<c:import url="/WEB-INF/views/include/top_menu.jsp"></c:import>

	<div class="container" style="margin-top: 100px">
		<div class="row">
			<div class="col-sm-3"></div>
			<div class="col-sm-6">
				<div class="card shadow">
					<div class="card-body">
						<!--  form -> form:form -->
						<!-- ${root }user/join_pro로 보내서 유효성 검사를 수행할거임 -->
						<form:form action="${root }user/join_pro"
							modelAttribute="joinUserBean" method="post">
							<div class="form-group">

								<form:label path="user_name">이름</form:label>
								<form:input path="user_name" class="form-control" />
								<form:errors path="user_name" />

							</div>

							<!-- 보이진 않지만 회원가입 전 중복확인 절차(true/false) 넣어주기 -->
							<form:hidden path="userIdExist" />

							<div class="form-group">

								<form:label path="user_id">아이디</form:label>

								<div class="input-group">

									<!-- 이 요소 user_id를 잡아올 거임 -->
									<!-- onkeypress : 키보드로 작업하면 수행하는 함수 -->
									<form:input path="user_id" class="form-control"
										onkeypress="resetUserIdExist()" />

									<div class="input-group-append">

										<!-- checkUserIdExist함수를 위에 만들거임 -->
										<button type="button" class="btn btn-primary"
											onclick="checkUserIdExist()">중복확인</button>

									</div>

									<form:errors path="user_id" />

								</div>

							</div>
							<div class="form-group">

								<form:label path="user_pw">비밀번호</form:label>
								<form:password path="user_pw" class="form-control" />
								<form:errors path="user_pw" />

							</div>
							<div class="form-group">

								<form:label path="user_pw2">비밀번호 확인</form:label>
								<form:password path="user_pw2" class="form-control" />
								<form:errors path="user_pw2" />

							</div>
							<div class="form-group">
								<div class="text-right">

									<form:button type="submit" class="btn btn-primary">회원가입</form:button>

								</div>
							</div>
						</form:form>
					</div>
				</div>
			</div>
			<div class="col-sm-3"></div>
		</div>
	</div>

	<!-- 하단 -->
	<c:import url="/WEB-INF/views/include/bottom_info.jsp"></c:import>

</body>
</html>








