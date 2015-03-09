<%@page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
<title>Insert title here</title>
</head>
<body>
<p> Sab7 sab7 ya 3am el 7ag " ${it.email} "</p>

  <strong>Send Friend Request </strong>
  <form action="/social/SendFriendRequest" method="post">
  Email : <input type="text" name="recevierEmail" /> <br>
  <input type="hidden" name="senderEmail" value="${it.email}">
  <input type="submit" value="send">
  </form>
  
  
  
  <form action="/social/addAllFriendRequests/" method="post">
  <input type="hidden" name="recevierEmail" value="${it.email}">
  <strong>Add all friends requests : </strong>  <input type="submit" value="Add all now">
  </form>
  <br>
  <a href="http://localhost:8888/social/signup">signup</a> <br>
  

</body>
</html>