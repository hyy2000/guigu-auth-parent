package com.atguigu.model.system;

import com.atguigu.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


@Data
//@ApiModel(description = "角色")
@TableName("sys_role")
public class SysRole extends BaseEntity { //SysRole ---->sys_role Sysrole--->sysrole
	private static final long serialVersionUID = 1L;

	//@NotBlank(message = "角色名称不能为空")
//	@ApiModelProperty(value = "角色名称")
//	@TableField("role_name")
	private String roleName;

//	@ApiModelProperty(value = "角色编码")
//	@TableField("role_code")
	private String roleCode;

//	@ApiModelProperty(value = "描述")
//	@TableField("description")
	private String description;

	@Override
	public String toString() {
		return "SysRole{" +
				"roleName='" + roleName + '\'' +
				", roleCode='" + roleCode + '\'' +
				", description='" + description + '\'' +
				"} " + super.toString();
	}
}

