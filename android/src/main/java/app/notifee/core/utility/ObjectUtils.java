package app.notifee.core.utility;

import android.os.Bundle;
import android.os.Parcel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ObjectUtils {

  public static @Nullable
  Class getClassForName(String className) {
    try {
      return Class.forName(className);
    } catch (ClassNotFoundException e) {
      return null;
    }
  }

  public static @Nullable
  Object getClassInstanceFromDefaultConstructor(@Nullable Class clazz) {
    Object classInstance = null;
    if (clazz == null) return null;
    try {
      classInstance = clazz.getConstructors()[0].newInstance();
    } catch (IllegalAccessException e) {
      // noop
    } catch (InstantiationException e) {
      // noop
    } catch (InvocationTargetException e) {
      // noop
    }
    return classInstance;
  }

  public static Map<String, Object> getClassProperties(Class clazz) {
    Map<String, Object> properties = new HashMap<>();
    try {
      for (Field field : clazz.getDeclaredFields()) {
        field.setAccessible(true);
        Object value = field.get(clazz);
        if (value != null) {
          properties.put(field.getName(), value);
        }
      }
    } catch (IllegalAccessException exception) {
      return null;
    }
    return properties;
  }

  public static byte[] bundleToBytes(@NonNull Bundle bundle) {
    Parcel parcel = Parcel.obtain();
    parcel.writeBundle(bundle);
    byte[] bytes = parcel.marshall();
    parcel.recycle();
    return bytes;
  }

  @NonNull
  public static Bundle bytesToBundle(byte[] bytes) {
    Parcel parcel = Parcel.obtain();
    parcel.unmarshall(bytes, 0, bytes.length);
    parcel.setDataPosition(0);
    Bundle bundle = parcel.readBundle(ObjectUtils.class.getClassLoader());
    parcel.recycle();
    return Objects.requireNonNull(bundle);
  }

  public interface TypedCallback<T> {
    void call(T t);
  }
}
