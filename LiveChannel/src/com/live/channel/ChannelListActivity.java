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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import common.image.load.ImageUtils;
import common.library.utils.MessageUtils;
import common.library.utils.MyTime;
import common.list.adapter.ItemCallBack;
import common.list.adapter.MyListAdapter;
import common.list.adapter.ViewHolder;
import common.manager.activity.ActivityManager;
import common.network.utils.LogicResult;
import common.network.utils.ResultCallBack;

public class ChannelListActivity extends BaseActivity {
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
		
		Bundle bundle = getIntent().getExtras();
		
		if( bundle != null )
		{
			String category_id = bundle.getString(INTENT_EXTRA, "");
			getChannelList(category_id);
		}
		
		
	}
	
	protected void initEvents()
	{ 
		super.initEvents();
				
		m_listItems.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {	
				gotoChannelListPage(pos);
			}
		});

	}
	
	private void gotoChannelListPage(int pos)
	{
		Bundle bundle = new Bundle();
		bundle.putString(INTENT_EXTRA, m_adapterList.getItem(pos).optString("channel_url", "0"));
//		ActivityManager.changeActivity(this, VideoViewDemo.class, bundle, false, null );
		ActivityManager.changeActivity(this, VideoViewIJK.class, bundle, false, null );
	}
	
	private void getChannelList(String category_id)
	{
		showLoadingProgress();
		
		ServerManager.getChannelList(category_id, new ResultCallBack() {
			
			@Override
			public void doAction(LogicResult result) {
				hideProgress();
				
				JSONObject data = result.getData();
				if( data == null || data.has("channels_list") == false )
				{
					MessageUtils.showMessageDialog(ChannelListActivity.this, "There is no channel list");
					return;
				}		
				
				JSONArray array = data.optJSONArray("channels_list");
				showChannelList(array);								
			}
		});
	}
	
	private void showChannelList(JSONArray array)
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
		
		
		m_adapterList = new ChannelListAdapter(this, list, R.layout.fragment_category_list_item, null);
		
		m_listItems.setAdapter(m_adapterList);
	}
	
	class ChannelListAdapter extends MyListAdapter {
		public ChannelListAdapter(Context context, List<JSONObject> data,
			int resource, ItemCallBack callback) {
			super(context, data, resource, callback);
		}
		@Override
		protected void loadItemViews(View rowView, int position)
		{
			final JSONObject item = getItem(position);
			
			DisplayImageOptions options = ImageUtils.buildUILOption(R.drawable.ic_launcher).build();
			ImageLoader.getInstance().displayImage(ServerTask.SERVER_UPLOAD_PHOTO_PATH + item.optString("channel_thumbnail", ""), (ImageView)ViewHolder.get(rowView, R.id.img_thumbnail), options);

			((TextView)ViewHolder.get(rowView, R.id.txt_name)).setText(item.optString("channel_title", ""));
			
		}	
	}
	
	 	 
	 
}
