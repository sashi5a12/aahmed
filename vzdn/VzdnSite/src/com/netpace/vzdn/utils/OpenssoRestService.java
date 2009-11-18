package com.netpace.vzdn.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class OpenssoRestService {

	public static String request(URL url) throws Exception {

		URLConnection conn = url.openConnection();
		BufferedReader dis = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		StringBuffer buff = new StringBuffer();
		String inputLine;

		while ((inputLine = dis.readLine()) != null) {
			buff.append(inputLine).append("\n");
		}
		dis.close();
		return buff.toString();
	}

	public static void main(String args[]) {
		try {
			System.out.println(OpenssoRestService.readAttribute("vzdnadmin@netpace.com", "givenname"));
			System.out.println(OpenssoRestService.readAttribute("vzdnadmin@netpace.com", "sn"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String readAttribute(String username, String attribute)
			throws Exception {

		ConfigEnvProperties props = ConfigEnvProperties.getInstance();
		String openssoAdmin = props.getProperty("opensso.admin.user");
		String password = PasswordUtil.decrypt(props.getProperty("opensso.admin.password"));
		String serverURl = props.getProperty("opensso.serverURL");


		String res = request(
					new URL(serverURl + "/identity/authenticate?"
					+ "username=" + URLEncoder.encode(openssoAdmin, "UTF-8")
					+ "&password=" + URLEncoder.encode(password, "UTF-8"))
					);
		String tokenId = res.substring(9);
		// System.out.println("pre token : "+tokenId);
		tokenId = tokenId.substring(0, tokenId.length() - 1);

		String output = request(
						new URL(serverURl + "/identity/read?" + "name="
						+ username + "&" + "admin="
						+ URLEncoder.encode(tokenId, "UTF-8") + "&attributes_names="
						+ attribute)
						);

		String attributeName = attribute;
		int startIndex = output.indexOf(attributeName) + attributeName.length();
		String tempString = output.substring(output.indexOf(attributeName)+ attributeName.length() + 1);
		// System.out.println("TempStr=" + tempString);
		int endIndex = output.indexOf(attributeName) + attributeName.length()+ tempString.indexOf('\n') + 1;

		// System.out.println("\n\n" + startIndex + "\n\n" +endIndex);
		if (startIndex >= endIndex)
			return null;
		String pair = output.substring(startIndex, endIndex).trim();
		String value = pair.substring("identitydetails.attribute.value".length() + 1).trim();

		// System.out.println("^^^" + output + "***");
		// System.out.println("\n\n'" + pair+"'");
		// System.out.println("\n\nRoles from Opensso='" + value+"'");

		return value;

	}

	public String retreiveAttributes() {
		return "";
	}
}
