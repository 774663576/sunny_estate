package com.sunnyestate.contentprovider;

import com.sunnyestate.db.Const;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * 存放跟数据库有关的常量
 * 
 */
public class AdressProvider {

	// 这个是每个Provider的标识，在Manifest中使用
	public static final String AUTHORITY = "com.sunnyestate.madress";
	public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.sunnyestate";

	public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.sunnyestate";

	public static final class AdressColumns implements BaseColumns {
		// CONTENT_URI跟数据库的表关联，最后根据CONTENT_URI来查询对应的表
		public static final Uri CONTENT_URI = Uri.parse("content://"
				+ AUTHORITY + "/madress");
		public static final String TABLE_NAME = Const.ADRESS_TABLE_NAME;
		public static final String ID = "_id";
		public static final String DEFAULT_ADRESS = "default_adress";
		public static final String NAME = "name";
		public static final String CELLPHONE = "cellphone";
		public static final String ADRESS = "adress";
		public static final String ADRESS_DETAIL = "adress_detail";
		public static final String CODE = "code";

	}

}
