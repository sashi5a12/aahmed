package com.netpace.aims.bo.masters;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import net.sf.hibernate.type.IntegerType;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.contrib.HSSFCellUtil;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.netpace.aims.bo.application.AimsFileDateException;
import com.netpace.aims.bo.application.AimsInvalidDataException;
import com.netpace.aims.bo.core.TempFilesManager;
import com.netpace.aims.controller.devices.AimsAllianceExt;
import com.netpace.aims.controller.devices.AimsLoanDevicExt;
import com.netpace.aims.controller.devices.LoanDeviceListForm;
import com.netpace.aims.controller.devices.LoanDevicesCollInfo;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.core.AimsTempFile;
import com.netpace.aims.model.masters.AimsAllianceLoanDevic;
import com.netpace.aims.model.masters.AimsDevicesToLoan;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.StringFuncs;
import com.netpace.aims.util.Utility;

/**
 * 
 * @author Ahson Imtiaz
 */
public class AimsDevicesOnLoanManager
{

    static Logger log = Logger.getLogger(AimsDevicesOnLoanManager.class.getName());

    public static Collection getDevicesOnLoan(int iPageLength, int iPageNo) throws HibernateException
    {
        ArrayList collection = new ArrayList();
        Session session = null;
        StringBuffer sbQuery = new StringBuffer();

        try
        {
            session = DBHelper.getInstance().getSession();

            sbQuery.append(" SELECT ad.deviceId, ad.deviceModel, ad.deviceMfgBy, ad.status ");
            sbQuery.append(" FROM ");
            sbQuery.append(" com.netpace.aims.model.masters.AimsDevic ad  ");
            sbQuery.append(" WHERE ");
            sbQuery.append(" ad.deviceId != 0");
            sbQuery.append(" ORDER BY 2 ");

            Query query = session.createQuery(sbQuery.toString());

            query.setMaxResults(iPageLength);
            query.setFirstResult(iPageLength * (iPageNo - 1));

            Object[] objCols;

            for (Iterator it = query.iterate(); it.hasNext();)
            {
                com.netpace.aims.controller.devices.AimsLoanDevicExt loanDeviceExt = new com.netpace.aims.controller.devices.AimsLoanDevicExt();
                objCols = (Object[]) it.next();
                loanDeviceExt.setDeviceId((java.lang.Long) objCols[0]);
                loanDeviceExt.setDeviceModel(Utility.getObjectString(objCols[1]));
                loanDeviceExt.setDeviceMfgBy(Utility.getObjectString(objCols[2]));
                loanDeviceExt.setStatus(Utility.getObjectString(objCols[3]));
                collection.add(loanDeviceExt);

            }

            log.debug("No of records returned: " + collection.size());

        }
        catch (HibernateException e)
        {
            e.printStackTrace();
            throw e;
        }
        finally
        {
            session.close();
        }

        return collection;
    }

    public static int getDevicesOnLoanCount() throws HibernateException
    {
        Session session = null;
        StringBuffer sbQuery = new StringBuffer();

        try
        {
            session = DBHelper.getInstance().getSession();

            sbQuery.append(" SELECT count(ad.deviceId) ");
            sbQuery.append(" FROM ");
            sbQuery.append(" com.netpace.aims.model.masters.AimsDevic ad  ");
            sbQuery.append(" WHERE ");
            sbQuery.append(" ad.deviceId != 0");

            Query query = session.createQuery(sbQuery.toString());

            for (Iterator it = query.iterate(); it.hasNext();)
                return ((java.lang.Integer) it.next()).intValue();
            return 0;

        }
        catch (HibernateException e)
        {
            e.printStackTrace();
            throw e;
        }
        finally
        {
            session.close();
        }

    }

    /** Return the details for specific device 
    */
    public static AimsLoanDevicExt getDevicesOnLoan(java.lang.Long lngDeviceId) throws HibernateException
    {

        Session session = null;
        StringBuffer sbQuery = new StringBuffer();

        try
        {
            session = DBHelper.getInstance().getSession();

            sbQuery.append(" SELECT ad.deviceId, ad.deviceModel, ad.deviceMfgBy");
            sbQuery.append(" FROM ");
            sbQuery.append(" com.netpace.aims.model.masters.AimsDevic ad  ");
            sbQuery.append(" WHERE ");
            sbQuery.append(" ad.deviceId = ").append(lngDeviceId.toString());
            sbQuery.append(" ORDER BY 2 ");

            Query query = session.createQuery(sbQuery.toString());
            Object[] objCols;

            for (Iterator it = query.iterate(); it.hasNext();)
            {
                com.netpace.aims.controller.devices.AimsLoanDevicExt loanDeviceExt = new com.netpace.aims.controller.devices.AimsLoanDevicExt();
                objCols = (Object[]) it.next();
                loanDeviceExt.setDeviceId((java.lang.Long) objCols[0]);
                loanDeviceExt.setDeviceModel(Utility.getObjectString(objCols[1]));
                loanDeviceExt.setDeviceMfgBy(Utility.getObjectString(objCols[2]));
                return loanDeviceExt;

            }

        }
        catch (HibernateException e)
        {
            e.printStackTrace();
            throw e;
        }
        finally
        {
            session.close();
        }

        return null;
    }

    /**
    */

    public static LoanDevicesCollInfo getLoanedDevices(LoanDeviceListForm frmCriteria, int iPageLength) throws HibernateException
    {

        LoanDevicesCollInfo ldci = null;
        Session session = null;
        StringBuffer sbQuery = new StringBuffer();

        sbQuery.append(" WHERE ");
        if (frmCriteria.getAllianceName() != null && frmCriteria.getAllianceName().length() > 0)
            sbQuery.append(" upper(aald.allianceCompanyName) like '%").append(frmCriteria.getAllianceName().toUpperCase()).append("%' AND ");
        if (frmCriteria.getEsnDec() != null && frmCriteria.getEsnDec().length() > 0)
            sbQuery.append(" aald.esnDec like '%").append(frmCriteria.getEsnDec()).append("%' AND ");
        if (frmCriteria.getEsn() != null && frmCriteria.getEsn().length() > 0)
            sbQuery.append(" aald.esn like '%").append(frmCriteria.getEsn()).append("%' AND ");
        if (frmCriteria.getMtn() != null && frmCriteria.getMtn().length() > 0)
            sbQuery.append(" aald.mtn like '%").append(frmCriteria.getMtn()).append("%' AND ");
        if (frmCriteria.getAllianceMemberName() != null && frmCriteria.getAllianceMemberName().length() > 0)
            sbQuery.append(" upper(aald.allianceMemberName) like '%").append(frmCriteria.getAllianceMemberName().toUpperCase()).append("%' AND ");
        if (frmCriteria.getEmailSent() != null && frmCriteria.getEmailSent().length() > 0)
            sbQuery.append(" aald.emailSent = '").append(frmCriteria.getEmailSent()).append("' AND ");

        if (frmCriteria.getStatus() != null && frmCriteria.getStatus().length() > 0)
            sbQuery.append(" upper(aald.status) like '%").append(frmCriteria.getStatus().toUpperCase()).append("%' AND ");

        // general condition to complete AND
        sbQuery.append(" aald.deviceId = ").append(frmCriteria.getDeviceId().toString());

        try
        {
            session = DBHelper.getInstance().getSession();

            Query query = session.createQuery(" SELECT aald FROM com.netpace.aims.model.masters.AimsAllianceLoanDevic aald " + sbQuery.toString());
            Query queryCount = session.createQuery(" SELECT count(aald) FROM com.netpace.aims.model.masters.AimsAllianceLoanDevic aald " + sbQuery.toString());

            query.setMaxResults(iPageLength);
            query.setFirstResult(iPageLength * (frmCriteria.getPageNumber() == null ? 0 : frmCriteria.getPageNumber().intValue()));

            ldci = new LoanDevicesCollInfo();
            ldci.setRecords(query.list());

            Iterator it = queryCount.iterate();

            if (it.hasNext())
                ldci.setTotalNumberOfRecords((Integer) it.next());
            else
                ldci.setTotalNumberOfRecords(new Integer(0));

        }
        catch (HibernateException e)
        {
            e.printStackTrace();
            throw e;
        }
        finally
        {
            session.close();
        }

        return ldci;

    }

    /** 
    * Return details for the specific loaned device 
    */

    public static AimsAllianceLoanDevic getLoanedDeviceDetails(java.lang.Long lngLoanDeviceId) throws HibernateException
    {

        Session session = null;
        com.netpace.aims.model.masters.AimsAllianceLoanDevic aald = null;
        StringBuffer sbQuery = new StringBuffer();

        sbQuery.append(" SELECT aald FROM com.netpace.aims.model.masters.AimsAllianceLoanDevic aald");
        sbQuery.append(" WHERE ");
        sbQuery.append(" aald.loanDeviceId = ").append(lngLoanDeviceId.toString());

        try
        {
            session = DBHelper.getInstance().getSession();
            Query query = session.createQuery(sbQuery.toString());

            for (Iterator it = query.iterate(); it.hasNext();)
            {
                aald = (com.netpace.aims.model.masters.AimsAllianceLoanDevic) it.next();
                return aald;
            }

        }
        catch (HibernateException e)
        {
            e.printStackTrace();
            throw e;
        }
        finally
        {
            session.close();
        }

        return aald;

    }

    /**
    * Return collection for Alliance containing Alliance ID and Alliance Company Name
    */

    public static Collection getAimsAlliances() throws HibernateException
    {

        Session session = null;
        ArrayList collection = new ArrayList();
        String sbQuery =
            " SELECT alliance.allianceId, alliance.companyName FROM com.netpace.aims.model.core.AimsAllianc alliance order by alliance.companyName ";

        try
        {
            session = DBHelper.getInstance().getSession();
            Query query = session.createQuery(sbQuery);

            for (Iterator it = query.iterate(); it.hasNext();)
            {
                Object[] objValues = (Object[]) it.next();
                AimsAllianceExt alliance = new AimsAllianceExt();
                alliance.setAllianceId((Long) objValues[0]);
                if (objValues[1] != null)
                    alliance.setCompanyName(objValues[1].toString());
                else
                    alliance.setCompanyName("undefined");
                collection.add(alliance);
            }

        }
        catch (HibernateException e)
        {
            e.printStackTrace();
            throw e;
        }
        finally
        {
            session.close();
        }

        return collection;

    }

    /**
    *
    */

    public static int deleteDeviceOnLoan(int loanDeviceId) throws HibernateException
    {

        int delCount = 0;
        Session session = null;
        try
        {

            session = DBHelper.getInstance().getSession();
            Transaction tx = session.beginTransaction();
            delCount =
                session.delete(
                    "from com.netpace.aims.model.masters.AimsAllianceLoanDevic as loanDevice where loanDevice.loanDeviceId = :aimsAllianceLoanId",
                    new Integer(loanDeviceId),
                    new IntegerType());

            tx.commit();
            log.debug("Number of loan devices deleted: " + delCount);

        }
        catch (HibernateException e)
        {
            e.printStackTrace();
            throw e;
        }
        finally
        {
            session.close();
        }

        return delCount;
    }

    /**/
    public static void saveOrUpdate(AimsAllianceLoanDevic aald) throws HibernateException
    {
        Session session = null;
        Transaction tx = null;
        StringBuffer sbQuery = new StringBuffer();

        try
        {
            session = DBHelper.getInstance().getSession();

            try
            {
                boolean bConAdded = false;
                sbQuery.append("from com.netpace.aims.model.masters.AimsDevicesToLoan adtl WHERE ");

                if (aald.getEsnDec() != null)
                {
                    sbQuery.append(" adtl.esnDec = '").append(aald.getEsnDec()).append("' ");
                    bConAdded = true;
                }
                if (aald.getEsn() != null)
                {
                    if (bConAdded)
                        sbQuery.append(" OR ");
                    sbQuery.append(" adtl.esn = '").append(aald.getEsn()).append("' ");
                }

                Collection coll = session.find(sbQuery.toString());

                if (coll == null || coll.size() <= 0)
                {
                    AimsDevicesToLoan adtl = new AimsDevicesToLoan();

                    if (aald.getDeviceToLoanId() != null && aald.getDeviceToLoanId().intValue() != 0)
                        adtl.setDeviceToLoanId(aald.getDeviceToLoanId());

                    adtl.setDeviceId(aald.getDeviceId());
                    adtl.setEsn(aald.getEsn());
                    adtl.setEsnDec(aald.getEsnDec());
                    adtl.setMtn(aald.getMtn());
                    adtl.setCreatedBy(aald.getCreatedBy());
                    adtl.setCreatedDate(aald.getCreatedDate());
                    adtl.setLastUpdatedBy(aald.getCreatedBy());
                    adtl.setLastUpdatedDate(aald.getCreatedDate());
                    session.saveOrUpdate(adtl);
                    aald.setDeviceToLoanId(adtl.getDeviceToLoanId());
                    log.debug("New ESN-HEX , ESN-DEC Saved, New Device to loan ESN record saved for re-use.");
                }
                else
                {
                    Iterator it = coll.iterator();
                    if (it.hasNext())
                    {
                        AimsDevicesToLoan adtl = (AimsDevicesToLoan) it.next();
                        adtl.setEsn(aald.getEsn());
                        adtl.setEsnDec(aald.getEsnDec());
                        adtl.setMtn(aald.getMtn());
                        adtl.setLastUpdatedBy(aald.getLastUpdatedBy());
                        adtl.setLastUpdatedDate(aald.getLastUpdatedDate());
                        session.saveOrUpdate(adtl);
                        aald.setDeviceToLoanId(adtl.getDeviceToLoanId());
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                log.debug("Negligible Error: New ESN Saving, New Device to loan ESN record not saved");
            }

            tx = session.beginTransaction();
            session.saveOrUpdate(aald);
            tx.commit();
            log.debug("Loan Device Details are saved with apps and new apps");
        }
        catch (HibernateException e)
        {
            log.debug("Exception");
            if (null != tx)
                tx.rollback();
            e.printStackTrace();
            throw e;
        }
        finally
        {
            session.close();
        }
    }

    /*
    */
    public static Collection getAimsPlatforms() throws HibernateException
    {
        Session session = null;
        Collection coll = null;

        try
        {
            session = DBHelper.getInstance().getSession();
            coll = session.find("from com.netpace.aims.model.core.AimsPlatform as aap order by aap.platformName ");
        }
        catch (HibernateException e)
        {
            e.printStackTrace();
            throw e;
        }
        finally
        {
            session.close();
        }

        return coll;
    }

    /**
    * 
    */
    public static Collection getDeviceToLoan(Long lngDeviceId) throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        try
        {
            session = DBHelper.getInstance().getSession();
            collection =
                session.find(
                    " from com.netpace.aims.model.masters.AimsDevicesToLoan adtl WHERE adtl.deviceId = "
                        + lngDeviceId
                        + " AND ( adtl.esnDec NOT IN (select aald.esnDec FROM com.netpace.aims.model.masters.AimsAllianceLoanDevic aald WHERE aald.status = 'Open' AND aald.deviceId = "
                        + lngDeviceId
                        + " AND aald.esnDec is not null) OR adtl.esnDec is null) "
                        + " AND ( adtl.esn NOT IN (select aald.esn FROM com.netpace.aims.model.masters.AimsAllianceLoanDevic aald WHERE aald.status = 'Open' AND aald.deviceId = "
                        + lngDeviceId
                        + " AND aald.esn is not null) OR adtl.esn is null) "
                        + " ORDER BY adtl.esnDec , adtl.esn ");

            log.debug("No of records returned: " + collection.size());

        }
        catch (HibernateException e)
        {
            e.printStackTrace();
            throw e;
        }
        finally
        {
            session.close();
        }

        return collection;
    }

    /**
    * 
    */
    public static Collection getDeviceToLoan(Long lngDeviceId, Long lngLoanDeviceId) throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        try
        {
            session = DBHelper.getInstance().getSession();
            collection =
                session.find(
                    " from com.netpace.aims.model.masters.AimsDevicesToLoan adtl WHERE adtl.deviceId = "
                        + lngDeviceId
                        + " AND ( adtl.esnDec NOT IN (select aald.esnDec FROM com.netpace.aims.model.masters.AimsAllianceLoanDevic aald WHERE aald.status = 'Open' AND aald.loanDeviceId != "
                        + lngLoanDeviceId
                        + " AND aald.esnDec is not null) OR adtl.esnDec is null) "
                        + " AND ( adtl.esn NOT IN (select aald.esn FROM com.netpace.aims.model.masters.AimsAllianceLoanDevic aald WHERE aald.status = 'Open' AND aald.loanDeviceId != "
                        + lngLoanDeviceId
                        + " AND aald.esn is not null) OR adtl.esn is null)"
                        + " ORDER BY adtl.esnDec, adtl.esn ");

            log.debug("No of records returned: " + collection.size());

        }
        catch (HibernateException e)
        {
            e.printStackTrace();
            throw e;
        }
        finally
        {
            session.close();
        }

        return collection;
    }

    /**/
    public static void writeExcelData(OutputStream osResponse, Long lngFilterDeviceId) throws Exception
    {
        // Creating Workbook
        HSSFWorkbook wb = new HSSFWorkbook();
        Session session = null;
        Collection devColl, devOnLoanColl;
        Object[] objValues = null;
        Long lngDeviceId = null;
        String strQuery = null;

        if (lngFilterDeviceId == null || lngFilterDeviceId.intValue() == 0)
            strQuery = " SELECT ad.deviceId, ad.deviceModel FROM com.netpace.aims.model.masters.AimsDevic ad WHERE ad.deviceId != 0 ORDER BY ad.deviceModel ";
        else
            strQuery =
                " SELECT ad.deviceId, ad.deviceModel FROM com.netpace.aims.model.masters.AimsDevic ad WHERE ad.deviceId = " + lngFilterDeviceId.toString();

        log.debug("Query : " + strQuery);

        String strDeviceName = null;
        StringBuffer strAppNames;
        int iDeviceCount = 1;
        int iEntryCount = 1;
        HSSFRow row;

        try
        {
            session = DBHelper.getInstance().getSession();
            devColl = session.find(strQuery);

            HSSFFont font = wb.createFont();
            font.setFontHeightInPoints((short) 9);
            font.setFontName(HSSFFont.FONT_ARIAL);
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

            HSSFFont font2 = wb.createFont();
            font2.setFontHeightInPoints((short) 12);
            font2.setFontName(HSSFFont.FONT_ARIAL);
            font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

            HSSFFont fontNormal = wb.createFont();
            fontNormal.setFontHeightInPoints((short) 7);
            fontNormal.setFontName(HSSFFont.FONT_ARIAL);

            HSSFCellStyle style = wb.createCellStyle();
            style.setFont(font);
            style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
            style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

            HSSFCellStyle style2 = wb.createCellStyle();
            style2.setFont(font2);
            style2.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
            style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            style2.setWrapText(true);

            HSSFCellStyle styleNormal = wb.createCellStyle();
            styleNormal.setFont(fontNormal);

            HSSFCellStyle styleLoaned = wb.createCellStyle();
            styleLoaned.setFont(fontNormal);
            styleLoaned.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
            styleLoaned.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

            HSSFCellStyle styleToApply = null;

            for (Iterator it = devColl.iterator(); it.hasNext(); iDeviceCount++)
            {
                objValues = (Object[]) it.next();
                lngDeviceId = (Long) objValues[0];
                strDeviceName = (String) objValues[1];

                if (strDeviceName == null)
                    strDeviceName = "Undefined Model " + iDeviceCount;
                // Creating worksheet to hold each device loan information , Name should be unique
                HSSFSheet sheet = wb.createSheet(strDeviceName);

                row = sheet.createRow((short) 1);
                HSSFCellUtil.createCell(row, 0, strDeviceName, style2);
                sheet.addMergedRegion(new Region(1, (short) 0, 1, (short) 10));

                row = sheet.createRow((short) 2);
                HSSFCellUtil.createCell(row, 0, "Verizon Wireless", style2);
                sheet.addMergedRegion(new Region(2, (short) 0, 2, (short) 10));

                row = sheet.createRow((short) 4);
                HSSFCellUtil.createCell(row, 0, "Item", style);
                sheet.setColumnWidth((short) 0, (short) (256 * 5));
                HSSFCellUtil.createCell(row, 1, "ESN (DEC)", style);
                sheet.setColumnWidth((short) 1, (short) (256 * 12));
                HSSFCellUtil.createCell(row, 2, "ESN (Hex)", style);
                sheet.setColumnWidth((short) 2, (short) (256 * 12));
                HSSFCellUtil.createCell(row, 3, "MTN", style);
                sheet.setColumnWidth((short) 3, (short) (256 * 12));
                HSSFCellUtil.createCell(row, 4, "Developer", style);
                sheet.setColumnWidth((short) 4, (short) (256 * 20));
                HSSFCellUtil.createCell(row, 5, "Applications Committed", style);
                sheet.setColumnWidth((short) 5, (short) (256 * 20));
                HSSFCellUtil.createCell(row, 6, "Contact Name", style);
                sheet.setColumnWidth((short) 6, (short) (256 * 15));
                HSSFCellUtil.createCell(row, 7, "Phone Number", style);
                sheet.setColumnWidth((short) 7, (short) (256 * 13));
                HSSFCellUtil.createCell(row, 8, "Address", style);
                sheet.setColumnWidth((short) 8, (short) (256 * 20));
                HSSFCellUtil.createCell(row, 9, "Email", style);
                sheet.setColumnWidth((short) 9, (short) (256 * 17));
                HSSFCellUtil.createCell(row, 10, "Status", style);
                sheet.setColumnWidth((short) 10, (short) (256 * 17));

                strQuery =
                    "SELECT aald FROM com.netpace.aims.model.masters.AimsAllianceLoanDevic aald WHERE aald.deviceId = "
                        + lngDeviceId.toString()
                        + " ORDER BY aald.status DESC, aald.esnDec";
                devOnLoanColl = session.find(strQuery);

                iEntryCount = 1;
                for (Iterator itEnteries = devOnLoanColl.iterator(); itEnteries.hasNext(); iEntryCount++)
                {
                    AimsAllianceLoanDevic aald = (AimsAllianceLoanDevic) itEnteries.next();

                    if (aald.getStatus().equalsIgnoreCase("Open"))
                        styleToApply = styleNormal;
                    else
                        styleToApply = styleLoaned;

                    row = sheet.createRow((short) 5 + iEntryCount);

                    HSSFCellUtil.createCell(row, 0, java.lang.Integer.toString(iEntryCount), styleToApply);
                    if (aald.getEsnDec() != null)
                        HSSFCellUtil.createCell(row, 1, aald.getEsnDec(), styleToApply);
                    if (aald.getEsn() != null)
                        HSSFCellUtil.createCell(row, 2, aald.getEsn(), styleToApply);
                    HSSFCellUtil.createCell(row, 3, aald.getMtn(), styleToApply);
                    HSSFCellUtil.createCell(row, 4, aald.getAllianceCompanyName(), styleToApply);

                    strAppNames = new StringBuffer();

                    if (strAppNames.length() > 2)
                        strAppNames.setLength(strAppNames.length() - 2);

                    HSSFCellUtil.createCell(row, 5, strAppNames.toString(), styleToApply);
                    HSSFCellUtil.createCell(row, 6, aald.getAllianceMemberName(), styleToApply);
                    HSSFCellUtil.createCell(row, 7, aald.getAllianceMemberTelephone(), styleToApply);
                    HSSFCellUtil.createCell(row, 8, aald.getAllianceMemberAddress(), styleToApply);
                    HSSFCellUtil.createCell(row, 9, aald.getAllianceMemberEmail(), styleToApply);
                    HSSFCellUtil.createCell(row, 10, aald.getStatus(), styleToApply);

                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }
        finally
        {
            session.close();
        }

        wb.write(osResponse);

    }

    /**/
    public static void writeExcelData(OutputStream osResponse) throws Exception
    {
        writeExcelData(osResponse, null);
    }

    public static Collection getDevices() throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        try
        {
            session = DBHelper.getInstance().getSession();
            StringBuffer query = new StringBuffer();
            query.append(" from com.netpace.aims.model.masters.AimsDevic as device where device.deviceId != 0");
            query.append(" order by device_model");
            collection = session.find(query.toString());
            log.debug("No of records returned: " + collection.size());

        }
        catch (HibernateException e)
        {
            e.printStackTrace();
            throw e;
        }
        finally
        {
            session.close();
        }

        return collection;
    }

    public static LoanDevicesCollInfo saveDeviceOnLoanData(Long deviceId, Long tempFileId, String username)
        throws HibernateException, Exception, AimsInvalidDataException, AimsFileDateException
    {

        String ERROR = "ERROR!!! ";
        InputStream inputStream = null;
        StringBuffer allianceAddress = null;

        AimsAllianceLoanDevic aimsAllianceLoanDevice = null;
        LoanDevicesCollInfo ldci = new LoanDevicesCollInfo();
        ldci.setTotalNumberOfRecords(new Integer(0));

        AimsTempFile aimsTempFile = TempFilesManager.getTempFile(tempFileId, username);

        POIFSFileSystem fs = new POIFSFileSystem(aimsTempFile.getTempFile().getBinaryStream());
        HSSFWorkbook wb = new HSSFWorkbook(fs);

        log.debug("File Opened");

        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow row = null;
        HSSFCell cell = null;

        if (sheet == null)
            throw new AimsInvalidDataException();

        int iLastRowNum = sheet.getLastRowNum();
        ArrayList collection = new ArrayList(iLastRowNum + 1);
        int iCurrentRow = 1;

        try
        {
            while (iCurrentRow <= iLastRowNum)
            {
                System.out.println("iCurrentRow: " + iCurrentRow);
                aimsAllianceLoanDevice = new AimsAllianceLoanDevic();
                allianceAddress = new StringBuffer();

                aimsAllianceLoanDevice.setCreatedDate(new Date());
                aimsAllianceLoanDevice.setCreatedBy(username);
                aimsAllianceLoanDevice.setLastUpdatedDate(new Date());
                aimsAllianceLoanDevice.setLastUpdatedBy(username);
                aimsAllianceLoanDevice.setDeviceId(deviceId);

                row = sheet.getRow(iCurrentRow);

                if (row == null)
                {
                    iCurrentRow++;
                    continue;
                }

                // Getting ESN-DEC
                try
                {
                    cell = row.getCell((short) 1);
                    if (cell != null && cell.getCellType() != HSSFCell.CELL_TYPE_BLANK)
                    {
                        if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
                            aimsAllianceLoanDevice.setEsnDec(new Integer((int) cell.getNumericCellValue()).toString());
                        else
                            aimsAllianceLoanDevice.setEsnDec(cell.getStringCellValue());

                        if (aimsAllianceLoanDevice.getEsnDec().length() < 1 || aimsAllianceLoanDevice.getEsnDec().length() > 200)
                            aimsAllianceLoanDevice.setComments(ERROR + "ESN");
                    }
                    else
                        aimsAllianceLoanDevice.setComments(ERROR + "ESN");

                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                    aimsAllianceLoanDevice.setComments(ERROR + "ESN");
                }

                // Getting MTN
                try
                {

                    cell = row.getCell((short) 2);
                    if (cell != null && cell.getCellType() != HSSFCell.CELL_TYPE_BLANK)
                    {
                        if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
                            aimsAllianceLoanDevice.setMtn(new Integer((int) cell.getNumericCellValue()).toString());
                        else
                            aimsAllianceLoanDevice.setMtn(cell.getStringCellValue());

                        if (aimsAllianceLoanDevice.getMtn().length() < 1 || aimsAllianceLoanDevice.getMtn().length() > 50)
                            aimsAllianceLoanDevice.setComments(ERROR + "MTN");
                    }
                    else
                        aimsAllianceLoanDevice.setComments(ERROR + "MTN");
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                    aimsAllianceLoanDevice.setComments(ERROR + "MTN");
                }

                // Getting Developer
                try
                {
                    cell = row.getCell((short) 3);
                    if (cell != null && cell.getCellType() != HSSFCell.CELL_TYPE_BLANK)
                    {
                        aimsAllianceLoanDevice.setAllianceCompanyName(cell.getStringCellValue());
                        if (aimsAllianceLoanDevice.getAllianceCompanyName().length() < 1 || aimsAllianceLoanDevice.getAllianceCompanyName().length() > 255)
                            aimsAllianceLoanDevice.setComments(ERROR + "Developer");
                    }
                    else
                    {
                        aimsAllianceLoanDevice.setComments(ERROR + "Developer");
                        iCurrentRow++;
                        log.debug("Skipped Row");
                        continue;
                    }
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                    aimsAllianceLoanDevice.setComments(ERROR + "Developer");
                }

                // Getting Contact Name
                try
                {
                    cell = row.getCell((short) 4);
                    if (cell != null && cell.getCellType() != HSSFCell.CELL_TYPE_BLANK)
                    {
                        aimsAllianceLoanDevice.setAllianceMemberName(cell.getStringCellValue());
                        if (aimsAllianceLoanDevice.getAllianceMemberName().length() < 1 || aimsAllianceLoanDevice.getAllianceMemberName().length() > 50)
                            aimsAllianceLoanDevice.setComments(ERROR + "Contact Name");
                    }
                    else
                        aimsAllianceLoanDevice.setComments(ERROR + "Contact Name");

                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                    aimsAllianceLoanDevice.setComments(ERROR + "Contact Name");
                }

                // Getting Contact Phone Number
                try
                {
                    cell = row.getCell((short) 5);
                    if (cell != null && cell.getCellType() != HSSFCell.CELL_TYPE_BLANK)
                    {
                        if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
                            aimsAllianceLoanDevice.setAllianceMemberTelephone(new Integer((int) cell.getNumericCellValue()).toString());
                        else
                            aimsAllianceLoanDevice.setAllianceMemberTelephone(cell.getStringCellValue());

                        if (aimsAllianceLoanDevice.getAllianceMemberTelephone().length() < 1
                            || aimsAllianceLoanDevice.getAllianceMemberTelephone().length() > 50)
                            aimsAllianceLoanDevice.setComments(ERROR + "Phone Number");
                    }
                    else
                        aimsAllianceLoanDevice.setComments(ERROR + "Phone Number");
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                    aimsAllianceLoanDevice.setComments(ERROR + "Phone Number");
                }

                // Getting Contact Address
                try
                {
                    cell = row.getCell((short) 6);
                    if (cell != null && cell.getCellType() != HSSFCell.CELL_TYPE_BLANK)
                    {
                        allianceAddress.append(cell.getStringCellValue());
                    }
                    //else
                    //    aimsAllianceLoanDevice.setComments(ERROR + "Address");
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                    aimsAllianceLoanDevice.setComments(ERROR + "Address");
                }

                // Getting Contact City
                try
                {

                    cell = row.getCell((short) 7);
                    if (cell != null && cell.getCellType() != HSSFCell.CELL_TYPE_BLANK)
                    {
                        allianceAddress.append(", ");
                        allianceAddress.append(cell.getStringCellValue());
                    }
                    //else
                    //    aimsAllianceLoanDevice.setComments(ERROR + "City");
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                    aimsAllianceLoanDevice.setComments(ERROR + "City");
                }

                // Getting Contact State
                try
                {
                    cell = row.getCell((short) 8);
                    if (cell != null && cell.getCellType() != HSSFCell.CELL_TYPE_BLANK)
                    {
                        allianceAddress.append(", ");
                        allianceAddress.append(cell.getStringCellValue());
                    }
                    //else
                    //    aimsAllianceLoanDevice.setComments(ERROR + "State");
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                    aimsAllianceLoanDevice.setComments(ERROR + "State");
                }

                // Getting Contact Zip
                try
                {
                    cell = row.getCell((short) 9);
                    if (cell != null && cell.getCellType() != HSSFCell.CELL_TYPE_BLANK)
                    {
                        allianceAddress.append(" ");
                        if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
                            allianceAddress.append(new Integer((int) cell.getNumericCellValue()).toString());
                        else
                            allianceAddress.append(cell.getStringCellValue());
                    }
                    //else
                    //    aimsAllianceLoanDevice.setComments(ERROR + "Zip");

                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                    aimsAllianceLoanDevice.setComments(ERROR + "Zip");
                }

                //Setting Address + City + State + Zip
                aimsAllianceLoanDevice.setAllianceMemberAddress(allianceAddress.toString());

                if (aimsAllianceLoanDevice.getAllianceMemberAddress().length() < 1 || aimsAllianceLoanDevice.getAllianceMemberAddress().length() > 800)
                    aimsAllianceLoanDevice.setComments(ERROR + "Address");

                aimsAllianceLoanDevice.setStatus(AimsConstants.DEVICE_ON_LOAN_STATUS_OPEN);

                try
                {
                    log.debug(aimsAllianceLoanDevice.getComments());
                    if (aimsAllianceLoanDevice.getComments() != null)
                        System.out.println(aimsAllianceLoanDevice.getComments().indexOf(ERROR));

                    if ((aimsAllianceLoanDevice.getComments() == null) || (aimsAllianceLoanDevice.getComments().indexOf(ERROR) == -1))
                    {
                        AimsAllianceLoanDevic aimsAllianceLoanDeviceExists = getLoanedDeviceDetails(aimsAllianceLoanDevice);

                        if (aimsAllianceLoanDeviceExists != null)
                        {
                            saveOrUpdate(aimsAllianceLoanDeviceExists);
                            aimsAllianceLoanDevice.setComments("Record Successfully UPDATED");
                        }
                        else
                        {
                            saveOrUpdate(aimsAllianceLoanDevice);
                            aimsAllianceLoanDevice.setComments("Record Successfully ADDED");
                        }
                    }

                }
                catch (HibernateException e)
                {
                    aimsAllianceLoanDevice.setComments(ERROR + "DB Error");
                }

                // Add the class to ArrayList 
                collection.add(aimsAllianceLoanDevice);

                // Row Increament
                log.debug("Row Added");
                iCurrentRow++;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new AimsInvalidDataException();
        }
        finally
        {
            try
            {
                if (inputStream != null)
                    inputStream.close();
            }
            catch (Exception eex)
            {
                System.out.println("COULD NOT Input Stream Closed in try");
            }
        }

        log.debug("No of records returned: " + collection.size());
        for (int iCount = 0; iCount < collection.size(); iCount++)
        {
            aimsAllianceLoanDevice = (AimsAllianceLoanDevic) collection.get(iCount);

        }

        if (collection.size() > 0)
        {
            ldci.setRecords(collection);
            ldci.setTotalNumberOfRecords(new Integer(collection.size()));
        }

        return ldci;
    }

    /** 
    * Return AimsAllianceLoanDevic if the ESN and Alliance Company Name match 
    */

    public static AimsAllianceLoanDevic getLoanedDeviceDetails(AimsAllianceLoanDevic aimsAllianceLoanDevicFromXML) throws HibernateException
    {

        Session session = null;
        AimsAllianceLoanDevic aald = null;
        StringBuffer sbQuery = new StringBuffer();

        if ((!StringFuncs.isEmpty(aimsAllianceLoanDevicFromXML.getEsnDec())) && (!StringFuncs.isEmpty(aimsAllianceLoanDevicFromXML.getAllianceCompanyName())))
        {

            sbQuery.append(" SELECT aald FROM com.netpace.aims.model.masters.AimsAllianceLoanDevic aald");
            sbQuery.append(" WHERE ");
            sbQuery.append(" aald.esnDec = :esnDec ");
            sbQuery.append(" and aald.allianceCompanyName = :allianceCompanyName ");
            sbQuery.append(" and aald.deviceId = :deviceId ");

            try
            {
                session = DBHelper.getInstance().getSession();
                Query query = session.createQuery(sbQuery.toString());

                query.setString("esnDec", aimsAllianceLoanDevicFromXML.getEsnDec());
                query.setString("allianceCompanyName", aimsAllianceLoanDevicFromXML.getAllianceCompanyName());
                query.setLong("deviceId", aimsAllianceLoanDevicFromXML.getDeviceId().longValue());

                for (Iterator it = query.iterate(); it.hasNext();)
                {
                    aald = (AimsAllianceLoanDevic) it.next();
                    aald.setEsnDec(aimsAllianceLoanDevicFromXML.getEsnDec());
                    aald.setMtn(aimsAllianceLoanDevicFromXML.getMtn());
                    aald.setAllianceCompanyName(aimsAllianceLoanDevicFromXML.getAllianceCompanyName());
                    aald.setAllianceMemberName(aimsAllianceLoanDevicFromXML.getAllianceMemberName());
                    aald.setAllianceMemberTelephone(aimsAllianceLoanDevicFromXML.getAllianceMemberTelephone());
                    aald.setAllianceMemberAddress(aimsAllianceLoanDevicFromXML.getAllianceMemberAddress());
                    aald.setLastUpdatedDate(aimsAllianceLoanDevicFromXML.getLastUpdatedDate());
                    aald.setLastUpdatedBy(aimsAllianceLoanDevicFromXML.getLastUpdatedBy());
                    aald.setDeviceId(aimsAllianceLoanDevicFromXML.getDeviceId());
                    aald.setStatus(aimsAllianceLoanDevicFromXML.getStatus());
                    return aald;
                }

            }
            catch (HibernateException e)
            {
                e.printStackTrace();
                throw e;
            }
            finally
            {
                session.close();
            }
        }

        return aald;

    }

    /* C L A S S   E N D S  */
}
