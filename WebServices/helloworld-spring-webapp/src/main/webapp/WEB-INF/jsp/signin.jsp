<html>
<head>
    <title> Welcome to Login Page </title>
    <script type="text/javascript">
        function checkLogin() {
            document.forms[0].action = "http://localhost:9090/jersey/services/loginservice";
            document.forms[0].submit();
        }
    </script>
</head>
<body bgcolor="grey">
    <form method="post">
    <span style="font-size:100%;margin-left:550px;">
        <b>Welcome to Login Page</b>
    </span>
    <table>
        <tr>
            <td> <label><b>User Id:</b></label></td>
            <td><input type="text" name="userName" id="userName" value='' size="40"/> </td>
        </tr>
        <tr>
            <td> <label><b>Password:</b></label></td>
            <td><input type="text" name="password" id="password" value='' size="40"/></td>
        </tr>
        <tr>
            <td>
                <input type="button" name="LogIn" value="LogIn" onclick="checkLogin()"/>
            </td>
        </tr>
    </table>
</form>
</body>
</html>