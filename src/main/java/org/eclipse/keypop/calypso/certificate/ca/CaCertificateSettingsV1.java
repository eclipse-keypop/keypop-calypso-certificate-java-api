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

import java.security.PrivateKey;
import java.security.PublicKey;
import org.eclipse.keypop.calypso.certificate.ca.spi.CaCertificateSignerSpi;

/**
 * Extends {@link CaCertificateSettings} to manage and generate CA certificates, conforming to
 * version 1 of the CA certificate format.
 *
 * @since 0.1.0
 */
public interface CaCertificateSettingsV1 extends CaCertificateSettings {
  /**
   * Sets the external signer to be used for generating signed CA certificates.
   *
   * @param caCertificateSigner The external signer for ca certificate generation.
   * @return The current instance.
   * @throws IllegalArgumentException If the provided signer is null, invalid, or not compatible
   *     with the required signature formats.
   * @throws IllegalStateException If an internal signer has already been configured using {@link
   *     #useInternalSigner(PrivateKey, byte[])}.
   * @since 0.1.0
   */
  CaCertificateSettingsV1 useExternalSigner(CaCertificateSignerSpi caCertificateSigner);

  /**
   * Configures the settings to use the internal signer for generating signed CA certificates.
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
   *     #useExternalSigner(CaCertificateSignerSpi)}.
   * @since 0.1.0
   */
  CaCertificateSettingsV1 useInternalSigner(
      PrivateKey issuerPrivateKey, byte[] issuerPublicKeyReference);

  /**
   * Sets the public key of the CA, provided as a 64-byte raw array.
   *
   * <p>This key is expected to be a 2048 bits RSA public key with a public exponent equal to 65537.
   * It will be used for the verification of card certificates.
   *
   * <p>The associated reference is a 29-byte byte array.
   *
   * @param caPublicKey The RSA public key of the CA (2048 bits, public exponent 65537).
   * @param caPublicKeyReference The CA public key reference.
   * @return The current instance.
   * @throws IllegalArgumentException If one of the provided argument is null or invalid.
   * @since 0.1.0
   */
  CaCertificateSettingsV1 setCaPublicKey(PublicKey caPublicKey, byte[] caPublicKeyReference);

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
  CaCertificateSettingsV1 setValidityPeriod(
      int startDateYear,
      int startDateMonth,
      int startDateDay,
      int endDateYear,
      int endDateMonth,
      int endDateDay);

  /**
   * Sets the AID (Application Identifier) of the card certificates for which the certificate is
   * applicable.
   *
   * <p>If the AID is not set, the certificate will be applicable to any card certificates.
   *
   * @param aid The AID value as a 5 to 16 bytes byte array.
   * @return The current instance.
   * @throws IllegalArgumentException If the provided argument is null or out of range.
   * @since 0.1.0
   */
  CaCertificateSettingsV1 setAid(byte[] aid);

  /**
   * Sets the CA rights for this card certificate, controlling which types of certificates the card
   * can be used to authenticate.
   *
   * <p>The provided <code>caRights</code> byte defines the following permissions:
   *
   * <ul>
   *   <li><b>Bits b7-b4:</b> Reserved for future use (RFU). Must be set to 0. <br>
   *       Attempting to set non-zero values in these bits will throw an {@link
   *       IllegalArgumentException}.
   *   <li><b>Bits b3-b2:</b> Card key certificates authentication right:
   *       <ul>
   *         <li>%00: CardCert authentication right not specified.
   *         <li>%01: CardCert authentication forbidden.
   *         <li>%10: CardCert authentication allowed.
   *         <li>%11: Reserved for future use.
   *       </ul>
   *   <li><b>Bits b1-b0:</b> CA key certificates authentication right:
   *       <ul>
   *         <li>%00: CACert authentication right not specified.
   *         <li>%01: CACert authentication forbidden.
   *         <li>%10: CACert authentication allowed.
   *         <li>%11: Reserved for future use.
   *       </ul>
   * </ul>
   *
   * @param caRights The byte representing the CA rights for this card certificate.
   * @return The current instance.
   * @throws IllegalArgumentException If the provided byte contains invalid values, including
   *     non-zero values in reserved bits.
   * @since 0.1.0
   */
  CaCertificateSettingsV1 setCaRights(byte caRights);

  /**
   * Sets the CA scope for this card certificate, defining the context in which the CA key pair can
   * be used.
   *
   * <p>The provided <code>caScope</code> byte specifies the allowed usage context:
   *
   * <ul>
   *   <li>%00: Scope restrictions not specified.
   *   <li>%01: Allowed only for development, tests, pilots, etc. (limited scope).
   *   <li>%FF: No scope restriction (full scope).
   *   <li>Other values: Reserved for future use (RFU).
   * </ul>
   *
   * Choosing an appropriate scope is crucial for security and proper certificate management. Select
   * a scope that aligns with the intended use of the CA key pair to avoid potential misuse.
   *
   * @param caScope The byte representing the CA scope for this card certificate.
   * @return The current instance.
   * @throws IllegalArgumentException If the provided byte contains an invalid value, including
   *     non-standard or reserved values.
   * @since 0.1.0
   */
  CaCertificateSettingsV1 setCaScope(byte caScope);

  /**
   * Sets the CA operating mode, controlling how the target Calypso Prime PKI application AID should
   * be verified during card certificate validation.
   *
   * <p>The provided <code>caOperatingMode</code> byte defines the following behavior:
   *
   * <ul>
   *   <li><b>Bits b7-b1:</b> Reserved for future use (RFU). Must be set to 0. <br>
   *       Attempting to set non-zero values in these bits will throw an {@link
   *       IllegalArgumentException}.
   *   <li><b>Bit b0:</b> Target Calypso Prime PKI application AID matching:
   *       <ul>
   *         <li><b>%0:</b> Truncation forbidden:
   *             <ul>
   *               <li>The size of the card AID in the card certificate must be equal to the size of
   *                   the target AID specified in this certificate.
   *               <li>The corresponding number of first (leftmost) bytes of the card AID value in
   *                   the card certificate and the target AID value in this certificate must be
   *                   equal.
   *             </ul>
   *         <li><b>%1:</b> Truncation allowed:
   *             <ul>
   *               <li>The size of the card AID in the card certificate can be equal to or greater
   *                   than the size of the target AID specified in this certificate.
   *               <li>The specified size first (leftmost) bytes of the card AID value in the card
   *                   certificate and the target AID value in this certificate must be equal.
   *             </ul>
   *       </ul>
   * </ul>
   *
   * Choosing an appropriate operating mode is crucial for secure and verified certificate issuance.
   * Refer to the Calypso Prime PKI specifications for further details and guidelines.
   *
   * @param caOperatingMode The byte representing the CA operating mode for this card certificate.
   * @return The current instance.
   * @throws IllegalArgumentException If the provided byte contains invalid values, including
   *     non-zero values in reserved bits.
   * @since 0.1.0
   */
  CaCertificateSettingsV1 setCaOperatingMode(byte caOperatingMode);
}
