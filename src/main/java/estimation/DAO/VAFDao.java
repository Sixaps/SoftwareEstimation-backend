package estimation.DAO;

import estimation.bean.Requirement;
import estimation.bean.VAF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

/**
 * Created by xuawai on 16/06/2017.
 */
@Repository
public class VAFDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    //向数组中增添一个对象，如果数组不存在，将被创建
    public boolean add(String id, VAF vaf){
        Query query = new Query(Criteria.where("_id").is(id));
        Update update = Update.update("originalVAF",vaf);
        update.set("newVAF", vaf);
        mongoTemplate.upsert(query, update, Requirement.class);
        return true;
    }
    
    public boolean change(String id, VAF vaf) {
    	Query query = new Query(Criteria.where("_id").is(id));
        Update update = Update.update("newVAF", vaf);
        mongoTemplate.upsert(query, update, Requirement.class);
        return true;
    }

}
