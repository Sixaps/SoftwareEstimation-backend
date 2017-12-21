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

    public void add(String id, Description description){
        Query query = new Query(Criteria.where("_id").is(id));
        //如果用两个update，第一条信息插不进去。不知道原因，可以查看一下API文档，为什么要先update再set。现在是快十二点了先睡觉。
        Update update = Update.update("description.$.projectName", description.getProjectName()).set("description.$.projectDescription", description.getProjectDescription());
        update.set("description.$.projectLeader", description.getProjectLeader());
        update.set("description.$.projectContact",description.getProjectContact());
        update.set("description.$.estimationMethod",description.getEstimationMethod());
        mongoTemplate.upsert(query, update, Requirement.class);
    }
}
