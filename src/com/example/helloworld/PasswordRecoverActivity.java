package com.example.helloworld;

import com.example.helloworld.fragments.PasswordRecoverStep1Fragment;
import com.example.helloworld.fragments.PasswordRecoverStep2Fragment;

import android.app.Activity;
import android.os.Bundle;

public class PasswordRecoverActivity extends Activity {

	PasswordRecoverStep1Fragment frag1 = new PasswordRecoverStep1Fragment();
	PasswordRecoverStep2Fragment frag2 = new PasswordRecoverStep2Fragment();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_password_recover);

//		PasswordRecoverStep1Fragment frag = (PasswordRecoverStep1Fragment) getFragmentManager()
//				.findFragmentById(R.layout.fragment_password_recover_step1);

	}
}
