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
import android.widget.Toast;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

public class VideoViewDemo extends BaseActivity {

	/**
	 * TODO: Set the path variable to a streaming video URL or a local media file
	 * path.
	 */

	boolean ifUpdate;;
	

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		setContentView(R.layout.layout_videoview);

		playfunction();	

	}

	
	void playfunction(){
		
		
		Bundle bundle = getIntent().getExtras();
		
		String path="http://dlqncdn.miaopai.com/stream/MVaux41A4lkuWloBbGUGaQ__.mp4";
		if( bundle != null )
		{
			path = bundle.getString(INTENT_EXTRA, "");
		}
		
		 VideoView mVideoView;
		mVideoView = (VideoView) findViewById(R.id.surface_view);

		
      if (path == "") {
			// Tell the user to provide a media file URL/path.
			Toast.makeText(VideoViewDemo.this, "Please edit VideoViewDemo Activity, and set path" + " variable to your media file URL/path", Toast.LENGTH_LONG).show();
			return;
		} else {
			/*
			 * Alternatively,for streaming media you can use
			 * mVideoView.setVideoURI(Uri.parse(URLstring));
			 */
			mVideoView.setVideoPath(path);
			mVideoView.setMediaController(new MediaController(this));
			mVideoView.requestFocus();

			mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
				@Override
				public void onPrepared(MediaPlayer mediaPlayer) {
					// optional need Vitamio 4.0
					mediaPlayer.setPlaybackSpeed(1.0f);
				}
			});
		}
	}
	
}
