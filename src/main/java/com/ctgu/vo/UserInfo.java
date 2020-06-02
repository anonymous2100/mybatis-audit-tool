package com.ctgu.vo;

import java.io.Serializable;

public class UserInfo implements Serializable
{
	private static final long serialVersionUID = 5327454436429976725L;

	private Integer id;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	@Override
	public String toString()
	{
		return "UserInfo [id=" + id + "]";
	}

}
