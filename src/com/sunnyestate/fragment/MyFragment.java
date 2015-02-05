package com.sunnyestate.fragment;

import java.io.File;
import java.util.UUID;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.sunnyestate.EditPassWordActivity;
import com.sunnyestate.LoginAndRegisterActivity;
import com.sunnyestate.MyApplation;
import com.sunnyestate.PersonalCentenDingDan;
import com.sunnyestate.PersonalCenter;
import com.sunnyestate.R;
import com.sunnyestate.imagecrop.ImageFactoryActivity;
import com.sunnyestate.popwindow.MyRightPopWindow;
import com.sunnyestate.popwindow.MyRightPopWindow.OnlistOnclick;
import com.sunnyestate.popwindow.SelectPicPopwindow;
import com.sunnyestate.popwindow.SelectPicPopwindow.SelectOnclick;
import com.sunnyestate.task.ConfirmDialog;
import com.sunnyestate.utils.Constants;
import com.sunnyestate.utils.DialogUtil;
import com.sunnyestate.utils.FileUtils;
import com.sunnyestate.utils.PhotoUtils;
import com.sunnyestate.utils.SharedUtils;
import com.sunnyestate.utils.ToastUtil;
import com.sunnyestate.utils.Utils;
import com.sunnyestate.utils.WigdtContorl;
import com.sunnyestate.views.CircularImage;

import fynn.app.PromptDialog;

public class MyFragment extends Fragment implements OnClickListener,
		SelectOnclick {
	private Button btn_login;
	private Button btn_register;
	private ViewFlipper mVfFlipper;
	private TextView txt_jifen;
	private ImageView img_select;
	private CircularImage img_head;
	private TextView txt_title;
	private ImageView img_dingdan;
	private ImageView img_head_bg;
	private ScrollView mScrollView;
	private LinearLayout layout_login;
	private TextView txt_nick_name;
	private ImageView img_more;
	private TextView txt_member_state;

	private PersonalCenter perCenter;
	private PersonalCentenDingDan dingDan_view;

	private SelectPicPopwindow pic_pop;

	private String mTakePicturePath = "";
	private String imgPath = "";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.my_layout, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
		setValue();
		initTab();
		registerBoradcastReceiver();
	}

	private void initView() {
		txt_member_state = (TextView) getView().findViewById(
				R.id.txt_member_state);
		btn_register = (Button) getView().findViewById(R.id.btn_register);
		img_more = (ImageView) getView().findViewById(R.id.img_more);
		txt_nick_name = (TextView) getView().findViewById(R.id.txt_nick_name);
		layout_login = (LinearLayout) getView().findViewById(R.id.layout_login);
		mScrollView = (ScrollView) getView().findViewById(R.id.scrollView1);
		img_head_bg = (ImageView) getView().findViewById(R.id.img_head_bg);
		mVfFlipper = (ViewFlipper) getView().findViewById(R.id.viewflipper);
		mVfFlipper.setDisplayedChild(0);
		btn_login = (Button) getView().findViewById(R.id.btn_login);
		txt_jifen = (TextView) getView().findViewById(R.id.txt_jifen);
		txt_jifen.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD_ITALIC));
		txt_jifen.getPaint().setFakeBoldText(true);
		img_select = (ImageView) getView().findViewById(R.id.img_select);
		img_head = (CircularImage) getView().findViewById(R.id.img_head);
		txt_title = (TextView) getView().findViewById(R.id.txt_title);
		img_dingdan = (ImageView) getView().findViewById(R.id.img_dingdan);
		setListener();
		if (!SharedUtils.getPasswordKey().equals("")) {
			mScrollView.setVisibility(View.VISIBLE);
			img_more.setVisibility(View.VISIBLE);
		} else {
			layout_login.setVisibility(View.VISIBLE);
		}
	}

	private void setListener() {
		btn_login.setOnClickListener(this);
		img_select.setOnClickListener(this);
		img_more.setOnClickListener(this);
		btn_register.setOnClickListener(this);
	}

	private void initTab() {
		dingDan_view = new PersonalCentenDingDan(getActivity(), this,
				mVfFlipper.getChildAt(0));

	}

	public int getImgWidth() {
		return WigdtContorl.getWidth(img_dingdan);

	}

	private void setValue() {
		txt_jifen.setText(SharedUtils.getScore() + "分");
		txt_nick_name.setText(SharedUtils.getUserName());
		txt_member_state.setText(SharedUtils.getLevel());
	}

	private void setHead(Bitmap bmp, String path) {
		if (bmp != null) {
			img_head.setImageBitmap(bmp);
			img_head_bg.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_login:
			getActivity().startActivity(
					new Intent(getActivity(), LoginAndRegisterActivity.class)
							.putExtra("type", 1));
			Utils.leftOutRightIn(getActivity());
			break;
		case R.id.btn_register:
			getActivity().startActivity(
					new Intent(getActivity(), LoginAndRegisterActivity.class)
							.putExtra("type", 2));
			Utils.leftOutRightIn(getActivity());
			break;
		case R.id.img_select:
			pic_pop = new SelectPicPopwindow(getActivity(), v, "拍照", "从相册选择");
			pic_pop.setmSelectOnclick(this);
			pic_pop.show();
			break;
		case R.id.img_more:
			showPop(v);
			break;
		default:
			break;
		}
	}

	private MyRightPopWindow pop;

	private void showPop(View v) {
		pop = new MyRightPopWindow(getActivity(), v);
		pop.setOnlistOnclick(new OnlistOnclick() {

			@Override
			public void onclick(int position) {
				if (position == 0) {
					quitPrompt();
				}
				if (position == 1) {
					startActivity(new Intent(getActivity(),
							EditPassWordActivity.class));
					Utils.leftOutRightIn(getActivity());
				}
			}
		});
		pop.show();

	}

	private void quitPrompt() {
		PromptDialog.Builder dialog = DialogUtil.confirmDialog(getActivity(),
				"确定要退出吗?", "确定", "取消", new ConfirmDialog() {
					@Override
					public void onOKClick() {
						SharedUtils.clearData();
						Utils.cleanDatabaseByName(getActivity());
						MyApplation.exit(true);

					}

					@Override
					public void onCancleClick() {
					}
				});
		dialog.show();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Utils.getFocus(txt_title);
		if (requestCode == PhotoUtils.INTENT_REQUEST_CODE_ALBUM) {
			if (resultCode == getActivity().RESULT_OK) {
				if (data.getData() == null) {
					return;
				}
				if (!FileUtils.isSdcardExist()) {
					ToastUtil.showToast("SD卡不可用,请检查", Toast.LENGTH_SHORT);
					return;
				}
				Uri uri = data.getData();
				String[] proj = { MediaStore.Images.Media.DATA };
				@SuppressWarnings("deprecation")
				Cursor cursor = getActivity().managedQuery(uri, proj, null,
						null, null);
				if (cursor != null) {
					int column_index = cursor
							.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
					if (cursor.getCount() > 0 && cursor.moveToFirst()) {
						String path = cursor.getString(column_index);
						Bitmap bitmap = BitmapFactory.decodeFile(path);
						if (PhotoUtils.bitmapIsLarge(bitmap)) {
							cropPhoto(path);
						} else {
							setHead(bitmap, path);
						}
					}
				}
			}
		}
		if (requestCode == PhotoUtils.INTENT_REQUEST_CODE_CAMERA) {
			String path = mTakePicturePath;
			Bitmap bitmap = BitmapFactory.decodeFile(path);
			if (PhotoUtils.bitmapIsLarge(bitmap)) {
				cropPhoto(path);
			} else {
				setHead(bitmap, path);
			}
		}
		if (requestCode == PhotoUtils.INTENT_REQUEST_CODE_CROP) {
			if (resultCode == getActivity().RESULT_OK) {
				String path = data.getStringExtra("path");
				if (path != null) {
					Bitmap bitmap = BitmapFactory.decodeFile(path);
					if (bitmap != null) {
						setHead(bitmap, path);
					}
				}
			}
		}
	}

	@Override
	public void menu1_select() {
		mTakePicturePath = takePicture();
	}

	@Override
	public void menu2_select() {
		Intent intent = new Intent(Intent.ACTION_PICK, null);
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				"image/*");
		startActivityForResult(intent, PhotoUtils.INTENT_REQUEST_CODE_ALBUM);

	}

	/**
	 * 通过手机照相获取图片
	 * 
	 * @param activity
	 * @return 照相后图片的路径
	 */
	public String takePicture() {
		FileUtils.createDirFile(PhotoUtils.IMAGE_PATH);
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		String path = PhotoUtils.IMAGE_PATH + UUID.randomUUID().toString()
				+ "jpg";
		File file = FileUtils.createNewFile(path);
		if (file != null) {
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
		}
		startActivityForResult(intent, PhotoUtils.INTENT_REQUEST_CODE_CAMERA);
		return path;
	}

	public void cropPhoto(String path) {
		Intent intent = new Intent(getActivity(), ImageFactoryActivity.class);
		if (path != null) {
			intent.putExtra("path", path);
			intent.putExtra(ImageFactoryActivity.TYPE,
					ImageFactoryActivity.CROP);
		}
		startActivityForResult(intent, PhotoUtils.INTENT_REQUEST_CODE_CROP);
	}

	/**
	 * 注册该广播
	 */
	public void registerBoradcastReceiver() {
		IntentFilter myIntentFilter = new IntentFilter();
		myIntentFilter.addAction(Constants.REGISTER_SUCCESS);
		myIntentFilter.addAction(Constants.REFUSH_MY_ORDER_LIST);

		getActivity().registerReceiver(mBroadcastReceiver, myIntentFilter);
	}

	/**
	 * 定义广播
	 */
	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(Constants.REGISTER_SUCCESS)) {
				mScrollView.setVisibility(View.VISIBLE);
				img_more.setVisibility(View.VISIBLE);
				layout_login.setVisibility(View.GONE);
				txt_jifen.setText(SharedUtils.getScore() + "分");
				txt_nick_name.setText(SharedUtils.getUserName());
				dingDan_view.getOrderList();
			} else if (action.equals(Constants.REFUSH_MY_ORDER_LIST)) {
				dingDan_view.getOrderList();
			}
		}
	};

	public void onDestroy() {
		super.onDestroy();
		getActivity().unregisterReceiver(mBroadcastReceiver);
	};
}
