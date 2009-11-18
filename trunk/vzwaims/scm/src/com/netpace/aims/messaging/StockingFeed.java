package com.netpace.aims.messaging;

import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.TimeZone;

import oracle.jdbc.pool.OracleDataSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import com.netpace.aims.util.PsmsUtility;
import com.netpace.aims.util.StreamGobbler;
import com.netpace.aims.util.MailUtils;

/**
 * This purpose of this class is to load the product categories
 * for dirty records and create and XML Document for those categories
 * and printed onto an XML file with particular file name and
 * stored onto a particular send folder to be ftped to client
 */

public class StockingFeed
{
    static java.util.Date startTime = new java.util.Date();
    static final String propertyFile = "prdcat.properties";
    static final String tagsPropsFile = "tags.properties";
    static String schema = null;

    static final String folderName = "pc";
    static final String sendFolder = "sent_folder";

    static Connection dbConn = null;
    static ResultSet rs = null;

    private static String psms_folder_path = null;
    private static String schemaLoc = null;
    private static String dbURL = null;
    
    private static String dbUser = null;
    private static String dbPassword = null;

    private static TimeZone zoneGMT = TimeZone.getTimeZone("GMT");

    private CharArrayWriter memoryWriter = new CharArrayWriter();
    private PrintWriter memoryWriterOut = new PrintWriter(memoryWriter, true);
    private Properties params;
    private Properties tagsProps = null;
    private String resourceFolder;
    private String XMLTemplate;
    private String PPTemplate;
    private String WSPTemplate;
    private String NDMScriptFile;
    private String stockingFileID = null;

    private String vendor_id;

    private int fileId;
    private String paddedFileId;

    static boolean noDirtyRecordsFound = false;

    /**
     * @param args the command line arguments
     */

    public static void main(String[] args) throws Exception
    {
        try
        {

            StockingFeed stockingFeed = new StockingFeed(args[0], args[1], args[2], args[3], args[4]);
            Document doc = stockingFeed.getProductCategories();

            try
            {
                // validate Document against Schema
                PsmsUtility.checkXml(doc, schemaLoc);
            }
            catch (Exception e)
            {
                System.out.println(" System was not able to validate the XML...");
                throw e;
            }

            stockingFeed.finalizeModule(doc);
        }
        catch (Exception e)
        {
            if (dbConn != null)
                dbConn.rollback();
            e.printStackTrace();
            if (!noDirtyRecordsFound)
                MailUtils.sendMailWithHandledExceptions(
                    "vzwtech@netpace.com",
                    "vzwtech@netpace.com",
                    "Error while sending SCM, from Cisco DB",
                    null,
                    e.getMessage());
        }
        finally
        {
            try
            {
                if (rs != null)
                {
                    rs.close();
                    dbConn.commit();
                    rs = null;
                    dbConn.close();
                    System.out.println("\n\nDB Connection Closed!\n\n");
                }
            }
            catch (Exception e)
            {
                System.out.println("Query to retreive data from CAT failed..");
                System.exit(1);
            }
        } //End of finally block

    }

    /**
     * 
     * Constructor
     * @ param psmsFolderPath from property File
     * @ param schemaLocd from property File
     * throws Exception if error occurs
     */

    public StockingFeed(
        String psmsFolderPath,
        String paramSchemaLoc,
        String paramDbURL,
        String paramDbUser,
        String paramDbPassword)
        throws Exception
    {

        try
        {
            psms_folder_path = psmsFolderPath;
            schemaLoc = paramSchemaLoc;
            dbURL = paramDbURL;
            dbUser = paramDbUser;
            dbPassword = paramDbPassword;

        }
        catch (ArrayIndexOutOfBoundsException arrExcep)
        {
            System.out.println("Usage : java psms_folder_path schema_loc db_server db_database db_port db_user db_password");
            System.exit(1);
        }

        // folder to which resource files will be loaded and stored
        resourceFolder = psms_folder_path + File.separator + folderName + File.separator;
        System.out.println(resourceFolder);

        params = new Properties();
        tagsProps = new Properties();

        try
        {
            // load property file
            params.load(new FileInputStream(resourceFolder + propertyFile));
            tagsProps.load(new FileInputStream(resourceFolder + tagsPropsFile));
        }
        catch (IOException ioe)
        {
            System.out.println("Error : property file missing..");
            System.exit(1);
        }

        //load  params in the property file
        XMLTemplate = (String) params.get("XMLTemplate");
        PPTemplate = (String) params.get("PPTemplate");
        WSPTemplate = (String) params.get("WSPTemplate");
        NDMScriptFile = (String) params.get("NDMScriptFile");
        fileId = Integer.parseInt((String) params.get("FILEID"));
        vendor_id = (String) params.get("vendor_id_attribute");

        paddedFileId = (new DecimalFormat("000000000")).format(fileId);
        stockingFileID = paddedFileId + "_" + vendor_id + "_STOCKING.xml";
        System.out.println("stockingFileID: " + stockingFileID);

    }

    /**
     * 
     * This method will finalize the application
     * @ param doc - XML Document object
     * throws Exception if error occurs
     */

    public void finalizeModule(Document doc) throws Exception
    {

        FileOutputStream fosError = null;
        FileOutputStream fosOutput = null;

        // if no error occurs print xml to hard disk
        try
        {
            //print to the file
            PsmsUtility.printXML(doc, psms_folder_path + File.separator + sendFolder, stockingFileID);
        }
        catch (Exception e)
        {
            System.out.println(" Error occured while printing XML to Hard Disk...");
            throw e;
        }

        //The following method calls the batch file for NDM (transfer) 
        try
        {
            fosError = new FileOutputStream(psms_folder_path + File.separator + "error_log.txt", true);
            fosOutput = new FileOutputStream(psms_folder_path + File.separator + "output_log.txt", true);
            System.out.println("resourceFolder + NDMScriptFile = " + resourceFolder + NDMScriptFile);
            System.out.println("psms_folder_path + File.separator + sendFolder = " + psms_folder_path + File.separator + sendFolder);
            System.out.println("stockingFileID = " + stockingFileID);
            
            Process p =
                Runtime.getRuntime().exec(new String[] { resourceFolder + NDMScriptFile, psms_folder_path + File.separator + sendFolder, stockingFileID });
            
            // Printing Error Messages & Output on Console
            StreamGobbler errorGobbler = new StreamGobbler(p.getErrorStream(), "ERROR", fosError);
            StreamGobbler outputGobbler = new StreamGobbler(p.getInputStream(), "OUTPUT", fosOutput);
            
            //Launch Threads
            errorGobbler.start();
            outputGobbler.start();
            
            p.waitFor();
            System.out.println("exit value = " + p.exitValue());
            
            if (p.exitValue() != 0)
                throw new Exception("Aborting whole process because exit value != 0");
            
        }
        catch (java.io.IOException e)
        {
            System.out.println("Error IOException while calling script file: " + e);
            e.printStackTrace();
            throw e;
        }
        catch (java.lang.InterruptedException e1)
        {
            System.out.println("Error InterruptedException while calling script file: " + e1);
            throw e1;
        }
        finally
        {
            if (fosError != null)
            {
                fosError.flush();
                fosError.close();
            }
            if (fosOutput != null)
            {
                fosOutput.flush();
                fosOutput.close();
            }
        }

        params.setProperty("FILEID", String.valueOf(++fileId));

        try
        {
            params.store(new FileOutputStream(resourceFolder + propertyFile), null);
        }
        catch (IOException ioe)
        {
            System.out.println(" not able to write fileID to the properties file....");
            throw ioe;
        }

        System.out.println("Writing XML file to " + psms_folder_path + File.separator + sendFolder);

        // if no error occurs before printing set the Dirty records to 'N'
        rs.beforeFirst();
        while (rs.next())
        {
            rs.updateString("DIRTY", "N");
            rs.updateString("LAST_UPDATED_BY", "SCM-NDM");
            rs.updateDate("LAST_UPDATED_DATE", new java.sql.Date(new Date().getTime()));
            rs.updateString("FILE_NAME", stockingFileID);
            rs.updateRow();
        }
        MailUtils.sendMailWithHandledExceptions(
            "vzwtech@netpace.com",
            "vzwtech@netpace.com",
            "SUCCESS! in sending SCM, from Cisco DB: " + stockingFileID,
            null,
            PsmsUtility.getXML(doc));
    }

    /*
     * This method retreives the records from CAT table
     * @ param fileId String
     * @ param Properties from property File
     * @ param doc which needs to be modified with PC nodes
     * throws Exception if error occurs
     */

    private Document getProductCategories() throws Exception
    {

        String sql =
            "Select "
                + "VENDOR_ID, PRODUCT_VERSION, SCM_PROD_CAT_ID, VZW_PRODUCT_CATEGORY, "
                + "CATEGORIZATION, DIRTY, VENDOR_PRODUCT_CODE, DOWNLOAD_URL, "
                + "LAST_UPDATED_BY, LAST_UPDATED_DATE, FILE_NAME "
                + "from AIMS_SCM_PROD_CAT WHERE DIRTY='Y'";

        Document doc = null;

        try
        {

            dbConn = getDBConnection(dbURL, dbUser, dbPassword);
            dbConn.setAutoCommit(false);

            System.out.println("sql: " + sql);

            rs = dbConn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(sql);

            try
            {
                // create Document
                doc = createXML();
            }
            catch (Exception e)
            {
                System.out.println(" error occured while creating XML...");
                throw e;
            }

        }
        catch (Exception e)
        {
            System.out.println("error occured while retreiving dirty records..");
            throw e;
        }

        return doc;

    }

    /**
     * This function accepts a Resultset object and returns the XML that has been generated
     * using this Resultset object.
     * @param Resultset - Resultset used to generate the XML
     * @param fileId - File ID
     * @param Properties params
     * @param Document doc to be modified
     * @see java.util.properties
     * @return the XML using the given Resultset. The return type is the Document Object
     */
    public Document createXML() throws Exception
    {

        Document doc = PsmsUtility.loadFile(resourceFolder + XMLTemplate);
        Document PurPriceRevModelDoc = PsmsUtility.loadFile(resourceFolder + PPTemplate);
        Document wholeSaleRevModelDoc = PsmsUtility.loadFile(resourceFolder + WSPTemplate);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        formatter.setTimeZone(zoneGMT);

        String fileDate = formatter.format(new java.util.Date());

        //Get total records fetched
        rs.last();
        int recCount = rs.getRow();
        int curRec = recCount;

        //If no records found, abort operation
        if (recCount == 0)
        {
            noDirtyRecordsFound = true;
            throw new Exception("Record Count of PCs flagged as 'Dirty' is Zero");
        }

        NodeList rootNodeList = doc.getElementsByTagName("ProductCategoryDoc");
        Element rootNode = (Element) rootNodeList.item(0);

        NodeList endNodeList = doc.getElementsByTagName("ProductCategoryDoc.End");
        Element documentEndNode = (Element) endNodeList.item(0);

        rootNode.setAttribute("fileID", paddedFileId);
        rootNode.setAttribute("fileDate", fileDate);
        rootNode.setAttribute("vendor_id", String.valueOf(vendor_id));

        Element purPriceModelElement = (Element) PurPriceRevModelDoc.getElementsByTagName("Vendor_Pricing").item(0);
        Element wholeSaleModelElement = (Element) wholeSaleRevModelDoc.getElementsByTagName("Vendor_Pricing").item(0);

        rs.beforeFirst();

        // if there's no record found in DB, remove the PC node
        // and return the Document object

        if (recCount == 0)
        {

            Node removedNode = rootNode.removeChild(rootNode.getChildNodes().item(1));
            return doc;

        }

        int count = 0;

        BigDecimal wholeSaleSum = new BigDecimal("0.00");
        BigDecimal VSRPSum = new BigDecimal("0.00");
        BigDecimal purchasePriceSum = new BigDecimal("0.00");

        Element recordElement = null;
        Element childElement = null;
        Text textNode = null;
        Node childNode;
        Element endNode = (Element) doc.getElementsByTagName("ProductCategoryDoc.End").item(0);
        NodeList childElements = null;
        String textData;

        Element searchFrom = null;

        Element currentElement = null;

        while (rs.next())
        {
            count++;

            if (count == 1)
            {
                recordElement = (Element) doc.getElementsByTagName("PC").item(0);
                Element test = (Element) recordElement.getElementsByTagName("Vendor_Pricing").item(0);
            }
            else
            {
                recordElement = (Element) doc.getElementsByTagName("PC").item(0).cloneNode(true);
                rootNode.insertBefore(recordElement, endNode);
            }

            recordElement.setAttribute("recID", String.valueOf(count));

            //populating VENDOR_PRODUCT_ID NODE

            searchFrom = (Element) recordElement.getElementsByTagName("Vendor_Product_ID").item(0);

            textData = rs.getString(tagsProps.getProperty("Vendor_Product_Version".toUpperCase()));
            updateTextNode("Vendor_Product_Version", searchFrom, textData, 0);

            textData = rs.getString(tagsProps.getProperty("Vendor_Product_Code".toUpperCase()));
            updateTextNode("Vendor_Product_Code", searchFrom, textData, 0);

            textData = rs.getString(tagsProps.getProperty("Vendor_Product_Display".toUpperCase()));
            updateTextNode("Vendor_Product_Display", searchFrom, textData, 0);

            //populating DOWNLOAD_URL NODE

            searchFrom = (Element) recordElement.getElementsByTagName("Vendor_Item_Info").item(0);

            textData = rs.getString(tagsProps.getProperty("Download_URL".toUpperCase()));
            updateTextNode("Download_URL", searchFrom, textData, 0);

            //populating VENDOR_ID 

            searchFrom = (Element) recordElement.getElementsByTagName("Vendor_Id").item(0);

            textData = rs.getString(tagsProps.getProperty("Vendor_Id".toUpperCase()));
            updateTextNode("Vendor_Id", searchFrom, textData, 0);

            //populating CATEGORIZATION 

            searchFrom = (Element) recordElement.getElementsByTagName("Product_Type").item(0);

            textData = rs.getString(tagsProps.getProperty("Categorization".toUpperCase()));
            updateTextNode("Categorization", searchFrom, textData, 0);

            //populating PRICING 

            String revenueModelSQL =
                "Select PRICE_PLAN_TYPE, PURCHASE_PRICE_VALUE, PURCHASE_PRICE_CURRENCY, VENDOR_SPLIT_PERCENTAGE, WHOLE_SALE_PRICE_VALUE, WHOLE_SALE_PRICE_CURRENCY, VSRP_PRICE_VALUE, VSRP_SALE_PRICE_CURRENCY, LICENSES from AIMS_SCM_PROD_CAT_PRICING where SCM_PROD_CAT_ID = "
                    + rs.getInt("SCM_PROD_CAT_ID");

            Statement revStmt = dbConn.createStatement();
            ResultSet revRS = revStmt.executeQuery(revenueModelSQL);

            int revCounter = 0;
            while (revRS.next())
            {

                //remove all pricing models from this RECORD NOD
                if (revCounter == 0)
                {
                    try
                    {
                        while (true)
                        {
                            Element a = (Element) recordElement.getElementsByTagName("Vendor_Pricing").item(0);
                            Element b = (Element) a.getParentNode();
                            b.removeChild((Node) a);
                        }
                    }
                    catch (NullPointerException npe)
                    {}
                }

                if (revRS.getString("PRICE_PLAN_TYPE").equalsIgnoreCase("W"))
                {
                    String VSRPValue = null;
                    String VSRPCurrency = null;

                    Element wholeSaleModelElementClone = (Element) wholeSaleModelElement.cloneNode(true);

                    VSRPValue = (revRS.getString(tagsProps.getProperty("VSRP_PRICE".toUpperCase())));
                    VSRPCurrency = (revRS.getString(tagsProps.getProperty("VSRP_SALE_PRICE_CURRENCY".toUpperCase())));

                    if (VSRPValue != null && VSRPCurrency != null && VSRPValue.trim() != "" && VSRPCurrency.trim() != "")
                    {

                        VSRPSum = VSRPSum.add(new BigDecimal(VSRPValue));

                        searchFrom = (Element) wholeSaleModelElementClone.getElementsByTagName("VSRP").item(0);
                        textData = VSRPValue;
                        updateTextNode("value", searchFrom, textData, 0);

                        textData = VSRPCurrency;
                        updateTextNode("currency_type", searchFrom, textData, 0);
                    }
                    else
                    {
                        Element child = (Element) wholeSaleModelElementClone.getElementsByTagName("VSRP").item(0);
                        Element parent = (Element) child.getParentNode();
                        parent.removeChild(child);
                    }

                    searchFrom = (Element) wholeSaleModelElementClone.getElementsByTagName("Whole_Sale_Price").item(0);
                    textData = revRS.getString(tagsProps.getProperty("WHOLE_SALE_PRICE".toUpperCase()));
                    updateTextNode("value", searchFrom, textData, 0);

                    wholeSaleSum = wholeSaleSum.add(new BigDecimal(textData));

                    textData = revRS.getString(tagsProps.getProperty("WHOLE_SALE_PRICE_CURRENCY".toUpperCase()));
                    updateTextNode("currency_type", searchFrom, textData, 0);

                    searchFrom = (Element) wholeSaleModelElementClone.getElementsByTagName("License").item(0);
                    textData = revRS.getString(tagsProps.getProperty("LICENSES".toUpperCase()));
                    updateTextNode("Licenses", searchFrom, textData, 0);

                    Element parent = (Element) recordElement.getElementsByTagName("Vendor_Item_Info").item(0);
                    Node test = parent.getOwnerDocument().importNode((Node) wholeSaleModelElementClone, true);
                    parent.appendChild(test);
                }
                else
                {
                    String splitPercentage = null;

                    Element purPriceModelElementClone = (Element) purPriceModelElement.cloneNode(true);

                    splitPercentage = (revRS.getString(tagsProps.getProperty("VENDOR_SPLIT_PERCENTAGE".toUpperCase())));

                    if (splitPercentage != null && splitPercentage.trim() != "")
                    {
                        searchFrom = purPriceModelElementClone;
                        textData = splitPercentage;
                        updateTextNode("Vendor_Split_Percentage", searchFrom, textData, 0);
                    }
                    else
                    {
                        Element child = (Element) purPriceModelElementClone.getElementsByTagName("Vendor_Split_Percentage").item(0);
                        Element parent = (Element) child.getParentNode();
                        parent.removeChild(child);
                    }

                    searchFrom = (Element) purPriceModelElementClone.getElementsByTagName("Vendor_Purchase_Price").item(0);
                    textData = revRS.getString(tagsProps.getProperty("PURCHASE_PRICE".toUpperCase()));
                    updateTextNode("value", searchFrom, textData, 0);
                    purchasePriceSum = purchasePriceSum.add(new BigDecimal(textData));

                    textData = revRS.getString(tagsProps.getProperty("PURCHASE_PRICE_CURRENCY".toUpperCase()));
                    updateTextNode("currency_type", searchFrom, textData, 0);

                    searchFrom = (Element) purPriceModelElementClone.getElementsByTagName("License").item(0);
                    textData = revRS.getString(tagsProps.getProperty("LICENSES".toUpperCase()));
                    updateTextNode("Licenses", searchFrom, textData, 0);

                    Element parent = (Element) recordElement.getElementsByTagName("Vendor_Item_Info").item(0);
                    Node test = parent.getOwnerDocument().importNode((Node) purPriceModelElementClone, true);
                    parent.appendChild(test);
                }

                revCounter++;

            } //End of While Loop

        } //End of While Loop

        documentEndNode.setAttribute("Purchase_Price_Sum", purchasePriceSum.toString());
        documentEndNode.setAttribute("VSRP_Sum", VSRPSum.toString());
        documentEndNode.setAttribute("Wholesale_Sum", wholeSaleSum.toString());
        documentEndNode.setAttribute("recCount", String.valueOf(count));

        return doc;

    }

    public boolean updateTextNode(String nodeName, Element searchFrom, String data, int position) throws Exception
    {
        Element currentElement = (Element) searchFrom.getElementsByTagName(nodeName).item(position);

        boolean result = false;

        if (currentElement != null && currentElement.getNodeType() == Node.ELEMENT_NODE)
        {
            Text textNode = (Text) (currentElement.getFirstChild());

            textNode.setData(data);

            if (textNode != null && textNode.getNodeValue() == null)
            {
                result = false;
            }
            else
            {
                result = true;
            }
        }

        return result;
    }

    public static Connection getDBConnection(String dbURL, String userName, String password) throws Exception
    {
        try
        {
            OracleDataSource ods = new OracleDataSource();
            ods.setURL(dbURL);
            ods.setUser(userName);
            ods.setPassword(password);

            return ods.getConnection();

        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }
    }
}
