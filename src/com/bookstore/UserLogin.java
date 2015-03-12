package com.bookstore;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bookstore.db.UserDB;
import com.bookstore.models.User;
import com.bookstore.util.Hasher;

/**
 * Servlet implementation class UserLogin
 */
public class UserLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserLogin() {
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
		String usernameIn = request.getParameter("username");
		String passwordIn = request.getParameter("passwd");
		if (usernameIn == "" || passwordIn == "") {
			System.out.println("Username or password empty!");
			response.sendRedirect(request.getContextPath() + "/loginError.jsp?errFlag=missing");
			return;
		}
		User user = new UserDB().selectUser(usernameIn);
		if (user == null) {
			System.out.println("Username not known");
			response.sendRedirect(request.getContextPath() + "/loginError.jsp?errFlag=inv");
			return;
		}
		
		// Made it this far, user is valid. Now compare passwords.
		String hashed = Hasher.getHash(passwordIn);
		if (hashed.equals(user.getPasswd())) {
			request.getSession().setAttribute("user", usernameIn);
		} else {
			System.out.println("Password incorrect");
			response.sendRedirect(request.getContextPath() + "/loginError.jsp?errFlag=inv");
			return;
		}
		System.out.println("getRequestURL: " + request.getRequestURL().toString());
		System.out.println("getServletPath: " + request.getServletPath());
		System.out.println("getContextPath: " + request.getContextPath());
		response.sendRedirect(request.getContextPath() + "/");
	}

}
