package org.cgiar.ccafs.security.util.tags;

/**
 * <p>
 * Equivalent to {@link org.apache.shiro.web.tags.HasRoleTag}
 * </p>
 */
public class HasRoleTag extends RoleTag {

  @Override
  protected boolean showTagBody(String roleName) {
    return getSubject() != null && getSubject().hasRole(roleName);
  }
}
