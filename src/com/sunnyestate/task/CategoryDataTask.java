package com.sunnyestate.task;

import com.sunnyestate.data.CategoryData;
import com.sunnyestate.enums.RetError;

public class CategoryDataTask extends
		BaseAsyncTask<CategoryData, Void, RetError> {
	private CategoryData data;
	private boolean refush_menu;
	private int cid = 0;
	private int filter_id = 0;
	private int filter_item_id = 0;

	public CategoryDataTask(boolean refush_menu, int cid, int filter_id,
			int filter_item_id) {
		this.refush_menu = refush_menu;
		this.cid = cid;
		this.filter_id = filter_id;
		this.filter_item_id = filter_item_id;
	}

	// "cid=0&filter=0-0"
	@Override
	protected RetError doInBackground(CategoryData... params) {
		data = params[0];
		RetError ret = RetError.NONE;
		if (refush_menu) {
			ret = data.refushCategoryList();
			if (ret != RetError.NONE) {
				return ret;
			}
		}
		ret = data.refushCategoryData("cid=" + cid + "&filter=" + filter_id
				+ "-" + filter_item_id);
		return ret;
	}
}
