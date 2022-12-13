<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- JSTL(JavaServer Pages Standard Tag Library; 자바서버 페이지 표준 태그 라이브러리): 특별한 기능을 가진 Tag들을 제공한다. --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>회원 목록(MVC)</title>
    <%-- MVC Pattern: 회원 목록을 일괄 출력하는 view --%>
</head>
<body>
<a href="/index.html">메인</a>
<table>
    <thead>
    <th>id</th>
    <th>username</th>
    <th>age</th>
    </thead>
    <tbody>
        <c:forEach var="item" items="${members}">  <%-- for (Member item : members) {...}와 동일한 기능을 수행한다. --%>
            <tr>
                <td>${item.id}</td>
                <td>${item.username}</td>
                <td>${item.age}</td>
            </tr>
        </c:forEach>
    </tbody>
</table>
</body>
</html>
