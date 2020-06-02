package com.ctgu.audit;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReflectionUtil
{
	private static final Logger log = LoggerFactory.getLogger(ReflectionUtil.class);

	public static boolean isPrimitiveType(Class<?> clazz)
	{
		return clazz.isPrimitive();
	}

	public static boolean isWrapClass(Class<?> clazz)
	{
		try
		{
			return ((Class<?>) clazz.getField("TYPE")
					.get((Object) null)).isPrimitive();
		}
		catch (Exception e)
		{
			return false;
		}
	}

	public static boolean isJavaClass(Class<?> clazz)
	{
		return (clazz != null && clazz.getClassLoader() == null);
	}

	public static List<String> getAllProperties(Class<?> clazz)
	{
		List<String> fieldList = new ArrayList<>();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields)
			fieldList.add(field.getName());
		return fieldList;
	}

	public static Map<String, Object> getAllPropertiesMap(Object obj) throws Exception
	{
		Class<?> clazz = obj.getClass();
		Map<String, Object> fieldMap = new LinkedHashMap<>();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields)
		{
			field.setAccessible(true);
			fieldMap.put(field.getName(), field.get(obj));
		}
		fieldMap.remove("serialVersionUID");
		return fieldMap;
	}

	public static Object getFieldValueByFieldName(Object result, String string)
	{
		try
		{
			Class<?> clazz = result.getClass();
			Field[] fields = clazz.getDeclaredFields();
			for (int i = 0; i < fields.length; i++)
			{
				Field field = fields[i];
				field.setAccessible(true);
				if (string.equals(field.getName()))
					return field.get(result);
			}
			return null;
		}
		catch (Exception e)
		{
			log.error(e.getMessage());
			return null;
		}
	}

	public static Class<?> getFieldTypeByFieldName(Class<?> clazz, String propertyName)
	{
		try
		{
			Field[] fields = clazz.getDeclaredFields();
			for (int i = 0; i < fields.length; i++)
			{
				Field field = fields[i];
				field.setAccessible(true);
				if (propertyName.equals(field.getName()))
					return field.getType();
			}
			return null;
		}
		catch (Exception e)
		{
			return null;
		}
	}
}
