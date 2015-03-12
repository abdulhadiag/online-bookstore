package com.bookstore;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.bookstore.db.AuthorDB;
import com.bookstore.db.BookDB;
import com.bookstore.models.Author;
import com.bookstore.models.Book;
import com.bookstore.models.Genre;
import com.bookstore.db.GenreDB;

/**
 * Servlet implementation class AddBook
 */
public class AddBook extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddBook() {
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
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		Book book = null;
		ArrayList<Author> authors = null;
		ArrayList<Genre> genres = null;
		HashMap<String, String> map = new HashMap<String, String>();
		if (isMultipart) {
			// Configure a repository parameter
			ServletContext context = this.getServletConfig().getServletContext();
			String filePath = context.getInitParameter("file-upload");
			
			// Create a factory for disk-based file items
			DiskFileItemFactory factory = new DiskFileItemFactory();

			// Create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload(factory);

			try{ 
				// Parse the request to get file items.
				List<FileItem> items = upload.parseRequest(request);

				// Process the uploaded file items
				Iterator<FileItem> iter = items.iterator();

				int indx = 1;
				
				while (iter.hasNext ()){
					FileItem fi = iter.next();
					if ( fi.isFormField () ) {
						// Process a regular form field
		            	String otherFieldName = fi.getFieldName();
		            	String otherFieldValue = fi.getString();
		            	map.put(otherFieldName, otherFieldValue);
		            	
		            	System.out.println(otherFieldName + " : " + otherFieldValue);
		            } else {
		            	// Get the uploaded file parameters
						
						String fileName = fi.getName();
						String fieldName = fi.getFieldName();
						long sizeInBytes = fi.getSize();
						
						if(fi.getName() == ""){
							continue;
						}
						// Write the file
						String path = getServletContext().getInitParameter("file-upload");
						File file = new File(path + File.separator + fileName) ;
		            	fi.write( file ) ;
		            	if(!fileName.equals("")){
		            		System.out.println("fileName: " + fileName);
		            	}
		            	map.put("coverImageFile", fileName);
		            }
				}
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		if (!map.isEmpty()) {
			book = populateBook(map);
			authors = AddAuthor.populateAuthor(map);
			genres = AddGenre.populateGenre(map);
		}
		int result = new BookDB().createNewBook(book);
		
		System.out.println(authors.get(0).getAuthorName());
		int authorResult = new AuthorDB().checkAndAdd(authors, book.getIsbn());
		int genreResult = new GenreDB().checkAndAdd(genres, book.getIsbn());
		if (result == 1 && authorResult >= 1 && genreResult >= 1) {
			response.sendRedirect(request.getContextPath() + "/AddBookConfirmation.jsp?success=true");
		} else {
			response.sendRedirect(request.getContextPath() + "/AddBookConfirmation.jsp?success=false");
		}
	}

	private Book populateBook(HashMap<String, String> map) {
		Book book = new Book();
		book.setIsbn(map.get("isbn"));
		book.setTitle(map.get("title"));
		book.setDescription(map.get("description"));
		book.setPublisher(map.get("publisher"));
		book.setPrice(Double.parseDouble(map.get("price")));
		book.setPublishDate(map.get("publishDate"));
		book.setInventory(Integer.parseInt(map.get("inventory")));
		if (map.containsKey("coverImageFile")) {
			book.setCoverImageFile(map.get("coverImageFile"));
		}
		return book;
	}

}
