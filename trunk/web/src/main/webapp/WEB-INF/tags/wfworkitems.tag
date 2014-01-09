<%@tag import="com.netpace.device.utils.enums.WorkflowAction"%>
<%@tag import="com.netpace.device.utils.enums.WorkflowStep"%>
<%@tag import="com.netpace.device.utils.VAPConstants"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="tg" tagdir="/WEB-INF/tags" %>
<%@attribute name="companyId" required="true" type="java.lang.Integer" %>
<%@attribute name="workitems" required="true" type="java.util.List" %>
<%@attribute name="offlineCertNdaId" required="true" type="java.lang.Integer" %>
<%@attribute name="offlineCertNdaName" required="true" type="java.lang.String" %>
<%@attribute name="loggedInUserRoles" required="true" type="java.util.List" %>

<spring:eval expression="T(com.netpace.device.utils.enums.WorkflowStep).CertificationNDA.toString()" var="STEP_CERTIFICATION_NDA"/>
<spring:eval expression="T(com.netpace.device.utils.enums.WorkflowStep).OfflineCertificationNDA.toString()" var="STEP_OFFLINE_CERTIFICATION_NDA"/>
<spring:eval expression="T(com.netpace.device.utils.VAPConstants).ROLE_PARTNER_USER" var="ROLE_PARTNER_USER"/>
<spring:eval expression="T(com.netpace.device.utils.VAPConstants).ROLE_SUPER_ADMIN" var="ROLE_SUPER_ADMIN"/>
<spring:eval expression="T(com.netpace.device.utils.VAPConstants).ROLE_VERIZON_ADMIN" var="ROLE_VERIZON_SYSTEM_ADMIN"/>
<spring:eval expression="T(com.netpace.device.utils.VAPConstants).ROLE_DEVICE_MARKETING" var="ROLE_DEVICE_MARKETING"/>
<spring:eval expression="T(com.netpace.device.utils.VAPConstants).ROLE_OFAC" var="ROLE_OFAC"/>
<spring:eval expression="T(com.netpace.device.utils.VAPConstants).ROLE_PARTNER_USER" var="ROLE_PARTNER_USER"/>
<spring:eval expression="T(com.netpace.device.utils.enums.WorkflowAction).Deny.toString()" var="ActionDeny"/>
<spring:eval expression="T(com.netpace.device.utils.VAPConstants).ACTION_SUSPEND" var="ActionSuspend"/>

<c:if test="${fn:contains(loggedInUserRoles, ROLE_SUPER_ADMIN) 
              || fn:contains(loggedInUserRoles, ROLE_VERIZON_SYSTEM_ADMIN)
              || fn:contains(loggedInUserRoles, ROLE_DEVICE_MARKETING)
              || fn:contains(loggedInUserRoles, ROLE_OFAC)
              || fn:contains(loggedInUserRoles, ROLE_PARTNER_USER)}">
    <c:if test="${empty workitems && !empty offlineCertNdaId && !empty offlineCertNdaName}">
    <form>
            <input type="hidden" id="id" name="id" value="0"/>
            <div class="grayBoxLayouts">
                <div class="threeColmnLayout">	
                    <h1 class="noBold">Offline Certification Agreement</h1>
                    <div class="columnOne" style="width:250px;">
                        <div class="redstar">*</div>
                        <label class="inputlabel">Certification Agreement Upload</label>
                        <span class="label-info-small">(.PDF)(Size=10MB)</span>
                        <div class="clearboth"></div>    
                        <div class="uploadeded-File">
                            <a 
                            id="download_pdf_sample_product"
                            title="${offlineCertNdaName}"
                            class="${partnerProcessVO.company.offlineCertNdaId}" 
                            href="return false;" >${offlineCertNdaName}</a> 
                            <div class="clearboth"></div>
                        </div>
                        
                        <div class="clearboth"></div>
                    </div>

                    <div class="clearboth"></div>
                </div>
            </div>
            <br />
    </form>
    </c:if>
</c:if>

<c:forEach items="${workitems}" var="workItem">
    <form:form name="process${workItem.title}Form" action="processWorkitem.do" method="post" modelAttribute="workItemVO">
        
    <input type="hidden" name="workflow.id" value="${workItem.workflow.id}"/>
    <input type="hidden" name="workflow.companyId" value="${workItem.workflow.companyId}"/>
    <input type="hidden" name="title" value="${workItem.title}"/>
    <input type="hidden" name="decision" value=""/>
    <input type="hidden" name="companyOfflineCertNdaId"/>

    <input type="hidden" id="id" name="id" value="0"/>
    <input type="hidden" id="pdf_sample_product.mediaId" name="pdf_sample_product.mediaId" value=""/>
    <input type="hidden" id="pdf_sample_product.fileName" name="pdf_sample_product.fileName" value=""/>
    <input type="hidden" id="pdf_sample_product.fileType" name="pdf_sample_product.fileType" value=""/>
    <input type="hidden" id="pdf_sample_product.fileFullName" name="pdf_sample_product.fileFullName" value=""/>
            
    <c:choose>
        <c:when test="${workItem.title == STEP_CERTIFICATION_NDA}">
            <c:if test="${fn:contains(loggedInUserRoles, ROLE_PARTNER_USER) 
                          || fn:contains(loggedInUserRoles, ROLE_SUPER_ADMIN)}">
            <a id="${workItem.title}" name="${workItem.title}"></a>
            <div class="grayBoxLayouts">
                <label class="inputlabel redColor" style="width:400px;">Verizon Smart Accessory Certification Agreement</label>
				<label class="inputlabel" style="margin-top:0px; clear:both; width:760px;">	                
				The Verizon Smart Accessory Certification Agreement contains important information about this program including your obligations related to the submission of products and ideas and responsibilities for testing. It describes your rights and limitations around the use of the certification and the use of marks in support of the program as well as your confidentiality obligations to Verizon Wireless. This contract applies to all smart accessory products and ideas submitted through this portal.

				<br/><br/>The person accepting this agreement represents and warrants that the company that will be party to this agreement: (a) is in good standing under the laws of the state of its incorporation or formation; (b) that the execution, delivery and performance of this Agreement have been duly authorized by all necessary company action to the extent applicable; and (c) the person signing this Agreement on its behalf is duly authorized to bind it to this Agreement.</p>  
                </label>
                <label class="inputlabel" style="margin-top:0px; clear:both; width:650px;">Read &amp; Agree to the agreement. <a href="${pageContext.request.contextPath}/resources/secure/VSAPCertificationAgreement.pdf" >Click here </a>to download pdf version of this agreement.</label>
                <textarea name="textarea6" class="textfield font fullwidth height80" style="height:150px;" readonly="true">                                       VERIZON SMART ACCESSORY
                                       CERTIFICATION AGREEMENT
                                                  FOR
                                              [DEVELOPER]


This Verizon Smart Accessory Certification Agreement ("Certification Agreement" or "Agreement") is made between [Developer's full company name], a [state] [corporation / limited liability company / general partnership], with offices at [Developer's address] ("Developer"), and Cellco Partnership d/b/a Verizon Wireless ("Verizon"), a Delaware general partnership, having an office at One Verizon Way, Basking Ridge, NJ 07920, on behalf of itself and for the benefit of its Affiliates.

1.        PURPOSE
The purpose of this Agreement is to set forth the terms and conditions pursuant to which (a) Verizon will certify accessories, based on reports from authorized test labs that are compatible with certain wireless devices and approve Developer's use of the Verizon Certification Mark as branded stickers affixed to the packaging, and in marketing and promotion, of such accessory; and (b) Developer will use the Verizon Certification Mark in conjunction with the sale, marketing and promotion of such accessories through sales channels of distribution defined for each category of Certification Mark..

2.        DEFINITIONS
For purposes of this Agreement.

2.1        "Accessory" or "Smart Accessory" means an individual, specific accessory designed and manufactured by, or on behalf of, the Developer that is intended to interoperate with a Compatible Device to provide enhanced functionality to a user. "Affiliate" means, at any time, and with respect to any corporation, partnership, person or other

2.2        entity, any other corporation, partnership, person or entity that at such time, directly or indirectly through one or more intermediaries, controls, or is controlled by, or is under common control with, such first corporation, partnership, person, or other entity. As used in this definition, "control" means the possession, directly or indirectly, of the power to direct or cause the direction of the management and policies of a corporation, partnership, person or other entity, whether through the ownership of voting securities, or by contract or otherwise.

2.3        "Authorized Test Lab" means an independent test lab that has been approved by Verizon to test accessories for compliance to the Specification.

2.4        "Compatible Device" means any wireless device, smartphone or similar equipment tested and approved by an Authorized Test Lab as meeting the standards of interoperability with an Accessory based on the Certification criteria.

2.5        "Compatible Device Operating System" or "Compatible Device OS" means the software operating system (e.g. Android, iOS, BlackBerry OS, Windows Phone OS) utilized on a Compatible Device.

2.6        "Compatible Device Applications" or "Application" means any software application that is either pre-loaded on, or downloaded by an end user onto a Compatible Device that is designed and written by, or on behalf of, the Developer that is necessary to allow the Compatible Device to interoperate with the Certified Accessory.

2.7        "Certification" means the process in which Developer submits an Accessory for Evaluation Services at a Verizon Authorized Test Lab to determine if the Accessory complies with the Smart Accessory Certification Process.

2.8        "Certification Mark" means the mark or sticker to be provided by Verizon for use by the Developer.

2.9        "Certification Period" means period during which an Accessory may be certified by Verizon.

2.10        "Certified Accessory" means the Accessory, as tested, has successfully completed the Smart Accessory Certification Process as defined by Verizon and for which Verizon has issued its final formal certification authorization.

2.11        "Evaluation Services" means the testing services an Authorized Test Lab provides to Accessory developers to determine if the Accessory submitted for testing conforms to the Verizon Smart Accessory Certification Process.

2.12        "Smart Accessory Number" means the number Authorized Test Lab assigns to Compliant Accessories that have passed all test cases and test scripts set forth in the Smart Accessory Specification.

2.13        "Network" means the CDMA and/or the LTE 3GPP Band 13 Network telecommunications switching equipment, cell site transceiver equipment and other equipment and systems which are owned, operated and managed by Verizon for the provision of its wireless services, including roaming services, in any part of the territory in which Verizon is licensed by the FCC to provide wireless services, and as may be configured and reconfigured at anytime and from time to time by Verizon in its sole discretion.

2.14        "Notice of Certification" means the letter submitted to Developer advising them that the Accessory has been certified by Verizon.
		  
2.15        "Smart Accessory Compliant" means the Accessory has passed all required tests in a lab and/or field environment and has demonstrated that the Accessory is compatible with, and safe for, the Network to achieve full compliance with the Smart Accessory Certification Process.

2.16        "Release Notes" mean the document prepared by Developer providing detailed information related to the Accessory's components, versioning, change history, special instructions and contact information that enables Verizon to determine the testing capabilities of the Accessory.

2.17        "Test Entrance Capability Checklist" or "TECC" means the checklist submitted by Developer identifying the capabilities supported by the Accessory that, in turn, are reviewed by Verizon to indicate which tests will be executed.

2.18        "Third Party" means any person, company, corporation, entity, and the like, which is not a party to this Agreement.

3.        CERTIFICATION MARKS

3.1        Certification Mark Categories. Verizon may introduce multiple categories of Certification Marks under this Agreement (e.g. a "Verizon Compatible Accessory" category and sticker). Guidelines applicable to the use, including lab testing requirements and approved channels of distribution, of each Certification Mark category will be detailed in Exhibits to this Agreement that are specific to each category.

3.2        Scope of Certification Mark. Verizon will approve Developer's use of a Certification Mark, as defined by the Exhibit applicable to each Accessory, solely for use with that Approved Accessory. The Parties shall execute an Exhibit for each Accessory that is certified pursuant to the terms of this Agreement.

4.        TERM

4.1       Term of Agreement. This Certification Agreement shall become effective on, and as of, the date of execution by the last signing Party ("Effective Date") and shall continue in effect for a term of 3 years, afterwhich it will automatically renew for additional 1 year terms until terminated by either Party upon 90 days written notice to the other Party.

5.        CERTIFICATION PERIOD

5.1       Certification Period. . Verizon will certify the Accessory, and Developer can use the Certification Mark with such Accessory for a period of time as defined in the Exhibit applicable to that Accessory. the duration of the time it is sold in the Verizon Wireless Sales channel

6.        CERTIFICATION MARK LICENSE

6.1       Verizon Ownership of Certification Mark. Verizon is the exclusive owner of all right, title and interest in and to the Verizon Certified Accessory Certification Mark and its goodwill will inure to the benefit of Verizon. Developer agrees that the Certification Mark and any application or registration of the Certification Mark is good, valid, and enforceable in law and equity. Developer warrants and represents with respect to the Certification Mark that (a) it will not at any time challenge the right, title, or interest of Verizon in the Certification Mark or the validity of the Certification Mark or any registration; (b) it will not do or cause to be done or omit to do anything, the doing, causing, or omitting of which would contest or in any way impair or tend to impair the rights of Verizon in the Certification Mark; (c) it will not represent that it has any ownership in or rights with respect to the Certification Mark; and (d) it will not, either during or subsequent to the term of this Agreement, adopt, use, or register any certification mark, trademark, service mark, trade name, insignia or logo that is confusingly similar to or a colorable imitation of the Certification Mark or any of the other marks of Verizon.

6.2       License Grant. Verizon hereby grants to Developer a limited, non-exclusive, worldwide, revocable, non-transferable, royalty-free license to use the Certification Mark on or in connection with the Certified Accessory during the Certification Period with a limited right of sublicense solely as provided in this Section. Developer may not use the Certification Mark on packaging material outside of Verizon channels of distribution and may not use or reference the Certification Mark in any advertising, marketing, public relations or similar material without express written approval by Verizon.

6.3       Sublicense Grant. Verizon hereby grants to Developer a limited, non-exclusive, worldwide, revocable, non-transferable license to sublicense the Certification Mark to third parties with whom Developer has contracted to advertise, promote or market the Certified Accessory during the Certification Period. Developer shall require all such third parties to agree in writing to all terms and conditions necessary and appropriate to protect Verizon's right, title and interest to the Certification Mark, that (a) shall include, but not be limited to, all applicable terms and conditions of this Agreement and (b) shall provide that Verizon shall be a third party beneficiary of each such agreement.

6.4       Reservation of Rights. Except for the limited license rights granted herein, Verizon reserves to itself all right, title and interest in and to the Certification Mark.

7.        USE OF THE CERTIFICATION MARK

7.1       Use of Certification Mark. Verizon will permit the use of the Verizon Certification Mark solely in connection with the Certified Accessory during the Certification Period. The Verizon Certification Mark may appear in advertising, product packaging, promotional material or other literature to indicate the Certified Accessory meets Verizon's requirements for Smart Accessory Certification and is subject to Verizon's written approval. Except as set forth in this Section 6, no other reference that may be interpreted to mean Verizon may appear in any product packaging, advertising, promotional material, or other literature without the prior written consent of Verizon. Developer may not use the Certification Mark in conjunction with any Applications associated with the Certified Accessory.

7.2       No Use Outside Verizon Distribution Channels. Developer's use of the Verizon Certification Mark on Certified Accessories is limited to accessories sold through Verizon Channels of Distribution including Verizon owned retail stores and verizonwireless.com. This limitation applies only to the use of the Certification Mark and in no way limits Developer's rights to distribute and sell accessories, even the accessories of the identical model that is subject to the Certification mark in whatsoever non-Verizon channel of distribution it opts for.

7.2       Use During Period of Certification. Developer shall be entitled to utilize the Smart Accessory Certification Mark during the Certification Period, provided that the Certified Accessory continues to meet Verizon's requirements for certification. Developer will discontinue use of the Smart Accessory Certification Mark immediately upon receipt of the written request of Verizon.

7.3       Discontinuance of Smart Accessory Certification by Verizon. In the event that Verizon decides, in its sole discretion, to discontinue Smart Accessory Certification, Developer agrees that within sixty (60) days after receipt of written notice from Verizon, Developer shall: (a) refrain from using the discontinued Certification Mark; and (b) destroy or return to Verizon all designs, stationery, promotional materials, and advertising of every kind using any of the discontinued Certification Mark.

7.4       Modification of Certification Mark. In the event that Verizon decides, in its sole discretion, to modify the Certification Mark or to substitute one or more marks in place of the Certification Mark, Developer agrees that within sixty (60) days after receipt of written notice from Verizon, Developer shall: (a) refrain from using the unmodified Certification Mark; (b) destroy or return to Verizon all designs, stationery, promotional materials, and advertising of every kind using any of the unmodified Certification Mark; and (c) commence using the modified or substituted Certification Mark in accordance with this Agreement.

7.5       No Misleading References. References to Verizon and the Certification Mark shall not be misleading as to the extent of certification.

8.        ACCESSORY CERTIFICATION

8.1       Submission to Authorized Test Lab. Developer must submit samples of each Accessory, and any associated Applications, as well as TECC information to a Verizon Authorized Test Lab for Certification. Verizon will review and, in its sole discretion, approve such Accessory for use of the Certification Mark only after receipt of the Certification from the Authorized Test Lab. The Developer is solely responsible for the costs associated with the Certification process as well as any contractual relationship with the Authorized Test Lab.

8.2       Certification Notice. If the Accessory achieves certification status, Verizon will notify Developer of Certification status in writing.

8.3       Verizon Listing. Developer shall provide Accessory technical data and marketing material required by Verizon and Verizon shall list Developer and its Certified Accessory on Verizon's web site and in other materials during the Certification Period. This listing may include a description of the Device, features, and images. The list of required materials and their appropriate formats relating to the Certified Accessory is outlined in the document "VZW Smart Accessory Marketing Launch Packet."

8.4       Internal Verizon User Trial. Develop shall provide samples of Certified Accessories with the same software and functionality as those it submits to the Authorized Test Lab to Verizon to allow for Internal User Trials. The quantity of samples and the duration of any trial will be determined by mutual agreement of Developer and Verizon for each Certified Accessory.

9.        ONGOING SUPPORT OF CERTIFIED ACCESSORY

9.1       Modification of Certified Accessory

9.1.1        Disclosure of Modification. Developer shall not modify the Certified Accessory or Application in any manner that results in more than an immaterial change in features, function, or performance (as determined by Verizon), without first disclosing such proposed modification to Verizon in writing and re-submitting the Certified Accessory or Application for regression testing or re-testing. Developer shall provide amended documentation as requested by Verizon, including but not limited to the Release Notes and TECC for the Accessory. Developer agrees that it will cooperate with and assist Verizon in ascertaining the facts needed to determine that the modified Certified Accessory complies with the Verizon Certification requirements.

9.1.2        Re-submission to Verizon and/or Authorized Test Lab due to Modification of Certified Accessory or Application. Verizon, on reviewing the Developer's TECC may require the Developer to commit to re-submitting the modified Certified Accessory for re-testing at an Authorized Test Lab, provided that the Verizon Authorized Test Lab that performed the initial testing has maintained the test report and all associated test data, including Release Notes and TECC, from the initial test, or request that the Developer work, to the best of Developer's ability, with specific Compatible Device Manufacturer(s) to resolve issues arising from the modification to the Certified Accessory and then to provide updates Release Notes and TECC to Verizon for review. 
Updated Application(s) must be delivered to Verizon at least 10 business days prior to Developer's commercial release. Hardware updates must be delivered to Verizon 30 business days prior to Developer's commercial release.

9.2       Testing for New Compatible Devices or Compatible Device OS

9.2.1        Re-submission of Certified Accessory for testing due to Verizon or Manufacturer introduction of new Compatible Devices or Compatible Device OS SW. Developer must submit each Certified Accessory for testing with each new Compatible Device or Compatible Device OS to Verizon and Compatible Device Manufacturer. Developer will work with Verizon and Compatible Device Manufacturer to resolve compatibility issues arising from the introduction of the new Compatible Devices or Compatible Device OS. If the Parties cannot resolve these compatibility issues to their mutual satisfaction, or when a solution is not forth coming, the Accessory will need to be submitted to an authorized lab with the new Compatible Devices, or the release of substantive updated versions of Compatible Device OS, introduced by Verizon, or a manufacturer, for Certified Devices for Certification testing by an Authorized Test Lab, provided that the Verizon Authorized Test Lab that performed the initial testing has maintained the test report and all associated test data, including Release Notes and TECC, from the initial test.

9.3       Verizon shall determine, with input from Developer and Compatible Device Manufacturer, (a) the scope of testing required to demonstrate that the modified Accessory continues to meet Verizon's Smart Accessory Certification Requirements, (b) device compatibility additions and (c) if the Accessory will be re-certified.

9.4       Responsibility for Re-Testing Fees. Developer shall be solely responsible for any fees, due to an Authorized Test Lab, associated with the Re-Testing of Certified Accessories and/or Applications for any of the reasons in section 8 that triggered the re-testing requirement.

9.5       Verizon Notification of Unauthorized Reference. Developer agrees that with prior written notice to Developer, Verizon may notify vendors, authorities, potential users, and others of an improper or unauthorized use of the Verizon Certification, or any improper or unauthorized reference to Verizon, when in Verizon's opinion such notification is necessary in the interest of the public or for Verizon's own protection.

10.       CALEA

Developer represents and warrants that the Accessory does and will comply in all respects with the Communications Assistance for Law Enforcement Act ("CALEA") as it may be amended from time to time as well as any regulations or industry standards implementing the provisions of the law.

11.       PROTECTED HEALTH INFORMATION AND REGULATORY APPROVALS

11.1      Protected Health Information. Developer represents and warrants that in the event that Developer and/or Developer's customers and/or end-users utilize any aspect of the Network to transmit, receive, store, or process Protected Health Information ("PHI") as that term is defined by the Health Information Portability and Accountability Act of 1996 ("HIPAA") and the Health Information Technology for Economic and Clinical Health Act ("HITECH Act") as amended and as implemented by the associated regulations (collectively, the "Acts"), Developer shall be fully compliant with the Acts, and shall encrypt all PHI at rest and in motion.

11.2      Regulatory Approvals. Developer represents and warrants that it has all approvals required by the Food and Drug Administration (FDA) and/or other federal and state regulators in connection with all Devices, and Developer shall provide Verizon with any and all FDA clearance letters or other approvals upon request. Developer shall defend, hold harmless and indemnify Verizon for all Claims arising from an alleged failure to have such approvals and/or from injuries, damages and/or losses allegedly caused by the Accessory (used independently or in conjunction with the Network) or by Developer's alleged breach of the Acts or alleged unauthorized disclosure of PHI.

12.       PUBLICITY

12.1      Except as specifically permitted in this Agreement, nothing in this Agreement shall grant, suggest or imply any authority for the Developer to use the name, trademarks, service marks or trade names of Verizon for any purpose whatsoever. Developer further agrees to submit to the contacts below, for written approval of all advertising, sales promotion, press releases and other publicity matters relating to Developer or the Accessory when a Verizon name or mark or the name or mark of any of its partners or Affiliates is mentioned or language from which the connection of said name or mark may be inferred or implied.

12.2      Such requests shall be sent to:
                       Vice President -- Marketing Communications
                       Verizon Wireless
                       One Verizon Way
                       VC43E062
                       Basking Ridge, New Jersey 07920
Or to such other person as the Vice President - Marketing Communications shall designate from time-to-time.



13.       REPRESENTATIONS AND WARRANTIES
13.1      Developer represents, warrants, covenants and agrees that: (a) the Certified Accessory is and will be safe for its intended use, (b) the Certified Accessory will be free from defects in materials and workmanship for the longer of ninety (90) days or the warranty period expressly provided by Developer at time of sale, (c) the Certified Accessory will fully conform with the Accessory Specification whenever used in connection with a Compatible Device, (d) Developer will provide all required warnings regarding the safety of the Certified Accessory; and (e) the Certified Accessory will comply with all applicable FCC, FDA and other governmental regulations and requirements. The foregoing warranties shall extend to any purchaser or end-user of the Certified Accessory. These representations and warranties shall survive termination or expiration of this Agreement.

13.2      Developer represents and warrants that it has good title to the Accessory and the right to sell them free of any proprietary rights, including Intellectual Property rights, of any other party.

14.       DISCLAIMER OF WARRANTIES

14.1      VERIZON MAKES NO EXPRESS OR IMPLIED WARRANTIES OF MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE OR ANY OTHER WARRANTIES OR REPRESENTATIONS WITH RESPECT TO ACCESSORY CERTIFICATION OR THE NETWORK, INCLUDING ANY WARRANTY OF NON-INFRINGEMENT.

14.2      CERTIFICATION OF A ACCESSORY FOR USE ON THE NETWORK DOES NOT MEAN THAT VERIZON HAS MADE ANY DETERMINATION AS TO THE CALL QUALITY OR OTHER FUNCTIONALITY PROVIDED BY THE CERTIFIED ACCESSORY. VERIZON DOES NOT IN ANY WAY WARRANT THAT THE CERTIFIED ACCESSORY (A) WILL OPERATE WITHOUT ERROR ON THE NETWORK (INCLUDING THE NETWORK OF ANY OTHER CARRIER ACCESSED WHILE ROAMING OR OTHERWISE), (B) WILL OPERATE WITHOUT THE NEED FOR PERIODIC UPGRADES OR MODIFICATIONS TO THE CERTIFIED ACCESSORY; (C) WILL OPERATE INDEFINITELY ON THE NETWORK; (D) WILL NOT BE SUBJECT TO SERVICE DISRUPTIONS OR INTERRUPTIONS DUE TO GOVERNMENT REGULATION, SYSTEM CAPACITY, COVERAGE LIMITATIONS, RADIO SIGNAL INTERFERENCE OR OTHER ANOMALIES; OR (E) WILL NOT BE ADVERSELY AFFECTED BY NETWORK-RELATED MODIFICATIONS, UPGRADES OR SIMILAR ACTIVITY.

15.       INDEMNIFICATION

15.1      Developer hereby agrees to indemnify, hold harmless and forever discharge Verizon and its officers, directors, contractors, agents, employees, affiliates, successors and assigns (the "Indemnified Parties") from and against any and all suits, claims, demands, liabilities, damages, costs, attorneys' fees, disbursements or expenses that may be claimed or asserted against any Indemnified Party by any person, entity or government on account of: (i) Developer's violation of this Agreement; or (ii) Developer's Accessory, the use of Developer's Accessory on the Network or while roaming, or Developer's use of the Certification Mark ((i) and (ii) together constituting the "Indemnified Claims"). The Indemnified Claims are not limited to any theory of recovery and include but are not limited to claims alleging breach of express or implied warranty, breach of contract, negligence or other negligent or intentional torts, strict liability, fraud, statutory claims, patent, trademark or copyright infringement, or any other claim at law or in equity. The foregoing indemnity obligations shall apply whether the Developer or Verizon defends such Indemnified Claim and whether the Indemnified Claim arises or is alleged to have arisen out of Developer's sole acts or omissions or out of the concurrent acts or omissions of Developer and any Indemnified Party.

15.2      Verizon will provide Developer with notice of the Indemnified Claim within a reasonable time after Verizon becomes aware of facts sufficient to identify Developer. Lack of notice will not relieve Developer of its indemnity obligations unless Developer suffers actual prejudice resulting from the lack of notice and then only to the extent of such prejudice.

15.3      Provided that Developer agrees to indemnify Verizon pursuant to this Agreement, Developer shall control the defense and settlement of an Indemnified Claim using counsel acceptable to Verizon and shall provide written updates to Verizon on the status of any pending Indemnified Claim no less than once per quarter. Verizon's approval of counsel chosen by Developer shall not be withheld unreasonably. Developer shall not settle, compromise, or otherwise dispose of an Indemnified Claim without the prior written consent of Verizon, which consent shall not be withheld unreasonably. Verizon shall retain the right to select its own counsel and to participate fully in the defense of any Indemnified Claim at Verizon's expense. In such event, Developer shall remain liable to Verizon for any verdict, judgment, award or damages rendered against Verizon as a result of an Indemnified Claim. If Developer refuses in whole or part to indemnify Verizon for any Claim tendered to Developer pursuant to this Agreement, then Verizon shall control the defense or settlement of such Claim using counsel of Verizon's choosing and Verizon shall retain all rights to seek to recover from Developer all liabilities, payments, damages, costs, attorneys' fees, disbursements or expenses incurred by Verizon in connection with such Claim.

16.       LIMITATION OF LIABILITY

16.1      Developer acknowledges and agrees that Verizon shall not be responsible or liable to Developer for any loss, damage, claim, or expense, including but not limited to claims for contribution or indemnity, allegedly arising from or in connection with this Agreement or the development, testing, manufacture, sale, distribution, use or marketing of the Certified Accessory or the Certification Mark.

16.2      Except for indemnification obligations with respect (i) to intellectual property infringement and (ii) death, personal injury, damage or loss of property, Developer shall not be liable to Verizon for indirect, incidental, consequential, or special, damages.

17.       INSURANCE
17.1      Throughout the term of the Accessory Certification, Developer shall maintain all insurance
and/or bonds required by law, including but not limited to:
(a)          Worker's compensation insurance as prescribed by the law of the state(s) in which Developer does business;
(b)          Employer's liability insurance with limits of at least $1,000,000 each occurrence;
(c)          Commercial general liability insurance (including, but not limited to, bodily injury, property damage, personal/advertising injury and products liability without an exclusion for EMF/RF exposures) with a minimum general aggregate limit of $ 2,000,000 and a minimum limit of $ 2,000,000 per occurrence ;
(d)          Commercial automobile liability insurance with limits of at least $1,000,000 combined single limit for each occurrence;
(e)          Such other insurance/bonds as Verizon shall reasonably require from time to time.

17.2      Limits may be satisfied through a combination of primary and/or excess umbrella coverage. Verizon shall be named as an additional insured on all general and automobile liability insurance coverage.

17.3      Developer's insurer or broker shall submit certificates of insurance evidencing insurance requirements to Verizon promptly upon execution of the Agreement and with each policy renewal. The insurance shall be primary without contribution from Verizon coverage and shall include a waiver of subrogation in favor of Verizon. Each insurance policy shall state by endorsement that such policy shall not be canceled or materially changed without at least ten (10) days prior written notification to Verizon by registered mail; and Developer will immediately notify Verizon of any reduction or possible reduction in the limits of any such policy, where such reduction when added to any previous reduction, would reduce coverage below the limits required by this Agreement

18.       EXPORT CONTROL CLASSIFICATION NUMBER

Verizon may request Developer to provide an Export Control Classification Number ("ECCN") for the Accessory prior to Certification under this Agreement. Developer agrees to promptly comply with any such requests.

19.       TERMINATION

19.1      Verizon may terminate this Agreement upon the breach of any provision or warranty in this Agreement by Developer without prior notice to Developer. Developer acknowledges that termination of this Agreement by Verizon will not result in liability to Verizon and that termination of this Agreement shall not be cause for a claim by Developer against Verizon for any damages whatsoever, whether direct, indirect, special or consequential. Upon termination of this Agreement, Developer shall immediately cease its use of the Accessory Certification Notice in connection with the Certified Accessory.

19.2      Termination of this Agreement shall not relieve Developer of its obligation to indemnify Verizon hereunder.

19.3      Notwithstanding anything to the contrary, Verizon maintains the right to change or discontinue the Accessory Certification program, and/or revise the criteria for Accessory Certification without notice at any time and for any reason.

19.4      Developer commits to providing continued support to the Certified Accessory for a period of one (1) year after the termination of this Agreement. Continued support follows the guidelines as set forth in Section 8 for Re-submission of Certified Accessory.

20.       NO GUARANTEE OR WARRANTY

This Agreement does not constitute Verizon's guarantee or warranty of the Certified Accessory and no representation of any kind by Developer in connection with its manufacture, sale, and/or use of the Certified Accessory or Accessory Certification or otherwise will directly or indirectly, explicitly or implicitly convey or suggest any such guarantee or warranty. Verizon may require that Developer include a statement in the marketing and informational materials accompanying a Certified Accessory disclaiming any guarantee or warranty of the Accessory by Verizon.


21.       CHOICE OF LAW

This Agreement must be construed and enforced according to the laws of the State of New York without regard to those laws relating to conflict of laws. Developer shall be subject to the jurisdiction of the courts in the State of New York if a suit is commenced in connection with this Agreement.

22.       ARBITRATION

22.1      The Parties desire to resolve certain disputes, controversies, and claims arising of this Agreement ("Dispute") without litigation. Accordingly, except in the case of (i) suit, action, or proceeding to compel Developer to comply with the Arbitration procedures set forth in this Section 21, (ii) a suit, action, or proceeding to compel Developer to comply with its obligations to indemnify pursuant to Sections 14 and 15 of this Agreement, or (iii) a suit, action, or proceeding to enforce an arbitration award, arbitration shall be the exclusive means of resolving any controversy or claim ("Dispute") between the Parties that may exist now or arise in the future relating to or arising out of this Agreement, or the Accessory certified in connection with this Agreement and neither Party to this Agreement may commence any action in any court except to enforce this obligation to arbitrate or to enforce any arbitration award. Nothing herein, however, shall be construed to limit a Party's right to submit a claim for relief to a regulatory agency of competent jurisdiction. All discussions, communications, and documents produced or developed for these alternative dispute resolution procedures set forth in this Section 21 shall be treated as information developed for purposes of settlement and shall constitute confidential information, exempt from discovery and production, and shall not be admissible in the arbitration described herein or any lawsuit without the concurrence of all the Parties.

22.2.     Within ten (10) days of receipt of written notice of an alleged Dispute under this Section submitted by one Party to the other, the Parties shall meet in person or by telephone in good faith to attempt to negotiate a resolution of the Dispute. If the Parties are unable to resolve the Dispute within sixty (60) days of initial written notice, either may serve upon the other, by certified mail, a written demand for arbitration under this Section 21 specifying in reasonable detail the nature of the Dispute to be submitted to arbitration pursuant to Wireless Industry Arbitration Rules (The "WIA Rules") of the American Arbitration Association (the "AAA"), as modified by this Agreement and any other written modification to which all Parties agree.

22.3.     Within twenty (20) days after service of the demand, each Party shall select one (1) arbitrator. The two (2) arbitrators selected shall, in turn, select a third arbitrator. If the arbitrators selected by the Parties cannot agree on a third arbitrator, the third arbitrator shall be selected from the AAA's Commercial panel, Telecommunications panel, or Large/Complex Case panel as provided by the WIA Rules. No "Fast Track" arbitration as defined in the WIA Rules shall be available to or requested by either Party. No arbitration required by this Agreement may proceed on a class basis, or be consolidated with any other arbitration, without the written consent of all Parties. Discovery shall be controlled by the arbitrators except it shall be limited to a combination of 35 (without subparts) of the following: interrogatories, demands to produce documents, and requests for admission. Each Party is also entitled to take the oral deposition of one (1) individual per Party. Any arbitration shall be held in New York City. The Parties may submit written briefs. The arbitrators shall rule on the Dispute within thirty (30) days following the close the hearing to include providing a written ruling explaining such ruling.

22.4      Each Party shall bear its own costs of these alternative dispute resolution procedures, except that a Party seeking discovery shall reimburse the other for the costs of document production (including search and reproduction time costs). The Parties shall equally split the fees of the arbitration and arbitrator.

22.5.     The arbitrators shall have no authority to award punitive or exemplary damages and may not, in any event, make any ruling, finding, or award that does not conform to the terms and conditions of this Agreement, including making any award which may reform or amend this Agreement. No arbitration award may include any award of attorney's fees (except as may be required by any statute preempting the Federal Arbitration Act to such extent or to the extent attorney's fees may be part of costs indemnified pursuant to this Agreement). Any arbitration award must include a statement by the arbitrator or arbitrators of the reasons for the award. Any arbitration award may be reviewed pursuant to Rule L-6 of the WIA Rules. Judgment may be entered on any arbitration award in any court of competent jurisdiction.

23.       ASSIGNMENT
The rights, obligations, and other interests of Developer shall not be assigned by Developer, in whole or in part, without the prior written consent of Verizon, and any purported assignment of same shall be void and ineffective.

24.       SEVERABILITY
If any provision of this Agreement shall be invalid or unenforceable, then such invalidity or unenforceability shall not invalidate or render unenforceable the entire Agreement. The entire Agreement shall be construed as if not containing the particular invalid or unenforceable provision or provisions, and the rights and obligations of the Parties shall be construed and enforced accordingly.

25.       SURVIVAL OF OBLIGATIONS
The respective obligations of the Parties under this Agreement that by their nature would continue beyond the termination, cancellation or expiration, shall survive any termination, cancellation or expiration, including, but not limited to, obligations to indemnify, insure and maintain confidentiality.

26.       ENTIRE AGREEMENT
This is the entire agreement between the Parties about Certification and the Certification Mark. It incorporates and supersedes all written and oral communications about its subject. It may only be changed or supplemented by a written amendment signed by the authorized representatives of the Parties.

IN WITNESS WHEREOF, Verizon and Developer have executed this Agreement as of the dates set forth below.

Cellco Partnership d/b/a Verizon Wireless       [Developer]

By: ______________________________             By: ____________________________

Name: ____________________________ Name: __________________________

Title: _____________________________           Title: ___________________________

Date: _____________________________ Date: ___________________________


                                                                                             EXHIBIT A

                                   ADDENDUM [INSERT NUMBER]

                                   VERIZON SMART ACCESSORY
                                   CERTIFICATION AGREEMENT
                                         FOR [DEVELOPER]

This Addendum is subject to the Verizon Smart Accessory Certification Agreement MA-XXXXX-20XX between [Developer's full company name ("Developer") and Cellco Partnership d/b/a Verizon Wireless ("Verizon") permitting Developer to use the Certification Mark identified below on the following Accessory only in the designated channel of distribution:

Accessory Product Number: [TRACKING NUMBER]

Accessory Name: [NAME]

Accessory Description: [DESCRIPTION]

Certification Mark Category: "Verizon Compatible Accessory"
.
Lab Certification Requirements: [HOW DO WE REFERENCE THIS?]

Certification Period. Developer can use this Certification Mark with the identified Accessory for the duration of the time it is sold in the Verizon Wireless Sales channel

Channel of Distribution: Developer's use of the Verizon Certification Mark on the identified Accessory is limited to accessories sold through Verizon Channels of Distribution including Verizon owned retail stores and verizonwireless.com.

IN WITNESS WHEREOF, Verizon and Developer have executed this Addendum [INSERT NUMBER] as of the dates set forth below.

AGREED:

Cellco Partnership d/b/a Verizon Wireless      [Developer]

By: ______________________________            By: ____________________________

Name: ____________________________ Name: __________________________

Title: _____________________________          Title: ___________________________

Date: _____________________________ Date: ___________________________
</textarea>
                <div class="clearboth"></div>
                <br />
                <input id="acceptStatus" name="acceptStatus" type="checkbox" />I agree and I am willing to associate my company registration with Agreement.
                <br /><br />
                <label class="inputlabel" style="margin-top:0px; clear:both; width:650px;">If you have questions about the agreement above please email<a href="mailto:${applicationScope.CERT_AGREEMENT_HELP_EMAIL}" >  ${applicationScope.CERT_AGREEMENT_HELP_EMAIL}</a></label>
                <div class="clearboth"></div>
                <c:forEach items="${workItem.nextActions}" var="nextAction">
                    <a class="button floatRight marginLeft10 marginTop20 workflowButton" href="javascript:void(0)" onclick="acceptAgreement('process${workItem.title}Form', '${nextAction}');" ><span class="red">${nextAction}</span></a>
                </c:forEach>
                <div class="clearboth"></div>
            </div>
            <br />
            </c:if>
            
        </c:when>
        <c:when test="${workItem.title == STEP_OFFLINE_CERTIFICATION_NDA}">
            <div class="grayBoxLayouts">
                <div class="threeColmnLayout">	
                    <h1 class="noBold">Offline Certification Agreement</h1>
                    <div class="columnOne" style="width:250px;">
                        <div class="redstar">*</div>
                        <label class="inputlabel">Certification Agreement Upload</label>
                        <span class="label-info-small">(.PDF)(Size=10MB)</span>
                        <div class="clearboth"></div>
                        <div id="upload_pdf_sample_product"></div>
                        <div class="uploadeded-File" id="uploaded_pdf_sample_product" style="display: none;" >
                            <a 
                            id="download_pdf_sample_product"
                            title=""
                            class="" 
                            href="return false;" ></a> 
                            <span><a 
                                    id="delete_pdf_sample_product"
                                    rel="" 
                                    name=""
                                    class=""
                                    href="#" 
                                    title="Delete" 
                                    ><tg:img src="/images/uploadedFile-icon.png" width="16" height="16" alt="Delete" border="0" />
                                </a>
                            </span>
                            <div class="clearboth"></div>
                        </div>
                    </div>
                    <div class="clearboth"></div>
                    <div class="clearboth"></div>

                    <textarea name="commentText" class="textfield font fullwidth" style="margin-top:10px; height:33px;">Add email notification text here...</textarea>
                    <div class="button-container-right">
                    <c:forEach items="${workItem.nextActions}" var="nextAction">
                        <a class="button floatRight marginLeft10 marginTop20 workflowButton" href="javascript:void(0)" onclick="submitAgreement('process${workItem.title}Form', '${nextAction}');" ><span class="${nextAction == ActionDeny?'gray':'red'}">${nextAction}</span></a>
                    </c:forEach>
                    </div>
                    <div class="clearboth"></div>
                </div>
            </div>
            <br />
        </c:when>
        <c:otherwise>

            <div class="grayBoxLayouts">
                <label class="inputlabel redColor">${workItem.displayName}</label>
                <div class="clearboth"></div>
                <textarea name="commentText" class="textfield font fullwidth" style="margin-top:5px; height:33px;">Add email notification text here...</textarea>
                <div class="button-container-right">
                <c:forEach items="${workItem.nextActions}" var="nextAction">
                    <a class="button floatRight marginLeft10 marginTop20 workflowButton" href="#" onclick="submitForm('process${workItem.title}Form', '${nextAction}');" ><span class="${(nextAction == ActionDeny or nextAction == ActionSuspend) ?'gray':'red'}">${nextAction}</span></a>
                </c:forEach>
                </div>

                <div class="clearboth"></div>
            </div>
        </c:otherwise>
    </c:choose>
    </form:form>    
<br />

</c:forEach>

<script type="text/javascript">
    
    function acceptAgreement(formId, decision) {
        if( !$('#acceptStatus').prop('checked') ){
            alert("In order to Accept Agreement, you must agree to Verison's Certification Agreement & Non-Disclosure Agreement.");
            return;
        }
        document.forms[formId].decision.value = decision; 
        document.forms[formId].action = 'acceptAgreement.do'; 
        disableWorkflowButtons();
        document.forms[formId].submit();
    }
    
    function submitAgreement(formId, decision) {
        if(decision == '<%=WorkflowAction.Deny %>'){
            submitForm(formId, decision);
            return;
        }
        
        document.forms[formId].decision.value = decision; 
        document.forms[formId].action = 'submitAgreement.do'; 
        if(document.getElementById('pdf_sample_product.mediaId').value == '' || document.getElementById('pdf_sample_product.mediaId').value == 'null'){
            alert('Certification Agreement PDF Upload is required');
            return;
        }
        document.forms[formId].elements['companyOfflineCertNdaId'].value = document.getElementById('pdf_sample_product.mediaId').value;
        disableWorkflowButtons();
        document.forms[formId].submit();
    }
    
    function submitForm(formId, decision) {
        document.forms[formId].decision.value = decision; 
        disableWorkflowButtons();
        document.forms[formId].submit();
    }
    
    function disableWorkflowButtons(){
        $('div.grayBoxLayouts a.workflowButton').removeAttr("onclick");
    }
    
</script>
