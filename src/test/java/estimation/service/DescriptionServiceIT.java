package estimation.service;

import estimation.DAO.DescriptionDAO;
import estimation.bean.Description;
import estimation.service.DescriptionService;
import net.sf.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * DescriptionService Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>���� 8, 2018</pre>
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DescriptionServiceIT {
    @Autowired
    private DescriptionService descriptionService;
    private Description description;

    @Before
    public void before() throws Exception {
        description = new Description();
        description.setProjectName("projectName");
        description.setProjectDescription("projectDescription");
        description.setProjectLeader("projectLeader");
        description.setProjectContact("projectContact");
        description.setEstimationMethod("estimationMethod");
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: add(String id, Description description)
     */
    @Test
    public void testAddTrue() throws Exception {
        JSONObject jsonObject = JSONObject.fromObject(description);

        assertTrue(descriptionService.add("1529486307378",jsonObject));
    }

    @Test
    public void testAddWrongInput() throws Exception {
        JSONObject jsonObject = JSONObject.fromObject(description);
        jsonObject.remove("projectDescription");

        assertFalse(descriptionService.add("1529486307378",jsonObject));
    }



} 
