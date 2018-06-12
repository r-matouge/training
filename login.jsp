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
<title>ログイン</title>
</head>
<body>

	<div class="login">
		<form action="LoginServlet" method="post">

			<div class="main_content">
				<div class="container">

					<div class="panel-heading">
						<font size="5" color="blue" face="Impact">Event Manager</font>
					</div>

					<div class="panel-body">
						<p>
							<input type="text" class="form-control" name="loginId"
								pattern="^[0-9A-Za-z]+$" placeholder="ログインID" required>
						</p>
						<p>
							<input type="password" class="form-control" name="loginPass"
								placeholder="パスワード" required>
						</p>
						<p>
							<input type="submit" value="ログイン"
								class="btn btn-primary btn-lg btn-block" />
						</p>
					</div>
				</div>
			</div>
		</form>
	</div>


</body>
</html>