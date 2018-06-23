package estimation.service;

import estimation.DAO.DescriptionDAO;
import estimation.bean.Description;
import net.sf.json.JSONObject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * DescriptionService Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>���� 8, 2018</pre>
 */
public class DescriptionServiceTest {

    @Mock
    private DescriptionDAO descriptionDAO;
    private DescriptionService descriptionService;

    @Before
    public void before() throws Exception {
        MockitoAnnotations.initMocks(this);
        descriptionService = new DescriptionService(descriptionDAO);
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: add(String id, Description description)
     */
    @Test
    public void testAdd() throws Exception {
        Description description = new Description();
        description.setProjectName("projectName");
        description.setProjectDescription("projectDescription");
        description.setProjectLeader("projectLeader");
        description.setProjectContact("projectContact");
        description.setEstimationMethod("estimationMethod");
        JSONObject jsonObject = JSONObject.fromObject(description);

        when(descriptionDAO.add(any(String.class),any(Description.class))).thenReturn(true);

        assertTrue(descriptionService.add("1",jsonObject));
    }

    @Test
    public void testAddException() throws Exception {
        Description description = new Description();
        description.setProjectName("projectName");
        description.setProjectDescription("projectDescription");
        description.setProjectLeader("projectLeader");
        description.setProjectContact("projectContact");
        description.setEstimationMethod("estimationMethod");
        JSONObject jsonObject = JSONObject.fromObject(description);

        when(descriptionDAO.add(any(String.class),any(Description.class))).thenThrow(new Exception());

        assertFalse(descriptionService.add("1",jsonObject));
    }

    @Test
    public void testAddWrongInput() throws Exception {
        Description description = new Description();
        description.setProjectName("projectName");
        description.setProjectLeader("projectLeader");
        description.setProjectContact("projectContact");
        description.setEstimationMethod("estimationMethod");
        JSONObject jsonObject = JSONObject.fromObject(description);

        when(descriptionDAO.add("1",description)).thenThrow(new Exception());

        assertFalse(descriptionService.add("1",jsonObject));
    }



} 
