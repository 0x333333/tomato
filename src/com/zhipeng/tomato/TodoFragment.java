package com.zhipeng.tomato;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public final class TodoFragment extends Fragment {
	private TextView text;
	private TextView text2;

	public static TodoFragment newInstance(String content) {
		TodoFragment fragment = new TodoFragment();
		return fragment;
	}	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@SuppressLint("NewApi")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		text = new TextView(getActivity());
        text.setGravity(Gravity.CENTER);
        text.setText(getString(R.string.noTodo));
//        text.setTextColor(getResources().getColor(R.color.backup));
        text.setTextSize(20 * getResources().getDisplayMetrics().density);
        text.setPadding(20, 20, 20, 5);
        
        text2 = new TextView(getActivity());
        text2.setGravity(Gravity.CENTER);
        text2.setText(getString(R.string.addTodo));
//        text2.setTextColor(getResources().getColor(R.color.backup));
        text2.setTextSize(20 * getResources().getDisplayMetrics().density);
        text2.setPadding(20, 5, 20, 20);
        
        LinearLayout layout = new LinearLayout(getActivity());
		layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		layout.setOrientation(1);
		layout.setGravity(Gravity.CENTER);
        layout.addView(text);
        layout.addView(text2);

        return layout;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}
}
