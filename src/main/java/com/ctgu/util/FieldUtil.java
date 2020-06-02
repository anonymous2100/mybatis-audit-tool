package com.ctgu.util;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.BeanWrapperImpl;

public final class FieldUtil
{
	public static <T> T getValue(T obj, T defaultValue)
	{
		return (obj == null) ? defaultValue : obj;
	}

	public static String[] getNullFieldNames(Object obj)
	{
		BeanWrapperImpl bean = new BeanWrapperImpl(obj);
		PropertyDescriptor[] propertyDescriptors = bean.getPropertyDescriptors();
		Set<String> nullFields = new HashSet<>();
		for (PropertyDescriptor pd : propertyDescriptors)
		{
			Object value = bean.getPropertyValue(pd.getName());
			if (value == null)
				nullFields.add(pd.getName());
		}
		return nullFields.<String>toArray(new String[nullFields.size()]);
	}
}
