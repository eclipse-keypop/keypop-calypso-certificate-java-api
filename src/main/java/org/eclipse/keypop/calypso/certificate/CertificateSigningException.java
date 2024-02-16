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

/**
 * Exception that is thrown when an error occurs during the certificate signing process.
 *
 * @since 0.1.0
 */
public class CertificateSigningException extends RuntimeException {

  /**
   * @param message Message to identify the exception context.
   * @since 0.1.0
   */
  public CertificateSigningException(String message) {
    super(message);
  }

  /**
   * Encapsulates a lower level exception.
   *
   * @param message Message to identify the exception context.
   * @param cause The cause.
   * @since 0.1.0
   */
  public CertificateSigningException(String message, Throwable cause) {
    super(message, cause);
  }
}
