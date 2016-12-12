package com.example.helloworld;

import java.io.IOException;

import com.example.helloworld.entity.Article;
import com.example.helloworld.entity.Server;
import com.example.helloworld.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AddContentActivity extends Activity {

 ProgressDialog progressDialog;
Article article = null;
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_content);
			
		findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				addContent();
			}
		});
		
		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("请稍等");
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.setCancelable(false);
	}
	private void addContent() {
		// TODO Auto-generated method stub
		String title = ((EditText)findViewById(R.id.input_title)).getText().toString();
		String content = ((EditText)findViewById(R.id.input_content)).getText().toString();
		progressDialog.show();
		
		OkHttpClient client = Server.getSharedClient();
		
		MultipartBody requestBody = new MultipartBody.Builder().addFormDataPart("title",title)
				.addFormDataPart("text", content)
				.build();
		
		Request request = Server.requestBuilderWithApi("article").method("get", null).post(requestBody).build();
		
		client.newCall(request).enqueue(new Callback() {
			
			@Override
			public void onResponse(final Call arg0, final Response arg1) throws IOException {
				// TODO Auto-generated method stub
				String jsonString = arg1.body().string();
				ObjectMapper mapper = new ObjectMapper();

				try {
					article = mapper.readValue(jsonString, Article.class);
					if (article != null) {
						runOnUiThread(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								AddContentActivity.this.onResponse(arg0, arg1);
							}
						});
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							Toast.makeText(AddContentActivity.this, "添加文章失败", Toast.LENGTH_SHORT).show();
							progressDialog.dismiss();
						}
					});
				}
			}
			
			@Override
			public void onFailure(Call arg0, IOException arg1) {
				// TODO Auto-generated method stub
				AddContentActivity.this.onFailure(arg0, arg1);
			}
		});
		
	}
	private void onFailure(Call arg0, IOException arg1) {
		// TODO Auto-generated method stub
		
	}
	private void onResponse(Call arg0, Response arg1) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "提交成功", Toast.LENGTH_SHORT).show();
		finish();
		Intent itnt = new Intent(this, HelloWorldActivity.class);
		startActivity(itnt);
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		overridePendingTransition(0, R.anim.slide_out_top);
	}
}
