package com.netpace.device.config;

import com.netpace.device.dao.GenericDao;
import com.netpace.device.dao.GenericReadOnlyDao;
import com.netpace.device.dao.jpa.GenericDaoJpaImpl;
import com.netpace.device.dao.jpa.GenericReadOnlyDaoJpaImpl;
import com.netpace.device.entities.SystemRole;
import com.netpace.device.entities.UserRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GenericDaoConfigration {
    @Bean(name="systemRoleDao")
    public GenericReadOnlyDao<SystemRole,Integer> systemRoleDao(){
        return new GenericReadOnlyDaoJpaImpl<SystemRole, Integer> (SystemRole.class);
    }
    
    @Bean(name="userRoleDao")
    public GenericDao<UserRole,Integer> userRoleDao(){
        return new GenericDaoJpaImpl<UserRole, Integer> (UserRole.class);
    }

}
