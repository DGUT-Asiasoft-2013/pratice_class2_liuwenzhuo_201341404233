package com.example.helloworld;

import java.io.IOException;

import com.example.helloworld.entity.Server;
import com.example.helloworld.entity.User;
import com.example.helloworld.fragments.PasswordRecoverStep1Fragment;
import com.example.helloworld.fragments.PasswordRecoverStep1Fragment.OnGoNextListener;
import com.example.helloworld.fragments.PasswordRecoverStep2Fragment;
import com.example.helloworld.fragments.PasswordRecoverStep2Fragment.OnSubmitListener;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PasswordRecoverActivity extends Activity {

	PasswordRecoverStep1Fragment step1 = new PasswordRecoverStep1Fragment();
	PasswordRecoverStep2Fragment step2 = new PasswordRecoverStep2Fragment();
	User user = null;
	ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_password_recover);
		getFragmentManager().beginTransaction().replace(R.id.container, step1).commit();
		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("«Î…‘µ»");
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.setCancelable(false);

		step1.setOnGoNextListener(new OnGoNextListener() {

			@Override
			public void onGoNext() {
				goStep2();
			}
		});
		step2.setOnSubmitListener(new OnSubmitListener() {

			@Override
			public void onSubmit() {
				// TODO Auto-generated method stub
				PasswordRecoverActivity.this.submit();
			}
		});
	}

	void goStep2() {

		progressDialog.show();

		OkHttpClient client = Server.getSharedClient();

		MultipartBody requestBody = new MultipartBody.Builder().addFormDataPart("email", step1.getEmail()).build();

		Request request = Server.requestBuilderWithApi("inputemail").method("get", null).post(requestBody).build();

		client.newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(final Call arg0, final Response arg1) throws IOException {
				// TODO Auto-generated method stub
				String jsonString = arg1.body().string();
				ObjectMapper mapper = new ObjectMapper();

				try {
					user = mapper.readValue(jsonString, User.class);
					if (user != null) {
						runOnUiThread(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								PasswordRecoverActivity.this.onResponse(arg0, arg1);
							}
						});
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							Toast.makeText(PasswordRecoverActivity.this, "” œ‰≤ª¥Ê‘⁄", Toast.LENGTH_SHORT).show();
							progressDialog.dismiss();
						}
					});
				}
			}

			@Override
			public void onFailure(Call arg0, IOException arg1) {
				// TODO Auto-generated method stub
				PasswordRecoverActivity.this.onFailure(arg0, arg1);
			}
		});

	}

	private void onFailure(Call arg0, IOException arg1) {
		// TODO Auto-generated method stub
		progressDialog.dismiss();
		new AlertDialog.Builder(this).setTitle("«Î«Û ß∞‹").setMessage("").setNegativeButton("∫√", null).show();
	}

	private void onResponse(Call arg0, Response arg1) {
		// TODO Auto-generated method stub
		progressDialog.dismiss();
		getFragmentManager()
				.beginTransaction().setCustomAnimations(R.animator.slide_in_right, R.animator.slide_out_left,
						R.animator.slide_in_left, R.animator.slide_out_right)
				.replace(R.id.container, step2).addToBackStack(null).commit();
	}

	private void submit() {
		progressDialog.show();
		String password = step2.getPassword();
		String passwordRepeat = step2.getPasswordRepeat();

		if (!password.equals(passwordRepeat)) {
			Toast.makeText(this, "√‹¬Î≤ª“ª÷¬", Toast.LENGTH_SHORT).show();
			progressDialog.dismiss();
			return;
		}

		OkHttpClient client = Server.getSharedClient();

		MultipartBody requestBody = new MultipartBody.Builder().addFormDataPart("password", MD5.getMD5(password))
				.addFormDataPart("account", user.getAccount()).build();

		Request request = Server.requestBuilderWithApi("updatePwd").method("get", null).post(requestBody).build();

		client.newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(final Call arg0, final Response arg1) throws IOException {
				// TODO Auto-generated method stub
				String jsonString = arg1.body().string();
				ObjectMapper mapper = new ObjectMapper();

				try {
					final int resultCode = mapper.readValue(jsonString, Integer.class);
					System.out.println("resultCode====================" + resultCode);

					if (resultCode == 1) {
						runOnUiThread(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub

								PasswordRecoverActivity.this.onSubmitResponse(arg0, arg1);
							}
						});
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							Toast.makeText(PasswordRecoverActivity.this, "—È÷§¬Î¥ÌŒÛ", Toast.LENGTH_SHORT).show();
							progressDialog.dismiss();
						}
					});
				}
			}

			@Override
			public void onFailure(Call arg0, IOException arg1) {
				// TODO Auto-generated method stub
				PasswordRecoverActivity.this.onSubmitFailure(arg0, arg1);
			}
		});

	}

	private void onSubmitResponse(Call arg0, Response arg1) {
		Toast.makeText(this, "–ﬁ∏ƒ√‹¬Î≥…π¶£¨«Î÷ÿ–¬µ«¬º", Toast.LENGTH_SHORT).show();
		finish();
		Intent itnt = new Intent(this, LoginActivity.class);
		startActivity(itnt);
	}

	private void onSubmitFailure(Call arg0, final IOException arg1) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				new AlertDialog.Builder(PasswordRecoverActivity.this).setTitle("–ﬁ∏ƒ√‹¬Î ß∞‹")
						.setMessage(arg1.getLocalizedMessage()).show();
			}
		});
	}
}
