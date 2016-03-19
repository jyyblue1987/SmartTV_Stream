/*
 * Copyright (C) 2013 yixia.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.live.channel;


import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import tv.danmaku.ijk.media.widget.MediaController;
import tv.danmaku.ijk.media.widget.VideoView;

public class VideoViewIJK extends BaseActivity {

	private VideoView mVideoView;
	private View mBufferingIndicator;
	private MediaController mMediaController;
	
	
	boolean ifUpdate;;

	

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		setContentView(R.layout.layout_videoviewijk);

		playfunction();	

	}

	
	void playfunction(){
		
		
		
		Bundle bundle = getIntent().getExtras();
		
		String path="http://dlqncdn.miaopai.com/stream/MVaux41A4lkuWloBbGUGaQ__.mp4";
		if( bundle != null )
		{
			path = bundle.getString(INTENT_EXTRA, "");
		}
				
      if (path == "") {
			// Tell the user to provide a media file URL/path.
			Toast.makeText(VideoViewIJK.this, "Please edit VideoViewDemo Activity, and set path" + " variable to your media file URL/path", Toast.LENGTH_LONG).show();
			return;
		} else {
			mBufferingIndicator = findViewById(R.id.buffering_indicator);
			mMediaController = new MediaController(this);
		
			mVideoView = (VideoView) findViewById(R.id.video_view);
			mVideoView.setMediaController(mMediaController);
			mVideoView.setMediaBufferingIndicator(mBufferingIndicator);
			mVideoView.setVideoPath(path);
			mVideoView.requestFocus();
			mVideoView.start();
		}      	
	}
	
}
