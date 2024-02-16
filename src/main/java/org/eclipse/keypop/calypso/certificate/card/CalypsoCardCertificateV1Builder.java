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

import java.security.interfaces.RSAPrivateKey;
import org.eclipse.keypop.calypso.certificate.CertificateSigningException;
import org.eclipse.keypop.calypso.certificate.card.spi.CalypsoCardCertificateSignerSpi;

/**
 * Builds a {@link CalypsoCardCertificateV1} conforming to version 1 of the Calypso card certificate
 * format.
 *
 * @since 0.1.0
 */
public interface CalypsoCardCertificateV1Builder {

  /**
   * Sets the public key of the card, provided as a 64-byte array.
   *
   * <p>This key is expected to be on the <strong>secp256r1</strong> elliptic curve. It will be used
   * for the verification of card signatures.
   *
   * @param cardPublicKey The 64-byte array representing the public key on the
   *     <strong>secp256r1</strong> curve.
   * @return The current instance.
   * @throws IllegalArgumentException If the provided key is null or out of range.
   * @since 0.1.0
   */
  CalypsoCardCertificateV1Builder withCardPublicKey(byte[] cardPublicKey);

  /**
   * Sets the start date of the validity period of the certificate's public key.
   *
   * <p>No consistency test is performed on the values supplied, as they will be coded in BCD
   * YYYYMMDD format in the certificate.
   *
   * <p>The start date is optional. If it is not defined, the certificate is not subject to a start
   * date constraint.
   *
   * @param year The year of the start date (0-9999).
   * @param month The month of the start date (1-99).
   * @param day The day of the start date (1-99).
   * @return The current instance.
   * @throws IllegalArgumentException If any date parameter is out of range.
   * @since 0.1.0
   */
  CalypsoCardCertificateV1Builder withStartDate(int year, int month, int day);

  /**
   * Sets the end date of the validity period of the certificate's public key.
   *
   * <p>No consistency test is performed on the values supplied, as they will be coded in BCD
   * YYYYMMDD format in the certificate.
   *
   * <p>The end date is optional. If it is not defined, the certificate is not subject to an end
   * date constraint.
   *
   * @param year The year of the start date (0-9999).
   * @param month The month of the start date (1-99).
   * @param day The day of the start date (1-99).
   * @return The current instance.
   * @throws IllegalArgumentException If any date parameter is out of range.
   * @since 0.1.0
   */
  CalypsoCardCertificateV1Builder withEndDate(int year, int month, int day);

  /**
   * Sets the AID of the autonomous PKI application of the target card.
   *
   * <p>The <b>aid</b> field cannot contain only zero bytes.
   *
   * @param aid The AID value as a 5 to 16 bytes byte array. Must not contain only zero bytes.
   * @return The current instance.
   * @throws IllegalArgumentException If the provided AID is null, out of range, or contains only
   *     zero bytes.
   * @since 0.1.0
   */
  CalypsoCardCertificateV1Builder withAid(byte[] aid);

  /**
   * Sets the serial number of the card for which the certificate is being generated.
   *
   * @param serialNumber The serial number of the card as an 8-byte byte array.
   * @return The current instance.
   * @throws IllegalArgumentException If the provided argument is null or out of range.
   * @since 0.1.0
   */
  CalypsoCardCertificateV1Builder withCardSerialNumber(byte[] serialNumber);

  /**
   * Sets the startup info of the card for which the certificate is being generated.
   *
   * @param startupInfo The 7-byte byte array representing the startup info for the card
   *     certificate.
   * @return The current instance.
   * @throws IllegalArgumentException If the provided argument is null or out of range.
   * @since 0.1.0
   */
  CalypsoCardCertificateV1Builder withCardStartupInfo(byte[] startupInfo);

  /**
   * Sets the index used to differentiate two card certificates generated with the same issuer
   * public key reference for the same card.
   *
   * <p>The index is optional. By default, it is set to 0.
   *
   * @param index The index of the card certificate.
   * @return The current instance.
   * @since 0.1.0
   */
  CalypsoCardCertificateV1Builder withIndex(int index);

  /**
   * Checks the consistency of the parameters, signs the certificate using the provided private key
   * and returns a new instance of {@link CalypsoCardCertificateV1}.
   *
   * <p>The internal signer will use the provided 2048 bits RSA private key with a public exponent
   * of 65537 and the specified public key reference for signing operations.
   *
   * @param issuerPrivateKey The RSA private key of the issuer (2048 bits, public exponent 65537).
   * @param issuerPublicKeyReference A 29-byte byte array representing a reference to the issuer's
   *     public key.
   * @return A non-null reference.
   * @throws IllegalArgumentException If one of the provided arguments is null.
   * @throws IllegalStateException If one of the required parameters is wrong or missing.
   * @throws CertificateSigningException If an error occurs during the signing process.
   * @since 0.1.0
   */
  CalypsoCardCertificateV1 build(RSAPrivateKey issuerPrivateKey, byte[] issuerPublicKeyReference);

  /**
   * Checks the consistency of the parameters, signs the certificate using the provided signer and
   * returns a new instance of {@link CalypsoCardCertificateV1}.
   *
   * @param cardCertificateSigner The external signer for card certificate generation.
   * @return A non-null reference.
   * @throws IllegalArgumentException If the provided signer is null.
   * @throws IllegalStateException If one of the required parameters is wrong or missing.
   * @throws CertificateSigningException If an error occurs during the signing process.
   * @since 0.1.0
   */
  CalypsoCardCertificateV1 build(CalypsoCardCertificateSignerSpi cardCertificateSigner);
}
