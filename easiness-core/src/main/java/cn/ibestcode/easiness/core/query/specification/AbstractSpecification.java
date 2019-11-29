package cn.ibestcode.easiness.core.query.specification;

import cn.ibestcode.easiness.core.exception.EasinessException;
import cn.ibestcode.easiness.core.query.filter.IFilter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.Assert;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public abstract class AbstractSpecification<T> implements Specification<T> {

  public static <T> Predicate generatePredicate(IFilter filter, Root<T> root, CriteriaBuilder builder) {
    if (filter.isAndComplex()) {
      return generatePredicate(filter.getList(), root, builder, true);
    }
    if (filter.isOrComplex()) {
      return generatePredicate(filter.getList(), root, builder, false);
    }
    throw new EasinessException("FilterTypeOnlySupportTwo", "and", "or");
  }

  public static <T> Predicate generatePredicate(List<? extends IFilter> filters, Root<T> root, CriteriaBuilder builder) {
    return generatePredicate(filters, root, builder, true);
  }

  public static <T> Predicate generatePredicate(List<? extends IFilter> filters, Root<T> root, CriteriaBuilder builder, boolean and) {
    List<Predicate> predicates = new ArrayList<>();
    for (IFilter filter : filters) {
      switch (filter.getType()) {
        case and: {
          predicates.add(generatePredicate(filter.getList(), root, builder, true));
          break;
        }
        case or: {
          predicates.add(generatePredicate(filter.getList(), root, builder, false));
          break;
        }
        case in: {
          if (filter.getClazz() == null) {
            predicates.add(root.get(filter.getKey()).in(filter.getInList()));
          } else {
            List<Object> os = generateInEnumObjects(filter);
            predicates.add(root.get(filter.getKey()).in(os));
          }
          break;
        }
        case notIn: {
          if (filter.getClazz() == null) {
            predicates.add(builder.not(root.get(filter.getKey()).in(filter.getInList())));
          } else {
            List<Object> os = generateInEnumObjects(filter);
            predicates.add(builder.not(root.get(filter.getKey()).in(os)));
          }
          break;
        }
        case equal: {
          if (filter.getClazz() == null) {
            predicates.add(builder.equal(root.get(filter.getKey()), filter.getValue()));
          } else {
            Object o = generateEqualEnumObject(filter);
            predicates.add(builder.equal(root.get(filter.getKey()), o));
          }
          break;
        }
        case greaterEqual: {
          predicates.add(builder.greaterThanOrEqualTo(root.get(filter.getKey()), filter.getValue()));
          break;
        }
        case lessEqual: {
          predicates.add(builder.lessThanOrEqualTo(root.get(filter.getKey()), filter.getValue()));
          break;
        }
        case greaterThen: {
          predicates.add(builder.greaterThan(root.get(filter.getKey()), filter.getValue()));
          break;
        }
        case lessThen: {
          predicates.add(builder.lessThan(root.get(filter.getKey()), filter.getValue()));
          break;
        }
        case notEqual: {
          if (filter.getClazz() == null) {
            predicates.add(builder.notEqual(root.get(filter.getKey()), filter.getValue()));
          } else {
            Object o = generateEqualEnumObject(filter);
            predicates.add(builder.notEqual(root.get(filter.getKey()), o));
          }
          break;
        }

        case eq: {
          predicates.add(builder.equal(root.get(filter.getKey()), Double.parseDouble(filter.getValue())));
          break;
        }
        case ge: {
          predicates.add(builder.ge(root.get(filter.getKey()), Double.parseDouble(filter.getValue())));
          break;
        }
        case le: {
          predicates.add(builder.le(root.get(filter.getKey()), Double.parseDouble(filter.getValue())));
          break;
        }
        case gt: {
          predicates.add(builder.gt(root.get(filter.getKey()), Double.parseDouble(filter.getValue())));
          break;
        }
        case lt: {
          predicates.add(builder.lt(root.get(filter.getKey()), Double.parseDouble(filter.getValue())));
          break;
        }
        case ne: {
          predicates.add(builder.notEqual(root.get(filter.getKey()), Double.parseDouble(filter.getValue())));
          break;
        }

        case like: {
          predicates.add(builder.like(root.get(filter.getKey()), filter.getValue()));
          break;
        }
        case notLike: {
          predicates.add(builder.notLike(root.get(filter.getKey()), filter.getValue()));
          break;
        }
        case isNull: {
          predicates.add(root.get(filter.getKey()).isNull());
          break;
        }
        case isNotNull: {
          predicates.add(root.get(filter.getKey()).isNotNull());
          break;
        }
        case isTrue: {
          predicates.add(builder.isTrue(root.get(filter.getKey())));
          break;
        }
        case isFalse: {
          predicates.add(builder.isFalse(root.get(filter.getKey())));
          break;
        }
        default: {
          log.warn("the FilterType {} not found ", filter.getType().name());
          throw new EasinessException("FilterTypeNotFound", filter.getType().name());
        }
      }
    }
    if (and) {
      return builder.and(predicates.toArray(new Predicate[0]));
    } else {
      return builder.or(predicates.toArray(new Predicate[0]));
    }
  }

  private static List<Object> generateInEnumObjects(IFilter filter) {
    Class clazz = filter.getClazz();
    Object[] objects = clazz.getEnumConstants();
    List<Object> os = new ArrayList<>();
    if (clazz.isEnum()) {
      Method method = getMethod(clazz, "name");
      List<String> list = filter.getInList();
      for (Object o : objects) {
        String name = (String) invoke(o, method);
        if (list.contains(name)) {
          os.add(o);
        }
      }
    }
    return os;
  }

  private static Object generateEqualEnumObject(IFilter filter) {
    Class clazz = filter.getClazz();
    if (clazz.isEnum()) {
      Object[] objects = clazz.getEnumConstants();
      Method method = getMethod(clazz, "name");
      for (Object o : objects) {
        String name = (String) invoke(o, method);
        if (name.equals(filter.getValue())) {
          return o;
        }
      }
    }
    return null;
  }

  private static Method getMethod(Class clazz, String methodName, Class<?>... parameterTypes) {
    try {
      return clazz.getMethod(methodName, parameterTypes);
    } catch (NoSuchMethodException e) {
      log.warn(e.getMessage(), e);
      throw new EasinessException("NoSuchMethodException", e, "name");
    }
  }

  private static Object invoke(Object object, Method method, Class<?>... parameterTypes) {
    try {
      return method.invoke(object, parameterTypes);
    } catch (IllegalAccessException e) {
      log.warn(e.getMessage(), e);
      throw new EasinessException("IllegalAccessException", e);
    } catch (InvocationTargetException e) {
      log.warn(e.getMessage(), e);
      if (e.getCause() instanceof Error) {
        throw (Error) e.getCause();
      }
      if (e.getCause() instanceof RuntimeException) {
        throw (RuntimeException) e.getCause();
      }
      throw new EasinessException("IllegalAccessException", e.getCause());
    }
  }

}
