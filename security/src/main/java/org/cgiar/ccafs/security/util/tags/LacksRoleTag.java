package org.cgiar.ccafs.security.util.tags;

/**
 * <p>
 * Equivalent to {@link org.apache.shiro.web.tags.LacksRoleTag}
 * </p>
 */
public class LacksRoleTag extends RoleTag {

  @Override
  protected boolean showTagBody(String roleName) {
    boolean hasRole = getSubject() != null && getSubject().hasRole(roleName);
    return !hasRole;
  }
}
