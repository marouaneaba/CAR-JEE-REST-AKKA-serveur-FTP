<%@page import="car.tp4.entity.Book"%>
<%@page import="java.util.Collection"%>
<%@ page import="java.util.List" %>
<%@page contentType="text/html" pageEncoding="iso-8859-1"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <title>Home</title>
    </head>
    <body>
        <h2>Les livres du panier</h2>
        <a href="/books">afficher tous les livres</a>
        <%
            if(request.getAttribute("books") != null ) {
                if (((List<Book>) request.getAttribute("books")).size() > 0) {
                    Collection<Book> books = (Collection<Book>) request.getAttribute("books");
                    out.print("<form method=\"POST\" action=\"/retirerPanier\">");
                    for (Book book : books) {
                        out.print(book.toString());
                        out.print("<input type=\"checkbox\" name=\"cbox\" value=\"" + book.getId() + "\"/>");
                        out.print("<br/>");
                    }
                    out.print("<input type=\"submit\" value=\"Supprimer du panier\">");
                    out.print("<br/><a href=\"/ValiderPanier\">ValiderPanier</a>\n");
                    out.print("</form>");
                }
            }
            else
                out.print("Vous n'avez pas encore selectionner des livres");
        %>
    </body>
</html>
