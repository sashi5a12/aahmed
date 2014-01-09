package com.netpace.notification.dao.jpa;

import com.netpace.commons.AbstractDaoTest;
import com.netpace.notification.dao.PlaceholderDao;
import com.netpace.notification.entities.Placeholder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class PlaceholderDaoTest extends AbstractDaoTest{
    
    @Resource(name=PlaceholderDao.name)
    PlaceholderDao placeholderDao;
    
    public PlaceholderDaoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testAll(){
        /*
        testGetPlaceholderByName("USERNAME");
        testGetPlaceholderById(11);
        
        ArrayList<Integer> placeholderIds = new ArrayList<Integer>();
        placeholderIds.add(10);
        placeholderIds.add(11);
        testGetPlaceHoldersList(placeholderIds);
        */
    }

    /**
     * Test of getPlaceholderById method, of class PlaceholderDaoJpaImpl.
     */
    public void testGetPlaceholderById(Integer placeholderId) {
        System.out.println("getPlaceholderById");
        Placeholder result = placeholderDao.getPlaceholderById(placeholderId);
        assertNotNull(result);
    }
    
    /**
     * Test of getPlaceholderByName method, of class PlaceholderDaoJpaImpl.
     */
    public void testGetPlaceholderByName(String displayName) {
        System.out.println("getPlaceholderByName");
        Placeholder result = placeholderDao.getPlaceholderByName(displayName);
        assertNotNull(result);
    }
    
    /**
     * Test of getPlaceHoldersList method, of class PlaceholderDaoJpaImpl.
     */
    public void testGetPlaceHoldersList(List<Integer> placeHoldersIds) {
        System.out.println("getPlaceHoldersList");
        List result = placeholderDao.getPlaceHoldersList(placeHoldersIds);
        assertNotNull(result);
    }

}
