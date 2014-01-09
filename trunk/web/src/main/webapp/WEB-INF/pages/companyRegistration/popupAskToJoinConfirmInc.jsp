<%-- 
    Created on : Aug 12, 2013, 11:24:05 AM
    Author     : Noorain
--%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- popup code starts-->
<div id="popupAskToJoinConfirmOverlay" class="overlay hide-popup"></div>

<div id="popupAskToJoinConfirm" class="popup hide-popup">

    <div class="popup-overlay">
        <div class="popup-header">
            <div style="float:left; ">
                Cancellation Confirmation
            </div>
            <div class="popup-close-button" ><a href="javascript:;"><img 
                        src="${applicationScope.STATICS_URL}/images/cross-popup.png" 
                        width="16" height="15" alt="" style="border:none;" 
                        onclick="javascript:togglePopupAskToJoinConfirm(HIDE_POPUP);"/></a></div>
        </div>
        <div class="popup-body">
            <br />
            <fmt:message key="msg.popup.ask.to.join.confirm" />
        </div>
        <div class="popup-bottom">

            <a class="button mLeftBtn" href="javascript:;" onclick="javascript:togglePopupAskToJoinConfirm(HIDE_POPUP);" style="float:right;"><span class="gray">Cancel</span></a>
            <a class="button" href="javascript:;" onclick="javascript:document.forms['frmasktojoin'].requestToJoin.value = 'false';document.forms['frmasktojoin'].submit();" style="float:right;"><span class="red">Accept</span></a>

        </div>
    </div>
</div>
<!-- popup code ends -->

<script>
    var SHOW_POPUP = true;
    var HIDE_POPUP = false;
    
    function togglePopupAskToJoinConfirm(showFlag) {
        if (showFlag) {
            $("#popupAskToJoinConfirm").removeClass("hide-popup");
            $("#popupAskToJoinConfirmOverlay").removeClass("hide-popup");
            $("#popupAskToJoinConfirmOverlay").addClass("show-popup");
            $("#popupAskToJoinConfirm").addClass("show-popup");
            return;
        }
        $("#popupAskToJoinConfirmOverlay").removeClass("show-popup");
        $("#popupAskToJoinConfirm").removeClass("show-popup");
        $("#popupAskToJoinConfirm").addClass("hide-popup");
        $("#popupAskToJoinConfirmOverlay").addClass("hide-popup");
    }
</script>
