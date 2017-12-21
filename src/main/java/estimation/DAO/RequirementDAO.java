package estimation.DAO;

import estimation.bean.Requirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xuawai on 03/05/2017.
 */

@Repository
public class RequirementDAO {
    @Autowired
    private MongoTemplate mongoTemplate;

    public void add(Requirement requirement){
        this.mongoTemplate.insert(requirement);
    }

    public Requirement getRequirement(String id){
        return mongoTemplate.findOne(new Query(Criteria.where("_id").is(id)),Requirement.class);
    }

    public List<Requirement> getAllRequirements(){
        return mongoTemplate.findAll(Requirement.class,"requirement");
    }
    
    public void deleteRequirement(String id) {
    	Query query = new Query(Criteria.where("_id").is(id));
    	mongoTemplate.remove(query, Requirement.class);
    }
    
    public void changeState(String id, String state, String remark) {
    	Query query = new Query(Criteria.where("_id").is(id));
    	Update update = Update.update("remark", remark);
    	update.set("state", state);
    	mongoTemplate.upsert(query, update, Requirement.class);
    }
}
