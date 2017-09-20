<%@page import="car.tp4.entity.Book"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="iso-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    <title>Home</title>
</head>
<body>
<h2>Existing Authors</h2>
<a href="/Panier">afficher mon panier</a>
<a href="/books">afficher les livres</a>
<%
    List<String> authors = (List<String>)request.getAttribute("authors");
    for(String auteur : authors){
        out.println("<h3>"+ auteur +"</h3>");
    }
%>
</body>
</html>
