package estimation.DAO;

import estimation.bean.EstimationTransactionData;
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
    public boolean add(String id, Transaction transaction) throws Exception{
        Query query = new Query(Criteria.where("_id").is(id));
        Update update = new Update();
        update.addToSet("transactions", transaction);
        mongoTemplate.upsert(query, update, Requirement.class);
        return true;
    }

    public boolean addTree(String id, Folder tree) {
        Query query = new Query(Criteria.where("_id").is(id));
        Update update = Update.update("treeOfTransactions", tree);
        mongoTemplate.upsert(query, update, Requirement.class);
        return true;
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

    public Boolean reName(String id, String tId, String tName) {
        Query query = new Query(Criteria.where("_id").is(id));
        Requirement requirement = mongoTemplate.findOne(query, Requirement.class);
        List<Transaction> transactions = requirement.getTransactions();
        int i;
        boolean flag = false;
        for (i = 0; i < transactions.size(); i++) {
            if (transactions.get(i).getId().equals(tId)) {
                transactions.get(i).setTransactionName(tName);
                flag = true;
            }
        }
        if(flag) {
            Update update = Update.update("transactions", transactions);
            mongoTemplate.upsert(query, update, Requirement.class);
        }
        return flag;
    }

    public boolean updateETDs(String id, String tId, List<EstimationTransactionData> eTDs) throws Exception{
        Transaction transaction = geTransaction(id, tId);
        if(transaction  != null){
            transaction.setEstimationTransactionDatas(eTDs);
            deleteTransaction(id,tId);
            add(id, transaction);
        }
        return true;
    }


}
