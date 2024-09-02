<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>User 관리 프로젝트</title>
</head>
<body>
  
  <h1>Login</h1>
  <form action="/login" method="post">
    <div>
      ID : <input type="text" name="userId">
    </div>
    <div>
      PW : <input type="text" name="userPw">
    </div>
    <div>
      <button>로그인</button>
      <a href="/signUp">사용자 등록</a>
    </div>
  </form>

  <%-- session에 message가 존재하는 경우 --%>
  <c:if test="${!empty sessionScope.message}" >
    <script>
      alert("${sessionScope.message}")
    </script>

    <%-- session에 존재하는 message 제거 --%>
    <c:remove var="message" scope="session" />
  </c:if>

</body>
</html>