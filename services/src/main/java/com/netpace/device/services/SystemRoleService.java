package com.netpace.device.services;

import com.netpace.device.vo.SystemRoleVO;
import java.util.List;

public interface SystemRoleService {

    public List<SystemRoleVO> getAllSystemRoles();
    public List<SystemRoleVO> getAllUnhiddenRoles();
}