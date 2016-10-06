/*
 * Copyright (c) 2008, 2009, 2011 Oracle, Inc. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0
 * which accompanies this distribution.  The Eclipse Public License is available
 * at http://www.eclipse.org/legal/epl-v10.html and the Eclipse Distribution License
 * is available at http://www.eclipse.org/org/documents/edl-v10.php.
 */
package javax.persistence.criteria;

/**
 * An object that defines an ordering over the query results.
 */
public interface Order
{
    /**
     * Switch the ordering.
     * @return a new <code>Order</code> instance with the reversed ordering
     */
    Order reverse();

    /**
     * Whether ascending ordering is in effect.
     * @return boolean indicating whether ordering is ascending
     */
    boolean isAscending();

    /**
     * Return the expression that is used for ordering.
     * @return expression used for ordering
     */
    Expression<?> getExpression();

    /**
     * Set nulls to be positioned first in the ordering.
     * <b>Note that this is a DataNucleus extension, post JPA2.1</b>
     * @return This instance
     */
    Order nullsFirst();

    /**
     * Set nulls to be positioned last in the ordering.
     * <b>Note that this is a DataNucleus extension, post JPA 2.1</b>
     * @return This instance
     */
    Order nullsLast();
}
