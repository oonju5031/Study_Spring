<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>회원 가입(JSP)</title>
    <!-- 회원 정보를 입력하는 JSP -->
</head>
<body>
<!-- /basic/hello-form.html과 동일한 내용이나 action 경로만 바꾸어 준다. -->
<form action="/jsp/members/save.jsp" method="post">
    username: <input type="text" name="username" />
    age:      <input type="text" name="age" />
    <button type="submit">전송</button>
</form>
</body>
</html>
