/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.eventbus.subscriber;

import cn.ibestcode.easiness.eventbus.annotation.Listener;
import cn.ibestcode.easiness.eventbus.annotation.Subscribe;

import java.lang.annotation.*;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2019/11/19 19:25
 */
public abstract class AbstractSubscriberResolver implements SubscriberResolver {

  private final Class<? extends Annotation> subscribeAnnotation;
  private final Class<? extends Annotation> listenerAnnotation;

  protected AbstractSubscriberResolver() {
    this(Subscribe.class, Listener.class);
  }

  protected AbstractSubscriberResolver(Class<? extends Annotation> subscribeAnnotation,
                                       Class<? extends Annotation> listenerAnnotation) {
    if (subscribeAnnotation == null) {
      throw new NullPointerException("subscribeAnnotation can be Null");
    }
    if (listenerAnnotation == null) {
      throw new NullPointerException("listenerAnnotation can be Null");
    }
    this.subscribeAnnotation = subscribeAnnotation;
    this.listenerAnnotation = listenerAnnotation;
  }

  @Override
  public List<Subscriber> getListeners(Object instance) {
    if (instance == null) {
      return Collections.emptyList();
    }

    final List<Subscriber> subscribers = new ArrayList<>();

    // 由 Subscribe 注解的方法
    if (isAnnotationPresent(instance.getClass(), listenerAnnotation)) {
      subscribers.addAll(getListenersBySubscribeAnnotation(instance));
    }

    return subscribers;
  }

  private List<Subscriber> getListenersBySubscribeAnnotation(Object instance) {
    List<Method> methods = getAnnotatedMethods(instance.getClass());
    if (methods == null || methods.isEmpty()) {
      return Collections.emptyList();
    }
    List<Subscriber> subscribers = new ArrayList<>(methods.size());
    for (Method method : methods) {
      subscribers.add(generateListener(instance, method));
    }
    return subscribers;
  }

  protected abstract Subscriber generateListener(Object instance, Method method);


  private List<Method> getAnnotatedMethods(final Class type) {
    final List<Method> methods = new ArrayList<>();
    Class clazz = type;
    while (!Object.class.equals(clazz)) {
      Method[] currentClassMethods = clazz.getDeclaredMethods();
      for (final Method method : currentClassMethods) {
        if (isAnnotationPresent(method, subscribeAnnotation) && !method.isSynthetic()) {
          methods.add(method);
        }
      }
      // 获取其父类，以搜索更多的方法
      // 直到 Object 类为止
      clazz = clazz.getSuperclass();
    }
    return methods;
  }

  private static final List<Class<? extends Annotation>> metaAnnotations = Arrays.asList(
    Target.class, Retention.class, Inherited.class, Documented.class
  );

  protected boolean isAnnotationPresent(AnnotatedElement element, Class<? extends Annotation> annotationClass) {
    Set<AnnotatedElement> processedClasses = new HashSet<>();
    Queue<AnnotatedElement> pendingQueue = new LinkedList<>();
    pendingQueue.offer(element);
    while ((element = pendingQueue.poll()) != null) {
      if (element.isAnnotationPresent(annotationClass)) {
        return true;
      }
      processedClasses.add(element);
      for (Annotation annotation : element.getAnnotations()) {
        Class<? extends Annotation> annotationType = annotation.annotationType();
        if (!metaAnnotations.contains(annotationType) && !processedClasses.contains(annotationType)) {
          pendingQueue.offer(annotationType);
        }

      }
    }
    return false;
  }
}
