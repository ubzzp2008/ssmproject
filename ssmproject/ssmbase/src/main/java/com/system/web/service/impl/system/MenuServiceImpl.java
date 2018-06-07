package com.system.web.service.impl.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.web.dao.system.MenuDao;
import com.system.web.entity.system.TMenu;
import com.system.web.model.system.Menu;
import com.system.web.service.system.MenuService;

/**
 * 项目名称：sshbase 类名称：MenuServiceImpl 类描述： 创建人：zzp 创建时间：2015-5-27 下午10:26:42 修改人：
 * 修改时间： 修改备注：
 * 
 * @version V0.1
 */
@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuDao menuDao;

	@Override
	public void saveMenuInfo(TMenu menu) {
		menuDao.saveMenuInfo(menu);
	}

	@Override
	public TMenu getMenuInfoById(Integer id) {
		return menuDao.getMenuInfoById(id);
	}

	@Override
	public void updateMenuInfo(TMenu menu) {
		menuDao.updateMenuInfo(menu);
	}

	@Override
	public void deleteMenuInfoByIds(List<Integer> ids) {
		// 删除菜单
		menuDao.deleteMenuInfoByIds(ids);
	}

	@Override
	public List<TMenu> getMenuByLevele(Integer levele) {
		return menuDao.getMenuByLevele(levele);
	}

	@Override
	public List<Menu> findMenuByUserId(Integer userId) {
		return menuDao.findMenuByUserId(userId);
	}

}