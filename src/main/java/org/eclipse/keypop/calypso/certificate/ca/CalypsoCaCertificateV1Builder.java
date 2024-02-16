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

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import org.eclipse.keypop.calypso.certificate.CertificateSigningException;
import org.eclipse.keypop.calypso.certificate.ca.spi.CalypsoCaCertificateSignerSpi;

/**
 * Builds a {@link CalypsoCaCertificateV1} conforming to version 1 of the Calypso CA certificate
 * format.
 *
 * @since 0.1.0
 */
public interface CalypsoCaCertificateV1Builder {

  /**
   * Sets the public key of the CA.
   *
   * <p>This key is expected to be a 2048 bits RSA public key with a public exponent equal to 65537.
   * It will be used for the verification of card certificates.
   *
   * <p>The associated reference is a 29-byte byte array.
   *
   * @param caPublicKey The RSA public key of the CA (2048 bits, public exponent 65537).
   * @param caPublicKeyReference A 29-byte byte array representing a reference to the CA's public
   *     key.
   * @return The current instance.
   * @throws IllegalArgumentException If one of the provided argument is null or invalid.
   * @since 0.1.0
   */
  CalypsoCaCertificateV1Builder withCaPublicKey(
      RSAPublicKey caPublicKey, byte[] caPublicKeyReference);

  /**
   * Sets the start date of the validity period of the certificate's public key.
   *
   * <p>No consistency test is performed on the values supplied, as they will be coded in BCD
   * YYYYMMDD format in the certificate.
   *
   * @param year The year of the start date (0-9999).
   * @param month The month of the start date (1-99).
   * @param day The day of the start date (1-99).
   * @return The current instance.
   * @throws IllegalArgumentException If any date parameter is out of range.
   * @since 0.1.0
   */
  CalypsoCaCertificateV1Builder withStartDate(int year, int month, int day);

  /**
   * Sets the end date of the validity period of the certificate's public key.
   *
   * <p>No consistency test is performed on the values supplied, as they will be coded in BCD
   * YYYYMMDD format in the certificate.
   *
   * @param year The year of the start date (0-9999).
   * @param month The month of the start date (1-99).
   * @param day The day of the start date (1-99).
   * @return The current instance.
   * @throws IllegalArgumentException If any date parameter is out of range.
   * @since 0.1.0
   */
  CalypsoCaCertificateV1Builder withEndDate(int year, int month, int day);

  /**
   * Restricts certificate validity to cards whose AID begins with the bytes provided.
   *
   * <p>If the AID is not set, the certificate will be applicable to any card certificates.
   *
   * @param aid The AID value as a 5 to 16 bytes byte array.
   * @return The current instance.
   * @throws IllegalArgumentException If the provided argument is null or out of range.
   * @since 0.1.0
   */
  CalypsoCaCertificateV1Builder withAid(byte[] aid);

  /**
   * Sets the CA rights for this card certificate, controlling which types of certificates can be
   * authenticated.
   *
   * <p>The provided byte defines the following permissions:
   *
   * <ul>
   *   <li><b>Bits b7-b4:</b> Reserved for future use (RFU). Must be set to 0.
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
   * @throws IllegalArgumentException If the provided byte contains RFU values.
   * @since 0.1.0
   */
  CalypsoCaCertificateV1Builder withCaRights(byte caRights);

  /**
   * Sets the CA scope for this card certificate, defining the context in which the CA key pair can
   * be used.
   *
   * <p>The provided byte specifies the allowed usage context:
   *
   * <ul>
   *   <li>%00: Scope restrictions not specified.
   *   <li>%01: Allowed only for development, tests, pilots, etc. (limited scope).
   *   <li>%FF: No scope restriction (full scope).
   *   <li>Other values: Reserved for future use (RFU).
   * </ul>
   *
   * @param caScope The byte representing the CA scope for this card certificate.
   * @return The current instance.
   * @throws IllegalArgumentException If the provided byte contains RFU values.
   * @since 0.1.0
   */
  CalypsoCaCertificateV1Builder withCaScope(byte caScope);

  /**
   * Sets the CA operating mode, controlling how the target Calypso Prime PKI application AID should
   * be verified during card certificate validation.
   *
   * <p>The provided byte defines the following behavior:
   *
   * <ul>
   *   <li><b>Bits b7-b1:</b> Reserved for future use (RFU). Must be set to 0.
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
   * @throws IllegalArgumentException If the provided byte contains RFU values.
   * @since 0.1.0
   */
  CalypsoCaCertificateV1Builder withCaOperatingMode(byte caOperatingMode);

  /**
   * Checks the consistency of the parameters, signs the certificate using the provided private key
   * and returns a new instance of {@link CalypsoCaCertificateV1}.
   *
   * <p>The internal signer will use the provided 2048 bits RSA private key with a public exponent
   * of 65537 and the specified public key reference for signing operations.
   *
   * @param issuerPrivateKey The RSA private key of the issuer (2048 bits, public exponent 65537).
   * @param issuerPublicKeyReference A 29-byte byte array representing a reference to the issuer's
   *     public key.
   * @return A non-null reference.
   * @throws IllegalArgumentException If one of the provided arguments is null.
   * @throws CertificateSigningException If an error occurs during the signing process.
   * @since 0.1.0
   */
  CalypsoCaCertificateV1 build(RSAPrivateKey issuerPrivateKey, byte[] issuerPublicKeyReference);

  /**
   * Checks the consistency of the parameters, signs the certificate using the provided signer and
   * returns a new instance of {@link CalypsoCaCertificateV1}.
   *
   * @param caCertificateSigner The external signer to use for signing the CA certificate.
   * @return A non-null reference.
   * @throws IllegalArgumentException If the provided signer is null.
   * @throws CertificateSigningException If an error occurs during the signing process.
   * @since 0.1.0
   */
  CalypsoCaCertificateV1 build(CalypsoCaCertificateSignerSpi caCertificateSigner);
}
