package car.tp4.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import car.tp4.entity.BookBean;
import car.tp4.entity.Book;
import car.tp4.entity.PanierBean;

/**
 * Servlet implementation class AddPanier
 */
@WebServlet("/AddPanier")
public class AddPanier extends HttpServlet {
	@EJB
	private PanierBean panierBean;
	@EJB
	private BookBean bookBean;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String ids[] = request.getParameterValues("cbox");
		HttpSession session = request.getSession();
		if(ids!=null) {
			for (String id_book : ids) {
				if (bookBean.getBook(Long.parseLong(id_book)).getQte() > 0) {
					bookBean.deleteBookOnly(Long.parseLong(id_book), 1);
					panierBean.addBook(session.getId(), bookBean.getBook(Long.parseLong(id_book)));
				} else
					throw new RuntimeException("livre non disponible en stock");
			}
		}
		request.setAttribute("books", panierBean.getSelectedBooks(session.getId()));
		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher("/jsp/panier.jsp");
		dispatcher.forward(request, response);
	}

}
