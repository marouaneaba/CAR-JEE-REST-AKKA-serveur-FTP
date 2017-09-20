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
 * Created by a1h on 30/04/2017.
 */
@WebServlet("/ValiderPanier")

public class ValiderPanier extends HttpServlet {
    @EJB
    private PanierBean panierBean;
    @EJB
    private BookBean bookBean;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        panierBean.addPanier(session.getId());
        request.setAttribute("books", bookBean.getAllBooks());
        RequestDispatcher dispatcher = getServletContext()
                .getRequestDispatcher("/jsp/book.jsp");
        dispatcher.forward(request, response);
    }
}
