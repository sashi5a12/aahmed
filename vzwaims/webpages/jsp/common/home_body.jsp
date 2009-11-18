<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<!-- CONTENT START HERE -->
<div id="contentBox">
<DIV>
    <script type="text/javascript">
        AC_FL_RunContent('codebase', 'http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,19,0', 'width', '810', 'height', '242', 'wmode', 'transparent', 'src', 'Flash/ZON_Flash', 'quality', 'high', 'pluginspage', 'http://www.macromedia.com/go/getflashplayer', 'movie', 'Flash/ZON_Flash');
        //end AC code
    </script>
    <noscript>
        <object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
                codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,19,0"
                width="810" height="242">
            <param name="movie" value="Flash/ZON_Flash.swf">
            <param name="quality" value="high">
            <param name="wmode" value="transparent"/>
            <embed wmode='transparent' src="Flash/ZON_Flash.swf" quality="high"
                   pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash"
                   width="810" height="242"></embed>
        </object>
    </noscript>
</DIV>
<DIV>
<div class="contentBox2">
    <div class="floatL marginR" style="width:648px;">
        <div>
            <!-- INFOBASE BOX START HERE -->
            <DIV class="homeColumn mBox marginR">
                <DIV class="headLeftCurveblk"></DIV>
                <H1>InfoBase</H1>

                <DIV class="headRightCurveblk"></DIV>
                <DIV class="box">
                    <UL>
                        <LI><A href="http://responsibility.verizon.com/contentpolicy/" target="_blank">BREW Content Guidelines</A>
                        <LI><A href="http://support.vzw.com/faqs/TXT%20messaging/faq.html" target="_blank">SMS FAQs</A>
                        <LI><A href="http://support.vzw.com/faqs/Picture%20Messaging/faq_pixmessaging.html" target="_blank">MMS FAQs</A>
                        <LI><A href="/aims/public/wapBenef.jsp">WAP Benefits</A>
                        <LI><A href="/aims/public/EntrOppr.jsp">Enterprise Opportunities</A>
                        <LI><A href="/aims/public/menu/lbs/LBSFAQ.jsp">LBS FAQs</A>
                        <LI><A href="/aims/public/vzappzone/vzAppZoneFAQ.jsp">VZAppZone FAQs</A>
                    </UL>
                </DIV>
            </DIV>
            <!-- INFOBASE BOX END HERE -->
            <!-- LATEST DEVELOPMENTS BOX START HERE -->
            <DIV class="homeColumn mBox">
                <DIV class="headLeftCurveblk"></DIV>
                <H1>Latest Developments</H1>

                <DIV class="headRightCurveblk"></DIV>
                <DIV class="box">
                    <UL>
                        <LI>
                            <A href="http://www.adobe.com/devnet/devices/dev_program/" target="_blank">Adobe Mobile Developer Program</A>
                        </LI>
                        <LI>
							<A href="/aims/public/vzappzone/vzAppZoneLanding.jsp">VZAppZone Developer Program</A>
                        </LI>
                        <LI>
							<img src="images/greyRndArrow.gif" width="17" height="17" border="0" align="absmiddle"/>	                       
                        	<A href="http://news.vzw.com/" target="_blank"><strong>Verizon News</strong></A>
                        </LI>
                    </UL>
                </DIV>
            </DIV>
            <!-- LATEST DEVELOPMENTS BOX END HERE -->
        </div>
        <div>
            <!-- TOP TEN GAMES BOX START HERE -->
            <DIV class="homeColumn mBox marginT marginR">
                <DIV class="headLeftCurveblk"></DIV>
                <H1>Top Ten Games</H1>

                <DIV class="headRightCurveblk"></DIV>
                <DIV class="box">
                    <UL>
                    	<c:forEach var="game" items="${requestScope.topTenGames}">                    		
                    		<LI>
                    		<c:choose>
                    			<c:when test="${not empty game.baseUrl or not empty game.url}">
                    				<A href="javascript:MM_openBrWindow('<c:out value='${game.baseUrl}' /><c:out value='${game.url}' />','appDetail','scrollbars=yes,resizable=yes,width=800,height=700')"><c:out value="${game.name}" escapeXml="false" /></A>	
                    			</c:when>
	                    		<c:otherwise>
	                    			<A href="#" onclick="return false;"><c:out value="${game.name}" escapeXml="false" /></A>	
	                    		</c:otherwise>
                    		</c:choose>
                    		</LI>
                    	</c:forEach>
                    </UL>
                </DIV>
            </DIV>
            <!-- TOP TEN GAMES BOX END HERE -->
            <!-- CONFERENCES BOX START HERE -->
            <DIV class="homeColumn mBox marginT">
                <DIV class="headLeftCurveblk"></DIV>
                <H1>Conferences</H1>

                <DIV class="headRightCurveblk"></DIV>
                <DIV class="box">
                    <UL>
                        <c:forEach var="conf" items="${requestScope.conferences}">                    		
                    		<LI>
                    		<c:choose>
                    			<c:when test="${not empty conf.url}">
                    				
                    				<A href="<c:out value='${conf.url}'/>" target="_blank"><c:out value="${conf.name}" escapeXml="false" /></A>	
                    			</c:when>
	                    		<c:otherwise>
	                    			<A href="#" onclick="return false;"><c:out value="${conf.name}" escapeXml="false" /></A>	
	                    		</c:otherwise>
                    		</c:choose>
                    		</LI>
                    	</c:forEach>
                    </UL>
                </DIV>
            </DIV>
            <!-- CONFERENCES BOX END HERE -->
        </div>
    </div>
    <div>
        <!-- SITE FAQS BOX START HERE -->
        <DIV class="homeColumn sBox">
            <DIV class="headLeftCurveblk"></DIV>
            <H1>Site FAQs</H1>

            <DIV class="headRightCurveblk"></DIV>
            <DIV class="box"> In this section you can find information on <a href="/aims/public/siteFAQ.jsp">Frequently Asked Questions</a>
                on different topic related to using this web site. You can find information on topics like
                Registering. Submitting Application, Tracking Status etc.
            </DIV>
        </DIV>
        <!-- SITE FAQS BOX END HERE -->
    </div>
</DIV>
</div>
</div>
<!-- CONTENT END HERE -->