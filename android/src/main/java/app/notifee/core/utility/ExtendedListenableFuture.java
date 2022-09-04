package app.notifee.core.utility;

import com.google.common.util.concurrent.AsyncFunction;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.checkerframework.checker.nullness.qual.Nullable;

public class ExtendedListenableFuture<T> implements ListenableFuture<T> {

  private final ListenableFuture<T> future;

  public ExtendedListenableFuture(ListenableFuture<T> future) {
    this.future = future;
  }

  public <O extends @Nullable Object> ExtendedListenableFuture<O> continueWith(
    AsyncFunction<T, O> asyncFunction, Executor lExecutor) {
    ListenableFuture<O> future = Futures.transformAsync(this, asyncFunction,
      lExecutor);
    return new ExtendedListenableFuture<>(future);
  }

  public void addOnCompleteListener(Callbackable<T> callbackable,
    Executor executor) {
    Futures.addCallback(this, new FutureCallback<T>() {
      @Override
      public void onSuccess(T result) {
        callbackable.call(null, result);
      }

      @Override
      public void onFailure(Throwable t) {
        callbackable.call(new Exception(t), null);
      }
    }, executor);
  }

  @Override
  public void addListener(Runnable listener, Executor executor) {
    future.addListener(listener, executor);
  }

  @Override
  public boolean cancel(boolean mayInterruptIfRunning) {
    return future.cancel(mayInterruptIfRunning);
  }

  @Override
  public boolean isCancelled() {
    return future.isCancelled();
  }

  @Override
  public boolean isDone() {
    return future.isDone();
  }

  @Override
  public T get() throws ExecutionException, InterruptedException {
    return future.get();
  }

  @Override
  public T get(long timeout, TimeUnit unit)
    throws ExecutionException, InterruptedException, TimeoutException {
    return future.get(timeout, unit);
  }

}
