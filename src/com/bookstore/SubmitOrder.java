package com.bookstore;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bookstore.db.LineItemDB;
import com.bookstore.db.TransactionDB;
import com.bookstore.models.Cart;
import com.bookstore.models.LineItem;
import com.bookstore.models.Transaction;

/**
 * Servlet implementation class SubmitOrder
 */
public class SubmitOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubmitOrder() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Cart cart = (Cart) session.getAttribute("cart");
		String userStr = (String) session.getAttribute("user");
		
		// Check if logged in
		if (userStr == null) {
			// Not logged in, redirect
			response.sendRedirect("./SignUp.jsp?checkErr");
			return;
		}
		
		if (cart == null) {
			response.sendError(404);
			return;
		}
		
		ArrayList<LineItem> lineItems = cart.getItems();
		
		// Ensure enough inventory
		boolean enoughInventory = true;
		for (int i = 0; i < lineItems.size(); i++) {
			if (!lineItems.get(i).enoughInventory()) {
				enoughInventory = false;
				cart.remove(lineItems.get(i));
			}
		}
		if (!enoughInventory) {
			response.sendRedirect("./cartUpdate?quanErr");
			return;
		}
		
		// Ready to commit order, okay to update book inventories
		for (int i = 0; i < lineItems.size(); i++) {
			lineItems.get(i).commitInventoryDecrement();
		}
		
		// Create transaction and commit to db
		Transaction transaction = new Transaction();
		transaction.setTotalPrice(calculateTotalPrice(cart));
		transaction.setUserName(userStr);
		transaction.setIsProcessed(1);
		int transactionId = new TransactionDB().insertTransaction(transaction);
		
		// Update Line items and commit to db
		for (LineItem item : lineItems) {
			item.setTransactionId(transactionId);
		}
		new LineItemDB().insertLineItems(lineItems);
		cart.removeAllItems();
		
		request.setAttribute("confirmId", transactionId);
		
		RequestDispatcher dispatcher =
                getServletContext().getRequestDispatcher("/OrderConfirmation.jsp");
        dispatcher.forward(request, response);
	}

	private double calculateTotalPrice(Cart cart) {
		double totalPrice = 0;
		for (LineItem item : cart.getItems()) {
			totalPrice += item.getTotalPriceDouble();
		}
		return totalPrice;
	}

}
