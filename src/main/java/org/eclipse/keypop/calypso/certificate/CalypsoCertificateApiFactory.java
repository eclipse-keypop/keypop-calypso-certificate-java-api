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

import org.eclipse.keypop.calypso.certificate.spi.CalypsoCertificateSignerSpi;

/**
 * Factory of CA and card certificate builders.
 *
 * @since 0.1.0
 */
public interface CalypsoCertificateApiFactory {

  /**
   * Returns the store where Calypso Certificate Authority (CA) certificates and optionally the
   * private keys are injected and made available for certificate generation processes.
   *
   * <p>The store contains the necessary certificates and private keys required for generating
   * Calypso CA and card certificates.
   *
   * <p>Use the methods provided by this store to inject the appropriate certificates and private
   * keys before creating the certificate builders.
   *
   * @return A non-null reference.
   * @since 0.1.0
   */
  CalypsoCertificateStore getCalypsoCertificateStore();

  /**
   * Creates a new generator for Calypso CA certificates (version 1) using an external signer.
   *
   * <p>The generator is used to configure and generate signed Calypso CA certificates.
   *
   * <p>This method must be called after the issuer certificate of the signer using the specified
   * issuer public key reference.
   *
   * @param issuerPublicKeyReference The reference to issuer's public key in the store.
   * @param caCertificateSigner The external signer to use for signing the CA certificate.
   * @throws IllegalStateException If the provided reference is unknown.
   * @throws CertificateConsistencyException If the provided reference doesn't designate a valid
   *     certification for the operation.
   * @return A non-null reference.
   * @since 0.1.0
   */
  CalypsoCaCertificateV1Generator createCalypsoCaCertificateV1Generator(
      byte[] issuerPublicKeyReference, CalypsoCertificateSignerSpi caCertificateSigner);

  /**
   * Creates a new generator for Calypso card certificates (version 1) using an external signer.
   *
   * <p>The generator is used to configure and generate signed Calypso card certificates.
   *
   * <p>This method must be called after the issuer certificate of the signer using the specified
   * issuer public key reference.
   *
   * @param issuerPublicKeyReference The reference to issuer's public key in the store.
   * @param cardCertificateSigner The external signer to use for signing the card certificate.
   * @throws IllegalStateException If the provided reference is unknown.
   * @throws CertificateConsistencyException If the provided reference doesn't designate a valid
   *     certification for the operation.
   * @return A non-null reference.
   * @since 0.1.0
   */
  CalypsoCardCertificateV1Generator createCalypsoCardCertificateV1Generator(
      byte[] issuerPublicKeyReference, CalypsoCertificateSignerSpi cardCertificateSigner);
}
