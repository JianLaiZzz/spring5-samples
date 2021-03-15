import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 支付完成以后的状态
 * Created by Tom.
 */
public class MsgResult
{
	private int code;
	private Object data;
	private String msg;

	public MsgResult(int code, String msg, Object data)
	{
		this.code = code;
		this.data = data;
		this.msg = msg;
	}

	public static void main(String[] args) throws SocketException, InterruptedException {
		InetAddress inet = getFirstNonLoopbackAddress(true, false);

		System.out.println(getLinuxLocalIp());
		//		System.out.println("local realIP: " + getIp());
		//
		//		System.out.println(getNetIp());

		System.out.println("yuncheng:  "+getV4IP());
	}

	public static String getIp()
	{
		try
		{
			return getFirstNonLoopbackAddress(true, false).getHostName() + "/"
					+ getFirstNonLoopbackAddress(true, false).getHostAddress();
		}
		catch (SocketException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return " noIP!! ";
	}

	public static InetAddress getFirstNonLoopbackAddress(boolean preferIpv4, boolean preferIPv6)
			throws SocketException
	{
		Enumeration en = NetworkInterface.getNetworkInterfaces();
		while (en.hasMoreElements())
		{
			NetworkInterface i = (NetworkInterface) en.nextElement();
			for (Enumeration en2 = i.getInetAddresses(); en2.hasMoreElements();)
			{
				InetAddress addr = (InetAddress) en2.nextElement();
				System.out.println(addr.getHostAddress());
				if (!addr.isLoopbackAddress())
				{
					if (addr instanceof Inet4Address)
					{
						if (preferIPv6)
						{
							continue;
						}
						return addr;
					}
					if (addr instanceof Inet6Address)
					{
						if (preferIpv4)
						{
							continue;
						}
						return addr;
					}
				}
			}
		}
		return null;
	}

	public static String getLinuxLocalIp() throws SocketException
	{
		String ip = "";
		try
		{
			for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en
					.hasMoreElements();)
			{
				NetworkInterface intf = en.nextElement();
				String name = intf.getName();
				if (!name.contains("docker") && !name.contains("lo"))
				{
					for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr
							.hasMoreElements();)
					{
						InetAddress inetAddress = enumIpAddr.nextElement();
						if (!inetAddress.isLoopbackAddress())
						{
							String ipaddress = inetAddress.getHostAddress().toString();
							if (!ipaddress.contains("::") && !ipaddress.contains("0:0:")
									&& !ipaddress.contains("fe80"))
							{
								ip = ipaddress;
							}
						}
					}
				}
			}
		}
		catch (SocketException ex)
		{
			System.out.println("获取ip地址异常");
			ex.printStackTrace();
		}
		System.out.println("IP:" + ip);
		return ip;
	}

	public static String getRemoteIp()
	{
		InputStream in = null;
		StringBuffer buffer = new StringBuffer();
		try
		{
			URL url = new URL("http://ip.chinaz.com/"); //创建 URL
			in = url.openStream(); // 打开到这个URL的流
			BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			String inputLine = "";
			while ((inputLine = reader.readLine()) != null)
			{
				buffer.append(inputLine);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				in.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		return buffer.toString();
	}


	public static String getV4IP() throws InterruptedException {
		String ip = "";
		String chinaz = "http://ip.chinaz.com";

		StringBuilder inputLine = new StringBuilder();
		String read = "";
		URL url = null;
		HttpURLConnection urlConnection = null;
		BufferedReader in = null;
		try
		{
			url = new URL(chinaz);
			urlConnection = (HttpURLConnection) url.openConnection();
			in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
			while ((read = in.readLine()) != null)
			{
				inputLine.append(read + "\r\n");
			}
			//System.out.println(inputLine.toString());
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (in != null)
			{
				try
				{
					in.close();
				}
				catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		Thread.sleep(1000L);
		Pattern p = Pattern.compile("\\<dd class\\=\"fz24\">(.*?)\\<\\/dd>");
		Matcher m = p.matcher(inputLine.toString());
		if (m.find())
		{
			String ipstr = m.group(1);
			ip = ipstr;
			//System.out.println(ipstr);
		}
		return ip;
	}

}
