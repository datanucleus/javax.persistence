/*
 * Copyright (c) 2008, 2009, 2011 Oracle, Inc. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0
 * which accompanies this distribution.  The Eclipse Public License is available
 * at http://www.eclipse.org/legal/epl-v10.html and the Eclipse Distribution License
 * is available at http://www.eclipse.org/org/documents/edl-v10.php.
 */
package javax.persistence.metamodel;

/**
 * Instances of the type <code>SetAttribute</code> represent
 * persistent <code>java.util.Set</code>-valued attributes.
 *
 * @param <X> The type the represented Set belongs to
 * @param <E> The element type of the represented Set
 *
 * @since Java Persistence 2.0
 */
public interface SetAttribute<X, E> extends PluralAttribute<X, java.util.Set<E>, E> {
}
