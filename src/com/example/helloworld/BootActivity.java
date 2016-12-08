package com.example.helloworld;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BootActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_boot);

	}

	@Override
	protected void onResume() {
		super.onResume();

		// Handler handler = new Handler();
		// handler.postDelayed(new Runnable() {
		// private int abcd = 0;
		//
		// @Override
		// public void run() {
		// startLoginActivity();
		// }
		// }, 1000);

		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder().url("http://119.29.254.43/hello.txt").method("GET", null)
				.build();

		client.newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(Call arg0, final Response arg1) throws IOException {
				// TODO Auto-generated method stub
				BootActivity.this.runOnUiThread(new Runnable() {

					@Override
					public void run() {

						try {
							Toast.makeText(BootActivity.this, arg1.body().string(), Toast.LENGTH_SHORT).show();

							startLoginActivity();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
			}

			@Override
			public void onFailure(Call arg0, final IOException arg1) {
				// TODO Auto-generated method stub
				BootActivity.this.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						Toast.makeText(BootActivity.this, arg1.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
					}
				});
			}
		});
	}

	void startLoginActivity() {
		Intent itnt = new Intent(this, LoginActivity.class);
		startActivity(itnt);
		finish();
	}
}
