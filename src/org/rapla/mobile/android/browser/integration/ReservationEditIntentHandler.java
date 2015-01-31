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

package org.rapla.mobile.android.browser.integration;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.rapla.entities.Entity;
import org.rapla.entities.domain.Reservation;
import org.rapla.facade.ClientFacade;
import org.rapla.mobile.android.RaplaMobileApplication;
import org.rapla.mobile.android.RuntimeStorage;
import org.rapla.mobile.android.activity.EventDetailsActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * This class handles browser intents for editing a reservation. Pattern:
 * <code>raplaclient://action/edit/reservation/{id}</code>.
 * 
 * @author Maximilian Lenkeit <dev@lenki.com>
 * 
 */
public class ReservationEditIntentHandler implements BrowserIntentHandler {

	/**
	 * Matches <code>raplaclient://action/edit/reservation/{id}</code>
	 * 
	 * @see org.rapla.mobile.android.browser.integration.BrowserIntentHandler#matches(android.net.Uri)
	 */
	public boolean matches(Uri data) {
		List<String> parameters = data.getPathSegments();
		//String action = parameters.get(0);
		//String raplaType = parameters.get(1);
		//String id = parameters.get(2);
		return true;

		// Check path segments
//		if (action.equals("edit") && raplaType.equals("reservation") ) {
//			return true;
//		} else {
//			return false;
//		}
	}

	/**
	 * Handle intent by reading the reservation id, putting the reservation into
	 * the runtime storage and starting the corresponding event details screen
	 * for editing the reservation.
	 * 
	 * @see org.rapla.mobile.android.browser.integration.BrowserIntentHandler#handleIntent(android.content.Context,
	 *      org.rapla.mobile.android.RaplaMobileApplication,
	 *      android.content.Intent, org.rapla.framework.RaplaContext)
	 */
	public void handleIntent(Context context,
			RaplaMobileApplication application, Intent intent,
			ClientFacade facade) throws Exception {

		
		// Get reservation
		List<String> parameters = intent.getData().getPathSegments();
		String reservationId = parameters.get(2);
		boolean throwEntityNotFound = true;
        Collection<String> idSet = Collections.singleton( reservationId );
        Map<String, Entity> fromId = facade.getOperator().getFromId(idSet, throwEntityNotFound);
        Reservation reservation = (Reservation) fromId.get( reservationId);
		Reservation editableReservation = facade.edit(reservation);

		// Put reservation into runtime storage
		application.storageSet(RuntimeStorage.IDENTIFIER_SELECTED_RESERVATION,
				editableReservation);

		// Create intent and start activity
		Intent i = new Intent(context, EventDetailsActivity.class);
		context.startActivity(i);
	}

	
}
