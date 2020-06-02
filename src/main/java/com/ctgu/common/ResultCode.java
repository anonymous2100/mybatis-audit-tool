package com.ctgu.common;

public enum ResultCode
{
	SUCCESS(0, "成功"), //
	FAIL(-1, "失败"), //

	NO_AUTHORIZED(10001, "登录失效"), //
	NULL_PARAMS(10002, "输入参数不可为空"), //
	NO_DATA(10003, "未查询到数据"), //
	FILE_SIZE_EXCEED_LIMIT(10004, "上传的文件大小为0或者超过限制"), //
	FILE_CONTENT_EMPTY(10005, "文件内容为空"), //
	DATA_ALREADY_EXISTS(10006, "数据已经存在"), //
	PARAMS_INVALID(10007, "请求参数错误"); //

	private Integer code;
	private String desc;

	private ResultCode(Integer code, String desc)
	{
		this.code = code;
		this.desc = desc;
	}

	public Integer getCode()
	{
		return code;
	}

	public void setCode(Integer code)
	{
		this.code = code;
	}

	public String getDesc()
	{
		return desc;
	}

	public void setDesc(String desc)
	{
		this.desc = desc;
	}
}
