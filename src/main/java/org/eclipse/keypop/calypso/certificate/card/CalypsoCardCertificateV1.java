/* ******************************************************************************
 * Copyright (c) 2024 Calypso Networks Association https://calypsonet.org/
 *
 * This program and the accompanying materials are made available under the
 * terms of the MIT License which is available at
 * https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: MIT
 ****************************************************************************** */
package org.eclipse.keypop.calypso.certificate.card;

/**
 * A Calypso card certificate version 1.
 *
 * @since 0.1.0
 */
public interface CalypsoCardCertificateV1 {

  /**
   * Returns a byte array corresponding to the certificate as it is stored in the card.
   *
   * @return A 316-byte byte array.
   * @since 0.1.0
   */
  byte[] getRawData();
}
