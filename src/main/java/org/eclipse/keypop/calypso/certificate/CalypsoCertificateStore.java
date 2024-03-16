/* ******************************************************************************
 * Copyright (c) 2024 Calypso Networks Association https://calypsonet.org/
 *
 * This program and the accompanying materials are made available under the
 * terms of the MIT License which is available at
 * https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: MIT
 ****************************************************************************** */
package org.eclipse.keypop.calypso.certificate;

import java.security.interfaces.RSAPublicKey;

/**
 * Provides a store for managing Calypso Certificate Authority (CA) certificates and public keys.
 *
 * <p>The stored certificates and keys are used for verifying and creating Calypso CA certificates
 * within the certificate infrastructure.
 *
 * <p>Certificates and keys must be added to the store in a specific trust order to ensure a proper
 * chain of trust within the certificate infrastructure. This order reflects the trust relationships
 * between different authorities, allowing certificates to verify their validity up to a trusted
 * root.
 *
 * @since 0.1.0
 */
public interface CalypsoCertificateStore {

  /**
   * Adds a Primary Certification Authority (PCA) public key and its reference.
   *
   * <p>This method expects a 2048-bit RSA public key with an exponent of 65537.
   *
   * <p>The provided public key reference will be used for identifying the key when creating
   * certificates within the certificate infrastructure.
   *
   * <p>The key reference is to be used when creating certificates according to the desired
   * infrastructure.
   *
   * <p>This method is suitable for providing means to verify CA certificates.
   *
   * @param pcaPublicKeyReference The reference to the PCA public key.
   * @param pcaPublicKey The PCA public key.
   * @throws IllegalArgumentException If one of the argument is null or if the key is not a 2048-bit
   *     RSA key.
   * @throws IllegalStateException If the reference to the public key already in the store.
   * @since 0.1.0
   */
  void addPcaPublicKey(byte[] pcaPublicKeyReference, RSAPublicKey pcaPublicKey);

  /**
   * Adds a Primary Certification Authority (PCA) public key from it modulus and its reference.
   *
   * <p>This method expects the 256-byte modulus of a 2048-bit RSA public key with an exponent of
   * 65537.
   *
   * <p>The provided public key reference will be used for identifying the key when creating
   * certificates within the certificate infrastructure.
   *
   * <p>The key reference is to be used when creating certificates according to the desired
   * infrastructure.
   *
   * <p>This method is suitable for providing means to verify CA certificates.
   *
   * @param pcaPublicKeyReference The reference to the PCA public key.
   * @param pcaPublicKeyModulus The modulus of the PCA public key as a 256-byte byte array.
   * @throws IllegalArgumentException If one of the argument is null or if the key modulus is not
   *     256 bytes long.
   * @throws IllegalStateException If the reference to the public key already in the store.
   * @throws CertificateConsistencyException If the certificate is not trusted.
   * @since 0.1.0
   */
  void addPcaPublicKey(byte[] pcaPublicKeyReference, byte[] pcaPublicKeyModulus);

  /**
   * Adds a Calypso Certificate Authority (CA) certificate to the store and returns its public key
   * reference.
   *
   * <p>This method adds the provided certificate to the store. The certificate must be valid
   * (signed by an already referenced authority) and issued by a trusted authority previously
   * referenced in the store.
   *
   * <p>This method is typically used to add an intermediate CA certificates necessary for
   * validating other certificates within the infrastructure.
   *
   * @param caCertificate A 384-byte byte array containing the Calypso CA certificate to add.
   * @return The public key reference of the added certificate.
   * @throws IllegalArgumentException If the certificate is null or its format is unknown.
   * @throws IllegalStateException If the reference to the public key already in the store or the
   *     parent certificate was not found.
   * @throws CertificateConsistencyException If the certificate is not trusted.
   * @since 0.1.0
   */
  byte[] addCalypsoCaCertificate(byte[] caCertificate);
}
