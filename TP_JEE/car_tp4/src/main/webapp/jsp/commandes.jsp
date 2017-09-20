<%@page import="java.util.Collection"%>
<%@ page import="java.util.List" %>
<%@ page import="car.tp4.entity.Panier" %>
<%@ page import="car.tp4.entity.Book" %>
<%@page contentType="text/html" pageEncoding="iso-8859-1"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <title>Home</title>
    </head>
    <body>
        <h2>Les commandes récentes</h2>
        <a href="/books">afficher tous les livres</a>
        <%
            if(request.getAttribute("commandes") != null )
                if(((List<Panier>) request.getAttribute("commandes")).size()>0){
                    List<Panier> commandes = (List<Panier>) request.getAttribute("commandes");
                    for (Panier p : commandes) {
                        out.print("<br/>");
                        out.print(p);
                        out.print(p.getCommande().get(0).getAuthor());
                        out.print("<br/>");
                    }
            }
            else
                out.print("Vous n'avez pas encore passé des commandes");
        %>
    </body>
</html>
