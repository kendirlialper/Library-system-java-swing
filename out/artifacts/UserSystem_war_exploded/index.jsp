<%--
  Created by IntelliJ IDEA.
  User: KayademirS
  Date: 12.05.2018
  Time: 19:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
  <head>
    <title>Login</title>
  </head>
  <body>

     <%
       String email=(String)session.getAttribute("mail");

       //redirect user to home page if already logged in
       if(email!=null){
         response.sendRedirect("Home.jsp");
       }

       String status=request.getParameter("status");

       if(status!=null){
         if(status.equals("false")){
           out.print("Hatalı bilgi girdiz tekrar deneyiniz!");
         }
         else{
           out.print("Sisteme Bağlanılamıyor! Daha sonra deneyiniz.");
         }
       }
     %>
     <form action="loginRequestHandler.jsp">
       <table cellpadding="5">
         <tr>
           <td><b>Email:</b></td>
           <td><input type="text" name="mail" required/></td>
         </tr>

         <tr>
           <td><b>Password:</b></td>
           <td><input type="password" name="password" required/></td>
         </tr>

         <tr>
           <td colspan="2" align="center"><input type="submit" value="Login"/></td>
         </tr>

       </table>
   </form>
  </body>
</html>
