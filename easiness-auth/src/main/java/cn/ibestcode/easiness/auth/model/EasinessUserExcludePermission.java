package cn.ibestcode.easiness.auth.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "easiness_user_permission_exclude",
  indexes = {
    @Index(columnList = "userId", name = "easiness_user_permission_exclude_userId"),
    @Index(columnList = "permissionCode", name = "easiness_user_permission_exclude_permissionCode")
  }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(EasinessUserExcludePermission.class)
@ApiModel("用户-排除-权限")
public class EasinessUserExcludePermission implements Serializable {
  @Id
  @ApiModelProperty("用户的ID")
  private long userId;

  @Id
  @ApiModelProperty("权限的Code")
  @Column(length = 100)
  private String permissionCode;
}
