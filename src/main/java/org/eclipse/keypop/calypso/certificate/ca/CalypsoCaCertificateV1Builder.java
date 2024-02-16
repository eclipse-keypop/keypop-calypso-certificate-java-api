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
  CalypsoCaCertificateV1Builder withStartDate(int year, int month, int day);

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
  CalypsoCaCertificateV1Builder withEndDate(int year, int month, int day);

  /**
   * Restricts certificate validity to cards whose Application Identifier (AID) begins with the
   * bytes provided.
   *
   * <p>This method allows you to specify an AID value to limit the applicability of the
   * certificate. The certificate will only be valid for cards whose AID starts with the provided
   * bytes.
   *
   * <p>The AID is optional. When not set, no restriction related to the card AID will be applied.
   *
   * <p><b>Important:</b>
   *
   * <p>The <b>aid</b> field cannot contain only zero bytes.
   *
   * <p>The <b>isTruncated</b> field indicates whether the provided AID is truncated. If set to
   * <b>true</b>, the certificate will be valid for cards whose AID starts with the provided bytes,
   * even if the card's full AID is longer. If set to <b>false</b>, the certificate will only be
   * valid for cards whose full AID exactly matches the provided bytes.
   *
   * @param aid The AID value as a 5 to 16 bytes byte array. Must not contain only zero bytes.
   * @param isTruncated true if the provided AID is truncated, false otherwise.
   * @return The current instance.
   * @throws IllegalArgumentException If the provided AID is null, out of range, or contains only
   *     zero bytes.
   * @since 0.1.0
   */
  CalypsoCaCertificateV1Builder withAid(byte[] aid, boolean isTruncated);

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
   * The CA rights byte is optional. If not set, the default value is 0.
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
   * The CA scope byte is optional. If not set, the default value is 0.
   *
   * @param caScope The byte representing the CA scope for this card certificate.
   * @return The current instance.
   * @throws IllegalArgumentException If the provided byte contains RFU values.
   * @since 0.1.0
   */
  CalypsoCaCertificateV1Builder withCaScope(byte caScope);

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
   * @throws IllegalStateException If one of the required parameters is wrong or missing.
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
   * @throws IllegalStateException If one of the required parameters is wrong or missing.
   * @throws CertificateSigningException If an error occurs during the signing process.
   * @since 0.1.0
   */
  CalypsoCaCertificateV1 build(CalypsoCaCertificateSignerSpi caCertificateSigner);
}
