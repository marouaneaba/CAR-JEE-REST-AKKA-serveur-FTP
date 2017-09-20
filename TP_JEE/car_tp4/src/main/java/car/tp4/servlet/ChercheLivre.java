package car.tp4.servlet;

import car.tp4.entity.BookBean;
import car.tp4.entity.PanierBean;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet implementation class AddPanier
 */
@WebServlet("/ChercheLivre")
public class ChercheLivre extends HttpServlet {
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
		String titre = request.getParameter("titre");
		request.setAttribute("books", bookBean.findTitle(titre));
		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher("/jsp/book.jsp");
		dispatcher.forward(request, response);
	}

}
