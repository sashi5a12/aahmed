<html>
 
 <%@ include file="../../../commons/taglibs.jsp"%>
 
 
<head>

<meta name="mainTitle" content="Authorization Required" />

	<title>Authorization Required</title>



</head>

 
 
 
<body>

<div id=homepageWrapper >
    <div id=bodyWrapper>
      <div id=homepageContainer>
   <div>
          </div>

     
        <div id="contentCol">
			
            <div class="box653 mR10">
            <div class="box2">
                <div class="boxTop box653"><img src="images/shared/elements/boxCLT.jpg" width="6" height="6"></div>
                <div class="boxContent">
                <!-- POD CONTENT START HERE -->
                <div class="boxContBM">
                            <div class="subHead">Authorization Required</div>
                            <div class="txtContent"><br />The page you have tried to access is a restricted resource. You do not have enough privileges to view this page.
                                <br />
                                <br />
                                <br />
								<s:form name="homePageForm">
									<BUTTON class="input_primary mR5"
													onClick="javascript:takeToHomePage(); return false;">
											<SPAN><SPAN><SPAN>Back&nbsp;To&nbsp;Home&nbsp;Page</SPAN> </SPAN> </SPAN>
									</BUTTON>
								</s:form>	

								
                              
                              <div class="clear"></div>
                              
                              
                              </div>
                          </div>
                <!-- POD CONTENT END HERE -->
                </div>
                <div class="boxBottom"><img src="images/shared/elements/boxCLB.jpg" width="6" height="6"></div>
            </div>
        </div>
            
            
            
			</div>
          
        </div>
        <!-- VIDEO SLIDER START -->
          
        <!-- VIDEO SLIDER END -->
      </div>
    </div>
	
 <script type="text/javascript">

function takeToHomePage(){
	document.homePageForm.action="home.action";
	document.homePageForm.submit();
}

</script>	

</body>
</html>