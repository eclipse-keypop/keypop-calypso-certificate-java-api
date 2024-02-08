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

import org.eclipse.keypop.calypso.certificate.ca.CaCertificateManager;
import org.eclipse.keypop.calypso.certificate.ca.CaCertificateSettings;
import org.eclipse.keypop.calypso.certificate.card.CardCertificateManager;
import org.eclipse.keypop.calypso.certificate.card.CardCertificateSettings;

/**
 * Factory of {@link CaCertificateSettings}, {@link CaCertificateManager}, {@link
 * CardCertificateSettings} and {@link CardCertificateManager}.
 *
 * @since 0.1.0
 */
public interface CalypsoCertificateApiFactory {

  /**
   * Returns a new instance of the specified CA certificate settings class.
   *
   * @param <T> The type of the lowest level child object.
   * @param classOfT The `Class` object of the desired CA certificate settings type. This type must
   *     extend {@link CaCertificateSettings}.
   * @return A non-null reference.
   * @since 0.1.0
   */
  <T extends CaCertificateSettings> T createCaCertificateSettings(Class<T> classOfT);

  /**
   * Returns a new instance of the specified card certificate settings class.
   *
   * @param <T> The type of the lowest level child object.
   * @param classOfT The `Class` object of the desired card certificate settings type. This type
   *     must extend {@link CardCertificateSettings}.
   * @return A non-null reference.
   * @since 0.1.0
   */
  <T extends CardCertificateSettings> T createCardCertificateSettings(Class<T> classOfT);

  /**
   * Returns a new instance of {@link CaCertificateManager}.
   *
   * @param settings The CA certificate settings to use.
   * @return A non-null reference.
   * @since 0.1.0
   */
  CaCertificateManager createCaCertificateManager(CaCertificateSettings settings);

  /**
   * Returns a new instance of {@link CardCertificateManager}.
   *
   * @param settings The card certificate settings to use.
   * @return A non-null reference.
   * @since 0.1.0
   */
  CardCertificateManager createCardCertificateManager(CardCertificateSettings settings);
}
