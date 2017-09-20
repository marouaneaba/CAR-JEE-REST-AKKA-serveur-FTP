package car.tp4.servlet;

import car.tp4.entity.Book;
import car.tp4.entity.BookBean;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.lang.Integer.parseInt;

@WebServlet("/books")
public class BookServlet extends HttpServlet {
	@EJB
	private BookBean bookBean;

	@Override
	public void init() throws ServletException {
		bookBean.init();
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String titre = request.getParameter("titre");
		String auteur = request.getParameter("auteur");
		String parution = request.getParameter("annee");
		int qte = parseInt(request.getParameter("qte"));
		bookBean.addBook(titre, auteur, parution,qte);
		request.setAttribute("books", bookBean.getAllBooks());
		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher("/jsp/book.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		request.setAttribute("books", bookBean.getAllBooks());
		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher("/jsp/book.jsp");
		dispatcher.forward(request, response);
	}

}
