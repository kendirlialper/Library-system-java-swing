
<%@page import="Database.LSDB"%>
<%@ page import="Database.User" %>
<jsp:useBean id="loginBean" class="Database.User" scope="session"/>
<jsp:setProperty name="loginBean" property="mail"/>
<jsp:setProperty name="loginBean" property="password"/>


<%
    LSDB lsdb = new LSDB();
    User u = lsdb.userControl(loginBean.getMail() , loginBean.getPassword());

    if( u != null){
        session.setAttribute("mail",loginBean.getMail());
        session.setAttribute("TC" ,u.getTC());
        response.sendRedirect("Home.jsp");
    }

   else {
        response.sendRedirect("index.jsp?status=false");
    }



%>
