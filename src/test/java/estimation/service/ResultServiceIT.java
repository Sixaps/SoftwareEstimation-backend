package estimation.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/** 
* ResultService Tester. 
* 
* @author <Authors name> 
* @since <pre>���� 8, 2018</pre> 
* @version 1.0 
*/
@RunWith(SpringRunner.class)
@SpringBootTest
public class ResultServiceIT {

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: updateResult(String id, String tId, List<EstimationFileData> eFDs, List<EstimationTransactionData> eTDs) 
* 
*/ 
@Test
public void testUpdateResult() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: calFileComplexity(EstimationFileData eFD) 
* 
*/ 
@Test
public void testCalFileComplexity() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: calTransactionComplexity(EstimationTransactionData eTD) 
* 
*/ 
@Test
public void testCalTransactionComplexity() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getReport(String id) 
* 
*/ 
@Test
public void testGetReport() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getState(Requirement requirement) 
* 
*/ 
@Test
public void testGetState() throws Exception { 
//TODO: Test goes here... 
} 


/** 
* 
* Method: calFileUFP(int level1, int level2, int level3, EstimationFileData eFD) 
* 
*/ 
@Test
public void testCalFileUFP() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = ResultService.getClass().getMethod("calFileUFP", int.class, int.class, int.class, EstimationFileData.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 

/** 
* 
* Method: calTransactionUTF(int level1, int level2, int level3, EstimationTransactionData eTD) 
* 
*/ 
@Test
public void testCalTransactionUTF() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = ResultService.getClass().getMethod("calTransactionUTF", int.class, int.class, int.class, EstimationTransactionData.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 

/** 
* 
* Method: CalVAF(VAF vaf) 
* 
*/ 
@Test
public void testCalVAF() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = ResultService.getClass().getMethod("CalVAF", VAF.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 

/** 
* 
* Method: judge(String a, String b) 
* 
*/ 
@Test
public void testJudge() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = ResultService.getClass().getMethod("judge", String.class, String.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 

} 
