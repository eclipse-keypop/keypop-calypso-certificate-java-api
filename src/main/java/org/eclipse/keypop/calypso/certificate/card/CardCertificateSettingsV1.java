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

import java.security.PrivateKey;
import org.eclipse.keypop.calypso.certificate.card.spi.CardCertificateSignerSpi;

/**
 * Extends {@link CardCertificateSettings} to manage and generate card certificates, conforming to
 * version 1 of the card certificate format.
 *
 * @since 0.1.0
 */
public interface CardCertificateSettingsV1 extends CardCertificateSettings {

  /**
   * Sets the external signer to be used for generating signed card certificates.
   *
   * @param cardCertificateSigner The external signer for card certificate generation.
   * @return The current instance.
   * @throws IllegalArgumentException If the provided signer is null, invalid, or not compatible
   *     with the required signature formats.
   * @throws IllegalStateException If an internal signer has already been configured using {@link
   *     #useInternalSigner(PrivateKey, byte[])}.
   * @since 0.1.0
   */
  CardCertificateSettingsV1 useExternalSigner(CardCertificateSignerSpi cardCertificateSigner);

  /**
   * Configures the settings to use the internal signer for generating signed card certificates.
   *
   * <p>The internal signer will use the provided 2048 bits RSA private key with a public exponent
   * of 65537 and the specified public key reference for signing operations.
   *
   * @param issuerPrivateKey The RSA private key of the issuer (2048 bits, public exponent 65537).
   * @param issuerPublicKeyReference A 29-byte byte array representing a reference to the issuer's
   *     public key.
   * @return The current instance.
   * @throws IllegalArgumentException If any of the provided arguments are null, invalid, or have
   *     incompatible formats.
   * @throws IllegalStateException If an external signer has already been set using {@link
   *     #useExternalSigner(CardCertificateSignerSpi)}.
   * @since 0.1.0
   */
  CardCertificateSettingsV1 useInternalSigner(
      PrivateKey issuerPrivateKey, byte[] issuerPublicKeyReference);

  /**
   * Sets the public key of the card, provided as a 64-byte raw array.
   *
   * <p>This key is expected to be on the <strong>secp256r1</strong> elliptic curve. It will be used
   * for the verification of card signatures.
   *
   * @param cardPublicKey The 64-byte raw array representing the public key on the
   *     <strong>secp256r1</strong> curve.
   * @return The current instance.
   * @throws IllegalArgumentException If the provided key is null or has an invalid length (not 64).
   * @since 0.1.0
   */
  CardCertificateSettingsV1 setCardPublicKey(byte[] cardPublicKey);

  /**
   * Sets the validity period of the certificate's public key. This defines the timeframe when the
   * certificate can be considered trusted.
   *
   * <p>If neither start nor end date is set, the certificate will have open-ended validity.
   *
   * @param startDateYear The year of the start date (e.g., 2024). Valid range: 1900-2100.
   * @param startDateMonth The month of the start date (1-12).
   * @param startDateDay The day of the start date (1-31).
   * @param endDateYear The year of the end date (e.g., 2025). Valid range: 1900-2100.
   * @param endDateMonth The month of the end date (1-12).
   * @param endDateDay The day of the end date.
   * @return The current instance.
   * @throws IllegalArgumentException If any date parameter is out of range.
   * @since 0.1.0
   */
  CardCertificateSettingsV1 setValidityPeriod(
      int startDateYear,
      int startDateMonth,
      int startDateDay,
      int endDateYear,
      int endDateMonth,
      int endDateDay);

  /**
   * Sets the AID (Application Identifier) associated with the certificate.
   *
   * @param aid The AID value as a 5 to 16 bytes byte array.
   * @return The current instance.
   * @throws IllegalArgumentException If the provided argument is null or out of range.
   * @since 0.1.0
   */
  CardCertificateSettingsV1 setAid(byte[] aid);

  /**
   * Sets the serial number of the card for which the certificate is being generated.
   *
   * @param serialNumber The serial number of the card as a 8-byte byte array.
   * @return The current instance.
   * @throws IllegalArgumentException If the provided argument is null or out of range.
   * @since 0.1.0
   */
  CardCertificateSettingsV1 setCardSerialNumber(byte[] serialNumber);

  /**
   * Sets the startup info for the card certificate.
   *
   * @param startupInfo The 7-byte byte array representing the startup info for the card
   *     certificate.
   * @return The current instance.
   * @throws IllegalArgumentException If the provided argument is null or out of range.
   * @since 0.1.0
   */
  CardCertificateSettingsV1 setCardStartupInfo(byte[] startupInfo);

  /**
   * Sets the index used to differentiate two card certificates generated with the same issuer
   * public key reference for the same card.
   *
   * @param index The index of the card certificate.
   * @return The current instance.
   * @since 0.1.0
   */
  CardCertificateSettingsV1 setIndex(int index);
}
