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
 * Provides methods to create a card certificate and get the associated card public key.
 *
 * @since 0.1.0
 */
public interface CardCertificateManager {

  /**
   * Based on provided settings, generates the certificate data to be stored in a card.
   *
   * @return A 384-byte byte array.
   * @since 0.1.0
   */
  byte[] createCertificate();

  /**
   * Based on provided settings, extracts the public key data.
   *
   * @return A 64-byte byte array.
   * @since 0.1.0
   */
  byte[] getCardPublicKeyData();
}
