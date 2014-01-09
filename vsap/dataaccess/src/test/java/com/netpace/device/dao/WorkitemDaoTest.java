package com.netpace.device.dao;

import com.netpace.commons.utils.VAPUtils;
import com.netpace.device.AbstractDaoTest;
import com.netpace.device.entities.Workitem;
import com.netpace.device.utils.enums.WorkitemStatus;
import com.netpace.device.vo.PageModel;
import com.netpace.device.vo.WorkItem;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import static org.junit.Assert.*;
import org.junit.Test;

public class WorkitemDaoTest extends AbstractDaoTest {

    protected final Log log = LogFactory.getLog(getClass());
    @Resource(name = WorkitemDao.name)
    private WorkitemDao workitemDao;

    /**
     * Test of getWorkitems method, of class WorkitemDao.
     */
    @Test
    public void testAll() {
        //String[] userRoles = new String[]{"DeviceMarketing", "ROLE_ADMIN"};
        //testGetPaggedData(WorkitemStatus.Processed, userRoles, 0, 10);
        //testGetDelayedWorkitems(VAPUtils.getDateOlderThanDays(14));
    }

    /**
     * Test of getWorkitem method, of class WorkitemDao.
     */
    public void testGetWorkitem(String title, WorkitemStatus status, Integer workflowId) {
        System.out.println("getWorkitem");
        Workitem result = workitemDao.getWorkitem(title, status, workflowId);
        assertNotNull(result);
    }

    /**
     * Test of getPaggedData method, of class WorkitemDao.
     */
    public void testGetPaggedData(WorkitemStatus status, String[] userRoles, PageModel<WorkItem> pageModel) {
        System.out.println("getPaggedData");
        PaggedResult result = workitemDao.getPaggedWorkitems(status, userRoles, pageModel, null, false);
        assertNotNull(result);
        log.info(result.records);
    }

    /**
     * Test of getDelayedWorkitems method, of class WorkitemDao.
     */
    public void testGetDelayedWorkitems(Date olderThanDate) {
        log.info("[Test] ---- getDelayedWorkitems");
        List<Workitem> items = workitemDao.getDelayedWorkitems(olderThanDate);
        assertNotNull(items);
        log.info("[Test] ---- getDelayedWorkitems: itemsCount["+items.size()+"]");
    }
}
