<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h1 class="redheading">Company Information</h1>
<!-- Gray Box Layout - Two column Starts-->
<div class="grayBoxLayouts">
    <div class="twoColmnLayout">	
        <div class="columnOne">
            <label class="inputlabel">Company Name</label>
            <input class="input width320" value="${profile.company.companyName}" type="text">
            <div class="clearboth"></div>

            <label class="inputlabel">Company Legal Name</label>
            <input class="input width320" value="${profile.company.companyLegalName}" type="text">
            <div class="clearboth"></div>
            <label class="inputlabel">Main Company Street Address</label>
            <textarea name="textarea" class="textfield width320" cols="45" rows="5">${profile.company.mainCompanyStreetAddress}</textarea>
            <div class="clearboth"></div>
            <label class="inputlabel">City/Town</label>
            <input class="input width320" value="${profile.company.city}" type="text">
            <div class="clearboth"></div>

            <label class="inputlabel">State/Province</label>
            <input class="input width320" value="${profile.company.state}" type="text">
            <div class="clearboth"></div>
            <label class="inputlabel">Country</label>
            <select class="selct width330" name="select">
                <!--<option>USA</option> -->
                <option>${profile.company.country}</option>
            </select>
            <div class="clearboth"></div>
        </div>
        <div class="columnTwo">
            <label class="inputlabel width400">State of Incorporation (or Country if foreign corporation)</label>
            <input class="input width320" value="${profile.nda.incorporationState}" type="text">
            <div class="clearboth"></div>

            <label class="inputlabel">Company Domain</label>
            <div class="value">${profile.company.domainName}</div>
            <div class="clearboth"></div>
            <label class="inputlabel">Company Website</label>
            <input class="input width320" value="${profile.company.website}" type="text">
            <div class="clearboth"></div>
            <label class="inputlabel">Main Phone Number</label>
            <input class="input width320" value="${profile.company.mainPhoneNumber}" type="text">
            <div class="clearboth"></div>
            <label class="inputlabel">Zip Code/Postal Code</label>
            <input class="input width320" value="${profile.company.zip}" type="text">
            <div class="clearboth"></div>
        </div>
        <div class="clearboth"></div>
    </div>
</div>
<!-- Gray Box Layout - Two column ends-->
<br>
<!-- Gray Box Layout - Three column Starts-->
<div class="grayBoxLayouts">
    <div class="threeColmnLayout">
        <div class="columnOne">
            <h1>Contact Information</h1>
            <label class="inputlabel">Full name <a href="javascript:void(0);" class="font12">Change Contact</a></label>            
            <div class="value">${profile.mainPOC.fullName}</div>
            <div class="clearboth"></div>

            <label class="inputlabel">Email Address</label>            
            <div class="value">${profile.mainPOC.emailAddress}</div>
            <div class="clearboth"></div>
            
            <label class="inputlabel">Phone Number</label>            
            <div class="value">${profile.mainPOC.phoneNumber}</div>
            <div class="clearboth"></div>
            
            <label class="inputlabel">Mobile Phone Number</label> 
            <div class="value">${profile.mainPOC.mobilePhoneNumber}</div>
            <div class="clearboth"></div>
        </div>
        <div class="columnTwo">
            <h1>NDA Contact Information</h1>
            <label class="inputlabel width100">Full Name</label>            
            <input class="input width120" value="${profile.nda.ndaContactName}" type="text">
            <div class="clearboth"></div>

            <label class="inputlabel">Title</label>            
            <input class="input width120" value="${profile.nda.ndaContactTitle}" type="text">
            <div class="clearboth"></div>
            
            <label class="inputlabel">Email Address</label>
            <input class="input width120" value="${profile.nda.ndaContactEmail}" type="text">
            <div class="clearboth"></div>
            
            <label class="inputlabel">Phone Number</label>            
            <input class="input width120" value="${profile.nda.ndaContactPhone}" type="text">
            <div class="clearboth"></div>
        </div>
        <div class="columnThree">
            <h1>Main Contact Information</h1>
            <label class="inputlabel width100">Full name</label>            
            <input class="input width120" value="${profile.mainPOC.fullName}" type="text">
            <div class="clearboth"></div>

            <label class="inputlabel">Phone Number</label>            
            <input class="input width120" value="${profile.mainPOC.phoneNumber}" type="text">
            <div class="clearboth"></div>
        </div>
        <div class="clearboth"></div>
    </div>

    <br>

    <div class="threeColmnLayout">
        <h1>NDA Vendor Address</h1>
        <div class="columnOne">

            <label class="inputlabel">Vendor Corporate Name</label>           
            <input class="input width120" value="${profile.nda.corporateName}" type="text">
            <div class="clearboth"></div>

            <label class="inputlabel">Street Address</label>            
            <textarea name="textarea2" class="textfield width120" cols="45" rows="5">${profile.nda.streetAddress}</textarea>
            <div class="clearboth"></div>
        </div>
        <div class="columnTwo">
            <label class="inputlabel width100">City/Town</label>            
            <input class="input width120" value="${profile.nda.city}" type="text">
            <div class="clearboth"></div>

            <label class="inputlabel">State/Province</label>            
            <input class="input width120" value="${profile.nda.state}" type="text">
            <div class="clearboth"></div>
            
            <label class="inputlabel">Country</label>
            <span class="columnOne">
                <select class="selct width130" name="select2">                    
                    <option>${profile.nda.country}</option>
                </select>
            </span>
            <div class="clearboth"></div>
        </div>                        
        <div class="columnThree">
            <label class="inputlabel width100">Zip Code/Postal Code</label>            
            <input class="input width120" value="${profile.nda.zip}" type="text">
            <div class="clearboth"></div>
        </div>
        <div class="clearboth"></div>
    </div>
</div>
<!-- Gray Box Layout - Three column ends--><br>

<div class="regReviewPod">
    <div class="col3">
        <h1>Existing Company Registration</h1>
        <div class="clearboth"></div>
        <span>Full Name:</span><p>XXXX</p>
        <div class="p11"></div>
        <span>If Company already has an NDA with Verizon Wireless, then please provide reference below:</span>
        <div class="p11"></div>
        <div class="w300">
            <label class="inputlabel">NDA Reference:</label>
            <input class="input" value="" name="" type="text">
        </div>
    </div>
</div>
<h1 class="redheading">Pre-Work NDA Responses</h1>

<div class="regReviewPod">
    <div class="ndaPre-WorkResponses">
        <h1>Pre-Work NDA Questions</h1>
        <c:forEach items="${profile.nda.questions}" var="ques">
            <label class="inputlabelBig">- ${ques.question}</label>
            <textarea name="textarea" class="textfieldBig" cols="45" rows="5">${ques.answer}</textarea>
            <span>Characters (500)</span>
            <div class="clearboth"></div>
        </c:forEach>    
    </div>
</div>

<div class="regReviewPod">
    <div class="ndaPre-WorkResponses">
        <h1>Comment History</h1><br>
        <br>

        <textarea name="textarea" class="textfieldBig" style="height:150px;" cols="45" rows="5">Public 04/12/2011 05:46:58 (System)
Click Through Contact Distribution Agreement (Version:2) ACCEPTED by a3companiya@gmail.com
Comments:

Public 04/12/2011 05:25:04 (System)
Click Through Agreement_final (Verison:vs.1)
ACCEPTED by a3companiya@gmail.com
comments:
        </textarea>
        <span>Characters (500)</span>
        <div class="clearboth"></div>

    </div>
</div>

<!-- buttons starts -->

<!-- buttons starts --> 
<a class="button floatRight" href="javascript:void(0);"><span class="red">Update</span></a>
<!-- buttons ends -->
