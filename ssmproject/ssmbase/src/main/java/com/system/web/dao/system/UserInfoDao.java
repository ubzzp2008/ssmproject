package com.system.web.dao.system;

import java.util.List;
import java.util.Map;

import com.system.web.entity.system.TUserInfo;


/** 
 * 项目名称：sshbase 
 * 类名称：IUserInfoDao 
 * 类描述： 
 * 创建人：zzp 
 * 创建时间：2015-4-10 下午3:47:46 修改人：
 * 修改时间： 修改备注：
 * @version V0.1
 */

public interface UserInfoDao {

	/**
	 * 获取所有用户数
	 * @return
	 */
	public Long getCountUserInfo();

	/**
	 * 根据用户名密码检查用户是否存在
	 * @param map
	 * @return
	 */
	public TUserInfo checkUserExits(Map<String, Object> map);

	/**
	 * 保存用户
	 * @param user
	 */
	public void saveUserInfo(TUserInfo user);

	/**
	 * 根据ids批量删除用户信息
	 * @TODO
	 * @author justion.zhou
	 * @date 2016-5-22 下午4:44:52
	 */
	public void deleteUserInfoByIds(List<Integer> ids);

	/**
	 * 根据ID获取用户信息
	 * @TODO
	 * @param id
	 * @return
	 * @author justion.zhou
	 * @date 2016-5-22 下午6:42:18
	 */
	public TUserInfo getUserInfoById(Integer id);

	/**
	 * 修改用户信息
	 * @TODO
	 * @param userInfo
	 * @author justion.zhou
	 * @date 2016-5-22 下午7:21:51
	 */
	public void updateUserInfo(TUserInfo userInfo);

}
