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
        <h2>Existing books</h2>
        <a href="/Panier">afficher mon panier</a></br>
        <a href="/AuthorsServlet">afficher les auteurs</a></br>
        <a href="/SortBooks">trier les livres par rapport à l'année de parution</a></br>
        <a href="/">ajouter un livre</a></br>
        </br>
        <form method="POST" action="/ChercheLivre">
            Titre à chercher<br/><input type="text" name="titre"/>
            <br/>
            <input type="submit" value="Chercher le livre"/>
        </form>
        <%
            Collection<Book> books = (Collection<Book>) request.getAttribute("books");
            if(books!=null)
                if(((List<Book>)books).size()>0) {
                    out.print("<form method=\"POST\" action=\"/AddPanier\">");
                    for (Book book : books) {
                        out.print(book.toString());
                        out.print("<input type=\"checkbox\" name=\"cbox\" value=\"" + book.getId() + "\"/>");
                        out.print("<br/>");
                }
                out.print("<input type=\"submit\" value=\"ajouter au panier\"/>");
                out.print("</form>");
            }
        %>
    </body>
</html>
