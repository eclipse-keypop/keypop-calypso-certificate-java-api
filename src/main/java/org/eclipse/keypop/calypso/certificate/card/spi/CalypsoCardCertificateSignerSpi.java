/* ******************************************************************************
 * Copyright (c) 2024 Calypso Networks Association https://calypsonet.org/
 *
 * This program and the accompanying materials are made available under the
 * terms of the MIT License which is available at
 * https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: MIT
 ****************************************************************************** */
package org.eclipse.keypop.calypso.certificate.card.spi;

/**
 * Signer for a Calypso card certificate.
 *
 * <p>Implementations of this interface provide the cryptographic signing functionality used to
 * generate signed Calypso card certificates.
 *
 * @since 0.1.0
 */
public interface CalypsoCardCertificateSignerSpi {

  /**
   * Returns the reference to the issuer's public key.
   *
   * @return A 29-byte byte array.
   * @since 0.1.0
   */
  byte[] getIssuerPublicKeyReference();

  /**
   * Generates a signed card certificate based on the provided data and recoverable data.
   *
   * @param data The byte array containing the non-recoverable data for the certificate.
   * @param recoverableData The byte array containing the recoverable data for the certificate. This
   *     might be encrypted or protected data that shouldn't be included in the final certificate
   *     but is needed for signature generation.
   * @return The signed card certificate, a 316-byte byte array containing the data followed by the
   *     signature.
   * @since 0.1.0
   */
  byte[] generateSignedCertificate(byte[] data, byte[] recoverableData);
}
