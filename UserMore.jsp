<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	//セッションスコープからユーザー情報を取得
	Integer TypeId = (Integer) session.getAttribute("type_id");
	String loginName = (String) session.getAttribute("loginName");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset='utf-8' />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<script src="js/jquery-3.3.1.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<link href="css/bootstrap.min.css" rel="stylesheet" />
<link href="css/style.css" rel="stylesheet" />
<title>ユーザ詳細</title>
</head>
<body>

	<div class="navbar navbar-fixed-top color navbar-default" id="mynavbar">
		<ul class="nav navbar-nav">
			<li class="active" id="logo"><font size="5" color="blue" face="Impact">Event
					Manager</font></li>
			<li class="active"><a href="ListEventNowServlet">本日のイベント</a></li>
			<li class="active"><a href="ListEventServlet">イベント管理</a></li>
			<c:if test="<%=TypeId == 1%>">
				<li class="active"><a href="ListUserServlet">会員管理</a></li>
			</c:if>
		</ul>
		<ul class="nav navbar-nav navbar-right">
			<li class="dropdown active navbar-right"><a href="#"
				class="dropdown-toggle" data-toggle="dropdown" role="button"> <span
					class="glyphicon glyphicon-user" aria-hidden="true"></span> <%=loginName%>
					<span class="caret"></span>
			</a>
				<ul class="dropdown-menu" role="menu">
					<li><a href="LogoutServlet">ログアウト</a></li>
				</ul></li>
		</ul>
	</div>

	<div class="main_content">
		<div class="container">
			<c:forEach items="${userList}" var="user">

				<h1>ユーザ詳細</h1>

				<table class="table">

					<tr>
						<th>ID</th>
						<td><c:out value="${user.loginId}" /></td>
					</tr>
					<tr>
						<th>氏名</th>
						<td><c:out value="${user.name} " /></td>
					</tr>
					<tr>
						<th>所属グループ</th>
						<td><c:out value="${user.group_name}" /></td>
					</tr>

				</table>



				<a href="ListUserServlet"><input type="submit"
					class="btn btn-primary" value="一覧に戻る" /></a>

				<form action="UpdateUserServlet" method="get"
					style="display: inline" style="margin-top:0em; margin-bottom:0em">
					<input type="submit" class="btn btn-default" value="編集" /> <input
						type="hidden" name="user_id" value="<c:out value="${user.id}" />">
				</form>

				<input type="submit" class="btn btn-danger" data-toggle="modal"
					data-target="#modal-example" value="削除" />

				<div class="modal" id="modal-example" tabindex="-1">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-body">本当に削除してよろしいですか？</div>
							<div class="modal-footer">
								<form action="UserMoreServlet" method="get"
								style="display: inline">
									<input type="submit" class="btn btn-default"
										data-dismiss="modal" value="Cancel" />
								</form>
								<form action="DeleteUserServlet" method="post"
								style="display: inline">
									<input type="submit" class="btn btn-primary" value="OK" /> <input
										type="hidden" name="id" value="<c:out value="${user.id}" />">
								</form>
							</div>
						</div>
					</div>
				</div>

			</c:forEach>
		</div>
	</div>
	<script>
		$(window).on('load resize', function() {
			// navbarの高さを取得する
			var height = $('.navbar').height();
			// bodyのpaddingにnavbarの高さを設定する
			$('body').css('padding-top', height);
		});
	</script>
</body>
</html>