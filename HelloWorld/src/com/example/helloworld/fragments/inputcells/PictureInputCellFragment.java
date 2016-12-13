package com.example.helloworld.fragments.inputcells;

import com.example.helloworld.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class PictureInputCellFragment extends BaseInputCellFragment {

	final int REQUESTCODE_CAMERA = 1;
	final int REQUESTCODE_ALBUM = 2;

	ImageView imageView;
	TextView labelText;
	TextView hintText;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_inputcell_picture, container);

		imageView = (ImageView) view.findViewById(R.id.image);
		labelText = (TextView) view.findViewById(R.id.label);
		hintText = (TextView) view.findViewById(R.id.hint);

		imageView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onImageClickListener();
			}
		});
		return view;
	}

	private void onImageClickListener() {

		String[] items = { "����", "���" };

		new AlertDialog.Builder(getActivity()).setTitle(labelText.getText())
				.setItems(items, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						switch (which) {
						case 0:
							takePhoto();
							break;

						case 1:
							pickFromAlum();
							break;

						default:
							break;

						}
					}
				}).setNegativeButton("ȡ��", null).show();
	}

	void takePhoto() {
		Intent itnt = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(itnt, REQUESTCODE_CAMERA);
	}

	void pickFromAlum() {
		Intent itnt = new Intent(Intent.ACTION_PICK);
		itnt.setType("image/*");
		startActivityForResult(itnt, REQUESTCODE_ALBUM);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_CANCELED) {
			return;
		}

		if (requestCode == REQUESTCODE_CAMERA) {
			Bitmap bmp = (Bitmap) data.getExtras().get("data");
			imageView.setImageBitmap(bmp);
			// Log.d("camera data", dataObj.getClass().toString());
			// Toast.makeText(getActivity(), data.getDataString(),
			// Toast.LENGTH_LONG).show();
		} else if (requestCode == REQUESTCODE_ALBUM) {

			Bitmap bmp = BitmapFactory.decodeFile(data.getDataString());

		}
	}

	public void setLabelText(String labelText) {
		this.labelText.setText(labelText);
	}

	public void setHintText(String hintText) {
		this.hintText.setHint(hintText);
	}
}
