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
<title>イベント編集完了</title>
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
			<ul style="list-style: none;">
				<li><h1>イベント編集</h1></li>
				<li>イベントの編集が完了しました</li>

				<li>
				<form name="form_Id" action="EventMoreServlet" method="get">
				<a href="javascript:form_Id.submit()">イベント詳細に戻る</a>
				<input type="hidden"name="id" value="${eventId}">
				</form>
				</li>
			</ul>
		</div>
	</div>
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