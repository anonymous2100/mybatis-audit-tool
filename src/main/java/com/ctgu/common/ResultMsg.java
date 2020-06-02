package com.ctgu.common;

import java.io.Serializable;

public class ResultMsg implements Serializable
{
	private static final long serialVersionUID = 7434088173776840432L;

	// 状态码
	private Integer code;

	// 信息描述
	private String msg;

	// 数据结果对象
	private Object data;

	public ResultMsg()
	{
		super();
	}

	public ResultMsg(Integer code, String msg, Object data)
	{
		super();
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public Integer getCode()
	{
		return code;
	}

	public void setCode(Integer code)
	{
		this.code = code;
	}

	public String getMsg()
	{
		return msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}

	public Object getData()
	{
		return data;
	}

	public void setData(Object data)
	{
		this.data = data;
	}

	@Override
	public String toString()
	{
		return "CommonResponse [code=" + code + ", msg=" + msg + ", data=" + data + "]";
	}

	public static ResultMsg success()
	{
		ResultMsg resultMsg = new ResultMsg();
		resultMsg.setCode(ResultCode.SUCCESS.getCode());
		resultMsg.setMsg(ResultCode.SUCCESS.getDesc());
		return resultMsg;
	}

	public static ResultMsg success(Object data)
	{
		ResultMsg resultMsg = new ResultMsg();
		resultMsg.setCode(ResultCode.SUCCESS.getCode());
		resultMsg.setMsg(ResultCode.SUCCESS.getDesc());
		resultMsg.setData(data);
		return resultMsg;
	}
	
	public static ResultMsg success(Object data,ResultCode resultCode)
	{
		ResultMsg resultMsg = new ResultMsg();
		resultMsg.setCode(resultCode.getCode());
		resultMsg.setMsg(resultCode.getDesc());
		resultMsg.setData(data);
		return resultMsg;
	}

	public static ResultMsg fail()
	{
		ResultMsg resultMsg = new ResultMsg();
		resultMsg.setCode(ResultCode.FAIL.getCode());
		resultMsg.setMsg(ResultCode.FAIL.getDesc());
		return resultMsg;
	}

	public static ResultMsg fail(ResultCode resultCode)
	{
		ResultMsg resultMsg = new ResultMsg();
		resultMsg.setCode(resultCode.getCode());
		resultMsg.setMsg(resultCode.getDesc());
		return resultMsg;
	}

	public static ResultMsg fail(String msg)
	{
		ResultMsg resultMsg = new ResultMsg();
		resultMsg.setCode(ResultCode.FAIL.getCode());
		resultMsg.setMsg(msg);
		return resultMsg;
	}

	public static ResultMsg fail(Integer code, String msg)
	{
		ResultMsg resultMsg = new ResultMsg();
		resultMsg.setCode(code);
		resultMsg.setMsg(msg);
		return resultMsg;
	}
}
