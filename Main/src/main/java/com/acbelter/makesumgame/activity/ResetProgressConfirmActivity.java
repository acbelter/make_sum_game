/*
 * Copyright 2014 acbelter
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

package com.acbelter.makesumgame.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import com.acbelter.makesumgame.R;

public class ResetProgressConfirmActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_progress_confirm);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.exit_slide_in, R.anim.exit_slide_out);
    }

    public void resetGameProgress(View view) {
        setResult(RESULT_OK);
        finish();
        overridePendingTransition(R.anim.exit_slide_in, R.anim.exit_slide_out);
    }

    public void cancelConfirmation(View view) {
        setResult(RESULT_CANCELED);
        finish();
        overridePendingTransition(R.anim.exit_slide_in, R.anim.exit_slide_out);
    }
}
