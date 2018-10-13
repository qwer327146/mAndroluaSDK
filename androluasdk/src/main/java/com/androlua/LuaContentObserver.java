package com.androlua;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;

import com.luajava.LuaContext;
import com.luajava.LuaGcable;

/**
 * Created by Administrator on 2017/11/09 0009.
 */

public class LuaContentObserver extends ContentObserver implements LuaGcable {

    private OnChangeListener mOnChangeListener;

    private LuaContentObserver(Handler handler) {
        super(handler);
    }

    public LuaContentObserver(LuaContext<Context> context, String uri) {
        this(new Handler(LuaApplication.getInstance().getMainLooper()));
        Uri mUri = Uri.parse(uri);
        context.regGc(this);
        LuaApplication.getInstance().getContentResolver().registerContentObserver(
                mUri,
                true,
                this
        );
    }


    public LuaContentObserver(LuaContext<Context> context, Uri mUri) {
        this(new Handler(LuaApplication.getInstance().getMainLooper()));

        context.regGc(this);
        LuaApplication.getInstance().getContentResolver().registerContentObserver(
                mUri,
                true,
                this
        );
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        super.onChange(selfChange, uri);
        if (mOnChangeListener != null){
            Cursor cursor = LuaApplication.getInstance().getContentResolver().query(uri, null, null, null, null);
            if(cursor != null)
                cursor.moveToFirst();
            mOnChangeListener.onChange(selfChange, uri,cursor);
            if(cursor != null)
               cursor.close();
        }
    }

    public void setOnChangeListener(OnChangeListener listener) {
        mOnChangeListener = listener;
    }

    @Override
    public void gc() {
        LuaApplication.getInstance().getContentResolver().unregisterContentObserver(this);
    }

    public interface OnChangeListener {
        public void onChange(boolean selfChange, Uri uri,Cursor cursor);
    }

}
