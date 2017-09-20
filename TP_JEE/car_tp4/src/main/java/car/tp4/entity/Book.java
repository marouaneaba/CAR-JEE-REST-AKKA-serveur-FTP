package car.tp4.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Book implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String author;
	private String title;
	private String yearPar;
	private int qte;

	public Book(String title, String author, String date, int qte) {
		this.author = author;
		this.title = title;
		this.yearPar = date;
		this.qte=qte;
	}

	public Book() {}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getYear() {
		return yearPar;
	}

	public void setYear(String date) {
		this.yearPar = date;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getQte() {
		return qte;
	}

	public void setQte(int qte) {
		this.qte = qte;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Book book = (Book) o;

		if (id != book.id)
			return false;
		if (!author.equals(book.author))
			return false;
		if (!yearPar.equals(book.yearPar))
			return false;
		return title.equals(book.title);
	}

	@Override
	public String toString() {
		return "Author='" + author + '\'' + ", Title='" + title
				+ '\'' + ", Date='" + yearPar + '\'' + ", Qte Restante =" + qte;
	}
}
