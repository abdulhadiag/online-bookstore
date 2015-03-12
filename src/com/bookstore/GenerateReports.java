package com.bookstore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bookstore.db.ReportsDB;

/**
 * Servlet implementation class GenerateReports
 */
public class GenerateReports extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GenerateReports() {
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
		ReportsDB reportDB = new ReportsDB();
		// Weekly sales
		ArrayList<ArrayList<String>> weeklyReportRows = reportDB.getWeeklySales();
		request.setAttribute("weeklySales", weeklyReportRows);
		
		// Monthly sales
		ArrayList<ArrayList<String>> monthlyReportRows = reportDB.getMonthlySales();
		request.setAttribute("monthlySales", monthlyReportRows);
		
		// Top 10 books by week
		HashMap<String, ArrayList<ArrayList<String>>> topTenByWeek = reportDB.getWeeklyTopTen();
		request.setAttribute("top10Weekly", topTenByWeek);
		
		// Top 5 books per genre by week
		HashMap<String, HashMap<String, ArrayList<ArrayList<String>>>> topFiveByGenre = reportDB.getWeeklyTopFiveByGenre();
		request.setAttribute("top5ByGenreWeekly", topFiveByGenre);
		
		// Top reviewed biweekly
		HashMap<String, ArrayList<ArrayList<String>>> topReviews = reportDB.getBiweeklyTopRated();
		request.setAttribute("topRated", topReviews);
		
		// Direct Marketing report, customers who bought at least 2 books in one genre in one month
		HashMap<String, ArrayList<ArrayList<String>>> directMarketing = reportDB.getDirectMarketingReport();
		request.setAttribute("directMarketing", directMarketing);
		
		RequestDispatcher dispatcher =
                getServletContext().getRequestDispatcher("/Reports.jsp");
        dispatcher.forward(request, response);
	}

}
