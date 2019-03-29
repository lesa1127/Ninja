package de.baumann.browser.Browser;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.BottomSheetDialog;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.TextView;

import de.baumann.browser.Activity.Settings_Aria2Activity;
import de.baumann.browser.Ninja.R;
import de.baumann.browser.Unit.BrowserUnit;
import de.baumann.browser.Unit.IntentUnit;
import lib.aria2.Arai2_Addurl;

public class NinjaDownloadListener implements DownloadListener {
    private final Context context;

    public NinjaDownloadListener(Context context) {
        super();
        this.context = context;
    }

    @Override
    public void onDownloadStart(final String url, String userAgent, final String contentDisposition, final String mimeType, long contentLength) {
        final Context holder = IntentUnit.getContext();
        if (holder == null || !(holder instanceof Activity)) {
            BrowserUnit.download(context, url, contentDisposition, mimeType);
            return;
        }

        String text = holder.getString(R.string.dialog_title_download) + " - " + URLUtil.guessFileName(url, contentDisposition, mimeType);
        final BottomSheetDialog dialog = new BottomSheetDialog(holder);
        View dialogView = View.inflate(holder, R.layout.dialog_action_download, null);
        TextView textView = dialogView.findViewById(R.id.dialog_text);
        textView.setText(text);
        Button action_ok = dialogView.findViewById(R.id.action_ok);
        action_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BrowserUnit.download(holder, url, contentDisposition, mimeType);
                dialog.cancel();
            }
        });
        Button action_cancel = dialogView.findViewById(R.id.action_cancel);
        action_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        //aria2
        Button aria2putserve = dialogView.findViewById(R.id.action_aria2);
        aria2putserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences settings = context.getSharedPreferences(Settings_Aria2Activity.DATA_SAVE_FILE, 0);
                Arai2_Addurl task = new Arai2_Addurl(
                        settings.getString(Settings_Aria2Activity.DATA_HOST,"localhost")+":"+settings.getString(Settings_Aria2Activity.DATA_PORT,"6800"),
                        settings.getString(Settings_Aria2Activity.DATA_TOKEN,""),
                        url,
                        settings.getBoolean(Settings_Aria2Activity.DATA_ABLESSL,false)
                );
                Thread taskthread = new Thread(task);
                taskthread.start();
                dialog.cancel();
            }
        });

        dialog.setContentView(dialogView);
        dialog.show();

    }
}
