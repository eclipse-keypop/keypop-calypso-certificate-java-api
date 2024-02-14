/* ******************************************************************************
 * Copyright (c) 2024 Calypso Networks Association https://calypsonet.org/
 *
 * This program and the accompanying materials are made available under the
 * terms of the MIT License which is available at
 * https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: MIT
 ****************************************************************************** */
package org.eclipse.keypop.calypso.certificate;

import org.eclipse.keypop.calypso.certificate.ca.CalypsoCaCertificateV1Builder;
import org.eclipse.keypop.calypso.certificate.card.CalypsoCardCertificateV1Builder;

/**
 * Factory of CA and card certificate builders.
 *
 * @since 0.1.0
 */
public interface CalypsoCertificateApiFactory {

  /**
   * Returns a builder of Calypso CA certificate version 1.
   *
   * @return A non-null reference.
   * @since 0.1.0
   */
  CalypsoCaCertificateV1Builder createCalypsoCaCertificateV1Builder();

  /**
   * Returns a builder of Calypso card certificate version 1.
   *
   * @return A non-null reference.
   * @since 0.1.0
   */
  CalypsoCardCertificateV1Builder createCalypsoCardCertificateV1Builder();
}
