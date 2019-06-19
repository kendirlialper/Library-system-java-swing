<%--
  Created by IntelliJ IDEA.
  User: KayademirS
  Date: 19.05.2018
  Time: 14:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import = "java.util.List" %>
<%@ page import="Database.*" %>
<html>
<head>
    <title>Author</title>
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
</head>
<body>
<form action="Author.jsp">
    <h1>Yazarlarımzı</h1>
    <a href="Home.jsp">Anasayfa</a> &nbsp;
    <a href="Categories.jsp">Kategorilerimiz</a> &nbsp;
    <a href="Publisher.jsp">Yayınevlerimiz</a> &nbsp;
    <a href="Author.jsp">Yazarlarımız</a>&nbsp;
    <a href="Search.jsp">Kitap Ara</a>&nbsp;
    <a href="PresentBook.jsp">Mevcud Kitaplar</a>&nbsp;
    <a href="Borrowed.jsp">Ödünç Verilmiş Kitalar</a> &nbsp;
    <a href="User.jsp">Kullanıcı</a>&nbsp;
    <a href="logout.jsp">Çıkış</a>
    <br><br>
    <table id="table_categories" >
        <tr><td>Yazar</td><td>Book</td></tr>
        <%
            LSDB lsdb = new LSDB();
            List <String> author = lsdb.BooksByAouthorList();
            for(int i =0 ; i < author.size() ; ){
        %>

        <tr><td><%=author.get(i)%> <%=author.get(i+1)%></td><td><%=author.get(i+2)%></td></tr>
        <%
                i= i+3; }
        %>
    </table>
</form>
</body>
</html>
