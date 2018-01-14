package estimation.DAO;

import estimation.bean.Manager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author : MianHong Li
 * @date : 2018/1/14
 */
@Repository
public class ManagerDAO {

    @Autowired
    MongoTemplate mongoTemplate;

    public void add(Manager manager){
        mongoTemplate.insert(manager, "Manager");
    }

    public void delete(String username){
        Query query = new Query(Criteria.where("username").is(username));
        mongoTemplate.findAllAndRemove(query, Manager.class, "Manager");
    }

    public List<Manager> getAll(){
        return mongoTemplate.findAll(Manager.class, "Manager");
    }
}
