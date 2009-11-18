<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr> 
    	<td width="185">
    		&nbsp;
    	</td>
    	<td width="20">&nbsp;</td>
		<td width="100%">
			<span class="aimsmasterheading">Advanced Devices</span>               
		</td>
	</tr>
	<tr>
    	<td width="185" height="100%" valign="top" bgcolor="#EBEBEB">
            <table width="185" height="100%" border="0" cellspacing="0" cellpadding="0">
           		<tr>
               		<td>
		          		<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
							<tr><td width="100%">
								<table class="sectable" width="100%" height="100%">
					            	<tr class="sectitle">
					            		<td valign="top" class="aimssecheading">Advanced Devices Links</td>
					            	</tr>
					            	<tr>
					            		<td><img src="/aims/images/arrow.gif" width="11" height="14 border="0"><a href="/aims/public/AdvDevLanding.jsp" class="a">Advanced Devices Home</a></td>
					            	</tr>
									<tr>
					            		<td><img src="/aims/images/arrow.gif" width="11" height="14 border="0"><a href="/aims/public/AdvDevPalmOne.jsp" class="a">Palm</a></td>
					            	</tr>	
					            	<tr>
					            		<td><img src="/aims/images/arrow.gif" width="11" height="14 border="0"><a href="/aims/public/AdvDevMSWindowsMobile2002_03.jsp" class="a">Windows Mobile 2002 & 2003</a></td>
					            	</tr>
					            	<tr>
					            		<td><img src="/aims/images/arrow.gif" width="11" height="14 border="0"><a href="http://msdn.microsoft.com/mobility/windowsmobile/partners/mobile2market/default.aspx" class="a" target="_blank">Microsoft Mobile 2 Market</a></td>
					            	</tr>
					            	<tr>
					            		<td><img src="/aims/images/arrow.gif" width="11" height="14 border="0"><a href="http://msdn.microsoft.com/mobility/windowsmobile/howto/windowsmobile5/default.aspx" class="a" target="_blank">Microsoft Windows Mobile 5.0</a></td>
					            	</tr>
					            	<tr>
					            		<td><img src="/aims/images/arrow.gif" width="11" height="14 border="0"><a href="http://msdn.microsoft.com/mobility/windowsmobile/default.aspx" class="a" target="_blank">Windows Mobile General Information</a></td>
					            	</tr>
					            	<tr>
					            		<td><img src="/aims/images/arrow.gif" width="11" height="14 border="0"><a href="http://msdn.microsoft.com/mobility/windowsmobile/partners/mobile2market/smartphoneapps/default.aspx" class="a" target="_blank">Windows Mobile Security Policy</a></td>
					            	</tr>
					            	<tr>
					            		<td><img src="/aims/images/arrow.gif" width="11" height="14 border="0"><a href="http://msdn.microsoft.com/mobility/windowsmobile/howto/kb/default.aspx" class="a" target="_blank">Windows Mobile White Papers</a></td>
					            	</tr>
					            	<tr height="100%">
					            		<td>&nbsp;</td>
					            	</tr>
								</table>
							</td></tr>
						</table>
					</td>
				</tr>
            </table> 	
    	</td>
    	<td width="20">&nbsp;</td>
      	<td align="center" valign="middle" bgcolor="#EBEBEB"> 
            <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
           		<tr>
               		<td>
		          		<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
							<tr><td width="100%" valign="top" height="100%">
								<table class="sectable" width="100%" height="100%">
									<tr class="sectitle"><td class="aimssecheading">Windows Mobile 5.0</td></tr>
									<tr><td width="100%">
									    Verizon Wireless has implemented the 1 tier model for Windows Mobile 5.0 Smartphone and Pocket PC Phone Edition devices. The following parameters have been selected: 
									    <br/>
											<ul>
												<li>Normal and trusted API's are accessible without code signing</li>
												<li>Prompt has been enabled, allowing End Users control of which application get installed or executed</li>
												<li>For Retail devices, RAPI access is restricted, but can be enabled by any Developer</li>
												<li>Some Registry and Metabase parameters are read-only and Privileged-Certificate restricted if modification is needed.</li>
											</ul>
												Developers desiring applications to install and execute without a prompt challenge, can do so by signing the application through Microsoft's Mobile 2 Market program. For more information click the Microsoft Mobile 2 Market link on the left.
												<br/><br/>
                                                <u><b>Developer Access</b></u> - Developers who require access to restricted area's of the device can <a href="http://msdn.microsoft.com/library/default.asp?url=/library/en-us/mobilesdk5/html/wce51howCreatingCpfFile.asp" class="a" target="blank">create a script</a> to modify the <a href="http://msdn.microsoft.com/library/default.asp?url=/library/en-us/mobilesdk5/html/wce51conDefaultSecurityPolicySettingsForWindowsMobile-BasedDevices.asp" class="a" target="blank">Remote-API (RAPI) Policy</a> to allow access from the PC to the device.  This script can be a .CPF if it is signed by the Verizon Wireless.  You can contact <a href="mailto:bdsteam@verizonwireless.com">bdsteam@verizonwireless.com</a> for further information. <b>Or</b> the script can be created as a .CAB and executed with a prompt challenge on a single device without requiring signing by Verizon Wireless.
                                                <br/><br/>
												<u><b>Enterprise Configuration</b></u> - Enterprises can now configure the device according to their own Enterprise policies.  This can be achieved by either End User acceptance of the new Enterprise policies upon execution, or by an Enterprise pushing a signed script to the device and executing automatically.
                                                <br/>
											<ul>
												<li>Using the End User prompt method:  This will allow Enterprises to issue CAB installations that are subject to End User acceptance upon installation:</li>
													<ul>
 												    	<li><a href="http://msdn.microsoft.com/mobility/windowsmobile/howto/windowsmobile5/install/default.aspx" class="a" target="blank">Create Enterprise_Cert_Install.CAB script</a>, push or download to device and execute</li>
													</ul>
												<li>Automatic installation method:  This process will allow an Enterprise to create a known identity with Verisign, sign an installation script, and silently install to an End User's device: </li>
												            <ul>
 												               <li><a href="http://www.verisign.com/products-services/security-services/code-signing/microsoft-smartphone-code-signing/index.html" class="a" target="blank"> Enroll in the VZW-Verisign Authenticated Content Signing (ACS)</a> for Microsoft</li>
 												               <li>Create or obtain an Enterprise Certificate Installation CPF file</li>
 												               <li>Sign the Enterprise_Cert_Install.CPF with Verisign ACS (VZW Privileged Certificate)</li>
 												               <li>Push or download Enterprise_Cert_Install.CPF on your Windows Mobile device, and execute</li>
 												               <li>The End User will get a 'Successful' message in your Text Message Inbox</li>
												            <ul/>
										    </ul>
										    <br/>
									</td>
                                    </tr>
            					</table>
	  						</td></tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>