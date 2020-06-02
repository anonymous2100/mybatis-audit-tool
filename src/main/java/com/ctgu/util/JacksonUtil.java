package com.ctgu.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonUtil
{
	private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	private JacksonUtil()
	{

	}

	public static ObjectMapper getInstance()
	{
		return OBJECT_MAPPER;
	}

	/**
	 * javaBean、列表数组转换为json字符串
	 */
	public static String obj2json(Object obj) throws Exception
	{
		return OBJECT_MAPPER.writeValueAsString(obj);
	}

	/**
	 * javaBean、列表数组转换为json字符串,忽略空值
	 */
	public static String obj2jsonIgnoreNull(Object obj) throws Exception
	{
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		return mapper.writeValueAsString(obj);
	}

	public static <T> T readValue(String jsonStr, Class<T> clazz) throws IOException
	{
		return getInstance().readValue(jsonStr, clazz);
	}

	/**
	 * json 转JavaBean
	 */

	public static <T> T json2pojo(String jsonString, Class<T> clazz) throws Exception
	{
		OBJECT_MAPPER.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		return OBJECT_MAPPER.readValue(jsonString, clazz);
	}

	/**
	 * json字符串转换为map
	 */
	public static <T> Map<String, Object> json2map(String jsonString) throws Exception
	{
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		return mapper.readValue(jsonString, Map.class);
	}

	/**
	 * json字符串转换为map
	 */
	public static <T> Map<String, T> json2map(String jsonString, Class<T> clazz) throws Exception
	{
		Map<String, Map<String, Object>> map = OBJECT_MAPPER.readValue(jsonString, new TypeReference<Map<String, T>>()
		{
		});
		Map<String, T> result = new HashMap<String, T>();
		for (Map.Entry<String, Map<String, Object>> entry : map.entrySet())
		{
			result.put(entry.getKey(), map2pojo(entry.getValue(), clazz));
		}
		return result;
	}

	/**
	 * 深度转换json成map
	 *
	 * @param json
	 * @return
	 */
	public static Map<String, Object> json2mapDeeply(String json) throws Exception
	{
		return json2MapRecursion(json, OBJECT_MAPPER);
	}

	/**
	 * 把json解析成list，如果list内部的元素存在jsonString，继续解析
	 *
	 * @param json
	 * @param mapper
	 *            解析工具
	 * @return
	 * @throws Exception
	 */
	private static List<Object> json2ListRecursion(String json, ObjectMapper mapper) throws Exception
	{
		if (json == null)
		{
			return null;
		}

		List<Object> list = mapper.readValue(json, List.class);

		for (Object obj : list)
		{
			if (obj != null && obj instanceof String)
			{
				String str = (String) obj;
				if (str.startsWith("["))
				{
					obj = json2ListRecursion(str, mapper);
				}
				else if (obj.toString().startsWith("{"))
				{
					obj = json2MapRecursion(str, mapper);
				}
			}
		}

		return list;
	}

	/**
	 * 把json解析成map，如果map内部的value存在jsonString，继续解析
	 *
	 * @param json
	 * @param mapper
	 * @return
	 * @throws Exception
	 */
	private static Map<String, Object> json2MapRecursion(String json, ObjectMapper mapper) throws Exception
	{
		if (json == null)
		{
			return null;
		}

		Map<String, Object> map = mapper.readValue(json, Map.class);

		for (Map.Entry<String, Object> entry : map.entrySet())
		{
			Object obj = entry.getValue();
			if (obj != null && obj instanceof String)
			{
				String str = ((String) obj);

				if (str.startsWith("["))
				{
					List<?> list = json2ListRecursion(str, mapper);
					map.put(entry.getKey(), list);
				}
				else if (str.startsWith("{"))
				{
					Map<String, Object> mapRecursion = json2MapRecursion(str, mapper);
					map.put(entry.getKey(), mapRecursion);
				}
			}
		}

		return map;
	}

	/**
	 * 与javaBean json数组字符串转换为列表
	 */
	public static <T> List<T> json2list(String jsonArrayStr, Class<T> clazz) throws Exception
	{
		JavaType javaType = getCollectionType(ArrayList.class, clazz);
		OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		List<T> lst = (List<T>) OBJECT_MAPPER.readValue(jsonArrayStr, javaType);
		return lst;
	}

	/**
	 * 获取泛型的Collection Type
	 *
	 * @param collectionClass
	 *            泛型的Collection
	 * @param elementClasses
	 *            元素类
	 * @return JavaType Java类型
	 * @since 1.0
	 */
	public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses)
	{
		return OBJECT_MAPPER.getTypeFactory().constructParametricType(collectionClass, elementClasses);
	}

	/**
	 * map 转JavaBean
	 */
	public static <T> T map2pojo(Map map, Class<T> clazz)
	{
		return OBJECT_MAPPER.convertValue(map, clazz);
	}

	/**
	 * map 转json
	 *
	 * @param map
	 * @return
	 */
	public static String mapToJson(Map map)
	{
		try
		{
			return OBJECT_MAPPER.writeValueAsString(map);
		}
		catch (Exception e)
		{
			return "";
		}
	}

	/**
	 * map 转JavaBean
	 */
	public static <T> T obj2pojo(Object obj, Class<T> clazz)
	{
		OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return OBJECT_MAPPER.convertValue(obj, clazz);
	}
}