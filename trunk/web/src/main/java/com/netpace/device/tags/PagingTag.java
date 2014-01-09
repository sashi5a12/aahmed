/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netpace.device.tags;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Hamza Ghayas
 */
public class PagingTag extends TagSupport{
    
    private Log log =LogFactory.getLog(PagingTag.class);
    List list;
    Boolean ajax= Boolean.FALSE;
    String cssTableClass;
    String cssTableHeaderClass;
    String cssTableRowOddClass;
    String cssTableRowEvenClass;

      private PageContext pageContext;

    @Override
    public int doStartTag() throws JspException {
        try {
            
            pageContext.setAttribute( "list", list );
            pageContext.setAttribute( "cssTableClass", cssTableClass);
            pageContext.setAttribute( "cssTableHeaderClass", cssTableHeaderClass);
            pageContext.setAttribute( "cssTableRowOddClass", cssTableRowOddClass);
            pageContext.setAttribute( "cssTableRowEvenClass", cssTableRowEvenClass);
            
            HttpServletResponse response = (HttpServletResponse)pageContext.getResponse();
            response.sendRedirect("WEB-INF/tag-pages/paging-tag.jsp");
        } catch (IOException ex) {
            log.error(ex);
        }
        
        return SKIP_BODY;
    }
    
    
    public Boolean getAjax() {
        return ajax;
    }
    public void setAjax(Boolean ajax) {
        this.ajax = ajax;
    }
    public String getCssTableClass() {
        return cssTableClass;
    }
    public void setCssTableClass(String cssTableClass) {
        this.cssTableClass = cssTableClass;
    }
    public String getCssTableHeaderClass() {
        return cssTableHeaderClass;
    }
    public void setCssTableHeaderClass(String cssTableHeaderClass) {
        this.cssTableHeaderClass = cssTableHeaderClass;
    }
    public String getCssTableRowEvenClass() {
        return cssTableRowEvenClass;
    }
    public void setCssTableRowEvenClass(String cssTableRowEvenClass) {
        this.cssTableRowEvenClass = cssTableRowEvenClass;
    }
    public String getCssTableRowOddClass() {
        return cssTableRowOddClass;
    }
    public void setCssTableRowOddClass(String cssTableRowOddClass) {
        this.cssTableRowOddClass = cssTableRowOddClass;
    }
    public List getList() {
        return list;
    }
    public void setList(List list) {
        this.list = list;
    }
    
    public void setPageContext(PageContext pageContext) {
        this.pageContext = pageContext;
    }
}
