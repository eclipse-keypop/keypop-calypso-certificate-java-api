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
 * CA Certificate compliant with the 384-byte format supported by the Calypso cards.
 *
 * @since 0.1.0
 */
public interface CalypsoCaCertificate extends CaCertificate {

  /**
   * Returns a byte array corresponding to the certificate as it is stored in the card.
   *
   * @return A 384-byte byte array.
   * @since 0.1.0
   */
  byte[] getRawData();
}
