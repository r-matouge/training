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
				class="dropdown-toggle" data-toggle="dropdown" role="button">
				<span class="glyphicon glyphicon-user" aria-hidden="true"></span>
				<%=loginName%>
					<span class="caret"></span> </a>
				<ul class="dropdown-menu" role="menu">
					<li><a href="LogoutServlet">ログアウト</a></li>
				</ul></li>
		</ul>
	</div>

	<div class="main_content">
	<div class="container">
		<h1>イベント一覧</h1>
		<div align="right">
			<nav>
				<c:choose>
					<c:when test="${event.length<=5}">
						<c:set var="start" value="1" />
						<c:set var="end" value="${event.length}" />
					</c:when>
					<c:when test="${(event.page+2)>event.length}">
						<c:set var="start" value="${event.length-5}" />
						<c:set var="end" value="${event.length}" />
					</c:when>
					<c:when test="${(event.page-2)<=0}">
						<c:set var="start" value="1" />
						<c:set var="end" value="5" />
					</c:when>

					<c:when test="${(event.page-2)>0}">
						<c:set var="start" value="${event.page-2}" />
						<c:set var="end" value="${event.page+2}" />
					</c:when>

				</c:choose>
				<ul class="pagination">
					<li><a
						href="ListEventServlet?page=
						<c:out value="${start}"/>"
						aria-label="前のページへ"> <span aria-hidden="true">«</span>
					</a></li>

					<c:forEach var="i" begin="${start}" end="${end}">
						<li <c:if test="${event.page==i}">class="active"</c:if>><a href="ListEventServlet?page=<c:out value="${i}"/>"><c:out
									value="${i}" /></a></li>
					</c:forEach>
					<li><a href="ListEventServlet?page=<c:out value="${end}"/>"
						aria-label="次のページへ"> <span aria-hidden="true">»</span>
					</a></li>
				</ul>
			</nav>
		</div>



		<table class="table table-bordered table-condensed">
			<tr>
				<th>タイトル</th>
				<th>開催日時</th>
				<th>場所</th>
				<th>対象グループ</th>
				<th>詳細</th>
			</tr>

			<c:forEach items="${eventList}" var="event">

				<tr>
					<td><c:out value="${event.title}" />
					<c:if test="${event.attend eq true}">
						<span class="label label-danger">参加</span>
					</c:if></td>
					<td><fmt:formatDate value="${event.start}"
							pattern="y年M月d日(E) HH時mm分" /></td>
					<td><c:out value="${event.place}" /></td>
					<td><c:out value="${event.group_name}" /></td>

					<td>
						<form action="EventMoreServlet" method="get">
							<input type="submit" class="btn btn-default" value="詳細" /> <input
								type="hidden" name="id" value="<c:out value="${event.id}" />">
						</form>
					</td>
				</tr>
			</c:forEach>
		</table>

		<form action="AddEventServlet" method="get">
			<input type="submit" class="btn btn-primary" value="イベントの登録" />
		</form>
	</div>
</div>

<!--ページ幅を狭くしてnavbarが折り返されても、見出しが隠れないようにする-->
<script>
$(window).on('load resize', function(){
    // navbarの高さを取得する
    var height = $('.navbar').height();
    // bodyのpaddingにnavbarの高さを設定する
    $('body').css('padding-top',height);
});
</script>

</body>
</html>



