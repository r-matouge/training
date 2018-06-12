<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset='utf-8' />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link href="css/bootstrap.min.css" rel="stylesheet" />
<link href="css/style.css" rel="stylesheet" />
<title>ログアウト</title>
</head>
<body>

	<div class="main_content">
		<div class="container">

			<div class="panel panel-default">
				<div class="panel-heading">
					<font size="5" color="blue" face="Impact">Event Manager</font>
				</div>
			</div>

				<h1>ログアウト</h1>
				<p>ログアウトしました。</p>
				<a href="LoginServlet">ログイン画面に戻る</a>

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