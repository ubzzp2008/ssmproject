package com.system.web.service.system;

import java.util.List;

import com.system.web.entity.system.TUserInfo;


/**
 * 项目名称：sshbase 类名称：IUserInfoService 类描述： 创建人：zzp 创建时间：2015-4-10 下午3:46:27 修改人：
 * 修改时间： 修改备注：
 *  
 * @version V0.1
 */

public interface UserInfoService {

	/**
	 * 获取所有用户数
	 * @return
	 */
	public Long getCountUserInfo();

	/**
	 * 根据用户名和密码检查用户是否存在
	 * @param userName
	 * @param password
	 * @return
	 */
	public TUserInfo checkUserExits(String userName, String password);

	/**
	 * 保存用户信息
	 * @TODO
	 * @param userInfo
	 * @author justion.zhou
	 * @date 2016-5-21 下午7:29:28
	 */
	public void saveUserInfo(TUserInfo userInfo);

	/**
	 * 根据ids批量删除用户信息
	 * @TODO
	 * @param ids
	 * @author justion.zhou
	 * @date 2016-5-22 下午4:43:08
	 */
	public void deleteUserInfoByIds(List<Integer> ids);

	/**
	 * 根据ID获取用户信息
	 * @TODO
	 * @param id
	 * @return
	 * @author justion.zhou
	 * @date 2016-5-22 下午6:41:39
	 */
	public TUserInfo getUserInfoById(Integer id);

	/**
	 * 修改用户信息
	 * @TODO
	 * @param userInfo
	 * @author justion.zhou
	 * @date 2016-5-22 下午7:21:22
	 */
	public void updateUserInfo(TUserInfo userInfo);

}
