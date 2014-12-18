/*
 * Copyright (C) 2014 Carbon Development
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.carbon.fibers.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

import com.carbon.fibers.R;

public class CarbonChangelog extends PreferenceFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.changelog, container, false);
        final Activity activity = getActivity();
        if (v != null) {
            if (hasNetworkConnection(activity)) {
                WebView webView = (WebView) v.findViewById(R.id.webview);
                webView.loadUrl(getString(R.string.changelog_url));
            } else {
                AlertDialog alert = new AlertDialog.Builder(activity).create();
                alert.setTitle(getString(R.string.changelog_not_connected_title));
                alert.setMessage(getString(R.string.changelog_not_connected_msg));
                alert.setButton(getString(android.R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        activity.getFragmentManager().beginTransaction().remove(CarbonChangelog.this).commit();
                    }
                });
                alert.show();
            }
        }
        return v;
    }

    public boolean hasNetworkConnection(Activity activity) {
        ConnectivityManager cm =
                (ConnectivityManager)activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
