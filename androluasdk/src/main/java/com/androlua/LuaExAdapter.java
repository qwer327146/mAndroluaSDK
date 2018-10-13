package com.androlua;

import android.content.Context;

import com.luajava.LuaContext;
import com.luajava.LuaException;
import com.luajava.LuaTable;

public class LuaExAdapter extends LuaExpandableListAdapter 
{
	public LuaExAdapter(LuaContext<Context> context, LuaTable groupLayout, LuaTable childLayout) throws LuaException {
		this(context,null,null,groupLayout,childLayout);
	}
	
	public LuaExAdapter(LuaContext<Context> context, LuaTable<Integer,LuaTable<String,Object>> groupData, LuaTable<Integer,LuaTable<Integer,LuaTable<String,Object>>> childData, LuaTable groupLayout, LuaTable childLayout) throws LuaException {
		super(context,groupData,childData,groupLayout,childLayout);
	}
}
