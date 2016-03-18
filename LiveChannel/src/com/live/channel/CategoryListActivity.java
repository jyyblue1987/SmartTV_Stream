package com.live.channel;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.live.channel.network.ServerManager;
import com.live.channel.network.ServerTask;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import common.image.load.ImageUtils;
import common.library.utils.DataUtils;
import common.library.utils.MessageUtils;
import common.library.utils.MyTime;
import common.list.adapter.ItemCallBack;
import common.list.adapter.MyListAdapter;
import common.list.adapter.ViewHolder;
import common.network.utils.LogicResult;
import common.network.utils.ResultCallBack;

public class CategoryListActivity extends BaseActivity {
	ListView			m_listItems = null;
	MyListAdapter		m_adapterList = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category_page);
		
		loadComponents();
	}
	
	protected void findViews()
	{
		super.findViews();

		m_listItems = (ListView) findViewById(R.id.list_items);			
	}
	
	protected void initData()
	{
		super.initData();
		
		getCategoryList();
	}
	
	protected void initEvents()
	{ 
		super.initEvents();
				
	}
	
	private void getCategoryList()
	{
		showLoadingProgress();
		
		String userid = DataUtils.getPreference(Const.USER_ID, "");
		ServerManager.getCategoryList(userid, new ResultCallBack() {
			
			@Override
			public void doAction(LogicResult result) {
				hideProgress();
				
				JSONObject data = result.getData();
				if( data == null || data.has("category_list") == false )
				{
					MessageUtils.showMessageDialog(CategoryListActivity.this, "There is no category list");
					return;
				}		
				
				JSONArray array = data.optJSONArray("category_list");
				showCategoryList(array);
								
			}
		});
	}
	
	private void showCategoryList(JSONArray array)
	{
		List<JSONObject> list = new ArrayList<JSONObject>();
		String now = MyTime.getCurrentDate();
		
		for(int i = 0; i < array.length(); i++ )
		{
			JSONObject data = array.optJSONObject(i);
			
//			String expire_date = data.optString("Expiry_date", now);
//			if( now.compareTo(expire_date) < 0 )
//				continue;
//			
			list.add(data);
		}
		
		
		m_adapterList = new CategoryListAdapter(this, list, R.layout.fragment_category_list_item, null);
		
		m_listItems.setAdapter(m_adapterList);
	}
	
	class CategoryListAdapter extends MyListAdapter {
		public CategoryListAdapter(Context context, List<JSONObject> data,
			int resource, ItemCallBack callback) {
			super(context, data, resource, callback);
		}
		@Override
		protected void loadItemViews(View rowView, int position)
		{
			final JSONObject item = getItem(position);
			
			DisplayImageOptions options = ImageUtils.buildUILOption(R.drawable.ic_launcher).build();
			ImageLoader.getInstance().displayImage(ServerTask.SERVER_UPLOAD_PHOTO_PATH + item.optString("category_image", ""), (ImageView)ViewHolder.get(rowView, R.id.img_thumbnail), options);

			((TextView)ViewHolder.get(rowView, R.id.txt_name)).setText(item.optString("category_name", ""));
			
		}	
	}
	
	 	 
	 
}
