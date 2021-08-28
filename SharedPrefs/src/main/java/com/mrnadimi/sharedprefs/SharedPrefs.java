package com.mrnadimi.sharedprefs;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.StringRes;

import com.google.gson.Gson;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Developer: Mohamad Nadimi
 * Company: Saghe
 * Website: https://www.mrnadimi.com
 * Created on 02 August 2021
 * <p>
 * Description: ...
 */
public class SharedPrefs {

    private static SharedPrefs sharedPrefs;

    private final WeakReference<Context> context;

    private final String defaultFileName;

    public static SharedPrefs init(Context context){
        Objects.requireNonNull(context);
        if (sharedPrefs == null){
            sharedPrefs = new SharedPrefs(context.getApplicationContext());
        }
        return sharedPrefs;
    }

    public static SharedPrefs get(){
        return getOrThrow();
    }

    private static SharedPrefs getOrThrow(){
        if (sharedPrefs == null){
            throw new NullPointerException("Please init 'SharedPrefs' first.");
        }
        return sharedPrefs;
    }

    private SharedPrefs(Context applicationContext) {
        context = new WeakReference<Context>(applicationContext);
        defaultFileName = applicationContext.getPackageName();
    }


    private SharedPreferences getSharedPerformance(String saveFileName){
        return context.get().getSharedPreferences(saveFileName
                , Context.MODE_PRIVATE);
    }

    private <T> void save(String fileName , String key , T value , Class<T> clazz){
        SharedPreferences.Editor edit =  getSharedPerformance(fileName).edit();
        if (clazz == Integer.class){
            edit.putInt(key , (Integer) value);
        }else if (clazz == Float.class){
            edit.putFloat(key , (Float) value);
        }else if (clazz == Long.class){
            edit.putLong(key , (Long) value);
        }else if (clazz == Boolean.class){
            edit.putBoolean(key , (Boolean) value);
        }else if (clazz == String.class){
            edit.putString(key , (String) value);
        }else {
            Gson gson = new Gson();
            String json = value != null ?  gson.toJson(value) : null;
            edit.putString(key , json);
        }
        edit.apply();
    }

    @SuppressWarnings("unchecked")
    private <T> T load(String fileName , String key  , Class<T> clazz , T defValue){
        SharedPreferences sharedPreferences = getSharedPerformance(fileName);
        if (clazz == Integer.class){
            return ((T)((Integer)sharedPreferences.getInt(key , (Integer) defValue)));
        }else if (clazz == Float.class){
            return ((T)((Float)sharedPreferences.getFloat(key , (Float) defValue)));
        }else if (clazz == Long.class){
            return ((T)((Long)sharedPreferences.getLong(key , (Long) defValue)));
        }else if (clazz == Boolean.class){
            return ((T)((Boolean)sharedPreferences.getBoolean(key , (Boolean) defValue)));
        }else if (clazz == String.class){
            return ((T)(sharedPreferences.getString(key , (String) defValue)));
        }else {
            Gson gson = new Gson();
            String result = sharedPreferences.getString(key, (String) defValue);
            if (result == null){
                return null;
            }
            return gson.fromJson(result , clazz);
        }
    }

    public void saveInteger(@StringRes int key , int value ){
        saveInteger( defaultFileName , key, value);
    }

    public void saveInteger( String key , int value ){
        saveInteger( defaultFileName ,  key , value);
    }

    public void saveInteger(String fileName ,  @StringRes int key , int value ){
        saveInteger( fileName ,  context.get().getString(key) , value);
    }

    public void saveInteger( String fileName ,  String key , int value ){
        save( fileName , key , value , Integer.class);
    }

    public void saveFloat( @StringRes int key  , float value ){
        saveFloat(defaultFileName , key  , value );
    }

    public void saveFloat( String key , float value ){
        saveFloat(defaultFileName ,  key , value );
    }

    public void saveFloat(String saveFileName, @StringRes int key  , float value ){
        saveFloat(saveFileName ,  context.get().getString(key) , value );
    }

    public void saveFloat(String saveFileName ,  String key , float value ){
        save( saveFileName , key , value , Float.class);
    }

    public void saveLong(@StringRes int key , long value ){
        saveLong(defaultFileName ,  key , value);
    }

    public void saveLong(String key , long value ){
        saveLong(defaultFileName ,  key , value);
    }

    public void saveLong(String saveFileName ,@StringRes int key , long value ){
        saveLong(saveFileName ,  context.get().getString(key) , value);
    }

    public void saveLong(String saveFileName ,String key , long value ){
        save(saveFileName ,  key , value , Long.class);
    }

    public void saveBoolean( @StringRes int key  , boolean value ){
        saveBoolean( defaultFileName , key  , value );
    }

    public void saveBoolean( String key , boolean value ){
        saveBoolean( defaultFileName , key , value );
    }

    public void saveBoolean(String saveFileName , @StringRes int key, boolean value ){
        saveBoolean( saveFileName , context.get().getString(key) , value );
    }

    public void saveBoolean(String saveFileName , String key , boolean value ){
        save(saveFileName ,  key , value , Boolean.class);
    }

    public void saveString( @StringRes int key  , String value ){
        saveString(defaultFileName , key , value);
    }

    public void saveString( String key , String value ){
        saveString(defaultFileName , key , value);
    }

    public void saveString(String saveFileName ,  @StringRes int key , String value ){
        saveString(saveFileName , context.get().getString(key) , value);
    }

    public void saveString(String saveFileName , String key , String value ){
        save(saveFileName , key , value , String.class);
    }

    public void saveMap( @StringRes int key , Map<? , ?> value ){
        saveMap(defaultFileName , key  , value);
    }

    public void saveMap( String key , Map<? , ?> value ){
        saveMap(defaultFileName , key , value);
    }

    public void saveMap(String saveFileName , @StringRes int key , Map<? , ?> value ){
        saveMap(saveFileName , context.get().getString(key) , value);
    }

    public void saveMap(String saveFileName , String key , Map<? , ?> value ){
        save(saveFileName , key , value , Map.class);
    }

    public void saveList( @StringRes int key , List<?> value ){
        saveList(defaultFileName , key , value);
    }

    public void saveList( String key , List<?> value ){
        saveList(defaultFileName , key , value);
    }

    public void saveList(String saveFileName , @StringRes int key , List<?> value ){
        saveList(saveFileName , context.get().getString(key)  , value);
    }

    public void saveList(String saveFileName , String key , List<?> value ){
        save(saveFileName , key , value , List.class);
    }

    public <T> void saveObject( @StringRes int key , T value){
        saveObject(defaultFileName , key  , value);
    }

    public <T> void saveObject(String key , T value){
        saveObject(defaultFileName , key , value);
    }

    public <T> void saveObject(String saveFileName ,@StringRes int key , T value){
        saveObject(saveFileName , context.get().getString(key) , value);
    }

    @SuppressWarnings("unchecked")
    public <T> void saveObject(String saveFileName , String key , T value){
        save(saveFileName , key , value , (Class<T>)value.getClass());
    }





    public int loadInteger(@StringRes int key, int defaultValue){
        return loadInteger(defaultFileName ,  key , defaultValue);
    }

    public int loadInteger(String key , int defaultValue){
        return loadInteger(defaultFileName ,  key, defaultValue);
    }

    public int loadInteger(String saveFileName ,@StringRes int key , int defaultValue){
        return loadInteger(saveFileName ,  context.get().getString(key), defaultValue);
    }

    @SuppressWarnings("ConstantConditions")
    public int loadInteger(String saveFileName , String key , int defaultValue){
        return load(saveFileName ,  key , Integer.class , defaultValue);
    }

    public float loadFloat(@StringRes int key , float defaultValue){
        return loadFloat(defaultFileName,  key  , defaultValue);
    }

    public float loadFloat(String key , float defaultValue){
        return loadFloat(defaultFileName,  key  , defaultValue);
    }

    public float loadFloat(String saveFileName  ,@StringRes int key , float defaultValue){
        return loadFloat(saveFileName,  context.get().getString(key)   , defaultValue);
    }

    @SuppressWarnings("ConstantConditions")
    public float loadFloat(String saveFileName  ,String key , float defaultValue){
        return load(saveFileName ,   key , Float.class , defaultValue);
    }

    public long loadLong(@StringRes int key  , long defaultValue){
        return loadLong(defaultFileName, key   , defaultValue);
    }

    public long loadLong( String key , long defaultValue){
        return loadLong(defaultFileName, key  , defaultValue);
    }

    public long loadLong(String saveFileName  , @StringRes int key, long defaultValue){
        return loadLong(saveFileName,  context.get().getString(key)  , defaultValue);
    }

    @SuppressWarnings("ConstantConditions")
    public long loadLong(String saveFileName  , String key , long defaultValue){
        return load(saveFileName ,  key , Long.class , defaultValue);
    }

    public boolean loadBoolean( @StringRes int key , boolean defaultValue){
        return loadBoolean(defaultFileName ,  key , defaultValue);
    }

    public boolean loadBoolean( String key , boolean defaultValue){
        return loadBoolean(defaultFileName ,  key  , defaultValue);
    }

    public boolean loadBoolean(String saveFileName ,  @StringRes int key , boolean defaultValue){
        return loadBoolean(saveFileName ,  context.get().getString(key)    , defaultValue);
    }

    @SuppressWarnings("ConstantConditions")
    public boolean loadBoolean(String saveFileName ,  String key , boolean defaultValue){
        return load(saveFileName ,  key , Boolean.class , defaultValue);
    }

    public String loadString(@StringRes int key  , String defaultValue){
        return loadString(defaultFileName ,  key , defaultValue);
    }

    public String loadString(String key , String defaultValue){
        return loadString(defaultFileName ,  key  , defaultValue);
    }

    public String loadString(String saveFileName ,@StringRes int key  , String defaultValue){
        return loadString(saveFileName ,  context.get().getString(key)   , defaultValue);
    }

    public String loadString(String saveFileName , String key , String defaultValue){
        return load(saveFileName ,  key , String.class , defaultValue);
    }

    public Map<? , ?> loadMap(@StringRes int key){
        return loadMap(defaultFileName ,  key );
    }

    public Map<? , ?> loadMap(String key){
        return loadMap(defaultFileName ,  key );
    }

    public Map<? , ?> loadMap(String saveFileName ,@StringRes int key){
        return loadMap(saveFileName ,  context.get().getString(key) );
    }

    public Map<? , ?> loadMap(String saveFileName , String key){
        return load(saveFileName ,  key , Map.class , null);
    }

    public List<?> loadList(@StringRes int key){
        return loadList(defaultFileName ,  key);
    }

    public List<?> loadList(String key){
        return loadList(defaultFileName ,  key );
    }

    public List<?> loadList(String saveFileName ,@StringRes int key){
        return loadList(saveFileName ,  context.get().getString(key)  );
    }

    public List<?>  loadList(String saveFileName , String key){
        return load(saveFileName ,  key , List.class , null);
    }

    public <T> T loadObject(@StringRes int key  ,Class<T> clazz ,  T defaultValue){
        return loadObject(defaultFileName ,   key  ,clazz ,  defaultValue);
    }

    public <T> T loadObject(String key  ,Class<T> clazz ,  T defaultValue){
        return loadObject(defaultFileName ,  key  ,clazz ,  defaultValue);
    }

    public <T> T loadObject(String saveFileName ,@StringRes int key   ,Class<T> clazz ,  T defaultValue){
        return loadObject(saveFileName ,  context.get().getString(key) , clazz  , defaultValue);
    }

    public <T> T loadObject(String saveFileName , String key ,Class<T> clazz ,  T defaultValue){
        return load(saveFileName ,  key , clazz , defaultValue);
    }
}
