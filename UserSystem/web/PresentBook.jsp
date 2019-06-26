
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
    <title>Peresent Books</title>
</head>
<body>
<form onsubmit="PresentBook.jsp">
<h1>Mevcut Kitaplar</h1>
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
<table  id="table_true" >
    <tr><td>Name</td></tr>
    <%
        LSDB lsdb = new LSDB();
        List <Books> book_true = lsdb.booksList(true);
        for(int i =0 ; i < book_true.size() ; i++){
    %>
    <tr><td><%=book_true.get(i).getName()%></td></tr>
    <%
        }
    %>
</table>
</form>
</body>
</html>
