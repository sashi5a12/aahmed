package test.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
	^ ---->Start of a string. 
	$ ---->End of a string. 
	. ----> Any character (except \n newline) 
	{...}----> Explicit quantifier notation. 
	[...] ---->Explicit set of characters to match. 
	(...) ---->Logical grouping of part of an expression. 
	* ---->0 or more of previous expression. 
	+ ---->1 or more of previous expression. 
	? ---->0 or 1 of previous expression; also forces minimal matching when an expression might match several strings within a search string. 
	\ ---->Preceding one of the above, it makes it a literal instead of a special character. Preceding a special matching character, see below. 
	\w ----> matches any word character, equivalent to [a-zA-Z0-9] 
	\W ----> matches any non word character, equivalent to [^a-zA-Z0-9]. 
	\s ----> matches any white space character, equivalent to [\f\n\r\v] 
	\S----> matches any non-white space characters, equivalent to [^\f\n\r\v] 
	\d ----> matches any decimal digits, equivalent to [0-9] 
	\D----> matches any non-digit characters, equivalent to [^0-9] 
 */
public class RegexTest {
/*

Regex									Test Data					Result
^[0-9]$									1							true
										12							false
^[0-9]*$								123							true
^\d*$									1234						true
^\w[^&]*$								A1B#						true			all char, digits and special symbols except &
										&ABC1#						false
^\w*[^http|ftp|https]$					http						false 			http,ftp,https not allow in alpha numeric char set						
^[^1]\d*$								213							true			exclude 1 as first digit
										123							false
^[^http|https|ftp]\w*$																exclude http, https ftp as first world										

*/
	public static void main(String[] args) {
		Pattern p =Pattern.compile("^[^1]\\d*");
		Matcher m =p.matcher("i am 123");
		System.out.println(m.matches());
	}

}
