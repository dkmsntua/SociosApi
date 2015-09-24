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
package helper.misc;

public class SociosConstants {
	public static int threads = Runtime.getRuntime().availableProcessors() + 1;
	public static int timeOut = 15;
	public static final int ERROR_400 = 400;
	public static final int ERROR_500 = 500;
	public static final int ERROR_501 = 501;
	public static final int ONE_THOUSAND = 1000;
	public static final int MAX_LONGITUDE = 180;
	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
	public static final String UTF8 = "UTF-8";
	public static final int AWAIT_TIME = 180;
}
