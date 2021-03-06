/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.emrmonitor.api.db.hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.openmrs.api.context.Context;
import org.openmrs.module.emrmonitor.EmrMonitorServer;
import org.openmrs.module.emrmonitor.EmrMonitorServerType;
import org.openmrs.module.emrmonitor.api.db.EmrMonitorDAO;

import java.util.List;

/**
 * It is a default implementation of  {@link EmrMonitorDAO}.
 */
public class HibernateEmrMonitorDAO implements EmrMonitorDAO {
	protected final Log log = LogFactory.getLog(this.getClass());
	
	private SessionFactory sessionFactory;
	
	/**
     * @param sessionFactory the sessionFactory to set
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
	    this.sessionFactory = sessionFactory;
    }
    
	/**
     * @return the sessionFactory
     */
    public SessionFactory getSessionFactory() {
	    return sessionFactory;
    }

    @Override
    public List<EmrMonitorServer> getEmrMonitorServers() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(EmrMonitorServer.class);
        List<EmrMonitorServer> list = null;
        try {
            list = (List<EmrMonitorServer>) criteria.list();
        } catch (Exception e) {
            log.error("Failed to retrieve emr monitor servers", e);
        }
        return list;
    }

    @Override
    public EmrMonitorServer getEmrMonitorServerByUuid(String serverUuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(EmrMonitorServer.class);
        criteria.add(Restrictions.eq("uuid", serverUuid));
        try {
            List<EmrMonitorServer> list = list = (List<EmrMonitorServer>) criteria.list();
            if (list != null && list.size() > 0 ) {
                return (EmrMonitorServer) list.get(0);
            }
        } catch (Exception e) {
            log.error("Failed to retrieve emr monitor server record", e);
        }
        return null;
    }

    @Override
    public EmrMonitorServer getEmrMonitorServerByType(EmrMonitorServerType serverType) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(EmrMonitorServer.class);
        criteria.add(Restrictions.eq("serverType", serverType));
        try {
            List<EmrMonitorServer> list = list = (List<EmrMonitorServer>) criteria.list();
            if (list != null && list.size() > 0 ) {
                return (EmrMonitorServer) list.get(0);
            }
        } catch (Exception e) {
            log.error("Failed to retrieve emr monitor servers", e);
        }
        return null;
    }

    @Override
    public EmrMonitorServer saveEmrMonitorServer(EmrMonitorServer server) {
        try{
            sessionFactory.getCurrentSession().saveOrUpdate(server);
        } catch (Exception e) {
            log.error("Error saving EmrMonitor Server", e);
        }
        return server;
    }
}