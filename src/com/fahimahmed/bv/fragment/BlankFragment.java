package com.fahimahmed.bv.fragment;

import com.fahimahmed.bv.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BlankFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		int i = getArguments().getInt("menu_position");
		String itemText = getResources().getStringArray(R.array.menu_array)[i];
		getActivity().setTitle(itemText);
		return inflater.inflate(R.layout.fragment_blank, container, false);
	}
}
