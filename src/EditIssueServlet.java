

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
 * Servlet implementation class EditIssueServlet
 */
@WebServlet("/EditIssueServlet")
public class EditIssueServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditIssueServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		System.out.println("Inside edit");
		
		
		
		String id = request.getParameter("id");
        if (id == null || "".equals(id)) {
            throw new ServletException("id missing for edit operation");
        }
        System.out.println("Issue edit requested with id=" + id);
        MongoClient mongo = (MongoClient) request.getServletContext()
                .getAttribute("MONGO_CLIENT");
        MongoDBIssueDAO issueDAO = new MongoDBIssueDAO(mongo);
        Issue p = new Issue();
        p.setId(id);
        p = issueDAO.readIssue(p);
        request.setAttribute("issue", p);
        List<Issue> Issues = issueDAO.readAllIssue();
        request.setAttribute("Issues", Issues);
 
        RequestDispatcher rd = getServletContext().getRequestDispatcher(
                "/IssueTrackerNew.jsp");
        rd.forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		System.out.println("Inside edit post");
		
		String id = request.getParameter("id"); // keep it non-editable in UI
        if (id == null || "".equals(id)) {
            throw new ServletException("id missing for edit operation");
        }
 
        String description = request.getParameter("description");
        String status = request.getParameter("status");
        String severity = request.getParameter("severity");
        
        System.out.println(description);
        System.out.println(status);
        System.out.println(severity);
 
        if ((description == null || description.equals(""))
                || (status == null || status.equals("")) || (severity == null || severity.equals(""))) {
            request.setAttribute("error", "Description and Status Can't be empty");
            MongoClient mongo = (MongoClient) request.getServletContext()
                    .getAttribute("MONGO_CLIENT");
            MongoDBIssueDAO issueDAO = new MongoDBIssueDAO(mongo);
            Issue p = new Issue();
            p.setId(id);
            p.setDescription(description);
            p.setStatus(status);
            p.setSeverity(severity);
            request.setAttribute("issue", p);
            List<Issue> Issues = issueDAO.readAllIssue();
            request.setAttribute("Issues", Issues);
 
            RequestDispatcher rd = getServletContext().getRequestDispatcher(
                    "/IssueTrackerNew.jsp");
            rd.forward(request, response);
        } else {
            MongoClient mongo = (MongoClient) request.getServletContext()
                    .getAttribute("MONGO_CLIENT");
            MongoDBIssueDAO issueDAO = new MongoDBIssueDAO(mongo);
            Issue p = new Issue();
            p.setId(id);
            p.setDescription(description);
            p.setStatus(status);
            p.setSeverity(severity);
            issueDAO.updateIssue(p);
            System.out.println("Issue edited successfully with id=" + id);
            request.setAttribute("success", "Issue edited successfully");
            List<Issue> Issues = issueDAO.readAllIssue();
            request.setAttribute("Issues", Issues);
 
            RequestDispatcher rd = getServletContext().getRequestDispatcher(
                    "/IssueTrackerNew.jsp");
            rd.forward(request, response);
        }
    }
		
	}


