package com.jchen.shiro_demon;

import com.jchen.shiro_demon.model.Permission;
import com.jchen.shiro_demon.model.Role;
import com.jchen.shiro_demon.model.User;
import com.jchen.shiro_demon.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AuthRealm extends AuthorizingRealm {


    @Autowired
    private UserService userService;


//    作为授权来用
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        User user=(User)principalCollection.fromRealm(this.getClass().getName()).iterator().next();
        List<String> permissionList=new ArrayList<>();
        Set<Role> roleSet=user.getRoles();
        if(!CollectionUtils.isEmpty(roleSet)){
            for(Role role:roleSet){
                Set<Permission> permissionSet=role.getPermissions();
                if(!CollectionUtils.isEmpty(permissionSet)){
                    for (Permission permission:permissionSet){
                        permissionList.add(permission.getName());
                    }
                }
            }
        }
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        info.addStringPermissions(permissionList);
        return info;
    }

//    作为认证登录
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken=(UsernamePasswordToken)authenticationToken;

        String username=usernamePasswordToken.getUsername();
        User user=userService.findByUsername(username);

        return new SimpleAuthenticationInfo(user,user.getPassword(),this.getClass().getName());
    }
}
