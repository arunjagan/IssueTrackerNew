

import org.bson.types.ObjectId;


import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;


public class IssueConverter {
	
	
	public static DBObject toDBObject(Issue p) {
		 
        BasicDBObjectBuilder builder = BasicDBObjectBuilder.start()
        		.append("description", p.getDescription()).append("status", p.getStatus()).append("severity", p.getSeverity());
        if (p.getId() != null)
            builder = builder.append("_id", new ObjectId(p.getId()));
        return builder.get();
    }
	
	public static Issue toIssue(DBObject doc) {
        Issue p = new Issue();
        p.setDescription((String) doc.get("description"));
        p.setStatus((String) doc.get("status"));
        p.setSeverity((String) doc.get("severity"));
        ObjectId id = (ObjectId) doc.get("_id");
        p.setId(id.toString());
        return p;
 
	}

}