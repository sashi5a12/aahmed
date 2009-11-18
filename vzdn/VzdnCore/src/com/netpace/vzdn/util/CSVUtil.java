package com.netpace.vzdn.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.netpace.vzdn.exceptions.InvalidCSVFormatException;
import com.netpace.vzdn.exceptions.NoRolesFoundException;
import com.netpace.vzdn.model.VzdnSysRoles;

public class CSVUtil {
	public static Set<VzdnSysRoles> CSVToRolesSet(String csvOriginal) throws NoRolesFoundException, InvalidCSVFormatException{
		Set<VzdnSysRoles> roles = new HashSet<VzdnSysRoles>();
		VzdnSysRoles role = new VzdnSysRoles();
		String roleStr = "";
		int roleId;
		String csv = csvOriginal.trim();
		
		if(null == csv || "".equals(csv))
			throw new NoRolesFoundException("No Roles Found :'" + csv + "'");		
		if(csv.indexOf(',')<0 && csv.length()>0){
			role = new VzdnSysRoles();
			roleId = Integer.parseInt(csv);
			role.setRoleId(roleId);
			roles.add(role);
		}
		else
		{
			int beginIndex=0;
			int endIndex;
			try
			{
				do
				{
					endIndex = csv.indexOf(',');
					roleStr = csv.substring(beginIndex, endIndex);
					csv = csv.substring(endIndex+1);
					role = new VzdnSysRoles();
					if(null == roleStr || "".equals(roleStr))
						throw new InvalidCSVFormatException("Problem with CSV format:'" + csvOriginal + "'");
					roleId = Integer.parseInt(roleStr);
					role.setRoleId(roleId);
					roles.add(role);
				}
				while(csv.indexOf(',') >= 0);
				role = new VzdnSysRoles();
				roleId = Integer.parseInt(csv);
				role.setRoleId(roleId);
				roles.add(role);
			}
			catch(NumberFormatException e){
				throw new InvalidCSVFormatException("Problem with CSV format:'" + csvOriginal + "'");				
			}
	
		}		
		return roles;
	}
	
	
	public static List<Integer> CSVToRolesList(String csvOriginal) throws NoRolesFoundException, InvalidCSVFormatException{
		ArrayList<Integer> rolesIds = new ArrayList<Integer>();
		VzdnSysRoles role = new VzdnSysRoles();
		String roleStr = "";
		int roleId;
		String csv = csvOriginal.trim();
		
		if(null == csv || "".equals(csv))
			throw new NoRolesFoundException("No Roles Found :'" + csv + "'");		
		if(csv.indexOf(',')<0 && csv.length()>0){
			role = new VzdnSysRoles();
			roleId = Integer.parseInt(csv);
			role.setRoleId(roleId);
			rolesIds.add(new Integer(roleId));
		}
		else
		{
			int beginIndex=0;
			int endIndex;
			try
			{
				do
				{
					endIndex = csv.indexOf(',');
					roleStr = csv.substring(beginIndex, endIndex);
					csv = csv.substring(endIndex+1);
					role = new VzdnSysRoles();
					if(null == roleStr || "".equals(roleStr))
						throw new InvalidCSVFormatException("Problem with CSV format:'" + csvOriginal + "'");
					roleId = Integer.parseInt(roleStr);
					role.setRoleId(roleId);
					rolesIds.add(new Integer(roleId));
				}
				while(csv.indexOf(',') >= 0);
				role = new VzdnSysRoles();
				roleId = Integer.parseInt(csv);
				role.setRoleId(roleId);
				rolesIds.add(new Integer(roleId));
			}
			catch(NumberFormatException e){
				throw new InvalidCSVFormatException("Problem with CSV format:'" + csvOriginal + "'");				
			}
	
		}		
		return rolesIds;
	}

}
