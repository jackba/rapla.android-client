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

import org.rapla.components.xmlbundle.I18nBundle;
import org.rapla.entities.domain.AppointmentFormater;
import org.rapla.facade.ClientFacade;
import org.rapla.facade.RaplaComponent;
import org.rapla.framework.RaplaContext;
import org.rapla.framework.RaplaContextException;
import org.rapla.framework.RaplaException;
import org.rapla.framework.RaplaLocale;
import org.rapla.mobile.android.PreferencesHandler;
import org.rapla.mobile.android.RaplaMobileException;
import org.rapla.mobile.android.RaplaMobileLoginException;
import org.rapla.mobile.android.utility.factory.RaplaContextFactory;

/**
 * Manages the connection to a rapla server.
 * 
 * @author Maximilian Lenkeit <dev@lenki.com>
 * 
 */
public class RaplaConnection {

	public static final String IDENTIFIER = "RaplaConnection";
	private RaplaContext context;
	private ClientFacade facade;
	private String username;
	private String password;
    private RaplaLocale raplaLocale;
    private AppointmentFormater appointmentFormater;

	public RaplaConnection(String username, String password, String host	) throws RaplaMobileException {

		RaplaContextFactory instance = RaplaContextFactory.getInstance();
		
        this.context = instance.createInstance(host);
		try {
            this.facade = context.lookup( ClientFacade.class);
            this.raplaLocale = context.lookup( RaplaLocale.class);
            this.appointmentFormater = context.lookup( AppointmentFormater.class);
        } catch (RaplaContextException e) {
            throw new RaplaMobileException( e.getMessage(), e);
        }
		this.username = username;
		this.password = password;
	}

	public RaplaConnection(PreferencesHandler preferences)
			throws RaplaMobileException {
		this(preferences.getUsername(), preferences.getPassword(), preferences
				.getHost());
	}

	public boolean login() throws RaplaMobileLoginException {
		try {
			return this.facade
					.login(this.username, this.password.toCharArray());
		} catch (RaplaException ex) {
			throw new RaplaMobileLoginException(ex);
		}
	}

	
	public AppointmentFormater getAppointmentFormater() 
	{
        return appointmentFormater;
    }
	
	public RaplaLocale getRaplaLocale() 
	{
        return raplaLocale;
    }
	
	public ClientFacade getFacade() {
	    if ( facade == null)
	    {
	        throw new IllegalStateException();
	    }
		return this.facade;
	}
}
