package car.tp4.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Session Bean implementation class PanierBean
 */
@Stateless
@Local
public class PanierBean {

	@PersistenceContext(unitName = "panier-pu")
	private EntityManager entityManager;

	@EJB
	private BookBean bookBean;

	String idClient;
	List<Book> books = new ArrayList<Book>();


	/**
	 * @param idClient
	 * @param book
	 *
	 * ajouter un livre au panier éléctronique
	 *
	 */
	public void addBook(String idClient, Book book) {
		if(!idClient.equals(idClient)) {
			this.idClient = idClient;
			book.setQte(1);
			books.add(book);
		}
		else {
			boolean found = false;
			for (Book bk : books) {
				if (bk.equals(book)){
					bk.setQte(1 + bk.getQte());
					found = true;
				}
			}
			if(found!=true) {
				this.idClient = idClient;
				book.setQte(1);
				books.add(book);
			}
		}
	}


	/**
	 * @param idClient
	 * @param book
	 *
	 * enlever un livre du panier éléctronique
	 *
	 */
	public void removeBook(String idClient, Book book) {
		int index=-1;
		if(this.idClient.equals(idClient)) {
			for(Book bk: books)
				if(bk.getId()==book.getId())
					index=books.indexOf(bk);
		}
		if(index!=-1) {
			books.remove(index);
		}
	}


	/**
	 * @param idClient
	 *
	 * @return liste des livres ajoutés au panier par le client
	 */
	public List<Book> getSelectedBooks(String idClient) {
		if (idClient.equals(this.idClient))
			return this.books;
		return null;
	}


	/**
	 * @param idClient
	 *
	 * Valider une commande et l'ajouter à la base de données
	 */
	public void addPanier(String idClient) {
		if(this.idClient.equals(idClient)) {
			entityManager.persist(new Panier(idClient, books));
			this.books.clear();
		}
		else
			throw new RuntimeException("erreur de validation de la commande");
	}


	/**
	 * @param idClient
	 *
	 * @return liste des paniers ayant validé par le client
	 */
	public List<Panier> getCommandes(String idClient) {
		Query query = entityManager.createQuery("SELECT p.commande from Panier p WHERE p.idClient=?1");
		query.setParameter(1, idClient);
		return query.getResultList();
	}
}
