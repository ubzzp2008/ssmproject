package com.system.web.entity.system;

import java.util.List;

import com.system.web.common.component.module.query.QueryResult;

/**     
* 项目名称：sshbase   
* 类名称：TMenu   
* 类描述： 功能菜单实体类  
* 创建人：zzp
* 创建时间：2015-5-27 下午10:05:16   
* 修改人：
* 修改时间： 
* 修改备注：   
* @version V0.1 
*/
public class TMenu extends QueryResult {
	private static final long serialVersionUID = 1485983435397409703L;
	private Integer id;
	private String name;// 菜单名称
	private Integer seq;// 显示顺序
	private Long status;// 状态：0 启用; 1：禁用
	private String url;// 菜单地址
	private Integer levele;//层级
	private Integer pid;// 父级id
	private String pname;// 父级菜单
	private String description;// 描述
	private List<TMenu> childMenuList;// 下级菜单集合
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public List<TMenu> getChildMenuList() {
		return childMenuList;
	}

	public void setChildMenuList(List<TMenu> childMenuList) {
		this.childMenuList = childMenuList;
	}

	public Integer getLevele() {
		return levele;
	}

	public void setLevele(Integer levele) {
		this.levele = levele;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}


}
