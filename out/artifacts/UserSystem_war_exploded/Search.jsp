<%--
  Created by IntelliJ IDEA.
  User: KayademirS
  Date: 13.05.2018
  Time: 13:46
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
    <title>Search</title>
</head>
<body>
<form onsubmit="Search.jsp">
<h1>Hangi Kitabı Aramıstınız ?</h1><br>
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
<input type="text" name="searchtxt"> <input type="submit" value="Ara" >
<br><br>
<table  id="table_search"  >

    <%
        String search = null;
        search = request.getParameter("searchtxt");
        if (search != null){
            LSDB lsdbsearch = new LSDB();
            List<String> booklist = lsdbsearch.booksListSearch(search);
    %>
    <tr><td>Name</td><td>Categories</td><td>Publisher</td></tr>
    <%
        for(int i =0 ; i < booklist.size() ;  ){
    %>

    <tr><td><%=booklist.get(i)%></td><td><%=booklist.get(i+1)%></td><td><%=booklist.get(i+2)%></td></tr>

    <%
                i = i+3; }
        }

    %>
</table>
</form>
</body>
</html>
