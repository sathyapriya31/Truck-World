package com.spritle.truck_apps;

import org.codepond.wizardroid.WizardStep;
import org.codepond.wizardroid.persistence.ContextVariable;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;

public class EventsList extends WizardStep {

	/**
	 * Tell WizarDroid that these are context variables. These values will be
	 * automatically bound to any field annotated with {@link ContextVariable}.
	 * NOTE: Context Variable names are unique and therefore must have the same
	 * name wherever you wish to use them.
	 */
	@ContextVariable
	private String listdata;
	@ContextVariable
	private String lastname;
	ListView lView;
	View v;

	// You must have an empty constructor for every step
	public EventsList() {
	}

	// Set your layout here
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.eventslist_layout, container, false);
		lView = (ListView) v.findViewById(R.id.listView1);

		String[] name = new String[] { "Radio Service Events",
				"Equipment Inspection", "Facility Inspection",
				"Cargo Inspection" };

		// Instantiating array adapter to populate the listView
		// The layout android.R.layout.simple_list_item_single_choice creates
		// radio button for each listview item
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_single_choice, name);

		lView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		// LETS HIGHLIGHT SELECTED ITEMS
		lView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView arg0, View view, int position,
					long itemId) {
				listdata=(String)arg0.getItemAtPosition(position);

			}
		});

		return v;
	}
	@Override
	public void onExit(int exitCode) {
		switch (exitCode) {
		case WizardStep.EXIT_NEXT:
			
			break;
		case WizardStep.EXIT_PREVIOUS:
			// Do nothing...
			break;
	
		}
	}

	
}
