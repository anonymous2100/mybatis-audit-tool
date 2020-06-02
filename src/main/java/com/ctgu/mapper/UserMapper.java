package com.ctgu.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.ctgu.entity.User;

@Mapper
public interface UserMapper
{
	int deleteByPrimaryKey(Integer id);

	int insert(User record);

	int insertSelective(User record);

	User selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(User record);

	int updateByPrimaryKey(User record);

	User getUserInfoById(Integer id);

	// User insertUser(Integer id, String userName);
}