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
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public final class TestFragment extends Fragment {
	private static final String KEY_CONTENT = "TestFragment:Content";

	private ProgressWheel pw;
	private TextView text;
	private Thread s;
	private boolean isStop = false; 

	private boolean running;
	private int progress = 0;

	public static TestFragment newInstance(String content) {
		TestFragment fragment = new TestFragment();

		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < 20; i++) {
			builder.append(content).append(" ");
		}
		builder.deleteCharAt(builder.length() - 1);
		fragment.mContent = builder.toString();

		return fragment;
	}

	private String mContent = "???";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if ((savedInstanceState != null)
				&& savedInstanceState.containsKey(KEY_CONTENT)) {
			mContent = savedInstanceState.getString(KEY_CONTENT);
		}
	}

	@SuppressLint("ResourceAsColor")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		pw = new ProgressWheel(getActivity(), null);

		final Runnable r = new Runnable() {
			public void run() {
				running = true;
				while (progress < 150000 && !isStop) {
					progress++;
					pw.incrementProgress(progress);
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				running = false;
			}
		};

		LayoutParams params = new LayoutParams((int) (getResources()
				.getDisplayMetrics().density * 250), (int) (getResources()
				.getDisplayMetrics().density * 250));
		params.gravity = Gravity.CENTER;
		pw.setLayoutParams(params);
		pw.setText(getString(R.string.start));
		pw.setTextColor(R.color.textColor);
		pw.setTextSize((int) (50 * getResources().getDisplayMetrics().density));
		pw.setRimWidth(10);
		pw.setBarWidth(15);
		pw.setBarLength(60);
		pw.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (!running) {
					progress = 0;
					pw.resetCount();
					isStop = false;
					s = new Thread(r);
					s.start();
				}
			}
		});

		text = new TextView(getActivity());
		text.setGravity(Gravity.CENTER);
		text.setText(getString(R.string.give_up));
		text.setTextSize(10 * getResources().getDisplayMetrics().density);
		text.setPadding(20, 20, 20, 20);
		text.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				AlertDialog dialog = new AlertDialog.Builder(getActivity())
				.setTitle(getResources().getString(R.string.stopTitle))
				.setMessage(getResources().getString(R.string.stopMsg))
				.setPositiveButton(getResources().getString(R.string.yes),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								isStop = true;
								pw.stopSpinning();
								dialog.dismiss();
							}
						})
				.setNegativeButton(getResources().getString(R.string.no),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
							}
						})
				.create();
				dialog.show();

			}
		});

		LinearLayout layout = new LinearLayout(getActivity());
		layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		layout.setOrientation(1);
		layout.setGravity(Gravity.CENTER);
		layout.addView(pw);
		layout.addView(text);

		return layout;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(KEY_CONTENT, mContent);
	}
}
