package com.androlua;

import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.util.SparseIntArray;

import com.luajava.LuaException;
import com.luajava.LuaMetaTable;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/04/24 0024.
 */

public class LuaResources extends Resources implements LuaMetaTable {
    private static SparseArray<String> mTextMap = new SparseArray<String>();
    private static SparseArray<Drawable> mDrawableMap = new SparseArray<Drawable>();
    private static SparseIntArray mColorMap = new SparseIntArray();
    private static int mId = 0x7f050000;
    private static HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

    /**
     * Create a new Resources object on top of an existing set of assets in an
     * AssetManager.
     *
     * @param assets  Previously created AssetManager.
     * @param metrics Current display metrics to consider when
     *                selecting/computing resource values.
     * @param config  Desired device configuration to consider when
     */
    public LuaResources(AssetManager assets, DisplayMetrics metrics, Configuration config) {
        super(assets, metrics, config);
    }

    public static void setText(int id, String text) {
        if(text==null)
            throw new NullPointerException();
        mTextMap.put(id, text);
    }

    public static void setDrawable(int id, Drawable drawable) {
        if(drawable==null)
            throw new NullPointerException();
        mDrawableMap.put(id, drawable);
    }

    public static void setColor(int id, int color) {
        mColorMap.put(id, color);
    }

    @Override
    public CharSequence getText(int id) throws NotFoundException {
        String text = mTextMap.get(id);
        if (text != null)
            return text;
        return super.getText(id);
    }

    @Override
    public CharSequence getText(int id, CharSequence def) {
        String text = mTextMap.get(id);
        if (text != null)
            return text;
        return super.getText(id, def);
    }

    @Override
    public Drawable getDrawable(int id) throws NotFoundException {
        Drawable drawable = mDrawableMap.get(id);
        if (drawable != null)
            return drawable;
        return super.getDrawable(id);
    }

    @Override
    public Drawable getDrawable(int id, Theme theme) throws NotFoundException {
        Drawable drawable = mDrawableMap.get(id);
        if (drawable != null)
            return drawable;
        return super.getDrawable(id, theme);
    }

    @Override
    public int getColor(int id) throws NotFoundException {
        int color = mColorMap.get(id);
        if (color != 0)
            return color;
        return super.getColor(id);
    }

    @Override
    public Object __call(Object... arg) throws LuaException {
        return null;
    }

    public static int put(String key, Object value) {
        if(value==null)
            throw new NullPointerException();
        int id = mId++;
        if (value instanceof Drawable) {
            setDrawable(id, (Drawable) value);
        } else if (value instanceof String) {
            setText(id, (String) value);
        } else if (value instanceof Number) {
            setColor(id, ((Number) value).intValue());
        }else{
            throw new IllegalArgumentException();
        }
        mIdMap.put(key, id);
        return id;
    }

    public static Object get(String key) {
        return mIdMap.get(key);
    }

    @Override
    public Object __index(String key) {
        return get(key);
    }

    @Override
    public void __newIndex(String key, Object value) {
        put(key,value);
    }
}
