package estimation.DAO;

import estimation.bean.Description;
import estimation.bean.Requirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

/**
 * Created by xuawai on 03/05/2017.
 */
@Repository
public class DescriptionDAO {
    @Autowired
    private MongoTemplate mongoTemplate;

    public boolean add(String id, Description description) throws Exception {
        Query query = new Query(Criteria.where("_id").is(id));
        Requirement requirement = mongoTemplate.findOne(query, Requirement.class, "requirement");
        Update update = Update.update("description.$.projectName", description.getProjectName()).set("description.$.projectDescription", description.getProjectDescription());
        update.set("description.$.projectLeader", description.getProjectLeader());
        update.set("description.$.projectContact", description.getProjectContact());
        update.set("description.$.estimationMethod", description.getEstimationMethod());
        mongoTemplate.upsert(query, update, Requirement.class);
        return true;
    }
}
