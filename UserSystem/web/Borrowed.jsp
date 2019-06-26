
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import = "java.util.List" %>
<%@ page import="Database.*" %>
<html>
<head>
    <style>
        table, th, td {
            border: 1px solid black;
            border-collapse: collapse;
            width: 500px;
        }
        th, td {
            padding: 10px;
            text-align: left;
        }
    </style>
    <title>Borrowed Books</title>
</head>
<body>

<form action="Borrowed.jsp">
<h1>Ödünç Verilen Kitaplar</h1>

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
<table  id="table_false" bgcolor="#7b68ee">
    <tr><td>Name</td></tr>
    <%
        LSDB lsdb = new LSDB();
        List <Books> book_false = lsdb.booksList(false);
        for(int i =0 ; i < book_false.size() ; i++){
    %>
    <tr><td><%=book_false.get(i).getName()%></td></tr>
    <%
        }
    %>
</table>
</form>
</body>
</html>
