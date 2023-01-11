/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.keycloak.quickstart.event.listener;

import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventType;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.events.admin.OperationType;
import org.keycloak.models.UserModel;
import org.keycloak.models.UserProvider;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;

import java.util.Map;
import java.util.Set;
import java.util.Random;


/**
 * @author <a href="mailto:remy@grunblatt.org">Rémy Grünblatt</a>
 */
public class MattermostAutoGenerateIdEventListenerProvider implements EventListenerProvider {
    private Set<EventType> includedEvents;
    private KeycloakSession session;

    public MattermostAutoGenerateIdEventListenerProvider(KeycloakSession session, Set<EventType> includedEvents) {
        this.session = session;
        this.includedEvents = includedEvents;
    }

    @Override
    public void onEvent(Event event) {
        if (includedEvents != null && includedEvents.contains(event.getType())) {
            RealmModel realm = session.getContext().getRealm();
            UserModel user = session.users().getUserById(realm, event.getUserId());
               if(user.getAttributeStream("mattermostId").count() == 0) {
                Long generatedLong = new Random().nextLong() & Long.MAX_VALUE;
                while (!(session.users().searchForUserByUserAttributeStream(realm, "mattermostId", generatedLong.toString()).count() == 0)) {
                    System.out.println("Collision detected, generating a new id.");
                    generatedLong = new Random().nextLong() & Long.MAX_VALUE;
                }
                user.setSingleAttribute("mattermostId", generatedLong.toString());
                System.out.println("Assigning ID " + generatedLong.toString() + " to " + user.getUsername() + " [ID: " + user.getId() + "]");
            }
        }
    }

    @Override
    public void onEvent(AdminEvent event, boolean includeRepresentation) {
        // Do nothing
    }
    
    @Override
    public void close() {
    }

}
