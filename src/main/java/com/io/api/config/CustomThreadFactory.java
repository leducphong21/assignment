package com.io.api.config;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;
import com.sun.istack.NotNull;

public class CustomThreadFactory implements ThreadFactory {
  
  private String namePrefix;
  private final AtomicLong threadNumber = new AtomicLong();

  public CustomThreadFactory(String namePrefix) {
    this.namePrefix = namePrefix;
  }

  @Override
  public Thread newThread(@NotNull Runnable runnable) {
    return new Thread(runnable, namePrefix + ".Thread-" + threadNumber.getAndIncrement());
  }

}
