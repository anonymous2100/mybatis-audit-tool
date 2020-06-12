package com.ctgu.entity;

import java.io.Serializable;
import java.util.Date;

import com.ctgu.audit.annotation.Audited;

/**
 * @ClassName: User
 * @Description:
 * @author lh2
 * @date 2020年6月12日 下午5:46:54
 */
@Audited // 加了注解才能审计
public class User implements Serializable
{
	private static final long serialVersionUID = -2087523555348653244L;

	private Integer id;

	private String userName;

	private String password;

	private String phoneNumber;

	private String email;

	private Integer gender;

	private Date birthDate;

	private String city;

	private String province;

	private String country;

	private String address;

	private Date createTime;

	private Date updateTime;

	private Integer isDelete;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public Integer getGender()
	{
		return gender;
	}

	public void setGender(Integer gender)
	{
		this.gender = gender;
	}

	public Date getBirthDate()
	{
		return birthDate;
	}

	public void setBirthDate(Date birthDate)
	{
		this.birthDate = birthDate;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	public String getProvince()
	{
		return province;
	}

	public void setProvince(String province)
	{
		this.province = province;
	}

	public String getCountry()
	{
		return country;
	}

	public void setCountry(String country)
	{
		this.country = country;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public Date getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

	public Date getUpdateTime()
	{
		return updateTime;
	}

	public void setUpdateTime(Date updateTime)
	{
		this.updateTime = updateTime;
	}

	public Integer getIsDelete()
	{
		return isDelete;
	}

	public void setIsDelete(Integer isDelete)
	{
		this.isDelete = isDelete;
	}

	@Override
	public String toString()
	{
		return "User [id=" + id + ", userName=" + userName + ", password=" + password + ", phoneNumber=" + phoneNumber
				+ ", email=" + email + ", gender=" + gender + ", birthDate=" + birthDate + ", city=" + city
				+ ", province=" + province + ", country=" + country + ", address=" + address + ", createTime="
				+ createTime + ", updateTime=" + updateTime + ", isDelete=" + isDelete + "]";
	}

}