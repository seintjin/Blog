package com.cos.util;

import javax.mail.*;

public class Gmail extends Authenticator{
	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
	
		return new PasswordAuthentication("ID", "PW");
	}
}
