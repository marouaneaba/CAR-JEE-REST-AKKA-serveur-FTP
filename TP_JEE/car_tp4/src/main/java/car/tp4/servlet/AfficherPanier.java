package car.tp4.servlet;

import java.io.IOException;
import java.io.PrintWriter;
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
 * Servlet implementation class AfficherPanier
 */
@WebServlet("/Panier")
public class AfficherPanier extends HttpServlet {
	@EJB
	private PanierBean panierBean;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		request.setAttribute("books", panierBean.getSelectedBooks(session.getId()));
		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher("/jsp/panier.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

}
