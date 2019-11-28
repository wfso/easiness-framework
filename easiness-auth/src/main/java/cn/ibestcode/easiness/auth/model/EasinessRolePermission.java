package cn.ibestcode.easiness.auth.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "easiness_role_permission",
  indexes = {
    @Index(columnList = "roleCode", name = "easiness_role_permission_roleCode"),
    @Index(columnList = "permissionCode", name = "easiness_role_permission_permissionCode")
  }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(EasinessRolePermission.class)
@ApiModel(description = "角色-权限")
public class EasinessRolePermission implements Serializable {
  @Id
  @ApiModelProperty("角色的ID")
  @Column(length = 50)
  private String roleCode;

  @Id
  @ApiModelProperty("权限的ID")
  @Column(length = 100)
  private String permissionCode;
}
