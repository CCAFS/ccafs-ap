package org.cgiar.ccafs.ap.config;

import org.apache.commons.lang3.builder.ToStringStyle;

import org.apache.commons.lang3.builder.ToStringBuilder;
import com.google.inject.AbstractModule;


public class APModule extends AbstractModule {

  @Override
  protected void configure() {
    // We are configuring google guice using annotation. However you can do it here if you want.

    // In addition, we are using this place to configure other stuffs.
    ToStringBuilder.setDefaultStyle(ToStringStyle.MULTI_LINE_STYLE);
  }

}
