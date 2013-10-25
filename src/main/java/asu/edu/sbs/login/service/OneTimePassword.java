package asu.edu.sbs.login.service;


import java.util.Calendar;
import java.util.Date;
import java.util.Random;
public class OneTimePassword
{
	private String password;
	private Date expirationTime;
	
	public OneTimePassword()
	{
		this.password = this.passwordGenerator();
		this.expirationTime = this.setExpireTime();
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getExpirationTime() {
		return expirationTime;
	}

	public void setExpirationTime(Date expirationTime) {
		this.expirationTime = expirationTime;
	}

	private String passwordGenerator()
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
	
	private Date setExpireTime()
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MINUTE, 5);
		return cal.getTime();
	}
	
	public static void main(String a[])
	{
		OneTimePassword otp=new OneTimePassword();
		System.out.println("The OTP generated is --->" + "["+otp.password+"]");
		System.out.println(otp.getExpirationTime());
	}
}