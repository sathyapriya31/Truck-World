package com.spritle.truck_apps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codepond.wizardroid.WizardStep;
import org.codepond.wizardroid.persistence.ContextVariable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class CreateEvents extends WizardStep {

	/**
	 * Tell WizarDroid that these are context variables. These values will be
	 * automatically bound to any field annotated with {@link ContextVariable}.
	 * NOTE: Context Variable names are unique and therefore must have the same
	 * name wherever you wish to use them.
	 */
	@ContextVariable
	private String listdata;
	View view;
	ListView eventsListView;
	private ProgressDialog pDialog;

	// URL to get contacts JSON
	private static String url = "https://spritleapi.herokuapp.com/trucks.json";

	// JSON Node names

	private static final String TAG_ID = "id";
	private static final String TAG_TITLE = "title";
	private static final String TAG_DESCP = "description";
	private static final String TAG_CREATED = "created_at";
	// contacts JSONArray
	JSONArray contacts = null;
	LinearLayout scrollView;
	RelativeLayout relative_lay;
	ArrayList<String> arrayList = new ArrayList<String>();
	List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();
	String no_val = "No data found.";
	CustomAdapter adapter;
    public  ArrayList<EventModel> CustomListViewValuesArr = new ArrayList<EventModel>();
	// You must have an empty constructor for every step
	public CreateEvents() {
	}

	// Set your layout here
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.eventsform, container, false);

		eventsListView = (ListView) view.findViewById(R.id.eventlistdata);
		scrollView = (LinearLayout) view.findViewById(R.id.scrollView);

		if (listdata == "Radio Service Events") {

			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);

			if (haveNetworkConnection(getActivity())) {

				new GetContacts().execute();

				eventsListView.setVisibility(View.VISIBLE);
				scrollView.setVisibility(View.GONE);
			} else {
				if (arrayList.size() > 0) {
					Log.d("1--", "" + arrayList.size());
				} else {
					Log.d("2--", "" + arrayList.size());
				}
				arrayList.add(no_val);
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						getActivity(), android.R.layout.simple_list_item_1,
						arrayList);

				eventsListView.setAdapter(adapter);
				Toast.makeText(getActivity(), "No Internet Connection",
						Toast.LENGTH_SHORT).show();
				eventsListView.setVisibility(View.VISIBLE);
				scrollView.setVisibility(View.GONE);
			}
		} else if (listdata == "Equipment Inspection") {

			eventsListView.setVisibility(View.GONE);
			scrollView.setVisibility(View.VISIBLE);

		} else if (listdata == "Facility Inspection") {

			view = inflater.inflate(R.layout.facility_layout, container, false);
		} else if (listdata == "Cargo Inspection") {

			view = inflater.inflate(R.layout.cargo_layout, container, false);
		} else {

		}

		return view;
	}

	/**
	 * Called whenever the wizard proceeds to the next step or goes back to the
	 * previous step
	 */

	@Override
	public void onExit(int exitCode) {
		switch (exitCode) {
		case WizardStep.EXIT_NEXT:
			ViewGroup parent1 = (ViewGroup) view.getParent();
			parent1.removeView(view);
			break;
		case WizardStep.EXIT_PREVIOUS:

			ViewGroup parent = (ViewGroup) view.getParent();
			parent.removeView(view);
			// Do nothing...
			break;

		}
	}

	public boolean haveNetworkConnection(Context con) {
		boolean haveConnectedWifi = false;
		boolean haveConnectedMobile = false;

		ConnectivityManager cm = (ConnectivityManager) con
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] netInfo = cm.getAllNetworkInfo();
		for (NetworkInfo ni : netInfo) {
			if (ni.getTypeName().equalsIgnoreCase("WIFI"))
				if (ni.isConnected())
					haveConnectedWifi = true;
			if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
				if (ni.isConnected())
					haveConnectedMobile = true;
		}
		return haveConnectedWifi || haveConnectedMobile;
	}

	/**
	 * Async task class to get json by making HTTP call
	 * */
	private class GetContacts extends AsyncTask<Void, Void, Void> {

		private CustomAdapter adapter;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			pDialog = new ProgressDialog(getActivity());
			pDialog.setMessage("Please wait...");
			pDialog.setCancelable(false);
			pDialog.show();

		}

		@Override
		protected Void doInBackground(Void... arg0) {
			// Creating service handler class instance
			ServiceHandler sh = new ServiceHandler();

			// Making a request to url and getting response

			String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

			Log.d("Response: ", "> " + jsonStr);

			if (jsonStr != null) {
				try {

					JSONArray jsonarray = new JSONArray(jsonStr);
					// looping through All events
					for (int i = 0; i < jsonarray.length(); i++) {

						JSONObject c = jsonarray.getJSONObject(i);

						String name = c.getString(TAG_TITLE);
						String descp = c.getString(TAG_DESCP);
						Map<String, String> hm = new HashMap<String, String>();
						Log.d("Response12: ", "> " + name);

						hm.put("name", name);
						hm.put("descp", descp);

						arrayList.add(name);

						final EventModel sched = new EventModel();

						/******* Firstly take data in model object ******/
						sched.setName(name);
						sched.setDescription(descp);
						/******** Take Model Object in ArrayList **********/
						CustomListViewValuesArr.add(sched);
						/*
						 * HashMap<String, String> hm1 = new HashMap<String,
						 * String>(); hm1.put("name", name); hm1.put("descp",
						 * descp); aList.add(hm1);
						 */
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else {
				Log.e("ServiceHandler", "Couldn't get any data from the url");
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// Dismiss the progress dialog
			if (pDialog.isShowing())
				pDialog.dismiss();
			// ArrayAdapter<String> adapter;
			// if (arrayList.size() > 0) {
			// adapter = new ArrayAdapter<String>(getActivity(),
			// android.R.layout.simple_list_item_1, arrayList);
			// } else {
			//
			// arrayList.add(no_val);
			// adapter = new ArrayAdapter<String>(getActivity(),
			// android.R.layout.simple_list_item_1, arrayList);
			//
			// eventsListView.setAdapter(adapter);
			// }
			// eventsListView.setAdapter(adapter);

			adapter = new CustomAdapter(getActivity(),CustomListViewValuesArr);
			eventsListView.setAdapter(adapter);
			return;

		}
	}
}
