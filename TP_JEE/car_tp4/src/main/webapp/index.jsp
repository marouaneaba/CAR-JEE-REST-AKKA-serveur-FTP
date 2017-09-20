<!DOCTYPE html>
<html>
    <head>
        <title>Ajouter un livre</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <div>
            <center>
                <h2>Ajouter un livre :</h2>
            </center>
        </div> 
           
        <div>
            <form method="POST" action="jsp/addbook.jsp">
                Titre<br/><input type="text" name="titre"/>
                <br/>
                Nom de l'auteur<br/><input type="text" name="auteur"/>
                <br/>
                Année de parution<br/><input type="text" name="annee"/>
                <br/>
                Quantité<br/><input type="text" name="qte"/>
                <br/>
                <input type="submit" value="Afficher"/>
            </form>
            <a href="/books">Afficher les livres</a>
        </div>
    </body>
</html>