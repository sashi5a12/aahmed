<%@ page import="com.netpace.vzdn.header.GlobalNavProperties"%>

<%
    GlobalNavProperties globalNavPropsForFooter = GlobalNavProperties.getInstance();

    String footerAboutUrl = globalNavPropsForFooter.getProperty("about.url");
    String footerPrivacyUrl = globalNavPropsForFooter.getProperty("privacy.url");
    String footerLegalNoticesUrl = globalNavPropsForFooter.getProperty("legalNotices.url");
%>

<div class="gn_clear18"></div>
<!-- FOOTER START -->
<DIV class="noindex" id="gn_footer">
    <DIV id="gn_footerLinks">
        <DIV class="gn_footerCopyright noindex">&copy; 2009 Verizon Wireless</DIV>
        <A href="<%=footerAboutUrl%>" target="_blank">About Us</A>
        <SPAN class="gn_pipe">|</SPAN>
        <A href="<%=footerPrivacyUrl%>" target="_blank">Privacy</A>
        <SPAN class="gn_pipe">|</SPAN>
        <A href="<%=footerLegalNoticesUrl%>" target="_blank">Legal Notices</A>
    </DIV>
</DIV>
<!-- FOOTER END -->