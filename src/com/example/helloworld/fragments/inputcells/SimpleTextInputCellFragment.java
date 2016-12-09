package com.example.helloworld.fragments.inputcells;

import com.example.helloworld.R;

import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class SimpleTextInputCellFragment extends BaseInputCellFragment {

	TextView label;
	EditText edit;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_inputcell_simpletext, container);
		label = (TextView) view.findViewById(R.id.label);
		edit = (EditText) view.findViewById(R.id.edit);
		return view;
	}

	@Override
	public void setLabelText(String labelText) {
		label.setText(labelText);
	}

	@Override
	public void setHintText(String hintText) {
		edit.setHint(hintText);
	}

	public String getText() {
		String text = edit.getText().toString();
		return text;
	}

	public void setIsPassword(boolean isPassword) {
		if (isPassword) {
			edit.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
		} else {
			edit.setInputType(InputType.TYPE_CLASS_TEXT);
		}
	}
}
