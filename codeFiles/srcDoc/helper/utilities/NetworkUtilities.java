/*******************************************************************************
 * Copyright 2015 National Technical University of Athens
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package helper.utilities;

import helper.misc.SociosConstants;
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

public class NetworkUtilities {
	public static String getResponse(String mUrlString) throws IOException, snException {
		HttpURLConnection conn;
		String response;
		URL url = new URL(mUrlString);
		try {
			conn = (HttpURLConnection) url.openConnection();
		}
		catch (IOException exc) {
			throw exc;
		}
		conn.setRequestProperty("Accept-Charset", SociosConstants.UTF8);
		conn.setRequestProperty("User-Agent", "Mozilla/5.0");
		conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		InputStream inStream;
		try {
			inStream = conn.getInputStream();
			response = getStringFromInputStream(inStream);
			conn.disconnect();
			conn = null;
		}
		catch (IOException e) {
			InputStream error = conn.getErrorStream();
			response = getStringFromInputStream(error);
			snException dataCarrier = new snException();
			dataCarrier.setData(response);
			throw dataCarrier;
		}
		finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		return response;
	}

	public static String readResponse(HttpURLConnection connection) throws IOException, snException {
		String result;
		try {
			InputStream inputStream = connection.getInputStream();
			result = getStringFromInputStream(inputStream);
		}
		catch (IOException exc) {
			InputStream error = connection.getErrorStream();
			result = getStringFromInputStream(error);
			snException dataCarrier = new snException();
			dataCarrier.setData(result);
			throw dataCarrier;
		}
		return result;
	}

	public static String getStringFromInputStream(InputStream stream) {
		InputStreamReader inReader = new InputStreamReader(stream);
		BufferedReader in = new BufferedReader(inReader);
		StringBuilder output = new StringBuilder();
		try {
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				if (!inputLine.isEmpty()) {
					output.append(inputLine);
				}
			}
			in.close();
			in = null;
			inReader.close();
			inReader = null;
		}
		catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
		finally {
			if (in != null) {
				try {
					in.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (inReader != null) {
				try {
					inReader.close();
				}
				catch (IOException e) {
					System.out.println(e.getMessage());
				}
			}
		}
		return output.toString();
	}

	public static boolean writeRequest(HttpsURLConnection connection, String textBody) {
		OutputStream outStream;
		OutputStreamWriter writer;
		BufferedWriter wr = null;
		try {
			outStream = connection.getOutputStream();
			writer = new OutputStreamWriter(outStream);
			wr = new BufferedWriter(writer);
			wr.write(textBody);
			wr.flush();
			wr.close();
			return true;
		}
		catch (IOException e) {
			return false;
		}
		finally {
			if (wr != null) {
				try {
					wr.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
