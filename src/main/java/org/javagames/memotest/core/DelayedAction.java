package org.javagames.memotest.core;

import java.util.concurrent.CompletableFuture;

public class DelayedAction {

  static void delay(final long time, final Runnable runnable) {
    CompletableFuture.runAsync(() -> {
      try {
        Thread.sleep(time);
      }
      catch (InterruptedException e) {}

      runnable.run();
    });
  }
}
