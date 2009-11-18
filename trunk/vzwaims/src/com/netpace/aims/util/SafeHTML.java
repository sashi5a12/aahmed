/*
 * Copyright (c) JForum Team
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms,
 * with or without modification, are permitted provided
 * that the following conditions are met:
 *
 * 1) Redistributions of source code must retain the above
 * copyright notice, this list of conditions and the
 * following  disclaimer.
 * 2)  Redistributions in binary form must reproduce the
 * above copyright notice, this list of conditions and
 * the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 * 3) Neither the name of "Rafael Steil" nor
 * the names of its contributors may be used to endorse
 * or promote products derived from this software without
 * specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT
 * HOLDERS AND CONTRIBUTORS "AS IS" AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING,
 * BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 * THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER
 * IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE
 *
 * This file creation date: 27/09/2004 23:59:10
 * The JForum Project
 * http://www.jforum.net
 */

package com.netpace.aims.util;

import org.htmlparser.lexer.Lexer;
import org.htmlparser.Node;
import org.htmlparser.Tag;
import org.htmlparser.Attribute;
import org.htmlparser.nodes.TextNode;
import org.apache.commons.lang.StringUtils;

import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;

/**
 * Customized JForum SafeHTML.java
 */
public class SafeHTML {

    private static Set welcomeTags;
    private static Set welcomeAttributes;
    private static Set allowedProtocols;
    private static String[] disallowedEventHandlers;
    private static String[] disallowedEventAttributes;

    private static final String WELCOME_TAGS_PROP = "html.tags.welcome";
    private static final String WELCOME_ATTRIBUTES_PROP = "html.attributes.welcome";
    private static final String WELCOME_PROTOCOLS = "html.links.allow.protocols";

    static {
        welcomeTags = new HashSet();
        welcomeAttributes = new HashSet();
        allowedProtocols = new HashSet();

        splitAndTrim(SafeHTML.WELCOME_TAGS_PROP, welcomeTags);
        splitAndTrim(SafeHTML.WELCOME_ATTRIBUTES_PROP, welcomeAttributes);
        splitAndTrim(SafeHTML.WELCOME_PROTOCOLS, allowedProtocols);
        disallowedEventHandlers = new String[]
          {
                "onabort", "onblur", "onchange", "onclick", "ondblclick",
                "onerror", "onfocus", "onkeydown", "onkeypress", "onkeyup", "onload", "onmousedown", "onmousemove",
                "onmouseout", "onmouseover", "onmouseup", "onreset", "onresize", "onselect", "onsubmit", "onunload"
          };
        disallowedEventAttributes = new String[]
          {
                "bubbles", "cancelable", "currentTarget", "eventPhase", "target", "timeStamp", "type"
          };
    }


    private static void splitAndTrim(String propName, Set data) {
        //String s1 = SystemGlobals.getValue(s);

        CommonProperties commonProps = CommonProperties.getInstance();
        String s1 = commonProps.getProperty(propName);

        if (s1 == null) {
            return;
        }

        String[] tags = s1.toUpperCase().split(",");

        for (int i = 0; i < tags.length; i++) {
            data.add(tags[i].trim());
        }
    }

    /**
	 * Given an input, makes it safe for HTML displaying.
	 * Removes any not allowed HTML tag or attribute, as well
	 * unwanted Javascript statements inside the tags.
	 * @param data the input to analyze
	 * @return the modified and safe string
	 */
    public static String makeSafe(String data) {

        if (data == null || data.length() == 0) {
            return data;
        }

        StringBuffer sb = new StringBuffer(data.length());
        try {
            Lexer lexer = new Lexer(data);
            Node node;

            while ((node = lexer.nextNode()) != null) {
                boolean isTextNode = node instanceof TextNode;

                if (isTextNode) {
                    // Text nodes are raw data, so we just
                    // strip off all possible html content
                    String text = node.toHtml();

                    if (text.indexOf('>') > -1 || text.indexOf('<') > -1) {
                        //StringBuffer tmp = new StringBuffer(text);
                        String tmp = text;

                        /*ViewCommon.replaceAll(tmp, "<", "&lt;");
                              ViewCommon.replaceAll(tmp, ">", "&gt;");
                              ViewCommon.replaceAll(tmp, "\"", "&quot;");
                              */
                        tmp = StringUtils.replace(tmp, "<", "&lt;");
                        tmp = StringUtils.replace(tmp, ">", "&gt;");
                        tmp = StringUtils.replace(tmp, "\"", "&quot;");

                        node.setText(tmp);
                    }
                }

                if (isTextNode || (node instanceof Tag && SafeHTML.isTagWelcome(node))) {
                    //if current node is textnode or allowed tag then append it
                    sb.append(node.toHtml());
                }
                else {
                    //otherwise escape html tags
                    //StringBuffer tmp = new StringBuffer(node.toHtml());
                    String tmp = node.toHtml();

                    tmp = StringUtils.replace(tmp, "<", "&lt;");
                    tmp = StringUtils.replace(tmp, ">", "&gt;");

                    /*
                    ViewCommon.replaceAll(tmp, "<", "&lt;");
                    ViewCommon.replaceAll(tmp, ">", "&gt;");
                    */

                    sb.append(tmp);
                }


            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        
        return sb.toString();
    }

    /**
	 * Returns true if a given tag is allowed.
	 * Also, it checks and removes any unwanted attribute the tag may contain.
	 * @param node The tag node to analyze
	 * @return true if it is a valid tag.
	 */
    private static boolean isTagWelcome(Node node)
    {
        Tag tag = (Tag)node;

        if (!welcomeTags.contains(tag.getTagName())) {
            return false;
        }

        SafeHTML.checkAndValidateAttributes(tag, true);

        return true;
    }


    /**
     * Given a tag, check its attributes, removing those unwanted or not secure
     * @param tag The tag to analyze
     * @param checkIfAttributeIsWelcome true if the attribute name should be matched
     * against the list of welcome attributes, set in the main configuration file.
     */
    private static void checkAndValidateAttributes(Tag tag, boolean checkIfAttributeIsWelcome)
    {
        Vector newAttributes = new Vector();

        for (Iterator iter = tag.getAttributesEx().iterator(); iter.hasNext(); ) {
            Attribute a = (Attribute)iter.next();

            String name = a.getName();

            if (name == null) {
                newAttributes.add(a);
            }
            else {
                name = name.toUpperCase();

                if (a.getValue() == null) {
                    newAttributes.add(a);
                    continue;
                }

                String value = a.getValue().toLowerCase();

                if (checkIfAttributeIsWelcome && !SafeHTML.isAttributeWelcome(name)) {
                    continue;
                }

                if(isEventHandler(name.trim().toLowerCase())) {
                    continue;
                }

                if(isEventAttribute(name.trim().toLowerCase())) {
                    continue;
                }

                if (!SafeHTML.isAttributeSafe(name, value)) {
                    continue;
                }

                if (a.getValue().indexOf("&#") > -1) {
                    a.setValue(a.getValue().replaceAll("&#", "&amp;#"));
                }

                newAttributes.add(a);
            }
        }

        tag.setAttributesEx(newAttributes);
    }

    /**
     * Check if the given attribute name is in the list of allowed attributes
     * @param name the attribute name
     * @return true if it is an allowed attribute name
     */
    private static boolean isAttributeWelcome(String name)
    {
        return welcomeAttributes.contains(name);
    }

    /**
     * Check if the attribute is safe, checking either its name and value.
     * @param name the attribute name
     * @param value the attribute value
     * @return true if it is a safe attribute
     */
    private static boolean isAttributeSafe(String name, String value)
    {
        if (name.length() >= 2 && name.charAt(0) == 'O' && name.charAt(1) == 'N') {
            return false;
        }

        if (value.indexOf('\n') > -1 || value.indexOf('\r') > -1 || value.indexOf('\0') > -1) {
            return false;
        }

        //if (("HREF".equals(name) || "SRC".equals(name))) {
        if (("HREF".equals(name) )) {
            if (!SafeHTML.isHrefValid(value)) {
                return false;
            }
        }
        else if ("STYLE".equals(name)) {
            // It is much more a try to not allow constructions
            // like style="background-color: url(javascript:xxxx)" than anything else
            if (value.indexOf('(') > -1) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks if a given address is valid
     * @param href The address to check
     * @return true if it is valid
     */
    private static boolean isHrefValid(String href)
    {
        boolean allowRelativeHref = true;
        //if (SystemGlobals.getBoolValue(ConfigKeys.HTML_LINKS_ALLOW_RELATIVE)
        if( allowRelativeHref
            && href.length() > 0
            && href.charAt(0) == '/') {
            return true;
        }

        for (Iterator iter = allowedProtocols.iterator(); iter.hasNext(); ) {
            String protocol = iter.next().toString().toLowerCase();

            if (href.startsWith(protocol)) {
                return true;
            }
        }

        return false;
    }

    private static boolean isEventHandler(String name)
    {
        name = name.replace("'", "");
        name = name.replace("\"", "");
        for (String disallowedEventHandler : disallowedEventHandlers) {
            if (name.equals(disallowedEventHandler)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isEventAttribute(String name)
    {
        name = name.replace("'", "");
        name = name.replace("\"", "");
        for (String disallowedEventHandlersAndAttribute : disallowedEventHandlers) {
            if (name.equals(disallowedEventHandlersAndAttribute)) {
                return true;
            }
        }
        return false;
    }

}
