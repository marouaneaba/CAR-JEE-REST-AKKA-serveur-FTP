package car.tp4.entity;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

@Stateless
@Local
public class BookBean {

	@PersistenceContext(unitName = "book-pu")
	private EntityManager entityManager;

    /**
     * Initialiser la base de données avec 5 livres.
     *
     */
	public void init() {
		addBook("Harry Potter", "J.K Rowling", "1997", 1);
		addBook("Le seigneur des anneaux", "J.R.R Tolkien", "1954", 1);
		addBook("Bilbo le hobbit", "J.R.R Tolkien", "1937", 1);
		addBook("La cité des ténèbres", "Cassandra Clare", "2007", 1);
		addBook("Uglies", "Scott Westerfeld", "2005", 1);
	}

	/**
	 * @param titre
	 * @param auteur
	 * @param annee
	 * @param qte
     * Ajouter un livre dans la base de données ou mettre à jour sa quantité .
     *
     */
	public void addBook(String titre, String auteur, String annee, int qte) {
		Query query = entityManager.createQuery("SELECT m FROM Book m WHERE m.title=?1 AND m.author=?2 AND m.yearPar=?3");
		query.setParameter(1, titre);
		query.setParameter(2, auteur);
		query.setParameter(3, annee);
		List<Book> books = query.getResultList();
		if(books!=null) {
            if (books.size() > 0) {
                Query queryUpdate = entityManager.createQuery("UPDATE Book m set m.qte=m.qte+?4 WHERE m.title=?1 AND m.author=?2 AND m.yearPar=?3");
                queryUpdate.setParameter(1, titre);
                queryUpdate.setParameter(2, auteur);
                queryUpdate.setParameter(3, annee);
                queryUpdate.setParameter(4, qte);
                queryUpdate.executeUpdate();
            } else {
                entityManager.persist(new Book(titre, auteur, annee, qte));
            }
        }
    }

	/**
	 * @return liste des auteurs ayant au moins un livre dans la base de données.
	 */
	@SuppressWarnings("unchecked")
	public List<String> listAllAuthors() {
		Query query = entityManager.createQuery("SELECT DISTINCT b.author from Book b");
		return query.getResultList();
	}

	/**
	 *
	 * @return la liste de tous les livres existants dans la base de données.
	 */
	@SuppressWarnings("unchecked")
	public List<Book> getAllBooks() {
		Query query = entityManager.createQuery("SELECT m from Book as m");
		return query.getResultList();
	}

    /**
     * @param ID
     *
     * @return un livre à partir de son ID
     */
	public Book getBook(long ID) {
		Book b = entityManager.find(Book.class, ID);
		if (b == null)
			throw new RuntimeException("livre introuvable");
		return b;
	}

    /**
     * @param id
     * @param number
     *
     * @return décrémenter la quantité d'un livre de number à partir de son id
     */
    public void deleteBookOnly(long id, int number) {
        Query queryUpdate = entityManager.createQuery("UPDATE Book m SET m.qte=m.qte-?1 WHERE m.id=?2 AND m.qte>=1");
        queryUpdate.setParameter(1, number);
        queryUpdate.setParameter(2, id);
        queryUpdate.executeUpdate();
    }

    /**
     * @param id
     * @param number
     *
     * @return incrémenter la quantité d'un livre de number à partir de son id
     */
    public void addBookOnly(long id, int number) {
        Query queryUpdate = entityManager.createQuery("UPDATE Book m SET m.qte=m.qte+?1 WHERE m.id=?2 AND m.qte>=0");
        queryUpdate.setParameter(1, number);
        queryUpdate.setParameter(2, id);
        queryUpdate.executeUpdate();
    }

    /**
     * @return liste des livres triés par rapport à leurs date de parution
     */
	public List<Book> getSortedBooksDesc() {
        Query query = entityManager.createQuery("SELECT m from Book as m ORDER BY m.yearPar DESC ");
        return query.getResultList();
    }

    /**
     * @param title
     *
     * @return liste des livres ayant une apparence de title sur leurs titres
     */
	public List<Book> findTitle(String title) {
        Query query = entityManager.createQuery("SELECT m from Book m");
        List<Book> findedBookList = query.getResultList();
        ArrayList<Book> findedBookTitle = new ArrayList<Book>();
        if (title != null) {
            String titleSearch = Normalizer.normalize(title.toLowerCase(), Normalizer.Form.NFD).replaceAll("\\s", "").replaceAll("[^\\p{ASCII}]", "");
            System.out.println(titleSearch);
            for (Book book : findedBookList) {
                if (Normalizer.normalize(book.getTitle().toLowerCase(), Normalizer.Form.NFD).replaceAll("\\s", "").replaceAll("[^\\p{ASCII}]", "").contains(titleSearch)) {
                    System.out.println("trouvé");
                    findedBookTitle.add(book);
                }
            }
        }
		return findedBookTitle;
	}
}