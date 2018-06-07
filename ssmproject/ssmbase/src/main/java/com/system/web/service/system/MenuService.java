package com.system.web.service.system;

import java.util.List;

import com.system.web.entity.system.TMenu;
import com.system.web.model.system.Menu;

/**
 * 项目名称：sshbase 类名称：MenuService 类描述： 创建人：zzp 创建时间：2015-5-27 下午10:26:58 修改人：
 * 修改时间： 修改备注：
 * 
 * @version V0.1
 */

public interface MenuService {
	/**
	 * 保存菜单信息
	 * 
	 * @TODO
	 * @param menu
	 * @author justion.zhou
	 * @date 2016-6-1 下午10:12:54
	 */
	public void saveMenuInfo(TMenu menu);

	/**
	 * 根据ID获取菜单信息
	 * 
	 * @TODO
	 * @param id
	 * @return
	 * @author justion.zhou
	 * @date 2016-6-1 下午10:13:15
	 */
	public TMenu getMenuInfoById(Integer id);

	/**
	 * 修改菜单信息
	 * 
	 * @TODO
	 * @param menu
	 * @author justion.zhou
	 * @date 2016-6-1 下午10:13:31
	 */
	public void updateMenuInfo(TMenu menu);

	/**
	 * 根据Id删除菜单信息
	 * 
	 * @TODO
	 * @param ids
	 * @author justion.zhou
	 * @date 2016-6-1 下午10:13:46
	 */
	public void deleteMenuInfoByIds(List<Integer> ids);

	/**
	 * 根据菜单层级获取菜单
	 * 
	 * @TODO
	 * @param levele
	 * @return
	 * @author justion.zhou
	 * @date 2016-6-1 下午11:20:38
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
