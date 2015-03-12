package com.bookstore;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bookstore.db.ReviewDB;
import com.bookstore.models.Review;

/**
 * Servlet implementation class SubmitReview
 */
public class SubmitReview extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubmitReview() {
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
		// Get new data
		int rating = Integer.parseInt(request.getParameter("rating"));
		String reviewText = request.getParameter("review-text");
		String isbn = request.getParameter("isbn");
		String userName = (String) request.getSession().getAttribute("user");
		
		// Populate a review object
		Review review = new Review();
		review.setISBN(isbn);
		review.setRating(rating);
		review.setReviewText(reviewText);
		review.setUserName(userName);
		
		// Commit to DB and redirect back to the book listing
		new ReviewDB().registerReview(review);
		response.sendRedirect("/BookLookup?isbn=" + isbn);
		return;
	}

}
