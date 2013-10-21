package asu.edu.sbs.service;

import java.util.Date;
import java.util.Random;
import java.util.Calendar;
public class OneTimePassword
{
	private String password;
	private Date expirationTime;
	
	public OneTimePassword()
	{
		this.password = this.passwordGenerator();
		this.expirationTime = this.setExpireTime();
	}
	
	public String passwordGenerator()
	{
		String password = new String();
		Random random = new Random();
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789~!@#$%^&*()?+=";
		for (int i = 0; i < 6; i++)
		{
			password = password + alphabet.charAt(random.nextInt(alphabet.length()));
		}
		return password;
	}
	
	public Date setExpireTime()
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MINUTE, 5);
		return cal.getTime();
	}
	public static void main(String a[])
	{
		OneTimePassword otp=new OneTimePassword();
		otp.passwordGenerator();
		System.out.println("The OTP generated is --->" + "["+otp.password+"]");
	}
}
