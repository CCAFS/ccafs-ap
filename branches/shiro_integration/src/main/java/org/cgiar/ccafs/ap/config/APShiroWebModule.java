package org.cgiar.ccafs.ap.config;

import org.cgiar.ccafs.ap.security.realm.DatabaseRealm;

import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.ServletContext;

import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.apache.shiro.config.Ini;
import org.apache.shiro.guice.web.ShiroWebModule;

public class APShiroWebModule extends ShiroWebModule {

  private final ServletContext servletContext;

  public APShiroWebModule(ServletContext servletContext) {
    super(servletContext);

    this.servletContext = servletContext;
  }

  @Override
  protected void configureShiroWeb() {
    /*
     * try {
     * bindRealm().toConstructor(IniRealm.class.getConstructor(Ini.class));
     * } catch (NoSuchMethodException | SecurityException e) {
     * // TODO Auto-generated catch block
     * e.printStackTrace();
     * }
     */
    bindRealm().to(DatabaseRealm.class);

    this.addFilterChain("/login.do", AUTHC);
    this.addFilterChain("/logout", LOGOUT);
    this.addFilterChain("/account/**", AUTHC);
    this.addFilterChain("/remoting/**", AUTHC, config(ROLES, "b2bClient"), config(PERMS, "remote:invoke:lan,wan"));

  }

  @Provides
  @Singleton
  Ini loadShiroIni() {
    URL iniUrl = null;
    try {
      iniUrl = servletContext.getResource("/WEB-INF/shiro.ini");
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
    return Ini.fromResourcePath("url:" + iniUrl.toExternalForm());
  }

}
