

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mongodb.MongoClient;
/**
 * Servlet implementation class DeleteIssueServlet
 */
@WebServlet("/DeleteIssueServlet")
public class DeleteIssueServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteIssueServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		 String id = request.getParameter("id");
	        if (id == null || "".equals(id)) {
	            throw new ServletException("id missing for delete operation");
	        }
	        MongoClient mongo = (MongoClient) request.getServletContext()
	                .getAttribute("MONGO_CLIENT");
	        MongoDBIssueDAO issueDAO = new MongoDBIssueDAO(mongo);
	        Issue p = new Issue();
	        p.setId(id);
	        issueDAO.deleteIssue(p);
	        System.out.println("Issue deleted successfully with id=" + id);
	        request.setAttribute("success", "Issue deleted successfully");
	        List<Issue> Issues = issueDAO.readAllIssue();
	        request.setAttribute("Issues", Issues);
	 
	        RequestDispatcher rd = getServletContext().getRequestDispatcher(
	                "/IssueTrackerNew.jsp");
	        rd.forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	

}
