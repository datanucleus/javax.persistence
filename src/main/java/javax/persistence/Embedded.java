/*
 * Copyright (c) 2008, 2009, 2011 Oracle, Inc. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0
 * which accompanies this distribution.  The Eclipse Public License is available
 * at http://www.eclipse.org/legal/epl-v10.html and the Eclipse Distribution License
 * is available at http://www.eclipse.org/org/documents/edl-v10.php.
 */
package javax.persistence;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Specifies a persistent field or property of an entity whose
 * value is an instance of an embeddable class. The embeddable
 * class must be annotated as {@link Embeddable}.
 *
 * <p> The <code>AttributeOverride</code>, <code>AttributeOverrides</code>,
 * <code>AssociationOverride</code>, and <code>AssociationOverrides</code>
 * annotations may be used to override mappings declared or defaulted
 * by the embeddable class.
 *
 * <pre>
 *   Example:
 *
 *   &#064;Embedded
 *   &#064;AttributeOverrides({
 *       &#064;AttributeOverride(name="startDate", column=&#064;Column("EMP_START")),
 *       &#064;AttributeOverride(name="endDate", column=&#064;Column("EMP_END"))
 *   })
 *   public EmploymentPeriod getEmploymentPeriod() { ... }
 * </pre>
 *
 * @see Embeddable
 * @see AttributeOverride
 * @see AttributeOverrides
 * @see AssociationOverride
 * @see AssociationOverrides
 *
 * @since Java Persistence 1.0
 */
@Target({ANNOTATION_TYPE, METHOD, FIELD})
@Retention(RUNTIME)
public @interface Embedded {

    /**
     * (Optional) The entity class that is the target of the association.
     * <p>
     * Defaults to the type of the field or property that stores the association.
     * @return target entity
     */
    Class targetEntity() default void.class;
}
