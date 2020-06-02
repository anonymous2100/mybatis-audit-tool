package com.ctgu.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ctgu.common.ResultCode;
import com.ctgu.common.ResultMsg;
import com.ctgu.entity.User;
import com.ctgu.service.IUserService;
import com.ctgu.vo.UserInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/user")
@Api(value = "/user", tags = "用户相关操作")
public class UserController
{
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "userService")
	private IUserService userService;

	// 获取单个用户的信息
	@RequestMapping(value = "/getUserInfoById.do", method = { RequestMethod.POST })
	@ResponseBody
	@ApiOperation(value = "获取单个用户的信息  ", notes = " ")
	public ResultMsg getUserInfoById(@RequestBody UserInfo vo)
	{
		if (vo == null)
		{
			return ResultMsg.fail(ResultCode.NULL_PARAMS);
		}
		log.info("getUserInfoById入参：" + vo.toString());

		Integer id = vo.getId();
		User user = userService.getUserInfoById(id);
		try
		{
			return ResultMsg.success(user);
		}
		catch (Exception e)
		{
			return ResultMsg.fail(e.getMessage());
		}
	}

}
