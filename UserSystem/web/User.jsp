
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import = "java.util.List" %>
<%@ page import="Database.*" %>
<jsp:useBean id="loginBean" class="Database.User" scope="session"/>
<jsp:setProperty name="loginBean" property="mail"/>
<jsp:setProperty name="loginBean" property="password"/>
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
    <title>User</title>
</head>
<body>
<form action="User.jsp">
<h1>Kullanıcı</h1>
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
    <table id="table_user" >
        <tr><td>Kitap</td><td>Aldığınız Tarih</td></tr>
    <%
     //   String TC = (String) session.getAttribute("TC");
        LSDB lsdb = new LSDB();
        User u = lsdb.userControl(loginBean.getMail() , loginBean.getPassword());
        List<String> list = lsdb.booksListUser(u.getTC());
       if ( list != null){
        for(int i =0 ; i < list.size() ; ){
    %>

    <tr><td><%=list.get(i)%></td><td><%=list.get(i+3)%></td></tr>
    <%
            i= i+4; }
       }
       else
           out.println(" Ödünç aldığınız kitap bulunmamaktadır! ");
    %>

    </table>

</form>
</body>
</html>
