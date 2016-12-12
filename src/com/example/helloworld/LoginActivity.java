package com.example.helloworld;

import java.io.IOException;

import com.example.helloworld.entity.Server;
import com.example.helloworld.entity.User;
import com.example.helloworld.fragments.inputcells.SimpleTextInputCellFragment;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends Activity {
	SimpleTextInputCellFragment fragAccount, fragPassword;
	ProgressDialog progressDialog;
	User user = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				goRegister();
			}
		});

		findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				goLogin();
			}
		});

		findViewById(R.id.btn_forgot_password).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				goRecoverPassword();
			}
		});

		fragAccount = (SimpleTextInputCellFragment) getFragmentManager().findFragmentById(R.id.input_account);
		fragPassword = (SimpleTextInputCellFragment) getFragmentManager().findFragmentById(R.id.input_password);
		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("请稍等");
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.setCancelable(false);
	}

	@Override
	protected void onResume() {
		super.onResume();

		fragAccount.setLabelText("账户名");
		fragAccount.setHintText("请输入账户名");
		fragPassword.setLabelText("密码");
		fragPassword.setHintText("请输入密码");
		fragPassword.setIsPassword(true);
	}

	void goRegister() {
		Intent itnt = new Intent(this, RegisterActivity.class);
		startActivity(itnt);
	}

	void goLogin() {
		String account = fragAccount.getText();
		String password = fragPassword.getText();
		progressDialog.show();

		OkHttpClient client = Server.getSharedClient();

		MultipartBody requestBody = new MultipartBody.Builder()
				.addFormDataPart("account", account)
				.addFormDataPart("password", MD5.getMD5(password))
				.build();

		Request request = Server.requestBuilderWithApi("login").method("get", null).post(requestBody).build();

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
								LoginActivity.this.onResponse(arg0, arg1);
							}
						});
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							Toast.makeText(LoginActivity.this, "数据解析异常", Toast.LENGTH_SHORT).show();
							progressDialog.dismiss();
						}
					});
				}
			}

			@Override
			public void onFailure(Call arg0, IOException arg1) {
				// TODO Auto-generated method stub
				LoginActivity.this.onFailure(arg0, arg1);
			}
		});
	}

	private void onResponse(Call call, Response response) {
		progressDialog.dismiss();
		try {
			String userAccount = user.getAccount();
			new AlertDialog.Builder(this).setTitle("登录成功").setMessage("hello!"+userAccount)
					.setNegativeButton("确定", new OnClickListener() {
						
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							// TODO Auto-generated method stub
							Intent itnt = new Intent(LoginActivity.this, HelloWorldActivity.class);
							startActivity(itnt);
						}
					}).show();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			onFailure(call, e);
		}
	}

	private void onFailure(Call arg0, Exception arg1) {
		progressDialog.dismiss();
		new AlertDialog.Builder(this).setTitle("登录失败").setMessage("")
				.setNegativeButton("好", null).show();
	}

	void goRecoverPassword() {
		Intent itnt = new Intent(this, PasswordRecoverActivity.class);
		startActivity(itnt);
	}
}
