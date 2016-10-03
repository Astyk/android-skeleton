package com.github.willjgriff.skeleton.ui.form;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.willjgriff.skeleton.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Will on 28/09/2016.
 */

public class PaginationTest extends Fragment {

	private BasicAdapter mBasicAdapter;
	private int dataCount = 0;
	private boolean mGettingData = false;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_pagination_test, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.fragment_pagination_test_recycler);
		recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

		mBasicAdapter = new BasicAdapter(createSomeData(), new ListEndApproachingListener() {
			@Override
			public void listEndApproaching() {

				if (!mGettingData) {
					mGettingData = true;
					addNewData();
				}
			}
		});

		recyclerView.setAdapter(mBasicAdapter);
	}

	public List<String> createSomeData() {
		List<String> data = new ArrayList<>();
		int maxDataValue = dataCount + 20;

		for (; dataCount < maxDataValue; dataCount++) {
			data.add(String.valueOf(dataCount));
		}

		return data;
	}

	private void addNewData() {
		Runnable uiRunnable = new Runnable() {
			@Override
			public void run() {
				mBasicAdapter.addData(createSomeData());
				mBasicAdapter.lastPageLoaded();
			}
		};

		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(3000);
					getActivity().runOnUiThread(uiRunnable);
					mGettingData = false;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		new Thread(runnable).start();
	}

	public interface ListEndApproachingListener {
		void listEndApproaching();
	}
}
