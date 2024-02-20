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

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import org.eclipse.keypop.calypso.certificate.ca.CalypsoCaCertificate;

/**
 * Provides a store for managing Calypso Certificate Authority (CA) certificates and keys.
 *
 * <p>This interface offers methods for adding the following to the store:
 *
 * <ul>
 *   <li>PCA public keys and key pairs
 *   <li>Calypso CA certificates
 *   <li>Calypso CA certificates with their associated private keys
 * </ul>
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
   * <p>This method is typically used for scenarios where the need is to verify Calypso CA
   * certificates. By providing both the public and private keys, the store has the necessary
   * information for these operations.
   *
   * <p>The key reference is to be used when creating certificates according to the desired
   * infrastructure.
   *
   * <p>This method is suitable for providing means to verify CA certificates.
   *
   * @param publicKeyReference The reference to the public key.
   * @param publicKey The public key.
   * @return The current instance.
   * @throws IllegalArgumentException If one of the argument is null or if the key is not a 2048-bit
   *     RSA key.
   * @since 0.1.0
   */
  CalypsoCertificateStore addPcaPublicKey(byte[] publicKeyReference, RSAPublicKey publicKey);

  /**
   * Adds a Primary Certification Authority (PCA) public key, its reference, and the associated
   * private key.
   *
   * <p>This method expects a 2048-bit RSA key with an exponent of 65537. Both the public part of
   * the key and its associated private part must be provided.
   *
   * <p>The provided public key reference will be used for identifying the key when creating
   * certificates within the certificate infrastructure.
   *
   * <p>This method is typically used for scenarios where the need is to verify and/or create
   * Calypso CA certificates. By providing both the public and private keys, the store has the
   * necessary information for these operations.
   *
   * @param publicKeyReference The reference to the public key.
   * @param publicKey The public key.
   * @param privateKey The associated private key corresponding to the provided public key.
   * @return The current instance.
   * @throws IllegalArgumentException If one of the argument is null or if the provided key pair is
   *     not a valid 2048-bit RSA key pair.
   * @since 0.1.0
   */
  CalypsoCertificateStore addPcaKeyPair(
      byte[] publicKeyReference, RSAPublicKey publicKey, RSAPrivateKey privateKey);

  /**
   * Adds a Calypso Certificate Authority (CA) certificate to the store.
   *
   * <p>This method adds the provided certificate to the store. The certificate must be valid
   * (signed by an already referenced authority) and issued by a trusted authority previously
   * referenced in the store.
   *
   * <p>This method is typically used to add an intermediate CA certificates necessary for
   * validating other certificates within the infrastructure.
   *
   * @param caCertificate The Calypso CA certificate to add.
   * @return The current instance.
   * @throws IllegalArgumentException If the certificate is null.
   * @throws CertificateConsistencyException If the certificate is not trusted.
   * @since 0.1.0
   */
  CalypsoCertificateStore addCalypsoCaCertificate(CalypsoCaCertificate caCertificate);

  /**
   * Adds a Calypso Certificate Authority (CA) certificate and its associated private key to the
   * store.
   *
   * <p>This method adds the provided certificate and its associated private key to the store. Both
   * the certificate must be valid (signed by an already referenced authority) and the private key
   * must correspond to the certificate.
   *
   * <p>This method is typically used for scenarios where you need to issue Calypso CA or card
   * certificates. By providing both the certificate and its private key, the store has the
   * necessary information to sign new certificates.
   *
   * @param caCertificate The Calypso CA certificate to add.
   * @param caPrivateKey The private key associated with the provided certificate.
   * @return The current instance.
   * @throws IllegalArgumentException If either the certificate or the private key is null or
   *     invalid, or if they do not correspond to each other.
   * @throws CertificateConsistencyException If the certificate is not trusted.
   * @since 0.1.0
   */
  CalypsoCertificateStore addCalypsoCaCertificate(
      CalypsoCaCertificate caCertificate, RSAPrivateKey caPrivateKey);
}
