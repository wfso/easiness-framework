/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.form.domain;

import java.util.List;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/10/23 10:47
 */
public interface Form<T extends Item<L>, L> {
  String getUuid();

  List<T> getItems();

  String getName();

  String getDescription();

  default Item<L> addItem(T item) {
    getItems().add(item);
    item.setPosition(getItems().size());
    return item;
  }
}
