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
<title>ユーザ登録</title>
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
				data-toggle="dropdown" role="button">
				<span class="glyphicon glyphicon-user" aria-hidden="true"></span>
				<%=loginName%><span
					class="caret"></span></a>
				<ul class="dropdown-menu" role="menu">
					<li><a href="LogoutServlet">ログアウト</a></li>
				</ul></li>
</ul>
	</div>

	<div class="main_content">
	<div class="container">

		<h1>ユーザ登録</h1>
		<%if(request.getAttribute("Caution")!=null){ %>
		<%=request.getAttribute("Caution") %>
		<%} %>
		<form action="AddUserServlet" method="post">

			<label>氏名<font color="red">(必須)</font></label>
			<p>
				<input type="text" name="name" class="form-control" placeholder="氏名"
					required>
			</p>
			<label>ログインID<font color="red">(必須)</font></label>
			<p>
				<input type="text" name="login_id" class="form-control"
					pattern="^[0-9A-Za-z]+$" placeholder="ログインID"  required>
			</p>
			<label>パスワード<font color="red">(必須)</font></label>

			<p>
				<input type="text" name="login_pass" class="form-control"
					pattern="^[0-9A-Za-z]+$" placeholder="パスワード" required>
			</p>

			<div class="form-group">
				<label for="group">所属グループ</label> <select class="form-control"
					name="group_id">
					<option value="1">総務部</option>
					<option value="2">人事部</option>
					<option value="3">技術部</option>
					<option value="4">営業部</option>
					<option value="5">広報部</option>
					<option value="6">経理部</option>
					<option value="7">企画部</option>
				</select>
			</div>
			<a href="ListUserServlet" class="btn btn-default">キャンセル</a>
			<input type="submit" value="登録" class="btn btn-primary">
		</form>

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

