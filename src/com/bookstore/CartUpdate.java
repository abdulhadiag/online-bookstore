package com.bookstore;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bookstore.models.Cart;
import com.bookstore.models.LineItem;

/**
 * Servlet implementation class DisplayServlet
 */
public class CartUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CartUpdate() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Items specific to single item updates
		String quantityStr = request.getParameter("quantity");
		String isbn = request.getParameter("isbn");
		String title = request.getParameter("title");
		
		String removeButtonVal = request.getParameter("remove");
		String addButtonVal = request.getParameter("add");
		String quantityError = request.getParameter("quanErr");
		
		HttpSession session = request.getSession();
		
		Cart cart = (Cart) session.getAttribute("cart");  
        if (cart == null) {
            cart = new Cart();
        } 
        
        if (quantityError == null) {
			// Parse the quantity from the client, and determine if a removal
			int quantity;
			if (addButtonVal != null) {
				quantity = 1;
			} else if (removeButtonVal != null) {
				quantity = 0;
			} else {
				quantity = Integer.parseInt(quantityStr);
			}
			// Add new book, update quantity
			LineItem lineItem = new LineItem();
			lineItem.setIsbn(isbn);
			lineItem.setQuantity(quantity);
			if (title != null) {
				lineItem.setTitle(title);
			}
			cart.add(lineItem);
			if (quantity > 0) {
				cart.add(lineItem);
			} else {
				cart.remove(lineItem);
			}
		}
		session.setAttribute("cart", cart);
        RequestDispatcher dispatcher =
                getServletContext().getRequestDispatcher("/Checkout.jsp");
        dispatcher.forward(request, response);
	}

}
