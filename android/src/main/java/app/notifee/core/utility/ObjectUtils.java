package app.notifee.core.utility;

/*
 * Copyright (c) 2016-present Invertase Limited & Contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this library except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

import android.os.Bundle;
import android.os.Parcel;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ObjectUtils {

  public static @Nullable Class getClassForName(String className) {
    try {
      return Class.forName(className);
    } catch (ClassNotFoundException e) {
      return null;
    }
  }

  public static @Nullable Object getClassInstanceFromDefaultConstructor(@Nullable Class clazz) {
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

  public static int getInt(Object value) {
    if (value instanceof Double) {
      return (int) ((Double) value).doubleValue();
    }

    return (int) value;
  }

  public static long getLong(Object value) {
    if (value instanceof Double) {
      return (long) ((Double) value).doubleValue();
    }

    return (long) value;
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

  public static Map<String, Object> bundleToMap(Bundle bundle) throws IllegalArgumentException {
    Map<String, Object> map = new HashMap<>();
    for (String key : bundle.keySet()) {
      Object value = bundle.get(key);
      if (value == null) {
        map.put(key, null);
      } else if (value.getClass().isArray()) {
        map.put(key, arrayToMap(value));
      } else if (value instanceof Bundle) {
        map.put(key, bundleToMap((Bundle) value));
      } else if (value instanceof List) {
        map.put(key, listToMap((List) value));
      } else {
        map.put(key, value);
      }
    }
    return map;
  }

  public static ArrayList arrayToMap(Object array) throws IllegalArgumentException {
    ArrayList catalystArray = new ArrayList();
    if (array instanceof String[]) {
      for (String v : (String[]) array) {
        catalystArray.add(v);
      }
    } else if (array instanceof Bundle[]) {
      for (Bundle v : (Bundle[]) array) {
        catalystArray.add(ObjectUtils.bundleToMap(v));
      }
    } else if (array instanceof int[]) {
      for (int v : (int[]) array) {
        catalystArray.add(v);
      }
    } else if (array instanceof float[]) {
      for (float v : (float[]) array) {
        catalystArray.add(v);
      }
    } else if (array instanceof double[]) {
      for (double v : (double[]) array) {
        catalystArray.add(v);
      }
    } else if (array instanceof boolean[]) {
      for (boolean v : (boolean[]) array) {
        catalystArray.add(v);
      }
    } else {
      throw new IllegalArgumentException("Unknown array type " + array.getClass());
    }
    return catalystArray;
  }

  public static ArrayList listToMap(List list) throws IllegalArgumentException {
    ArrayList catalystArray = new ArrayList();
    for (Object obj : list) {
      if (obj == null) {
        catalystArray.add(null);
      } else if (obj.getClass().isArray()) {
        catalystArray.add(arrayToMap(obj));
      } else if (obj instanceof Bundle) {
        catalystArray.add(ObjectUtils.bundleToMap((Bundle) obj));
      } else if (obj instanceof List) {
        catalystArray.add(listToMap((List) obj));
      } else if (obj instanceof String) {
        catalystArray.add((String) obj);
      } else if (obj instanceof Integer) {
        catalystArray.add((Integer) obj);
      } else if (obj instanceof Number) {
        catalystArray.add(((Number) obj).doubleValue());
      } else if (obj instanceof Boolean) {
        catalystArray.add((Boolean) obj);
      } else {
        throw new IllegalArgumentException("Unknown value type " + obj.getClass());
      }
    }
    return catalystArray;
  }

  public interface TypedCallback<T> {
    void call(T t);
  }
}
