package com.fahimahmed.bv.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.fahimahmed.bv.R;

public class BlankFragment extends DialogFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		try{
			int i = getArguments().getInt("menu_position");
			String itemText = getResources().getStringArray(R.array.menu_array)[i];
			getActivity().setTitle(itemText);
		}catch(Exception e){
			e.printStackTrace();
		}
		return inflater.inflate(R.layout.fragment_blank, container, false);
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Dialog dialog = super.onCreateDialog(savedInstanceState);
		dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		return dialog;
	}

	public static BlankFragment newInstance() {
		return new BlankFragment();
	}
}
