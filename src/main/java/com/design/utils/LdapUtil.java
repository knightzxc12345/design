package com.design.utils;

import com.design.base.common.Common;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import java.util.Hashtable;

@Component
public class LdapUtil{

    @Value("${ldap.url}")
    private String url;

    @Value("${ldap.domain}")
    private String domain;

    @Value("${ldap.base}")
    private String base;

    private static String ldap_url;

    private static String ldap_domain;

    private static String ldap_base;

    @PostConstruct
    public void init() {
        ldap_url = url;
        ldap_domain = domain;
        ldap_base = base;
    }

    public static boolean check(String account, String password){
        try {
            String filter = String.format("%s%s%s", "(&(objectClass=user)(sAMAccountName=" , account, "))");
            SearchControls searchControls = new SearchControls();
            searchControls.setReturningAttributes(new String[]{"displayName", "description", "mail", "department"});
            searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            Hashtable<String, String> environment = new Hashtable<>();
            environment.put(Context.INITIAL_CONTEXT_FACTORY, Common.LDAP_FACTORY);
            environment.put(Context.PROVIDER_URL, ldap_url);
            environment.put(Context.SECURITY_AUTHENTICATION, "simple");
            environment.put(Context.SECURITY_PRINCIPAL, String.format("%s@%s", account, ldap_domain));
            environment.put(Context.SECURITY_CREDENTIALS, password);
            environment.put(Common.LDAP_TIMEOUT_TITLE, Common.LDAP_TIMEOUT_TIME);
            LdapContext ldapContext = new InitialLdapContext(environment, null);
            NamingEnumeration answer = ldapContext.search(ldap_base, filter, searchControls);
            while (answer.hasMoreElements()) {
                SearchResult searchResult = (SearchResult) answer.next();
                Attributes attributes = searchResult.getAttributes();
                if(null != attributes){
                    return true;
                }
            }
            return false;
        } catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

}
