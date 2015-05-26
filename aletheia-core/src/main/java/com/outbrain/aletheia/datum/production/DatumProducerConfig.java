package com.outbrain.aletheia.datum.production;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Configuration details for {@link DatumProducer}s.
 */
public class DatumProducerConfig {

  private final int incarnation;
  private final String hostname;

  /**
   * @param incarnation incarnation provides means to further control data filtering, e.g., discarding all data
   *                    below a certain incarnation.
   * @param hostname    the hostname this {@link DatumProducer} instance is running from.
   */
  public DatumProducerConfig(final int incarnation, final String hostname) {

    this.incarnation = incarnation;
    this.hostname = hostname;
  }

  public int getIncarnation() {
    return incarnation;
  }

  public String getHostname() {
    return hostname;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
  }
}
