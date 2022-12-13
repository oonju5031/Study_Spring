<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="hello.servlet.domain.member.Member" %>
<%@ page import="hello.servlet.domain.member.MemberRepository" %>

<%  /**
    hello/servlet/web/servlet/MemberSaveServlet.java와 동일
    request, response는 jsp에서 자동으로 생성해 준다.
    */
    MemberRepository memberRepository = MemberRepository.getInstance();

    System.out.println("MemberSaveServlet.service");

    // request의 form값을 읽음: GET query string, HTML POST form 둘 다 getParameter() 사용
    String username = request.getParameter("username");
    int age = Integer.parseInt(request.getParameter("age"));// getParameter()는 항상 string이므로 int로 변환하여야 함

    // 읽은 정보를 MemberRepository에 저장
    Member member = new Member(username, age);
    memberRepository.save(member);
%>
<html>
<head>
    <title>가입 결과(JSP)</title>
    <!-- 가입 결과를 출력하는 JSP -->
</head>
<body>
성공
<ul>
    <li>id=<%=member.getId()%></li>
    <li>username=<%=member.getUsername()%></li>
    <li>age=<%=member.getAge()%></li>
</ul>
<a href="/index.html">메인</a>
</body>
</html>
