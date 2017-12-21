package estimation.DAO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import estimation.bean.EstimationFileData;
import estimation.bean.EstimationTransactionData;
import estimation.bean.Requirement;
import estimation.bean.Transaction;

@Repository
public class ResultDAO {
	
	@Autowired
	private TransactionDAO transactionDAO;
	
	@Autowired
    private MongoTemplate mongoTemplate;
	
	public Transaction UpdateResult(String id, String tId, List<EstimationFileData> eFDs, List<EstimationTransactionData> eTDs) {
		Transaction transaction = transactionDAO.geTransaction(id, tId);
		transaction.setEstimationFileDatas(eFDs);
		transaction.setEstimationTransactionDatas(eTDs);
		transactionDAO.deleteTransaction(id, tId);
		transactionDAO.add(id, transaction);
		return transaction;
	}
	
	public Requirement getReport(String id) {
        return mongoTemplate.findOne(new Query(Criteria.where("_id").is(id)),Requirement.class);
	}
	
}
