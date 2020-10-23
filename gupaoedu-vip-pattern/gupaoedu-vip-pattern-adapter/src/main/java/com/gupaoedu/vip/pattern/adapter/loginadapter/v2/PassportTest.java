package com.gupaoedu.vip.pattern.adapter.loginadapter.v2;

/**
 * Created by Tom.
 */
public class PassportTest
{

	public static void main(String[] args)
	{

		IPassportForThird passportForThird = new PassportForThirdAdapter();

		passportForThird.loginForQQ("");

	}

}
