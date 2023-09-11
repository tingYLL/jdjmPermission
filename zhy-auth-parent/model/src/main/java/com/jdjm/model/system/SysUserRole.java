package com.jdjm.model.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jdjm.model.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ApiModel(description = "用户角色")
@TableName("sys_user_role")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SysUserRole extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "角色id")
	@TableField("role_id")
	private String roleId;

	@ApiModelProperty(value = "用户id")
	@TableField("user_id")
	private String userId;
}

