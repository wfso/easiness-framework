package cn.ibestcode.easiness.auth.domain;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EasinessLoginBeforeResult implements Serializable {
  private String salt;

  private String token;
}
