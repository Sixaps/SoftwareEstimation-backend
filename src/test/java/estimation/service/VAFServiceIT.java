package estimation.service;

import estimation.Application;
import estimation.DAO.VAFDao;
import estimation.bean.VAF;
import estimation.service.VAFService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * VAFService Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>���� 8, 2018</pre>
 */
@RunWith(SpringRunner.class)
@SpringBootTest//(classes = Application.class)
@EnableAutoConfiguration
public class VAFServiceIT {


    @Autowired
    private VAFService vafService;
    private VAF vaf;

    @Before
    public void before() throws Exception {
        vaf = new VAF();
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: add(String id, VAF vaf)
     */
    @Test
    public void testAddTrue() throws Exception {
//TODO: Test goes here...
        assertTrue(vafService.add("1",vaf));
    }


    @Test
    public void testAddNullVAF() throws Exception {
//TODO: Test goes here...
        assertFalse(vafService.add("1",null));
    }

    @Test
    public void testAddNullId() throws Exception {
//TODO: Test goes here..
        assertFalse(vafService.add(null,vaf));
    }


    /**
     * Method: change(String id, VAF vaf)
     */
    @Test
    public void testChangeTrue() throws Exception {
//TODO: Test goes here...
        assertTrue(vafService.change("1",vaf));
    }


    @Test
    public void testChangeNullVAF() throws Exception {
//TODO: Test goes here...
        assertFalse(vafService.change("1",null));
    }

    @Test
    public void testChangeNullId() throws Exception {
//TODO: Test goes here..
        assertFalse(vafService.change(null,vaf));
    }


} 
