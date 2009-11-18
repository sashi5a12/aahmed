<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tiles.tld" prefix="tiles" %>

<%@ include file="/common/tile-def-SimpleLayout.jsp" %>
<tiles:insert beanName="simpleLayout" beanScope="request">

    <tiles:put name="headingRightContent">
        <div class="blackBtn" title="Back to Contracts" style="float:right;">
            <div>
                <div>
                    <div onclick="document.AllianceContractAcceptRejectForm.task.value='cancel';document.AllianceContractAcceptRejectForm.submit();">
                        Back to Contracts
                    </div>
                </div>
            </div>
        </div>
    </tiles:put>

    <tiles:put name="body" value="/alliance/allianceContractAcceptReject_body.jsp"/>

    <tiles:put name="headingText" value="Accept Click Through Contract"/>

</tiles:insert>