package cn.ibestcode.easiness.core.query.filter;

import java.util.List;

public interface IFilter {

  FilterType getType();

  String getKey();

  String getCondition();

  String getValue();

  List<String> getInList();

  List<? extends IFilter> getList();

  Class getClazz();

  boolean isNoValue();

  boolean isAndComplex();

  boolean isOrComplex();

  boolean isIn();

  boolean isComplex();
}
