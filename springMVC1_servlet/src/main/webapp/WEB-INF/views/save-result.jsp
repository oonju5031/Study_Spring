<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>가입 결과(MVC View)</title>
    <%-- MVC Pattern: 가입 결과를 출력하는 view --%>
</head>
<body>
성공
<ul>
    <%--방법 1. getAttribute() 사용: getAttribute()로 반환된 값은 Object이므로 Member으로 캐스팅해야 한다.
    <li>id=<%=((Member)request.getAttribute("member")).getId()%></li>
    <li>username=<%=((Member)request.getAttribute("member")).getUsername()%></li>
    <li>age=<%=((Member)request.getAttribute("member")).getAge()%></li>
    --%>

    <%--방법 2. Property 접근법: Java Bean에서 유효하며, 자동으로 getId(), getUsername(), getAge()를 호출한다.
        Java Bean은 데이터를 이름에 해당하는 property(=member)와 실체에 해당하는 field(=member.id, member.username, member.age}로 구성된다.
     --%>
    <li>id=${member.id}</li>
    <li>username=${member.username}</li>
    <li>age=${member.age}</li>
</ul>
<a href="/index.html">메인</a>
</body>
</html>
