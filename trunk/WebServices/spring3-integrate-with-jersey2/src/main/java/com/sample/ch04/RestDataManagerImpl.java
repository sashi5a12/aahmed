package com.sample.ch04;

import java.net.URI;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

public class RestDataManagerImpl implements RestDataManager {

    public String getCommmonInfo(UriInfo uriInfo, HttpHeaders headers, HttpServletRequest req) {

        //path
        String path = uriInfo.getPath();
        System.out.println("--------- path --------" + path);

        //Aboslute path
        URI uriPath = uriInfo.getAbsolutePath();
        System.out.println("--------- uriPath --------" + uriPath);

        //host andf port
        URI requestUri = uriInfo.getRequestUri();
        System.out.println("--------- Host and Port --------" + requestUri.getHost() + ":" + requestUri.getPort());

        //Matched uri
        List<String> matchedUri = uriInfo.getMatchedURIs();
        System.out.println("--------- matchedUri --------" + matchedUri);

        URI baseUri = uriInfo.getBaseUri();
        System.out.println("--------- baseUri --------" + baseUri);

        UriBuilder uriBuilder = uriInfo.getBaseUriBuilder();
        System.out.println("--------- name --------" + uriBuilder.queryParam("name"));

        //Query Parameters
        MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
        System.out.println("--------- queryParams --------" + queryParams);
        for (Map.Entry<String, List<String>> entry : queryParams.entrySet()) {
            System.out.println("---- query param ----" + entry.getValue());
        }

        //Path Parameters
        MultivaluedMap<String, String> pathParams = uriInfo.getPathParameters();
        System.out.println("--------- pathParams --------" + pathParams);

        MultivaluedMap<String, String> requestHeaders = headers.getRequestHeaders();
        System.out.println("--------- requestHeaders --------" + requestHeaders);

        Map<String, Cookie> cookie = headers.getCookies();
        System.out.println("--------- cookie --------" + cookie);

        Locale locale = headers.getLanguage();
        System.out.println("--------- locale --------" + locale);

        String paramName = req.getParameter("name");
        System.out.println("--------- paramName --------" + paramName);

        return path;
    }
}