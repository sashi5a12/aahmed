<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@attribute name="fieldName" required="true" type="java.lang.String" %>
<%@attribute name="charsLimit" required="true" type="java.lang.String" %>
<script type="text/javascript">
var filedName="<c:out value="${fieldName}"/>";
var options2 = {
		'maxCharacterSize': <c:out value="${charsLimit}"/>,
		'originalStyle': 'charsLeft',
		'warningStyle' : 'warningCharsLeft',
		'warningNumber': 50,
		'displayFormat' : '#left Characters Left / #max'
};
$('#'+filedName).textareaCount(options2);
$('#'+filedName).trigger("mouseover");
</script>