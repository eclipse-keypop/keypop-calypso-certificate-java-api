/* ******************************************************************************
 * Copyright (c) 2024 Calypso Networks Association https://calypsonet.org/
 *
 * This program and the accompanying materials are made available under the
 * terms of the MIT License which is available at
 * https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: MIT
 ****************************************************************************** */
package org.eclipse.keypop.calypso.certificate.ca;

import org.eclipse.keypop.calypso.card.transaction.spi.CaCertificate;

/**
 * A Calypso CA certificate version 1.
 *
 * <p>It can be used for certificate generation and card transaction security settings.
 *
 * @see CaCertificate
 * @since 0.1.0
 */
public interface CalypsoCaCertificateV1 extends CaCertificate {

  /**
   * Returns a byte array corresponding to the certificate as it is stored in the card.
   *
   * @return A 384-byte byte array.
   * @since 0.1.0
   */
  byte[] getRawData();
}
