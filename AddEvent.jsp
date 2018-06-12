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

<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="js/jquery-ui-timepicker-addon.js"></script>
<script src="js/jquery-ui-timepicker-ja.js"></script>
<link rel="stylesheet" href="css/jquery-ui-timepicker-addon.css">

<script src="js/bootstrap.min.js"></script>
<link href="css/bootstrap.min.css" rel="stylesheet" />
<link href="css/style.css" rel="stylesheet" />
<title>新規イベント登録</title>

<script>

	$(function() {

		$("#datetimepicker1").datetimepicker({

			showOn : "button",
			dateFormat : "yy-mm-dd",
			hourGrid : 4,
			minuteGrid : 10,
			stepHour : 1,
			stepMinute : 5,
		});
	});

	$(function() {
		$("#datetimepicker2").datetimepicker({

			showOn : "button",
			dateFormat : "yy-mm-dd",
			hourGrid : 4,
			minuteGrid : 10,
			stepHour : 1,
			stepMinute : 5,
		});
	});
</script>
</head>
<body>

		<div class="navbar navbar-fixed-top color navbar-default"
			id="mynavbar">
			<ul class="nav navbar-nav">
				<li class="active" id="logo"><font size="5" color="blue"
					face="Impact">Event Manager</font></li>
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

				<h1>イベント登録</h1>
				<form action="AddEventServlet" method="post">

					<label>タイトル<font color="red">(必須)</font></label>
					<p>
						<input type="text" class="form-control" name="title" required>
					</p>


					<label>開始日時<font color="red">(必須)</font></label>
					<p>
						<input type="text" class="form-control" name="start"
							id="datetimepicker1" placeholder="0000-00-00 00:00" required>
					</p>

					<label>終了日時</label>
					<p>
						<input type="text" class="form-control" name="end"
							id="datetimepicker2" placeholder="0000-00-00 00:00">
					</p>

					<label>場所<font color="red">(必須)</font></label>
					<p>
						<input type="text" class="form-control" name="place" required>
					</p>

					<div class="form-group">
						<label>対象グループ</label> <select class="form-control" name="group_id">
							<option value="1">総務部</option>
							<option value="2">人事部</option>
							<option value="3">技術部</option>
							<option value="4">営業部</option>
							<option value="5">広報部</option>
							<option value="6">経理部</option>
							<option value="7">企画部</option>
							<option value="8">全員</option>
						</select>
					</div>
					<label>詳細</label>
					<p>
						<textarea class="form-control" name="detail" rows="8" cols="45"></textarea>
					</p>

					<a href="ListEventServlet" class="btn btn-default">キャンセル</a> <input
						type="submit" value="登録" class="btn btn-primary" />
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