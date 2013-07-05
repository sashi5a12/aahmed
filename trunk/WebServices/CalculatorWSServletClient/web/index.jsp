    <%-- start web service invocation --%><hr/>
    <%
    try {
	org.me.calculator.CalculatorWS_Service service = new org.me.calculator.CalculatorWS_Service();
	org.me.calculator.CalculatorWS port = service.getCalculatorWSPort();
	 // TODO initialize WS operation arguments here
	int i = 4;
	int j = 5;
	// TODO process result here
	int result = port.add(i, j);
	out.println("Result = "+result);
    } catch (Exception ex) {
	// TODO handle custom exceptions here
    }
    %>
    <%-- end web service invocation --%><hr/>
