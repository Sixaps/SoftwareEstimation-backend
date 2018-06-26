package estimation.service;

import estimation.DAO.VAFDao;
import estimation.bean.VAF;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
/**
 * VAFService Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>���� 8, 2018</pre>
 */
public class VAFServiceTest {


    @Mock
    private VAFDao vafDao;
    private VAFService vafService;
    private VAF vaf;

    @Before
    public void before() throws Exception {
        MockitoAnnotations.initMocks(this);
        vafService = new VAFService(vafDao);
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
        when(vafDao.add("1",vaf)).thenReturn(true);
        assertTrue(vafService.add("1",vaf));
    }

    @Test
    public void testAddException() throws Exception {
//TODO: Test goes here...
        when(vafDao.add("1",vaf)).thenThrow(new Exception());
        assertFalse(vafService.add("1",vaf));
    }


    @Test
    public void testAddNullVAF() throws Exception {
//TODO: Test goes here...
        when(vafDao.add("1",vaf)).thenReturn(true);
        assertFalse(vafService.add("1",null));
    }

    @Test
    public void testAddNullId() throws Exception {
//TODO: Test goes here..
        when(vafDao.add("1",vaf)).thenReturn(true);
        assertFalse(vafService.add(null,vaf));
    }


    /**
     * Method: change(String id, VAF vaf)
     */
    @Test
    public void testChangeTrue() throws Exception {
//TODO: Test goes here...
        when(vafDao.change("1",vaf)).thenReturn(true);
        assertTrue(vafService.change("1",vaf));
    }

    @Test
    public void testChangeException() throws Exception {
//TODO: Test goes here...
        when(vafDao.change("1",vaf)).thenThrow(new Exception());
        assertFalse(vafService.change("1",vaf));
    }


    @Test
    public void testChangeNullVAF() throws Exception {
//TODO: Test goes here...
        when(vafDao.change("1",vaf)).thenReturn(true);
        assertFalse(vafService.change("1",null));
    }

    @Test
    public void testChangeNullId() throws Exception {
//TODO: Test goes here..
        when(vafDao.change("1",vaf)).thenReturn(true);
        assertFalse(vafService.change(null,vaf));
    }


} 
