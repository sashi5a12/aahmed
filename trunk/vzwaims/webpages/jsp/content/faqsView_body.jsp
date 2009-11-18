<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@ include file="/common/error.jsp" %>

<div id="contentBox" style="float:left">
    <DIV class="homeColumnTab lBox">

        <div style="margin-bottom:10px">
            <table cellpadding="2">
                <tr>
                    <td valign="middle">
                        <img src="images/greyRndArrowL.gif" border="0">
                    </td>
                    <td valign="middle">
                        <a href='/aims/FAQsViewList.do' class="a">Back to Main FAQs</a>
                    </td>
                </tr>
            </table>
        </div>

        <DIV class="headLeftCurveblk"></DIV>
        <H1><%= request.getAttribute("faqCategoryName")%>
        </H1>

        <DIV class="headRightCurveblk"></DIV>


        <DIV class="contentbox">

            <table width="100%" cellpadding="0" cellspacing="0" class="GridGradient">
                <tr>
                    <th class="blulink">
                        <logic:empty name="aimsFAQTopics">
                            <bean:message key="TopicsForm.NoFAQs" bundle="com.netpace.aims.action.CONTENT_MESSAGE"/>
                        </logic:empty>
                        <ul>
                            <logic:iterate id="faqTopic" name="aimsFAQTopics">
                                <li>
                                    <a name="<bean:write name="faqTopic" property="faqTopicId"/>h" href='#<bean:write name="faqTopic" property="faqTopicId"/>'>
                                        <bean:write name="faqTopic" property="faqTopicQuestion"/>
                                    </a>
                                </li>
                                <div style="margin-top:5px"/>
                            </logic:iterate>
                        </ul>
                    </th>
                </tr>
                <logic:notEmpty name="aimsFAQTopics">
                    <tr>
                        <td><strong><%= request.getAttribute("faqCategoryName") + " FAQ Details"%>
                        </strong></td>
                    </tr>
                    <tr><td>&nbsp;</td></tr>
                </logic:notEmpty>
                <tr>
                    <td>
                        <ul>
                            <logic:iterate id="faqTopic" name="aimsFAQTopics">
                                <li>
				                    <a name='<bean:write name="faqTopic" property="faqTopicId"/>'></a>
                                    <strong><bean:write name="faqTopic" property="faqTopicQuestion"/></strong> <br/>
                                    <div style="margin-top:5px"/>
                                    <bean:write name="faqTopic" property="faqTopicAnswer"/>
                                    <br/>
                                    <a href="#<bean:write name="faqTopic" property="faqTopicId"/>h">Back to top</a>
                                </li>
                                <div style="margin-top:10px"/>
                            </logic:iterate>
                        </ul>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</div>