package com.jchen.shiro_demon.service;

import com.jchen.shiro_demon.AuthRealm;
import com.jchen.shiro_demon.CredentialMacher;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

@Configuration
public class ShiroConfig  {
    @Bean("credentialMacher")
    public CredentialMacher credentialMacher(){
        return new CredentialMacher();
    }
    @Bean("authRealm")
    public AuthRealm authRealm(@Qualifier("credentialMacher")CredentialMacher credentialMacher){
        AuthRealm authRealm=new AuthRealm();
        authRealm.setCredentialsMatcher(credentialMacher);
        return authRealm;
    }

    @Bean("securityManager")
    public SecurityManager securityManager(@Qualifier("authRealm") AuthRealm authRealm){
        DefaultWebSecurityManager manager=new DefaultWebSecurityManager();
        manager.setRealm(authRealm);
        return manager;
    }

    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager manager){
        ShiroFilterFactoryBean bean=new ShiroFilterFactoryBean();
        bean.setSecurityManager(manager);
        bean.setLoginUrl("/login");
        bean.setSuccessUrl("/index");
        bean.setUnauthorizedUrl("/unauthorized");
        LinkedHashMap<String,String> filterChain=new LinkedHashMap<>();
        filterChain.put("/index","authc");
        filterChain.put("/login","anon");
        bean.setFilterChainDefinitionMap(filterChain);
        return bean;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor advisor=new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator creat=new DefaultAdvisorAutoProxyCreator();
        creat.setProxyTargetClass(true);
        return creat;
    }
}


