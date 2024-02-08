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

/**
 * Provides a method to create a CA certificate.
 *
 * @since 0.1.0
 */
public interface CaCertificateManager {
  /**
   * Returns a byte array containing the data to be stored in a card.
   *
   * @return A 384-byte byte array.
   * @since 0.1.0
   */
  byte[] createCertificate();
}
