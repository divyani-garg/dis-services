package sgsits.cse.dis.administration.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileNameValidation {
	
	public boolean filenameIsValidOrNot(String file)
	{
		Pattern regex = Pattern.compile("^[^A-Za-z0-9][^A-Za-z0-9.,/&()_-]*");
		Matcher matcher = regex.matcher(file);
		if(matcher.find())
			return false;
		else
			return true;
	}
}