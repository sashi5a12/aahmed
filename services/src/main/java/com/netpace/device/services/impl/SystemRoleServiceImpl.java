package com.netpace.device.services.impl;

import com.netpace.device.dao.GenericReadOnlyDao;
import com.netpace.device.entities.SystemRole;
import com.netpace.device.services.SystemRoleService;
import com.netpace.notification.services.util.ETDConverter;
import com.netpace.device.vo.SystemRoleVO;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("systemRoleService")
public class SystemRoleServiceImpl implements SystemRoleService {

    private final static Log log = LogFactory.getLog(SystemRoleServiceImpl.class);
    @Autowired
    private GenericReadOnlyDao<SystemRole, Integer> systemRoleDao;

    @Override
    @Transactional
    public List<SystemRoleVO> getAllSystemRoles() {
        return getSystemRoleVOs(systemRoleDao.getAll());
    }

    @Override
    @Transactional
    public List<SystemRoleVO> getAllUnhiddenRoles() {
        return getSystemRoleVOs(
                systemRoleDao.findByNamedQuery("findUnhiddenSystemRoles", null));
    }

    private List<SystemRoleVO> getSystemRoleVOs(List<SystemRole> systemRoles) {
        List<SystemRoleVO> systemRoleVOs = new ArrayList<SystemRoleVO>();
        for (SystemRole systemRole : systemRoles) {
            systemRoleVOs.add(
                    ETDConverter.convert(systemRole, new SystemRoleVO()));
        }
        return systemRoleVOs;
    }
}
