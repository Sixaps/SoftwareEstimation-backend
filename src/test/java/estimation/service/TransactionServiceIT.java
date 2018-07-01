package estimation.service;

import estimation.DAO.TransactionDAO;
import estimation.bean.EstimationTransactionData;
import estimation.bean.Folder;
import estimation.bean.Step;
import estimation.bean.Transaction;
import estimation.service.TransactionService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * TransactionService Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>���� 8, 2018</pre>
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionServiceIT {
    @Autowired
    private TransactionService transactionService;
    private Transaction trueTransaction;
    private JSONObject tree;
    private Folder parent;

    @Before
    public void before() throws Exception {
        trueTransaction = new Transaction();
        String transactionName = "testName";
        String transactionId = "tId";
        List<Step> steps = new ArrayList<>();
        List<EstimationTransactionData> estimationTransactionDatas = new ArrayList<>();
        EstimationTransactionData estimationTransactionData = new EstimationTransactionData();
        estimationTransactionData.setDET("");
        estimationTransactionData.setDETNum(0);
        estimationTransactionData.setFileNum(0);
        estimationTransactionData.setLogicalFile("");
        estimationTransactionData.setTransactionType("");
        estimationTransactionData.setName("");
        estimationTransactionDatas.add(estimationTransactionData);
        trueTransaction.setEstimationTransactionDatas(estimationTransactionDatas);
        trueTransaction.setTransactionName(transactionName);
        trueTransaction.setId(transactionId);
        trueTransaction.setSteps(steps);


        tree = new JSONObject();
        tree.accumulate("childFiles",new JSONArray());
        tree.accumulate("childFolders",new JSONArray());

        parent = new Folder();
        parent.setId("001");
        parent.setName("testName");
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: add(String id, Transaction transaction)
     */
    @Test
    public void testAddTrue() throws Exception {
//TODO: Test goes here...
        assertTrue(transactionService.add("1",trueTransaction));
    }

    @Test
    public void testAddNullTransaction() throws Exception {
//TODO: Test goes here...
        assertFalse(transactionService.add("1",null));
    }

    @Test
    public void testAddNullId() throws Exception {
//TODO: Test goes here...
        assertFalse(transactionService.add(null,trueTransaction));
    }

    @Test
    public void testAddError() throws Exception {
//TODO: Test goes here...
        assertFalse(transactionService.add(null,null));
    }

    /**
     * Method: deleteArray(String id, String key)
     */
    @Test
    public void testDeleteArray() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: getAllTransactions(String id)
     */
    @Test
    public void testGetAllTransactions() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: buildTree(Folder parent, JSONObject jsonObject)
     */
    @Test
    public void testBuildTreeNullParent() throws Exception {
//TODO: Test goes here...
        assertFalse(transactionService.buildTree(null, tree));
    }

    @Test
    public void testBuildTreeNullJSON() throws Exception {
//TODO: Test goes here...
        tree.remove("childFiles");
        assertFalse(transactionService.buildTree(parent, tree));
        tree.accumulate("childFiles",new JSONArray());
    }

    @Test
    public void testBuildTreeError() throws Exception {
//TODO: Test goes here...
        tree.remove("childFiles");
        assertFalse(transactionService.buildTree(null, tree));
        tree.accumulate("childFiles",new JSONArray());
    }

    @Test
    public void testBuildTreeTrue() throws Exception {
//TODO: Test goes here...
        assertTrue(transactionService.buildTree(parent, tree));
    }

    /**
     * Method: addTree(String id, Folder tree)
     */
    @Test
    public void testAddTreeNullId() throws Exception {
//TODO: Test goes here...
        assertFalse(transactionService.addTree(null, parent));
    }

    @Test
    public void testAddTreeNullTree() throws Exception {
//TODO: Test goes here...
        assertFalse(transactionService.addTree("1", null));
    }

    @Test
    public void testAddTreeError() throws Exception {
//TODO: Test goes here...
        assertFalse(transactionService.addTree(null, null));
    }

    @Test
    public void testAddTreeTrue() throws Exception {
//TODO: Test goes here...
        assertTrue(transactionService.addTree("1", parent));
    }

    /**
     * Method: getTree(String id)
     */
    @Test
    public void testGetTree() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: addFile(String id, String name, String tId)
     */

    @Test
    public void testAddFileNullId() throws Exception {
//TODO: Test goes here...
        assertFalse(transactionService.addFile(null,"name","11"));
    }

    @Test
    public void testAddFileError() throws Exception {
//TODO: Test goes here...
        assertFalse(transactionService.addFile(null,null,null));
    }

    @Test
    public void testAddFileNullTid() throws Exception {
//TODO: Test goes here...
        assertFalse(transactionService.addFile("1","name",null));
    }

    @Test
    public void testAddFileNullName() throws Exception {
//TODO: Test goes here...
        assertFalse(transactionService.addFile("1",null,"11"));
    }

    @Test
    public void testAddFileTree() throws Exception {
//TODO: Test goes here...
        assertTrue(transactionService.addFile("1","name","11"));
    }

    @Test
    public void testGeTransactionTrue() throws Exception {
//TODO: Test goes here...
        assertEquals(transactionService.geTransaction("1","tId").getId(),"tId");
    }

    @Test
    public void testGeTransactionNullId() throws Exception {
//TODO: Test goes here...
        assertEquals(transactionService.geTransaction(null,"tId"),null);
    }
    @Test
    public void testGeTransactionNulltId() throws Exception {
//TODO: Test goes here...
        assertEquals(transactionService.geTransaction("1",null),null);
    }
    @Test
    public void testGeTransactionError() throws Exception {
//TODO: Test goes here...
        assertEquals(transactionService.geTransaction(null,null),null);
    }

    /**
     * Method: deleteTransaction(String id, String tId)
     */
    @Test
    public void testDeleteTransaction() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: reName(String id, String tId, String tName)
     */
    @Test
    public void testReName() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: getAllTransactionId(Folder tree, List<String> tIds)
     */
    @Test
    public void testGetAllTransactionId() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: updateTransactionList(String id, List<String> tIds)
     */
    @Test
    public void testUpdateTransactionList() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: updateILFAndEIFData(String id, JSONArray jsonArray, int flag)
     */
    @Test
    public void testUpdateILFAndEIFData() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: updateETDs(String id, String tId, List<EstimationTransactionData> eTDs)
     */
    @Test
    public void testUpdateETDs() throws Exception {
//TODO: Test goes here... 
    }


} 
