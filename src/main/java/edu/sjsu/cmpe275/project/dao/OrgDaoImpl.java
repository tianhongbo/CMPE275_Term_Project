package edu.sjsu.cmpe275.project.dao;

import edu.sjsu.cmpe275.project.model.Address;
import edu.sjsu.cmpe275.project.model.Organization;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Project Name: cmpe275lab2
 * Packet Name: edu.sjsu.cmpe275.project.dao
 * Author: Scott
 * Created Date: 11/5/15 4:56 PM
 * Copyright (c) 2015, 2015 All Right Reserved, http://sjsu.edu/
 * This source is subject to the GPL2 Permissive License.
 * Please see the License.txt file for more information.
 * All other rights reserved.
 * <p>
 * THIS CODE AND INFORMATION ARE PROVIDED "AS IS" WITHOUT WARRANTY OF ANY
 * KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND/OR FITNESS FOR A
 * PARTICULAR PURPOSE.
 */

@Repository
public class OrgDaoImpl implements OrgDao{
    @Autowired
    SessionFactory sessionFactory;

    public Organization create(Organization organization){
        Session session = null;
        Transaction transaction = null;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(organization);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            organization=null;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return organization;
    }

    @Override
    public Organization get(long id) {

        Session session = null;
        Transaction transaction = null;
        Organization organization = null;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            organization = (Organization)session.get(Organization.class, id);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return organization;
    }

    @Override
    public Organization update(Organization organization) {
        Session session = null;
        Transaction transaction = null;
        Organization organizationOld = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            organizationOld = (Organization)session.get(Organization.class, organization.getId());
            if (organizationOld == null) {
                throw new HibernateException("can't find organization with id = " + organization.getId());
            }
            if (organization.getDescription() != null) {
                organizationOld.setDescription(organization.getDescription());
            }
            if (organization.getName() != null) {
                organizationOld.setName(organization.getName());
            }
            if (organization.getAddress() != null) {
                Address address = new Address(organization.getAddress().getStreet(),
                        organization.getAddress().getCity(),
                        organization.getAddress().getState(),
                        organization.getAddress().getZip());
                organizationOld.setAddress(address);
            }
            session.update(organizationOld);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return organizationOld;
    }

    @Override
    public Organization delete(long id) {
        Session session = null;
        Transaction transaction = null;
        Organization organization = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            organization = (Organization)session.get(Organization.class, id);
            if(organization == null ) {
                throw new HibernateException("Can't find organization record with id = " + id);
            } else if (organization.getPersons() != null && organization.getPersons().size() != 0) {
                organization = null;
                throw new HibernateException("Can't delete organization record because it contains members.");
            }
            session.delete(organization);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return organization;
    }
}
