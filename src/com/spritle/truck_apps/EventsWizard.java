/*
 * Copyright 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.spritle.truck_apps;

import org.codepond.wizardroid.WizardFlow;
import org.codepond.wizardroid.layouts.BasicWizardLayout;

public class EventsWizard extends BasicWizardLayout {
	/**
	 * Note that initially BasicWizardLayout inherits from
	 * {@link android.support.v4.app.Fragment} and therefore you must have an
	 * empty constructor
	 */
	public EventsWizard() {
		super();
	}

	// You must override this method and create a wizard flow by
	// using WizardFlow.Builder as shown in this example
	@Override
	public WizardFlow onSetup() {
		// TODO Auto-generated method stub
		/*
		 * Optionally, you can set different labels for the control buttons
		 * 
		 * setNextButtonLabel("Advance"); setBackButtonLabel("Return");
		 * setFinishButtonLabel("Finalize");
		 */

		 // to create the wizard flow.
		WizardFlow droid= new WizardFlow.Builder().addStep(EventsList.class)
				.addStep(CreateEvents.class).create();
		 return droid;
	}
	
}