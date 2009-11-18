<script	language="javascript">
    function showProcessingInfo() {
        document.getElementById("divButtons").style.visibility = "hidden";
        document.getElementById("divTabs").style.visibility = "hidden";        
        showProcessingInfoPopup();
    }    

    function TruncateTextToMaxChars(fieldObj, maxChars) {
        if ((maxChars - fieldObj.value.length) < 0) {
            fieldObj.value = fieldObj.value.substring(0, maxChars);
        }
    }

    //Start of functions to limit text in TextArea
    function TrackCountToMaxChars(fieldObj, countFieldName, maxChars) {
        var countField = eval("fieldObj.form." + countFieldName);
        var diff = maxChars - fieldObj.value.length;

        // Need to check & enforce limit here also in case user pastes data
        if (diff < 0) {
            fieldObj.value = fieldObj.value.substring(0, maxChars );
            diff = maxChars - fieldObj.value.length;
        }
        countField.value = diff;
    }
</script>