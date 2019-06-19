<%--
  Created by IntelliJ IDEA.
  User: KayademirS
  Date: 12.05.2018
  Time: 19:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import = "java.util.List" %>
<%@ page import="Database.*" %>
<html>
<head>
    <style>
        table, th, td {
            border: 1px solid black;
            border-collapse: collapse;
            background: mediumslateblue;
            width: 500px;
        }
        th, td {
            padding: 10px;
            text-align: left;
        }
    </style>
    <title>Home</title>
</head>
<body>
<form name="form_home" method="post">
<%
    String email=(String)session.getAttribute("mail");

    //redirect user to login page if not logged in
    if(email==null){
        response.sendRedirect("index.jsp");
    }
%>

<h1> Kütüphanemiz</h1>
    <a href="Home.jsp">Anasayfa</a>&nbsp;
    <a href="Categories.jsp">Kategorilerimiz</a>&nbsp;
    <a href="Publisher.jsp">Yayınevlerimiz</a>&nbsp;
    <a href="Author.jsp">Yazarlarımız</a>&nbsp;
    <a href="Search.jsp">Kitap Ara</a>&nbsp;
    <a href="PresentBook.jsp">Mevcud Kitaplar</a>&nbsp;
    <a href="Borrowed.jsp">Ödünç Verilmiş Kitalar</a>&nbsp;
    <a href="User.jsp">Kullanıcı</a>&nbsp;
    <a href="logout.jsp">Çıkış</a>
    <br><br>
<table  id="table_name" >
    <tr><td>Book</td><td>Categories</td><td>Publisher</td></tr>
        <%
            LSDB lsdb = new LSDB();
            List<String> books = lsdb.booksListAll();

   for(int i =0 ; i < books.size() ; ){
    %>

    <tr><td><%=books.get(i)%></td><td><%=books.get(i+1)%></td><td><%=books.get(i+2)%></td></tr>
    <%
            i= i+3; }
    %>
</table>
</form>

</body>
</html>
