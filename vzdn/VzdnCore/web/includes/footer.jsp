<%@ page import="com.netpace.vzdn.util.GlobalNavProperties"%>

<%
    GlobalNavProperties globalNavPropsForFooter = GlobalNavProperties.getInstance();
    String footerServerUrl = globalNavPropsForFooter.getProperty("server.url");
    String footerContextName = globalNavPropsForFooter.getProperty("vzdncore.contextName");

    String footerAboutUrl = footerServerUrl+"/"+footerContextName +"/"+globalNavPropsForFooter.getProperty("about.url");
    String footerPrivacyUrl = footerServerUrl+"/"+footerContextName +"/"+globalNavPropsForFooter.getProperty("privacy.url");
    String footerLegalNoticesUrl = footerServerUrl+"/"+footerContextName +"/"+globalNavPropsForFooter.getProperty("legalNotices.url");
%>

<div class="gn_clear18"></div>
<!-- FOOTER START -->
<DIV class="noindex" id="gn_footer">
    <DIV id="gn_footerLinks">
        <DIV class="gn_footerCopyright noindex">&copy; 2009 Verizon Wireless</DIV>
        <A href="<%=footerAboutUrl%>">About</A>
        <SPAN class="gn_pipe">|</SPAN>
        <A href="<%=footerPrivacyUrl%>">Privacy</A>
        <SPAN class="gn_pipe">|</SPAN>
        <A href="<%=footerLegalNoticesUrl%>">Legal Notices</A>
    </DIV>
</DIV>
<!-- FOOTER END -->