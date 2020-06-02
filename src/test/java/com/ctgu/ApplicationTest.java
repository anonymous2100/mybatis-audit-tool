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

	@Test
	public void test1()
	{
		for (int i = 0; i < 10; i++)
		{
			User user = new User();
			user.setUserName("zs" + i);
			user.setPassword("123456");
			user.setEmail("2120991055@qq.com");
			user.setCreateTime(new Date());
			user.setUpdateTime(new Date());
			user.setIsDelete(0);
			userService.addUser(user);
		}
	}

	@Test
	public void test2()
	{
		User user = userService.getUserInfoById(514);

		user.setUserName("lisi");
		user.setPassword("111222");
		user.setEmail("2112931156@qq.com");
		user.setCreateTime(new Date());
		user.setUpdateTime(new Date());

		userService.updateUser(user);
	}

	@Test
	public void test3()
	{
		// userService.deleteUserById(520);

		// 删除的时候必须传对象类型（添加注解的那个类，这里是User）的参数，不能是int,Integer等类型
		User user = userService.getUserInfoById(523);
		userService.deleteUser(user);
	}

}
