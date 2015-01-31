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

package org.rapla.mobile.android.utility.factory;

import java.net.URL;

import org.rapla.RaplaMainContainer;
import org.rapla.facade.ClientFacade;
import org.rapla.facade.internal.FacadeImpl;
import org.rapla.framework.RaplaContext;
import org.rapla.framework.RaplaException;
import org.rapla.framework.StartupEnvironment;
import org.rapla.framework.logger.Logger;
import org.rapla.framework.logger.NullLogger;
import org.rapla.mobile.android.RaplaMobileException;
import org.rapla.storage.StorageOperator;
import org.rapla.storage.dbrm.RemoteOperator;

/**
 * Rapla Context Factory (Singleton)
 * 
 * @author Maximilian Lenkeit <dev@lenki.com>
 */
public class RaplaContextFactory {

	protected static RaplaContextFactory instance;

	protected RaplaContextFactory() {

	}

	/**
	 * Get factory instance (singleton)
	 * 
	 * @return Singleton
	 */
	public static RaplaContextFactory getInstance() {
		if (instance == null) {
			instance = new RaplaContextFactory();
		}
		return instance;
	}

	/**
	 * Create RaplaContext instance
	 * 
	 * @param url
	 *            Url for rapla server
	 * @param port
	 *            Port for rapla server
	 * @param isSecure
	 *            Whether to use http or https
	 */
	public RaplaContext createInstance(final String host)
			throws RaplaMobileException {

		
		try {
		    final URL url = new URL(host);
		    final Logger logger = new NullLogger();
            StartupEnvironment env = new StartupEnvironment() {
                
                @Override
                public int getStartupMode() {
                    return CONSOLE;
                }
                
                @Override
                public URL getDownloadURL() throws RaplaException {
                    return url;
                }
                
                @Override
                public Logger getBootstrapLogger() {
                    return logger;
                }
            };
			RaplaMainContainer container = new RaplaMainContainer(env);
			container.addContainerProvidedComponent(RemoteOperator.class, RemoteOperator.class);
			RemoteOperator lookup = container.getContext().lookup( RemoteOperator.class);
			container.addContainerProvidedComponentInstance( StorageOperator.class, lookup);
	        container.addContainerProvidedComponent(ClientFacade.class, FacadeImpl.class);
			RaplaContext context = container.getContext();
			return context;
		} catch (Exception ex) {
			throw new RaplaMobileException("Initializing context failed", ex);
		}
	}
}
