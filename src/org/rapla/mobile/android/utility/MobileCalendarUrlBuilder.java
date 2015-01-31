/*--------------------------------------------------------------------------*
 | Copyright (C) 2012 Maximilian Lenkeit                                    |
 |                                                                          |
 | This program is free software; you can redistribute it and/or modify     |
 | it under the terms of the GNU General Public License as published by the |
 | Free Software Foundation. A copy of the license has been included with   |
 | these distribution in the COPYING file, if not go to www.fsf.org         |
 |                                                                          |
 | As a special exception, you are granted the permissions to link this     |
 | program with every library, which license fulfills the Open Source       |
 | Definition as published by the Open Source Initiative (OSI).             |
 *--------------------------------------------------------------------------*/

package org.rapla.mobile.android.utility;

import android.net.Uri;

/**
 * This class builds an url for the mobile view of a calendar. The url pattern
 * is
 * <code>http://{HOST}:{PORT}/rapla?page=mobile&user={USER}&file={CALENDAR}&android-client=1</code>
 * .
 * 
 * @author Maximilian Lenkeit <dev@lenki.com>
 * 
 */
public class MobileCalendarUrlBuilder {

	private String host;
	private String username;
	private String file;
	public static final String PATTERN = "%s/rapla?page=mobile&user=%s&file=%s&android-client=1";

	/**
	 * @param host
	 *            Server host
	 * @param port
	 *            Rapla port on server
	 * @param username
	 *            Username of current user
	 * @param isSecure
	 *            Whether to use http or https
	 * @param calendarName
	 *            Name of calendar to be displayed
	 */
	public MobileCalendarUrlBuilder(String host, String username,
			String calendarName) {
		this.host = host;
		this.username = username;
		this.file = calendarName;
	}

	/**
	 * Build url as string
	 * 
	 * @return Ready-to-use url for mobile view of calendar
	 */
	public String buildString() {
		String url = String.format(PATTERN,  this.host, this.username, this.file);
		// url = "http://lenki.com/test.php";
		return url;
	}

	/**
	 * Build url as uri
	 * 
	 * @return Ready-to-use uri for mobile view of calendar
	 */
	public Uri buildUri() {
		return Uri.parse(this.buildString());
	}
}
