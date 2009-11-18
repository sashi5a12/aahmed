package com.netpace.aims.util;

import org.apache.commons.validator.ValidatorAction;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.commons.validator.Field;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.ValidatorUtil;
import org.apache.struts.validator.Resources;
import java.io.Serializable;

 
/**
 * This class has static validation methods that will be used for validation. 
 * 
 * @author Adnan Makda
 */
public class AimsValidation
{
	
	public static boolean validateTwoFields(Object bean, ValidatorAction va,
                                        Field field, ActionErrors errors,
                                        HttpServletRequest request) 
	{
		String value =	ValidatorUtil.getValueAsString(bean, field.getProperty());
    String sProperty2 = field.getVarValue("secondProperty");
    String value2 = ValidatorUtil.getValueAsString(bean, sProperty2);

    if (!GenericValidator.isBlankOrNull(value)) 
    {
			try 
			{
      	if (!value.equals(value2)) 
      	{
      		errors.add(field.getKey(), Resources.getActionError(request, va, field));
					return false;
				}
      }
			catch (Exception e) 
			{
				errors.add(field.getKey(), Resources.getActionError(request, va, field));
				return false;
			}
		}
		return true;
	}
	
	public static boolean validateCompareField(Object bean, ValidatorAction va,
                                        Field field, ActionErrors errors,
                                        HttpServletRequest request) 
	{
		String value =	ValidatorUtil.getValueAsString(bean, field.getProperty());
    String value2 = field.getVarValue("valueToCompare");
    
    if (!GenericValidator.isBlankOrNull(value)) 
    {
			try 
			{
      	if (value.equals(value2)) 
      	{
      		errors.add(field.getKey(), Resources.getActionError(request, va, field));
					return false;
				}
      }
			catch (Exception e) 
			{
				errors.add(field.getKey(), Resources.getActionError(request, va, field));
				return false;
			}
		}
		return true;
	}
	

}


