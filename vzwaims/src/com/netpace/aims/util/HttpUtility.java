package com.netpace.aims.util;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class HttpUtility
{

    public static void forward(String url, HttpServletRequest request,
        HttpServletResponse response)
        throws IOException, ServletException {
        RequestDispatcher rd = request.getRequestDispatcher(url);
        rd.forward(request, response);
    }

}