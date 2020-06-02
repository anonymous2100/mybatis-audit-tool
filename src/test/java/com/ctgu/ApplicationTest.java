package com.ctgu;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ctgu.entity.User;
import com.ctgu.service.IUserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest
{
	@Resource(name = "userService")
	private IUserService userService;

//	@Resource(name = "departmentService")
//	private IDepartmentService departmentService;

	@Test
	public void test1()
	{
		User user = new User();
		user.setUserName("zs");
		user.setPassword("123456");
		user.setEmail("2110931055@qq.com");
		user.setCreateTime(new Date());
		user.setUpdateTime(new Date());
		userService.addUser(user);

	}

}
