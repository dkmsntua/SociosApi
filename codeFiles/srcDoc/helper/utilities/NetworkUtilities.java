package helper.utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import objects.main.snException;

public class NetworkUtilities
{
	public static String getResponse(String mUrlString) throws IOException, snException
	{
		HttpURLConnection conn = null;
		String response = "";
		URL url = new URL(mUrlString);
		try
		{
			conn = (HttpURLConnection) url.openConnection();
		}
		catch (IOException exc)
		{
			throw exc;
		}
		conn.setRequestProperty("Accept-Charset", "UTF-8");
		conn.setRequestProperty("User-Agent", "Mozilla/5.0");
		conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		InputStream inStream = null;
		try
		{
			inStream = conn.getInputStream();
			response = getStringFromInputStream(inStream);
			conn.disconnect();
			conn = null;
		}
		catch (IOException e)
		{
			InputStream error = conn.getErrorStream();
			response = getStringFromInputStream(error);
			snException dataCarrier = new snException();
			dataCarrier.data = response;
			throw dataCarrier;
		}
		finally
		{
			if (conn != null)
			{
				conn.disconnect();
			}
		}
		return response;
	}

	public static String readResponse(HttpURLConnection connection) throws IOException, snException
	{
		String result = "";
		try
		{
			InputStream inputStream = connection.getInputStream();
			result = getStringFromInputStream(inputStream);
		}
		catch (IOException exc)
		{
			InputStream error = connection.getErrorStream();
			result = getStringFromInputStream(error);
			snException dataCarrier = new snException();
			dataCarrier.data = result;
			throw dataCarrier;
		}
		return result;
	}

	public static String getStringFromInputStream(InputStream stream)
	{
		InputStreamReader inReader = new InputStreamReader(stream);
		BufferedReader in = new BufferedReader(inReader);
		StringBuilder output = new StringBuilder();
		try
		{
			String inputLine;
			while ((inputLine = in.readLine()) != null)
			{
				if (!inputLine.isEmpty())
				{
					output.append(inputLine);
				}
			}
			in.close();
			in = null;
			inReader.close();
			inReader = null;
		}
		catch (IOException ex)
		{
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
					e.printStackTrace();
				}
			}
			if (inReader != null)
			{
				try
				{
					inReader.close();
				}
				catch (IOException e)
				{
				}
			}
		}
		String result = output.toString();
		return result;
	}

	public static boolean writeRequest(HttpsURLConnection connection, String textBody)
	{
		try
		{
			OutputStream outStream = connection.getOutputStream();
			OutputStreamWriter writer = new OutputStreamWriter(outStream);
			BufferedWriter wr = new BufferedWriter(writer);
			wr.write(textBody);
			wr.flush();
			wr.close();
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}
}
