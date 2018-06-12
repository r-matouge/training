<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	//セッションスコープからユーザー情報を取得
	Integer TypeId = (Integer) session.getAttribute("type_id");
	String loginName = (String) session.getAttribute("loginName");
	Integer Id = (Integer) session.getAttribute("Id");
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
			<li class="dropdown active"><a href="#" class="dropdown-toggle"
				data-toggle="dropdown" role="button"> <span
					class="glyphicon glyphicon-user" aria-hidden="true"></span> <%=loginName%>
					<span class="caret"></span></a>
				<ul class="dropdown-menu" role="menu">
					<li><a href="LogoutServlet">ログアウト</a></li>
				</ul></li>
		</ul>

	</div>

	<div class="main_content">
		<div class="container">
			<h1>イベント詳細</h1>

			<c:forEach items="${eventList}" var="event">
				<table class="table">

					<tr>
						<th>タイトル</th>
						<td><c:out value="${event.title}" /> <c:if
								test="${event.attend eq true}">
								<span class="label label-danger">参加</span>
							</c:if></td>
					</tr>
					<tr>
						<th>開催日時</th>
						<td><fmt:formatDate value="${event.start}"
								pattern="y年M月d日(E) HH時mm分" /></td>
					</tr>
					<tr>
						<th>終了日時</th>
						<td><c:choose>
								<c:when test="${event.end_not==true}">未定</c:when>
								<c:when test="${event.end_not==false}">
									<fmt:formatDate value="${event.end}" pattern="y年M月d日(E) HH時mm分" />
								</c:when>
							</c:choose></td>
					</tr>
					<tr>
						<th>場所</th>
						<td><c:out value="${event.place}" /></td>
					</tr>
					<tr>

						<th>対象グループ</th>
						<td><c:out value="${event.group_name}" /></td>
					</tr>
					<tr>
						<th>詳細</th>
						<td><c:out value="${event.detail}" /></td>
					</tr>
					<tr>
						<th>管理者</th>
						<td><c:forEach items="${make}" var="make">
								<c:out value="${make.name}" />

							</c:forEach></td>
					</tr>
					<tr>
						<th>参加者</th>
						<td><c:forEach items="${userList}" var="user">
								<c:out value="${user.name}" />　

							</c:forEach></td>
					</tr>
					<tr>
						<th>参加人数</th>
						<td><%=request.getAttribute("sumMember")%></td>
					</tr>

				</table>

				<a href="ListEventServlet"><input type="submit"
					class="btn btn-primary" value="一覧に戻る" /></a>

				<c:choose>
					<c:when test="${event.attend eq true}">
						<a
							href="EventMoreServlet?id=<c:out value="${event.id}" />&attend=0&userid=<%=TypeId%>">
							<input type="submit" class="btn btn-warning" value="参加を取り消す" />
						</a>
					</c:when>
					<c:when test="${event.attend eq false}">
						<a
							href="EventMoreServlet?id=<c:out value="${event.id}" />&attend=1&userid=<%=TypeId%>">
							<input type="submit" class="btn btn-info" value="参加する" />
						</a>
					</c:when>
				</c:choose>

				<c:choose>
					<c:when test="${event.registered_by eq Id}">
						<form action="UpdateEventServlet" method="get"
							style="display: inline" style="margin-top:0em; margin-bottom:0em">
							<input type="submit" class="btn btn-default" value="編集" /> <input
								type="hidden" name="event_id"
								value="<c:out value="${event.id}" />">
						</form>
						<input type="submit" class="btn btn-danger" data-toggle="modal"
							data-target="#modal-example" value="削除" />

						<div class="modal" id="modal-example" tabindex="-1">
							<div class="modal-dialog">
								<!-- 3.モーダルのコンテンツ -->
								<div class="modal-content">
									<!-- 5.モーダルのボディ -->
									<div class="modal-body">本当に削除してよろしいですか？</div>
									<!-- 6.モーダルのフッタ -->
									<div class="modal-footer">
										<form action="EventMoreServlet" method="get"
											style="display: inline">
											<input type="submit" class="btn btn-default"
												data-dismiss="modal" value="cancel" />
										</form>
										<form action="DeleteEventServlet" method="post"
											style="display: inline">
											<input type="submit" class="btn btn-primary" value="OK" /> <input
												type="hidden" name="id"
												value="<c:out value="${event.id}" />">
										</form>
									</div>
								</div>
							</div>
						</div>
					</c:when>
				</c:choose>
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