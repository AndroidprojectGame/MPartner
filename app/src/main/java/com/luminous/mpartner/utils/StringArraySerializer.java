package com.luminous.mpartner.utils;



import com.luminous.mpartner.constants.UrlConstants;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;
import java.util.Vector;

public class StringArraySerializer extends Vector<String> implements
		KvmSerializable {

	@Override
	public Object getProperty(int arg0) {
		return this.get(arg0);
	}

	@Override
	public int getPropertyCount() {
		return this.size();
	}

	@Override
	public void getPropertyInfo(int arg0, Hashtable arg1, PropertyInfo arg2) {
		arg2.name = "string";
		arg2.type = PropertyInfo.STRING_CLASS;
		arg2.namespace = UrlConstants.NAMESPACE;
	}

	@Override
	public void setProperty(int arg0, Object arg1) {
		this.add(arg1.toString());
	}

}