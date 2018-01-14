package estimation.DAO;

import estimation.bean.Folder;
import estimation.bean.Requirement;
import estimation.bean.Transaction;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

/**
 * Created by Sixaps on 12/05/2017.
 */
@Repository
public class TransactionDAO {
    @Autowired
    private MongoTemplate mongoTemplate;

    //向数组中增添一个对象，如果数组不存在，将被创建
    public void add(String id, Transaction transaction) {
        Query query = new Query(Criteria.where("_id").is(id));
        Update update = new Update();
        update.addToSet("transactions", transaction);
        mongoTemplate.upsert(query, update, Requirement.class);
    }

    //这个函数可以提到基类中去
    //删除以key为键的数组对象，其中，键也会被删除
    public void deleteArray(String id, String key) {
        Query query = new Query(Criteria.where("_id").is(id));
        Update update = new Update();
        update.unset(key);
        mongoTemplate.upsert(query, update, Requirement.class);
    }

    public void addTree(String id, Folder tree) {
        Query query = new Query(Criteria.where("_id").is(id));
        Update update = Update.update("treeOfTransactions", tree);
        mongoTemplate.upsert(query, update, Requirement.class);
    }

    public void updateTransaction(String id, List<String> tIds) {
        Query query = new Query(Criteria.where("_id").is(id));
        Requirement requirement = mongoTemplate.findOne(query, Requirement.class);
        Transaction transaction;
        List<Transaction> transactions = requirement.getTransactions();
        boolean flag;
        for (int i = 0; i < transactions.size(); i++) {
            flag = true;
            transaction = transactions.get(i);
            for (int j = 0; j < tIds.size(); j++) {
                if (transaction.getId().equals(tIds.get(j))) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                transactions.remove(i);
            }
        }
        Update update = Update.update("transactions", transactions);
        mongoTemplate.upsert(query, update, Requirement.class);
    }

    public Transaction geTransaction(String id, String tId) {
        Query query = new Query(Criteria.where("_id").is(id));
        Requirement requirement = mongoTemplate.findOne(query, Requirement.class);
        Transaction transaction;
        List<Transaction> transactions = requirement.getTransactions();
        for (int i = 0; i < transactions.size(); i++) {
            if (transactions.get(i).getId().equals(tId)) {
                transaction = transactions.get(i);
                return transaction;
            }
        }
        return null;
    }

    public Folder getTree(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        Requirement requirement = mongoTemplate.findOne(query, Requirement.class);
        return requirement.getTreeOfTransactions();
    }

    public void deleteTransaction(String id, String tId) {
        Query query = new Query(Criteria.where("_id").is(id));
        Requirement requirement = mongoTemplate.findOne(query, Requirement.class);
        List<Transaction> transactions = requirement.getTransactions();
        for (int i = 0; i < transactions.size(); i++) {
            if (transactions.get(i).getId().equals(tId)) {
                transactions.remove(i);
            }
        }
        Update update = Update.update("transactions", transactions);
        mongoTemplate.upsert(query, update, Requirement.class);
    }

    public void reName(String id, String tId, String tName) {
        Query query = new Query(Criteria.where("_id").is(id));
        Requirement requirement = mongoTemplate.findOne(query, Requirement.class);
        List<Transaction> transactions = requirement.getTransactions();
        int i;
        for (i = 0; i < transactions.size(); i++) {
            if (transactions.get(i).getId().equals(tId)) {
                transactions.get(i).setTransactionName(tName);
            }
        }
        Update update = Update.update("transactions", transactions);
        mongoTemplate.upsert(query, update, Requirement.class);
    }


}
