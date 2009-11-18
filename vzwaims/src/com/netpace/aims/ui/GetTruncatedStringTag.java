package com.netpace.aims.ui;

import com.netpace.aims.util.StringFuncs;
import org.apache.log4j.Logger;
import org.apache.struts.util.RequestUtils;
import org.apache.struts.util.ResponseUtils;
import org.apache.commons.lang.StringUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * This tag will be used to show truncated string
 * if length of text is greater than given length,
 * then it will return html output having full text in tooltip of span with truncated string in its body
 */
public class GetTruncatedStringTag extends TagSupport {

    static Logger log = Logger.getLogger(GetTruncatedStringTag.class.getName());

    private String name = null;
    private String property = null;
    private String scope = null;
    private boolean filter = true;
    private boolean ignore = false;
    private int maxLength = 30;
    private String fullTextVarName = null;//javascript full text variable name, it will be used to show full text in Tip()

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public boolean isFilter() {
        return filter;
    }

    public void setFilter(boolean filter) {
        this.filter = filter;
    }

    public boolean isIgnore() {
        return ignore;
    }

    public void setIgnore(boolean ignore) {
        this.ignore = ignore;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public String getFullTextVarName() {
        return fullTextVarName;
    }

    public void setFullTextVarName(String fullTextVarName) {
        this.fullTextVarName = fullTextVarName;
    }

    public int doStartTag() throws JspException {
        // Look up the requested bean (if necessary)
        if (ignore) {
            if (RequestUtils.lookup(pageContext, name, scope) == null)
                return (SKIP_BODY);  // Nothing to output
        }

        // Look up the requested property value
        Object value =
            RequestUtils.lookup(pageContext, name, property, scope);

        if (value == null)
            return (SKIP_BODY);  // Nothing to output

        // Convert value to the String with some formatting
        String output = getEllipseText((String)value, filter) ;
        
        ResponseUtils.write(pageContext, output);

        // Continue processing this page
        return (SKIP_BODY);
    }

    private String getEllipseText(String data, boolean filter) {
        StringBuffer ellipseStr = new StringBuffer();
        String fullText = data;
        if(filter) {
            fullText = ResponseUtils.filter(data);
        }
        if(data.length()>maxLength) {
            if(!StringFuncs.isNullOrEmpty(fullTextVarName)) {
                //if javascript variable name is available, then store full text in js variable and show it in Tip()
                ellipseStr.append("<script>var ").append(fullTextVarName).append(" = '").append(fullText).append("';").append("</script>");
                ellipseStr.append("<span onmouseover=\"return Tip(").append(fullTextVarName).append(")\">");
            }
            else {
                //if javascript variable name is not available, then show full text in Tip()
                ellipseStr.append("<span onmouseover=\"return Tip('").append(fullText).append("')\">");
            }
                ellipseStr.append(ResponseUtils.filter(StringFuncs.getTruncatedString(maxLength, data)));
                ellipseStr.append("</span>");
        }
        else {
            ellipseStr.append(fullText);
        }
        return ellipseStr.toString();
    }

    public void release() {
        super.release();
        name = null;
        property = null;
        scope = null;
        filter = true;
        ignore = false;
        maxLength = 30;//default to 30 chars
    }

}
