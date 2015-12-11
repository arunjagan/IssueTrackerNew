

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
 * Servlet implementation class AddIssueServlet
 */
@WebServlet("/AddIssueServlet")
public class AddIssueServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddIssueServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		System.out.println("inside add servlet");
		
		 String description = request.getParameter("description");
	        String status = request.getParameter("status");
	        String severity = request.getParameter("severity");
	        
	        System.out.println(description);
	        System.out.println(status);
	        System.out.println(severity);
	        
	        if ((description == null || description.equals(""))
	                || (status == null || status.equals("")) || (severity == null || severity.equals("")) ) {
	            request.setAttribute("error", "Mandatory Parameters Missing");
	            RequestDispatcher rd = getServletContext().getRequestDispatcher(
	                    "/IssueTrackerNew.jsp");
	            rd.forward(request, response);
	        } else {
	            Issue p = new Issue();
	            p.setStatus(status);
	            p.setDescription(description);
	            p.setSeverity(severity);
	            MongoClient mongo = (MongoClient) request.getServletContext()
	                    .getAttribute("MONGO_CLIENT");
	            MongoDBIssueDAO issueDAO = new MongoDBIssueDAO(mongo);
	            issueDAO.createIssue(p);
	            System.out.println("Issue Added Successfully with id="+p.getId());
	            request.setAttribute("success", "Issue Added Successfully");
	            List<Issue> Issues = issueDAO.readAllIssue();
	            request.setAttribute("Issues", Issues);
	 
	            RequestDispatcher rd = getServletContext().getRequestDispatcher(
	                    "/IssueTrackerNew.jsp");
	            rd.forward(request, response);
		
	}
	}
}
