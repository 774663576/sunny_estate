package com.sunnyestate.contentprovider;

import com.sunnyestate.db.Const;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * 存放跟数据库有关的常量
 * 
 */
public class ShoppingCarProvider {

	// 这个是每个Provider的标识，在Manifest中使用
	public static final String AUTHORITY = "com.sunnyestate.shoppingcar";
	public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.sunnyestate";

	public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.sunnyestate";

	public static final class ShoppingCarColumns implements BaseColumns {
		// CONTENT_URI跟数据库的表关联，最后根据CONTENT_URI来查询对应的表
		public static final Uri CONTENT_URI = Uri.parse("content://"
				+ AUTHORITY + "/shoppingcar");
		public static final String TABLE_NAME = Const.SHOPPING_CAR_TABLE_NAME;
		public static final String SHOPPING_ID = "shopping_id";
		public static final String PRICE = "price";
		public static final String TITLE = "title";
		public static final String IMG_URL = "img_url";
		public static final String COUNT = "count";
		public static final String MEMBER_PRICE = "member_price";

	}

}
