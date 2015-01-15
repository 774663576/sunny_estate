package com.sunnyestate.enums;

import java.util.HashMap;
import java.util.Map;

public enum RetError {
	NONE, INVALID, NETWORK_ERROR, ;

	public static Map<String, RetError> str2Error = new HashMap<String, RetError>();
	static {
		for (RetError err : RetError.values()) {
			str2Error.put(err.name(), err);
		}
	}
	public static Map<String, String> s2t = new HashMap<String, String>();
	static {
		s2t.put("NETWORK_ERROR", "ÍøÂç´íÎó,Çë¼ì²éÍøÂç");
	}

	public static RetError convert(String err) {
		if (!str2Error.containsKey(err)) {
			return RetError.INVALID;
		} else {
			return str2Error.get(err);
		}
	}

	public static String toText(RetError err) {
		if (s2t.containsKey(err.toString())) {
			return s2t.get(err.toString());
		}
		return "²Ù×÷Ê§°Ü";
	}

}
