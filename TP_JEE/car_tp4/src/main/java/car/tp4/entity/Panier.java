package car.tp4.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Panier implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String idClient;
	private List<Book> commande;

	public Panier(String idClient, List<Book> commande) {
		this.idClient = idClient;
		this.commande = commande;
	}

	public Panier() {
		commande = new ArrayList<Book>();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getIdClient() {
		return idClient;
	}

	public void setIdClient(String idClient) {
		this.idClient = idClient;
	}

	public List<Book> getCommande() {
		return commande;
	}

	public void setCommande(List<Book> commande) {
		this.commande = commande;
	}
	
	public void addBook(Book b){
		this.commande.add(b);
	}


	@Override
	public String toString() {
		String str = new String("idClient : "+idClient);
		for(Book b : commande)
			str+=" "+b.toString()+" \n";
		return str;
	}
}
