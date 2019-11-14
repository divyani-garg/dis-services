package sgsits.cse.dis.administration.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileNameValidation {
	
	public boolean filenameIsValidOrNot(String file)
	{
		Pattern regex = Pattern.compile("^[a-zA-Z0-9][a-zA-Z0-9.,/&()_-]*$");
		Matcher matcher = regex.matcher(file);
		if(matcher.find())
			return true;
		else
			return false;
	}
}