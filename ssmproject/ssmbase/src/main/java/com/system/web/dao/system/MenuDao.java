package com.system.web.dao.system;

import java.util.List;

import com.system.web.entity.system.TMenu;
import com.system.web.model.system.Menu;

/**
 * 项目名称：sshbase 类名称：MenuDao 类描述： 创建人：zzp 创建时间：2015-5-28 下午12:43:30 修改人： 修改时间：
 * 修改备注：
 * 
 * @version V0.1
 */

public interface MenuDao {
	/**
	 * 保存菜单信息
	 * 
	 * @TODO
	 * @param menu
	 * @author justion.zhou
	 * @date 2016-6-1 下午10:16:51
	 */
	public void saveMenuInfo(TMenu menu);

	/**
	 * 根据ID获取菜单信息
	 * 
	 * @TODO
	 * @param id
	 * @return
	 * @author justion.zhou
	 * @date 2016-6-1 下午10:23:21
	 */
	public TMenu getMenuInfoById(Integer id);

	/**
	 * 修改菜单信息
	 * 
	 * @TODO
	 * @param menu
	 * @author justion.zhou
	 * @date 2016-6-1 下午10:26:10
	 */
	public void updateMenuInfo(TMenu menu);

	/**
	 * 根据主键ID删除菜单
	 * 
	 * @TODO
	 * @param ids
	 * @author justion.zhou
	 * @date 2016-6-1 下午10:30:03
	 */
	public void deleteMenuInfoByIds(List<Integer> ids);

	/**
	 * 根据菜单层级获取菜单
	 * 
	 * @TODO
	 * @param levele
	 * @return
	 * @author justion.zhou
	 * @date 2016-6-1 下午11:21:13
	 */
	public List<TMenu> getMenuByLevele(Integer levele);

	/**
	 * 根据用户ID获取菜单信息
	 * 
	 * @param userId
	 * @return
	 */
	public List<Menu> findMenuByUserId(Integer userId);

}
