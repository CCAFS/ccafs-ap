package org.cgiar.ccafs.ap.config;

import org.cgiar.ccafs.ap.security.realm.DatabaseRealm;

import javax.servlet.ServletContext;

import com.google.inject.name.Names;
import org.apache.shiro.guice.web.ShiroWebModule;

public class APShiroWebModule extends ShiroWebModule {

  public APShiroWebModule(ServletContext servletContext) {
    super(servletContext);
  }

  @Override
  protected void configureShiroWeb() {

    // [main]
    bindConstant().annotatedWith(Names.named("shiro.loginUrl")).to("/");

    // [realms]
    bindRealm().to(DatabaseRealm.class).asEagerSingleton();


    // [urls]
    bindConstant().annotatedWith(Names.named("/")).to("authc");
    bindConstant().annotatedWith(Names.named("/logout.do")).to("logout");
  }
}
