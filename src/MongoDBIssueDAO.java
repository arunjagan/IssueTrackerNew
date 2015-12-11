


import java.util.ArrayList;
import java.util.List;
 
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class MongoDBIssueDAO {
	
	private DBCollection col;
	 
    public MongoDBIssueDAO(MongoClient mongo) {
        this.col = mongo.getDB("IssueTracker").getCollection("Issues");
    }
    
    public Issue createIssue(Issue p) {
    	
    	System.out.println("inside createIssue");
        DBObject doc = IssueConverter.toDBObject(p);
        this.col.insert(doc);
        ObjectId id = (ObjectId) doc.get("_id");
        p.setId(id.toString());
        return p;
    }
    
    public void updateIssue(Issue p) {
        DBObject query = BasicDBObjectBuilder.start()
                .append("_id", new ObjectId(p.getId())).get();
        this.col.update(query, IssueConverter.toDBObject(p));
    }
    
    public List<Issue> readAllIssue() {
    	
    	System.out.println("inside read all Issue");
    	
        List<Issue> data = new ArrayList<Issue>();
        DBCursor cursor = col.find();
        while (cursor.hasNext()) {
            DBObject doc = cursor.next();
            Issue p = IssueConverter.toIssue(doc);
            
            System.out.println(p.getId());
            System.out.println(p.getDescription());
            System.out.println(p.getStatus());
            System.out.println(p.getSeverity());
            data.add(p);
        }
        System.out.println("read all success");
        return data;
    }
    
    public void deleteIssue(Issue p) {
        DBObject query = BasicDBObjectBuilder.start()
                .append("_id", new ObjectId(p.getId())).get();
        this.col.remove(query);
    }
 
    public Issue readIssue(Issue p) {
        DBObject query = BasicDBObjectBuilder.start()
                .append("_id", new ObjectId(p.getId())).get();
        DBObject data = this.col.findOne(query);
        return IssueConverter.toIssue(data);
    }
    
}
