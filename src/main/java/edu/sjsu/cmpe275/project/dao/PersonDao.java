package edu.sjsu.cmpe275.project.dao;

import edu.sjsu.cmpe275.project.model.Person;

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
public interface PersonDao {
    public Person create(Person person, Long orgId);
    public Person get(long id);
    public Person update(Person person, Long orgId);
    public Person delete(long id);
}

